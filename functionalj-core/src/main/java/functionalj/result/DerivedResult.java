package functionalj.result;

import functionalj.function.Func0;
import functionalj.function.Func1;

public class DerivedResult<DATA> extends Result<DATA>{

    private final Func0<Result<DATA>> valueSupplier;
    
    public DerivedResult(Func0<DATA> dataSupplier) {
        this.valueSupplier = ()->{
            try {
                return dataSupplier.applySafely();
            } catch (Exception e) {
                return Result.ofException(e);
            }
        };
    }
    public <ORG> DerivedResult(Result<ORG> orgValue, Func1<Result<ORG>, Result<DATA>> mapper) {
        this.valueSupplier = ()->{
            try {
                return mapper.applyUnsafe(orgValue);
            } catch (Exception e) {
                return Result.ofException(e);
            }
        };
    }
    
    @Override
    Object __valueData() {
        return valueSupplier.get().__valueData();
    }
}
