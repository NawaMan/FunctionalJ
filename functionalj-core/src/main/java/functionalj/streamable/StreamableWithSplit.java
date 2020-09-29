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
package functionalj.streamable;

import static functionalj.streamable.AsStreamable.streamableFrom;

import java.util.function.Predicate;

import functionalj.list.FuncList;
import functionalj.map.FuncMap;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;
import lombok.val;

public interface StreamableWithSplit<DATA> extends StreamableWithMapToTuple<DATA> {
    
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
    public default Tuple2<Streamable<DATA>, Streamable<DATA>> split(
            Predicate<? super DATA> predicate) {
        val temp
            = mapToTuple(
                    it -> predicate.test(it) ? 0 : 1,
                    it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToObj(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToObj(it -> it._2());
        return Tuple.of(
                streamableFrom(list1),
                streamableFrom(list2));
    }
    
    /**
     * Split the stream using the predicates.
     * 
     * The element will be in the first sub stream if the first predicate return true.
     * The element will be in the second sub stream if the first predicate return false and the second predicate is true.
     * Otherwise, it will be in the last sub stream.
     * 
     * The elements in this stream is guaranteed to be in one of the sub streams.
     */
    public default Tuple3<Streamable<DATA>, Streamable<DATA>, Streamable<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2) {
        val temp
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToObj(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToObj(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToObj(it -> it._2());
        return Tuple.of(
                streamableFrom(list1),
                streamableFrom(list2),
                streamableFrom(list3));
    }
    
    /**
     * Split the stream using the predicates.
     * 
     * The element will be in the first sub stream if the first predicate return true.
     * The element will be in the second sub stream if the first predicate return false and the second predicate is true.
     * The element will be in the third sub stream if the first and second predicate return false and the third predicate is true.
     * Otherwise, it will be in the last sub stream.
     * 
     * The elements in this stream is guaranteed to be in one of the sub streams.
     */
    public default Tuple4<Streamable<DATA>, Streamable<DATA>, Streamable<DATA>, Streamable<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3) {
        val temp
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToObj(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToObj(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToObj(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).mapToObj(it -> it._2());
        return Tuple.of(
                streamableFrom(list1),
                streamableFrom(list2),
                streamableFrom(list3),
                streamableFrom(list4));
    }
    
    /**
     * Split the stream using the predicates.
     * 
     * The element will be in the first sub stream if the first predicate return true.
     * The element will be in the second sub stream if the first predicate return false and the second predicate is true.
     * The element will be in the third sub stream if the first and second predicate return false and the third predicate is true.
     * The element will be in the forth sub stream if the first, second, third predicate return false and the forth predicate is true.
     * Otherwise, it will be in the last sub stream.
     * 
     * The elements in this stream is guaranteed to be in one of the sub streams.
     */
    public default Tuple5<Streamable<DATA>, Streamable<DATA>, Streamable<DATA>, Streamable<DATA>, Streamable<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4) {
        val temp
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToObj(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToObj(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToObj(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).mapToObj(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).mapToObj(it -> it._2());
        return Tuple.of(
                streamableFrom(list1),
                streamableFrom(list2),
                streamableFrom(list3),
                streamableFrom(list4),
                streamableFrom(list5));
    }
    
    /**
     * Split the stream using the predicates.
     * 
     * The element will be in the first sub stream if the first predicate return true.
     * The element will be in the second sub stream if the first predicate return false and the second predicate is true.
     * The element will be in the third sub stream if the first and second predicate return false and the third predicate is true.
     * The element will be in the forth sub stream if the first, second and third predicate return false and the forth predicate is true.
     * The element will be in the fifth sub stream if the first, second, third and forth predicate return false and the fifth predicate is true.
     * Otherwise, it will be in the last sub stream.
     * 
     * The elements in this stream is guaranteed to be in one of the sub streams.
     */
    public default Tuple6<Streamable<DATA>, Streamable<DATA>, Streamable<DATA>, Streamable<DATA>, Streamable<DATA>, Streamable<DATA>> split(
            Predicate<? super DATA> predicate1,
            Predicate<? super DATA> predicate2,
            Predicate<? super DATA> predicate3,
            Predicate<? super DATA> predicate4,
            Predicate<? super DATA> predicate5) {
        val temp 
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
            )
            .toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToObj(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToObj(it -> it._2());
        val list3 = temp.filter(it -> it._1() == 2).mapToObj(it -> it._2());
        val list4 = temp.filter(it -> it._1() == 3).mapToObj(it -> it._2());
        val list5 = temp.filter(it -> it._1() == 4).mapToObj(it -> it._2());
        val list6 = temp.filter(it -> it._1() == 5).mapToObj(it -> it._2());
        return Tuple.of(
                streamableFrom(list1),
                streamableFrom(list2),
                streamableFrom(list3),
                streamableFrom(list4),
                streamableFrom(list5),
                streamableFrom(list6));
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, Streamable<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate,
            KEY key2) {
        val temp 
            = mapToTuple(
                it -> predicate.test(it) ? 0 : 1,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, streamableFrom(list1),
                key2, streamableFrom(list2));
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, Streamable<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3) {
        val temp 
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    :                       2,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, streamableFrom(list1),
                key2, streamableFrom(list2),
                key3, streamableFrom(list3));
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, Streamable<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4) {
        val temp 
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    :                       3,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, streamableFrom(list1),
                key2, streamableFrom(list2),
                key3, streamableFrom(list3),
                key4, streamableFrom(list4));
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, Streamable<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5) {
        val temp
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    :                       4,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, streamableFrom(list1),
                key2, streamableFrom(list2),
                key3, streamableFrom(list3),
                key4, streamableFrom(list4),
                key5, streamableFrom(list5));
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, Streamable<DATA>> split(
            KEY key1, Predicate<? super DATA> predicate1,
            KEY key2, Predicate<? super DATA> predicate2,
            KEY key3, Predicate<? super DATA> predicate3,
            KEY key4, Predicate<? super DATA> predicate4,
            KEY key5, Predicate<? super DATA> predicate5,
            KEY key6) {
        val temp
            = mapToTuple(
                it -> predicate1.test(it) ? 0
                    : predicate2.test(it) ? 1
                    : predicate3.test(it) ? 2
                    : predicate4.test(it) ? 3
                    : predicate5.test(it) ? 4
                    :                       5,
                it -> it
            )
            .toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        val list6 = (key6 != null) ? temp.filter(it -> it._1() == 5).mapToObj(it -> it._2()) : FuncList.<DATA>empty();
        return FuncMap.of(
                key1, streamableFrom(list1),
                key2, streamableFrom(list2),
                key3, streamableFrom(list3),
                key4, streamableFrom(list4),
                key5, streamableFrom(list5),
                key6, streamableFrom(list6));
    }
    
}
