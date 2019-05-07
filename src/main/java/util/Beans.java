package util;

import repository.AccountRepository;

import java.util.Objects;

public class Beans {

    private static AccountRepository accountRepository;

    public static AccountRepository getAccountRepository() {
        if (Objects.isNull(accountRepository)) {
            accountRepository = new AccountRepository();
        }
        return accountRepository;
    }

}
