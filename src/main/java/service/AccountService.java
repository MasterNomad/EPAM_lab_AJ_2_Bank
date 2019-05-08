package service;

import repository.AccountRepository;
import util.Beans;

import java.util.concurrent.atomic.AtomicLong;

public class AccountService {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    public String getAccountsInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        accountRepository.getAll().stream()
                .peek(stringBuilder::append)
                .forEach(e -> stringBuilder.append("\n"));
        return stringBuilder.toString();
    }

    public long getAccountMoneySum() {
        AtomicLong sum = new AtomicLong();
        accountRepository.getAll().forEach(e -> sum.addAndGet(e.getBalance()));
        return sum.get();
    }
}
