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
package functionalj.functions;

import java.util.function.Supplier;
import java.util.regex.Pattern;

public class RegExMatchResult implements java.util.regex.MatchResult {
    private final Supplier<String> sourceString;
    private final Pattern          pattern;
    private final int              index;
    private final java.util.regex.MatchResult result;
    
    public RegExMatchResult(Supplier<String> sourceString, Pattern pattern, int index, java.util.regex.MatchResult result) {
        this.sourceString = sourceString;
        this.pattern = pattern;
        this.index = index;
        this.result = result;
    }
    
    public String  sourceString()               { return sourceString.get(); }
    public Pattern pattern()                    { return pattern; }
    public int     index()                      { return index;   }
    public java.util.regex.MatchResult result() { return result;  }
    
    @Override public int    start()          { return result.start();      }
    @Override public int    start(int group) { return result.start(group); }
    @Override public int    end()            { return result.end();        }
    @Override public int    end(int group)   { return result.end(group);   }
    @Override public String group()          { return result.group();      }
    @Override public String group(int group) { return result.group(group); }
    @Override public int    groupCount()     { return result.groupCount(); }
    @Override public String toString()       { return group();             }
}