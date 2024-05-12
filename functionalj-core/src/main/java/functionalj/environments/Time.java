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
package functionalj.environments;

import functionalj.InterruptedRuntimeException;

public final class Time {
    
    private Time() {
    }
    
    public static long currentMilliSecond() {
        return Env.time().currentMilliSecond();
    }
    
    public static void sleep(long millisecond) {
        Env.time().sleep(millisecond);
    }
    
    public static interface Instance {
        
        public long currentMilliSecond();
        
        public void sleep(long millisecond);
    }
    
    public static class System implements Instance {
        
        public static final Time.Instance instance = new Time.System();
        
        // private final ScheduledExecutorService scheduler =
        // Executors.newScheduledThreadPool(1);
        public long currentMilliSecond() {
            return java.lang.System.currentTimeMillis();
        }
        
        public void sleep(long millisecond) {
            try {
                Thread.sleep(millisecond);
            } catch (InterruptedException e) {
                throw new InterruptedRuntimeException(e);
            }
        }
        // public <EXECUTION extends Exception>
        // Promise<Object> schedule(RunBody<EXECUTION> callable, long delay, TimeUnit unit) {
        // }
        // public <DATA, EXECUTION extends Exception>
        // Promise<Object> schedule(ComputeBody<DATA, EXECUTION> callable, long delay, TimeUnit unit) {
        // }
        // Schedule that start with absolute time
        // Schedule periodic tasks.
    }
    // TODO - Implement that allow fake
}
