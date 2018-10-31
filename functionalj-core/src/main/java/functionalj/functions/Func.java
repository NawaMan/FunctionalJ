package functionalj.functions;

import static java.util.Arrays.stream;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.tuple.ImmutableTuple2;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

@SuppressWarnings("javadoc")
public interface Func {
    

    /**
     * A shorter way to use Function.identity() -- alias for itself() and themAll().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Func1<TYPE, TYPE> it() {
        return it -> it;
    }
    /**
     * A shorter way to use Function.identity() -- alias for it() and themAll().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Func1<TYPE, TYPE> itself() {
        return it -> it;
    }
    
    /**
     * A shorter way to use Function.identity() -- alias for it() and itself().
     * 
     * @param <TYPE> the type of it.
     * @return the function that take it and return it.
     **/
    public static <TYPE> Func1<TYPE, TYPE> themAll() {
        return it -> it;
    }
    
    /**
     * A shorter way to do flatmap on list directly.
     * 
     * @param <IN>   the input data type.
     * @param <OUT>  the output data type.
     * @return the function that take list and change to stream.
     */
    public static <IN extends List<? extends OUT>, OUT> Func1<? super IN, Stream<? extends OUT>> allLists() {
        return it -> it.stream();
    }
    
    /**
     * A shorter way to do flatmap on list directly from the result of the given mapper function.
     * 
     * @param <IN>    the input data type.
     * @param <OUT>   the output data type.
     * @param mapper  the mapper function.
     * @return the function that will apply the given mapper functio to the input and change the result list to stream.
     */
    public static <IN, OUT> Func1<IN, Stream<OUT>> allList(Func1<IN, ? extends List<OUT>> mapper) {
        return it -> mapper.apply(it).stream();
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(IN delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter, in);
        };
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(Supplier<? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.get(), in);
        };
    }
    
    public static <IN> Func1<IN, Stream<IN>> delimitWith(Func1<IN, ? extends IN> delimiter) {
        val isFirst = new AtomicBoolean(true);
        return in -> {
            if (isFirst.getAndSet(false))
                return Stream.of(in);
            return Stream.of(delimiter.apply(in), in);
        };
    }
    
    public static <INPUT> Predicate<INPUT> only(Function<INPUT, Boolean> check) {
        return input -> check.apply(input);
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> withIndex(BiFunction<INPUT, Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.apply(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> usingIndex(Function<Integer, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> body.apply(index.getAndIncrement());
    }
    
    public static class WithIndex<DATA> extends ImmutableTuple2<DATA, Integer> {
        public WithIndex(DATA _1, Integer _2) {
            super(_1, _2);
        }
        public int  index() { return _2.intValue(); }
        public DATA value()  { return _1; }
        
        public String toString() {
            // TODO - Umm .. this feels useful but it will break Tuple2.toString() contract.
            return "[#" + _2 + ":" + _1 + "]";
        }
    }
    
    public static interface WithIndexFunction<INPUT, OUTPUT> extends Function<WithIndex<INPUT>, OUTPUT> {
        
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> withIndex(WithIndexFunction<INPUT, OUTPUT> body) {
        val index = new AtomicInteger();
        return input -> {
            val withIndex = new WithIndex<INPUT>(input, index.getAndIncrement());
            return body.apply(withIndex);
        };
    }
    
    public static <INPUT> Function<INPUT, WithIndex<INPUT>> withIndex() {
        val index = new AtomicInteger();
        return input -> new WithIndex<INPUT>(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Consumer<INPUT> withIndex(BiConsumer<INPUT, Integer> body) {
        val index = new AtomicInteger();
        return input -> body.accept(input, index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Consumer<INPUT> withIndex(Consumer<Integer> body) {
        val index = new AtomicInteger();
        return input -> body.accept(index.getAndIncrement());
    }
    
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> cacheFor(Function<INPUT, OUTPUT> inFunction) {
        val cache = new ConcurrentHashMap<INPUT, OUTPUT>();
        return in -> cache.computeIfAbsent(in, inFunction::apply);
    }
    public static <INPUT, OUTPUT> Function<INPUT, OUTPUT> cacheFor(long time, Function<INPUT, OUTPUT> inFunction) {
        val cache       = new ConcurrentHashMap<INPUT, OUTPUT>();
        val expiredTime = new ConcurrentHashMap<INPUT, Long>();
        return in -> {
            if (expiredTime.contains(in)
             && expiredTime.get(in) > System.currentTimeMillis()) {
                cache.remove(in);
            }
            return cache.computeIfAbsent(in, key->{
                expiredTime.put(key, System.currentTimeMillis() + time);
                return inFunction.apply(key);
            });
        };
    }
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> cacheFor(Func1<INPUT, OUTPUT> inFunction) {
        val cache = new ConcurrentHashMap<INPUT, OUTPUT>();
        return in -> cache.computeIfAbsent(in, inFunction::apply);
    }
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> cacheFor(long time, Func1<INPUT, OUTPUT> inFunction) {
        val cache       = new ConcurrentHashMap<INPUT, OUTPUT>();
        val expiredTime = new ConcurrentHashMap<INPUT, Long>();
        return in -> {
            if (expiredTime.contains(in)
             && expiredTime.get(in) > System.currentTimeMillis()) {
                cache.remove(in);
            }
            return cache.computeIfAbsent(in, key->{
                expiredTime.put(key, System.currentTimeMillis() + time);
                return inFunction.apply(key);
            });
        };
    }
    
    @SuppressWarnings("unchecked")
    public static <TYPE> Supplier<TYPE> lazy(Supplier<TYPE> supplier) {
        val reference = new AtomicReference<Object>();
        val startKey  = new Object();
        return ()->{
            if (reference.compareAndSet(null, startKey)) {
                try {
                    val value = supplier.get();
                    reference.set((Supplier<TYPE>)(()->value));
                } catch (RuntimeException e) {
                    reference.set(e);
                }
            }
            while (!(reference.get() instanceof Supplier)) {
                if (reference.get() instanceof RuntimeException)
                    throw (RuntimeException)reference.get();
            }
            return ((Supplier<TYPE>)reference.get()).get();
        };
    }
    
    public static <I, O1, I2> Predicate<I> only(
            java.util.function.Function<I,O1> head,
            java.util.function.BiPredicate<O1, I2> tail,
            I2 tailInput) {
        return i->tail.test(head.apply(i), tailInput);
    }
    
    @SafeVarargs
    public static <T> Stream<T> streamConcat(Stream<T> ...  streams) {
        return stream(streams)
                .filter(Objects::nonNull)
                .flatMap(themAll());
    }
    
    public static <I1, I2, R> Func1<I1, R> recusive(Absent absent, I2 i2, Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        Func2<I1, I2, R> grt = recusive(func3);
        return grt.bind(absent, i2);
    }
    public static <I1, I2, R> Func1<I2, R> recusive(I1 i1, Absent absent, Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        Func2<I1, I2, R> grt = recusive(func3);
        return grt.bind(i1, absent);
    }
    public static <I1, I2, R> Func2<I1, I2, R> recusive(
            Func3<Func2<I1, I2, R>, I1, I2, R> func3) {
        AtomicReference<Func2<I1, I2, R>> selfRef = new AtomicReference<>();
        Supplier<Func2<I1, I2, R>> self = selfRef::get;
        Func2<I1, I2, R> selfFunc = (i1, i2) -> func3.apply(self.get(), i1, i2);
        selfRef.set(selfFunc);
        return selfFunc;
    }
    public static <I, R> Func1<I, R> recusive(Func2<Func1<I, R>, I, R> func2) {
        AtomicReference<Func1<I, R>> selfRef = new AtomicReference<>();
        Supplier<Func1<I, R>> self = selfRef::get;
        Func1<I, R> selfFunc = (_i) -> func2.apply(self.get(), _i);
        selfRef.set(cacheFor(selfFunc));
        return selfFunc;
    }
    
    public static Consumer<String> throwThis(Function<String, ? extends RuntimeException> exceptionCreator) {
        return errMsg -> {
            if (errMsg == null)
                return;
            
            val exception = exceptionCreator.apply(errMsg);
            if (exception == null)
                return;
            
            throw exception;
        };
    }
    
    /**
     * Returns a function that simply return the value.
     * 
     * @param <OUTPUT>  the value type.
     * @param value     the value.
     * @return          the function that return the value.
     */
    public static <OUTPUT> Func0<OUTPUT> supply(OUTPUT value) {
        return () -> value;
    }
    
    public static FuncUnit0 from(Runnable runnable) {
        if (runnable instanceof FuncUnit0)
            return (FuncUnit0)runnable;
        return runnable::run;
    }
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> Func0<OUTPUT> from(Supplier<OUTPUT> supplier) {
        return supplier::get;
    }
    
    public static <INPUT> FuncUnit1<INPUT> from(Consumer<INPUT> consumer) {
        return (value) -> consumer.accept(value);
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> from(Function<INPUT, OUTPUT> function) {
        return function::apply;
    }
    
    public static <INPUT1, INPUT2, OUTPUT> Func1<INPUT1, OUTPUT> from(BiFunction<INPUT1, INPUT2, OUTPUT> function, INPUT2 input2) {
        return input1 -> function.apply(input1, input2);
    }
    
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT1, OUTPUT> from(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function, INPUT2 input2, INPUT3 input3) {
        return input1 -> function.apply(input1, input2, input3);
    }

    /**
     * Constructs a Func2 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT1>  the first input data type.
     * @param  <INPUT2>  the second input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func2.
     **/
    public static <INPUT1, INPUT2, OUTPUT> Func2<INPUT1, INPUT2, OUTPUT> from(BiFunction<INPUT1, INPUT2, OUTPUT> function) {
        return function::apply;
    }
    
    public static <INPUT> FuncUnit1<INPUT> of(FuncUnit1<INPUT> consumer) {
        return (value) -> consumer.accept(value);
    }
    
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> 
            Func0<OUTPUT> of(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, OUTPUT> 
            Func1<INPUT, OUTPUT> of(Func1<INPUT, OUTPUT> function) {
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
     **/
    public static <INPUT1, INPUT2,OUTPUT> 
            Func2<INPUT1, INPUT2, OUTPUT> of(Func2<INPUT1, INPUT2, OUTPUT> function) {
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
     **/
    public static <INPUT1,INPUT2,INPUT3,OUTPUT> 
            Func3<INPUT1, INPUT2, INPUT3, OUTPUT> of(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
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
     **/
    public static
            <INPUT1,INPUT2,INPUT3,INPUT4,OUTPUT> 
            Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> of(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
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
     **/
    public static 
            <INPUT1,INPUT2,INPUT3,INPUT4,INPUT5,OUTPUT> 
            Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> of(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func2 from function or lambda.
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
     **/
    public static 
            <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> 
            Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> of(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    public static FuncUnit0 F(FuncUnit0 runnable) {
        return runnable;
    }
    
    public static void carelessly(Runnable runnable) {
        if (runnable == null)
            return;
        
        try {
            runnable.run();
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    public static void carelessly(FuncUnit0 action) {
        if (action == null)
            return;
        
        try {
            action.run();
        } catch (Exception e) {
            // Do nothing
        }
    }
    
    public static void gracefully(Runnable runnable) {
        if (runnable == null)
            return;
        
        try {
            runnable.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public static void gracefully(FuncUnit0 action) {
        if (action == null)
            return;
        
        try {
            action.run();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new FunctionInvocationException(e);
        }
    }
    
    public static <INPUT> FuncUnit1<INPUT> f(FuncUnit1<INPUT> consumer) {
        return consumer;
    }
    
    public static <INPUT1, INPUT2> FuncUnit2<INPUT1, INPUT2> f(FuncUnit2<INPUT1, INPUT2> consumer) {
        return consumer;
    }
    
    /**
     * Constructs a Func0 from supplier or lambda.
     * 
     * @param  supplier  the supplier or lambda.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func0.
     **/
    public static <OUTPUT> 
            Func0<OUTPUT> f(Func0<OUTPUT> supplier) {
        return supplier;
    }
    
    /**
     * Constructs a Func1 from function or lambda.
     * 
     * @param  function  the function or lambda.
     * @param  <INPUT>   the input data type.
     * @param  <OUTPUT>  the output data type.
     * @return           the result Func1.
     **/
    public static <INPUT, OUTPUT> 
            Func1<INPUT, OUTPUT> f(Func1<INPUT, OUTPUT> function) {
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
     **/
    public static <INPUT1, INPUT2,OUTPUT> 
            Func2<INPUT1, INPUT2, OUTPUT> f(Func2<INPUT1, INPUT2, OUTPUT> function) {
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
     **/
    public static <INPUT1,INPUT2,INPUT3,OUTPUT> 
            Func3<INPUT1, INPUT2, INPUT3, OUTPUT> f(Func3<INPUT1, INPUT2, INPUT3, OUTPUT> function) {
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
     **/
    public static
            <INPUT1,INPUT2,INPUT3,INPUT4,OUTPUT> 
            Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> f(Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> function) {
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
     **/
    public static 
            <INPUT1,INPUT2,INPUT3,INPUT4,INPUT5,OUTPUT> 
            Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> f(Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> function) {
        return function;
    }
    
    /**
     * Constructs a Func2 from function or lambda.
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
     **/
    public static 
            <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> 
            Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> f(Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> function) {
        return function;
    }
    
    
    /**
     * Get the value from the supplier that might be null.
     * 
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param supplier  the suppler.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <O, F extends O> O getOrElse(Supplier<O> supplier, F fallback) {
        if (supplier == null)
            return fallback;
        
        return supplier.get();
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I>       the input type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I, O, F extends O> O applyOrElse(Function<? super I, O> function, I input, F fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input1    the input 1 value.
     * @param input2    the input 2 value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, O, F extends O> O applyOrElse(
            BiFunction<? super I1, ? super I2, O> function, 
            I1                                    input1, 
            I2                                    input2,
            F                                     fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input1, input2);
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, O, F extends O> O applyOrElse(
            Function<Tuple2<? super I1, ? super I2>, O> function, 
            Tuple2<? super I1, ? super I2>              input,
            F                                           fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <I3>      the input 3 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input1    the input 1 value.
     * @param input2    the input 2 value.
     * @param input3    the input 3 value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, O, F extends O> O applyOrElse(
            Func3<? super I1, ? super I2, ? super I3, O> function, 
            I1                                           input1,
            I2                                           input2, 
            I3                                           input3, 
            F                                            fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input1, input2, input3);
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <I3>      the input 3 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, O, F extends O> O applyOrElse(
            Function<Tuple3<? super I1, ? super I2, ? super I3>, O> function, 
            Tuple3<? super I1, ? super I2, ? super I3>              input, 
            F                                                       fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
    }
    
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <I3>      the input 3 type.
     * @param <I4>      the input 4 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input1    the input 1 value.
     * @param input2    the input 2 value.
     * @param input3    the input 3 value.
     * @param input4    the input 4 value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, I4, O, F extends O> O applyOrElse(
            Func4<? super I1, ? super I2, ? super I3, ? super I4, O> function, 
            I1                                                       input1,
            I2                                                       input2, 
            I3                                                       input3, 
            I4                                                       input4, 
            F                                                        fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input1, input2, input3, input4);
    }
    
    /**
     * Apply the input value to the function that might be null.
     * 
     * @param <I1>      the input 1 type.
     * @param <I2>      the input 2 type.
     * @param <I3>      the input 3 type.
     * @param <I4>      the input 4 type.
     * @param <O>       the output type.
     * @param <F>       the fallback data type
     * @param function  the function.
     * @param input     the input value.
     * @param fallback  the fallback value.
     * @return  the result or the fallback type.
     */
    public static <I1, I2, I3, I4, O, F extends O> O applyOrElse(
            Function<Tuple4<? super I1, ? super I2, ? super I3, ? super I4>, O> function, 
            Tuple4<? super I1, ? super I2, ? super I3, ? super I4>              input, 
            F                                                                   fallback) {
        if (function == null)
            return fallback;
        
        return function.apply(input);
    }
    
    //== All ==
    
    /**
     * Create a high-order function that will take another function that take the given input and return output.
     * NOTE: Not sure if this a traverse.
     * 
     * @param <INPUT>   the input data type.
     * @param <OUTPUT>  the output data type.
     * @param input     the input.
     * @return          the high-order function.
     */
    public static <INPUT,OUTPUT> Func1<Function<INPUT,OUTPUT>, OUTPUT> allApplyTo(INPUT input) {
        return func -> {
            return func.apply(input);
        };
    }
    
    /**
     * Create a high-order function that will take another function that take the given input and return output.
     * NOTE: Not sure if this a traverse.
     * 
     * @param <INPUT>   the input data type.
     * @param input     the input.
     * @return          the high-order function.
     */
    public static <INPUT> Predicate<Function<INPUT, Boolean>> allCheckWith(INPUT input) {
        return func -> {
            return func.apply(input);
        };
    }
    
    //== condition ==
    
    public static <INPUT> Func1<INPUT, INPUT> ifThen(
            Predicate<INPUT>    check, 
            Func1<INPUT, INPUT> then) {
        return input -> {
            if (check.test(input))
                 return then.apply(input);
            else return input;
        };
    }
    public static <INPUT> Func1<INPUT, INPUT> ifNotThen(
            Predicate<INPUT>    check, 
            Func1<INPUT, INPUT> then) {
        return input -> {
            if (!check.test(input))
                 return then.apply(input);
            else return input;
        };
    }
    public static <INPUT, OUTPUT> Func1<INPUT, OUTPUT> ifElse(
            Predicate<INPUT>     check, 
            Func1<INPUT, OUTPUT> then, 
            Func1<INPUT, OUTPUT> thenElse) {
        return input -> {
            if (check.test(input))
                 return then    .apply(input);
            else return thenElse.apply(input);
        };
    }
    
    //== toTuple ==
    
    public static <I, T1, T2> Func1<I, Tuple2<T1, T2>> toTuple(
            Function<I, T1> func1, 
            Function<I, T2> func2) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            return Tuple.of(_1, _2);
        };
    }
    
    public static <I, T1, T2> Func1<I, Tuple2<T1, T2>> toTuple(
            Function<I, Map.Entry<? extends T1, ? extends T2>> func) {
        return input -> {
            val entry = func.apply(input);
            return new ImmutableTuple2<>(entry);
        };
    }
    
    public static <I, T1, T2, T3> Func1<I, Tuple3<T1, T2, T3>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            return Tuple.of(_1, _2, _3);
        };
    }
    
    public static <I, T1, T2, T3, T4> Func1<I, Tuple4<T1, T2, T3, T4>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3,
            Function<I, T4 > func4) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            val _4 = func4.apply(input);
            return Tuple.of(_1, _2, _3, _4);
        };
    }
    
    public static <I, T1, T2, T3, T4, T5> Func1<I, Tuple5<T1, T2, T3, T4, T5>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3,
            Function<I, T4 > func4,
            Function<I, T5 > func5) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            val _4 = func4.apply(input);
            val _5 = func5.apply(input);
            return Tuple.of(_1, _2, _3, _4, _5);
        };
    }

    public static <I, T1, T2, T3, T4, T5, T6> Func1<I, Tuple6<T1, T2, T3, T4, T5, T6>> toTuple(
            Function<I, T1 > func1, 
            Function<I, T2>  func2, 
            Function<I, T3 > func3,
            Function<I, T4 > func4,
            Function<I, T5 > func5,
            Function<I, T6 > func6) {
        return input -> {
            val _1 = func1.apply(input);
            val _2 = func2.apply(input);
            val _3 = func3.apply(input);
            val _4 = func4.apply(input);
            val _5 = func5.apply(input);
            val _6 = func6.apply(input);
            return Tuple.of(_1, _2, _3, _4, _5, _6);
        };
    }
    
    //== Lift ==
    
    public static <INPUT1, INPUT2, OUTPUT> Func1<INPUT1, OUTPUT> lift(
            Func2<INPUT1, INPUT2, OUTPUT> func, 
            INPUT2 i2) {
        return (i1) -> func.apply(i1, i2);
    }
    
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> Func1<INPUT1, OUTPUT> lift(
            Func3<INPUT1, INPUT2, INPUT3, OUTPUT> func, 
            INPUT2 i2, 
            INPUT3 i3) {
        return (i1) -> func.apply(i1, i2, i3);
    }
    
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> Func1<INPUT1, OUTPUT> lift(
            Func4<INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> func, 
            INPUT2 i2, 
            INPUT3 i3, 
            INPUT4 i4) {
        return (i1) -> func.apply(i1, i2, i3, i4);
    }
    
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> Func1<INPUT1, OUTPUT> lift(
            Func5<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> func, 
            INPUT2 i2, 
            INPUT3 i3, 
            INPUT4 i4, 
            INPUT5 i5) {
        return (i1) -> func.apply(i1, i2, i3, i4, i5);
    }
    
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> Func1<INPUT1, OUTPUT> lift(
            Func6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> func, 
            INPUT2 i2, 
            INPUT3 i3, 
            INPUT4 i4, 
            INPUT5 i5, 
            INPUT6 i6) {
        return (i1) -> func.apply(i1, i2, i3, i4, i5, i6);
    }
    
    //== Compose ==
    
    public static <INPUT1, INPUT2, OUTPUT> 
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, OUTPUT> f2) {
        return f1.then(f2);
    }
    public static <INPUT1, INPUT2, INPUT3, OUTPUT> 
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, OUTPUT> f3) {
        return f1.then(f2)
                 .then(f3);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, OUTPUT> 
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, OUTPUT> f4) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, OUTPUT> 
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, INPUT5> f4,
                Func1<INPUT5, OUTPUT> f5) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4)
                 .then(f5);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, OUTPUT> 
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, INPUT5> f4,
                Func1<INPUT5, INPUT6> f5,
                Func1<INPUT6, OUTPUT> f6) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4)
                 .then(f5)
                 .then(f6);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, OUTPUT>
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, INPUT5> f4,
                Func1<INPUT5, INPUT6> f5,
                Func1<INPUT6, INPUT7> f6,
                Func1<INPUT7, OUTPUT> f7) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4)
                 .then(f5)
                 .then(f6)
                 .then(f7);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, OUTPUT>
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, INPUT5> f4,
                Func1<INPUT5, INPUT6> f5,
                Func1<INPUT6, INPUT7> f6,
                Func1<INPUT7, INPUT8> f7,
                Func1<INPUT8, OUTPUT> f8) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4)
                 .then(f5)
                 .then(f6)
                 .then(f7)
                 .then(f8);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, OUTPUT>
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, INPUT5> f4,
                Func1<INPUT5, INPUT6> f5,
                Func1<INPUT6, INPUT7> f6,
                Func1<INPUT7, INPUT8> f7,
                Func1<INPUT8, INPUT9> f8,
                Func1<INPUT9, OUTPUT> f9) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4)
                 .then(f5)
                 .then(f6)
                 .then(f7)
                 .then(f8)
                 .then(f9);
    }
    public static <INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6, INPUT7, INPUT8, INPUT9, INPUT10, OUTPUT>
            Func1<INPUT1, OUTPUT> 
            compose(
                Func1<INPUT1, INPUT2> f1,
                Func1<INPUT2, INPUT3> f2,
                Func1<INPUT3, INPUT4> f3,
                Func1<INPUT4, INPUT5> f4,
                Func1<INPUT5, INPUT6> f5,
                Func1<INPUT6, INPUT7> f6,
                Func1<INPUT7, INPUT8> f7,
                Func1<INPUT8, INPUT9> f8,
                Func1<INPUT9, INPUT10> f9,
                Func1<INPUT10, OUTPUT> f10) {
        return f1.then(f2)
                 .then(f3)
                 .then(f4)
                 .then(f5)
                 .then(f6)
                 .then(f7)
                 .then(f8)
                 .then(f9)
                 .then(f10);
    }
    
}