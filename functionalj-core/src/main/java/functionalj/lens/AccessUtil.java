package functionalj.lens;

import static functionalj.compose.Functional.pipe;

import java.util.function.Function;

import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public class AccessUtil {
    
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
