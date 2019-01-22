package functionalj.function;

import java.util.function.Predicate;

// Ummm ... these are not a good name. :-(
public interface Check {
    
    public static <T> Func1<T, T> check(Predicate<T> checker, Func1<T, T> mapper) {
        return input -> checker.test(input) ? mapper.apply(input) : input;
    }
    public static <T> Func1<T, T> check(Predicate<T> checker, Func0<T> supplier) {
        return input -> checker.test(input) ? supplier.get() : input;
    }
    public static <T> Func1<T, T> check(Predicate<T> checker, T value) {
        return input -> checker.test(input) ? value : input;
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func1<T, D> mapper, Func1<T, D> elseMapper) {
        return input -> checker.test(input) ? mapper.apply(input) : elseMapper.apply(input);
    }
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func1<T, D> mapper, Func0<D> elseSupplier) {
        return input -> checker.test(input) ? mapper.apply(input) : elseSupplier.get();
    }
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func1<T, D> mapper, D elseValue) {
        return input -> checker.test(input) ? mapper.apply(input) : elseValue;
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func0<D> supplier, Func1<T, D> elseMapper) {
        return input -> checker.test(input) ? supplier.get() : elseMapper.apply(input);
    }
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func0<D> supplier, Func0<D> elseSupplier) {
        return input -> checker.test(input) ? supplier.get() : elseSupplier.get();
    }
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func0<D> supplier, D elseValue) {
        return input -> checker.test(input) ? supplier.get() : elseValue;
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, D value, Func1<T, D> elseMapper) {
        return input -> checker.test(input) ? value : elseMapper.apply(input);
    }
    public static <T, D> Func1<T, D> check(Predicate<T> checker, D value, Func0<D> elseSupplier) {
        return input -> checker.test(input) ? value : elseSupplier.get();
    }
    public static <T, D> Func1<T, D> check(Predicate<T> checker, D value, D elseValue) {
        return input -> checker.test(input) ? value : elseValue;
    }
    
    public static <T, D> Func1<T, D> only(Predicate<T> checker, Func1<T, D> mapper) {
        return input -> checker.test(input) ? mapper.apply(input) : null;
    }
    public static <T, D> Func1<T, D> only(Predicate<T> checker, Func0<D> supplier) {
        return input -> checker.test(input) ? supplier.get() : null;
    }
    public static <T, D> Func1<T, D> only(Predicate<T> checker, D value) {
        return input -> checker.test(input) ? value : null;
    }
    
}
