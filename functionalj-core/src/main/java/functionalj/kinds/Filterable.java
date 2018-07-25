package functionalj.kinds;

import java.util.function.Function;

@SuppressWarnings("javadoc")
public interface Filterable<TYPE, DATA> {
    
    public Filterable<TYPE, DATA> _filter(Function<? super DATA, Boolean> predicate);
    
}
