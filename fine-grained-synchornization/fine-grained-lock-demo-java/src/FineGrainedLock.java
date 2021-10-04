import util.*;

import java.util.concurrent.atomic.AtomicInteger;

public class FineGrainedLock implements SyncLock{
    private static AtomicInteger num = new AtomicInteger(0);

    public void service() throws InterruptedException {
        int n = num.getAndIncrement();
        synchronized (this){
            Util.processA();
        }
        if (!Util.judge(n)){
            Util.processB();
            synchronized (this){
                Util.processC();
            }
        }
    }
}
