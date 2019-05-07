package service;


import dto.Account;
import exception.BankException;
import exception.NotEnoughMoneyException;
import exception.SameAccountException;

public class MoneyTransferService {

    public void transferMoney(Account sender, Account recipient, long amount) throws BankException {
        if (sender.equals(recipient)) {
            throw new SameAccountException("Account id: " + sender.getId() +
                    "attempt to transfer money himself");
        }
        if (sender.getBalance() < amount) {
            throw new NotEnoughMoneyException("Account id: " + sender.getId() +
                    "; balance: " + sender.getBalance() +
                    "; attempt to transfer: " + amount);
        }
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);
    }

}
