package functionalj.map;

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
import java.util.stream.Stream;

import functionalj.list.FuncList;
import functionalj.tuple.ImmutableTuple2;

@SuppressWarnings("javadoc")
public abstract class FuncMap<KEY, VALUE>
                    implements
                        ReadOnlyMap<KEY, VALUE>, 
                        IFuncMap<KEY, VALUE, FuncMap<KEY, VALUE>> {
    
    
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(Map<? extends KEY, ? extends VALUE> map) {
        return new ImmutableMap<KEY, VALUE>(map.entrySet().stream());
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        return new ImmutableMap<KEY, VALUE>(stream);
    }
    
    @SafeVarargs
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> ofEntries(ImmutableTuple2<KEY, VALUE> ... entries) {
        return new ImmutableMap<KEY, VALUE>(Stream.of(entries));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0) {
        return ofEntries(new ImmutableTuple2<KEY, VALUE>(key0, value0));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8,
            KEY key9, VALUE value9) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8),
                new ImmutableTuple2<KEY, VALUE>(key9, value9));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5,
            KEY key6, VALUE value6,
            KEY key7, VALUE value7,
            KEY key8, VALUE value8,
            KEY key9, VALUE value9,
            KEY key10, VALUE value10) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new ImmutableTuple2<KEY, VALUE>(key0, value0),
                new ImmutableTuple2<KEY, VALUE>(key1, value1),
                new ImmutableTuple2<KEY, VALUE>(key2, value2),
                new ImmutableTuple2<KEY, VALUE>(key3, value3),
                new ImmutableTuple2<KEY, VALUE>(key4, value4),
                new ImmutableTuple2<KEY, VALUE>(key5, value5),
                new ImmutableTuple2<KEY, VALUE>(key6, value6),
                new ImmutableTuple2<KEY, VALUE>(key7, value7),
                new ImmutableTuple2<KEY, VALUE>(key8, value8),
                new ImmutableTuple2<KEY, VALUE>(key9, value9),
                new ImmutableTuple2<KEY, VALUE>(key10, value10));
    }
    
    // TODO Map builder.
    
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
    public abstract FuncList<VALUE> select(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FuncList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate);
    
    @Override
    public abstract FuncMap<KEY, VALUE> with(KEY key, VALUE value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultTo(KEY key, VALUE value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> value);
    
    @Override
    public abstract FuncMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries);
    
    @Override
    public abstract FuncMap<KEY, VALUE> exclude(KEY key);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck);
    
    @Override
    public abstract FuncMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck);

    @Override
    public abstract FuncList<KEY> keys();
    
    @Override
    public abstract FuncList<VALUE> values();
    
    @Override
    public abstract Set<Entry<KEY, VALUE>> entrySet();
    
    @Override
    public abstract FuncList<ImmutableTuple2<KEY, VALUE>> entries();
    
    @Override
    public abstract Map<KEY, VALUE> toMap();
    
    @Override
    public abstract ImmutableMap<KEY, VALUE> toImmutableMap();
    
    @Override
    public abstract FuncMap<KEY, VALUE> sorted();
    
    @Override
    public abstract FuncMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator);
    
    @Override
    public abstract <TARGET> FuncMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper);
    
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
