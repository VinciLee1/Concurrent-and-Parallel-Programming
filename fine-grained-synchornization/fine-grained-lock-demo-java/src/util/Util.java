package util;
public class Util {
    public static int factor = 3;

    public static int processACost = 10;
    public static int processBCost = 100;
    public static int processCCost = 10;

    public static void processA() throws InterruptedException {
        Thread.sleep(processACost);
    }

    public static boolean judge(int num){
        return num % factor == 0;
    }

    public static void processB() throws InterruptedException {
        Thread.sleep(processBCost);
    }

    public static void processC() throws InterruptedException {
        Thread.sleep(processCCost);
    }

}
