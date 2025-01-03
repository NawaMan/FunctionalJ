// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.functions;

import java.util.function.Supplier;

import functionalj.environments.Log;
import functionalj.exception.FunctionInvocationException;
import functionalj.function.Func1;
import functionalj.ref.Ref;
import lombok.val;

public interface ThrowFuncs {
    
    public static final Ref<Func1<Exception, RuntimeException>> exceptionTransformer = Ref.ofValue(e -> {
        val throwable = (e instanceof RuntimeException) ? (RuntimeException) e : new FunctionInvocationException(e);
        return throwable;
    });
    
    public static <T extends Throwable> T doThrow(T throwable) throws T {
        throw throwable;
    }
    
    public static <T extends Throwable> T doThrowFrom(Supplier<T> supplier) throws T {
        throw supplier.get();
    }
    
    public static void handleNoThrow(Exception exception) {
        // TODO - Make a ref.
        Log.logErr(exception);
    }
    
    public static void handleThrowRuntime(Exception exception) {
        val throwable = exceptionTransformer.value().apply(exception);
        handleNoThrow(throwable);
        throw throwable;
    }
    // TODO - Add wrap around for some type
    // Umm ... should rename this class or add in Func.carelessly
}
