package functionalj.lens;

public class Access {

    public static final AnyAccess<Object, Object> theThing   = (Object  item) -> item;
    public static final BooleanAccess<Boolean>    theBoolean = (Boolean item) -> item;
    public static final StringAccess<String>      theString  = (String  item) -> item;
    public static final IntegerAccess<Integer>    theInteger = (Integer item) -> item;

    public static final <T> AnyAccess<T, T> theItem() {
        return (T item) -> item;
    }
    public static final <T> ObjectAccess<T, T> theObject() {
        return (T item) -> item;
    }
    public static final <T extends Comparable<T>> ComparableAccess<T, T> theComparable() {
        return (T item) -> item;
    }
    // TODO - Take the comment out.
//    public static final <T, C extends Collection<T>> CollectionAccess<C, T, C> theCollection() {
//        return (C item) -> item;
//    }
//    public static final <T, L extends List<T>> ListAccess<L, T, L> theList() {
//        return (L item) -> item;
//    }
}
