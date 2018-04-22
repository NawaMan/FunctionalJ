package functionalj.kinds;

public interface Group<TYPE,DATA> extends Monoid<TYPE, DATA> {
    
    public Group<TYPE,DATA> invert();
    
}
