package functionalj.types;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

import lombok.val;

public class FunctionalMapStream<KEY, VALUE> extends FunctionalMap<KEY, VALUE> {

    private final FunctionalList<IntTuple<Tuple2<KEY, VALUE>>> entries;
    private final boolean isKeyComparable;
    
    FunctionalMapStream(FunctionalList<IntTuple<Tuple2<KEY, VALUE>>> entries) {
        this.entries         = entries;
        this.isKeyComparable = entries
                .allMatch(entry -> 
                    (entry._2._1 == null) || (entry._2._1 instanceof Comparable));
    }
    
    private Integer calculateHash(Object key) {
        return Optional.ofNullable(key)
                .map   (Object::hashCode)
                .orElse(0);
    }

    @Override
    public int size() {
        return entries.size();
    }
    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }
    
    @Override
    public boolean hasKey(Predicate<? super KEY> keyCheck) {
        return entries.anyMatch(entry -> keyCheck.test(entry._2._1));
    }

    @Override
    public boolean hasValue(Predicate<? super VALUE> valueCheck) {
        return entries.anyMatch(entry -> valueCheck.test(entry._2._2));
    }
    @Override
    public boolean hasKey(KEY key) {
        return containsKey(key);
    }
    
    @Override
    public boolean hasValue(VALUE value) {
        return containsValue(value);
    }

    @Override
    public boolean has(Predicate<? super Entry<KEY, VALUE>> entryCheck) {
        return entries.anyMatch(entry -> entryCheck.test(entry._2));
    }

    @Override
    public boolean has(BiPredicate<? super KEY, ? super VALUE> entryCheck) {
        return entries.anyMatch(entry -> entryCheck.test(entry._2._1, entry._2._2));
    }
    
    @Override
    public boolean containsKey(Object key) {
        val keyHash = calculateHash(key);
        return Streamable.Helper.hasAt(
                entries
                    .filter(entry -> entry._int() == keyHash)
                    .filter(entry -> Objects.equals(key, entry._2._1))
                    .stream(),
                0L);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return Streamable.Helper.hasAt(
                entries
                    .filter(entry -> Objects.equals(value, entry._2._2))
                    .stream(),
                0L);
    }
    
    @Override
    public Optional<VALUE> findBy(KEY key) {
        val keyHash = calculateHash(key);
        return entries
                .filter (entry -> entry._int() == keyHash)
                .filter (entry -> Objects.equals(entry._2._1, key))
                .map    (entry -> entry._2._2)
                .findAny();
    }
    
    @Override
    public VALUE get(Object key) {
        val keyHash = calculateHash(key);
        return entries
                .filter (entry -> entry._int() == keyHash)
                .filter (entry -> Objects.equals(entry._2._1, key))
                .map    (entry -> entry._2._2)
                .findAny()
                .orElse (null);
    }
    
    @Override
    public VALUE getOrDefault(Object key, VALUE orElse) {
        val keyHash = calculateHash(key);
        return entries
                .filter (entry -> entry._int() == keyHash)
                .filter (entry -> Objects.equals(key, entry._2._1))
                .map    (entry -> entry._2._2)
                .findAny()
                .orElse (orElse);
    }
    
    @Override
    public FunctionalList<Tuple2<KEY, VALUE>> selectBy(Predicate<? super KEY> key) {
        val keyHash = calculateHash(key);
        return entries
                .filter(entry -> entry._int() == keyHash)
                .filter(entry -> Objects.equals(key, entry._2._1))
                .map   (entry -> entry._2);
    }
    
    @Override
    public FunctionalList<Tuple2<KEY, VALUE>> selectEntry(Predicate<? super Entry<KEY, VALUE>> entryCheck) {
        return entries
                .filter(entry -> entryCheck.test(entry._2))
                .map   (entry -> entry._2);
    }
    
    @Override
    public FunctionalList<Tuple2<KEY, VALUE>> selectEntry(BiPredicate<? super KEY, ? super VALUE> entryCheck) {
        return entries
                .filter(entry -> entryCheck.test(entry._2._1, entry._2._2))
                .map   (entry -> entry._2);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FunctionalMap<KEY, VALUE> with(KEY key, VALUE value) {
        int keyHash    = calculateHash(key);
        val valueEntry = new Tuple2<KEY, VALUE>(key, value);
        val mapEntry   = new IntTuple<Tuple2<KEY, VALUE>>(keyHash, valueEntry);
        val newList    = entries
                            .filter(entry -> !Objects.equals(key, entry._2._1))
                            .append(mapEntry);
        return new FunctionalMapStream<>(newList);
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries) {
        val newMap = new HashMap<>(this.toMap());
        newMap.putAll(entries);
        return ImmutableMap.of(newMap);
    }

    @Override
    public FunctionalMap<KEY, VALUE> exclude(KEY key) {
        return new FunctionalMapStream<>(
                entries
                    .filter(entry -> !Objects.equals(key, entry._2._1)));
    }

    @Override
    public FunctionalMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck) {
        return new FunctionalMapStream<>(
                entries
                    .filter(entry -> keyCheck.test(entry._2._1)));
    }

    @Override
    public FunctionalMap<KEY, VALUE> filterEntry(Predicate<? super Entry<KEY, VALUE>> entryCheck) {
        return new FunctionalMapStream<>(
                entries
                    .filter(entry -> entryCheck.test(entry._2)));
    }

    @Override
    public FunctionalMap<KEY, VALUE> filterEntry(BiPredicate<? super KEY, ? super VALUE> entryCheck) {
        return new FunctionalMapStream<>(
                entries
                    .filter(entry -> entryCheck.test(entry._2._1, entry._2._2)));
    }

    @Override
    public FunctionalList<KEY> keys() {
        return new FunctionalListStream<>(
                entries.map(each -> each._2._1));
    }

    @Override
    public FunctionalList<VALUE> values() {
        return new FunctionalListStream<>(
                entries.map(each -> each._2._2));
    }

    @Override
    public Set<KEY> keySet() {
        return entries
                .map(each -> each._2._1)
                .collect(toSet());
    }

    @Override
    public Set<Entry<KEY, VALUE>> entrySet() {
        return entries
                .map    (each -> each._2)
                .collect(toSet());
    }
    @Override
    public FunctionalList<Tuple2<KEY, VALUE>> entries() {
        return new FunctionalListStream<>(
                entries.map(each -> each._2));
    }
    
    public Map<KEY, VALUE> toMap() {
        return this;
    }
    
    public ImmutableMap<KEY, VALUE> toImmutableMap() {
        return ImmutableMap.of(this);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FunctionalMap<KEY, VALUE> sorted() {
        if (isKeyComparable) {
            return new FunctionalMapStream<>(
                    entries.sorted((t1,t2)-> {
                        val k1 = t1._2._1;
                        val k2 = t2._2._1;
                        if (k1 == k2)
                            return 0;
                        if (k1 == null)
                            return -1;
                        if (k2 == null)
                            return 1;
                        return ((Comparable)k1).compareTo(k2);
                    }));
        }
        
        return new FunctionalMapStream<>(
                entries
                .sorted((t1,t2)->t1._int() - t2._int()));
    }
    
    public FunctionalMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator) {
        return new FunctionalMapStream<>(
                entries
                .sorted((t1,t2)->comparator.compare(t1._2._1, t2._2._1)));
    }
    
    public <TARGET> FunctionalMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper) {
        return new FunctionalMapStream<>(
                entries
                .map(intTuple -> 
                    new IntTuple<>(intTuple._1, 
                            new Tuple2<>(intTuple._2._1, 
                                    mapper.apply(intTuple._2._2)))));
    }

}
