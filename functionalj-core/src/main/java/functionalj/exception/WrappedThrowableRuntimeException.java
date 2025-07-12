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
package functionalj.exception;

/**
 * An {@link RuntimeException} that wrap a {@link Throwable} so that it appears as an {@link RuntimeException}.
 */
public class WrappedThrowableRuntimeException extends RuntimeException {
    
    private static final long serialVersionUID = -5814440055771538679L;
    
    public static WrappedThrowableRuntimeException of(Throwable throwable) {
        if (throwable instanceof WrappedThrowableRuntimeException)
            return (WrappedThrowableRuntimeException) throwable;
        if (throwable instanceof WrappedThrowableException)
            return new WrappedThrowableRuntimeException(((WrappedThrowableException)throwable).getThrowable());
        if (throwable == null) {
            return null;
        }
        return new WrappedThrowableRuntimeException(throwable);
    }
    
    public static RuntimeException runtimeExceptionOf(Throwable throwable) {
    	if (throwable instanceof RuntimeException) {
        	return (RuntimeException)throwable;
    	}
		return WrappedThrowableRuntimeException.of(throwable);
    }
    
    WrappedThrowableRuntimeException(Throwable exception) {
        super(exception);
    }
    
    public WrappedThrowableRuntimeException(String message, Throwable exception) {
        super(message);
    }
    
    public Throwable getThrowable() {
        return this.getCause();
    }
}
