package functionalj.lens.lenses.time;

import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface MonthAccess<HOST>
                    extends
                        AnyAccess             <HOST, Month>,
                        TemporalAccessorAccess<HOST, Month>,
                        TemporalAdjusterAccess<HOST, Month>,
                        ConcreteAccess        <HOST, Month, MonthAccess<HOST>> {
    
    public default MonthAccess<HOST> newAccess(Function<HOST, Month> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default BooleanAccess<HOST> isJanuary() {
        return host -> {
            val value = apply(host);
            return value == Month.JANUARY;
        };
    }
    public default BooleanAccess<HOST> isFebruary() {
        return host -> {
            val value = apply(host);
            return value == Month.FEBRUARY;
        };
    }
    public default BooleanAccess<HOST> isMarch() {
        return host -> {
            val value = apply(host);
            return value == Month.MARCH;
        };
    }
    public default BooleanAccess<HOST> isApril() {
        return host -> {
            val value = apply(host);
            return value == Month.APRIL;
        };
    }
    public default BooleanAccess<HOST> isMay() {
        return host -> {
            val value = apply(host);
            return value == Month.MAY;
        };
    }
    public default BooleanAccess<HOST> isJune() {
        return host -> {
            val value = apply(host);
            return value == Month.JUNE;
        };
    }
    public default BooleanAccess<HOST> isJuly() {
        return host -> {
            val value = apply(host);
            return value == Month.JULY;
        };
    }
    public default BooleanAccess<HOST> isAugust() {
        return host -> {
            val value = apply(host);
            return value == Month.AUGUST;
        };
    }
    public default BooleanAccess<HOST> isSeptember() {
        return host -> {
            val value = apply(host);
            return value == Month.SEPTEMBER;
        };
    }
    public default BooleanAccess<HOST> isOctober() {
        return host -> {
            val value = apply(host);
            return value == Month.OCTOBER;
        };
    }
    public default BooleanAccess<HOST> isNovember() {
        return host -> {
            val value = apply(host);
            return value == Month.NOVEMBER;
        };
    }
    public default BooleanAccess<HOST> isDecember() {
        return host -> {
            val value = apply(host);
            return value == Month.DECEMBER;
        };
    }
    
    public default IntegerAccess<HOST> getValue() {
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
    public default BooleanAccess<HOST> thatIsSupported(TemporalField field) {
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
    public default IntegerAccess<HOST> get(TemporalField field) {
        return host -> {
            val value = apply(host);
            return value.get(field);
        };
    }
    public default LongAccess<HOST> getLong(TemporalField field) {
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
    public default IntegerAccess<HOST> length(boolean leapYear) {
        return host -> {
            val value = apply(host);
            return value.length(leapYear);
        };
    }
    public default IntegerAccess<HOST> minLength() {
        return host -> {
            val value = apply(host);
            return value.minLength();
        };
    }
    public default IntegerAccess<HOST> maxLength() {
        return host -> {
            val value = apply(host);
            return value.maxLength();
        };
    }
    public default IntegerAccess<HOST> firstDayOfYear(boolean leapYear) {
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
}
