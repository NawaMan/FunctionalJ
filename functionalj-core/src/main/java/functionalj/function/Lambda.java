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

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.promise.HasPromise;
import functionalj.promise.Promise;
import functionalj.result.Result;
import functionalj.stream.ZipWithOption;
import functionalj.task.Task;
import nullablej.nullable.Nullable;

/**
 * A utility class for creating function using Lambda (λ).
 **/
public class Lambda {
    
    /**
     * Constructs a Func0 from supplier or lambda.
     *
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> λ(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> λ(Func1<INPUT, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> λ(Func2<INPUT1, INPUT2, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> λ(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> λ(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> λ(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func6 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> λ(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func7 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func7.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> λ(Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func8 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func8.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> λ(Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func9 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func9.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> λ(Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func10 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <INPUT10> the tenth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func10.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> λ(Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a FuncUnit0 from runnable or lambda.
     *
     * @param  runnable  the runnable or lambda.
     * @return           the result FuncUnit0.
     */
    public static FuncUnit0 λ(FuncUnit0 runnable) {
        return runnable;
    }
    
    /**
     * Constructs a FuncUnit1 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT>   the input data type.
     * @return           the result FuncUnit1.
     */
    public static <INPUT> FuncUnit1<INPUT> λ(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit2 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @return           the result FuncUnit2.
     */
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> λ(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit3 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @return           the result FuncUnit3.
     */
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> λ(FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit4 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @return           the result FuncUnit4.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4> FuncUnit4<INPUT1, INPUT2, INPUT3, INPUT4> λ(FuncUnit4<INPUT1, INPUT2, INPUT3, INPUT4> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit5 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @return           the result FuncUnit5.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> FuncUnit5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> λ(FuncUnit5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit6 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @return           the result FuncUnit6.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> FuncUnit6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> λ(FuncUnit6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit7 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @return           the result FuncUnit7.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7> FuncUnit7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7> λ(FuncUnit7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit8 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @return           the result FuncUnit8.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8> FuncUnit8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8> λ(FuncUnit8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit9 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @return           the result FuncUnit9.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> FuncUnit9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> λ(FuncUnit9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a FuncUnit10 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <INPUT10> the tenth input data type.
     * @return           the result FuncUnit10.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> FuncUnit10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> λ(FuncUnit10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> consumer) {
        return consumer;
    }
    
    //== Named ==
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  name      the name.
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     */
    public static <OUTPUT> Func0<OUTPUT> λ(String name, Func0<OUTPUT> supplier) {
        return Named.func0(name, supplier);
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     */
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> λ(String name, Func1<INPUT, OUTPUT> function) {
        return Named.func1(name, function);
    }
    
    /**
     * Constructs a Func2 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     */
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> λ(String name, Func2<INPUT1, INPUT2, OUTPUT> function) {
        return Named.func2(name, function);
    }
    
    /**
     * Constructs a Func3 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func3<INPUT1, INPUT2, INPUT3, OUTPUT> λ(String name, Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
        return Named.func3(name, function);
    }
    
    /**
     * Constructs a Func4 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> λ(String name, Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
        return Named.func4(name, function);
    }
    
    /**
     * Constructs a Func5 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> λ(String name, Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return Named.func5(name, function);
    }
    
    /**
     * Constructs a Func6 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the forth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func3.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> λ(String name, Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return Named.func6(name, function);
    }
    
    /**
     * Constructs a Func7 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func7.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> λ(String name, Func7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT> function) {
        return Named.func7(name, function);
    }
    
    /**
     * Constructs a Func8 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func8.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> λ(String name, Func8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT> function) {
        return Named.func8(name, function);
    }
    
    /**
     * Constructs a Func9 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func9.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> λ(String name, Func9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT> function) {
        return Named.func9(name, function);
    }
    
    /**
     * Constructs a Func10 from function or lambda.
     *
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <INPUT10> the tenth input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func10.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> λ(String name, Func10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT> function) {
        return Named.func10(name, function);
    }
    
    /**
     * Constructs a FuncUnit0 from runnable or lambda.
     *
     * @param  runnable  the runnable or lambda.
     * @return           the result FuncUnit0.
     */
    public static FuncUnit0 λ(String name, FuncUnit0 runnable) {
        return Named.funcUnit0(name, runnable);
    }
    
    /**
     * Constructs a FuncUnit1 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT>   the input data type.
     * @return           the result FuncUnit1.
     */
    public static <INPUT> FuncUnit1<INPUT> λ(String name, FuncUnit1<INPUT> consumer) {
        return Named.funcUnit1(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit2 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @return           the result FuncUnit2.
     */
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> λ(String name, FuncUnit2<INPUT1, INPUT2> consumer) {
        return Named.funcUnit2(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit3 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @return           the result FuncUnit3.
     */
    public static <INPUT1, INPUT2, INPUT3> FuncUnit3<INPUT1, INPUT2, INPUT3> λ(String name, FuncUnit3<INPUT1, INPUT2, INPUT3> consumer) {
        return Named.funcUnit3(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit4 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @return           the result FuncUnit4.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4> FuncUnit4<INPUT1, INPUT2, INPUT3, INPUT4> λ(String name, FuncUnit4<INPUT1, INPUT2, INPUT3, INPUT4> consumer) {
        return Named.funcUnit4(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit5 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @return           the result FuncUnit5.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> FuncUnit5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> λ(String name, FuncUnit5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5> consumer) {
        return Named.funcUnit5(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit6 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @return           the result FuncUnit6.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> FuncUnit6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> λ(String name, FuncUnit6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> consumer) {
        return Named.funcUnit6(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit7 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @return           the result FuncUnit7.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7> FuncUnit7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7> λ(String name, FuncUnit7<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7> consumer) {
        return Named.funcUnit7(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit8 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @return           the result FuncUnit8.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8> FuncUnit8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8> λ(String name, FuncUnit8<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8> consumer) {
        return Named.funcUnit8(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit9 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @return           the result FuncUnit9.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> FuncUnit9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> λ(String name, FuncUnit9<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9> consumer) {
        return Named.funcUnit9(name, consumer);
    }
    
    /**
     * Constructs a FuncUnit10 from consumer or lambda.
     *
     * @param  consumer  the consumer or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <INPUT3>  the third input data type.
     * @param  <INPUT4>  the fourth input data type.
     * @param  <INPUT5>  the fifth input data type.
     * @param  <INPUT6>  the sixth input data type.
     * @param  <INPUT7>  the seventh input data type.
     * @param  <INPUT8>  the eighth input data type.
     * @param  <INPUT9>  the ninth input data type.
     * @param  <INPUT10> the tenth input data type.
     * @return           the result FuncUnit10.
     */
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> FuncUnit10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> λ(String name, FuncUnit10<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10> consumer) {
        return Named.funcUnit10(name, consumer);
    }
    
    //== Apply ==
    
    /**
     * Applies a {@link Func1} function to a given input and returns the result.
     *
     * @param <I>   the type of the input to the function
     * @param <O>   the type of the output returned by the function
     * @param func  the Func1 function to be applied
     * @param input the input value to be passed to the function
     * @return      the result of applying the {@link Func1} function to the given input
     */
    public static <I, O> O λ(Func1<I, O> func, I input) {
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
    public static <I, O> Result<O> λ(Func1<I, O> func, Result<I> input) {
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
    public static <I, O> Optional<O> λ(Func1<I, O> func, Optional<I> input) {
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
    public static <I, O> Nullable<O> λ(Func1<I, O> func, Nullable<I> input) {
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
    public static <I, O> FuncList<O> λ(Func1<I, O> func, List<I> input) {
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
    public static <K, I, O> FuncMap<K, O> λ(Func1<I, O> func, Map<K, I> input) {
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
    public static <I, O> Func0<O> λ(Func1<I, O> func, Func0<I> input) {
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
    public static <S, I, O> Func1<S, O> λ(Func1<I, O> func, Func1<S, I> input) {
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
    public static <I, O> Promise<O> λ(Func1<I, O> func, HasPromise<I> input) {
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
    public static <I, O> Task<O> λ(Func1<I, O> func, Task<I> input) {
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
    public static <I1, I2, O> O λ(Func2<I1, I2, O> func, I1 input1, I2 input2) {
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
    public static <I1, I2, O> Func1<I2, O> λ(Func2<I1, I2, O> func, I1 input1) {
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
    public static <I1, I2, O> Result<O> λ(Func2<I1, I2, O> func, Result<I1> input1, Result<I2> input2) {
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
    public static <I1, I2, O> Optional<O> λ(Func2<I1, I2, O> func, Optional<I1> input1, Optional<I2> input2) {
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
    public static <I1, I2, O> Nullable<O> λ(Func2<I1, I2, O> func, Nullable<I1> input1, Nullable<I2> input2) {
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
    public static <I1, I2, O> FuncList<O> λ(Func2<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2) {
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
    public static <I1, I2, O> FuncList<O> λ(Func2<I1, I2, O> func, FuncList<I1> input1, FuncList<I2> input2, ZipWithOption option) {
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
    public static <K, I1, I2, O> FuncMap<K, O> λ(Func2<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2) {
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
    public static <K, I1, I2, O> FuncMap<K, O> λ(Func2<I1, I2, O> func, FuncMap<K, I1> input1, FuncMap<K, I2> input2, ZipWithOption option) {
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
    public static <I1, I2, O> Func0<O> λ(Func2<I1, I2, O> func, Func0<I1> input1, Func0<I2> input2) {
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
    public static <S, I1, I2, O> Func1<S, O> λ(Func2<I1, I2, O> func, Func1<S, I1> input1, Func1<S, I2> input2) {
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
    public static <I1, I2, O> Promise<O> λ(Func2<I1, I2, O> func, HasPromise<I1> input1, HasPromise<I2> input2) {
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
    public static <I1, I2, O> Task<O> λ(Func2<I1, I2, O> func, Task<I1> input1, Task<I2> input2) {
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
    public static <I1, I2, I3, O> O λ(Func3<I1, I2, I3, O> func, I1 input1, I2 input2, I3 input3) {
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
    public static <I1, I2, I3, O> Func2<I2, I3, O> λ(Func3<I1, I2, I3, O> func, I1 input1) {
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
    public static <I1, I2, I3, O> Result<O> λ(Func3<I1, I2, I3, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3) {
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
    public static <I1, I2, I3, O> Optional<O> λ(Func3<I1, I2, I3, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3) {
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
    public static <I1, I2, I3, O> Nullable<O> λ(Func3<I1, I2, I3, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3) {
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
    public static <I1, I2, I3, O> Func0<O> λ(Func3<I1, I2, I3, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3) {
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
    public static <S, I1, I2, I3, O> Func1<S, O> λ(Func3<I1, I2, I3, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3) {
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
    public static <I1, I2, I3, O> Promise<O> λ(Func3<I1, I2, I3, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
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
    public static <I1, I2, I3, O> Task<O> λ(Func3<I1, I2, I3, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3) {
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
    public static <I1, I2, I3, I4, O> O λ(Func4<I1, I2, I3, I4, O> func, I1 input1, I2 input2, I3 input3, I4 input4) {
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
    public static <I1, I2, I3, I4, O> Func3<I2, I3, I4, O> λ(Func4<I1, I2, I3, I4, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, O> Result<O> λ(Func4<I1, I2, I3, I4, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4) {
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
    public static <I1, I2, I3, I4, O> Optional<O> λ(Func4<I1, I2, I3, I4, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4) {
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
    public static <I1, I2, I3, I4, O> Nullable<O> λ(Func4<I1, I2, I3, I4, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4) {
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
    public static <I1, I2, I3, I4, O> Func0<O> λ(Func4<I1, I2, I3, I4, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4) {
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
    public static <S, I1, I2, I3, I4, O> Func1<S, O> λ(Func4<I1, I2, I3, I4, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4) {
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
    public static <I1, I2, I3, I4, O> Promise<O> λ(Func4<I1, I2, I3, I4, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
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
    public static <I1, I2, I3, I4, O> Task<O> λ(Func4<I1, I2, I3, I4, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4) {
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
    public static <I1, I2, I3, I4, I5, O> O λ(Func5<I1, I2, I3, I4, I5, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
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
    public static <I1, I2, I3, I4, I5, O> Func4<I2, I3, I4, I5, O> λ(Func5<I1, I2, I3, I4, I5, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, I5, O> Optional<O> λ(Func5<I1, I2, I3, I4, I5, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5) {
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
    public static <I1, I2, I3, I4, I5, O> Nullable<O> λ(Func5<I1, I2, I3, I4, I5, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5) {
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
    public static <I1, I2, I3, I4, I5, O> Func0<O> λ(Func5<I1, I2, I3, I4, I5, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5) {
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
    public static <S, I1, I2, I3, I4, I5, O> Func1<S, O> λ(Func5<I1, I2, I3, I4, I5, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5) {
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
    public static <I1, I2, I3, I4, I5, O> Promise<O> λ(Func5<I1, I2, I3, I4, I5, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
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
    public static <I1, I2, I3, I4, I5, O> Task<O> λ(Func5<I1, I2, I3, I4, I5, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5) {
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
    public static <I1, I2, I3, I4, I5, I6, O> O λ(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Func5<I2, I3, I4, I5, I6, O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Optional<O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Nullable<O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Func0<O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6) {
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
    public static <S, I1, I2, I3, I4, I5, I6, O> Func1<S, O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
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
    public static <I1, I2, I3, I4, I5, I6, O> Task<O> λ(Func6<I1, I2, I3, I4, I5, I6, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func6<I2, I3, I4, I5, I6, I7, O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Optional<O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Nullable<O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7) {
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, O> Func1<S, O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> λ(Func7<I1, I2, I3, I4, I5, I6, I7, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> O λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Func7<I2, I3, I4, I5, I6, I7, I8, O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Optional<O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Nullable<O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8) {
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, O> Func1<S, O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> λ(Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> O λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func8<I2, I3, I4, I5, I6, I7, I8, I9, O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Result<O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Optional<O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8, Optional<I9> input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Nullable<O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8, Nullable<I9> input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func0<O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9) {
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func1<S, O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Promise<O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Task<O> λ(Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> O λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func9<I2, I3, I4, I5, I6, I7, I8, I9, I10, O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, I1 input1) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Result<O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9, Result<I10> input10) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Optional<O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Optional<I1> input1, Optional<I2> input2, Optional<I3> input3, Optional<I4> input4, Optional<I5> input5, Optional<I6> input6, Optional<I7> input7, Optional<I8> input8, Optional<I9> input9, Optional<I10> input10) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Nullable<O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Nullable<I1> input1, Nullable<I2> input2, Nullable<I3> input3, Nullable<I4> input4, Nullable<I5> input5, Nullable<I6> input6, Nullable<I7> input7, Nullable<I8> input8, Nullable<I9> input9, Nullable<I10> input10) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func0<O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9, Func0<I10> input10) {
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func1<S, O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9, Func1<S, I10> input10) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Promise<O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9, HasPromise<I10> input10) {
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Task<O> λ(Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9, Task<I10> input10) {
        return func.applyTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    //-- FuncUnit1 --
    
    /**
     * Applies a {@link FuncUnit1} function to a given input and returns the result.
     *
     * @param <I>   the type of the input to the function
     * @param func  the Func1 function to be applied
     * @param input the input value to be passed to the function
     */
    public static <I> void λ(FuncUnit1<I> func, I input) {
        func.apply(input);
    }
    
    /**
     * Applies a {@link FuncUnit1} function to the value inside a {@link Result} if it exists,
     * wrapping the result or any exception in a new {@link Result}.
     *
     * @param <I>    the type of the input inside the {@link Result}
     * @param <O>    the type of the output inside the {@link Result}
     * @param func   the {@link FuncUnit1} function to be applied to the input value
     * @param input  the {@link Result} containing the input value or an exception
     * @return       a {@link Result} containing either the function's output or any exception thrown
     */
    public static <I, O> Result<O> λ(FuncUnit1<I> func, Result<I> input) {
        return func.acceptTo(input);
    }
    
    /**
     * Applies a {@link FuncUnit1} function to the result of a {@link Func0} and returns a {@link Func0}
     * containing the transformed result.
     *
     * @param <I>    the type of the input produced by the {@link Func0}
     * @param func   the {@link FuncUnit1} function to be applied to the result of the {@link Func0}
     * @param input  the {@link Func0} whose result will be transformed by the function
     */
    public static <I> void λ(FuncUnit1<I> func, Func0<I> input) {
        func.acceptTo(input);
    }
    
    /**
     * Composes two {@link FuncUnit1} functions to create a new {@link FuncUnit1} function.
     *
     * @param <S>    the type of the input parameter for the first {@link Func1}
     * @param <I>    the type of the output of the first Func1 and the input of the second {@link Func1}
     * @param func   the second {@link FuncUnit1} function to be applied
     * @param input  the first {@link Func1} function whose output will be the input to the second function
     * @return       a Func1 function that applies the first function and then the second function to its input
     */
    public static <S, I> FuncUnit1<S> λ(FuncUnit1<I> func, Func1<S, I> input) {
        return func.acceptTo(input);
    }
    
    /**
     * Applies a {@link FuncUnit1} function to the value inside a {@link HasPromise}, if present,
     * and returns a {@link Promise} containing the result.
     *
     * @param <I>    the type of the input value within the {@link HasPromise}
     * @param <O>    the type of the output value within the {@link HasPromise}
     * @param func   the {@link FuncUnit1} function to be applied to the input value, if present
     * @param input  the {@link HasPromise} containing the input value
     * @return       a {@link Promise} containing the result of applying the function, or a pending {@link Promise} if input is not ready
     */
    public static <I, O> Promise<O> λ(FuncUnit1<I> func, HasPromise<I> input) {
        return func.acceptTo(input);
    }
    
    /**
     * Applies a {@link FuncUnit1} function to the value inside a {@link Task} if it becomes available,
     * and returns a new {@link Task} containing the result.
     *
     * @param <I>    the type of the input value within the {@link Task}
     * @param <O>    the type of the output value within the {@link Task}
     * @param func   the {@link FuncUnit1} function to be applied to the input value, when available
     * @param input  the {@link Task} supplying the input value
     * @return       a {@link Task} that will contain the result of applying the function, once available
     */
    public static <I, O> Task<O> λ(FuncUnit1<I> func, Task<I> input) {
        return func.acceptTo(input);
    }
    
    //-- FuncUnit2 --
    
    /**
     * Invokes a {@link FuncUnit2} function with two input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param func    the {@link FuncUnit2} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     */
    public static <I1, I2> void λ(FuncUnit2<I1, I2> func, I1 input1, I2 input2) {
        func.accept(input1, input2);
    }
    
    /**
     * Partially applies a {@link FuncUnit2} function by fixing the first input parameter,
     *     resulting in a new {@link FuncUnit1} function.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param func    the {@link FuncUnit2} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link FuncUnit1} function that takes the second input parameter and applies
     *                the original {@link FuncUnit2} function with the fixed first input parameter
     */
    public static <I1, I2> FuncUnit1<I2> λ(FuncUnit2<I1, I2> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit2} function to the values inside two {@link Result} objects if both values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <O>     the type of the output of the {@link Result}
     * @param func    the {@link FuncUnit2} function to be applied to the input values, if both values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if either input is an exception
     */
    public static <I1, I2, O> Result<O> λ(FuncUnit2<I1, I2> func, Result<I1> input1, Result<I2> input2) {
        return func.acceptTo(input1, input2);
    }
    
    /**
     * Applies a {@link FuncUnit2} function to values extracted from two {@link Func0} suppliers and returns
     *     a new {@link Func0} containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param func    the {@link FuncUnit2} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     */
    public static <I1, I2> void λ(FuncUnit2<I1, I2> func, Func0<I1> input1, Func0<I2> input2) {
        func.acceptTo(input1, input2);
    }
    
    /**
     * Partially applies a {@link FuncUnit2} function by fixing two input parameters with values obtained from
     *     two {@link Func1} functions, resulting in a new {@link Func1} function.
     *
     * @param <S>     the type of the shared input parameter for both input1 and input2 functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param func    the {@link FuncUnit2} function to be partially applied
     * @param input1  the first {@link Func1} function producing the first input value
     * @param input2  the second {@link Func1} function producing the second input value
     * @return        a new {@link Func1} function that applies the original {@link Func2} function with the two fixed input parameters
     */
    public static <S, I1, I2> FuncUnit1<S> λ(FuncUnit2<I1, I2> func, Func1<S, I1> input1, Func1<S, I2> input2) {
        return func.acceptTo(input1, input2);
    }
    
    /**
     * Applies a {@link FuncUnit2} function to values inside two {@link HasPromise} objects, if both values become available,
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link HasPromise}
     * @param <I2>    the type of the value inside the second {@link HasPromise}
     * @param <O>     the type of the output of the {@link HasPromise}
     * @param func    the {@link FuncUnit2} function to be applied to the input values, if both values are ready
     * @param input1  the {@link HasPromise} containing the first input value
     * @param input2  the {@link HasPromise} containing the second input value
     * @return        a {@link Promise} containing the result of applying the function, once both input values are ready
     */
    public static <I1, I2, O> Promise<O> λ(FuncUnit2<I1, I2> func, HasPromise<I1> input1, HasPromise<I2> input2) {
        return func.acceptTo(input1, input2);
    }
    
    /**
     * Applies a {@link FuncUnit2} function to values inside two {@link Task} objects, if both values become available,
     *     and returns a new {@link Task} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Task}
     * @param <I2>    the type of the value inside the second {@link Task}
     * @param <O>     the type of the output of the {@link Task}
     * @param func    the {@link FuncUnit2} function to be applied to the input values, if both values are ready
     * @param input1  the {@link Task} containing the first input value
     * @param input2  the {@link Task} containing the second input value
     * @return        a {@link Task} containing the result of applying the function, once both input values are ready
     */
    public static <I1, I2, O> Task<O> λ(FuncUnit2<I1, I2> func, Task<I1> input1, Task<I2> input2) {
        return func.acceptTo(input1, input2);
    }
    
    //-- Func3 --
    
    /**
     * Invokes a {@link FuncUnit3} function with three input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param func    the {@link FuncUnit3} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @param input3  the third input parameter for the function
     */
    public static <I1, I2, I3> void λ(FuncUnit3<I1, I2, I3> func, I1 input1, I2 input2, I3 input3) {
        func.accept(input1, input2, input3);
    }
    
    /**
     * Partially applies a {@link FuncUnit3} function by fixing the first input parameter, resulting in
     *     a new {@link Func2} function that takes the remaining two parameters.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param func    the {@link FuncUnit3} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link FuncUnit2} function that takes the remaining two parameters
     *                and applies the original {@link FuncUnit3} function with the fixed first input parameter
     */
    public static <I1, I2, I3> FuncUnit2<I2, I3> λ(FuncUnit3<I1, I2, I3> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit3} function to values inside three {@link Result} objects if all values exist,
     * and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <I3>    the type of the value inside the third {@link Result}
     * @param <O>     the type of the value of {@link Result}
     * @param func    the {@link FuncUnit3} function to be applied to the input values, if all values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if any input is an exception
     */
    public static <I1, I2, I3, O> Result<O> λ(FuncUnit3<I1, I2, I3> func, Result<I1> input1, Result<I2> input2, Result<I3> input3) {
        return func.acceptTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link FuncUnit3} function to values extracted from three {@link Func0} suppliers and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <I3>    the type of the value supplied by the third {@link Func0}
     * @param func    the {@link FuncUnit3} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @param input3  the third {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, I3, O> Func0<O> λ(FuncUnit3<I1, I2, I3> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3) {
        return func.acceptTo(input1, input2, input3);
    }
    
    /**
     * Partially applies a {@link FuncUnit3} function by fixing three input parameters with values obtained from
     *      three {@link Func1} functions, resulting in a new {@link Func1} function.
     *
     * @param <S>     the type of the shared input parameter for all input functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <I3>    the type of the value produced by the third input3 function
     * @param func    the {@link FuncUnit3} function to be partially applied
     * @param input1  the first input1 function producing the first input value
     * @param input2  the second input2 function producing the second input value
     * @param input3  the third input3 function producing the third input value
     * @return        a new {@link FuncUnit1} function that applies the original {@link FuncUnit3} function with the three fixed input parameters
     */
    public static <S, I1, I2, I3> FuncUnit1<S> λ(FuncUnit3<I1, I2, I3> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3) {
        return func.acceptTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link FuncUnit3} function to values inside three {@link HasPromise} objects, if all values become available,
     * and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link HasPromise}
     * @param <I2>    the type of the value inside the second {@link HasPromise}
     * @param <I3>    the type of the value inside the third {@link HasPromise}
     * @param <O>     the type of the output of the {@link HasPromise}
     * @param func    the {@link FuncUnit3} function to be applied to the input values, if all values are ready
     * @param input1  the {@link HasPromise} containing the first input value
     * @param input2  the {@link HasPromise} containing the second input value
     * @param input3  the {@link HasPromise} containing the third input value
     * @return        a {@link Promise} containing the result of applying the function, once all input values are ready
     */
    public static <I1, I2, I3, O> Promise<O> λ(FuncUnit3<I1, I2, I3> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3) {
        return func.acceptTo(input1, input2, input3);
    }
    
    /**
     * Applies a {@link FuncUnit3} function to values inside three {@link Task} objects, if all values become available,
     *      and returns a new {@link Task} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Task}
     * @param <I2>    the type of the value inside the second {@link Task}
     * @param <I3>    the type of the value inside the third {@link Task}
     * @param <O>     the type of the output of the {@link Task}
     * @param func    the {@link FuncUnit3} function to be applied to the input values, if all values are ready
     * @param input1  the {@link Task} containing the first input value
     * @param input2  the {@link Task} containing the second input value
     * @param input3  the {@link Task} containing the third input value
     * @return        a {@link Task} containing the result of applying the function, once all input values are ready
     */
    public static <I1, I2, I3, O> Task<O> λ(FuncUnit3<I1, I2, I3> func, Task<I1> input1, Task<I2> input2, Task<I3> input3) {
        return func.acceptTo(input1, input2, input3);
    }
    
    //-- FuncUnit4 --
    
    /**
     * Invokes a {@link FuncUnit4} function with four input parameters and returns the result.
     *
     * @param <I1>    the type of the first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <I4>    the type of the fourth input parameter
     * @param func    the {@link FuncUnit4} function to be invoked
     * @param input1  the first input parameter for the function
     * @param input2  the second input parameter for the function
     * @param input3  the third input parameter for the function
     * @param input4  the fourth input parameter for the function
     */
    public static <I1, I2, I3, I4> void λ(FuncUnit4<I1, I2, I3, I4> func, I1 input1, I2 input2, I3 input3, I4 input4) {
        func.accept(input1, input2, input3, input4);
    }
    
    /**
     * Partially applies a {@link FuncUnit4} function by fixing the first input parameter, resulting in
     *      a new {@link FuncUnit3} function that takes the remaining three parameters.
     *
     * @param <I1>    the type of the fixed first input parameter
     * @param <I2>    the type of the second input parameter
     * @param <I3>    the type of the third input parameter
     * @param <I4>    the type of the fourth input parameter
     * @param func    the {@link FuncUnit4} function to be partially applied
     * @param input1  the fixed first input parameter
     * @return        a new {@link FuncUnit3} function that takes the remaining three parameters
     *                  and applies the original {@link FuncUnit4} function with the fixed first input parameter
     */
    public static <I1, I2, I3, I4, O> FuncUnit3<I2, I3, I4> λ(FuncUnit4<I1, I2, I3, I4> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit4} function to values inside four {@link Result} objects if all values exist,
     *      and returns a new {@link Result} containing the result.
     *
     * @param <I1>    the type of the value inside the first {@link Result}
     * @param <I2>    the type of the value inside the second {@link Result}
     * @param <I3>    the type of the value inside the third {@link Result}
     * @param <I4>    the type of the value inside the fourth {@link Result}
     * @param <O>     the type of the value of {@link Result}
     * @param func    the {@link FuncUnit4} function to be applied to the input values, if all values exist
     * @param input1  the {@link Result} containing the first input value
     * @param input2  the {@link Result} containing the second input value
     * @param input3  the {@link Result} containing the third input value
     * @param input4  the {@link Result} containing the fourth input value
     * @return        a {@link Result} containing the result of applying the function, or an exception {@link Result} if any input is an exception
     */
    public static <I1, I2, I3, I4, O> Result<O> λ(FuncUnit4<I1, I2, I3, I4> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4) {
        return func.acceptTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link FuncUnit4} function to values extracted from four {@link Func0} suppliers and returns
     *      a new {@link Func0} containing the result.
     *
     * @param <I1>    the type of the value supplied by the first {@link Func0}
     * @param <I2>    the type of the value supplied by the second {@link Func0}
     * @param <I3>    the type of the value supplied by the third {@link Func0}
     * @param <I4>    the type of the value supplied by the fourth {@link Func0}
     * @param <O>     the type of the value of {@link Func0}
     * @param func    the {@link Func4} function to be applied to the values supplied by the {@link Func0} suppliers
     * @param input1  the first {@link Func0} supplier
     * @param input2  the second {@link Func0} supplier
     * @param input3  the third {@link Func0} supplier
     * @param input4  the fourth {@link Func0} supplier
     * @return        a {@link Func0} containing the result of applying the function to the values supplied by the {@link Func0} suppliers
     */
    public static <I1, I2, I3, I4, O> Func0<O> λ(FuncUnit4<I1, I2, I3, I4> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4) {
        return func.acceptTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link FuncUnit4} function to values obtained from four {@link FuncUnit1} functions and returns
     *      a new {@link FuncUnit1} function that takes a shared input parameter and applies the original {@link Func4}
     *      function with the values from the input functions.
     *
     * @param <S>     the type of the shared input parameter for all input functions
     * @param <I1>    the type of the value produced by the first input1 function
     * @param <I2>    the type of the value produced by the second input2 function
     * @param <I3>    the type of the value produced by the third input3 function
     * @param <I4>    the type of the value produced by the fourth input4 function
     * @param func    the {@link FuncUnit4} function to be applied to the input values obtained from the input functions
     * @param input1  the first input1 function producing the first input value
     * @param input2  the second input2 function producing the second input value
     * @param input3  the third input3 function producing the third input value
     * @param input4  the fourth input4 function producing the fourth input value
     * @return        a new {@link Func1} function that applies the original {@link Func4} function with the input values obtained from the input functions
     */
    public static <S, I1, I2, I3, I4> FuncUnit1<S> λ(FuncUnit4<I1, I2, I3, I4> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4) {
        return func.acceptTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link FuncUnit4} function to values obtained from four objects implementing {@link HasPromise},
     *  and returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link HasPromise}
     * @param <I2>    the type of the value obtained from the second {@link HasPromise}
     * @param <I3>    the type of the value obtained from the third {@link HasPromise}
     * @param <I4>    the type of the value obtained from the fourth {@link HasPromise}
     * @param <O>     the type of the value of {@link HasPromise}
     * @param func    the {@link FuncUnit4} function to be applied to the input values obtained from the {@link HasPromise} objects
     * @param input1  the first {@link HasPromise} object providing the first input value
     * @param input2  the second {@link HasPromise} object providing the second input value
     * @param input3  the third {@link HasPromise} object providing the third input value
     * @param input4  the fourth {@link HasPromise} object providing the fourth input value
     * @return        a {@link Promise} containing the result of applying the function to the input values obtained from the {@link HasPromise} objects
     */
    public static <I1, I2, I3, I4, O> Promise<O> λ(FuncUnit4<I1, I2, I3, I4> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4) {
        return func.acceptTo(input1, input2, input3, input4);
    }
    
    /**
     * Applies a {@link FuncUnit4} function to values obtained from four {@link Task} objects and returns a new {@link Task}
     *      containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Task}
     * @param <I2>    the type of the value obtained from the second {@link Task}
     * @param <I3>    the type of the value obtained from the third {@link Task}
     * @param <I4>    the type of the value obtained from the fourth {@link Task}
     * @param <O>     the type of the value of {@link Task}
     * @param func    the {@link FuncUnit4} function to be applied to the input values obtained from the {@link Task} objects
     * @param input1  the first {@link Task} providing the first input value
     * @param input2  the second {@link Task} providing the second input value
     * @param input3  the third {@link Task} providing the third input value
     * @param input4  the fourth {@link Task} providing the fourth input value
     * @return        a {@link Task} containing the result of applying the function to the input values obtained from the {@link Task} objects
     */
    public static <I1, I2, I3, I4, O> Task<O> λ(FuncUnit4<I1, I2, I3, I4> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4) {
        return func.acceptTo(input1, input2, input3, input4);
    }
    
    //-- Func5 --
    
    /**
     * Applies a {@link FuncUnit5} function to values provided as input parameters and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param func    the {@link FuncUnit5} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     */
    public static <I1, I2, I3, I4, I5> void λ(FuncUnit5<I1, I2, I3, I4, I5> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5) {
        func.accept(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link FuncUnit5} function to values obtained from the function's input1 parameter and returns a new {@link FuncUnit4}
     * function that takes four input parameters of types I2, I3, I4, and I5 and applies the original {@link FuncUnit5} function with them.
     *
     * @param <I1>    the type of the value obtained from the input1 parameter of the {@link FuncUnit5} function
     * @param <I2>    the type of the first input value for the resulting {@link FuncUnit4} function
     * @param <I3>    the type of the second input value for the resulting {@link FuncUnit4} function
     * @param <I4>    the type of the third input value for the resulting {@link FuncUnit4} function
     * @param <I5>    the type of the fourth input value for the resulting {@link FuncUnit4} function
     * @param func    the {@link FuncUnit5} function to be applied to the input1 value
     * @param input1  the input value for the {@link FuncUnit5} function
     * @return        a new {@link Func4} function that applies the original Func5 function with the specified input value
     */
    public static <I1, I2, I3, I4, I5> FuncUnit4<I2, I3, I4, I5> λ(FuncUnit5<I1, I2, I3, I4, I5> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit5} function to values obtained from five {@link Func0} objects and returns a new {@link Func0}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Func0}
     * @param <I2>    the type of the value obtained from the second {@link Func0}
     * @param <I3>    the type of the value obtained from the third {@link Func0}
     * @param <I4>    the type of the value obtained from the fourth {@link Func0}
     * @param <I5>    the type of the value obtained from the fifth {@link Func0}
     * @param func    the {@link FuncUnit5} function to be applied to the input values obtained from the {@link Func0} objects
     * @param input1  the first {@link Func0} providing the first input value
     * @param input2  the second {@link Func0} providing the second input value
     * @param input3  the third {@link Func0} providing the third input value
     * @param input4  the fourth {@link Func0} providing the fourth input value
     * @param input5  the fifth {@link Func0} providing the fifth input value
     * @return        a {@link Func0} containing the result of applying the function to the input values obtained from the {@link Func0} objects
     */
    public static <I1, I2, I3, I4, I5> FuncUnit0 λ(FuncUnit5<I1, I2, I3, I4, I5> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5) {
        return func.acceptTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link FuncUnit5} function to values obtained from five {@link Func1} objects and returns a new {@link Func1}
     * that applies the original {@link Func5} function with input values obtained from the provided {@link Func1} objects.
     *
     * @param <S>     the type of the value produced by the resulting {@link Func1}
     * @param <I1>    the type of the value obtained from the first {@link Func1}
     * @param <I2>    the type of the value obtained from the second {@link Func1}
     * @param <I3>    the type of the value obtained from the third {@link Func1}
     * @param <I4>    the type of the value obtained from the fourth {@link Func1}
     * @param <I5>    the type of the value obtained from the fifth {@link Func1}
     * @param func    the {@link Func5} function to be applied to the input values obtained from the {@link Func1} objects
     * @param input1  the first {@link Func1} providing the first input value
     * @param input2  the second {@link Func1} providing the second input value
     * @param input3  the third {@link Func1} providing the third input value
     * @param input4  the fourth {@link Func1} providing the fourth input value
     * @param input5  the fifth {@link Func1} providing the fifth input value
     * @return        a new {@link Func1} that applies the original {@link Func5} function with the specified input values
     */
    public static <S, I1, I2, I3, I4, I5> FuncUnit1<S> λ(FuncUnit5<I1, I2, I3, I4, I5> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5) {
        return func.acceptTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link FuncUnit5} function to values obtained from five objects implementing {@link HasPromise} and
     *      returns a new {@link Promise} containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link HasPromise}
     * @param <I2>    the type of the value obtained from the second {@link HasPromise}
     * @param <I3>    the type of the value obtained from the third {@link HasPromise}
     * @param <I4>    the type of the value obtained from the fourth {@link HasPromise}
     * @param <I5>    the type of the value obtained from the fifth {@link HasPromise}
     * @param <O>     the type of the value of {@link HasPromise}
     * @param func    the {@link FuncUnit5} function to be applied to the input values obtained from the {@link HasPromise} objects
     * @param input1  the first {@link HasPromise} providing the first input value
     * @param input2  the second {@link HasPromise} providing the second input value
     * @param input3  the third {@link HasPromise} providing the third input value
     * @param input4  the fourth {@link HasPromise} providing the fourth input value
     * @param input5  the fifth {@link HasPromise} providing the fifth input value
     * @return        a {@link Promise} containing the result of applying the function to the input values obtained
     *                  from the {@link HasPromise} objects
     */
    public static <I1, I2, I3, I4, I5, O> Promise<O> λ(FuncUnit5<I1, I2, I3, I4, I5> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5) {
        return func.acceptTo(input1, input2, input3, input4, input5);
    }
    
    /**
     * Applies a {@link FuncUnit5} function to values obtained from five {@link Task} objects and returns a new {@link Task}
     * containing the result.
     *
     * @param <I1>    the type of the value obtained from the first {@link Task}
     * @param <I2>    the type of the value obtained from the second {@link Task}
     * @param <I3>    the type of the value obtained from the third {@link Task}
     * @param <I4>    the type of the value obtained from the fourth {@link Task}
     * @param <I5>    the type of the value obtained from the fifth {@link Task}
     * @param <O>     the type of the value of {@link Task}
     * @param func    the {@link FuncUnit5} function to be applied to the input values obtained from the {@link Task} objects
     * @param input1  the first Task providing the first input value
     * @param input2  the second Task providing the second input value
     * @param input3  the third Task providing the third input value
     * @param input4  the fourth Task providing the fourth input value
     * @param input5  the fifth Task providing the fifth input value
     * @return        a {@link Task} containing the result of applying the function to the input values obtained from the {@link Task} objects
     */
    public static <I1, I2, I3, I4, I5, O> Task<O> λ(FuncUnit5<I1, I2, I3, I4, I5> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5) {
        return func.acceptTo(input1, input2, input3, input4, input5);
    }
    
    //-- Func6 --
    
    /**
     * Applies a {@link FuncUnit6} function to values obtained from six input objects and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param func    the {@link FuncUnit6} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     */
    public static <I1, I2, I3, I4, I5, I6> void λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6) {
        func.accept(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Creates a {@link FuncUnit5} function by applying the first input value to a {@link FuncUnit6} function and returning the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param func    the {@link FuncUnit6} function to be applied to the first input value
     * @param input1  the first input value
     * @return        a {@link FuncUnit5} function resulting from applying the first input value to the {@link FuncUnit6} function
     */
    public static <I1, I2, I3, I4, I5, I6> FuncUnit5<I2, I3, I4, I5, I6> λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit6} function to values obtained from six {@link Result} objects and returns the result as a {@link Result}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output
     * @param func    the {@link FuncUnit6} function to be applied to the input values
     * @param input1  the first input value as a Result
     * @param input2  the second input value as a Result
     * @param input3  the third input value as a Result
     * @param input4  the fourth input value as a Result
     * @param input5  the fifth input value as a Result
     * @param input6  the sixth input value as a Result
     * @return        the result of applying the function to the input values as a {@link Result}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Result<O> λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link FuncUnit6} function to values obtained from six {@link FuncUnit0} objects and returns the result as a {@link FuncUnit0}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param func    the {@link FuncUnit6} function to be applied to the input values
     * @param input1  the first input value as a {@link Func0}
     * @param input2  the second input value as a {@link Func0}
     * @param input3  the third input value as a {@link Func0}
     * @param input4  the fourth input value as a {@link Func0}
     * @param input5  the fifth input value as a {@link Func0}
     * @param input6  the sixth input value as a {@link Func0}
     * @return        the result of applying the function to the input values as a {@link Func0}
     */
    public static <I1, I2, I3, I4, I5, I6> FuncUnit0 λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link FuncUnit6} function to values obtained from six {@link FuncUnit1} objects and returns the result as a {@link FuncUnit1}.
     *
     * @param <S>     the type of the parameter for the {@link FuncUnit1} objects
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param func    the {@link FuncUnit6} function to be applied to the input values
     * @param input1  the first input value as a {@link Func1}
     * @param input2  the second input value as a {@link Func1}
     * @param input3  the third input value as a {@link Func1}
     * @param input4  the fourth input value as a {@link Func1}
     * @param input5  the fifth input value as a {@link Func1}
     * @param input6  the sixth input value as a {@link Func1}
     * @return        the result of applying the function to the input values as a {@link Func1}
     */
    public static <S, I1, I2, I3, I4, I5, I6> FuncUnit1<S> λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link FuncUnit6} function to values obtained from six {@link HasPromise} objects and returns the result as a {@link Promise}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output value
     * @param func    the {@link FuncUnit6} function to be applied to the input values
     * @param input1  the first input value as a {@link HasPromise}
     * @param input2  the second input value as a {@link HasPromise}
     * @param input3  the third input value as a {@link HasPromise}
     * @param input4  the fourth input value as a {@link HasPromise}
     * @param input5  the fifth input value as a {@link HasPromise}
     * @param input6  the sixth input value as a {@link HasPromise}
     * @return        the result of applying the function to the input values as a {@link Promise}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Promise<O> λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6);
    }
    
    /**
     * Applies a {@link FuncUnit6} function to values obtained from six {@link Task} objects and returns the result as a {@link Task}.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <O>     the type of the output value
     * @param func    the {@link FuncUnit6} function to be applied to the input values
     * @param input1  the first input value as a {@link Task}
     * @param input2  the second input value as a {@link Task}
     * @param input3  the third input value as a {@link Task}
     * @param input4  the fourth input value as a {@link Task}
     * @param input5  the fifth input value as a {@link Task}
     * @param input6  the sixth input value as a {@link Task}
     * @return        the result of applying the function to the input values as a {@link Task}
     */
    public static <I1, I2, I3, I4, I5, I6, O> Task<O> λ(FuncUnit6<I1, I2, I3, I4, I5, I6> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6);
    }
    
    //-- Func7 --
    
    /**
     * Applies a {@link FuncUnit7} function to values obtained from seven input parameters and returns the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param func    the {@link FuncUnit7} function to be applied to the input values
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @param input7  the seventh input value
     */
    public static <I1, I2, I3, I4, I5, I6, I7> void λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7) {
        func.accept(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Partially applies a {@link FuncUnit7} function to the first input value, creating a new {@link FuncUnit6} function.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param func    the {@link FuncUnit7} function to be partially applied
     * @param input1  the first input value
     * @return        a new {@link FuncUnit6} function that takes the remaining six input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7> FuncUnit6<I2, I3, I4, I5, I6, I7> λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, I1 input1) {
        return func.accept(input1);
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
     * @param <O>     the type of the output value
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
    public static <I1, I2, I3, I4, I5, I6, I7, O> Result<O> λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link FuncUnit7} function to the input values obtained from Func0 suppliers and wrapped in Nullable objects.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param func    the {@link FuncUnit7} function to be applied
     * @param input1  the {@link Func0} supplier for the first input value
     * @param input2  the {@link Func0} supplier for the second input value
     * @param input3  the {@link Func0} supplier for the third input value
     * @param input4  the {@link Func0} supplier for the fourth input value
     * @param input5  the {@link Func0} supplier for the fifth input value
     * @param input6  the {@link Func0} supplier for the sixth input value
     * @param input7  the {@link Func0} supplier for the seventh input value
     * @return        the result of applying the function to the input values as a {@link FuncUnit0}
     */
    public static <I1, I2, I3, I4, I5, I6, I7> FuncUnit0 λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link FuncUnit7} function to the input values obtained from {@link FuncUnit1}.
     *
     * @param <S>     the type of the source input
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param func    the {@link Func7} function to be applied
     * @param input1  the {@link Func1} supplier for the first input value
     * @param input2  the {@link Func1} supplier for the second input value
     * @param input3  the {@link Func1} supplier for the third input value
     * @param input4  the {@link Func1} supplier for the fourth input value
     * @param input5  the {@link Func1} supplier for the fifth input value
     * @param input6  the {@link Func1} supplier for the sixth input value
     * @param input7  the {@link Func1} supplier for the seventh input value
     * @return        a {@link FuncUnit1} that applies the {@link FuncUnit7} function to the provided input values
     */
    public static <S, I1, I2, I3, I4, I5, I6, I7> FuncUnit1<S> λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link FuncUnit7} function to the input values obtained from {@link HasPromise} objects and returns a {@link Promise} of the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <O>     the type of the output value
     * @param func    the {@link FuncUnit7} function to be applied
     * @param input1  the {@link HasPromise} for the first input value
     * @param input2  the {@link HasPromise} for the second input value
     * @param input3  the {@link HasPromise} for the third input value
     * @param input4  the {@link HasPromise} for the fourth input value
     * @param input5  the {@link HasPromise} for the fifth input value
     * @param input6  the {@link HasPromise} for the sixth input value
     * @param input7  the {@link HasPromise} for the seventh input value
     * @return        a {@link Promise} of the result of applying the {@link FuncUnit7} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> Promise<O> λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    /**
     * Applies a {@link FuncUnit7} function to the input values obtained from Task objects and returns a Task of the result.
     *
     * @param <I1>    the type of the first input value
     * @param <I2>    the type of the second input value
     * @param <I3>    the type of the third input value
     * @param <I4>    the type of the fourth input value
     * @param <I5>    the type of the fifth input value
     * @param <I6>    the type of the sixth input value
     * @param <I7>    the type of the seventh input value
     * @param <O>     the type of the output value
     * @param func    the {@link FuncUnit7} function to be applied
     * @param input1  the {@link Task} for the first input value
     * @param input2  the {@link Task} for the second input value
     * @param input3  the {@link Task} for the third input value
     * @param input4  the {@link Task} for the fourth input value
     * @param input5  the {@link Task} for the fifth input value
     * @param input6  the {@link Task} for the sixth input value
     * @param input7  the {@link Task} for the seventh input value
     * @return        a {@link Task} of the result of applying the {@link Func7} function to the provided input values
     */
    public static <I1, I2, I3, I4, I5, I6, I7, O> Task<O> λ(FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7);
    }
    
    //-- Func8 --
    
    /**
     * Applies a {@link FuncUnit8} function to the provided input values and returns the result.
     *
     * @param <I1>     the type of the first input value
     * @param <I2>     the type of the second input value
     * @param <I3>     the type of the third input value
     * @param <I4>     the type of the fourth input value
     * @param <I5>     the type of the fifth input value
     * @param <I6>     the type of the sixth input value
     * @param <I7>     the type of the seventh input value
     * @param <I8>     the type of the eighth input value
     * @param func     the {@link Func8} function to be applied
     * @param input1   the first input value
     * @param input2   the second input value
     * @param input3   the third input value
     * @param input4   the fourth input value
     * @param input5   the fifth input value
     * @param input6   the sixth input value
     * @param input7   the seventh input value
     * @param input8   the eighth input value
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8> void λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8) {
        func.accept(input1, input2, input3, input4, input5, input6, input7, input8);
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
     * @param func    the {@link Func8} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func7} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8> FuncUnit7<I2, I3, I4, I5, I6, I7, I8> λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit9} function to a list of input values wrapped in {@link Result}.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Result<O> λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8);
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> FuncUnit0 λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link FuncUnit8} function to a list of input values wrapped in {@link Func1} for a specific context.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8> FuncUnit1<S> λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link FuncUnit8} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
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
     * @param func    the {@link FuncUnit8} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Promise<O> λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    /**
     * Applies a {@link FuncUnit9} function to a list of input values wrapped in {@link Task} for asynchronous processing.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Task<O> λ(FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8);
    }
    
    //-- Func9 --
    
    /**
     * Applies a {@link FuncUnit9} function to the provided input values and returns the result.
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
     * @param func    the {@link FuncUnit9} function to be applied
     * @param input1  the first input value
     * @param input2  the second input value
     * @param input3  the third input value
     * @param input4  the fourth input value
     * @param input5  the fifth input value
     * @param input6  the sixth input value
     * @param input7  the seventh input value
     * @param input8  the eighth input value
     * @param input9  the ninth input value
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9> void λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9) {
        func.accept(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Partially applies a {@link FuncUnit9} function by fixing the first input value.
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
     * @param func    the {@link FuncUnit9} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link FuncUnit8} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9> FuncUnit8<I2, I3, I4, I5, I6, I7, I8, I9> λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit9} function to a list of input values wrapped in {@link Result}.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Result<O> λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link FuncUnit9} function to a list of input values wrapped in {@link Func0}.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9> FuncUnit0 λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link FuncUnit9} function to a list of input values wrapped in {@link FuncUnit1} for a specific context.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9> FuncUnit1<S> λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link FuncUnit10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
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
     * @param func    the {@link FuncUnit10} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Promise<O> λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    /**
     * Applies a {@link FuncUnit9} function to a list of input values wrapped in {@link Task} for asynchronous processing.
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
     * @param func    the {@link FuncUnit9} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Task<O> λ(FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9);
    }
    
    //-- Func10 --
    
    /**
     * Applies a {@link FuncUnit10} function to the provided input values and returns the result.
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
     * @param func     the {@link FuncUnit10} function to be applied
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
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> void λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, I1 input1, I2 input2, I3 input3, I4 input4, I5 input5, I6 input6, I7 input7, I8 input8, I9 input9, I10 input10) {
        func.accept(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Partially applies a {@link FuncUnit10} function by fixing the first input value.
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
     * @param func    the {@link Func10} function to be partially applied
     * @param input1  the fixed first input value
     * @return        a {@link Func9} function that takes the remaining nine input values and returns the output
     */
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> FuncUnit9<I2, I3, I4, I5, I6, I7, I8, I9, I10> λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, I1 input1) {
        return func.accept(input1);
    }
    
    /**
     * Applies a {@link FuncUnit10} function to a list of input values wrapped in {@link Result}.
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
     * @param func     the {@link FuncUnit10} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Result<O> λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, Result<I1> input1, Result<I2> input2, Result<I3> input3, Result<I4> input4, Result<I5> input5, Result<I6> input6, Result<I7> input7, Result<I8> input8, Result<I9> input9, Result<I10> input10) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link FuncUnit10} function to a list of input values wrapped in {@link FuncUnit0}.
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
     * @param func     the {@link FuncUnit10} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> FuncUnit0 λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, Func0<I1> input1, Func0<I2> input2, Func0<I3> input3, Func0<I4> input4, Func0<I5> input5, Func0<I6> input6, Func0<I7> input7, Func0<I8> input8, Func0<I9> input9, Func0<I10> input10) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link FuncUnit10} function to a list of input values wrapped in {@link Func1} for a specific context.
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
     * @param func     the {@link FuncUnit10} function to apply
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
    public static <S, I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> FuncUnit1<S> λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, Func1<S, I1> input1, Func1<S, I2> input2, Func1<S, I3> input3, Func1<S, I4> input4, Func1<S, I5> input5, Func1<S, I6> input6, Func1<S, I7> input7, Func1<S, I8> input8, Func1<S, I9> input9, Func1<S, I10> input10) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link FuncUnit10} function to a list of input values wrapped in {@link HasPromise} for asynchronous processing.
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
     * @param func     the {@link FuncUnit10} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Promise<O> λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, HasPromise<I1> input1, HasPromise<I2> input2, HasPromise<I3> input3, HasPromise<I4> input4, HasPromise<I5> input5, HasPromise<I6> input6, HasPromise<I7> input7, HasPromise<I8> input8, HasPromise<I9> input9, HasPromise<I10> input10) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }
    
    /**
     * Applies a {@link FuncUnit10} function to a list of input values wrapped in {@link Task} for asynchronous processing.
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
     * @param func     the {@link FuncUnit10} function to apply
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
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Task<O> λ(FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func, Task<I1> input1, Task<I2> input2, Task<I3> input3, Task<I4> input4, Task<I5> input5, Task<I6> input6, Task<I7> input7, Task<I8> input8, Task<I9> input9, Task<I10> input10) {
        return func.acceptTo(input1, input2, input3, input4, input5, input6, input7, input8, input9, input10);
    }

}
