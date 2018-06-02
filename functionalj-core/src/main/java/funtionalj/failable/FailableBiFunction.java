package funtionalj.failable;

import functionalj.types.ITuple2;

@FunctionalInterface
public interface FailableBiFunction<INPUT1, INPUT2, OUTPUT> {
    
    public OUTPUT apply(INPUT1 input1, INPUT2 input2) throws Throwable;
    
    public default OUTPUT apply(ITuple2<INPUT1, INPUT2> input) throws Throwable {
        return apply(input._1(), input._2());
    }
    
}
