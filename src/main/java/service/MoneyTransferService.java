package service;

import demo.Counter;
import dto.Account;
import dto.Transfer;
import exception.BankException;
import exception.NotEnoughMoneyException;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class MoneyTransferService {

    private Logger logger = Logger.getLogger(MoneyTransferService.class);

    public void tryTransferMoney(Transfer transfer) throws BankException {
        Counter.operationCounter.getAndIncrement();

        Account fromAccount = transfer.getFrom();
        Account toAccount = transfer.getTo();
        long amount = transfer.getAmount();

        ReentrantLock firstLock = fromAccount.getId() < toAccount.getId() ?
                fromAccount.getReentrantLock() : toAccount.getReentrantLock();
        ReentrantLock secondLock = fromAccount.getId() > toAccount.getId() ?
                fromAccount.getReentrantLock() : toAccount.getReentrantLock();

        try {
            firstLock.lock();
            secondLock.lock();
            transferMoney(fromAccount, toAccount, amount);
        } finally {
            firstLock.unlock();
            secondLock.unlock();
        }
    }

    private void transferMoney(Account fromAccount, Account toAccount, long amount) throws NotEnoughMoneyException {
        if (fromAccount.getBalance() < amount) {
            throw new NotEnoughMoneyException(
                    String.format("Not enough money. Account id: %-2d balance: %-5d attempt to transfer: %-5d",
                            fromAccount.getId(),
                            fromAccount.getBalance(),
                            amount));
        }
        fromAccount.getMoney(amount);
        toAccount.putMoney(amount);
        logTransfer(fromAccount, toAccount, amount);
    }

    private void logTransfer(Account fromAccount, Account toAccount, long amount) {
        Counter.transferCounter.getAndIncrement();
        logger.info(String.format("Thread: %-17s transferred from id: %-2d balance: %-5d " +
                        "to id: %-2d  balance: %-5d  transferSum: %d",
                Thread.currentThread().getName(),
                fromAccount.getId(),
                fromAccount.getBalance(),
                toAccount.getId(),
                toAccount.getBalance(),
                amount));
    }

}
