package functionalj.lens.lenses.java.time;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

public interface ValueRangeAccess<HOST>
                    extends AnyAccess<HOST, ValueRange>
                    ,       ConcreteAccess<HOST, ValueRange, ValueRangeAccess<HOST>> {
    
    public static <H> ValueRangeAccess<H> of(Function<H, ValueRange> func) {
        return func::apply;
    }
    
    public default ValueRangeAccess<HOST> newAccess(Function<HOST, ValueRange> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccess<HOST> isFixed() {
        return host -> {
            val value = apply(host);
            return value.isFixed();
        };
    }
    public default LongAccess<HOST> getMinimum() {
        return host -> {
            val value = apply(host);
            return value.getMinimum();
        };
    }
    public default LongAccess<HOST> getLargestMinimum() {
        return host -> {
            val value = apply(host);
            return value.getLargestMinimum();
        };
    }
    public default LongAccess<HOST> getSmallestMaximum() {
        return host -> {
            val value = apply(host);
            return value.getSmallestMaximum();
        };
    }
    public default LongAccess<HOST> getMaximum() {
        return host -> {
            val value = apply(host);
            return value.getMaximum();
        };
    }
    public default BooleanAccess<HOST> isIntValue() {
        return host -> {
            val value = apply(host);
            return value.isIntValue();
        };
    }
    public default BooleanAccess<HOST> isValidValue(long value) {
        return host -> {
            return apply(host)
                    .isValidValue(value);
        };
    }
    public default BooleanAccess<HOST> isValidIntValue(long value) {
        return host -> {
            return apply(host)
                    .isValidIntValue(value);
        };
    }
    public default LongAccess<HOST> checkValidValue(long value, TemporalField field) {
        return host -> {
            return apply(host)
                    .checkValidValue(value, field);
        };
    }
    public default IntegerAccess<HOST> checkValidIntValue(long value, TemporalField field) {
        return host -> {
            return apply(host)
                    .checkValidIntValue(value, field);
        };
    }
    
}
