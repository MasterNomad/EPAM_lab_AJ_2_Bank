package dto;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Serializable {

    private final long initialBalance;
    private long id;
    private long balance;
    private ReentrantLock reentrantLock;

    public Account(long id, long balance) {
        this.id = id;
        this.balance = balance;
        this.initialBalance = balance;
        this.reentrantLock = new ReentrantLock();
    }

    public void putMoney(long amount) {
        balance += amount;
    }

    public void getMoney(long amount) {
        balance -= amount;
    }

    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    public long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("id: %-2d initBalance: %-5d balance: %-5d",
                id,
                initialBalance,
                balance);
    }

}
