package functionalj.kinds;

import functionalj.functions.Func1;

public interface Semigroup<TYPE,DATA> {
    
    public Semigroup<TYPE,DATA> concat(Semigroup<TYPE,DATA> another);
    
}
