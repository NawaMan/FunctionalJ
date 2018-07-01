package functionalj.kinds;

public interface Ord<TYPE,DATA> extends Setoid<TYPE, DATA> {
    
    public boolean _lessThanEquals(Ord<TYPE,DATA> another);
    
}