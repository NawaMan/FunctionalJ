package functionalj.lens;

import java.util.function.Function;

public interface LensTypes {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <H> LensType<H, String, StringAccess<H>, StringLens<H>> ofString() {
        return (LensType<H, String, StringAccess<H>, StringLens<H>>)(LensType)__internal__.lensCreatorString;
    }
    
    public static final class __internal__ {
        
        static final LensType<Object, String, StringAccess<Object>, StringLens<Object>> lensCreatorString
        = new AbstractLensType<Object, String, StringAccess<Object>, StringLens<Object>>(String.class, StringAccess.class, StringLens.class) {
            @Override
            public StringLens<Object> newLens(LensSpec<Object, String> spec) {
                return StringLens.of(spec);
            }
            
            @Override
            public StringAccess<Object> newAccess(Function<Object, String> accessToValue) {
                return accessToValue::apply;
            }
        };
    }
    
}
