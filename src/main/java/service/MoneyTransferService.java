package service;

import demo.Counter;
import dto.Account;
import dto.Transfer;
import exception.BankException;
import exception.NotEnoughMoneyException;
import exception.SameAccountException;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class MoneyTransferService {

    private static final Logger logger = Logger.getLogger(MoneyTransferService.class);

    public void tryTransferMoney(Transfer transfer) throws BankException {
        Counter.operationCounter.getAndIncrement();

        if (transfer.getFrom().getId() == transfer.getTo().getId()) {
            throw new SameAccountException();
        }

        checkBalanceAndTransfer(transfer);
    }

    private void checkBalanceAndTransfer(Transfer transfer) throws NotEnoughMoneyException {
        Account fromAccount = transfer.getFrom();
        Account toAccount = transfer.getTo();

        ReentrantLock firstLock = fromAccount.getId() < toAccount.getId() ?
                fromAccount.getReentrantLock() : toAccount.getReentrantLock();
        ReentrantLock secondLock = fromAccount.getId() > toAccount.getId() ?
                fromAccount.getReentrantLock() : toAccount.getReentrantLock();

        try {
            firstLock.lock();
            secondLock.lock();

            if (fromAccount.getBalance() < transfer.getAmount()) {
                throw new NotEnoughMoneyException();
            }
            transferMoney(transfer);
        } finally {
            firstLock.unlock();
            secondLock.unlock();
        }
    }

    private void transferMoney(Transfer transfer) {
        transfer.getFrom().getMoney(transfer.getAmount());
        transfer.getTo().putMoney(transfer.getAmount());
        logTransfer(transfer);
    }

    private void logTransfer(Transfer transfer) {
        Counter.transferCounter.getAndIncrement();
        logger.info(String.format("Thread: %-17s transferred from id: %-2d balance: %-5d " +
                        "to id: %-2d  balance: %-5d  transferSum: %d",
                Thread.currentThread().getName(),
                transfer.getFrom().getId(),
                transfer.getFrom().getBalance(),
                transfer.getTo().getId(),
                transfer.getTo().getBalance(),
                transfer.getAmount()));
    }

}
