package nawaman.functionalj.kinds;

public interface Monoid<TYPE,DATA> extends Semigroup<TYPE, DATA> {
    
    public Monoid<TYPE,DATA> _empty();
    
}
