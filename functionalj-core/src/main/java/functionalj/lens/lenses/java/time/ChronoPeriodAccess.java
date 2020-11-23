package functionalj.lens.lenses.java.time;

import java.time.chrono.ChronoPeriod;
import java.time.chrono.Chronology;
import java.time.temporal.TemporalAmount;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;


@FunctionalInterface
public interface ChronoPeriodAccess<HOST, CHRONO_PERIOD extends ChronoPeriod>
                    extends AnyAccess           <HOST, CHRONO_PERIOD>
                    ,       TemporalAmountAccess<HOST, CHRONO_PERIOD> {
    
    public static <H, C extends ChronoPeriod> ChronoPeriodAccess<H, C> of(Function<H, C> func) {
        return func::apply;
    }
    
    public default ChronologyAccess<HOST, ? extends Chronology> getChronology() {
        return host -> {
            var value = apply(host);
            return value.getChronology();
        };
    }
    public default BooleanAccessPrimitive<HOST> isZero() {
        return host -> {
            var value = apply(host);
            return value.isZero();
        };
    }
    public default BooleanAccessPrimitive<HOST> isNegative() {
        return host -> {
            var value = apply(host);
            return value.isNegative();
        };
    }
    
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> plus(TemporalAmount amountToAdd) {
        return host -> {
            var value = apply(host);
            return (CHRONO_PERIOD) value.plus(amountToAdd);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> minus(TemporalAmount amountToAdd) {
        return host -> {
            var value = apply(host);
            return (CHRONO_PERIOD) value.minus(amountToAdd);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> multipliedBy(int scalar) {
        return host -> {
            var value = apply(host);
            return (CHRONO_PERIOD) value.multipliedBy(scalar);
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> negated() {
        return host -> {
            var value = apply(host);
            return (CHRONO_PERIOD) value.negated();
        };
    }
    @SuppressWarnings("unchecked")
    public default ChronoPeriodAccess<HOST, CHRONO_PERIOD> normalized() {
        return host -> {
            var value = apply(host);
            return (CHRONO_PERIOD) value.normalized();
        };
    }
}
