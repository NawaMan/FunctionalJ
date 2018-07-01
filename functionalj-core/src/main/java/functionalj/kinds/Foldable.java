package functionalj.kinds;

import functionalj.functions.Func2;

public interface Foldable<TYPE,DATA> {
    
    public <TARGET> TARGET _reduce(TARGET identity, Func2<DATA, TARGET, TARGET> reducer);
    
}
