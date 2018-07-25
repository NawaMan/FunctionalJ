package functionalj.lens.lenses;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import functionalj.lens.core.AccessUtils;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ListAccess<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> 
        extends CollectionAccess<HOST, List<TYPE>, TYPE, TYPEACCESS> {
    
    public default TYPEACCESS first() {
        return at(0);
    }
    public default TYPEACCESS last() {
        return accessParameterized().createSubAccess((List<TYPE> list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(list.size() - 1);
        });
    }
    public default TYPEACCESS at(int index) {
        return accessParameterized().createSubAccess((List<TYPE> list) -> {
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
    
    public default ListAccess<HOST, TYPE, TYPEACCESS> filter(Predicate<TYPE> checker) {
        return AccessUtils.createSubListAccess(
                this.accessParameterized(),
                host -> {
                    return apply(host).stream().filter(checker).collect(Collectors.toList());
                });
    }
    
}
