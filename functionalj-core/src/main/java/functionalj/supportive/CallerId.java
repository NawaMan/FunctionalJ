// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.supportive;

import java.util.ArrayList;
import java.util.List;

import functionalj.ref.ProcessBody;


/**
 * Implementation to get the caller.
 * 
 * @author NawaMan -- nawaman@dssb.io
 */
public class CallerId {
    
    /** The default instance of the caller id. */
    public static final CallerId instance = new CallerId();
    
    
    private static ThreadLocal<List<StackTraceElement>> callerTrace
                        = ThreadLocal.withInitial(()->new ArrayList<>(10));
    static {
        callerTrace.get().add(null);
    }
    
    private static StackTraceElement last() {
        var list = callerTrace.get();
        if (list.isEmpty()) {
            callerTrace.get().add(null);
            return null;
        }
        return list.get(0);
    }
    
    /**
     * Run the body and continue (or start) the tracing.
     * 
     * @param   body  the code to run -- the traced element will be passed on as the body parameter.
     * @return  the value returned by the body.
     * @throws T  the exception thrown by the value.
     */
    public <V, T extends Exception> V trace(ProcessBody<StackTraceElement, V, T> body) throws T {
        StackTraceElement  trace   = last();
        boolean isAdded = false;
        if (trace == null) {
            var stackTrace = Thread.currentThread().getStackTrace();
            var length     = stackTrace.length;
            var index      = Math.min(length - 1, 3);
            trace = stackTrace[index];
            
            var list = callerTrace.get();
            list.set(0, trace);
            isAdded = true;
        }
        try {
            return body.process(trace);
        } finally {
            if (isAdded) 
                callerTrace.get().set(0, null);
        }
    }
    
    /**
     * Run the body but pause the tracing.
     * 
     * @param   body  the code to run -- the traced element will be passed on as the body parameter.
     * @return  the value returned by the body.
     * @throws T  the exception thrown by the value.
     */
    public <V, T extends Exception> V tracePause(ProcessBody<StackTraceElement, V, T> body) throws T {
        var trace = last();
        callerTrace.get().add(0, null);
        
        try {
            return body.process(trace);
        } finally {
            callerTrace.get().remove(0);
        }
    }
    
}
