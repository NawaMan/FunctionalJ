package nawaman.functionalj.lens;

import java.util.function.Function;

public interface ComparableAccess<HOST, TYPE extends Comparable<TYPE>> extends AnyEqualableAccess<HOST, TYPE> {
    
    public default IntegerAccess<HOST> compareTo(TYPE anotherValue) {
        return intAccess(Integer.MIN_VALUE, any -> any.compareTo(anotherValue));
    }
    public default BooleanAccess<HOST> greaterThan(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanAccess<HOST> lessThan(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanAccess<HOST> greaterThanOrEqualsTo(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanAccess<HOST> lessThanOrEqualsTo(TYPE anotherValue) {
        return booleanAccess(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
}
