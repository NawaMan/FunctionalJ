package functionalj.list.intlist;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.stream.intstream.IntStreamable;
import functionalj.stream.intstream.IntStreamableWithMapToMap;

public interface IntFuncListWithMapToMap extends IntStreamableWithMapToMap {
    
    public <TARGET> FuncList<TARGET> deriveToList(Function<IntStreamable, Stream<TARGET>> action);
    
    
    //== mapToMap ==
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key, IntFunction<? extends VALUE> mapper) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key, mapper);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key1, mapper1,
                          key2, mapper2);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key1, mapper1,
                          key2, mapper2,
                          key3, mapper3);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3,
        KEY key4, IntFunction<? extends VALUE> mapper4) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key1, mapper1,
                          key2, mapper2,
                          key3, mapper3,
                          key4, mapper4);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3,
        KEY key4, IntFunction<? extends VALUE> mapper4,
        KEY key5, IntFunction<? extends VALUE> mapper5) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key1, mapper1,
                          key2, mapper2,
                          key3, mapper3,
                          key4, mapper4,
                          key5, mapper5);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3,
        KEY key4, IntFunction<? extends VALUE> mapper4,
        KEY key5, IntFunction<? extends VALUE> mapper5,
        KEY key6, IntFunction<? extends VALUE> mapper6) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key1, mapper1,
                          key2, mapper2,
                          key3, mapper3,
                          key4, mapper4,
                          key5, mapper5,
                          key6, mapper6);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3,
        KEY key4, IntFunction<? extends VALUE> mapper4,
        KEY key5, IntFunction<? extends VALUE> mapper5,
        KEY key6, IntFunction<? extends VALUE> mapper6,
        KEY key7, IntFunction<? extends VALUE> mapper7) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
                .mapToMap(key1, mapper1,
                          key2, mapper2,
                          key3, mapper3,
                          key4, mapper4,
                          key5, mapper5,
                          key6, mapper6,
                          key7, mapper7);
    });
    }
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3,
        KEY key4, IntFunction<? extends VALUE> mapper4,
        KEY key5, IntFunction<? extends VALUE> mapper5,
        KEY key6, IntFunction<? extends VALUE> mapper6,
        KEY key7, IntFunction<? extends VALUE> mapper7,
        KEY key8, IntFunction<? extends VALUE> mapper8) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1, IntFunction<? extends VALUE> mapper1,
        KEY key2, IntFunction<? extends VALUE> mapper2,
        KEY key3, IntFunction<? extends VALUE> mapper3,
        KEY key4, IntFunction<? extends VALUE> mapper4,
        KEY key5, IntFunction<? extends VALUE> mapper5,
        KEY key6, IntFunction<? extends VALUE> mapper6,
        KEY key7, IntFunction<? extends VALUE> mapper7,
        KEY key8, IntFunction<? extends VALUE> mapper8,
        KEY key9, IntFunction<? extends VALUE> mapper9) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
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
    
    public default <KEY, VALUE> FuncList<FuncMap<KEY, VALUE>> mapToMap(
        KEY key1,  IntFunction<? extends VALUE> mapper1,
        KEY key2,  IntFunction<? extends VALUE> mapper2,
        KEY key3,  IntFunction<? extends VALUE> mapper3,
        KEY key4,  IntFunction<? extends VALUE> mapper4,
        KEY key5,  IntFunction<? extends VALUE> mapper5,
        KEY key6,  IntFunction<? extends VALUE> mapper6,
        KEY key7,  IntFunction<? extends VALUE> mapper7,
        KEY key8,  IntFunction<? extends VALUE> mapper8,
        KEY key9,  IntFunction<? extends VALUE> mapper9,
        KEY key10, IntFunction<? extends VALUE> mapper10) {
    return deriveToList(streamable -> {
        return streamable
                .stream()
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
