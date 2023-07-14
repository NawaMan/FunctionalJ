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

public class ObjLongTuple<T1> implements Tuple2<T1, Long>, Map.Entry<T1, Long> {

    public static <T1> ObjLongTuple<T1> of(T1 t1, long i) {
        return new ObjLongTuple<>(t1, i);
    }

    public final T1 _1;

    public final long _2;

    public ObjLongTuple(T1 _1, long _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public T1 _1() {
        return _1;
    }

    public long _2long() {
        return _2;
    }

    public Long _2() {
        return _2;
    }

    @Override
    public T1 getKey() {
        return _1();
    }

    @Override
    public Long getValue() {
        return _2();
    }

    @Override
    public Long setValue(Long value) {
        throw new UnsupportedOperationException();
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
