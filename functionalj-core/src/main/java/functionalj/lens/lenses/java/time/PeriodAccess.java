package functionalj.lens.lenses.java.time;

import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.LongAccess;
import lombok.val;

@FunctionalInterface
public interface PeriodAccess<HOST>
                    extends
                        AnyAccess<HOST, Period>,
                        ChronoPeriodAccess<HOST, Period>,
                        ConcreteAccess<HOST, Period, PeriodAccess<HOST>> {
    
    public default PeriodAccess<HOST> newAccess(Function<HOST, Period> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    // TODO
    public default LongAccess<HOST> get(TemporalUnit unit) {
        return host -> {
            val value = apply(host);
            return value.get(unit);
        };
    }
//    public List<TemporalUnit> getUnits() {
//        return host -> {
//            val value = apply(host);
//            return value.getUnits();
//        };
//    }
    @SuppressWarnings("unchecked")
    public default IsoChronologyAccess<HOST> getChronology() {
        return host -> {
            val value = apply(host);
            return value.getChronology();
        };
    }
    public default BooleanAccess<HOST> isZero() {
        return host -> {
            val value = apply(host);
            return value.isZero();
        };
    }
    public default BooleanAccess<HOST> isNegative() {
        return host -> {
            val value = apply(host);
            return value.isNegative();
        };
    }
    public default IntegerAccess<HOST> getYears() {
        return host -> {
            val value = apply(host);
            return value.getYears();
        };
    }
    public default IntegerAccess<HOST> getMonths() {
        return host -> {
            val value = apply(host);
            return value.getMonths();
        };
    }
    public default IntegerAccess<HOST> getDays() {
        return host -> {
            val value = apply(host);
            return value.getDays();
        };
    }
    public default PeriodAccess<HOST> withYears(int years) {
        return host -> {
            val value = apply(host);
            return value.withYears(years);
        };
    }
    public default PeriodAccess<HOST> withMonths(int months) {
        return host -> {
            val value = apply(host);
            return value.withMonths(months);
        };
    }
    public default PeriodAccess<HOST> withDays(int days) {
        return host -> {
            val value = apply(host);
            return value.withDays(days);
        };
    }
    public default PeriodAccess<HOST> plus(TemporalAmount amountToAdd) {
        return host -> {
            val value = apply(host);
            return value.plus(amountToAdd);
        };
    }
    public default PeriodAccess<HOST> plusYears(long yearsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusYears(yearsToAdd);
        };
    }
    public default PeriodAccess<HOST> plusMonths(long monthsToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusMonths(monthsToAdd);
        };
    }
    public default PeriodAccess<HOST> plusDays(long daysToAdd) {
        return host -> {
            val value = apply(host);
            return value.plusDays(daysToAdd);
        };
    }
    public default PeriodAccess<HOST> minus(TemporalAmount amountToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minus(amountToSubtract);
        };
    }
    public default PeriodAccess<HOST> minusYears(long yearsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusYears(yearsToSubtract);
        };
    }
    public default PeriodAccess<HOST> minusMonths(long monthsToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusMonths(monthsToSubtract);
        };
    }
    public default PeriodAccess<HOST> minusDays(long daysToSubtract) {
        return host -> {
            val value = apply(host);
            return value.minusDays(daysToSubtract);
        };
    }
    public default PeriodAccess<HOST> multipliedBy(int scalar) {
        return host -> {
            val value = apply(host);
            return value.multipliedBy(scalar);
        };
    }
    public default PeriodAccess<HOST> negated() {
        return host -> {
            val value = apply(host);
            return value.negated();
        };
    }
    public default PeriodAccess<HOST> normalized() {
        return host -> {
            val value = apply(host);
            return value.normalized();
        };
    }
    public default LongAccess<HOST> toTotalMonths() {
        return host -> {
            val value = apply(host);
            return value.toTotalMonths();
        };
    }
    
}
