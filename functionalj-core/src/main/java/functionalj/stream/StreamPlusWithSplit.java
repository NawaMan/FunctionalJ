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
package functionalj.stream;

import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple2;

public interface StreamPlusWithSplit<DATA> extends StreamPlusWithMapToTuple<DATA> {

    // The most important thing here is to only evaluate the value once.
    // Everything else that contradict that must give. That because we can use regular filter if evaluating once is not important.
    // == split ==
    // TODO - Try to make it lazy
    // It is not easy as it seems as there has to be buffer for one branch when go through with another branch.
    // We may need a dynamic collection of all branch as we goes along.
    /**
     * Split the stream using the predicate.
     * The result is a tuple of streams where the first stream is for those element that the predicate returns true.
     *
     * The elements in this stream is guaranteed to be in one of the result stream.
     */
    public default Tuple2<StreamPlus<DATA>, StreamPlus<DATA>> split(Predicate<DATA> predicate) {
        Function<? super FuncList<DATA>, StreamPlus<DATA>> toStreamPlus = FuncList::streamPlus;
        return FuncList.from(() -> streamPlus()).split(predicate).map(toStreamPlus, toStreamPlus);
    }

    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with theat associated key.
     */
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> split(KEY key1, Predicate<? super DATA> predicate, KEY key2) {
        Function<? super FuncList<DATA>, StreamPlus<DATA>> toStreamPlus = FuncList::streamPlus;
        return FuncList.from(() -> streamPlus()).split(key1, predicate, key2).mapValue(toStreamPlus);
    }

    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with theat associated key.
     */
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> split(KEY key1, Predicate<? super DATA> predicate1, KEY key2, Predicate<? super DATA> predicate2, KEY key3) {
        Function<? super FuncList<DATA>, StreamPlus<DATA>> toStreamPlus = FuncList::streamPlus;
        return FuncList.from(() -> streamPlus()).split(key1, predicate1, key2, predicate2, key3).mapValue(toStreamPlus);
    }

    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with theat associated key.
     */
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> split(KEY key1, Predicate<? super DATA> predicate1, KEY key2, Predicate<? super DATA> predicate2, KEY key3, Predicate<? super DATA> predicate3, KEY key4) {
        Function<? super FuncList<DATA>, StreamPlus<DATA>> toStreamPlus = FuncList::streamPlus;
        return FuncList.from(() -> streamPlus()).split(key1, predicate1, key2, predicate2, key3, predicate3, key4).mapValue(toStreamPlus);
    }

    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with theat associated key.
     */
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> split(KEY key1, Predicate<? super DATA> predicate1, KEY key2, Predicate<? super DATA> predicate2, KEY key3, Predicate<? super DATA> predicate3, KEY key4, Predicate<? super DATA> predicate4, KEY key5) {
        Function<? super FuncList<DATA>, StreamPlus<DATA>> toStreamPlus = FuncList::streamPlus;
        return FuncList.from(() -> streamPlus()).split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5).mapValue(toStreamPlus);
    }

    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with theat associated key.
     */
    public default <KEY> FuncMap<KEY, StreamPlus<DATA>> split(KEY key1, Predicate<? super DATA> predicate1, KEY key2, Predicate<? super DATA> predicate2, KEY key3, Predicate<? super DATA> predicate3, KEY key4, Predicate<? super DATA> predicate4, KEY key5, Predicate<? super DATA> predicate5, KEY key6) {
        Function<? super FuncList<DATA>, StreamPlus<DATA>> toStreamPlus = FuncList::streamPlus;
        return FuncList.from(() -> streamPlus()).split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5, predicate5, key6).mapValue(toStreamPlus);
    }
}
