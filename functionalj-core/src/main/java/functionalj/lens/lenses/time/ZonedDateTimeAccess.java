package functionalj.lens.lenses.time;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;

public interface ZonedDateTimeAccess<HOST>
                    extends
                        AnyAccess                <HOST, ZonedDateTime>,
                        TemporalAccess           <HOST, ZonedDateTime>,
                        ChronoZonedDateTimeAccess<HOST, LocalDate, ZonedDateTime>,
                        ConcreteAccess           <HOST, ZonedDateTime, ZonedDateTimeAccess<HOST>> {
    
    public default ZonedDateTimeAccess<HOST> newAccess(Function<HOST, ZonedDateTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    
    
    
    // TODO 
//
//    /**
//     * Returns a copy of this date-time changing the zone offset to the
//     * later of the two valid offsets at a local time-line overlap.
//     * <p>
//     * This method only has any effect when the local time-line overlaps, such as
//     * at an autumn daylight savings cutover. In this scenario, there are two
//     * valid offsets for the local date-time. Calling this method will return
//     * a zoned date-time with the later of the two selected.
//     * <p>
//     * If this method is called when it is not an overlap, {@code this}
//     * is returned.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @return a {@code ZonedDateTime} based on this date-time with the later offset, not null
//     */
//    @Override
//    public ZonedDateTime withLaterOffsetAtOverlap() {
//        ZoneOffsetTransition trans = getZone().getRules().getTransition(toLocalDateTime());
//        if (trans != null) {
//            ZoneOffset laterOffset = trans.getOffsetAfter();
//            if (laterOffset.equals(offset) == false) {
//                return new ZonedDateTime(dateTime, laterOffset, zone);
//            }
//        }
//        return this;
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Gets the time-zone, such as 'Europe/Paris'.
//     * <p>
//     * This returns the zone ID. This identifies the time-zone {@link ZoneRules rules}
//     * that determine when and how the offset from UTC/Greenwich changes.
//     * <p>
//     * The zone ID may be same as the {@linkplain #getOffset() offset}.
//     * If this is true, then any future calculations, such as addition or subtraction,
//     * have no complex edge cases due to time-zone rules.
//     * See also {@link #withFixedOffsetZone()}.
//     *
//     * @return the time-zone, not null
//     */
//    @Override
//    public ZoneId getZone() {
//        return zone;
//    }
//
//    /**
//     * Returns a copy of this date-time with a different time-zone,
//     * retaining the local date-time if possible.
//     * <p>
//     * This method changes the time-zone and retains the local date-time.
//     * The local date-time is only changed if it is invalid for the new zone,
//     * determined using the same approach as
//     * {@link #ofLocal(LocalDateTime, ZoneId, ZoneOffset)}.
//     * <p>
//     * To change the zone and adjust the local date-time,
//     * use {@link #withZoneSameInstant(ZoneId)}.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param zone  the time-zone to change to, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
//     */
//    @Override
//    public ZonedDateTime withZoneSameLocal(ZoneId zone) {
//        Objects.requireNonNull(zone, "zone");
//        return this.zone.equals(zone) ? this : ofLocal(dateTime, zone, offset);
//    }
//
//    /**
//     * Returns a copy of this date-time with a different time-zone,
//     * retaining the instant.
//     * <p>
//     * This method changes the time-zone and retains the instant.
//     * This normally results in a change to the local date-time.
//     * <p>
//     * This method is based on retaining the same instant, thus gaps and overlaps
//     * in the local time-line have no effect on the result.
//     * <p>
//     * To change the offset while keeping the local time,
//     * use {@link #withZoneSameLocal(ZoneId)}.
//     *
//     * @param zone  the time-zone to change to, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the requested zone, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    @Override
//    public ZonedDateTime withZoneSameInstant(ZoneId zone) {
//        Objects.requireNonNull(zone, "zone");
//        return this.zone.equals(zone) ? this :
//            create(dateTime.toEpochSecond(offset), dateTime.getNano(), zone);
//    }
//
//    /**
//     * Returns a copy of this date-time with the zone ID set to the offset.
//     * <p>
//     * This returns a zoned date-time where the zone ID is the same as {@link #getOffset()}.
//     * The local date-time, offset and instant of the result will be the same as in this date-time.
//     * <p>
//     * Setting the date-time to a fixed single offset means that any future
//     * calculations, such as addition or subtraction, have no complex edge cases
//     * due to time-zone rules.
//     * This might also be useful when sending a zoned date-time across a network,
//     * as most protocols, such as ISO-8601, only handle offsets,
//     * and not region-based zone IDs.
//     * <p>
//     * This is equivalent to {@code ZonedDateTime.of(zdt.toLocalDateTime(), zdt.getOffset())}.
//     *
//     * @return a {@code ZonedDateTime} with the zone ID set to the offset, not null
//     */
//    public ZonedDateTime withFixedOffsetZone() {
//        return this.zone.equals(offset) ? this : new ZonedDateTime(dateTime, offset, offset);
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Gets the {@code LocalDateTime} part of this date-time.
//     * <p>
//     * This returns a {@code LocalDateTime} with the same year, month, day and time
//     * as this date-time.
//     *
//     * @return the local date-time part of this date-time, not null
//     */
//    @Override  // override for return type
//    public LocalDateTime toLocalDateTime() {
//        return dateTime;
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Gets the {@code LocalDate} part of this date-time.
//     * <p>
//     * This returns a {@code LocalDate} with the same year, month and day
//     * as this date-time.
//     *
//     * @return the date part of this date-time, not null
//     */
//    @Override  // override for return type
//    public LocalDate toLocalDate() {
//        return dateTime.toLocalDate();
//    }
//
//    /**
//     * Gets the year field.
//     * <p>
//     * This method returns the primitive {@code int} value for the year.
//     * <p>
//     * The year returned by this method is proleptic as per {@code get(YEAR)}.
//     * To obtain the year-of-era, use {@code get(YEAR_OF_ERA)}.
//     *
//     * @return the year, from MIN_YEAR to MAX_YEAR
//     */
//    public int getYear() {
//        return dateTime.getYear();
//    }
//
//    /**
//     * Gets the month-of-year field from 1 to 12.
//     * <p>
//     * This method returns the month as an {@code int} from 1 to 12.
//     * Application code is frequently clearer if the enum {@link Month}
//     * is used by calling {@link #getMonth()}.
//     *
//     * @return the month-of-year, from 1 to 12
//     * @see #getMonth()
//     */
//    public int getMonthValue() {
//        return dateTime.getMonthValue();
//    }
//
//    /**
//     * Gets the month-of-year field using the {@code Month} enum.
//     * <p>
//     * This method returns the enum {@link Month} for the month.
//     * This avoids confusion as to what {@code int} values mean.
//     * If you need access to the primitive {@code int} value then the enum
//     * provides the {@link Month#getValue() int value}.
//     *
//     * @return the month-of-year, not null
//     * @see #getMonthValue()
//     */
//    public Month getMonth() {
//        return dateTime.getMonth();
//    }
//
//    /**
//     * Gets the day-of-month field.
//     * <p>
//     * This method returns the primitive {@code int} value for the day-of-month.
//     *
//     * @return the day-of-month, from 1 to 31
//     */
//    public int getDayOfMonth() {
//        return dateTime.getDayOfMonth();
//    }
//
//    /**
//     * Gets the day-of-year field.
//     * <p>
//     * This method returns the primitive {@code int} value for the day-of-year.
//     *
//     * @return the day-of-year, from 1 to 365, or 366 in a leap year
//     */
//    public int getDayOfYear() {
//        return dateTime.getDayOfYear();
//    }
//
//    /**
//     * Gets the day-of-week field, which is an enum {@code DayOfWeek}.
//     * <p>
//     * This method returns the enum {@link DayOfWeek} for the day-of-week.
//     * This avoids confusion as to what {@code int} values mean.
//     * If you need access to the primitive {@code int} value then the enum
//     * provides the {@link DayOfWeek#getValue() int value}.
//     * <p>
//     * Additional information can be obtained from the {@code DayOfWeek}.
//     * This includes textual names of the values.
//     *
//     * @return the day-of-week, not null
//     */
//    public DayOfWeek getDayOfWeek() {
//        return dateTime.getDayOfWeek();
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Gets the {@code LocalTime} part of this date-time.
//     * <p>
//     * This returns a {@code LocalTime} with the same hour, minute, second and
//     * nanosecond as this date-time.
//     *
//     * @return the time part of this date-time, not null
//     */
//    @Override  // override for Javadoc and performance
//    public LocalTime toLocalTime() {
//        return dateTime.toLocalTime();
//    }
//
//    /**
//     * Gets the hour-of-day field.
//     *
//     * @return the hour-of-day, from 0 to 23
//     */
//    public int getHour() {
//        return dateTime.getHour();
//    }
//
//    /**
//     * Gets the minute-of-hour field.
//     *
//     * @return the minute-of-hour, from 0 to 59
//     */
//    public int getMinute() {
//        return dateTime.getMinute();
//    }
//
//    /**
//     * Gets the second-of-minute field.
//     *
//     * @return the second-of-minute, from 0 to 59
//     */
//    public int getSecond() {
//        return dateTime.getSecond();
//    }
//
//    /**
//     * Gets the nano-of-second field.
//     *
//     * @return the nano-of-second, from 0 to 999,999,999
//     */
//    public int getNano() {
//        return dateTime.getNano();
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns an adjusted copy of this date-time.
//     * <p>
//     * This returns a {@code ZonedDateTime}, based on this one, with the date-time adjusted.
//     * The adjustment takes place using the specified adjuster strategy object.
//     * Read the documentation of the adjuster to understand what adjustment will be made.
//     * <p>
//     * A simple adjuster might simply set the one of the fields, such as the year field.
//     * A more complex adjuster might set the date to the last day of the month.
//     * A selection of common adjustments is provided in
//     * {@link java.time.temporal.TemporalAdjusters TemporalAdjusters}.
//     * These include finding the "last day of the month" and "next Wednesday".
//     * Key date-time classes also implement the {@code TemporalAdjuster} interface,
//     * such as {@link Month} and {@link java.time.MonthDay MonthDay}.
//     * The adjuster is responsible for handling special cases, such as the varying
//     * lengths of month and leap years.
//     * <p>
//     * For example this code returns a date on the last day of July:
//     * <pre>
//     *  import static java.time.Month.*;
//     *  import static java.time.temporal.TemporalAdjusters.*;
//     *
//     *  result = zonedDateTime.with(JULY).with(lastDayOfMonth());
//     * </pre>
//     * <p>
//     * The classes {@link LocalDate} and {@link LocalTime} implement {@code TemporalAdjuster},
//     * thus this method can be used to change the date, time or offset:
//     * <pre>
//     *  result = zonedDateTime.with(date);
//     *  result = zonedDateTime.with(time);
//     * </pre>
//     * <p>
//     * {@link ZoneOffset} also implements {@code TemporalAdjuster} however using it
//     * as an argument typically has no effect. The offset of a {@code ZonedDateTime} is
//     * controlled primarily by the time-zone. As such, changing the offset does not generally
//     * make sense, because there is only one valid offset for the local date-time and zone.
//     * If the zoned date-time is in a daylight savings overlap, then the offset is used
//     * to switch between the two valid offsets. In all other cases, the offset is ignored.
//     * <p>
//     * The result of this method is obtained by invoking the
//     * {@link TemporalAdjuster#adjustInto(Temporal)} method on the
//     * specified adjuster passing {@code this} as the argument.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param adjuster the adjuster to use, not null
//     * @return a {@code ZonedDateTime} based on {@code this} with the adjustment made, not null
//     * @throws DateTimeException if the adjustment cannot be made
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public ZonedDateTime with(TemporalAdjuster adjuster) {
//        // optimizations
//        if (adjuster instanceof LocalDate) {
//            return resolveLocal(LocalDateTime.of((LocalDate) adjuster, dateTime.toLocalTime()));
//        } else if (adjuster instanceof LocalTime) {
//            return resolveLocal(LocalDateTime.of(dateTime.toLocalDate(), (LocalTime) adjuster));
//        } else if (adjuster instanceof LocalDateTime) {
//            return resolveLocal((LocalDateTime) adjuster);
//        } else if (adjuster instanceof OffsetDateTime) {
//            OffsetDateTime odt = (OffsetDateTime) adjuster;
//            return ofLocal(odt.toLocalDateTime(), zone, odt.getOffset());
//        } else if (adjuster instanceof Instant) {
//            Instant instant = (Instant) adjuster;
//            return create(instant.getEpochSecond(), instant.getNano(), zone);
//        } else if (adjuster instanceof ZoneOffset) {
//            return resolveOffset((ZoneOffset) adjuster);
//        }
//        return (ZonedDateTime) adjuster.adjustInto(this);
//    }
//
//    /**
//     * Returns a copy of this date-time with the specified field set to a new value.
//     * <p>
//     * This returns a {@code ZonedDateTime}, based on this one, with the value
//     * for the specified field changed.
//     * This can be used to change any supported field, such as the year, month or day-of-month.
//     * If it is not possible to set the value, because the field is not supported or for
//     * some other reason, an exception is thrown.
//     * <p>
//     * In some cases, changing the specified field can cause the resulting date-time to become invalid,
//     * such as changing the month from 31st January to February would make the day-of-month invalid.
//     * In cases like this, the field is responsible for resolving the date. Typically it will choose
//     * the previous valid date, which would be the last valid day of February in this example.
//     * <p>
//     * If the field is a {@link ChronoField} then the adjustment is implemented here.
//     * <p>
//     * The {@code INSTANT_SECONDS} field will return a date-time with the specified instant.
//     * The zone and nano-of-second are unchanged.
//     * The result will have an offset derived from the new instant and original zone.
//     * If the new instant value is outside the valid range then a {@code DateTimeException} will be thrown.
//     * <p>
//     * The {@code OFFSET_SECONDS} field will typically be ignored.
//     * The offset of a {@code ZonedDateTime} is controlled primarily by the time-zone.
//     * As such, changing the offset does not generally make sense, because there is only
//     * one valid offset for the local date-time and zone.
//     * If the zoned date-time is in a daylight savings overlap, then the offset is used
//     * to switch between the two valid offsets. In all other cases, the offset is ignored.
//     * If the new offset value is outside the valid range then a {@code DateTimeException} will be thrown.
//     * <p>
//     * The other {@link #isSupported(TemporalField) supported fields} will behave as per
//     * the matching method on {@link LocalDateTime#with(TemporalField, long) LocalDateTime}.
//     * The zone is not part of the calculation and will be unchanged.
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * All other {@code ChronoField} instances will throw an {@code UnsupportedTemporalTypeException}.
//     * <p>
//     * If the field is not a {@code ChronoField}, then the result of this method
//     * is obtained by invoking {@code TemporalField.adjustInto(Temporal, long)}
//     * passing {@code this} as the argument. In this case, the field determines
//     * whether and how to adjust the instant.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param field  the field to set in the result, not null
//     * @param newValue  the new value of the field in the result
//     * @return a {@code ZonedDateTime} based on {@code this} with the specified field set, not null
//     * @throws DateTimeException if the field cannot be set
//     * @throws UnsupportedTemporalTypeException if the field is not supported
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public ZonedDateTime with(TemporalField field, long newValue) {
//        if (field instanceof ChronoField) {
//            ChronoField f = (ChronoField) field;
//            switch (f) {
//                case INSTANT_SECONDS:
//                    return create(newValue, getNano(), zone);
//                case OFFSET_SECONDS:
//                    ZoneOffset offset = ZoneOffset.ofTotalSeconds(f.checkValidIntValue(newValue));
//                    return resolveOffset(offset);
//            }
//            return resolveLocal(dateTime.with(field, newValue));
//        }
//        return field.adjustInto(this, newValue);
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the year altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#withYear(int) changing the year} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param year  the year to set in the result, from MIN_YEAR to MAX_YEAR
//     * @return a {@code ZonedDateTime} based on this date-time with the requested year, not null
//     * @throws DateTimeException if the year value is invalid
//     */
//    public ZonedDateTime withYear(int year) {
//        return resolveLocal(dateTime.withYear(year));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the month-of-year altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#withMonth(int) changing the month} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param month  the month-of-year to set in the result, from 1 (January) to 12 (December)
//     * @return a {@code ZonedDateTime} based on this date-time with the requested month, not null
//     * @throws DateTimeException if the month-of-year value is invalid
//     */
//    public ZonedDateTime withMonth(int month) {
//        return resolveLocal(dateTime.withMonth(month));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the day-of-month altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#withDayOfMonth(int) changing the day-of-month} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param dayOfMonth  the day-of-month to set in the result, from 1 to 28-31
//     * @return a {@code ZonedDateTime} based on this date-time with the requested day, not null
//     * @throws DateTimeException if the day-of-month value is invalid,
//     *  or if the day-of-month is invalid for the month-year
//     */
//    public ZonedDateTime withDayOfMonth(int dayOfMonth) {
//        return resolveLocal(dateTime.withDayOfMonth(dayOfMonth));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the day-of-year altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#withDayOfYear(int) changing the day-of-year} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param dayOfYear  the day-of-year to set in the result, from 1 to 365-366
//     * @return a {@code ZonedDateTime} based on this date with the requested day, not null
//     * @throws DateTimeException if the day-of-year value is invalid,
//     *  or if the day-of-year is invalid for the year
//     */
//    public ZonedDateTime withDayOfYear(int dayOfYear) {
//        return resolveLocal(dateTime.withDayOfYear(dayOfYear));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the hour-of-day altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@linkplain LocalDateTime#withHour(int) changing the time} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param hour  the hour-of-day to set in the result, from 0 to 23
//     * @return a {@code ZonedDateTime} based on this date-time with the requested hour, not null
//     * @throws DateTimeException if the hour value is invalid
//     */
//    public ZonedDateTime withHour(int hour) {
//        return resolveLocal(dateTime.withHour(hour));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the minute-of-hour altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@linkplain LocalDateTime#withMinute(int) changing the time} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param minute  the minute-of-hour to set in the result, from 0 to 59
//     * @return a {@code ZonedDateTime} based on this date-time with the requested minute, not null
//     * @throws DateTimeException if the minute value is invalid
//     */
//    public ZonedDateTime withMinute(int minute) {
//        return resolveLocal(dateTime.withMinute(minute));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the second-of-minute altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@linkplain LocalDateTime#withSecond(int) changing the time} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param second  the second-of-minute to set in the result, from 0 to 59
//     * @return a {@code ZonedDateTime} based on this date-time with the requested second, not null
//     * @throws DateTimeException if the second value is invalid
//     */
//    public ZonedDateTime withSecond(int second) {
//        return resolveLocal(dateTime.withSecond(second));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the nano-of-second altered.
//     * <p>
//     * This operates on the local time-line,
//     * {@linkplain LocalDateTime#withNano(int) changing the time} of the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param nanoOfSecond  the nano-of-second to set in the result, from 0 to 999,999,999
//     * @return a {@code ZonedDateTime} based on this date-time with the requested nanosecond, not null
//     * @throws DateTimeException if the nano value is invalid
//     */
//    public ZonedDateTime withNano(int nanoOfSecond) {
//        return resolveLocal(dateTime.withNano(nanoOfSecond));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the time truncated.
//     * <p>
//     * Truncation returns a copy of the original date-time with fields
//     * smaller than the specified unit set to zero.
//     * For example, truncating with the {@link ChronoUnit#MINUTES minutes} unit
//     * will set the second-of-minute and nano-of-second field to zero.
//     * <p>
//     * The unit must have a {@linkplain TemporalUnit#getDuration() duration}
//     * that divides into the length of a standard day without remainder.
//     * This includes all supplied time units on {@link ChronoUnit} and
//     * {@link ChronoUnit#DAYS DAYS}. Other units throw an exception.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#truncatedTo(TemporalUnit) truncating}
//     * the underlying local date-time. This is then converted back to a
//     * {@code ZonedDateTime}, using the zone ID to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param unit  the unit to truncate to, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the time truncated, not null
//     * @throws DateTimeException if unable to truncate
//     * @throws UnsupportedTemporalTypeException if the unit is not supported
//     */
//    public ZonedDateTime truncatedTo(TemporalUnit unit) {
//        return resolveLocal(dateTime.truncatedTo(unit));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this date-time with the specified amount added.
//     * <p>
//     * This returns a {@code ZonedDateTime}, based on this one, with the specified amount added.
//     * The amount is typically {@link Period} or {@link Duration} but may be
//     * any other type implementing the {@link TemporalAmount} interface.
//     * <p>
//     * The calculation is delegated to the amount object by calling
//     * {@link TemporalAmount#addTo(Temporal)}. The amount implementation is free
//     * to implement the addition in any way it wishes, however it typically
//     * calls back to {@link #plus(long, TemporalUnit)}. Consult the documentation
//     * of the amount implementation to determine if it can be successfully added.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param amountToAdd  the amount to add, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the addition made, not null
//     * @throws DateTimeException if the addition cannot be made
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public ZonedDateTime plus(TemporalAmount amountToAdd) {
//        if (amountToAdd instanceof Period) {
//            Period periodToAdd = (Period) amountToAdd;
//            return resolveLocal(dateTime.plus(periodToAdd));
//        }
//        Objects.requireNonNull(amountToAdd, "amountToAdd");
//        return (ZonedDateTime) amountToAdd.addTo(this);
//    }
//
//    /**
//     * Returns a copy of this date-time with the specified amount added.
//     * <p>
//     * This returns a {@code ZonedDateTime}, based on this one, with the amount
//     * in terms of the unit added. If it is not possible to add the amount, because the
//     * unit is not supported or for some other reason, an exception is thrown.
//     * <p>
//     * If the field is a {@link ChronoUnit} then the addition is implemented here.
//     * The zone is not part of the calculation and will be unchanged in the result.
//     * The calculation for date and time units differ.
//     * <p>
//     * Date units operate on the local time-line.
//     * The period is first added to the local date-time, then converted back
//     * to a zoned date-time using the zone ID.
//     * The conversion uses {@link #ofLocal(LocalDateTime, ZoneId, ZoneOffset)}
//     * with the offset before the addition.
//     * <p>
//     * Time units operate on the instant time-line.
//     * The period is first added to the local date-time, then converted back to
//     * a zoned date-time using the zone ID.
//     * The conversion uses {@link #ofInstant(LocalDateTime, ZoneOffset, ZoneId)}
//     * with the offset before the addition.
//     * <p>
//     * If the field is not a {@code ChronoUnit}, then the result of this method
//     * is obtained by invoking {@code TemporalUnit.addTo(Temporal, long)}
//     * passing {@code this} as the argument. In this case, the unit determines
//     * whether and how to perform the addition.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param amountToAdd  the amount of the unit to add to the result, may be negative
//     * @param unit  the unit of the amount to add, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the specified amount added, not null
//     * @throws DateTimeException if the addition cannot be made
//     * @throws UnsupportedTemporalTypeException if the unit is not supported
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public ZonedDateTime plus(long amountToAdd, TemporalUnit unit) {
//        if (unit instanceof ChronoUnit) {
//            if (unit.isDateBased()) {
//                return resolveLocal(dateTime.plus(amountToAdd, unit));
//            } else {
//                return resolveInstant(dateTime.plus(amountToAdd, unit));
//            }
//        }
//        return unit.addTo(this, amountToAdd);
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of years added.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#plusYears(long) adding years} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param years  the years to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the years added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusYears(long years) {
//        return resolveLocal(dateTime.plusYears(years));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of months added.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#plusMonths(long) adding months} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param months  the months to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the months added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusMonths(long months) {
//        return resolveLocal(dateTime.plusMonths(months));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of weeks added.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#plusWeeks(long) adding weeks} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param weeks  the weeks to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the weeks added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusWeeks(long weeks) {
//        return resolveLocal(dateTime.plusWeeks(weeks));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of days added.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#plusDays(long) adding days} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param days  the days to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the days added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusDays(long days) {
//        return resolveLocal(dateTime.plusDays(days));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of hours added.
//     * <p>
//     * This operates on the instant time-line, such that adding one hour will
//     * always be a duration of one hour later.
//     * This may cause the local date-time to change by an amount other than one hour.
//     * Note that this is a different approach to that used by days, months and years,
//     * thus adding one day is not the same as adding 24 hours.
//     * <p>
//     * For example, consider a time-zone where the spring DST cutover means that the
//     * local times 01:00 to 01:59 occur twice changing from offset +02:00 to +01:00.
//     * <ul>
//     * <li>Adding one hour to 00:30+02:00 will result in 01:30+02:00
//     * <li>Adding one hour to 01:30+02:00 will result in 01:30+01:00
//     * <li>Adding one hour to 01:30+01:00 will result in 02:30+01:00
//     * <li>Adding three hours to 00:30+02:00 will result in 02:30+01:00
//     * </ul>
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param hours  the hours to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the hours added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusHours(long hours) {
//        return resolveInstant(dateTime.plusHours(hours));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of minutes added.
//     * <p>
//     * This operates on the instant time-line, such that adding one minute will
//     * always be a duration of one minute later.
//     * This may cause the local date-time to change by an amount other than one minute.
//     * Note that this is a different approach to that used by days, months and years.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param minutes  the minutes to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the minutes added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusMinutes(long minutes) {
//        return resolveInstant(dateTime.plusMinutes(minutes));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of seconds added.
//     * <p>
//     * This operates on the instant time-line, such that adding one second will
//     * always be a duration of one second later.
//     * This may cause the local date-time to change by an amount other than one second.
//     * Note that this is a different approach to that used by days, months and years.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param seconds  the seconds to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the seconds added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusSeconds(long seconds) {
//        return resolveInstant(dateTime.plusSeconds(seconds));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of nanoseconds added.
//     * <p>
//     * This operates on the instant time-line, such that adding one nano will
//     * always be a duration of one nano later.
//     * This may cause the local date-time to change by an amount other than one nano.
//     * Note that this is a different approach to that used by days, months and years.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param nanos  the nanos to add, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the nanoseconds added, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime plusNanos(long nanos) {
//        return resolveInstant(dateTime.plusNanos(nanos));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this date-time with the specified amount subtracted.
//     * <p>
//     * This returns a {@code ZonedDateTime}, based on this one, with the specified amount subtracted.
//     * The amount is typically {@link Period} or {@link Duration} but may be
//     * any other type implementing the {@link TemporalAmount} interface.
//     * <p>
//     * The calculation is delegated to the amount object by calling
//     * {@link TemporalAmount#subtractFrom(Temporal)}. The amount implementation is free
//     * to implement the subtraction in any way it wishes, however it typically
//     * calls back to {@link #minus(long, TemporalUnit)}. Consult the documentation
//     * of the amount implementation to determine if it can be successfully subtracted.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param amountToSubtract  the amount to subtract, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the subtraction made, not null
//     * @throws DateTimeException if the subtraction cannot be made
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public ZonedDateTime minus(TemporalAmount amountToSubtract) {
//        if (amountToSubtract instanceof Period) {
//            Period periodToSubtract = (Period) amountToSubtract;
//            return resolveLocal(dateTime.minus(periodToSubtract));
//        }
//        Objects.requireNonNull(amountToSubtract, "amountToSubtract");
//        return (ZonedDateTime) amountToSubtract.subtractFrom(this);
//    }
//
//    /**
//     * Returns a copy of this date-time with the specified amount subtracted.
//     * <p>
//     * This returns a {@code ZonedDateTime}, based on this one, with the amount
//     * in terms of the unit subtracted. If it is not possible to subtract the amount,
//     * because the unit is not supported or for some other reason, an exception is thrown.
//     * <p>
//     * The calculation for date and time units differ.
//     * <p>
//     * Date units operate on the local time-line.
//     * The period is first subtracted from the local date-time, then converted back
//     * to a zoned date-time using the zone ID.
//     * The conversion uses {@link #ofLocal(LocalDateTime, ZoneId, ZoneOffset)}
//     * with the offset before the subtraction.
//     * <p>
//     * Time units operate on the instant time-line.
//     * The period is first subtracted from the local date-time, then converted back to
//     * a zoned date-time using the zone ID.
//     * The conversion uses {@link #ofInstant(LocalDateTime, ZoneOffset, ZoneId)}
//     * with the offset before the subtraction.
//     * <p>
//     * This method is equivalent to {@link #plus(long, TemporalUnit)} with the amount negated.
//     * See that method for a full description of how addition, and thus subtraction, works.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param amountToSubtract  the amount of the unit to subtract from the result, may be negative
//     * @param unit  the unit of the amount to subtract, not null
//     * @return a {@code ZonedDateTime} based on this date-time with the specified amount subtracted, not null
//     * @throws DateTimeException if the subtraction cannot be made
//     * @throws UnsupportedTemporalTypeException if the unit is not supported
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public ZonedDateTime minus(long amountToSubtract, TemporalUnit unit) {
//        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of years subtracted.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#minusYears(long) subtracting years} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param years  the years to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the years subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusYears(long years) {
//        return (years == Long.MIN_VALUE ? plusYears(Long.MAX_VALUE).plusYears(1) : plusYears(-years));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of months subtracted.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#minusMonths(long) subtracting months} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param months  the months to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the months subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusMonths(long months) {
//        return (months == Long.MIN_VALUE ? plusMonths(Long.MAX_VALUE).plusMonths(1) : plusMonths(-months));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of weeks subtracted.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#minusWeeks(long) subtracting weeks} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param weeks  the weeks to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the weeks subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusWeeks(long weeks) {
//        return (weeks == Long.MIN_VALUE ? plusWeeks(Long.MAX_VALUE).plusWeeks(1) : plusWeeks(-weeks));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of days subtracted.
//     * <p>
//     * This operates on the local time-line,
//     * {@link LocalDateTime#minusDays(long) subtracting days} to the local date-time.
//     * This is then converted back to a {@code ZonedDateTime}, using the zone ID
//     * to obtain the offset.
//     * <p>
//     * When converting back to {@code ZonedDateTime}, if the local date-time is in an overlap,
//     * then the offset will be retained if possible, otherwise the earlier offset will be used.
//     * If in a gap, the local date-time will be adjusted forward by the length of the gap.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param days  the days to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the days subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusDays(long days) {
//        return (days == Long.MIN_VALUE ? plusDays(Long.MAX_VALUE).plusDays(1) : plusDays(-days));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of hours subtracted.
//     * <p>
//     * This operates on the instant time-line, such that subtracting one hour will
//     * always be a duration of one hour earlier.
//     * This may cause the local date-time to change by an amount other than one hour.
//     * Note that this is a different approach to that used by days, months and years,
//     * thus subtracting one day is not the same as adding 24 hours.
//     * <p>
//     * For example, consider a time-zone where the spring DST cutover means that the
//     * local times 01:00 to 01:59 occur twice changing from offset +02:00 to +01:00.
//     * <ul>
//     * <li>Subtracting one hour from 02:30+01:00 will result in 01:30+02:00
//     * <li>Subtracting one hour from 01:30+01:00 will result in 01:30+02:00
//     * <li>Subtracting one hour from 01:30+02:00 will result in 00:30+01:00
//     * <li>Subtracting three hours from 02:30+01:00 will result in 00:30+02:00
//     * </ul>
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param hours  the hours to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the hours subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusHours(long hours) {
//        return (hours == Long.MIN_VALUE ? plusHours(Long.MAX_VALUE).plusHours(1) : plusHours(-hours));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of minutes subtracted.
//     * <p>
//     * This operates on the instant time-line, such that subtracting one minute will
//     * always be a duration of one minute earlier.
//     * This may cause the local date-time to change by an amount other than one minute.
//     * Note that this is a different approach to that used by days, months and years.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param minutes  the minutes to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the minutes subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusMinutes(long minutes) {
//        return (minutes == Long.MIN_VALUE ? plusMinutes(Long.MAX_VALUE).plusMinutes(1) : plusMinutes(-minutes));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of seconds subtracted.
//     * <p>
//     * This operates on the instant time-line, such that subtracting one second will
//     * always be a duration of one second earlier.
//     * This may cause the local date-time to change by an amount other than one second.
//     * Note that this is a different approach to that used by days, months and years.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param seconds  the seconds to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the seconds subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusSeconds(long seconds) {
//        return (seconds == Long.MIN_VALUE ? plusSeconds(Long.MAX_VALUE).plusSeconds(1) : plusSeconds(-seconds));
//    }
//
//    /**
//     * Returns a copy of this {@code ZonedDateTime} with the specified number of nanoseconds subtracted.
//     * <p>
//     * This operates on the instant time-line, such that subtracting one nano will
//     * always be a duration of one nano earlier.
//     * This may cause the local date-time to change by an amount other than one nano.
//     * Note that this is a different approach to that used by days, months and years.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param nanos  the nanos to subtract, may be negative
//     * @return a {@code ZonedDateTime} based on this date-time with the nanoseconds subtracted, not null
//     * @throws DateTimeException if the result exceeds the supported date range
//     */
//    public ZonedDateTime minusNanos(long nanos) {
//        return (nanos == Long.MIN_VALUE ? plusNanos(Long.MAX_VALUE).plusNanos(1) : plusNanos(-nanos));
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Queries this date-time using the specified query.
//     * <p>
//     * This queries this date-time using the specified query strategy object.
//     * The {@code TemporalQuery} object defines the logic to be used to
//     * obtain the result. Read the documentation of the query to understand
//     * what the result of this method will be.
//     * <p>
//     * The result of this method is obtained by invoking the
//     * {@link TemporalQuery#queryFrom(TemporalAccessor)} method on the
//     * specified query passing {@code this} as the argument.
//     *
//     * @param <R> the type of the result
//     * @param query  the query to invoke, not null
//     * @return the query result, null may be returned (defined by the query)
//     * @throws DateTimeException if unable to query (defined by the query)
//     * @throws ArithmeticException if numeric overflow occurs (defined by the query)
//     */
//    @SuppressWarnings("unchecked")
//    @Override  // override for Javadoc
//    public <R> R query(TemporalQuery<R> query) {
//        if (query == TemporalQueries.localDate()) {
//            return (R) toLocalDate();
//        }
//        return ChronoZonedDateTime.super.query(query);
//    }
//
//    /**
//     * Calculates the amount of time until another date-time in terms of the specified unit.
//     * <p>
//     * This calculates the amount of time between two {@code ZonedDateTime}
//     * objects in terms of a single {@code TemporalUnit}.
//     * The start and end points are {@code this} and the specified date-time.
//     * The result will be negative if the end is before the start.
//     * For example, the amount in days between two date-times can be calculated
//     * using {@code startDateTime.until(endDateTime, DAYS)}.
//     * <p>
//     * The {@code Temporal} passed to this method is converted to a
//     * {@code ZonedDateTime} using {@link #from(TemporalAccessor)}.
//     * If the time-zone differs between the two zoned date-times, the specified
//     * end date-time is normalized to have the same zone as this date-time.
//     * <p>
//     * The calculation returns a whole number, representing the number of
//     * complete units between the two date-times.
//     * For example, the amount in months between 2012-06-15T00:00Z and 2012-08-14T23:59Z
//     * will only be one month as it is one minute short of two months.
//     * <p>
//     * There are two equivalent ways of using this method.
//     * The first is to invoke this method.
//     * The second is to use {@link TemporalUnit#between(Temporal, Temporal)}:
//     * <pre>
//     *   // these two lines are equivalent
//     *   amount = start.until(end, MONTHS);
//     *   amount = MONTHS.between(start, end);
//     * </pre>
//     * The choice should be made based on which makes the code more readable.
//     * <p>
//     * The calculation is implemented in this method for {@link ChronoUnit}.
//     * The units {@code NANOS}, {@code MICROS}, {@code MILLIS}, {@code SECONDS},
//     * {@code MINUTES}, {@code HOURS} and {@code HALF_DAYS}, {@code DAYS},
//     * {@code WEEKS}, {@code MONTHS}, {@code YEARS}, {@code DECADES},
//     * {@code CENTURIES}, {@code MILLENNIA} and {@code ERAS} are supported.
//     * Other {@code ChronoUnit} values will throw an exception.
//     * <p>
//     * The calculation for date and time units differ.
//     * <p>
//     * Date units operate on the local time-line, using the local date-time.
//     * For example, the period from noon on day 1 to noon the following day
//     * in days will always be counted as exactly one day, irrespective of whether
//     * there was a daylight savings change or not.
//     * <p>
//     * Time units operate on the instant time-line.
//     * The calculation effectively converts both zoned date-times to instants
//     * and then calculates the period between the instants.
//     * For example, the period from noon on day 1 to noon the following day
//     * in hours may be 23, 24 or 25 hours (or some other amount) depending on
//     * whether there was a daylight savings change or not.
//     * <p>
//     * If the unit is not a {@code ChronoUnit}, then the result of this method
//     * is obtained by invoking {@code TemporalUnit.between(Temporal, Temporal)}
//     * passing {@code this} as the first argument and the converted input temporal
//     * as the second argument.
//     * <p>
//     * This instance is immutable and unaffected by this method call.
//     *
//     * @param endExclusive  the end date, exclusive, which is converted to a {@code ZonedDateTime}, not null
//     * @param unit  the unit to measure the amount in, not null
//     * @return the amount of time between this date-time and the end date-time
//     * @throws DateTimeException if the amount cannot be calculated, or the end
//     *  temporal cannot be converted to a {@code ZonedDateTime}
//     * @throws UnsupportedTemporalTypeException if the unit is not supported
//     * @throws ArithmeticException if numeric overflow occurs
//     */
//    @Override
//    public long until(Temporal endExclusive, TemporalUnit unit) {
//        ZonedDateTime end = ZonedDateTime.from(endExclusive);
//        if (unit instanceof ChronoUnit) {
//            end = end.withZoneSameInstant(zone);
//            if (unit.isDateBased()) {
//                return dateTime.until(end.dateTime, unit);
//            } else {
//                return toOffsetDateTime().until(end.toOffsetDateTime(), unit);
//            }
//        }
//        return unit.between(this, end);
//    }
//
//    /**
//     * Formats this date-time using the specified formatter.
//     * <p>
//     * This date-time will be passed to the formatter to produce a string.
//     *
//     * @param formatter  the formatter to use, not null
//     * @return the formatted date-time string, not null
//     * @throws DateTimeException if an error occurs during printing
//     */
//    @Override  // override for Javadoc and performance
//    public String format(DateTimeFormatter formatter) {
//        Objects.requireNonNull(formatter, "formatter");
//        return formatter.format(this);
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Converts this date-time to an {@code OffsetDateTime}.
//     * <p>
//     * This creates an offset date-time using the local date-time and offset.
//     * The zone ID is ignored.
//     *
//     * @return an offset date-time representing the same local date-time and offset, not null
//     */
//    public OffsetDateTime toOffsetDateTime() {
//        return OffsetDateTime.of(dateTime, offset);
//    }
}
