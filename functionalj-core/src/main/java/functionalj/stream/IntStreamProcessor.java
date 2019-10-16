package functionalj.stream;

import static functionalj.lens.Access.theInteger;

import java.util.function.ToIntFunction;

import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public interface IntStreamProcessor<TARGET> {
    
    public TARGET process(IntStreamPlus stream);
    
    
    default StreamProcessor<Integer, TARGET> ofInteger() {
        return of(theInteger);
    }
    default <SOURCE> StreamProcessor<SOURCE, TARGET> of(ToIntFunction<SOURCE> mapper) {
        return new StreamProcessor<SOURCE, TARGET>() {
            @Override
            public TARGET process(StreamPlus<SOURCE> stream) {
                val dataStream = stream.mapToInt(mapper);
                val target     = IntStreamProcessor.this.process(dataStream);
                return target;
            }
        };
    }
}
