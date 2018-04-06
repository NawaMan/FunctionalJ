package nawaman.functionalj.lens;

@FunctionalInterface
public interface AnyEqualableAccess<HOST, DATA> extends AnyAccess<HOST, DATA> {
    public default BooleanAccess<HOST> thatIs(DATA value) {
        return thatEqualsTo(value);
    }
    public default BooleanAccess<HOST> isNot(DATA value) {
        return thatNotEqualsTo(value);
    }
}
