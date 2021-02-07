package functionalj.lens.lenses;

import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;

import functionalj.function.ToDoubleBiDoubleFunction;
import lombok.NonNull;
import lombok.val;


public class DoubleAccessEqual<HOST> implements BooleanAccessPrimitive<HOST> {
    
    final boolean isNegate;
    final DoubleAccess<HOST> access;
    final ToDoubleBiDoubleFunction<HOST> anotherValueFunction;
    
    DoubleAccessEqual(
            boolean isNegate,
            @NonNull DoubleAccess<HOST> access,
            @NonNull ToDoubleBiDoubleFunction<HOST> anotherValueFunction) {
        this.isNegate             = isNegate;
        this.access               = access;
        this.anotherValueFunction = anotherValueFunction;
    }
    
    public boolean test(HOST host) {
        val value        = access.applyAsDouble(host);
        val anotherValue = anotherValueFunction.applyAsDouble(host, value);
        val error        = Math.abs(value - anotherValue);
        val precision    = DoubleAccess.equalPrecisionToUse.get().getAsDouble();
        return isNegate != (error <= precision);
    }
    
    public DoubleAccessEqualPrecision<HOST> withIn(double precision) {
        return new DoubleAccessEqualPrecision<>(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecision<HOST> withPrecision(double precision) {
        return new DoubleAccessEqualPrecision<>(this, error -> precision);
    }
    
    public DoubleAccessEqualPrecision<HOST> withPrecision(@NonNull DoubleSupplier precisionSupplier) {
        return new DoubleAccessEqualPrecision<>(this, error -> precisionSupplier.getAsDouble());
    }
    
    public DoubleAccessEqualPrecision<HOST> withPrecision(@NonNull DoubleUnaryOperator precisionFunction) {
        return new DoubleAccessEqualPrecision<>(this, precisionFunction);
    }
    
}
