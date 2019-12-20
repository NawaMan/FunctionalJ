package functionalj.list.intlist;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import lombok.val;

public class IntFuncListDerivedFromIntStreamable
                implements IntFuncList {
    
    private static final IntBiFunctionPrimitive zeroForEquals = (i1, i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate toZero = i -> i == 0;
    
    private IntStreamable source;
    private Function<IntStreamable, IntStream> action;
    
    public IntFuncListDerivedFromIntStreamable(IntStreamable souce, Function<IntStreamable, IntStream> action) {
        this.action = Objects.requireNonNull(action);
        this.source = Objects.requireNonNull(souce);
    }
    
    @Override
    public IntStreamPlus stream() {
        val newStream = action.apply(source);
        return IntStreamPlus
                .from(newStream);
    }
    
    @Override
    public IntFuncList lazy() {
        return this;
    }
    
    @Override
    public IntFuncList eager() {
        return new ImmutableIntList(this, false);
    }
    
    public String toString() {
        return toListString();
    }
    
    public int hashCode() {
        return reduce(43, (hash, each) -> hash*43 + each);
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof IntFuncList))
            return false;
        
        if (hashCode() != o.hashCode())
            return false;
        
        val anotherList = (IntFuncList)o;
        if (size() != anotherList.size())
            return false;
        
        return zipWith(anotherList.stream(), zeroForEquals)
                .allMatch(toZero);
    }

}
