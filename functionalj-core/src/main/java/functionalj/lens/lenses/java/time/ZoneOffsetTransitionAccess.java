package functionalj.lens.lenses.java.time;

import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;


@FunctionalInterface
public interface ZoneOffsetTransitionAccess<HOST>
                    extends AnyAccess      <HOST, ZoneOffsetTransition>
                    ,       ConcreteAccess <HOST, ZoneOffsetTransition, ZoneOffsetTransitionAccess<HOST>> {
    
    public static <H> ZoneOffsetTransitionAccess<H> of(Function<H, ZoneOffsetTransition> func) {
        return func::apply;
    }
    
    public default ZoneOffsetTransitionAccess<HOST> newAccess(Function<HOST, ZoneOffsetTransition> accessToValue) {
        return ZoneOffsetTransitionAccess.of(accessToValue);
    }
    
    public default InstantAccess<HOST> getInstant() {
        return host -> {
            var value = apply(host);
            return value.getInstant();
        };
    }
    public default LongAccessPrimitive<HOST> toEpochSecond() {
        return host -> {
            var value = apply(host);
            return value.toEpochSecond();
        };
    }
    public default LocalDateTimeAccess<HOST> getDateTimeBefore() {
        return host -> {
            var value = apply(host);
            return value.getDateTimeBefore();
        };
    }
    public default LocalDateTimeAccess<HOST> getDateTimeAfter() {
        return host -> {
            var value = apply(host);
            return value.getDateTimeAfter();
        };
    }
    public default ZoneOffsetAccess<HOST> getOffsetBefore() {
        return host -> {
            var value = apply(host);
            return value.getOffsetBefore();
        };
    }
    public default ZoneOffsetAccess<HOST> getOffsetAfter() {
        return host -> {
            var value = apply(host);
            return value.getOffsetAfter();
        };
    }
    public default DurationAccess<HOST> getDuration() {
        return host -> {
            var value = apply(host);
            return value.getDuration();
        };
    }
    public default BooleanAccessPrimitive<HOST> isGap() {
        return host -> {
            var value = apply(host);
            return value.isGap();
        };
    }
    public default BooleanAccessPrimitive<HOST> isOverlap() {
        return host -> {
            var value = apply(host);
            return value.isOverlap();
        };
    }
    public default BooleanAccessPrimitive<HOST> isValidOffset(ZoneOffset offset) {
        return host -> {
            var value = apply(host);
            return value.isValidOffset(offset);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(ZoneOffsetTransition other) {
        return host -> {
            var value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(ZoneOffsetTransition anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
}
