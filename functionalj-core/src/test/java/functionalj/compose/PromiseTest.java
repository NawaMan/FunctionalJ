package functionalj.compose;

import static functionalj.compose.Functional.compose;
import static functionalj.compose.Functional.curry1;
import static functionalj.compose.Functional.curry2;
import static functionalj.compose.Promise.promiseThenDo;
import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.junit.Assert.assertEquals;

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
            assertEquals("Hello world!", str);
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
    }
    
    @Test
    public void testCompose() {
        val logPromise = compose(
                promiseFrom(),
                promiseMap(curry2(String::concat, "It say: ")),
                promiseThenDo(s->{
                    assertEquals("S: It say: Hello world!", "S: " + s);
                    done();
                }));
        
        logPromise.apply(()->{
            sleep();
            return "Hello world!";
        });
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
