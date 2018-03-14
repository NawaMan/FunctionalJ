package nawaman.functionalj.compose;

import java.util.function.Function;

public interface IHaveFMap<TYPE> {

    public <RESULT, IHAVEFMAP extends IHaveFMap<RESULT>> IHAVEFMAP fmap(Function<TYPE, IHAVEFMAP> f);
    
}
