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
package functionalj.stream;

import static functionalj.function.Func.f;
import static functionalj.stream.StreamPlusMapAddOnHelper.doMapFirst;

import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;


class StreamPlusMapAddOnHelper {
    
    @SafeVarargs
    static final <D, T> StreamPlus<T> doMapFirst(
            StreamPlus<D>              streamPlus,
            Function<? super D, T> ... mappers) {
        return streamPlus.mapToObj(f((D data) -> eachMapFirst(data, mappers)));
    }
    
    private static <T, D> T eachMapFirst(D d, Function<? super D, T>[] mappers) throws Exception {
        Exception exception = null;
        boolean hasNull = false;
        for(val mapper : mappers) {
            try {
                var res = mapper.apply(d);
                if (res == null)
                     hasNull = true;
                else return (T)res;
            } catch (Exception e) {
                if (exception == null)
                    exception = e;
            }
        }
        if (hasNull)
            return (T)null;
        
        throw exception;
    }
    
}

public interface StreamPlusWithMapFirst<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    public <TARGET> StreamPlus<TARGET> derive(Func1<StreamPlus<DATA>, Stream<TARGET>> action);
    
    public IntStreamPlus deriveToInt(Func1<StreamPlus<DATA>, IntStream> action);
    
    public <TARGET> StreamPlus<TARGET> deriveToObj(Func1<StreamPlus<DATA>, Stream<TARGET>> action);
    
    /** Map the value by applying each mapper one by one and use the first one that does not return null. */
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        var streamPlus = streamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2);
    }
    
    /** Map the value by applying each mapper one by one and use the first one that does not return null. */
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        var streamPlus = streamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3);
    }
    
    /** Map the value by applying each mapper one by one and use the first one that does not return null. */
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        var streamPlus = streamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3, mapper4);
    }
    
    /** Map the value by applying each mapper one by one and use the first one that does not return null. */
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        var streamPlus = streamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    /** Map the value by applying each mapper one by one and use the first one that does not return null. */
    public default <T> StreamPlus<T> mapFirst(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        var streamPlus = streamPlus();
        return doMapFirst(streamPlus, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
    
}
