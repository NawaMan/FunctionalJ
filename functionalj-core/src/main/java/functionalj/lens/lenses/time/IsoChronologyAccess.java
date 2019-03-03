package functionalj.lens.lenses.time;

import java.time.chrono.IsoChronology;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;

@FunctionalInterface
public interface IsoChronologyAccess<HOST>
                    extends
                        AnyAccess       <HOST, IsoChronology>,
                        ChronologyAccess<HOST, IsoChronology>,
                        ConcreteAccess  <HOST, IsoChronology, IsoChronologyAccess<HOST>> {
    
    public default IsoChronologyAccess<HOST> newAccess(Function<HOST, IsoChronology> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    // TODO - Not sure if any of this needed as it should be in ChronologyAccess

//    public LocalDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
//        return date(prolepticYear(era, yearOfEra), month, dayOfMonth);
//    }
//    public LocalDate date(int prolepticYear, int month, int dayOfMonth) {
//        return LocalDate.of(prolepticYear, month, dayOfMonth);
//    }
//    public LocalDate dateYearDay(Era era, int yearOfEra, int dayOfYear) {
//        return dateYearDay(prolepticYear(era, yearOfEra), dayOfYear);
//    }
//    public LocalDate dateYearDay(int prolepticYear, int dayOfYear) {
//        return LocalDate.ofYearDay(prolepticYear, dayOfYear);
//    }
//    public LocalDate dateEpochDay(long epochDay) {
//        return LocalDate.ofEpochDay(epochDay);
//    }
//    public LocalDate date(TemporalAccessor temporal) {
//        return LocalDate.from(temporal);
//    }
//    public LocalDateTime localDateTime(TemporalAccessor temporal) {
//        return LocalDateTime.from(temporal);
//    }
//    public ZonedDateTime zonedDateTime(TemporalAccessor temporal) {
//        return ZonedDateTime.from(temporal);
//    }
//    public ZonedDateTime zonedDateTime(Instant instant, ZoneId zone) {
//        return ZonedDateTime.ofInstant(instant, zone);
//    }
//    public LocalDate dateNow() {
//        return dateNow(Clock.systemDefaultZone());
//    }
//    public LocalDate dateNow(ZoneId zone) {
//        return dateNow(Clock.system(zone));
//    }
//    public LocalDate dateNow(Clock clock) {
//        Objects.requireNonNull(clock, "clock");
//        return date(LocalDate.now(clock));
//    }
//    public boolean isLeapYear(long prolepticYear) {
//        return ((prolepticYear & 3) == 0) && ((prolepticYear % 100) != 0 || (prolepticYear % 400) == 0);
//    }
//    public int prolepticYear(Era era, int yearOfEra) {
//        if (era instanceof IsoEra == false) {
//            throw new ClassCastException("Era must be IsoEra");
//        }
//        return (era == IsoEra.CE ? yearOfEra : 1 - yearOfEra);
//    }
//    public IsoEra eraOf(int eraValue) {
//        return IsoEra.of(eraValue);
//    }
//    public List<Era> eras() {
//        return Arrays.<Era>asList(IsoEra.values());
//    }
//    public LocalDate resolveDate(Map<TemporalField, Long> fieldValues, ResolverStyle resolverStyle) {
//        return (LocalDate) super.resolveDate(fieldValues, resolverStyle);
//    }
//    public ValueRange range(ChronoField field) {
//        return field.range();
//    }
//    public Period period(int years, int months, int days) {
//        return Period.of(years, months, days);
//    }
    
}
