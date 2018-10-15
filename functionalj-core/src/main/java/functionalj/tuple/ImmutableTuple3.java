package functionalj.tuple;

@SuppressWarnings("javadoc")
public class ImmutableTuple3<T1, T2, T3> implements Tuple3<T1, T2, T3> {
    
    public final T1 _1;
    public final T2 _2;
    public final T3 _3;
    
    public ImmutableTuple3(T1 _1, T2 _2, T3 _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
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
