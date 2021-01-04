package functionalj.function;

import java.util.function.BiPredicate;


@FunctionalInterface
public interface IntDoublePredicate extends Func2<Integer, Double, Boolean>, BiPredicate<Integer, Double> {
    
    
    public boolean testIntegerDoubleUnsafe(int input1, double input2) throws Exception;
    
    
    @Override
    public default boolean test(Integer input1, Double input2) {
        return apply(input1, input2);
    }
    
    public default boolean testIntegerDouble(int input1, double input2) {
        return apply(input1, input2);
    }
    
    public default Boolean applyUnsafe(Integer input1, Double input2) throws Exception {
        return testIntegerDoubleUnsafe(input1, input2);
    }
    
}
