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
package functionalj.environments;

import static functionalj.function.Func.f;
import static functionalj.ref.Run.With;
import static java.lang.String.format;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import functionalj.InterruptedRuntimeException;
import functionalj.function.FuncUnit1;
import functionalj.functions.ThrowFuncs;
import functionalj.list.FuncList;
import functionalj.promise.DeferAction;
import functionalj.promise.Promise;
import functionalj.ref.ComputeBody;
import functionalj.ref.RunBody;
import functionalj.stream.BlockingQueueIteratorPlus;
import functionalj.stream.StreamPlus;
import lombok.val;

public final class Console {
    
    private Console() {
    }
    
    public static FuncUnit1<Object> print = Console::print;
    
    public static FuncUnit1<Object> println = Console::println;
    
    public static FuncUnit1<Object> printf(String format) {
        return obj -> print(format(format, obj));
    }
    
    public static Console.Instance print(Object text) {
        return Env.console().print(text);
    }
    
    public static Console.Instance println(Object line) {
        return Env.console().println(line);
    }
    
    public static Console.Instance println() {
        return Env.console().println();
    }
    
    public static Console.Instance outPrint(Object text) {
        return Env.console().outPrint(text);
    }
    
    public static Console.Instance outPrintln(Object line) {
        return Env.console().outPrintln(line);
    }
    
    public static Console.Instance outPrintln() {
        return Env.console().outPrintln();
    }
    
    public static Console.Instance errPrint(Object text) {
        return Env.console().errPrint(text);
    }
    
    public static Console.Instance errPrintln(Object line) {
        return Env.console().errPrintln(line);
    }
    
    public static Console.Instance errPrintln() {
        return Env.console().outPrintln();
    }
    
    public static String readln() {
        return Env.console().readln();
    }
    
    public static String pollln() {
        return Env.console().pollln();
    }
    
    public static Promise<String> inputLine() {
        return Env.console().inputLine();
    }
    
    public static void stopRead() {
        Env.console().stopRead();
    }
    
    public static abstract class Instance {
    
        public final Console.Instance print(Object text) {
            return outPrint(text);
        }
    
        public final Console.Instance println(Object line) {
            return outPrintln(line);
        }
    
        public final Console.Instance printf(String format, Object... args) {
            return outPrintf(format, args);
        }
    
        public final Console.Instance println() {
            return outPrintln();
        }
    
        public abstract Console.Instance outPrint(Object text);
    
        public abstract Console.Instance outPrintln(Object line);
    
        public abstract Console.Instance outPrintf(String format, Object... args);
    
        public abstract Console.Instance outPrintln();
    
        public abstract Console.Instance errPrint(Object text);
    
        public abstract Console.Instance errPrintln(Object line);
    
        public abstract Console.Instance errPrintf(String format, Object... args);
    
        public abstract Console.Instance errPrintln();
    
        public abstract String readln();
    
        public abstract String pollln();
    
        public Promise<String> inputLine() {
            return DeferAction.run(() -> {
                String str;
                while ((str = pollln()) == null) {
                    Thread.sleep(1);
                }
                return str;
            }).getPromise();
        }
    
        public abstract void stopRead();
    }
    
    public static class StubRecord<DATA> {
    
        private final DATA data;
    
        private final FuncList<String> outLines;
    
        private final FuncList<String> errLines;
    
        private final FuncList<String> inLines;
    
        public StubRecord(DATA data, FuncList<String> outLines, FuncList<String> errLines, FuncList<String> inLines) {
            this.data = data;
            this.outLines = outLines;
            this.errLines = errLines;
            this.inLines = inLines;
        }
    
        public DATA getData() {
            return data;
        }
    
        public StreamPlus<String> outLines() {
            return outLines.stream();
        }
    
        public StreamPlus<String> errLines() {
            return errLines.stream();
        }
    
        public StreamPlus<String> inLines() {
            return inLines.stream();
        }
    
        public String toString() {
            return "++++++++++++++++++++\n" + "Data: " + data + "\n" + "outLines(" + outLines.size() + "): \n    " + outLines.join("\n    ") + "\n" + "errLines(" + errLines.size() + "): \n    " + errLines.join("\n    ") + "\n" + "inLines(" + inLines.size() + "): \n    " + inLines.join("\n    ") + "\n" + "--------------------";
        }
    }
    
    public static <EXCEPTION extends Exception> StubRecord<Object> useStub(RunBody<EXCEPTION> body) throws EXCEPTION {
        return useStub(new ConsoleInQueue(), () -> {
            body.run();
            return null;
        });
    }
    
    public static <DATA, EXCEPTION extends Exception> StubRecord<DATA> useStub(ComputeBody<DATA, EXCEPTION> body) throws EXCEPTION {
        return useStub(new ConsoleInQueue(), () -> {
            body.run();
            return null;
        });
    }
    
    public static <EXCEPTION extends Exception> StubRecord<Object> useStub(FuncUnit1<ConsoleInQueue> holder, RunBody<EXCEPTION> body) throws EXCEPTION {
        val inQueue = new ConsoleInQueue();
        if (holder != null)
            holder.accept(inQueue);
        return useStub(inQueue, () -> {
            body.run();
            return null;
        });
    }
    
    public static <DATA, EXCEPTION extends Exception> StubRecord<DATA> useStub(FuncUnit1<ConsoleInQueue> holder, ComputeBody<DATA, EXCEPTION> body) throws EXCEPTION {
        val inQueue = new ConsoleInQueue();
        if (holder != null)
            holder.accept(inQueue);
        return useStub(inQueue, () -> {
            body.run();
            return null;
        });
    }
    
    public static <EXCEPTION extends Exception> StubRecord<Object> useStub(Stream<String> inLines, RunBody<EXCEPTION> body) throws EXCEPTION {
        return useStub(inLines, () -> {
            body.run();
            return null;
        });
    }
    
    public static <EXCEPTION extends Exception> StubRecord<Object> useStub(ConsoleInQueue inQueue, RunBody<EXCEPTION> body) throws EXCEPTION {
        return useStub(inQueue, () -> {
            body.run();
            return null;
        });
    }
    
    public static <DATA, EXCEPTION extends Exception> StubRecord<DATA> useStub(Stream<String> inLines, ComputeBody<DATA, EXCEPTION> body) throws EXCEPTION {
        val inQueue = new ConsoleInQueue(StreamPlus.from(inLines).toJavaList());
        return useStub(true, inQueue, body);
    }
    
    public static <DATA, EXCEPTION extends Exception> StubRecord<DATA> useStub(ConsoleInQueue inQueue, ComputeBody<DATA, EXCEPTION> body) throws EXCEPTION {
        return useStub(false, inQueue, body);
    }
    
    private static <DATA, EXCEPTION extends Exception> StubRecord<DATA> useStub(boolean isInStreamDone, ConsoleInQueue inQueue, ComputeBody<DATA, EXCEPTION> body) throws EXCEPTION {
        val stub = new Console.Stub(isInStreamDone, inQueue);
        val data = With(Env.refs.console.butWith(stub)).run(body);
        stub.flush();
        val outLines = stub.outLines().toImmutableList();
        val errLines = stub.errLines().toImmutableList();
        val inLines = stub.recordedInLines().toImmutableList();
        val result = new StubRecord<DATA>(data, outLines, errLines, inLines);
        return result;
    }
    
    public static class System extends Instance {
    
        public static Instance instance = new System();
    
        private static class InPuller {
    
            private static final ConcurrentLinkedQueue<String> lines = new ConcurrentLinkedQueue<String>();
    
            private static final AtomicReference<Thread> pullThread = new AtomicReference<Thread>();
    
            static {
                if (pullThread.get() == null) {
                    if (pullThread.compareAndSet(null, createPullThread())) {
                        pullThread.get().start();
                    }
                }
            }
    
            private static Thread createPullThread() {
                Thread thread = new Thread(() -> {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in))) {
                        br.lines().forEach(lines::add);
                    } catch (UncheckedIOException e) {
                    } catch (IOException e) {
                        throw new InterruptedRuntimeException(e);
                    }
                }, "SystemInReadThread");
                thread.setDaemon(false);
                return thread;
            }
    
            static String readln() {
                String line;
                while ((line = lines.poll()) == null) ;
                return line;
            }
    
            static String pollln() {
                return lines.poll();
            }
    
            static void stopRead() {
                pullThread.getAndUpdate(t -> {
                    t.interrupt();
                    try {
                        java.lang.System.in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return t;
                });
            }
        }
    
        @Override
        public Instance outPrint(Object text) {
            java.lang.System.out.print(text);
            return this;
        }
    
        @Override
        public Instance outPrintln(Object line) {
            java.lang.System.out.println(line);
            return this;
        }
    
        @Override
        public Instance outPrintf(String format, Object... args) {
            java.lang.System.out.printf(format, args);
            return this;
        }
    
        @Override
        public Instance outPrintln() {
            java.lang.System.out.println();
            return this;
        }
    
        @Override
        public Instance errPrint(Object text) {
            java.lang.System.err.print(text);
            return this;
        }
    
        @Override
        public Instance errPrintln(Object line) {
            java.lang.System.err.println(line);
            return this;
        }
    
        @Override
        public Instance errPrintf(String format, Object... args) {
            java.lang.System.err.printf(format, args);
            return this;
        }
    
        @Override
        public Instance errPrintln() {
            java.lang.System.err.println();
            return this;
        }
    
        @Override
        public String readln() {
            return InPuller.readln();
        }
    
        @Override
        public String pollln() {
            return InPuller.pollln();
        }
    
        public void stopRead() {
            InPuller.stopRead();
        }
    }
    
    public static class Stub extends Instance {
    
        public static String newEndValue() {
            return UUID.randomUUID().toString();
        }
    
        private final AtomicReference<ConcurrentLinkedQueue<String>> outTexts = new AtomicReference<>(new ConcurrentLinkedQueue<String>());
    
        private final AtomicReference<ConcurrentLinkedQueue<String>> errTexts = new AtomicReference<>(new ConcurrentLinkedQueue<String>());
    
        private final ConcurrentLinkedQueue<String> outLines = new ConcurrentLinkedQueue<String>();
    
        private final ConcurrentLinkedQueue<String> errLines = new ConcurrentLinkedQueue<String>();
    
        private final ConcurrentLinkedQueue<String> inLines = new ConcurrentLinkedQueue<String>();
    
        private final ConsoleInQueue inQueue;
    
        private final BlockingQueueIteratorPlus<String> lines;
    
        private final FuncUnit1<String> putInLine;
    
        public Stub() {
            this(false, new ConsoleInQueue());
        }
    
        public Stub(Collection<String> inQueue) {
            this(true, new ConsoleInQueue(inQueue));
        }
    
        public Stub(ConsoleInQueue inQueue) {
            this(false, inQueue);
        }
    
        public Stub(boolean inStreamEnded, ConsoleInQueue inQueue) {
            this.inQueue = (inQueue != null) ? inQueue : new ConsoleInQueue();
            this.lines = new BlockingQueueIteratorPlus<String>(this.inQueue.getEndValue(), this.inQueue);
            this.putInLine = f((String line) -> this.inQueue.put(line)).carelessly();
            if (inStreamEnded)
                this.inQueue.end();
        }
    
        public StreamPlus<String> outLines() {
            return StreamPlus.from(outLines.stream());
        }
    
        public void clear() {
            clearOutLines();
            clearErrLines();
            clearInLines();
        }
    
        public void clearOutLines() {
            outLines.clear();
        }
    
        public StreamPlus<String> errLines() {
            return StreamPlus.from(errLines.stream());
        }
    
        public void clearErrLines() {
            errLines.clear();
        }
    
        @Override
        public Instance outPrint(Object obj) {
            val text = String.valueOf(obj);
            outTexts.get().add(text);
            return this;
        }
    
        @Override
        public Instance outPrintln(Object line) {
            println(line, outTexts, outLines);
            return this;
        }
    
        @Override
        public Instance outPrintf(String format, Object... args) {
            val line = String.format(format, args);
            return outPrintln(line);
        }
    
        @Override
        public Instance outPrintln() {
            return outPrintln("");
        }
    
        @Override
        public Instance errPrint(Object obj) {
            val text = String.valueOf(obj);
            errTexts.get().add(text);
            return this;
        }
    
        @Override
        public Instance errPrintln(Object line) {
            println(line, errTexts, errLines);
            return this;
        }
    
        @Override
        public Instance errPrintf(String format, Object... args) {
            val line = String.format(format, args);
            return errPrintln(line);
        }
    
        @Override
        public Instance errPrintln() {
            return errPrintln("");
        }
    
        public void flush() {
            val outs = outTexts.get();
            if ((outs != null) && !outs.isEmpty()) {
                outPrintln();
            }
            val errs = errTexts.get();
            if ((errs != null) && !errs.isEmpty()) {
                errPrintln();
            }
        }
    
        private void println(Object line, AtomicReference<ConcurrentLinkedQueue<String>> texts, ConcurrentLinkedQueue<String> lines) {
            texts.getAndUpdate(oldQuery -> {
                if (oldQuery.isEmpty()) {
                    val fullLine = String.valueOf(line);
                    val lineArray = fullLine.split("(\n|\r\n?)");
                    Arrays.stream(lineArray).forEach(lines::add);
                    return oldQuery;
                }
                val fullLine = oldQuery.stream().collect(Collectors.joining()) + String.valueOf(line);
                val lineArray = fullLine.split("(\n|\r\n?)");
                Arrays.stream(lineArray).forEach(lines::add);
                return new ConcurrentLinkedQueue<String>();
            });
        }
    
        public Stub addInLines(String... lines) {
            Arrays.stream(lines).forEach(putInLine);
            return this;
        }
    
        public Stub addInLines(Iterable<String> lines) {
            lines.forEach(putInLine);
            return this;
        }
    
        public Stub addInLines(Iterator<String> lines) {
            while (lines.hasNext()) {
                val line = lines.next();
                putInLine.accept(line);
            }
            return this;
        }
    
        public Stub addInLines(Stream<String> lines) {
            lines.forEach(putInLine);
            return this;
        }
    
        public Stub endInStream() {
            inQueue.end();
            return this;
        }
    
        public StreamPlus<String> remainingInLines() {
            return StreamPlus.from(lines.remainingValues());
        }
    
        public StreamPlus<String> recordedInLines() {
            return StreamPlus.from(inLines.stream());
        }
    
        public void clearInLines() {
            inQueue.clear();
        }
    
        @Override
        public String readln() {
            String currentLine;
            try {
                currentLine = inQueue.take();
                inLines.add("" + currentLine);
                return currentLine;
            } catch (InterruptedException e) {
                throw ThrowFuncs.exceptionTransformer.get().apply(e);
            }
        }
    
        @Override
        public String pollln() {
            String currentLine = inQueue.poll();
            if (currentLine == null)
                return null;
            inLines.add("" + currentLine);
            return currentLine;
        }
    
        public void stopRead() {
            // Not sure what to do here.
        }
    }
}
