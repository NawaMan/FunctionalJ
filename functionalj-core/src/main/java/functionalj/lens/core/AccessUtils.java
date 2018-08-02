package functionalj.lens.core;

import java.util.List;
import java.util.function.Function;

import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.FuncListAccess;
import functionalj.lens.lenses.ListAccess;
import functionalj.lens.lenses.NullableAccess;
import functionalj.types.list.FuncList;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public class AccessUtils {


    // List 
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> ListAccess<HOST, TYPE, TYPEACCESS>
            createSubListAccess(
                    AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, List<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, List<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public List<TYPE> applyUnsafe(HOST host) throws Exception {
                return read.apply(host);
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<HOST, TYPE> accessToParameter) {
                return accessParameterized.createSubAccessFromHost(accessToParameter);
            }
        };
        return () -> specWithSub;
    }
    
    public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> FuncListAccess<HOST, TYPE, TYPEACCESS>
            createSubFuncListAccess(
                    AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPEACCESS> accessParameterized,
                    Function<HOST, FuncList<TYPE>>                              read) {
        val specWithSub = new AccessParameterized<HOST, FuncList<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public FuncList<TYPE> applyUnsafe(HOST host) throws Exception {
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
            public Nullable<TYPE> applyUnsafe(HOST host) throws Exception {
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
