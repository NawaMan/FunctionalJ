package functionalj.kinds;

@SuppressWarnings("javadoc")
public interface Semigroup<TYPE,DATA> {
    
    public Semigroup<TYPE,DATA> _concat(Semigroup<TYPE,DATA> another);
    
}
