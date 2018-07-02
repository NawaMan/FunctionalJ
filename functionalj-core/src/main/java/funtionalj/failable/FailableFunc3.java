package funtionalj.failable;

import functionalj.types.Tuple3;

@FunctionalInterface
public interface FailableFunc3<INPUT1, INPUT2, INPUT3, OUTPUT> {
    
    public OUTPUT apply(INPUT1 input1, INPUT2 input2, INPUT3 input3) throws Exception;
    
    public default OUTPUT apply(Tuple3<INPUT1, INPUT2, INPUT3> input) throws Exception {
        return apply(input._1(), input._2(), input._3());
    }
    
}
