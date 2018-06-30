package functionalj.lens;

import functionalj.lens.LensUtils.TheListLensCreator;

public class Lenses {
    
    public static final AnyLens<Object, Object> theObject  = AnyLens    .of(LensSpec.of(Object.class));
    public static final BooleanLens<Boolean>    theBoolean = BooleanLens.of(LensSpec.of(Boolean.class));
    public static final StringLens<String>      theString  = StringLens .of(LensSpec.of(String.class));
    public static final IntegerLens<Integer>    theInteger = IntegerLens.of(LensSpec.of(Integer.class));
    
    public static final BooleanLens<Boolean>    $B = theBoolean;
    public static final StringLens<String>      $S = theString;
    public static final IntegerLens<Integer>    $I = theInteger;
    
    
    public static <T> AnyLens<T, T> theItem() {
        return AnyLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T> ObjectLens<T, T> theObject() {
        return ObjectLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }
    public static <T extends Comparable<T>> ComparableLens<T, T> theComparable() {
        return ComparableLens.of(LensSpec.of((T item) -> item, (T host, T newItem) -> newItem));
    }

    
    public static final  TheListLensCreator theList = new TheListLensCreator();
    
}
