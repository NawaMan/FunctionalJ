package functionalj.stream;

public interface StreamableWithForEach<DATA> extends AsStreamable<DATA>, StreamPlusWithForEach<DATA> {
    
    @Override
    default StreamPlus<DATA> streamPlus() {
        return AsStreamable.super.streamPlus();
    }
    
}
