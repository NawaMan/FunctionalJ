package functionalj.lens.core;

import java.util.List;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.FunctionalListAccess;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.NullableAccess;
import functionalj.types.FunctionalList;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class AccessUtils {


    // List 
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> ListAccess<HOST, TYPE, TYPEACCESS>
            createSubListAccess(
                    AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, List<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public List<TYPE> apply(HOST host) {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> FunctionalListAccess<HOST, TYPE, TYPEACCESS>
            createSubFunctionalListAccess(
                    AccessParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, FunctionalList<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, FunctionalList<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public FunctionalList<TYPE> apply(HOST host) {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyAccess<HOST, TYPE>> 
            NullableAccess<HOST, TYPE, TYPELENS> createNullableAccess(
                        Function<HOST, Nullable<TYPE>>           accessNullable,
                        Function<Function<HOST, TYPE>, TYPELENS> createSubLens) {
        val accessWithSub = new AccessParameterized<HOST, Nullable<TYPE>, TYPE, TYPELENS>() {
            @Override
            public Nullable<TYPE> apply(HOST host) {
                return accessNullable.apply(host);
            }
            @Override
            public TYPELENS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return createSubLens.apply(accessToParameter);
            }
        };
        return () -> accessWithSub;
    }
    
}
