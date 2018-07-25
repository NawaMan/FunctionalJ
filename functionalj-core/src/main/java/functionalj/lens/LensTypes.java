package functionalj.lens;

import java.util.function.Function;

import functionalj.lens.core.AbstractLensType;
import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensType;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.ObjectAccess;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.StringAccess;
import functionalj.lens.lenses.StringLens;

@SuppressWarnings("javadoc")
public interface LensTypes {
    
    @SuppressWarnings("rawtypes")
	public static <H, T, TA extends AnyAccess<H, T>, TL extends AnyLens<H, T>> 
            LensType<H, T, TA, TL> of(
                    Class<T> dataClass, 
                    Class<? extends AnyAccess> accessClass, 
                    Class<? extends AnyLens> lensClass,
                    Function<Function<H, T>, TA> accessCreator,
                    Function<LensSpec<H, T>, TL> lensCreator) {
        return new AbstractLensType<H, T, TA, TL>(dataClass, accessClass, lensClass) {
            @Override
            public TL newLens(LensSpec<H, T> spec) {
                return lensCreator.apply(spec);
            }
            @Override
            public TA newAccess(Function<H, T> accessToValue) {
                return accessCreator.apply(accessToValue);
            }
        };
    }
    
    
    public static <H> LensType<H, Object, AnyAccess<H, Object>, AnyLens<H, Object>> OBJECT() {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        LensType<H, Object, AnyAccess<H, Object>, AnyLens<H, Object>> type 
            = (LensType<H, Object, AnyAccess<H, Object>, AnyLens<H, Object>>)(LensType)__internal__.objectLensType;
        return type;
    }
    
    public static <H> LensType<H, String, StringAccess<H>, StringLens<H>> STRING() {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        LensType<H, String, StringAccess<H>, StringLens<H>> type 
            = (LensType<H, String, StringAccess<H>, StringLens<H>>)(LensType)__internal__.stringLensType;
        return type;
    }
    
    
    public static final class __internal__ {
        
        static final LensType<Object, Object, ObjectAccess<Object, Object>, ObjectLens<Object, Object>> objectLensType
                = LensTypes.of(Object.class, ObjectAccess.class, ObjectLens.class, access -> access::apply, ObjectLens::of);
        
        static final LensType<Object, String, StringAccess<Object>, StringLens<Object>> stringLensType
                = LensTypes.of(String.class, StringAccess.class, StringLens.class, access -> access::apply, StringLens::of);
    
    }
    
}
