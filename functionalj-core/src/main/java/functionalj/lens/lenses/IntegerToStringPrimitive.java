package functionalj.lens.lenses;

import java.util.function.IntFunction;


@FunctionalInterface
public interface IntegerToStringPrimitive extends StringAccess<Integer>, IntFunction<String> {
    
    public String applyIntToString(int host);
    
    
    @Override
    public default String applyUnsafe(Integer input) throws Exception {
        return applyIntToString(input);
    }
    
    @Override
    public default String apply(int value) {
        return applyIntToString(value);
    }
    
}
