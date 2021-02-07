package functionalj.lens.lenses;


import static functionalj.lens.lenses.DoubleAccess.equalPrecisionToUse;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import lombok.NonNull;
import lombok.val;

public class DoubleAccessEqualPrimitive extends DoubleAccessEqual<Double> implements DoubleToBooleanAccessPrimitive {
    
    final DoubleBinaryOperator anotherValueFunction;
    
    DoubleAccessEqualPrimitive(
            boolean isNegate,
            @NonNull DoubleToDoubleAccessPrimitive access,
            @NonNull DoubleBinaryOperator          anotherValueFunction) {
        super(isNegate, access, (host, value) -> anotherValueFunction.applyAsDouble(host, value));
        this.anotherValueFunction = anotherValueFunction;
    }
    
    @Override
    public boolean test(double host) {
        val value        = access.applyAsDouble(host);
        val anotherValue = anotherValueFunction.applyAsDouble(host, value);
        val error        = Math.abs(value - anotherValue);
        val precision    = equalPrecisionToUse.get().getAsDouble();
        return isNegate != (error <= precision);
    }
    
    @Override
    public boolean applyDoubleToBoolean(double host) {
        return test(host);
    }
    
    public DoubleAccessEqualPrecisionPrimitive withIn(double precision) {
        return new DoubleAccessEqualPrecisionPrimitive(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecisionPrimitive withPrecision(double precision) {
        return new DoubleAccessEqualPrecisionPrimitive(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecisionPrimitive withPrecision(@NonNull DoubleSupplier precisionSupplier) {
        return new DoubleAccessEqualPrecisionPrimitive(this, error -> precisionSupplier.getAsDouble());
    }
    
    public DoubleAccessEqualPrecisionPrimitive withPrecision(@NonNull DoubleUnaryOperator precisionFunction) {
        return new DoubleAccessEqualPrecisionPrimitive(this, precisionFunction);
    }
    
}
