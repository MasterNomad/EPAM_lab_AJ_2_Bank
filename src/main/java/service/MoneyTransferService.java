package service;


import demo.Counter;
import dto.Account;
import exception.BankException;
import exception.NotEnoughMoneyException;
import exception.SameAccountException;

import java.util.concurrent.locks.ReentrantLock;

public class MoneyTransferService {

    public void transferMoney(Account sender, Account recipient, long amount) throws BankException, InterruptedException {
        Counter.operationCounter.getAndIncrement();
        if (sender.getId() == recipient.getId()) {
            throw new SameAccountException("Account id: " + sender.getId() +
                    " attempt to transfer money himself");
        }

        ReentrantLock firstLock = sender.getId() < recipient.getId() ?
                sender.getReentrantLock() : recipient.getReentrantLock();
        ReentrantLock secondLock = sender.getId() > recipient.getId() ?
                sender.getReentrantLock() : recipient.getReentrantLock();

        firstLock.lock();
        secondLock.lock();

        if (sender.getBalance() < amount) {
            firstLock.unlock();
            secondLock.unlock();
            throw new NotEnoughMoneyException("Account id- " + sender.getId() +
                    " with balance: " + sender.getBalance() +
                    " attempt to transfer: " + amount);
        }

        System.out.println(String.format("Thread: %-17s transferred from id: %d balance: %-5d " +
                        "to id: %d  balance: %-5d  transferSum: %d",
                Thread.currentThread().getName(),
                sender.getId(),
                sender.getBalance(),
                recipient.getId(),
                recipient.getBalance(),
                amount));

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        Counter.transferCounter.getAndIncrement();

        firstLock.unlock();
        secondLock.unlock();
    }

}
