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
import java.util.function.DoubleUnaryOperator;
import java.util.function.LongUnaryOperator;

public class LongDoubleTuple implements Tuple2<Long, Double>, Map.Entry<Long, Double> {
    
    public static LongDoubleTuple of(long i1, double i2) {
        return new LongDoubleTuple(i1, i2);
    }
    
    public static LongDoubleTuple longDoubleTuple(long i1, double i2) {
        return new LongDoubleTuple(i1, i2);
    }
    
    public final long _1;
    
    public final double _2;
    
    public LongDoubleTuple(long _1, double _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public long long1() {
        return _1;
    }
    
    public double _double2() {
        return _2;
    }
    
    public Long _1() {
        return _1;
    }
    
    public Double _2() {
        return _2;
    }
    
    @Override
    public Long getKey() {
        return _1();
    }
    
    @Override
    public Double getValue() {
        return _2();
    }
    
    public LongDoubleTuple mapToLong(LongUnaryOperator mapper1, DoubleUnaryOperator mapper2) {
        return LongDoubleTuple.of(mapper1.applyAsLong(_1), mapper2.applyAsDouble(_2));
    }
    
    public LongDoubleTuple map1ToLong(LongUnaryOperator mapper) {
        return LongDoubleTuple.of(mapper.applyAsLong(_1), _2);
    }
    
    public LongDoubleTuple map2ToDouble(DoubleUnaryOperator mapper) {
        return LongDoubleTuple.of(_1, mapper.applyAsDouble(_2));
    }
    
    @Override
    public Double setValue(Double value) {
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
