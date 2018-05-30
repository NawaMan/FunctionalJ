package functionalj.types;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import functionalj.kinds.Functor;
import functionalj.kinds.Monad;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public interface Result<VALUE> extends Functor<Result<?>, VALUE>, Monad<Result<?>, VALUE>, Nullable<VALUE> {
    
    @Override
    default VALUE get() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    default <TARGET> Monad<Result<?>, TARGET> _of(TARGET target) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    default <TARGET> Monad<Result<?>, TARGET> flatMap(Function<VALUE, Monad<Result<?>, TARGET>> mapper) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    default <TARGET> Functor<Result<?>, TARGET> map(Function<VALUE, ? extends TARGET> mapper) {
        // TODO Auto-generated method stub
        return null;
    }
    public VALUE     getValue();
    public Throwable getThrowable();
//    
//    public default Optional<VALUE> getValueOptional() {
//        
//    }
//    public default Nullable<VALUE> getValueNullable() {
//        
//    }
//    public default MayBe<VALUE> getValueMayBe() {
//        
//    }
    
//    
//
//    public <TARGET> Result<TARGET> map(Function<? extends VALUE, ? extends TARGET> mapper);
//
//    public default <TARGET> Result<TARGET> flatMap(Function<VALUE, Monad<Result<?>, TARGET>> mapper) {
//        return null;
//    }
    
    
    
    public default boolean hasValue() {
        Throwable throwable = getThrowable();
        return throwable != null;
    }
    
    public default VALUE orThrow() throws Throwable {
        Throwable throwable = getThrowable();
        if (throwable != null)
            throw throwable;

        val value = getValue();
        return value;
    }
    public default VALUE orThrowRuntimeException() {
        Throwable throwable = getThrowable();
        if (throwable == null) {
            val value = getValue();
            return value;
        }
        if (throwable instanceof RuntimeException)
            throw (RuntimeException)throwable;
        
        throw new RuntimeException(throwable);
    }
    public default <THROWABLE extends Throwable> VALUE orThrow(Function<Throwable, THROWABLE> toThrowable) throws THROWABLE {
        Throwable throwable = getThrowable();
        if (throwable == null) {
            val value = getValue();
            return value;
        }
        throw toThrowable.apply(throwable);
    }
    public default <RUNTIMEEXCEPTION extends RuntimeException> VALUE orThrowRuntimeException(Function<Throwable, RUNTIMEEXCEPTION> toRuntimeException) {
        Throwable throwable = getThrowable();
        if (throwable == null) {
            val value = getValue();
            return value;
        }
        throw toRuntimeException.apply(throwable);
    }
    
}
