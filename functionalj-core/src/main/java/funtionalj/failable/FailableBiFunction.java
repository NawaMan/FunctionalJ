package funtionalj.failable;

import functionalj.types.Tuple2;

@FunctionalInterface
public interface FailableBiFunction<INPUT1, INPUT2, OUTPUT> {
    
    public OUTPUT apply(INPUT1 input1, INPUT2 input2) throws Exception;
    
    public default OUTPUT apply(Tuple2<INPUT1, INPUT2> input) throws Exception {
        return apply(input._1(), input._2());
    }
    
}
