package functionalj.lens;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

import functionalj.functions.Func1;
import lombok.val;

@FunctionalInterface
public interface ListAccess<HOST, LIST extends List<TYPE>, TYPE, SUBACCESS extends AnyAccess<HOST, TYPE>> 
        extends CollectionAccess<HOST, LIST, TYPE, SUBACCESS> {
    
    public default SUBACCESS first() {
        return createSubAccess(list -> {
                if (list == null)
                    return null;
                return list.get(0);
        });
    }
    public default SUBACCESS last() {
        return createSubAccess(list -> {
                if (list == null)
                    return null;
                return list.get(list.size() - 1);
        });
    }
    public default SUBACCESS at(int index) {
        return createSubAccess(list -> {
                if (list == null)
                    return null;
                return list.get(index);
        });
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
}
