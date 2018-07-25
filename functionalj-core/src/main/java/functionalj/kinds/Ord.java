package functionalj.kinds;

@SuppressWarnings("javadoc")
public interface Ord<TYPE,DATA> extends Setoid<TYPE, DATA> {
    
    public boolean _lessThanEquals(Ord<TYPE,DATA> another);
    
}