package functionalj.compose;

import static functionalj.compose.Functional.compose;
import static functionalj.compose.Functional.curry;
import static functionalj.compose.Functional.curry1;
import static functionalj.compose.Functional.curry2;
import static functionalj.compose.Promise.promiseThenDo;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Test;

import lombok.val;

public class PromiseTest {
    
    private CountDownLatch latch = new CountDownLatch(1);
    
    @Test
    public void test() {
        supplyAsync(()->{
            sleep();
            return "Hello world!";
        }).handle((str, e)->{
            System.out.println(str);
            done();
            return null;
        });
    }
    
    @Test
    public void testPromise() {
        Promise<String> helloWorld = Promise.from(()->{
            sleep();
            done();
            return "Hello world!";
        });
        Functional.pmap(helloWorld, curry2(String::concat, "It say: "));
        System.out.println("Done?");
    }
    
    @Test
    public void testCompose() {
        val logPromise = compose(
                promiseFrom(),
                promiseMap(curry2(String::concat, "It say: ")),
                promiseThenDo(s->{
                    System.out.println("S: " + s);
                    done();
                }));
        
//        logPromise.apply(askUser("Printing the file passed in:"));
        logPromise.apply(()->{
            sleep();
            return "Hello world!";
        });
        System.out.println("Done?");
    }

    private Function<String, String> askUser() {
        return message -> {
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println(message);
                if (sc.hasNextLine()) {
                    done();
                    return sc.nextLine();
                }
                return null;
            }
        };
    }
    private Supplier<String> askUser(String message) {
        return curry(askUser(), message);
    }

    private <TYPE, RESULT> Function<Promise<TYPE>, Promise<RESULT>> promiseMap(Function<TYPE, RESULT> mapper) {
        return curry1(promiseMap(), mapper);
    }
    
    public static <TYPE> Function<Promise<TYPE>, Either<Throwable, TYPE>> promiseGet() {
        return Promise::get;
    }

    public static <TYPE> Function<Either<Throwable, TYPE>, TYPE> log() {
        return thing->{ 
            System.out.println(thing);
            return thing.getRight();
        };
    }

    public static <TYPE, RESULT> BiFunction<Promise<TYPE>, Function<TYPE, RESULT>, Promise<RESULT>> promiseMap() {
        return Promise::map;
    }

    private static <TYPE> Function<Supplier<TYPE>, Promise<TYPE>> promiseFrom() {
        return Promise::from;
    }
    
    @After
    public void after() {
        await();
    }
    
    private void done() {
        latch.countDown();
        shortSleep();
    }
    
    private void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void shortSleep() {
        sleep(100);
    }
    
    private void sleep() {
        sleep(2000);
    }
    
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
