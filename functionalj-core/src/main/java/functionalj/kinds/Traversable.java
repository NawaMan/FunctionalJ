package functionalj.kinds;

import java.util.function.Function;

import functionalj.functions.Func1;

public interface Traversable<TYPE,DATA> extends Functor<TYPE, DATA>, Foldable<TYPE, DATA> {
    
    public <TARGET, ATYPE> Func1<Traversable<TYPE,DATA>, Applicative<ATYPE, TARGET>> 
            _traverse(Applicative<ATYPE, Function<DATA, Traversable<TYPE,TARGET>>> f);
    
}
