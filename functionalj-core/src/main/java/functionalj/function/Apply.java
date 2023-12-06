// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongSupplier;
import java.util.function.LongUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import functionalj.lens.lenses.DoubleAccess;
import functionalj.lens.lenses.DoubleToDoubleAccessPrimitive;
import functionalj.lens.lenses.DoubleToIntegerAccessPrimitive;
import functionalj.lens.lenses.DoubleToLongAccessPrimitive;
import functionalj.lens.lenses.IntegerAccess;
import functionalj.lens.lenses.IntegerToDoubleAccessPrimitive;
import functionalj.lens.lenses.IntegerToIntegerAccessPrimitive;
import functionalj.lens.lenses.IntegerToLongAccessPrimitive;
import functionalj.lens.lenses.LongAccess;
import functionalj.lens.lenses.LongToDoubleAccessPrimitive;
import functionalj.lens.lenses.LongToIntegerAccessPrimitive;
import functionalj.lens.lenses.LongToLongAccessPrimitive;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.ref.Ref;
import functionalj.result.Result;
import functionalj.stream.ZipWithOption;
import functionalj.task.Task;
import lombok.val;
import nullablej.nullable.Nullable;

/**
 * The class contains static methods for applying functions.
 */
public interface Apply {
    
    /**
     * Applies a zero-argument function and returns its result.
     * 
     * @param  func the zero-argument function to be applied
     * @return      the result of applying the provided function
     */
    public static <O> O apply(Func0<O> func) {
        return func.get();
    }
    
    /**
     * Retrieves the value referenced by the given Ref object.
     * 
     * @param  ref  the Ref object containing the value to be retrieved
     * @return      the value referenced by the provided Ref object
     */
    public static <O> O apply(Ref<O> ref) {
        return ref.value();
    }
    
    //-- Func1 --
    
    /**
     * Applies a single-argument function to the given input and returns the result.
     * 
     * @param  func   the function to be applied
     * @param  input  the input to the function
     * @return        the result of applying the function to the provided input
     */
    public static <I, O> O apply(Func1<I, O> func, I input) {
        return func.apply(input);
    }
    
    //-- Func2 --
    
    /**
     * Applies a two-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, O> O apply(Func2<I1, I2, O> func, I1 input1, I2 input2) {
        return func.apply(input1, input2);
    }
    
    /**
     * Partially applies a two-argument function to its first argument, returning a function that takes the second argument.
     * 
     * @param  func    the two-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the second argument and returns the result of the original function
     */
    public static <I1, I2, O> Func1<I2, O> apply(Func2<I1, I2, O> func, I1 input1) {
        return input2 -> func.apply(input1, input2);
    }
    
    //-- Func3 --
    
    /**
     * Applies a three-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, O> O apply(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1, input2, input3);
    }
    
    /**
     * Partially applies a three-argument function to its first argument, returning a function that takes the remaining two arguments.
     * 
     * @param  func    the three-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the second and third arguments and returns the result of the original function
     */
    public static <I1, I2, I3, O> Func2<I2, I3, O> apply(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func4 --
    
    /**
     * Applies a four-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @param  input4  the fourth input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, O> O apply(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    /**
     * Partially applies a four-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the four-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> apply(Func4<I1, I2, I3, I4, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func5 --
    
    /**
     * Applies a five-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @param  input4  the fourth input to the function
     * @param  input5  the fifth input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, I5, O> O apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    
    /**
     * Partially applies a four-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the four-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func6 --
    
    /**
     * Applies a six-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @param  input4  the fourth input to the function
     * @param  input5  the fifth input to the function
     * @param  input6  the sixth input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, I5, I6, O> O apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Partially applies a five-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the five-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func7 --
    
    /**
     * Applies a seven-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @param  input4  the fourth input to the function
     * @param  input5  the fifth input to the function
     * @param  input6  the sixth input to the function
     * @param  input7  the seventh input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> O apply(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Partially applies a six-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the six-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> Func6<I2, I3, I4, I5, I6, I7, O> apply(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func8 --
    
    /**
     * Applies a nine-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @param  input4  the fourth input to the function
     * @param  input5  the fifth input to the function
     * @param  input6  the sixth input to the function
     * @param  input7  the seventh input to the function
     * @param  input8  the eighth input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O apply(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Partially applies a seven-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the seven-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func7<I2, I3, I4, I5, I6, I7, I8, O> apply(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func9 --
    
    /**
     * Applies a nine-argument function to the given inputs and returns the result.
     * 
     * @param  func    the function to be applied
     * @param  input1  the first input to the function
     * @param  input2  the second input to the function
     * @param  input3  the third input to the function
     * @param  input4  the fourth input to the function
     * @param  input5  the fifth input to the function
     * @param  input6  the sixth input to the function
     * @param  input7  the seventh input to the function
     * @param  input8  the eighth input to the function
     * @param  input9  the ninth input to the function
     * @return         the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> O apply(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Partially applies a nine-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the nine-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func8<I2, I3, I4, I5, I6, I7, I8, I9, O> apply(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    //-- Func10 --
    
    /**
     * Applies a ten-argument function to the given inputs and returns the result.
     * 
     * @param  func     the function to be applied
     * @param  input1   the first input to the function
     * @param  input2   the second input to the function
     * @param  input3   the third input to the function
     * @param  input4   the fourth input to the function
     * @param  input5   the fifth input to the function
     * @param  input6   the sixth input to the function
     * @param  input7   the seventh input to the function
     * @param  input8   the eighth input to the function
     * @param  input9   the ninth input to the function
     * @param  input10  the tenth input to the function
     * @return          the result of applying the function to the provided inputs
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> O apply(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Partially applies a ten-argument function to its first argument, returning a function that takes the remaining nine arguments.
     * 
     * @param  func    the ten-argument function to be partially applied
     * @param  input1  the first argument to be applied to the function
     * @return         a function that takes the remaining nine arguments and returns the result of the original function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func9<I2, I3, I4, I5, I6, I7, I8, I9, I10, O> apply(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    
    // == $ ==
    
    /**
     * Invokes a Supplier function and returns its result.
     * 
     * @param  func  the Supplier function to be invoked
     * @return       the result of invoking the Supplier function
     */
    public static <O> O $(Supplier<O> func) {
        return func.get();
    }
    
    /**
     * Retrieves the value referenced by the given Ref object.
     * 
     * @param  ref  the Ref object containing the value to be retrieved
     * @return      the value referenced by the provided Ref object
     */
    public static <O> O $(Ref<O> ref) {
        return ref.value();
    }
    
    /**
     * Retrieves the value referenced by the given Ref object.
     * 
     * @param  ref  the Ref object containing the value to be retrieved
     * @return      the value referenced by the provided Ref object
     */
    public static <I, O> O $(Function<I, O> func, I input) {
        return func.apply(input);
    }
    
    public static <I, O> Result<O> $(Function<I, O> func, Result<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I, O> Optional<O> $(Function<I, O> func, Optional<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I, O> Nullable<O> $(Function<I, O> func, Nullable<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I, O> FuncList<O> $(Function<I, O> func, List<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <K, I, O> FuncMap<K, O> $(Function<I, O> func, Map<K, I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I, O> Func0<O> $(Function<I, O> func, Supplier<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <S, I, O> Func1<S, O> $(Function<I, O> func, Function<S, I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I, O> Promise<O> $(Function<I, O> func, HasPromise<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I, O> Task<O> $(Function<I, O> func, Task<I> input) {
        return Func1.from(func).apply(input);
    }
    
    public static <I1, I2, O> O $(BiFunction<I1, I2, O> func, I1 input1, I2 input2) {
        return func.apply(input1, input2);
    }
    
    public static <I1, I2, O> Func1<I2, O> $(BiFunction<I1, I2, O> func, I1 input1) {
        return input2 -> func.apply(input1, input2);
    }
    
    public static <I1, I2, O> Result<O> $(BiFunction<I1, I2, O> func, Result<I1> input1, Result<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, O> Optional<O> $(BiFunction<I1, I2, O> func, Optional<I1> input1, Optional<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, O> Nullable<O> $(BiFunction<I1, I2, O> func, Nullable<I1> input1, Nullable<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, O> FuncList<O> $(BiFunction<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, O> FuncList<O> $(BiFunction<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2, ZipWithOption option) {
        return Func2.from(func).apply(input1, input2, option);
    }
    
    public static <K, I1, I2, O> FuncMap<K, O> $(BiFunction<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <K, I1, I2, O> FuncMap<K, O> $(BiFunction<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2, ZipWithOption option) {
        return Func2.from(func).apply(input1, input2, option);
    }
    
    public static <I1, I2, O> Func0<O> $(BiFunction<I1, I2, O> func, Supplier<I1> input1, Supplier<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, O> Promise<O> $(BiFunction<I1, I2, O> func, HasPromise<I1> input1, HasPromise<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, O> Task<O> $(BiFunction<I1, I2, O> func, Task<I1> input1, Task<I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <S, I1, I2, O> Func1<S, O> $(BiFunction<I1, I2, O> func, Func1<S, I1> input1, Func1<S, I2> input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    public static <I1, I2, I3, O> O $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, O> Func2<I2, I3, O> $(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    public static <I1, I2, I3, O> Func1<I3, O> $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2) {
        return func.apply(input1).apply(input2);
    }
    
    public static <I1, I2, I3, O> Result<O> $(Func3<I1, I2, I3, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, O> Optional<O> $(Func3<I1, I2, I3, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, O> Nullable<O> $(Func3<I1, I2, I3, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, O> Func0<O> $(Func3<I1, I2, I3, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, O> Promise<O> $(Func3<I1, I2, I3, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, O> Task<O> $(Func3<I1, I2, I3, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3) {
        return func.apply(input1, input2, input3);
    }
    
    public static <I1, I2, I3, I4, O> O $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    public static <I1, I2, I3, I4, O> Func2<I3, I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2) {
        return func.apply(input1).apply(input2);
    }
    
    public static <I1, I2, I3, I4, O> Func1<I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1).apply(input2).apply(input3);
    }
    
    public static <I1, I2, I3, I4, O> Result<O> $(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Func0<O> $(Func4<I1, I2, I3, I4, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I, I1, O> Func1<I, O> $(Func1<I1, O> func, Func1<I, I1> input1) {
        return input -> {
            val value1 = input1.apply(input);
            return func.apply(value1);
        };
    }
    
    public static <I, I1, I2, O> Func1<I, O> $(Func2<I1, I2, O> func, Func1<I, I1> input1, Func1<I, I2> input2) {
        return input -> {
            val value1 = input1.apply(input);
            val value2 = input2.apply(input);
            return func.apply(value1, value2);
        };
    }
    
    public static <I, I1, I2, I3, O> Func1<I, O> $(Func3<I1, I2, I3, O> func, Func1<I, I1> input1, Func1<I, I2> input2, Func1<I, I3> input3) {
        return input -> {
            val value1 = input1.apply(input);
            val value2 = input2.apply(input);
            val value3 = input3.apply(input);
            return func.apply(value1, value2, value3);
        };
    }
    
    public static <I, I1, I2, I3, I4, O> Func1<I, O> $(Func4<I1, I2, I3, I4, O> func, Func1<I, I1> input1, Func1<I, I2> input2, Func1<I, I3> input3, Func1<I, I4> input4) {
        return input -> {
            val value1 = input1.apply(input);
            val value2 = input2.apply(input);
            val value3 = input3.apply(input);
            val value4 = input4.apply(input);
            return func.apply(value1, value2, value3, value4);
        };
    }
    
    public static <I, I1, I2, I3, I4, I5, O> Func1<I, O> $(Func5<I1, I2, I3, I4, I5, O> func, Func1<I, I1> input1, Func1<I, I2> input2, Func1<I, I3> input3, Func1<I, I4> input4, Func1<I, I5> input5) {
        return input -> {
            val value1 = input1.apply(input);
            val value2 = input2.apply(input);
            val value3 = input3.apply(input);
            val value4 = input4.apply(input);
            val value5 = input5.apply(input);
            return func.apply(value1, value2, value3, value4, value5);
        };
    }
    
    public static <I, I1, I2, I3, I4, I5, I6, O> Func1<I, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Func1<I, I1> input1, Func1<I, I2> input2, Func1<I, I3> input3, Func1<I, I4> input4, Func1<I, I5> input5, Func1<I, I6> input6) {
        return input -> {
            val value1 = input1.apply(input);
            val value2 = input2.apply(input);
            val value3 = input3.apply(input);
            val value4 = input4.apply(input);
            val value5 = input5.apply(input);
            val value6 = input6.apply(input);
            return func.apply(value1, value2, value3, value4, value5, value6);
        };
    }
    
    public static <I1, I2, I3, I4, I5, O> O $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    public static <I1, I2, I3, I4, I5, O> Func3<I3, I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2) {
        return func.apply(input1).apply(input2);
    }
    
    public static <I1, I2, I3, I4, I5, O> Func2<I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1).apply(input2).apply(input3);
    }
    
    public static <I1, I2, I3, I4, I5, O> Func1<I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1).apply(input2).apply(input3).apply(input4);
    }
    
    public static <I1, I2, I3, I4, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Func0<O> $(Func4<I1, I2, I3, I4, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <S, I1, I2, I3, I4, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> O $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func4<I3, I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2) {
        return func.apply(input1).apply(input2);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func3<I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1).apply(input2).apply(input3);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func2<I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1).apply(input2).apply(input3).apply(input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func1<I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1).apply(input2).apply(input3).apply(input4).apply(input5);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Func0<O> $(Func4<I1, I2, I3, I4, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    public static <S, I1, I2, I3, I4, I5, I6, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
        return func.apply(input1, input2, input3, input4);
    }
    
    // == $$ ==
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <O>   the type of DATA being supplied.
     * @param func  the supplier.
     * @return      the supplied result.
     */
    public static <O> Result<O> $$(Supplier<O> func) {
        return applySafely(func);
    }
    
    /**
     * Obtain the value safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <O>  the data type.
     * 
     * @param ref  the reference.
     * @return     the result of value from the reference.
     */
    public static <O> Result<O> $$(Ref<O> ref) {
        return applySafely(ref);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I>    the input data type.
     * @param <O>    the output data type.
     * 
     * @param func   the function.
     * @param input  the input to be applied to.
     * @return       the output as a result.
     */
    public static <I, O> Result<O> $$(Function<I, O> func, I input) {
        return applySafely(func, input);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, O> Result<O> $$(BiFunction<I1, I2, O> func, I1 input1, I2 input2) {
        return applySafely(func, input1, input2);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, O> Result<O> $$(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return applySafely(func, input1, input2, input3);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, O> Result<O> $$(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return applySafely(func, input1, input2, input3, input4);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, O> Result<O> $$(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return applySafely(func, input1, input2, input3, input4, input5);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $$(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return applySafely(func, input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <O>  the output data type.
     * 
     * @param func  the supplier.
     * @return      the output as a result.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <O> Result<O> applySafely(Supplier<O> func) {
        if (func instanceof Func0)
            return ((Func0) func).applySafely();
        return Result.of(() -> func.get());
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <O>  the output data type.
     * 
     * @param ref  the reference.
     * @return     the output value.
     */
    public static <O> Result<O> applySafely(Ref<O> ref) {
        return ref.asResult();
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I>  the input data type.
     * @param <O>  the output data type.
     * 
     * @param func   the function.
     * @param input  the input to be applied to.
     * @return       the output as a result.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <I, O> Result<O> applySafely(Function<I, O> func, I input) {
        if (func instanceof Func1)
            return ((Func1) func).applySafely(input);
        return Result.of(() -> func.apply(input));
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @return        the output as a result.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <I1, I2, O> Result<O> applySafely(BiFunction<I1, I2, O> func, I1 input1, I2 input2) {
        if (func instanceof Func2)
            return ((Func2) func).applySafely(input1, input2);
        return Result.of(() -> func.apply(input1, input2));
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, O> Result<O> applySafely(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.applySafely(input1, input2, input3);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, O> Result<O> applySafely(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.applySafely(input1, input2, input3, input4);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, O> Result<O> applySafely(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.applySafely(input1, input2, input3, input4, input5);
    }
    
    /**
     * Apply the function safely (return {@code Result&lt;O&gt;}).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> applySafely(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.applySafely(input1, input2, input3, input4, input5, input6);
    }
    
    // == $$$ ==
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <O>  the output data type.
     * 
     * @param func  the supplier.
     * @return      the output as a result.
     * @throws Exception  the exception from the supplier.
     */
    public static <O> O applyUnsafe(Supplier<O> func) throws Exception {
        if (func instanceof Func0)
            return ((Func0<O>) func).applyUnsafe();
        return func.get();
    }
    
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <I>  the input data type.
     * @param <O>  the output data type.
     * 
     * @param func   the function.
     * @param input  the input 1 to be applied to.
     * @return       the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I, O> O $$$(Function<I, O> func, I input) throws Exception {
        if (func instanceof Func1)
            return ((Func1<I, O>) func).applyUnsafe(input);
        return func.apply(input);
    }
    
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, O> O $$$(BiFunction<I1, I2, O> func, I1 input1, I2 input2) throws Exception {
        if (func instanceof Func2)
            return ((Func2<I1, I2, O>) func).applyUnsafe(input1, input2);
        return func.apply(input1, input2);
    }
    
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, O> O $$$(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) throws Exception {
        return func.applyUnsafe(input1, input2, input3);
    }
    
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, O> O $$$(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4);
    }
    
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, O> O $$$(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4, input5);
    }
    
    /**
     * Apply the function safely (might throw exception).
     * 
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <O>    the output data type.
     * 
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, O> O $$$(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4, input5, input6);
    }
    
    // == Access ==
    public static <HOST> Integer access(IntegerAccess<HOST> access, HOST host) {
        return access.apply(host);
    }
    
    public static <HOST> Long access(LongAccess<HOST> access, HOST host) {
        return access.apply(host);
    }
    
    public static <HOST> Double access(DoubleAccess<HOST> access, HOST host) {
        return access.apply(host);
    }
    
    // == Primitive ==
    public static int getPrimitive(IntSupplier supplier) {
        return supplier.getAsInt();
    }
    
    public static long getPrimitive(LongSupplier supplier) {
        return supplier.getAsLong();
    }
    
    public static double getPrimitive(DoubleSupplier supplier) {
        return supplier.getAsDouble();
    }
    
    public static int applyPrimitive(IntUnaryOperator function, int value) {
        return function.applyAsInt(value);
    }
    
    public static long applyPrimitive(LongUnaryOperator function, long value) {
        return function.applyAsLong(value);
    }
    
    public static double applyPrimitive(DoubleUnaryOperator function, double value) {
        return function.applyAsDouble(value);
    }
    
    public static double applyPrimitive(ToDoubleFunction<Integer> function, int value) {
        return function.applyAsDouble(value);
    }
    
    public static double applyPrimitive(ToDoubleFunction<Long> function, long value) {
        return function.applyAsDouble(value);
    }
    
    public static double applyPrimitive(ToDoubleFunction<Double> function, double value) {
        return function.applyAsDouble(value);
    }
    
    public static double applyPrimitive(ToIntFunction<Integer> function, int value) {
        return function.applyAsInt(value);
    }
    
    public static double applyPrimitive(ToIntFunction<Long> function, long value) {
        return function.applyAsInt(value);
    }
    
    public static double applyPrimitive(ToIntFunction<Double> function, double value) {
        return function.applyAsInt(value);
    }
    
    public static <HOST> int applyPrimitive(ToIntFunction<HOST> function, HOST value) {
        return function.applyAsInt(value);
    }
    
    public static <HOST> long applyPrimitive(ToLongFunction<HOST> function, HOST value) {
        return function.applyAsLong(value);
    }
    
    public static <HOST> double applyPrimitive(ToDoubleFunction<HOST> function, HOST value) {
        return function.applyAsDouble(value);
    }
    
    public static int applyPrimitive(IntBinaryOperator function, int value1, int value2) {
        return function.applyAsInt(value1, value2);
    }
    
    public static long applyPrimitive(LongBinaryOperator function, long value1, long value2) {
        return function.applyAsLong(value1, value2);
    }
    
    public static double applyPrimitive(DoubleBinaryOperator function, double value1, double value2) {
        return function.applyAsDouble(value1, value2);
    }
    
    public static double applyPrimitive(ToDoubleBiFunction<Integer, Double> function, int value1, double value2) {
        return function.applyAsDouble(value1, value2);
    }
    
    public static int accessPrimitive(IntegerToIntegerAccessPrimitive access, int host) {
        return access.applyAsInt(host);
    }
    
    public static int accessPrimitive(LongToIntegerAccessPrimitive access, long host) {
        return access.applyLongToInt(host);
    }
    
    public static int accessPrimitive(DoubleToIntegerAccessPrimitive access, double host) {
        return access.applyDoubleToInt(host);
    }
    
    public static long accessPrimitive(IntegerToLongAccessPrimitive access, int host) {
        return access.applyIntToLong(host);
    }
    
    public static long accessPrimitive(LongToLongAccessPrimitive access, long host) {
        return access.applyAsLong(host);
    }
    
    public static long accessPrimitive(DoubleToLongAccessPrimitive access, double host) {
        return access.applyAsLong(host);
    }
    
    public static double accessPrimitive(IntegerToDoubleAccessPrimitive access, int host) {
        return access.applyIntToDouble(host);
    }
    
    public static double accessPrimitive(LongToDoubleAccessPrimitive access, long host) {
        return access.applyLongToDouble(host);
    }
    
    public static double accessPrimitive(DoubleToDoubleAccessPrimitive access, double host) {
        return access.applyAsDouble(host);
    }
}
