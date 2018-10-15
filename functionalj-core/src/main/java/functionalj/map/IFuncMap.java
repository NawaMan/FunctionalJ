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

import functionalj.types.list.FuncList;
import functionalj.types.tuple.ImmutableTuple2;

@SuppressWarnings("javadoc")
public interface IFuncMap<KEY, VALUE, SELF extends IFuncMap<KEY, VALUE, ?>> {
    
    public int size();
    
    public boolean isEmpty();
    
    public boolean hasKey(KEY key);
    
    public boolean hasValue(VALUE value);
    
    public boolean hasKey(Predicate<? super KEY> keyCheck);
    
    public boolean hasValue(Predicate<? super VALUE> valueCheck);
    
    public Optional<VALUE> findBy(KEY key);
    
    public FuncList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    public FuncList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    public FuncMap<KEY, VALUE> with(KEY key, VALUE value);
    
    public FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    public FuncMap<KEY, VALUE> defaultTo(KEY key, VALUE value);
    
    public FuncMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> value);
    
    public FuncMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> value);
    
    public FuncMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries);
    
    public FuncMap<KEY, VALUE> exclude(KEY key);
    
    public FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    public FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    public FuncMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck);

    public FuncList<KEY> keys();
    
    public FuncList<VALUE> values();
    
    public Set<Entry<KEY, VALUE>> entrySet();
    
    public FuncList<ImmutableTuple2<KEY, VALUE>> entries();
    
    public Map<KEY, VALUE> toMap();
    
    public ImmutableMap<KEY, VALUE> toImmutableMap();
    
    public FuncMap<KEY, VALUE> sorted();
    
    public FuncMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator);
    
    public <TARGET> FuncMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper);
    
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action);
    
    public void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action);
    
}
