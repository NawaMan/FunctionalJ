package functionalj.lens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ListLens<HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        extends
            ObjectLens<HOST, LIST>,
            ListAccess<HOST, LIST, TYPE, SUBLENS> {
    

    public static <HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> 
            ListLens<HOST, LIST, TYPE, SUBLENS> of(
                Function<HOST, LIST>                    read,
                BiFunction<HOST, LIST, HOST>            write,
                Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        return Lenses.createListLens(read, write, subCreator);
    }
    public static <HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> 
            ListLens<HOST, LIST, TYPE, SUBLENS> of(LensSpecParameterized<HOST, LIST, TYPE, SUBLENS> spec) {
        return new ListLens<HOST, LIST, TYPE, SUBLENS>() {
            @Override
            public LensSpecParameterized<HOST, LIST, TYPE, SUBLENS> lensSpecParameterized() {
                return spec;
            }
        };
    }
    
    public LensSpecParameterized<HOST, LIST, TYPE, SUBLENS> lensSpecParameterized();
    
    public default AccessParameterized<HOST, LIST, TYPE, SUBLENS> accessParameterized() {
        return lensSpecParameterized();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<LIST, TYPE> accessToSub) {
        return accessParameterized().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, LIST> lensSpec() {
        return lensSpecParameterized().getSpec();
    }
    
    @Override
    public default LIST apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
    
    public default SUBLENS first() {
        return at(0);
    }
    
    @SuppressWarnings("unchecked")
    public default SUBLENS last() {
        return createSubLens(
                (list) -> {
                    if (list == null)
                        return null;
                    if (list.isEmpty())
                        return null;
                    return list.get(list.size() - 1);
                },
                (list, newValue)->{
                    val newList = new ArrayList<>(list);
                    newList.set(list.size() - 1, newValue);
                    return (LIST)newList;
                });
    }
    
    @SuppressWarnings("unchecked")
    public default SUBLENS at(int index) {
        return createSubLens(
                (list) -> {
                    if (list == null)
                        return null;
                    if (list.isEmpty())
                        return null;
                    if (index < 0) 
                        return null;
                    if (index >= list.size())
                        return null;
                    return list.get(index);
                },
                (list, newValue)->{
                    val newList = new ArrayList<>(list);
                    newList.set(index, newValue);
                    return (LIST)newList;
                });
    }
    
    public default Function<HOST, HOST> selectiveMap(Predicate<TYPE> checker, Function<TYPE, TYPE> mapper) {
        return host -> {
            val newList = apply(host).stream()
                    .map(each -> checker.test(each) ? mapper.apply(each) : each)
                    .collect(Collectors.toList());
            val newHost = apply(host, (LIST)newList);
            return newHost;
        };
    }
    
    
    public default SUBLENS createSubLens(Function<LIST, TYPE> readSub, BiFunction<LIST, TYPE, LIST> writeSub) {
        return (SUBLENS)Lenses.createSubLens(this, readSub, writeSub, lensSpecParameterized()::createSubLens);
    }
    
}
