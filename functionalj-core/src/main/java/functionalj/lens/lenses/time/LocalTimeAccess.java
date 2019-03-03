package functionalj.lens.lenses.time;

import java.time.LocalTime;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.BooleanAccess;
import functionalj.lens.lenses.ConcreteAccess;
import functionalj.lens.lenses.IntegerAccess;
import lombok.val;

public interface LocalTimeAccess<HOST>
                    extends
                        AnyAccess             <HOST, LocalTime>,
                        TemporalAccess        <HOST, LocalTime>,
                        TemporalAdjusterAccess<HOST, LocalTime>,
                        ConcreteAccess        <HOST, LocalTime, LocalTimeAccess<HOST>>  {
    
    public default LocalTimeAccess<HOST> newAccess(Function<HOST, LocalTime> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    // TODO
//    public int getHour() {
//        return hour;
//    }
//    public int getMinute() {
//        return minute;
//    }
//    public int getSecond() {
//        return second;
//    }
//    public int getNano() {
//        return nano;
//    }
//    public LocalTime with(TemporalAdjuster adjuster) {
//        // optimizations
//        if (adjuster instanceof LocalTime) {
//            return (LocalTime) adjuster;
//        }
//        return (LocalTime) adjuster.adjustInto(this);
//    }
//    public LocalTime with(TemporalField field, long newValue) {
//        if (field instanceof ChronoField) {
//            ChronoField f = (ChronoField) field;
//            f.checkValidValue(newValue);
//            switch (f) {
//                case NANO_OF_SECOND: return withNano((int) newValue);
//                case NANO_OF_DAY: return LocalTime.ofNanoOfDay(newValue);
//                case MICRO_OF_SECOND: return withNano((int) newValue * 1000);
//                case MICRO_OF_DAY: return LocalTime.ofNanoOfDay(newValue * 1000);
//                case MILLI_OF_SECOND: return withNano((int) newValue * 1000_000);
//                case MILLI_OF_DAY: return LocalTime.ofNanoOfDay(newValue * 1000_000);
//                case SECOND_OF_MINUTE: return withSecond((int) newValue);
//                case SECOND_OF_DAY: return plusSeconds(newValue - toSecondOfDay());
//                case MINUTE_OF_HOUR: return withMinute((int) newValue);
//                case MINUTE_OF_DAY: return plusMinutes(newValue - (hour * 60 + minute));
//                case HOUR_OF_AMPM: return plusHours(newValue - (hour % 12));
//                case CLOCK_HOUR_OF_AMPM: return plusHours((newValue == 12 ? 0 : newValue) - (hour % 12));
//                case HOUR_OF_DAY: return withHour((int) newValue);
//                case CLOCK_HOUR_OF_DAY: return withHour((int) (newValue == 24 ? 0 : newValue));
//                case AMPM_OF_DAY: return plusHours((newValue - (hour / 12)) * 12);
//            }
//            throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
//        }
//        return field.adjustInto(this, newValue);
//    }
//    public LocalTime withHour(int hour) {
//        if (this.hour == hour) {
//            return this;
//        }
//        HOUR_OF_DAY.checkValidValue(hour);
//        return create(hour, minute, second, nano);
//    }
//    public LocalTime withMinute(int minute) {
//        if (this.minute == minute) {
//            return this;
//        }
//        MINUTE_OF_HOUR.checkValidValue(minute);
//        return create(hour, minute, second, nano);
//    }
//    public LocalTime withSecond(int second) {
//        if (this.second == second) {
//            return this;
//        }
//        SECOND_OF_MINUTE.checkValidValue(second);
//        return create(hour, minute, second, nano);
//    }
//    public LocalTime withNano(int nanoOfSecond) {
//        if (this.nano == nanoOfSecond) {
//            return this;
//        }
//        NANO_OF_SECOND.checkValidValue(nanoOfSecond);
//        return create(hour, minute, second, nanoOfSecond);
//    }
//    public LocalTime truncatedTo(TemporalUnit unit) {
//        if (unit == ChronoUnit.NANOS) {
//            return this;
//        }
//        Duration unitDur = unit.getDuration();
//        if (unitDur.getSeconds() > SECONDS_PER_DAY) {
//            throw new UnsupportedTemporalTypeException("Unit is too large to be used for truncation");
//        }
//        long dur = unitDur.toNanos();
//        if ((NANOS_PER_DAY % dur) != 0) {
//            throw new UnsupportedTemporalTypeException("Unit must divide into a standard day without remainder");
//        }
//        long nod = toNanoOfDay();
//        return ofNanoOfDay((nod / dur) * dur);
//    }
//    public LocalTime plus(TemporalAmount amountToAdd) {
//        return (LocalTime) amountToAdd.addTo(this);
//    }
//    public LocalTime plus(long amountToAdd, TemporalUnit unit) {
//        if (unit instanceof ChronoUnit) {
//            switch ((ChronoUnit) unit) {
//                case NANOS: return plusNanos(amountToAdd);
//                case MICROS: return plusNanos((amountToAdd % MICROS_PER_DAY) * 1000);
//                case MILLIS: return plusNanos((amountToAdd % MILLIS_PER_DAY) * 1000_000);
//                case SECONDS: return plusSeconds(amountToAdd);
//                case MINUTES: return plusMinutes(amountToAdd);
//                case HOURS: return plusHours(amountToAdd);
//                case HALF_DAYS: return plusHours((amountToAdd % 2) * 12);
//            }
//            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
//        }
//        return unit.addTo(this, amountToAdd);
//    }
//    public LocalTime plusHours(long hoursToAdd) {
//        if (hoursToAdd == 0) {
//            return this;
//        }
//        int newHour = ((int) (hoursToAdd % HOURS_PER_DAY) + hour + HOURS_PER_DAY) % HOURS_PER_DAY;
//        return create(newHour, minute, second, nano);
//    }
//    public LocalTime plusMinutes(long minutesToAdd) {
//        if (minutesToAdd == 0) {
//            return this;
//        }
//        int mofd = hour * MINUTES_PER_HOUR + minute;
//        int newMofd = ((int) (minutesToAdd % MINUTES_PER_DAY) + mofd + MINUTES_PER_DAY) % MINUTES_PER_DAY;
//        if (mofd == newMofd) {
//            return this;
//        }
//        int newHour = newMofd / MINUTES_PER_HOUR;
//        int newMinute = newMofd % MINUTES_PER_HOUR;
//        return create(newHour, newMinute, second, nano);
//    }
//    public LocalTime plusSeconds(long secondstoAdd) {
//        if (secondstoAdd == 0) {
//            return this;
//        }
//        int sofd = hour * SECONDS_PER_HOUR +
//                    minute * SECONDS_PER_MINUTE + second;
//        int newSofd = ((int) (secondstoAdd % SECONDS_PER_DAY) + sofd + SECONDS_PER_DAY) % SECONDS_PER_DAY;
//        if (sofd == newSofd) {
//            return this;
//        }
//        int newHour = newSofd / SECONDS_PER_HOUR;
//        int newMinute = (newSofd / SECONDS_PER_MINUTE) % MINUTES_PER_HOUR;
//        int newSecond = newSofd % SECONDS_PER_MINUTE;
//        return create(newHour, newMinute, newSecond, nano);
//    }
//    public LocalTime plusNanos(long nanosToAdd) {
//        if (nanosToAdd == 0) {
//            return this;
//        }
//        long nofd = toNanoOfDay();
//        long newNofd = ((nanosToAdd % NANOS_PER_DAY) + nofd + NANOS_PER_DAY) % NANOS_PER_DAY;
//        if (nofd == newNofd) {
//            return this;
//        }
//        int newHour = (int) (newNofd / NANOS_PER_HOUR);
//        int newMinute = (int) ((newNofd / NANOS_PER_MINUTE) % MINUTES_PER_HOUR);
//        int newSecond = (int) ((newNofd / NANOS_PER_SECOND) % SECONDS_PER_MINUTE);
//        int newNano = (int) (newNofd % NANOS_PER_SECOND);
//        return create(newHour, newMinute, newSecond, newNano);
//    }
//    public LocalTime minus(TemporalAmount amountToSubtract) {
//        return (LocalTime) amountToSubtract.subtractFrom(this);
//    }
//    public LocalTime minus(long amountToSubtract, TemporalUnit unit) {
//        return (amountToSubtract == Long.MIN_VALUE ? plus(Long.MAX_VALUE, unit).plus(1, unit) : plus(-amountToSubtract, unit));
//    }
//    public LocalTime minusHours(long hoursToSubtract) {
//        return plusHours(-(hoursToSubtract % HOURS_PER_DAY));
//    }
//    public LocalTime minusMinutes(long minutesToSubtract) {
//        return plusMinutes(-(minutesToSubtract % MINUTES_PER_DAY));
//    }
//    public LocalTime minusSeconds(long secondsToSubtract) {
//        return plusSeconds(-(secondsToSubtract % SECONDS_PER_DAY));
//    }
//    public LocalTime minusNanos(long nanosToSubtract) {
//        return plusNanos(-(nanosToSubtract % NANOS_PER_DAY));
//    }
    
//    public LocalDateTime atDate(LocalDate date) {
//        return LocalDateTime.of(date, this);
//    }
//    public OffsetTime atOffset(ZoneOffset offset) {
//        return OffsetTime.of(this, offset);
//    }
//    public int toSecondOfDay() {
//        int total = hour * SECONDS_PER_HOUR;
//        total += minute * SECONDS_PER_MINUTE;
//        total += second;
//        return total;
//    }
//    public long toNanoOfDay() {
//        long total = hour * NANOS_PER_HOUR;
//        total += minute * NANOS_PER_MINUTE;
//        total += second * NANOS_PER_SECOND;
//        total += nano;
//        return total;
//    }
    
    public default IntegerAccess<HOST> compareTo(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.compareTo(other);
        };
    }
    // Duplicate the lt,lte,gt,gte as I am fail to make this extends ComparableAccess
    public default BooleanAccess<HOST> thatGreaterThan(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> thatLessThan(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> thatGreaterThanOrEqualsTo(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> thatLessThanOrEqualsTo(LocalTime anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
    public default BooleanAccess<HOST> thatIsAfter(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.isAfter(other);
        };
    }
    public default BooleanAccess<HOST> thatIsBefore(LocalTime other) {
        return host -> {
            val value = apply(host);
            return value.isBefore(other);
        };
    }
}
