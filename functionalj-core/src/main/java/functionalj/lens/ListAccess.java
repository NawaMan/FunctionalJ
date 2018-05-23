package functionalj.lens;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.val;

@FunctionalInterface
public interface ListAccess<HOST, SUB, SUBACCESS extends AnyAccess<HOST, SUB>> 
        extends CollectionAccess<HOST, List<SUB>, SUB, SUBACCESS> {
    
    public default SUBACCESS first() {
        return at(0);
    }
    public default SUBACCESS last() {
        return accessParameterized().createSubAccess((List<SUB> list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(list.size() - 1);
        });
    }
    public default SUBACCESS at(int index) {
        return accessParameterized().createSubAccess((List<SUB> list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            if (index < 0) 
                return null;
            if (index >= list.size())
                return null;
            return list.get(index);
        });
    }
    
    public default ListAccess<HOST, SUB, SUBACCESS> filter(Predicate<SUB> checker) {
        val spec        = accessParameterized();
        val specWithSub = new AccessParameterized<HOST, List<SUB>, SUB, SUBACCESS>() {
            @Override
            public List<SUB> apply(HOST host) {
                return spec.apply(host).stream().filter(checker).collect(Collectors.toList());
            }
            @Override
            public SUBACCESS createSubAccess(Function<List<SUB>, SUB> accessToSub) {
                return spec.createSubAccess(accessToSub);
            }
        };
        return () -> specWithSub;
    }
}
