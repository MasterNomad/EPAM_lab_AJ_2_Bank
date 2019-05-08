package dto;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Serializable {

    private long id;
    private long initialBalance;
    private long balance;
    private ReentrantLock reentrantLock = new ReentrantLock();

    public Account(long id, long balance) {
        this.id = id;
        this.balance = balance;
        this.initialBalance = balance;
    }

    public ReentrantLock getReentrantLock() {
        return reentrantLock;
    }

    public void setReentrantLock(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "id=" + id +
                " initBalance=" + initialBalance +
                ", balance=" + balance;
    }
}
