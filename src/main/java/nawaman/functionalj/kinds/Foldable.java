package nawaman.functionalj.kinds;

import nawaman.functionalj.functions.Func2;

public interface Foldable<TYPE,DATA> {
    
    public <TARGET> TARGET reduce(TARGET identity, Func2<DATA, TARGET, TARGET> reducer);
    
}
