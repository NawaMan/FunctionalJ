// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.Function;
import java.util.regex.Pattern;

import functionalj.function.Func;
import lombok.val;


public class RegExFlag {
    
    private int flags = 0;
    
    private static final Function<Integer, RegExFlag> newFlag = Func.cacheFor(RegExFlag::new);
    
    public static RegExFlag of(RegExFlag ... flags) {
        if (flags == null)
            return newFlag.apply(0);
        
        int f = 0;
        for (int i = 0; i < flags.length; i++) {
            val flag = flags[i];
            f |= (flag != null) ? flag.flags : 0;
        }
        
        return newFlag.apply(f);
    }
    public static RegExFlag of(int ... flags) {
        if (flags == null)
            return newFlag.apply(0);
        
        int f = 0;
        for (int i = 0; i < flags.length; i++) {
            val flag = flags[i];
            f |= flag;
        }
        return newFlag.apply(f);
    }
    
    public RegExFlag() {}
    public RegExFlag(int flags) {
        this.flags = flags;
    }
    
    public int getIntValue() {
        return flags;
    }
    
    public RegExFlag unixLines() {
        return unixLines(true);
    }
    public RegExFlag caseInsensitive() {
        return caseInsensitive(true);
    }
    public RegExFlag caseComments() {
        return caseComments(true);
    }
    public RegExFlag multiline() {
        return multiline(true);
    }
    public RegExFlag literal() {
        return literal(true);
    }
    public RegExFlag dotAll() {
        return dotAll(true);
    }
    public RegExFlag unicodeCase() {
        return unicodeCase(true);
    }
    public RegExFlag canonicalEquivalence() {
        return canonicalEquivalence(true);
    }
    public RegExFlag unicodeCharacterClass() {
        return unicodeCharacterClass(true);
    }
    
    public RegExFlag unixLines(boolean included) {
        if (included)
            return of(flags |  Pattern.UNIX_LINES);
       else return of(flags & ~Pattern.UNIX_LINES);
    }
    public RegExFlag caseInsensitive(boolean included) {
        if (included)
            return of(flags |  Pattern.CASE_INSENSITIVE);
       else return of(flags & ~Pattern.CASE_INSENSITIVE);
    }
    public RegExFlag caseComments(boolean included) {
        if (included)
            return of(flags |  Pattern.COMMENTS);
       else return of(flags & ~Pattern.COMMENTS);
    }
    public RegExFlag multiline(boolean included) {
        if (included)
            return of(flags |  Pattern.MULTILINE);
       else return of(flags & ~Pattern.MULTILINE);
    }
    public RegExFlag literal(boolean included) {
        if (included)
            return of(flags |  Pattern.LITERAL);
       else return of(flags & ~Pattern.LITERAL);
    }
    public RegExFlag dotAll(boolean included) {
        if (included)
            return of(flags |  Pattern.DOTALL);
       else return of(flags & ~Pattern.DOTALL);
    }
    public RegExFlag unicodeCase(boolean included) {
        if (included)
            return of(flags |  Pattern.UNICODE_CASE);
       else return of(flags & ~Pattern.UNICODE_CASE);
    }
    public RegExFlag canonicalEquivalence(boolean included) {
        if (included)
            return of(flags |  Pattern.CANON_EQ);
       else return of(flags & ~Pattern.CANON_EQ);
    }
    public RegExFlag unicodeCharacterClass(boolean included) {
        if (included)
             return of(flags |  Pattern.UNICODE_CHARACTER_CLASS);
        else return of(flags & ~Pattern.UNICODE_CHARACTER_CLASS);
    }
    
}
