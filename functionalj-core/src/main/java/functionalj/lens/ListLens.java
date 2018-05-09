package functionalj.lens;

import java.util.List;
import java.util.function.Function;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import lombok.val;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface ListLens<HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
        extends
            ObjectLens<HOST, LIST>,
            ListAccess<HOST, LIST, TYPE, SUBLENS> {
    
    public LensSpecWithSub<HOST, LIST, TYPE, SUBLENS> lensSpecWithSub();
    
    public default AccessWithSub<HOST, LIST, TYPE, SUBLENS> accessWithSub() {
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
    
//    
//    public default SUBLENS first() {
//        return at(0);
//    }
//    
//    @SuppressWarnings("unchecked")
//    public default SUBLENS last() {
//        return createSubLens(
//                (list) -> {
//                    return list.get(list.size() - 1);
//                },
//                (list, newValue)->{
//                    val newList = new ArrayList<>(list);
//                    newList.set(list.size() - 1, newValue);
//                    return (LIST)newList;
//                });
//    }
//    
//    @SuppressWarnings("unchecked")
//    public default SUBLENS at(int index) {
//        return createSubLens(
//                (list) -> {
//                    return list.get(index);
//                },
//                (list, newValue)->{
//                    val newList = new ArrayList<>(list);
//                    newList.set(index, newValue);
//                    return (LIST)newList;
//                });
//    }
    // Add filterMap
//    public default ListLens<HOST, LIST, TYPE, SUBLENS> filter(Predicate<TYPE> checker) {
//        val spec        = lensSpecWithSub();
//        val specWithSub = new AccessWithSub<HOST, LIST, TYPE, SUBACCESS>() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public LIST apply(HOST host) {
//                return (LIST)spec.apply(host).stream().filter(checker).collect(toList());
//            }
//            @Override
//            public SUBACCESS createSubAccess(Func1<LIST, TYPE> accessToSub) {
//                return spec.createSubAccess(accessToSub);
//            }
//        };
//        return () -> specWithSub;
//    }
    
    
    public default SUBLENS createSubLens(Func1<LIST, TYPE> readSub, Func2<LIST, TYPE, LIST> writeSub) {
        return (SUBLENS)LensSpec.createSubLens(this, readSub, writeSub, lensSpecWithSub()::createSubLens);
    }
    
    public static <HOST, LIST extends List<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, LIST, TYPE, SUBLENS> 
        createListLens(
            Func1<HOST, LIST> read,
            Func2<HOST, LIST, HOST> write,
            Func1<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
        val spec = LensSpecWithSub.createLensSpecWithSub(read, write, subCreator);
        val listLens = (ListLens<HOST, LIST, TYPE, SUBLENS>)()->spec;
        return listLens;
    }
    
}
