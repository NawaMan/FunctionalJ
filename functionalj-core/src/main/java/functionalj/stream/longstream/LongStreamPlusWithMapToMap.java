package functionalj.stream.longstream;

import static functionalj.map.FuncMap.mapOf;

import java.util.function.LongFunction;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.StreamPlus;

public interface LongStreamPlusWithMapToMap {

    public <TARGET> StreamPlus<TARGET> mapToObj(
            LongFunction<? extends TARGET> mapper);
    
    //== mapToMap ==
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, LongFunction<? extends VALUE> mapper) {
        return mapToObj(data -> mapOf(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4,
            KEY key5, LongFunction<? extends VALUE> mapper5) {
        return mapToObj(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4,
            KEY key5, LongFunction<? extends VALUE> mapper5,
            KEY key6, LongFunction<? extends VALUE> mapper6) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4,
            KEY key5, LongFunction<? extends VALUE> mapper5,
            KEY key6, LongFunction<? extends VALUE> mapper6,
            KEY key7, LongFunction<? extends VALUE> mapper7) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4,
            KEY key5, LongFunction<? extends VALUE> mapper5,
            KEY key6, LongFunction<? extends VALUE> mapper6,
            KEY key7, LongFunction<? extends VALUE> mapper7,
            KEY key8, LongFunction<? extends VALUE> mapper8) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4,
            KEY key5, LongFunction<? extends VALUE> mapper5,
            KEY key6, LongFunction<? extends VALUE> mapper6,
            KEY key7, LongFunction<? extends VALUE> mapper7,
            KEY key8, LongFunction<? extends VALUE> mapper8,
            KEY key9, LongFunction<? extends VALUE> mapper9) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, LongFunction<? extends VALUE> mapper1,
            KEY key2, LongFunction<? extends VALUE> mapper2,
            KEY key3, LongFunction<? extends VALUE> mapper3,
            KEY key4, LongFunction<? extends VALUE> mapper4,
            KEY key5, LongFunction<? extends VALUE> mapper5,
            KEY key6, LongFunction<? extends VALUE> mapper6,
            KEY key7, LongFunction<? extends VALUE> mapper7,
            KEY key8, LongFunction<? extends VALUE> mapper8,
            KEY key9, LongFunction<? extends VALUE> mapper9,
            KEY key10, LongFunction<? extends VALUE> mapper10) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data),
                key7, mapper7.apply(data),
                key8, mapper8.apply(data),
                key9, mapper9.apply(data),
                key10, mapper10.apply(data)));
    }
}
