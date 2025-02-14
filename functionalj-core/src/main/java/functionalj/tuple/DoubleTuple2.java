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

public class DoubleTuple2<T2> implements Tuple2<Double, T2>, Map.Entry<Double, T2> {
    
    public static <T2> DoubleTuple2<T2> of(double i, T2 t2) {
        return new DoubleTuple2<>(i, t2);
    }
    
    public final double _1;
    
    public final T2 _2;
    
    public DoubleTuple2(double _1, T2 T2) {
        this._1 = _1;
        this._2 = T2;
    }
    
    public double _double() {
        return _1;
    }
    
    public Double _1() {
        return _1;
    }
    
    public T2 _2() {
        return _2;
    }
    
    @Override
    public Double getKey() {
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
        if (!(obj instanceof DoubleTuple2))
            return false;
        
        @SuppressWarnings("rawtypes")
        DoubleTuple2 other = (DoubleTuple2) obj;
        if (!Objects.equals(_1, other._1()))
            return false;
        if (!Objects.equals(_2, other._2()))
            return false;
        return true;
    }
}
