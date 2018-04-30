package functionalj.lens;

import functionalj.functions.Func1;

public interface AccessWithSub<HOST, TYPE, SUB, SUBACCESS extends AnyAccess<HOST, SUB>> extends AnyAccess<HOST, TYPE> {
    
    public SUBACCESS createSubAccess(Func1<TYPE, SUB> accessToSub);
    
}
