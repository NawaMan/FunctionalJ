// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.environments.AsyncRunner;

public abstract class Wait {
    
    public static WaitForever forever() {
        return WaitForever.instance;
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds) {
        return new WaitAwhile.WaitAsync(milliseconds);
    }
    
    public static WaitAwhile forSeconds(long seconds) {
        return new WaitAwhile.WaitAsync(seconds * 1000);
    }
    
    public static WaitAwhile forMilliseconds(long milliseconds, AsyncRunner asyncRunner) {
        return new WaitAwhile.WaitAsync(milliseconds, asyncRunner);
    }
    
    public static WaitAwhile forSeconds(long seconds, AsyncRunner asyncRunner) {
        return new WaitAwhile.WaitAsync(seconds * 1000, asyncRunner);
    }
    
    Wait() {
    }
    
    public abstract WaitSession newSession();
    
    protected final void expire(WaitSession session, String message, Exception throwable) {
        session.expire(message, throwable);
    }
}
