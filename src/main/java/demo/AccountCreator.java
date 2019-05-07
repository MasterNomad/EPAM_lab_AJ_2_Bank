package demo;

import dto.Account;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

public class AccountCreator {

    public List<Account> createAccounts(int quantity) {
        List<Account> accountList = new LinkedList<>();
        Queue<Long> ids = new LinkedList<>();

        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            long id = random.nextInt(900) + 100;
            while (ids.contains(id)) {
                id = random.nextInt(900) + 100;
            }
            ids.add(id);
        }

        IntStream.range(0, quantity)
                .forEach(i -> accountList.add(new Account(ids.remove(), random.nextInt(10000))));
        return accountList;
    }
}
