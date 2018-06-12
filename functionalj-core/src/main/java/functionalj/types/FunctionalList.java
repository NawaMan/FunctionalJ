package functionalj.types;

import java.util.ListIterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class FunctionalList<DATA> implements IList<DATA, FunctionalList<DATA>> {

    @Override
    public abstract FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);

    @Override
    public abstract <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> 
                    TARGET_SELF stream(Function<Stream<DATA>, Stream<TARGET>> action);

    @Override
    public abstract Stream<DATA> stream();

    @Override
    public abstract <T> T[] toArray(T[] a);

    @Override
    public abstract DATA get(int index);

    @Override
    public abstract int indexOf(Object o);

    @Override
    public abstract int lastIndexOf(Object o);

    @Override
    public abstract ListIterator<DATA> listIterator();

    @Override
    public abstract ListIterator<DATA> listIterator(int index);
    
}
