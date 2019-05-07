package thread;

import exception.BankException;
import exception.NotEnoughMoneyException;
import repository.AccountRepository;
import service.MoneyTransferService;
import util.Beans;

import java.util.Random;

public class TransferTask extends Thread {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    public void run() {
        Random random = new Random();

        try {
            new MoneyTransferService()
                    .transferMoney(accountRepository.getByIndex(random.nextInt(accountRepository.getSize())),
                            accountRepository.getByIndex(random.nextInt(accountRepository.getSize())),
                            random.nextInt(2000));
        } catch (BankException e) {
            System.out.println(e.getMessage());
        }
    }

}
