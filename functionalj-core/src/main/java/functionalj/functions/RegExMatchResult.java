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
package functionalj.functions;

import static functionalj.function.Func.itself;

import java.util.function.Supplier;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import functionalj.function.Func1;
import functionalj.lens.lenses.AnyAccess;
import functionalj.lens.lenses.IntegerAccessPrimitive;
import functionalj.lens.lenses.StringAccess;
import functionalj.stream.StreamPlus;

public class RegExMatchResult implements MatchResult {
    
    
    public static final RegExMatchResultAccess<RegExMatchResult> theResult = new RegExMatchResultAccess<>(itself());
    
    public static class RegExMatchResultAccess<HOST>
            implements AnyAccess<HOST, RegExMatchResult> {
        
        public final StringAccess<HOST>           sourceString = (HOST host) -> apply(host).sourceString();
        public final AnyAccess<HOST, Pattern>     pattern      = (HOST host) -> apply(host).pattern();
        public final IntegerAccessPrimitive<HOST> index        = (HOST host) -> apply(host).index();
        public final AnyAccess<HOST, MatchResult> result       = (HOST host) -> apply(host).result();
        
        public final IntegerAccessPrimitive<HOST> start      = (HOST host) -> apply(host).start();
        public final IntegerAccessPrimitive<HOST> end        = (HOST host) -> apply(host).end();
        public final StringAccess<HOST>           group      = (HOST host) -> apply(host).group();
        public final IntegerAccessPrimitive<HOST> groupCount = (HOST host) -> apply(host).groupCount();
        public final StringAccess<HOST>           text       = (HOST host) -> apply(host).text();
        
        public final IntegerAccessPrimitive<HOST> start(int group) { return (HOST host) -> apply(host).start(group); }
        public final IntegerAccessPrimitive<HOST> end(int group)   { return (HOST host) -> apply(host).end(group);   }
        public final StringAccess<HOST>           group(int group) { return (HOST host) -> apply(host).group(group); }
        
        private final Func1<HOST, RegExMatchResult> access;
        
        public RegExMatchResultAccess(Func1<HOST, RegExMatchResult> access) {
            this.access = access;
        }
        
        @Override
        public RegExMatchResult applyUnsafe(HOST host) throws Exception {
            return access.applyUnsafe(host);
        }
    }
    
    public static final RegExMatchResultsAccess<StreamPlus<RegExMatchResult>> theResults = new RegExMatchResultsAccess<>(itself());
    
    
    public static class RegExMatchResultsAccess<HOST>
        implements AnyAccess<HOST, StreamPlus<RegExMatchResult>> {
        
        public final AnyAccess<HOST, StreamPlus<String>> texts = (HOST host) -> apply(host).map(theResult.text);
        
        private final Func1<HOST, StreamPlus<RegExMatchResult>> access;
        
        public RegExMatchResultsAccess(Func1<HOST, StreamPlus<RegExMatchResult>> access) {
            this.access = access;
        }
        
        @Override
        public StreamPlus<RegExMatchResult> applyUnsafe(HOST host) throws Exception {
            return access.applyUnsafe(host);
        }
    }
    
    private final Supplier<? extends CharSequence> source;
    private final Pattern                          pattern;
    private final int                              index;
    private final MatchResult                      result;
    
    public RegExMatchResult(Supplier<? extends CharSequence> source, Pattern pattern, int index, MatchResult result) {
        this.source = source;
        this.pattern = pattern;
        this.index = index;
        this.result = result;
    }
    
    public CharSequence source()       { return source.get(); }
    public String       sourceString() { return source().toString(); }
    public Pattern      pattern()      { return pattern; }
    public int          index()        { return index;   }
    public MatchResult  result()       { return result;  }
    
    @Override public int    start()          { return result.start();      }
    @Override public int    start(int group) { return result.start(group); }
    @Override public int    end()            { return result.end();        }
    @Override public int    end(int group)   { return result.end(group);   }
    @Override public String group()          { return result.group();      }
    @Override public String group(int group) { return result.group(group); }
    @Override public int    groupCount()     { return result.groupCount(); }
              public String text()           { return result.group();      }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RegExMatchResult other = (RegExMatchResult) obj;
        if (index != other.index)
            return false;
        if (pattern == null) {
            if (other.pattern != null)
                return false;
        } else if (!pattern.equals(other.pattern))
            return false;
        if (result == null) {
            if (other.result != null)
                return false;
        } else if (!result.equals(other.result))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "RegExMatchResult [source=" + source + ", pattern=" + pattern + ", index=" + index
                + ", result=" + result + "]";
    }
    
}