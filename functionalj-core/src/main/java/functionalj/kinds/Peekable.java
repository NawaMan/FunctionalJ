package functionalj.kinds;

import java.util.function.Function;

public interface Peekable<TYPE, DATA> {
    
    public Peekable<TYPE, DATA> _peek(Function<? super DATA, ? extends Object> consumer);
    
}
