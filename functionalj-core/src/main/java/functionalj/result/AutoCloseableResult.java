package functionalj.result;

import static functionalj.function.Func.f;
import functionalj.function.Func0;

public class AutoCloseableResult<DATA extends AutoCloseable> extends DerivedResult<DATA> implements AutoCloseable {

    public static <D extends AutoCloseable> AutoCloseableResult<D> valueOf(D value) {
        return new AutoCloseableResult<D>(() -> value);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <D extends AutoCloseable> AutoCloseableResult<D> from(Result<D> result) {
        if (result instanceof AutoCloseableResult)
            return (AutoCloseableResult) result;
        return new AutoCloseableResult(() -> result.get());
    }

    AutoCloseableResult(Func0<DATA> dataSupplier) {
        super(dataSupplier);
    }

    @Override
    public void close() throws Exception {
        ifPresent(f(value -> {
            value.close();
        }));
    }
}
