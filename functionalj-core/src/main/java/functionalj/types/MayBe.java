package functionalj.types;

import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.functions.Func1;
import functionalj.kinds.Functor;
import functionalj.kinds.Monad;

/**
 * This data structure represents data that may or may have value.
 * 
 * @author NawaMan -- nawa@nawaman.net
 *
 * @param <DATA>  the data type.
 */
public abstract class MayBe<DATA> implements Functor<MayBe<?>, DATA>, Monad<MayBe<?>, DATA> {
    
    /**
     * Get instance with no value.
     * 
     * @param <DATA>  the data type.
     * @return        the MayBe instance with no value.
     */
    @SuppressWarnings("unchecked")
    public static <DATA> MayBe<DATA> empty() {
        return Nothing.instance;
    }
    
    /**
     * Get instance with no value.
     * 
     * @param <DATA>  the data type.
     * @return        the MayBe instance with no value.
     */
    @SuppressWarnings("unchecked")
    public static <DATA> MayBe<DATA> nothing() {
        return Nothing.instance;
    }
    
    /**
     * Get instance with a value.
     * 
     * @param <DATA>  the data type.
     * @param value   the data value.
     * @return        the MayBe instance with value.
     */
    @SuppressWarnings("unchecked")
    public static <DATA> MayBe<DATA> of(DATA value) {
        return (value != null) ? new Just<DATA>(value) : Nothing.instance;
    }
    
    /**
     * Create a function from the given function but return MayBe as a result.
     * 
     * @param <I>       the input type.
     * @param <O>       the output type.
     * @param function  the function.
     * @return          the result function.
     */
    public static <I, O> Func1<I, MayBe<O>> mayBe(Function<I, O> function) {
        return input -> {
            try {
                return MayBe.of(function.apply(input));
            } catch (NullPointerException e) {
                return MayBe.empty();
            }
        };
    }
    
    /**
     * Return the maybe object from the running the supplier.
     * If NPE is throw from that supplier, MayBe.nothing() is returned.
     * 
     * @param <T>       the data type.
     * @param supplier  the supplier.
     * @return          the may be result.
     */
    public static <T> MayBe<T> from(Supplier<T> supplier) {
        try {
            return MayBe.of(supplier.get());
        } catch (NullPointerException e) {
            return MayBe.nothing();
        }
    }
    
    
    private MayBe() {
    }
    
    /**
     * Check if this instance has a value.
     * 
     * @return {@code true} if this instance has a value.
     */
    public abstract boolean isPresent();
    
    
    public <TARGET> MayBe<TARGET> _of(TARGET target) {
        return MayBe.of(target);
    }
    
    /**
     * Returns the value inside this maybe.
     * 
     * @return  the value.
     */
    public abstract DATA get();
    
    /**
     * Returns the value inside this maybe or given value in case of nothing.
     * 
     * @param or  the or value.
     * @return    the value or the or value.
     */
    public abstract DATA or(DATA or);
    
    /**
     * Returns the value inside this maybe or the value from the supplier.
     * 
     * @param orSupplier  the fallback supplier.
     * @return            the value inside or the fallback back value.
     */
    public abstract DATA orGet(Supplier<DATA> orSupplier);
    
    /**
     * Returns the value inside this maybe or throw NPE.
     * 
     * @return  the value inside.
     */
    public abstract DATA orThrow();
    
    // TODO - To either
    
    //-- Sub classes --
    
    /**
     * MayBe with value.
     * 
     * @param <DATA>  the data type.
     */
    public static class Just<DATA> extends MayBe<DATA> {
        private DATA data;
        
        Just(DATA data) {
            this.data = data;
        }
        
        @Override
        public boolean isPresent() {
            return true;
        }
        
        @Override
        public DATA get() {
            return data;
        }
        
        @Override
        public DATA or(DATA or) {
            return data;
        }
        
        @Override
        public DATA orGet(Supplier<DATA> orSupplier) {
            return data;
        }
        
        @Override
        public DATA orThrow() {
            return data;
        }
        
        @Override
        public <B> MayBe<B> map(Func1<DATA, B> mapper) {
            return MayBe.of(mapper.apply(data));
        }
        
        @Override
        public <B> Monad<MayBe<?>, B> flatMap(Func1<DATA, Monad<MayBe<?>, B>> mapper) {
            return mapper.apply(data);
        }
        
        public String toString() {
            return "Just(" + this.data + ")";
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((data == null) ? 0 : data.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Just<?> other = (Just<?>) obj;
            if (data == null) {
                if (other.data != null)
                    return false;
            } else if (!data.equals(other.data))
                return false;
            return true;
        }
    }
    
    /**
     * MayBe with no value.
     * 
     * @param <DATA>  the data type.
     */
    public static class Nothing<DATA> extends MayBe<DATA> {
        
        @SuppressWarnings("rawtypes")
        private static final Nothing instance = new Nothing<>();
        
        private Nothing() {
            super();
        }
        
        @Override
        public boolean isPresent() {
            return false;
        }
        
        @Override
        public DATA get() {
            return null;
        }
        
        @Override
        public DATA or(DATA or) {
            return or;
        }
        
        @Override
        public DATA orThrow() {
            throw new NullPointerException();
        }
        
        @Override
        public DATA orGet(Supplier<DATA> orSupplier) {
            return orSupplier.get();
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <B> MayBe<B> map(Func1<DATA, B> mapper) {
            return (MayBe<B>)Nothing.instance;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <B> Monad<MayBe<?>, B> flatMap(Func1<DATA, Monad<MayBe<?>, B>> mapper) {
            return (MayBe<B>)Nothing.instance;
        }
        
        public String toString() {
            return "Nothing";
        }
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result;
            return result;
        }
        
        @Override
        public boolean equals(Object obj) {
            return (this == obj);
        }
    }
    
}