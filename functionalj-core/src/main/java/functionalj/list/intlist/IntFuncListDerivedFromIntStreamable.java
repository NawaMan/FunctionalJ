package functionalj.list.intlist;

import java.util.Objects;

import functionalj.stream.IntIteratorPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;

class IntFuncListDerivedFromIntStreamable
                implements IntFuncList {
    
    //private static final IntBiFunctionPrimitive zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    //private static final IntPredicate           toZero        = (int i)          -> i  == 0;
    
    private IntStreamable source;
    
    public IntFuncListDerivedFromIntStreamable(IntStreamable souce) {
        this.source = Objects.requireNonNull(souce);
    }
    
    //@Override
    //public IntStreamable intStreamable() {
    //    return source;
    //}
    
    @Override
    public IntStreamPlus intStream() {
        return source.intStream();
    }
    
    //@Override
    //public IntFuncList lazy() {
    //    return this;
    //}
    
    //@Override
    //public IntFuncList eager() {
    //    val data = this.toArray();
    //    return new ImmutableIntFuncList(data, false);
    //}
    
    //public String toString() {
    //    return toListString();
    //}
    
    //public int hashCode() {
    //    return reduce(43, (hash, each) -> hash*43 + each);
    //}
    
    //public boolean equals(Object o) {
    //    if (!(o instanceof IntFuncList))
    //        return false;
        
    //    if (hashCode() != o.hashCode())
    //        return false;
        
    //    val anotherList = (IntFuncList)o;
    //    if (size() != anotherList.size())
    //        return false;
        
    //    return zipWith   (anotherList, zeroForEquals)
    //            .allMatch(toZero);
    //}

}
