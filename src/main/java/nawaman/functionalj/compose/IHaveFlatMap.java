package nawaman.functionalj.compose;

import java.util.function.Function;

public interface IHaveFlatMap<TYPE> {

    public <RESULT, IHAVEFLATMAP extends IHaveFlatMap<RESULT>> IHAVEFLATMAP fmap(Function<TYPE, IHAVEFLATMAP> f);
    
}
