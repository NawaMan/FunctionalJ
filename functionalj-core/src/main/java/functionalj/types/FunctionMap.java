package functionalj.types;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface FunctionMap<KEY, VALUE> {
    
    public int size() ;
    
    public boolean isEmpty();
    
    public boolean containsKey(KEY key);
    
    public boolean containsValue(VALUE value);
    
    public boolean containsKey(Predicate<KEY> key);
    
    public boolean containsValue(Predicate<VALUE> value);
    
    public VALUE get(KEY key);

    public VALUE getOrDefault(KEY key, VALUE orElse);
    
    public VALUE getOrElse(KEY key, Supplier<VALUE> orElse);
    
    public FunctionMap<KEY, VALUE> put(KEY key, VALUE value);

    public Set<KEY> keySet();
    
    public Collection<VALUE> values();
    
    public Set<Entry<KEY, VALUE>> entrySet();

    public FunctionMap<KEY, VALUE> asMap();
    
    // ==

    // Selective map
    // entry map
//    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, 
//                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
//            filterEntries(Predicate<Map.Entry<KEY, VALUE>> entryPredicate);
//    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, 
//                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
//            filterEntries(BiPredicate<KEY, VALUE> entryBiPredicate);
//    public default CollectionAccess<HOST, Collection<Map.Entry<KEY, VALUE>>, Map.Entry<KEY, VALUE>, 
//                    MapEntryAccess<HOST, Map.Entry<KEY, VALUE>, KEY, VALUE, KEYACCESS, VALUEACCESS>> 
//            filter(Predicate<KEY> keyPredicate);
    
}
