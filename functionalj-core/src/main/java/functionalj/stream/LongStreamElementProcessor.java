package functionalj.stream;

import static functionalj.lens.Access.theLong;

import java.util.function.ToLongFunction;

import lombok.val;

public interface LongStreamElementProcessor<TARGET> {
    void   processLongElement (long index, long element);
    TARGET processLongComplete(long count);
    
    
    default StreamElementProcessor<Long, TARGET> ofLong() {
        return of(theLong);
    }
    default <S> StreamElementProcessor<S, TARGET> of(ToLongFunction<S> mapper) {
        return new StreamElementProcessor<S, TARGET>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsLong(source);
                LongStreamElementProcessor.this.processLongElement(index, element);
            }
            @Override
            public TARGET processComplete(long count) {
                return LongStreamElementProcessor.this.processLongComplete(count);
            }
        };
    }
}
