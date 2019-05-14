package thread;

import beans.Beans;
import demo.Counter;
import dto.Transfer;
import exception.BankException;
import exception.NotEnoughMoneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MoneyTransferService;

public class TransferTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TransferTask.class);
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
            logger.info("Not enough money. Account id:{} balance:{} attempt to transfer:{}",
                    transfer.getFrom().getId(),
                    transfer.getFrom().getBalance(),
                    transfer.getAmount());
        } catch (BankException e) {
            Counter.bankExceptionCounter.getAndIncrement();
            logger.info("Attempt to transfer money himself. Account id:{} attempt to transfer:{}",
                    transfer.getFrom().getId(),
                    transfer.getAmount());
        }
    }

}
