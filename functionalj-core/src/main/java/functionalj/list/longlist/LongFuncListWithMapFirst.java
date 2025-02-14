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
package functionalj.list.longlist;

import static functionalj.list.longlist.LongFuncList.deriveToObj;
import java.util.function.LongFunction;
import functionalj.list.FuncList;

public interface LongFuncListWithMapFirst extends AsLongFuncList {
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2) {
        return deriveToObj(this, stream -> stream.mapFirst(mapper1, mapper2));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3) {
        return deriveToObj(this, stream -> stream.mapFirst(mapper1, mapper2, mapper3));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3, LongFunction<T> mapper4) {
        return deriveToObj(this, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3, LongFunction<T> mapper4, LongFunction<T> mapper5) {
        return deriveToObj(this, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5));
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> FuncList<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3, LongFunction<T> mapper4, LongFunction<T> mapper5, LongFunction<T> mapper6) {
        return deriveToObj(this, stream -> stream.mapFirst(mapper1, mapper2, mapper3, mapper4, mapper5, mapper6));
    }
}
