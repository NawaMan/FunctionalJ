package functionalj.stream;

import java.util.function.Function;

import functionalj.pipeable.Pipeable;

public interface StreamableWithPipe<DATA> extends AsStreamable<DATA> {
    
    public default <T> Pipeable<Streamable<DATA>> pipable() {
        return Pipeable.of(this.streamable());
    }
    
    public default <T> T pipe(Function<? super Streamable<DATA>, T> piper) {
        return piper.apply(this.streamable());
    }
    
}
