package functionalj.compose;

import java.util.function.Function;

@SuppressWarnings("javadoc")
public class Either<L, R> {
    
    private final L left;
    private final R right;
    
    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    public static <L, R> Either<L, R> of(L left, R right) {
        return new Either<L, R>(left, right);
    }
    public static <L, R> Either<L, R> left(L left) {
        return new Either<L, R>(left, null);
    }
    public static <L, R> Either<L, R> right(R right) {
        return new Either<L, R>(null, right);
    }
    
    public L getLeft() {
        return left;
    }
    
    public R getRight() {
        return right;
    }
    
    public <RESULT> Either<L, RESULT> map(Function<R, RESULT> f) {
        if (this.right == null)
            return Either.of(left, null);
        
        RESULT result = f.apply(right);
        return Either.<L, RESULT>of(left, result);
    }
    
    public <RESULT> Either<L, RESULT> fmap(Function<R, Either<L, RESULT>> f) {
        if (this.right == null)
            return Either.of(left, null);
        
        return f.apply(right);
    }
    
    public static <L, R, T> Either<L, T> emap(Either<L, R> e, Function<R, T> mapper) {
        return e.map(mapper);
    }
    public static <L, R, T> Either<L, T> efmap(Either<L, R> e, Function<R, Either<L, T>> mapper) {
        return e.fmap(mapper);
    }
    
    public String toString() {
        if (right == null)
            return "Left(" + this.left + ")";
        
        return "Right(" + this.right + ")";
    }
    
}
