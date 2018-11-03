package functionalj.function;

/**
 * This class offer a way to make it easy to debug lambda by adding name to them.
 * 
 * @author NawaMan
 **/
// https://stackoverflow.com/questions/42876840/namingtostring-lambda-expressions-for-debugging-purpose/42876841#42876841
// https://stackoverflow.com/questions/23704355/creating-string-representation-of-lambda-expression/23705160#23705160
@SuppressWarnings("javadoc")
public interface Named {
    
    /**
     * Returns the name. 
     * 
     * @return the name.
     **/
    public String getName();
    
    /**
     * Add name to the given predicate. 
     * 
     * @param name 
     * @param check 
     * @return the named predicate.
     **/
    public static <T> Annotated.Predicate<T> predicate(String name, java.util.function.Predicate<T> check) {
        return new Annotated.Predicate<T>(name, check);
    }
    
    /** Add name to the given predicate. */
    public static <T> Annotated.Predicate<T> Predicate(String name, java.util.function.Predicate<T> check) {
        return new Annotated.Predicate<T>(name, check);
    }
    
    /** Add name to the given supplier. */
    public static <T> Annotated.Supplier<T> supplier(String name, java.util.function.Supplier<T> supplier) {
        return new Annotated.Supplier<T>(name, supplier);
    }
    
    /** Add name to the given supplier. */
    public static <T> Annotated.Supplier<T> Supplier(String name, java.util.function.Supplier<T> supplier) {
        return new Annotated.Supplier<T>(name, supplier);
    }
    
    /** Add name to the given runnable. */
    public static Annotated.Runnable runnable(String name, java.lang.Runnable runnable) {
        return new Annotated.Runnable(name, runnable);
    }
    
    /** Add name to the given runnable. */
    public static Annotated.Runnable Runnable(String name, java.lang.Runnable runnable) {
        return new Annotated.Runnable(name, runnable);
    }
    
    /** Add name to the given consumer. */
    public static <T> Annotated.Consumer<T> consumer(String name, java.util.function.Consumer<T> consumer) {
        return new Annotated.Consumer<T>(name, consumer);
    }
    
    /** Add name to the given consumer. */
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
    
}
