// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.function.Func.getOrElse;

import java.util.function.Supplier;

import functionalj.environments.AsyncRunner;
import functionalj.function.Func0;
import functionalj.result.Result;
import lombok.val;


public abstract class WaitAwhile extends Wait {
    
    @SuppressWarnings("rawtypes")
    public static final Supplier<Result> CANCELLATION_RESULT = ()->Result.ofCancelled();
    
    
    protected WaitAwhile() {}
    
    
    public <DATA> WaitOrDefault<DATA> orDefaultTo(DATA data) {
        return new WaitOrDefault<>(this, ()->Result.valueOf(data));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultFrom(Func0<DATA> supplier) {
        return new WaitOrDefault<>(this, ()->Result.of(supplier));
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultGet(Func0<Result<DATA>> supplier) {
        return new WaitOrDefault<>(this, supplier);
    }
    
    public <DATA> WaitOrDefault<DATA> orDefaultResult(Result<DATA> result) {
        return new WaitOrDefault<>(this, ()->result);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <DATA> WaitOrDefault<DATA> orCancel() {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)(Supplier)CANCELLATION_RESULT);
    }
    
    // TODO - from Exception class.
    public <DATA> WaitOrDefault<DATA> orException(Exception exception) {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)()->Result.ofException(exception));
    }
    public <DATA> WaitOrDefault<DATA> orException(Supplier<Exception> exceptionSupplier) {
        return new WaitOrDefault<>(this, (Supplier<Result<DATA>>)()->Result.ofException(getOrElse(exceptionSupplier, null)));
    }
    
    //==  Basic sub class ==
    
    public static class WaitAsync extends WaitAwhile {
        
        private final long        time;
        private final AsyncRunner asyncRunner;
        
        public WaitAsync(long time) {
            this(time, null);
        }
        public WaitAsync(long time, AsyncRunner asyncRunner) {
            this.time = Math.max(0, time);
            this.asyncRunner = asyncRunner;
            
        }
        
        @Override
        public WaitSession newSession() {
            val session = new WaitSession();
            AsyncRunner.run(asyncRunner, ()->{
                // TODO - Once scheduling is available, use it.
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // Allow interruption.
                }
                session.expire(null, null);
            });
            return session;
        }
        
    }
    
}
