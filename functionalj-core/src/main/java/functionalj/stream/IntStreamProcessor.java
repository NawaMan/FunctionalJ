package functionalj.stream;

import java.util.function.ToIntFunction;

import lombok.val;

public interface IntStreamProcessor<T> {
    void processElement (long index, int element);
    T    processComplete(long count);
    
    default <S> StreamProcessor<S, T> of(ToIntFunction<S> mapper) {
        return new StreamProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsInt(source);
                IntStreamProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return IntStreamProcessor.this.processComplete(count);
            }
        };
    }
}
