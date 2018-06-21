package functionalj.types;

import java.util.Map;

public class IntTuple<T2> implements ITuple2<Integer, T2>, Map.Entry<Integer, T2> {
    
    public final int _1;
    public final T2  _2;
    
    public IntTuple(int _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public int _int() {
        return _1;
    }
    public Integer _1() {
        return _1;
    }
    public T2 _2() {
        return _2;
    }
    
    public String toString() {
        return "[" + _1 + "," + _2 + "]";
    }

    @Override
    public Integer getKey() {
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
    
}