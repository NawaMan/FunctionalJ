// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.tuple;

import java.util.Objects;

import functionalj.function.Func0;
import functionalj.function.Func1;

/**
 * Represents an immutable 10-element tuple, providing a way to store ten different values of possibly differing types.
 * This class implements {@link Tuple10}, ensuring that the values cannot be modified after creation.
 * 
 * @param <T1>   the type of the first element in the tuple
 * @param <T2>   the type of the second element in the tuple
 * @param <T3>   the type of the third element in the tuple
 * @param <T4>   the type of the fourth element in the tuple
 * @param <T5>   the type of the fifth element in the tuple
 * @param <T6>   the type of the sixth element in the tuple
 * @param <T7>   the type of the seventh element in the tuple
 * @param <T8>   the type of the eighth element in the tuple
 * @param <T9>   the type of the ninth element in the tuple
 * @param <T10>  the type of the tenth element in the tuple
 */
public class ImmutableTuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> implements Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    
    /** The first element of the tuple. */
    public final T1 _1;
    
    /** The second element of the tuple. */
    public final T2 _2;
    
    /** The third element of the tuple. */
    public final T3 _3;
    
    /** The fourth element of the tuple. */
    public final T4 _4;
    
    /** The fifth element of the tuple. */
    public final T5 _5;
    
    /** The sixth element of the tuple. */
    public final T6 _6;
    
    /** The seventh element of the tuple. */
    public final T7 _7;
    
    /** The eighth element of the tuple. */
    public final T8 _8;
    
    /** The ninth element of the tuple. */
    public final T9 _9;
    
    /** The tenth element of the tuple. */
    public final T10 _10;
    
    /**
     * Constructs an immutable 10-element tuple with the specified values.
     * Each parameter corresponds to an element in the tuple, with its position in the parameter list 
     * reflecting its position in the tuple.
     *
     * @param _1   the value of the first element
     * @param _2   the value of the second element
     * @param _3   the value of the third element
     * @param _4   the value of the fourth element
     * @param _5   the value of the fifth element
     * @param _6   the value of the sixth element
     * @param _7   the value of the seventh element
     * @param _8   the value of the eighth element
     * @param _9   the value of the ninth element
     * @param _10  the value of the tenth element
     */
    public ImmutableTuple10(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
        this._1  = _1;
        this._2  = _2;
        this._3  = _3;
        this._4  = _4;
        this._5  = _5;
        this._6  = _6;
        this._7  = _7;
        this._8  = _8;
        this._9  = _9;
        this._10 = _10;
    }
    
    @Override
    public T1 _1() {
        return _1;
    }
    
    @Override
    public T2 _2() {
        return _2;
    }
    
    @Override
    public T3 _3() {
        return _3;
    }
    
    @Override
    public T4 _4() {
        return _4;
    }
    
    @Override
    public T5 _5() {
        return _5;
    }
    
    @Override
    public T6 _6() {
        return _6;
    }
    
    @Override
    public T7 _7() {
        return _7;
    }
    
    @Override
    public T8 _8() {
        return _8;
    }
    
    @Override
    public T9 _9() {
        return _9;
    }
    
    @Override
    public T10 _10() {
        return _10;
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with1(T1 new1) {
        return new ImmutableTuple10<>(new1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with1(Func0<T1> supplier1) {
        return new ImmutableTuple10<>(supplier1.get(), _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with1(Func1<T1, T1> function1) {
        return new ImmutableTuple10<>(function1.apply(_1()), _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with2(T2 new2) {
        return new ImmutableTuple10<>(_1, new2, _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with2(Func0<T2> supplier2) {
        return new ImmutableTuple10<>(_1, supplier2.get(), _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with2(Func1<T2, T2> function2) {
        return new ImmutableTuple10<>(_1, function2.apply(_2()), _3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with3(T3 new3) {
        return new ImmutableTuple10<>(_1, _2, new3, _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with3(Func0<T3> supplier3) {
        return new ImmutableTuple10<>(_1, _2, supplier3.get(), _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with3(Func1<T3, T3> function3) {
        return new ImmutableTuple10<>(_1, _2, function3.apply(_3()), _4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with4(T4 new4) {
        return new ImmutableTuple10<>(_1, _2, _3, new4, _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with4(Func0<T4> supplier4) {
        return new ImmutableTuple10<>(_1, _2, _3, supplier4.get(), _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with4(Func1<T4, T4> function4) {
        return new ImmutableTuple10<>(_1, _2, _3, function4.apply(_4()), _5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with5(T5 new5) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, new5, _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with5(Func0<T5> supplier5) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, supplier5.get(), _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with5(Func1<T5, T5> function5) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, function5.apply(_5()), _6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with6(T6 new6) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, new6, _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with6(Func0<T6> supplier6) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, supplier6.get(), _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with6(Func1<T6, T6> function6) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, function6.apply(_6()), _7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with7(T7 new7) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, new7, _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with7(Func0<T7> supplier7) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, supplier7.get(), _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with7(Func1<T7, T7> function7) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, function7.apply(_7()), _8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with8(T8 new8) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, new8, _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with8(Func0<T8> supplier8) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, supplier8.get(), _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with8(Func1<T8, T8> function8) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, function8.apply(_8()), _9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with9(T9 new9) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, new9, _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with9(Func0<T9> supplier9) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, supplier9.get(), _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with9(Func1<T9, T9> function9) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, function9.apply(_9()), _10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with10(T10 new10) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, new10);
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with10(Func0<T10> supplier10) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, supplier10.get());
    }
    
    @Override
    public Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> with10(Func1<T10, T10> function10) {
        return new ImmutableTuple10<>(_1, _2, _3, _4, _5, _6, _7, _8, _9, function10.apply(_10()));
    }
    
    
    @Override
    public String toString() {
        return "(" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + "," + _6 + "," + _7 + "," + _8 + "," + _9 + "," + _10 + ")";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_1  == null) ? 0 :  _1.hashCode());
        result = prime * result + ((_2  == null) ? 0 :  _2.hashCode());
        result = prime * result + ((_3  == null) ? 0 :  _3.hashCode());
        result = prime * result + ((_4  == null) ? 0 :  _4.hashCode());
        result = prime * result + ((_5  == null) ? 0 :  _5.hashCode());
        result = prime * result + ((_6  == null) ? 0 :  _6.hashCode());
        result = prime * result + ((_7  == null) ? 0 :  _7.hashCode());
        result = prime * result + ((_8  == null) ? 0 :  _8.hashCode());
        result = prime * result + ((_9  == null) ? 0 :  _9.hashCode());
        result = prime * result + ((_10 == null) ? 0 : _10.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Tuple10))
            return false;
        @SuppressWarnings("rawtypes")
        Tuple10 other = (Tuple10) obj;
        if (!Objects.equals(_1, other._1()))
            return false;
        if (!Objects.equals(_2, other._2()))
            return false;
        if (!Objects.equals(_3, other._3()))
            return false;
        if (!Objects.equals(_4, other._4()))
            return false;
        if (!Objects.equals(_5, other._5()))
            return false;
        if (!Objects.equals(_6, other._6()))
            return false;
        if (!Objects.equals(_7, other._7()))
            return false;
        if (!Objects.equals(_8, other._8()))
            return false;
        if (!Objects.equals(_9, other._9()))
            return false;
        if (!Objects.equals(_10, other._10()))
            return false;
        return true;
    }
    
}
