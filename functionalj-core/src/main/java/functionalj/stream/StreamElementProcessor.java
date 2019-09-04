package functionalj.stream;

import functionalj.function.Func1;
import lombok.val;

public interface StreamElementProcessor<F, T> {
    void processElement (long index, F element);
    T    processComplete(long count);
    
    default <S> StreamElementProcessor<S, T> of(Func1<S, F> mapper) {
        return new StreamElementProcessor<S, T>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.apply(source);
                StreamElementProcessor.this.processElement(index, element);
            }
            @Override
            public T processComplete(long count) {
                return StreamElementProcessor.this.processComplete(count);
            }
        };
    }
}
