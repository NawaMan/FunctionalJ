package functionalj.stream;

import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.map.FuncMap;

public interface StreamableWithMapToMap<DATA> {
    
    public <TARGET> Streamable<TARGET> deriveWith(
            Function<StreamPlus<DATA>, Stream<TARGET>> action);
    
    
    //== mapToMap ==
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, Function<? super DATA, VALUE> mapper) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key, mapper);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7,
            KEY key8, Function<? super DATA, VALUE> mapper8) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7,
                              key8, mapper8);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7,
            KEY key8, Function<? super DATA, VALUE> mapper8,
            KEY key9, Function<? super DATA, VALUE> mapper9) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7,
                              key8, mapper8,
                              key9, mapper9);
        });
    }
    
    public default <KEY, VALUE> Streamable<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, Function<? super DATA, VALUE> mapper1,
            KEY key2, Function<? super DATA, VALUE> mapper2,
            KEY key3, Function<? super DATA, VALUE> mapper3,
            KEY key4, Function<? super DATA, VALUE> mapper4,
            KEY key5, Function<? super DATA, VALUE> mapper5,
            KEY key6, Function<? super DATA, VALUE> mapper6,
            KEY key7, Function<? super DATA, VALUE> mapper7,
            KEY key8, Function<? super DATA, VALUE> mapper8,
            KEY key9, Function<? super DATA, VALUE> mapper9,
            KEY key10, Function<? super DATA, VALUE> mapper10) {
        return deriveWith(stream -> {
            return StreamPlus
                    .from    (stream)
                    .mapToMap(key1, mapper1,
                              key2, mapper2,
                              key3, mapper3,
                              key4, mapper4,
                              key5, mapper5,
                              key6, mapper6,
                              key7, mapper7,
                              key8, mapper8,
                              key9, mapper9,
                              key10, mapper10);
        });
    }
    
}
