package demo;

import repository.AccountRepository;
import service.AccountService;
import service.MoneyTransferService;
import thread.TransferTask;
import util.Beans;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Demo {

    private AccountRepository accountRepository = Beans.getAccountRepository();
    private AccountService accountService = Beans.getAccountService();
    private MoneyTransferService moneyTransferService = Beans.getMoneyTransferService();

    public void execute() {
        new Filler().fillAccounts("data", 10);

        long initialSum = accountService.getAccountMoneySum();

        ExecutorService executor = Executors.newFixedThreadPool(20);

        IntStream.range(0, 1000).forEach(i -> executor.submit(new TransferTask()));

        executor.shutdown();

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        accountService.printAccountsBalance();
        System.out.println("Initial sum: " + initialSum + "\nFinal sum:   " + accountService.getAccountMoneySum());
        System.out.println();
        System.out.println("Operations: " + Counter.operationCounter.get());
        System.out.println("Bank exceptions: " + Counter.bankExceptionCounter.get());
        System.out.println("Transfers: " + Counter.transferCounter.get());
    }
}
