package functionalj.map;

import java.util.ArrayList;
import java.util.List;
import functionalj.map.FuncMap.UnderlineMap;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;

public class FuncMapBuilder<K, V> {
    
    private final List<Tuple2<K, V>> entries;
    
    public FuncMapBuilder() {
        this.entries = new ArrayList<Tuple2<K, V>>();
    }
    
    private FuncMapBuilder(List<Tuple2<K, V>> entries) {
        this.entries = entries;
    }
    
    public FuncMapBuilder<K, V> with(K key, V value) {
        this.entries.add(Tuple.of(key, value));
        return new FuncMapBuilder<>(this.entries);
    }
    
    public ImmutableFuncMap<K, V> build() {
        val map = FuncMap.underlineMap.orElse(UnderlineMap.HashMap).<K, V>newMap();
        for (Tuple2<K, V> entry : this.entries) {
            K key = entry._1();
            V value = entry._2();
            map.put(key, value);
        }
        return new ImmutableFuncMap<K, V>(map);
    }
}
