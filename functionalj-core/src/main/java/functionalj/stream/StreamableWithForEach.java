package functionalj.stream;

import functionalj.function.IntObjBiConsumer;

public interface StreamableWithForEach<DATA> {
    
    public StreamPlus<DATA> stream();
    
    
    //-- Functionalities --
    
    public default void forEachWithIndex(IntObjBiConsumer<? super DATA> action) {
        stream()
        .forEachWithIndex(action);
    }
}
