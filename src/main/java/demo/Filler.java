package demo;

import beans.Beans;
import dto.Account;
import io.FilesIO;
import repository.AccountRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Filler {

    private final static int MAX_MONEY_AMOUNT = 10000;
    private AccountRepository accountRepository = Beans.getAccountRepository();

    public void fillAccounts(String path, int quantity) {
        FilesIO filesIO = new FilesIO();
        filesIO.createAccountsFiles(path, createAccounts(quantity));
        accountRepository.addAll(filesIO.readAccountsFiles(path));
    }

    private List<Account> createAccounts(int quantity) {
        List<Account> accountList = new LinkedList<>();
        Random random = new Random();

        IntStream.range(0, quantity).forEach(id -> accountList.add(new Account(id, random.nextInt(MAX_MONEY_AMOUNT))));
        return accountList;
    }
}
