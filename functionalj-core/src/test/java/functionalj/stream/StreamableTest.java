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

import static functionalj.lens.Access.$S;
import static functionalj.lens.Access.theString;
import static functionalj.list.FuncList.listOf;
import static functionalj.streamable.Streamable.zipOf;
import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

import org.junit.Test;

import functionalj.function.Func0;
import functionalj.function.FuncUnit1;
import functionalj.function.FuncUnit2;
import functionalj.list.FuncListDerived;
import functionalj.list.ImmutableList;
import functionalj.result.NoMoreResultException;
import functionalj.streamable.Streamable;


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
                var counter = new AtomicInteger();
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
            assertStrings("[true, true, true, true, true]", streamable.toListString());
        });
        run(Streamable.loop(6), streamable -> {
            assertStrings("[true, true, true, true, true, true]", streamable.toListString());
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
//    
//    @Test
//    public void testIterator() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        var iterator = stream.iterator();
//        
//        assertTrue(iterator.hasNext());
//        assertTrue("One".equals(iterator.next()));
//        
//        assertTrue(iterator.hasNext());
//        assertTrue("Two".equals(iterator.next()));
//        
//        assertTrue(iterator.hasNext());
//        assertTrue("Three".equals(iterator.next()));
//        
//        assertStrings("[Four, Five]", stream.toList());
//    }
//    
//    @Test
//    public void testToIterator() throws Exception {
//        var stream  = StreamPlus.of("One", "Two", "Three");
//        var list    = new ArrayList<String>();
//        var isClose = new AtomicBoolean(false);
//        stream.onClose(()->isClose.set(true));
//        try(var iterator = stream.iterator()) {
//            while (iterator.hasNext())
//                list.add(iterator.next());
//            assertStrings("[One, Two, Three]", list);
//        }
//        assertTrue(isClose.get());
//    }
//    
//    @Test
//    public void testMap() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[3, 3, 5]",
//                stream
//                .map(s -> s.length())
//                .toList());
//    }
//    
//    @Test
//    public void testMapToInt() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("11", stream.mapToInt(String::length).sum());
//    }
//    
//    @Test
//    public void testMapToLong() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("11", stream.mapToLong(String::length).sum());
//    }
//    
//    @Test @Ignore
//    public void testMapToDouble() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("11.0", stream.mapToDouble(s -> s.length()*1.0).sum());
//    }
//    
//    //-- FlatMap --
//    
//    @Test
//    public void testFlatMap() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[3, 3, 5]", stream.flatMap(s -> Stream.of(s.length())).toList());
//    }
//    
//    @Test
//    public void testFlatMapToInt() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[3, 3, 5]", stream.flatMapToInt(s -> IntStreamPlus.of(s.length())).toList());
//    }
//    
//    @Test @Ignore
//    public void testFlatMapToLong() {
////        var stream = StreamPlus.of("One", "Two", "Three");
////        assertStrings("[3, 3, 5]", stream.flatMapToLong(s -> LongStreamPlus.of((long)s.length())).toList()));
//    }
//    
//    @Test @Ignore
//    public void testFlatMapToDouble() {
////        var stream = StreamPlus.of("One", "Two", "Three");
////        assertStrings("[3, 3, 5]", stream.flatMapToDouble(s -> DoubleStreamPlus.of((double)s.length())).toList());
//    }
//    
//    //-- Filter --
//    
//    @Test
//    public void testFilter() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[Three]", stream.filter(s -> s.length() > 4).toList());
//    }
//    
//    @Test
//    public void testPeek() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var logs   = new ArrayList<String>();
//        assertStrings("[One, Two, Three]", stream.peek(s -> logs.add(s)).toList());
//        assertStrings("[One, Two, Three]", logs);
//    }
//    
//    @Test
//    public void testLimit() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("[One, Two, Three]", stream.limit(3).toList());
//    }
//    
//    @Test
//    public void testSkip() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("[Three, Four, Five]", stream.skip(2).toList());
//    }
//    
//    @Test
//    public void testDistinct() {
//        var stream = StreamPlus.of("One", "Two", "Two", "Three");
//        assertStrings("[One, Two, Three]", stream.distinct().toList());
//    }
//    
//    @Test
//    public void testSorted() {
//        var stream1 = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("[3, 3, 4, 4, 5]", stream1.map(theString.length()).sorted().toList());
//        
//        var stream2 = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("[5, 4, 4, 3, 3]", stream2.map(theString.length()).sorted((a, b) -> (b - a)).toList());
//    }
//    
//    @Test
//    public void testForEach() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var logs   = new ArrayList<String>();
//        stream.forEach(s -> logs.add(s));
//        assertStrings("[One, Two, Three]", logs);
//    }
//    
//    @Test
//    public void testForEachOrdered() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var logs   = new ArrayList<String>();
//        stream.forEachOrdered(s -> logs.add(s));
//        assertStrings("[One, Two, Three]", logs);
//    }
//    
//    @Test
//    public void testReduce() {
//        var stream1 = StreamPlus.of(1, 2, 3);
//        assertEquals(6, stream1.reduce(0, (a, b) -> a + b).intValue());
//        
//        var stream2 = StreamPlus.of(1, 2, 3);
//        assertEquals(6, stream2.reduce((a, b) -> a + b).get().intValue());
//        
//        var stream3 = StreamPlus.of(1, 2, 3);
//        assertEquals(6, stream3.reduce(
//                                    BigInteger.ZERO,
//                                    (b, i) -> b.add(BigInteger.valueOf((long)i)),
//                                    (a, b) -> a.add(b)).intValue());
//    }
//    
//    @Test
//    public void testCollect() {
//        assertStrings("[One, Two, Three]", StreamPlus.of("One", "Two", "Three").collect(toList()));
//        
//        Supplier<StringBuffer> supplier = StringBuffer::new;
//        BiConsumer<StringBuffer, String> accumulator = StringBuffer::append;
//        BiConsumer<StringBuffer, StringBuffer> combiner = (a, b) -> a.append(b.toString());
//        assertStrings("OneTwoThree", StreamPlus.of("One", "Two", "Three").collect(supplier, accumulator, combiner));
//    }
//    
//    @Test
//    public void testMinMax() {
//        var stream1 = StreamPlus.of("One", "Two", "Three", "Four");
//        assertStrings("Optional[One]",   stream1.min((a, b)-> a.length()-b.length()));
//        
//        var stream2 = StreamPlus.of("One", "Two", "Three", "Four");
//        assertStrings("Optional[Three]", stream2.max((a, b)-> a.length()-b.length()));
//    }
//    
//    @Test
//    public void testCount() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("3", stream.count());
//    }
//    
//    @Test
//    public void testAnyMatch() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertTrue(stream.anyMatch("One"::equals));
//    }
//    
//    @Test
//    public void testAllMatch() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertFalse(stream.allMatch("One"::equals));
//    }
//    
//    @Test
//    public void testNoneMatch() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertTrue(stream.noneMatch("Five"::equals));
//    }
//    
//    @Test
//    public void testFindFirst() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("Optional[One]", stream.findFirst());
//    }
//    
//    @Test
//    public void testFindAny() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("Optional[One]", stream.findAny());
//    }
//    
//    @Test
//    public void testToArray() {
//        var stream1 = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, Two, Three]", Arrays.toString(stream1.map(s -> s).toArray()));
//        
//        var stream2 = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, Two, Three]", Arrays.toString(stream2.map(s -> s).toArray(n -> new String[n])));
//    }
//    
//    //-- AsStreamPlus --
//    
//    @Test
//    public void testStreamOf_fromAsStreamPlus() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, Two, Three]", AsStreamPlus.streamOf(stream).toListString());
//    }
//    
//    //-- AsStreamPlusWithConversion --
//    
//    @Test
//    public void testToByteArray() {
//        var stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65, 66, 67, 68]", Arrays.toString(stream.toByteArray(c -> (byte)(int)c)));
//    }
//    
//    @Test
//    public void testToIntArray() {
//        var stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65, 66, 67, 68]", Arrays.toString(stream.toIntArray(c -> (int)c)));
//    }
//    
//    @Test
//    public void testToLongArray() {
//        var stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65, 66, 67, 68]", Arrays.toString(stream.toLongArray(c -> (long)c)));
//    }
//    
//    @Test @Ignore
//    public void testToDoubleArray() {
//        var stream = StreamPlus.of('A', 'B', 'C', 'D');
//        assertStrings("[65.0, 66.0, 67.0, 68.0]", Arrays.toString(stream.toDoubleArray(c -> (double)(int)c)));
//    }
//    
//    @Test
//    public void testToArrayList() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var list   = stream.toArrayList();
//        assertStrings("[One, Two, Three]", list);
//        assertTrue(list instanceof ArrayList);
//    }
//    
//    @Test
//    public void testToFuncList() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var list   = stream.toFuncList();
//        assertStrings("[One, Two, Three]", list.toString());
//        assertTrue(list instanceof FuncList);
//    }
//    
//    @Test
//    public void testToImmutableList() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var list   = stream.toImmutableList();
//        assertStrings("[One, Two, Three]", list.toString());
//        assertTrue(list instanceof ImmutableList);
//    }
//    
//    @Test
//    public void testToJavaList() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var list   = stream.toJavaList();
//        assertStrings("[One, Two, Three]", list.toString());
//        assertTrue(list instanceof List);
//    }
//    
//    @Test
//    public void testToList() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var list   = stream.toJavaList();
//        assertStrings("[One, Two, Three]", list.toString());
//        assertTrue(list instanceof List);
//    }
//    
//    @Test
//    public void testToMutableList() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var list   = stream.toMutableList();
//        assertStrings("[One, Two, Three]", list);
//        // This is because we use ArrayList as mutable list ... not it should not always be.
//        assertTrue(list instanceof ArrayList);
//    }
//    
//    //-- join --
//    
//    @Test
//    public void testJoin() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("OneTwoThree", stream.join());
//    }
//    
//    @Test
//    public void testJoin_withDelimiter() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("One, Two, Three", stream.join(", "));
//    }
//    
//    @Test
//    public void testToListString() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, Two, Three]", stream.toListString());
//    }
//    
//    //-- toMap --
//    
//    @Test
//    public void testToMap() {
//        var stream = StreamPlus.of("One", "Three", "Five");
//        assertStrings("{3:One, 4:Five, 5:Three}", stream.toMap(theString.length()).toString());
//    }
//    
//    @Test
//    public void testToMap_withValue() {
//        var stream = StreamPlus.of("One", "Three", "Five");
//        assertStrings("{3:-->One, 4:-->Five, 5:-->Three}", stream.toMap(theString.length(), theString.withPrefix("-->")).toString());
//    }
//    
//    @Test
//    public void testToMap_withMappedMergedValue() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Five");
//        assertStrings("{3:One+Two, 4:Five, 5:Three}", stream.toMap(theString.length(), theString, (a, b) -> a + "+" + b).toString());
//    }
//    
//    
//    @Test
//    public void testToMap_withMergedValue() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Five");
//        assertStrings("{3:One+Two, 4:Five, 5:Three}", stream.toMap(theString.length(), (a, b) -> a + "+" + b).toString());
//    }
//    
//    @Test
//    public void testToSet() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var set    = stream.toSet();
//        assertStrings("[One, Two, Three]", set);
//        assertTrue(set instanceof Set);
//    }
//    
//    @Test
//    public void testForEachWithIndex() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        var logs   = new ArrayList<String>();
//        stream.forEachWithIndex((i, s) -> logs.add(i + ":" + s));
//        assertStrings("[0:One, 1:Two, 2:Three]", logs);
//    }
//    
//    @Test
//    public void testPopulateArray() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        var array  = new String[5];
//        stream.populateArray(array);
//        assertStrings("[One, Two, Three, Four, Five]", Arrays.toString(array));
//    }
//    
//    @Test
//    public void testPopulateArray_withOffset() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        var array  = new String[3];
//        stream.populateArray(array, 2);
//        assertStrings("[null, null, One]", Arrays.toString(array));
//    }
//    
//    @Test
//    public void testPopulateArray_withOffsetLength() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        var array  = new String[5];
//        stream.populateArray(array, 1, 3);
//        assertStrings("[null, One, Two, Three, null]", Arrays.toString(array));
//    }
//    
//    //-- AsStreamPlusWithMatch --
//    
//    @Test
//    public void testFindFirst_withPredicate() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[Three]", stream.findFirst(theString.thatContains("ee")));
//    }
//    
//    @Test
//    public void testFindAny_withPredicate() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[Three]", stream.findAny(theString.thatContains("ee")));
//    }
//    
//    @Test
//    public void testFindFirst_withMapper_withPredicate() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[Three]", stream.findFirst(theString.length(), l ->  l == 5));
//    }
//    
//    @Test
//    public void testFindAny_withMapper_withPredicate() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[Three]", stream.findAny(theString.length(), l -> l == 5));
//    }
//    
//    //-- AsStreamPlusWithStatistic --
//    
//    @Test
//    public void testSize() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("3", stream.size());
//    }
//    
//    @Test
//    public void testMinBy() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[One]", stream.minBy(theString.length()));
//    }
//    
//    @Test
//    public void testMaxBy() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[Three]", stream.maxBy(theString.length()));
//    }
//    
//    @Test
//    public void testMinBy_withMapper() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[Three]", stream.minBy(theString.length(), (a, b)->b-a));
//    }
//    
//    @Test
//    public void testMaxBy_withMapper() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("Optional[One]", stream.maxBy(theString.length(), (a, b)->b-a));
//    }
//    
//    @Test
//    public void testMinMaxBy() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings("(Optional[Five],Optional[Two])", stream.minMax(String.CASE_INSENSITIVE_ORDER));
//    }
//    
//    @Test
//    public void testMinMaxBy_withMapper() {
//        var stream3 = StreamPlus.of("One", "Two", "Three", "Four");
//        assertStrings("(Optional[One],Optional[Three])", stream3.minMaxBy(theString.length()));
//    }
//    
//    @Test
//    public void testMinMaxBy_withMapper_withComparator() {
//        var stream3 = StreamPlus.of("One", "Two", "Three", "Four");
//        assertStrings("(Optional[Three],Optional[Two])", stream3.minMaxBy(theString.length(), (a, b) -> b-a));
//    }
//    
//    //-- StreamPlusWithCalculate --
//
//    
//    static class SumLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { 0 }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] += s.length(); }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { a1[0] + a1[1] }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class AvgLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { 0, 0 }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] += s.length(); a[1]++; }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { a1[0] + a2[0], a1[1] + a2[1] }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]/a[1]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class MinLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { Integer.MAX_VALUE }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] = Math.min(a[0], s.length()); }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { Math.min(a1[0], a2[0]) }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class MaxLength implements CollectorPlus<String, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>           supplier()          { return ()->new int[] { Integer.MIN_VALUE }; }
//        @Override public BiConsumer<int[], String> accumulator()       { return (a, s)->{ a[0] = Math.max(a[0], s.length()); }; }
//        @Override public BinaryOperator<int[]>     combiner()          { return (a1, a2) -> new int[] { Math.max(a1[0], a2[0]) }; }
//        @Override public Function<int[], Integer>  finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>      characteristics()   { return characteristics; }
//        @Override public Collector<String, int[], Integer> collector() { return this; }
//    }
//    static class Sum implements CollectorPlus<Integer, int[], Integer> {
//        private Set<Characteristics> characteristics = EnumSet.of(CONCURRENT, UNORDERED);
//        @Override public Supplier<int[]>            supplier()          { return ()->new int[] { 0 }; }
//        @Override public BiConsumer<int[], Integer> accumulator()       { return (a, e)->{ a[0] += e.intValue(); }; }
//        @Override public BinaryOperator<int[]>      combiner()          { return (a1, a2) -> new int[] { a1[0] + a1[1] }; }
//        @Override public Function<int[], Integer>   finisher()          { return a -> a[0]; }
//        @Override public Set<Characteristics>       characteristics()   { return characteristics; }
//        @Override public Collector<Integer, int[], Integer> collector() { return this; }
//    }
    
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
            var sumLength = new SumLength();
            assertEquals(18, streamable.calculate(sumLength).intValue());
        });
    }
    
    @Test
    public void testCalculate2() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            assertEquals("(18,4)", streamable.calculate(sumLength, avgLength).toString());
        });
    }
    
    @Test
    public void testCalculate2_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            var range = streamable.calculate(maxLength, minLength).mapTo((max, min) -> max - min).intValue();
            assertEquals(3, range);
        });
    }
    
    @Test
    public void testCalculate3() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            assertEquals("(18,4,3)", streamable.calculate(sumLength, avgLength, minLength).toString());
        });
    }
    
    @Test
    public void testCalculate3_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var value     = streamable
                            .calculate(sumLength, avgLength, minLength)
                            .mapTo((sum, avg, min) -> "sum: " + sum + ", avg: " + avg + ", min: " + min);
            assertEquals("sum: 18, avg: 4, min: 3", value);
        });
    }
    
    @Test
    public void testCalculate4() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            assertEquals("(18,4,3,6)", streamable.calculate(sumLength, avgLength, minLength, maxLength).toString());
        });
    }
    
    @Test
    public void testCalculate4_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            var value     = streamable
                            .calculate(sumLength, avgLength, minLength, maxLength)
                            .mapTo((sum, avg, min, max) -> "sum: " + sum + ", avg: " + avg + ", min: " + min + ", max: " + max);
            assertEquals("sum: 18, avg: 4, min: 3, max: 6", value);
        });
    }
    
    @Test
    public void testCalculate5() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            assertEquals("(18,4,3,6,18)", streamable.calculate(sumLength, avgLength, minLength, maxLength, sumLength).toString());
        });
    }
    
    @Test
    public void testCalculate5_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            var value     = streamable
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
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            assertEquals("(18,4,3,6,18,4)", streamable.calculate(sumLength, avgLength, minLength, maxLength, sumLength, avgLength).toString());
        });
    }
    
    @Test
    public void testCalculate6_combine() {
        run(Streamable.of("Two", "Three", "Four", "Eleven"), streamable -> {
            var sumLength = new SumLength();
            var avgLength = new AvgLength();
            var minLength = new MinLength();
            var maxLength = new MaxLength();
            var value     = streamable
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
            var sum = new Sum();
            assertEquals(18, streamable.calculate(sum.of(theString.length())).intValue());
        });
    }
    
//    @Test
//    public void testConcatWith() {
//        assertStrings("[One, Two, Three, Four]",
//                streamOf("One", "Two").concatWith(streamOf("Three", "Four"))
//                .toList());
//    }
//    
//    @Test
//    public void testMerge() {
//        var streamA = StreamPlus.of("A", "B", "C");
//        var streamB
//                = IntStreamPlus
//                .infinite()
//                .boxed()
//                .map(theInteger.asString());
//        assertEquals(
//                "A, 0, B, 1, C, 2, 3, 4, 5, 6",
//                streamA
//                    .mergeWith(streamB)
//                    .limit    (10)
//                    .join     (", "));
//    }
//    
//    @Test
//    public void testZipWith() {
//        {
//            var streamA = StreamPlus.of("A", "B", "C");
//            var streamB = IntStreamPlus.infinite().limit(10).boxed();
//            assertEquals("(A,0), (B,1), (C,2)", streamA.zipWith(streamB).join(", "));
//        }
//        {
//            var streamA = StreamPlus.of("A", "B", "C");
//            var streamB = IntStreamPlus.infinite().boxed();
//            assertEquals("(A,0), (B,1), (C,2)", streamA.zipWith(streamB, RequireBoth).join(", "));
//        }
//        {
//            var streamA = StreamPlus.of("A", "B", "C");
//            var streamB = IntStreamPlus.infinite().limit(10).boxed();
//            assertEquals("A:0, B:1, C:2", streamA.zipWith(streamB, (c, i) -> c + ":" + i).join(", "));
//        }
//        {
//            var streamA = StreamPlus.of("A", "B", "C");
//            var streamB = IntStreamPlus.infinite().boxed();
//            assertEquals("(A,0), (B,1), (C,2), (null,3), (null,4)", streamA.zipWith(streamB, AllowUnpaired).limit(5).join(", "));
//        }
//    }
//    
//    @Test
//    public void testChoose() {
//        var streamA = StreamPlus.of("A", "B", "C");
//        var streamB = IntStreamPlus.infinite().boxed().map(theInteger.asString());
//        var bool    = new AtomicBoolean(true);
//        assertEquals("A, 1, C", streamA.choose(streamB, (a, b) -> {
//            boolean curValue = bool.get();
//            return bool.getAndSet(!curValue);
//        }).limit(5).join(", "));
//    }
//    
//    @Test
//    public void testChoose_AllowUnpaired() {
//        var streamA = StreamPlus.of("A", "B", "C");
//        var streamB = IntStreamPlus.infinite().boxed().map(theInteger.asString());
//        var bool    = new AtomicBoolean(true);
//        assertEquals("A, 1, C, 3, 4, 5, 6", streamA.choose(streamB, AllowUnpaired, (a, b) -> {
//            boolean curValue = bool.get();
//            return bool.getAndSet(!curValue);
//        }).limit(7).join(", "));
//    }
//    
//    //-- StreamPlusWithFillNull --
//    
//    @Test
//    public void testFillNull() {
//        var stream = StreamPlus.of("A", "B",  null, "C");
//        assertEquals("[A, B, Z, C]", stream.fillNull("Z").toListString());
//    }
//    
//    @Test
//    public void testFillNull_lens() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                stream.fillNull(Car.theCar.color, "Black").toListString());
//    }
//    
//    @Test
//    public void testFillNull_getter_setter() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                stream.fillNull(
//                        (Car car)               -> car.color(),
//                        (Car car, String color) -> car.withColor(color),
//                        "Black").toListString());
//    }
//    
//    @Test
//    public void testFillNull_lens_supplier() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                stream.fillNullWith(Car.theCar.color, () -> "Black").toListString());
//    }
//    
//    @Test
//    public void testFillNull_getter_setter_supplier() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                stream.fillNullWith(
//                        (Car car)               -> car.color(),
//                        (Car car, String color) -> car.withColor(color),
//                        ()                      -> "Black").toListString());
//    }
//    
//    @Test
//    public void testFillNull_lens_function() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                stream.fillNullBy(Car.theCar.color, (Car car) -> "Black").toListString());
//    }
//    
//    @Test
//    public void testFillNull_getter_setter_function() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Black), Car(color=Red)]",
//                stream.fillNullBy(
//                        (Car car)               -> car.color(),
//                        (Car car, String color) -> car.withColor(color),
//                        (Car car)               -> "Black").toListString());
//    }
//    
//    //-- StreamPlusWithFilter --
//    
//    @Test
//    public void testFilterClass() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        assertStrings("[One, Three, Five]", stream.filter(String.class).toListString());
//    }
//    
//    @Test
//    public void testFilterClass_withPredicate() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        assertStrings("[One, Five]", stream.filter(String.class, theString.length().thatLessThan(5)).toListString());
//    }
//    
//    @Test
//    public void testFilterAsInt() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertEquals("[Three, Four, Five]", stream.filterAsInt(str -> str.length(), i -> i >= 4).toListString());
//    }
//    
//    @Test
//    public void testFilterAsLong() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertEquals("[Three, Four, Five]", stream.filterAsLong(str -> (long)str.length(), i -> i >= 4).toListString());
//    }
//    
//    @Test
//    public void testFilterAsDouble() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertEquals("[Three, Four, Five]", stream.filterAsDouble(str -> (double)str.length(), i -> i >= 4).toListString());
//    }
//    
//    @Test
//    public void testFilterAsObject() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertEquals("[Three, Four, Five]", stream.filterAsObject(str -> BigInteger.valueOf(str.length()), b -> b.intValue() >= 4).toListString());
//    }
//    
//    @Test
//    public void testFilterWithIndex() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertEquals("[Four, Five]", stream.filterWithIndex((index, str) -> index > 2 && !str.startsWith("T")).toListString());
//    }
//    
//    @Test
//    public void testFilterNonNull() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Blue, Green, Red]",
//                stream.map(theCar.color).filterNonNull().toListString());
//    }
//    
//    @Test
//    public void testFilterNonNull_withMapper() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
//                stream.filterNonNull(theCar.color).toListString());
//    }
//    
//    @Test
//    public void testExcludeNull() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Blue, Green, Red]",
//                stream.map(theCar.color).excludeNull().toListString());
//    }
//    
//    @Test
//    public void testExcludeNull_withMapper() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=Green), Car(color=Red)]",
//                stream.excludeNull(theCar.color).toListString());
//    }
//    
//    @Test
//    public void testFilterIn() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Blue, Red]",
//                stream.map(theCar.color).filterIn("Blue", "Red").toListString());
//    }
//    
//    @Test
//    public void testFilterIn_collection() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Blue, Red]",
//                stream.map(theCar.color).filterIn(asList("Blue", "Red")).toListString());
//    }
//    
//    @Test
//    public void testExclude() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Car(color=Blue), Car(color=null)]",
//                stream.exclude(theCar.color.toLowerCase().thatContains("r")).toListString());
//    }
//    
//    @Test
//    public void testExcludeIn() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Green, null]",
//                stream.map(theCar.color).excludeIn("Blue", "Red").toListString());
//    }
//    
//    @Test
//    public void testExcludeIn_collection() {
//        var stream = StreamPlus.of(new Car("Blue"), new Car("Green"), new Car(null), new Car("Red"));
//        assertEquals(
//                "[Green, null]",
//                stream.map(theCar.color).excludeIn(asList("Blue", "Red")).toListString());
//    }
//    
//    //-- StreamPlusWithFlatMap --
//    
//    @Test
//    public void testFlatMapToObj() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[3, 3, 5]", stream.flatMapToObj(s -> Stream.of(s.length())).toList());
//    }
//    
//    @Test
//    public void testFlatMapOnly() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, 3, 5]", stream.flatMapOnly(str -> str.toLowerCase().startsWith("t"), s -> Stream.of("" + s.length())).toList());
//    }
//    
//    @Test
//    public void testFlatMapIf() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[(One), [3], [5]]", stream.flatMapIf(str -> str.toLowerCase().startsWith("t"), s -> Stream.of("[" + s.length() + "]"), s -> Stream.of("(" + s + ")")).toList());
//    }
//    
//    @Test
//    public void testFlatMapToObjIf() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[(One), [3], [5]]", stream.flatMapToObjIf(str -> str.toLowerCase().startsWith("t"), s -> Stream.of("[" + s.length() + "]"), s -> Stream.of("(" + s + ")")).toList());
//    }
//    
//    //-- StreamPlusWithLimit --
//    
//    @Test
//    public void testSkipLimitLong() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[Two]", stream.skip((Long)1L).limit((Long)1L).toList());
//    }
//    
//    @Test
//    public void testSkipLimitLongNull() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, Two, Three]", stream.skip(null).limit(null).toList());
//    }
//    
//    @Test
//    public void testSkipLimitLongMinus() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[One, Two, Three]", stream.skip(Long.valueOf(-1)).limit(Long.valueOf(-1)).toList());
//    }
//    
//    @Test
//    public void testSkipWhile() {
//        assertStrings("[3, 4, 5, 4, 3, 2, 1]",       StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i < 3).toList());
//        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i > 3).toList());
//    }
//    
//    @Test
//    public void testSkipUntil() {
//        assertStrings("[4, 5, 4, 3, 2, 1]",          StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipUntil(i -> i > 3).toList());
//        assertStrings("[1, 2, 3, 4, 5, 4, 3, 2, 1]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipUntil(i -> i < 3).toList());
//    }
//    
//    @Test
//    public void testTakeWhile() {
//        var list = new ArrayList<Integer>();
//        assertStrings("[1, 2, 3]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeWhile(i -> i < 4).peek(list::add).toList());
//        assertStrings("[1, 2, 3]", list);
//        
//        list.clear();
//        assertStrings("[]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeWhile(i -> i > 4).peek(list::add).toList());
//        assertStrings("[]", list);
//    }
//    
//    @Test
//    public void testTakeUtil() {
//        var list = new ArrayList<Integer>();
//        assertStrings("[1, 2, 3, 4]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeUntil(i -> i > 4).peek(list::add).toList());
//        assertStrings("[1, 2, 3, 4]", list);
//        
//        list.clear();
//        assertStrings("[]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).takeUntil(i -> i < 4).peek(list::add).toList());
//        assertStrings("[]", list);
//    }
//    
//    @Test
//    public void testSkipTake() {
//        var list = new ArrayList<Integer>();
//        assertStrings("[3, 4, 5, 4, 3]", StreamPlus.of(1, 2, 3, 4, 5, 4, 3, 2, 1).skipWhile(i -> i < 3).takeUntil(i -> i < 3).peek(list::add).toList());
//        assertStrings("[3, 4, 5, 4, 3]", list);
//    }
//    
//    //-- StreamPlusWithMap --
//    
//    @Test
//    public void testMapToObj() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[=3=, =3=, =5=]",
//                stream
//                .mapToObj(s -> "=" + s.length() + "=")
//                .toList());
//    }
//    
//    @Test
//    public void testMapOnly() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[ONE, TWO, Three]",
//                stream
//                .mapOnly(
//                        $S.length().thatLessThan(4),
//                        $S.toUpperCase()
//                ).toList());
//    }
//    
//    @Test
//    public void testMapIf() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[ONE, TWO, three]",
//                stream
//                .mapIf(
//                        $S.length().thatLessThan(4), $S.toUpperCase(),
//                        $S.toLowerCase()
//                ).toList());
//    }
//    
//    @Test
//    public void testMapToObjIf() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("[ONE, TWO, three]",
//                stream
//                .mapToObjIf(
//                        $S.length().thatLessThan(4), $S.toUpperCase(),
//                        $S.toLowerCase()
//                ).toList());
//    }
//    
//    @Test
//    public void testMapFirst_2() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve");
//        assertStrings("[ONE, TWO, three, four, five, SIX, seven, eight, nine, TEN, eleven, twelve]",
//                stream.mapFirst(
//                        str -> str.length() == 3 ? str.toUpperCase() : null,
//                        str -> str.toLowerCase()).toListString());
//    }
//    
//    @Test
//    public void testMapFirst_3() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve");
//        assertStrings("[ONE, TWO, Three, four, five, SIX, Seven, Eight, nine, TEN, Eleven, Twelve]",
//                stream.mapFirst(
//                        str -> str.length() == 3 ? str.toUpperCase() : null,
//                        str -> str.length() == 4 ? str.toLowerCase() : null,
//                        str -> str).toListString());
//    }
//    
//    @Test
//    public void testMapFirst_4() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve");
//        assertStrings("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, Eleven, Twelve]",
//                stream.mapFirst(
//                        str -> str.length() == 3 ? str.toUpperCase() : null,
//                        str -> str.length() == 4 ? str.toLowerCase() : null,
//                        str -> str.length() == 5 ? "(" + str + ")": null,
//                        str -> str).toListString());
//    }
//    
//    @Test
//    public void testMapFirst_5() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve");
//        assertStrings("[ONE, TWO, (Three), four, five, SIX, (Seven), (Eight), nine, TEN, [Eleven], Twelve]",
//                stream.mapFirst(
//                        str -> str.length() == 3 ? str.toUpperCase() : null,
//                        str -> str.length() == 4 ? str.toLowerCase() : null,
//                        str -> str.length() == 5 ? "(" + str + ")": null,
//                        str -> str.length() == 6 && !str.contains("w")? "[" + str + "]": null,
//                        str -> str).toListString());
//    }
//    
//    @Test
//    public void testMapFirst_6() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve");
//        assertStrings("[ONE, TWO, (Three), four, -- Five --, -- Six --, (Seven), -- Eight --, -- Nine --, TEN, [Eleven], Twelve]",
//                stream.mapFirst(
//                        str -> str.contains("i") ? "-- " + str + " --" : null,
//                        str -> str.length() == 3 ? str.toUpperCase() : null,
//                        str -> str.length() == 4 ? str.toLowerCase() : null,
//                        str -> str.length() == 5 ? "(" + str + ")": null,
//                        str -> str.length() == 6 && !str.contains("w") ? "[" + str + "]": null,
//                        str -> str).toListString());
//    }
//    
//    @Test
//    public void testMapThen_2() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings(
//                "[O-n, T-w, T-h, F-o, F-i]",
//                stream.mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        (a, b) -> a + "-" + b).toListString());
//    }
//    
//    @Test
//    public void testMapThen_3() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five");
//        assertStrings(
//                "[O-n-e, T-w-o, T-h-r, F-o-u, F-i-v]",
//                stream.mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        $S.charAt(2),
//                        (a, b, c) -> a + "-" + b + "-" + c).toListString());
//    }
//    
//    @Test
//    public void testMapThen_4() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "None", "Ten", "Eleven", "Twelve");
//        assertStrings(
//                "[T-h-r-e, F-o-u-r, F-i-v-e, S-e-v-e, E-i-g-h, N-o-n-e, E-l-e-v, T-w-e-l]",
//                stream
//                    .filter($S.length().thatGreaterThanOrEqualsTo(4))
//                    .mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        $S.charAt(2),
//                        $S.charAt(3),
//                        (a, b, c, d) -> a + "-" + b + "-" + c + "-" + d).toListString());
//    }
//    
//    @Test
//    public void testMapThen_5() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "None", "Ten", "Eleven", "Twelve");
//        assertStrings(
//                "[T-h-r-e-e, S-e-v-e-n, E-i-g-h-t, E-l-e-v-e, T-w-e-l-v]",
//                stream
//                    .filter($S.length().thatGreaterThanOrEqualsTo(5))
//                    .mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        $S.charAt(2),
//                        $S.charAt(3),
//                        $S.charAt(4),
//                        (a, b, c, d, e) -> a + "-" + b + "-" + c + "-" + d + "-" + e).toListString());
//    }
//    
//    @Test
//    public void testMapThen_6() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "None", "Ten", "Eleven", "Twelve");
//        assertStrings(
//                "[E-l-e-v-e-n, T-w-e-l-v-e]",
//                stream
//                    .filter($S.length().thatGreaterThanOrEqualsTo(6))
//                    .mapThen(
//                        $S.charAt(0),
//                        $S.charAt(1),
//                        $S.charAt(2),
//                        $S.charAt(3),
//                        $S.charAt(4),
//                        $S.charAt(5),
//                        (a, b, c, d, e, f) -> a + "-" + b + "-" + c + "-" + d + "-" + e + "-" + f).toListString());
//    }
//    
//    //-- StreamPlusWithMapToMap --
//    
//    @Test
//    public void testMapToMap_1() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:O}, "
//                + "{<1>:T}, "
//                + "{<1>:F}, "
//                + "{<1>:S}, "
//                + "{<1>:E}, "
//                + "{<1>:T}, "
//                + "{<1>:S}]",
//                stream
//                    .filter($S.length().thatGreaterThanOrEqualsTo(1))
//                    .mapToMap(
//                            "<1>", $S.charAt(0))
//                    .map(map -> map.sorted())
//                    .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_2() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:O, <2>:n}, "
//                + "{<1>:T, <2>:h}, "
//                + "{<1>:F, <2>:i}, "
//                + "{<1>:S, <2>:e}, "
//                + "{<1>:E, <2>:l}, "
//                + "{<1>:T, <2>:h}, "
//                + "{<1>:S, <2>:e}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(2))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_3() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:O, <2>:n, <3>:e}, "
//                + "{<1>:T, <2>:h, <3>:r}, "
//                + "{<1>:F, <2>:i, <3>:v}, "
//                + "{<1>:S, <2>:e, <3>:v}, "
//                + "{<1>:E, <2>:l, <3>:e}, "
//                + "{<1>:T, <2>:h, <3>:i}, "
//                + "{<1>:S, <2>:e, <3>:v}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(3))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_4() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:T, <2>:h, <3>:r, <4>:e}, "
//                + "{<1>:F, <2>:i, <3>:v, <4>:e}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e}, "
//                + "{<1>:E, <2>:l, <3>:e, <4>:v}, "
//                + "{<1>:T, <2>:h, <3>:i, <4>:r}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(4))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_5() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:T, <2>:h, <3>:r, <4>:e, <5>:e}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}, "
//                + "{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e}, "
//                + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(5))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3),
//                        "<5>", $S.charAt(4))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_6() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:E, <2>:l, <3>:e, <4>:v, <5>:e, <6>:n}, "
//                + "{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(6))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3),
//                        "<5>", $S.charAt(4),
//                        "<6>", $S.charAt(5))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_7() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(7))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3),
//                        "<5>", $S.charAt(4),
//                        "<6>", $S.charAt(5),
//                        "<7>", $S.charAt(6))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_8() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:T, <2>:h, <3>:i, <4>:r, <5>:t, <6>:e, <7>:e, <8>:n}, "
//                + "{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(8))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3),
//                        "<5>", $S.charAt(4),
//                        "<6>", $S.charAt(5),
//                        "<7>", $S.charAt(6),
//                        "<8>", $S.charAt(7))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_9() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen");
//        assertStrings(
//                "[{<1>:S, <2>:e, <3>:v, <4>:e, <5>:n, <6>:t, <7>:e, <8>:e, <9>:n}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(9))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3),
//                        "<5>", $S.charAt(4),
//                        "<6>", $S.charAt(5),
//                        "<7>", $S.charAt(6),
//                        "<8>", $S.charAt(7),
//                        "<9>", $S.charAt(8))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToMap_10() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven", "Thirteen", "Seventeen", "Nineteen", "Twenty-three");
//        assertStrings(
//                "[{<10>:r, <1>:T, <2>:w, <3>:e, <4>:n, <5>:t, <6>:y, <7>:-, <8>:t, <9>:h}]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(10))
//                .mapToMap(
//                        "<1>", $S.charAt(0),
//                        "<2>", $S.charAt(1),
//                        "<3>", $S.charAt(2),
//                        "<4>", $S.charAt(3),
//                        "<5>", $S.charAt(4),
//                        "<6>", $S.charAt(5),
//                        "<7>", $S.charAt(6),
//                        "<8>", $S.charAt(7),
//                        "<9>", $S.charAt(8),
//                        "<10>", $S.charAt(9))
//                .map(map -> map.sorted())
//                .toListString());
//    }
//    
//    //-- StreamPlusWithMapToTuple --
//    
//    @Test
//    public void testMapToTuple_2() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[(O,n), (T,h), (F,i), (S,e), (E,l)]",
//                stream
//                    .filter($S.length().thatGreaterThanOrEqualsTo(2))
//                    .mapToTuple($S.charAt(0), $S.charAt(1))
//                    .toListString());
//    }
//    
//    @Test
//    public void testMapToTuple_3() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[(O,n,e), (T,h,r), (F,i,v), (S,e,v), (E,l,e)]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(3))
//                .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2))
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToTuple_4() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[(T,h,r,e), (F,i,v,e), (S,e,v,e), (E,l,e,v)]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(4))
//                .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3))
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToTuple_5() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[(T,h,r,e,e), (S,e,v,e,n), (E,l,e,v,e)]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(5))
//                .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4))
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToTuple_6() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[(E,l,e,v,e,n)]",
//                stream
//                .filter($S.length().thatGreaterThanOrEqualsTo(6))
//                .mapToTuple($S.charAt(0), $S.charAt(1), $S.charAt(2), $S.charAt(3), $S.charAt(4), $S.charAt(5))
//                .toListString());
//    }
//    
//    //-- StreamPlusWithMapWithIndex --
//    
//    @Test
//    public void testMapWithIndex() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[(0,One), (1,Three), (2,Five), (3,Seven), (4,Eleven)]",
//                stream
//                .mapWithIndex()
//                .toListString());
//    }
//    
//    @Test
//    public void testMapWithIndex_combine() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
//                stream
//                .mapWithIndex((i, each) -> i + ": " + each)
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToObjWithIndex_combine() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[0: One, 1: Three, 2: Five, 3: Seven, 4: Eleven]",
//                stream
//                .mapToObjWithIndex((i, each) -> i + ": " + each)
//                .toListString());
//    }
//    
//    @Test
//    public void testMapWithIndex_map_combine() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[0: 3, 1: 5, 2: 4, 3: 5, 4: 6]",
//                stream
//                .mapWithIndex(each -> each.length(), (i, each) -> i + ": " + each)
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToObjWithIndex_map_combine() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings(
//                "[0: 3, 1: 5, 2: 4, 3: 5, 4: 6]",
//                stream
//                .mapToObjWithIndex(each -> each.length(), (i, each) -> i + ": " + each)
//                .toListString());
//    }
//    
//    //-- StreamPlusWithMapWithPrev --
//    
//    @Test
//    public void testMapWithPrev() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings(
//                "(Result:{ NotExist },One), "
//                + "(Result:{ Value: One },Two), "
//                + "(Result:{ Value: Two },Three)", 
//                stream.mapWithPrev().join(", "));
//    }
//    
//    @Test
//    public void testMapWithPrev_combine() {
//        var stream = StreamPlus.of("One", "Two", "Three");
//        assertStrings("3, 6, 8", stream.mapWithPrev((prev, element) -> prev.orElse("").length() + element.length()).join(", "));
//    }
//    
//    //-- StreamPlusWithModify --
//    
//    @Test
//    public void testAccumulate1() {
//        var stream = StreamPlus.of(1, 2, 3, 4, 5);
//        assertStrings(
//                "1, 3, 6, 10, 15",
//                stream
//                    .accumulate((a, b)->a+b)
//                    .join(", "));
//    }
//    
//    @Test
//    public void testAccumulate2() {
//        var stream = StreamPlus.of(1, 2, 3, 4, 5);
//        assertStrings(
//                "1, 12, 123, 1234, 12345",
//                stream
//                    .accumulate((prev, current)->prev*10 + current)
//                    .join(", "));
//    }
//    
//    @Test
//    public void testRestate1() {
//        var stream = IntStreamable.infiniteInt().map(i -> i % 5).limit(20).boxed().stream();
//        assertStrings(
//                "0, 1, 2, 3, 4",
//              stream
//                  .restate((a, s)->s.filter(x -> x != a))
//                  .join   (", "));
//    }
//    
//    // sieve of eratosthenes
//    @Test
//    public void testRestate2() {
//        var stream = IntStreamable.infiniteInt().skip(2).boxed().stream();
//        assertStrings(
//                "2, 3, 5, 7, 11, 13, 17, 19, 23, 29, "
//              + "31, 37, 41, 43, 47, 53, 59, 61, 67, 71, "
//              + "73, 79, 83, 89, 97, 101, 103, 107, 109, 113, "
//              + "127, 131, 137, 139, 149, 151, 157, 163, 167, 173, "
//              + "179, 181, 191, 193, 197, 199, 211, 223, 227, 229, "
//              + "233, 239, 241, 251, 257, 263, 269, 271, 277, 281",
//              stream
//                  .restate((a, s)->s.filter(x -> x % a != 0))
//                  .limit(60)
//                  .join (", "));
//    }
//    
//    @Test
//    public void testSpawn() {
//        var timePrecision = 100;
//        var stream = StreamPlus.of("Two", "Three", "Four", "Eleven");
//        var first  = new AtomicLong(-1);
//        var logs   = new ArrayList<String>();
//        stream
//        .spawn(str -> {
//            return Sleep(str.length()*timePrecision + 5).thenReturn(str).defer();
//        })
//        .forEach(element -> {
//            first.compareAndSet(-1, System.currentTimeMillis());
//            var start    = first.get();
//            var end      = System.currentTimeMillis();
//            var duration = Math.round((end - start)/(1.0 * timePrecision))*timePrecision;
//            logs.add(element + " -- " + duration);
//        });
//        assertEquals("["
//                + "Result:{ Value: Two } -- 0, "
//                + "Result:{ Value: Four } -- " + (1*timePrecision) + ", "
//                + "Result:{ Value: Three } -- " + (2*timePrecision) + ", "
//                + "Result:{ Value: Eleven } -- " + (3*timePrecision) + ""
//                + "]",
//                logs.toString());
//    }
//    
//    @Test
//    public void testSpawn_limit() {
//        var stream  = StreamPlus.of("Two", "Three", "Four", "Eleven");
//        var first   = new AtomicLong(-1);
//        var actions = new ArrayList<DeferAction<String>>();
//        var logs    = new ArrayList<String>();
//        stream
//        .spawn(str -> {
//            var action = Sleep(str.length()*50 + 5).thenReturn(str).defer();
//            actions.add(action);
//            return action;
//        })
//        .limit(1)
//        .forEach(element -> {
//            first.compareAndSet(-1, System.currentTimeMillis());
//            var start    = first.get();
//            var end      = System.currentTimeMillis();
//            var duration = Math.round((end - start)/50.0)*50;
//            logs.add(element + " -- " + duration);
//        });
//        assertEquals("[Result:{ Value: Two } -- 0]",
//                logs.toString());
//        assertEquals(
//                "Result:{ Value: Two }, " +
//                "Result:{ Cancelled: Stream closed! }, " +
//                "Result:{ Cancelled: Stream closed! }, " +
//                "Result:{ Cancelled: Stream closed! }",
//                actions.stream().map(DeferAction::getResult).map(String::valueOf).collect(Collectors.joining(", ")));
//    }
//    
//    @Test
//    public void testSegmentSize() {
//        assertEquals(
//                "[0, 1, 2, 3, 4, 5], "
//                + "[6, 7, 8, 9, 10, 11], "
//                + "[12, 13, 14, 15, 16, 17], "
//                + "[18, 19]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segmentSize(6)
//                .map        (s -> s.toList())
//                .join       (", ")
//        );
//    }
//    
//    @Test
//    public void testSegmentSize_excludeTail() {
//        assertEquals(
//                "[0, 1, 2, 3, 4, 5], "
//                + "[6, 7, 8, 9, 10, 11], "
//                + "[12, 13, 14, 15, 16, 17]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segmentSize(6, false)
//                .map        (s -> s.toList())
//                .join       (", ")
//        );
//    }
//    
//    @Test
//    public void testSegmentSize_includeIncomplete() {
//        assertEquals(
//                "[0, 1, 2, 3, 4, 5], "
//                + "[6, 7, 8, 9, 10, 11], "
//                + "[12, 13, 14, 15, 16, 17], "
//                + "[18, 19]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segmentSize(6, IncompletedSegment.included)
//                .map        (s -> s.toList())
//                .join       (", ")
//                );
//    }
//    
//    @Test
//    public void testSegmentSize_excludeIncomplete() {
//        assertEquals(
//                "[0, 1, 2, 3, 4, 5], "
//                + "[6, 7, 8, 9, 10, 11], "
//                + "[12, 13, 14, 15, 16, 17]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segmentSize(6, IncompletedSegment.excluded)
//                .map        (s -> s.toList())
//                .join       (", ")
//            );
//    }
//    
//    @Test
//    public void testSegmentSize_function() {
//        assertEquals(
//                "[], " + 
//                "[1], " +
//                "[2, 3], " +
//                "[4, 5, 6, 7], " +
//                "[8, 9, 10, 11, 12, 13, 14, 15], " +
//                "[16, 17, 18, 19]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segmentSize(i -> i)
//                .map        (s -> s.toList())
//                .join       (", ")
//                );
//    }
//    
//    @Test
//    public void testSegmentStartCondition() {
//        assertEquals(
//                "[0, 1, 2], "
//                + "[3, 4, 5], "
//                + "[6, 7, 8], "
//                + "[9, 10, 11], "
//                + "[12, 13, 14], "
//                + "[15, 16, 17], "
//                + "[18, 19]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segment    (theInteger.thatIsDivisibleBy(3))
//                .map        (s -> s.toList())
//                .join       (", ")
//        );
//    }
//    
//    @Test
//    public void testSegmentStartCondition_includeIncomplete() {
//        assertEquals(
//                "[0, 1, 2], "
//                + "[3, 4, 5], "
//                + "[6, 7, 8], "
//                + "[9, 10, 11], "
//                + "[12, 13, 14], "
//                + "[15, 16, 17], "
//                + "[18, 19]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segment    (theInteger.thatIsDivisibleBy(3), IncompletedSegment.included)
//                .map        (s -> s.toList())
//                .join       (", ")
//                );
//    }
//    
//    @Test
//    public void testSegmentStartCondition_excludeIncomplete() {
//        assertEquals(
//                "[0, 1, 2], "
//                + "[3, 4, 5], "
//                + "[6, 7, 8], "
//                + "[9, 10, 11], "
//                + "[12, 13, 14], "
//                + "[15, 16, 17]",
//                IntStreamable
//                .infiniteInt()
//                .boxed      ()
//                .streamPlus ()
//                .limit      (20)
//                .segment    (theInteger.thatIsDivisibleBy(3), IncompletedSegment.excluded)
//                .map        (s -> s.toList())
//                .join       (", ")
//                );
//    }
//    
//    @Test
//    public void testSegmentCondition() {
//        Predicate<Integer> startCondition = i ->(i % 10) == 3;
//        Predicate<Integer> endCondition   = i ->(i % 10) == 6;
//        
//        assertEquals("[[53, 54, 55, 56], " +
//                      "[63, 64, 65, 66], " +
//                      "[73, 74, 75, 76]]",
//                IntStreamable
//                .infiniteInt()
//                .boxed  ()
//                .stream ()
//                .segment(startCondition, endCondition)
//                .skip   (5)
//                .limit  (3)
//                .map    (StreamPlus::toListString)
//                .toListString());
//        
//        assertEquals("[[53, 54, 55, 56], " +
//                      "[63, 64, 65, 66], " +
//                      "[73, 74, 75, 76]]",
//                IntStreamable
//                .infiniteInt()
//                .boxed  ()
//                .stream ()
//                .segment(startCondition, endCondition, true)
//                .skip   (5)
//                .limit  (3)
//                .map    (StreamPlus::toListString)
//                .toListString());
//        
//        assertEquals("[[53, 54, 55], " +
//                      "[63, 64, 65], " +
//                      "[73, 74, 75]]",
//                IntStreamable
//                .infiniteInt()
//                .boxed  ()
//                .stream ()
//                .segment(startCondition, endCondition, false)
//                .skip   (5)
//                .limit  (3)
//                .map    (StreamPlus::toListString)
//                .toListString());
//        
//        assertEquals("[[53, 54, 55, 56], " +
//                      "[63, 64, 65, 66], " +
//                      "[73, 74, 75, 76]]",
//                IntStreamable
//                .infiniteInt()
//                .boxed  ()
//                .stream ()
//                .segment(startCondition, endCondition, IncompletedSegment.included)
//                .skip   (5)
//                .limit  (3)
//                .map    (StreamPlus::toListString)
//                .toListString());
//        
//        assertEquals("[[53, 54, 55], " +
//                      "[63, 64, 65], " +
//                      "[73, 74, 75]]",
//                IntStreamable
//                .infiniteInt()
//                .boxed  ()
//                .stream ()
//                .segment(startCondition, endCondition, IncompletedSegment.excluded)
//                .skip   (5)
//                .limit  (3)
//                .map    (StreamPlus::toListString)
//                .toListString());
//    }
//    
//    @Test
//    public void testCollapse() {
//        var stream1 = StreamPlus.of(1, 2, 3, 4, 5, 6);
//        // Because 3 and 6 do match the condition to collapse ... so they are merged with the one before them.
//        assertEquals(
//                "1, 5, 4, 11",
//                stream1.collapseWhen(
//                        i -> (i % 3) == 0,
//                        (a,b)->a+b
//                    ).join(", "));
//        
//        var stream2 = StreamPlus.of(1, 2, 3, 4, 5, 6);
//        assertEquals(
//                "1, 2, 7, 5, 6",
//                stream2.collapseWhen(
//                        i -> (i % 3) == 1,
//                        (a,b)->a+b
//                    ).join(", "));
//        
//        var stream3 = StreamPlus.of(1, 2, 3, 4, 5, 6);
//        assertEquals(
//                "1, 9, 11",
//                stream3.collapseWhen(
//                        i -> (i % 3) <= 1,
//                        (a,b)->a+b
//                    ).join(", "));
//    }
//    
//    @Test
//    public void testCollapseSize() {
//        var stream1 = IntStreamable.infiniteInt().limit(20).boxed();
//        assertEquals(
//                "1, 5, 22, 92, 70",
//                stream1.collapseSize(
//                        i -> i,
//                        (a,b)->a+b
//                    ).join(", "));
//        
//        var stream2 = IntStreamable.infiniteInt().limit(20).boxed();
//        assertEquals(
//                "1, 2-3, 4-5-6-7, 8-9-10-11-12-13-14-15, 16-17-18-19",
//                stream2.collapseSize(
//                        i -> i,
//                        i -> "" + i,
//                        (a,b)->a + "-" + b
//                    ).join(", "));
//    }
//    
//    //-- StreamPlusWithPeek --
//    
//    @Test
//    public void testPeekClass() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        var elementStrings = new ArrayList<String>();
//        var elementIntegers = new ArrayList<Integer>();
//        stream.peek(String.class, elementStrings::add).peek(Integer.class, elementIntegers::add).toList();
//        assertStrings("[One, Three, Five]", elementStrings);
//        assertStrings("[0, 2, 4]", elementIntegers);
//    }
//    
//    @Test
//    public void testPeekBy() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        var elementStrings = new ArrayList<String>();
//        var elementIntegers = new ArrayList<Integer>();
//        stream
//            .peekBy(String.class::isInstance, e -> elementStrings.add((String)e))
//            .peekBy(Integer.class::isInstance, e -> elementIntegers.add((Integer)e))
//            .toList();
//        assertStrings("[One, Three, Five]", elementStrings);
//        assertStrings("[0, 2, 4]", elementIntegers);
//    }
//    
//    @Test
//    public void testPeekAs() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        var elementStrings = new ArrayList<String>();
//        stream
//            .peekAs(e -> "<" + e + ">", e -> elementStrings.add((String)e))
//            .toList();
//        assertStrings("[<0>, <One>, <2>, <Three>, <4>, <Five>]", elementStrings);
//    }
//    
//    @Test
//    public void testPeekBy_map() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        var elementStrings = new ArrayList<String>();
//        stream
//            .peekBy(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add("" + e))
//            .toList();
//        assertStrings("[0, One, 2, Three, 4]", elementStrings);
//    }
//    
//    @Test
//    public void testPeekAs_map() {
//        StreamPlus<Object> stream = StreamPlus.of(0, "One", 2, "Three", 4, "Five");
//        var elementStrings = new ArrayList<String>();
//        stream
//            .peekAs(e -> "<" + e + ">", s -> !s.contains("v"), e -> elementStrings.add((String)e))
//            .toList();
//        assertStrings("[<0>, <One>, <2>, <Three>, <4>]", elementStrings);
//    }
//    
//    //-- StreamPlusWithPipe --
//    
//    @Test
//    public void testPipeable() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings("[One, Three, Five, Seven, Eleven]", stream.pipable().pipeTo(StreamPlus::toListString));
//    }
//    
//    @Test
//    public void testPipe() {
//        var stream = StreamPlus.of("One", "Three", "Five", "Seven", "Eleven");
//        assertStrings("[One, Three, Five, Seven, Eleven]", stream.pipeTo(StreamPlus::toListString));
//    }
//    
//    //-- StreamPlusWithSort --
//    
//    @Test
//    public void testSortedBy() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four");
//        assertStrings("[One, Two, Four, Three]", stream.sortedBy(String::length).toList());
//    }
//    
//    @Test
//    public void testSortedByComparator() {
//        var stream = StreamPlus.of("One", "Two", "Three", "Four");
//        assertStrings("[Three, Four, One, Two]", stream.sortedBy(String::length, (a,b)->b-a).toList());
//    }
//    
//    //-- StreamPlusWithSplit --
//    
//    @Test
//    public void testSplit_1() {
//        Function<StreamPlus<String>, FuncList<String>> streamPlusToList = s -> s.toImmutableList();
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six");
//        assertStrings("([One, Two, Six],"
//                + "[Four, Five, Three])",
//                stream
//                .split(theString.length().thatEquals(3))
//                .map(
//                        streamPlusToList,
//                        streamPlusToList)
//                .toString());
//    }
//    
//    @Test
//    public void testSplit_2() {
//        Function<StreamPlus<String>, FuncList<String>> streamPlusToList = s -> s.toImmutableList();
//        var stream = StreamPlus.of("One", "Two", "Three", "Four", "Five", "Six");
//        assertStrings("([One, Two, Six],"
//                     + "[Four, Five],"
//                     + "[Three])",
//                stream
//                .split(
//                    s -> s.length() == 3,
//                    s -> s.length() == 4)
//                .map(
//                    streamPlusToList,
//                    streamPlusToList,
//                    streamPlusToList)
//                .toString());
//    }
//    
//    @Test
//    public void testFizzBuzz() {
//        Function<StreamPlus<Integer>, FuncList<Integer>> streamPlusToList = s -> s.toImmutableList();
//        var stream  = infiniteInt().limit(20).boxed().streamPlus();
//        var toString =
//                With(FuncMap.underlineMap.butWith(LinkedHashMap))
//                .run(()->{
//                    var splited
//                            = stream
//                            .split(
//                                "FizzBuzz", i -> i % (3*5) == 0,
//                                "Buzz",     i -> i % 5     == 0,
//                                "Fizz",     i -> i % 3     == 0,
//                                null);
//                    return splited
//                            .mapValue(streamPlusToList)
//                            .toString();
//                });
//        assertEquals("{"
//                + "FizzBuzz:[0, 15], "
//                + "Buzz:[5, 10], "
//                + "Fizz:[3, 6, 9, 12, 18], "
//                + "null:[]}",
//                toString);
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Test
    public void testMap() {
        assertEquals("[3, 3, 5, 4, 4, 3, 5]", 
                Streamable.of("One", "Two", "Three", "Four", "Five", "Six", "Seven")
                        .map(String::length)
                        .toListString());
    }
    
    @Test
    public void testCycle() {
        var stream = Streamable.cycle("One", "Two", "Three");
        assertStrings("Two, Three, One, Two, Three", stream.skip(1).limit(5).join(", "));
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
    public void testMapWithPrev() {
        var stream = Streamable.of("One", "Two", "Three").mapWithPrev((prev, element) -> prev.orElse("").length() + element.length());
        assertEquals("3, 6, 8", stream.join(", "));
        assertEquals("3, 6, 8", stream.join(", "));
    }
    
    @Test
    public void testMapWithPrev_each_prior() {
        // Check if mapWithPrev is "Each-Prior" adverb in Q
        var stream = Streamable.infiniteInt().limit(5).mapWithPrev((prev, element) -> element - prev.orElse(0));
        assertEquals("0, 1, 1, 1, 1", stream.join(", "));
    }
    
    @Test
    public void testAccumulate() {
        var stream = Streamable.of(1, 2, 3, 4, 5);
        assertEquals("1, 3, 6, 10, 15", stream.accumulate((a, b)->a+b).join(", "));
    }
    
}
