package tuple;

@SuppressWarnings("javadoc")
public class ImmutableTuple6<T1, T2, T3, T4, T5, T6> implements Tuple6<T1, T2, T3, T4, T5, T6> {
    
    public final T1 _1;
    public final T2 _2;
    public final T3 _3;
    public final T4 _4;
    public final T5 _5;
    public final T6 _6;
    
    public ImmutableTuple6(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
    }
    
    public T1 _1() {
        return _1;
    }
    public T2 _2() {
        return _2;
    }
    public T3 _3() {
        return _3;
    }
    public T4 _4() {
        return _4;
    }
    public T5 _5() {
        return _5;
    }
    public T6 _6() {
        return _6;
    }
    
    public String toString() {
        return "[" + _1 + "," + _2 + "," + _3 + "," + _4 + "," + _5 + "," + _6 + "]";
    }
    
}
