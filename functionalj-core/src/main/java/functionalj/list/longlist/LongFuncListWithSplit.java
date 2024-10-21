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
package functionalj.list.longlist;

import static functionalj.list.longlist.AsLongFuncListHelper.funcListOf;
import java.util.function.LongPredicate;
import functionalj.map.FuncMap;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.Tuple;
import functionalj.tuple.Tuple2;
import lombok.val;

public interface LongFuncListWithSplit extends AsLongFuncList {
    
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
    public default Tuple2<LongFuncList, LongFuncList> split(LongPredicate predicate) {
        val temp = funcListOf(this).mapToTuple(it -> predicate.test(it) ? 0 : 1, it -> it).toImmutableList();
        val list1 = temp.filter(it -> it._1() == 0).mapToLong(it -> it._2());
        val list2 = temp.filter(it -> it._1() == 1).mapToLong(it -> it._2());
        return Tuple.of(list1, list2);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will thrown away.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate) {
        val list1 = longStreamPlus().filter(predicate).toImmutableList();
        return FuncMap.of(key1, list1);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will thrown away.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2) {
        return split(key1, predicate1, key2, predicate2, null);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will thrown away.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3) {
        return split(key1, predicate1, key2, predicate2, key3, predicate3, null);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will thrown away.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3, KEY key4, LongPredicate predicate4) {
        return split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, null);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will thrown away.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3, KEY key4, LongPredicate predicate4, KEY key5, LongPredicate predicate5) {
        return split(key1, predicate1, key2, predicate2, key3, predicate3, key4, predicate4, key5, predicate5, null);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will thrown away.
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3, KEY key4, LongPredicate predicate4, KEY key5, LongPredicate predicate5, KEY key6, LongPredicate predicate6) {
        val temp = funcListOf(this).mapToTuple(it -> predicate1.test(it) ? 0 : predicate2.test(it) ? 1 : predicate3.test(it) ? 2 : predicate4.test(it) ? 3 : predicate5.test(it) ? 4 : 5, it -> it).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list6 = (key6 != null) ? temp.filter(it -> it._1() == 5).mapToLong(it -> it._2()) : LongFuncList.empty();
        return FuncMap.of(key1, list1, key2, list2, key3, list3, key4, list4, key5, list5, key6, list6.filter(predicate6));
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will be associated with "otherKey".
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate, KEY key2) {
        val temp = funcListOf(this).mapToTuple(it -> predicate.test(it) ? 0 : 1, it -> it).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToLong(it -> it._2()) : LongFuncList.empty();
        return FuncMap.of(key1, list1, key2, list2);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will be associated with "otherKey".
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3) {
        val temp = funcListOf(this).mapToTuple(it -> predicate1.test(it) ? 0 : predicate2.test(it) ? 1 : 2, it -> it).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToLong(it -> it._2()) : LongFuncList.empty();
        return FuncMap.of(key1, list1, key2, list2, key3, list3);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will be associated with "otherKey".
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3, KEY otherKey) {
        val temp = funcListOf(this).mapToTuple(it -> predicate1.test(it) ? 0 : predicate2.test(it) ? 1 : predicate3.test(it) ? 2 : 3, it -> it).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list4 = (otherKey != null) ? temp.filter(it -> it._1() == 3).mapToLong(it -> it._2()) : LongFuncList.empty();
        return FuncMap.of(key1, list1, key2, list2, key3, list3, otherKey, list4);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will be associated with "otherKey".
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3, KEY key4, LongPredicate predicate4, KEY otherKey) {
        val temp = funcListOf(this).mapToTuple(it -> predicate1.test(it) ? 0 : predicate2.test(it) ? 1 : predicate3.test(it) ? 2 : predicate4.test(it) ? 3 : 4, it -> it).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list5 = (otherKey != null) ? temp.filter(it -> it._1() == 4).mapToLong(it -> it._2()) : LongFuncList.empty();
        return FuncMap.of(key1, list1, key2, list2, key3, list3, key4, list4, otherKey, list5);
    }
    
    /**
     * Partitioning the stream using the predicates and return as a map of each partition.
     *
     * The predicate will be checked one by one and when match the element will be used as part of the value with the associated key.
     * If all the keys are given as non-null, all the elements are guaranteed to be in one of the partition.
     * Elements that are associated with a key that are given as null will be thrown away.
     *
     * Any element that does not check any predicate will be associated with "otherKey".
     */
    @Eager
    @Terminal
    public default <KEY> FuncMap<KEY, LongFuncList> split(KEY key1, LongPredicate predicate1, KEY key2, LongPredicate predicate2, KEY key3, LongPredicate predicate3, KEY key4, LongPredicate predicate4, KEY key5, LongPredicate predicate5, KEY otherKey) {
        val temp = funcListOf(this).mapToTuple(it -> predicate1.test(it) ? 0 : predicate2.test(it) ? 1 : predicate3.test(it) ? 2 : predicate4.test(it) ? 3 : predicate5.test(it) ? 4 : 5, it -> it).toImmutableList();
        val list1 = (key1 != null) ? temp.filter(it -> it._1() == 0).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list2 = (key2 != null) ? temp.filter(it -> it._1() == 1).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list3 = (key3 != null) ? temp.filter(it -> it._1() == 2).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list4 = (key4 != null) ? temp.filter(it -> it._1() == 3).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list5 = (key5 != null) ? temp.filter(it -> it._1() == 4).mapToLong(it -> it._2()) : LongFuncList.empty();
        val list6 = (otherKey != null) ? temp.filter(it -> it._1() == 5).mapToLong(it -> it._2()) : LongFuncList.empty();
        return FuncMap.of(key1, list1, key2, list2, key3, list3, key4, list4, key5, list5, otherKey, list6);
    }
}
