package functionalj.pipeable;

import java.util.function.Supplier;

import functionalj.functions.Func1;
import functionalj.functions.FunctionInvocationException;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Pipeable<DATA> {
    
    
    public static <D> Pipeable<D> of(D data) {
        return ()->data;
    }
    public static <D> Pipeable<D> from(Supplier<D> supplier) {
        return supplier::get;
    }
    
    
    public DATA __data() throws Exception;
    
    
    public default <OUTPUT> 
        OUTPUT pipe(Func1<? super DATA, OUTPUT> func1) {
        try {
            val input  = __data();
            val output = __internal.apply(func1, input);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
            Func1<? super DATA, OUTPUT>           func1,
            Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
            throws EXCEPTION {
        try {
            val input  = __data();
            val output = __internal.apply(func1, input);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1, 
                Func1<? super DATA1, OUTPUT> func2) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val output = __internal.apply(func2, data1);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1, 
                Func1<? super DATA1, OUTPUT> func2,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val output = __internal.apply(func2, data1);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1, 
                Func1<? super DATA1, DATA2>  func2, 
                Func1<? super DATA2, OUTPUT> func3) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val output = __internal.apply(func3, data2);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1, 
                Func1<? super DATA1, DATA2>  func2, 
                Func1<? super DATA2, OUTPUT> func3,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val output = __internal.apply(func3, data2);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1, 
                Func1<? super DATA1, DATA2>  func2, 
                Func1<? super DATA2, DATA3>  func3, 
                Func1<? super DATA3, OUTPUT> func4) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val output = __internal.apply(func4, data3);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1, 
                Func1<? super DATA1, DATA2>  func2, 
                Func1<? super DATA2, DATA3>  func3, 
                Func1<? super DATA3, OUTPUT> func4,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val output = __internal.apply(func4, data3);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, OUTPUT> func5) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val output = __internal.apply(func5, data4);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, OUTPUT> func5,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val output = __internal.apply(func5, data4);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, OUTPUT> func6) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val output = __internal.apply(func6, data5);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, OUTPUT> func6,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val output = __internal.apply(func6, data5);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, OUTPUT> func7) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val data6  = __internal.apply(func6, data5);
            val output = __internal.apply(func7, data6);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, OUTPUT> func7,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val data6  = __internal.apply(func6, data5);
            val output = __internal.apply(func7, data6);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, DATA7>  func7,
                Func1<? super DATA7, OUTPUT> func8) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val data6  = __internal.apply(func6, data5);
            val data7  = __internal.apply(func7, data6);
            val output = __internal.apply(func8, data7);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, DATA7>  func7,
                Func1<? super DATA7, OUTPUT> func8,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val data6  = __internal.apply(func6, data5);
            val data7  = __internal.apply(func7, data6);
            val output = __internal.apply(func8, data7);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, DATA7>  func7,
                Func1<? super DATA7, DATA8>  func8,
                Func1<? super DATA8, OUTPUT> func9) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val data6  = __internal.apply(func6, data5);
            val data7  = __internal.apply(func7, data6);
            val data8  = __internal.apply(func8, data7);
            val output = __internal.apply(func9, data8);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, DATA7>  func7,
                Func1<? super DATA7, DATA8>  func8,
                Func1<? super DATA8, OUTPUT> func9,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1, input);
            val data2  = __internal.apply(func2, data1);
            val data3  = __internal.apply(func3, data2);
            val data4  = __internal.apply(func4, data3);
            val data5  = __internal.apply(func5, data4);
            val data6  = __internal.apply(func6, data5);
            val data7  = __internal.apply(func7, data6);
            val data8  = __internal.apply(func8, data7);
            val output = __internal.apply(func9, data8);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, OUTPUT> 
        OUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, DATA7>  func7,
                Func1<? super DATA7, DATA8>  func8,
                Func1<? super DATA8, DATA9>  func9,
                Func1<? super DATA9, OUTPUT> func10) {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1,  input);
            val data2  = __internal.apply(func2,  data1);
            val data3  = __internal.apply(func3,  data2);
            val data4  = __internal.apply(func4,  data3);
            val data5  = __internal.apply(func5,  data4);
            val data6  = __internal.apply(func6,  data5);
            val data7  = __internal.apply(func7,  data6);
            val data8  = __internal.apply(func8,  data7);
            val data9  = __internal.apply(func9,  data8);
            val output = __internal.apply(func10, data9);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<? super DATA,  DATA1>  func1,
                Func1<? super DATA1, DATA2>  func2,
                Func1<? super DATA2, DATA3>  func3,
                Func1<? super DATA3, DATA4>  func4,
                Func1<? super DATA4, DATA5>  func5,
                Func1<? super DATA5, DATA6>  func6,
                Func1<? super DATA6, DATA7>  func7,
                Func1<? super DATA7, DATA8>  func8,
                Func1<? super DATA8, DATA9>  func9,
                Func1<? super DATA9, OUTPUT> func10,
                Catch<OUTPUT, FINALOUTPUT, EXCEPTION> catchHandler)
                throws EXCEPTION {
        try {
            val input  = __data();
            val data1  = __internal.apply(func1,  input);
            val data2  = __internal.apply(func2,  data1);
            val data3  = __internal.apply(func3,  data2);
            val data4  = __internal.apply(func4,  data3);
            val data5  = __internal.apply(func5,  data4);
            val data6  = __internal.apply(func6,  data5);
            val data7  = __internal.apply(func7,  data6);
            val data8  = __internal.apply(func8,  data7);
            val data9  = __internal.apply(func9,  data8);
            val output = __internal.apply(func10, data9);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    //== Internal use ==
    
    public static class __internal {
        
        public static <I, O> O apply(Func1<I, O> func, I input) throws Exception {
            if (func == null)
                return null;
            if (input == null) {
                if (!(func instanceof NullSafeOperator))
                    return null;
            }
            return func.applyUnsafe(input);
        }
        
    }
    
}
