package nawaman.functionalj.kinds;

public interface Setoid<TYPE,DATA> {
    
    public boolean equals(Setoid<TYPE,DATA> another);
    
}
