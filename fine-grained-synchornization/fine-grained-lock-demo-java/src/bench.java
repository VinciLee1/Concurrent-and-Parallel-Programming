import util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class bench {
    public static void main(String[] arg){
        FineGrainedLock fineGrainedLock = new FineGrainedLock();
        CoarseGrainedLock coarseGrainedLock = new CoarseGrainedLock();
        Util.processACost = 1;
        Util.processBCost = 5;
        Util.processCCost = 1;
        Util.factor = 2;
        run(fineGrainedLock);
        run(coarseGrainedLock);
    }

    private static void run(SyncLock instance){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++){
            executorService.submit(()->{
                try {
                    instance.service();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        try{
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            long cost = System.currentTimeMillis() - start;
            System.out.println(instance.getClass().getName() + " cost:" + cost);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


