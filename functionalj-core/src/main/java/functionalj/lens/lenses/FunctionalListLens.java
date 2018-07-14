package functionalj.lens.lenses;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

import functionalj.functions.Func1;
import functionalj.lens.core.AccessParameterized;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensUtils;
import functionalj.lens.core.WriteLens;
import functionalj.types.FunctionalList;
import functionalj.types.ImmutableList;
import lombok.val;

public interface FunctionalListLens<HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>>
        extends
            ObjectLens<HOST, FunctionalList<TYPE>>,
            FunctionalListAccess<HOST, TYPE, TYPELENS> {
    

    public static <HOST, TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
            FunctionalListLens<HOST, TYPE, TYPELENS> of(
                Function<HOST,  FunctionalList<TYPE>>    read,
                WriteLens<HOST, FunctionalList<TYPE>>    write,
                Function<LensSpec<HOST, TYPE>, TYPELENS> subCreator) {
        return LensUtils.createFunctionalListLens(read, write, subCreator);
    }
    public static <HOST,  TYPE, TYPELENS extends AnyLens<HOST, TYPE>> 
            FunctionalListLens<HOST, TYPE, TYPELENS> of(LensSpecParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPELENS> spec) {
        return ()->spec;
    }
    
    // :-( Duplicate 
    
    public LensSpecParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPELENS> lensSpecParameterized();
    
    public default AccessParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPELENS> accessParameterized() {
        return lensSpecParameterized();
    }
    
    @Override
    public default TYPELENS createSubAccess(Function<FunctionalList<TYPE>, TYPE> accessToSub) {
        return accessParameterized()
                .createSubAccess(accessToSub);
    }
    
    @Override
    public default LensSpec<HOST, FunctionalList<TYPE>> lensSpec() {
        return lensSpecParameterized()
                .getSpec();
    }
    
    @Override
    public default FunctionalList<TYPE> applyUnsafe(HOST host) throws Exception {
        return lensSpec()
                .getRead()
                .apply(host);
    }
    
    public default TYPELENS createSubLens(Function<FunctionalList<TYPE>, TYPE> readSub, WriteLens<FunctionalList<TYPE>, TYPE> writeSub) {
        return LensUtils.createSubLens(this, readSub, writeSub, lensSpecParameterized()::createSubLens);
    }
    
    public default TYPELENS first() {
        return createSubLens(
                (list)           -> list.first(),
                (list, newValue) -> list.with(0, newValue));
    }
    
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
                    return ImmutableList.of(newList);
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
                (list, newValue) -> {
                    return list.with(index, newValue);
                });
    }
    
    public default Func1<HOST, HOST> changeTo(Predicate<TYPE> checker, Function<TYPE, TYPE> mapper) {
        return host -> {
            val newList = apply(host).map(each -> checker.test(each) ? mapper.apply(each) : each);
            val newHost = FunctionalListLens.this.apply(host, newList);
            return newHost;
        };
    }
    
}
