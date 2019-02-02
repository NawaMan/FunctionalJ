// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.io;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.function.Func3;
import functionalj.function.Func4;
import functionalj.function.Func5;
import functionalj.function.Func6;
import functionalj.function.FuncUnit1;
import functionalj.function.Named;
import functionalj.io.IOs.IOCached;
import functionalj.io.IOs.IOCachedFor;
import functionalj.io.IOs.IOChain;
import functionalj.io.IOs.IOFilter;
import functionalj.io.IOs.IOMap;
import functionalj.io.IOs.IOMerge2;
import functionalj.io.IOs.IOMerge3;
import functionalj.io.IOs.IOMerge4;
import functionalj.io.IOs.IOMerge5;
import functionalj.io.IOs.IOMerge6;
import functionalj.io.IOs.IOResult;
import functionalj.io.IOs.IOPromise;
import functionalj.io.IOs.IOSupplier;
import functionalj.io.IOs.IOValue;
import functionalj.io.IOs.IORace;
import functionalj.io.IOs.IOPeek;
import functionalj.list.FuncList;
import functionalj.promise.DeferAction;
import functionalj.promise.Promise;
import functionalj.result.Result;

@FunctionalInterface
public interface IO<DATA> {
    
    public DeferAction<DATA> createAction();
    
    
    public static <D> IO<D> ofValue(D value) {
        return new IOValue<>(value);
    }
    public static <D> IO<D> from(Supplier<D> supplier) {
        return new IOSupplier<>(supplier);
    }
    public static <D> IO<D> from(Result<D> result) {
        return new IOResult<>(result);
    }
    public static <D> IO<D> from(Promise<D> promise) {
        return new IOPromise<>(promise);
    }
    
    public static <I1, I2, D> IO<D> from(
            IO<I1>           io1,
            IO<I2>           io2,
            Func2<I1, I2, D> merger) {
        return new IOMerge2<>(io1, io2, merger);
    }
    public static <I1, I2, I3, D> IO<D> from(
            IO<I1>               io1,
            IO<I2>               io2,
            IO<I3>               io3,
            Func3<I1, I2, I3, D> merger) {
        return new IOMerge3<>(io1, io2, io3, merger);
    }
    public static <I1, I2, I3, I4, D> IO<D> from(
            IO<I1>                   io1,
            IO<I2>                   io2,
            IO<I3>                   io3,
            IO<I4>                   io4,
            Func4<I1, I2, I3, I4, D> merger) {
        return new IOMerge4<>(io1, io2, io3, io4, merger);
    }
    public static <I1, I2, I3, I4, I5, D> IO<D> from(
            IO<I1>                       io1,
            IO<I2>                       io2,
            IO<I3>                       io3,
            IO<I4>                       io4,
            IO<I5>                       io5,
            Func5<I1, I2, I3, I4, I5, D> merger) {
        return new IOMerge5<>(io1, io2, io3, io4, io5, merger);
    }
    public static <I1, I2, I3, I4, I5, I6, D> IO<D> from(
            IO<I1>                           io1,
            IO<I2>                           io2,
            IO<I3>                           io3,
            IO<I4>                           io4,
            IO<I5>                           io5,
            IO<I6>                           io6,
            Func6<I1, I2, I3, I4, I5, I6, D> merger) {
        return new IOMerge6<>(io1, io2, io3, io4, io5, io6, merger);
    }
    
    @SafeVarargs
    public static <D> IO<D> firstOf(IO<D> ... ios) {
        return new IORace<D>(FuncList.from(ios));
    }
    public static <D> IO<D> firstFrom(List<? extends IO<D>> ios) {
        return new IORace<D>(FuncList.from(ios));
    }
    
    public static <D> IO<D> doUntil(IO<D> body, Predicate<Result<D>> breakCondition) {
        return new IOs.IODoUntil<D>(body, breakCondition);
    }
    
    // TODO - Do while that has IO<Boolean> as a way to check if the loop should still continue.
    
    public default IO<DATA> peek(FuncUnit1<? super DATA> keeper) {
        return new IOPeek<>(this, keeper);
    }
    
    public default <TARGET> IO<TARGET> map(Func1<? super DATA, TARGET> mapper) {
        return new IOMap<>(this, mapper);
    }
    
    public default <TARGET> IO<TARGET> flatMap(Func1<? super DATA, IO<? extends TARGET>> mapper) {
        return new IOChain<>(this, mapper);
    }
    
    public default IO<DATA> filter(Predicate<? super DATA> predicate) {
        return new IOFilter<>(this, predicate);
    }
    
    public default <S> IO<DATA> cached() {
        return new IOCached<>(this);
    }
    public default <S> IO<DATA> cached(Supplier<S> theContext) {
        return cached(theContext, Named.BiPredicate("when-change", (o,n)->!Objects.equals(o, n)));
    }
    public default <S> IO<DATA> cached(Supplier<S> contextSupplier, BiPredicate<S, S> staleChecker) {
        return new IOCachedFor<>(this, contextSupplier, staleChecker);
    }
    
}
