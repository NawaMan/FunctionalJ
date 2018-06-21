package functionalj.types;

import java.util.Map;
import java.util.stream.Stream;

import lombok.val;
import nawaman.nullablej.nullable.Nullable;

public final class ImmutableMap<KEY, VALUE> extends FunctionalMapStream<KEY, VALUE> {
    
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(Map<? extends KEY, ? extends VALUE> map) {
        return new ImmutableMap<KEY, VALUE>(map.entrySet().stream());
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(FunctionalMap<? extends KEY, ? extends VALUE> map) {
        return new ImmutableMap<KEY, VALUE>(map.entries().stream());
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        return new ImmutableMap<KEY, VALUE>(stream);
    }
    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        super(ImmutableList.of(stream.map(entry -> {
            val keyHash = calculateHash(entry.getKey());
            @SuppressWarnings("unchecked")
            val tuple = (entry instanceof Tuple2)
                    ? (Tuple2<KEY, VALUE>)entry
                    : new Tuple2<KEY, VALUE>(entry);
            return new IntTuple<Tuple2<KEY, VALUE>>(keyHash, tuple);
        })));
    }
    
    private static Integer calculateHash(Object key) {
        return Nullable.of(key)
                .map(Object::hashCode)
                .orElse(0);
    }

    @SafeVarargs
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> ofEntries(Tuple2<KEY, VALUE> ... entries) {
        return new ImmutableMap<KEY, VALUE>(Stream.of(entries));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0) {
        return ofEntries(new Tuple2<KEY, VALUE>(key0, value0));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new Tuple2<KEY, VALUE>(key0, value0),
                new Tuple2<KEY, VALUE>(key1, value1));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new Tuple2<KEY, VALUE>(key0, value0),
                new Tuple2<KEY, VALUE>(key1, value1),
                new Tuple2<KEY, VALUE>(key2, value2));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new Tuple2<KEY, VALUE>(key0, value0),
                new Tuple2<KEY, VALUE>(key1, value1),
                new Tuple2<KEY, VALUE>(key2, value2),
                new Tuple2<KEY, VALUE>(key3, value3));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new Tuple2<KEY, VALUE>(key0, value0),
                new Tuple2<KEY, VALUE>(key1, value1),
                new Tuple2<KEY, VALUE>(key2, value2),
                new Tuple2<KEY, VALUE>(key3, value3),
                new Tuple2<KEY, VALUE>(key4, value4));
    }
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(
            KEY key0, VALUE value0,
            KEY key1, VALUE value1,
            KEY key2, VALUE value2,
            KEY key3, VALUE value3,
            KEY key4, VALUE value4,
            KEY key5, VALUE value5) {
        return (ImmutableMap<KEY, VALUE>) ofEntries(
                new Tuple2<KEY, VALUE>(key0, value0),
                new Tuple2<KEY, VALUE>(key1, value1),
                new Tuple2<KEY, VALUE>(key2, value2),
                new Tuple2<KEY, VALUE>(key3, value3),
                new Tuple2<KEY, VALUE>(key4, value4),
                new Tuple2<KEY, VALUE>(key5, value5));
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
                new Tuple2<KEY, VALUE>(key0, value0),
                new Tuple2<KEY, VALUE>(key1, value1),
                new Tuple2<KEY, VALUE>(key2, value2),
                new Tuple2<KEY, VALUE>(key3, value3),
                new Tuple2<KEY, VALUE>(key4, value4),
                new Tuple2<KEY, VALUE>(key5, value5),
                new Tuple2<KEY, VALUE>(key6, value6));
    }
}
