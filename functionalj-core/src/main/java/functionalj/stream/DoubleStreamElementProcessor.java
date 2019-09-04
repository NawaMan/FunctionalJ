package functionalj.stream;

import java.util.function.ToDoubleFunction;

import lombok.val;

public interface DoubleStreamElementProcessor<T> {
    void processElement (long index, double element);
    T    processComplete(long count);
    
    default <S> StreamElementProcessor<S, T> of(ToDoubleFunction<S> mapper) {
        return new StreamElementProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsDouble(source);
                DoubleStreamElementProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return DoubleStreamElementProcessor.this.processComplete(count);
            }
        };
    }
}
