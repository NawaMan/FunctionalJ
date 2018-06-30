package functionalj.lens.core;

import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;

@FunctionalInterface
public interface AccessCreator<HOST, TYPE, TYPEACCESS extends AnyAccess<?, TYPE>> {
    
    public TYPEACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
}
