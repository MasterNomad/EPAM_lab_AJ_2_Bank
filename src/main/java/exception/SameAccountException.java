package exception;

public class SameAccountException extends BankException {
    public SameAccountException() {
    }

    public SameAccountException(String msg) {
        super(msg);
    }
}
