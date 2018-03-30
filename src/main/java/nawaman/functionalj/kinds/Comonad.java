package nawaman.functionalj.kinds;

public interface Comonad<TYPE, DATA> extends Functor<TYPE, DATA> {
    
    public DATA extract();
    
}
