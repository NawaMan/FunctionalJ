package nawaman.functionalj.compose;

@FunctionalInterface
public interface Func5<I1, I2, I3, I4, I5, R> {
    
    public static <I1,I2,I3,I4,I5,R> Func5<I1, I2, I3, I4, I5, R> of(Func5<I1, I2, I3, I4, I5, R> f5) {
        return f5;
    }
    
    public R apply(I1 i1, I2 i2, I3 i3, I4 i4, I5 i5);
    
    
    public default Func1<I1, Func1<I2, Func1<I3, Func1<I4, Func1<I5, R>>>>> curry() {
        return i1 -> i2 -> i3 -> i4 -> i5 -> this.apply(i1, i2, i3, i4, i5);
    }
    
    public default Func4<I2, I3, I4, I5, R> curry1(I1 i1) {
        return (i2,i3,i4,i5) -> this.apply(i1, i2, i3,i4, i5);
    }
    public default Func4<I1, I3, I4, I5, R> curry2(I2 i2) {
        return (i1,i3,i4,i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I1, I2, I4, I5, R> curry3(I3 i3) {
        return (i1,i2,i4,i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I1, I2, I3, I5, R> curry4(I4 i4) {
        return (i1,i2,i3,i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I1, I2, I3, I4, R> curry5(I5 i5) {
        return (i1,i2,i3,i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    
    public default Func1<I1, R> curry(Absent<I1> a1, I2 i2, I3 i3, I4 i4, I5 i5) {
        return i1 -> this.apply(i1, i2, i3, i4, i5);
    }
    
    public default Func1<I2, R> curry(I1 i1, Absent<I2> a2, I3 i3, I4 i4, I5 i5) {
        return i2 -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func1<I3, R> curry(I1 i1, I2 i2, Absent<I3> a3, I4 i4, I5 i5) {
        return i3 -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func1<I4, R> curry(I1 i1, I2 i2, I3 i3, Absent<I4> a4, I5 i5) {
        return i4 -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func1<I5, R> curry(I1 i1, I2 i2, I3 i3, I4 i4, Absent<I5> a5) {
        return i5 -> this.apply(i1, i2, i3, i4, i5);
    }
    
    public default Func2<I1, I2, R> curry(Absent<I1> a1, Absent<I2> a2, I3 i3, I4 i4, I5 i5) {
        return (i1, i2) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I1, I3, R> curry(Absent<I1> a1, I2 i2, Absent<I3> a3, I4 i4, I5 i5) {
        return (i1, i3) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I1, I4, R> curry(Absent<I1> a1, I2 i2, I3 i3, Absent<I4> a4, I5 i5) {
        return (i1, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I1, I5, R> curry(Absent<I1> a1, I2 i2, I3 i3, I4 i4, Absent<I5> a5) {
        return (i1, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I2, I3, R> curry(I1 i1, Absent<I2> a2, Absent<I3> a3, I4 i4, I5 i5) {
        return (i2, i3) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I2, I4, R> curry(I1 i1, Absent<I2> a2, I3 i3, Absent<I4> a4, I5 i5) {
        return (i2, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I2, I5, R> curry(I1 i1, Absent<I2> a2, I3 i3, I4 i4, Absent<I5> a5) {
        return (i2, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I3, I4, R> curry(I1 i1, I2 i2, Absent<I3> a3, Absent<I4> a4, I5 i5) {
        return (i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I3, I5, R> curry(I1 i1, I2 i2, Absent<I3> a3, I4 i4, Absent<I5> a5) {
        return (i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func2<I4, I5, R> curry(I1 i1, I2 i2, I3 i3, Absent<I4> a4, Absent<I5> a5) {
        return (i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    
    public default Func3<I1, I2, I3, R> curry(Absent<I1> a1, Absent<I2> a2, Absent<I3> a3, I4 i4, I5 i5) {
        return (i1, i2, i3) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I1, I2, I4, R> curry(Absent<I1> a1, Absent<I2> a2, I3 i3, Absent<I4> a4, I5 i5) {
        return (i1, i2, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I1, I2, I5, R> curry(Absent<I1> a1, Absent<I2> a2, I3 i3, I4 i4, Absent<I5> a5) {
        return (i1, i2, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I1, I3, I4, R> curry(Absent<I1> a1, I2 i2, Absent<I3> a3, Absent<I4> a4, I5 i5) {
        return (i1, i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I1, I3, I5, R> curry(Absent<I1> a1, I2 i2, Absent<I3> a3, I4 i4, Absent<I5> a5) {
        return (i1, i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I2, I3, I4, R> curry(I1 i1, Absent<I2> a2, Absent<I3> a3, Absent<I4> a4, I5 i5) {
        return (i2, i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I2, I3, I5, R> curry(I1 i1, Absent<I2> a2, Absent<I3> a3, I4 i4, Absent<I5> a5) {
        return (i2, i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func3<I3, I4, I5, R> curry(I1 i1, I2 i2, Absent<I3> a3, Absent<I4> I4, Absent<I5> a5) {
        return (i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    
    public default Func4<I1, I2, I3, I4, R> curry(Absent<I1> a1, Absent<I2> a2, Absent<I3> a3, Absent<I4> a4, I5 i5) {
        return (i1, i2, i3, i4) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I1, I2, I3, I5, R> curry(Absent<I1> a1, Absent<I2> a2, Absent<I3> a3, I4 i4, Absent<I5> a5) {
        return (i1, i2, i3, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I1, I2, I4, I5, R> curry(Absent<I1> a1, Absent<I2> a2, I3 i3, Absent<I4> a4, Absent<I5> a5) {
        return (i1, i2, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I1, I3, I4, I5, R> curry(Absent<I1> a1, I2 i2, Absent<I3> a3, Absent<I4> a4, Absent<I5> a5) {
        return (i1, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
    public default Func4<I2, I3, I4, I5, R> curry(I1 i1, Absent<I2> a2, Absent<I3> a3, Absent<I4> a4, Absent<I5> a5) {
        return (i2, i3, i4, i5) -> this.apply(i1, i2, i3, i4, i5);
    }
}