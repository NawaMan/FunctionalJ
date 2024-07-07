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
package functionalj.list.intlist;

import static functionalj.lens.Access.theInteger;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import functionalj.function.Func;
import functionalj.function.IntComparator;
import functionalj.function.aggregator.IntAggregation;
import functionalj.function.aggregator.IntAggregationToBoolean;
import functionalj.function.aggregator.IntAggregationToDouble;
import functionalj.function.aggregator.IntAggregationToInt;
import functionalj.function.aggregator.IntAggregationToLong;
import functionalj.list.AsFuncList;
import functionalj.list.FuncList;
import functionalj.list.FuncList.Mode;
import functionalj.list.ImmutableFuncList;
import functionalj.list.doublelist.AsDoubleFuncList;
import functionalj.list.doublelist.DoubleFuncList;
import functionalj.list.longlist.AsLongFuncList;
import functionalj.list.longlist.LongFuncList;
import functionalj.result.NoMoreResultException;
import functionalj.result.Result;
import functionalj.stream.StreamPlus;
import functionalj.stream.SupplierBackedIterator;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.GrowOnlyIntArray;
import functionalj.stream.intstream.IntIterable;
import functionalj.stream.intstream.IntIteratorPlus;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamPlusHelper;
import functionalj.stream.longstream.LongStreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Sequential;
import functionalj.stream.markers.Terminal;
import functionalj.tuple.IntIntTuple;
import lombok.val;
import nullablej.nullable.Nullable;

// TODO - Use this for byte, short and char
public interface IntFuncList extends AsIntFuncList, IntIterable, IntPredicate, IntFuncListWithCombine, IntFuncListWithFilter, IntFuncListWithFlatMap, IntFuncListWithLimit, IntFuncListWithMap, IntFuncListWithMapFirst, IntFuncListWithMapGroup, IntFuncListWithMapMulti, IntFuncListWithMapThen, IntFuncListWithMapToMap, IntFuncListWithMapToTuple, IntFuncListWithMapWithIndex, IntFuncListWithModify, IntFuncListWithPeek, IntFuncListWithPipe, IntFuncListWithSegment, IntFuncListWithSort, IntFuncListWithSplit {
    
    /**
     * Throw a no more element exception. This is used for generator.
     */
    public static int noMoreElement() throws NoMoreResultException {
        SupplierBackedIterator.noMoreElement();
        return Integer.MIN_VALUE;
    }
    
    /**
     * Returns an empty IntFuncList.
     */
    public static ImmutableIntFuncList empty() {
        return ImmutableIntFuncList.empty();
    }
    
    /**
     * Returns an empty functional list.
     */
    public static ImmutableIntFuncList emptyList() {
        return ImmutableIntFuncList.empty();
    }
    
    /**
     * Returns an empty functional list.
     */
    public static ImmutableIntFuncList emptyIntList() {
        return ImmutableIntFuncList.empty();
    }
    
    /**
     * Create a FuncList from the given ints.
     */
    public static ImmutableIntFuncList of(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /**
     * Create a FuncList from the given ints.
     */
    public static ImmutableIntFuncList AllOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /**
     * Create a FuncList from the given ints.
     */
    public static ImmutableIntFuncList ints(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /**
     * Create a FuncList from the given ints.
     */
    public static ImmutableIntFuncList intList(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /**
     * Create a FuncList from the given ints.
     */
    @SafeVarargs
    public static ImmutableIntFuncList ListOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    /**
     * Create a FuncList from the given ints.
     */
    @SafeVarargs
    public static ImmutableIntFuncList listOf(int... data) {
        return ImmutableIntFuncList.of(data);
    }
    
    // -- From --
    /**
     * Create a FuncList from the given ints.
     */
    public static ImmutableIntFuncList from(int[] datas) {
        return new ImmutableIntFuncList(datas, datas.length);
    }
    
    /**
     * Create a FuncList from the given collection.
     */
    public static IntFuncList from(Collection<Integer> data, int valueForNull) {
        IntStream intStream = StreamPlus.from(data.stream()).fillNull((Integer) valueForNull).mapToInt(theInteger);
        Mode mode = (data instanceof FuncList) ? ((FuncList<Integer>) data).mode() : Mode.lazy;
        return ImmutableIntFuncList.from(mode, intStream);
    }
    
    /**
     * Create a FuncList from the given FuncList.
     */
    public static IntFuncList from(Mode mode, AsIntFuncList asFuncList) {
        val funcList = asFuncList.asIntFuncList();
        return funcList.toMode(mode);
    }
    
    /**
     * Create a FuncList from the given stream.
     */
    public static IntFuncList from(IntStream stream) {
        return new StreamBackedIntFuncList(stream);
    }
    
    /**
     * Create a FuncList from the given supplier of stream.
     *
     * The provided stream should produce the same sequence of values.
     */
    public static IntFuncList from(Supplier<IntStream> supplier) {
        return new IntFuncListDerived(() -> IntStreamPlus.from(supplier.get()));
    }
    
    // == Create ==
    /**
     * Returns the infinite streams of zeroes.
     */
    public static IntFuncList zeroes() {
        return zeroes(Integer.MAX_VALUE);
    }
    
    /**
     * Returns a list that contains zeroes.
     */
    public static IntFuncList zeroes(int count) {
        return IntFuncList.from(() -> IntStreamPlus.zeroes(count));
    }
    
    /**
     * Returns the list of ones.
     */
    public static IntFuncList ones() {
        return ones(Integer.MAX_VALUE);
    }
    
    /**
     * Returns a list that contains ones.
     */
    public static IntFuncList ones(int count) {
        return IntFuncList.from(() -> IntStreamPlus.ones(count));
    }
    
    /**
     * Create a list that is the repeat of the given array of data.
     */
    public static IntFuncList repeat(int... data) {
        return IntFuncList.from(() -> IntStreamPlus.repeat(data));
    }
    
    /**
     * Create a list that is the repeat of the given array of data.
     */
    public static IntFuncList cycle(int... data) {
        return IntFuncList.from(() -> IntStreamPlus.cycle(data));
    }
    
    /**
     * Create a list that is the repeat of the given list of data.
     */
    public static IntFuncList repeat(IntFuncList data) {
        return IntFuncList.from(() -> IntStreamPlus.repeat(data));
    }
    
    /**
     * Create a list that is the repeat of the given list of data.
     */
    public static IntFuncList cycle(IntFuncList data) {
        return IntFuncList.from(() -> IntStreamPlus.cycle(data));
    }
    
    /**
     * Create a list that for a loop with the number of time given - the value is the index of the loop.
     */
    public static IntFuncList loop() {
        return IntFuncList.from(() -> IntStreamPlus.loop());
    }
    
    /**
     * Create a list that for a loop with the number of time given - the value is the index of the loop.
     */
    public static IntFuncList loop(int times) {
        return IntFuncList.from(() -> IntStreamPlus.loop(times));
    }
    
    public static IntFuncList loopBy(int step) {
        return IntFuncList.from(() -> IntStreamPlus.loopBy(step));
    }
    
    public static IntFuncList loopBy(int step, int times) {
        return IntFuncList.from(() -> IntStreamPlus.loopBy(step, times));
    }
    
    public static IntFuncList infinite() {
        return IntFuncList.from(() -> IntStreamPlus.infinite());
    }
    
    public static IntFuncList infiniteInt() {
        return IntFuncList.from(() -> IntStreamPlus.infiniteInt());
    }
    
    public static IntFuncList naturalNumbers() {
        return IntFuncList.from(() -> IntStreamPlus.naturalNumbers());
    }
    
    public static IntFuncList naturalNumbers(int count) {
        return IntFuncList.from(() -> IntStreamPlus.naturalNumbers(count));
    }
    
    public static IntFuncList wholeNumbers() {
        return IntFuncList.from(() -> IntStreamPlus.wholeNumbers());
    }
    
    /**
     * Returns the infinite streams of wholes numbers -- 0, 1, 2, 3, ....
     */
    public static IntFuncList wholeNumbers(int count) {
        return IntFuncList.from(() -> IntStreamPlus.wholeNumbers(count));
    }
    
    /**
     * Create a FuncList that for a loop with the number of time given - the value is the index of the loop.
     */
    public static IntFuncList range(int startInclusive, int endExclusive) {
        return IntFuncList.from(() -> IntStreamPlus.range(startInclusive, endExclusive));
    }
    
    // -- Concat + Combine --
    /**
     * Concatenate all the given streams.
     */
    public static IntFuncList concat(IntFuncList... lists) {
        return combine(lists);
    }
    
    /**
     * Concatenate all the given lists.
     *
     * This method is the alias of {@link FuncList#concat(FuncList...)}
     *   but allowing static import without colliding with {@link String#concat(String)}.
     */
    public static IntFuncList combine(IntFuncList... lists) {
        ImmutableFuncList<IntFuncList> listOfList = FuncList.listOf(lists);
        return listOfList.flatMapToInt(Func.itself());
    }
    
    // TODO - Rethink ... as this will generate un-repeatable stream.
    // we may want to do cache here.
    /**
     * Create a FuncList from the supplier of suppliers.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     */
    public static IntFuncList generate(Supplier<IntSupplier> suppliers) {
        return IntFuncList.from(() -> {
            val generator = suppliers.get();
            return IntStreamPlus.generate(generator);
        });
    }
    
    /**
     * Create a list from the supplier of suppliers.
     * The supplier will be repeatedly asked for value until NoMoreResultException is thrown.
     */
    public static IntFuncList generateWith(Supplier<IntSupplier> suppliers) {
        return generate(suppliers);
    }
    
    /**
     * Create a list by apply the compounder to the seed over and over.
     *
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 &lt;- seed,
     *      2 &lt;- (1*2),
     *      4 &lt;- ((1*2)*2),
     *      8 &lt;- (((1*2)*2)*2),
     *      16 &lt;- ((((1*2)*2)*2)*2)
     *      ...
     *
     * Note: this is an alias of compound()
     */
    public static IntFuncList iterate(int seed, IntUnaryOperator compounder) {
        return IntFuncList.from(() -> IntStreamPlus.iterate(seed, compounder));
    }
    
    public static IntFuncList iterate(int seed, IntAggregationToInt aggregation) {
        return IntFuncList.from(() -> IntStreamPlus.iterate(seed, aggregation));
    }
    
    /**
     * Create a list by apply the compounder to the seed over and over.
     *
     * For example: let say seed = 1 and f(x) = x*2.
     * The result stream will be:
     *      1 &lt;- seed,
     *      2 &lt;- (1*2),
     *      4 &lt;- ((1*2)*2),
     *      8 &lt;- (((1*2)*2)*2),
     *      16 &lt;- ((((1*2)*2)*2)*2)
     *      ...
     *
     * Note: this is an alias of iterate()
     */
    public static IntFuncList compound(int seed, IntUnaryOperator compounder) {
        return IntFuncList.from(() -> IntStreamPlus.compound(seed, compounder));
    }
    
    public static IntFuncList compound(int seed, IntAggregationToInt aggregation) {
        return IntFuncList.from(() -> IntStreamPlus.compound(seed, aggregation));
    }
    
    /**
     * Create a list by apply the compounder to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 &lt;- seed1,
     *      1 &lt;- seed2,
     *      2 &lt;- (1+1),
     *      3 &lt;- (1+2),
     *      5 &lt;- (2+3),
     *      8 &lt;- (5+8)
     *      ...
     *
     * Note: this is an alias of compound()
     */
    public static IntFuncList iterate(int seed1, int seed2, IntBinaryOperator compounder) {
        return IntFuncList.from(() -> IntStreamPlus.iterate(seed1, seed2, compounder));
    }
    
    /**
     * Create a list by apply the compounder to the seeds over and over.
     *
     * For example: let say seed1 = 1, seed2 = 1 and f(a,b) = a+b.
     * The result stream will be:
     *      1 &lt;- seed1,
     *      1 &lt;- seed2,
     *      2 &lt;- (1+1),
     *      3 &lt;- (1+2),
     *      5 &lt;- (2+3),
     *      8 &lt;- (5+8)
     *      ...
     *
     * Note: this is an alias of iterate()
     */
    public static IntFuncList compound(int seed1, int seed2, IntBinaryOperator compounder) {
        return iterate(seed1, seed2, compounder);
    }
    
    // == Zip ==
    /**
     * Create a FuncList by combining elements together into a FuncList of tuples.
     * Only elements with pair will be combined. If this is not desirable, use FuncList1.zip(FuncList2).
     *
     * For example:
     *     list1 = [A, B, C, D, E]
     *     list2 = [1, 2, 3, 4]
     *
     * The result stream = [(A,1), (B,2), (C,3), (D,4)].
     */
    public static FuncList<IntIntTuple> zipOf(AsIntFuncList list1, AsIntFuncList list2) {
        return FuncList.from(() -> {
            return IntStreamPlus.zipOf(list1.intStream(), list2.intStream());
        });
    }
    
    /**
     * Create a FuncList by combining elements together using the merger function and collected into the result stream.
     * Only elements with pair will be combined. If this is not desirable, use FuncList1.zip(FuncList2).
     *
     * For example:
     *     list1 = [A, B, C, D, E]
     *     list2 = [1, 2, 3, 4]
     *     merger      = a + "+" + b
     *
     * The result stream = ["A+1", "B+2", "C+3", "D+4"].
     */
    public static FuncList<IntIntTuple> zipOf(AsIntFuncList list1, int defaultValue1, AsIntFuncList list2, int defaultValue2) {
        return FuncList.from(() -> {
            return IntStreamPlus.zipOf(list1.intStream(), defaultValue1, list2.intStream(), defaultValue2);
        });
    }
    
    /**
     * Zip integers from two IntFuncLists and combine it into another object.
     */
    public static IntFuncList zipOf(AsIntFuncList list1, AsIntFuncList list2, IntBinaryOperator merger) {
        return IntFuncList.from(() -> {
            return IntStreamPlus.zipOf(list1.intStream(), list2.intStream(), merger);
        });
    }
    
    /**
     * Zip integers from an int stream and another object stream and combine it into another object.
     * The result stream has the size of the shortest stream.
     */
    public static IntFuncList zipOf(AsIntFuncList list1, int defaultValue1, AsIntFuncList list2, int defaultValue2, IntBinaryOperator merger) {
        return IntFuncList.from(() -> {
            return IntStreamPlus.zipOf(list1.intStream(), defaultValue1, list2.intStream(), defaultValue2, merger);
        });
    }
    
    // -- Builder --
    /**
     * Create a new FuncList.
     */
    public static IntFuncListBuilder newListBuilder() {
        return new IntFuncListBuilder();
    }
    
    /**
     * Create a new list builder.
     */
    public static IntFuncListBuilder newBuilder() {
        return new IntFuncListBuilder();
    }
    
    /**
     * Create a new FuncList.
     */
    public static IntFuncListBuilder newIntListBuilder() {
        return new IntFuncListBuilder();
    }
    
    // == Core ==
    /**
     * Return the stream of data behind this IntFuncList.
     */
    public IntStreamPlus intStream();
    
    /**
     * Return the stream of data behind this IntFuncList.
     */
    public default IntStreamPlus intStreamPlus() {
        return intStream();
    }
    
    /**
     * Return the this as a FuncList.
     */
    @Override
    public default IntFuncList asIntFuncList() {
        return this;
    }
    
    // -- Derive --
    /**
     * Create a FuncList from the given FuncList.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <SOURCE> IntFuncList deriveFrom(List<SOURCE> list, Function<StreamPlus<SOURCE>, IntStream> action) {
        Mode mode = (list instanceof FuncList) ? ((FuncList) list).mode() : Mode.lazy;
        switch(mode) {
            case lazy:
                {
                    return IntFuncList.from(() -> {
                        val orgStreamPlus
                                = (list instanceof FuncList) 
                                ? ((FuncList) list).streamPlus()
                                : StreamPlus.from(list.stream());
                        val newStream = action.apply(orgStreamPlus);
                        val newStreamPlus = IntStreamPlus.from(newStream);
                        return newStreamPlus;
                    });
                }
            case eager:
                {
                    val orgStreamPlus = (list instanceof FuncList) ? ((FuncList) list).streamPlus() : StreamPlus.from(list.stream());
                    val newStream = action.apply(orgStreamPlus);
                    val newStreamPlus = IntStreamPlus.from(newStream);
                    return ImmutableIntFuncList.from(Mode.eager, newStreamPlus);
                }
            case cache:
                {
                    val orgStreamPlus = (list instanceof FuncList) ? ((FuncList) list).streamPlus() : StreamPlus.from(list.stream());
                    val newStream = action.apply(orgStreamPlus);
                    return IntStreamPlus.from(newStream).toFuncList();
                }
        }
        throw new IllegalArgumentException("Unknown functional list mode: " + mode);
    }
    
    /**
     * Create a FuncList from the given IntFuncList.
     */
    public static <TARGET> IntFuncList deriveFrom(AsIntFuncList asFuncList, Function<IntStreamPlus, IntStream> action) {
        Mode mode = asFuncList.asIntFuncList().mode();
        switch(mode) {
            case lazy:
                {
                    return IntFuncList.from(() -> {
                        val orgStreamPlus = asFuncList.intStreamPlus();
                        val newStream = action.apply(orgStreamPlus);
                        return IntStreamPlus.from(newStream);
                    });
                }
            case eager:
            case cache:
                {
                    val orgStreamPlus = asFuncList.intStreamPlus();
                    val newStream = action.apply(orgStreamPlus);
                    val newStreamPlus = IntStreamPlus.from(newStream);
                    return ImmutableIntFuncList.from(mode, newStreamPlus);
                }
            default:
                throw new IllegalArgumentException("Unknown functional list mode: " + mode);
        }
    }
    
    /**
     * Create a FuncList from the given IntFuncList.
     */
    public static <TARGET> IntFuncList deriveFrom(AsLongFuncList asFuncList, Function<LongStreamPlus, IntStream> action) {
        Mode mode = asFuncList.asLongFuncList().mode();
        switch(mode) {
            case lazy:
                {
                    return IntFuncList.from(() -> {
                        val orgStreamPlus = asFuncList.longStreamPlus();
                        val newStream = action.apply(orgStreamPlus);
                        return IntStreamPlus.from(newStream);
                    });
                }
            case eager:
            case cache:
                {
                    val orgStreamPlus = asFuncList.longStreamPlus();
                    val newStream = action.apply(orgStreamPlus);
                    val newStreamPlus = IntStreamPlus.from(newStream);
                    return ImmutableIntFuncList.from(mode, newStreamPlus);
                }
            default:
                throw new IllegalArgumentException("Unknown functional list mode: " + mode);
        }
    }
    
    /**
     * Create a FuncList from the given DoubleFuncList.
     */
    public static <TARGET> IntFuncList deriveFrom(AsDoubleFuncList asFuncList, Function<DoubleStreamPlus, IntStream> action) {
        Mode mode = asFuncList.asDoubleFuncList().mode();
        switch(mode) {
            case lazy:
                {
                    return IntFuncList.from(() -> {
                        val orgStreamPlus = asFuncList.doubleStreamPlus();
                        val newStream = action.apply(orgStreamPlus);
                        return IntStreamPlus.from(newStream);
                    });
                }
            case eager:
            case cache:
                {
                    val orgStreamPlus = asFuncList.doubleStreamPlus();
                    val newStream = action.apply(orgStreamPlus);
                    val newStreamPlus = IntStreamPlus.from(newStream);
                    return ImmutableIntFuncList.from(mode, newStreamPlus);
                }
            default:
                throw new IllegalArgumentException("Unknown functional list mode: " + mode);
        }
    }
    
    /**
     * Create a FuncList from another FuncList.
     */
    public static IntFuncList deriveToInt(AsIntFuncList funcList, Function<IntStreamPlus, IntStream> action) {
        return IntFuncList.deriveFrom(funcList, action);
    }
    
    /**
     * Create a FuncList from another FuncList.
     */
    public static LongFuncList deriveToLong(AsIntFuncList funcList, Function<IntStreamPlus, LongStream> action) {
        return LongFuncList.deriveFrom(funcList, action);
    }
    
    /**
     * Create a FuncList from another FuncList.
     */
    public static DoubleFuncList deriveToDouble(AsIntFuncList funcList, Function<IntStreamPlus, DoubleStream> action) {
        return DoubleFuncList.deriveFrom(funcList, action);
    }
    
    /**
     * Create a FuncList from another FuncList.
     */
    public static <TARGET> FuncList<TARGET> deriveToObj(AsIntFuncList funcList, Function<IntStreamPlus, Stream<TARGET>> action) {
        return FuncList.deriveFrom(funcList, action);
    }
    
    // -- Predicate --
    /**
     * Test if the data is in the list
     */
    @Override
    public default boolean test(int value) {
        return contains(value);
    }
    
    // -- Mode --
    /**
     * Check if this list is a lazy list.
     */
    public default Mode mode() {
        return Mode.lazy;
    }
    
    /**
     * Return a list with the specified mode.
     */
    public default IntFuncList toMode(Mode mode) {
        switch(mode) {
            case lazy:
                return toLazy();
            case eager:
                return toEager();
            case cache:
                return toCache();
        }
        throw new IllegalArgumentException("Unknown list mode: " + mode);
    }
    
    public default boolean isLazy() {
        return mode().isLazy();
    }
    
    /**
     * Check if this list is an eager list.
     */
    public default boolean isEager() {
        return mode().isEager();
    }
    
    /**
     * Check if this list is a cache list.
     */
    public default boolean isCache() {
        return mode().isCache();
    }
    
    // -- Lazy + Eager --
    /**
     * Return a lazy list with the data of this list.
     */
    public default IntFuncList toLazy() {
        if (mode().isLazy()) {
            return this;
        }
        return new IntFuncListDerived(() -> intStreamPlus());
    }
    
    /**
     * Return a eager list with the data of this list.
     */
    public default IntFuncList toEager() {
        if (mode().isEager()) {
            return this;
        }
        // Just materialize all value.
        int size = size();
        return new ImmutableIntFuncList(this, size, Mode.eager);
    }
    
    /**
     * Return a cache list with the data of this list.
     */
    public default IntFuncList toCache() {
        if (mode().isCache()) {
            return this;
        }
        return new StreamBackedIntFuncList(intStreamPlus(), Mode.cache);
    }
    
    /**
     * Freeze the data of this list as an immutable list and maintain the mode afterward.
     */
    public default ImmutableIntFuncList freeze() {
        return new ImmutableIntFuncList(this, -1, mode());
    }
    
    /**
     * Create a cache list but maintain the mode afterward.
     */
    public default IntFuncList cache() {
        return new StreamBackedIntFuncList(intStreamPlus(), mode());
    }
    
    // -- Iterable --
    /**
     * @return the iterable of this FuncList.
     */
    public default IntIterable iterable() {
        return () -> iterator();
    }
    
    // -- Iterator --
    /**
     * @return a iterator of this list.
     */
    @Override
    public default IntIteratorPlus iterator() {
        return IntIteratorPlus.from(intStream());
    }
    
    /**
     * @return a spliterator of this list.
     */
    public default Spliterator.OfInt spliterator() {
        val iterator = iterator();
        return Spliterators.spliteratorUnknownSize(iterator, 0);
    }
    
    // -- Map --
    /**
     * Map each value into a string value.
     */
    public default FuncList<String> mapToString() {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(i -> String.valueOf(i)));
    }
    
    /**
     * Map each value into other value using the function.
     */
    public default IntFuncList map(IntUnaryOperator mapper) {
        return deriveFrom(this, streamble -> streamble.intStream().map(mapper));
    }
    
    /**
     * Map each value into other value using the function.
     */
    public default IntFuncList map(IntAggregationToInt aggregation) {
        val mapper = aggregation.newAggregator();
        return map(mapper);
    }
    
    /**
     * Map each value into an integer value using the function.
     */
    public default IntFuncList mapToInt(IntUnaryOperator mapper) {
        return map(mapper);
    }
    
    /**
     * Map each value into an integer value using the function.
     */
    public default IntFuncList mapToInt(IntAggregationToInt aggregation) {
        val mapper = aggregation.newAggregator();
        return map(mapper);
    }
    
    /**
     * Map each value into an integer value using the function.
     */
    public default LongFuncList mapToLong(IntToLongFunction mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.mapToLong(mapper));
    }
    
    /**
     * Map each value into an integer value using the function.
     */
    public default LongFuncList mapToLong(IntAggregationToLong aggregation) {
        val mapper = aggregation.newAggregator();
        return mapToLong(mapper);
    }
    
    /**
     * Map each value into a double value.
     */
    public default DoubleFuncList mapToDouble() {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(i -> i));
    }
    
    /**
     * Map each value into a double value using the function.
     */
    public default DoubleFuncList mapToDouble(IntToDoubleFunction mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.mapToDouble(mapper));
    }
    
    /**
     * Map each value into a double value using the function.
     */
    public default DoubleFuncList mapToDouble(IntAggregationToDouble aggregation) {
        val mapper = aggregation.newAggregator();
        return mapToDouble(mapper);
    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(IntFunction<? extends TARGET> mapper) {
        return FuncList.deriveFrom(this, stream -> stream.mapToObj(mapper));
    }
    
    public default <TARGET> FuncList<TARGET> mapToObj(IntAggregation<? extends TARGET> aggregation) {
        val mapper = aggregation.newAggregator();
        return mapToObj(mapper);
    }
    
    // -- FlatMap --
    /**
     * Map a value into a FuncList and then flatten that list
     */
    public default IntFuncList flatMap(IntFunction<? extends AsIntFuncList> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).intStream()));
    }
    
    /**
     * Map a value into a FuncList and then flatten that list
     */
    public default IntFuncList flatMap(IntAggregation<? extends AsIntFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMap(mapper);
    }
    
    /**
     * Map a value into an integer FuncList and then flatten that list
     */
    public default IntFuncList flatMapToInt(IntFunction<? extends AsIntFuncList> mapper) {
        return IntFuncList.deriveFrom(this, stream -> stream.flatMap(value -> mapper.apply(value).intStream()));
    }
    
    /**
     * Map a value into a FuncList and then flatten that list
     */
    public default IntFuncList flatMapToInt(IntAggregation<? extends AsIntFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMapToInt(mapper);
    }
    
    /**
     * Map a value into an integer FuncList and then flatten that list
     */
    public default LongFuncList flatMapToLong(IntFunction<? extends AsLongFuncList> mapper) {
        return LongFuncList.deriveFrom(this, stream -> stream.flatMapToLong(value -> mapper.apply(value).longStream()));
    }
    
    /**
     * Map a value into an integer FuncList and then flatten that list
     */
    public default LongFuncList flatMapToLong(IntAggregation<? extends AsLongFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMapToLong(mapper);
    }
    
    /**
     * Map a value into a double FuncList and then flatten that list
     */
    public default DoubleFuncList flatMapToDouble(IntFunction<? extends AsDoubleFuncList> mapper) {
        return DoubleFuncList.deriveFrom(this, stream -> stream.flatMapToDouble(value -> mapper.apply(value).doubleStream()));
    }
    
    /**
     * Map a value into an integer FuncList and then flatten that list
     */
    public default DoubleFuncList flatMapToDouble(IntAggregation<? extends AsDoubleFuncList> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMapToDouble(mapper);
    }
    
    /**
     * Map a value into an integer FuncList and then flatten that list
     */
    public default <D> FuncList<D> flatMapToObj(IntFunction<? extends AsFuncList<D>> mapper) {
        return FuncList.deriveFrom(this, stream -> stream.flatMapToObj(value -> mapper.apply(value).stream()));
    }
    
    /**
     * Map a value into an integer FuncList and then flatten that list
     */
    public default <D> FuncList<D> flatMapToObj(IntAggregation<? extends AsFuncList<D>> aggregation) {
        val mapper = aggregation.newAggregator();
        return flatMapToObj(mapper);
    }
    
    // -- Filter --
    /**
     * Select only the element that passes the predicate
     */
    public default IntFuncList filter(IntPredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(predicate));
    }
    
    /**
     * Select only the element that passes the predicate
     */
    public default IntFuncList filter(IntAggregationToBoolean aggregation) {
        val mapper = aggregation.newAggregator();
        return filter(mapper);
    }
    
    /**
     * Select only the element that the mapped value passes the predicate
     */
    public default IntFuncList filter(IntUnaryOperator mapper, IntPredicate predicate) {
        return deriveFrom(this, stream -> stream.filter(mapper, predicate));
    }
    
    // -- Peek --
    /**
     * Consume each value using the action whenever a termination operation is called
     */
    public default IntFuncList peek(IntConsumer action) {
        return deriveFrom(this, stream -> stream.peek(action));
    }
    
    // -- Limit/Skip --
    /**
     * Limit the size
     */
    public default IntFuncList limit(long maxSize) {
        return deriveFrom(this, stream -> stream.limit(maxSize));
    }
    
    /**
     * Trim off the first n values
     */
    public default IntFuncList skip(long offset) {
        return deriveFrom(this, stream -> stream.skip(offset));
    }
    
    // -- Distinct --
    /**
     * Remove duplicates
     */
    public default IntFuncList distinct() {
        return deriveFrom(this, stream -> stream.distinct());
    }
    
    // -- Sorted --
    /**
     * Sort the values in this stream
     */
    public default IntFuncList sorted() {
        return deriveFrom(this, stream -> stream.sorted());
    }
    
    /**
     * Sort the values in this stream using the given comparator
     */
    public default IntFuncList sorted(IntComparator comparator) {
        return deriveFrom(this, stream -> stream.sorted(comparator));
    }
    
    // -- Terminate --
    /**
     * Process each value using the given action
     */
    public default void forEach(IntConsumer action) {
        intStream().forEach(action);
    }
    
    /**
     * Performs an action for each element of this stream,
     *   in the encounter order of the stream if the stream has a defined encounter order.
     */
    public default void forEachOrdered(IntConsumer action) {
        intStream().forEachOrdered(action);
    }
    
    // == Conversion ==
    public default IntFuncList toFuncList() {
        return this;
    }
    
    /**
     * Convert this FuncList to an array.
     */
    public default int[] toArray() {
        return intStream().toArray();
    }
    
    /**
     * Returns stream of Integer from the value of this list.
     */
    public default FuncList<Integer> boxed() {
        return mapToObj(theInteger.boxed());
    }
    
    /**
     * Returns a functional list builder with the initial data of this func list.
     */
    public default IntFuncListBuilder toBuilder() {
        return new IntFuncListBuilder(toArray());
    }
    
    // == Nullable, Optional and Result
    public default Nullable<IntFuncList> __nullable() {
        return Nullable.of(this);
    }
    
    public default Optional<IntFuncList> __optional() {
        return Optional.of(this);
    }
    
    public default Result<IntFuncList> __result() {
        return Result.valueOf(this);
    }
    
    // -- List specific --
    public default int size() {
        return (int) intStream().count();
    }
    
    /**
     * Find any indexes that the elements match the predicate
     */
    public default IntFuncList indexesOf(IntPredicate check) {
        return mapWithIndex((index, data) -> check.test(data) ? index : -1).filter(i -> i != -1);
    }
    
    /**
     * Find any indexes that the elements match the predicate
     */
    public default IntFuncList indexesOf(IntAggregationToBoolean aggregation) {
        val check = aggregation.newAggregator();
        return indexesOf(check);
    }
    
    public default IntFuncList indexesOf(int value) {
        return mapWithIndex((index, data) -> (data == value) ? index : -1).filter(i -> i != -1);
    }
    
    public default int indexOf(int o) {
        return indexesOf(each -> Objects.equals(o, each)).findFirst().orElse(-1);
    }
    
    public default int lastIndexOf(int o) {
        return indexesOf(each -> Objects.equals(o, each)).last().orElse(-1);
    }
    
    /**
     * Returns the first element.
     */
    public default OptionalInt first() {
        return intStream().limit(1).findFirst();
    }
    
    /**
     * Returns the first elements
     */
    public default IntFuncList first(int count) {
        return limit(count);
    }
    
    /**
     * Returns the last element.
     */
    public default OptionalInt last() {
        return last(1).findFirst();
    }
    
    /**
     * Returns the last elements
     */
    public default IntFuncList last(int count) {
        val size = this.size();
        val offset = Math.max(0, size - count);
        return skip(offset);
    }
    
    /**
     * Returns the element at the index.
     */
    public default OptionalInt at(int index) {
        return skip(index).limit(1).findFirst();
    }
    
    /**
     * Returns the second to the last elements.
     */
    public default IntFuncList tail() {
        return skip(1);
    }
    
    /**
     * Add the given value to the end of the list.
     */
    public default IntFuncList append(int value) {
        return IntFuncList.concat(this, IntFuncList.of(value));
    }
    
    /**
     * Add the given values to the end of the list.
     */
    public default IntFuncList appendAll(int... values) {
        return IntFuncList.concat(this, IntFuncList.of(values));
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public default IntFuncList appendAll(IntFuncList values) {
        return IntFuncList.concat(this, values);
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public default IntFuncList appendAll(List<Integer> ints, int fallbackValue) {
        return IntFuncList.from(() -> {
            IntStreamPlus thisStream = this.intStream();
            IntStream intStream = ints.stream().mapToInt(i -> (i == null) ? fallbackValue : i.intValue());
            return IntStreamPlus.concat(thisStream, intStream);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public default IntFuncList appendAll(GrowOnlyIntArray array) {
        return IntFuncList.from(() -> {
            IntStreamPlus thisStream = this.intStream();
            IntStream intStream = array.stream();
            return IntStreamPlus.concat(thisStream, intStream);
        });
    }
    
    /**
     * Add the given value to the beginning of the list
     */
    public default IntFuncList prepend(int value) {
        return IntFuncList.concat(IntFuncList.of(value), this);
    }
    
    /**
     * Add the given values to the beginning of the list
     */
    public default IntFuncList prependAll(int... values) {
        return IntFuncList.concat(IntFuncList.of(values), this);
    }
    
    /**
     * Add the given value in the collection to the beginning of the list
     */
    public default IntFuncList prependAll(IntFuncList prefixFuncList) {
        if (prefixFuncList == null)
            return this;
        return IntFuncList.concat(prefixFuncList, this);
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public default IntFuncList prependAll(List<Integer> ints, int fallbackValue) {
        return IntFuncList.from(() -> {
            IntStreamPlus thisStream = this.intStream();
            IntStream intStream = ints.stream().mapToInt(i -> (i == null) ? fallbackValue : i.intValue());
            return IntStreamPlus.concat(intStream, thisStream);
        });
    }
    
    /**
     * Add the given value in the collection to the end of the list.
     */
    public default IntFuncList prependAll(GrowOnlyIntArray array) {
        return IntFuncList.from(() -> {
            IntStreamPlus thisStream = this.intStream();
            IntStream intStream = array.stream();
            return IntStreamPlus.concat(intStream, thisStream);
        });
    }
    
    /**
     * Returns a new functional list with the value replacing at the index.
     */
    public default IntFuncList with(int index, int value) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        return mapWithIndex((i, orgValue) -> ((i == index) ? value : orgValue));
    }
    
    /**
     * Returns a new functional list with the new value (calculated from the mapper) replacing at the index.
     */
    public default IntFuncList with(int index, IntUnaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        return mapWithIndex((i, value) -> {
            return (i == index) ? mapper.applyAsInt(value) : value;
        });
    }
    
    /**
     * Returns a new functional list with the new value (calculated from the mapper) replacing at the index.
     */
    public default IntFuncList with(int index, IntBinaryOperator mapper) {
        if (index < 0)
            throw new IndexOutOfBoundsException(index + "");
        if (index >= size())
            throw new IndexOutOfBoundsException(index + " vs " + size());
        return mapWithIndex((i, value) -> {
            int newValue = (i == index) ? mapper.applyAsInt(i, value) : value;
            return newValue;
        });
    }
    
    /**
     * Returns a new list with the given elements inserts into at the given index.
     */
    public default IntFuncList insertAt(int index, int... elements) {
        if ((elements == null) || (elements.length == 0))
            return this;
        val first = limit(index);
        val tail = skip(index);
        return IntFuncList.concat(first, IntFuncList.of(elements), tail);
    }
    
    /**
     * Returns a new list with the given elements inserts into at the given index.
     */
    public default IntFuncList insertAllAt(int index, AsIntFuncList theFuncList) {
        if (theFuncList == null)
            return this;
        val first = limit(index);
        val middle = theFuncList.asIntFuncList();
        val tail = skip(index);
        return IntFuncList.concat(first, middle, tail);
    }
    
    /**
     * Returns the new list from this list without the element.
     */
    public default IntFuncList exclude(int element) {
        return filter(each -> each != element);
    }
    
    /**
     * Returns the new list from this list without the element at the index.
     */
    public default IntFuncList excludeAt(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("index: " + index);
        val first = limit(index);
        val tail = skip(index + 1);
        return IntFuncList.concat(first, tail);
    }
    
    /**
     * Returns the new list from this list without the `count` elements starting at `fromIndexInclusive`.
     */
    public default IntFuncList excludeFrom(int fromIndexInclusive, int count) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (count <= 0)
            throw new IndexOutOfBoundsException("count: " + count);
        val first = limit(fromIndexInclusive);
        val tail = skip(fromIndexInclusive + count);
        return IntFuncList.concat(first, tail);
    }
    
    /**
     * Returns the new list from this list without the element starting at `fromIndexInclusive` to `toIndexExclusive`.
     */
    public default IntFuncList excludeBetween(int fromIndexInclusive, int toIndexExclusive) {
        if (fromIndexInclusive < 0)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive);
        if (toIndexExclusive < 0)
            throw new IndexOutOfBoundsException("toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive > toIndexExclusive)
            throw new IndexOutOfBoundsException("fromIndexInclusive: " + fromIndexInclusive + ", toIndexExclusive: " + toIndexExclusive);
        if (fromIndexInclusive == toIndexExclusive)
            return this;
        val first = limit(fromIndexInclusive);
        val tail = skip(toIndexExclusive);
        return IntFuncList.concat(first, tail);
    }
    
    /**
     * Returns the sub list from the index starting `fromIndexInclusive` to `toIndexExclusive`.
     */
    public default IntFuncList subList(int fromIndexInclusive, int toIndexExclusive) {
        val length = toIndexExclusive - fromIndexInclusive;
        return skip(fromIndexInclusive).limit(length);
    }
    
    /**
     * Returns the new list with reverse order of this list.
     */
    @Eager
    public default IntFuncList reverse() {
        val length = size();
        if (length <= 1)
            return this;
        val array = toArray();
        val mid = length / 2;
        for (int i = 0; i < mid; i++) {
            val j = length - i - 1;
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableIntFuncList(array, array.length, mode());
    }
    
    /**
     * Returns the new list with random order of this list.
     */
    @Eager
    public default IntFuncList shuffle() {
        val length = size();
        if (length <= 1)
            return this;
        val array = toArray();
        val rand = new Random();
        for (int i = 0; i < length; i++) {
            val j = rand.nextInt(length);
            val temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return new ImmutableIntFuncList(array, array.length, mode());
    }
    
    // -- Query --
    /**
     * Returns the list of tuple of the index and the value for which the value match the predicate.
     */
    public default FuncList<IntIntTuple> query(IntPredicate check) {
        return mapToObjWithIndex((index, data) -> check.test(data) ? IntIntTuple.of(index, data) : null).filterNonNull();
    }
    
    /**
     * Returns the list of tuple of the index and the value for which the value match the predicate.
     */
    public default FuncList<IntIntTuple> query(IntAggregationToBoolean aggregation) {
        val check = aggregation.newAggregator();
        return query(check);
    }
    
    public default boolean isEmpty() {
        return !iterator().hasNext();
    }
    
    public default boolean contains(int value) {
        return intStream().anyMatch(i -> i == value);
    }
    
    @SuppressWarnings("resource")
    public default boolean containsAllOf(int... array) {
        return IntStreamPlus.of(array).allMatch(each -> intStream().anyMatch(value -> Objects.equals(each, value)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsSomeOf(int... c) {
        return IntStreamPlus.of(c).anyMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsNoneOf(int... c) {
        return IntStreamPlus.of(c).noneMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsAllOf(Collection<Integer> c) {
        return c.stream().allMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsSomeOf(Collection<Integer> c) {
        return c.stream().anyMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default boolean containsNoneOf(Collection<Integer> c) {
        return c.stream().noneMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsAllOf(IntFuncList c) {
        return c.intStream().allMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsSomeOf(IntFuncList c) {
        return c.intStream().anyMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    @SuppressWarnings("resource")
    public default boolean containsNoneOf(IntFuncList c) {
        return c.intStream().noneMatch(each -> intStream().anyMatch(o -> Objects.equals(each, o)));
    }
    
    public default int get(int index) {
        val ref = new int[1][];
        val found = IntStreamPlusHelper.hasAt(this.intStream(), index, ref);
        if (!found)
            throw new IndexOutOfBoundsException("" + index);
        return ref[0][0];
    }
    
    // -- Match --
    /**
     * Check if any element match the predicate
     */
    public default boolean anyMatch(IntPredicate predicate) {
        return intStream().anyMatch(predicate);
    }
    
    /**
     * Check if any element match the predicate
     */
    public default boolean anyMatch(IntAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return intStream().anyMatch(predicate);
    }
    
    /**
     * Check if all elements match the predicate
     */
    public default boolean allMatch(IntPredicate predicate) {
        return intStream().allMatch(predicate);
    }
    
    /**
     * Check if all elements match the predicate
     */
    public default boolean allMatch(IntAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return intStream().allMatch(predicate);
    }
    
    /**
     * Check if none of the elements match the predicate
     */
    public default boolean noneMatch(IntPredicate predicate) {
        return intStream().noneMatch(predicate);
    }
    
    /**
     * Check if none of the elements match the predicate
     */
    public default boolean noneMatch(IntAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return intStream().noneMatch(predicate);
    }
    
    /**
     * Returns the sequentially first element
     */
    public default OptionalInt findFirst() {
        return intStream().findFirst();
    }
    
    /**
     * Returns the sequentially first element
     */
    public default OptionalInt findFirst(IntPredicate predicate) {
        return intStream().filter(predicate).findFirst();
    }
    
    /**
     * Returns the sequentially first element
     */
    public default OptionalInt findFirst(IntAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return intStream().filter(predicate).findFirst();
    }
    
    /**
     * Returns the any element
     */
    public default OptionalInt findAny() {
        return intStream().findAny();
    }
    
    /**
     * Returns the sequentially first element
     */
    public default OptionalInt findAny(IntPredicate predicate) {
        return intStream().filter(predicate).findFirst();
    }
    
    /**
     * Returns the sequentially first element
     */
    public default OptionalInt findAny(IntAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return intStream().filter(predicate).findFirst();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt findLast() {
        return intStream().findLast();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt findLast(IntPredicate predicate) {
        return intStream().filter(predicate).findLast();
    }
    
    @Sequential
    @Terminal
    public default OptionalInt findLast(IntAggregationToBoolean aggregation) {
        val predicate = aggregation.newAggregator();
        return intStream().filter(predicate).findLast();
    }
}
