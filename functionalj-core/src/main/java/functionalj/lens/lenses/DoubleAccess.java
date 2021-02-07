// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.lens.lenses;

import static java.util.Objects.requireNonNull;

import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

import functionalj.function.Func1;
import functionalj.function.ToDoubleBiDoubleFunction;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.ref.Ref;
import lombok.val;


/**
 * Classes implementing this interface know how to access to a double value.
 **/
public interface DoubleAccess<HOST> 
                    extends 
                        NumberAccess<HOST, Double, DoubleAccess<HOST>>, 
                        ToDoubleFunction<HOST>, 
                        ConcreteAccess<HOST, Double, DoubleAccess<HOST>> {
    
    /** The reference to a function to calculate factorial for integer. **/
    public static final Ref<Double> equalPrecision = Ref.ofValue(0.0).whenAbsentUse(0.0);
    
    public static final Ref<DoubleSupplier> equalPrecisionToUse 
            = Ref.<DoubleSupplier>dictactedTo(() -> Math.abs(DoubleAccess.equalPrecision.get()));
    
    //== Constructor ==
    
    public static <H> DoubleAccess<H> of(Function<H, Double> accessToValue) {
        requireNonNull(accessToValue);
        
        if (accessToValue instanceof DoubleAccess) {
            return (DoubleAccess<H>)accessToValue;
        }
        
        if (accessToValue instanceof ToDoubleFunction) {
            @SuppressWarnings("unchecked")
            val func1  = (ToDoubleFunction<H>)accessToValue;
            val access = ofPrimitive(func1);
            return access;
        }
        
        if (accessToValue instanceof Func1) {
            val func1  = (Func1<H, Double>)accessToValue;
            val access = (DoubleAccessBoxed<H>)func1::applyUnsafe;
            return access;
        }
        
        val func   = (Function<H, Double>)accessToValue;
        val access = (DoubleAccessBoxed<H>)(host -> func.apply(host));
        return access;
    }
    
    public static <H> DoubleAccess<H> ofPrimitive(ToDoubleFunction<H> accessToValue) {
        requireNonNull(accessToValue);
        val access = (DoubleAccessPrimitive<H>)accessToValue::applyAsDouble;
        return access;
    }
    
    //== abstract functionalities ==
    
    public double applyAsDouble(HOST host);
    
    
    public Double applyUnsafe(HOST host) throws Exception;
    
    //-- create --
    
    @Override
    public default DoubleAccess<HOST> newAccess(Function<HOST, Double> accessToValue) {
        return of(accessToValue);
    }
    
    //-- conversion --
    
    public default DoubleAccessBoxed<HOST> boxed() {
        return host -> apply(host);
    }
    
    @Override
    public default IntegerAccessPrimitive<HOST> asInteger() {
        return asInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    @Override
    public default LongAccessPrimitive<HOST> asLong() {
        return asLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    @Override
    public default DoubleAccessPrimitive<HOST> asDouble() {
        return host -> applyAsDouble(host);
    }
    
    public default IntegerAccessPrimitive<HOST> asInteger(int overflowValue) {
        return asInteger(overflowValue, overflowValue);
    }
    
    public default LongAccessPrimitive<HOST> asLong(long overflowValue) {
        return asLong(overflowValue, overflowValue);
    }
    
    public default IntegerAccessPrimitive<HOST> asInteger(int negativeOverflowValue, int positiveOverflowValue) {
        return host -> {
            val value = applyAsDouble(host);
            if (value < Integer.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Integer.MAX_VALUE)
                return positiveOverflowValue;
            
            return (int)value;
        };
    }
    
    public default LongAccessPrimitive<HOST> asLong(long negativeOverflowValue, long positiveOverflowValue) {
        return host -> {
            val value = applyAsDouble(host);
            if (value < Long.MIN_VALUE)
                return negativeOverflowValue;
            
            if (value > Long.MAX_VALUE)
                return positiveOverflowValue;
            
            return (long)value;
        };
    }
    
    public default IntegerAccessBoxed<HOST> asIntegerOrNull(Integer negativeOverflowValue, Integer positiveOverflowValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            if (doubleValue < Integer.MIN_VALUE)
                return negativeOverflowValue;
            
            if (doubleValue > Integer.MIN_VALUE)
                return positiveOverflowValue;
            
            return (int)doubleValue;
        };
    }
    
    public default LongAccessBoxed<HOST> asLongOrNull(Long negativeOverflowValue, Long positiveOverflowValue) {
        return host -> {
            double doubleValue = applyAsDouble(host);
            if (doubleValue < Long.MIN_VALUE)
                return negativeOverflowValue;
            
            if (doubleValue > Long.MIN_VALUE)
                return positiveOverflowValue;
            
            return (long)doubleValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> round() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.round(value);
        };
    }
    public default IntegerAccessPrimitive<HOST> roundToInt() {
        return round().asInteger();
    }
    
    public default LongAccessPrimitive<HOST> roundToLong() {
        return round().asLong();
    }
    
    public default DoubleAccessPrimitive<HOST> ceil() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.ceil(value);
        };
    }
    
    public default IntegerAccessPrimitive<HOST> ceilToInt() {
        return round().asInteger();
    }
    
    public default LongAccessPrimitive<HOST> ceilToLong() {
        return round().asLong();
    }
    
    public default DoubleAccessPrimitive<HOST> floor() {
        return host -> {
            val value = applyAsDouble(host);
            return Math.floor(value);
        };
    }
    public default IntegerAccessPrimitive<HOST> floorToInt() {
        return floor().asInteger();
    }
    public default LongAccessPrimitive<HOST> floorToLong() {
        return floor().asLong();
    }
    
    public default DoubleAccessPrimitive<HOST> roundBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default DoubleAccessPrimitive<HOST> roundBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    public default DoubleAccessPrimitive<HOST> roundBy(ToDoubleBiDoubleFunction<HOST> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.round(value);
            }
            
            return Math.round(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> ceilBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default DoubleAccessPrimitive<HOST> ceilBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    public default DoubleAccessPrimitive<HOST> ceilBy(ToDoubleBiDoubleFunction<HOST> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.ceil(value);
            }
            
            return Math.ceil(value / precision) * precision;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> floorBy(double precision) {
        return host -> {
            val value = applyAsDouble(host);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default DoubleAccessPrimitive<HOST> floorBy(DoubleSupplier precisionSupplier) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionSupplier.getAsDouble();
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    public default DoubleAccessPrimitive<HOST> floorBy(ToDoubleBiDoubleFunction<HOST> precisionFunction) {
        return host -> {
            val value     = applyAsDouble(host);
            val precision = precisionFunction.applyAsDouble(host, value);
            if (precision == 0.0) {
                return Math.floor(value);
            }
            
            return Math.floor(value / precision) * precision;
        };
    }
    
    public default StringAccess<HOST> asString() {
        return host -> "" + applyAsDouble(host);
    }
    public default StringAccess<HOST> asString(String template) {
        return host -> {
            val value = applyAsDouble(host);
            return String.format(template, value);
        };
    }
    
    // TODO - Find a better way to format this that allow a fix width disregards of the magnitude of the value.
    //          or just redirect the format to another function that can be substituted.
    
    //-- to value --
    
    public default DoubleAccessPrimitive<HOST> toZero() {
        return host -> 0.0;
    }
    
    public default DoubleAccessPrimitive<HOST> toOne() {
        return host -> 1.0;
    }
    
    public default DoubleAccessPrimitive<HOST> toMinusOne() {
        return host -> -1.0;
    }
    
    public default DoubleAccessPrimitive<HOST> to(double anotherValue) {
        return host -> {
            val value   = applyAsDouble(host);
            val compare = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleAccessPrimitive<HOST> to(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleAccessPrimitive<HOST> to(ToDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default DoubleAccessPrimitive<HOST> to(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    
    //-- Equality --
    
    public default BooleanAccessPrimitive<HOST> that(DoublePredicate checker) {
        return host -> {
            val value = applyAsDouble(host);
            return checker.test(value);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIs(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value == anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIs(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value == anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNot(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value != anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNot(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value != anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(double ... otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return true;
                }
            }
            return false;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsAnyOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            return otherValues.anyMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(double ... otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            for (val anotherValue : otherValues) {
                if (value == anotherValue) {
                    return false;
                }
            }
            return true;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNoneOf(DoubleFuncList otherValues) {
        return host -> {
            val value = applyAsDouble(host);
            return otherValues.noneMatch(anotherValue -> value == anotherValue);
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsOne() {
        return thatIs(1);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsZero() {
        return thatIs(0.0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsMinusOne() {
        return thatIs(-1.0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsFourtyTwo() {
        return thatIs(42.0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotOne() {
        return thatIsNot(1.0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotZero() {
        return thatIsNot(0.0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsNotMinusOne() {
        return thatIsNot(-1.0);
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsPositive() {
        return host -> {
            val value = applyAsDouble(host);
            return value > 0.0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNegative() {
        return host -> {
            val value = applyAsDouble(host);
            return value < 0.0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotPositive() {
        return host -> {
            val value = applyAsDouble(host);
            return value <= 0.0;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatIsNotNegative() {
        return host -> {
            val value = applyAsDouble(host);
            return value >= 0.0;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> thatIsRound() {
        return host -> {
            val value = applyAsDouble(host);
            return 1.0*Math.round(value) == value;
        };
    }
    
    public default DoubleAccessEqual<HOST> thatEquals(double anotherValue) {
        return new DoubleAccessEqual<>(false, this, (host, value) -> anotherValue);
    }
    public default DoubleAccessEqual<HOST> thatEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqual<>(false, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default DoubleAccessEqual<HOST> thatEquals(ToDoubleFunction<HOST> anotherAccess) {
        return new DoubleAccessEqual<>(false, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    public default DoubleAccessEqual<HOST> thatEquals(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return new DoubleAccessEqual<>(false, this, anotherFunction);
    }
    
    public default DoubleAccessEqual<HOST> eq(double anotherValue) {
        return thatEquals(anotherValue);
    }
    public default DoubleAccessEqual<HOST> eq(DoubleSupplier anotherSupplier) {
        return thatEquals(anotherSupplier);
    }
    public default DoubleAccessEqual<HOST> eq(ToDoubleFunction<HOST> anotherAccess) {
        return thatEquals(anotherAccess);
    }
    public default DoubleAccessEqual<HOST> eq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatEquals(anotherFunction);
    }
    
    public default DoubleAccessEqual<HOST> thatNotEquals(double anotherValue) {
        return new DoubleAccessEqual<>(true, this, (host, value) -> anotherValue);
    }
    public default DoubleAccessEqual<HOST> thatNotEquals(DoubleSupplier anotherSupplier) {
        return new DoubleAccessEqual<>(true, this, (host, value) -> anotherSupplier.getAsDouble());
    }
    public default DoubleAccessEqual<HOST> thatNotEquals(ToDoubleFunction<HOST> anotherAccess) {
        return new DoubleAccessEqual<>(true, this, (host, value) -> anotherAccess.applyAsDouble(host));
    }
    public default DoubleAccessEqual<HOST> thatNotEquals(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return new DoubleAccessEqual<>(true, this, anotherFunction);
    }
    
    public default DoubleAccessEqual<HOST> neq(double anotherValue) {
        return thatNotEquals(anotherValue);
    }
    public default DoubleAccessEqual<HOST> neq(DoubleSupplier anotherSupplier) {
        return thatNotEquals(anotherSupplier);
    }
    public default DoubleAccessEqual<HOST> neq(ToDoubleFunction<HOST> anotherAccess) {
        return thatNotEquals(anotherAccess);
    }
    public default DoubleAccessEqual<HOST> neq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatNotEquals(anotherFunction);
    }
    
    //-- Compare --
    
    public default DoubleComparator ascendingOrder() {
        return (a, b) -> Double.compare(a, b);
    }
    
    public default DoubleComparator descendingOrder() {
        return (a, b) -> Double.compare(b, a);
    }
    
    public default IntegerAccessPrimitive<HOST> compareTo(double anotherValue) {
        return host -> {
            val value   = applyAsDouble(host);
            val compare = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    public default IntegerAccessPrimitive<HOST> compareTo(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            val compare      = Double.compare(value, anotherValue);
            return compare;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> cmp(double anotherValue) {
        return compareTo(anotherValue);
    }
    public default IntegerAccessPrimitive<HOST> cmp(DoubleSupplier anotherSupplier) {
        return compareTo(anotherSupplier);
    }
    public default IntegerAccessPrimitive<HOST> cmp(ToDoubleFunction<HOST> anotherAccess) {
        return compareTo(anotherAccess);
    }
    public default IntegerAccessPrimitive<HOST> cmp(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return compareTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value > anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThan(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value > anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gt(double anotherValue) {
        return thatGreaterThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gt(DoubleSupplier anotherSupplier) {
        return thatGreaterThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToDoubleFunction<HOST> anotherAccess) {
        return thatGreaterThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gt(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatGreaterThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThan(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value < anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThan(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value < anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lt(double anotherValue) {
        return thatLessThan(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lt(DoubleSupplier anotherSupplier) {
        return thatLessThan(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToDoubleFunction<HOST> anotherAccess) {
        return thatLessThan(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lt(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatLessThan(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value >= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatGreaterThanOrEqualsTo(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value >= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> gteq(double anotherValue) {
        return thatGreaterThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> gteq(DoubleSupplier anotherSupplier) {
        return thatGreaterThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToDoubleFunction<HOST> anotherAccess) {
        return thatGreaterThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> gteq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatGreaterThanOrEqualsTo(anotherFunction);
    }
    
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherSupplier.getAsDouble();
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToDoubleFunction<HOST> anotherAccess) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherAccess.applyAsDouble(host);
            return value <= anotherValue;
        };
    }
    public default BooleanAccessPrimitive<HOST> thatLessThanOrEqualsTo(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = anotherFunction.applyAsDouble(host, value);
            return value <= anotherValue;
        };
    }
    
    public default BooleanAccessPrimitive<HOST> lteq(double anotherValue) {
        return thatLessThanOrEqualsTo(anotherValue);
    }
    public default BooleanAccessPrimitive<HOST> lteq(DoubleSupplier anotherSupplier) {
        return thatLessThanOrEqualsTo(anotherSupplier);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToDoubleFunction<HOST> anotherAccess) {
        return thatLessThanOrEqualsTo(anotherAccess);
    }
    public default BooleanAccessPrimitive<HOST> lteq(ToDoubleBiDoubleFunction<HOST> anotherFunction) {
        return thatLessThanOrEqualsTo(anotherFunction);
    }
    
    // TODO - See if the following is valid
    //-- digitAt
    //-- digitValueAt
    //-- factorValueAt
    //-- factorValueAt
    
    //-- Min+Max --
    
    public default DoubleAccessPrimitive<HOST> min(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> min(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> min(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return Math.min(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> min(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return Math.min(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> max(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> max(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> max(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return Math.max(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> max(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return Math.max(value, anotherValue);
        };
    }
    
    //-- Math --
    
    public default MathOperators<Double> __mathOperators() {
        return DoubleMathOperators.instance;
    }
    
    public default DoubleAccessPrimitive<HOST> abs() {
        return host -> {
            val value = applyAsDouble(host);
            return (value < 0) ? -value : value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> negate() {
        return host -> {
            val value = applyAsDouble(host);
            return -value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> signum() {
        return host -> {
            val value = applyAsDouble(host);
            return (value == 0) ? 0 : (value < 0) ? -1 : 1;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> plus(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value + anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> plus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value + anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> plus(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return value + anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> plus(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return value + anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> minus(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value - anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> minus(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value - anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> minus(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return value - anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> minus(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return value - anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> time(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return value * anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> time(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return value * anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> time(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return value * anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> time(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return value * anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> dividedBy(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return 1.0 * value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return 1.0*value / anotherValue;
        };
    }
    public default DoubleAccessPrimitive<HOST> dividedBy(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return 1.0 * value / anotherValue;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> remainderBy(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            val division = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> remainderBy(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            val division     = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> remainderBy(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            val division     = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> remainderBy(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            val division     = Math.round(value / anotherValue);
            return value - (division * anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> square() {
        return host -> {
            val value = applyAsDouble(host);
            return value * value;
        };
    }
    
    public default DoubleAccessPrimitive<HOST> squareRoot () {
        return host -> {
            val value = applyAsDouble(host);
            return Math.sqrt(value);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> pow(double anotherValue) {
        return host -> {
            val value = applyAsDouble(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(DoubleSupplier valueSupplier) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueSupplier.getAsDouble();
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host);
            return Math.pow(value, anotherValue);
        };
    }
    public default DoubleAccessPrimitive<HOST> pow(ToDoubleBiDoubleFunction<HOST> valueFunction) {
        return host -> {
            val value        = applyAsDouble(host);
            val anotherValue = valueFunction.applyAsDouble(host, value);
            return Math.pow(value, anotherValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> exp() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.exp(doubleValue);
        };
    }
    
    /**
     * Returns <i>e</i><sup>x</sup>&nbsp;-1.  Note that for values of
     * <i>x</i> near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of <i>e</i><sup>x</sup> than {@code exp(x)}.
     **/
    public default DoubleAccessPrimitive<HOST> expm1() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.expm1(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> log() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log(doubleValue);
        };
    }
    
    public default DoubleAccessPrimitive<HOST> log10() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log10(doubleValue);
        };
    }
    
    /**
     * Returns the base 10 logarithm of a {@code double} value.
     * Special cases:
     *
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.
     * <li> If the argument is equal to 10<sup><i>n</i></sup> for
     * integer <i>n</i>, then the result is <i>n</i>.
     * </ul>
     *
     * <p>The computed result must be within 1 ulp of the exact result.
     * Results must be semi-monotonic.
     */
    public default DoubleAccessPrimitive<HOST> log1p() {
        return host -> {
            double doubleValue = applyAsDouble(host);
            return Math.log1p(doubleValue);
        };
    }
    
    
    // TODO - Add more
//    Math.addExact((int)0, (int)0)
//    Math.addExact((long)0, (long)0)
//    Math.decrementExact((int)0)
//    Math.decrementExact((long)0)
//    Math.incrementExact((int)0)
//    Math.incrementExact((long)0)
//    Math.multiplyExact(int, int)
//    Math.multiplyExact(long, long)
//    Math.negateExact(int)
//    Math.negateExact(long)
//    Math.subtractExact(int, int)
//    Math.subtractExact(long, long)
//    Math.toIntExact(0)
    
    // TODO - Add more.
    
//    Math.acos(doubleValue)
//    Math.asin(doubleValue)
//    Math.tan(doubleValue)
//    Math.tan2(doubleValue)
//    Math.cos(doubleValue)
//    Math.cosh(doubleValue)
//    Math.sin(doubleValue)
//    Math.sinh(doubleValue)
//    Math.tan(doubleValue)
//    Math.tanh(doubleValue)
//    
//    Math.toDegrees(doubleValue)
//    Math.toRadians(doubleValue)
    
}
