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

import java.util.function.Function;

import lombok.val;


class StreamPlusMapAddOnHelper {
    
    @SafeVarargs
    static final <D, T> StreamPlus<T> mapCase(
            StreamPlusWithMapCase<D>  stream,
            Function<? super D, T> ... mappers) {
        return stream.map(f((D data) -> eachMapCase(data, mappers)));
    }
    
    private static <T, D> T eachMapCase(D d, Function<? super D, T>[] mappers) throws Exception {
        Exception exception = null;
        boolean hasNull = false;
        for(val mapper : mappers) {
            try {
                val res = mapper.apply(d);
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

public interface StreamPlusWithMapCase<DATA> {
    
    public <TARGET> StreamPlus<TARGET> map(
            Function<? super DATA, ? extends TARGET> mapper);
    
    //== mapCase ==
    
    public default <T> StreamPlus<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2) {
        return StreamPlusMapAddOnHelper
                .mapCase(this, mapper1, mapper2);
    }
    
    public default <T> StreamPlus<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3) {
        return StreamPlusMapAddOnHelper
                .mapCase(this, mapper1, mapper2, mapper3);
    }
    
    public default <T> StreamPlus<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4) {
        return StreamPlusMapAddOnHelper
                .mapCase(this, mapper1, mapper2, mapper3, mapper4);
    }
    
    public default <T> StreamPlus<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5) {
        return StreamPlusMapAddOnHelper
                .mapCase(this, mapper1, mapper2, mapper3, mapper4, mapper5);
    }
    
    public default <T> StreamPlus<T> mapCase(
            Function<? super DATA, T> mapper1,
            Function<? super DATA, T> mapper2,
            Function<? super DATA, T> mapper3,
            Function<? super DATA, T> mapper4,
            Function<? super DATA, T> mapper5,
            Function<? super DATA, T> mapper6) {
        return StreamPlusMapAddOnHelper
                .mapCase(this, mapper1, mapper2, mapper3, mapper4, mapper5, mapper6);
    }
}
