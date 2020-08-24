package functionalj.list.intlist;

import static functionalj.streamable.intstreamable.IntStreamable.zipOf;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.streamable.intstreamable.AsIntStreamable;
import functionalj.streamable.intstreamable.IntStreamable;
import lombok.val;

class IntFuncListDerivedFromIntStreamable
                implements IntFuncList {

    private static final IntBiFunctionPrimitive zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;
    private static final IntPredicate           toZero        = (int i)          -> i  == 0;

    private AsIntStreamable source;

    public IntFuncListDerivedFromIntStreamable(AsIntStreamable souce) {
        this.source = Objects.requireNonNull(souce);
    }

    @Override
    public IntStreamable intStreamable() {
        return source.intStreamable();
    }

    @Override
    public IntStream intStream() {
        return source.intStream();
    }

    @Override
    public IntFuncList lazy() {
        return this;
    }

    @Override
    public IntFuncList eager() {
        val data = this.toArray();
        return new ImmutableIntFuncList(data, false);
    }

    public String toString() {
        return streamable().toListString();
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

        return zipOf(this.intStreamable(), anotherList.intStreamable(), zeroForEquals)
                .allMatch(toZero);
    }

}
