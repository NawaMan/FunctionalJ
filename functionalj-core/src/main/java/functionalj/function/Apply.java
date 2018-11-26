package functionalj.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.ref.Ref;
import functionalj.result.Result;

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
    
    public static <O> O $(Supplier<O> func) {
        return func.get();
    }
    public static <O> Result<O> $$(Supplier<O> func) {
        return Result.from(()->func.get());
    }
    public static <O> O $(Ref<O> ref) {
        return ref.value();
    }
    public static <O> Result<O> $$(Ref<O> ref) {
        return ref.asResult();
    }
    
    public static <I, O> O $(Function<I, O> func, I input) {
        return func.apply(input);
    }
    public static <I, O> Result<O> $$(Function<I, O> func, I input) {
        return Result.from(()->func.apply(input));
    }
    
    public static <I1, I2, O> O $(BiFunction<I1, I2, O> func, I1 input1, I2 input2) {
        return func.apply(input1, input2);
    }
    public static <I1, I2, O> Func1<I2, O> $(BiFunction<I1, I2, O> func, I1 input1) {
        return input2 -> func.apply(input1, input2);
    }
    
    public static <I1, I2, I3, O> O $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applyTo(input1, input2, input3);
    }
    public static <I1, I2, I3, O> Result<O> $$(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return Result.from(()->func.applyTo(input1, input2, input3));
    }
    public static <I1, I2, I3, O> Func2<I2, I3, O> $(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.applyTo(input1);
    }
    public static <I1, I2, I3, O> Func1<I3, O> $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2) {
        return func.applyTo(input1).applyTo(input2);
    }
    
    public static <I1, I2, I3, I4, O> O $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    public static <I1, I2, I3, I4, O> Result<O> $$(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return Result.from(()->func.apply(input1, input2, input3, input4));
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
    
    public static <I1, I2, I3, I4, I5, O> O $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    public static <I1, I2, I3, I4, I5, O> Result<O> $$(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return Result.from(()->func.apply(input1, input2, input3, input4, input5));
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
    
    public static <I1, I2, I3, I4, I5, I6, O> O $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $$(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return Result.from(()->func.apply(input1, input2, input3, input4, input5, input6));
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
    
}
