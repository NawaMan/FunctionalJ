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
package functionalj.tuple;

import java.util.Objects;

import functionalj.function.Func0;
import functionalj.function.Func1;

/**
 * Represents an immutable 5-element tuple, providing a way to store seven different values of possibly differing types.
 * This class implements {@link Tuple5}, ensuring that the values cannot be modified after creation.
 * 
 * @param <T1>  the type of the first element in the tuple
 * @param <T2>  the type of the second element in the tuple
 * @param <T3>  the type of the third element in the tuple
 * @param <T4>  the type of the fourth element in the tuple
 * @param <T5>  the type of the fifth element in the tuple
 */
public class ImmutableTuple5<T1, T2, T3, T4, T5> implements Tuple5<T1, T2, T3, T4, T5> {
    
    /** The fisrt element of the tuple. */
    public final T1 _1;
    
    /** The second element of the tuple. */
    public final T2 _2;
    
    /** The third element of the tuple. */
    public final T3 _3;
    
    /** The fourth element of the tuple. */
    public final T4 _4;
    
    /** The fifth element of the tuple. */
    public final T5 _5;
    
    /**
     * Constructs an immutable 5-element tuple with the specified values.
     * Each parameter corresponds to an element in the tuple, with its position in the parameter list 
     * reflecting its position in the tuple.
     *
     * @param _1  the value of the first element
     * @param _2  the value of the second element
     * @param _3  the value of the third element
     * @param _4  the value of the fourth element
     * @param _5  the value of the fifth element
     */
    public ImmutableTuple5(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
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
    public Tuple5<T1, T2, T3, T4, T5> with1(T1 new1) {
        return new ImmutableTuple5<>(new1, _2, _3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with1(Func0<T1> supplier1) {
        return new ImmutableTuple5<>(supplier1.get(), _2, _3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with1(Func1<T1, T1> function1) {
        return new ImmutableTuple5<>(function1.apply(_1()), _2, _3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with2(T2 new2) {
        return new ImmutableTuple5<>(_1, new2, _3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with2(Func0<T2> supplier2) {
        return new ImmutableTuple5<>(_1, supplier2.get(), _3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with2(Func1<T2, T2> function2) {
        return new ImmutableTuple5<>(_1, function2.apply(_2()), _3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with3(T3 new3) {
        return new ImmutableTuple5<>(_1, _2, new3, _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with3(Func0<T3> supplier3) {
        return new ImmutableTuple5<>(_1, _2, supplier3.get(), _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with3(Func1<T3, T3> function3) {
        return new ImmutableTuple5<>(_1, _2, function3.apply(_3()), _4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with4(T4 new4) {
        return new ImmutableTuple5<>(_1, _2, _3, new4, _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with4(Func0<T4> supplier4) {
        return new ImmutableTuple5<>(_1, _2, _3, supplier4.get(), _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with4(Func1<T4, T4> function4) {
        return new ImmutableTuple5<>(_1, _2, _3, function4.apply(_4()), _5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with5(T5 new5) {
        return new ImmutableTuple5<>(_1, _2, _3, _4, new5);
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with5(Func0<T5> supplier5) {
        return new ImmutableTuple5<>(_1, _2, _3, _4, supplier5.get());
    }
    
    @Override
    public Tuple5<T1, T2, T3, T4, T5> with5(Func1<T5, T5> function5) {
        return new ImmutableTuple5<>(_1, _2, _3, _4, function5.apply(_5()));
    }
    
    
    @Override
    public String toString() {
        return "(" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + ")";
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
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Tuple9))
            return false;
        @SuppressWarnings("rawtypes")
        Tuple9 other = (Tuple9) obj;
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
        return true;
    }
}
