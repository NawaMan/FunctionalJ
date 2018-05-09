package functionalj.lens;

import static functionalj.compose.Functional.pipe;

import java.util.function.Function;

import functionalj.functions.Func1;
import lombok.val;
import nawaman.nullablej.nullable.Nullable;

@FunctionalInterface
public interface AnyLens<HOST, DATA> extends Lens<HOST, DATA>, AnyAccess<HOST, DATA> {
    
    @Override
    public LensSpec<HOST, DATA> lensSpec();
    
    @Override
    default DATA apply(HOST host) {
        return lensSpec().getRead().apply(host);
    }
    
}
