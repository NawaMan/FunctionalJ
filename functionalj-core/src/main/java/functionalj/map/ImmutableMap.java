package functionalj.map;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import functionalj.list.ImmutableList;
import functionalj.tuple.ImmutableTuple2;
import functionalj.tuple.IntTuple2;
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
    
    private final boolean isLazy;

    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream) {
        this(stream, true);
    }
    public ImmutableMap(Stream<? extends Map.Entry<? extends KEY, ? extends VALUE>> stream, boolean isLazy) {
        // TODO - this shitty code have to be replaced .... :-(
        super(null, createPairList(stream));
        this.isLazy = isLazy;
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
    
    public boolean isLazy() {
        return isLazy;
    }
    
    public boolean isEager() {
        return !isLazy;
    }
    
    public FuncMap<KEY, VALUE> lazy() {
        if (isLazy)
            return this;
        
        val entries = entries();
        return new ImmutableMap<KEY, VALUE>(entries, true);
    }
    public FuncMap<KEY, VALUE> eager() {
        if (!isLazy)
            return this;
        
        val entries = entries();
        return new ImmutableMap<KEY, VALUE>(entries, false);
    }
    
}
