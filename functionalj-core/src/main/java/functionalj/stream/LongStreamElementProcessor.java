package functionalj.stream;

import java.util.function.ToLongFunction;

import lombok.val;

public interface LongStreamElementProcessor<T> {
    void processElement (long index, long element);
    T    processComplete(long count);
    
    default <S> StreamElementProcessor<S, T> of(ToLongFunction<S> mapper) {
        return new StreamElementProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsLong(source);
                LongStreamElementProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return LongStreamElementProcessor.this.processComplete(count);
            }
        };
    }
}
