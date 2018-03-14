package nawaman.functionalj.fields;

import java.util.Objects;
import java.util.function.Function;

import lombok.val;

@FunctionalInterface
public interface AnyField<HOST, TYPE> extends Function<HOST, TYPE> {

    public default BooleanField<HOST> is(TYPE value) {
        return booleanField(value != null, any -> any == value);
    }
    public default BooleanField<HOST> isNot(TYPE value) {
        return booleanField(value == null, any -> any != value);
    }
    public default BooleanField<HOST> equalsTo(TYPE value) {
        return booleanField(value == null, any -> Objects.equals(any, value));
    }
    public default BooleanField<HOST> notEqualsTo(TYPE value) {
        return booleanField(value == null, any -> !Objects.equals(any, value));
    }
    public default BooleanField<HOST> isNull() {
        return booleanField(true, any -> any == null);
    }
    public default BooleanField<HOST> isNotNull() {
        return booleanField(false, any -> any != null);
    }
    public default IntegerField<HOST> getHashCode() {
        return intField(Integer.MIN_VALUE, any -> any.hashCode());
    }
    public default StringField<HOST> getToString() {
        return stringField(null, any -> any.toString());
    }
    
    //== Generic processing of value ==
    
    public default <TARGET> TARGET processValue(HOST host, TARGET defaultValue, Function<TYPE, TARGET> function) {
        if (host == null)
            return defaultValue;
        
        val value = this.apply(host);
        if (value == null)
            return defaultValue;
        
        return function.apply(value);
    }
    
    public default IntegerField<HOST> intField(int defaultValue, Function<TYPE, Integer> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
    public default StringField<HOST> stringField(String defaultValue, Function<TYPE, String> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
    public default BooleanField<HOST> booleanField(boolean defaultValue, Function<TYPE, Boolean> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
}
