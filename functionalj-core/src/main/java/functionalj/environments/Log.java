// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.function.Supplier;
import functionalj.function.Func;
import functionalj.function.Func1;
import functionalj.list.FuncList;
import lombok.val;

public final class Log {
    
    private Log() {
    }
    
    public static <DATA> Func1<DATA, DATA> tab() {
        return Log::log;
    }
    
    public static <DATA> Func1<DATA, DATA> tab(Object prefix) {
        return v -> log(prefix, v);
    }
    
    public static <DATA> Func1<DATA, DATA> tabf(String format) {
        return v -> {
            log(String.format(format, v));
            return v;
        };
    }
    
    public static <DATA> DATA log(DATA value) {
        return Env.log().log(value);
    }
    
    public static <DATA> DATA log(Object prefix, DATA value) {
        return Env.log().log(prefix, value);
    }
    
    public static <DATA> DATA log(Object prefix, DATA value, Object suffix) {
        return Env.log().log(prefix, value, suffix);
    }
    
    @SuppressWarnings("unchecked")
    public static <DATA> FuncList<DATA> logEach(DATA... values) {
        return Env.log().logEach(values);
    }
    
    public static <DATA> FuncList<DATA> logEach(String prefix, Collection<DATA> values) {
        return Env.log().logEach(prefix, values);
    }
    
    public static <DATA> FuncList<DATA> logEach(String prefix, Collection<DATA> values, String suffix) {
        return Env.log().logEach(prefix, values, suffix);
    }
    
    public static <DATA> DATA logBy(Supplier<DATA> supplier) {
        return Env.log().logBy(supplier);
    }
    
    public static <THROWABLE extends Throwable> THROWABLE logErr(THROWABLE throwable) {
        return Env.log().logErr(throwable);
    }
    
    public static <THROWABLE extends Throwable> THROWABLE logErr(Object prefix, THROWABLE throwable) {
        return Env.log().logErr(prefix, throwable);
    }
    
    public static <THROWABLE extends Throwable> THROWABLE logErr(Object prefix, THROWABLE throwable, Object suffix) {
        return Env.log().logErr(prefix, throwable, suffix);
    }
    
    public static class Instance {
        
        public static final Instance instance = new Instance();
        
        public <DATA> DATA log(DATA value) {
            Env.console().println(value);
            return value;
        }
        
        public <DATA> DATA log(Object prefix, DATA value) {
            return log(prefix, value, null);
        }
        
        public <DATA> DATA log(Object prefix, DATA value, Object suffix) {
            val prefixStr = (prefix != null) ? String.valueOf(prefix) : "";
            val suffixStr = (suffix != null) ? String.valueOf(suffix) : "";
            val line = prefixStr + value + suffixStr;
            log(line);
            return value;
        }
        
        @SuppressWarnings("unchecked")
        public <DATA> FuncList<DATA> logEach(DATA... values) {
            val funcList = FuncList.of((DATA[]) values);
            funcList.forEach(value -> this.log(value));
            return funcList;
        }
        
        public <DATA> FuncList<DATA> logEach(String prefix, Collection<DATA> values) {
            return logEach(prefix, values, null);
        }
        
        public <DATA> FuncList<DATA> logEach(String prefix, Collection<DATA> values, String suffix) {
            val list = FuncList.from(values);
            list.forEach(value -> this.log(prefix, value, suffix));
            return list;
        }
        
        public <DATA> DATA logBy(Supplier<DATA> supplier) {
            DATA value = Func.getOrElse(supplier, null);
            return log(value);
        }
        
        public <THROWABLE extends Throwable> THROWABLE logErr(THROWABLE throwable) {
            return logErr(null, throwable, null);
        }
        
        public <THROWABLE extends Throwable> THROWABLE logErr(Object prefix, THROWABLE throwable) {
            return logErr(prefix, throwable, null);
        }
        
        public <THROWABLE extends Throwable> THROWABLE logErr(Object prefix, THROWABLE throwable, Object suffix) {
            val prefixStr = (prefix != null) ? String.valueOf(prefix) + "\n" : "";
            val suffixStr = (suffix != null) ? String.valueOf(suffix) : "";
            val buffer = new ByteArrayOutputStream();
            throwable.printStackTrace(new PrintStream(buffer));
            val toPrint = prefixStr + buffer.toString() + suffixStr;
            Env.console().errPrintln(toPrint);
            return throwable;
        }
    }
}
