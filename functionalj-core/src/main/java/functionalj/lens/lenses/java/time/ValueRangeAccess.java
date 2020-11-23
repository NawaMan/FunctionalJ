package functionalj.lens.lenses.java.time;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;


public interface ValueRangeAccess<HOST>
                    extends AnyAccess<HOST, ValueRange>
                    ,       ConcreteAccess<HOST, ValueRange, ValueRangeAccess<HOST>> {
    
    public static <H> ValueRangeAccess<H> of(Function<H, ValueRange> func) {
        return func::apply;
    }
    
    public default ValueRangeAccess<HOST> newAccess(Function<HOST, ValueRange> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccessPrimitive<HOST> isFixed() {
        return host -> {
            var value = apply(host);
            return value.isFixed();
        };
    }
    public default LongAccessPrimitive<HOST> getMinimum() {
        return host -> {
            var value = apply(host);
            return value.getMinimum();
        };
    }
    public default LongAccessPrimitive<HOST> getLargestMinimum() {
        return host -> {
            var value = apply(host);
            return value.getLargestMinimum();
        };
    }
    public default LongAccessPrimitive<HOST> getSmallestMaximum() {
        return host -> {
            var value = apply(host);
            return value.getSmallestMaximum();
        };
    }
    public default LongAccessPrimitive<HOST> getMaximum() {
        return host -> {
            var value = apply(host);
            return value.getMaximum();
        };
    }
    public default BooleanAccessPrimitive<HOST> isIntValue() {
        return host -> {
            var value = apply(host);
            return value.isIntValue();
        };
    }
    public default BooleanAccessPrimitive<HOST> isValidValue(long value) {
        return host -> {
            return apply(host)
                    .isValidValue(value);
        };
    }
    public default BooleanAccessPrimitive<HOST> isValidIntValue(long value) {
        return host -> {
            return apply(host)
                    .isValidIntValue(value);
        };
    }
    public default LongAccessPrimitive<HOST> checkValidValue(long value, TemporalField field) {
        return host -> {
            return apply(host)
                    .checkValidValue(value, field);
        };
    }
    public default IntegerAccessPrimitive<HOST> checkValidIntValue(long value, TemporalField field) {
        return host -> {
            return apply(host)
                    .checkValidIntValue(value, field);
        };
    }
    
}
