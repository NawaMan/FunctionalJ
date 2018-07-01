package functionalj.kinds;

public interface Setoid<TYPE, DATA> {
    
    public boolean _equals(Setoid<TYPE,DATA> another);
    
}
