import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorPerformanceTest {
    
    public static void main(String[] args) throws Exception {
        int parallelismMax = parseInt(args[0]);
        int taskSize = parseInt(args[1]);
        int latency = parseInt(args[2]);
        int executorType = parseInt(args[3]);
        ExecutorService executorService;
        Tasks tasks = new Tasks(taskSize, (long)latency);

        for (int i = 1; i <= parallelismMax; i++) {
            executorService = get(executorType, i);
            tasks.execute(executorService);
            executorService.shutdown();
        }
        
        System.exit(0);
    }

    public static int parseInt(String num) {
        return Integer.parseInt(num);
    }





    public static ExecutorService get(int type, int parallelism) {
        switch (type) {
            case 0:
                return Executors.newFixedThreadPool(parallelism);
            case 1:
                return Executors.newWorkStealingPool(parallelism);
            case 2:
                return Executors.newCachedThreadPool();
        }
        return Executors.newFixedThreadPool(parallelism);
    }

    public static class Tasks {
        
        int subTaskSize;
        long latency;

        List<Task> subTasks;
        CountDownLatch countDownLatch;

        public Tasks(int subTaskSize, long latency) {
            countDownLatch = new CountDownLatch(subTaskSize);
            subTasks = new ArrayList<Task>();
            for (int i = 0; i < subTaskSize; i++) {
                subTasks.add(new Task(latency, countDownLatch));
            }
        }

        public void execute(ExecutorService executorService) throws Exception {
            long start = System.nanoTime();
            executorService.invokeAll(subTasks);
            long end = System.nanoTime();
            System.out.println((end - start) / 1000000);
        }
    }

    public static class Task implements Callable<String> {
        long latency;
        CountDownLatch countDownLatch;
        public Task(long latency, CountDownLatch countDownLatch) {
            this.latency = latency;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(latency);
            countDownLatch.countDown();
            return null;
        }
        
    }


}