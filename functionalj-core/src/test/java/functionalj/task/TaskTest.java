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
package functionalj.task;

import static functionalj.function.Func.f;
import static functionalj.lens.Access.$S;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import functionalj.function.Func;
import functionalj.promise.DeferAction;
import functionalj.promise.DeferActionBuilder;
import functionalj.result.Result;
import lombok.val;

// @Ignore("Still has problems")
public class TaskTest {
    
    @Test
    public void testValue() {
        val task = Task.ofValue("Hello!");
        val action = task.createAction();
        assertEquals("Result:{ Value: Hello! }", action.getResult().toString());
    }
    
    @Test
    public void testSupplier() {
        val counter = new AtomicInteger(0);
        val task = Task.from(counter::getAndIncrement);
        val action1 = task.createAction();
        val action2 = task.createAction();
        assertEquals("Result:{ Value: 0 }", action1.getResult().toString());
        assertEquals("Result:{ Value: 0 }", action1.getResult().toString());
        assertEquals("Result:{ Value: 1 }", action2.getResult().toString());
        assertEquals("Result:{ Value: 1 }", action2.getResult().toString());
    }
    
    @Test
    public void testResult() {
        val task = Task.from(Result.ofNotExist());
        assertEquals("Result:{ NotExist }", task.createAction().getResult().toString());
        assertEquals("Result:{ NotExist }", task.createAction().getResult().toString());
    }
    
    @Test
    public void testPromise() {
        val counter = new AtomicInteger(0);
        val action = DeferAction.from(counter::getAndIncrement);
        val task = Task.from(action.getPromise());
        assertEquals("Result:{ Value: 0 }", task.createAction().getResult().toString());
        assertEquals("Result:{ Value: 0 }", task.createAction().getResult().toString());
    }
    
    @Test
    public void testMap() {
        val logs = new ArrayList<String>();
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val action2 = action.map(f("prefix-with-dash", "-"::concat)).map(f("suffix-with-dash", $S.concat("-")));
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        assertEquals("[" + // + "Action1 runs!, A, Result: Result:{ Value: -A- }"
        "Action1 runs!, A, Result: Result:{ Value: -A- }, " + "Action1 runs!, B, Result: Result:{ Value: -B- }, " + "Action1 runs!, C, Result: Result:{ Value: -C- }, " + "Action1 runs!, D, Result: Result:{ Value: -D- }" + "]", logs.toString());
        assertEquals("Task#F0::Action1", action.toString());
        assertEquals("Task#F0::Action1" + ".map(F1::prefix-with-dash)" + ".map(F1::suffix-with-dash)", action2.toString());
    }
    
    @Test
    public void testFlatMap() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val action1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val action2 = action1.flatMap(f("FM", (String t) -> {
            return DeferActionBuilder.from(f("Action2", () -> {
                logs.add("Action2 runs!");
                String s = t + " - " + (char) ('a' + counter2.getAndIncrement());
                logs.add(s);
                return s;
            }));
        }));
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        assertEquals("[" + "Action1 runs!, A, Action2 runs!, A - a, Result: Result:{ Value: A - a }, " + "Action1 runs!, B, Action2 runs!, B - b, Result: Result:{ Value: B - b }, " + "Action1 runs!, C, Action2 runs!, C - c, Result: Result:{ Value: C - c }" + "]", logs.toString());
        assertEquals("Task#F0::Action1", action1.toString());
        assertEquals("Task#F0::Action1.chain(F1::FM)", action2.toString());
    }
    
    @Test
    public void testFilter() {
        val logs = new ArrayList<String>();
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder.from(() -> counter.getAndIncrement()).filter(i -> (i % 2) == 0);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("[" + "Result: Result:{ Value: 0 }, " + "Result: Result:{ Value: null }, " + "Result: Result:{ Value: 2 }, " + "Result: Result:{ Value: null }, " + "Result: Result:{ Value: 4 }, " + "Result: Result:{ Value: null }" + "]", logs.toString());
    }
    
    @Test
    public void testMapError() {
        val logs = new ArrayList<String>();
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder.from(() -> {
            logs.add("Action1 runs!");
            int count = counter.getAndIncrement();
            if (count % 2 == 0)
                throw new IllegalArgumentException("Count: " + count);
            String s = "" + (char) ('A' + count);
            logs.add(s);
            return s;
        });
        val action2 = action.map("-"::concat).map($S.concat("-"));
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        assertEquals("[" + "Action1 runs!, Result: Result:{ Exception: java.lang.IllegalArgumentException: Count: 0 }, " + "Action1 runs!, B, Result: Result:{ Value: -B- }, " + "Action1 runs!, Result: Result:{ Exception: java.lang.IllegalArgumentException: Count: 2 }, " + "Action1 runs!, D, Result: Result:{ Value: -D- }" + "]", logs.toString());
    }
    
    @Test
    public void testReuseable() {
        val logs = new ArrayList<String>();
        val ref = new AtomicInteger(0);
        val counter = new AtomicInteger(0);
        val action = DeferActionBuilder.from(() -> {
            logs.add("Action1 runs!");
            int count = counter.getAndIncrement();
            String s = "" + (char) ('A' + count);
            logs.add(s);
            return s;
        }).cached(ref::get);
        val action2 = action.map("-"::concat).map($S.concat("-"));
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        ref.incrementAndGet();
        logs.add("Result: " + action2.createAction().getResult());
        logs.add("Result: " + action2.createAction().getResult());
        assertEquals("Action1 runs!,\n" + "A,\n" + "Result: Result:{ Value: -A- },\n" + "Result: Result:{ Value: -A- },\n" + "Action1 runs!,\n" + "B,\n" + "Result: Result:{ Value: -B- },\n" + "Result: Result:{ Value: -B- }", logs.stream().collect(joining(",\n")));
    }
    
    @Test
    public void testMerge() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            Thread.sleep(10);
            logs.add("Action2 runs!");
            String s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val merger = f("merge", (String s1, String s2) -> s1 + "-" + s2);
        val action = Task.from(task1, task2, merger);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("F2::merge(Task#F0::Action1, Task#F0::Action2)", action.toString());
        assertEquals("[" + "Action1 runs!, A, Action2 runs!, a, Result: Result:{ Value: A-a }, " + "Action1 runs!, B, Action2 runs!, b, Result: Result:{ Value: B-b }, " + "Action1 runs!, C, Action2 runs!, c, Result: Result:{ Value: C-c }" + "]", logs.toString());
    }
    
    @Test
    public void testMerge_ioUsedMultipleTime() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            Thread.sleep(50);
            logs.add("Action2 runs!");
            String s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val merger = f("merge", (String s1, String s2) -> s1 + "-" + s2);
        val action = Task.from(task1, Task.from(task1, task2, merger), merger);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("F2::merge(Task#F0::Action1, F2::merge(Task#F0::Action1, Task#F0::Action2))", action.toString());
        assertEquals("[" + "Action1 runs!, A, Action1 runs!, B, Action2 runs!, a, Result: Result:{ Value: A-B-a }, " + "Action1 runs!, C, Action1 runs!, D, Action2 runs!, b, Result: Result:{ Value: C-D-b }, " + "Action1 runs!, E, Action1 runs!, F, Action2 runs!, c, Result: Result:{ Value: E-F-c }" + "]", logs.toString());
    }
    
    @Test
    public void testMerge3_reusable() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            Thread.sleep(10);
            logs.add("Action2 runs!");
            String s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val w1 = task1.cached();
        val merger = f("merge", (String s1, String s2) -> s1 + "-" + s2);
        val action = Task.from(w1, Task.from(w1, task2, merger), merger);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("F2::merge(Task#F0::Action1.cached(), F2::merge(Task#F0::Action1.cached(), Task#F0::Action2))", action.toString());
        assertEquals("[" + "Action1 runs!, A, " + "Action2 runs!, a, " + "Result: Result:{ Value: A-A-a }, " + "Action2 runs!, b, " + "Result: Result:{ Value: A-A-b }, " + "Action2 runs!, c, " + "Result: Result:{ Value: A-A-c }" + "]", logs.toString());
    }
    
    @Test
    public void testMerge4_reusable_withRef() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            Thread.sleep(10);
            logs.add("Action2 runs!");
            String s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val ref = new AtomicInteger(0);
        val w1 = task1.cached(f("get-ref", ref::get));
        val merger = f("merge", (String s1, String s2) -> s1 + "-" + s2);
        val action = Task.from(w1, Task.from(w1, task2, merger), merger);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        ref.incrementAndGet();
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("F2::merge(Task#F0::Action1.cachedFor(F0::get-ref,BiPredicate::when-change), F2::merge(Task#F0::Action1.cachedFor(F0::get-ref,BiPredicate::when-change), Task#F0::Action2))", action.toString());
        assertEquals("[" + "Action1 runs!, A, " + "Action2 runs!, a, Result: Result:{ Value: A-A-a }, " + "Action2 runs!, b, Result: Result:{ Value: A-A-b }, " + "Action2 runs!, c, Result: Result:{ Value: A-A-c }, " + "Action1 runs!, B, " + "Action2 runs!, d, Result: Result:{ Value: B-B-d }, " + "Action2 runs!, e, Result: Result:{ Value: B-B-e }, " + "Action2 runs!, f, Result: Result:{ Value: B-B-f }" + "]", logs.toString());
    }
    
    @Test
    public void testRace_complete_UpperCaseDoneFirst() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            Thread.sleep(50);
            logs.add("Action2 runs!");
            String s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val action = Task.firstOf(task1, task2);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("Race(Task#F0::Action1,Task#F0::Action2)", action.toString());
        assertEquals("Action1 runs!,\n" + "A,\n" + "Result: Result:{ Value: A },\n" + "Action1 runs!,\n" + "B,\n" + "Result: Result:{ Value: B },\n" + "Action1 runs!,\n" + "C,\n" + "Result: Result:{ Value: C }", logs.stream().collect(joining(",\n")));
    }
    
    @Test
    public void testRace_complete_LowerCaseDoneFirst() throws InterruptedException {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            // Action1 will start a little late.
            Thread.sleep(70);
            logs.add("Action1 runs!");
            String s = "" + (char) ('A' + counter1.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            logs.add("Action2 runs!");
            String s = "" + (char) ('a' + counter2.getAndIncrement());
            logs.add(s);
            return s;
        }));
        val action = Task.firstOf(task1, task2);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("Race(Task#F0::Action1,Task#F0::Action2)", action.toString());
        // Ensure that if there is enough time, Action1 will finish
        Thread.sleep(100);
        // Then check that the action didn't get to run.
        assertEquals("Action2 runs!,\n" + "a,\n" + "Result: Result:{ Value: a },\n" + "Action2 runs!,\n" + "b,\n" + "Result: Result:{ Value: b },\n" + "Action2 runs!,\n" + "c,\n" + "Result: Result:{ Value: c }", logs.stream().collect(joining(",\n")));
    }
    
    @Test
    public void testRace_complete_bothFail() {
        val logs = new ArrayList<String>();
        val counter1 = new AtomicInteger(0);
        val task1 = DeferActionBuilder.from(f("Action1", () -> {
            logs.add("Action1 runs!");
            int currentCount = counter1.getAndIncrement();
            if (currentCount >= 1)
                throw new RuntimeException();
            String s = "" + (char) ('A' + currentCount);
            logs.add(s);
            return s;
        }));
        val counter2 = new AtomicInteger(0);
        val task2 = DeferActionBuilder.from(f("Action2", () -> {
            Thread.sleep(50);
            logs.add("Action2 runs!");
            int currentCount = counter2.getAndIncrement();
            if (currentCount >= 1)
                throw new RuntimeException();
            String s = "" + (char) ('a' + currentCount);
            logs.add(s);
            return s;
        }));
        val action = Task.firstOf(task1, task2);
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        logs.add("Result: " + action.createAction().getResult());
        assertEquals("Race(Task#F0::Action1,Task#F0::Action2)", action.toString());
        assertEquals("Action1 runs!,\n" + "A,\n" + "Result: Result:{ Value: A },\n" + "Action1 runs!,\n" + "Action2 runs!,\n" + "a,\n" + "Result: Result:{ Value: a },\n" + "Action1 runs!,\n" + "Action2 runs!,\n" + "Result: Result:{ Cancelled: Finish without non-null result. },\n" + "Action1 runs!,\n" + "Action2 runs!,\n" + "Result: Result:{ Cancelled: Finish without non-null result. },\n" + "Action1 runs!,\n" + "Action2 runs!,\n" + "Result: Result:{ Cancelled: Finish without non-null result. }", logs.stream().collect(joining(",\n")));
    }
    
    @Test
    public void testDoUntil() {
        val logs = new ArrayList<String>();
        val counter = new AtomicInteger(0);
        val task = DeferActionBuilder.from(f("Action", () -> {
            logs.add("Action runs!");
            int currentCount = counter.getAndIncrement();
            String s = "" + (char) ('A' + currentCount);
            logs.add(s);
            return s;
        }));
        val loop = Task.doUntil(task, Func.from("is-C", result -> result.filter("C"::equals).isPresent()));
        logs.add("Result: " + loop.createAction().getResult());
        assertEquals("DoUntil(do: Task#F0::Action, util: Predicate::is-C)", loop.toString());
        assertEquals("Action runs!,\n" + "A,\n" + "Action runs!,\n" + "B,\n" + "Action runs!,\n" + "C,\n" + "Result: Result:{ Value: C }", logs.stream().collect(joining(",\n")));
    }
}
