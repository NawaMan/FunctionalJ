package functionalj.lens.lenses;

import java.util.function.DoubleUnaryOperator;

import lombok.NonNull;
import lombok.val;

public class DoubleAccessEqualPrecisionPrimitive extends DoubleAccessEqualPrecision<Double> implements DoubleToBooleanAccessPrimitive {
    
    public DoubleAccessEqualPrecisionPrimitive(
            @NonNull DoubleAccessEqualPrimitive equals,
            @NonNull DoubleUnaryOperator        precisionFunction) {
        super(equals, precisionFunction);
    }
    
    @Override
    public boolean applyDoubleToBoolean(double host) {
        return test(host);
    }
    
    @Override
    public boolean test(double host) {
        val value        = equals.access.applyAsDouble(host);
        val anotherValue = equals.anotherValueFunction.applyAsDouble(host, value);
        val error        = Math.abs(value - anotherValue);
        val precision    = precisionFunction.applyAsDouble(error);
        return equals.isNegate != (error <= precision);
    }
    
}
