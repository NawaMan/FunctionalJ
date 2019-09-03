package functionalj.stream;

import java.util.function.ToDoubleFunction;

import lombok.val;

public interface DoubleStreamProcessor<T> {
    void processElement (long index, double element);
    T    processComplete(long count);
    
    default <S> StreamProcessor<S, T> of(ToDoubleFunction<S> mapper) {
        return new StreamProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsDouble(source);
                DoubleStreamProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return DoubleStreamProcessor.this.processComplete(count);
            }
        };
    }
}
