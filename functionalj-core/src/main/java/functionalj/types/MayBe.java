package functionalj.types;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.functions.Func1;
import functionalj.kinds.Functor;
import functionalj.kinds.Monad;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

/**
 * This data structure represents data that may or may have value.
 * 
 * @author NawaMan -- nawa@nawaman.net
 *
 * @param <DATA>  the data type.
 */
public abstract class MayBe<DATA> implements Functor<MayBe<?>, DATA>, Monad<MayBe<?>, DATA>//, Nullable<DATA> 
{
    
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
    
    /**
     * Create a MayBe from the given nullable.
     * 
     * @param <T>       the data type.
     * @param nullable  the nullable.
     * @return          the may be result.
     */
    public static <T> MayBe<T> from(Nullable<T> nullable) {
        return nullable.isNotNull()
                ? new Just<T>(nullable.get()) 
                : MayBe.nothing();
    }
    
    /**
     * Create a MayBe from the given optional.
     * 
     * @param <T>       the data type.
     * @param optional  the optional.
     * @return          the may be result.
     */
    public static <T> MayBe<T> from(Optional<T> optional) {
        return optional.isPresent()
                ? new Just<T>(optional.get())
                : MayBe.nothing();
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
    public abstract DATA orElse(DATA or);
    
    /**
     * Returns the value inside this maybe or the value from the supplier.
     * 
     * @param orSupplier  the fallback supplier.
     * @return            the value inside or the fallback back value.
     */
    public abstract DATA orElseGet(Supplier<? extends DATA> orSupplier);
    
    /**
     * Returns the value inside this maybe or throw NPE.
     * 
     * @return  the value inside.
     */
    public abstract DATA orElseThrow();
    
    /**
     * Returns the value inside this maybe or throw the supplied exception.
     * 
     * @param exceptionSupplier  the exception supplier.
     * @param <THROWABLE>        the exception type.
     * @return                   the value inside.
     * @throws THROWABLE         the exception thrown if the MayBe has no data.
     */
    public abstract <THROWABLE extends Throwable> DATA orElseThrow(Supplier<? extends THROWABLE> exceptionSupplier) throws THROWABLE;
    
    /**
     * Returns this MayBe object if the value is null or fail the condition test otherwise return empty MayBe.
     * 
     * @param theCondition  the condition to be filter in.
     * @return  this object or empty MayBe.
     */
    public abstract MayBe<DATA> filter(Predicate<? super DATA> theCondition);
    
    /**
     * Apply the mapper if the value is not null then return the result wrapped with MayBe otherwise return empty MayBe.
     * 
     * @param mapper    the mapper.
     * @return          the result MayBe or empty MayBe.
     * @param <TARGET>  the target of the mapping.
     */
    public abstract <TARGET> MayBe<TARGET> map(Function<DATA, TARGET> mapper);
    
    /**
     * Run a body of code with the value is not null. Then returns itself.
     * 
     * @param theConsumer  the consumer.
     * @return  the value.
     */
    public abstract MayBe<DATA> peek(Consumer<? super DATA> theConsumer) ;
    
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
        public DATA orElse(DATA or) {
            return data;
        }
        
        @Override
        public DATA orElseGet(Supplier<? extends DATA> orSupplier) {
            return data;
        }
        
        @Override
        public DATA orElseThrow() {
            return data;
        }
        
        @Override
        public <THROWABLE extends Throwable> DATA orElseThrow(Supplier<? extends THROWABLE> exceptionSupplier) throws THROWABLE {
            return data;
        }
        

        @Override
        public MayBe<DATA> filter(Predicate<? super DATA> theCondition) {
            val value = get();
            if (value == null)
                return this;
            
            val isPass = theCondition.test(value);
            if (!isPass)
                return MayBe.empty();
            
            return this;
        }

        @Override
        public <TARGET> MayBe<TARGET> map(Function<DATA, TARGET> mapper) {
            val value = get();
            if (value == null)
                return MayBe.nothing();
                
            val newValue = mapper.apply(value);
            return MayBe.of(newValue);
        }

        @Override
        public <TARGET> Monad<MayBe<?>, TARGET> flatMap(Function<DATA, Monad<MayBe<?>, TARGET>> mapper) {
            val value = get();
            if (value == null)
                return MayBe.nothing();
                
            val newNullableValue = mapper.apply(value);
            return newNullableValue;
        }

        @Override
        public MayBe<DATA> peek(Consumer<? super DATA> theConsumer) {
            val value = get();
            if (value != null)
                theConsumer.accept(value);
            
            return this;
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
        public DATA orElse(DATA or) {
            return or;
        }
        
        @Override
        public DATA orElseThrow() {
            throw new NullPointerException();
        }
        
        @Override
        public <THROWABLE extends Throwable> DATA orElseThrow(Supplier<? extends THROWABLE> exceptionSupplier) throws THROWABLE {
            throw exceptionSupplier.get();
        }
        
        @Override
        public DATA orElseGet(Supplier<? extends DATA> orSupplier) {
            return orSupplier.get();
        }
        
        @Override
        public MayBe<DATA> filter(Predicate<? super DATA> theCondition) {
            return MayBe.nothing();
        }

        @Override
        public <TARGET> Monad<MayBe<?>, TARGET> flatMap(Function<DATA, Monad<MayBe<?>, TARGET>> mapper) {
            return MayBe.nothing();
        }

        @Override
        public <TARGET> MayBe<TARGET> map(Function<DATA, TARGET> mapper) {
            return MayBe.nothing();
        }

        @Override
        public MayBe<DATA> peek(Consumer<? super DATA> theConsumer) {
            return null;
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