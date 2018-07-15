package functionalj.pipeable;

import java.util.function.Supplier;

import functionalj.functions.Func1;
import functionalj.functions.FunctionInvocationException;
import lombok.val;

public interface Pipeable<DATA> {
    
    
    public static <D> Pipeable<D> of(D data) {
        return ()->data;
    }
    public static <D> Pipeable<D> from(Supplier<D> supplier) {
        return supplier::get;
    }
    
    
    public DATA __data() throws Exception;
    
    
    public default <OUTPUT> 
        OUTPUT pipe(Func1<DATA, OUTPUT> func1) {
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
            Func1<DATA,   OUTPUT> func1,
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
                Func1<DATA, DATA1>   func1, 
                Func1<DATA1, OUTPUT> func2) {
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
                Func1<DATA, DATA1>   func1, 
                Func1<DATA1, OUTPUT> func2,
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
                Func1<DATA,  DATA1>  func1, 
                Func1<DATA1, DATA2>  func2, 
                Func1<DATA2, OUTPUT> func3) {
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
                Func1<DATA,  DATA1>  func1, 
                Func1<DATA1, DATA2>  func2, 
                Func1<DATA2, OUTPUT> func3,
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
                Func1<DATA,  DATA1>  func1, 
                Func1<DATA1, DATA2>  func2, 
                Func1<DATA2, DATA3>  func3, 
                Func1<DATA3, OUTPUT> func4) {
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
                Func1<DATA,  DATA1>  func1, 
                Func1<DATA1, DATA2>  func2, 
                Func1<DATA2, DATA3>  func3, 
                Func1<DATA3, OUTPUT> func4,
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, OUTPUT> func5) {
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, OUTPUT> func5,
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, OUTPUT> func6) {
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, OUTPUT> func6,
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, OUTPUT> func7) {
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, OUTPUT> func7,
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, DATA7>  func7,
                Func1<DATA7, OUTPUT> func8) {
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, DATA7>  func7,
                Func1<DATA7, OUTPUT> func8,
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, DATA7>  func7,
                Func1<DATA7, DATA8>  func8,
                Func1<DATA8, OUTPUT> func9) {
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, DATA7>  func7,
                Func1<DATA7, DATA8>  func8,
                Func1<DATA8, OUTPUT> func9,
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, DATA7>  func7,
                Func1<DATA7, DATA8>  func8,
                Func1<DATA8, DATA9>  func9,
                Func1<DATA9, OUTPUT> func10) {
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
                Func1<DATA,  DATA1>  func1,
                Func1<DATA1, DATA2>  func2,
                Func1<DATA2, DATA3>  func3,
                Func1<DATA3, DATA4>  func4,
                Func1<DATA4, DATA5>  func5,
                Func1<DATA5, DATA6>  func6,
                Func1<DATA6, DATA7>  func7,
                Func1<DATA7, DATA8>  func8,
                Func1<DATA8, DATA9>  func9,
                Func1<DATA9, OUTPUT> func10,
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
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, OUTPUT> 
        OUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, OUTPUT> func11) {
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
            val data10 = __internal.apply(func10, data9);
            val output = __internal.apply(func11, data10);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, OUTPUT> func11,
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
            val data10 = __internal.apply(func10, data9);
            val output = __internal.apply(func11, data10);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, OUTPUT> 
        OUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, OUTPUT> func12) {
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val output = __internal.apply(func12, data11);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, OUTPUT> func12,
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val output = __internal.apply(func12, data11);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, OUTPUT> 
        OUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, OUTPUT> func13) {
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val output = __internal.apply(func13, data12);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, OUTPUT> func13,
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val output = __internal.apply(func13, data12);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, OUTPUT> 
        OUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, DATA13> func13,
                Func1<DATA13, OUTPUT> func14) {
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val data13 = __internal.apply(func13, data12);
            val output = __internal.apply(func14, data13);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, DATA13> func13,
                Func1<DATA13, OUTPUT> func14,
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val data13 = __internal.apply(func13, data12);
            val output = __internal.apply(func14, data13);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, OUTPUT> 
        OUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, DATA13> func13,
                Func1<DATA13, DATA14> func14,
                Func1<DATA14, OUTPUT> func15) {
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val data13 = __internal.apply(func13, data12);
            val data14 = __internal.apply(func14, data13);
            val output = __internal.apply(func15, data14);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, DATA13> func13,
                Func1<DATA13, DATA14> func14,
                Func1<DATA14, OUTPUT> func15,
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val data13 = __internal.apply(func13, data12);
            val data14 = __internal.apply(func14, data13);
            val output = __internal.apply(func15, data14);
            return catchHandler.doCatch(output, null);
        } catch (Exception e) {
            return catchHandler.doCatch(null, e);
        }
    }
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, DATA15, OUTPUT> 
        OUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, DATA13> func13,
                Func1<DATA13, DATA14> func14,
                Func1<DATA14, DATA15> func15,
                Func1<DATA15, OUTPUT> func16) {
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val data13 = __internal.apply(func13, data12);
            val data14 = __internal.apply(func14, data13);
            val data15 = __internal.apply(func15, data14);
            val output = __internal.apply(func16, data15);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new FunctionInvocationException(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, DATA15, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipe(
                Func1<DATA,   DATA1>  func1,
                Func1<DATA1,  DATA2>  func2,
                Func1<DATA2,  DATA3>  func3,
                Func1<DATA3,  DATA4>  func4,
                Func1<DATA4,  DATA5>  func5,
                Func1<DATA5,  DATA6>  func6,
                Func1<DATA6,  DATA7>  func7,
                Func1<DATA7,  DATA8>  func8,
                Func1<DATA8,  DATA9>  func9,
                Func1<DATA9,  DATA10> func10,
                Func1<DATA10, DATA11> func11,
                Func1<DATA11, DATA12> func12,
                Func1<DATA12, DATA13> func13,
                Func1<DATA13, DATA14> func14,
                Func1<DATA14, DATA15> func15,
                Func1<DATA15, OUTPUT> func16,
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
            val data10 = __internal.apply(func10, data9);
            val data11 = __internal.apply(func11, data10);
            val data12 = __internal.apply(func12, data11);
            val data13 = __internal.apply(func13, data12);
            val data14 = __internal.apply(func14, data13);
            val data15 = __internal.apply(func15, data14);
            val output = __internal.apply(func16, data15);
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
