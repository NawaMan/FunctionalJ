package functionalj.lens.core;

import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;

public interface LensType<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>, TYPELENS extends AnyLens<HOST, TYPE>> 
                    extends AccessCreator<HOST, TYPE, TYPEACCESS>{
    
    public Class<TYPE> getDataClass();
    
    public Class<TYPEACCESS> getAccessClass();
    
    public Class<TYPELENS> getLensClass();
    
    public TYPELENS newLens(LensSpec<HOST, TYPE> spec);
    
    @Override
    public TYPEACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
}
