package functionalj.lens;

import java.util.function.Function;

@FunctionalInterface
public interface AccessCreator<HOST, TYPE, TYPEACCESS extends AnyAccess<?, TYPE>> {
    
    public TYPEACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
}
