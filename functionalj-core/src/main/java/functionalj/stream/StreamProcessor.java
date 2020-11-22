package functionalj.stream;

import functionalj.function.Func1;
import lombok.val;

public interface StreamProcessor<DATA, TARGET> {
    
    public static <D, T> StreamProcessor<D, T> from(Func1<? super StreamPlus<? extends D>, T> mapper) {
        return s -> mapper.apply(s);
    }
    
    public TARGET process(StreamPlus<? extends DATA> stream);
    
    
    public default <SOURCE> StreamProcessor<SOURCE, TARGET> of(Func1<SOURCE, DATA> mapper) {
        return stream -> {
            var dataStream = stream.map(mapper);
            var target     = StreamProcessor.this.process(dataStream);
            return target;
        };
    }
}
