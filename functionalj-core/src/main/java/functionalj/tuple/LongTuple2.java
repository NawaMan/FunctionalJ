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

public class LongTuple2<T2> implements Tuple2<Long, T2>, Map.Entry<Long, T2> {
    
    public static <T2> LongTuple2<T2> of(long i1, T2 i2) {
        return new LongTuple2<T2>(i1, i2);
    }
    
    public static <T2> LongTuple2<T2> intTuple(long i1, T2 i2) {
        return new LongTuple2<T2>(i1, i2);
    }
    
    public final long _1;
    
    public final T2 _2;
    
    public LongTuple2(long _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public long _long1() {
        return _1;
    }
    
    public Long _1() {
        return _1;
    }
    
    public T2 _2() {
        return _2;
    }
    
    @Override
    public Long getKey() {
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
