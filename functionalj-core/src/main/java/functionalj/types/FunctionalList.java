package functionalj.types;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class FunctionalList<DATA> 
                    implements 
                        IList<DATA, FunctionalList<DATA>>, 
                        IFunctionalList<DATA,FunctionalList<DATA>> {

    @Override
    public abstract FunctionalList<DATA> streamFrom(Function<Supplier<Stream<DATA>>, Stream<DATA>> supplier);

    @Override
    public abstract <TARGET, TARGET_SELF extends ICanStream<TARGET, TARGET_SELF>> 
                    TARGET_SELF stream(Function<Stream<DATA>, Stream<TARGET>> action);

    @Override
    public abstract Stream<DATA> stream();

    @Override
    public ImmutableList<DATA> toImmutableList() {
        return ImmutableList.of(this);
    }
    
    @Override
    public Iterator<DATA> iterator() {
        return stream().iterator();
    }
    
    @Override
    public Spliterator<DATA> spliterator() {
        return stream().spliterator();
    }

    @Override
    public int size() {
        return (int)stream().count();
    }

    @Override
    public abstract DATA get(int index);
    
    
}
