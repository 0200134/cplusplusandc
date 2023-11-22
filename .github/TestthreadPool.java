import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

@SuppressWarnings("unused")

class DeadlockException extends Exception {
    public DeadlockException() {
        super("Deadlock Occured");
    }
}

class LiveLockException extends Exception {
    public LiveLockException() {
        super("LiveLock Occured");
    }
}

class ResourceHog extends Exception {
    public ResourceHog() {
        super("Resource Hogged!!!");
    }
}

class ExceptionNotFound extends Exception {
    public ExceptionNotFound() {
        super("Exception not found");
    }
}

class WorkerThread implements Runnable {
    private String message;

    public WorkerThread(String message) {
        this.message = message;
    }

    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " (Start) message = " + message);
            processMessage();  // This method may throw DeadlockException or LiveLockException
            System.out.println(Thread.currentThread().getName() + " (End)");
        } catch (DeadlockException e) {
            System.out.println("Caught DeadlockException: " + e.getMessage());
            // Add recovery actions for Deadlock here
        } catch (LiveLockException e) {
            System.out.println("Caught LiveLockException: " + e.getMessage());
            // Add recovery actions for LiveLock here
        } catch (ResourceHog e) {
            System.out.println("Caught Resource Hog: " + e.getMessage());
            // Add recovery actions for Resource Hog here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processMessage() throws DeadlockException, LiveLockException, InterruptedException, ResourceHog {
        // Detect Deadlock and LiveLock conditions
        if (detectDeadlock()) {
            throw new DeadlockException();
        }
        if (detectLiveLock()) {
            throw new LiveLockException();
        }
        if (detectResourceHog()) {
            throw new ResourceHog();
        }
        Thread.sleep(2000);  // Simulate some work
    }

    private boolean detectDeadlock() {
        // Implement your Deadlock detection logic here
        return false;  // For now, we just return false
    }

    private boolean detectLiveLock() {
        // Implement your LiveLock detection logic here
        return false;  // For now, we just return false
    }

    private boolean detectResourceHog() {
        // Implement your Resource Hog detection logic here
        return false;  // For now, we just return false
    }
}

public class TestthreadPool {
    public static void main(String[] args) {
        // First thread pool
        ExecutorService executor1 = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1500; i++) {
            Runnable worker = new WorkerThread("Pool1: " + i);
            executor1.execute(worker);
        }
        executor1.shutdown();

        // Second thread pool
        ExecutorService executor2 = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1500; i++) {
            Runnable worker = new WorkerThread("Pool2: " + i);
            executor2.execute(worker);
        }
        executor2.shutdown();

        while (!executor1.isTerminated() || !executor2.isTerminated()) {
            // Wait until other executors are terminated
        }

        System.out.println("Finished all threads");
    }
}
