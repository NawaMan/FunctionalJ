package functionalj.lens.lenses.java.time;

import java.time.Period;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;

public interface PeriodAccess<HOST>
                    extends
                        AnyAccess<HOST, Period>,
                        ChronoPeriodAccess<HOST, Period>,
                        ConcreteAccess<HOST, Period, PeriodAccess<HOST>> {
    
    public default PeriodAccess<HOST> newAccess(Function<HOST, Period> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    // TODO
//    
//    @Override
//    public long get(TemporalUnit unit) {
//        if (unit == ChronoUnit.YEARS) {
//            return getYears();
//        } else if (unit == ChronoUnit.MONTHS) {
//            return getMonths();
//        } else if (unit == ChronoUnit.DAYS) {
//            return getDays();
//        } else {
//            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
//        }
//    }
//    @Override
//    public List<TemporalUnit> getUnits() {
//        return SUPPORTED_UNITS;
//    }
//    @Override
//    public IsoChronology getChronology() {
//        return IsoChronology.INSTANCE;
//    }
//    public boolean isZero() {
//        return (this == ZERO);
//    }
//    public boolean isNegative() {
//        return years < 0 || months < 0 || days < 0;
//    }
//    public int getYears() {
//        return years;
//    }
//    public int getMonths() {
//        return months;
//    }
//    public int getDays() {
//        return days;
//    }
//    public Period withYears(int years) {
//        if (years == this.years) {
//            return this;
//        }
//        return create(years, months, days);
//    }
//    public Period withMonths(int months) {
//        if (months == this.months) {
//            return this;
//        }
//        return create(years, months, days);
//    }
//    public Period withDays(int days) {
//        if (days == this.days) {
//            return this;
//        }
//        return create(years, months, days);
//    }
//    public Period plus(TemporalAmount amountToAdd) {
//        Period isoAmount = Period.from(amountToAdd);
//        return create(
//                Math.addExact(years, isoAmount.years),
//                Math.addExact(months, isoAmount.months),
//                Math.addExact(days, isoAmount.days));
//    }
//    public Period plusYears(long yearsToAdd) {
//        if (yearsToAdd == 0) {
//            return this;
//        }
//        return create(Math.toIntExact(Math.addExact(years, yearsToAdd)), months, days);
//    }
//    public Period plusMonths(long monthsToAdd) {
//        if (monthsToAdd == 0) {
//            return this;
//        }
//        return create(years, Math.toIntExact(Math.addExact(months, monthsToAdd)), days);
//    }
//    public Period plusDays(long daysToAdd) {
//        if (daysToAdd == 0) {
//            return this;
//        }
//        return create(years, months, Math.toIntExact(Math.addExact(days, daysToAdd)));
//    }
//    public Period minus(TemporalAmount amountToSubtract) {
//        Period isoAmount = Period.from(amountToSubtract);
//        return create(
//                Math.subtractExact(years, isoAmount.years),
//                Math.subtractExact(months, isoAmount.months),
//                Math.subtractExact(days, isoAmount.days));
//    }
//    public Period minusYears(long yearsToSubtract) {
//        return (yearsToSubtract == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-yearsToSubtract));
//    }
//    public Period minusMonths(long monthsToSubtract) {
//        return (monthsToSubtract == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-monthsToSubtract));
//    }
//    public Period minusDays(long daysToSubtract) {
//        return (daysToSubtract == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-daysToSubtract));
//    }
//    public Period multipliedBy(int scalar) {
//        if (this == ZERO || scalar == 1) {
//            return this;
//        }
//        return create(
//                Math.multiplyExact(years, scalar),
//                Math.multiplyExact(months, scalar),
//                Math.multiplyExact(days, scalar));
//    }
//    public Period negated() {
//        return multipliedBy(-1);
//    }
//    public Period normalized() {
//        long totalMonths = toTotalMonths();
//        long splitYears = totalMonths / 12;
//        int splitMonths = (int) (totalMonths % 12);  // no overflow
//        if (splitYears == years && splitMonths == months) {
//            return this;
//        }
//        return create(Math.toIntExact(splitYears), splitMonths, days);
//    }
//    public long toTotalMonths() {
//        return years * 12L + months;  // no overflow
//    }
    
}
