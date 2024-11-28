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
    
}
