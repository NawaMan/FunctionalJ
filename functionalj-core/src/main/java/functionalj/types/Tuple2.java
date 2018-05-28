package functionalj.types;

import java.util.Map;

public class Tuple2<T1, T2> implements ITuple2<T1, T2>, Map.Entry<T1, T2> {
    
    public final T1 _1;
    public final T2 _2;
    
    public Tuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }
    
    public Tuple2(Map.Entry<T1, T2> entry) {
        this._1 = entry.getKey();
        this._2 = entry.getValue();
    }
    
    public T1 _1() {
        return _1;
    }
    public T2 _2() {
        return _2;
    }
    
    public String toString() {
        return "[" + _1 + "," + _2 + "]";
    }

    @Override
    public T1 getKey() {
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
