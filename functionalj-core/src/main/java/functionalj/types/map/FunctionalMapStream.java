package functionalj.types.map;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

import functionalj.types.list.FunctionalList;
import functionalj.types.list.FunctionalListStream;
import functionalj.types.stream.Streamable;
import functionalj.types.stream.Streamable.Helper;
import lombok.val;
import tuple.ImmutableTuple2;
import tuple.IntTuple2;

public class FunctionalMapStream<KEY, VALUE> extends FunctionalMap<KEY, VALUE> {
    
    private static final Supplier<Stream<IntTuple2<ImmutableTuple2<?, ?>>>> EmptyStreamSupplier = ()->Stream.empty();

    private final FunctionalList<IntTuple2<ImmutableTuple2<KEY, VALUE>>> entries;
    private final boolean isKeyComparable;
    
    FunctionalMapStream(Boolean isKeyComparable, FunctionalList<IntTuple2<ImmutableTuple2<KEY, VALUE>>> entries) {
        // TODO - Thinking about sorting by hash to take advantage of binary search
        // TODO - Ensure that the key is unique!!!
        this.entries         = entries;
        this.isKeyComparable = (isKeyComparable != null)
                ? isKeyComparable.booleanValue()
                : entries.allMatch(entry -> 
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
                .map    (entry -> entry._2()._2())
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
    public FunctionalList<VALUE> select(Predicate<? super KEY> keyPredicate) {
        return entries
                .filter(entry -> keyPredicate.test(entry._2._1))
                .map   (entry -> entry._2._2);
    }
    
    @Override
    public FunctionalList<ImmutableTuple2<KEY, VALUE>> selectEntry(Predicate<? super KEY> keyPredicate) {
        return entries
                .filter(entry -> keyPredicate.test(entry._2._1))
                .map   (entry -> entry._2);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public FunctionalMap<KEY, VALUE> with(KEY key, VALUE value) {
        // Find the way to put in it in the same location.
        int keyHash    = calculateHash(key);
        val valueEntry = new ImmutableTuple2<KEY, VALUE>(key, value);
        val mapEntry   = new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, valueEntry);
        val newEntries = entries
                            .filter(entry -> !Objects.equals(key, entry._2._1))
                            .append(mapEntry);
        val newIsKeyComparable = isKeyComparable && ((key == null) || (key instanceof Comparable));
        return new FunctionalMapStream<>(newIsKeyComparable, newEntries);
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> withAll(Map<? extends KEY, ? extends VALUE> entries) {
        val newMap = new HashMap<>(this.toMap());
        newMap.putAll(entries);
        return ImmutableMap.of(newMap);
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> defaultTo(KEY key, VALUE value) {
        return defaultBy(key, oldValue -> value);
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> defaultBy(KEY key, Supplier<VALUE> valueSupplier) {
        return defaultBy(key, oldValue -> valueSupplier.get());
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> defaultBy(KEY key, Function<KEY, VALUE> valueFunction) {
        int keyHash = calculateHash(key);
        val newIsKeyComparable = isKeyComparable && ((key == null) || (key instanceof Comparable));
        return new FunctionalMapStream<KEY, VALUE>(newIsKeyComparable, FunctionalListStream.of(() -> {
                AtomicReference<Supplier<Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>>> ref = new AtomicReference<>(()->{
                        val valueEntry = new ImmutableTuple2<KEY, VALUE>(key, valueFunction.apply(key));
                        val mapEntry   = new IntTuple2<ImmutableTuple2<KEY, VALUE>>(keyHash, valueEntry);
                        return Stream.of(mapEntry);
                    });
                    @SuppressWarnings({ "unchecked", "rawtypes" })
                    val main = new AtomicReference<Supplier<Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>>>(()->{
                        return entries.filter(entry -> {
                            boolean found = (entry._1 == keyHash) && Objects.equals(key, entry._2._1);
                            if (found) {
                                ref.set((Supplier<Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>>)(Supplier)EmptyStreamSupplier);
                            }
                            return true;
                        }).stream();
                    });
                    return (Stream<IntTuple2<ImmutableTuple2<KEY, VALUE>>>)Stream.of(main, ref).
                            flatMap(each -> each.get().get());
                }));
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> defaultTo(Map<? extends KEY, ? extends VALUE> entries) {
        return null;
    }

    @Override
    public FunctionalMap<KEY, VALUE> exclude(KEY key) {
        return new FunctionalMapStream<>(isKeyComparable,
                entries
                    .filter(entry -> !Objects.equals(key, entry._2._1)));
    }

    @Override
    public FunctionalMap<KEY, VALUE> filter(Predicate<? super KEY> keyCheck) {
        return new FunctionalMapStream<>(isKeyComparable, 
                entries
                    .filter(entry -> keyCheck.test(entry._2._1)));
    }

    @Override
    public FunctionalMap<KEY, VALUE> filter(BiPredicate<? super KEY, ? super VALUE> entryCheck) {
        return new FunctionalMapStream<>(isKeyComparable, 
                entries
                    .filter(entry -> entryCheck.test(entry._2._1, entry._2._2)));
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> filterByEntry(Predicate<Entry<? super KEY, ? super VALUE>> entryCheck) {
        return new FunctionalMapStream<>(isKeyComparable, 
                entries
                    .filter(entry -> entryCheck.test(entry._2)));
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
    public FunctionalList<ImmutableTuple2<KEY, VALUE>> entries() {
        return new FunctionalListStream<>(
                entries.map(each -> each._2));
    }
    
    @Override
    public Map<KEY, VALUE> toMap() {
        return this;
    }
    
    @Override
    public ImmutableMap<KEY, VALUE> toImmutableMap() {
        return ImmutableMap.of(this);
    }
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FunctionalMap<KEY, VALUE> sorted() {
        if (isKeyComparable) {
            return new FunctionalMapStream<>(isKeyComparable, 
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
        
        return new FunctionalMapStream<>(isKeyComparable, 
                entries
                .sorted((t1,t2)->t1._int() - t2._int()));
    }
    
    @Override
    public FunctionalMap<KEY, VALUE> sorted(Comparator<? super KEY> comparator) {
        return new FunctionalMapStream<>(isKeyComparable, 
                entries
                .sorted((t1,t2)->comparator.compare(t1._2._1, t2._2._1)));
    }
    
    @Override
    public <TARGET> FunctionalMap<KEY, TARGET> map(Function<? super VALUE, ? extends TARGET> mapper) {
        return new FunctionalMapStream<>(isKeyComparable, 
                entries
                .map(intTuple -> 
                    new IntTuple2<>(intTuple._1, 
                            new ImmutableTuple2<>(intTuple._2._1, 
                                    mapper.apply(intTuple._2._2)))));
    }
    
    @Override
    public void forEach(BiConsumer<? super KEY, ? super VALUE> action) {
        entries
            .map(entry -> entry._2)
            .forEach(entry -> action.accept(entry._1, entry._2));
    }
    
    @Override
    public void forEach(Consumer<? super Map.Entry<? super KEY, ? super VALUE>> action) {
        entries
        .map(entry -> entry._2)
        .forEach(entry -> action.accept(entry));
    }

}
