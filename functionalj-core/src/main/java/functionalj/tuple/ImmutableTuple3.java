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

import functionalj.function.Func0;
import functionalj.function.Func1;

/**
 * Represents an immutable 3-element tuple, providing a way to store seven different values of possibly differing types.
 * This class implements {@link Tuple3}, ensuring that the values cannot be modified after creation.
 * 
 * @param <T1>  the type of the first element in the tuple
 * @param <T2>  the type of the second element in the tuple
 * @param <T3>  the type of the third element in the tuple
 */
public class ImmutableTuple3<T1, T2, T3> implements Tuple3<T1, T2, T3> {
    
    /** The first element of the tuple. */
    public final T1 _1;
    
    /** The second element of the tuple. */
    public final T2 _2;
    
    /** The third element of the tuple. */
    public final T3 _3;
    
    /**
     * Constructs an immutable 3-element tuple with the specified values.
     * Each parameter corresponds to an element in the tuple, with its position in the parameter list 
     * reflecting its position in the tuple.
     *
     * @param _1  the value of the first element
     * @param _2  the value of the second element
     * @param _3  the value of the third element
     */
    public ImmutableTuple3(T1 _1, T2 _2, T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
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
    public Tuple3<T1, T2, T3> with1(T1 new1) {
        return new ImmutableTuple3<>(new1, _2, _3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with1(Func0<T1> supplier1) {
        return new ImmutableTuple3<>(supplier1.get(), _2, _3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with1(Func1<T1, T1> function1) {
        return new ImmutableTuple3<>(function1.apply(_1()), _2, _3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with2(T2 new2) {
        return new ImmutableTuple3<>(_1, new2, _3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with2(Func0<T2> supplier2) {
        return new ImmutableTuple3<>(_1, supplier2.get(), _3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with2(Func1<T2, T2> function2) {
        return new ImmutableTuple3<>(_1, function2.apply(_2()), _3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with3(T3 new3) {
        return new ImmutableTuple3<>(_1, _2, new3);
    }
    
    @Override
    public Tuple3<T1, T2, T3> with3(Func0<T3> supplier3) {
        return new ImmutableTuple3<>(_1, _2, supplier3.get());
    }
    
    @Override
    public Tuple3<T1, T2, T3> with3(Func1<T3, T3> function3) {
        return new ImmutableTuple3<>(_1, _2, function3.apply(_3()));
    }
    
    
    @Override
    public String toString() {
        return "(" + _1 + "," + _2 + "," + _3 + ")";
    }
    
    @Override
    public int hashCode() {
        return Tuple.hashCode(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        return Tuple.equals(this, obj);
    }
}
