package functionalj.lens.lenses.java.time;

import java.time.DayOfWeek;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
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
    
    public default BooleanAccess<HOST> isMonday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.MONDAY;
        };
    }
    public default BooleanAccess<HOST> isTuesday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.TUESDAY;
        };
    }
    public default BooleanAccess<HOST> isWednesday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.WEDNESDAY;
        };
    }
    public default BooleanAccess<HOST> isThursday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.THURSDAY;
        };
    }
    public default BooleanAccess<HOST> isFriday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.FRIDAY;
        };
    }
    public default BooleanAccess<HOST> isSaturday() {
        return host -> {
            val value = apply(host);
            return value == DayOfWeek.SATURDAY;
        };
    }
    public default BooleanAccess<HOST> isSunday() {
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
    
    public default IntegerAccess<HOST> compareTo(DayOfWeek other) {
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
