import demo.Demo;
import repository.AccountRepository;
import util.Beans;


public class Main {

    private static AccountRepository accountRepository = Beans.getAccountRepository();

    public static void main(String[] args) {

        new Demo().FillData("data", 10);
        System.out.println(accountRepository.getAll());
    }

}
