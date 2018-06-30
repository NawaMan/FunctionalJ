package functionalj.lens;

import static functionalj.lens.core.LensSpec.selfRead;
import static functionalj.lens.core.LensSpec.selfWrite;

import java.util.List;

import functionalj.lens.core.LensSpec;
import functionalj.lens.core.LensSpecParameterized;
import functionalj.lens.core.LensType;
import functionalj.lens.core.LensUtils;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.AnyLens;
import functionalj.lens.lenses.BooleanLens;
import functionalj.lens.lenses.ComparableLens;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ListLens;
import functionalj.lens.lenses.ObjectLens;
import functionalj.lens.lenses.StringLens;

public interface Lenses {
    
    public static final AnyLens<Object, Object> theObject  = AnyLens    .of(LensSpec.of(Object.class));
    public static final BooleanLens<Boolean>    theBoolean = BooleanLens.of(LensSpec.of(Boolean.class));
    public static final StringLens<String>      theString  = StringLens .of(LensSpec.of(String.class));
    public static final IntegerLens<Integer>    theInteger = IntegerLens.of(LensSpec.of(Integer.class));
    
    public static final BooleanLens<Boolean>    $B = theBoolean;
    public static final StringLens<String>      $S = theString;
    public static final IntegerLens<Integer>    $I = theInteger;
    
    public static final TheListLens theList = new TheListLens();
    
    
    public static <T> AnyLens<T, T> theItem() {
        return AnyLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T> ObjectLens<T, T> theObject() {
        return ObjectLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T extends Comparable<T>> ComparableLens<T, T> theComparable() {
        return ComparableLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    
    
    
    
    
    //== Internal use only ==
    
    public static class TheListLens implements ListLens<List<?>, Object, ObjectLens<List<?>, Object>> {

        private static final LensSpecParameterized<List<?>, List<?>, Object, ObjectLens<List<?>, Object>> 
                common = LensUtils.createLensSpecParameterized(selfRead(), selfWrite(), ObjectLens::of);
        
        public <T, SA extends AnyAccess<List<T>, T>, SL extends AnyLens<List<T>, T>> 
                ListLens<List<T>, T, SL> of(LensType<List<T>, T, SA, SL> type) {
            LensSpecParameterized<List<T>, List<T>, T, SL> spec
                    = LensUtils.createLensSpecParameterized(LensSpec.selfRead(), LensSpec.selfWrite(), s -> type.newLens(s));
            ListLens<List<T>, T, SL> listLens = ListLens.of(spec);
            return listLens;
        }
        
        public ListLens<List<String>, String, StringLens<List<String>>> ofString() {
            return of(LensTypes.STRING());
        }
        
        @Override
        public LensSpecParameterized<List<?>, List<Object>, Object, ObjectLens<List<?>, Object>> lensSpecParameterized() {
            return (LensSpecParameterized)common;
        }
    }
    
}
