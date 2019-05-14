package demo;

import beans.Beans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AccountService;
import thread.TransferTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Demo {

    protected static final int OPERATIONS_AMOUNT = 1000;
    private final int THREADS_AMOUNT = 20;

    private AccountService accountService = Beans.getAccountService();

    private Logger logger = LoggerFactory.getLogger(Demo.class);

    public void execute() {
        long initialSum = accountService.getAccountMoneySum();
        TransferGenerator transferGenerator = new TransferGenerator();

        ExecutorService executor = Executors.newFixedThreadPool(THREADS_AMOUNT);
        IntStream.range(0, OPERATIONS_AMOUNT)
                .forEach(i -> executor.submit(new TransferTask(transferGenerator.generateTransfer())));

        executor.shutdown();

        while (!executor.isTerminated()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.debug(accountService.getAccountsInfo());
        logger.debug("Initial sum: {} Final sum: {} \n\n" +
                        "Operations: {}\n" +
                        "Bank exceptions: {}\n" +
                        "Transfers: {}",
                initialSum,
                accountService.getAccountMoneySum(),
                Counter.operationCounter.get(),
                Counter.bankExceptionCounter.get(),
                Counter.transferCounter.get());
    }
}
