// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.pipeable;

import static functionalj.result.Result.Try;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.functions.ThrowFuncs;
import functionalj.result.Result;
import lombok.val;
import nullablej.nullable.Nullable;

// TODO - Think about adding Finally.


//== Internal use ==

class __internal {
  
  public static <I, O> O apply(Func1<I, O> func, I input) throws Exception {
      if (func == null)
          return null;
      if (input == null) {
          if (!(func instanceof NullSafeOperator))
              return null;
      }
      return Func.applyUnsafe(func, input);
  }
  
}

public interface Pipeable<DATA> {
    
    public static <D> Pipeable<D> of(D data) {
        return ()->data;
    }
    public static <D> Pipeable<D> pipable(D data) {
        return ()->data;
    }
    public static <D> Pipeable<D> from(Supplier<D> supplier) {
        return supplier::get;
    }
    public static <D> Pipeable<D> StartWtih(D data) {
        return ()->data;
    }
    public static <D> Pipeable<D> StartBy(Supplier<D> supplier) {
        return supplier::get;
    }
    
    
    public DATA __data() throws Exception;
    
    
    @SuppressWarnings("unchecked")
    public default Nullable<DATA> __nullable() {
        if (this instanceof Result)
            return ((Result<DATA>)this).toNullable();
        
        try {
            return Nullable.of(this.__data());
        } catch (Exception e) {
            return Nullable.empty();
        }
    }
    
    @SuppressWarnings("unchecked")
    public default Optional<DATA> __optional() {
        if (this instanceof Result)
            return ((Result<DATA>)this).toOptional();
        
        try {
            return Optional.ofNullable(this.__data());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    @SuppressWarnings("unchecked")
    public default Result<DATA> __result() {
        if (this instanceof Result)
            return ((Result<DATA>)this);
        
        return Try(this::__data);
    }
    
    @SuppressWarnings("unchecked")
    public default <OUTPUT> Result<OUTPUT> __map(Func1<? super DATA, OUTPUT> func) {
        return ((this instanceof Result) 
                ? ((Result<DATA>)this) 
                : Try(this::__data))
                .map(func);
    }
    
    @SuppressWarnings("unchecked")
    public default Result<DATA> __filter(Predicate<? super DATA> theCondition) {
        return ((this instanceof Result) 
                ? ((Result<DATA>)this) 
                : Try(this::__data))
                .filter(theCondition);
    }
    
    @SuppressWarnings("unchecked")
    public default Result<DATA> __peek(Consumer<? super DATA> theConsumer) {
        return ((this instanceof Result) 
                ? ((Result<DATA>)this) 
                : Try(this::__data))
                .peek(theConsumer);
    }
    
    @SuppressWarnings("unchecked")
    public default DATA __orElse(DATA data) {
        return ((this instanceof Result) 
                ? ((Result<DATA>)this) 
                : Try(this::__data))
                .orElse(data);
    }
    
    @SuppressWarnings("unchecked")
    public default DATA __orGet(Supplier<DATA> dataSupplier) {
        return ((this instanceof Result) 
                ? ((Result<DATA>)this) 
                : Try(this::__data))
                .orGet(dataSupplier);
    }
    
    @SuppressWarnings("unchecked")
    public default DATA __orApply(Func1<Exception, DATA> dataMapper) {
        return ((this instanceof Result) 
                ? ((Result<DATA>)this) 
                : Try(this::__data))
                .orApply(dataMapper);
    }
    
    public default <OUTPUT> 
        OUTPUT pipeTo(Func1<? super DATA, OUTPUT> func1) {
        try {
            val input  = __data();
            val output = __internal.apply(func1, input);
            return output;
        } catch (RuntimeException exception) {
            throw exception;
        } catch (Exception exception) {
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
        OUTPUT pipeTo(
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
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
    
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, OUTPUT> 
        OUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, OUTPUT> func11) {
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, OUTPUT> func11,
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
        OUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, OUTPUT> func12) {
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, OUTPUT> func12,
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
        OUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, OUTPUT> func13) {
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, OUTPUT> func13,
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
        OUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, DATA13> func13,
                Func1<? super DATA13, OUTPUT> func14) {
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, DATA13> func13,
                Func1<? super DATA13, OUTPUT> func14,
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
        OUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, DATA13> func13,
                Func1<? super DATA13, DATA14> func14,
                Func1<? super DATA14, OUTPUT> func15) {
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, DATA13> func13,
                Func1<? super DATA13, DATA14> func14,
                Func1<? super DATA14, OUTPUT> func15,
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
        OUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, DATA13> func13,
                Func1<? super DATA13, DATA14> func14,
                Func1<? super DATA14, DATA15> func15,
                Func1<? super DATA15, OUTPUT> func16) {
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
            throw ThrowFuncs.exceptionTransformer.value().apply(exception);
        }
    }
    
    public default <DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, DATA15, OUTPUT, FINALOUTPUT, EXCEPTION extends Exception> 
        FINALOUTPUT pipeTo(
                Func1<? super DATA,   DATA1>  func1,
                Func1<? super DATA1,  DATA2>  func2,
                Func1<? super DATA2,  DATA3>  func3,
                Func1<? super DATA3,  DATA4>  func4,
                Func1<? super DATA4,  DATA5>  func5,
                Func1<? super DATA5,  DATA6>  func6,
                Func1<? super DATA6,  DATA7>  func7,
                Func1<? super DATA7,  DATA8>  func8,
                Func1<? super DATA8,  DATA9>  func9,
                Func1<? super DATA9,  DATA10> func10,
                Func1<? super DATA10, DATA11> func11,
                Func1<? super DATA11, DATA12> func12,
                Func1<? super DATA12, DATA13> func13,
                Func1<? super DATA13, DATA14> func14,
                Func1<? super DATA14, DATA15> func15,
                Func1<? super DATA15, OUTPUT> func16,
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
    
}
