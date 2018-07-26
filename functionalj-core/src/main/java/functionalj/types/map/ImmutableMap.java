package functionalj.types.map;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import functionalj.types.list.ImmutableList;
import functionalj.types.tuple.ImmutableTuple2;
import functionalj.types.tuple.IntTuple2;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public final class ImmutableMap<KEY, VALUE> extends FunctionalMapStream<KEY, VALUE> {
    
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> of(FunctionalMap<? extends KEY, ? extends VALUE> map) {
        return new ImmutableMap<KEY, VALUE>(map.entries().stream());
    }
    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        super(null, ImmutableList.of(stream.map(entry -> {
                    if (entry == null)
                        return null;
                    
                    int keyHash = calculateHash(entry.getKey());
                    @SuppressWarnings("unchecked")
                    ImmutableTuple2<KEY, VALUE> tuple = (entry instanceof ImmutableTuple2)
                            ? (ImmutableTuple2<KEY, VALUE>)entry
                            : new ImmutableTuple2<KEY, VALUE>(entry);
                    return new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, tuple);
                })
                .filter(Objects::nonNull)));
    }
    
    private static Integer calculateHash(Object key) {
        return Nullable.of(key)
                .map(Object::hashCode)
                .orElse(0);
    }
    
}
