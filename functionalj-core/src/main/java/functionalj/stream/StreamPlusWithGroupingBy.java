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

import java.util.function.Function;

import functionalj.map.FuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.streamable.Streamable;

public interface StreamPlusWithGroupingBy<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** Group the elements by determining the grouping keys */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, StreamPlus<? super DATA>> groupingBy(Function<? super DATA, KEY> keyMapper) {
        Streamable<DATA> streamable = () -> streamPlus();
        return streamable
                .groupingBy(keyMapper)
                .mapValue(Streamable::streamPlus);
    }
    
    /** Group the elements by determining the grouping keys and aggregate the result */
    @Eager
    @Terminal
    public default <KEY, VALUE> FuncMap<KEY, VALUE> groupingBy(
            Function<? super DATA, KEY> keyMapper,
            StreamProcessor<? super DATA, VALUE>  processor) {
        Streamable<DATA> streamable = () -> streamPlus();
        return streamable
                .groupingBy(keyMapper, processor::process);
    }
    
}
