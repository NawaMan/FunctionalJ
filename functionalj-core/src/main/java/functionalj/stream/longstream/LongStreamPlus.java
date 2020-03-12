package functionalj.stream.longstream;

import java.util.function.LongSupplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import functionalj.stream.StreamPlus;
import lombok.val;


public interface LongStreamPlus
        extends 
            LongStream {
    
    public static LongStreamPlus from(LongStream longStream) {
//        if (longStream instanceof LongStreamPlus)
//            return (LongStreamPlus)longStream;
//            
//        return ()->longStream;
        return null;
    }
    
    public static LongStreamPlus generate(LongSupplier supplier) {
        // TODO Auto-generated method stub
        return null;
    }
    
    //== Core ==
    
    public LongStream longStream();
    
    public default LongStreamPlus longStreamPlus() {
        return this;
    }
    
    @Override
    public default StreamPlus<Long> boxed() {
        return StreamPlus.from(longStream().boxed());
    }
    
    public default String toListString() {
        // TODO - There must be a faster way
        val strValue 
            = mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
}
