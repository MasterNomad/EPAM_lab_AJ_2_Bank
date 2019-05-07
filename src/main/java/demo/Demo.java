package demo;

import io.FilesIO;
import repository.AccountRepository;
import util.Beans;

public class Demo {

    private AccountRepository accountRepository = Beans.getAccountRepository();

    public void FillData(String path, int quantity) {
        FilesIO filesIO = new FilesIO();
        filesIO.createAccountsFiles(path, new AccountCreator().createAccounts(quantity));
        accountRepository.addAll(filesIO.readAccountsFiles(path));
    }
}
