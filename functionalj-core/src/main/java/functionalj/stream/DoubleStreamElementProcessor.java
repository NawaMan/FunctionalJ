package functionalj.stream;

import static functionalj.lens.Access.theDouble;

import java.util.function.ToDoubleFunction;

import lombok.val;

public interface DoubleStreamElementProcessor<TARGET> {
    void   processDoubleElement (long index, double element);
    TARGET processDoubleComplete(long count);
    
    
    default StreamElementProcessor<Double, TARGET> ofDouble() {
        return of(theDouble);
    }
    default <S> StreamElementProcessor<S, TARGET> of(ToDoubleFunction<S> mapper) {
        return new StreamElementProcessor<S, TARGET>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsDouble(source);
                DoubleStreamElementProcessor.this.processDoubleElement(index, element);
            }
            @Override
            public TARGET processComplete(long count) {
                return DoubleStreamElementProcessor.this.processDoubleComplete(count);
            }
        };
    }
}
