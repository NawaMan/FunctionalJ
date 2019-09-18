package functionalj.stream;

import static functionalj.lens.Access.theInteger;

import java.util.function.ToIntFunction;

import lombok.val;

public interface IntStreamElementProcessor<TARGET> {
    void   processIntElement (long index, int element);
    TARGET processIntComplete(long count);
    
    
    default StreamElementProcessor<Integer, TARGET> ofInteger() {
        return of(theInteger);
    }
    default <S> StreamElementProcessor<S, TARGET> of(ToIntFunction<S> mapper) {
        return new StreamElementProcessor<S, TARGET>() {
            @Override
            public void processElement(long index, S source) {
                val element = mapper.applyAsInt(source);
                IntStreamElementProcessor.this.processIntElement(index, element);
            }
            @Override
            public TARGET processComplete(long count) {
                return IntStreamElementProcessor.this.processIntComplete(count);
            }
        };
    }
}
