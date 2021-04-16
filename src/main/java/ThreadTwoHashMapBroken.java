import java.util.HashMap;
import java.util.Set;

public class ThreadTwoHashMapBroken extends Thread {
    HashMap<String, Thread> threadMap;

    public ThreadTwoHashMapBroken(String name) {
        super(name);
        this.threadMap = new HashMap<>();
    }

    @Override
    public void run() {
        System.out.println("ThreadTwoHashMapB - START "+Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
            //Get database connection, delete unused data from DB
            doDBProcessing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ThreadTwoHashMapB - END "+Thread.currentThread().getName());
    }

    private void doDBProcessing() throws InterruptedException {
        Thread.sleep(5000);
    }


    // Run this
    public static void main(String[] args){
        ThreadTwoHashMapBroken tm = new ThreadTwoHashMapBroken(""+10);

        // What's wrong with this idea??...Because both of these threads using both run method at the same time, and both updating/changing threadMap at the same time
        new Thread("Run of " + 20){
            public void run(){
                tm.runMapOfSize(20);
            }
        }.start();
        new Thread("Run of " + 28){
            public void run(){
                tm.runMapOfSize(22);
            }
        }.start();

    }

    private void runMapOfSize(int size) {
        System.out.println("Constructing HashMap of Size "+size);
        Integer threadCount = size;

        for (int i = 0; i < threadCount; i++) {
            this.threadMap.put("T"+ i, new ThreadTwoHashMapBroken("T"+ i));
        }
        System.out.println("Starting Threads in HashMap");
        Set<String> names = this.threadMap.keySet();
        for (String name : names) {
            this.threadMap.get(name).start();
        }
        System.out.println("Thread HashMap, all have been started");
    }
}
