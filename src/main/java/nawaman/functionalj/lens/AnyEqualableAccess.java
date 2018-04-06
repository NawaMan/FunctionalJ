package nawaman.functionalj.lens;

@FunctionalInterface
public interface AnyEqualableAccess<HOST, DATA> extends AnyAccess<HOST, DATA> {
    public default BooleanAccess<HOST> is(DATA value) {
        return equalsTo(value);
    }
    public default BooleanAccess<HOST> isNot(DATA value) {
        return notEqualsTo(value);
    }
}
