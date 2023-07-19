package functionalj.lens.lenses;

import java.util.function.DoubleUnaryOperator;
import lombok.NonNull;
import lombok.val;

public class LongToDoubleAccessEqualPrecisionPrimitive extends DoubleAccessEqualPrecision<Long> implements LongToBooleanAccessPrimitive {
    
    public LongToDoubleAccessEqualPrecisionPrimitive(@NonNull LongToDoubleAccessEqualPrimitive equals, @NonNull DoubleUnaryOperator precisionFromErrorFunction) {
        super(equals, precisionFromErrorFunction);
    }
    
    @Override
    public boolean applyLongToBoolean(long host) {
        return test(host);
    }
    
    @Override
    public boolean test(long host) {
        val value = equals.access.applyAsDouble(host);
        val anotherValue = equals.anotherValueFunction.applyAsDouble(host, value);
        val error = Math.abs(value - anotherValue);
        val precision = precisionFromErrorFunction.applyAsDouble(error);
        return equals.isNegate != (error <= precision);
    }
}
