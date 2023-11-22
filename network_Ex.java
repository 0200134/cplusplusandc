import java.net.http.*;
import java.applet.*;
import java.awt.*;
import java.nio.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.Random;
import java.net.spi.*;
import java.security.*;
import java.rmi.server.*;
import java.lang.Runtime;
import java.time.*;
import java.lang.management.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.math.*;

@SuppressWarnings("unused")
class network_Ex{
    public static void main(String []args){
        try {
            // Use java.time module
            LocalDate date = LocalDate.now();
            System.out.println("Today's date: " + date);

            // Use java.util.concurrent module
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Hello " + threadName);
            });
            executor.shutdown();

            // Use java.util.function module
            Function<Integer, String> intToString = Object::toString;
            System.out.println(intToString.apply(123));

            // Use java.util.Random module
            Random rand = new Random();
            System.out.println("Random number: " + rand.nextInt(100));

            // Use java.nio.file module
            Path path = Paths.get("test.txt");
            System.out.println("File path: " + path.toAbsolutePath());

            // Use java.math module
            BigInteger bi = new BigInteger("1234567890");
            System.out.println("BigInteger value: " + bi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
