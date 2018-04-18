package nawaman.functionalj.kinds;

import nawaman.functionalj.functions.Func1;

public interface Apply<TYPE,DATA> {
    
    public <TARGET> Apply<TYPE,TARGET> ap(Apply<TYPE,Func1<DATA, TARGET>> function);
    
}
