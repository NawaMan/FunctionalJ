package functionalj.lens;

import java.util.Collection;
import java.util.List;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import lombok.val;
//
//@FunctionalInterface
//public interface CollectionLens<HOST, COLLECTION extends Collection<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> 
//        extends
//            ObjectLens<HOST, COLLECTION>,
//            CollectionAccess<HOST, COLLECTION, TYPE, SUBLENS> {
//    
//    public LensSpecWithSub<HOST, COLLECTION, TYPE, SUBLENS> lensSpecWithSub();
//    
//    public default AccessWithSub<HOST, COLLECTION, TYPE, SUBLENS> accessWithSub() {
//        return lensSpecWithSub();
//    }
//    
//    @Override
//    public default SUBLENS createSubAccess(Func1<COLLECTION, TYPE> accessToSub) {
//        return lensSpecWithSub().createSubAccess(accessToSub);
//    }
//    
//    @Override
//    public default LensSpec<HOST, COLLECTION> lensSpec() {
//        return lensSpecWithSub().getSpec();
//    }
//    
//    @Override
//    public default COLLECTION apply(HOST input) {
//        return lensSpecWithSub().getSpec().getRead().apply(input);
//    }
//    
//    public static <HOST, COLLECTION extends Collection<TYPE>, TYPE, SUBLENS extends AnyLens<HOST, TYPE>>
//        CollectionLens<HOST, COLLECTION, TYPE, SUBLENS> createCollectionLens(
//            Func1<HOST, COLLECTION> read,
//            Func2<HOST, COLLECTION, HOST> write,
//            Func1<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
//        val spec = LensSpecWithSub.createLensSpecWithSub(read, write, subCreator);
//        val lens = (CollectionLens<HOST, COLLECTION, TYPE, SUBLENS>)()->spec;
//        return lens;
//    }
//}
