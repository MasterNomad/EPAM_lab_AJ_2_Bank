package thread;

import demo.Counter;
import exception.BankException;
import org.apache.log4j.Logger;
import repository.AccountRepository;
import service.MoneyTransferService;
import util.Beans;

import java.util.Random;

public class TransferTask implements Runnable {

    private AccountRepository accountRepository = Beans.getAccountRepository();
    private org.apache.log4j.Logger logger = Logger.getLogger(TransferTask.class);

    @Override
    public void run() {
        Random random = new Random();

        int firstId = random.nextInt(accountRepository.getSize());
        int secondId = random.nextInt(accountRepository.getSize());
        while (secondId == firstId) {
            secondId = random.nextInt(accountRepository.getSize());
        }

        try {
            new MoneyTransferService()
                    .transferMoney(accountRepository.getByIndex(firstId),
                            accountRepository.getByIndex(secondId),
                            random.nextInt(2000));
        } catch (BankException e) {
            Counter.bankExceptionCounter.getAndIncrement();
            logger.info(e.getMessage());
        }
    }

}
