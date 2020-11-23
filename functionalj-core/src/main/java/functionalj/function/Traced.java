// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.supportive.CallerId;


public interface Traced {
    
    /**
     * Returns the location. 
     * 
     * @return the location.
     **/
    public String getLocation();
    
    
    public static String extractLocationString(StackTraceElement caller) {
        return caller.getClassName() + "#" + caller.getLineNumber();
    }
    
    /**
     * Add name to the given predicate. 
     * 
     * @param name 
     * @param check 
     * @return the named predicate.
     **/
    public static <T> Annotated.Predicate<T> predicate(String name, java.util.function.Predicate<T> check) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Predicate<T>(name, location, check);
    }
    public static <T> Annotated.Predicate<T> predicate(java.util.function.Predicate<T> check) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Predicate<T>(null, location, check);
    }
    
    /** Add name to the given predicate. */
    public static <T> Annotated.Predicate<T> Predicate(String name, java.util.function.Predicate<T> check) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Predicate<T>(name, location, check);
    }
    /** Add name to the given predicate. */
    public static <T> Annotated.Predicate<T> Predicate(java.util.function.Predicate<T> check) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Predicate<T>(null, location, check);
    }
    
    /** Add name to the given supplier. */
    public static <T> Annotated.Supplier<T> supplier(String name, java.util.function.Supplier<T> supplier) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Supplier<T>(name, location, supplier);
    }
    /** Add name to the given supplier. */
    public static <T> Annotated.Supplier<T> supplier(java.util.function.Supplier<T> supplier) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Supplier<T>(null, location, supplier);
    }
    
    /** Add name to the given supplier. */
    public static <T> Annotated.Supplier<T> Supplier(String name, java.util.function.Supplier<T> supplier) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Supplier<T>(name, location, supplier);
    }
    /** Add name to the given supplier. */
    public static <T> Annotated.Supplier<T> Supplier(java.util.function.Supplier<T> supplier) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Supplier<T>(null, location, supplier);
    }
    
    /** Add name to the given runnable. */
    public static Annotated.Runnable runnable(String name, java.lang.Runnable runnable) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Runnable(name, location, runnable);
    }
    /** Add name to the given runnable. */
    public static Annotated.Runnable runnable(java.lang.Runnable runnable) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Runnable(null, location, runnable);
    }
    
    /** Add name to the given runnable. */
    public static Annotated.Runnable Runnable(String name, java.lang.Runnable runnable) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Runnable(name, location, runnable);
    }
    /** Add name to the given runnable. */
    public static Annotated.Runnable Runnable(java.lang.Runnable runnable) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Runnable(null, location, runnable);
    }
    
    /** Add name to the given consumer. */
    public static <T> Annotated.Consumer<T> consumer(String name, java.util.function.Consumer<T> consumer) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Consumer<T>(name, location, consumer);
    }
    /** Add name to the given consumer. */
    public static <T> Annotated.Consumer<T> consumer(java.util.function.Consumer<T> consumer) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Consumer<T>(null, location, consumer);
    }
    
    /** Add name to the given consumer. */
    public static <T> Annotated.Consumer<T> Consumer(String name, java.util.function.Consumer<T> consumer) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Consumer<T>(name, location, consumer);
    }
    /** Add name to the given consumer. */
    public static <T> Annotated.Consumer<T> Consumer(java.util.function.Consumer<T> consumer) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Consumer<T>(null, location, consumer);
    }
    
    public static <O> Annotated.Func0<O> func0(String name, Func0<O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func0<O>(name, location, func);
    }
    public static <O> Annotated.Func0<O> func0(Func0<O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func0<O>(null, location, func);
    }
    public static <O> Annotated.Func0<O> Func0(String name, Func0<O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func0<O>(name, location, func);
    }
    public static <O> Annotated.Func0<O> Func0(Func0<O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func0<O>(null, location, func);
    }
    
    public static <I, O> Annotated.Func1<I, O> func1(String name, Func1<I, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func1<I, O>(name, location, func);
    }
    public static <I, O> Annotated.Func1<I, O> func1(Func1<I, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func1<I, O>(null, location, func);
    }
    public static <I, O> Annotated.Func1<I, O> Func1(String name, Func1<I, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func1<I, O>(name, location, func);
    }
    public static <I, O> Annotated.Func1<I, O> Func1(Func1<I, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func1<I, O>(null, location, func);
    }
    
    public static <I1, I2, O> Annotated.Func2<I1, I2, O> func2(String name, Func2<I1, I2, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func2<I1, I2, O>(name, location, func);
    }
    public static <I1, I2, O> Annotated.Func2<I1, I2, O> func2(Func2<I1, I2, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func2<I1, I2, O>(null, location, func);
    }
    public static <I1, I2, O> Annotated.Func2<I1, I2, O> Func2(String name, Func2<I1, I2, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func2<I1, I2, O>(name, location, func);
    }
    public static <I1, I2, O> Annotated.Func2<I1, I2, O> Func2(Func2<I1, I2, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func2<I1, I2, O>(null, location, func);
    }
    
    public static <I1, I2, I3, O> Annotated.Func3<I1, I2, I3, O> func3(String name, Func3<I1, I2, I3, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func3<I1, I2, I3, O>(name, location, func);
    }
    public static <I1, I2, I3, O> Annotated.Func3<I1, I2, I3, O> func3(Func3<I1, I2, I3, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func3<I1, I2, I3, O>(null, location, func);
    }
    public static <I1, I2, I3, O> Annotated.Func3<I1, I2, I3, O> Func3(String name, Func3<I1, I2, I3, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func3<I1, I2, I3, O>(name, location, func);
    }
    public static <I1, I2, I3, O> Annotated.Func3<I1, I2, I3, O> Func3(Func3<I1, I2, I3, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func3<I1, I2, I3, O>(null, location, func);
    }
    
    public static <I1, I2, I3, I4, O> Annotated.Func4<I1, I2, I3, I4, O> func4(String name, Func4<I1, I2, I3, I4, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func4<I1, I2, I3, I4, O>(name, location, func);
    }
    public static <I1, I2, I3, I4, O> Annotated.Func4<I1, I2, I3, I4, O> func4(Func4<I1, I2, I3, I4, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func4<I1, I2, I3, I4, O>(null, location, func);
    }
    public static <I1, I2, I3, I4, O> Annotated.Func4<I1, I2, I3, I4, O> Func4(String name, Func4<I1, I2, I3, I4, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func4<I1, I2, I3, I4, O>(name, location, func);
    }
    public static <I1, I2, I3, I4, O> Annotated.Func4<I1, I2, I3, I4, O> Func4(Func4<I1, I2, I3, I4, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func4<I1, I2, I3, I4, O>(null, location, func);
    }
    
    public static <I1, I2, I3, I4, I5, O> Annotated.Func5<I1, I2, I3, I4, I5, O> func5(String name, Func5<I1, I2, I3, I4, I5, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func5<I1, I2, I3, I4, I5, O>(name, location, func);
    }
    public static <I1, I2, I3, I4, I5, O> Annotated.Func5<I1, I2, I3, I4, I5, O> func5(Func5<I1, I2, I3, I4, I5, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func5<I1, I2, I3, I4, I5, O>(null, location, func);
    }
    public static <I1, I2, I3, I4, I5, O> Annotated.Func5<I1, I2, I3, I4, I5, O> Func5(String name, Func5<I1, I2, I3, I4, I5, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func5<I1, I2, I3, I4, I5, O>(name, location, func);
    }
    public static <I1, I2, I3, I4, I5, O> Annotated.Func5<I1, I2, I3, I4, I5, O> Func5(Func5<I1, I2, I3, I4, I5, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func5<I1, I2, I3, I4, I5, O>(null, location, func);
    }
    
    public static <I1, I2, I3, I4, I5, I6, O> Annotated.Func6<I1, I2, I3, I4, I5, I6, O> func5(String name, Func6<I1, I2, I3, I4, I5, I6, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func6<I1, I2, I3, I4, I5, I6, O>(name, location, func);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Annotated.Func6<I1, I2, I3, I4, I5, I6, O> func5(Func6<I1, I2, I3, I4, I5, I6, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func6<I1, I2, I3, I4, I5, I6, O>(null, location, func);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Annotated.Func6<I1, I2, I3, I4, I5, I6, O> Func6(String name, Func6<I1, I2, I3, I4, I5, I6, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func6<I1, I2, I3, I4, I5, I6, O>(name, location, func);
    }
    public static <I1, I2, I3, I4, I5, I6, O> Annotated.Func6<I1, I2, I3, I4, I5, I6, O> Func6(Func6<I1, I2, I3, I4, I5, I6, O> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.Func6<I1, I2, I3, I4, I5, I6, O>(null, location, func);
    }
    
    public static <O> Annotated.FuncUnit0 funcUnit0(String name, FuncUnit0 func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit0(name, location, func);
    }
    public static <O> Annotated.FuncUnit0 funcUnit0(FuncUnit0 func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit0(null, location, func);
    }
    public static <O> Annotated.FuncUnit0 FuncUnit0(String name, FuncUnit0 func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit0(name, location, func);
    }
    public static <O> Annotated.FuncUnit0 FuncUnit0(FuncUnit0 func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit0(null, location, func);
    }
    
    public static <I> Annotated.FuncUnit1<I> funcUnit1(String name, FuncUnit1<I> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit1<I>(name, location, func);
    }
    public static <I> Annotated.FuncUnit1<I> funcUnit1(FuncUnit1<I> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit1<I>(null, location, func);
    }
    public static <I> Annotated.FuncUnit1<I> FuncUnit1(String name, FuncUnit1<I> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit1<I>(name, location, func);
    }
    public static <I> Annotated.FuncUnit1<I> FuncUnit1(FuncUnit1<I> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit1<I>(null, location, func);
    }
    
    public static <I1, I2> Annotated.FuncUnit2<I1, I2> funcUnit2(String name, FuncUnit2<I1, I2> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit2<I1, I2>(name, location, func);
    }
    public static <I1, I2> Annotated.FuncUnit2<I1, I2> funcUnit2(FuncUnit2<I1, I2> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit2<I1, I2>(null, location, func);
    }
    public static <I1, I2> Annotated.FuncUnit2<I1, I2> FuncUnit2(String name, FuncUnit2<I1, I2> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit2<I1, I2>(name, location, func);
    }
    public static <I1, I2> Annotated.FuncUnit2<I1, I2> FuncUnit2(FuncUnit2<I1, I2> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit2<I1, I2>(null, location, func);
    }
    
    public static <I1, I2, I3> Annotated.FuncUnit3<I1, I2, I3> funcUnit3(String name, FuncUnit3<I1, I2, I3> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit3<I1, I2, I3>(name, location, func);
    }
    public static <I1, I2, I3> Annotated.FuncUnit3<I1, I2, I3> funcUnit3(FuncUnit3<I1, I2, I3> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit3<I1, I2, I3>(null, location, func);
    }
    public static <I1, I2, I3> Annotated.FuncUnit3<I1, I2, I3> FuncUnit3(String name, FuncUnit3<I1, I2, I3> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit3<I1, I2, I3>(name, location, func);
    }
    public static <I1, I2, I3> Annotated.FuncUnit3<I1, I2, I3> FuncUnit3(FuncUnit3<I1, I2, I3> func) {
        var location = CallerId.instance.trace(Traced::extractLocationString);
        return new Annotated.FuncUnit3<I1, I2, I3>(null, location, func);
    }
    
}
