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

import functionalj.result.Result;
import lombok.val;


public abstract class UncompletedAction<DATA> extends StartableAction<DATA> implements HasPromise<DATA> {
    
    protected final Promise<DATA> promise;
    
    UncompletedAction(OnStart onStart) {
        if (onStart != null) {
            this.promise = new Promise<DATA>(onStart);
        } else {
            this.promise = new Promise<DATA>(this);
        }
    }
    UncompletedAction(Promise<DATA> promise) {
        this.promise = promise;
    }
    
    public final CompletedAction<DATA> abort() {
        promise.abort();
        return new CompletedAction<DATA>(promise);
    }
    public final CompletedAction<DATA> abort(String message) {
        promise.abort(message);
        return new CompletedAction<DATA>(promise);
    }
    public final CompletedAction<DATA> abort(Exception cause) {
        promise.abort(cause);
        return new CompletedAction<DATA>(promise);
    }
    public final CompletedAction<DATA> abort(String message, Exception cause) {
        promise.abort(message, cause);
        return new CompletedAction<DATA>(promise);
    }
    
    // For internal use only -- This will be wrong if the result is not ready.
    final CompletedAction<DATA> completeWith(Result<DATA> result) {
        if (result.isException())
             promise.makeFail    (result.getException());
        else promise.makeComplete(result.get());
        
        return new CompletedAction<DATA>(promise);
    }
    
    public final CompletedAction<DATA> complete(DATA value) {
        promise.makeComplete(value);
        return new CompletedAction<DATA>(promise);
    }
    
    public final CompletedAction<DATA> fail(Exception exception) {
        promise.makeFail(exception);
        return new CompletedAction<DATA>(promise);
    }
    
    public Promise<DATA> getPromise() {
        return promise;
    }
    public final Result<DATA> getCurrentResult() {
        return promise.getCurrentResult();
    }
    public final Result<DATA> getResult() {
        if (!promise.isStarted() && this instanceof DeferAction)
            ((DeferAction<DATA>)this).start();
        
        val result = promise.getResult();
        return result;
    }
    public final Result<DATA> getResult(long timeout, TimeUnit unit) {
        return promise.getResult(timeout, unit);
    }
    
}
