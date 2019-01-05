package functionalj.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.io.IO;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.ref.Ref;
import functionalj.result.Result;
import functionalj.stream.ZipWithOption;

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
    public static <I, O> IO<O> $(Function<I, O> func, IO<I> input) {
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
    public static <I1, I2, O> IO<O> $(BiFunction<I1, I2, O> func, IO<I1> input1, IO<I2> input2) {
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
    public static <I1, I2, I3, O> Supplier<O> $(Func3<I1, I2, I3, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Promise<O> $(Func3<I1, I2, I3, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> IO<O> $(Func3<I1, I2, I3, O> func, IO<I1> input1, IO<I2> input2, IO<I3> input3) {
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
    public static <I1, I2, I3, I4, O> Supplier<O> $(Func4<I1, I2, I3, I4, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3, Supplier<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> IO<O> $(Func4<I1, I2, I3, I4, O> func, IO<I1> input1, IO<I2> input2, IO<I3> input3, IO<I4> input4) {
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
    public static <I1, I2, I3, I4, I5, O> Supplier<O> $(Func4<I1, I2, I3, I4, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3, Supplier<I4> input4, Supplier<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, O> IO<O> $(Func4<I1, I2, I3, I4, O> func, IO<I1> input1, IO<I2> input2, IO<I3> input3, IO<I4> input4, IO<I5> input5) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Supplier<O> $(Func4<I1, I2, I3, I4, O> func, Supplier<I1> input1, Supplier<I2> input2, Supplier<I3> input3, Supplier<I4> input4, Supplier<I5> input5, Supplier<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, I5, I6, O> IO<O> $(Func4<I1, I2, I3, I4, O> func, IO<I1> input1, IO<I2> input2, IO<I3> input3, IO<I4> input4, IO<I5> input5, IO<I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    public static <S, I1, I2, I3, I4, I5, I6, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    //== $$ ==
    
    public static <O> Result<O> $$(Supplier<O> func) {
        return Result.from(()->func.get());
    }
    public static <O> Result<O> $$(Ref<O> ref) {
        return ref.asResult();
    }
    public static <I, O> Result<O> $$(Function<I, O> func, I input) {
        return Result.from(()->func.apply(input));
    }
    public static <I1, I2, I3, O> Result<O> $$(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return Result.from(()->func.applyTo(input1, input2, input3));
    }
    public static <I1, I2, I3, I4, O> Result<O> $$(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return Result.from(()->func.apply(input1, input2, input3, input4));
    }
    public static <I1, I2, I3, I4, I5, O> Result<O> $$(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return Result.from(()->func.apply(input1, input2, input3, input4, input5));
    }
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $$(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return Result.from(()->func.apply(input1, input2, input3, input4, input5, input6));
    }
    
}
