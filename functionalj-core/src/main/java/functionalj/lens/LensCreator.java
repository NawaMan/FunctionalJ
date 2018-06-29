package functionalj.lens;

import java.util.function.Function;

public interface LensCreator<HOST, TYPE, TYPEACCESS extends AnyAccess<?, TYPE>, TYPELENS extends Lens<?, TYPE>> 
                    extends AccessCreator<HOST, TYPE, TYPEACCESS>{
    
    public TYPELENS newLenes(LensSpec<HOST, TYPE> spec);

    public TYPEACCESS newAccess(Function<HOST, TYPE> accessToValue);
    
}
