package thread;

import beans.Beans;
import demo.Counter;
import dto.Transfer;
import exception.BankException;
import exception.NotEnoughMoneyException;
import org.apache.log4j.Logger;
import service.MoneyTransferService;

public class TransferTask implements Runnable {

    private static final Logger logger = Logger.getLogger(TransferTask.class);
    private static MoneyTransferService moneyTransferService = Beans.getMoneyTransferService();
    private final Transfer transfer;

    public TransferTask(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public void run() {
        try {
            moneyTransferService.tryTransferMoney(transfer);
        } catch (NotEnoughMoneyException e) {
            Counter.bankExceptionCounter.getAndIncrement();
            logger.info(String.format("Not enough money. Account id: %-2d balance: %-5d attempt to transfer: %-5d",
                    transfer.getFrom().getId(),
                    transfer.getFrom().getBalance(),
                    transfer.getAmount()));
        } catch (BankException e) {
            Counter.bankExceptionCounter.getAndIncrement();
            logger.info(String.format("Attempt to transfer money himself. Account id: %-2d attempt to transfer: %-5d",
                    transfer.getFrom().getId(),
                    transfer.getAmount()));
        }
    }

}
