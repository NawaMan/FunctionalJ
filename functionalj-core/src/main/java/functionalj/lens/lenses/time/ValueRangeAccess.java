package functionalj.lens.lenses.time;

import java.time.temporal.ValueRange;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.ConcreteAccess;

public interface ValueRangeAccess<HOST>
                    extends
                        AnyAccess<HOST, ValueRange>,
                        ConcreteAccess<HOST, ValueRange, ValueRangeAccess<HOST>> {
    
    public default ValueRangeAccess<HOST> newAccess(Function<HOST, ValueRange> accessToValue) {
        return host -> accessToValue.apply(host);
    }
    
    // TODO
//
//    //-----------------------------------------------------------------------
//    /**
//     * Is the value range fixed and fully known.
//     * <p>
//     * For example, the ISO day-of-month runs from 1 to between 28 and 31.
//     * Since there is uncertainty about the maximum value, the range is not fixed.
//     * However, for the month of January, the range is always 1 to 31, thus it is fixed.
//     *
//     * @return true if the set of values is fixed
//     */
//    public boolean isFixed() {
//        return minSmallest == minLargest && maxSmallest == maxLargest;
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Gets the minimum value that the field can take.
//     * <p>
//     * For example, the ISO day-of-month always starts at 1.
//     * The minimum is therefore 1.
//     *
//     * @return the minimum value for this field
//     */
//    public long getMinimum() {
//        return minSmallest;
//    }
//
//    /**
//     * Gets the largest possible minimum value that the field can take.
//     * <p>
//     * For example, the ISO day-of-month always starts at 1.
//     * The largest minimum is therefore 1.
//     *
//     * @return the largest possible minimum value for this field
//     */
//    public long getLargestMinimum() {
//        return minLargest;
//    }
//
//    /**
//     * Gets the smallest possible maximum value that the field can take.
//     * <p>
//     * For example, the ISO day-of-month runs to between 28 and 31 days.
//     * The smallest maximum is therefore 28.
//     *
//     * @return the smallest possible maximum value for this field
//     */
//    public long getSmallestMaximum() {
//        return maxSmallest;
//    }
//
//    /**
//     * Gets the maximum value that the field can take.
//     * <p>
//     * For example, the ISO day-of-month runs to between 28 and 31 days.
//     * The maximum is therefore 31.
//     *
//     * @return the maximum value for this field
//     */
//    public long getMaximum() {
//        return maxLargest;
//    }
//
//    //-----------------------------------------------------------------------
//    /**
//     * Checks if all values in the range fit in an {@code int}.
//     * <p>
//     * This checks that all valid values are within the bounds of an {@code int}.
//     * <p>
//     * For example, the ISO month-of-year has values from 1 to 12, which fits in an {@code int}.
//     * By comparison, ISO nano-of-day runs from 1 to 86,400,000,000,000 which does not fit in an {@code int}.
//     * <p>
//     * This implementation uses {@link #getMinimum()} and {@link #getMaximum()}.
//     *
//     * @return true if a valid value always fits in an {@code int}
//     */
//    public boolean isIntValue() {
//        return getMinimum() >= Integer.MIN_VALUE && getMaximum() <= Integer.MAX_VALUE;
//    }
//
//    /**
//     * Checks if the value is within the valid range.
//     * <p>
//     * This checks that the value is within the stored range of values.
//     *
//     * @param value  the value to check
//     * @return true if the value is valid
//     */
//    public boolean isValidValue(long value) {
//        return (value >= getMinimum() && value <= getMaximum());
//    }
//
//    /**
//     * Checks if the value is within the valid range and that all values
//     * in the range fit in an {@code int}.
//     * <p>
//     * This method combines {@link #isIntValue()} and {@link #isValidValue(long)}.
//     *
//     * @param value  the value to check
//     * @return true if the value is valid and fits in an {@code int}
//     */
//    public boolean isValidIntValue(long value) {
//        return isIntValue() && isValidValue(value);
//    }
//
//    /**
//     * Checks that the specified value is valid.
//     * <p>
//     * This validates that the value is within the valid range of values.
//     * The field is only used to improve the error message.
//     *
//     * @param value  the value to check
//     * @param field  the field being checked, may be null
//     * @return the value that was passed in
//     * @see #isValidValue(long)
//     */
//    public long checkValidValue(long value, TemporalField field) {
//        if (isValidValue(value) == false) {
//            throw new DateTimeException(genInvalidFieldMessage(field, value));
//        }
//        return value;
//    }
//
//    /**
//     * Checks that the specified value is valid and fits in an {@code int}.
//     * <p>
//     * This validates that the value is within the valid range of values and that
//     * all valid values are within the bounds of an {@code int}.
//     * The field is only used to improve the error message.
//     *
//     * @param value  the value to check
//     * @param field  the field being checked, may be null
//     * @return the value that was passed in
//     * @see #isValidIntValue(long)
//     */
//    public int checkValidIntValue(long value, TemporalField field) {
//        if (isValidIntValue(value) == false) {
//            throw new DateTimeException(genInvalidFieldMessage(field, value));
//        }
//        return (int) value;
//    }
    
}
