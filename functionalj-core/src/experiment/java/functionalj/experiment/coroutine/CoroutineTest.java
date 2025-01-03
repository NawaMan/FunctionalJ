// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.experiment.coroutine;

import static functionalj.experiment.coroutine.CoroutineTest.CoroutineEntry.c;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.function.Func;
import functionalj.function.Func0;
import functionalj.function.Func1;
import functionalj.map.FuncMap;
import functionalj.promise.Promise;

// This is an experiment to see if we can simulate coroutine the kind that help with async/await
public class CoroutineTest {
    
    public static abstract class CoroutineEntry<IN, OUT> {
        
        public abstract OUT get();
        
        public static <IN, MIDDLE, OUT> CoroutineEntry<IN, OUT> c(Func0<MIDDLE> f, Func1<MIDDLE, CoroutineEntry<MIDDLE, OUT>> next) {
            return new CoroutineBetweenEntry<IN, MIDDLE, OUT>(f, next);
        }
        
        public static <IN, OUT> CoroutineEntry<IN, OUT> c(Func0<OUT> supplier) {
            return new CoroutineLastEntry<IN, OUT>(supplier);
        }
    }
    
    public static class CoroutineLastEntry<IN, OUT> extends CoroutineEntry<IN, OUT> {
        
        private final Func0<OUT> f;
        
        public CoroutineLastEntry(Func0<OUT> f) {
            this.f = f;
        }
        
        public OUT get() {
            return f.get();
        }
    }
    
    public static class CoroutineBetweenEntry<IN, MID, OUT> extends CoroutineEntry<IN, OUT> {
        
        private final Func0<MID> f;
        
        private final Func1<MID, CoroutineEntry<MID, OUT>> n;
        
        public CoroutineBetweenEntry(Func0<MID> f, Func1<MID, CoroutineEntry<MID, OUT>> n) {
            this.f = f;
            this.n = n;
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public OUT get() {
            CoroutineBetweenEntry btEntry = this;
            while (true) {
                val fValue = btEntry.f.get();
                val newCr = btEntry.n.apply(fValue);
                if (newCr instanceof CoroutineBetweenEntry)
                    btEntry = (CoroutineBetweenEntry) newCr;
                else
                    return (OUT) ((CoroutineLastEntry) newCr).get();
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        val c1 = c(() -> "One");
        assertEquals("One", c1.get());
        val c2 = c(() -> "One", one -> c(() -> one.length(), two -> c(() -> two + 1)));
        assertEquals(4, c2.get());
        val m = FuncMap.of(1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five");
        val f1 = Func.F(String::length);
        val f2 = Func.F((Integer i) -> (String) m.get(i));
        val f1d = Func.F(String::length).defer();
        val f2d = Func.F((Integer i) -> (String) m.get(i)).defer();
        val c3 = c(() -> Promise.ofValue("One"), one -> c(() -> one.map(f1), two -> c(() -> two.map(f2), three -> c(() -> three.map(f1)))));
        assertEquals("Result:{ Value: 5 }", ((Promise<Integer>) c3.get()).getResult().toString());
        val c4 = c(() -> Promise.ofValue("One"), (Promise<String> one) -> c(() -> f1d.apply(one), (Promise<Integer> two) -> c(() -> f2d.apply(two), (Promise<String> three) -> c(() -> three.map(f1)))));
        assertEquals("Result:{ Value: 5 }", c4.get().getResult().toString());
        val c5 = c(() -> Promise.ofValue("One"), (Promise<String> one) -> c(() -> f1d.apply(one), (Promise<Integer> two) -> c(() -> f2d.apply(two), (Promise<String> three) -> c(() -> three.map(f1)))));
        assertEquals("Result:{ Value: 5 }", c5.get().getResult().toString());
    }
}
