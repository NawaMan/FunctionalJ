package functionalj.tuple;

import java.util.Map;

import functionalj.function.Func1;

@FunctionalInterface
public interface ToMapFunc<D, K, V> extends Func1<D, Map<K, V>> {

}
