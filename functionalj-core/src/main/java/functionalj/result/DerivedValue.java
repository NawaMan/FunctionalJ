package functionalj.result;

import functionalj.function.Func0;
import functionalj.function.Func1;

public class DerivedValue<DATA> extends Result<DATA>{

    private final Func0<Result<DATA>> valueSupplier;
    
    public DerivedValue(Func0<DATA> dataSupplier) {
        this.valueSupplier = ()->{
            try {
                return dataSupplier.applySafely();
            } catch (Exception e) {
                return Result.ofException(e);
            }
        };
    }
    public <ORG> DerivedValue(Result<ORG> orgValue, Func1<Result<ORG>, Result<DATA>> mapper) {
        this.valueSupplier = ()->{
            try {
                return mapper.apply(orgValue);
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
