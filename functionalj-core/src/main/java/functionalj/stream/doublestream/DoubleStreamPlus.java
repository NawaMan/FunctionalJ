package functionalj.stream.doublestream;

import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import functionalj.stream.StreamPlus;
import lombok.val;


public interface DoubleStreamPlus
        extends 
            DoubleStream {
    
    public static DoubleStreamPlus of(double ... percentiles) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public static DoubleStreamPlus from(DoubleStream longStream) {
//        if (longStream instanceof LongStreamPlus)
//            return (LongStreamPlus)longStream;
//            
//        return ()->longStream;
        return null;
    }
    
    public static DoubleStreamPlus generate(DoubleSupplier supplier) {
        // TODO Auto-generated method stub
        return null;
    }
    
    //== Core ==
    
    public DoubleStream doubleStream();
    
    public default DoubleStreamPlus doubleStreamPlus() {
        return this;
    }
    
    @Override
    public default StreamPlus<Double> boxed() {
        return StreamPlus.from(doubleStream().boxed());
    }
    
    public default String toListString() {
        // TODO - There must be a faster way
        val strValue 
            = mapToObj(String::valueOf)
            .collect(Collectors.joining(", "));
        return "[" + strValue + "]";
    }
    
}
