package functionalj.lens.lenses;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import functionalj.functions.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import lombok.val;

@FunctionalInterface
public interface ListLens<HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>>
        extends
            ObjectLens<HOST, List<TYPE>>,
            ListAccess<HOST, TYPE, TYPELENS> {
    

    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
            ListLens<HOST, TYPE, TYPELENS> of(
                Function<HOST, List<TYPE>>               read,
                WriteLens<HOST, List<TYPE>>              write,
                Function<LensSpec<HOST, TYPE>, TYPELENS> subCreator) {
        return LensUtils.createListLens(read, write, subCreator);
    }
    public static <HOST,  TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
            ListLens<HOST, TYPE, TYPELENS> of(LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> spec) {
        return new ListLens<HOST, TYPE, TYPELENS>() {
            @Override
            public LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> lensSpecParameterized() {
                return spec;
            }
        };
    }
    
    public LensSpecParameterized<HOST, List<TYPE>, TYPE, TYPELENS> lensSpecParameterized();
    
    public default AccessParameterized<HOST, List<TYPE>, TYPE, TYPELENS> accessParameterized() {
        return lensSpecParameterized();
    }
    
    @Override
    public default TYPELENS createSubAccess(Function<List<TYPE>, TYPE> accessToSub) {
        return accessParameterized().createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, List<TYPE>> lensSpec() {
        return lensSpecParameterized().getSpec();
    }
    
    @Override
    public default List<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec().getRead().apply(host);
    }
    
    public default TYPELENS createSubLens(Function<List<TYPE>, TYPE> readSub, WriteLens<List<TYPE>, TYPE> writeSub) {
        return LensUtils.createSubLens(this, readSub, writeSub, lensSpecParameterized()::createSubLens);
    }
    
    public default TYPELENS first() {
        return at(0);
    }
    
    @SuppressWarnings("unchecked")
    public default TYPELENS last() {
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
                    return newList;
                });
    }
    
    public default TYPELENS at(int index) {
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
                    return newList;
                });
    }
    
    public default Func1<HOST, HOST> changeTo(Predicate<TYPE> checker, Function<TYPE, TYPE> mapper) {
        return host -> {
            val newList = apply(host).stream()
                    .map(each -> checker.test(each) ? mapper.apply(each) : each)
                    .collect(Collectors.toList());
            val newHost = apply(host, newList);
            return newHost;
        };
    }
    
}
