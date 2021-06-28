// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import functionalj.pipeable.Pipeable;
import functionalj.result.Result;

public class CompletedAction<DATA> implements HasPromise<DATA>, Pipeable<HasPromise<DATA>> {
    
    protected final Promise<DATA> promise;
    
    CompletedAction(Promise<DATA> promise) {
        this.promise = promise;
    }
    
    public CompletedAction<DATA> use(Consumer<Promise<DATA>> consumer) {
        if (consumer != null)
            consumer.accept(promise);
        
        return this;
    }
    
    public final Promise<DATA> getPromise() {
        return promise;
    }
    public final Result<DATA> getCurentResult() {
        return promise.getCurrentResult();
    }
    public final Result<DATA> getResult() {
        return promise.getResult();
    }
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        return promise.getResult(timeout, unit);
    }
    
    public HasPromise<DATA> __data() throws Exception {
        return this;
    }
    
}
