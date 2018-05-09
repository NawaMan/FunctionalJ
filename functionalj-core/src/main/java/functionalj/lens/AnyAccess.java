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
    public default StringAccess<HOST> convertToString() {
        return stringAccess(null, any -> any.toString());
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
        return host -> processValue(host, defaultValue, function);
    }
    
    public default StringAccess<HOST> stringAccess(String defaultValue, Function<DATA, String> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
    public default BooleanAccess<HOST> booleanAccess(boolean defaultValue, Function<DATA, Boolean> function) {
        return host -> processValue(host, defaultValue, function);
    }
    
    public default NullableAccess<HOST, DATA, AnyAccess<HOST, DATA>> toNullable() {
        AccessCreator<HOST, DATA, AnyAccess<HOST, DATA>> createSubLens = new AccessCreator<HOST, DATA, AnyAccess<HOST, DATA>>() {
            @Override
            public AnyAccess<HOST, DATA> apply(Function<HOST, DATA> t) {
                return new AnyAccess<HOST, DATA>() {
                    @Override
                    public DATA apply(HOST host) {
                        return t.apply(host);
                    }
                };
            }
            
        };
        return createNullableAccess(
                host -> Nullable.of(AnyAccess.this.apply(host)),
                createSubLens);
    }
    
    public static <HOST, TYPE, TYPELENS extends AnyAccess<HOST, TYPE>> 
            NullableAccess<HOST, TYPE, TYPELENS> createNullableAccess(
                        Func1<HOST, Nullable<TYPE>>         accessNullable,
                        AccessCreator<HOST, TYPE, TYPELENS> createSubLens) {
        val accessWithSub = new AccessWithSub<HOST, Nullable<TYPE>, TYPE, TYPELENS>() {
            @Override
            public Nullable<TYPE> apply(HOST host) {
                return accessNullable.apply(host);
            }
            @Override
            public TYPELENS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToSub) {
                return createSubLens.apply(pipe(this::apply, accessToSub));
            }
        };
        return (NullableAccess<HOST, TYPE, TYPELENS>)() -> {
            return accessWithSub;
        };
    }
//    
//    @FunctionalInterface
//    public static interface NullableLens<HOST, TYPE, TYPELENS extends Lens<HOST, TYPE>>
//                extends ObjectLens<HOST, Nullable<TYPE>>, NullableAccess<HOST, TYPE, TYPELENS> {
//        @Override
//        default AccessWithSub<HOST, Nullable<TYPE>, TYPE, TYPELENS> accessWithSub() {
//            return new AccessWithSub<HOST, Nullable<TYPE>, TYPE, TYPELENS>() {
//                @Override
//                public Nullable<TYPE> apply(HOST host) {
//                    return lensSpec().apply(host);
//                }
//                @Override
//                public TYPELENS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToSub) {
//                    // TODO Auto-generated method stub
//                    return null;
//                }
//            };
//        }
//        @Override
//        public default Nullable<TYPE> apply(HOST host) {
//            return lensSpec().apply(host);
//        }
//    }
//    
//    
//    public static <HOST, TYPE, TYPELENS extends Lens<HOST, TYPE>> 
//            NullableLens<HOST, TYPE, TYPELENS> createNullableLens(
//                        Func1<HOST, Nullable<TYPE>>         accessNullable,
//                        AccessCreator<HOST, TYPE, TYPELENS> createSubLens) {
//        val accessWithSub = new AccessWithSub<HOST, Nullable<TYPE>, TYPE, TYPELENS>() {
//            @Override
//            public Nullable<TYPE> apply(HOST host) {
//                return accessNullable.apply(host);
//            }
//            @Override
//            public TYPELENS createSubAccess(Function<Nullable<TYPE>, TYPE> accessToSub) {
//                return createSubLens.apply(pipe(this::apply, accessToSub));
//            }
//        };
//        return (NullableAccess<HOST, TYPE, TYPELENS>)() -> {
//            return accessWithSub;
//        };
//    }
}
