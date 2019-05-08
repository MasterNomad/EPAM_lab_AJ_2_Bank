package demo;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    public static AtomicInteger operationCounter = new AtomicInteger(0);
    public static AtomicInteger transferCounter = new AtomicInteger(0);
    public static AtomicInteger bankExceptionCounter = new AtomicInteger(0);


    public static void clear() {
        operationCounter.set(0);
        transferCounter.set(0);
        bankExceptionCounter.set(0);
    }
}
