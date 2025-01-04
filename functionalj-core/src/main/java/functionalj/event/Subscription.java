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
package functionalj.event;

import java.util.concurrent.atomic.AtomicBoolean;
import functionalj.function.Func1;
import functionalj.result.Result;
import lombok.val;

public class Subscription<DATA> {
    
    public static final Cancellation Continue = Cancellation.Continue;
    
    public static final Cancellation Cancel = Cancellation.Cancel;
    
    private final Topic<DATA> topic;
    
    private final Func1<Result<DATA>, Cancellation> subscriber;
    
    private final AtomicBoolean isActive = new AtomicBoolean(true);
    
    public Subscription(Topic<DATA> topic, Func1<Result<DATA>, Cancellation> subscriber) {
        this.topic = topic;
        this.subscriber = subscriber;
    }
    
    public boolean isActive() {
        return isActive.get();
    }
    
    void notifyNext(Result<DATA> next) {
        if (!isActive.get())
            return;
        // TODO - Add scheduling here.
        try {
            val cancellation = subscriber.apply(next);
            if (Subscription.Cancel.equals(cancellation))
                unsubcribe();
        } catch (Exception e) {
            // TODO: handle exception
        }
        next.ifNoMore(() -> {
            isActive.set(false);
            unsubcribe();
        });
    }
    
    public void unsubcribe() {
        if (!isActive.get())
            return;
        this.topic.unsubcribe(this);
        isActive.set(false);
    }
}
