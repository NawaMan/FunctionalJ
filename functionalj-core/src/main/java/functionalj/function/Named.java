// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
 * This class offer a way to make it easy to debug lambda by adding name to them.
 *
 * @author NawaMan
 */
// https://stackoverflow.com/questions/42876840/namingtostring-lambda-expressions-for-debugging-purpose/42876841#42876841
// https://stackoverflow.com/questions/23704355/creating-string-representation-of-lambda-expression/23705160#23705160
public interface Named {
    
    /**
     * Returns the name.
     *
     * @return the name.
     */
    public String name();
    
    
    /**
     * Add name to the given predicate.
     *
     * @param name   the name of the predicate.
     * @param check  the predicate body.
     * @return the named predicate.
     */
    public static <T> Annotated.Predicate<T> predicate(String name, java.util.function.Predicate<T> check) {
        return new Annotated.Predicate<T>(name, check);
    }
    
    /**
     * Add name to the given predicate.
     * 
     * @param <T>  the data type to be check.
     * 
     * @param name  the name of the predicate.
     * @param check the predicate body.
     * @return the named predicate.
     */
    public static <T> Annotated.Predicate<T> Predicate(String name, java.util.function.Predicate<T> check) {
        return new Annotated.Predicate<T>(name, check);
    }
    
    /**
     * Add name to the given BiPredicate.
     * 
     * @param name   the name of the predicate.
     * @param check  the predicate body.
     * @return the named predicate.
     */
    public static <T, U> Annotated.BiPredicate<T, U> biPredicate(String name, java.util.function.BiPredicate<T, U> check) {
        return new Annotated.BiPredicate<T, U>(name, check);
    }
    
    /**
     * Add name to the given BiPredicate.
     * 
     * @param name  the name of the predicate.
     * @param check the predicate body.
     * @return the named predicate.
     */
    public static <T, U> Annotated.BiPredicate<T, U> BiPredicate(String name, java.util.function.BiPredicate<T, U> check) {
        return new Annotated.BiPredicate<T, U>(name, check);
    }
    
    /**
     * Add name to the given supplier.
     * 
     * @param name      the name of the supplier.
     * @param supplier  the supplier body.
     * @param <T>       the type of the data to be supplied.
     * @return          the named supplier.
     */
    public static <T> Annotated.Supplier<T> supplier(String name, java.util.function.Supplier<T> supplier) {
        return new Annotated.Supplier<T>(name, supplier);
    }
    
    /**
     * Add name to the given supplier.
     * 
     * @param name      the name of the supplier.
     * @param supplier  the supplier body.
     * @param <T>       the type of the data to be supplied.
     * @return          the named supplier.
     */
    public static <T> Annotated.Supplier<T> Supplier(String name, java.util.function.Supplier<T> supplier) {
        return new Annotated.Supplier<T>(name, supplier);
    }
    
    /**
     * Add name to the given runnable.
     * 
     * @param name      the name of the runnable.
     * @param runnable  the supper body.
     * @return          the named runnable.
     */
    public static Annotated.Runnable runnable(String name, java.lang.Runnable runnable) {
        return new Annotated.Runnable(name, runnable);
    }
    
    /**
     * Add name to the given runnable.
     * 
     * @param name      the name of the runnable.
     * @param runnable  the runnable body.
     * @return          the named runnable.
     */
    public static Annotated.Runnable Runnable(String name, java.lang.Runnable runnable) {
        return new Annotated.Runnable(name, runnable);
    }
    
    /**
     * Add name to the given consumer.
     * 
     * @param name      the name of the consumer.
     * @param consumer  the consumer body.
     * @return          the named consumer.
     */
    public static <T> Annotated.Consumer<T> consumer(String name, java.util.function.Consumer<T> consumer) {
        return new Annotated.Consumer<T>(name, consumer);
    }
    
    /**
     * Add name to the given consumer.
     * 
     * @param name      the name of the consumer.
     * @param consumer  the consumer body.
     * @return          the named consumer.
     */
    public static <T> Annotated.Consumer<T> Consumer(String name, java.util.function.Consumer<T> consumer) {
        return new Annotated.Consumer<T>(name, consumer);
    }
    
    public static <O> Annotated.Func0<O> func0(String name, Func0<O> func) {
        return new Annotated.Func0<O>(name, func);
    }
    
    public static <O> Annotated.Func0<O> Func0(String name, Func0<O> func) {
        return new Annotated.Func0<O>(name, func);
    }
    
    public static <I, O> Annotated.Func1<I, O> func1(String name, Func1<I, O> func) {
        return new Annotated.Func1<I, O>(name, func);
    }
    
    public static <I, O> Annotated.Func1<I, O> Func1(String name, Func1<I, O> func) {
        return new Annotated.Func1<I, O>(name, func);
    }
    
    public static <I1, I2, O> Annotated.Func2<I1, I2, O> func2(String name, Func2<I1, I2, O> func) {
        return new Annotated.Func2<I1, I2, O>(name, func);
    }
    
    public static <I1, I2, O> Annotated.Func2<I1, I2, O> Func2(String name, Func2<I1, I2, O> func) {
        return new Annotated.Func2<I1, I2, O>(name, func);
    }
    
    public static <I1, I2, I3, O> Annotated.Func3<I1, I2, I3, O> func3(String name, Func3<I1, I2, I3, O> func) {
        return new Annotated.Func3<I1, I2, I3, O>(name, func);
    }
    
    public static <I1, I2, I3, O> Annotated.Func3<I1, I2, I3, O> Func3(String name, Func3<I1, I2, I3, O> func) {
        return new Annotated.Func3<I1, I2, I3, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, O> Annotated.Func4<I1, I2, I3, I4, O> func4(String name, Func4<I1, I2, I3, I4, O> func) {
        return new Annotated.Func4<I1, I2, I3, I4, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, O> Annotated.Func4<I1, I2, I3, I4, O> Func4(String name, Func4<I1, I2, I3, I4, O> func) {
        return new Annotated.Func4<I1, I2, I3, I4, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, O> Annotated.Func5<I1, I2, I3, I4, I5, O> func5(String name, Func5<I1, I2, I3, I4, I5, O> func) {
        return new Annotated.Func5<I1, I2, I3, I4, I5, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, O> Annotated.Func5<I1, I2, I3, I4, I5, O> Func5(String name, Func5<I1, I2, I3, I4, I5, O> func) {
        return new Annotated.Func5<I1, I2, I3, I4, I5, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Annotated.Func6<I1, I2, I3, I4, I5, I6, O> func6(String name, Func6<I1, I2, I3, I4, I5, I6, O> func) {
        return new Annotated.Func6<I1, I2, I3, I4, I5, I6, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Annotated.Func6<I1, I2, I3, I4, I5, I6, O> Func6(String name, Func6<I1, I2, I3, I4, I5, I6, O> func) {
        return new Annotated.Func6<I1, I2, I3, I4, I5, I6, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, O> Annotated.Func7<I1, I2, I3, I4, I5, I6, I7, O> func7(String name, Func7<I1, I2, I3, I4, I5, I6, I7, O> func) {
        return new Annotated.Func7<I1, I2, I3, I4, I5, I6, I7, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, O> Annotated.Func7<I1, I2, I3, I4, I5, I6, I7, O> Func7(String name, Func7<I1, I2, I3, I4, I5, I6, I7, O> func) {
        return new Annotated.Func7<I1, I2, I3, I4, I5, I6, I7, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Annotated.Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func8(String name, Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func) {
        return new Annotated.Func8<I1, I2, I3, I4, I5, I6, I7, I8, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, O> Annotated.Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> Func8(String name, Func8<I1, I2, I3, I4, I5, I6, I7, I8, O> func) {
        return new Annotated.Func8<I1, I2, I3, I4, I5, I6, I7, I8, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Annotated.Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func9(String name, Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func) {
        return new Annotated.Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Annotated.Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> Func9(String name, Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O> func) {
        return new Annotated.Func9<I1, I2, I3, I4, I5, I6, I7, I8, I9, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Annotated.Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func10(String name, Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func) {
        return new Annotated.Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Annotated.Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> Func10(String name, Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O> func) {
        return new Annotated.Func10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10, O>(name, func);
    }
    
    public static <O> Annotated.FuncUnit0 funcUnit0(String name, FuncUnit0 func) {
        return new Annotated.FuncUnit0(name, func);
    }
    
    public static <O> Annotated.FuncUnit0 FuncUnit0(String name, FuncUnit0 func) {
        return new Annotated.FuncUnit0(name, func);
    }
    
    public static <I> Annotated.FuncUnit1<I> funcUnit1(String name, FuncUnit1<I> func) {
        return new Annotated.FuncUnit1<I>(name, func);
    }
    
    public static <I> Annotated.FuncUnit1<I> FuncUnit1(String name, FuncUnit1<I> func) {
        return new Annotated.FuncUnit1<I>(name, func);
    }
    
    public static <I1, I2> Annotated.FuncUnit2<I1, I2> funcUnit2(String name, FuncUnit2<I1, I2> func) {
        return new Annotated.FuncUnit2<I1, I2>(name, func);
    }
    
    public static <I1, I2> Annotated.FuncUnit2<I1, I2> FuncUnit2(String name, FuncUnit2<I1, I2> func) {
        return new Annotated.FuncUnit2<I1, I2>(name, func);
    }
    
    public static <I1, I2, I3> Annotated.FuncUnit3<I1, I2, I3> funcUnit3(String name, FuncUnit3<I1, I2, I3> func) {
        return new Annotated.FuncUnit3<I1, I2, I3>(name, func);
    }
    
    public static <I1, I2, I3> Annotated.FuncUnit3<I1, I2, I3> FuncUnit3(String name, FuncUnit3<I1, I2, I3> func) {
        return new Annotated.FuncUnit3<I1, I2, I3>(name, func);
    }
    
    public static <I1, I2, I3, I4> Annotated.FuncUnit4<I1, I2, I3, I4> funcUnit4(String name, FuncUnit4<I1, I2, I3, I4> func) {
        return new Annotated.FuncUnit4<I1, I2, I3, I4>(name, func);
    }
    
    public static <I1, I2, I3, I4> Annotated.FuncUnit4<I1, I2, I3, I4> FuncUnit4(String name, FuncUnit4<I1, I2, I3, I4> func) {
        return new Annotated.FuncUnit4<I1, I2, I3, I4>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5> Annotated.FuncUnit5<I1, I2, I3, I4, I5> funcUnit5(String name, FuncUnit5<I1, I2, I3, I4, I5> func) {
        return new Annotated.FuncUnit5<I1, I2, I3, I4, I5>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5> Annotated.FuncUnit5<I1, I2, I3, I4, I5> FuncUnit5(String name, FuncUnit5<I1, I2, I3, I4, I5> func) {
        return new Annotated.FuncUnit5<I1, I2, I3, I4, I5>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6> Annotated.FuncUnit6<I1, I2, I3, I4, I5, I6> funcUnit6(String name, FuncUnit6<I1, I2, I3, I4, I5, I6> func) {
        return new Annotated.FuncUnit6<I1, I2, I3, I4, I5, I6>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6> Annotated.FuncUnit6<I1, I2, I3, I4, I5, I6> FuncUnit6(String name, FuncUnit6<I1, I2, I3, I4, I5, I6> func) {
        return new Annotated.FuncUnit6<I1, I2, I3, I4, I5, I6>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7> Annotated.FuncUnit7<I1, I2, I3, I4, I5, I6, I7> funcUnit7(String name, FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func) {
        return new Annotated.FuncUnit7<I1, I2, I3, I4, I5, I6, I7>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7> Annotated.FuncUnit7<I1, I2, I3, I4, I5, I6, I7> FuncUnit7(String name, FuncUnit7<I1, I2, I3, I4, I5, I6, I7> func) {
        return new Annotated.FuncUnit7<I1, I2, I3, I4, I5, I6, I7>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8> Annotated.FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> funcUnit8(String name, FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func) {
        return new Annotated.FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8> Annotated.FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> FuncUnit8(String name, FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8> func) {
        return new Annotated.FuncUnit8<I1, I2, I3, I4, I5, I6, I7, I8>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9> Annotated.FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> funcUnit9(String name, FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func) {
        return new Annotated.FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9> Annotated.FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> FuncUnit9(String name, FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9> func) {
        return new Annotated.FuncUnit9<I1, I2, I3, I4, I5, I6, I7, I8, I9>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> Annotated.FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> funcUnit10(String name, FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func) {
        return new Annotated.FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10>(name, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> Annotated.FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> FuncUnit10(String name, FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10> func) {
        return new Annotated.FuncUnit10<I1, I2, I3, I4, I5, I6, I7, I8, I9, I10>(name, func);
    }
}
