package repository;

import dto.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AccountRepository {

    private List<Account> accounts = new ArrayList<>();

    public Account add(Account account) {
        accounts.add(account);
        return account;
    }

    public Account getByIndex(int index) {
        return accounts.get(index);
    }

    public int getSize() {
        return accounts.size();
    }

    public void addAll(List<Account> accountsList) {
        accounts.addAll(accountsList);
    }

    public List<Account> getAll() {
        return accounts;
    }

    public void clear () {
        accounts.clear();
    }

}
