package functionalj.lens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ListLens<HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        extends
            ObjectLens<HOST, LIST>,
            ListAccess<HOST, LIST, TYPE, SUBLENS> {
    
    public LensSpecWithSub<HOST, LIST, TYPE, SUBLENS> lensSpecWithSub();
    
    public default AccessParameterized<HOST, LIST, TYPE, SUBLENS> accessWithSub() {
        return lensSpecWithSub();
    }
    
    @Override
    public default SUBLENS createSubAccess(Function<LIST, TYPE> accessToSub) {
        return lensSpecWithSub().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, LIST> lensSpec() {
        return lensSpecWithSub().getSpec();
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
                    .collect(toList());
            return apply(host, (LIST)newList);
        };
    }
    
    
    public default SUBLENS createSubLens(Function<LIST, TYPE> readSub, BiFunction<LIST, TYPE, LIST> writeSub) {
        return (SUBLENS)LensSpec.createSubLens(this, readSub, writeSub, lensSpecWithSub()::createSubLens);
    }
    
    public static <HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, LIST, TYPE, SUBLENS> 
        createListLens(
            Function<HOST, LIST>                    read,
            BiFunction<HOST, LIST, HOST>            write,
            Function<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = LensSpecWithSub.createLensSpecWithSub(read, write, subCreator);
        val listLens = (ListLens<HOST, LIST, TYPE, SUBLENS>)()->spec;
        return listLens;
    }
    
}
