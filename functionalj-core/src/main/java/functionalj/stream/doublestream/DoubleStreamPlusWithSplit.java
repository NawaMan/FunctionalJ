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
package functionalj.stream.doublestream;

import java.util.function.DoublePredicate;
import java.util.function.Function;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple2;

public interface DoubleStreamPlusWithSplit extends DoubleStreamPlusWithMapToTuple {
    
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
    public default Tuple2<DoubleStreamPlus, DoubleStreamPlus> split(DoublePredicate predicate) {
        Function<DoubleFuncList, DoubleStreamPlus> toStreamPlus = DoubleFuncList::doubleStreamPlus;
        return DoubleFuncList.from(() -> doubleStreamPlus()).split(predicate).map(toStreamPlus, toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, DoubleStreamPlus> split(KEY key1, DoublePredicate predicate, KEY key2) {
        Function<DoubleFuncList, DoubleStreamPlus> toStreamPlus = DoubleFuncList::doubleStreamPlus;
        return DoubleFuncList.from(() -> doubleStreamPlus()).split(key1, predicate, key2).mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, DoubleStreamPlus> split(KEY key1, DoublePredicate predicate1, KEY key2, DoublePredicate predicate2, KEY key3) {
        Function<DoubleFuncList, DoubleStreamPlus> toStreamPlus = DoubleFuncList::doubleStreamPlus;
        return DoubleFuncList.from(() -> doubleStreamPlus()).split(key1, predicate1, key2, predicate2, key3).mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, DoubleStreamPlus> split(KEY key1, DoublePredicate predicate1, KEY key2, DoublePredicate predicate2, KEY key3, DoublePredicate predicate3, KEY key4) {
        Function<DoubleFuncList, DoubleStreamPlus> toStreamPlus = DoubleFuncList::doubleStreamPlus;
        return DoubleFuncList.from(() -> doubleStreamPlus()).split(key1, predicate1, key2, predicate2, key3, predicate3, key4).mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, DoubleStreamPlus> split(KEY key1, DoublePredicate predicate1, KEY key2, DoublePredicate predicate2, KEY key3, DoublePredicate predicate3, KEY key4, DoublePredicate predicate4, KEY key5) {
        Function<DoubleFuncList, DoubleStreamPlus> toStreamPlus = DoubleFuncList::doubleStreamPlus;
        return DoubleFuncList.from(() -> doubleStreamPlus()).split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5).mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, DoubleStreamPlus> split(KEY key1, DoublePredicate predicate1, KEY key2, DoublePredicate predicate2, KEY key3, DoublePredicate predicate3, KEY key4, DoublePredicate predicate4, KEY key5, DoublePredicate predicate5, KEY key6) {
        Function<DoubleFuncList, DoubleStreamPlus> toStreamPlus = DoubleFuncList::doubleStreamPlus;
        return DoubleFuncList.from(() -> doubleStreamPlus()).split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5, predicate5, key6).mapValue(toStreamPlus);
    }
}
