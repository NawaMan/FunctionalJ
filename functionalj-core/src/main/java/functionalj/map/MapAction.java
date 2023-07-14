package functionalj.map;

import java.util.function.BiPredicate;
import java.util.function.Predicate;
import functionalj.function.Func2;

abstract class MapAction<K, S, V> {

    static class With<K, V> extends MapAction<K, V, V> {

        final K key;

        final V value;

        With(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    static class FilterKey<K, V> extends MapAction<K, V, V> {

        final Predicate<? super K> keyCheck;

        FilterKey(Predicate<? super K> keyCheck) {
            this.keyCheck = keyCheck;
        }
    }

    static class FilterBoth<K, V> extends MapAction<K, V, V> {

        final BiPredicate<? super K, ? super V> check;

        FilterBoth(BiPredicate<? super K, ? super V> check) {
            this.check = check;
        }
    }

    static class Mapping<K, S, V> extends MapAction<K, S, V> {

        final Func2<? super K, ? super S, ? extends V> mapper;

        public Mapping(final Func2<? super K, ? super S, ? extends V> mapper) {
            this.mapper = mapper;
        }
    }
}
