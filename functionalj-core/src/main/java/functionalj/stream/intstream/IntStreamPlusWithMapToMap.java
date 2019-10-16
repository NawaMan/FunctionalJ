package functionalj.stream.intstream;

import static functionalj.map.FuncMap.mapOf;

import java.util.function.IntFunction;

import functionalj.map.FuncMap;
import functionalj.map.ImmutableMap;
import functionalj.stream.StreamPlus;

public interface IntStreamPlusWithMapToMap {

    public <TARGET> StreamPlus<TARGET> mapToObj(
            IntFunction<? extends TARGET> mapper);
    
    //== mapToMap ==
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key, IntFunction<? extends VALUE> mapper) {
        return mapToObj(data -> mapOf(key, mapper.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5) {
        return mapToObj(data -> ImmutableMap.of(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6) {
        return mapToObj(data -> mapOf(
                key1, mapper1.apply(data),
                key2, mapper2.apply(data),
                key3, mapper3.apply(data),
                key4, mapper4.apply(data),
                key5, mapper5.apply(data),
                key6, mapper6.apply(data)));
    }
    
    public default <KEY, VALUE> StreamPlus<FuncMap<KEY, VALUE>> mapToMap(
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7) {
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
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7,
            KEY key8, IntFunction<? extends VALUE> mapper8) {
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
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7,
            KEY key8, IntFunction<? extends VALUE> mapper8,
            KEY key9, IntFunction<? extends VALUE> mapper9) {
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
            KEY key1, IntFunction<? extends VALUE> mapper1,
            KEY key2, IntFunction<? extends VALUE> mapper2,
            KEY key3, IntFunction<? extends VALUE> mapper3,
            KEY key4, IntFunction<? extends VALUE> mapper4,
            KEY key5, IntFunction<? extends VALUE> mapper5,
            KEY key6, IntFunction<? extends VALUE> mapper6,
            KEY key7, IntFunction<? extends VALUE> mapper7,
            KEY key8, IntFunction<? extends VALUE> mapper8,
            KEY key9, IntFunction<? extends VALUE> mapper9,
            KEY key10, IntFunction<? extends VALUE> mapper10) {
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
