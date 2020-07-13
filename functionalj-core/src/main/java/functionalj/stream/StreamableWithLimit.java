package functionalj.stream;

import static functionalj.stream.Streamable.deriveFrom;

import java.util.function.Predicate;

public interface StreamableWithLimit<DATA> extends AsStreamable<DATA> {
    
    public default Streamable<DATA> limit(Long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    public default Streamable<DATA> skip(Long startAt) {
        return deriveFrom(this, stream -> stream.skip(startAt));
    }
    
    public default Streamable<DATA> skipWhile(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.skipWhile(condition));
    }
    
    public default Streamable<DATA> skipUntil(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.skipUntil(condition));
    }
    
    public default Streamable<DATA> takeWhile(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.takeWhile(condition));
    }
    
    public default Streamable<DATA> takeUntil(Predicate<? super DATA> condition) {
        return deriveFrom(this, stream -> stream.takeUntil(condition));
    }
    
}
