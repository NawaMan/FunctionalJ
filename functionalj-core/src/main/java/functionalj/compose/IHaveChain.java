package functionalj.compose;

import java.util.function.Function;

public interface IHaveChain<TYPE> {
    
    public <RESULT> RESULT chain(Function<TYPE, RESULT> f);
    
}
