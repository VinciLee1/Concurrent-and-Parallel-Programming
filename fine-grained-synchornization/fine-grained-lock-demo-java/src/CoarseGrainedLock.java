import util.Util;

import java.util.concurrent.atomic.AtomicInteger;

public class CoarseGrainedLock implements SyncLock{

    private static AtomicInteger num = new AtomicInteger(0);
    @Override
    public void service() throws InterruptedException {
        int n = num.getAndIncrement();
        synchronized (this) {
            Util.processA();
            if (!Util.judge(n)) {
                Util.processB();
                Util.processC();
            }
        }
    }
}
