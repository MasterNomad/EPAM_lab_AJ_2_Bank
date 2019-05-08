package service;

import repository.AccountRepository;
import util.Beans;

import java.util.concurrent.atomic.AtomicLong;

public class AccountService {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    public void printAccountsBalance() {
        AtomicLong sum = new AtomicLong();
        accountRepository.getAll().stream()
                .peek(e -> sum.addAndGet(e.getBalance()))
                .forEach(System.out::println);
    }

    public long getAccountMoneySum() {
        AtomicLong sum = new AtomicLong();
        accountRepository.getAll().forEach(e -> sum.addAndGet(e.getBalance()));
        return sum.get();
    }
}
