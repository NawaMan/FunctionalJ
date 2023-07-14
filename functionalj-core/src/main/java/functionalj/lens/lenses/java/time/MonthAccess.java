package functionalj.lens.lenses.java.time;

import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.LongAccessPrimitive;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface MonthAccess<HOST> extends AnyAccess<HOST, Month>, TemporalAccessorAccess<HOST, Month>, TemporalAdjusterAccess<HOST, Month>, ConcreteAccess<HOST, Month, MonthAccess<HOST>> {

    public static <H> MonthAccess<H> of(Function<H, Month> func) {
        return func::apply;
    }

    public default MonthAccess<HOST> newAccess(Function<HOST, Month> accessToValue) {
        return host -> accessToValue.apply(host);
    }

    public default BooleanAccessPrimitive<HOST> isJanuary() {
        return host -> {
            val value = apply(host);
            return value == Month.JANUARY;
        };
    }

    public default BooleanAccessPrimitive<HOST> isFebruary() {
        return host -> {
            val value = apply(host);
            return value == Month.FEBRUARY;
        };
    }

    public default BooleanAccessPrimitive<HOST> isMarch() {
        return host -> {
            val value = apply(host);
            return value == Month.MARCH;
        };
    }

    public default BooleanAccessPrimitive<HOST> isApril() {
        return host -> {
            val value = apply(host);
            return value == Month.APRIL;
        };
    }

    public default BooleanAccessPrimitive<HOST> isMay() {
        return host -> {
            val value = apply(host);
            return value == Month.MAY;
        };
    }

    public default BooleanAccessPrimitive<HOST> isJune() {
        return host -> {
            val value = apply(host);
            return value == Month.JUNE;
        };
    }

    public default BooleanAccessPrimitive<HOST> isJuly() {
        return host -> {
            val value = apply(host);
            return value == Month.JULY;
        };
    }

    public default BooleanAccessPrimitive<HOST> isAugust() {
        return host -> {
            val value = apply(host);
            return value == Month.AUGUST;
        };
    }

    public default BooleanAccessPrimitive<HOST> isSeptember() {
        return host -> {
            val value = apply(host);
            return value == Month.SEPTEMBER;
        };
    }

    public default BooleanAccessPrimitive<HOST> isOctober() {
        return host -> {
            val value = apply(host);
            return value == Month.OCTOBER;
        };
    }

    public default BooleanAccessPrimitive<HOST> isNovember() {
        return host -> {
            val value = apply(host);
            return value == Month.NOVEMBER;
        };
    }

    public default BooleanAccessPrimitive<HOST> isDecember() {
        return host -> {
            val value = apply(host);
            return value == Month.DECEMBER;
        };
    }

    public default IntegerAccessPrimitive<HOST> getValue() {
        return host -> {
            val value = apply(host);
            return value.getValue();
        };
    }

    public default StringAccess<HOST> getDisplayName(TextStyle style, Locale locale) {
        return host -> {
            val value = apply(host);
            return value.getDisplayName(style, locale);
        };
    }

    public default BooleanAccessPrimitive<HOST> thatIsSupported(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.isSupported(field);
        };
    }

    public default ValueRangeAccess<HOST> range(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.range(field);
        };
    }

    public default IntegerAccessPrimitive<HOST> get(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.get(field);
        };
    }

    public default LongAccessPrimitive<HOST> getLong(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.getLong(field);
        };
    }

    public default MonthAccess<HOST> plus(long months) {
        return host -> {
            val value = apply(host);
            return value.plus(months);
        };
    }

    public default MonthAccess<HOST> minus(long months) {
        return host -> {
            val value = apply(host);
            return value.minus(months);
        };
    }

    public default IntegerAccessPrimitive<HOST> length(boolean leapYear) {
        return host -> {
            val value = apply(host);
            return value.length(leapYear);
        };
    }

    public default IntegerAccessPrimitive<HOST> minLength() {
        return host -> {
            val value = apply(host);
            return value.minLength();
        };
    }

    public default IntegerAccessPrimitive<HOST> maxLength() {
        return host -> {
            val value = apply(host);
            return value.maxLength();
        };
    }

    public default IntegerAccessPrimitive<HOST> firstDayOfYear(boolean leapYear) {
        return host -> {
            val value = apply(host);
            return value.firstDayOfYear(leapYear);
        };
    }

    public default MonthAccess<HOST> firstMonthOfQuarter() {
        return host -> {
            val value = apply(host);
            return value.firstMonthOfQuarter();
        };
    }

    public default IntegerAccessPrimitive<HOST> compareTo(Month other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }

    public default BooleanAccess<HOST> thatGreaterThan(Month anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }

    public default BooleanAccess<HOST> thatLessThan(Month anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }

    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(Month anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }

    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(Month anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
}
