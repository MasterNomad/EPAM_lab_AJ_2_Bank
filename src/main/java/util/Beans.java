package util;

import repository.AccountRepository;
import service.AccountService;
import service.MoneyTransferService;

import java.util.Objects;

public class Beans {

    private static AccountRepository accountRepository;
    private static AccountService accountService;
    private static MoneyTransferService moneyTransferService;

    public static AccountRepository getAccountRepository() {
        if (Objects.isNull(accountRepository)) {
            accountRepository = new AccountRepository();
        }
        return accountRepository;
    }

    public static AccountService getAccountService() {
        if (Objects.isNull(accountService)) {
            accountService = new AccountService();
        }
        return accountService;
    }

    public static MoneyTransferService getMoneyTransferService() {
        if (Objects.isNull(moneyTransferService)){
            moneyTransferService = new MoneyTransferService();
        }

        return moneyTransferService;
    }
}
