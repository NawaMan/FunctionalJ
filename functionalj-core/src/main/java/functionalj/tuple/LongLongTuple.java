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
import java.util.function.LongUnaryOperator;

public class LongLongTuple implements Tuple2<Long, Long>, Map.Entry<Long, Long> {
    
    public static LongLongTuple of(long i1, long i2) {
        return new LongLongTuple(i1, i2);
    }
    
    public static LongLongTuple longTuple(long i1, long i2) {
        return new LongLongTuple(i1, i2);
    }
    
    public final long _1;
    
    public final long _2;
    
    public LongLongTuple(long _1, long _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public long _long1() {
        return _1;
    }
    
    public long _long2() {
        return _2;
    }
    
    public Long _1() {
        return _1;
    }
    
    public Long _2() {
        return _2;
    }
    
    @Override
    public Long getKey() {
        return _1();
    }
    
    @Override
    public Long getValue() {
        return _2();
    }
    
    public LongLongTuple mapToLong(LongUnaryOperator mapper1, LongUnaryOperator mapper2) {
        return LongLongTuple.of(mapper1.applyAsLong(_1), mapper2.applyAsLong(_2));
    }
    
    public LongLongTuple map1ToLong(LongUnaryOperator mapper) {
        return LongLongTuple.of(mapper.applyAsLong(_1), _2);
    }
    
    public LongLongTuple map2ToInt(LongUnaryOperator mapper) {
        return LongLongTuple.of(_1, mapper.applyAsLong(_2));
    }
    
    public LongLongTuple mapKeyToLong(LongUnaryOperator mapper) {
        return LongLongTuple.of(mapper.applyAsLong(_1), _2);
    }
    
    public LongLongTuple mapValueToLong(LongUnaryOperator mapper) {
        return LongLongTuple.of(_1, mapper.applyAsLong(_2));
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
