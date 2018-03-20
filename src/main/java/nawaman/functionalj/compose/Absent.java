package nawaman.functionalj.compose;

public class Absent<T> {
    public static final <T> Absent<T> absent() {
        return new Absent();
    }
}
