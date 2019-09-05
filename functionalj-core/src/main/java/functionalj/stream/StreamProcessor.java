package functionalj.stream;

import functionalj.function.Func1;
import lombok.val;

public interface StreamProcessor<DATA, TARGET> {
    
    public TARGET process(StreamPlus<DATA> stream);
    
    default <SOURCE> StreamProcessor<SOURCE, TARGET> of(Func1<SOURCE, DATA> mapper) {
        return new StreamProcessor<SOURCE, TARGET>() {
            @Override
            public TARGET process(StreamPlus<SOURCE> stream) {
                val dataStream = stream.map(mapper);
                val target     = StreamProcessor.this.process(dataStream);
                return target;
            }
        };
    }
}
