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
package functionalj.streamable;

import static functionalj.functions.TimeFuncs.Sleep;
import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.lens.LensTest.Car.theCar;
import static functionalj.list.FuncList.listOf;
import static functionalj.ref.Run.With;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static functionalj.stream.ZipWithOption.RequireBoth;
import static functionalj.streamable.Streamable.zipOf;
import static functionalj.streamable.intstreamable.IntStreamable.infiniteInt;
import static java.util.Arrays.asList;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.function.Func0;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.lens.Access;
import functionalj.lens.LensTest.Car;
import functionalj.list.FuncList;
import functionalj.list.FuncListDerived;
import functionalj.list.ImmutableList;
import functionalj.map.FuncMap;
import functionalj.promise.DeferAction;
import functionalj.result.NoMoreResultException;
import functionalj.stream.CollectorPlus;
import functionalj.stream.IncompletedSegment;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusUtils;
import functionalj.streamable.intstreamable.IntStreamable;
import lombok.val;


public class StreamableTest {
    
    private void assertStrings(String str, Object obj) {
        assertEquals(str, "" + obj);
    }
    
    private <T> void run(Streamable<T> streamable, FuncUnit1<Streamable<T>> action) {
        action.accept(streamable);
        action.accept(streamable);
    }
    
    private <T> void run(Streamable<T> streamable1, Streamable<T> streamable2, FuncUnit2<Streamable<T>, Streamable<T>> action) {
        action.accept(streamable1, streamable2);
        action.accept(streamable1, streamable2);
    }
    
    @Test
    public void testEmpty() {
        run(Streamable.empty(), streamable -> {
            assertStrings("[]", streamable.toList());
        });
    }
    
    @Test
    public void testEmptyStreamable() {
        run(Streamable.emptyStreamable(), streamable -> {
            assertStrings("[]", streamable.toList());
        });
    }
    
    @Test
    public void testOf() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.toList());
        });
    }
    
    @Test
    public void testStreamableOf() {
        run(Streamable.steamableOf("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.toList());
        });
    }
    
    @Test
    public void testEquals() {
        run(Streamable.steamableOf("One", "Two", "Three"),
            Streamable.steamableOf("One", "Two", "Three"),
            (streamable1, streamable2) -> {
            assertTrue(StreamPlusUtils.equals(streamable1, streamable2));
        });
    }
    
    @Test
    public void testFromStreamSupplier() {
        run(Streamable.from(() -> Stream.of("One", "Two", "Three")), streamable -> {
            assertStrings("[One, Two, Three]", streamable.toList());
        });
    }
    
    @Test
    public void testConcat() {
        run(Streamable.concat(
                Streamable.from(() -> Stream.of("One", "Two")),
                Streamable.from(() -> Stream.of("Three", "Four"))),
            streamable -> {
                assertStrings("[One, Two, Three, Four]", streamable.toList());
            }
        );
    }
    
    @Test
    public void testCombine() {
        run(Streamable.combine(
                Streamable.from(() -> Stream.of("One", "Two")),
                Streamable.from(() -> Stream.of("Three", "Four"))),
            streamable -> {
                assertStrings("[One, Two, Three, Four]", streamable.toList());
            }
        );
    }
    
    //-- Generate --
    
    @Test
    public void testGenerate() {
        run(Streamable.generateWith(() -> {
                val counter = new AtomicInteger();
                Func0<Integer> supplier = ()->{
                    int count = counter.getAndIncrement();
                    if (count < 5)
                        return count;
                    throw new NoMoreResultException();
                };
                return supplier;
            }),
            streamable -> {
                assertStrings("[0, 1, 2, 3, 4]", streamable.toListString());
            });
    }
    
    //-- Iterate + Compound --
    
    @Test
    public void testIterate() {
        run(Streamable.iterate(1, i -> i*2).limit(5), streamable -> {
            assertStrings("[1, 2, 4, 8, 16]", streamable.toListString());
        });
        run(Streamable.iterate(1, 1, (a, b) -> a + b).limit(5), streamable -> {
            assertStrings("[1, 1, 2, 3, 5]", streamable.toListString());
        });
    }
    
    @Test
    public void testCompound() {
        run(Streamable.compound(1, i -> i*2).limit(5), streamable -> {
            assertStrings("[1, 2, 4, 8, 16]", streamable.toListString());
        });
        run(Streamable.compound(1, 1, (a, b) -> a + b).limit(5), streamable -> {
            assertStrings("[1, 1, 2, 3, 5]", streamable.toListString());
        });
    }
    
    @Test
    public void testRepeat() {
        run(Streamable.repeat(1, 2).limit(11), streamable -> {
            assertStrings("[1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1]", streamable.toListString());
        });
        run(Streamable.repeat(listOf(1, 2)).limit(13), streamable -> {
            assertStrings("[1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1]", streamable.toListString());
        });
    }
    
    @Test
    public void testLoop() {
        run(Streamable.loop().limit(5), streamable -> {
            assertStrings("[null, null, null, null, null]", streamable.toListString());
        });
        run(Streamable.loop(6), streamable -> {
            assertStrings("[null, null, null, null, null, null]", streamable.toListString());
        });
    }
    
    //-- zipOf --
    
    @Test
    public void testZipOf_toTuple() {
        run(Streamable.of("A", "B", "C", "D", "E"),
            Streamable.of(1, 2, 3, 4),
            (streamable1, streamable2) -> {
                assertStrings("[(A,1), (B,2), (C,3), (D,4)]", zipOf(streamable1, streamable2).toListString());
        });
    }
    
    @Test
    public void testZipOf_merge() {
        run(Streamable.of("A", "B", "C", "D", "E"),
                Streamable.of(1, 2, 3, 4),
                (streamable1, streamable2) -> {
                    assertStrings("[A+1, B+2, C+3, D+4]", zipOf(streamable1, streamable2, (a, b) -> a + "+" + b).toListString());
            });
    }
//
//    @Test
//    public void testZipOf_merge_int() {
//        assertStrings("[5, 8, 9, 8, 5]",
//                zipOf(IntStream.of(1, 2, 3, 4, 5),
//                      IntStream.of(5, 4, 3, 2, 1),
//                      (a, b) -> a*b)
//                .toListString());
//    }
//
//    @Test
//    public void testZipOf_merge_int_with_default() {
//        assertStrings("[5, 8, 9, 8, 5, 0, 0, 0]",
//                zipOf(IntStream.of(1, 2, 3, 4, 5, 6, 7, 8),
//                      IntStream.of(5, 4, 3, 2, 1),
//                      0,
//                      (a, b) -> a*b)
//                .toListString());
//    }
//
//    @Test
//    public void testZipOf_merge_int_with_defaults() {
//        assertStrings("[5, 8, 9, 8, 5, 0, 0, 0]",
//                zipOf(IntStream.of(1, 2, 3, 4, 5, 6, 7, 8), 1,
//                      IntStream.of(5, 4, 3, 2, 1), 0,
//                      (a, b) -> a*b)
//                .toListString());
//
//        assertStrings("[5, 8, 9, 8, 5, 6, 7, 8]",
//                zipOf(IntStream.of(1, 2, 3, 4, 5), 1,
//                      IntStream.of(5, 4, 3, 2, 1, 6, 7, 8), 0,
//                      (a, b) -> a*b)
//                .toListString());
//    }
    
    @Test
    public void testIterator() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            val iterator = streamable.iterator();
            
            assertTrue(iterator.hasNext());
            assertTrue("One".equals(iterator.next()));
            
            assertTrue(iterator.hasNext());
            assertTrue("Two".equals(iterator.next()));
            
            assertTrue(iterator.hasNext());
            assertTrue("Three".equals(iterator.next()));
        });
    }
    
    @Test
    public void testMap() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("[3, 3, 5, 4, 4]",
                    streamable
                    .map(s -> s.length())
                    .toList());
        });
    }
    
    @Test
    public void testMapToInt() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("11", streamable.mapToInt(String::length).sum());
        });
    }
    
//    @Test
//    public void testMapToLong() {
//        val stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("11", stream.mapToLong(String::length).sum());
//    }
//
//    @Test @Ignore
//    public void testMapToDouble() {
//        val stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("11.0", stream.mapToDouble(s -> s.length()*1.0).sum());
//    }
    
    //-- FlatMap --
    
    @Test
    public void testFlatMap() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[3, 3, 5]", streamable.flatMap(s -> Streamable.of(s.length())).toList());
        });
    }
    
    @Test
    public void testFlatMapToInt() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[3, 3, 5]", streamable.flatMapToInt(s -> IntStreamable.of(s.length())).toList());
        });
    }
    
//    @Test @Ignore
//    public void testFlatMapToLong() {
////        val stream = StreamPlus.of("One", "Two", "Three");
////        assertStrings("[3, 3, 5]", stream.flatMapToLong(s -> LongStreamPlus.of((long)s.length())).toList()));
//    }
//
//    @Test @Ignore
//    public void testFlatMapToDouble() {
////        val stream = StreamPlus.of("One", "Two", "Three");
////        assertStrings("[3, 3, 5]", stream.flatMapToDouble(s -> DoubleStreamPlus.of((double)s.length())).toList());
//    }
    
    //-- Filter --
    
    @Test
    public void testFilter() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[Three]", streamable.filter(s -> s.length() > 4).toList());
        });
    }
    
    @Test
    public void testPeek() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val logs   = new ArrayList<String>();
            assertStrings("[One, Two, Three]", streamable.peek(s -> logs.add(s)).toList());
            assertStrings("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testLimit() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.limit(3).toList());
        });
    }
    
    @Test
    public void testSkip() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("[Three, Four, Five]", streamable.skip(2).toList());
        });
    }
    
    @Test
    public void testDistinct() {
        run(Streamable.of("One", "Two", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.distinct().toList());
        });
    }
    
    @Test
    public void testSorted() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("[3, 3, 4, 4, 5]", streamable.map(theString.length()).sorted().toList());
            assertStrings("[5, 4, 4, 3, 3]", streamable.map(theString.length()).sorted((a, b) -> (b - a)).toList());
        });
    }
    
    @Test
    public void testForEach() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val logs   = new ArrayList<String>();
            streamable.forEach(s -> logs.add(s));
            assertStrings("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testForEachOrdered() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val logs   = new ArrayList<String>();
            streamable.forEachOrdered(s -> logs.add(s));
            assertStrings("[One, Two, Three]", logs);
        });
    }
    
    @Test
    public void testReduce() {
        run(Streamable.of(1, 2, 3), streamable -> {
            assertEquals(6, streamable.reduce(0, (a, b) -> a + b).intValue());
            
            assertEquals(6, streamable.reduce((a, b) -> a + b).get().intValue());
            
            assertEquals(6, streamable.reduce(
                                        BigInteger.ZERO,
                                        (b, i) -> b.add(BigInteger.valueOf((long)i)),
                                        (a, b) -> a.add(b)).intValue());
        });
    }
    
    @Test
    public void testCollect() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.collect(toList()));
            
            Supplier<StringBuffer> supplier = StringBuffer::new;
            BiConsumer<StringBuffer, String> accumulator = StringBuffer::append;
            BiConsumer<StringBuffer, StringBuffer> combiner = (a, b) -> a.append(b.toString());
            assertStrings("OneTwoThree", streamable.collect(supplier, accumulator, combiner));
        });
    }
    
    @Test
    public void testMinMax() {
        run(Streamable.of("One", "Two", "Three", "Four"), streamable -> {
            assertStrings("Optional[One]",   streamable.min((a, b)-> a.length()-b.length()));
            assertStrings("Optional[Three]", streamable.max((a, b)-> a.length()-b.length()));
        });
    }
    
    @Test
    public void testCount() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("3", streamable.count());
        });
    }
    
    @Test
    public void testAnyMatch() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertTrue(streamable.anyMatch("One"::equals));
        });
    }
    
    @Test
    public void testAllMatch() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertFalse(streamable.allMatch("One"::equals));
        });
    }
    
    @Test
    public void testNoneMatch() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertTrue(streamable.noneMatch("Five"::equals));
        });
    }
    
    @Test
    public void testFindFirst() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("Optional[One]", streamable.findFirst());
        });
    }
    
    @Test
    public void testFindAny() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("Optional[One]", streamable.findAny());
        });
    }
    
    @Test
    public void testToArray() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", Arrays.toString(streamable.map(s -> s).toArray()));
            assertStrings("[One, Two, Three]", Arrays.toString(streamable.map(s -> s).toArray(n -> new String[n])));
        });
    }
    
    //-- AsStreamPlusWithConversion --
    
    @Test
    public void testToByteArray() {
        run(Streamable.of('A', 'B', 'C', 'D'), streamable -> {
            assertStrings("[65, 66, 67, 68]", Arrays.toString(streamable.toByteArray(c -> (byte)(int)c)));
        });
    }
    
    @Test
    public void testToIntArray() {
        run(Streamable.of('A', 'B', 'C', 'D'), streamable -> {
            assertStrings("[65, 66, 67, 68]", Arrays.toString(streamable.toIntArray(c -> (int)c)));
        });
    }
    
//    @Test
//    public void testToLongArray() {
//        val stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65, 66, 67, 68]", Arrays.toString(stream.toLongArray(c -> (long)c)));
//    }
//
//    @Test @Ignore
//    public void testToDoubleArray() {
//        val stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(stream.toDoubleArray(c -> (double)(int)c)));
//    }
    
    @Test
    public void testToArrayList() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val list   = streamable.toArrayList();
            assertStrings("[One, Two, Three]", list);
            assertTrue(list instanceof ArrayList);
        });
    }
    
    @Test
    public void testToFuncList() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val list   = streamable.toFuncList();
            assertStrings("[One, Two, Three]", list.toString());
            assertTrue(list instanceof FuncList);
        });
    }
    
    @Test
    public void testToImmutableList() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val list   = streamable.toImmutableList();
            assertStrings("[One, Two, Three]", list.toString());
            assertTrue(list instanceof ImmutableList);
        });
    }
    
    @Test
    public void testToJavaList() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val list   = streamable.toJavaList();
            assertStrings("[One, Two, Three]", list.toString());
            assertTrue(list instanceof List);
        });
    }
    
    @Test
    public void testToList() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val list   = streamable.toJavaList();
            assertStrings("[One, Two, Three]", list.toString());
            assertTrue(list instanceof List);
        });
    }
    
    @Test
    public void testToMutableList() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val list   = streamable.toMutableList();
            assertStrings("[One, Two, Three]", list);
            // This is because we use ArrayList as mutable list ... not it should not always be.
            assertTrue(list instanceof ArrayList);
        });
    }
    
    //-- join --
    
    @Test
    public void testJoin() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("OneTwoThree", streamable.join());
        });
    }
    
    @Test
    public void testJoin_withDelimiter() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("One, Two, Three", streamable.join(", "));
        });
    }
    
    @Test
    public void testToListString() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.toListString());
        });
    }
    
    //-- toMap --
    
    @Test
    public void testToMap() {
        run(Streamable.of("One", "Three", "Five"), streamable -> {
            assertStrings("{3:One, 4:Five, 5:Three}", streamable.toMap(theString.length()).toString());
        });
    }
    
    @Test
    public void testToMap_withValue() {
        run(Streamable.of("One", "Three", "Five"), streamable -> {
            assertStrings("{3:-->One, 4:-->Five, 5:-->Three}", streamable.toMap(theString.length(), theString.withPrefix("-->")).toString());
        });
    }
    
    @Test
    public void testToMap_withMappedMergedValue() {
        run(Streamable.of("One", "Two", "Three", "Five"), streamable -> {
            assertStrings("{3:One+Two, 4:Five, 5:Three}", streamable.toMap(theString.length(), theString, (a, b) -> a + "+" + b).toString());
        });
    }
    
    @Test
    public void testToMap_withMergedValue() {
        run(Streamable.of("One", "Two", "Three", "Five"), streamable -> {
            assertStrings("{3:One+Two, 4:Five, 5:Three}", streamable.toMap(theString.length(), (a, b) -> a + "+" + b).toString());
        });
    }
    
    @Test
    public void testToSet() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val set    = streamable.toSet();
            assertStrings("[One, Two, Three]", set);
            assertTrue(set instanceof Set);
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            val logs   = new ArrayList<String>();
            streamable.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
            assertStrings("[0:One, 1:Two, 2:Three]", logs);
        });
    }
    
    @Test
    public void testPopulateArray() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            val array  = new String[5];
            streamable.populateArray(array);
            assertStrings("[One, Two, Three, Four, Five]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffset() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            val array  = new String[3];
            streamable.populateArray(array, 2);
            assertStrings("[null, null, One]", Arrays.toString(array));
        });
    }
    
    @Test
    public void testPopulateArray_withOffsetLength() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            val array  = new String[5];
            streamable.populateArray(array, 1, 3);
            assertStrings("[null, One, Two, Three, null]", Arrays.toString(array));
        });
    }
    
    //-- AsStreamableWithMatch --
    
    @Test
    public void testFindFirst_withPredicate() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[Three]", streamable.findFirst(theString.thatContains("ee")));
        });
    }
    
    @Test
    public void testFindAny_withPredicate() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[Three]", streamable.findAny(theString.thatContains("ee")));
        });
    }
    
    @Test
    public void testFindFirst_withMapper_withPredicate() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[Three]", streamable.findFirst(theString.length(), l ->  l == 5));
        });
    }
    
    @Test
    public void testFindAny_withMapper_withPredicate() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[Three]", streamable.findAny(theString.length(), l -> l == 5));
        });
    }
    
    //-- AsStreamableWithStatistic --
    
    @Test
    public void testSize() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("3", streamable.size());
        });
    }
    
    @Test
    public void testMinBy() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[One]", streamable.minBy(theString.length()));
        });
    }
    
    @Test
    public void testMaxBy() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[Three]", streamable.maxBy(theString.length()));
        });
    }
    
    @Test
    public void testMinBy_withMapper() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[Three]", streamable.minBy(theString.length(), (a, b)->b-a));
        });
    }
    
    @Test
    public void testMaxBy_withMapper() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("Optional[One]", streamable.maxBy(theString.length(), (a, b)->b-a));
        });
    }
    
    @Test
    public void testMinMaxBy() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertStrings("(Optional[Five],Optional[Two])", streamable.minMax(String.CASE_INSENSITIVE_ORDER));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper() {
        run(Streamable.of("One", "Two", "Three", "Four"), streamable -> {
            assertStrings("(Optional[One],Optional[Three])", streamable.minMaxBy(theString.length()));
        });
    }
    
    @Test
    public void testMinMaxBy_withMapper_withComparator() {
        run(Streamable.of("One", "Two", "Three", "Four"), streamable -> {
            assertStrings("(Optional[Three],Optional[Two])", streamable.minMaxBy(theString.length(), (a, b) -> b-a));
        });
    }
    
    //-- StreamPlusWithCalculate --
    
    static class SumLength implements CollectorPlus<String, int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { 0 }; }
        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] += s.length(); }; }
        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { a1[0] + a1[1] }; }
        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
        @Override public Collector<String, int[], Integer> collector() { return this; }
    }
    static class AvgLength implements CollectorPlus<String, int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { 0, 0 }; }
        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] += s.length(); a[1]++; }; }
        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]/a[1]; }
        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
        @Override public Collector<String, int[], Integer> collector() { return this; }
    }
    static class MinLength implements CollectorPlus<String, int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { Integer.MAX_VALUE }; }
        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] = Math.min(a[0], s.length()); }; }
        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { Math.min(a1[0], a2[0]) }; }
        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
        @Override public Collector<String, int[], Integer> collector() { return this; }
    }
    static class MaxLength implements CollectorPlus<String, int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { Integer.MIN_VALUE }; }
        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] = Math.max(a[0], s.length()); }; }
        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { Math.max(a1[0], a2[0]) }; }
        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
        @Override public Collector<String, int[], Integer> collector() { return this; }
    }
    static class Sum implements CollectorPlus<Integer, int[], Integer> {
        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
        @Override public Supplier<int[]>            supplier()          { return ()->new int[] { 0 }; }
        @Override public BiConsumer<int[], Integer> accumulator()       { return (a, e)->{ a[0] += e.intValue(); }; }
        @Override public BinaryOperator<int[]>      combiner()          { return (a1, a2) -> new int[] { a1[0] + a1[1] }; }
        @Override public Function<int[], Integer>   finisher()          { return a -> a[0]; }
        @Override public Set<Characteristics>       characteristics()   { return characteristics; }
        @Override public Collector<Integer, int[], Integer> collector() { return this; }
    }
    
    @Test
    public void testCalculate() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            assertEquals(18, streamable.calculate(sumLength).intValue());
        });
    }
    
    @Test
    public void testCalculate2() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            assertEquals("(18,4)", streamable.calculate(sumLength, avgLength).toString());
        });
    }
    
    @Test
    public void testCalculate2_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val range = streamable.calculate(maxLength, minLength).mapTo((max, min) -> max - min).intValue();
            assertEquals(3, range);
        });
    }
    
    @Test
    public void testCalculate3() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            assertEquals("(18,4,3)", streamable.calculate(sumLength, avgLength, minLength).toString());
        });
    }
    
    @Test
    public void testCalculate3_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val value     = streamable
                            .calculate(sumLength, avgLength, minLength)
                            .mapTo((sum, avg, min) -> "sum: " + sum + ", avg: " + avg + ", min: " + min);
            assertEquals("sum: 18, avg: 4, min: 3", value);
        });
    }
    
    @Test
    public void testCalculate4() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            assertEquals("(18,4,3,6)", streamable.calculate(sumLength, avgLength, minLength, maxLength).toString());
        });
    }
    
    @Test
    public void testCalculate4_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val value     = streamable
                            .calculate(sumLength, avgLength, minLength, maxLength)
                            .mapTo((sum, avg, min, max) -> "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max);
            assertEquals("sum: 18, avg: 4, min: 3, max: 6", value);
        });
    }
    
    @Test
    public void testCalculate5() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            assertEquals("(18,4,3,6,18)", streamable.calculate(sumLength, avgLength, minLength, maxLength, sumLength).toString());
        });
    }
    
    @Test
    public void testCalculate5_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val value     = streamable
                            .calculate(sumLength, avgLength, minLength, maxLength, sumLength)
                            .mapTo((sum, avg, min, max, sum2) -> {
                                return "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max + ", sum2: " + sum2;
                            });
            assertEquals("sum: 18, avg: 4, min: 3, max: 6, sum2: 18", value);
        });
    }
    
    @Test
    public void testCalculate6() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            assertEquals("(18,4,3,6,18,4)", streamable.calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength).toString());
        });
    }
    
    @Test
    public void testCalculate6_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sumLength = new SumLength();
            val avgLength = new AvgLength();
            val minLength = new MinLength();
            val maxLength = new MaxLength();
            val value     = streamable
                            .calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength)
                            .mapTo((sum, avg, min, max, sum2, avg2) -> {
                                return "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max + ", sum2: " + sum2 + ", avg2: " + avg2;
                            });
            assertEquals("sum: 18, avg: 4, min: 3, max: 6, sum2: 18, avg2: 4", value);
        });
    }
    
    @Test
    public void testCalculate_of() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val sum = new Sum();
            assertEquals(18, streamable.calculate(sum.of(theString.length())).intValue());
        });
    }
    
    @Test
    public void testConcatWith() {
        run(Streamable.of("One", "Two"), Streamable.of("Three", "Four"), (streamable1, streamabl2) -> {
            assertStrings("[One, Two, Three, Four]",
                    streamable1.concatWith(streamabl2)
                    .toList());
        });
    }
        
    @Test
    public void testMerge() {
        run(Streamable.of("A", "B", "C"),
            IntStreamable.infinite().boxed().map(Access.theInteger.asString()),
            (streamable1, streamabl2) -> {
        assertEquals(
                "A, 0, B, 1, C, 2, 3, 4, 5, 6",
                streamable1
                    .mergeWith(streamabl2)
                    .limit    (10)
                    .join     (", "));
        });
    }
    
    @Test
    public void testZipWith() {
        run(Streamable.of("A", "B", "C"),
                IntStreamable.infinite().limit(10).boxed().map(theInteger.asString()),
                (streamableA, streamableB) -> {
            assertEquals(
                    "(A,0), (B,1), (C,2)",
                    streamableA.zipWith(streamableB).join(", "));
        });
        run(Streamable.of("A", "B", "C"),
                IntStreamable.infinite().boxed().map(theInteger.asString()),
                (streamableA, streamableB) -> {
            assertEquals(
                    "(A,0), (B,1), (C,2)",
                    streamableA.zipWith(streamableB, RequireBoth).join(", "));
        });
        run(Streamable.of("A", "B", "C"),
                IntStreamable.infinite().limit(10).boxed().map(theInteger.asString()),
                (streamableA, streamableB) -> {
            assertEquals(
                    "A:0, B:1, C:2",
                    streamableA.zipWith(streamableB, (c, i) -> c + ":" + i).join(", "));
        });
        run(Streamable.of("A", "B", "C"),
                IntStreamable.infinite().boxed().map(theInteger.asString()),
                (streamableA, streamableB) -> {
            assertEquals(
                    "(A,0), (B,1), (C,2), (null,3), (null,4)",
                    streamableA.zipWith(streamableB, AllowUnpaired).limit(5).join(", "));
        });
    }
    
    @Test
    public void testChoose() {
        run(Streamable.of("A", "B", "C"),
                IntStreamable.infinite().boxed().map(theInteger.asString()),
                (streamableA, streamableB) -> {
            val bool = new AtomicBoolean(true);
            assertEquals("A, 1, C", streamableA.choose(streamableB, (a, b) -> {
                boolean curValue = bool.get();
                return bool.getAndSet(!curValue);
            }).limit(5).join(", "));
        });
    }
    
    @Test
    public void testChoose_AllowUnpaired() {
        run(Streamable.of("A", "B", "C"),
                IntStreamable.infinite().boxed().map(theInteger.asString()),
                (streamableA, streamableB) -> {
            val bool    = new AtomicBoolean(true);
            assertEquals("A, 1, C, 3, 4, 5, 6", streamableA.choose(streamableB, AllowUnpaired, (a, b) -> {
                boolean curValue = bool.get();
                return bool.getAndSet(!curValue);
            }).limit(7).join(", "));
        });
    }
    
    //-- StreamPlusWithFillNull --
    
    @Test
    public void testFillNull() {
        run(Streamable.of("A", "B",  null, "C"), streamable -> {
            assertEquals("[A, B, Z, C]", streamable.fillNull("Z").toListString());
        });
    }
    
    @Test
    public void testFillNull_lens() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
                    streamable.fillNull(Car.theCar.color, "Black").toListString());
        });
    }
    
    @Test
    public void testFillNull_getter_setter() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
                    streamable.fillNull(
                            (Car car)               -> car.color(),
                            (Car car, String color) -> car.withColor(color),
                            "Black").toListString());
        });
    }
    
    @Test
    public void testFillNull_lens_supplier() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
                    streamable.fillNullWith(Car.theCar.color, () -> "Black").toListString());
        });
    }
    
    @Test
    public void testFillNull_getter_setter_supplier() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
                    streamable.fillNullWith(
                            (Car car)               -> car.color(),
                            (Car car, String color) -> car.withColor(color),
                            ()                      -> "Black").toListString());
        });
    }
    
    @Test
    public void testFillNull_lens_function() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
                    streamable.fillNullBy(Car.theCar.color, (Car car) -> "Black").toListString());
        });
    }
    
    @Test
    public void testFillNull_getter_setter_function() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
                    streamable.fillNullBy(
                            (Car car)               -> car.color(),
                            (Car car, String color) -> car.withColor(color),
                            (Car car)               -> "Black").toListString());
        });
    }
    
    //-- StreamPlusWithFilter --
    
    @Test
    public void testFilterClass() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            assertStrings("[One, Three, Five]", streamable.filter(String.class).toListString());
        });
    }
    
    @Test
    public void testFilterClass_withPredicate() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            assertStrings("[One, Five]", streamable.filter(String.class, theString.length().thatLessThan(5)).toListString());
        });
    }
    
    @Test
    public void testFilterAsInt() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertEquals("[Three, Four, Five]", streamable.filterAsInt(str -> str.length(), i -> i >= 4).toListString());
        });
    }
    
    @Test
    public void testFilterAsLong() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertEquals("[Three, Four, Five]", streamable.filterAsLong(str -> (long)str.length(), i -> i >= 4).toListString());
        });
    }
    
    @Test
    public void testFilterAsDouble() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertEquals("[Three, Four, Five]", streamable.filterAsDouble(str -> (double)str.length(), i -> i >= 4).toListString());
        });
    }
    
    @Test
    public void testFilterAsObject() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertEquals("[Three, Four, Five]", streamable.filterAsObject(str -> BigInteger.valueOf(str.length()), b -> b.intValue() >= 4).toListString());
        });
    }
    
    @Test
    public void testFilterWithIndex() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
            assertEquals("[Four, Five]", streamable.filterWithIndex((index, str) -> index > 2 && !str.startsWith("T")).toListString());
        });
    }
    
    @Test
    public void testFilterNonNull() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Blue, Green, Red]",
                    streamable.map(theCar.color).filterNonNull().toListString());
        });
    }
    
    @Test
    public void testFilterNonNull_withMapper() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
                    streamable.filterNonNull(theCar.color).toListString());
        });
    }
    
    @Test
    public void testExcludeNull() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Blue, Green, Red]",
                    streamable.map(theCar.color).excludeNull().toListString());
        });
    }
    
    @Test
    public void testExcludeNull_withMapper() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
                    streamable.excludeNull(theCar.color).toListString());
        });
    }
    
    @Test
    public void testFilterIn() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Blue, Red]",
                    streamable.map(theCar.color).filterIn("Blue", "Red").toListString());
        });
    }
    
    @Test
    public void testFilterIn_collection() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Blue, Red]",
                    streamable.map(theCar.color).filterIn(asList("Blue", "Red")).toListString());
        });
    }
    
    @Test
    public void testExclude() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Car(color=Blue), Car(color=null)]",
                    streamable.exclude(theCar.color.toLowerCase().thatContains("r")).toListString());
        });
    }
    
    @Test
    public void testExcludeIn() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Green, null]",
                    streamable.map(theCar.color).excludeIn("Blue", "Red").toListString());
        });
    }
    
    @Test
    public void testExcludeIn_collection() {
        run(Streamable.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red")), streamable -> {
            assertEquals(
                    "[Green, null]",
                    streamable.map(theCar.color).excludeIn(asList("Blue", "Red")).toListString());
        });
    }
    
    //-- StreamableWithFlatMap --
    
    @Test
    public void testFlatMapToObj() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[3, 3, 5]", streamable.flatMapToObj(s -> Streamable.of(s.length())).toList());
        });
    }
    
    @Test
    public void testFlatMapOnly() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, 3, 5]", streamable.flatMapOnly(str -> str.toLowerCase().startsWith("t"), s -> Streamable.of("" + s.length())).toList());
        });
    }
    
    @Test
    public void testFlatMapIf() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[(One), [3], [5]]", streamable.flatMapIf(str -> str.toLowerCase().startsWith("t"), s -> Streamable.of("[" + s.length() + "]"), s -> Streamable.of("(" + s + ")")).toList());
        });
    }
    
    @Test
    public void testFlatMapToObjIf() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[(One), [3], [5]]", streamable.flatMapToObjIf(str -> str.toLowerCase().startsWith("t"), s -> Streamable.of("[" + s.length() + "]"), s -> Streamable.of("(" + s + ")")).toList());
        });
    }
    
    //-- StreamPlusWithLimit --
    
    @Test
    public void testSkipLimitLong() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[Two]", streamable.skip((Long)1L).limit((Long)1L).toList());
        });
    }
    
    @Test
    public void testSkipLimitLongNull() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.skip(null).limit(null).toList());
        });
    }
    
    @Test
    public void testSkipLimitLongMinus() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[One, Two, Three]", streamable.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)).toList());
        });
    }
    
    @Test
    public void testSkipWhile() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            assertStrings("[3, 4, 5, 4, 3, 2, 1]",       streamable.skipWhile(i -> i < 3).toList());
            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", streamable.skipWhile(i -> i > 3).toList());
        });
    }
    
    @Test
    public void testSkipUntil() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            assertStrings("[4, 5, 4, 3, 2, 1]",          streamable.skipUntil(i -> i > 3).toList());
            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", streamable.skipUntil(i -> i < 3).toList());
        });
    }
    
    @Test
    public void testTakeWhile() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            val list = new ArrayList<Integer>();
            assertStrings("[1, 2, 3]",    streamable.peek(list::add).takeWhile(i -> i < 4).toList());
            assertStrings("[1, 2, 3, 4]", list);
            //                       ^--- Because it needs 4 to do the check in `takeWhile`
            
            list.clear();
            assertStrings("[]", streamable.peek(list::add).takeWhile(i -> i > 4).toList());
            assertStrings("[1]", list);
            //              ^--- Because it needs 1 to do the check in `takeWhile`
        });
    }
    
    @Test
    public void testTakeWhile_previous() {
        run(Streamable.of(1, 2, 3, 4, 6, 4, 3, 2, 1), streamable -> {
            assertStrings("[1, 2, 3, 4]", streamable.takeWhile((a, b) -> b == a + 1).toListString());
        });
    }
    
    @Test
    public void testTakeUtil() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            val list = new ArrayList<Integer>();
            assertStrings("[1, 2, 3, 4]", streamable.peek(list::add).takeUntil(i -> i > 4).toList());
            assertStrings("[1, 2, 3, 4, 5]", list);
            //                          ^--- Because it needs 5 to do the check in `takeUntil`
            
            list.clear();
            assertStrings("[]",  streamable.peek(list::add).takeUntil(i -> i < 4).toList());
            assertStrings("[1]", list);
            //              ^--- Because it needs 1 to do the check in `takeUntil`
        });
    }
    
    @Test
    public void testTakeUntil_previous() {
        run(Streamable.of(1, 2, 3, 4, 6, 4, 3, 2, 1), streamable -> {
            assertStrings("[1, 2, 3, 4]", streamable.takeUntil((a, b) -> b > a + 1).toListString());
        });
    }
    
    @Test
    public void testDropAfter() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            assertStrings("[1, 2, 3, 4]", streamable.dropAfter(i -> i == 4).toListString());
            //                       ^--- Include 4
        });
    }
    
    @Test
    public void testDropAfter_previous() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            assertStrings("[1, 2, 3, 4, 5, 4]", streamable.dropAfter((a, b) -> b < a).toListString());
            //                             ^--- Include 4
        });
    }
    
    @Test
    public void testSkipTake() {
        run(Streamable.of(1, 2, 3, 4, 5, 4, 3, 2, 1), streamable -> {
            val list = new ArrayList<Integer>();
            assertStrings("[3, 4, 5, 4, 3]", streamable.peek(list::add).skipWhile(i -> i < 3).takeUntil(i -> i < 3).toList());
            assertStrings("[1, 2, 3, 4, 5, 4, 3, 2]", list);
            //              ^--^-----------------^--- Because it needs these number to do the check in `skipWhile` and `takeWhile`
        });
    }
    
    //-- StreamPlusWithMap --
    
    @Test
    public void testMapToObj() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[=3=, =3=, =5=]",
                    streamable
                    .mapToObj(s -> "=" + s.length() + "=")
                    .toList());
        });
    }
    
    @Test
    public void testMapOnly() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[ONE, TWO, Three]",
                    streamable
                    .mapOnly(
                            $S.length().thatLessThan(4),
                            $S.toUpperCase())
                    .toList());
        });
    }
    
    @Test
    public void testMapIf() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[ONE, TWO, three]",
                    streamable
                    .mapIf(
                            $S.length().thatLessThan(4), $S.toUpperCase(),
                            $S.toLowerCase())
                    .toList());
        });
    }
    
    @Test
    public void testMapToObjIf() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("[ONE, TWO, three]",
                    streamable
                    .mapToObjIf(
                            $S.length().thatLessThan(4), $S.toUpperCase(),
                            $S.toLowerCase())
                    .toList());
        });
    }
    
    @Test
    public void testMapFirst_2() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings("[ONE, TWO, three, four, five, SIX, seven, eight, nine, TEN, eleven, twelve]",
                    streamable
                    .mapFirst(
                            str -> str.length() == 3 ? str.toUpperCase() : null,
                            str -> str.toLowerCase())
                    .toListString());
        });
    }
    
    @Test
    public void testMapFirst_3() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings("[ONE, TWO, Three, four, five, SIX, Seven, Eight, nine, TEN, Eleven, Twelve]",
                    streamable
                    .mapFirst(
                            str -> str.length() == 3 ? str.toUpperCase() : null,
                            str -> str.length() == 4 ? str.toLowerCase() : null,
                            str -> str)
                    .toListString());
        });
    }
    
    @Test
    public void testMapFirst_4() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, Eleven, Twelve]",
                    streamable
                    .mapFirst(
                            str -> str.length() == 3 ? str.toUpperCase() : null,
                            str -> str.length() == 4 ? str.toLowerCase() : null,
                            str -> str.length() == 5 ? "(" + str + ")": null,
                            str -> str)
                    .toListString());
        });
    }
    
    @Test
    public void testMapFirst_5() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, [Eleven], Twelve]",
                    streamable
                    .mapFirst(
                            str -> str.length() == 3 ? str.toUpperCase() : null,
                            str -> str.length() == 4 ? str.toLowerCase() : null,
                            str -> str.length() == 5 ? "(" + str + ")": null,
                            str -> str.length() == 6 && !str.contains("w")? "[" + str + "]": null,
                            str -> str)
                    .toListString());
        });
    }
    
    @Test
    public void testMapFirst_6() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings("[ONE, TWO, (Three), four, -- Five --, -- Six --, (Seven), -- Eight --, -- Nine --, TEN, [Eleven], Twelve]",
                    streamable
                    .mapFirst(
                            str -> str.contains("i") ? "-- " + str + " --" : null,
                            str -> str.length() == 3 ? str.toUpperCase() : null,
                            str -> str.length() == 4 ? str.toLowerCase() : null,
                            str -> str.length() == 5 ? "(" + str + ")": null,
                            str -> str.length() == 6 && !str.contains("w") ? "[" + str + "]": null,
                            str -> str)
                    .toListString());
        });
    }
    
    @Test
    public void testMapThen_2() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
        assertStrings(
                "[O-n, T-w, T-h, F-o, F-i]",
                streamable
                .mapThen(
                        $S.charAt(0),
                        $S.charAt(1),
                        (a, b) -> a + "-" + b)
                .toListString());
        });
    }
    
    @Test
    public void testMapThen_3() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five"), streamable -> {
        assertStrings(
                "[O-n-e, T-w-o, T-h-r, F-o-u, F-i-v]",
                streamable
                .mapThen(
                        $S.charAt(0),
                        $S.charAt(1),
                        $S.charAt(2),
                        (a, b, c) -> a + "-" + b + "-" + c)
                .toListString());
        });
    }
    
    @Test
    public void testMapThen_4() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings(
                    "[T-h-r-e, F-o-u-r, F-i-v-e, S-e-v-e, E-i-g-h, N-i-n-e, E-l-e-v, T-w-e-l]",
                    streamable
                        .filter($S.length().thatGreaterThanOrEqualsTo(4))
                        .mapThen(
                            $S.charAt(0),
                            $S.charAt(1),
                            $S.charAt(2),
                            $S.charAt(3),
                            (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d)
                        .toListString());
        });
    }
    
    @Test
    public void testMapThen_5() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings(
                    "[T-h-r-e-e, S-e-v-e-n, E-i-g-h-t, E-l-e-v-e, T-w-e-l-v]",
                    streamable
                        .filter($S.length().thatGreaterThanOrEqualsTo(5))
                        .mapThen(
                            $S.charAt(0),
                            $S.charAt(1),
                            $S.charAt(2),
                            $S.charAt(3),
                            $S.charAt(4),
                            (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e)
                        .toListString());
        });
    }
    
    @Test
    public void testMapThen_6() {
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve"), streamable -> {
            assertStrings(
                    "[E-l-e-v-e-n, T-w-e-l-v-e]",
                    streamable
                        .filter($S.length().thatGreaterThanOrEqualsTo(6))
                        .mapThen(
                            $S.charAt(0),
                            $S.charAt(1),
                            $S.charAt(2),
                            $S.charAt(3),
                            $S.charAt(4),
                            $S.charAt(5),
                            (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f)
                        .toListString());
        });
    }
    
    //-- StreamableWithMapToMap --
    
    @Test
    public void testMapToMap_1() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:O}, "
                    + "{<1>:T}, "
                    + "{<1>:F}, "
                    + "{<1>:S}, "
                    + "{<1>:E}, "
                    + "{<1>:T}, "
                    + "{<1>:S}]",
                    streamable
                        .filter($S.length().thatGreaterThanOrEqualsTo(1))
                        .mapToMap(
                                "<1>", $S.charAt(0))
                        .map(map -> map.sorted())
                        .toListString());
        });
    }
    
    @Test
    public void testMapToMap_2() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:O, <2>:n}, "
                    + "{<1>:T, <2>:h}, "
                    + "{<1>:F, <2>:i}, "
                    + "{<1>:S, <2>:e}, "
                    + "{<1>:E, <2>:l}, "
                    + "{<1>:T, <2>:h}, "
                    + "{<1>:S, <2>:e}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(2))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_3() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:O, <2>:n, <3>:e}, "
                    + "{<1>:T, <2>:h, <3>:r}, "
                    + "{<1>:F, <2>:i, <3>:v}, "
                    + "{<1>:S, <2>:e, <3>:v}, "
                    + "{<1>:E, <2>:l, <3>:e}, "
                    + "{<1>:T, <2>:h, <3>:i}, "
                    + "{<1>:S, <2>:e, <3>:v}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(3))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_4() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:T, <2>:h, <3>:r, <4>:e}, "
                    + "{<1>:F, <2>:i, <3>:v, <4>:e}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e}, "
                    + "{<1>:E, <2>:l, <3>:e, <4>:v}, "
                    + "{<1>:T, <2>:h, <3>:i, <4>:r}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(4))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_5() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:T, <2>:h, <3>:r, <4>:e, <5>:e}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}, "
                    + "{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e}, "
                    + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(5))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_6() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e, <6>:n}, "
                    + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(6))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4),
                            "<6>", $S.charAt(5))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_7() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(7))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4),
                            "<6>", $S.charAt(5),
                            "<7>", $S.charAt(6))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_8() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e, <8>:n}, "
                    + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(8))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4),
                            "<6>", $S.charAt(5),
                            "<7>", $S.charAt(6),
                            "<8>", $S.charAt(7))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_9() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen"), streamable -> {
            assertStrings(
                    "[{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e, <9>:n}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(9))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4),
                            "<6>", $S.charAt(5),
                            "<7>", $S.charAt(6),
                            "<8>", $S.charAt(7),
                            "<9>", $S.charAt(8))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_10() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen", "Nineteen", "Twenty-three"), streamable -> {
            assertStrings(
                    "[{<10>:r, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(10))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4),
                            "<6>", $S.charAt(5),
                            "<7>", $S.charAt(6),
                            "<8>", $S.charAt(7),
                            "<9>", $S.charAt(8),
                            "<10>", $S.charAt(9))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    @Test
    public void testMapToMap_11() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen", "Nineteen", "Twenty-three"), streamable -> {
            assertStrings(
                    "[{<10>:r, <11>:e, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(11))
                    .mapToMap(
                            "<1>", $S.charAt(0),
                            "<2>", $S.charAt(1),
                            "<3>", $S.charAt(2),
                            "<4>", $S.charAt(3),
                            "<5>", $S.charAt(4),
                            "<6>", $S.charAt(5),
                            "<7>", $S.charAt(6),
                            "<8>", $S.charAt(7),
                            "<9>", $S.charAt(8),
                            "<10>", $S.charAt(9),
                            "<11>", $S.charAt(10))
                    .map(map -> map.sorted())
                    .toListString());
        });
    }
    
    //-- StreamableWithMapToTuple --
    
    @Test
    public void testMapToTuple_2() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[(O,n), (T,h), (F,i), (S,e), (E,l)]",
                    streamable
                        .filter($S.length().thatGreaterThanOrEqualsTo(2))
                        .mapToTuple($S.charAt(0), $S.charAt(1))
                        .toListString());
        });
    }
    
    @Test
    public void testMapToTuple_3() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[(O,n,e), (T,h,r), (F,i,v), (S,e,v), (E,l,e)]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(3))
                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2))
                    .toListString());
        });
    }
    
    @Test
    public void testMapToTuple_4() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[(T,h,r,e), (F,i,v,e), (S,e,v,e), (E,l,e,v)]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(4))
                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3))
                    .toListString());
        });
    }
    
    @Test
    public void testMapToTuple_5() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[(T,h,r,e,e), (S,e,v,e,n), (E,l,e,v,e)]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(5))
                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4))
                    .toListString());
        });
    }
    
    @Test
    public void testMapToTuple_6() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[(E,l,e,v,e,n)]",
                    streamable
                    .filter($S.length().thatGreaterThanOrEqualsTo(6))
                    .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4), $S.charAt(5))
                    .toListString());
        });
    }
    
    //-- StreamPlusWithMapWithIndex --
    
    @Test
    public void testMapWithIndex() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[(0,One), (1,Three), (2,Five), (3,Seven), (4,Eleven)]",
                    streamable
                    .mapWithIndex()
                    .toListString());
        });
    }
    
    @Test
    public void testMapWithIndex_combine() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
                    streamable
                    .mapWithIndex((i, each) -> i + ": " + each)
                    .toListString());
        });
    }
    
    @Test
    public void testMapToObjWithIndex_combine() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
        assertStrings(
                "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
                streamable
                .mapToObjWithIndex((i, each) -> i + ": " + each)
                .toListString());
        });
    }
    
    @Test
    public void testMapWithIndex_map_combine() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
        assertStrings(
                "[0: 3, 1: 5, 2: 4, 3: 5, 4: 6]",
                streamable
                .mapWithIndex(each -> each.length(), (i, each) -> i + ": " + each)
                .toListString());
        });
    }
    
    @Test
    public void testMapToObjWithIndex_map_combine() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
        assertStrings(
                "[0: 3, 1: 5, 2: 4, 3: 5, 4: 6]",
                streamable
                .mapToObjWithIndex(each -> each.length(), (i, each) -> i + ": " + each)
                .toListString());
        });
    }
    
    //-- StreamPlusWithMapGroup --
    
    @Test
    public void testMapGroup() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings(
                      "(One,Two), "
                    + "(Two,Three)",
                    streamable.mapTwo().join(", "));
        });
    }
    
    @Test
    public void testMapGroup_combine() {
        run(Streamable.of("One", "Two", "Three"), streamable -> {
            assertStrings("6, 8", streamable.mapGroup((prev, element) -> prev.length() + element.length()).join(", "));
        });
    }
    
    //-- StreamPlusWithModify --
    
    @Test
    public void testAccumulate1() {
        run(Streamable.of(1, 2, 3, 4, 5), streamable -> {
            assertStrings(
                    "1, 3, 6, 10, 15",
                    streamable
                        .accumulate((a, b)->a+b)
                        .join(", "));
        });
    }
    
    @Test
    public void testAccumulate2() {
        run(Streamable.of(1, 2, 3, 4, 5), streamable -> {
            assertStrings(
                    "1, 12, 123, 1234, 12345",
                    streamable
                        .accumulate((prev, current)->prev*10 + current)
                        .join(", "));
        });
    }
    
    @Test
    public void testRestate1() {
        run(IntStreamable.infiniteInt().map(i -> i % 5).limit(20).boxed(), streamable -> {
            assertStrings(
                    "0, 1, 2, 3, 4",
                  streamable
                      .restate((a, s)->s.filter(x -> x != a))
                      .join   (", "));
        });
    }
    
    // sieve of eratosthenes
    @Test
    public void testRestate2() {
        run(IntStreamable.infiniteInt().skip(2).boxed(), streamable -> {
            assertStrings(
                    "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, "
                  + "31, 37, 41, 43, 47, 53, 59, 61, 67, 71, "
                  + "73, 79, 83, 89, 97, 101, 103, 107, 109, 113, "
                  + "127, 131, 137, 139, 149, 151, 157, 163, 167, 173, "
                  + "179, 181, 191, 193, 197, 199, 211, 223, 227, 229, "
                  + "233, 239, 241, 251, 257, 263, 269, 271, 277, 281",
                  streamable
                      .restate((a, s)->s.filter(x -> x % a != 0))
                      .limit(60)
                      .join (", "));
        });
    }
    
    @Test
    public void testSpawn() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val timePrecision = 100;
            val first  = new AtomicLong(-1);
            val logs   = new ArrayList<String>();
            streamable
            .spawn(str -> Sleep(str.length()*timePrecision + 5).thenReturn(str).defer())
            .forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start    = first.get();
                val end      = System.currentTimeMillis();
                val duration = Math.round((end - start)/(1.0 * timePrecision))*timePrecision;
                logs.add(element + " -- " + duration);
            });
            assertEquals("["
                    + "Result:{ Value: Two } -- 0, "
                    + "Result:{ Value: Four } -- " + (1*timePrecision) + ", "
                    + "Result:{ Value: Three } -- " + (2*timePrecision) + ", "
                    + "Result:{ Value: Eleven } -- " + (3*timePrecision) + ""
                    + "]",
                    logs.toString());
        });
    }
    
    @Test
    public void testSpawn_limit() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            val first   = new AtomicLong(-1);
            val actions = new ArrayList<DeferAction<String>>();
            val logs    = new ArrayList<String>();
            streamable
            .spawn(str -> {
                DeferAction<String> action = Sleep(str.length()*50 + 5).thenReturn(str).defer();
                actions.add(action);
                return action;
            })
            .limit(1)
            .forEach(element -> {
                first.compareAndSet(-1, System.currentTimeMillis());
                val start    = first.get();
                val end      = System.currentTimeMillis();
                val duration = Math.round((end - start)/50.0)*50;
                logs.add(element + " -- " + duration);
            });
            assertEquals("[Result:{ Value: Two } -- 0]",
                    logs.toString());
            assertEquals(
                    "Result:{ Value: Two }, " +
                    "Result:{ Cancelled: Stream closed! }, " +
                    "Result:{ Cancelled: Stream closed! }, " +
                    "Result:{ Cancelled: Stream closed! }",
                    actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
        });
    }
    
    @Test
    public void testSegmentSize() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17], "
                    + "[18, 19]",
                    streamable
                    .segmentSize(6)
                    .map        (s -> s.toList())
                    .join       (", ")
            );
        });
    }
    
    @Test
    public void testSegmentSize_excludeTail() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17]",
                    streamable
                    .segmentSize(6, false)
                    .map        (s -> s.toList())
                    .join       (", ")
            );
        });
    }
    
    @Test
    public void testSegmentSize_includeIncomplete() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17], "
                    + "[18, 19]",
                    streamable
                    .segmentSize(6, IncompletedSegment.included)
                    .map        (s -> s.toList())
                    .join       (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentSize_excludeIncomplete() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2, 3, 4, 5], "
                    + "[6, 7, 8, 9, 10, 11], "
                    + "[12, 13, 14, 15, 16, 17]",
                    streamable
                    .segmentSize(6, IncompletedSegment.excluded)
                    .map        (s -> s.toList())
                    .join       (", ")
                );
        });
    }
    
    @Test
    public void testSegmentSize_function() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[], " +
                    "[1], " +
                    "[2, 3], " +
                    "[4, 5, 6, 7], " +
                    "[8, 9, 10, 11, 12, 13, 14, 15], " +
                    "[16, 17, 18, 19]",
                    streamable
                    .segmentSize(i -> i)
                    .map        (s -> s.toList())
                    .join       (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentStartCondition() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]",
                    streamable
                    .segment    (theInteger.thatIsDivisibleBy(3))
                    .map        (s -> s.toList())
                    .join       (", ")
            );
        });
    }
    
    @Test
    public void testSegmentStartCondition_includeIncomplete() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]",
                    streamable
                    .segment    (theInteger.thatIsDivisibleBy(3), IncompletedSegment.included)
                    .map        (s -> s.toList())
                    .join       (", ")
                    );
            
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17], "
                    + "[18, 19]",
                    streamable
                    .segment    (theInteger.thatIsDivisibleBy(3), true)
                    .map        (s -> s.toList())
                    .join       (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentStartCondition_excludeIncomplete() {
        run(IntStreamable.infiniteInt().boxed().limit(20), streamable -> {
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17]",
                    streamable
                    .segment    (theInteger.thatIsDivisibleBy(3), IncompletedSegment.excluded)
                    .map        (s -> s.toList())
                    .join       (", ")
                    );
            
            assertEquals(
                    "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9, 10, 11], "
                    + "[12, 13, 14], "
                    + "[15, 16, 17]",
                    streamable
                    .segment    (theInteger.thatIsDivisibleBy(3), false)
                    .map        (s -> s.toList())
                    .join       (", ")
                    );
        });
    }
    
    @Test
    public void testSegmentCondition() {
        Predicate<Integer> startCondition = i ->(i % 10) == 3;
        Predicate<Integer> endCondition   = i ->(i % 10) == 6;
        
        run(IntStreamable.infiniteInt().boxed(), streamable -> {
            assertEquals("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                      streamable
                      .segment(startCondition, endCondition)
                      .skip   (5)
                      .limit  (3)
                      .map    (StreamPlus::toListString)
                      .toListString());
            
            assertEquals("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                      streamable
                      .segment(startCondition, endCondition, true)
                      .skip   (5)
                      .limit  (3)
                      .map    (StreamPlus::toListString)
                      .toListString());
            
            assertEquals("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                      streamable
                      .segment(startCondition, endCondition, false)
                      .skip   (5)
                      .limit  (3)
                      .map    (StreamPlus::toListString)
                      .toListString());
            
            assertEquals("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                          streamable
                          .segment(startCondition, endCondition, IncompletedSegment.included)
                          .skip   (5)
                          .limit  (3)
                          .map    (StreamPlus::toListString)
                          .toListString());
            
            assertEquals("[[53, 54, 55, 56], " +
                          "[63, 64, 65, 66], " +
                          "[73, 74, 75, 76]]",
                          streamable
                          .segment(startCondition, endCondition, IncompletedSegment.excluded)
                          .skip   (5)
                          .limit  (3)
                          .map    (StreamPlus::toListString)
                          .toListString());
        });
    }
    
    @Test
    public void testCollapse() {
        run(Streamable.of(1, 2, 3, 4, 5, 6), streamable -> {
            // Because 3 and 6 do match the condition to collapse ... so they are merged with the one before them.
            assertEquals(
                    "1, 5, 4, 11",
                    streamable.collapseWhen(
                            i -> (i % 3) == 0,
                            (a,b)->a+b
                        ).join(", "));
            
            assertEquals(
                    "1, 2, 7, 5, 6",
                    streamable.collapseWhen(
                            i -> (i % 3) == 1,
                            (a,b)->a+b
                        ).join(", "));
            
            assertEquals(
                    "1, 9, 11",
                    streamable.collapseWhen(
                            i -> (i % 3) <= 1,
                            (a,b)->a+b
                        ).join(", "));
        });
    }
    
    @Test
    public void testCollapseSize() {
        run(IntStreamable.infiniteInt().limit(20).boxed(), streamable -> {
            assertEquals(
                    "1, 5, 22, 92, 70",
                    streamable.collapseSize(
                            i -> i,
                            (a,b)->a+b
                        ).join(", "));
                    
                    assertEquals(
                            "1, 2-3, 4-5-6-7, 8-9-10-11-12-13-14-15, 16-17-18-19",
                            streamable.collapseSize(
                                    i -> i,
                                    i -> "" + i,
                                    (a,b)->a + "-" + b
                                ).join(", "));
        });
    }
    
    //-- StreamPlusWithPeek --
    
    @Test
    public void testPeekClass() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            val elementStrings = new ArrayList<String>();
            val elementIntegers = new ArrayList<Integer>();
            streamable
                .peek(String.class,  elementStrings::add)
                .peek(Integer.class, elementIntegers::add)
                .toList();
            assertStrings("[One, Three, Five]", elementStrings);
            assertStrings("[0, 2, 4]", elementIntegers);
        });
    }
    
    @Test
    public void testPeekBy() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            val elementStrings = new ArrayList<String>();
            val elementIntegers = new ArrayList<Integer>();
            streamable
                .peekBy(String.class::isInstance,  e -> elementStrings.add((String)e))
                .peekBy(Integer.class::isInstance, e -> elementIntegers.add((Integer)e))
                .toList();
            assertStrings("[One, Three, Five]", elementStrings);
            assertStrings("[0, 2, 4]", elementIntegers);
        });
    }
    
    @Test
    public void testPeekAs() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            val elementStrings = new ArrayList<String>();
            streamable
                .peekAs(e -> "<" + e + ">", e -> elementStrings.add((String)e))
                .toList();
            assertStrings("[<0>, <One>, <2>, <Three>, <4>, <Five>]", elementStrings);
        });
    }
    
    @Test
    public void testPeekBy_map() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            val elementStrings = new ArrayList<String>();
            streamable
                .peekBy(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add("" + e))
                .toList();
            assertStrings("[0, One, 2, Three, 4]", elementStrings);
        });
    }
    
    @Test
    public void testPeekAs_map() {
        run(Streamable.of(0, "One", 2, "Three", 4, "Five"), streamable -> {
            val elementStrings = new ArrayList<String>();
            streamable
                .peekAs(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add((String)e))
                .toList();
            assertStrings("[<0>, <One>, <2>, <Three>, <4>]", elementStrings);
        });
    }
    
    //-- StreamPlusWithPipe --
    
    @Test
    public void testPipeable() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[One, Three, Five, Seven, Eleven]",
                    streamable
                        .pipable()
                        .pipeTo(Streamable::toListString));
        });
    }
    
    @Test
    public void testPipe() {
        run(Streamable.of("One", "Three", "Five", "Seven", "Eleven"), streamable -> {
            assertStrings(
                    "[One, Three, Five, Seven, Eleven]",
                    streamable
                        .pipeTo(Streamable::toListString));
        });
    }
    
    //-- StreamPlusWithSort --
    
    @Test
    public void testSortedBy() {
        run(Streamable.of("One", "Two", "Three", "Four"), streamable -> {
            assertStrings("[One, Two, Four, Three]", streamable.sortedBy(String::length).toList());
        });
    }
    
    @Test
    public void testSortedByComparator() {
        run(Streamable.of("One", "Two", "Three", "Four"), streamable -> {
            assertStrings(
                    "[Three, Four, One, Two]",
                    streamable.sortedBy(String::length, (a,b)->b-a).toList());
        });
    }
    
    //-- StreamPlusWithSplit --
    
    @Test
    public void testSplit_1() {
        Function<Streamable<String>, FuncList<String>> streamablePlusToList = s -> s.toImmutableList();
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six"), streamable -> {
            assertStrings(
                     "([One, Two, Six],"
                    + "[Three, Four, Five])",
                    streamable
                    .split(theString.length().thatEquals(3))
                    .map(
                        streamablePlusToList,
                        streamablePlusToList)
                    .toString());
        });
    }
    
    @Test
    public void testSplit_2() {
        Function<Streamable<String>, FuncList<String>> streamablePlusToList = s -> s.toImmutableList();
        run(Streamable.of("One", "Two", "Three", "Four", "Five", "Six"), streamable -> {
            assertStrings("([One, Two, Six],"
                         + "[Four, Five],"
                         + "[Three])",
                     streamable
                    .split(
                        s -> s.length() == 3,
                        s -> s.length() == 4)
                    .map(
                        streamablePlusToList,
                        streamablePlusToList,
                        streamablePlusToList)
                    .toString());
        });
    }
    
    @Test
    public void testFizzBuzz() {
        Function<Streamable<Integer>, FuncList<Integer>> streamableToList = s -> s.toImmutableList();
        run(infiniteInt().limit(20).boxed(), streamable -> {
            String toString = With(FuncMap.underlineMap.butWith(FuncMap.UnderlineMap.LinkedHashMap))
            .run(() -> {
                FuncMap<String, Streamable<Integer>> splited
                        = streamable
                        .split(
                            "FizzBuzz", i -> i % (3*5) == 0,
                            "Buzz",     i -> i % 5     == 0,
                            "Fizz",     i -> i % 3     == 0,
                            null);
                val string
                        = splited
                        .mapValue(streamableToList)
                        .toString();
                return string;
            });
            assertEquals("{"
                    + "FizzBuzz:[0, 15], "
                    + "Buzz:[5, 10], "
                    + "Fizz:[3, 6, 9, 12, 18], "
                    + "null:[]}",
                    toString);
        });
    }
    
    @Test
    public void testCycle() {
        run(Streamable.cycle("One", "Two", "Three"), streamable -> {
            assertStrings("Two, Three, One, Two, Three", streamable.skip(1).limit(5).join(", "));
        });
    }
    
    @Test
    public void testSelectiveMap() {
        assertEquals("[One, --Two, Three, Four, Five]",
                ImmutableList.of("One", "Two", "Three", "Four", "Five").mapOnly("Two"::equals, str -> "--" + str).toString());
    }
    
    @Test
    public void testSplit() {
        assertEquals("([One, Two],[Four, Five],[Three])",
                FuncListDerived.from((Supplier<Stream<String>>)()->Stream.of("One", "Two", "Three", "Four", "Five"))
                .split($S.length().thatEquals(3),
                       $S.length().thatLessThanOrEqualsTo(4))
                .toString());
    }
    
    @Test
    public void testMapGroup_each_prior() {
        // Check if mapWithPrev is "Each-Prior" adverb in Q
        val stream = Streamable.infiniteInt().limit(5).mapGroup((prev, element) -> element - prev);
        assertEquals("1, 1, 1, 1", stream.join(", "));
    }
    
    @Test
    public void testAccumulate() {
        val stream = Streamable.of(1, 2, 3, 4, 5);
        assertEquals("1, 3, 6, 10, 15", stream.accumulate((a, b)->a+b).join(", "));
    }

}
