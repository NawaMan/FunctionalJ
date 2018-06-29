package functionalj.lens;

import java.util.List;
import java.util.function.Function;

import functionalj.types.FunctionalList;
import lombok.val;

public interface Accesses {
    // TODO - theString should have 'of' that take a string function.
    
    public static final AnyAccess<Object, Object> theObject  = (Object  item) -> item;
    public static final BooleanAccess<Boolean>    theBoolean = (Boolean item) -> item;
    public static final StringAccess<String>      theString  = (String  item) -> item;
    public static final IntegerAccess<Integer>    theInteger = (Integer item) -> item;

    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> AccessCreator<H, Boolean, BooleanAccess<H>> ofBoolean() {
        return (AccessCreator<H, Boolean, BooleanAccess<H>>)(AccessCreator)__internal__.accessCreatorBoolean;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> AccessCreator<H, String, StringAccess<H>> ofString() {
        return (AccessCreator<H, String, StringAccess<H>>)(AccessCreator)__internal__.accessCreatorString;
    }
    
    public static <T> AnyAccess<T, T> theItem() {
        return (T item) -> item;
    }
    public static <T> ObjectAccess<T, T> theObject() {
        return (T item) -> item;
    }
    public static <T extends Comparable<T>> ComparableAccess<T, T> theComparable() {
        return (T item) -> item;
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
