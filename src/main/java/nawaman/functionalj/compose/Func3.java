package nawaman.functionalj.compose;

@FunctionalInterface
public interface Func3<I1, I2, I3, R> {
    
    public static <I1,I2,I3,R> Func3<I1, I2, I3, R> of(Func3<I1, I2, I3, R> f3) {
        return f3;
    }
    
    public R apply(I1 i1, I2 i2, I3 i3);
    
    
    public default Func1<I1, Func1<I2, Func1<I3, R>>> curry() {
        return i1 -> i2 ->i3 -> this.apply(i1, i2, i3);
    }
    
    public default Func2<I2, I3, R> curry1(I1 i1) {
        return (i2,i3) -> this.apply(i1, i2, i3);
    }
    public default Func2<I1, I3, R> curry2(I2 i2) {
        return (i1,i3) -> this.apply(i1, i2, i3);
    }
    public default Func2<I1, I2, R> curry3(I3 i3) {
        return (i1,i2) -> this.apply(i1, i2, i3);
    }
    
    public default Func1<I1, R> curry(Absent<I1> a1, I2 i2, I3 i3) {
        return i1 -> this.apply(i1, i2, i3);
    }
    
    public default Func1<I2, R> curry(I1 i1, Absent<I2> a2, I3 i3) {
        return i2 -> this.apply(i1, i2, i3);
    }
    public default Func1<I3, R> curry(I1 i1, I2 i2, Absent<I3> a3) {
        return i3 -> this.apply(i1, i2, i3);
    }
    
    public default Func2<I1, I2, R> curry(Absent<I1> a1, Absent<I2> a2, I3 i3) {
        return (i1, i2) -> this.apply(i1, i2, i3);
    }
    public default Func2<I1, I3, R> curry(Absent<I1> a1, I2 i2, Absent<I3> a3) {
        return (i1, i3) -> this.apply(i1, i2, i3);
    }
    public default Func2<I2, I3, R> curry(I1 i1, Absent<I2> a2, Absent<I3> a3) {
        return (i2, i3) -> this.apply(i1, i2, i3);
    }
    
}