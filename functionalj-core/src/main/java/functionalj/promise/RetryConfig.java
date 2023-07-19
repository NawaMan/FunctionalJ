package functionalj.promise;

import java.util.function.Function;
import functionalj.lens.lenses.AnyAccess;
import functionalj.promise.DeferActionBuilder.RetryBuilderTimes;
import functionalj.promise.DeferActionBuilder.WaitRetryBuilder;
import functionalj.promise.DeferActionBuilder.WaitRetryBuilderUnit;

public class RetryConfig<DATA> implements AnyAccess<DeferActionBuilder<DATA>, RetryBuilderTimes<DATA>> {
    
    private final int retryTimes;
    
    public WaitConfig<DATA> times = new WaitConfig<>(this);
    
    RetryConfig(int times) {
        this.retryTimes = times;
    }
    
    @Override
    public RetryBuilderTimes<DATA> applyUnsafe(DeferActionBuilder<DATA> input) throws Exception {
        return input.retry(retryTimes);
    }
    
    public static class WaitConfig<DATA> implements AnyAccess<DeferActionBuilder<DATA>, WaitRetryBuilder<DATA>> {
    
        private final RetryConfig<DATA> retryConfig;
    
        WaitConfig(RetryConfig<DATA> retryConfig) {
            this.retryConfig = retryConfig;
        }
    
        @Override
        public WaitRetryBuilder<DATA> applyUnsafe(DeferActionBuilder<DATA> input) throws Exception {
            return retryConfig.apply(input).times();
        }
    
        public WaitForConfig<DATA> waitFor(long period) {
            return new WaitForConfig<DATA>(this, period);
        }
    }
    
    public static class WaitForConfig<DATA> implements AnyAccess<DeferActionBuilder<DATA>, WaitRetryBuilderUnit<DATA>> {
    
        private final WaitConfig<DATA> waitConfig;
    
        private final long period;
    
        WaitForConfig(WaitConfig<DATA> waitConfig, long period) {
            this.waitConfig = waitConfig;
            this.period = period;
        }
    
        @Override
        public WaitRetryBuilderUnit<DATA> applyUnsafe(DeferActionBuilder<DATA> input) throws Exception {
            return waitConfig.apply(input).waitFor(period);
        }
    
        public Function<DeferActionBuilder<DATA>, DeferActionBuilder<DATA>> seconds = builder -> this.apply(builder).seconds();
    }
}
