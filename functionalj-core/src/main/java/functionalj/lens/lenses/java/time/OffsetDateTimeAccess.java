package functionalj.lens.lenses.java.time;

import java.time.OffsetDateTime;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import lombok.val;

public interface OffsetDateTimeAccess<HOST>
                    extends
                        AnyAccess             <HOST, OffsetDateTime>,
                        TemporalAccess        <HOST, OffsetDateTime>,
                        TemporalAdjusterAccess<HOST, OffsetDateTime>,
                        ConcreteAccess        <HOST, OffsetDateTime, OffsetDateTimeAccess<HOST>> {
    
    public default OffsetDateTimeAccess<HOST> newAccess(Function<HOST, OffsetDateTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    // TODO
//    public ZoneOffset getOffset() {
//        return offset;
//    }
//    public OffsetDateTime withOffsetSameLocal(ZoneOffset offset) {
//        return with(dateTime, offset);
//    }
//    public OffsetDateTime withOffsetSameInstant(ZoneOffset offset) {
//        if (offset.equals(this.offset)) {
//            return this;
//        }
//        int difference = offset.getTotalSeconds() - this.offset.getTotalSeconds();
//        LocalDateTime adjusted = dateTime.plusSeconds(difference);
//        return new OffsetDateTime(adjusted, offset);
//    }
//    public LocalDateTime toLocalDateTime() {
//        return dateTime;
//    }
//    public LocalDate toLocalDate() {
//        return dateTime.toLocalDate();
//    }
//    public int getYear() {
//        return dateTime.getYear();
//    }
//    public int getMonthValue() {
//        return dateTime.getMonthValue();
//    }
//    public Month getMonth() {
//        return dateTime.getMonth();
//    }
//    public int getDayOfMonth() {
//        return dateTime.getDayOfMonth();
//    }
//    public int getDayOfYear() {
//        return dateTime.getDayOfYear();
//    }
//    public DayOfWeek getDayOfWeek() {
//        return dateTime.getDayOfWeek();
//    }
//    public LocalTime toLocalTime() {
//        return dateTime.toLocalTime();
//    }
//    public int getHour() {
//        return dateTime.getHour();
//    }
//    public int getMinute() {
//        return dateTime.getMinute();
//    }
//    public int getSecond() {
//        return dateTime.getSecond();
//    }
//    public int getNano() {
//        return dateTime.getNano();
//    }
//    public OffsetDateTime with(TemporalAdjuster adjuster) {
//        // optimizations
//        if (adjuster instanceof LocalDate || adjuster instanceof LocalTime || adjuster instanceof LocalDateTime) {
//            return with(dateTime.with(adjuster), offset);
//        } else if (adjuster instanceof Instant) {
//            return ofInstant((Instant) adjuster, offset);
//        } else if (adjuster instanceof ZoneOffset) {
//            return with(dateTime, (ZoneOffset) adjuster);
//        } else if (adjuster instanceof OffsetDateTime) {
//            return (OffsetDateTime) adjuster;
//        }
//        return (OffsetDateTime) adjuster.adjustInto(this);
//    }
//    public OffsetDateTime with(TemporalField field, long newValue) {
//        if (field instanceof ChronoField) {
//            ChronoField f = (ChronoField) field;
//            switch (f) {
//                case INSTANT_SECONDS: return ofInstant(Instant.ofEpochSecond(newValue, getNano()), offset);
//                case OFFSET_SECONDS: {
//                    return with(dateTime, ZoneOffset.ofTotalSeconds(f.checkValidIntValue(newValue)));
//                }
//            }
//            return with(dateTime.with(field, newValue), offset);
//        }
//        return field.adjustInto(this, newValue);
//    }
//    public OffsetDateTime withYear(int year) {
//        return with(dateTime.withYear(year), offset);
//    }
//    public OffsetDateTime withMonth(int month) {
//        return with(dateTime.withMonth(month), offset);
//    }
//    public OffsetDateTime withDayOfMonth(int dayOfMonth) {
//        return with(dateTime.withDayOfMonth(dayOfMonth), offset);
//    }
//    public OffsetDateTime withDayOfYear(int dayOfYear) {
//        return with(dateTime.withDayOfYear(dayOfYear), offset);
//    }
//    public OffsetDateTime withHour(int hour) {
//        return with(dateTime.withHour(hour), offset);
//    }
//    public OffsetDateTime withMinute(int minute) {
//        return with(dateTime.withMinute(minute), offset);
//    }
//    public OffsetDateTime withSecond(int second) {
//        return with(dateTime.withSecond(second), offset);
//    }
//    public OffsetDateTime withNano(int nanoOfSecond) {
//        return with(dateTime.withNano(nanoOfSecond), offset);
//    }
//    public OffsetDateTime truncatedTo(TemporalUnit unit) {
//        return with(dateTime.truncatedTo(unit), offset);
//    }
//    public OffsetDateTime plus(TemporalAmount amountToAdd) {
//        return (OffsetDateTime) amountToAdd.addTo(this);
//    }
//    public OffsetDateTime plus(long amountToAdd, TemporalUnit unit) {
//        if (unit instanceof ChronoUnit) {
//            return with(dateTime.plus(amountToAdd, unit), offset);
//        }
//        return unit.addTo(this, amountToAdd);
//    }
//    public OffsetDateTime plusYears(long years) {
//        return with(dateTime.plusYears(years), offset);
//    }
//    public OffsetDateTime plusMonths(long months) {
//        return with(dateTime.plusMonths(months), offset);
//    }
//    public OffsetDateTime plusWeeks(long weeks) {
//        return with(dateTime.plusWeeks(weeks), offset);
//    }
//    public OffsetDateTime plusDays(long days) {
//        return with(dateTime.plusDays(days), offset);
//    }
//    public OffsetDateTime plusHours(long hours) {
//        return with(dateTime.plusHours(hours), offset);
//    }
//    public OffsetDateTime plusMinutes(long minutes) {
//        return with(dateTime.plusMinutes(minutes), offset);
//    }
//    public OffsetDateTime plusSeconds(long seconds) {
//        return with(dateTime.plusSeconds(seconds), offset);
//    }
//    public OffsetDateTime plusNanos(long nanos) {
//        return with(dateTime.plusNanos(nanos), offset);
//    }
//    public OffsetDateTime minus(TemporalAmount amountToSubtract) {
//        return (OffsetDateTime) amountToSubtract.subtractFrom(this);
//    }
//    public OffsetDateTime minus(long amountToSubtract, TemporalUnit unit) {
//        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
//    }
//    public OffsetDateTime minusYears(long years) {
//        return (years == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-years));
//    }
//    public OffsetDateTime minusMonths(long months) {
//        return (months == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-months));
//    }
//    public OffsetDateTime minusWeeks(long weeks) {
//        return (weeks == Long.MIN_VALUE ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeks));
//    }
//    public OffsetDateTime minusDays(long days) {
//        return (days == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-days));
//    }
//    public OffsetDateTime minusHours(long hours) {
//        return (hours == Long.MIN_VALUE ? plusHours(Long.MAX_VALUE).plusHours(1) : plusHours(-hours));
//    }
//    public OffsetDateTime minusMinutes(long minutes) {
//        return (minutes == Long.MIN_VALUE ? plusMinutes(Long.MAX_VALUE).plusMinutes(1) : plusMinutes(-minutes));
//    }
//    public OffsetDateTime minusSeconds(long seconds) {
//        return (seconds == Long.MIN_VALUE ? plusSeconds(Long.MAX_VALUE).plusSeconds(1) : plusSeconds(-seconds));
//    }
//    public OffsetDateTime minusNanos(long nanos) {
//        return (nanos == Long.MIN_VALUE ? plusNanos(Long.MAX_VALUE).plusNanos(1) : plusNanos(-nanos));
//    }
    
//    /**
//     * Combines this date-time with a time-zone to create a {@code ZonedDateTime}
//     * ensuring that the result has the same instant.
//     * <p>
//     * This returns a {@code ZonedDateTime} formed from this date-time and the specified time-zone.
//     * This conversion will ignore the visible local date-time and use the underlying instant instead.
//     * This avoids any problems with local time-line gaps or overlaps.
//     * The result might have different values for fields such as hour, minute an even day.
//     * <p>
//     * To attempt to retain the values of the fields, use {@link #atZoneSimilarLocal(ZoneId)}.
//     * To use the offset as the zone ID, use {@link #toZonedDateTime()}.
//     *
//     * @param zone  the time-zone to use, not null
//     * @return the zoned date-time formed from this date-time, not null
//     */
//    public ZonedDateTime atZoneSameInstant(ZoneId zone) {
//        return ZonedDateTime.ofInstant(dateTime, offset, zone);
//    }
//    public ZonedDateTime atZoneSimilarLocal(ZoneId zone) {
//        return ZonedDateTime.ofLocal(dateTime, zone, offset);
//    }
//    public OffsetTime toOffsetTime() {
//        return OffsetTime.of(dateTime.toLocalTime(), offset);
//    }
//    public ZonedDateTime toZonedDateTime() {
//        return ZonedDateTime.of(dateTime, offset);
//    }
//    public Instant toInstant() {
//        return dateTime.toInstant(offset);
//    }
//    public long toEpochSecond() {
//        return dateTime.toEpochSecond(offset);
//    }
    
    public default IntegerAccess<HOST> compareTo(OffsetDateTime other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    // Duplicate the lt,lte,gt,gte as I am fail to make this extends ComparableAccess
    public default BooleanAccess<HOST> thatGreaterThan(OffsetDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(OffsetDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(OffsetDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(OffsetDateTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccess<HOST> thatIsAfter(OffsetDateTime other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    public default BooleanAccess<HOST> thatIsBefore(OffsetDateTime other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
    public default BooleanAccess<HOST> thatIsEqual(OffsetDateTime other) {
        return host -> {
            val value = apply(host);
            return value.isEqual(other);
        };
    }
    
}
