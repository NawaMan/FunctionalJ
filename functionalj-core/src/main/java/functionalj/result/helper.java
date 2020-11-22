// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.result;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.FuncUnit2;
import lombok.val;

class helper {
    
    static <DATA> Func2<DATA, Exception, Boolean> processIs(Predicate<ResultStatus> statusCheck) {
        return (DATA value, Exception exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            return statusCheck.test(status);
        };
    }
    
    static <DATA> FuncUnit2<DATA, Exception> processIf(Predicate<ResultStatus> statusCheck, Consumer<? super DATA> consumer) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                consumer.accept(value);
        };
    }
    static <DATA> FuncUnit2<DATA, Exception> processIf(Predicate<ResultStatus> statusCheck, BiConsumer<? super DATA, ? super Exception> consumer) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                consumer.accept(value, exception);
        };
    }
    static <DATA> FuncUnit2<DATA, Exception> processIf(Predicate<ResultStatus> statusCheck, Runnable runnable) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                runnable.run();
        };
    }
    static <DATA> FuncUnit2<DATA, Exception> processIfException(Predicate<ResultStatus> statusCheck, Consumer<? super Exception> consumer) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                consumer.accept(exception);
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenUse(
            Predicate<ResultStatus> statusCheck,
            Result<DATA>            result,
            DATA                    fallbackValue) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.valueOf(fallbackValue);
            
            return result;
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenGet(
            Predicate<ResultStatus>  statusCheck,
            Result<DATA>             result,
            Supplier<? extends DATA> fallbackSupplier) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.of(Func0.from(fallbackSupplier));
            
            return result;
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenApply(
            Predicate<ResultStatus>                  statusCheck,
            Result<DATA>                             result,
            Func1<? super Exception, ? extends DATA> recoverFunction) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.of(()->recoverFunction.apply(exception));
            
            return result;
        };
    }
    
    static <DATA> Func2<DATA, Exception, Result<DATA>> processWhenApply(
            Predicate<ResultStatus>                        statusCheck,
            Result<DATA>                                   result,
            Func2<DATA, ? super Exception, ? extends DATA> recoverFunction) {
        return (value, exception) -> {
            var status = ResultStatus.getStatus(value, exception);
            if (statusCheck.test(status))
                return Result.of(()->recoverFunction.apply(value, exception));
            
            return result;
        };
    }
    
}
