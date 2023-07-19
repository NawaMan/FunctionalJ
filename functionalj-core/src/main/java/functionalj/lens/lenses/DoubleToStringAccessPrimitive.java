// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.DoubleFunction;

@FunctionalInterface
public interface DoubleToStringAccessPrimitive extends StringAccess<Double>, DoubleFunction<String> {
    
    public String applyDoubleToString(double host);
    
    public default String applyAsDouble(double host) {
        return applyDoubleToString(host);
    }
    
    public default String applyAsDouble(Double host) {
        return applyDoubleToString(host);
    }
    
    @Override
    public default String apply(double host) {
        return applyDoubleToString(host);
    }
    
    @Override
    public default String applyUnsafe(Double input) throws Exception {
        return applyAsDouble(input);
    }
    // 
    // public static double apply(DoubleAccess<Double> access, double value) {
    // val resValue
    // = (access instanceof DoubleToStringAccessPrimitive)
    // ? ((DoubleToStringAccessPrimitive)access).applyDoubleToDouble(value)
    // : access.applyAsDouble(value);
    // return resValue;
    // }
    // 
    // public default  asString() {
    // return stringAccess(
    // null,
    // any -> {
    // return any.toString();
    // });
    // }
    // 
    // public default DoubleAccessBoxed<Double> boxed() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue;
    // };
    // }
    // 
    // 
    // //-- Compare --
    // 
    // public default DoubleToBooleanAccessPrimitive that(DoublePredicate checker) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return checker.test(doubleValue);
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIs(Double value) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == value;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsNot(Double value) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != value;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIs(DoubleSupplier value) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == value.getAsDouble();
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleSupplier value) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != value.getAsDouble();
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIs(DoubleUnaryOperator value) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == value.applyAsDouble(host);
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsNot(DoubleUnaryOperator value) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != value.applyAsDouble(host);
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsOne() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == 1.0;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsZero() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == 0.0;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsMinusOne() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == -1.0;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsFourtyTwo() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == 42.0;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsNotOne() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != 1.0;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsNotZero() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != 0.0;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsNotMinusOne() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != -1.0;
    // };
    // }
    // 
    // public default DoubleToIntegerAccessPrimitive toInteger() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (int)doubleValue;
    // };
    // }
    // 
    // public default DoubleToLongAccessPrimitive toLong() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (long)doubleValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive toDouble() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (double)doubleValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive toZero() {
    // return host -> 0;
    // }
    // 
    // public default DoubleToStringAccessPrimitive toOne() {
    // return host -> 1;
    // }
    // 
    // public default DoubleToStringAccessPrimitive toMinusOne() {
    // return host -> -1;
    // }
    // 
    // public default DoubleToStringAccessPrimitive abs() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (doubleValue < 0) ? -doubleValue : doubleValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive negate() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return -doubleValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive signum() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (doubleValue == 0) ? 0 : (doubleValue < 0) ? -1 : 1;
    // };
    // }
    // 
    // 
    // public default DoubleToIntegerAccessPrimitive compareTo(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // int    compare     = Double.compare(doubleValue, anotherValue);
    // return compare;
    // };
    // }
    // public default DoubleToIntegerAccessPrimitive compareTo(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue  = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // int    compare      = Double.compare(doubleValue, anotherValue);
    // return compare;
    // };
    // }
    // public default DoubleToIntegerAccessPrimitive compareTo(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue  = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // int    compare      = Double.compare(doubleValue, anotherValue);
    // return compare;
    // };
    // }
    // public default DoubleToIntegerAccessPrimitive compareTo(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue  = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // int    compare      = Double.compare(doubleValue, anotherValue);
    // return compare;
    // };
    // }
    // 
    // public default DoubleToIntegerAccessPrimitive cmp(double anotherValue) {
    // return compareTo(anotherValue);
    // }
    // public default DoubleToIntegerAccessPrimitive cmp(DoubleSupplier anotherSupplier) {
    // return compareTo(anotherSupplier);
    // }
    // public default DoubleToIntegerAccessPrimitive cmp(DoubleAccess<Double> anotherAccess) {
    // return compareTo(anotherAccess);
    // }
    // public default DoubleToIntegerAccessPrimitive cmp(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return compareTo(anotherFunction);
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatEquals(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue == anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatEquals(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // return doubleValue == anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatEquals(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue == anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatEquals(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue == anotherValue;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive eq(double anotherValue) {
    // return thatEquals(anotherValue);
    // }
    // public default DoubleToBooleanAccessPrimitive eq(DoubleSupplier anotherSupplier) {
    // return thatEquals(anotherSupplier);
    // }
    // public default DoubleToBooleanAccessPrimitive eq(DoubleAccess<Double> anotherAccess) {
    // return thatEquals(anotherAccess);
    // }
    // public default DoubleToBooleanAccessPrimitive eq(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return thatEquals(anotherFunction);
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatNotEquals(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue != anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatNotEquals(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // return doubleValue != anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatNotEquals(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue != anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatNotEquals(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue != anotherValue;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive neq(double anotherValue) {
    // return thatNotEquals(anotherValue);
    // }
    // public default DoubleToBooleanAccessPrimitive neq(DoubleSupplier anotherSupplier) {
    // return thatNotEquals(anotherSupplier);
    // }
    // public default DoubleToBooleanAccessPrimitive neq(DoubleAccess<Double> anotherAccess) {
    // return thatNotEquals(anotherAccess);
    // }
    // public default DoubleToBooleanAccessPrimitive neq(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return thatNotEquals(anotherFunction);
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatGreaterThan(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue > anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // return doubleValue > anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatGreaterThan(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue > anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatGreaterThan(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue > anotherValue;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive gt(double anotherValue) {
    // return thatGreaterThan(anotherValue);
    // }
    // public default DoubleToBooleanAccessPrimitive gt(DoubleSupplier anotherSupplier) {
    // return thatGreaterThan(anotherSupplier);
    // }
    // public default DoubleToBooleanAccessPrimitive gt(DoubleAccess<Double> anotherAccess) {
    // return thatGreaterThan(anotherAccess);
    // }
    // public default DoubleToBooleanAccessPrimitive gt(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return thatGreaterThan(anotherFunction);
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatLessThan(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue < anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // return doubleValue < anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatLessThan(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue < anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatLessThan(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue < anotherValue;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive lt(double anotherValue) {
    // return thatLessThan(anotherValue);
    // }
    // public default DoubleToBooleanAccessPrimitive lt(DoubleSupplier anotherSupplier) {
    // return thatLessThan(anotherSupplier);
    // }
    // public default DoubleToBooleanAccessPrimitive lt(DoubleAccess<Double> anotherAccess) {
    // return thatLessThan(anotherAccess);
    // }
    // public default DoubleToBooleanAccessPrimitive lt(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return thatLessThan(anotherFunction);
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue >= anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // return doubleValue >= anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue >= anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatGreaterThanOrEqualsTo(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue >= anotherValue;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive gteq(double anotherValue) {
    // return thatGreaterThanOrEqualsTo(anotherValue);
    // }
    // public default DoubleToBooleanAccessPrimitive gteq(DoubleSupplier anotherSupplier) {
    // return thatGreaterThanOrEqualsTo(anotherSupplier);
    // }
    // public default DoubleToBooleanAccessPrimitive gteq(DoubleAccess<Double> anotherAccess) {
    // return thatGreaterThanOrEqualsTo(anotherAccess);
    // }
    // public default DoubleToBooleanAccessPrimitive gteq(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return thatGreaterThanOrEqualsTo(anotherFunction);
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(double anotherValue) {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue <= anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleSupplier anotherSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherSupplier.getAsDouble();
    // return doubleValue <= anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue <= anotherValue;
    // };
    // }
    // public default DoubleToBooleanAccessPrimitive thatLessThanOrEqualsTo(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue <= anotherValue;
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive lteq(double anotherValue) {
    // return thatLessThanOrEqualsTo(anotherValue);
    // }
    // public default DoubleToBooleanAccessPrimitive lteq(DoubleSupplier anotherSupplier) {
    // return thatLessThanOrEqualsTo(anotherSupplier);
    // }
    // public default DoubleToBooleanAccessPrimitive lteq(DoubleAccess<Double> anotherAccess) {
    // return thatLessThanOrEqualsTo(anotherAccess);
    // }
    // public default DoubleToBooleanAccessPrimitive lteq(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return thatLessThanOrEqualsTo(anotherFunction);
    // }
    // 
    // public default DoubleToIntegerAccessPrimitive roundToInt() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (int)Math.round(doubleValue);
    // };
    // }
    // 
    // public default DoubleToLongAccessPrimitive roundToLong() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return Math.round(doubleValue);
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive round() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (double)Math.round(doubleValue);
    // };
    // }
    // 
    // public default DoubleToLongAccessPrimitive ceil() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (long)Math.ceil(doubleValue);
    // };
    // }
    // 
    // public default DoubleToLongAccessPrimitive floor() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return (long)Math.floor(doubleValue);
    // };
    // }
    // 
    // public default DoubleToBooleanAccessPrimitive thatIsRound() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return 1.0*Math.round(doubleValue) == doubleValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive plus(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return doubleValue + anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive plus(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return doubleValue + anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive plus(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue + anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive plus(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue + anotherValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive minus(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return doubleValue - anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive minus(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return doubleValue - anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive minus(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue - anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive minus(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue - anotherValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive time(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return doubleValue * anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive time(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return doubleValue * anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive time(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue * anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive time(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue * anotherValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive dividedBy(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return 1.0 * doubleValue / anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive dividedBy(DoubleSupplier anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = anotherAccess.getAsDouble();
    // return 1.0*doubleValue / anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive dividedBy(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return 1.0*doubleValue / anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive dividedBy(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return 1.0*doubleValue / anotherValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive remainderBy(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return doubleValue % anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive remainderBy(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return doubleValue % anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive remainderBy(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return doubleValue % anotherValue;
    // };
    // }
    // public default DoubleToStringAccessPrimitive remainderBy(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return doubleValue % anotherValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive square() {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return doubleValue * doubleValue;
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive squareRoot () {
    // return host -> {
    // double doubleValue = applyAsDouble(host);
    // return Math.sqrt(doubleValue);
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive pow(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return Math.pow(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive pow(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return Math.pow(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive pow(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return Math.pow(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive pow(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return Math.pow(doubleValue, anotherValue);
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive min(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return Math.min(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive min(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return Math.min(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive min(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return Math.min(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive min(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return Math.min(doubleValue, anotherValue);
    // };
    // }
    // 
    // public default DoubleToStringAccessPrimitive max(double value) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = value;
    // return Math.max(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive max(DoubleSupplier valueSupplier) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = valueSupplier.getAsDouble();
    // return Math.max(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive max(DoubleAccess<Double> anotherAccess) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleToStringAccessPrimitive.apply(anotherAccess, host);
    // return Math.max(doubleValue, anotherValue);
    // };
    // }
    // public default DoubleToStringAccessPrimitive max(ToDoubleBiDoubleFunction<Double> anotherFunction) {
    // return host -> {
    // double doubleValue    = applyAsDouble(host);
    // double anotherValue = DoubleBiFunctionPrimitive.apply(anotherFunction, host, doubleValue);
    // return Math.max(doubleValue, anotherValue);
    // };
    // }
    // 
}
