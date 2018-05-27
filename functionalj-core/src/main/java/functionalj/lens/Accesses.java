package functionalj.lens;

import java.util.List;
import java.util.function.Function;

import lombok.val;

public class Accesses {
    // TODO - theString should have 'of' that take a string function.
    
    public static final AnyAccess<Object, Object> theThing   = (Object  item) -> item;
    public static final BooleanAccess<Boolean>    theBoolean = (Boolean item) -> item;
    public static final StringAccess<String>      theString  = (String  item) -> item;
    public static final IntegerAccess<Integer>    theInteger = (Integer item) -> item;

    public static final <T> AnyAccess<T, T> theItem() {
        return (T item) -> item;
    }
    public static final <T> ObjectAccess<T, T> theObject() {
        return (T item) -> item;
    }
    public static final <T extends Comparable<T>> ComparableAccess<T, T> theComparable() {
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
            public TYPEACCESS createSubAccess(Function<List<TYPE>, TYPE> accessToSub) {
                return accessParameterized.createSubAccess(accessToSub);
            }
        };
        return () -> specWithSub;
    }
    
}
