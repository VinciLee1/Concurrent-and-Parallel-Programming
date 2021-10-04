import util.*;

import java.util.concurrent.atomic.AtomicInteger;

public class FineGrainedLock {
    private static AtomicInteger num;

    public void service() throws InterruptedException {
        synchronized (this){
            Util.processA();
        }
        if (judge){
            processB();
            synchronized (this){
                processC();
            }
        }
    }
}
