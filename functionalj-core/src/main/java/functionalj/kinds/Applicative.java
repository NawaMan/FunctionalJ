package functionalj.kinds;

@SuppressWarnings("javadoc")
public interface Applicative<TYPE, DATA> extends Apply<TYPE, DATA> {

    /**
     * Create a Applicative of the same type for the given value.
     * 
     * @param  <TARGET>  the target data type.
     * @param  target    the target data value.
     * @return           the newly created monad.
     */
    public <TARGET> Applicative<TYPE, TARGET> _of(TARGET target);
    
}
