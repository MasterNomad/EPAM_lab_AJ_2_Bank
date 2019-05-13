package demo;

import beans.Beans;
import dto.Transfer;
import repository.AccountRepository;

import java.util.Random;

public class TransferGenerator {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    public Transfer generateTransfer() {
        Random random = new Random();

        int firstId = random.nextInt(accountRepository.getSize());
        int secondId = random.nextInt(accountRepository.getSize());
        while (secondId == firstId) {
            secondId = random.nextInt(accountRepository.getSize());
        }
        return new Transfer(
                accountRepository.getByIndex(firstId),
                accountRepository.getByIndex(secondId),
                random.nextInt(2000));

    }
}
