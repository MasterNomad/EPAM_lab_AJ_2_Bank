package dto;

import java.io.Serializable;

public class Account implements Serializable {

    private long id;
    private long balance;

    public Account(long id, long balance) {
        this.id = id;
        this.balance = balance;
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
                ", balance=" + balance;
    }
}
