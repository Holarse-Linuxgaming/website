package web.configuration;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicIdGenerator {

    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    // No Instances
    private AtomicIdGenerator() {
    }

    public static long nextId() {
        return counter.incrementAndGet();
    }

}
