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

import java.util.Map;

import functionalj.function.Func0;
import functionalj.function.Func1;

/**
 * Represents an immutable 2-element tuple, providing a way to store seven different values of possibly differing types.
 * This class implements {@link Tuple2}, ensuring that the values cannot be modified after creation.
 * 
 * @param <T1>  the type of the first element in the tuple
 * @param <T2>  the type of the second element in the tuple
 */
public class ImmutableTuple2<T1, T2> implements Tuple2<T1, T2>, Map.Entry<T1, T2> {
    
    /** The first element of the tuple. */
    public final T1 _1;
    
    /** The second element of the tuple. */
    public final T2 _2;
    
    /**
     * Constructs an immutable 2-element tuple with the specified values.
     * Each parameter corresponds to an element in the tuple, with its position in the parameter list 
     * reflecting its position in the tuple.
     *
     * @param _1  the value of the first element
     * @param _2  the value of the second element
     */
    public ImmutableTuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public ImmutableTuple2(Map.Entry<? extends T1, ? extends T2> entry) {
        this._1 = entry.getKey();
        this._2 = entry.getValue();
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
    public T1 getKey() {
        return _1();
    }
    
    @Override
    public T2 getValue() {
        return _2();
    }
    
    @Override
    public T2 setValue(T2 value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Tuple2<T1, T2> with1(T1 new1) {
        return new ImmutableTuple2<>(new1, _2);
    }
    
    @Override
    public Tuple2<T1, T2> with1(Func0<T1> supplier1) {
        return new ImmutableTuple2<>(supplier1.get(), _2);
    }
    
    @Override
    public Tuple2<T1, T2> with1(Func1<T1, T1> function1) {
        return new ImmutableTuple2<>(function1.apply(_1()), _2);
    }
    
    @Override
    public Tuple2<T1, T2> with2(T2 new2) {
        return new ImmutableTuple2<>(_1, new2);
    }
    
    @Override
    public Tuple2<T1, T2> with2(Func0<T2> supplier2) {
        return new ImmutableTuple2<>(_1, supplier2.get());
    }
    
    @Override
    public Tuple2<T1, T2> with2(Func1<T2, T2> function2) {
        return new ImmutableTuple2<>(_1, function2.apply(_2()));
    }
    
    @Override
    public Tuple2<T1, T2> withFirst(T1 new1) {
        return new ImmutableTuple2<>(new1, _2);
    }
    
    @Override
    public Tuple2<T1, T2> withFirst(Func0<T1> supplier1) {
        return new ImmutableTuple2<>(supplier1.get(), _2);
    }
    
    @Override
    public Tuple2<T1, T2> withFirst(Func1<T1, T1> function1) {
        return new ImmutableTuple2<>(function1.apply(_1()), _2);
    }
    
    @Override
    public Tuple2<T1, T2> withSecond(T2 new2) {
        return new ImmutableTuple2<>(_1, new2);
    }
    
    @Override
    public Tuple2<T1, T2> withSecond(Func0<T2> supplier2) {
        return new ImmutableTuple2<>(_1, supplier2.get());
    }
    
    @Override
    public Tuple2<T1, T2> withSecond(Func1<T2, T2> function2) {
        return new ImmutableTuple2<>(_1, function2.apply(_2()));
    }
    
    
    @Override
    public String toString() {
        return "(" + _1() + "," + _2() + ")";
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
