package functionalj.lens;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface ListAccess<HOST, LIST extends List<TYPE>, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
        extends CollectionAccess<HOST, LIST, TYPE, SUBACCESS> {
    
    // Change this to findXXX as it returns Nullable
    public default NullableAccess<HOST, Nullable<TYPE>, TYPE, SUBACCESS> first() {
        return createNullableSubAccess(Func1.of((LIST list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(0);
        }));
    }
    public default NullableAccess<HOST, Nullable<TYPE>, TYPE, SUBACCESS> last() {
        return createNullableSubAccess(Func1.of((LIST list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(list.size() - 1);
        }));
    }
    public default NullableAccess<HOST, Nullable<TYPE>, TYPE, SUBACCESS> at(int index) {
        return createNullableSubAccess(Func1.of((LIST list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            if (index < 0)
                return null;
            if (index >= list.size())
                return null;
            return list.get(index);
        }));
    }
    public default ListAccess<HOST, LIST, TYPE, SUBACCESS> filter(Predicate<TYPE> checker) {
        val spec        = lensSpecWithSub();
        val specWithSub = new AccessWithSub<HOST, LIST, TYPE, SUBACCESS>() {
            @SuppressWarnings("unchecked")
            @Override
            public LIST apply(HOST host) {
                return (LIST)spec.apply(host).stream().filter(checker).collect(toList());
            }
            @Override
            public SUBACCESS createSubAccess(Func1<LIST, TYPE> accessToSub) {
                return spec.createSubAccess(accessToSub);
            }
        };
        return () -> specWithSub;
    }
    
    public default NullableAccess<HOST, Nullable<TYPE>, TYPE, SUBACCESS> createNullableSubAccess(Func1<LIST, TYPE> getElement) {
        return ()->{
            return new AccessWithSub<HOST, Nullable<TYPE>, TYPE, SUBACCESS>() {
                @Override
                public Nullable<TYPE> apply(HOST host) {
                    val list    = ListAccess.this.apply(host);
                    val element = getElement.apply(list);
                    return Nullable.of(element);
                }
                
                @Override
                public SUBACCESS createSubAccess(Func1<Nullable<TYPE>, TYPE> accessToSub) {
                    return ListAccess.this.createSubAccess((Func1<LIST, TYPE>)getElement);
                }
                
            };
        };
    }
}
