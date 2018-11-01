package functionalj.environments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.InterruptedRuntimeException;
import functionalj.stream.StreamPlus;
import lombok.val;

public final class Console {
    
    private Console() {
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
    
    
    public static abstract class Instance {
        
        public final Console.Instance print(Object text) {
            return outPrint(text);
        }
        public final Console.Instance println(Object line) {
            return outPrintln(line);
        }
        public final Console.Instance printf(String format, Object ... args) {
            return outPrintf(format, args);
        }
        public final Console.Instance println() {
            return outPrintln();
        }
        
        
        public abstract Console.Instance outPrint(Object text);
        
        public abstract Console.Instance outPrintln(Object line);
        
        public abstract Console.Instance outPrintf(String format, Object ... args);
        
        public abstract Console.Instance outPrintln();
        
        
        public abstract Console.Instance errPrint(Object text);
        
        public abstract Console.Instance errPrintln(Object line);
        
        public abstract Console.Instance errPrintf(String format, Object ... args);
        
        public abstract Console.Instance errPrintln();
        
        
        public abstract String readln();
        
    }
    
    public static class System extends Instance {
        
        public static Instance instance = new System();
        
        private final ConcurrentLinkedQueue<String> lines = new ConcurrentLinkedQueue<String>();
        
        
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
        public Instance outPrintf(String format, Object ... args) {
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
        public Instance errPrintf(String format, Object ... args) {
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
            String currentLine = null;
            while ((currentLine = lines.poll()) == null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in))) {
                    br.lines().forEach(lines::add);
                } catch (IOException e) {
                    throw new InterruptedRuntimeException(e);
                }
            }
            return currentLine;
        }
        
    }
    
    public static class Stub extends Instance {
        
        public static Stub instance = new Stub();
        
        private final AtomicReference<ConcurrentLinkedQueue<String>> outTexts = new AtomicReference<>(new ConcurrentLinkedQueue<String>());
        private final AtomicReference<ConcurrentLinkedQueue<String>> errTexts = new AtomicReference<>(new ConcurrentLinkedQueue<String>());
        private final ConcurrentLinkedQueue<String> outLines = new ConcurrentLinkedQueue<String>();
        private final ConcurrentLinkedQueue<String> errLines = new ConcurrentLinkedQueue<String>();
        
        private final ConcurrentLinkedQueue<String> lines = new ConcurrentLinkedQueue<String>();
        
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
        public Instance outPrintf(String format, Object ... args) {
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
        public Instance errPrintf(String format, Object ... args) {
            val line = String.format(format, args);
            return errPrintln(line);
        }
        @Override
        public Instance errPrintln() {
            return errPrintln("");
        }
        
        private void println(Object line, 
                AtomicReference<ConcurrentLinkedQueue<String>> texts,
                ConcurrentLinkedQueue<String>                  lines) {
            texts.getAndUpdate(oldQuery -> {
                if (oldQuery.isEmpty()) {
                    val fullLine = String.valueOf(line);
                    val lineArray = fullLine.split("(\n|\r\n?)");
                    Arrays.stream(lineArray)
                          .forEach(lines::add);
                    return oldQuery;
                }
                
                val fullLine = oldQuery.stream().collect(Collectors.joining()) + String.valueOf(line);
                val lineArray = fullLine.split("(\n|\r\n?)");
                Arrays.stream(lineArray)
                      .forEach(lines::add);
                return new ConcurrentLinkedQueue<String>();
            });
        }
        
        public Stub addInLines(String ... lines) {
            Arrays.stream(lines).forEach(line -> this.lines.add(line));
            return this;
        }
        public Stub addInLines(Iterable<String> lines) {
            lines.forEach(line -> this.lines.add(line));
            return this;
        }
        public Stub addInLines(Iterator<String> lines) {
            while (lines.hasNext()) {
                val line = lines.next();
                this.lines.add(line);
            }
            return this;
        }
        public Stub addInLines(Stream<String> lines) {
            lines.forEach(line -> this.lines.add(line));
            return this;
        }
        
        public StreamPlus<String> inLines() {
            return StreamPlus.from(lines.stream());
        }
        public void clearInLines() {
            lines.clear();
        }
        
        @Override
        public String readln() {
            String currentLine = null;
            while ((currentLine = lines.poll()) == null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in))) {
                    br.lines().forEach(lines::add);
                } catch (IOException e) {
                    throw new InterruptedRuntimeException(e);
                }
            }
            return currentLine;
        }
    }
    
}
