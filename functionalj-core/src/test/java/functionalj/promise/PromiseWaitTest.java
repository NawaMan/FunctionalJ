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
package functionalj.promise;

import static functionalj.TestHelper.assertAsString;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;

import functionalj.list.FuncList;
import functionalj.tuple.Tuple;
import lombok.val;

public class PromiseWaitTest {
    
    @Test
    public void testWaitAWhile_complete() throws InterruptedException {
        val list = new ArrayList<String>();
        val action = DeferAction.of(String.class).use(promise -> promise.onCompleted(Wait.forMilliseconds(100).orDefaultTo("Not done."), r -> list.add(r.get()))).start();
        Thread.sleep(50);
        action.complete("Complete!");
        assertAsString("[Complete!]", list);
    }
    
    @Test
    public void testWaitAWhile_cancel() throws InterruptedException {
        val list = new ArrayList<String>();
        val action = DeferAction.of(String.class).use(promise -> promise.onCompleted(Wait.forMilliseconds(50).orDefaultTo("Not done."), r -> list.add(r.get()))).start();
        Thread.sleep(100);
        action.complete("Complete!");
        assertAsString("[Not done.]", list);
    }
    
    @Test
    public void testWaitAWhile_neverStart() throws InterruptedException {
        val list = new ArrayList<String>();
        DeferAction.of(String.class).onCompleted(Wait.forMilliseconds(50).orDefaultTo("Not done."), r -> list.add(r.get()));
        Thread.sleep(100);
        assertAsString("[Not done.]", list);
    }
    
    @Test
    public void testWaitAWhile_differentRunners_complete() throws InterruptedException {
        val runners = FuncList.of(Tuple.of("asyncRunnerOnNewThread", AsyncRunner.onNewThread), Tuple.of("asyncRunnerThreadFactory", AsyncRunner.threadFactory), Tuple.of("asyncRunnerExecutorService", AsyncRunner.executorService(Executors.newSingleThreadExecutor())), Tuple.of("asyncRunnerThreadFactory()", AsyncRunner.threadFactory(new ThreadFactory() {
        
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        })));
        runners.forEach(tuple -> {
            val list = new ArrayList<String>();
            val action = DeferAction.of(String.class).use(promise -> promise.onCompleted(Wait.forMilliseconds(150, tuple._2()).orDefaultTo("Not done."), r -> list.add(r.get()))).start();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.complete("Complete!");
            assertAsString("Name: " + tuple._1(), "[Complete!]", list);
        });
    }
    
    @Test
    public void testWaitAWhile_differentRunners_cancel() throws InterruptedException {
        val runners = FuncList.of(Tuple.of("asyncRunnerOnNewThread", AsyncRunner.onNewThread), Tuple.of("asyncRunnerThreadFactory", AsyncRunner.threadFactory), Tuple.of("asyncRunnerExecutorService", AsyncRunner.executorService(Executors.newSingleThreadExecutor())), Tuple.of("asyncRunnerThreadFactory()", AsyncRunner.threadFactory(new ThreadFactory() {
        
            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable);
            }
        })));
        runners.forEach(tuple -> {
            val list = new ArrayList<String>();
            val action = DeferAction.of(String.class).use(promise -> promise.onCompleted(Wait.forMilliseconds(50, tuple._2()).orDefaultTo("Not done."), r -> list.add(r.get()))).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            action.complete("Complete!");
            assertAsString("Name: " + tuple._1(), "[Not done.]", list);
        });
    }
    
    @Test
    public void testWaitAWhile_interrupt() throws InterruptedException {
        val threadRef = new AtomicReference<Thread>();
        val list = new ArrayList<String>();
        val action = DeferAction.of(String.class).use(promise -> {
            Wait wait = Wait.forMilliseconds(150, runnable -> {
                val thread = new Thread(runnable);
                threadRef.set(thread);
                thread.start();
            }).orDefaultTo("Not done.");
            promise.onCompleted(wait, r -> list.add(r.get()));
        }).start();
        Thread.sleep(50);
        threadRef.get().interrupt();
        Thread.sleep(50);
        action.complete("Complete!");
        assertAsString("[Not done.]", list);
        // The cancel should be initiated at 150 but that was interrupted at 50.
    }
    
    @Test
    public void testWaitAWhile_interrupt_late() throws InterruptedException {
        val threadRef = new AtomicReference<Thread>();
        val list = new ArrayList<String>();
        val action = DeferAction.of(String.class).use(promise -> {
            Wait wait = Wait.forMilliseconds(150, runnable -> {
                val thread = new Thread(runnable);
                threadRef.set(thread);
                thread.start();
                ;
            }).orDefaultTo("Not done.");
            promise.onCompleted(wait, r -> list.add(r.get()));
        }).start();
        Thread.sleep(50);
        action.complete("Complete!");
        Thread.sleep(100);
        threadRef.get().interrupt();
        assertAsString("[Complete!]", list);
    }
}
