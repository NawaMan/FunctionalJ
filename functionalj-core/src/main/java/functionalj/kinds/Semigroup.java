package functionalj.kinds;

public interface Semigroup<TYPE,DATA> {
    
    public Semigroup<TYPE,DATA> concat(Semigroup<TYPE,DATA> another);
    
}
