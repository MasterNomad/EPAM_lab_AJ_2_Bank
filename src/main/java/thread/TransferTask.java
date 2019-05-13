package thread;

import beans.Beans;
import demo.Counter;
import dto.Transfer;
import exception.BankException;
import org.apache.log4j.Logger;
import service.MoneyTransferService;

public class TransferTask implements Runnable {

    private final Transfer transfer;
    private Logger logger = Logger.getLogger(TransferTask.class);
    private MoneyTransferService moneyTransferService = Beans.getMoneyTransferService();

    public TransferTask(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public void run() {
        try {
            moneyTransferService.tryTransferMoney(transfer);
        } catch (BankException e) {
            Counter.bankExceptionCounter.getAndIncrement();
            logger.info(e.getMessage());
        }
    }
}
