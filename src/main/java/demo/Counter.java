package demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    public final static AtomicInteger operationCounter = new AtomicInteger(0);
    public final static AtomicInteger transferCounter = new AtomicInteger(0);
    public final static AtomicInteger bankExceptionCounter = new AtomicInteger(0);


    public static void clear() {
        operationCounter.set(0);
        transferCounter.set(0);
        bankExceptionCounter.set(0);
    }
}
