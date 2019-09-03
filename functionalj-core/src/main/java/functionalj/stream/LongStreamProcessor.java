package functionalj.stream;

import java.util.function.ToLongFunction;

import lombok.val;

public interface LongStreamProcessor<T> {
    void processElement (long index, long element);
    T    processComplete(long count);
    
    default <S> StreamProcessor<S, T> of(ToLongFunction<S> mapper) {
        return new StreamProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsLong(source);
                LongStreamProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return LongStreamProcessor.this.processComplete(count);
            }
        };
    }
}
