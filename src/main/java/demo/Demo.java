package demo;

import exception.BankException;
import exception.NotEnoughMoneyException;
import io.FilesIO;
import repository.AccountRepository;
import service.AccountService;
import service.MoneyTransferService;
import util.Beans;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo {

    private AccountRepository accountRepository = Beans.getAccountRepository();
    private AccountService accountService = Beans.getAccountService();
    private MoneyTransferService moneyTransferService = Beans.getMoneyTransferService();

    public void execute() {

        FillData("data", 10);
        long initialSum = accountService.getAccountMoneySum();

        accountService.printAccountsBalance();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        accountService.printAccountsBalance();

        System.out.println("Initial: " + initialSum + "\nFinal:   " + accountService.getAccountMoneySum());

    }

    public void FillData(String path, int quantity) {
        FilesIO filesIO = new FilesIO();
        filesIO.createAccountsFiles(path, new AccountCreator().createAccounts(quantity));
        accountRepository.addAll(filesIO.readAccountsFiles(path));
    }
}
