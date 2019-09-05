package functionalj.stream;

import java.util.function.ToIntFunction;

import lombok.val;

public interface IntStreamProcessor<TARGET> {
    
    public TARGET process(IntStreamPlus stream);
    
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
