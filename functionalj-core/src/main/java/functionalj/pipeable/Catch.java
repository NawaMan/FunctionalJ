package functionalj.pipeable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.FunctionInvocationException;
import functionalj.types.result.Result;

@FunctionalInterface
public interface Catch<OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> {
    
    public FINALOUTPUT doCatch(OUTPUT data, Exception exception) throws EXCEPTION;
    
    public static <OUTPUT, EXCEPTION extends RuntimeException> 
            Catch<OUTPUT, Result<OUTPUT>, EXCEPTION> toResult() {
        return (OUTPUT data, Exception exception) -> {
            if (exception != null)
                return Result.ofException(exception);
            
            return Result.of(data);
        };
    }
    public static <OUTPUT, EXCEPTION extends RuntimeException> 
            Catch<OUTPUT, OUTPUT, EXCEPTION> orElse(OUTPUT elseValue) {
        return (OUTPUT data, Exception exception) -> {
            if (exception != null)
                return elseValue;
            if (data == null)
                return elseValue;
            return data;
        };
    }
    public static <OUTPUT, EXCEPTION extends RuntimeException> 
            Catch<OUTPUT, OUTPUT, EXCEPTION> orElseGet(Supplier<OUTPUT> elseSupplier) {
        return (OUTPUT data, Exception exception) -> {
            if (exception != null)
                return elseSupplier.get();
            if (data == null)
                return elseSupplier.get();
            return data;
        };
    }
    public static <OUTPUT> 
            Catch<OUTPUT, OUTPUT, Exception> orThrow() {
        return (OUTPUT data, Exception exception) -> {
            if (exception != null)
                throw exception;
            return data;
        };
    }
    public static <OUTPUT> 
            Catch<OUTPUT, OUTPUT, RuntimeException> orThrowRuntimeException() {
        return (OUTPUT data, Exception exception) -> {
            if (exception instanceof RuntimeException)
                throw (RuntimeException)exception;
            if (exception != null)
                throw new FunctionInvocationException(exception);
            
            return data;
        };
    }
    public static <OUTPUT, FINALOUTPUT> 
            Catch<OUTPUT, FINALOUTPUT, RuntimeException> handleValue(Function<OUTPUT, FINALOUTPUT> mapper) {
        return (OUTPUT data, Exception exception) -> {
            return mapper.apply(data);
        };
    }
    public static <OUTPUT, FINALOUTPUT> 
            Catch<OUTPUT, FINALOUTPUT, RuntimeException> handleException(Function<Exception, FINALOUTPUT> mapper) {
        return (OUTPUT data, Exception exception) -> {
            return mapper.apply(exception);
        };
    }
    public static <OUTPUT, FINALOUTPUT> 
            Catch<OUTPUT, FINALOUTPUT, RuntimeException> handle(BiFunction<OUTPUT, Exception, FINALOUTPUT> mapper) {
        return (OUTPUT data, Exception exception) -> {
            return mapper.apply(data, exception);
        };
    }
    
}