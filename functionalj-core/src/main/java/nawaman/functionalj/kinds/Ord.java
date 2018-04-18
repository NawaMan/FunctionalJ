package nawaman.functionalj.kinds;

public interface Ord<TYPE,DATA> extends Setoid<TYPE, DATA> {
    
    public boolean lessThanEquals(Ord<TYPE,DATA> another);
    
}