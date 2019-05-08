package demo;

import org.apache.log4j.Logger;
import service.AccountService;
import thread.TransferTask;
import util.Beans;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Demo {

    private AccountService accountService = Beans.getAccountService();
    private Logger logger = Logger.getLogger(Demo.class);

    private final int THREADS_AMOUNT = 20;
    protected static final int OPERATIONS_AMOUNT = 1000;

    public void execute() {
        long initialSum = accountService.getAccountMoneySum();

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_AMOUNT);
        IntStream.range(0, OPERATIONS_AMOUNT).forEach(i -> executor.submit(new TransferTask()));
        executor.shutdown();

        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info(accountService.getAccountsInfo());
        logger.info(String.format("Initial sum: %d Final sum: %d %n%n" +
                "Operations: %d %n" +
                "Bank exceptions: %d %n" +
                "Transfers: %d ",
                initialSum,
                accountService.getAccountMoneySum(),
                Counter.operationCounter.get(),
                Counter.bankExceptionCounter.get(),
                Counter.transferCounter.get()));
    }
}
