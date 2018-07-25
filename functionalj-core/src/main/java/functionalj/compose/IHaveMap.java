package functionalj.compose;

import java.util.function.Function;

@SuppressWarnings("javadoc")
public interface IHaveMap<TYPE> {
    
    public <RESULT, IHAVEMAP extends IHaveMap<RESULT>> IHAVEMAP map(Function<TYPE, RESULT> f);
    
}
