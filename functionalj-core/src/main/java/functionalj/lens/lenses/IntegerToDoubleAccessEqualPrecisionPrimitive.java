package functionalj.lens.lenses;

import java.util.function.DoubleUnaryOperator;
import lombok.NonNull;
import lombok.val;

public class IntegerToDoubleAccessEqualPrecisionPrimitive extends DoubleAccessEqualPrecision<Integer> implements IntegerToBooleanAccessPrimitive {

    public IntegerToDoubleAccessEqualPrecisionPrimitive(@NonNull IntegerToDoubleAccessEqualPrimitive equals, @NonNull DoubleUnaryOperator precisionFromErrorFunction) {
        super(equals, precisionFromErrorFunction);
    }

    @Override
    public boolean applyIntToBoolean(int host) {
        return test(host);
    }

    @Override
    public boolean test(int host) {
        val value = equals.access.applyAsDouble(host);
        val anotherValue = equals.anotherValueFunction.applyAsDouble(host, value);
        val error = Math.abs(value - anotherValue);
        val precision = precisionFromErrorFunction.applyAsDouble(error);
        return equals.isNegate != (error <= precision);
    }
}
