package nawaman.functionalj;

import java.util.function.Function;

import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.kinds.Functor;
import nawaman.functionalj.kinds.Monad;

public interface PointFree {
    
    
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
    
    
    //== Functor ==
    
    //-- map --
    
    public static
            <TYPE, DATA, TARGET>
            Functor<TYPE, TARGET> 
            map(Functor<TYPE, DATA> functor, Func1<DATA, TARGET> mapper) {
        return functor.map(mapper);
    }
    
    //-- pull - only for Functor of Function --
    
    public static 
            <INPUT, TARGET, FUNCTOR> 
            Function<INPUT, Functor<FUNCTOR, TARGET>> 
            pull(Functor<FUNCTOR, Func1<INPUT, TARGET>> org) {
        return input -> { 
            return org.map(functor -> { 
                return functor.apply(input);
            });
        };
    }
    public static 
            <INPUT, TARGET, FUNCTOR1, FUNCTOR2> 
            Function<INPUT, Functor<FUNCTOR1, Functor<FUNCTOR2, TARGET>>> 
            pull2(Functor<FUNCTOR1, ? extends Functor<FUNCTOR2, Func1<INPUT, TARGET>>> org) {
        return input -> { 
            return org.map(functor1 -> { 
                return functor1.map(functor2 -> {
                    return functor2.apply(input);
                });
            });
        };
    }
    public static 
            <INPUT, TARGET, FUNCTOR1, FUNCTOR2, FUNCTOR3>
            Function<INPUT, Functor<FUNCTOR1, Functor<FUNCTOR2, Functor<FUNCTOR3, TARGET>>>>
            pull3(Functor<FUNCTOR1, ? extends Functor<FUNCTOR2, ? extends Functor<FUNCTOR3, Func1<INPUT, TARGET>>>> org) {
        return input -> {
            return org.map(functor1 -> {
                return functor1.map(functor2 -> {
                    return functor2.map(functor3 -> {
                        return functor3.apply(input);
                    });
                });
            });
        };
    }
    public static 
            <INPUT, TARGET, FUNCTOR1, FUNCTOR2, FUNCTOR3, FUNCTOR4>
            Function<INPUT, Functor<FUNCTOR1, Functor<FUNCTOR2, Functor<FUNCTOR3, Functor<FUNCTOR4, TARGET>>>>>
            pull4(Functor<FUNCTOR1, ? extends Functor<FUNCTOR2, ? extends Functor<FUNCTOR3, ? extends Functor<FUNCTOR4, Func1<INPUT, TARGET>>>>> org) {
        return input -> {
            return org.map(functor1 -> {
                return functor1.map(functor2 -> {
                    return functor2.map(functor3 -> {
                        return functor3.map(functor4 -> {
                            return functor4.apply(input);
                        });
                    });
                });
            });
        };
    }
    
    //== Monad ==
    
    /**
     * Lift a regular function into a Monadic function.
     * 
     * @param  <INPUT>    the input data type.
     * @param  <OUTPUT>   the output data type.
     * @param  function   the function.
     * @return            the lifted function.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <INPUT, OUTPUT> Func1<Monad<?, INPUT>, Monad<?, OUTPUT>> lift(Func1<INPUT, OUTPUT> function) {
        return monadInput -> {
            return monadInput.flatMap(input -> {
                return (Monad)monadInput._unit(function.apply(input));
            });
        };
    }
    
    
}
