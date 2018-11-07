package functionalj.ref;

import static functionalj.ref.Else.ElseDefault;
import static functionalj.ref.Else.ElseGet;
import static functionalj.ref.Else.ElseUse;

import java.util.Random;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;

public abstract class RefOf<DATA> extends Ref<DATA> {
    
    private static final Random random = new Random();
    
    private final int hashCode = random.nextInt();
    
    public RefOf(Class<DATA> dataClass) {
        super(dataClass, null);
    }
    RefOf(Class<DATA> dataClass, Supplier<DATA> elseSupplier) {
        super(dataClass, elseSupplier);
    }
    
    public final int hashCode() {
        return hashCode;
    }
    
    public final boolean equals(Object another) {
        if (this == another)
            return true;
        if (another == null)
            return false;
        if (!(another instanceof RefOf))
            return false;
        
        @SuppressWarnings("unchecked")
        val anotherRef = (RefOf<DATA>)another;
        if (!anotherRef.getDataType().equals(this.getDataType()))
            return false;
        
        return this.hashCode() == another.hashCode();
    }
    
    //== Sub classes ==
    
    public static class FromResult<DATA> extends RefOf<DATA> {
        
        private final Result<DATA> result;
        
        FromResult(Class<DATA> dataClass, Result<DATA> result, Supplier<DATA> elseSupplier) {
            super(dataClass, elseSupplier);
            this.result = (result != null)
                    ? result
                    : Result.ofNull();
        }
        
        @Override
        protected final Result<DATA> findResult() {
            return result;
        }
        
        public Ref<DATA> whenAbsentUse(DATA defaultValue) {
            return new RefOf.FromResult<>(getDataType(), result, ElseUse(defaultValue));
        }
        public Ref<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
            return new RefOf.FromResult<>(getDataType(), result, ElseGet(defaultSupplier));
        }
        public Ref<DATA> whenAbsentUseDefault() {
            return new RefOf.FromResult<>(getDataType(), result, ElseDefault(getDataType()));
        }
    }
    
    public static class FromSupplier<DATA> extends RefOf<DATA> {
        
        @SuppressWarnings("rawtypes")
        private static final Func0 notExist = ()->Result.ofNotExist();
        
        private final Func0<DATA> supplier;
        
        @SuppressWarnings("unchecked")
        FromSupplier(Class<DATA> dataClass, Func0<DATA> supplier, Supplier<DATA> elseSupplier) {
            super(dataClass, elseSupplier);
            this.supplier = (supplier != null)
                    ? supplier
                    : notExist;
        }
        
        @Override
        protected final Result<DATA> findResult() {
            val result = Result.from(supplier);
            return result;
        }
        
        public Ref<DATA> whenAbsentUse(DATA defaultValue) {
            return new RefOf.FromSupplier<>(getDataType(), supplier, ElseUse(defaultValue));
        }
        public Ref<DATA> whenAbsentGet(Supplier<DATA> defaultSupplier) {
            return new RefOf.FromSupplier<>(getDataType(), supplier, ElseGet(defaultSupplier));
        }
        public Ref<DATA> whenAbsentUseDefault() {
            return new RefOf.FromSupplier<>(getDataType(), supplier, ElseDefault(getDataType()));
        }
        
    }
    
}
