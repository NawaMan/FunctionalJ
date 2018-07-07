package functionalj.types;

import java.util.Map;
import java.util.stream.Stream;

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
        super(null, ImmutableList.of(stream.map(entry -> {
            int keyHash = calculateHash(entry.getKey());
            @SuppressWarnings("unchecked")
            ImmutableTuple2<KEY, VALUE> tuple = (entry instanceof ImmutableTuple2)
                    ? (ImmutableTuple2<KEY, VALUE>)entry
                    : new ImmutableTuple2<KEY, VALUE>(entry);
            return new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, tuple);
        })));
    }
    
    private static Integer calculateHash(Object key) {
        return Nullable.of(key)
                .map(Object::hashCode)
                .orElse(0);
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
}
