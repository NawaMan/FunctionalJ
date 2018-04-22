package functionalj.lens;

import java.util.Objects;
import java.util.function.Function;

import functionalj.functions.Func1;
import functionalj.types.MayBe;
import lombok.val;

@FunctionalInterface
public interface AnyAccess<HOST, DATA> extends Func1<HOST, DATA> {
    
    // TODO - See if we can return a lens here when this object is a lens.
    public default MayBeAccess<HOST, DATA> toMayBe() {
        return host -> MayBe.of(this.apply(host));
    }
    
    public default BooleanAccess<HOST> thatIs(DATA value) {
        return booleanAccess(value != null, any -> any == value);
    }
    public default BooleanAccess<HOST> thatIsNot(DATA value) {
        return booleanAccess(value == null, any -> any != value);
    }
    public default BooleanAccess<HOST> thatEquals(DATA value) {
        return booleanAccess(value == null, any -> Objects.equals(any, value));
    }
    public default BooleanAccess<HOST> thatNotEqualsTo(DATA value) {
        return booleanAccess(value == null, any -> !Objects.equals(any, value));
    }
    public default BooleanAccess<HOST> thatIsNull() {
        return booleanAccess(true, any -> any == null);
    }
    public default BooleanAccess<HOST> thatIsNotNull() {
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
