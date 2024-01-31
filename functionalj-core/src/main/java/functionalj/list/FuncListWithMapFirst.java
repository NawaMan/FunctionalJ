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
package functionalj.list;

import static functionalj.list.AsFuncListHelper.funcListOf;
import static functionalj.list.FuncList.deriveFrom;
import java.util.function.Function;
import lombok.val;

public interface FuncListWithMapFirst<DATA> extends AsFuncList<DATA> {
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5, Function<? super DATA, T> mapper6) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5, Function<? super DATA, T> mapper6, Function<? super DATA, T> mapper7) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5, Function<? super DATA, T> mapper6, Function<? super DATA, T> mapper7, Function<? super DATA, T> mapper8) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5, Function<? super DATA, T> mapper6, Function<? super DATA, T> mapper7, Function<? super DATA, T> mapper8, Function<? super DATA, T> mapper9) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(Function<? super DATA, T> mapper1, Function<? super DATA, T> mapper2, Function<? super DATA, T> mapper3, Function<? super DATA, T> mapper4, Function<? super DATA, T> mapper5, Function<? super DATA, T> mapper6, Function<? super DATA, T> mapper7, Function<? super DATA, T> mapper8, Function<? super DATA, T> mapper9, Function<? super DATA, T> mapper10) {
        val funcList = funcListOf(this);
        return deriveFrom(funcList, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6, mapper7, mapper8, mapper9, mapper10));
    }
    
}
