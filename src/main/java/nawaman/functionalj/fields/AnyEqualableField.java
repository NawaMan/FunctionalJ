package nawaman.functionalj.fields;

@FunctionalInterface
public interface AnyEqualableField<HOST, TYPE> extends AnyField<HOST, TYPE> {
    public default BooleanField<HOST> is(TYPE value) {
        return equalsTo(value);
    }
    public default BooleanField<HOST> isNot(TYPE value) {
        return notEqualsTo(value);
    }
}
