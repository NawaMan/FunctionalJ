package functionalj.lens;

import static functionalj.compose.Functional.pipe;

import java.util.function.Function;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface ObjectLens<HOST, DATA> extends AnyLens<HOST, DATA>, ObjectAccess<HOST, DATA> {
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    public default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    public default Func1<HOST, HOST> changeTo(DATA data) {
        return host -> lensSpec().getWrite().apply(host, data);
    }
    
}
