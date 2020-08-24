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
package functionalj.stream.intstream;

import java.util.function.Function;
import java.util.function.IntPredicate;

import functionalj.map.FuncMap;
import functionalj.streamable.intstreamable.IntStreamable;
import functionalj.tuple.Tuple2;
import functionalj.tuple.Tuple3;
import functionalj.tuple.Tuple4;
import functionalj.tuple.Tuple5;
import functionalj.tuple.Tuple6;

public interface IntStreamPlusWithSplit
            extends IntStreamPlusWithMapToTuple {
    
    // The most important thing here is to only evaluate the value once.
    // Everything else that contradict that must give. That because we can use regular filter if evaluating once is not important.
    
    //== split ==
    
    // TODO - Try to make it lazy 
    // It is not easy as it seems as there has to be buffer for one branch when go through with another branch.
    // We may need a dynamic collection of all branch as we goes along.
    
    /**
     * Split the stream using the predicate.
     * The result is a tuple of streams where the first stream is for those element that the predicate returns true.
     * 
     * The elements in this stream is guaranteed to be in one of the result stream.
     */
    public default Tuple2<IntStreamPlus, IntStreamPlus> split(
            IntPredicate predicate) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(predicate)
                .map(toStreamPlus, toStreamPlus);
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
    public default Tuple3<IntStreamPlus, IntStreamPlus, IntStreamPlus> split(
            IntPredicate predicate1,
            IntPredicate predicate2) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(predicate1, predicate2)
                .map(toStreamPlus, toStreamPlus, toStreamPlus);
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
    public default Tuple4<IntStreamPlus, IntStreamPlus, IntStreamPlus, IntStreamPlus> split(
            IntPredicate predicate1,
            IntPredicate predicate2,
            IntPredicate predicate3) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(predicate1, predicate2, predicate3)
                .map(toStreamPlus, toStreamPlus, toStreamPlus, toStreamPlus);
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
    public default Tuple5<IntStreamPlus, IntStreamPlus, IntStreamPlus, IntStreamPlus, IntStreamPlus> split(
            IntPredicate predicate1,
            IntPredicate predicate2,
            IntPredicate predicate3,
            IntPredicate predicate4) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(predicate1, predicate2, predicate3, predicate4)
                .map(toStreamPlus, toStreamPlus, toStreamPlus, toStreamPlus, toStreamPlus);
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
    public default Tuple6<IntStreamPlus, IntStreamPlus, IntStreamPlus, IntStreamPlus, IntStreamPlus, IntStreamPlus> split(
            IntPredicate predicate1,
            IntPredicate predicate2,
            IntPredicate predicate3,
            IntPredicate predicate4,
            IntPredicate predicate5) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(predicate1, predicate2, predicate3, predicate4, predicate5)
                .map(toStreamPlus, toStreamPlus, toStreamPlus, toStreamPlus, toStreamPlus, toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, IntStreamPlus> split(
            KEY key1, IntPredicate predicate,
            KEY key2) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(key1, predicate, key2)
                .mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, IntStreamPlus> split(
            KEY key1, IntPredicate predicate1,
            KEY key2, IntPredicate predicate2,
            KEY key3) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(key1, predicate1, key2, predicate2, key3)
                .mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, IntStreamPlus> split(
            KEY key1, IntPredicate predicate1,
            KEY key2, IntPredicate predicate2,
            KEY key3, IntPredicate predicate3,
            KEY key4) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(key1, predicate1, key2, predicate2, key3, predicate3, key4)
                .mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, IntStreamPlus> split(
            KEY key1, IntPredicate predicate1,
            KEY key2, IntPredicate predicate2,
            KEY key3, IntPredicate predicate3,
            KEY key4, IntPredicate predicate4,
            KEY key5) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5)
                .mapValue(toStreamPlus);
    }
    
    /**
     * Split the stream using the predicate and return as part of a map.
     * 
     * The predicate will be checked one by one and when match the element will be used as part of the value with that associated key.
     */
    public default <KEY> FuncMap<KEY, IntStreamPlus> split(
            KEY key1, IntPredicate predicate1,
            KEY key2, IntPredicate predicate2,
            KEY key3, IntPredicate predicate3,
            KEY key4, IntPredicate predicate4,
            KEY key5, IntPredicate predicate5,
            KEY key6) {
        Function<IntStreamable, IntStreamPlus> toStreamPlus = IntStreamable::intStreamPlus;
        IntStreamable streamable = ()->intStreamPlus();
        return streamable
                .split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5, predicate5, key6)
                .mapValue(toStreamPlus);
    }
}
