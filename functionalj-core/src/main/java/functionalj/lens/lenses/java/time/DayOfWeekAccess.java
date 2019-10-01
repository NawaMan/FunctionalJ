package functionalj.lens.lenses.java.time;

import java.time.DayOfWeek;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import lombok.val;

@FunctionalInterface
public interface DayOfWeekAccess<HOST>
                    extends AnyAccess             <HOST, DayOfWeek>
                    ,       TemporalAccessorAccess<HOST, DayOfWeek>
                    ,       TemporalAdjusterAccess<HOST, DayOfWeek>
                    ,       ConcreteAccess        <HOST, DayOfWeek, DayOfWeekAccess<HOST>> {
    
    public static <H> DayOfWeekAccess<H> of(Function<H, DayOfWeek> func) {
        return func::apply;
    }
    
    public default DayOfWeekAccess<HOST> newAccess(Function<HOST, DayOfWeek> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccessPrimitive<HOST> isMonday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.MONDAY;
        };
    }
    public default BooleanAccessPrimitive<HOST> isTuesday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.TUESDAY;
        };
    }
    public default BooleanAccessPrimitive<HOST> isWednesday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.WEDNESDAY;
        };
    }
    public default BooleanAccessPrimitive<HOST> isThursday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.THURSDAY;
        };
    }
    public default BooleanAccessPrimitive<HOST> isFriday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.FRIDAY;
        };
    }
    public default BooleanAccessPrimitive<HOST> isSaturday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.SATURDAY;
        };
    }
    public default BooleanAccessPrimitive<HOST> isSunday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.SUNDAY;
        };
    }
    
    public default DayOfWeekAccess<HOST> plus(long days) {
        return host -> {
            val value = apply(host);
            return value.plus(days);
        };
    }
    public default DayOfWeekAccess<HOST> minus(long days) {
        return host -> {
            val value = apply(host);
            return value.minus(days);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(DayOfWeek other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    public default BooleanAccess<HOST> thatGreaterThan(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(DayOfWeek anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
}
