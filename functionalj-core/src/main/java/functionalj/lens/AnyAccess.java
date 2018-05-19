package functionalj.lens;

import static functionalj.lens.AccessUtil.createNullableAccess;

import java.util.Objects;
import java.util.function.Function;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface AnyAccess<HOST, DATA> extends Func1<HOST, DATA> {
    
    public default BooleanAccess<HOST> thatIs(DATA value) {
        return booleanAccess(
                value != null,
                any -> {
                    return any == value;
                });
    }
    public default BooleanAccess<HOST> thatIsNot(DATA value) {
        return booleanAccess(
                value == null,
                any -> {
                    return any != value;
                });
    }
    public default BooleanAccess<HOST> thatEquals(DATA value) {
        return booleanAccess(
                value == null,
                any -> {
                    return Objects.equals(any, value);
                });
    }
    public default BooleanAccess<HOST> thatNotEqualsTo(DATA value) {
        return booleanAccess(
                value == null,
                any -> {
                    return !Objects.equals(any, value);
                });
    }
    public default BooleanAccess<HOST> thatIsNull() {
        return booleanAccess(
                true,
                any -> {
                    return any == null;
                });
    }
    public default BooleanAccess<HOST> thatIsNotNull() {
        return booleanAccess(
                false,
                any -> {
                    return any != null;
                });
    }
    public default IntegerAccess<HOST> getHashCode() {
        return intAccess(
                Integer.MIN_VALUE,
                any -> {
                    return any.hashCode();
                });
    }
    public default StringAccess<HOST> convertToString() {
        return stringAccess(
                null,
                any -> {
                    return any.toString();
                });
    }
    
    public default <TARGET> TARGET processValue(HOST host, TARGET defaultValue, Function<DATA, TARGET> function) {
        if (host == null)
            return defaultValue;
        
        val value = this.apply(host);
        if (value == null)
            return defaultValue;
        
        val newValue = function.apply(value);
        return newValue;
    }
    
    public default IntegerAccess<HOST> intAccess(int defaultValue, Function<DATA, Integer> function) {
        return host -> {
            val intValue = processValue(host, defaultValue, function);
            return intValue;
        };
    }
    
    public default StringAccess<HOST> stringAccess(String defaultValue, Function<DATA, String> function) {
        return host -> {
            val stringValue = processValue(host, defaultValue, function);
            return stringValue;
        };
    }
    
    public default BooleanAccess<HOST> booleanAccess(boolean defaultValue, Function<DATA, Boolean> function) {
        return host -> {
            val booleanValue = processValue(host, defaultValue, function);
            return booleanValue;
        };
    }
    
    public default NullableAccess<HOST, DATA, AnyAccess<HOST, DATA>> toNullable() {
        Function<Function<HOST, DATA>, AnyAccess<HOST, DATA>> createSubLens = (Function<HOST, DATA> hostToData) -> {
            return (AnyAccess<HOST, DATA>)(HOST host) -> {
                val value = hostToData.apply(host);
                return value;
            };
        };
        Function<HOST, Nullable<DATA>> accessToNullable = host -> {
            val value = AnyAccess.this.apply(host);
            return Nullable.of(value);
        };
        return createNullableAccess(accessToNullable, createSubLens);
    }
    
}
