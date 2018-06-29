package functionalj.lens;

import java.util.function.Function;

public interface LensCreator<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>, TYPELENS extends Lens<HOST, TYPE>> 
                    extends AccessCreator<HOST, TYPE, TYPEACCESS>{
    
    public TYPELENS newLenes(LensSpec<HOST, TYPE> spec);

    public TYPEACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
}
