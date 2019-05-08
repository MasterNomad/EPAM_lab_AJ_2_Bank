package demo;

import dto.Account;
import io.FilesIO;
import repository.AccountRepository;
import util.Beans;

import java.util.*;

public class Filler {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    public void fillAccounts(String path, int quantity) {
        FilesIO filesIO = new FilesIO();
        filesIO.createAccountsFiles(path, new Filler().createAccounts(quantity));
        accountRepository.addAll(filesIO.readAccountsFiles(path));
    }

    private List<Account> createAccounts(int quantity) {
        List<Account> accountList = new LinkedList<>();
        Set<Long> ids = new HashSet<>();
        Random random = new Random();

        while (ids.size() < quantity) {
            ids.add((long) random.nextInt(900) + 100);
        }
        ids.forEach(id -> accountList.add(new Account(id, random.nextInt(10000))));
        return accountList;
    }
}
