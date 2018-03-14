package nawaman.functionalj.compose;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static nawaman.functionalj.compose.Functional.compose;
import static nawaman.functionalj.compose.Functional.curry;
import static nawaman.functionalj.compose.Functional.curry1;
import static nawaman.functionalj.compose.Functional.curry2;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.After;
import org.junit.Test;

import lombok.val;

public class PromiseTest {
    
    private CountDownLatch latch = new CountDownLatch(1);
    
    public static class Promise<TYPE> {
        
        private final CompletableFuture<TYPE> future;
        
        private Promise(CompletableFuture<TYPE> future) {
            this.future = future;
        }
        public static <TYPE> Promise<TYPE> from(java.util.function.Supplier<TYPE> supplier) {
            return new Promise<TYPE>(CompletableFuture.supplyAsync(supplier));
        }
        
        public Either<Throwable, TYPE> get() {
            try {
                return Either.<Throwable, TYPE>of(null, this.future.get());
            } catch (Exception e) {
                return Either.<Throwable, TYPE>of(e, null); 
            }
        }
        
        public <RESULT> Promise<RESULT> map(java.util.function.Function<TYPE, RESULT> mapper) {
            return new Promise<RESULT>(future.thenApply(mapper));
        }
        
        public <RESULT> Promise<RESULT> fmap(Function<Either<Throwable, TYPE>, RESULT> handler) {
            return new Promise<RESULT>(future.handle((v,e)->{
                return handler.apply(Either.of(e, v));
            }));
        }
    }
    
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

    private Function<Promise<String>, CompletableFuture<Void>> promiseThenDo(Consumer<? super String> legAndDone) {
        return p->p.future.thenAccept(legAndDone);
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
