package functionalj.lens.lenses.time;

import java.time.chrono.Chronology;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.StringAccess;
import lombok.val;

@FunctionalInterface
public interface ChronologyAccess<HOST, CHRONOLOGY extends Chronology>
                    extends
                        AnyAccess<HOST, CHRONOLOGY> {
    
    public default StringAccess<HOST> getId() {
        return host -> {
            val value = apply(host);
            return value.getId();
        };
    }
    public default StringAccess<HOST> getCalendarType() {
        return host -> {
            val value = apply(host);
            return value.getCalendarType();
        };
    }
    
    // TODO
//    default ChronoLocalDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
//        return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
//    }
//    ChronoLocalDate date(int prolepticYear, int month, int dayOfMonth);
//    default ChronoLocalDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
//        return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
//    }
//    ChronoLocalDate dateYearDay(int prolepticYear, int dayOfYear);
//    ChronoLocalDate dateEpochDay(long epochDay);
//    default ChronoLocalDate dateNow() {
//        return dateNow(Clock.systemDefaultZone());
//    }
//    default ChronoLocalDate dateNow(ZoneId zone) {
//        return dateNow(Clock.system(zone));
//    }
//    default ChronoLocalDate dateNow(Clock clock) {
//        Objects.requireNonNull(clock, "clock");
//        return date(LocalDate.now(clock));
//    }
//    ChronoLocalDate date(TemporalAccessor temporal);
//    default ChronoLocalDateTime<? extends ChronoLocalDate> localDateTime(TemporalAccessor temporal) {
//        try {
//            return date(temporal).atTime(LocalTime.from(temporal));
//        } catch (DateTimeException ex) {
//            throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + temporal.getClass(), ex);
//        }
//    }
//    default ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(TemporalAccessor temporal) {
//        try {
//            ZoneId zone = ZoneId.from(temporal);
//            try {
//                Instant instant = Instant.from(temporal);
//                return zonedDateTime(instant, zone);
//
//            } catch (DateTimeException ex1) {
//                ChronoLocalDateTimeImpl<?> cldt = ChronoLocalDateTimeImpl.ensureValid(this, localDateTime(temporal));
//                return ChronoZonedDateTimeImpl.ofBest(cldt, zone, null);
//            }
//        } catch (DateTimeException ex) {
//            throw new DateTimeException("Unable to obtain ChronoZonedDateTime from TemporalAccessor: " + temporal.getClass(), ex);
//        }
//    }
//    default ChronoZonedDateTime<? extends ChronoLocalDate> zonedDateTime(Instant instant, ZoneId zone) {
//        return ChronoZonedDateTimeImpl.ofInstant(this, instant, zone);
//    }
    
    
//    boolean isLeapYear(long prolepticYear);
//    int prolepticYear(Era era, int yearOfEra);
//    Era eraOf(int eraValue);
//    List<Era> eras();
//    ValueRange range(ChronoField field);
//    default String getDisplayName(TextStyle style, Locale locale) {
//        TemporalAccessor temporal = new TemporalAccessor() {
//            @Override
//            public boolean isSupported(TemporalField field) {
//                return false;
//            }
//            @Override
//            public long getLong(TemporalField field) {
//                throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
//            }
//            @SuppressWarnings("unchecked")
//            @Override
//            public <R> R query(TemporalQuery<R> query) {
//                if (query == TemporalQueries.chronology()) {
//                    return (R) Chronology.this;
//                }
//                return TemporalAccessor.super.query(query);
//            }
//        };
//        return new DateTimeFormatterBuilder().appendChronologyText(style).toFormatter(locale).format(temporal);
//    }
//    ChronoLocalDate resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle);
//    default ChronoPeriod period(int years, int months, int days) {
//        return new ChronoPeriodImpl(this, years, months, days);
//    }
//    int compareTo(Chronology other);
//    
//    
//    
}
