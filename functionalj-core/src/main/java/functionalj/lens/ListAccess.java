package functionalj.lens;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

import functionalj.functions.Func1;
import lombok.val;

@FunctionalInterface
public interface ListAccess<HOST, LIST extends List<SUB>, SUB, SUBACCESS extends AnyAccess<HOST, SUB>> 
        extends CollectionAccess<HOST, LIST, SUB, SUBACCESS> {
    
    // Change this to findXXX as it returns Nullable
    public default NullableAccess<HOST, SUB, SUBACCESS> first() {
        @SuppressWarnings("unchecked")
        val nullableSubAccess = (NullableAccess<HOST, SUB, SUBACCESS>)createNullableSubAccess(Func1.of((LIST list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(0);
        }));
        return nullableSubAccess;
    }
    public default NullableAccess<HOST, SUB, SUBACCESS> last() {
        @SuppressWarnings("unchecked")
        val nullableSubAccess = (NullableAccess<HOST, SUB, SUBACCESS>)createNullableSubAccess(Func1.of((LIST list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            return list.get(list.size() - 1);
        }));
        return nullableSubAccess;
    }
    public default NullableAccess<HOST, SUB, SUBACCESS> at(int index) {
        @SuppressWarnings("unchecked")
        val nullableSubAccess = (NullableAccess<HOST, SUB, SUBACCESS>)createNullableSubAccess(Func1.of((LIST list) -> {
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
        return nullableSubAccess;
    }
    public default ListAccess<HOST, LIST, SUB, SUBACCESS> filter(Predicate<SUB> checker) {
        val spec        = lensSpecWithSub();
        val specWithSub = new AccessWithSub<HOST, LIST, SUB, SUBACCESS>() {
            @SuppressWarnings("unchecked")
            @Override
            public LIST apply(HOST host) {
                return (LIST)spec.apply(host).stream().filter(checker).collect(toList());
            }
            @Override
            public SUBACCESS createSubAccess(Function<LIST, SUB> accessToSub) {
                return spec.createSubAccess(accessToSub);
            }
        };
        return () -> specWithSub;
    }
}
