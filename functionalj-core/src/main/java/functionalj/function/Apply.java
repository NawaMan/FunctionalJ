// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import nullablej.nullable.Nullable;

/**
 * The class contains static methods for applying functions.
 */
public interface Apply {
    
    /**
     * Retrieves the value held by a {@link HasPromise}.
     *
     * @param <O>         the type of the value.
     * @param hasPromise  the value that has a promise containing the value
     * @return            the value
     */
    public static <O> O apply(HasPromise<O> hasPromise) {
        if (hasPromise instanceof Func0)
            return ((Func0<O>)hasPromise).get();
        if (hasPromise instanceof Ref)
            return ((Ref<O>)hasPromise).get();
        if (hasPromise instanceof Result)
            return ((Result<O>)hasPromise).get();
        if (hasPromise instanceof Promise)
            return ((Promise<O>)hasPromise).get();
        
        return hasPromise.getResult().get();
    }
    
    //-- Func1 --
    
    /**
     * Applies a {@link Func1} function to a given input and returns the result.
     *
     * @param <I>   the type of the input to the function
     * @param <O>   the type of the output returned by the function
     * @param func  the Func1 function to be applied
     * @param input the input value to be passed to the function
     * @return      the result of applying the {@link Func1} function to the given input
     */
    public static <I, O> O apply(Func1<I, O> func, I input) {
        return Func1.from(func).apply(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link Result} if it exists,
     * wrapping the result or any exception in a new {@link Result}.
     *
     * @param <I>    the type of the input inside the {@link Result}
     * @param <O>    the type of the output to be contained in the resulting {@link Result}
     * @param func   the {@link Func1} function to be applied to the input value
     * @param input  the {@link Result} containing the input value or an exception
     * @return       a {@link Result} containing either the function's output or any exception thrown
     */
    public static <I, O> Result<O> apply(Func1<I, O> func, Result<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside an {@link Optional}, if present,
     * and returns an Optional containing the result.
     *
     * @param <I>    the type of the input inside the {@link Optional}
     * @param <O>    the type of the output to be wrapped in the resulting {@link Optional}
     * @param func   the {@link Func1} function to be applied to the input value, if present
     * @param input  the {@link Optional} containing the input value
     * @return       an {@link Optional} containing the result of applying the function, or empty if input is empty
     */
    public static <I, O> Optional<O> applyWith(Func1<I, O> func, Optional<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link Nullable}, if not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I>    the type of the input within the {@link Nullable}
     * @param <O>    the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func   the {@link Func1} function to be applied to the input value, if not null
     * @param input  the {@link Nullable} containing the input value
     * @return       a {@link Nullable} containing the result of applying the function, or null if input is null
     */
    public static <I, O> Nullable<O> applyWith(Func1<I, O> func, Nullable<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to each element in a list and returns a {@link FuncList} 
     * containing the results.
     *
     * @param <I>    the type of elements in the input list
     * @param <O>    the type of elements in the resulting {@link FuncList}
     * @param func   the {@link Func1} function to be applied to each element of the list
     * @param input  the list of elements to be processed
     * @return       a {@link FuncList} containing the results of applying the function to each list element
     */
    public static <I, O> FuncList<O> applyWith(Func1<I, O> func, List<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the values of a {@link Map} and returns a {@link FuncMap} 
     * with the same keys and transformed values.
     *
     * @param <K>    the type of the keys in both the input and resulting {@link FuncMap}
     * @param <I>    the type of the values in the input {@link Map}
     * @param <O>    the type of the values in the resulting {@link FuncMap}
     * @param func   the Func1 function to be applied to each value in the input {@link Map}
     * @param input  the {@link Map} whose values are to be transformed
     * @return       a FuncMap containing the keys from the input {@link Map} and values obtained 
     *                 by applying the function to each input value
     */
    public static <K, I, O> FuncMap<K, O> applyWith(Func1<I, O> func, Map<K, I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the result of a {@link Func0} and returns a {@link Func0}
     * containing the transformed result.
     *
     * @param <I>    the type of the input produced by the {@link Func0}
     * @param <O>    the type of the output to be contained in the resulting {@link Func0}
     * @param func   the {@link Func1} function to be applied to the result of the {@link Func0}
     * @param input  the {@link Func0} whose result will be transformed by the function
     * @return       a {@link Func0} containing the result obtained by applying the function to the input {@link Func0}'s result
     */
    public static <I, O> Func0<O> applyWith(Func1<I, O> func, Func0<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Composes two {@link Func1} functions to create a new {@link Func1} function.
     *
     * @param <S>    the type of the input parameter for the first {@link Func1}
     * @param <I>    the type of the output of the first Func1 and the input of the second {@link Func1}
     * @param <O>    the type of the output produced by the second {@link Func1}
     * @param func   the second {@link Func1} function to be applied
     * @param input  the first {@link Func1} function whose output will be the input to the second function
     * @return       a Func1 function that applies the first function and then the second function to its input
     */
    public static <S, I, O> Func1<S, O> applyWith(Func1<I, O> func, Func1<S, I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link HasPromise}, if present,
     * and returns a {@link Promise} containing the result.
     *
     * @param <I>    the type of the input value within the {@link HasPromise}
     * @param <O>    the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func   the {@link Func1} function to be applied to the input value, if present
     * @param input  the {@link HasPromise} containing the input value
     * @return       a {@link Promise} containing the result of applying the function, or a pending {@link Promise} if input is not ready
     */
    public static <I, O> Promise<O> applyWith(Func1<I, O> func, HasPromise<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link Task} if it becomes available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I>    the type of the input value within the {@link Task}
     * @param <O>    the type of the output to be encapsulated in the resulting {@link Task}
     * @param func   the {@link Func1} function to be applied to the input value, when available
     * @param input  the {@link Task} supplying the input value
     * @return       a {@link Task} that will contain the result of applying the function, once available
     */
    public static <I, O> Task<O> applyWith(Func1<I, O> func, Task<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    //-- Func2 --
    
    /**
     * Invokes a {@link Func2} function with two input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func2} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @return        the result of invoking the {@link Func2} function with the given input parameters
     */
    public static <I1, I2, O> O apply(Func2<I1, I2, O> func, I1 input1, I2 input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    /**
     * Partially applies a {@link Func2} function by fixing the first input parameter, 
     * resulting in a new {@link Func1} function.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <O>     the type of the output produced by the resulting Func1
     * @param func    the {@link Func2} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link Func1} function that takes the second input parameter and applies 
     *                the original {@link Func2} function with the fixed first input parameter
     */
    public static <I1, I2, O> Func1<I2, O> apply(Func2<I1, I2, O> func, I1 input1) {
        return Func2.from(func).apply(input1);
    }
    
    /**
     * Applies a {@link Func2} function to the values inside two {@link Result} objects if both values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Result}
     * @param func    the {@link Func2} function to be applied to the input values, if both values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if either input is an exception
     */
    public static <I1, I2, O> Result<O> applyTo(Func2<I1, I2, O> func, Result<I1> input1, Result<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to the values inside two {@link Optional} objects if both values are present,
     * and returns an {@link Optional} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Optional}
     * @param <I2>    the type of the value inside the second {@link Optional}
     * @param <O>     the type of the output to be wrapped in the resulting {@link Optional}
     * @param func    the {@link Func2} function to be applied to the input values, if both are present
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @return        an {@link Optional} containing the result of applying the function, or empty if either input is empty
     */
    public static <I1, I2, O> Optional<O> applyTo(Func2<I1, I2, O> func, Optional<I1> input1, Optional<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to the values inside two {@link Nullable} objects if both values are not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Nullable}
     * @param <I2>    the type of the value inside the second {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func2} function to be applied to the input values, if both are not null
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @return        a {@link Nullable} containing the result of applying the function, or null if either input is null
     */
    public static <I1, I2, O> Nullable<O> applyTo(Func2<I1, I2, O> func, Nullable<I1> input1, Nullable<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    //-- Zip --
    
    /**
     * Applies a {@link Func2} function to corresponding elements from two {@link FuncList} objects and returns a new
     * {@link FuncList} containing the results.
     *
     * @param <I1>    the type of elements in the first {@link FuncList}
     * @param <I2>    the type of elements in the second {@link FuncList}
     * @param <O>     the type of elements in the resulting {@link FuncList}
     * @param func    the {@link Func2} function to be applied to corresponding elements
     * @param input1  the first {@link FuncList} of input elements
     * @param input2  the second {@link FuncList} of input elements
     * @return        a {@link FuncList} containing the results of applying the function to corresponding elements
     */
    public static <I1, I2, O> FuncList<O> applyEachOf(Func2<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2) {
        return Func2.from(func).applyEachOf(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding elements from two {@link FuncList} objects and returns a new
     * {@link FuncList} containing the results, with an option to specify the behavior when the input lists have different sizes.
     *
     * @param <I1>    the type of elements in the first {@link FuncList}
     * @param <I2>    the type of elements in the second {@link FuncList}
     * @param <O>     the type of elements in the resulting {@link FuncList}
     * @param func    the {@link Func2} function to be applied to corresponding elements
     * @param input1  the first {@link FuncList} of input elements
     * @param input2  the second {@link FuncList} of input elements
     * @param option  the option specifying how to handle input lists of different sizes
     * @return        a {@link FuncList} containing the results of applying the function to corresponding elements
     */
    public static <I1, I2, O> FuncList<O> applyEachOf(Func2<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2, ZipWithOption option) {
        return Func2.from(func).applyEachOf(input1, input2, option);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding values from two {@link FuncMap} objects and returns a new
     * {@link FuncMap} containing the results, using the keys present in both input maps.
     *
     * @param <K>     the type of keys in the input and resulting {@link FuncMap}
     * @param <I1>    the type of values in the first input {@link FuncMap}
     * @param <I2>    the type of values in the second input {@link FuncMap}
     * @param <O>     the type of values in the resulting {@link FuncMap}
     * @param func    the {@link Func2} function to be applied to corresponding values
     * @param input1  the first input {@link FuncMap}
     * @param input2  the second input {@link FuncMap}
     * @return        a {@link FuncMap} containing the results of applying the function to corresponding values with matching keys
     */
    public static <K, I1, I2, O> FuncMap<K, O> applyEachOf(Func2<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2) {
        return Func2.from(func).applyEachOf(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding values from two {@link FuncMap} objects and returns a new
     * {@link FuncMap} containing the results, with an option to specify the behavior when the input maps have different keys.
     *
     * @param <K>     the type of keys in the input and resulting {@link FuncMap}
     * @param <I1>    the type of values in the first input {@link FuncMap}
     * @param <I2>    the type of values in the second input {@link FuncMap}
     * @param <O>     the type of values in the resulting {@link FuncMap}
     * @param func    the {@link Func2} function to be applied to corresponding values
     * @param input1  the first input {@link FuncMap}
     * @param input2  the second input {@link FuncMap}
     * @param option  the option specifying how to handle input maps with different keys
     * @return        a {@link FuncMap} containing the results of applying the function to corresponding values
     *                  with matching keys or based on the specified option
     */
    public static <K, I1, I2, O> FuncMap<K, O> applyEachOf(Func2<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2, ZipWithOption option) {
        return Func2.from(func).applyEachOf(input1, input2, option);
    }
    
    /**
     * Applies a {@link Func2} function to values extracted from two {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <O>     the type of the output to be contained in the resulting {@link Func0}
     * @param func    the {@link Func2} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, O> Func0<O> applyTo(Func2<I1, I2, O> func, Func0<I1> input1, Func0<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Partially applies a {@link Func2} function by fixing two input parameters with values obtained from
     * two {@link Func1} functions, resulting in a new {@link Func1} function.
     *
     * @param <S>     the type of the shared input parameter for both input1 and input2 functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <O>     the type of the output produced by the resulting {@link Func1}
     * @param func    the {@link Func2} function to be partially applied
     * @param input1  the first {@link Func1} function producing the first input value
     * @param input2  the second {@link Func1} function producing the second input value
     * @return        a new {@link Func1} function that applies the original {@link Func2} function with the two fixed input parameters
     */
    public static <S, I1, I2, O> Func1<S, O> applyTo(Func2<I1, I2, O> func, Func1<S, I1> input1, Func1<S, I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to values inside two {@link HasPromise} objects, if both values become available,
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link HasPromise}
     * @param <I2>    the type of the value inside the second {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func2} function to be applied to the input values, if both values are ready
     * @param input1  the {@link HasPromise} containing the first input value
     * @param input2  the {@link HasPromise} containing the second input value
     * @return        a {@link Promise} containing the result of applying the function, once both input values are ready
     */
    public static <I1, I2, O> Promise<O> applyTo(Func2<I1, I2, O> func, HasPromise<I1> input1, HasPromise<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to values inside two {@link Task} objects, if both values become available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Task}
     * @param <I2>    the type of the value inside the second {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func2} function to be applied to the input values, if both values are ready
     * @param input1  the {@link Task} containing the first input value
     * @param input2  the {@link Task} containing the second input value
     * @return        a {@link Task} containing the result of applying the function, once both input values are ready
     */
    public static <I1, I2, O> Task<O> applyTo(Func2<I1, I2, O> func, Task<I1> input1, Task<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    //-- Func3 --
    
    /**
     * Invokes a {@link Func3} function with three input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func3} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @param input3  the third input parameter for the function
     * @return        the result of invoking the {@link Func3} function with the given input parameters
     */
    public static <I1, I2, I3, O> O apply(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1, input2, input3);
    }
    
    /**
     * Partially applies a {@link Func3} function by fixing the first input parameter, 
     * resulting in a new {@link Func2} function that takes the remaining two parameters.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <O>     the type of the output produced by the resulting {@link Func2}
     * @param func    the {@link Func3} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link Func2} function that takes the remaining two parameters
     *                and applies the original {@link Func3} function with the fixed first input parameter
     */
    public static <I1, I2, I3, O> Func2<I2, I3, O> apply(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Result} objects if all values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <I3>    the type of the value inside the third {@link Result}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Result}
     * @param func    the {@link Func3} function to be applied to the input values, if all values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if any input is an exception
     */
    public static <I1, I2, I3, O> Result<O> applyWith(Func3<I1, I2, I3, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Optional} objects if all values are present,
     * and returns an {@link Optional} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Optional}
     * @param <I2>    the type of the value inside the second {@link Optional}
     * @param <I3>    the type of the value inside the third {@link Optional}
     * @param <O>     the type of the output to be wrapped in the resulting {@link Optional}
     * @param func    the {@link Func3} function to be applied to the input values, if all are present
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @return        an {@link Optional} containing the result of applying the function, or empty if any input is empty
     */
    public static <I1, I2, I3, O> Optional<O> applyWith(Func3<I1, I2, I3, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Nullable} objects if all values are not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Nullable}
     * @param <I2>    the type of the value inside the second {@link Nullable}
     * @param <I3>    the type of the value inside the third {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func3} function to be applied to the input values, if all are not null
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @return        a {@link Nullable} containing the result of applying the function, or null if any input is null
     */
    public static <I1, I2, I3, O> Nullable<O> applyWith(Func3<I1, I2, I3, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values extracted from three {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <I3>    the type of the value supplied by the third {@link Func0}
     * @param <O>     the type of the output to be contained in the resulting {@link Func0}
     * @param func    the {@link Func3} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @param input3  the third {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, I3, O> Func0<O> applyWith(Func3<I1, I2, I3, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Partially applies a {@link Func3} function by fixing three input parameters with values obtained from
     * three {@link Func1} functions, resulting in a new {@link Func1} function.
     *
     * @param <S>     the type of the shared input parameter for all input functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <I3>    the type of the value produced by the third input3 function
     * @param <O>     the type of the output produced by the resulting Func1
     * @param func    the {@link Func3} function to be partially applied
     * @param input1  the first input1 function producing the first input value
     * @param input2  the second input2 function producing the second input value
     * @param input3  the third input3 function producing the third input value
     * @return        a new {@link Func1} function that applies the original {@link Func3} function with the three fixed input parameters
     */
    public static <S, I1, I2, I3, O> Func1<S, O> applyWith(Func3<I1, I2, I3, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link HasPromise} objects, if all values become available,
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link HasPromise}
     * @param <I2>    the type of the value inside the second {@link HasPromise}
     * @param <I3>    the type of the value inside the third {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func3} function to be applied to the input values, if all values are ready
     * @param input1  the {@link HasPromise} containing the first input value
     * @param input2  the {@link HasPromise} containing the second input value
     * @param input3  the {@link HasPromise} containing the third input value
     * @return        a {@link Promise} containing the result of applying the function, once all input values are ready
     */
    public static <I1, I2, I3, O> Promise<O> applyWith(Func3<I1, I2, I3, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Task} objects, if all values become available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Task}
     * @param <I2>    the type of the value inside the second {@link Task}
     * @param <I3>    the type of the value inside the third {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func3} function to be applied to the input values, if all values are ready
     * @param input1  the {@link Task} containing the first input value
     * @param input2  the {@link Task} containing the second input value
     * @param input3  the {@link Task} containing the third input value
     * @return        a {@link Task} containing the result of applying the function, once all input values are ready
     */
    public static <I1, I2, I3, O> Task<O> applyWith(Func3<I1, I2, I3, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    //-- Func4 --
    
    /**
     * Invokes a {@link Func4} function with four input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <I4>    the type of the fourth input parameter
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func4} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @param input3  the third input parameter for the function
     * @param input4  the fourth input parameter for the function
     * @return        the result of invoking the {@link Func4} function with the given input parameters
     */
    public static <I1, I2, I3, I4, O> O apply(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    /**
     * Partially applies a {@link Func4} function by fixing the first input parameter, 
     * resulting in a new {@link Func3} function that takes the remaining three parameters.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <I4>    the type of the fourth input parameter
     * @param <O>     the type of the output produced by the resulting {@link Func3}
     * @param func    the {@link Func4} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link Func3} function that takes the remaining three parameters
     *                  and applies the original {@link Func4} function with the fixed first input parameter
     */
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> apply(Func4<I1, I2, I3, I4, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func4} function to values inside four {@link Result} objects if all values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <I3>    the type of the value inside the third {@link Result}
     * @param <I4>    the type of the value inside the fourth {@link Result}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Result}
     * @param func    the {@link Func4} function to be applied to the input values, if all values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if any input is an exception
     */
    public static <I1, I2, I3, I4, O> Result<O> applyWith(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values inside four {@link Optional} objects if all values are present,
     * and returns an {@link Optional} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Optional}
     * @param <I2>    the type of the value inside the second {@link Optional}
     * @param <I3>    the type of the value inside the third {@link Optional}
     * @param <I4>    the type of the value inside the fourth {@link Optional}
     * @param <O>     the type of the output to be wrapped in the resulting {@link Optional}
     * @param func    the {@link Func4} function to be applied to the input values, if all are present
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @param input4  the {@link Optional} containing the fourth input value
     * @return        an {@link Optional} containing the result of applying the function, or empty if any input is empty
     */
    public static <I1, I2, I3, I4, O> Optional<O> applyWith(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values inside four {@link Nullable} objects if all values are not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Nullable}
     * @param <I2>    the type of the value inside the second {@link Nullable}
     * @param <I3>    the type of the value inside the third {@link Nullable}
     * @param <I4>    the type of the value inside the fourth {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func4} function to be applied to the input values, if all are not null
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @return        a {@link Nullable} containing the result of applying the function, or null if any input is null
     */
    public static <I1, I2, I3, I4, O> Nullable<O> applyWith(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values extracted from four {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <I3>    the type of the value supplied by the third {@link Func0}
     * @param <I4>    the type of the value supplied by the fourth {@link Func0}
     * @param <O>     the type of the output to be contained in the resulting {@link Func0}
     * @param func    the {@link Func4} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @param input3  the third {@link Func0} supplier
     * @param input4  the fourth {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, I3, I4, O> Func0<O> applyWith(Func4<I1, I2, I3, I4, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values obtained from four {@link Func1} functions and returns a new {@link Func1}
     * function that takes a shared input parameter and applies the original Func4 function with the values from the input functions.
     *
     * @param <S>     the type of the shared input parameter for all input functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <I3>    the type of the value produced by the third input3 function
     * @param <I4>    the type of the value produced by the fourth input4 function
     * @param <O>     the type of the output produced by the resulting {@link Func1}
     * @param func    the {@link Func4} function to be applied to the input values obtained from the input functions
     * @param input1  the first input1 function producing the first input value
     * @param input2  the second input2 function producing the second input value
     * @param input3  the third input3 function producing the third input value
     * @param input4  the fourth input4 function producing the fourth input value
     * @return        a new {@link Func1} function that applies the original {@link Func4} function with the input values obtained from the input functions
     */
    public static <S, I1, I2, I3, I4, O> Func1<S, O> applyWith(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values obtained from four objects implementing {@link HasPromise},
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link HasPromise}
     * @param <I2>    the type of the value obtained from the second {@link HasPromise}
     * @param <I3>    the type of the value obtained from the third {@link HasPromise}
     * @param <I4>    the type of the value obtained from the fourth {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func4} function to be applied to the input values obtained from the {@link HasPromise} objects
     * @param input1  the first {@link HasPromise} object providing the first input value
     * @param input2  the second {@link HasPromise} object providing the second input value
     * @param input3  the third {@link HasPromise} object providing the third input value
     * @param input4  the fourth {@link HasPromise} object providing the fourth input value
     * @return        a {@link Promise} containing the result of applying the function to the input values obtained from the {@link HasPromise} objects
     */
    public static <I1, I2, I3, I4, O> Promise<O> applyWith(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values obtained from four {@link Task} objects and returns a new {@link Task}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Task}
     * @param <I2>    the type of the value obtained from the second {@link Task}
     * @param <I3>    the type of the value obtained from the third {@link Task}
     * @param <I4>    the type of the value obtained from the fourth {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func4} function to be applied to the input values obtained from the {@link Task} objects
     * @param input1  the first {@link Task} providing the first input value
     * @param input2  the second {@link Task} providing the second input value
     * @param input3  the third {@link Task} providing the third input value
     * @param input4  the fourth {@link Task} providing the fourth input value
     * @return        a {@link Task} containing the result of applying the function to the input values obtained from the {@link Task} objects
     */
    public static <I1, I2, I3, I4, O> Task<O> applyWith(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    //-- Func5 --
    
    /**
     * Applies a {@link Func5} function to values provided as input parameters and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func5} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @return        the result of applying the function to the input values
     */
    public static <I1, I2, I3, I4, I5, O> O apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from the function's input1 parameter and returns a new {@link Func4}
     * function that takes four input parameters of types I2, I3, I4, and I5 and applies the original {@link Func5} function with them.
     *
     * @param <I1>    the type of the value obtained from the input1 parameter of the {@link Func5} function
     * @param <I2>    the type of the first input value for the resulting {@link Func4} function
     * @param <I3>    the type of the second input value for the resulting {@link Func4} function
     * @param <I4>    the type of the third input value for the resulting {@link Func4} function
     * @param <I5>    the type of the fourth input value for the resulting {@link Func4} function
     * @param <O>     the type of the output produced by the resulting {@link Func4} function
     * @param func    the {@link Func5} function to be applied to the input1 value
     * @param input1  the input value for the {@link Func5} function
     * @return        a new {@link Func4} function that applies the original Func5 function with the specified input value
     */
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> apply(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Optional} objects and returns a new {@link Optional}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Optional}
     * @param <I2>    the type of the value obtained from the second {@link Optional}
     * @param <I3>    the type of the value obtained from the third {@link Optional}
     * @param <I4>    the type of the value obtained from the fourth {@link Optional}
     * @param <I5>    the type of the value obtained from the fifth {@link Optional}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Optional}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Optional} objects
     * @param input1  the first {@link Optional} providing the first input value
     * @param input2  the second {@link Optional} providing the second input value
     * @param input3  the third {@link Optional} providing the third input value
     * @param input4  the fourth {@link Optional} providing the fourth input value
     * @param input5  the fifth {@link Optional} providing the fifth input value
     * @return        an {@link Optional} containing the result of applying the function to the input values obtained from the {@link Optional} objects
     */
    public static <I1, I2, I3, I4, I5, O> Optional<O> applyWith(Func5<I1, I2, I3, I4, I5, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Nullable} objects and returns a new {@link Nullable}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Nullable}
     * @param <I2>    the type of the value obtained from the second {@link Nullable}
     * @param <I3>    the type of the value obtained from the third {@link Nullable}
     * @param <I4>    the type of the value obtained from the fourth {@link Nullable}
     * @param <I5>    the type of the value obtained from the fifth {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Nullable} objects
     * @param input1  the first {@link Nullable} providing the first input value
     * @param input2  the second {@link Nullable} providing the second input value
     * @param input3  the third {@link Nullable} providing the third input value
     * @param input4  the fourth {@link Nullable} providing the fourth input value
     * @param input5  the fifth {@link Nullable} providing the fifth input value
     * @return        a {@link Nullable} containing the result of applying the function to the input values obtained from the {@link Nullable} objects
     */
    public static <I1, I2, I3, I4, I5, O> Nullable<O> applyWith(Func5<I1, I2, I3, I4, I5, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Func0} objects and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Func0}
     * @param <I2>    the type of the value obtained from the second {@link Func0}
     * @param <I3>    the type of the value obtained from the third {@link Func0}
     * @param <I4>    the type of the value obtained from the fourth {@link Func0}
     * @param <I5>    the type of the value obtained from the fifth {@link Func0}
     * @param <O>     the type of the output to be produced by the resulting {@link Func0}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Func0} objects
     * @param input1  the first {@link Func0} providing the first input value
     * @param input2  the second {@link Func0} providing the second input value
     * @param input3  the third {@link Func0} providing the third input value
     * @param input4  the fourth {@link Func0} providing the fourth input value
     * @param input5  the fifth {@link Func0} providing the fifth input value
     * @return        a {@link Func0} containing the result of applying the function to the input values obtained from the {@link Func0} objects
     */
    public static <I1, I2, I3, I4, I5, O> Func0<O> applyWith(Func5<I1, I2, I3, I4, I5, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Func1} objects and returns a new {@link Func1}
     * that applies the original {@link Func5} function with input values obtained from the provided {@link Func1} objects.
     *
     * @param <S>     the type of the value produced by the resulting {@link Func1}
     * @param <I1>    the type of the value obtained from the first {@link Func1}
     * @param <I2>    the type of the value obtained from the second {@link Func1}
     * @param <I3>    the type of the value obtained from the third {@link Func1}
     * @param <I4>    the type of the value obtained from the fourth {@link Func1}
     * @param <I5>    the type of the value obtained from the fifth {@link Func1}
     * @param <O>     the type of the output produced by the original {@link Func5} function
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Func1} objects
     * @param input1  the first {@link Func1} providing the first input value
     * @param input2  the second {@link Func1} providing the second input value
     * @param input3  the third {@link Func1} providing the third input value
     * @param input4  the fourth {@link Func1} providing the fourth input value
     * @param input5  the fifth {@link Func1} providing the fifth input value
     * @return        a new {@link Func1} that applies the original {@link Func5} function with the specified input values
     */
    public static <S, I1, I2, I3, I4, I5, O> Func1<S, O> applyWith(Func5<I1, I2, I3, I4, I5, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five objects implementing {@link HasPromise} and returns a new {@link Promise}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link HasPromise}
     * @param <I2>    the type of the value obtained from the second {@link HasPromise}
     * @param <I3>    the type of the value obtained from the third {@link HasPromise}
     * @param <I4>    the type of the value obtained from the fourth {@link HasPromise}
     * @param <I5>    the type of the value obtained from the fifth {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link HasPromise} objects
     * @param input1  the first {@link HasPromise} providing the first input value
     * @param input2  the second {@link HasPromise} providing the second input value
     * @param input3  the third {@link HasPromise} providing the third input value
     * @param input4  the fourth {@link HasPromise} providing the fourth input value
     * @param input5  the fifth {@link HasPromise} providing the fifth input value
     * @return        a {@link Promise} containing the result of applying the function to the input values obtained from the {@link HasPromise} objects
     */
    public static <I1, I2, I3, I4, I5, O> Promise<O> applyWith(Func5<I1, I2, I3, I4, I5, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Task} objects and returns a new {@link Task}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Task}
     * @param <I2>    the type of the value obtained from the second {@link Task}
     * @param <I3>    the type of the value obtained from the third {@link Task}
     * @param <I4>    the type of the value obtained from the fourth {@link Task}
     * @param <I5>    the type of the value obtained from the fifth {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Task} objects
     * @param input1  the first Task providing the first input value
     * @param input2  the second Task providing the second input value
     * @param input3  the third Task providing the third input value
     * @param input4  the fourth Task providing the fourth input value
     * @param input5  the fifth Task providing the fifth input value
     * @return        a {@link Task} containing the result of applying the function to the input values obtained from the {@link Task} objects
     */
    public static <I1, I2, I3, I4, I5, O> Task<O> applyWith(Func5<I1, I2, I3, I4, I5, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    //-- Func6 --
    
    /**
     * Applies a {@link Func6} function to values obtained from six input objects and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @return        the result of applying the function to the input values
     */
    public static <I1, I2, I3, I4, I5, I6, O> O apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Creates a {@link Func5} function by applying the first input value to a {@link Func6} function and returning the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the first input value
     * @param input1  the first input value
     * @return        a {@link Func5} function resulting from applying the first input value to the {@link Func6} function
     */
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> apply(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Result} objects and returns the result as a {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a Result
     * @param input2  the second input value as a Result
     * @param input3  the third input value as a Result
     * @param input4  the fourth input value as a Result
     * @param input5  the fifth input value as a Result
     * @param input6  the sixth input value as a Result
     * @return        the result of applying the function to the input values as a {@link Result}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Optional} objects and returns the result as an {@link Optional}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as an {@link Optional}
     * @param input2  the second input value as an {@link Optional}
     * @param input3  the third input value as an {@link Optional}
     * @param input4  the fourth input value as an {@link Optional}
     * @param input5  the fifth input value as an {@link Optional}
     * @param input6  the sixth input value as an {@link Optional}
     * @return        the result of applying the function to the input values as an {@link Optional}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Optional<O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Nullable} objects and returns the result as a {@link Nullable}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Nullable}
     * @param input2  the second input value as a {@link Nullable}
     * @param input3  the third input value as a {@link Nullable}
     * @param input4  the fourth input value as a {@link Nullable}
     * @param input5  the fifth input value as a {@link Nullable}
     * @param input6  the sixth input value as a {@link Nullable}
     * @return        the result of applying the function to the input values as a {@link Nullable}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Nullable<O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Func0} objects and returns the result as a {@link Func0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Func0}
     * @param input2  the second input value as a {@link Func0}
     * @param input3  the third input value as a {@link Func0}
     * @param input4  the fourth input value as a {@link Func0}
     * @param input5  the fifth input value as a {@link Func0}
     * @param input6  the sixth input value as a {@link Func0}
     * @return        the result of applying the function to the input values as a {@link Func0}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Func0<O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Func1} objects and returns the result as a {@link Func1}.
     *
     * @param <S>     the type of the parameter for the {@link Func1} objects
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Func1}
     * @param input2  the second input value as a {@link Func1}
     * @param input3  the third input value as a {@link Func1}
     * @param input4  the fourth input value as a {@link Func1}
     * @param input5  the fifth input value as a {@link Func1}
     * @param input6  the sixth input value as a {@link Func1}
     * @return        the result of applying the function to the input values as a {@link Func1}
     */
    public static <S, I1, I2, I3, I4, I5, I6, O> Func1<S, O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link HasPromise} objects and returns the result as a {@link Promise}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link HasPromise}
     * @param input2  the second input value as a {@link HasPromise}
     * @param input3  the third input value as a {@link HasPromise}
     * @param input4  the fourth input value as a {@link HasPromise}
     * @param input5  the fifth input value as a {@link HasPromise}
     * @param input6  the sixth input value as a {@link HasPromise}
     * @return        the result of applying the function to the input values as a {@link Promise}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Task} objects and returns the result as a {@link Task}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Task}
     * @param input2  the second input value as a {@link Task}
     * @param input3  the third input value as a {@link Task}
     * @param input4  the fourth input value as a {@link Task}
     * @param input5  the fifth input value as a {@link Task}
     * @param input6  the sixth input value as a {@link Task}
     * @return        the result of applying the function to the input values as a {@link Task}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Task<O> applyWith(Func6<I1, I2, I3, I4, I5, I6, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    //-- Func7 --
    
    /**
     * Applies a {@link Func7} function to values obtained from seven input parameters and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @param input7  the seventh input value
     * @return        the result of applying the function to the input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O apply(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Partially applies a {@link Func7} function to the first input value, creating a new {@link Func6} function.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the Func7 function to be partially applied
     * @param input1  the first input value
     * @return        a new {@link Func6} function that takes the remaining six input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func6<I2, I3, I4, I5, I6, I7, O> apply(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func7} function to the input values wrapped in {@link Result} objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the Func7 function to be applied
     * @param input1  the Result containing the first input value
     * @param input2  the Result containing the second input value
     * @param input3  the Result containing the third input value
     * @param input4  the Result containing the fourth input value
     * @param input5  the Result containing the fifth input value
     * @param input6  the Result containing the sixth input value
     * @param input7  the Result containing the seventh input value
     * @return        the {@link Result} containing the output of the {@link Func7} function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values wrapped in {@link Optional} objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @param input4  the {@link Optional} containing the fourth input value
     * @param input5  the {@link Optional} containing the fifth input value
     * @param input6  the {@link Optional} containing the sixth input value
     * @param input7  the {@link Optional} containing the seventh input value
     * @return        the {@link Optional} containing the output of the {@link Func7} function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Optional<O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values wrapped in {@link Nullable} objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @param input5  the {@link Nullable} containing the fifth input value
     * @param input6  the {@link Nullable} containing the sixth input value
     * @param input7  the {@link Nullable} containing the seventh input value
     * @return        the {@link Nullable} containing the output of the {@link Func7} function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Nullable<O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from Func0 suppliers and wrapped in Nullable objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Func0} supplier for the first input value
     * @param input2  the {@link Func0} supplier for the second input value
     * @param input3  the {@link Func0} supplier for the third input value
     * @param input4  the {@link Func0} supplier for the fourth input value
     * @param input5  the {@link Func0} supplier for the fifth input value
     * @param input6  the {@link Func0} supplier for the sixth input value
     * @param input7  the {@link Func0} supplier for the seventh input value
     * @return        the result of applying the function to the input values as a {@link Func0}
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from {@link Func1} and wrapped in {@link Nullable} objects.
     *
     * @param <S>     the type of the source input
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Func1} supplier for the first input value
     * @param input2  the {@link Func1} supplier for the second input value
     * @param input3  the {@link Func1} supplier for the third input value
     * @param input4  the {@link Func1} supplier for the fourth input value
     * @param input5  the {@link Func1} supplier for the fifth input value
     * @param input6  the {@link Func1} supplier for the sixth input value
     * @param input7  the {@link Func1} supplier for the seventh input value
     * @return        a {@link Func1} that applies the {@link Func7} function to the provided input values
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, O> Func1<S, O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from {@link HasPromise} objects and returns a {@link Promise} of the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link HasPromise} for the first input value
     * @param input2  the {@link HasPromise} for the second input value
     * @param input3  the {@link HasPromise} for the third input value
     * @param input4  the {@link HasPromise} for the fourth input value
     * @param input5  the {@link HasPromise} for the fifth input value
     * @param input6  the {@link HasPromise} for the sixth input value
     * @param input7  the {@link HasPromise} for the seventh input value
     * @return        a {@link Promise} of the result of applying the {@link Func7} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from Task objects and returns a Task of the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Task} for the first input value
     * @param input2  the {@link Task} for the second input value
     * @param input3  the {@link Task} for the third input value
     * @param input4  the {@link Task} for the fourth input value
     * @param input5  the {@link Task} for the fifth input value
     * @param input6  the {@link Task} for the sixth input value
     * @param input7  the {@link Task} for the seventh input value
     * @return        a {@link Task} of the result of applying the {@link Func7} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> applyWith(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    //-- Func8 --
    
    /**
     * Applies a {@link Func8} function to the provided input values and returns the result.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func8} function to be applied
     * @param input1   the first input value
     * @param input2   the second input value
     * @param input3   the third input value
     * @param input4   the fourth input value
     * @param input5   the fifth input value
     * @param input6   the sixth input value
     * @param input7   the seventh input value
     * @param input8   the eighth input value
     * @return         the result of applying the {@link Func8} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O apply(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Partially applies a {@link Func8} function by fixing the first input value.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func8} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func7} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func7<I2, I3, I4, I5, I6, I7, I8, O> apply(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @param input5  the {@link Result} containing the fifth input value
     * @param input6  the {@link Result} containing the sixth input value
     * @param input7  the {@link Result} containing the seventh input value
     * @param input8  the {@link Result} containing the eighth input value
     * @return        the {@link Result} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func8} function to a list of input values wrapped in {@link Optional}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func8} function to apply
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @param input4  the {@link Optional} containing the fourth input value
     * @param input5  the {@link Optional} containing the fifth input value
     * @param input6  the {@link Optional} containing the sixth input value
     * @param input7  the {@link Optional} containing the seventh input value
     * @param input8  the {@link Optional} containing the eighth input value
     * @return        the {@link Optional} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Optional<O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Nullable}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @param input5  the {@link Nullable} containing the fifth input value
     * @param input6  the {@link Nullable} containing the sixth input value
     * @param input7  the {@link Nullable} containing the seventh input value
     * @param input8  the {@link Nullable} containing the eighth input value
     * @return        the {@link Nullable} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Nullable<O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func8} function to a list of input values wrapped in {@link Func0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func8} function to apply
     * @param input1  the {@link Func0} containing the first input value
     * @param input2  the {@link Func0} containing the second input value
     * @param input3  the {@link Func0} containing the third input value
     * @param input4  the {@link Func0} containing the fourth input value
     * @param input5  the {@link Func0} containing the fifth input value
     * @param input6  the {@link Func0} containing the sixth input value
     * @param input7  the {@link Func0} containing the seventh input value
     * @param input8  the {@link Func0} containing the eighth input value
     * @return        the {@link Func0} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func8} function to a list of input values wrapped in {@link Func1} for a specific context.
     *
     * @param <S>     the type of the context
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Func1} containing the first input value for the context
     * @param input2  the {@link Func1} containing the second input value for the context
     * @param input3  the {@link Func1} containing the third input value for the context
     * @param input4  the {@link Func1} containing the fourth input value for the context
     * @param input5  the {@link Func1} containing the fifth input value for the context
     * @param input6  the {@link Func1} containing the sixth input value for the context
     * @param input7  the {@link Func1} containing the seventh input value for the context
     * @param input8  the {@link Func1} containing the eighth input value for the context
     * @return        the {@link Func1} containing the output of the function for the context
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, O> Func1<S, O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link HasPromise} containing the first input value for asynchronous processing
     * @param input2  the {@link HasPromise} containing the second input value for asynchronous processing
     * @param input3  the {@link HasPromise} containing the third input value for asynchronous processing
     * @param input4  the {@link HasPromise} containing the fourth input value for asynchronous processing
     * @param input5  the {@link HasPromise} containing the fifth input value for asynchronous processing
     * @param input6  the {@link HasPromise} containing the sixth input value for asynchronous processing
     * @param input7  the {@link HasPromise} containing the seventh input value for asynchronous processing
     * @param input8  the {@link HasPromise} containing the eighth input value for asynchronous processing
     * @return        a {@link Promise} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Task} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Task} containing the first input value for asynchronous processing
     * @param input2  the {@link Task} containing the second input value for asynchronous processing
     * @param input3  the {@link Task} containing the third input value for asynchronous processing
     * @param input4  the {@link Task} containing the fourth input value for asynchronous processing
     * @param input5  the {@link Task} containing the fifth input value for asynchronous processing
     * @param input6  the {@link Task} containing the sixth input value for asynchronous processing
     * @param input7  the {@link Task} containing the seventh input value for asynchronous processing
     * @param input8  the {@link Task} containing the eighth input value for asynchronous processing
     * @return        a {@link Task} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> applyWith(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    //-- Func9 --
    
    /**
     * Applies a {@link Func9} function to the provided input values and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to be applied
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @param input7  the seventh input value
     * @param input8  the eighth input value
     * @param input9  the ninth input value
     * @return        the result of applying the {@link Func9} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> O apply(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Partially applies a {@link Func9} function by fixing the first input value.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func8} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func8<I2, I3, I4, I5, I6, I7, I8, I9, O> apply(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @param input5  the {@link Result} containing the fifth input value
     * @param input6  the {@link Result} containing the sixth input value
     * @param input7  the {@link Result} containing the seventh input value
     * @param input8  the {@link Result} containing the eighth input value
     * @param input9  the {@link Result} containing the ninth input value
     * @return        the {@link Result} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Result<O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Optional}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func9} function to apply
     * @param input1   the {@link Optional} containing the first input value
     * @param input2   the {@link Optional} containing the second input value
     * @param input3   the {@link Optional} containing the third input value
     * @param input4   the {@link Optional} containing the fourth input value
     * @param input5   the {@link Optional} containing the fifth input value
     * @param input6   the {@link Optional} containing the sixth input value
     * @param input7   the {@link Optional} containing the seventh input value
     * @param input8   the {@link Optional} containing the eighth input value
     * @param input9   the {@link Optional} containing the ninth input value
     * @return         the {@link Optional} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Optional<O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8, Optional<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Nullable}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @param input5  the {@link Nullable} containing the fifth input value
     * @param input6  the {@link Nullable} containing the sixth input value
     * @param input7  the {@link Nullable} containing the seventh input value
     * @param input8  the {@link Nullable} containing the eighth input value
     * @param input9  the {@link Nullable} containing the ninth input value
     * @return        the {@link Nullable} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Nullable<O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8, Nullable<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Func0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Func0} containing the first input value
     * @param input2  the {@link Func0} containing the second input value
     * @param input3  the {@link Func0} containing the third input value
     * @param input4  the {@link Func0} containing the fourth input value
     * @param input5  the {@link Func0} containing the fifth input value
     * @param input6  the {@link Func0} containing the sixth input value
     * @param input7  the {@link Func0} containing the seventh input value
     * @param input8  the {@link Func0} containing the eighth input value
     * @param input9  the {@link Func0} containing the ninth input value
     * @return        the {@link Func0} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Func1} for a specific context.
     *
     * @param <S>     the type of the context
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Func1} containing the first input value for the context
     * @param input2  the {@link Func1} containing the second input value for the context
     * @param input3  the {@link Func1} containing the third input value for the context
     * @param input4  the {@link Func1} containing the fourth input value for the context
     * @param input5  the {@link Func1} containing the fifth input value for the context
     * @param input6  the {@link Func1} containing the sixth input value for the context
     * @param input7  the {@link Func1} containing the seventh input value for the context
     * @param input8  the {@link Func1} containing the eighth input value for the context
     * @param input9  the {@link Func1} containing the ninth input value for the context
     * @return        the {@link Func1} containing the output of the function for the context
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func1<S, O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link HasPromise} containing the first input value for asynchronous processing
     * @param input2  the {@link HasPromise} containing the second input value for asynchronous processing
     * @param input3  the {@link HasPromise} containing the third input value for asynchronous processing
     * @param input4  the {@link HasPromise} containing the fourth input value for asynchronous processing
     * @param input5  the {@link HasPromise} containing the fifth input value for asynchronous processing
     * @param input6  the {@link HasPromise} containing the sixth input value for asynchronous processing
     * @param input7  the {@link HasPromise} containing the seventh input value for asynchronous processing
     * @param input8  the {@link HasPromise} containing the eighth input value for asynchronous processing
     * @param input9  the {@link HasPromise} containing the ninth input value for asynchronous processing
     * @return        a {@link Promise} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Promise<O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Task} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Task} containing the first input value for asynchronous processing
     * @param input2  the {@link Task} containing the second input value for asynchronous processing
     * @param input3  the {@link Task} containing the third input value for asynchronous processing
     * @param input4  the {@link Task} containing the fourth input value for asynchronous processing
     * @param input5  the {@link Task} containing the fifth input value for asynchronous processing
     * @param input6  the {@link Task} containing the sixth input value for asynchronous processing
     * @param input7  the {@link Task} containing the seventh input value for asynchronous processing
     * @param input8  the {@link Task} containing the eighth input value for asynchronous processing
     * @param input9  the {@link Task} containing the ninth input value for asynchronous processing
     * @return        a {@link Task} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Task<O> applyWith(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    //-- Func10 --
    
    /**
     * Applies a {@link Func10} function to the provided input values and returns the result.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to be applied
     * @param input1   the first input value
     * @param input2   the second input value
     * @param input3   the third input value
     * @param input4   the fourth input value
     * @param input5   the fifth input value
     * @param input6   the sixth input value
     * @param input7   the seventh input value
     * @param input8   the eighth input value
     * @param input9   the ninth input value
     * @param input10  the tenth input value
     * @return         the result of applying the {@link Func10} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> O apply(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Partially applies a {@link Func10} function by fixing the first input value.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <I10>   the type of the tenth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func9} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func9<I2, I3, I4, I5, I6, I7, I8, I9, I10, O> apply(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Result}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Result} containing the first input value
     * @param input2   the {@link Result} containing the second input value
     * @param input3   the {@link Result} containing the third input value
     * @param input4   the {@link Result} containing the fourth input value
     * @param input5   the {@link Result} containing the fifth input value
     * @param input6   the {@link Result} containing the sixth input value
     * @param input7   the {@link Result} containing the seventh input value
     * @param input8   the {@link Result} containing the eighth input value
     * @param input9   the {@link Result} containing the ninth input value
     * @param input10  the {@link Result} containing the tenth input value
     * @return         the {@link Result} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Result<O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9, Result<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Optional}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Optional} containing the first input value
     * @param input2   the {@link Optional} containing the second input value
     * @param input3   the {@link Optional} containing the third input value
     * @param input4   the {@link Optional} containing the fourth input value
     * @param input5   the {@link Optional} containing the fifth input value
     * @param input6   the {@link Optional} containing the sixth input value
     * @param input7   the {@link Optional} containing the seventh input value
     * @param input8   the {@link Optional} containing the eighth input value
     * @param input9   the {@link Optional} containing the ninth input value
     * @param input10  the {@link Optional} containing the tenth input value
     * @return         the {@link Optional} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Optional<O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8, Optional<I9> input9, Optional<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Nullable}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Nullable} containing the first input value
     * @param input2   the {@link Nullable} containing the second input value
     * @param input3   the {@link Nullable} containing the third input value
     * @param input4   the {@link Nullable} containing the fourth input value
     * @param input5   the {@link Nullable} containing the fifth input value
     * @param input6   the {@link Nullable} containing the sixth input value
     * @param input7   the {@link Nullable} containing the seventh input value
     * @param input8   the {@link Nullable} containing the eighth input value
     * @param input9   the {@link Nullable} containing the ninth input value
     * @param input10  the {@link Nullable} containing the tenth input value
     * @return         the {@link Nullable} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Nullable<O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8, Nullable<I9> input9, Nullable<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Func0}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Func0} containing the first input value
     * @param input2   the {@link Func0} containing the second input value
     * @param input3   the {@link Func0} containing the third input value
     * @param input4   the {@link Func0} containing the fourth input value
     * @param input5   the {@link Func0} containing the fifth input value
     * @param input6   the {@link Func0} containing the sixth input value
     * @param input7   the {@link Func0} containing the seventh input value
     * @param input8   the {@link Func0} containing the eighth input value
     * @param input9   the {@link Func0} containing the ninth input value
     * @param input10  the {@link Func0} containing the tenth input value
     * @return         the {@link Func0} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func0<O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9, Func0<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Func1} for a specific context.
     *
     * @param <S>      the type of the context
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Func1} containing the first input value for the context
     * @param input2   the {@link Func1} containing the second input value for the context
     * @param input3   the {@link Func1} containing the third input value for the context
     * @param input4   the {@link Func1} containing the fourth input value for the context
     * @param input5   the {@link Func1} containing the fifth input value for the context
     * @param input6   the {@link Func1} containing the sixth input value for the context
     * @param input7   the {@link Func1} containing the seventh input value for the context
     * @param input8   the {@link Func1} containing the eighth input value for the context
     * @param input9   the {@link Func1} containing the ninth input value for the context
     * @param input10  the {@link Func1} containing the tenth input value for the context
     * @return         the {@link Func1} containing the output of the function for the context
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func1<S, O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9, Func1<S, I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link HasPromise} containing the first input value for asynchronous processing
     * @param input2   the {@link HasPromise} containing the second input value for asynchronous processing
     * @param input3   the {@link HasPromise} containing the third input value for asynchronous processing
     * @param input4   the {@link HasPromise} containing the fourth input value for asynchronous processing
     * @param input5   the {@link HasPromise} containing the fifth input value for asynchronous processing
     * @param input6   the {@link HasPromise} containing the sixth input value for asynchronous processing
     * @param input7   the {@link HasPromise} containing the seventh input value for asynchronous processing
     * @param input8   the {@link HasPromise} containing the eighth input value for asynchronous processing
     * @param input9   the {@link HasPromise} containing the ninth input value for asynchronous processing
     * @param input10  the {@link HasPromise} containing the tenth input value for asynchronous processing
     * @return         a {@link Promise} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Promise<O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9, HasPromise<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Task} for asynchronous processing.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Task} containing the first input value for asynchronous processing
     * @param input2   the {@link Task} containing the second input value for asynchronous processing
     * @param input3   the {@link Task} containing the third input value for asynchronous processing
     * @param input4   the {@link Task} containing the fourth input value for asynchronous processing
     * @param input5   the {@link Task} containing the fifth input value for asynchronous processing
     * @param input6   the {@link Task} containing the sixth input value for asynchronous processing
     * @param input7   the {@link Task} containing the seventh input value for asynchronous processing
     * @param input8   the {@link Task} containing the eighth input value for asynchronous processing
     * @param input9   the {@link Task} containing the ninth input value for asynchronous processing
     * @param input10  the {@link Task} containing the tenth input value for asynchronous processing
     * @return         a {@link Task} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Task<O> applyWith(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9, Task<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    // == $ ==
    
    /**
     * Retrieves the value held by a {@link HasPromise}.
     *
     * @param <O>         the type of the value.
     * @param hasPromise  the value that has a promise containing the value
     * @return            the value
     */
    public static <O> O $(HasPromise<O> hasPromise) {
        if (hasPromise instanceof Func0)
            return ((Func0<O>)hasPromise).get();
        if (hasPromise instanceof Ref)
            return ((Ref<O>)hasPromise).get();
        if (hasPromise instanceof Result)
            return ((Result<O>)hasPromise).get();
        if (hasPromise instanceof Promise)
            return ((Promise<O>)hasPromise).get();
        
        return hasPromise.getResult().get();
    }
    
    //-- Func1 --
    
    /**
     * Applies a {@link Func1} function to a given input and returns the result.
     *
     * @param <I>   the type of the input to the function
     * @param <O>   the type of the output returned by the function
     * @param func  the Func1 function to be applied
     * @param input the input value to be passed to the function
     * @return      the result of applying the {@link Func1} function to the given input
     */
    public static <I, O> O $(Func1<I, O> func, I input) {
        return Func1.from(func).apply(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link Result} if it exists,
     * wrapping the result or any exception in a new {@link Result}.
     *
     * @param <I>    the type of the input inside the {@link Result}
     * @param <O>    the type of the output to be contained in the resulting {@link Result}
     * @param func   the {@link Func1} function to be applied to the input value
     * @param input  the {@link Result} containing the input value or an exception
     * @return       a {@link Result} containing either the function's output or any exception thrown
     */
    public static <I, O> Result<O> $(Func1<I, O> func, Result<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside an {@link Optional}, if present,
     * and returns an Optional containing the result.
     *
     * @param <I>    the type of the input inside the {@link Optional}
     * @param <O>    the type of the output to be wrapped in the resulting {@link Optional}
     * @param func   the {@link Func1} function to be applied to the input value, if present
     * @param input  the {@link Optional} containing the input value
     * @return       an {@link Optional} containing the result of applying the function, or empty if input is empty
     */
    public static <I, O> Optional<O> $(Func1<I, O> func, Optional<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link Nullable}, if not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I>    the type of the input within the {@link Nullable}
     * @param <O>    the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func   the {@link Func1} function to be applied to the input value, if not null
     * @param input  the {@link Nullable} containing the input value
     * @return       a {@link Nullable} containing the result of applying the function, or null if input is null
     */
    public static <I, O> Nullable<O> $(Func1<I, O> func, Nullable<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to each element in a list and returns a {@link FuncList} 
     * containing the results.
     *
     * @param <I>    the type of elements in the input list
     * @param <O>    the type of elements in the resulting {@link FuncList}
     * @param func   the {@link Func1} function to be applied to each element of the list
     * @param input  the list of elements to be processed
     * @return       a {@link FuncList} containing the results of applying the function to each list element
     */
    public static <I, O> FuncList<O> $(Func1<I, O> func, List<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the values of a {@link Map} and returns a {@link FuncMap} 
     * with the same keys and transformed values.
     *
     * @param <K>    the type of the keys in both the input and resulting {@link FuncMap}
     * @param <I>    the type of the values in the input {@link Map}
     * @param <O>    the type of the values in the resulting {@link FuncMap}
     * @param func   the Func1 function to be applied to each value in the input {@link Map}
     * @param input  the {@link Map} whose values are to be transformed
     * @return       a FuncMap containing the keys from the input {@link Map} and values obtained 
     *                 by applying the function to each input value
     */
    public static <K, I, O> FuncMap<K, O> $(Func1<I, O> func, Map<K, I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the result of a {@link Func0} and returns a {@link Func0}
     * containing the transformed result.
     *
     * @param <I>    the type of the input produced by the {@link Func0}
     * @param <O>    the type of the output to be contained in the resulting {@link Func0}
     * @param func   the {@link Func1} function to be applied to the result of the {@link Func0}
     * @param input  the {@link Func0} whose result will be transformed by the function
     * @return       a {@link Func0} containing the result obtained by applying the function to the input {@link Func0}'s result
     */
    public static <I, O> Func0<O> $(Func1<I, O> func, Func0<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Composes two {@link Func1} functions to create a new {@link Func1} function.
     *
     * @param <S>    the type of the input parameter for the first {@link Func1}
     * @param <I>    the type of the output of the first Func1 and the input of the second {@link Func1}
     * @param <O>    the type of the output produced by the second {@link Func1}
     * @param func   the second {@link Func1} function to be applied
     * @param input  the first {@link Func1} function whose output will be the input to the second function
     * @return       a Func1 function that applies the first function and then the second function to its input
     */
    public static <S, I, O> Func1<S, O> $(Func1<I, O> func, Func1<S, I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link HasPromise}, if present,
     * and returns a {@link Promise} containing the result.
     *
     * @param <I>    the type of the input value within the {@link HasPromise}
     * @param <O>    the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func   the {@link Func1} function to be applied to the input value, if present
     * @param input  the {@link HasPromise} containing the input value
     * @return       a {@link Promise} containing the result of applying the function, or a pending {@link Promise} if input is not ready
     */
    public static <I, O> Promise<O> $(Func1<I, O> func, HasPromise<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    /**
     * Applies a {@link Func1} function to the value inside a {@link Task} if it becomes available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I>    the type of the input value within the {@link Task}
     * @param <O>    the type of the output to be encapsulated in the resulting {@link Task}
     * @param func   the {@link Func1} function to be applied to the input value, when available
     * @param input  the {@link Task} supplying the input value
     * @return       a {@link Task} that will contain the result of applying the function, once available
     */
    public static <I, O> Task<O> $(Func1<I, O> func, Task<I> input) {
        return Func1.from(func).applyTo(input);
    }
    
    //-- Func2 --
    
    /**
     * Invokes a {@link Func2} function with two input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func2} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @return        the result of invoking the {@link Func2} function with the given input parameters
     */
    public static <I1, I2, O> O $(Func2<I1, I2, O> func, I1 input1, I2 input2) {
        return Func2.from(func).apply(input1, input2);
    }
    
    /**
     * Partially applies a {@link Func2} function by fixing the first input parameter, 
     * resulting in a new {@link Func1} function.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <O>     the type of the output produced by the resulting Func1
     * @param func    the {@link Func2} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link Func1} function that takes the second input parameter and applies 
     *                the original {@link Func2} function with the fixed first input parameter
     */
    public static <I1, I2, O> Func1<I2, O> $(Func2<I1, I2, O> func, I1 input1) {
        return Func2.from(func).apply(input1);
    }
    
    /**
     * Applies a {@link Func2} function to the values inside two {@link Result} objects if both values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Result}
     * @param func    the {@link Func2} function to be applied to the input values, if both values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if either input is an exception
     */
    public static <I1, I2, O> Result<O> $(Func2<I1, I2, O> func, Result<I1> input1, Result<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to the values inside two {@link Optional} objects if both values are present,
     * and returns an {@link Optional} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Optional}
     * @param <I2>    the type of the value inside the second {@link Optional}
     * @param <O>     the type of the output to be wrapped in the resulting {@link Optional}
     * @param func    the {@link Func2} function to be applied to the input values, if both are present
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @return        an {@link Optional} containing the result of applying the function, or empty if either input is empty
     */
    public static <I1, I2, O> Optional<O> $(Func2<I1, I2, O> func, Optional<I1> input1, Optional<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to the values inside two {@link Nullable} objects if both values are not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Nullable}
     * @param <I2>    the type of the value inside the second {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func2} function to be applied to the input values, if both are not null
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @return        a {@link Nullable} containing the result of applying the function, or null if either input is null
     */
    public static <I1, I2, O> Nullable<O> $(Func2<I1, I2, O> func, Nullable<I1> input1, Nullable<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding elements from two {@link FuncList} objects and returns a new
     * {@link FuncList} containing the results.
     *
     * @param <I1>    the type of elements in the first {@link FuncList}
     * @param <I2>    the type of elements in the second {@link FuncList}
     * @param <O>     the type of elements in the resulting {@link FuncList}
     * @param func    the {@link Func2} function to be applied to corresponding elements
     * @param input1  the first {@link FuncList} of input elements
     * @param input2  the second {@link FuncList} of input elements
     * @return        a {@link FuncList} containing the results of applying the function to corresponding elements
     */
    public static <I1, I2, O> FuncList<O> $(Func2<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2) {
        return Func2.from(func).applyEachOf(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding elements from two {@link FuncList} objects and returns a new
     * {@link FuncList} containing the results, with an option to specify the behavior when the input lists have different sizes.
     *
     * @param <I1>    the type of elements in the first {@link FuncList}
     * @param <I2>    the type of elements in the second {@link FuncList}
     * @param <O>     the type of elements in the resulting {@link FuncList}
     * @param func    the {@link Func2} function to be applied to corresponding elements
     * @param input1  the first {@link FuncList} of input elements
     * @param input2  the second {@link FuncList} of input elements
     * @param option  the option specifying how to handle input lists of different sizes
     * @return        a {@link FuncList} containing the results of applying the function to corresponding elements
     */
    public static <I1, I2, O> FuncList<O> $(Func2<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2, ZipWithOption option) {
        return Func2.from(func).applyEachOf(input1, input2, option);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding values from two {@link FuncMap} objects and returns a new
     * {@link FuncMap} containing the results, using the keys present in both input maps.
     *
     * @param <K>     the type of keys in the input and resulting {@link FuncMap}
     * @param <I1>    the type of values in the first input {@link FuncMap}
     * @param <I2>    the type of values in the second input {@link FuncMap}
     * @param <O>     the type of values in the resulting {@link FuncMap}
     * @param func    the {@link Func2} function to be applied to corresponding values
     * @param input1  the first input {@link FuncMap}
     * @param input2  the second input {@link FuncMap}
     * @return        a {@link FuncMap} containing the results of applying the function to corresponding values with matching keys
     */
    public static <K, I1, I2, O> FuncMap<K, O> $(Func2<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2) {
        return Func2.from(func).applyEachOf(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to corresponding values from two {@link FuncMap} objects and returns a new
     * {@link FuncMap} containing the results, with an option to specify the behavior when the input maps have different keys.
     *
     * @param <K>     the type of keys in the input and resulting {@link FuncMap}
     * @param <I1>    the type of values in the first input {@link FuncMap}
     * @param <I2>    the type of values in the second input {@link FuncMap}
     * @param <O>     the type of values in the resulting {@link FuncMap}
     * @param func    the {@link Func2} function to be applied to corresponding values
     * @param input1  the first input {@link FuncMap}
     * @param input2  the second input {@link FuncMap}
     * @param option  the option specifying how to handle input maps with different keys
     * @return        a {@link FuncMap} containing the results of applying the function to corresponding values
     *                  with matching keys or based on the specified option
     */
    public static <K, I1, I2, O> FuncMap<K, O> $(Func2<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2, ZipWithOption option) {
        return Func2.from(func).applyEachOf(input1, input2, option);
    }
    
    /**
     * Applies a {@link Func2} function to values extracted from two {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <O>     the type of the output to be contained in the resulting {@link Func0}
     * @param func    the {@link Func2} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, O> Func0<O> $(Func2<I1, I2, O> func, Func0<I1> input1, Func0<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Partially applies a {@link Func2} function by fixing two input parameters with values obtained from
     * two {@link Func1} functions, resulting in a new {@link Func1} function.
     *
     * @param <S>     the type of the shared input parameter for both input1 and input2 functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <O>     the type of the output produced by the resulting {@link Func1}
     * @param func    the {@link Func2} function to be partially applied
     * @param input1  the first {@link Func1} function producing the first input value
     * @param input2  the second {@link Func1} function producing the second input value
     * @return        a new {@link Func1} function that applies the original {@link Func2} function with the two fixed input parameters
     */
    public static <S, I1, I2, O> Func1<S, O> $(Func2<I1, I2, O> func, Func1<S, I1> input1, Func1<S, I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to values inside two {@link HasPromise} objects, if both values become available,
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link HasPromise}
     * @param <I2>    the type of the value inside the second {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func2} function to be applied to the input values, if both values are ready
     * @param input1  the {@link HasPromise} containing the first input value
     * @param input2  the {@link HasPromise} containing the second input value
     * @return        a {@link Promise} containing the result of applying the function, once both input values are ready
     */
    public static <I1, I2, O> Promise<O> $(Func2<I1, I2, O> func, HasPromise<I1> input1, HasPromise<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    /**
     * Applies a {@link Func2} function to values inside two {@link Task} objects, if both values become available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Task}
     * @param <I2>    the type of the value inside the second {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func2} function to be applied to the input values, if both values are ready
     * @param input1  the {@link Task} containing the first input value
     * @param input2  the {@link Task} containing the second input value
     * @return        a {@link Task} containing the result of applying the function, once both input values are ready
     */
    public static <I1, I2, O> Task<O> $(Func2<I1, I2, O> func, Task<I1> input1, Task<I2> input2) {
        return Func2.from(func).applyTo(input1, input2);
    }
    
    //-- Func3 --
    
    /**
     * Invokes a {@link Func3} function with three input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func3} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @param input3  the third input parameter for the function
     * @return        the result of invoking the {@link Func3} function with the given input parameters
     */
    public static <I1, I2, I3, O> O $(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
        return func.apply(input1, input2, input3);
    }
    
    /**
     * Partially applies a {@link Func3} function by fixing the first input parameter, 
     * resulting in a new {@link Func2} function that takes the remaining two parameters.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <O>     the type of the output produced by the resulting {@link Func2}
     * @param func    the {@link Func3} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link Func2} function that takes the remaining two parameters
     *                and applies the original {@link Func3} function with the fixed first input parameter
     */
    public static <I1, I2, I3, O> Func2<I2, I3, O> $(Func3<I1, I2, I3, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Result} objects if all values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <I3>    the type of the value inside the third {@link Result}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Result}
     * @param func    the {@link Func3} function to be applied to the input values, if all values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if any input is an exception
     */
    public static <I1, I2, I3, O> Result<O> $(Func3<I1, I2, I3, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Optional} objects if all values are present,
     * and returns an {@link Optional} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Optional}
     * @param <I2>    the type of the value inside the second {@link Optional}
     * @param <I3>    the type of the value inside the third {@link Optional}
     * @param <O>     the type of the output to be wrapped in the resulting {@link Optional}
     * @param func    the {@link Func3} function to be applied to the input values, if all are present
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @return        an {@link Optional} containing the result of applying the function, or empty if any input is empty
     */
    public static <I1, I2, I3, O> Optional<O> $(Func3<I1, I2, I3, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Nullable} objects if all values are not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Nullable}
     * @param <I2>    the type of the value inside the second {@link Nullable}
     * @param <I3>    the type of the value inside the third {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func3} function to be applied to the input values, if all are not null
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @return        a {@link Nullable} containing the result of applying the function, or null if any input is null
     */
    public static <I1, I2, I3, O> Nullable<O> $(Func3<I1, I2, I3, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values extracted from three {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <I3>    the type of the value supplied by the third {@link Func0}
     * @param <O>     the type of the output to be contained in the resulting {@link Func0}
     * @param func    the {@link Func3} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @param input3  the third {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, I3, O> Func0<O> $(Func3<I1, I2, I3, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Partially applies a {@link Func3} function by fixing three input parameters with values obtained from
     * three {@link Func1} functions, resulting in a new {@link Func1} function.
     *
     * @param <S>     the type of the shared input parameter for all input functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <I3>    the type of the value produced by the third input3 function
     * @param <O>     the type of the output produced by the resulting Func1
     * @param func    the {@link Func3} function to be partially applied
     * @param input1  the first input1 function producing the first input value
     * @param input2  the second input2 function producing the second input value
     * @param input3  the third input3 function producing the third input value
     * @return        a new {@link Func1} function that applies the original {@link Func3} function with the three fixed input parameters
     */
    public static <S, I1, I2, I3, O> Func1<S, O> $(Func3<I1, I2, I3, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link HasPromise} objects, if all values become available,
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link HasPromise}
     * @param <I2>    the type of the value inside the second {@link HasPromise}
     * @param <I3>    the type of the value inside the third {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func3} function to be applied to the input values, if all values are ready
     * @param input1  the {@link HasPromise} containing the first input value
     * @param input2  the {@link HasPromise} containing the second input value
     * @param input3  the {@link HasPromise} containing the third input value
     * @return        a {@link Promise} containing the result of applying the function, once all input values are ready
     */
    public static <I1, I2, I3, O> Promise<O> $(Func3<I1, I2, I3, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link Func3} function to values inside three {@link Task} objects, if all values become available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Task}
     * @param <I2>    the type of the value inside the second {@link Task}
     * @param <I3>    the type of the value inside the third {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func3} function to be applied to the input values, if all values are ready
     * @param input1  the {@link Task} containing the first input value
     * @param input2  the {@link Task} containing the second input value
     * @param input3  the {@link Task} containing the third input value
     * @return        a {@link Task} containing the result of applying the function, once all input values are ready
     */
    public static <I1, I2, I3, O> Task<O> $(Func3<I1, I2, I3, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3) {
        return func.applyTo(input1, input2, input3);
    }
    
    //-- Func4 --
    
    /**
     * Invokes a {@link Func4} function with four input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <I4>    the type of the fourth input parameter
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func4} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @param input3  the third input parameter for the function
     * @param input4  the fourth input parameter for the function
     * @return        the result of invoking the {@link Func4} function with the given input parameters
     */
    public static <I1, I2, I3, I4, O> O $(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        return func.apply(input1, input2, input3, input4);
    }
    
    /**
     * Partially applies a {@link Func4} function by fixing the first input parameter, 
     * resulting in a new {@link Func3} function that takes the remaining three parameters.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <I4>    the type of the fourth input parameter
     * @param <O>     the type of the output produced by the resulting {@link Func3}
     * @param func    the {@link Func4} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link Func3} function that takes the remaining three parameters
     *                  and applies the original {@link Func4} function with the fixed first input parameter
     */
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> $(Func4<I1, I2, I3, I4, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func4} function to values inside four {@link Result} objects if all values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <I3>    the type of the value inside the third {@link Result}
     * @param <I4>    the type of the value inside the fourth {@link Result}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Result}
     * @param func    the {@link Func4} function to be applied to the input values, if all values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if any input is an exception
     */
    public static <I1, I2, I3, I4, O> Result<O> $(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values inside four {@link Optional} objects if all values are present,
     * and returns an {@link Optional} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Optional}
     * @param <I2>    the type of the value inside the second {@link Optional}
     * @param <I3>    the type of the value inside the third {@link Optional}
     * @param <I4>    the type of the value inside the fourth {@link Optional}
     * @param <O>     the type of the output to be wrapped in the resulting {@link Optional}
     * @param func    the {@link Func4} function to be applied to the input values, if all are present
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @param input4  the {@link Optional} containing the fourth input value
     * @return        an {@link Optional} containing the result of applying the function, or empty if any input is empty
     */
    public static <I1, I2, I3, I4, O> Optional<O> $(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values inside four {@link Nullable} objects if all values are not null,
     * and returns a {@link Nullable} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Nullable}
     * @param <I2>    the type of the value inside the second {@link Nullable}
     * @param <I3>    the type of the value inside the third {@link Nullable}
     * @param <I4>    the type of the value inside the fourth {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func4} function to be applied to the input values, if all are not null
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @return        a {@link Nullable} containing the result of applying the function, or null if any input is null
     */
    public static <I1, I2, I3, I4, O> Nullable<O> $(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values extracted from four {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <I3>    the type of the value supplied by the third {@link Func0}
     * @param <I4>    the type of the value supplied by the fourth {@link Func0}
     * @param <O>     the type of the output to be contained in the resulting {@link Func0}
     * @param func    the {@link Func4} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @param input3  the third {@link Func0} supplier
     * @param input4  the fourth {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, I3, I4, O> Func0<O> $(Func4<I1, I2, I3, I4, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values obtained from four {@link Func1} functions and returns a new {@link Func1}
     * function that takes a shared input parameter and applies the original Func4 function with the values from the input functions.
     *
     * @param <S>     the type of the shared input parameter for all input functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <I3>    the type of the value produced by the third input3 function
     * @param <I4>    the type of the value produced by the fourth input4 function
     * @param <O>     the type of the output produced by the resulting {@link Func1}
     * @param func    the {@link Func4} function to be applied to the input values obtained from the input functions
     * @param input1  the first input1 function producing the first input value
     * @param input2  the second input2 function producing the second input value
     * @param input3  the third input3 function producing the third input value
     * @param input4  the fourth input4 function producing the fourth input value
     * @return        a new {@link Func1} function that applies the original {@link Func4} function with the input values obtained from the input functions
     */
    public static <S, I1, I2, I3, I4, O> Func1<S, O> $(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values obtained from four objects implementing {@link HasPromise},
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link HasPromise}
     * @param <I2>    the type of the value obtained from the second {@link HasPromise}
     * @param <I3>    the type of the value obtained from the third {@link HasPromise}
     * @param <I4>    the type of the value obtained from the fourth {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func4} function to be applied to the input values obtained from the {@link HasPromise} objects
     * @param input1  the first {@link HasPromise} object providing the first input value
     * @param input2  the second {@link HasPromise} object providing the second input value
     * @param input3  the third {@link HasPromise} object providing the third input value
     * @param input4  the fourth {@link HasPromise} object providing the fourth input value
     * @return        a {@link Promise} containing the result of applying the function to the input values obtained from the {@link HasPromise} objects
     */
    public static <I1, I2, I3, I4, O> Promise<O> $(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link Func4} function to values obtained from four {@link Task} objects and returns a new {@link Task}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Task}
     * @param <I2>    the type of the value obtained from the second {@link Task}
     * @param <I3>    the type of the value obtained from the third {@link Task}
     * @param <I4>    the type of the value obtained from the fourth {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func4} function to be applied to the input values obtained from the {@link Task} objects
     * @param input1  the first {@link Task} providing the first input value
     * @param input2  the second {@link Task} providing the second input value
     * @param input3  the third {@link Task} providing the third input value
     * @param input4  the fourth {@link Task} providing the fourth input value
     * @return        a {@link Task} containing the result of applying the function to the input values obtained from the {@link Task} objects
     */
    public static <I1, I2, I3, I4, O> Task<O> $(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4) {
        return func.applyTo(input1, input2, input3, input4);
    }
    
    //-- Func5 --
    
    /**
     * Applies a {@link Func5} function to values provided as input parameters and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <O>     the type of the output produced by the function
     * @param func    the {@link Func5} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @return        the result of applying the function to the input values
     */
    public static <I1, I2, I3, I4, I5, O> O $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        return func.apply(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from the function's input1 parameter and returns a new {@link Func4}
     * function that takes four input parameters of types I2, I3, I4, and I5 and applies the original {@link Func5} function with them.
     *
     * @param <I1>    the type of the value obtained from the input1 parameter of the {@link Func5} function
     * @param <I2>    the type of the first input value for the resulting {@link Func4} function
     * @param <I3>    the type of the second input value for the resulting {@link Func4} function
     * @param <I4>    the type of the third input value for the resulting {@link Func4} function
     * @param <I5>    the type of the fourth input value for the resulting {@link Func4} function
     * @param <O>     the type of the output produced by the resulting {@link Func4} function
     * @param func    the {@link Func5} function to be applied to the input1 value
     * @param input1  the input value for the {@link Func5} function
     * @return        a new {@link Func4} function that applies the original Func5 function with the specified input value
     */
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> $(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Optional} objects and returns a new {@link Optional}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Optional}
     * @param <I2>    the type of the value obtained from the second {@link Optional}
     * @param <I3>    the type of the value obtained from the third {@link Optional}
     * @param <I4>    the type of the value obtained from the fourth {@link Optional}
     * @param <I5>    the type of the value obtained from the fifth {@link Optional}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Optional}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Optional} objects
     * @param input1  the first {@link Optional} providing the first input value
     * @param input2  the second {@link Optional} providing the second input value
     * @param input3  the third {@link Optional} providing the third input value
     * @param input4  the fourth {@link Optional} providing the fourth input value
     * @param input5  the fifth {@link Optional} providing the fifth input value
     * @return        an {@link Optional} containing the result of applying the function to the input values obtained from the {@link Optional} objects
     */
    public static <I1, I2, I3, I4, I5, O> Optional<O> $(Func5<I1, I2, I3, I4, I5, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Nullable} objects and returns a new {@link Nullable}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Nullable}
     * @param <I2>    the type of the value obtained from the second {@link Nullable}
     * @param <I3>    the type of the value obtained from the third {@link Nullable}
     * @param <I4>    the type of the value obtained from the fourth {@link Nullable}
     * @param <I5>    the type of the value obtained from the fifth {@link Nullable}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Nullable}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Nullable} objects
     * @param input1  the first {@link Nullable} providing the first input value
     * @param input2  the second {@link Nullable} providing the second input value
     * @param input3  the third {@link Nullable} providing the third input value
     * @param input4  the fourth {@link Nullable} providing the fourth input value
     * @param input5  the fifth {@link Nullable} providing the fifth input value
     * @return        a {@link Nullable} containing the result of applying the function to the input values obtained from the {@link Nullable} objects
     */
    public static <I1, I2, I3, I4, I5, O> Nullable<O> $(Func5<I1, I2, I3, I4, I5, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Func0} objects and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Func0}
     * @param <I2>    the type of the value obtained from the second {@link Func0}
     * @param <I3>    the type of the value obtained from the third {@link Func0}
     * @param <I4>    the type of the value obtained from the fourth {@link Func0}
     * @param <I5>    the type of the value obtained from the fifth {@link Func0}
     * @param <O>     the type of the output to be produced by the resulting {@link Func0}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Func0} objects
     * @param input1  the first {@link Func0} providing the first input value
     * @param input2  the second {@link Func0} providing the second input value
     * @param input3  the third {@link Func0} providing the third input value
     * @param input4  the fourth {@link Func0} providing the fourth input value
     * @param input5  the fifth {@link Func0} providing the fifth input value
     * @return        a {@link Func0} containing the result of applying the function to the input values obtained from the {@link Func0} objects
     */
    public static <I1, I2, I3, I4, I5, O> Func0<O> $(Func5<I1, I2, I3, I4, I5, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Func1} objects and returns a new {@link Func1}
     * that applies the original {@link Func5} function with input values obtained from the provided {@link Func1} objects.
     *
     * @param <S>     the type of the value produced by the resulting {@link Func1}
     * @param <I1>    the type of the value obtained from the first {@link Func1}
     * @param <I2>    the type of the value obtained from the second {@link Func1}
     * @param <I3>    the type of the value obtained from the third {@link Func1}
     * @param <I4>    the type of the value obtained from the fourth {@link Func1}
     * @param <I5>    the type of the value obtained from the fifth {@link Func1}
     * @param <O>     the type of the output produced by the original {@link Func5} function
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Func1} objects
     * @param input1  the first {@link Func1} providing the first input value
     * @param input2  the second {@link Func1} providing the second input value
     * @param input3  the third {@link Func1} providing the third input value
     * @param input4  the fourth {@link Func1} providing the fourth input value
     * @param input5  the fifth {@link Func1} providing the fifth input value
     * @return        a new {@link Func1} that applies the original {@link Func5} function with the specified input values
     */
    public static <S, I1, I2, I3, I4, I5, O> Func1<S, O> $(Func5<I1, I2, I3, I4, I5, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five objects implementing {@link HasPromise} and returns a new {@link Promise}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link HasPromise}
     * @param <I2>    the type of the value obtained from the second {@link HasPromise}
     * @param <I3>    the type of the value obtained from the third {@link HasPromise}
     * @param <I4>    the type of the value obtained from the fourth {@link HasPromise}
     * @param <I5>    the type of the value obtained from the fifth {@link HasPromise}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Promise}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link HasPromise} objects
     * @param input1  the first {@link HasPromise} providing the first input value
     * @param input2  the second {@link HasPromise} providing the second input value
     * @param input3  the third {@link HasPromise} providing the third input value
     * @param input4  the fourth {@link HasPromise} providing the fourth input value
     * @param input5  the fifth {@link HasPromise} providing the fifth input value
     * @return        a {@link Promise} containing the result of applying the function to the input values obtained from the {@link HasPromise} objects
     */
    public static <I1, I2, I3, I4, I5, O> Promise<O> $(Func5<I1, I2, I3, I4, I5, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link Func5} function to values obtained from five {@link Task} objects and returns a new {@link Task}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Task}
     * @param <I2>    the type of the value obtained from the second {@link Task}
     * @param <I3>    the type of the value obtained from the third {@link Task}
     * @param <I4>    the type of the value obtained from the fourth {@link Task}
     * @param <I5>    the type of the value obtained from the fifth {@link Task}
     * @param <O>     the type of the output to be encapsulated in the resulting {@link Task}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Task} objects
     * @param input1  the first Task providing the first input value
     * @param input2  the second Task providing the second input value
     * @param input3  the third Task providing the third input value
     * @param input4  the fourth Task providing the fourth input value
     * @param input5  the fifth Task providing the fifth input value
     * @return        a {@link Task} containing the result of applying the function to the input values obtained from the {@link Task} objects
     */
    public static <I1, I2, I3, I4, I5, O> Task<O> $(Func5<I1, I2, I3, I4, I5, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5) {
        return func.applyTo(input1, input2, input3, input4, input5);
    }
    
    //-- Func6 --
    
    /**
     * Applies a {@link Func6} function to values obtained from six input objects and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @return        the result of applying the function to the input values
     */
    public static <I1, I2, I3, I4, I5, I6, O> O $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        return func.apply(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Creates a {@link Func5} function by applying the first input value to a {@link Func6} function and returning the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the first input value
     * @param input1  the first input value
     * @return        a {@link Func5} function resulting from applying the first input value to the {@link Func6} function
     */
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Result} objects and returns the result as a {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a Result
     * @param input2  the second input value as a Result
     * @param input3  the third input value as a Result
     * @param input4  the fourth input value as a Result
     * @param input5  the fifth input value as a Result
     * @param input6  the sixth input value as a Result
     * @return        the result of applying the function to the input values as a {@link Result}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Optional} objects and returns the result as an {@link Optional}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as an {@link Optional}
     * @param input2  the second input value as an {@link Optional}
     * @param input3  the third input value as an {@link Optional}
     * @param input4  the fourth input value as an {@link Optional}
     * @param input5  the fifth input value as an {@link Optional}
     * @param input6  the sixth input value as an {@link Optional}
     * @return        the result of applying the function to the input values as an {@link Optional}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Optional<O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Nullable} objects and returns the result as a {@link Nullable}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Nullable}
     * @param input2  the second input value as a {@link Nullable}
     * @param input3  the third input value as a {@link Nullable}
     * @param input4  the fourth input value as a {@link Nullable}
     * @param input5  the fifth input value as a {@link Nullable}
     * @param input6  the sixth input value as a {@link Nullable}
     * @return        the result of applying the function to the input values as a {@link Nullable}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Nullable<O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Func0} objects and returns the result as a {@link Func0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Func0}
     * @param input2  the second input value as a {@link Func0}
     * @param input3  the third input value as a {@link Func0}
     * @param input4  the fourth input value as a {@link Func0}
     * @param input5  the fifth input value as a {@link Func0}
     * @param input6  the sixth input value as a {@link Func0}
     * @return        the result of applying the function to the input values as a {@link Func0}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Func0<O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Func1} objects and returns the result as a {@link Func1}.
     *
     * @param <S>     the type of the parameter for the {@link Func1} objects
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Func1}
     * @param input2  the second input value as a {@link Func1}
     * @param input3  the third input value as a {@link Func1}
     * @param input4  the fourth input value as a {@link Func1}
     * @param input5  the fifth input value as a {@link Func1}
     * @param input6  the sixth input value as a {@link Func1}
     * @return        the result of applying the function to the input values as a {@link Func1}
     */
    public static <S, I1, I2, I3, I4, I5, I6, O> Func1<S, O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link HasPromise} objects and returns the result as a {@link Promise}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link HasPromise}
     * @param input2  the second input value as a {@link HasPromise}
     * @param input3  the third input value as a {@link HasPromise}
     * @param input4  the fourth input value as a {@link HasPromise}
     * @param input5  the fifth input value as a {@link HasPromise}
     * @param input6  the sixth input value as a {@link HasPromise}
     * @return        the result of applying the function to the input values as a {@link Promise}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link Func6} function to values obtained from six {@link Task} objects and returns the result as a {@link Task}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func6} function to be applied to the input values
     * @param input1  the first input value as a {@link Task}
     * @param input2  the second input value as a {@link Task}
     * @param input3  the third input value as a {@link Task}
     * @param input4  the fourth input value as a {@link Task}
     * @param input5  the fifth input value as a {@link Task}
     * @param input6  the sixth input value as a {@link Task}
     * @return        the result of applying the function to the input values as a {@link Task}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Task<O> $(Func6<I1, I2, I3, I4, I5, I6, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6) {
        return func.applyTo(input1, input2, input3, input4, input5, input6);
    }
    
    //-- Func7 --
    
    /**
     * Applies a {@link Func7} function to values obtained from seven input parameters and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @param input7  the seventh input value
     * @return        the result of applying the function to the input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Partially applies a {@link Func7} function to the first input value, creating a new {@link Func6} function.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the Func7 function to be partially applied
     * @param input1  the first input value
     * @return        a new {@link Func6} function that takes the remaining six input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func6<I2, I3, I4, I5, I6, I7, O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func7} function to the input values wrapped in {@link Result} objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the Func7 function to be applied
     * @param input1  the Result containing the first input value
     * @param input2  the Result containing the second input value
     * @param input3  the Result containing the third input value
     * @param input4  the Result containing the fourth input value
     * @param input5  the Result containing the fifth input value
     * @param input6  the Result containing the sixth input value
     * @param input7  the Result containing the seventh input value
     * @return        the {@link Result} containing the output of the {@link Func7} function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values wrapped in {@link Optional} objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @param input4  the {@link Optional} containing the fourth input value
     * @param input5  the {@link Optional} containing the fifth input value
     * @param input6  the {@link Optional} containing the sixth input value
     * @param input7  the {@link Optional} containing the seventh input value
     * @return        the {@link Optional} containing the output of the {@link Func7} function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Optional<O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values wrapped in {@link Nullable} objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @param input5  the {@link Nullable} containing the fifth input value
     * @param input6  the {@link Nullable} containing the sixth input value
     * @param input7  the {@link Nullable} containing the seventh input value
     * @return        the {@link Nullable} containing the output of the {@link Func7} function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Nullable<O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from Func0 suppliers and wrapped in Nullable objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Func0} supplier for the first input value
     * @param input2  the {@link Func0} supplier for the second input value
     * @param input3  the {@link Func0} supplier for the third input value
     * @param input4  the {@link Func0} supplier for the fourth input value
     * @param input5  the {@link Func0} supplier for the fifth input value
     * @param input6  the {@link Func0} supplier for the sixth input value
     * @param input7  the {@link Func0} supplier for the seventh input value
     * @return        the result of applying the function to the input values as a {@link Func0}
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from {@link Func1} and wrapped in {@link Nullable} objects.
     *
     * @param <S>     the type of the source input
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Func1} supplier for the first input value
     * @param input2  the {@link Func1} supplier for the second input value
     * @param input3  the {@link Func1} supplier for the third input value
     * @param input4  the {@link Func1} supplier for the fourth input value
     * @param input5  the {@link Func1} supplier for the fifth input value
     * @param input6  the {@link Func1} supplier for the sixth input value
     * @param input7  the {@link Func1} supplier for the seventh input value
     * @return        a {@link Func1} that applies the {@link Func7} function to the provided input values
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, O> Func1<S, O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from {@link HasPromise} objects and returns a {@link Promise} of the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link HasPromise} for the first input value
     * @param input2  the {@link HasPromise} for the second input value
     * @param input3  the {@link HasPromise} for the third input value
     * @param input4  the {@link HasPromise} for the fourth input value
     * @param input5  the {@link HasPromise} for the fifth input value
     * @param input6  the {@link HasPromise} for the sixth input value
     * @param input7  the {@link HasPromise} for the seventh input value
     * @return        a {@link Promise} of the result of applying the {@link Func7} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link Func7} function to the input values obtained from Task objects and returns a Task of the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Task} for the first input value
     * @param input2  the {@link Task} for the second input value
     * @param input3  the {@link Task} for the third input value
     * @param input4  the {@link Task} for the fourth input value
     * @param input5  the {@link Task} for the fifth input value
     * @param input6  the {@link Task} for the sixth input value
     * @param input7  the {@link Task} for the seventh input value
     * @return        a {@link Task} of the result of applying the {@link Func7} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> $(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    //-- Func8 --
    
    /**
     * Applies a {@link Func8} function to the provided input values and returns the result.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func8} function to be applied
     * @param input1   the first input value
     * @param input2   the second input value
     * @param input3   the third input value
     * @param input4   the fourth input value
     * @param input5   the fifth input value
     * @param input6   the sixth input value
     * @param input7   the seventh input value
     * @param input8   the eighth input value
     * @return         the result of applying the {@link Func8} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Partially applies a {@link Func8} function by fixing the first input value.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func8} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func7} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func7<I2, I3, I4, I5, I6, I7, I8, O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @param input5  the {@link Result} containing the fifth input value
     * @param input6  the {@link Result} containing the sixth input value
     * @param input7  the {@link Result} containing the seventh input value
     * @param input8  the {@link Result} containing the eighth input value
     * @return        the {@link Result} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func8} function to a list of input values wrapped in {@link Optional}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func8} function to apply
     * @param input1  the {@link Optional} containing the first input value
     * @param input2  the {@link Optional} containing the second input value
     * @param input3  the {@link Optional} containing the third input value
     * @param input4  the {@link Optional} containing the fourth input value
     * @param input5  the {@link Optional} containing the fifth input value
     * @param input6  the {@link Optional} containing the sixth input value
     * @param input7  the {@link Optional} containing the seventh input value
     * @param input8  the {@link Optional} containing the eighth input value
     * @return        the {@link Optional} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Optional<O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Nullable}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @param input5  the {@link Nullable} containing the fifth input value
     * @param input6  the {@link Nullable} containing the sixth input value
     * @param input7  the {@link Nullable} containing the seventh input value
     * @param input8  the {@link Nullable} containing the eighth input value
     * @return        the {@link Nullable} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Nullable<O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func8} function to a list of input values wrapped in {@link Func0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func8} function to apply
     * @param input1  the {@link Func0} containing the first input value
     * @param input2  the {@link Func0} containing the second input value
     * @param input3  the {@link Func0} containing the third input value
     * @param input4  the {@link Func0} containing the fourth input value
     * @param input5  the {@link Func0} containing the fifth input value
     * @param input6  the {@link Func0} containing the sixth input value
     * @param input7  the {@link Func0} containing the seventh input value
     * @param input8  the {@link Func0} containing the eighth input value
     * @return        the {@link Func0} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func8} function to a list of input values wrapped in {@link Func1} for a specific context.
     *
     * @param <S>     the type of the context
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Func1} containing the first input value for the context
     * @param input2  the {@link Func1} containing the second input value for the context
     * @param input3  the {@link Func1} containing the third input value for the context
     * @param input4  the {@link Func1} containing the fourth input value for the context
     * @param input5  the {@link Func1} containing the fifth input value for the context
     * @param input6  the {@link Func1} containing the sixth input value for the context
     * @param input7  the {@link Func1} containing the seventh input value for the context
     * @param input8  the {@link Func1} containing the eighth input value for the context
     * @return        the {@link Func1} containing the output of the function for the context
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, O> Func1<S, O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link HasPromise} containing the first input value for asynchronous processing
     * @param input2  the {@link HasPromise} containing the second input value for asynchronous processing
     * @param input3  the {@link HasPromise} containing the third input value for asynchronous processing
     * @param input4  the {@link HasPromise} containing the fourth input value for asynchronous processing
     * @param input5  the {@link HasPromise} containing the fifth input value for asynchronous processing
     * @param input6  the {@link HasPromise} containing the sixth input value for asynchronous processing
     * @param input7  the {@link HasPromise} containing the seventh input value for asynchronous processing
     * @param input8  the {@link HasPromise} containing the eighth input value for asynchronous processing
     * @return        a {@link Promise} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Task} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Task} containing the first input value for asynchronous processing
     * @param input2  the {@link Task} containing the second input value for asynchronous processing
     * @param input3  the {@link Task} containing the third input value for asynchronous processing
     * @param input4  the {@link Task} containing the fourth input value for asynchronous processing
     * @param input5  the {@link Task} containing the fifth input value for asynchronous processing
     * @param input6  the {@link Task} containing the sixth input value for asynchronous processing
     * @param input7  the {@link Task} containing the seventh input value for asynchronous processing
     * @param input8  the {@link Task} containing the eighth input value for asynchronous processing
     * @return        a {@link Task} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> $(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    //-- Func9 --
    
    /**
     * Applies a {@link Func9} function to the provided input values and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to be applied
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @param input7  the seventh input value
     * @param input8  the eighth input value
     * @param input9  the ninth input value
     * @return        the result of applying the {@link Func9} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> O $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Partially applies a {@link Func9} function by fixing the first input value.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func8} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func8<I2, I3, I4, I5, I6, I7, I8, I9, O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @param input5  the {@link Result} containing the fifth input value
     * @param input6  the {@link Result} containing the sixth input value
     * @param input7  the {@link Result} containing the seventh input value
     * @param input8  the {@link Result} containing the eighth input value
     * @param input9  the {@link Result} containing the ninth input value
     * @return        the {@link Result} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Result<O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Optional}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func9} function to apply
     * @param input1   the {@link Optional} containing the first input value
     * @param input2   the {@link Optional} containing the second input value
     * @param input3   the {@link Optional} containing the third input value
     * @param input4   the {@link Optional} containing the fourth input value
     * @param input5   the {@link Optional} containing the fifth input value
     * @param input6   the {@link Optional} containing the sixth input value
     * @param input7   the {@link Optional} containing the seventh input value
     * @param input8   the {@link Optional} containing the eighth input value
     * @param input9   the {@link Optional} containing the ninth input value
     * @return         the {@link Optional} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Optional<O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8, Optional<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Nullable}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link Nullable} containing the first input value
     * @param input2  the {@link Nullable} containing the second input value
     * @param input3  the {@link Nullable} containing the third input value
     * @param input4  the {@link Nullable} containing the fourth input value
     * @param input5  the {@link Nullable} containing the fifth input value
     * @param input6  the {@link Nullable} containing the sixth input value
     * @param input7  the {@link Nullable} containing the seventh input value
     * @param input8  the {@link Nullable} containing the eighth input value
     * @param input9  the {@link Nullable} containing the ninth input value
     * @return        the {@link Nullable} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Nullable<O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8, Nullable<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Func0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Func0} containing the first input value
     * @param input2  the {@link Func0} containing the second input value
     * @param input3  the {@link Func0} containing the third input value
     * @param input4  the {@link Func0} containing the fourth input value
     * @param input5  the {@link Func0} containing the fifth input value
     * @param input6  the {@link Func0} containing the sixth input value
     * @param input7  the {@link Func0} containing the seventh input value
     * @param input8  the {@link Func0} containing the eighth input value
     * @param input9  the {@link Func0} containing the ninth input value
     * @return        the {@link Func0} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Func1} for a specific context.
     *
     * @param <S>     the type of the context
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Func1} containing the first input value for the context
     * @param input2  the {@link Func1} containing the second input value for the context
     * @param input3  the {@link Func1} containing the third input value for the context
     * @param input4  the {@link Func1} containing the fourth input value for the context
     * @param input5  the {@link Func1} containing the fifth input value for the context
     * @param input6  the {@link Func1} containing the sixth input value for the context
     * @param input7  the {@link Func1} containing the seventh input value for the context
     * @param input8  the {@link Func1} containing the eighth input value for the context
     * @param input9  the {@link Func1} containing the ninth input value for the context
     * @return        the {@link Func1} containing the output of the function for the context
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func1<S, O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to apply
     * @param input1  the {@link HasPromise} containing the first input value for asynchronous processing
     * @param input2  the {@link HasPromise} containing the second input value for asynchronous processing
     * @param input3  the {@link HasPromise} containing the third input value for asynchronous processing
     * @param input4  the {@link HasPromise} containing the fourth input value for asynchronous processing
     * @param input5  the {@link HasPromise} containing the fifth input value for asynchronous processing
     * @param input6  the {@link HasPromise} containing the sixth input value for asynchronous processing
     * @param input7  the {@link HasPromise} containing the seventh input value for asynchronous processing
     * @param input8  the {@link HasPromise} containing the eighth input value for asynchronous processing
     * @param input9  the {@link HasPromise} containing the ninth input value for asynchronous processing
     * @return        a {@link Promise} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Promise<O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link Func9} function to a list of input values wrapped in {@link Task} for asynchronous processing.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func9} function to apply
     * @param input1  the {@link Task} containing the first input value for asynchronous processing
     * @param input2  the {@link Task} containing the second input value for asynchronous processing
     * @param input3  the {@link Task} containing the third input value for asynchronous processing
     * @param input4  the {@link Task} containing the fourth input value for asynchronous processing
     * @param input5  the {@link Task} containing the fifth input value for asynchronous processing
     * @param input6  the {@link Task} containing the sixth input value for asynchronous processing
     * @param input7  the {@link Task} containing the seventh input value for asynchronous processing
     * @param input8  the {@link Task} containing the eighth input value for asynchronous processing
     * @param input9  the {@link Task} containing the ninth input value for asynchronous processing
     * @return        a {@link Task} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Task<O> $(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    //-- Func10 --
    
    /**
     * Applies a {@link Func10} function to the provided input values and returns the result.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to be applied
     * @param input1   the first input value
     * @param input2   the second input value
     * @param input3   the third input value
     * @param input4   the fourth input value
     * @param input5   the fifth input value
     * @param input6   the sixth input value
     * @param input7   the seventh input value
     * @param input8   the eighth input value
     * @param input9   the ninth input value
     * @param input10  the tenth input value
     * @return         the result of applying the {@link Func10} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> O $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
        return func.apply(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Partially applies a {@link Func10} function by fixing the first input value.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <I8>    the type of the eighth input value
     * @param <I9>    the type of the ninth input value
     * @param <I10>   the type of the tenth input value
     * @param <O>     the type of the output
     * @param func    the {@link Func10} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func9} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func9<I2, I3, I4, I5, I6, I7, I8, I9, I10, O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1) {
        return func.apply(input1);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Result}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Result} containing the first input value
     * @param input2   the {@link Result} containing the second input value
     * @param input3   the {@link Result} containing the third input value
     * @param input4   the {@link Result} containing the fourth input value
     * @param input5   the {@link Result} containing the fifth input value
     * @param input6   the {@link Result} containing the sixth input value
     * @param input7   the {@link Result} containing the seventh input value
     * @param input8   the {@link Result} containing the eighth input value
     * @param input9   the {@link Result} containing the ninth input value
     * @param input10  the {@link Result} containing the tenth input value
     * @return         the {@link Result} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Result<O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9, Result<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Optional}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Optional} containing the first input value
     * @param input2   the {@link Optional} containing the second input value
     * @param input3   the {@link Optional} containing the third input value
     * @param input4   the {@link Optional} containing the fourth input value
     * @param input5   the {@link Optional} containing the fifth input value
     * @param input6   the {@link Optional} containing the sixth input value
     * @param input7   the {@link Optional} containing the seventh input value
     * @param input8   the {@link Optional} containing the eighth input value
     * @param input9   the {@link Optional} containing the ninth input value
     * @param input10  the {@link Optional} containing the tenth input value
     * @return         the {@link Optional} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Optional<O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8, Optional<I9> input9, Optional<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Nullable}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Nullable} containing the first input value
     * @param input2   the {@link Nullable} containing the second input value
     * @param input3   the {@link Nullable} containing the third input value
     * @param input4   the {@link Nullable} containing the fourth input value
     * @param input5   the {@link Nullable} containing the fifth input value
     * @param input6   the {@link Nullable} containing the sixth input value
     * @param input7   the {@link Nullable} containing the seventh input value
     * @param input8   the {@link Nullable} containing the eighth input value
     * @param input9   the {@link Nullable} containing the ninth input value
     * @param input10  the {@link Nullable} containing the tenth input value
     * @return         the {@link Nullable} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Nullable<O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8, Nullable<I9> input9, Nullable<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Func0}.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Func0} containing the first input value
     * @param input2   the {@link Func0} containing the second input value
     * @param input3   the {@link Func0} containing the third input value
     * @param input4   the {@link Func0} containing the fourth input value
     * @param input5   the {@link Func0} containing the fifth input value
     * @param input6   the {@link Func0} containing the sixth input value
     * @param input7   the {@link Func0} containing the seventh input value
     * @param input8   the {@link Func0} containing the eighth input value
     * @param input9   the {@link Func0} containing the ninth input value
     * @param input10  the {@link Func0} containing the tenth input value
     * @return         the {@link Func0} containing the output of the function
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func0<O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9, Func0<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Func1} for a specific context.
     *
     * @param <S>      the type of the context
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Func1} containing the first input value for the context
     * @param input2   the {@link Func1} containing the second input value for the context
     * @param input3   the {@link Func1} containing the third input value for the context
     * @param input4   the {@link Func1} containing the fourth input value for the context
     * @param input5   the {@link Func1} containing the fifth input value for the context
     * @param input6   the {@link Func1} containing the sixth input value for the context
     * @param input7   the {@link Func1} containing the seventh input value for the context
     * @param input8   the {@link Func1} containing the eighth input value for the context
     * @param input9   the {@link Func1} containing the ninth input value for the context
     * @param input10  the {@link Func1} containing the tenth input value for the context
     * @return         the {@link Func1} containing the output of the function for the context
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func1<S, O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9, Func1<S, I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link HasPromise} containing the first input value for asynchronous processing
     * @param input2   the {@link HasPromise} containing the second input value for asynchronous processing
     * @param input3   the {@link HasPromise} containing the third input value for asynchronous processing
     * @param input4   the {@link HasPromise} containing the fourth input value for asynchronous processing
     * @param input5   the {@link HasPromise} containing the fifth input value for asynchronous processing
     * @param input6   the {@link HasPromise} containing the sixth input value for asynchronous processing
     * @param input7   the {@link HasPromise} containing the seventh input value for asynchronous processing
     * @param input8   the {@link HasPromise} containing the eighth input value for asynchronous processing
     * @param input9   the {@link HasPromise} containing the ninth input value for asynchronous processing
     * @param input10  the {@link HasPromise} containing the tenth input value for asynchronous processing
     * @return         a {@link Promise} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Promise<O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9, HasPromise<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link Func10} function to a list of input values wrapped in {@link Task} for asynchronous processing.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param <I9>     the type of the ninth input value
     * @param <I10>    the type of the tenth input value
     * @param <O>      the type of the output
     * @param func     the {@link Func10} function to apply
     * @param input1   the {@link Task} containing the first input value for asynchronous processing
     * @param input2   the {@link Task} containing the second input value for asynchronous processing
     * @param input3   the {@link Task} containing the third input value for asynchronous processing
     * @param input4   the {@link Task} containing the fourth input value for asynchronous processing
     * @param input5   the {@link Task} containing the fifth input value for asynchronous processing
     * @param input6   the {@link Task} containing the sixth input value for asynchronous processing
     * @param input7   the {@link Task} containing the seventh input value for asynchronous processing
     * @param input8   the {@link Task} containing the eighth input value for asynchronous processing
     * @param input9   the {@link Task} containing the ninth input value for asynchronous processing
     * @param input10  the {@link Task} containing the tenth input value for asynchronous processing
     * @return         a {@link Task} representing the result of the asynchronous computation
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Task<O> $(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9, Task<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> Result<O> $$(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
        return applySafely(func, input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> $$(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
        return applySafely(func, input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Result<O> $$(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
        return applySafely(func, input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <I10>  the input 10 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @param input10 the input 10 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Result<O> $$(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
        return applySafely(func, input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> Result<O> applySafely(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
        return func.applySafely(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> applySafely(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
        return func.applySafely(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Result<O> applySafely(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
        return func.applySafely(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Apply the function safely (return {@code Result<O>}).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <I10>  the input 10 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @param input10 the input 10 to be applied to.
     * @return        the output as a result.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Result<O> applySafely(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
        return func.applySafely(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
     * Apply the function unsafely (might throw exception).
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
     * Apply the function unsafely (might throw exception).
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
     * Apply the function unsafely (might throw exception).
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
     * Apply the function unsafely (might throw exception).
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
     * Apply the function unsafely (might throw exception).
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
     * Apply the function unsafely (might throw exception).
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
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> O $$$(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4, input5, input6, input7);
    }

    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O $$$(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> O $$$(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <I10>  the input 10 data type.
     * @param <O>    the output data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @param input10 the input 10 to be applied to.
     * @return        the output as a result.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> O $$$(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) throws Exception {
        return func.applyUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param func  the function to execute.
     * @throws Exception  the exception from the function.
     */
    public static void $$$(FuncUnit0 func) throws Exception {
        func.runUnsafe();
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1> void $$$(FuncUnit1<I1> func, I1 input1) throws Exception {
        func.acceptUnsafe(input1);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2> void $$$(FuncUnit2<I1, I2> func, I1 input1, I2 input2) throws Exception {
        func.acceptUnsafe(input1, input2);
    }
    
    // Similar methods for FuncUnit3 to FuncUnit10
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3> void $$$(FuncUnit3<I1, I2, I3> func, I1 input1, I2 input2, I3 input3) throws Exception {
        func.acceptUnsafe(input1, input2, input3);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4> void $$$(FuncUnit4<I1, I2, I3, I4> func, I1 input1, I2 input2, I3 input3, I4 input4) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5> void $$$(FuncUnit5<I1, I2, I3, I4, I5> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4, input5);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6> void $$$(FuncUnit6<I1, I2, I3, I4, I5, I6> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7> void $$$(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8> void $$$(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9> void $$$(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Apply the function unsafely (might throw exception).
     *
     * @param <I1>   the input 1 data type.
     * @param <I2>   the input 2 data type.
     * @param <I3>   the input 3 data type.
     * @param <I4>   the input 4 data type.
     * @param <I5>   the input 5 data type.
     * @param <I6>   the input 6 data type.
     * @param <I7>   the input 7 data type.
     * @param <I8>   the input 8 data type.
     * @param <I9>   the input 9 data type.
     * @param <I10>  the input 10 data type.
     *
     * @param func    the function.
     * @param input1  the input 1 to be applied to.
     * @param input2  the input 2 to be applied to.
     * @param input3  the input 3 to be applied to.
     * @param input4  the input 4 to be applied to.
     * @param input5  the input 5 to be applied to.
     * @param input6  the input 6 to be applied to.
     * @param input7  the input 7 to be applied to.
     * @param input8  the input 8 to be applied to.
     * @param input9  the input 9 to be applied to.
     * @param input10 the input 10 to be applied to.
     * @throws Exception  the exception from the function.
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> void $$$(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) throws Exception {
        func.acceptUnsafe(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
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
