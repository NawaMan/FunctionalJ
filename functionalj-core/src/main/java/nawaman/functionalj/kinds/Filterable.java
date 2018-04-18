package nawaman.functionalj.kinds;

import nawaman.functionalj.functions.Func1;

public interface Filterable<TYPE,DATA> {
    
    public Filterable<TYPE,DATA> filter(Func1<DATA, Boolean> predicate);
    
}
