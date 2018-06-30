package functionalj.lens;

import java.util.List;
import java.util.function.Function;

import functionalj.types.FunctionalList;
import lombok.val;

public interface Accesses {
    
    public static <H> BooleanAccess<H> $B(Function<H, Boolean> access) {
        return access::apply;
    }
    public static <H> IntegerAccess<H> $I(Function<H, Integer> access) {
        return access::apply;
    }
    public static <H> StringAccess<H> $S(Function<H, String> access) {
        return access::apply;
    }

    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> AccessCreator<H, Boolean, BooleanAccess<H>> ofBoolean() {
        return (AccessCreator<H, Boolean, BooleanAccess<H>>)(AccessCreator)__internal__.accessCreatorBoolean;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> AccessCreator<H, Integer, IntegerAccess<H>> ofInteger() {
        return (AccessCreator<H, Integer, IntegerAccess<H>>)(AccessCreator)__internal__.accessCreatorInteger;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> AccessCreator<H, String, StringAccess<H>> ofString() {
        return (AccessCreator<H, String, StringAccess<H>>)(AccessCreator)__internal__.accessCreatorString;
    }
    
    
    // List
    
    public static <TYPE, TYPEACCESS extends AnyAccess<List<TYPE>, TYPE>> 
            ListAccess<List<TYPE>, TYPE, TYPEACCESS> theList(AccessCreator<List<TYPE>, TYPE, TYPEACCESS> typeAccessCreator) {
        val accessParameterized = new AccessParameterized<List<TYPE>, List<TYPE>, TYPE, TYPEACCESS>() {
            @Override
            public List<TYPE> apply(List<TYPE> list) {
                return list;
            }
            @Override
            public TYPEACCESS createSubAccessFromHost(Function<List<TYPE>, TYPE> accessToParameter) {
                return typeAccessCreator.newAccess(accessToParameter);
            }
        };
        return () -> accessParameterized;
    }
    
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
    
    public static final class __internal__ {
        private static final AccessCreator<Object, Boolean, BooleanAccess<Object>> accessCreatorBoolean 
        = new AccessCreator<Object, Boolean, BooleanAccess<Object>>() {
            @Override
            public BooleanAccess<Object> newAccess(Function<Object, Boolean> accessToValue) {
                return accessToValue::apply;
            }
        };
        private static final AccessCreator<Object, Integer, IntegerAccess<Object>> accessCreatorInteger
        = new AccessCreator<Object, Integer, IntegerAccess<Object>>() {
            @Override
            public IntegerAccess<Object> newAccess(Function<Object, Integer> accessToValue) {
                return host -> {
                    return accessToValue.apply(host);
                };
            }
        };
        private static final AccessCreator<Object, String, StringAccess<Object>> accessCreatorString
        = new AccessCreator<Object, String, StringAccess<Object>>() {
            @Override
            public StringAccess<Object> newAccess(Function<Object, String> accessToValue) {
                return host -> {
                    return accessToValue.apply(host);
                };
            }
        };
    }
    
}
