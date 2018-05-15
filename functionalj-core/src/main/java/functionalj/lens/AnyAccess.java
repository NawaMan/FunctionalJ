package functionalj.lens;

import static functionalj.compose.Functional.pipe;

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
        
        return function.apply(value);
    }
    
    public default IntegerAccess<HOST> intAccess(int defaultValue, Function<DATA, Integer> function) {
        return host -> {
            return processValue(host, defaultValue, function);
        };
    }
    
    public default StringAccess<HOST> stringAccess(String defaultValue, Function<DATA, String> function) {
        return host -> {
            return processValue(host, defaultValue, function);
        };
    }
    
    public default BooleanAccess<HOST> booleanAccess(boolean defaultValue, Function<DATA, Boolean> function) {
        return host -> {
            return processValue(host, defaultValue, function);
        };
    }
    
    public default NullableAccess<HOST, DATA, AnyAccess<HOST, DATA>> toNullable() {
        Function<Function<HOST, DATA>, AnyAccess<HOST, DATA>> createSubLens = (Function<HOST, DATA> hostToData) -> {
            return (AnyAccess<HOST, DATA>)(HOST host) -> {
                return hostToData.apply(host);
            };
        };
        Function<HOST, Nullable<DATA>> accessToNullable = host -> {
            return Nullable.of(AnyAccess.this.apply(host));
        };
        return createNullableAccess(accessToNullable, createSubLens);
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyAccess<HOST, TYPE>> 
            NullableAccess<HOST, TYPE, TYPELENS> createNullableAccess(
                        Function<HOST, Nullable<TYPE>>           accessNullable,
                        Function<Function<HOST, TYPE>, TYPELENS> createSubLens) {
        val accessWithSub = new AccessParameterized<HOST, Nullable<TYPE>, TYPE, TYPELENS>() {
            @Override
            public Nullable<TYPE> apply(HOST host) {
                return accessNullable.apply(host);
            }
            @Override
            public TYPELENS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToParameter) {
                val hostToParameter = pipe(this::apply, accessToParameter);
                val parameterLens   = createSubLens.apply(hostToParameter);
                return parameterLens;
            }
        };
        return () -> accessWithSub;
    }
    
}
