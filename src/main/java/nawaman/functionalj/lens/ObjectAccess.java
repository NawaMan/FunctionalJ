package nawaman.functionalj.lens;

import java.util.function.Function;

import nawaman.functionalj.FunctionalJ.Person;

// TODO - Not sure if we still need it.
@FunctionalInterface
public interface ObjectAccess<HOST, TYPE> extends AnyEqualableAccess<HOST, TYPE> {
    
    public default <T> Function<HOST, T> linkTo(Function<TYPE, T> sub) {
        return host->sub.apply(this.apply(host));
    }
    
}
