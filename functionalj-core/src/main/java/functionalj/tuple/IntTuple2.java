package functionalj.types.tuple;

import java.util.Map;

@SuppressWarnings("javadoc")
public class IntTuple2<T2> implements Tuple2<Integer, T2>, Map.Entry<Integer, T2> {

    public final int _1;
    public final T2  _2;
    
    public IntTuple2(int _1, T2 T2) {
        this._1 = _1;
        this._2 = T2;
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