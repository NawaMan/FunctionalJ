package functionalj.lens;

import java.util.List;
import java.util.function.Function;

import functionalj.types.FunctionalList;
import lombok.val;

public interface Accesses {
    // TODO - theString should have 'of' that take a string function.
    
    public static final AnyAccess<Object, Object> theObject   = (Object  item) -> item;
    public static final BooleanAccess<Boolean>    theBoolean = (Boolean item) -> item;
    public static final StringAccess<String>      theString  = (String  item) -> item;
    public static final IntegerAccess<Integer>    theInteger = (Integer item) -> item;

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
    
}
