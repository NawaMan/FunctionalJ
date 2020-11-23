package functionalj.lens.lenses.java.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.chrono.Era;
import java.time.chrono.IsoChronology;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Map;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccessPrimitive;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.ListAccess;
import lombok.val;


@FunctionalInterface
public interface IsoChronologyAccess<HOST>
                    extends AnyAccess       <HOST, IsoChronology>
                    ,       ChronologyAccess<HOST, IsoChronology>
                    ,       ConcreteAccess  <HOST, IsoChronology, IsoChronologyAccess<HOST>> {
    
    public static <H> IsoChronologyAccess<H> of(Function<H, IsoChronology> func) {
        return func::apply;
    }
    
    public default IsoChronologyAccess<HOST> newAccess(Function<HOST, IsoChronology> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    public default LocalDateAccess<HOST> date(Era era, int yearOfEra, int month, int dayOfMonth) {
        return host -> {
            val value = apply(host);
            return value.date(era, yearOfEra, month, dayOfMonth);
        };
    }
    public default LocalDateAccess<HOST> date(int prolepticYear, int month, int dayOfMonth) {
        return host -> {
            val value = apply(host);
            return value.date(prolepticYear, month, dayOfMonth);
        };
    }
    public default LocalDateAccess<HOST> dateYearDay(Era era, int yearOfEra, int dayOfYear) {
        return host -> {
            val value = apply(host);
            return value.dateYearDay(era, yearOfEra, dayOfYear);
        };
    }
    public default LocalDateAccess<HOST> dateYearDay(int prolepticYear, int dayOfYear) {
        return host -> {
            val value = apply(host);
            return value.dateYearDay(prolepticYear, dayOfYear);
        };
    }
    public default LocalDateAccess<HOST> dateEpochDay(long epochDay) {
        return host -> {
            val value = apply(host);
            return value.dateEpochDay(epochDay);
        };
    }
    public default LocalDateAccess<HOST> date(TemporalAccessor temporal) {
        return host -> {
            val value = apply(host);
            return value.date(temporal);
        };
    }
    public default LocalDateTimeAccess<HOST> localDateTime(TemporalAccessor temporal) {
        return host -> {
            val value = apply(host);
            return value.localDateTime(temporal);
        };
    }
    public default ZonedDateTimeAccess<HOST> zonedDateTime(TemporalAccessor temporal) {
        return host -> {
            val value = apply(host);
            return value.zonedDateTime(temporal);
        };
    }
    public default ZonedDateTimeAccess<HOST>  zonedDateTime(Instant instant, ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.zonedDateTime(instant, zone);
        };
    }
    public default LocalDateAccess<HOST> dateNow() {
        return host -> {
            val value = apply(host);
            return value.dateNow();
        };
    }
    public default LocalDateAccess<HOST> dateNow(ZoneId zone) {
        return host -> {
            val value = apply(host);
            return value.dateNow(zone);
        };
    }
    public default LocalDateAccess<HOST> dateNow(Clock clock) {
        return host -> {
            val value = apply(host);
            return value.dateNow(clock);
        };
    }
    public default BooleanAccessPrimitive<HOST> isLeapYear(long prolepticYear) {
        return host -> {
            val value = apply(host);
            return value.isLeapYear(prolepticYear);
        };
    }
    public default IntegerAccessPrimitive<HOST> prolepticYear(Era era, int yearOfEra) {
        return host -> {
            val value = apply(host);
            return value.prolepticYear(era, yearOfEra);
        };
    }
    public default IsoEraAccess<HOST> eraOf(int eraValue) {
        return host -> {
            val value = apply(host);
            return value.eraOf(eraValue);
        };
    }
    public default ListAccess<HOST, Era, EraAccess<HOST, Era>> eras() {
        return ListAccess.of(host -> IsoChronologyAccess.this.apply(host).eras(), EraAccess::of);
    }
    public default LocalDateAccess<HOST> resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
        return host -> {
            val value = apply(host);
            return value.resolveDate(fieldValues, resolverStyle);
        };
    }
    public default ValueRangeAccess<HOST> range(ChronoField field) {
        return host -> {
            val value = apply(host);
            return value.range(field);
        };
    }
    public default PeriodAccess<HOST> period(int years, int months, int days) {
        return host -> {
            val value = apply(host);
            return value.period(years, months, days);
        };
    }
    
}
