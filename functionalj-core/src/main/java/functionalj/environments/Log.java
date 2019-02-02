// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import functionalj.list.FuncList;
import functionalj.list.FuncListStream;
import functionalj.stream.Streamable;
import lombok.val;

// TODO - Change this to Peek or Tab
public final class Log {
    
    private Log() {
    }
    
    
    public static <T> T log(T value) {
        return Env.log().log(value);
    }
    
    public static <T> T log(Object prefix, T value) {
        return Env.log().log(prefix, value);
    }
    public static <T> T log(Object prefix, T value, Object suffix) {
        return Env.log().log(prefix, value, suffix);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> FuncList<T> logEach(T ... values) {
        return Env.log().logEach(values);
    }
    
    public static <T> FuncList<T> logEach(String prefix, Collection<T> values) {
        return Env.log().logEach(prefix, values);
    }
    public static <T> FuncList<T> logEach(String prefix, Collection<T> values, String suffix) {
        return Env.log().logEach(prefix, values, suffix);
    }
    
    public static <T> T logBy(Supplier<T> supplier) {
        return Env.log().logBy(supplier);
    }
    public static <T extends Throwable> T logErr(T throwable) {
        return Env.log().logErr(throwable);
    }
    public static <T extends Throwable> T logErr(Object prefix, T throwable) {
        return Env.log().logErr(prefix, throwable);
    }
    public static <T extends Throwable> T logErr(Object prefix, T throwable, Object suffix) {
        return Env.log().logErr(prefix, throwable, suffix);
    }
    
    
    public static class Instance {
        
        public static final Instance instance = new Instance();
        
        public <T> T log(T value) {
            Env.console().println(value);
            return value;
        }
        
        public <T> T log(Object prefix, T value) {
            return log(prefix, value, null);
        }
        public <T> T log(Object prefix, T value, Object suffix) {
            val prefixStr = (prefix != null) ? String.valueOf(prefix) : "";
            val suffixStr = (suffix != null) ? String.valueOf(suffix) : "";
            val line      = prefixStr + value + suffixStr;
            log(line);
            return value;
        }
        
        @SuppressWarnings("unchecked")
        public <T> FuncList<T> logEach(T ... values) {
            val streamable = Streamable.of((T[])values);
            val list       = FuncListStream.from(streamable);
            list.forEach(value -> this.log(value));
            return list;
        }
        
        public <T> FuncList<T> logEach(String prefix, Collection<T> values) {
            return logEach(prefix, values, null);
        }
        public <T> FuncList<T> logEach(String prefix, Collection<T> values, String suffix) {
            val list = FuncListStream.from(values);
            list.forEach(value -> this.log(prefix, value, suffix));
            return list;
        }
        
        public <T> T logBy(Supplier<T> supplier) {
            val value = Func.getOrElse(supplier, null);
            return log(value);
        }
        
        public <T extends Throwable> T logErr(T throwable) {
            return logErr(null, throwable, null);
        }
        public <T extends Throwable> T logErr(Object prefix, T throwable) {
            return logErr(prefix, throwable, null);
        }
        public <T extends Throwable> T logErr(Object prefix, T throwable, Object suffix) {
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
