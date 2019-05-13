package dto;

public class Transfer {

    private final Account from;
    private final Account to;
    private final long amount;

    public Transfer(Account from, Account to, long amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public long getAmount() {
        return amount;
    }
}
