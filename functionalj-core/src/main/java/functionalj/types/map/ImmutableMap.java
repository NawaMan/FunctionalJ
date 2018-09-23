package functionalj.types.map;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import functionalj.types.list.ImmutableList;
import functionalj.types.tuple.ImmutableTuple2;
import functionalj.types.tuple.IntTuple2;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@SuppressWarnings("javadoc")
public final class ImmutableMap<KEY, VALUE> extends FuncMapStream<KEY, VALUE> {
    
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> from(Map<? extends KEY, ? extends VALUE> map) {
        return (map instanceof ImmutableMap)
                ? (ImmutableMap<KEY, VALUE>)map
                : new ImmutableMap<KEY, VALUE>(map.entrySet().stream());
    }
    @SuppressWarnings("unchecked")
    public static <KEY, VALUE> ImmutableMap<KEY, VALUE> from(FuncMap<? extends KEY, ? extends VALUE> map) {
        return (map instanceof ImmutableMap)
                ? (ImmutableMap<KEY, VALUE>)map
                : new ImmutableMap<KEY, VALUE>(map.entries().stream());
    }
    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        // TODO - this shitty code have to be replaced .... :-(
        super(null, createPairList(stream));
    }
    private static <KEY, VALUE> ImmutableList<IntTuple2<ImmutableTuple2<KEY, VALUE>>> createPairList(
            Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        val list = new ArrayList<KEY>();
        return ImmutableList.from(
                        stream
                        .filter(entry -> !list.contains(entry.getKey()))
                        .peek  (entry -> list.add(entry.getKey()))
                        .map   (entry -> {
                            int keyHash = calculateHash(entry.getKey());
                            @SuppressWarnings("unchecked")
                            ImmutableTuple2<KEY, VALUE> tuple = (entry instanceof ImmutableTuple2)
                                    ? (ImmutableTuple2<KEY, VALUE>)entry
                                    : new ImmutableTuple2<KEY, VALUE>(entry);
                            return new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, tuple);
                        }));
    }
    
    private static Integer calculateHash(Object key) {
        return Nullable.of(key)
                .map(Object::hashCode)
                .orElse(0);
    }
    
}
