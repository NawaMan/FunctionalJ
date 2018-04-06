package nawaman.functionalj.lens;

import java.util.Objects;
import java.util.function.Function;

import lombok.val;
import nawaman.functionalj.functions.Func1;

@FunctionalInterface
public interface AnyAccess<HOST, DATA> extends Func1<HOST, DATA> {
//    
//    public default MayBeField<HOST, DATA> toMayBe() {
//        return host -> MayBe.of(this.apply(host));
//    }
//    
    public default BooleanAccess<HOST> is(DATA value) {
        return booleanAccess(value != null, any -> any == value);
    }
    public default BooleanAccess<HOST> isNot(DATA value) {
        return booleanAccess(value == null, any -> any != value);
    }
    public default BooleanAccess<HOST> equalsTo(DATA value) {
        return booleanAccess(value == null, any -> Objects.equals(any, value));
    }
    public default BooleanAccess<HOST> notEqualsTo(DATA value) {
        return booleanAccess(value == null, any -> !Objects.equals(any, value));
    }
    public default BooleanAccess<HOST> isNull() {
        return booleanAccess(true, any -> any == null);
    }
    public default BooleanAccess<HOST> isNotNull() {
        return booleanAccess(false, any -> any != null);
    }
    public default IntegerAccess<HOST> getHashCode() {
        return intAccess(Integer.MIN_VALUE, any -> any.hashCode());
    }
    public default StringAccess<HOST> getToString() {
        return stringAccess(null, any -> any.toString());
    }
    
//    //== Generic processing of value ==
    
    public default <TARGET> TARGET processValue(HOST host, TARGET defaultValue, Function<DATA, TARGET> function) {
        if (host == null)
            return defaultValue;
        
        val value = this.apply(host);
        if (value == null)
            return defaultValue;
        
        return function.apply(value);
    }
    
    public default IntegerAccess<HOST> intAccess(int defaultValue, Function<DATA, Integer> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
    public default StringAccess<HOST> stringAccess(String defaultValue, Function<DATA, String> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
    public default BooleanAccess<HOST> booleanAccess(boolean defaultValue, Function<DATA, Boolean> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
}
