package functionalj.stream;

import static functionalj.lens.Access.theLong;

import java.util.function.ToLongFunction;

import lombok.val;

public interface LongStreamProcessor<TARGET> {
    
    public TARGET process(LongStreamPlus stream);
    
    
    default StreamProcessor<Long, TARGET> ofLong() {
        return of(theLong);
    }
    default <SOURCE> StreamProcessor<SOURCE, TARGET> of(ToLongFunction<SOURCE> mapper) {
        return new StreamProcessor<SOURCE, TARGET>() {
            @Override
            public TARGET process(StreamPlus<SOURCE> stream) {
                val dataStream = stream.mapToLong(mapper);
                val target     = LongStreamProcessor.this.process(dataStream);
                return target;
            }
        };
    }
}
