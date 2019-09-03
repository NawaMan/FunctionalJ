package functionalj.stream;

import functionalj.function.Func1;
import lombok.val;

public interface StreamProcessor<F, T> {
    void processElement (long index, F element);
    T    processComplete(long count);
    
    default <S> StreamProcessor<S, T> of(Func1<S, F> mapper) {
        return new StreamProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.apply(source);
                StreamProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return StreamProcessor.this.processComplete(count);
            }
        };
    }
}
