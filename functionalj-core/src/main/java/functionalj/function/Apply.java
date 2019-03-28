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
package functionalj.function;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.ref.Ref;
import functionalj.result.Result;
import functionalj.stream.ZipWithOption;
import functionalj.task.Task;
import nawaman.nullablej.nullable.Nullable;

public interface Apply {
    
    public static <O> O apply(Supplier<O> func) {
        return func.get();
    }
    public static <O> O apply(Ref<O> ref) {
        return ref.value();
    }
    
    public static <I, O> O apply(Function<I, O> func, I input) {
        return func.apply(input);
    }
    
    public static <I1, I2, O> O apply(BiFunction<I1, I2, O> func, I1 input1, I2 input2) {
        return func.apply(input1, input2);
    }
    public static <I1, I2, O> Func1<I2, O> apply(BiFunction<I1, I2, O> func, I1 input1) {
        return input2 -> func.apply(input1, input2);
    }
    
    public static <I1, I2, I3, O> O apply(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Func2<I2, I3, O> apply(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, O> Func1<I3, O> apply(Func3<I1, I2, I3, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    
    public static <I1, I2, I3, I4, O> O apply(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> apply(Func4<I1, I2, I3, I4, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, I4, O> Func2<I3, I4, O> apply(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, I4, O> Func1<I4, O> apply(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3);
    }
    
    public static <I1, I2, I3, I4, I5, O> O apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, I4, I5, O> Func3<I3, I4, I5, O> apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, I4, I5, O> Func2<I4, I5, O> apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3);
    }
    public static <I1, I2, I3, I4, I5, O> Func1<I5, O> apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3).applyTo(input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> O apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func4<I3, I4, I5, I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func3<I4, I5, I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func2<I5, I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3).applyTo(input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func1<I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3).applyTo(input4).applyTo(input5);
    }
    
    //== $ ==
    
    public static <O> O $(Supplier<O> func) {
        return func.get();
    }
    public static <O> O $(Ref<O> ref) {
        return ref.value();
    }
    
    public static <I, O> O $(Function<I, O> func, I input) {
        return func.apply(input);
    }
    public static <I, O> Result<O> $(Function<I, O> func, Result<I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <I, O> Optional<O> $(Function<I, O> func, Optional<I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <I, O> Nullable<O> $(Function<I, O> func, Nullable<I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <I, O> FuncList<O> $(Function<I, O> func, FuncList<I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <K, I, O> FuncMap<K, O> $(Function<I, O> func, FuncMap<K, I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <I, O> Supplier<O> $(Function<I, O> func, Supplier<I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <S, I, O> Function<S, O> $(Function<I, O> func, Function<S, I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <I, O> Promise<O> $(Function<I, O> func, HasPromise<I> input) {
        return Func1.from(func).applyTo(input);
    }
    public static <I, O> Task<O> $(Function<I, O> func, Task<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    public static <I1, I2, O> O $(BiFunction<I1, I2, O> func, I1 input1, I2 input2) {
        return func.apply(input1, input2);
    }
    public static <I1, I2, O> Func1<I2, O> $(BiFunction<I1, I2, O> func, I1 input1) {
        return input2 -> func.apply(input1, input2);
    }
    public static <I1, I2, O> Result<O> $(BiFunction<I1, I2, O> func, Result<I1> input1, Result<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <I1, I2, O> Optional<O> $(BiFunction<I1, I2, O> func, Optional<I1> input1, Optional<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <I1, I2, O> Nullable<O> $(BiFunction<I1, I2, O> func, Nullable<I1> input1, Nullable<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <I1, I2, O> FuncList<O> $(BiFunction<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <I1, I2, O> FuncList<O> $(BiFunction<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2, ZipWithOption option) {
        return Func2.from(func).applyTo(input1, input2, option);
    }
    public static <K, I1, I2, O> FuncMap<K, O> $(BiFunction<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <K, I1, I2, O> FuncMap<K, O> $(BiFunction<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2, ZipWithOption option) {
        return Func2.from(func).applyTo(input1, input2, option);
    }
    public static <I1, I2, O> Supplier<O> $(BiFunction<I1, I2, O> func, Supplier<I1> input1, Supplier<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <I1, I2, O> Promise<O> $(BiFunction<I1, I2, O> func, HasPromise<I1> input1, HasPromise<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <I1, I2, O> Task<O> $(BiFunction<I1, I2, O> func, Task<I1> input1, Task<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    public static <S, I1, I2, O> Func1<S, O> $(BiFunction<I1, I2, O> func, Func1<S, I1> input1, Func1<S, I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    public static <I1, I2, I3, O> O $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Func2<I2, I3, O> $(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, O> Func1<I3, O> $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, O> Result<O> $(Func3<I1, I2, I3, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Optional<O> $(Func3<I1, I2, I3, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Nullable<O> $(Func3<I1, I2, I3, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Supplier<O> $(Func3<I1, I2, I3, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Promise<O> $(Func3<I1, I2, I3, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Task<O> $(Func3<I1, I2, I3, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <S, I1, I2, I3, O> Func1<S, O> $(Func3<I1, I2, I3, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    public static <I1, I2, I3, I4, O> O $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, I4, O> Func2<I3, I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, I4, O> Func1<I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3);
    }
    public static <I1, I2, I3, I4, O> Result<O> $(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Supplier<O> $(Func4<I1, I2, I3, I4, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3, Supplier<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <S, I1, I2, I3, I4, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, O> O $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, I4, I5, O> Func3<I3, I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, I4, I5, O> Func2<I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3);
    }
    public static <I1, I2, I3, I4, I5, O> Func1<I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3).applyTo(input4);
    }
    public static <I1, I2, I3, I4, I5, O> Result<O> $(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> Supplier<O> $(Func4<I1, I2, I3, I4, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3, Supplier<I4> input4, Supplier<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <S, I1, I2, I3, I4, I5, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> O $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func4<I3, I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func3<I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func2<I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3).applyTo(input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Func1<I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.applyTo(input1).applyTo(input2).applyTo(input3).applyTo(input4).applyTo(input5);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Supplier<O> $(Func4<I1, I2, I3, I4, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3, Supplier<I4> input4, Supplier<I5> input5, Supplier<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <S, I1, I2, I3, I4, I5, I6, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    //== $$ ==
    
    public static <O> Result<O> $$(Supplier<O> func) {
        return Result.of(()->func.get());
    }
    public static <O> Result<O> $$(Ref<O> ref) {
        return ref.asResult();
    }
    public static <I, O> Result<O> $$(Function<I, O> func, I input) {
        return Result.of(()->func.apply(input));
    }
    public static <I1, I2, I3, O> Result<O> $$(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return Result.of(()->func.applyTo(input1, input2, input3));
    }
    public static <I1, I2, I3, I4, O> Result<O> $$(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return Result.of(()->func.apply(input1, input2, input3, input4));
    }
    public static <I1, I2, I3, I4, I5, O> Result<O> $$(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return Result.of(()->func.apply(input1, input2, input3, input4, input5));
    }
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $$(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return Result.of(()->func.apply(input1, input2, input3, input4, input5, input6));
    }
    
}
