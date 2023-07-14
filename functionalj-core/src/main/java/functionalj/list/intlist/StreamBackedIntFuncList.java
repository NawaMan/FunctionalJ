package functionalj.list.intlist;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import functionalj.list.FuncList.Mode;
import functionalj.stream.intstream.GrowOnlyIntArray;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.NonNull;
import lombok.val;

public class StreamBackedIntFuncList implements IntFuncList {

    private static final IntBinaryOperator zeroForEquals = (int i1, int i2) -> i1 == i2 ? 0 : 1;

    private static final IntPredicate notZero = (int i) -> i != 0;

    private final Mode mode;

    private final GrowOnlyIntArray cache = new GrowOnlyIntArray();

    private final Spliterator.OfInt spliterator;

    StreamBackedIntFuncList(@NonNull IntStream stream, @NonNull Mode mode) {
        this.spliterator = stream.spliterator();
        this.mode = mode;
        if (mode.isEager()) {
            size();
        }
    }

    public StreamBackedIntFuncList(@NonNull IntStream stream) {
        this(stream, Mode.cache);
    }

    public Mode mode() {
        return mode;
    }

    @Override
    public IntFuncList toLazy() {
        if (mode.isLazy()) {
            return this;
        }
        return new StreamBackedIntFuncList(intStreamPlus(), Mode.lazy);
    }

    @Override
    public IntFuncList toEager() {
        // Just materialize all value.
        int size = size();
        return new ImmutableIntFuncList(cache, size, Mode.eager);
    }

    @Override
    public IntFuncList toCache() {
        if (mode.isCache()) {
            return this;
        }
        return new StreamBackedIntFuncList(intStreamPlus(), Mode.cache);
    }

    @Override
    public IntStreamPlus intStream() {
        val indexRef = new AtomicInteger(0);
        val valueConsumer = (IntConsumer) ((int v) -> cache.add(v));
        val newSpliterator = new Spliterators.AbstractIntSpliterator(Long.MAX_VALUE, 0) {

            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                int index = indexRef.getAndIncrement();
                if (fromCache(consumer, index))
                    return true;
                boolean hadNext = false;
                synchronized (this) {
                    if (index >= cache.length()) {
                        hadNext = spliterator.tryAdvance(valueConsumer);
                    }
                }
                if (fromCache(consumer, index))
                    return true;
                return hadNext;
            }

            private boolean fromCache(IntConsumer consumer, int index) {
                if (index >= cache.length())
                    return false;
                int value = cache.get(index);
                consumer.accept(value);
                return true;
            }
        };
        val newStream = StreamSupport.intStream(newSpliterator, false);
        return IntStreamPlus.from(newStream);
    }

    @Override
    public int hashCode() {
        return reduce(43, (hash, each) -> hash * 43 + each);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsIntFuncList))
            return false;
        val anotherList = (IntFuncList) o;
        if (size() != anotherList.size())
            return false;
        return !IntFuncList.zipOf(this, anotherList.asIntFuncList(), zeroForEquals).anyMatch(notZero);
    }

    @Override
    public String toString() {
        return asIntFuncList().toListString();
    }
}
