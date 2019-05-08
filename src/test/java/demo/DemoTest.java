package demo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import repository.AccountRepository;
import service.AccountService;
import util.Beans;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class DemoTest {

    private AccountService accountService = Beans.getAccountService();
    private AccountRepository accountRepository = Beans.getAccountRepository();

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[100][0];
    }

    @Test
    public void execute() {
        accountRepository.clear();
        Counter.clear();
        new Filler().fillAccounts("data", 10);
        long initSum = accountService.getAccountMoneySum();
        new Demo().execute();
        long resultSum = accountService.getAccountMoneySum();
        assertEquals(initSum, resultSum);
        assertEquals(Demo.OPERATIONS_AMOUNT, Counter.operationCounter.get());
    }
}