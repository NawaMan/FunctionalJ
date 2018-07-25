package functionalj.types.map;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import functionalj.types.list.FunctionalList;
import tuple.ImmutableTuple2;

@SuppressWarnings("javadoc")
public interface IFunctionalMap<KEY, VALUE, SELF extends IFunctionalMap<KEY, VALUE, ?>> {
    
    public int size();
    
    public boolean isEmpty();
    
    public boolean hasKey(KEY key);
    
    public boolean hasValue(VALUE value);
    
    public boolean hasKey(Predicate<? super KEY> keyCheck);
    
    public boolean hasValue(Predicate<? super VALUE> valueCheck);
    
    public Optional<VALUE> findBy(KEY key);
    
    public FunctionalList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    public FunctionalList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    public FunctionalMap<KEY, VALUE> with(KEY key, VALUE value);
    
    public FunctionalMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    public FunctionalMap<KEY, VALUE> defaultTo(KEY key, VALUE value);
    
    public FunctionalMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> value);
    
    public FunctionalMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> value);
    
    public FunctionalMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries);
    
    public FunctionalMap<KEY, VALUE> exclude(KEY key);
    
    public FunctionalMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    public FunctionalMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    public FunctionalMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck);

    public FunctionalList<KEY> keys();
    
    public FunctionalList<VALUE> values();
    
    public Set<Entry<KEY, VALUE>> entrySet();
    
    public FunctionalList<ImmutableTuple2<KEY, VALUE>> entries();
    
    public Map<KEY, VALUE> toMap();
    
    public ImmutableMap<KEY, VALUE> toImmutableMap();
    
    public FunctionalMap<KEY, VALUE> sorted();
    
    public FunctionalMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator);
    
    public <TARGET> FunctionalMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper);
    
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    public void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action);
    
}
