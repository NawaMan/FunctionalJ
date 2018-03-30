package nawaman.functionalj.types;

import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.kinds.Functor;
import nawaman.functionalj.kinds.Monad;

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
     * @return  the MayBe instance with no value.
     */
    @SuppressWarnings("unchecked")
    public static <DATA> MayBe<DATA> empty() {
        return Nothing.instance;
    }
    
    /**
     * Get instance with a value.
     * 
     * @param <DATA>  the data type.
     * @param value   the data value.
     * @return  the MayBe instance with value.
     */
    @SuppressWarnings("unchecked")
    public static <DATA> MayBe<DATA> of(DATA value) {
        return (value != null) ? new Just<DATA>(value) : Nothing.instance;
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
    
    
    //-- Sub classes --
    
    /**
     * MayBe with value.
     * 
     * @param <TYPE>  the data type.
     */
    public static class Just<TYPE> extends MayBe<TYPE> {
        private TYPE data;
        
        Just(TYPE data) {
            this.data = data;
        }
        
        public boolean isPresent() {
            return true;
        }
        
        @Override
        public <B> MayBe<B> map(Func1<TYPE, B> mapper) {
            return MayBe.of(mapper.apply(data));
        }
        
        @Override
        public <B> Monad<MayBe<?>, B> flatMap(Func1<TYPE, Monad<MayBe<?>, B>> mapper) {
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
     * @param <TYPE>  the data type.
     */
    public static class Nothing<TYPE> extends MayBe<TYPE> {
        
        @SuppressWarnings("rawtypes")
        private static final Nothing instance = new Nothing<>();
        
        private Nothing() {
            super();
        }
        
        public boolean isPresent() {
            return false;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <B> MayBe<B> map(Func1<TYPE, B> mapper) {
            return (MayBe<B>)Nothing.instance;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <B> Monad<MayBe<?>, B> flatMap(Func1<TYPE, Monad<MayBe<?>, B>> mapper) {
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