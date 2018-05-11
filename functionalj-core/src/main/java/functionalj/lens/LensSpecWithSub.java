package functionalj.lens;

import java.util.function.BiFunction;
import java.util.function.Function;

import functionalj.functions.Func1;
import functionalj.functions.Func2;
import lombok.val;

public interface LensSpecWithSub<HOST, TYPE, SUB, SUBLENS extends AnyAccess<HOST, SUB>>
            extends AccessParameterized<HOST, TYPE, SUB, SUBLENS> {
    
    public LensSpec<HOST, TYPE> getSpec();
    public SUBLENS              createSubLens(LensSpec<HOST, SUB> subSpec);
    
    @Override
    public default SUBLENS createSubAccess(Function<TYPE, SUB> accessToSub) {
        val read = getSpec().getRead().andThen(accessToSub);
        val spec = LensSpec.of(read, null);
        return createSubLens(spec);
    }
    @Override
    public default TYPE apply(HOST host) {
        return getSpec().getRead().apply(host);
    }
    
    
//    public static <HOST, LIST, TYPE, SUBLENS extends AnyLens<HOST, TYPE>> ListLens<HOST, LIST, TYPE, SUBLENS> 
//        createLensWithSub(
//            Func1<HOST, LIST> read,
//            Func2<HOST, LIST, HOST> write,
//            Func1<LensSpec<HOST, TYPE>, SUBLENS> subCreator) {
//        val spec = createLensSpecWithSub(read, write, subCreator);
//        val listLens = (ListLens<HOST, LIST, TYPE, SUBLENS>)()->spec;
//        return listLens;
//    }
    
    public static <HOST, TYPE, SUB, SUBLENS extends AnyLens<HOST, SUB>> 
        LensSpecWithSub<HOST, TYPE, SUB, SUBLENS> createLensSpecWithSub(
            Function<HOST, TYPE> read,
            BiFunction<HOST, TYPE, HOST> write,
            Function<LensSpec<HOST, SUB>, SUBLENS> subCreator) {
        val spec = new LensSpecWithSub<HOST, TYPE, SUB, SUBLENS>() {
            @Override
            public LensSpec<HOST, TYPE> getSpec() {
                return LensSpec.of(read, write);
            }
            @Override
            public SUBLENS createSubLens(LensSpec<HOST, SUB> subSpec) {
                return subCreator.apply(subSpec);
            }
        };
        return spec;
    }
}