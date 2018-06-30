package functionalj.lens;

import static functionalj.lens.AccessUtil.createNullableAccess;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

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
    public default StringAccess<HOST> asString() {
        return stringAccess(
                null,
                any -> {
                    return any.toString();
                });
    }
    
    public default IntegerAccess<HOST> intAccess(int defaultValue, Function<DATA, Integer> function) {
        return host -> {
            val intValue = __internal__.processValue(this, host, defaultValue, function);
            return intValue;
        };
    }
    
    public default StringAccess<HOST> stringAccess(String defaultValue, Function<DATA, String> function) {
        return host -> {
            val stringValue = __internal__.processValue(this, host, defaultValue, function);
            return stringValue;
        };
    }
    
    public default BooleanAccess<HOST> booleanAccess(boolean defaultValue, Function<DATA, Boolean> function) {
        return host -> {
            val booleanValue = __internal__.processValue(this, host, defaultValue, function);
            return booleanValue;
        };
    }
    
    public default AnyAccess<HOST, DATA> or(DATA fallbackValue) {
        return __internal__.or(this, fallbackValue);
    }
    
    public default AnyAccess<HOST, DATA> orGet(Supplier<DATA> fallbackValueSupplier) {
        return __internal__.orGet(this, fallbackValueSupplier);
    }
    
    public default AnyAccess<HOST, DATA> orGet(Function<HOST, DATA> fallbackValueFunction) {
        return __internal__.orGet(this, fallbackValueFunction);
    }
    
    public default <EXCEPTION extends RuntimeException> AnyAccess<HOST, DATA> orThrow() {
        return host -> {
            if (host == null)
                throw new NullPointerException();
            
            val value = this.apply(host);
            if (value == null)
                throw new NullPointerException();
            
            return value;
        };
    }
    
    public default <EXCEPTION extends RuntimeException> AnyAccess<HOST, DATA> orThrow(Supplier<EXCEPTION> exceptionSupplier) {
        return host -> {
            if (host == null)
                throw exceptionSupplier.get();
            
            val value = this.apply(host);
            if (value == null)
                throw exceptionSupplier.get();
            
            return value;
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
    
    public static class __internal__ {
        
        public static <HOST, DATA, TARGET> TARGET processValue(AnyAccess<HOST, DATA> access, HOST host, TARGET defaultValue, Function<DATA, TARGET> function) {
            if (host == null)
                return defaultValue;
            
            val value = access.apply(host);
            if (value == null)
                return defaultValue;
            
            val newValue = function.apply(value);
            return newValue;
        }
        
        public static <HOST, DATA> AnyAccess<HOST, DATA> or(AnyAccess<HOST, DATA> access, DATA fallbackValue) {
            return host -> {
                if (host == null)
                    return fallbackValue;
                
                val value = access.apply(host);
                if (value == null)
                    return fallbackValue;
                
                return value;
            };
        }
        
        public static <HOST, DATA> AnyAccess<HOST, DATA> orGet(AnyAccess<HOST, DATA> access, Supplier<DATA> fallbackValueSupplier) {
            return host -> {
                if (host == null)
                    return fallbackValueSupplier.get();
                
                val value = access.apply(host);
                if (value == null)
                    return fallbackValueSupplier.get();
                
                return value;
            };
        }
        
        public static <HOST, DATA> AnyAccess<HOST, DATA> orGet(AnyAccess<HOST, DATA> access, Function<HOST, DATA> fallbackValueFunction) {
            return host -> {
                if (host == null)
                    return fallbackValueFunction.apply(host);
                
                val value = access.apply(host);
                if (value == null)
                    return fallbackValueFunction.apply(host);
                
                return value;
            };
        }
    }
    
}
