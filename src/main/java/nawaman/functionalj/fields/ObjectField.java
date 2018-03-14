package nawaman.functionalj.fields;

import java.util.function.Function;

import nawaman.functionalj.FunctionalJ.Person;

public interface ObjectField<HOST, TYPE> extends AnyEqualableField<HOST, TYPE> {

    public default <T> Function<HOST, T> linkTo(Function<TYPE, T> sub) {
        return host->sub.apply(this.apply(host));
    }
    
}
