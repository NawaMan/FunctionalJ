//  ========================================================================
//  Copyright (c) 2017-2018 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.pipeable;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.ThrowFuncs;
import functionalj.result.Result;

/**
 * Classes extending this class handle the last step in the processing of the Pipeable.
 * 
 * NOTE: This class was initially written as a functional interface.
 *       However, it causes ambiguity with other functions.
 * 
 * @param <OUTPUT>       the output from the step before.
 * @param <FINALOUTPUT>  the output from this last step.
 * @param <EXCEPTION>    the exception this last step might throw.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public abstract class Catch<OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> {
    
    /**
     * Performs the output and catch the exception if any.
     * 
     * @param data        the output data.
     * @param exception   the exception that occurs over the pipe.
     * @return            the final output.
     * @throws EXCEPTION  the exception that might be thrown from this method.
     */
    public abstract FINALOUTPUT doCatch(OUTPUT data, Exception exception) throws EXCEPTION;
    
    
    
    /**
     * Returns the catch that will returns Result.
     * 
     * @param <OUTPUT>     the output data type.
     * @return the catch that will returns Result.
     */
    public static <OUTPUT> 
            Catch<OUTPUT, Result<OUTPUT>, RuntimeException> toResult() {
        return new Catch<OUTPUT, Result<OUTPUT>, RuntimeException>() {
            public Result<OUTPUT> doCatch(OUTPUT data, Exception exception) {
                if (exception != null)
                    return Result.ofException(exception);
                
                return Result.valueOf(data);
            }
        };
    }
    
    /**
     * Returns the catch that will returns the output or the given elseValue if the value is null.
     * 
     * @param <OUTPUT>    the output data type.
     * @param  elseValue  the elseValue.
     * @return the output value or the elseValue.
     */
    public static <OUTPUT> 
            Catch<OUTPUT, OUTPUT, RuntimeException> thenReturn(OUTPUT elseValue) {
        return new Catch<OUTPUT, OUTPUT, RuntimeException>() {
            public OUTPUT doCatch(OUTPUT data, Exception exception) {
                if (exception != null)
                    return elseValue;
                if (data == null)
                    return elseValue;
                return data;
            }
        };
    }
    
    /**
     * Returns the catch that will returns the output or the value from the given elseSupplier if the value is null.
     * 
     * @param  <OUTPUT>      the output data type.
     * @param  elseSupplier  the elseSupplier.
     * @return the output value or the value from the elseSupplier.
     */
    public static <OUTPUT> 
            Catch<OUTPUT, OUTPUT, RuntimeException> thenGet(Supplier<OUTPUT> elseSupplier) {
        return new Catch<OUTPUT, OUTPUT, RuntimeException>() {
            public OUTPUT doCatch(OUTPUT data, Exception exception) {
                if (exception != null)
                    return elseSupplier.get();
                if (data == null)
                    return elseSupplier.get();
                return data;
            }
        };
    }
    
    /**
     * Returns the catch that will throw the exception if any.
     * 
     * @param  <OUTPUT>  the output data type.
     * @return the catch.
     */
    public static <OUTPUT> 
            Catch<OUTPUT, OUTPUT, Exception> thenThrow() {
        return new Catch<OUTPUT, OUTPUT, Exception>() {
            public OUTPUT doCatch(OUTPUT data, Exception exception) throws Exception {
                if (exception != null)
                    throw exception;
                return data;
            }
        };
    }
    
    /**
     * Returns the catch that will throw a runtime exception if any. If the exception is not a runtime exception,
     *   it will be wrapped with FunctionInvocationException.
     * 
     * @param  <OUTPUT>  the output data type.
     * @return the catch.
     */
    public static <OUTPUT> 
            Catch<OUTPUT, OUTPUT, RuntimeException> thenThrowRuntimeException() {
        return new Catch<OUTPUT, OUTPUT, RuntimeException>() {
            public OUTPUT doCatch(OUTPUT data, Exception exception) {
                if (exception instanceof RuntimeException)
                    throw (RuntimeException)exception;
                if (exception != null)
                    throw ThrowFuncs.exceptionTransformer.value().apply(exception);
                
                return data;
            }
        };
    }
    
    /**
     * Returns the catch that will process the output value by mapping it to other value.
     *   
     * @param  <OUTPUT>       the output data type.
     * @param  <FINALOUTPUT>  the final output data type.
     * @param  mapper         the mapper.
     * @return the catch.
     */
    public static <OUTPUT, FINALOUTPUT> 
            Catch<OUTPUT, FINALOUTPUT, RuntimeException> thenHandleValue(Function<OUTPUT, FINALOUTPUT> mapper) {
        return new Catch<OUTPUT, FINALOUTPUT, RuntimeException>() {
            public FINALOUTPUT doCatch(OUTPUT data, Exception exception) {
                return mapper.apply(data);
            }
        };
    }
    
    /**
     * Returns the catch that will returns the value OR process the exception by mapping it to other value.
     *   
     * @param  <OUTPUT>       the output data type.
     * @param  <FINALOUTPUT>  the final output data type.
     * @param  mapper         the mapper.
     * @return the catch.
     */
    public static <OUTPUT, FINALOUTPUT> 
            Catch<OUTPUT, FINALOUTPUT, RuntimeException> thenHandleException(Function<Exception, FINALOUTPUT> mapper) {
        return new Catch<OUTPUT, FINALOUTPUT, RuntimeException>() {
            public FINALOUTPUT doCatch(OUTPUT data, Exception exception) {
                return mapper.apply(exception);
            }
        };
    }
    
    /**
     * Returns the catch that will process both the value and exception.
     *   
     * @param  <OUTPUT>       the output data type.
     * @param  <FINALOUTPUT>  the final output data type.
     * @param  mapper         the mapper.
     * @return the catch.
     */
    public static <OUTPUT, FINALOUTPUT> 
            Catch<OUTPUT, FINALOUTPUT, RuntimeException> thenHandle(BiFunction<OUTPUT, Exception, FINALOUTPUT> mapper) {
        return new Catch<OUTPUT, FINALOUTPUT, RuntimeException>() {
            public FINALOUTPUT doCatch(OUTPUT data, Exception exception) {
                return mapper.apply(data, exception);
            }
        };
    }
    
}