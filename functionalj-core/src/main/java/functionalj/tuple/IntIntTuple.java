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
import java.util.function.IntUnaryOperator;

public class IntIntTuple implements Tuple2<Integer, Integer>, Map.Entry<Integer, Integer> {
    
    public static  IntIntTuple of(int i1, int i2) {
        return new IntIntTuple(i1, i2);
    }
    public static  IntIntTuple intTuple(int i1, int i2) {
        return of(i1, i2);
    }
    public static  IntIntTuple tuple(int i1, int i2) {
        return of(i1, i2);
    }
    
    public final int _1;
    public final int _2;
    
    public IntIntTuple(int _1, int _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public int _int1() {
        return _1;
    }
    public int _int2() {
        return _2;
    }
    
    public Integer _1() {
        return _1;
    }
    public Integer _2() {
        return _2;
    }
    
    @Override
    public Integer getKey() {
        return _1();
    }
    
    @Override
    public Integer getValue() {
        return _2();
    }
    
    public IntIntTuple mapToInt(IntUnaryOperator mapper1, IntUnaryOperator mapper2) {
        return IntIntTuple.of(mapper1.applyAsInt(_1), mapper2.applyAsInt(_2));
    }
    
    public IntIntTuple map1ToInt(IntUnaryOperator mapper) {
        return IntIntTuple.of(mapper.applyAsInt(_1), _2);
    }
    
    public IntIntTuple map2ToInt(IntUnaryOperator mapper) {
        return IntIntTuple.of(_1, mapper.applyAsInt(_2));
    }
    
    public IntIntTuple mapKeyToInt(IntUnaryOperator mapper) {
        return IntIntTuple.of(mapper.applyAsInt(_1), _2);
    }
    
    public IntIntTuple mapValueToInt(IntUnaryOperator mapper) {
        return IntIntTuple.of(_1, mapper.applyAsInt(_2));
    }
    
    @Override
    public Integer setValue(Integer value) {
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