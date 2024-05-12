// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
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
