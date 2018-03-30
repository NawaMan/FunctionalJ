package nawaman.functionalj;

import java.util.function.Function;

import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.kinds.Functor;
import nawaman.functionalj.kinds.Monad;

public interface PointFree {
    
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
