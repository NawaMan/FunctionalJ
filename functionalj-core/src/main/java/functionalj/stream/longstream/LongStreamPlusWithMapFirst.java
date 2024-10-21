// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.longstream;

import static functionalj.stream.longstream.LongStreamPlusMapFirstAddOnHelper.doMapFirst;
import java.util.function.LongFunction;
import functionalj.functions.ThrowFuncs;
import functionalj.stream.StreamPlus;
import lombok.val;

class LongStreamPlusMapFirstAddOnHelper {
    
    @SafeVarargs
    static final <T> StreamPlus<T> doMapFirst(LongStreamPlus streamPlus, LongFunction<T>... mappers) {
        return streamPlus.mapToObj((long data) -> eachMapFirst(data, mappers));
    }
    
    private static <T> T eachMapFirst(long d, LongFunction<T>[] mappers) {
        Exception exception = null;
        boolean hasNull = false;
        for (val mapper : mappers) {
            try {
                val res = mapper.apply(d);
                if (res == null)
                    hasNull = true;
                else
                    return (T) res;
            } catch (Exception e) {
                if (exception == null)
                    exception = e;
            }
        }
        if (hasNull)
            return (T) null;
        throw ThrowFuncs.exceptionTransformer.get().apply(exception);
    }
}

public interface LongStreamPlusWithMapFirst {
    
    public LongStreamPlus longStreamPlus();
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> StreamPlus<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2) {
        val streamPlus = longStreamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2);
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> StreamPlus<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3) {
        val streamPlus = longStreamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3);
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> StreamPlus<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3, LongFunction<T> mapper4) {
        val streamPlus = longStreamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3, mapper4);
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> StreamPlus<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3, LongFunction<T> mapper4, LongFunction<T> mapper5) {
        val streamPlus = longStreamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    /**
     * Map the value by applying each mapper one by one and use the first one that does not return null.
     */
    public default <T> StreamPlus<T> mapFirst(LongFunction<T> mapper1, LongFunction<T> mapper2, LongFunction<T> mapper3, LongFunction<T> mapper4, LongFunction<T> mapper5, LongFunction<T> mapper6) {
        val streamPlus = longStreamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
}
