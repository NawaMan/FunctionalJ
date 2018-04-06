package nawaman.functionalj.lens;

import java.util.function.Function;

import nawaman.functionalj.FunctionalJ.Person;
import nawaman.functionalj.functions.Func1;

@FunctionalInterface
public interface ObjectLens<HOST, DATA> extends AnyEqualableLens<HOST, DATA>, ObjectAccess<HOST, DATA> {
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    public default Func1<HOST, HOST> to(DATA data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }

}
