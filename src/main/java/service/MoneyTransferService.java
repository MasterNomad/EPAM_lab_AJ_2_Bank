package service;

import demo.Counter;
import dto.Account;
import exception.BankException;
import exception.NotEnoughMoneyException;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class MoneyTransferService {

    private Logger logger = Logger.getLogger(MoneyTransferService.class);

    public void transferMoney(Account sender, Account recipient, long amount) throws BankException {
        Counter.operationCounter.getAndIncrement();

        ReentrantLock firstLock = sender.getId() < recipient.getId() ?
                sender.getReentrantLock() : recipient.getReentrantLock();
        ReentrantLock secondLock = sender.getId() > recipient.getId() ?
                sender.getReentrantLock() : recipient.getReentrantLock();

        firstLock.lock();
        secondLock.lock();
        if (sender.getBalance() < amount) {
            firstLock.unlock();
            secondLock.unlock();
            throw new NotEnoughMoneyException(
                    String.format("Not enough money. Account id: %-2d balance: %-5d attempt to transfer: %-5d",
                            sender.getId(),
                            sender.getBalance(),
                            amount));
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
        logTransfer(sender, recipient, amount);

        firstLock.unlock();
        secondLock.unlock();
    }

    private void logTransfer(Account sender, Account recipient, long amount) {
        Counter.transferCounter.getAndIncrement();
        logger.info(String.format("Thread: %-17s transferred from id: %-2d balance: %-5d " +
                        "to id: %-2d  balance: %-5d  transferSum: %d",
                Thread.currentThread().getName(),
                sender.getId(),
                sender.getBalance(),
                recipient.getId(),
                recipient.getBalance(),
                amount));
    }

}
