package functionalj.list.intlist;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import lombok.val;

public class ImmutableIntList implements IntFuncList {
    
    private static final IntBiFunctionPrimitive zeroForEquals = (i1, i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate toZero = i -> i == 0;
    
    private final int[] data;
    private final boolean isLazy;
    
    private volatile String  toStringCache = null;
    private volatile Integer hashcodeCache = null;
    
    private static ImmutableIntList emptyList = new ImmutableIntList(new int[0], true);
    
    public static ImmutableIntList empty() {
        return emptyList;
    }
    
    public static ImmutableIntList of(int ... source) {
        if ((source == null) || source.length == 0)
            return emptyList;
        
        val newArray = source.clone();
        return new ImmutableIntList(newArray, true);
    }
    
    public static ImmutableIntList from(int[] data) {
        if ((data == null) || data.length == 0)
            return emptyList;
        
        return new ImmutableIntList(data.clone(), true);
    }
    
    public static ImmutableIntList from(IntStream source) {
        if ((source == null))
            return emptyList;
        
        return new ImmutableIntList(source.toArray(), true);
    }
    
    public static ImmutableIntList from(IntStreamable source) {
        if ((source == null))
            return emptyList;
        
        if (source instanceof ImmutableIntList)
            return (ImmutableIntList)source;
        
        return new ImmutableIntList(source, true);
    }
    
    ImmutableIntList(int[] data, boolean isLazy) {
        this.data = Objects.requireNonNull(data);
        this.isLazy = isLazy;
    }
    
    ImmutableIntList(IntStreamable source, boolean isLazy) {
        this(source.toArray(), isLazy);
    }
    
    @Override
    public IntStreamPlus stream() {
        return IntStreamPlus.of(data);
    }
    
    @Override
    public IntFuncList lazy() {
        if (isLazy)
            return this;
        
        return new ImmutableIntList(this, true);
    }
    
    @Override
    public IntFuncList eager() {
        if (!isLazy)
            return this;
        
        return new ImmutableIntList(this, false);
    }
    
    @Override
    public boolean isLazy() {
        return isLazy;
    }
    
    public String toString() {
        if (toStringCache != null)
            return toStringCache;
        
        synchronized (this) {
            if (toStringCache != null)
                return toStringCache;
            
            toStringCache = toListString();
            return toStringCache;
        }
    }
    
    public int hashCode() {
        if (hashcodeCache != null)
            return hashcodeCache;
        
        synchronized (this) {
            if (hashcodeCache != null)
                return hashcodeCache;
            
            hashcodeCache = reduce(43, (hash, each) -> hash*43 + each);
            return hashcodeCache;
        }
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof IntFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (IntFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return zipWith(anotherList, zeroForEquals)
                .allMatch(toZero);
    }
    
}
