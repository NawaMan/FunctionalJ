//  ========================================================================
//  Copyright (c) 2017 Direct Solution Software Builders (DSSB).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.supportive;

import java.util.ArrayList;
import java.util.List;

import functionalj.ref.ProcessBody;
import lombok.val;

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
        val list = callerTrace.get();
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
            val stackTrace = Thread.currentThread().getStackTrace();
            val length     = stackTrace.length;
            val index      = Math.min(length - 1, 3);
            trace = stackTrace[index];
            
            val list = callerTrace.get();
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
        val trace = last();
        callerTrace.get().add(0, null);
        
        try {
            return body.process(trace);
        } finally {
            callerTrace.get().remove(0);
        }
    }
    
}
