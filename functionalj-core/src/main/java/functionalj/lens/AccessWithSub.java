package functionalj.lens;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public interface AccessWithSub<HOST, TYPE, SUB, SUBACCESS extends AnyAccess<HOST, SUB>> extends AnyAccess<HOST, TYPE> {
    
    public TYPE apply(HOST host);
    
    public SUBACCESS createSubAccess(Func1<TYPE, SUB> accessToSub);
    
    
    public default NullableAccess<HOST, SUB, SUBACCESS> createNullableSubAccess(Func1<TYPE, SUB> getElement) {
        val accessWithSub = new AccessWithSub<HOST, Nullable<SUB>, SUB, SUBACCESS>() {
            @Override
            public Nullable<SUB> apply(HOST host) {
                val list    = AccessWithSub.this.apply(host);
                val element = getElement.apply(list);
                return Nullable.of(element);
            }
            @Override
            public SUBACCESS createSubAccess(Func1<Nullable<SUB>, SUB> accessToSub) {
                return AccessWithSub.this.createSubAccess((Func1<TYPE, SUB>)getElement);
            }
        };
        return ()->accessWithSub;
    }
}
