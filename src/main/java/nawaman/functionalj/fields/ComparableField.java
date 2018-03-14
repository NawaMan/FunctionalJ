package nawaman.functionalj.fields;

import java.util.function.Function;

public interface ComparableField<HOST, TYPE extends Comparable<TYPE>> extends AnyEqualableField<HOST, TYPE> {
    
    public default IntegerField<HOST> compareTo(TYPE anotherValue) {
        return intField(Integer.MIN_VALUE, any -> any.compareTo(anotherValue));
    }
    public default BooleanField<HOST> greaterThan(TYPE anotherValue) {
        return booleanField(false, any -> any.compareTo(anotherValue) > 0);
    }
    public default BooleanField<HOST> lessThan(TYPE anotherValue) {
        return booleanField(false, any -> any.compareTo(anotherValue) < 0);
    }
    public default BooleanField<HOST> greaterThanOrEqualsTo(TYPE anotherValue) {
        return booleanField(false, any -> any.compareTo(anotherValue) >= 0);
    }
    public default BooleanField<HOST> lessThanOrEqualsTo(TYPE anotherValue) {
        return booleanField(false, any -> any.compareTo(anotherValue) <= 0);
    }
    
}
