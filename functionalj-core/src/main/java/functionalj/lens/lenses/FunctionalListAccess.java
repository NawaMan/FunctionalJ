package functionalj.lens.lenses;

import static functionalj.lens.core.AccessUtils.createSubFunctionalListAccess;
import static functionalj.lens.lenses.FunctionalListAccess.__internal__.subList;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.lens.core.AccessParameterized;
import functionalj.types.list.FunctionalList;
import functionalj.types.stream.Streamable;
import lombok.val;

@FunctionalInterface
public interface FunctionalListAccess<HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> 
        extends CollectionAccess<HOST, FunctionalList<TYPE>, TYPE, TYPEACCESS> {
    
    // :-( .. have to be duplicate
    public default TYPEACCESS first() {
        return at(0);
    }
    public default TYPEACCESS last() {
        return accessParameterized()
                .createSubAccess((FunctionalList<TYPE> list) -> {
                    if (list == null)
                        return null;
                    if (list.isEmpty())
                        return null;
                    return list.get(list.size() - 1);
                });
    }
    public default TYPEACCESS at(int index) {
        return accessParameterized().createSubAccess((FunctionalList<TYPE> list) -> {
            if (list == null)
                return null;
            if (list.isEmpty())
                return null;
            if (index < 0) 
                return null;
            if (index >= list.size())
                return null;
            return list.get(index);
        });
    }
    public default TYPEACCESS get(int index) {
        return at(index);
    }
    public default IntegerAccess<HOST> indexOf(Object o) {
        return intAccess(-1, list -> list.indexOf(o));
    }
    public default IntegerAccess<HOST> lastIndexOf(Object o) {
        return intAccess(-1, list -> list.lastIndexOf(o));
    }
    
    public default FunctionalListAccess<HOST, Integer, IntegerAccess<HOST>> indexesOf(Predicate<? super TYPE> check) {
        val access  = new AccessParameterized<HOST, FunctionalList<Integer>, Integer, IntegerAccess<HOST>>() {
            @Override
            public FunctionalList<Integer> applyUnsafe(HOST host) throws Exception {
                return FunctionalListAccess.this.apply(host).indexesOf(check);
            }
            @Override
            public IntegerAccess<HOST> createSubAccessFromHost(Function<HOST, Integer> accessToParameter) {
                return host -> accessToParameter.apply(host);
            }
        };
        return () -> access;
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> rest() {
        return subList(this, host -> {
            return apply(host)
                    .rest();
        });
    }
    
    // map
    // flatMap
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> filter(Predicate<TYPE> checker) {
        return subList(this, host -> {
            return apply(host)
                    .filter(checker);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> filter(TYPE data) {
        return subList(this, host -> {
            return apply(host)
                    .filter(data);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> append(TYPE ... data) {
        return subList(this, host -> {
            return apply(host)
                    .append(data);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> appendAll(Collection<? extends TYPE> collection) {
        return subList(this, host -> {
            return apply(host)
                    .appendAll(collection);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> appendAll(Streamable<? extends TYPE, ?> streamable) {
        return subList(this, host -> {
            return apply(host)
                    .appendAll(streamable);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> appendAll(Supplier<Stream<? extends TYPE>> supplier) {
        return subList(this, host -> {
            return apply(host)
                    .appendAll(supplier);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> with(int index, TYPE value) {
        return subList(this, host -> {
            return apply(host)
                    .with(index, value);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> insertAt(int index, TYPE ... elements) {
        return subList(this, host -> {
            return apply(host)
                    .insertAt(index, elements);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> insertAllAt(int index, Collection<? extends TYPE> collection) {
        return subList(this, host -> {
            return apply(host)
                    .insertAllAt(index, collection);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> insertAllAt(int index, Streamable<? extends TYPE, ?> streamable) {
        return subList(this, host -> {
            return apply(host)
                    .insertAllAt(index, streamable);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> excludeAt(int index) {
        return subList(this, host -> {
            return apply(host)
                    .excludeAt(index);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> excludeFrom(int fromIndexInclusive, int count) {
        return subList(this, host -> {
            return apply(host)
                    .excludeFrom(fromIndexInclusive, count);
        });
    }
    
    public default FunctionalListAccess<HOST, TYPE, TYPEACCESS> excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        return subList(this, host -> {
            return apply(host)
                    .excludeBetween(fromIndexInclusive, toIndexExclusive);
        });
    }
    
    // groupingBy
    // toMap
    
    public static class __internal__ {

        public static <HOST, TYPE, TYPEACCESS extends AnyAccess<HOST, TYPE>> 
                        FunctionalListAccess<HOST, TYPE, TYPEACCESS> subList(
                                FunctionalListAccess<HOST, TYPE, TYPEACCESS> lens,
                                Function<HOST, FunctionalList<TYPE>> read) {
            return createSubFunctionalListAccess(lens.accessParameterized(), read);
        }
        
    }
    
}
