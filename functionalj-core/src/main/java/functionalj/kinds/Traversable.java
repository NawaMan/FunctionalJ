package functionalj.kinds;

import functionalj.functions.Func1;

public interface Traversable<TYPE,DATA> extends Functor<TYPE, DATA>, Foldable<TYPE, DATA> {
    
    public <TARGET, ATYPE> Func1<Traversable<TYPE,DATA>, Applicative<ATYPE, TARGET>> traverse(Applicative<ATYPE, Func1<DATA, Traversable<TYPE,TARGET>>> f);
    
}
