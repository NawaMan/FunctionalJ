package functionalj.types;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class FunctionalMap<KEY, VALUE>
                    implements
                        ReadOnlyMap<KEY, VALUE>, 
                        IFunctionalMap<KEY, VALUE, FunctionalMap<KEY, VALUE>> {

    @Override
    public abstract int size();
    
    @Override
    public abstract boolean isEmpty();
    
    @Override
    public abstract boolean hasKey(KEY key);
    
    @Override
    public abstract boolean hasValue(VALUE value);
    
    @Override
    public abstract boolean hasKey(Predicate<? super KEY> keyCheck);
    
    @Override
    public abstract boolean hasValue(Predicate<? super VALUE> valueCheck);
    
    @Override
    public abstract Optional<VALUE> findBy(KEY key);

    @Override
    public abstract FunctionalList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FunctionalList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> with(KEY key, VALUE value);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> defaultTo(KEY key, VALUE value);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> value);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> value);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> exclude(KEY key);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck);

    @Override
    public abstract FunctionalList<KEY> keys();
    
    @Override
    public abstract FunctionalList<VALUE> values();
    
    @Override
    public abstract Set<Entry<KEY, VALUE>> entrySet();
    
    @Override
    public abstract FunctionalList<ImmutableTuple2<KEY, VALUE>> entries();
    
    @Override
    public abstract Map<KEY, VALUE> toMap();
    
    @Override
    public abstract ImmutableMap<KEY, VALUE> toImmutableMap();
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> sorted();
    
    @Override
    public abstract FunctionalMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator);
    
    @Override
    public abstract <TARGET> FunctionalMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper);
    
    @Override
    public abstract void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    @Override
    public abstract void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action);
    
    public String toString() {
        return "{" +
                entries()
                .map(each -> each._1 + ":" + each._2)
                .collect(Collectors.joining(", ")) +
                "}";
    }
    
    
}
