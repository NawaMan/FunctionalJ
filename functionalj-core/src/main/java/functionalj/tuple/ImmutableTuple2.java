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
package functionalj.tuple;

import java.util.Map;
import java.util.function.Supplier;
import functionalj.function.Func1;

public class ImmutableTuple2<T1, T2> implements Tuple2<T1, T2>, Map.Entry<T1, T2> {

    public final T1 _1;

    public final T2 _2;

    public ImmutableTuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public ImmutableTuple2(Map.Entry<? extends T1, ? extends T2> entry) {
        this._1 = entry.getKey();
        this._2 = entry.getValue();
    }

    public T1 _1() {
        return _1;
    }

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
        return new ImmutableTuple2<>(new1, _2());
    }

    @Override
    public Tuple2<T1, T2> with1(Supplier<T1> supplier1) {
        return new ImmutableTuple2<>(supplier1.get(), _2());
    }

    @Override
    public Tuple2<T1, T2> with1(Func1<T1, T1> function1) {
        return new ImmutableTuple2<>(function1.apply(_1()), _2());
    }

    @Override
    public Tuple2<T1, T2> with2(T2 new2) {
        return new ImmutableTuple2<>(_1(), new2);
    }

    @Override
    public Tuple2<T1, T2> with2(Supplier<T2> supplier2) {
        return new ImmutableTuple2<>(_1(), supplier2.get());
    }

    @Override
    public Tuple2<T1, T2> with2(Func1<T2, T2> function2) {
        return new ImmutableTuple2<>(_1(), function2.apply(_2()));
    }

    @Override
    public String toString() {
        return Tuple.toString(this);
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
