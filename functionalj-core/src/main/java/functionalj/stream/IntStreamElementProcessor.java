package functionalj.stream;

import java.util.function.ToIntFunction;

import lombok.val;

public interface IntStreamElementProcessor<T> {
    void processElement (long index, int element);
    T    processComplete(long count);
    
    default <S> StreamElementProcessor<S, T> of(ToIntFunction<S> mapper) {
        return new StreamElementProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsInt(source);
                IntStreamElementProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return IntStreamElementProcessor.this.processComplete(count);
            }
        };
    }
}
