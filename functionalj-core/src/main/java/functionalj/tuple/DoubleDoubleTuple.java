// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.function.DoubleUnaryOperator;

public class DoubleDoubleTuple implements Tuple2<Double, Double>, Map.Entry<Double, Double> {
    
    public static DoubleDoubleTuple of(double v1, double v2) {
        return new DoubleDoubleTuple(v1, v2);
    }
    
    public static DoubleDoubleTuple doubleTuple(double v1, double v2) {
        return new DoubleDoubleTuple(v1, v2);
    }
    
    public final double _1;
    
    public final double _2;
    
    public DoubleDoubleTuple(double _1, double _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public double _double1() {
        return _1;
    }
    
    public double _double2() {
        return _2;
    }
    
    public Double _1() {
        return _1;
    }
    
    public Double _2() {
        return _2;
    }
    
    @Override
    public Double getKey() {
        return _1();
    }
    
    @Override
    public Double getValue() {
        return _2();
    }
    
    public DoubleDoubleTuple mapToDouble(DoubleUnaryOperator mapper1, DoubleUnaryOperator mapper2) {
        return DoubleDoubleTuple.of(mapper1.applyAsDouble(_1), mapper2.applyAsDouble(_2));
    }
    
    public DoubleDoubleTuple map1ToDouble(DoubleUnaryOperator mapper) {
        return DoubleDoubleTuple.of(mapper.applyAsDouble(_1), _2);
    }
    
    public DoubleDoubleTuple map2ToDouble(DoubleUnaryOperator mapper) {
        return DoubleDoubleTuple.of(_1, mapper.applyAsDouble(_2));
    }
    
    public DoubleDoubleTuple mapKeyToDouble(DoubleUnaryOperator mapper) {
        return DoubleDoubleTuple.of(mapper.applyAsDouble(_1), _2);
    }
    
    public DoubleDoubleTuple mapValueToDouble(DoubleUnaryOperator mapper) {
        return DoubleDoubleTuple.of(_1, mapper.applyAsDouble(_2));
    }
    
    @Override
    public Double setValue(Double value) {
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
        if (!(obj instanceof DoubleDoubleTuple))
            return false;
        
        DoubleDoubleTuple other = (DoubleDoubleTuple) obj;
        if (!Objects.equals(_1, other._1()))
            return false;
        if (!Objects.equals(_2, other._2()))
            return false;
        return true;
    }
}
