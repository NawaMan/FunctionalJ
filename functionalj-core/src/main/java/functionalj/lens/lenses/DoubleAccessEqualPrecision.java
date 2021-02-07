package functionalj.lens.lenses;

import static functionalj.lens.lenses.DoubleAccess.equalPrecisionToUse;
import static java.util.Objects.requireNonNull;
import static nullablej.nullable.Nullable.nullable;

import java.util.function.DoubleUnaryOperator;

import lombok.NonNull;
import lombok.val;


public class DoubleAccessEqualPrecision<HOST> implements BooleanAccessPrimitive<HOST> {
    
    final DoubleAccessEqual<HOST> equals;
    final DoubleUnaryOperator     precisionFunction;
    
    public DoubleAccessEqualPrecision(
            @NonNull DoubleAccessEqual<HOST> equals,
            @NonNull DoubleUnaryOperator     precisionFunction) {
        this.equals
                = requireNonNull(equals);
        this.precisionFunction
                = nullable(precisionFunction)
                .orElse((error) -> equalPrecisionToUse.get().getAsDouble());
    }
    
    @Override
    public boolean test(HOST host) {
        val value        = equals.access.applyAsDouble(host);
        val anotherValue = equals.anotherValueFunction.applyAsDouble(host, value);
        val error        = Math.abs(value - anotherValue);
        val precision    = precisionFunction.applyAsDouble(error);
        return equals.isNegate != (error <= precision);
    }
    
}
