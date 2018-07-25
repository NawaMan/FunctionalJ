package functionalj.compose;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public class Functional {

    public static <VALUE, TYPE> VALUE pluck(String name, TYPE object) {
        if (object == null)
            return null;
        
        try {
            Field field = object.getClass().getField(name);
            return (VALUE)field.get(object);
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
        try {
            Method method = object.getClass().getMethod(name);
            return (VALUE)method.invoke(object);
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        } catch (SecurityException e) {
        }
        try {
            String getGetter = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
            Method method = object.getClass().getMethod(getGetter);
            return (VALUE)method.invoke(object);
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        } catch (SecurityException e) {
        }
        return null;
    }
    
    public static <INPUT, VALUE> VALUE apply(Function<INPUT, VALUE> f, INPUT i) {
        return f.apply(i);
    }
    
    public static <I1, I2, R> Function<I1, R> compose(
            Function<I1, I2> f1,
            Function<I2, R> f2) {
        return f1.andThen(f2);
    }
    
    public static <I1, I2, I3, R> Function<I1, R> compose(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, R> f3) {
        return f1.andThen(f2).andThen(f3);
    }
    
    public static <I1, I2, I3, I4, R> Function<I1, R> compose(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, I4> f3,
            Function<I4, R> f4) {
        return f1.andThen(f2).andThen(f3).andThen(f4);
    }
    
    public static <I1, I2, I3, I4, I5, R> Function<I1, R> compose(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, I4> f3,
            Function<I4, I5> f4,
            Function<I5, R> f5) {
        return f1.andThen(f2).andThen(f3).andThen(f4).andThen(f5);
    }
    
    public static <I1, I2, I3, I4, I5, I6, R> Function<I1, R> compose(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, I4> f3,
            Function<I4, I5> f4,
            Function<I5, I6> f5,
            Function<I6, R> f6) {
        return f1.andThen(f2).andThen(f3).andThen(f4).andThen(f5).andThen(f6);
    }
    
    public static <I1, I2, R> Function<I1, R> pipe(
            Function<I1, I2> f1,
            Function<I2, R> f2) {
        return f1.andThen(f2);
    }
    
    public static <I1, I2, I3, R> Function<I1, R> pipe(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, R> f3) {
        return f1.andThen(f2).andThen(f3);
    }
    
    public static <I1, I2, I3, I4, R> Function<I1, R> pipe(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, I4> f3,
            Function<I4, R> f4) {
        return f1.andThen(f2).andThen(f3).andThen(f4);
    }
    
    public static <I1, I2, I3, I4, I5, R> Function<I1, R> pipe(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, I4> f3,
            Function<I4, I5> f4,
            Function<I5, R> f5) {
        return f1.andThen(f2).andThen(f3).andThen(f4).andThen(f5);
    }
    
    public static <I1, I2, I3, I4, I5, I6, R> Function<I1, R> pipe(
            Function<I1, I2> f1,
            Function<I2, I3> f2,
            Function<I3, I4> f3,
            Function<I4, I5> f4,
            Function<I5, I6> f5,
            Function<I6, R> f6) {
        return f1.andThen(f2).andThen(f3).andThen(f4).andThen(f5).andThen(f6);
    }

    public static <I, R> Supplier<R> curry(Function<I, R> f, I i) {
        return () -> f.apply(i);
    }
    public static <I1, I2, R> Function<I1, R> curry(BiFunction<I1, I2, R> f, I2 i2) {
        return i1 -> f.apply(i1, i2);
    }
    public static <I1, I2, R> Function<I1, R> curry1(BiFunction<I1, I2, R> f, I2 i2) {
        return i1 -> f.apply(i1, i2);
    }
    public static <I1, I2, R> Function<I2, R> curry2(BiFunction<I1, I2, R> f, I1 i1) {
        return i2 -> f.apply(i1, i2);
    }
    
    public static <TYPE, RESULT> Promise<RESULT> pmap(Promise<TYPE> promise, java.util.function.Function<TYPE, RESULT> mapper) {
        if (promise == null)
            return null;
        
        return promise.map(mapper);
    }
    
    public static <I,R> Function<I,R> f(Function<I, R> f) {
        return f;
    }
    
}
