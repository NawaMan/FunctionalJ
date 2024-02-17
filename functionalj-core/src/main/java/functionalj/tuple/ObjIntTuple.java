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
import java.util.Objects;

public class ObjIntTuple<T1> implements Tuple2<T1, Integer>, Map.Entry<T1, Integer> {
    
    public static <T1> ObjIntTuple<T1> of(T1 t1, int i) {
        return new ObjIntTuple<>(t1, i);
    }
    
    public final T1 _1;
    
    public final int _2;
    
    public ObjIntTuple(T1 _1, int _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public T1 _1() {
        return _1;
    }
    
    public int _2int() {
        return _2;
    }
    
    public Integer _2() {
        return _2;
    }
    
    @Override
    public T1 getKey() {
        return _1();
    }
    
    @Override
    public Integer getValue() {
        return _2();
    }
    
    @Override
    public Integer setValue(Integer value) {
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
        if (!(obj instanceof ObjIntTuple))
            return false;
        
        @SuppressWarnings("rawtypes")
        ObjIntTuple other = (ObjIntTuple) obj;
        if (!Objects.equals(_1, other._1()))
            return false;
        if (!Objects.equals(_2, other._2()))
            return false;
        return true;
    }
}
