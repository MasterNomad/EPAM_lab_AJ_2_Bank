package exception;

public class NotEnoughMoneyException extends BankException {

    public NotEnoughMoneyException() {
    }

    public NotEnoughMoneyException(String msg) {
        super(msg);
    }
}
