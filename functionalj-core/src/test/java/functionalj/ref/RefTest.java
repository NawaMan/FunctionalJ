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
package functionalj.ref;

import static functionalj.list.FuncList.listOf;
import static functionalj.ref.Run.With;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.junit.Test;
import functionalj.environments.Time;
import functionalj.result.Result;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public class RefTest {

    @Test
    public void testNull() {
        val ref = Ref.of(String.class).defaultToNull();
        assertNull(ref.value());
    }

    @Test
    public void testValue() {
        val ref1 = Ref.ofValue("Value");
        assertEquals("Value", ref1.value());
        val ref2 = Ref.ofValue(42);
        assertEquals(42, (int) ref2.value());
    }

    @Test
    public void testFrom() {
        val ref1 = Ref.of(String.class).defaultFrom(() -> "Value");
        assertEquals("Value", ref1.value());
        val ref2 = Ref.of(Integer.class).defaultFrom(() -> 42);
        assertEquals(42, (int) ref2.value());
        val counter = new AtomicInteger();
        val ref3 = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement);
        assertEquals(0, (int) ref3.value());
        assertEquals(1, (int) ref3.value());
        assertEquals(2, (int) ref3.value());
    }

    @Test
    public void testCurrentRef() {
        val ref1 = Ref.of(String.class).defaultFrom(() -> "OrgValue");
        val ref2 = ref1;
        assertEquals("OrgValue", ref2.value());
        Ref.runWith(listOf(ref2.butWith("NewValue")), () -> {
            assertEquals("NewValue", ref2.value());
        });
        assertEquals("OrgValue", ref2.value());
    }

    public static class Answer {

        private final int number;

        public Answer() {
            this(42);
        }

        public Answer(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "Answer [number=" + number + "]";
        }
    }

    @Test
    public void testRefTo() {
        val ref1 = Ref.to(Answer.class);
        val ref2 = ref1;
        assertEquals("Answer [number=42]", "" + ref1.value());
        assertEquals("Answer [number=42]", "" + ref2.value());
        Ref.runWith(listOf(ref1.butWith(new Answer(123)), ref2.butWith(new Answer(123))), () -> {
            assertEquals("Answer [number=123]", "" + ref1.value());
            assertEquals("Answer [number=123]", "" + ref2.value());
        });
        assertEquals("Answer [number=42]", "" + ref1.value());
        assertEquals("Answer [number=42]", "" + ref2.value());
    }

    @Test
    public void testDictate() {
        val ref1 = Ref.ofValue("DICTATE!").dictate();
        val ref2 = Ref.of(String.class).dictateTo("DICTATE!!");
        assertEquals("DICTATE! DICTATE!!", ref1.value() + " " + ref2.value());
        val value = With(ref1.butWith("Weak!")).and(ref2.butWith("Weak!!")).run(() -> ref1.value() + " " + ref2.value());
        assertEquals("DICTATE! DICTATE!!", value);
    }

    @Test
    public void testRefFunction() {
        Ref<Supplier<String>> ref = Ref.ofValue(() -> "Hello world!");
        assertEquals("Hello world!", ref.value().get());
    }

    @Test
    public void testBasicRetain() {
        val counter0 = new AtomicInteger();
        val counter1 = new AtomicInteger();
        val counter2 = new AtomicInteger();
        val ref0 = Ref.of(Integer.class).defaultFrom(counter0::getAndIncrement);
        val ref1 = Ref.of(Integer.class).defaultFrom(counter1::getAndIncrement).retained().forever();
        val ref2 = Ref.of(Integer.class).defaultFrom(counter2::getAndIncrement).retained().never();
        assertEquals(0, ref0.value().intValue());
        assertEquals(1, ref0.value().intValue());
        assertEquals(2, ref0.value().intValue());
        assertEquals(0, ref1.value().intValue());
        assertEquals(0, ref1.value().intValue());
        assertEquals(0, ref1.value().intValue());
        assertEquals(0, ref2.value().intValue());
        assertEquals(1, ref2.value().intValue());
        assertEquals(2, ref2.value().intValue());
    }

    @Test
    public void testRetainSame() {
        val state = new AtomicInteger(42);
        val counter = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().when(refState).same();
        assertEquals(42, state.get());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        state.incrementAndGet();
        assertEquals(43, state.get());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        state.incrementAndGet();
        assertEquals(44, state.get());
        assertEquals(2, ref.value().intValue());
        assertEquals(2, ref.value().intValue());
        state.decrementAndGet();
        assertEquals(43, state.get());
        assertEquals(3, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
        // NOTE: Since Integer caches from -128 to 128, 1024 will always be different objects,
        // thus, it appears to always be different objects when state is check.
        state.set(1024);
        assertEquals(1024, state.get());
        assertEquals(4, ref.value().intValue());
        assertEquals(5, ref.value().intValue());
        assertEquals(6, ref.value().intValue());
    }

    @Test
    public void testRetainEquals() {
        val state = new AtomicInteger(42);
        val counter = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().when(refState).equals();
        assertEquals(42, state.get());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        state.incrementAndGet();
        assertEquals(43, state.get());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        state.incrementAndGet();
        assertEquals(44, state.get());
        assertEquals(2, ref.value().intValue());
        assertEquals(2, ref.value().intValue());
        state.decrementAndGet();
        assertEquals(43, state.get());
        assertEquals(3, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
        // NOTE: Since Integer caches from -128 to 128, 1024 will always be different objects,
        // but they are considered equals, thus, it appears to always be different objects
        // but equals to each other so appears to be unchanged when state is check.
        state.set(1024);
        assertEquals(1024, state.get());
        assertEquals(4, ref.value().intValue());
        assertEquals(4, ref.value().intValue());
        assertEquals(4, ref.value().intValue());
    }

    @Test
    public void testRetainMatch() {
        val state = new AtomicInteger(42);
        val counter = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().when(refState).match((Integer s) -> s.intValue() == 42);
        assertEquals(42, state.get());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        state.set(43);
        assertEquals(43, state.get());
        assertEquals(1, ref.value().intValue());
        assertEquals(2, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
        state.set(42);
        assertEquals(42, state.get());
        assertEquals(3, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
        assertEquals(3, ref.value().intValue());
    }

    @Test
    public void testRetainPeriod() throws InterruptedException {
        val counter = new AtomicInteger(0);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().withIn(50).milliSeconds();
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        Thread.sleep(50);
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
    }

    @Test
    public void testRetain_crossThread() throws InterruptedException {
        val state = new AtomicInteger(42);
        val counter = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().locally().when(refState).equals();
        assertEquals(42, state.get());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        state.incrementAndGet();
        assertEquals(43, state.get());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        val resultRef = new AtomicReference<String>();
        Run.async(() -> {
            Time.sleep(10);
            val value1 = ref.value();
            Time.sleep(20);
            val value2 = ref.value();
            Time.sleep(100);
            val value3 = ref.value();
            resultRef.set(value1 + " - " + value2 + " - " + value3);
        });
        Time.sleep(50);
        state.incrementAndGet();
        assertEquals(44, state.get());
        assertEquals(3, ref.value().intValue());
        Time.sleep(200);
        assertEquals("2 - 2 - 4", resultRef.toString());
    }

    // TODO - Fix this. :-(
    // @Ignore("Fail test, need fix first.")
    @Test
    public void testRetain_localThread() throws InterruptedException {
        val state = new ThreadLocal<Integer>();
        val counter = new AtomicInteger(0);
        val refState = Ref.of(Integer.class).defaultFrom(state::get);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).retained().locally().when(refState).equals();
        state.set(42);
        assertEquals(42, state.get().intValue());
        assertEquals(0, ref.value().intValue());
        assertEquals(0, ref.value().intValue());
        state.set(state.get() + 1);
        assertEquals(43, state.get().intValue());
        assertEquals(1, ref.value().intValue());
        assertEquals(1, ref.value().intValue());
        val asyncResultRef = new AtomicReference<Result<String>>();
        Run.async(() -> {
            state.set(42);
            return IntStreamPlus.infinite().limit(5).peek(i -> Time.sleep(40)).map(i -> ref.value()).join(" - ");
        }).onComplete(r -> asyncResultRef.set(r));
        Time.sleep(100);
        for (int i = 0; i < 5; i++) {
            // value in the local thread change as we change the state ... but retained when the value stay.
            Time.sleep(40);
            state.set(state.get() + 1);
            assertEquals(44 + i, state.get().intValue());
            assertEquals(3 + i, ref.value().intValue());
            assertEquals(3 + i, ref.value().intValue());
            Time.sleep(10);
            assertEquals(3 + i, ref.value().intValue());
            assertEquals(3 + i, ref.value().intValue());
        }
        // Async result stay the same the whole time
        assertEquals("2 - 2 - 2 - 2 - 2", asyncResultRef.get().value());
    }

    @Test
    public void testMap() {
        val counter = new AtomicInteger(0);
        val ref = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).map(String.class, String::valueOf);
        val supplier = Ref.of(Integer.class).defaultFrom(counter::getAndIncrement).then(String::valueOf);
        assertEquals("0", ref.value());
        assertEquals("1", ref.value());
        assertEquals("2", ref.value());
        assertEquals("3", supplier.get());
        assertEquals("4", supplier.get());
        assertEquals("5", supplier.get());
    }
}
