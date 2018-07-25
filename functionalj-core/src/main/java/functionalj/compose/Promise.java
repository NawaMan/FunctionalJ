package functionalj.compose;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("javadoc")
public class Promise<TYPE> {
    
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
    
    public static Function<Promise<String>, CompletableFuture<Void>> promiseThenDo(Consumer<? super String> legAndDone) {
        return p->p.future.thenAccept(legAndDone);
    }
}
