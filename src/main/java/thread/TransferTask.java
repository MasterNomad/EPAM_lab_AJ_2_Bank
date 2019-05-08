package thread;

import demo.Counter;
import exception.BankException;
import repository.AccountRepository;
import service.MoneyTransferService;
import util.Beans;

import java.util.Random;

public class TransferTask implements Runnable {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    @Override
    public void run() {
        Random random = new Random();

        try {


            new MoneyTransferService()
                    .transferMoney(accountRepository.getByIndex(random.nextInt(accountRepository.getSize())),
                            accountRepository.getByIndex(random.nextInt(accountRepository.getSize())),
                            random.nextInt(2000));
        } catch (BankException e) {
            Counter.bankExceptionCounter.getAndIncrement();
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
