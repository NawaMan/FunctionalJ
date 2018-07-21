package functionalj.types.result;

import java.util.function.Predicate;

import functionalj.functions.Func2;

public abstract class Checked<DATA, CHECKER extends Predicate<? super DATA>> extends ImmutableResult<DATA> {

    protected static <D, P extends Predicate<? super D>, C extends Checked<D, P>> C valueOf(D value, Func2<D, Exception, C> maker) {
        try {
            return maker.apply(value, (Exception)null);
        } catch (ValidationException e) {
            return maker.apply((D)null, e);
        } catch (Exception e) {
            return maker.apply((D)null, new ValidationException(e));
        }
    }
    
    protected Checked(DATA value, Exception exception) {
        super((exception != null) ? null : value, exception);
        
        if (exception == null) {
            if (value == null) {
                if (!(this instanceof NullSafe))
                    throw new ValidationException(new NullPointerException());
            }
            
            try {
                if (this.getChecker().test(value))
                    return;
            } catch (Exception e) {
                throw new ValidationException(e);
            }
            
            throw new ValidationException();
        }
    }
    
    protected abstract CHECKER getChecker();
    
    
    public static abstract class NullSafe<DATA, CHECKER extends Predicate<? super DATA>> extends Checked<DATA, CHECKER> {
        protected NullSafe(DATA value, Exception exception) {
            super(value, exception);
        }
    }
    
    
}
