package functionalj.lens.lenses;

import static functionalj.lens.lenses.DoubleAccess.equalPrecisionToUse;
import static java.util.Objects.requireNonNull;
import static nullablej.nullable.Nullable.nullable;
import java.util.function.DoubleUnaryOperator;
import lombok.NonNull;
import lombok.val;

public class DoubleAccessEqualPrecision<HOST> implements BooleanAccessPrimitive<HOST> {

    final DoubleAccessEqual<HOST> equals;

    final DoubleUnaryOperator precisionFromErrorFunction;

    public DoubleAccessEqualPrecision(@NonNull DoubleAccessEqual<HOST> equals, @NonNull DoubleUnaryOperator precisionFromErrorFunction) {
        this.equals = requireNonNull(equals);
        this.precisionFromErrorFunction = nullable(precisionFromErrorFunction).orElse((error) -> equalPrecisionToUse.get().getAsDouble());
    }

    @Override
    public boolean test(HOST host) {
        val value = equals.access.applyAsDouble(host);
        val anotherValue = equals.anotherValueFunction.applyAsDouble(host, value);
        val error = Math.abs(value - anotherValue);
        val precision = precisionFromErrorFunction.applyAsDouble(error);
        return equals.isNegate != (error <= precision);
    }
}
