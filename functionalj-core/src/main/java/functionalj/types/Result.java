package functionalj.types;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.kinds.Functor;
import functionalj.kinds.Monad;
import funtionalj.failable.FailableBiFunction;
import funtionalj.failable.FailableFunc0;
import funtionalj.failable.FailableFunction;
import funtionalj.failable.FailableSupplier;

public final class Result<VALUE> implements Functor<Result<?>, VALUE>, Monad<Result<?>, VALUE>, ITuple2<VALUE, Throwable> {
    
    private static final Result NULL = new Result<>(null, null);
    
    public static <VALUE> Result<VALUE> of(VALUE value) {
        return new Result<VALUE>(value, null);
    }
    public static <VALUE> Result<VALUE> of(VALUE value, Throwable throwable) {
        return new Result<VALUE>(value, throwable);
    }
    public static <VALUE> Result<VALUE> of(ITuple2<VALUE, Throwable> tuple) {
        if (tuple != null)
            return Result.of(null, tuple._2());
            
        return Result.of(tuple._1(), null);
    }
    public static <VALUE> Result<VALUE> from(Supplier<VALUE> supplier) {
        try {
            return Result.of(supplier.get());
        } catch (RuntimeException e) {
            return Result.of(null, e);
        }
    }
    public static <VALUE> Result<VALUE> from(FailableSupplier<VALUE> supplier) {
        try {
            return Result.of(supplier.get());
        } catch (Throwable e) {
            return Result.of(null, e);
        }
    }
    public static <VALUE> Result<VALUE> ofNull() {
        return (Result<VALUE>)NULL;
    }

    @Override
    public <TARGET> Result<TARGET> _of(TARGET target) {
        return Result.of(target);
    }
    
    private VALUE     value;
    private Throwable throwable;
    
    public Result(VALUE value, Throwable throwable) {
        this.value     = value;
        this.throwable = throwable;
    }
    
    public final VALUE getValue() {
        return value;
    }
    public final Throwable getThrowable() {
        return throwable;
    }

    @Override
    public VALUE _1() {
        return getValue();
    }
    @Override
    public Throwable _2() {
        return getThrowable();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <TARGET> Result<TARGET> map(Function<VALUE, TARGET> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((Supplier<TARGET>)()->{
            return mapper.apply(value);
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TARGET> Result<TARGET> flatMap(Function<VALUE, Monad<Result<?>, TARGET>> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((Supplier<TARGET>)()->{
            Result<TARGET> newResult = (Result<TARGET>)mapper.apply(value);
            return newResult.getValue();
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Result<TARGET> map(BiFunction<VALUE, Throwable, TARGET> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((Supplier<TARGET>)()->{
            return mapper.apply(value, throwable);
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Result<TARGET> flatMap(BiFunction<VALUE, Throwable, Monad<Result<?>, TARGET>> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((Supplier<TARGET>)()->{
            Result<TARGET> newResult = (Result<TARGET>)mapper.apply(value, throwable);
            return newResult.getValue();
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Result<TARGET> map(FailableFunction<VALUE, TARGET> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((FailableSupplier<TARGET>)()->{
            return mapper.apply(value);
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Result<TARGET> flatMap(FailableFunction<VALUE, Monad<Result<?>, TARGET>> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((FailableSupplier<TARGET>)()->{
            Result<TARGET> newResult = (Result<TARGET>)mapper.apply(value);
            return newResult.getValue();
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Result<TARGET> map(FailableBiFunction<VALUE, Throwable, TARGET> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((FailableSupplier<TARGET>)()->{
            return mapper.apply(value, throwable);
        });
    }
    
    @SuppressWarnings("unchecked")
    public <TARGET> Result<TARGET> flatMap(FailableBiFunction<VALUE, Throwable, Monad<Result<?>, TARGET>> mapper) {
        if (value == null)
            return (Result<TARGET>)this;
        
        return Result.from((FailableSupplier<TARGET>)()->{
            Result<TARGET> newResult = (Result<TARGET>)mapper.apply(value, throwable);
            return newResult.getValue();
        });
    }
    
    public boolean isValue() {
        return throwable == null;
    }
    
    public boolean isNotNull() {
        if (throwable != null)
            return false;
        return (value != null);
    }
    
    public boolean isNull() {
        if (throwable != null)
            return false;
        return (value == null);
    }
    
    public boolean isThrowable() {
        return throwable != null;
    }
    
    public Result<VALUE> ensureNotNull() {
        if (value != null)
            return this;
        return new Result<VALUE>(null, new NullPointerException());
    }
    
    public Result<VALUE> makeNullable() {
        if (throwable instanceof NullPointerException)
            return ofNull();
        
        return this;
    }
    
    public Result<VALUE> filter(Predicate<VALUE> check) {
        if (value == null)
            return this;
        if (check.test(value))
            return this;
        return Result.ofNull();
    }
    
    public Result<VALUE> otherwise(VALUE otherwiseValue) {
        if (value == null)
            return Result.of(otherwiseValue, null);
        return this;
    }
    
    public Result<VALUE> otherwiseGet(Supplier<VALUE> otherwiseSupplier) {
        if (value == null)
            return this;
        
        return Result.from((Supplier<VALUE>)()->{
            return otherwiseSupplier.get();
        });
    }
    
    public Result<VALUE> otherwiseGet(FailableFunc0<VALUE> otherwiseSupplier) {
        if (value != null)
            return this;
        
        return Result.from((FailableFunc0<VALUE>)()->{
            return otherwiseSupplier.get();
        });
    }
    
    public VALUE orElse(VALUE elseValue) {
        if (value == null)
            return elseValue;
        return value;
    }
    
    public VALUE orElseGet(Supplier<VALUE> elseSupplier) {
        if (value == null)
            return elseSupplier.get();
        return value;
    }
    
    public MayBe<MayBe<VALUE>> asMayBe() {
        if (throwable != null)
            return MayBe.nothing();
        return MayBe.of(MayBe.of(value));
    }
    
    public MayBe<VALUE> getMayBeValue() {
        return MayBe.of(value);
    }
    
    public MayBe<Throwable> getMayBeThrowable() {
        return MayBe.of(throwable);
    }
    
    public VALUE orThrow() throws Throwable {
        if (throwable != null)
            throw throwable;
        return value;
    }
    public VALUE orThrowRuntimeException() {
        if (throwable == null)
            return value;
        
        if (throwable instanceof RuntimeException)
            throw (RuntimeException)throwable;
        
        throw new RuntimeException(throwable);
    }
    public <THROWABLE extends Throwable> 
            VALUE orThrow(Function<Throwable, THROWABLE> toThrowable) throws THROWABLE {
        if (throwable == null)
            return value;
        throw toThrowable.apply(throwable);
    }
    public <RUNTIMEEXCEPTION extends RuntimeException> 
            VALUE orThrowRuntimeException(Function<Throwable, RUNTIMEEXCEPTION> toRuntimeException) {
        if (throwable == null)
            return value;
        throw toRuntimeException.apply(throwable);
    }

}
