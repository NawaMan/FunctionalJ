package functionalj.kinds;

import functionalj.functions.Func1;

public interface Apply<TYPE,DATA> {
    
    public <TARGET> Apply<TYPE,TARGET> _ap(Apply<TYPE,Func1<DATA, TARGET>> function);
    
}
