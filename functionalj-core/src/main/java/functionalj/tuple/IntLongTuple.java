// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.Objects;
import java.util.function.IntUnaryOperator;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;

public class IntLongTuple implements Tuple2<Integer, Long>, Map.Entry<Integer, Long> {
    
    public static IntLongTuple of(int i1, long i2) {
        return new IntLongTuple(i1, i2);
    }
    
    public static IntLongTuple tuple(int i1, long i2) {
        return new IntLongTuple(i1, i2);
    }
    
    public final int _1;
    
    public final long _2;
    
    public IntLongTuple(int _1, long _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public int _int1() {
        return _1;
    }
    
    public long _long2() {
        return _2;
    }
    
    public Integer _1() {
        return _1;
    }
    
    public Long _2() {
        return _2;
    }
    
    @Override
    public Integer getKey() {
        return _1();
    }
    
    @Override
    public Long getValue() {
        return _2();
    }
    
    public IntLongTuple mapToInt(IntUnaryOperator mapper1, LongUnaryOperator mapper2) {
        return IntLongTuple.of(mapper1.applyAsInt(_1), mapper2.applyAsLong(_2));
    }
    
    public IntLongTuple map1ToInt(IntUnaryOperator mapper) {
        return IntLongTuple.of(mapper.applyAsInt(_1), _2);
    }
    
    public IntLongTuple map2ToLong(LongUnaryOperator mapper) {
        return IntLongTuple.of(_1, mapper.applyAsLong(_2));
    }
    
    public IntLongTuple mapKeyToInt(IntUnaryOperator mapper) {
        return IntLongTuple.of(mapper.applyAsInt(_1), _2);
    }
    
    public IntLongTuple mapValueToInt(LongToIntFunction mapper) {
        return IntLongTuple.of(_1, mapper.applyAsInt(_2));
    }
    
    @Override
    public Long setValue(Long value) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String toString() {
        return "(" + _1() + "," + _2() + ")";
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof IntLongTuple))
            return false;
        
        IntLongTuple other = (IntLongTuple) obj;
        if (!Objects.equals(_1, other._1()))
            return false;
        if (!Objects.equals(_2, other._2()))
            return false;
        return true;
    }
}
