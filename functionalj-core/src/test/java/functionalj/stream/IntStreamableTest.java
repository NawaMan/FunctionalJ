package functionalj.stream;

import static functionalj.lens.Access.theInteger;
import static functionalj.lens.Access.theString;
import static functionalj.stream.ZipWithOption.AllowUnpaired;
import static functionalj.stream.ZipWithOption.RequireBoth;
import static functionalj.stream.intstream.IntStreamable.compound;
import static functionalj.stream.intstream.IntStreamable.cycle;
import static functionalj.stream.intstream.IntStreamable.empty;
import static functionalj.stream.intstream.IntStreamable.emptyIntStreamable;
import static functionalj.stream.intstream.IntStreamable.generate;
import static functionalj.stream.intstream.IntStreamable.generateWith;
import static functionalj.stream.intstream.IntStreamable.infinite;
import static functionalj.stream.intstream.IntStreamable.infiniteInt;
import static functionalj.stream.intstream.IntStreamable.ints;
import static functionalj.stream.intstream.IntStreamable.iterate;
import static functionalj.stream.intstream.IntStreamable.loop;
import static functionalj.stream.intstream.IntStreamable.naturalNumbers;
import static functionalj.stream.intstream.IntStreamable.ones;
import static functionalj.stream.intstream.IntStreamable.range;
import static functionalj.stream.intstream.IntStreamable.repeat;
import static functionalj.stream.intstream.IntStreamable.steamableOf;
import static functionalj.stream.intstream.IntStreamable.wholeNumbers;
import static functionalj.stream.intstream.IntStreamable.zeroes;
import static functionalj.stream.intstream.IntStreamable.zipOf;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.junit.Ignore;
import org.junit.Test;

import functionalj.function.FuncUnit0;
import functionalj.list.intlist.ImmutableIntFuncList;
import functionalj.promise.DeferAction;
import functionalj.stream.intstream.IntStreamPlus;
import functionalj.stream.intstream.IntStreamable;
import lombok.val;

public class IntStreamableTest {
    
    void run(FuncUnit0 runnable) {
        runnable.run();
        runnable.run();
    }
    
    @Test
    public void testEmpty() {
        val streamble = empty();
        run(()->{
            assertEquals("[]", streamble.toListString());
        });
    }
    
    @Test
    public void testEmptyIntStream() {
        val streamble = emptyIntStreamable();
        run(()->{
            assertEquals("[]", streamble.toListString());
        });
    }
    
    @Test
    public void testOf() {
        val intArray = new int[] {1, 1, 2, 3, 5, 8};
        
        val streamble1 = IntStreamable.of(intArray);
        run(()->{
            assertEquals("[1, 1, 2, 3, 5, 8]", streamble1.toListString());
        });
        
        val streamble2 = steamableOf(intArray);
        run(()->{
            assertEquals("[1, 1, 2, 3, 5, 8]", streamble2.toListString());
        });
        
        val streamble3 = ints(intArray);
        run(()->{
            assertEquals("[1, 1, 2, 3, 5, 8]", streamble3.toListString());
        });
        
        val nullStreamble = IntStreamable.of(null);
        run(()->{
            assertEquals("[]", nullStreamble.toListString());
        });
        
        val zeroStreamble = IntStreamable.of(new int[0]);
        run(()->{
            assertEquals("[]", zeroStreamble.toListString());
        });
    }
    
    @Test
    public void testOf_immutable() {
        val intArray = new int[] {1, 1, 2, 3, 5, 8};
        val streamble = steamableOf(intArray);
        run(()->{
            intArray[0] = 0;
            assertEquals("[1, 1, 2, 3, 5, 8]", streamble.toListString());
            // NOICE ------^  The value are not changed after.
        });
    }
    
    @Test
    public void testZeroes() {
        val zeroes  = zeroes();
        val zeroes6 = zeroes(6);
        run(()->{
            assertEquals("[0, 0, 0, 0, 0]",    zeroes.limit(5).toListString());
            assertEquals("[0, 0, 0, 0, 0, 0]", zeroes6.toListString());
        });
    }
    
    @Test
    public void testOnes() {
        val ones  = ones();
        val ones6 = ones(6);
        run(()->{
            assertEquals("[1, 1, 1, 1, 1]",    ones.limit(5).toListString());
            assertEquals("[1, 1, 1, 1, 1, 1]", ones6.toListString());
        });
    }
    
    @Test
    public void testRepeat() {
        val streamable = repeat(1, 2, 3);
        run(()->{
            assertEquals("[1, 2, 3, 1, 2, 3, 1, 2, 3, 1]", streamable.limit(10).toListString());
        });
    }
    
    @Test
    public void testCycle() {
        val streamable = cycle(1, 2, 3);
        run(()->{
            assertEquals("[1, 2, 3, 1, 2, 3, 1, 2, 3, 1, 2]", streamable.limit(11).toListString());
        });
    }
    
    @Test
    public void testLoop() {
        val loop  = loop();
        val loop5 = loop(5);
        run(()->{
            assertEquals("[0, 1, 2, 3, 4]", loop.limit(5).toListString());
            assertEquals("[0, 1, 2, 3, 4]", loop5.toListString());
        });
    }
    
    @Test
    public void testInfinite() {
        val streamable  = infinite();
        run(()->{
            assertEquals("[5, 6, 7, 8, 9]", streamable.skip(5).limit(5).toListString());
        });
    }
    
    @Test
    public void testInfiniteInt() {
        val streamable  = infiniteInt();
        run(()->{
            assertEquals("[5, 6, 7, 8, 9]", streamable.skip(5).limit(5).toListString());
        });
    }
    
    @Test
    public void testNaturalNumbers() {
        val streamable1 = naturalNumbers();
        val streamable2 = naturalNumbers(5);
        run(()->{
            assertEquals("[1, 2, 3, 4, 5]", streamable1.limit(5).toListString());
            assertEquals("[1, 2, 3, 4, 5]", streamable2.toListString());
        });
    }
    
    @Test
    public void testWholeNumbers() {
        val streamable1 = wholeNumbers();
        val streamable2 = wholeNumbers(5);
        run(()->{
            assertEquals("[0, 1, 2, 3, 4]", streamable1.limit(5).toListString());
            assertEquals("[0, 1, 2, 3, 4]", streamable2.toListString());
        });
    }
    
    @Test
    public void testRange() {
        val streamable  = range(7, 12);
        run(()->{
            assertEquals("[7, 8, 9, 10, 11]", streamable.toListString());
        });
    }
    
    @Test
    public void testGenerate() {
        val streamable  = generate(()->5);
        run(()->{
            assertEquals("[5, 5, 5]", streamable.limit(3).toListString());
        });
    }
    
    @Test
    public void testGenerateWith() {
        val streamable  = generateWith(()->IntStreamPlus.of(1, 2, 3));
        run(()->{
            assertEquals("[1, 2, 3]", streamable.toListString());
        });
    }
    
    @Test
    public void testIterate() {
        val streamable1  = iterate(1, a -> a + 1);
        run(()->{
            assertEquals("[6, 7, 8, 9, 10]", streamable1.skip(5).limit(5).toListString());
        });
        
        val streamable2  = iterate(1, 1, (a, b) -> a + b);
        run(()->{
            assertEquals("[1, 1, 2, 3, 5, 8, 13]", streamable2.limit(7).toListString());
        });
    }
    
    @Test
    public void testConcat() {
        val range1  = range(0, 5);
        val range2  = range(21, 27);
        val streamable  = IntStreamable.concat(range1, range2);
        run(()->{
            assertEquals("["
                            + "0, 1, 2, 3, 4, "
                            + "21, 22, 23, 24, 25, 26"
                        + "]", 
                        streamable.toListString());
        });
    }
    
    @Test
    public void testCompound() {
        val streamable1 = compound(1, i -> i * 2);
        run(()->{
            assertEquals("[32, 64, 128, 256, 512]", 
                    streamable1.skip(5).limit(5).toListString());
        });
        
        val streamable2 = compound(1, 2, (a, b) -> a * 3 + b);
        run(()->{
            assertEquals("[1, 2, 5, 11, 26]", 
                    streamable2.limit(5).toListString());
        });
    }
    
    @Test
    public void testZipOf() {
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8)]", 
                        zipOf(streamable1, streamable2)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (0,9), (0,10), (0,11)]", 
                        zipOf(streamable1, streamable2, 0)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (-1,9), (-1,10), (-1,11)]", 
                        zipOf(streamable1, -1, streamable2, 1)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(8, 12);
            run(()->{
                assertEquals(
                        "[(5,8), (6,9), (7,10), (8,11), (9,1), (10,1), (11,1)]", 
                        zipOf(streamable1, -1, streamable2, 1)
                        .toListString());
            });
        }
        
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8]", 
                        Streamable.zipOf(streamable1, streamable2, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, 0-9, 0-10, 0-11]", 
                        Streamable.zipOf(streamable1, streamable2, 0, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, -1-9, -1-10, -1-11]", 
                        Streamable.zipOf(streamable1, -1, streamable2, 1, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(8, 12);
            run(()->{
                assertEquals(
                        "[5-8, 6-9, 7-10, 8-11, 9-1, 10-1, 11-1]", 
                        Streamable.zipOf(streamable1, -1, streamable2, 1, (a, b) -> a + "-" + b)
                        .toListString());
                    });
        }
        
        {
            val streamable1 = range(5, 10);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals("[7, 9, 11, 13, 15]", 
                        zipOf(streamable1, streamable2, (a, b) -> a + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 10);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[7, 9, 11, 13, 15, 7, 8, 9, 10, 11]", 
                        zipOf(streamable1, streamable2, 0, (a, b) -> a + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 10);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[7, 9, 11, 13, 15, 6, 7, 8, 9, 10]", 
                        zipOf(streamable1, -1, streamable2, 1, (a, b) -> a + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(8, 12);
            run(()->{
                assertEquals(
                        "[13, 15, 17, 19, 10, 11, 12]", 
                        zipOf(streamable1, -1, streamable2, 1, (a, b) -> a + b)
                        .toListString());
            });
        }
    }
    
    @Test
    public void testZipWith() {
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8)]", 
                        streamable1.zipWith(streamable2)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (0,9), (0,10), (0,11)]", 
                        streamable1.zipWith(streamable2, 0)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[(5,2), (6,3), (7,4), (8,5), (9,6), (10,7), (11,8), (-1,9), (-1,10), (-1,11)]", 
                        streamable1.zipWith(streamable2, -1, 1)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(8, 12);
            run(()->{
                assertEquals(
                        "[(5,8), (6,9), (7,10), (8,11), (9,1), (10,1), (11,1)]", 
                        streamable1.zipWith(streamable2, -1, 1)
                        .toListString());
            });
        }
        
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8]", 
                        streamable1.zipToObjWith(streamable2, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, 0-9, 0-10, 0-11]", 
                        streamable1.zipToObjWith(streamable2, 0, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8, -1-9, -1-10, -1-11]", 
                        streamable1.zipToObjWith(streamable2, -1, 1, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(8, 12);
            run(()->{
                assertEquals(
                        "[5-8, 6-9, 7-10, 8-11, 9-1, 10-1, 11-1]", 
                        streamable1.zipToObjWith(streamable2, -1, 1, (a, b) -> a + "-" + b)
                        .toListString());
                    });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[5-2, 6-3, 7-4, 8-5, 9-6, 10-7, 11-8]", 
                        streamable1.zipToObjWith(streamable2.boxed(), AllowUnpaired, (a, b) -> a + "-" + b)
                        .toListString());
            });
        }
        
        {
            val streamable1 = range(5, 10);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals("[7, 9, 11, 13, 15]", 
                        streamable1.zipWith(streamable2, (a, b) -> a + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 10);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[7, 9, 11, 13, 15, 7, 8, 9, 10, 11]", 
                        streamable1.zipWith(streamable2, 0, (a, b) -> a + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 10);
            val streamable2 = range(2, 12);
            run(()->{
                assertEquals(
                        "[7, 9, 11, 13, 15, 6, 7, 8, 9, 10]", 
                        streamable1.zipWith(streamable2, -1, 1, (a, b) -> a + b)
                        .toListString());
            });
        }
        {
            val streamable1 = range(5, 12);
            val streamable2 = range(8, 12);
            run(()->{
                assertEquals(
                        "[13, 15, 17, 19, 10, 11, 12]", 
                        streamable1.zipWith(streamable2, -1, 1, (a, b) -> a + b)
                        .toListString());
            });
        }
    }
    
    @Test
    public void testMap() {
        val streamable = ints(1, 1, 2, 3, 5, 8);
        run(()->{
            assertEquals(
                    "[2, 2, 4, 6, 10, 16]", 
                    streamable
                    .map(i -> i * 2)
                    .toListString());
        });
    }
    
    @Test
    public void testMapToInt() {
        val streamable = ints(1, 1, 2, 3, 5, 8);
        run(()->{
            assertEquals(
                    "[2, 2, 4, 6, 10, 16]", 
                    streamable
                    .mapToInt(i -> i * 2)
                    .toListString());
        });
    }
    
//    @Test
//    public void testMapToLong() {
//        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
//        assertEquals(
//                "[2, 2, 4, 6, 10, 16]", 
//                intStream
//                .mapToLong(i -> i * 2)
//                .toListString());
//    }
//    
//    @Test
//    public void testMapToDouble() {
//        val intStream = IntStreamPlus.of(1, 1, 2, 3, 5, 8);
//        assertEquals(
//                "[2.0, 2.0, 4.0, 6.0, 10.0, 16.0]", 
//                intStream
//                .mapToDouble(i -> i * 2)
//                .toListString());
//    }
    
    @Test
    public void testMapToObj() {
        val streamable = ints(1, 1, 2, 3, 5, 8);
        run(()->{
            assertEquals(
                    "['1', '1', '2', '3', '5', '8']", 
                    streamable
                    .mapToObj(i -> "'" + i + "'")
                    .toListString());
        });
    }
    
    @Test
    public void testFlatMap() {
        val streamable = ints(1, 2, 3, 5);
        run(()->{
            assertEquals(
                    "[1, 2, 2, 3, 3, 3, 5, 5, 5, 5, 5]", 
                    streamable
                    .flatMap(i -> cycle(i).limit(i))
                    .toListString());
        });
    }
    
    @Test
    public void testFlatMapToObj() {
        val streamable = ints(1, 2, 3, 5);
        IntFunction<? extends Streamable<String>> mapper = i -> Streamable.of(cycle(i).limit(i).toListString());
        assertEquals(
                "[[1], [2, 2], [3, 3, 3], [5, 5, 5, 5, 5]]", 
                streamable
                .flatMapToObj(mapper)
                .toListString());
    }
    
    @Test
    public void testFilter() {
        val streamable = loop(10);
        run(()->{
            assertEquals(
                    "[1, 3, 5, 7, 9]", 
                    streamable
                    .filter(i -> i % 2 == 1)
                    .toListString());
            assertEquals(
                    "[0, 1, 2]",
                    streamable
                    .filter(theInteger.time(3),
                            theInteger.thatLessThan(9))
                    .toListString());
            assertEquals(
                    "[0, 1, 2, 3]",
                    streamable
                    .filterAsObject(
                            (int i) -> "" + (i*3),
                            theString.length().eq(1))
                    .toListString());
            assertEquals(
                    "[0, 1, 2, 3]",
                    streamable
                    .filterAsObject(
                            theInteger.time(3).asString(),
                            theString.length().eq(1))
                    .toListString());
        });
    }
    
    @Test
    public void testPeek() {
        val streamable = loop(5);
        run(()->{
            val list = new ArrayList<String>();
            assertEquals(
                    "[0, 1, 2, 3, 4]", 
                    streamable
                    .peek(i -> list.add("" + i))
                    .toListString());
            assertEquals(
                    "[0, 1, 2, 3, 4]", 
                    list.toString());
        });
    }
    
    @Test
    public void testLimit() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21);
        run(()->{
            assertEquals(
                    "[1, 1, 2, 3]", 
                    streamable
                    .limit(4)
                    .toListString());
        });
    }
    
    @Test
    public void testSkip() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21);
        run(()->{
            assertEquals(
                    "[5, 8, 13, 21]", 
                    streamable
                    .skip(4)
                    .toListString());
        });
    }
    
    @Test
    public void testSkipWhile() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .toListString());
            assertEquals(
                    "[0, 3, 3, 1, 4, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .skipWhile(i -> i != 0)
                    .toListString());
        });
    }
    
    @Test
    public void testSkipUtil() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .toListString());
            assertEquals(
                    "[0, 3, 3, 1, 4, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .skipUntil(i -> i == 0)
                    .toListString());
        });
    }
    
    @Test
    public void testTakeWhile() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .toListString());
            assertEquals(
                    "[1, 1, 2, 3]", 
                    streamable
                    .map(i -> i % 5)
                    .takeWhile(i -> i != 0)
                    .toListString());
        });
    }
    
    @Test
    public void testTakeUtil() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[1, 1, 2, 3, 0, 3, 3, 1, 4, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .toListString());
            assertEquals(
                    "[1, 1, 2, 3]", 
                    streamable
                    .map(i -> i % 5)
                    .takeUntil(i -> i == 0)
                    .toListString());
        });
    }
    
    @Test
    public void testDistinct() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[1, 2, 3, 0, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .distinct()
                    .toListString());
        });
    }
    
    @Test
    public void testSorted() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[0, 0, 1, 1, 1, 2, 3, 3, 3, 4, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .sorted()
                    .toListString());
        });
    }
    
    @Test
    public void testSortedBy() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[0, 0, 1, 1, 1, 2, 3, 3, 3, 4, 4]", 
                    streamable
                    .map(i -> i % 5)
                    .sorted()
                    .toListString());
        });
    }
    
    @Test
    public void testSortedBy_mapper() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[34, 21, 13, 8, 5, 55, 3, 2, 1, 1, 89]", 
                    streamable
                    .sortedBy(i -> Math.abs(i - 30))
                    .toListString());
            assertEquals(
                    "[34 -> 4, 21 -> 9, 13 -> 17, 8 -> 22, 5 -> 25, 55 -> 25, 3 -> 27, 2 -> 28, 1 -> 29, 1 -> 29, 89 -> 59]", 
                    streamable
                    .sortedBy(i -> Math.abs(i - 30))
                    .mapToObj(i -> "" + i + " -> " + Math.abs(i - 30))
                    .toListString());
        });
    }
    
    @Test
    public void testSortedBy_mapper_comparator() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[89, 1, 1, 2, 3, 5, 55, 8, 13, 21, 34]", 
                    streamable
                    .sortedBy(i -> Math.abs(i - 30), (a, b) -> b - a)
                    .toListString());
        });
    }
    
    @Test
    public void testSortedBy_mapper_object() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[1, 1, 13, 2, 21, 3, 34, 5, 55, 8, 89]", 
                    streamable
                    .sortedByObj(i -> "" + i)
                    .toListString());
        });
    }
    
    @Test
    public void testSortedBy_mapper_object_with_comparator() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            assertEquals(
                    "[13, 21, 34, 55, 89, 1, 1, 2, 3, 5, 8]", 
                    streamable
                    .sortedByObj(i -> "" + i, (a, b) -> b.length() - a.length())
                    .toListString());
        });
    }
    
    @Test
    public void testForEachOrdered() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89);
        run(()->{
            val list = new ArrayList<Integer>();
            streamable
            .map(i -> Math.abs(i - 30))
            .forEachOrdered(list::add);
            assertEquals(
                    "[29, 29, 28, 27, 25, 22, 17, 9, 4, 25, 59]", 
                    list.toString());
        });
    }
    
    @Test
    public void testReduce() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    OptionalInt.of(88), 
                    streamable
                    .reduce((a, b) -> a + b));
        });
    }
    
    @Test
    public void testReduce_withIdentity() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    188, 
                    streamable
                    .reduce(100, (a, b) -> a + b));
        });
    }
    
    @Test
    public void testCollect() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    ",1,1,2,3,5,8,13,21,34", 
                    streamable
                    .collect(
                            ()-> new StringBuffer(), 
                            (StringBuffer a, int          b)-> a.append("," + b), 
                            (StringBuffer a, StringBuffer b)-> a.append(";" + b))
                    .toString());
        });
    }
    
    @Test
    public void testMin() {
        val streamable1 = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        val streamable2 = ints();
        run(()->{
            assertEquals(
                    "OptionalInt[0]", 
                    streamable1
                    .map(i -> Math.abs(i - 34))
                    .min()
                    .toString());
            assertEquals(
                    "OptionalInt.empty", 
                    streamable2
                    .min()
                    .toString());
        });
    }
    
    @Test
    public void testMax() {
        val streamable1 = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        val streamable2 = ints();
        run(()->{
            assertEquals(
                    "OptionalInt[33]", 
                    streamable1
                    .map(i -> Math.abs(i - 34))
                    .max()
                    .toString());
            assertEquals(
                    "OptionalInt.empty", 
                    streamable2
                    .max()
                    .toString());
        });
    }
    
    @Test
    public void testCountSize() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    9L, 
                    streamable
                    .count());
            assertEquals(
                    9, 
                    streamable
                    .size());
        });
    }
    
    @Test
    public void testMatch() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertTrue(streamable.anyMatch(i -> i == 13));
            assertFalse(streamable.anyMatch(i -> i == 14));
            
            assertTrue(streamable.allMatch(i -> i < 50));
            assertFalse(streamable.allMatch(i -> i < 20));
            
            assertTrue(streamable.noneMatch(i -> i == 15));
            assertFalse(streamable.noneMatch(i -> i == 21));
        });
    }
    
    @Test
    public void testFind() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(OptionalInt.of(21),  streamable.filter(i -> i == 21).findFirst());
            assertEquals(OptionalInt.empty(), streamable.filter(i -> i == 55).findFirst());
            
            assertEquals(OptionalInt.of(21),  streamable.filter(i -> i == 21).findAny());
            assertEquals(OptionalInt.empty(), streamable.filter(i -> i == 55).findAny());
        });
    }
    
    @Test
    public void testAsStream() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    StreamPlus.of(1, 1, 2, 3, 5, 8, 13, 21, 34).toListString(),
                    streamable.intStream().toListString());
        });
    }
    
    @Test
    public void testToArray() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertArrayEquals(
                    new int[] {1, 1, 2, 3, 5, 8, 13, 21, 34},
                    streamable.toArray());
        });
    }
    
    @Test
    public void testSum() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    88,
                    streamable.sum());
        });
    }
    
    @Test
    public void testAverage() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34, 55);
        run(()->{
            assertEquals(
                    OptionalDouble.of(14.3),
                    streamable.average());
        });
    }
    
    @Test
    public void testSummaryStatistics() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "IntSummaryStatistics{count=9, sum=88, min=1, average=9.777778, max=34}",
                    streamable.summaryStatistics().toString());
        });
    }
    
    @Test
    public void testBoxed() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    StreamPlus.of(1, 1, 2, 3, 5, 8, 13, 21, 34).toList(),
                    streamable.boxed().toList());
        });
    }
    
    @Test
    public void testToImmutableList() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    ImmutableIntFuncList.of(1, 1, 2, 3, 5, 8, 13, 21, 34),
                    streamable.toImmutableList());
        });
    }
    
    @Test
    public void testJoinToString() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "112358132134",
                    streamable.joinToString());
            assertEquals(
                    "1, 1, 2, 3, 5, 8, 13, 21, 34",
                    streamable.joinToString(", "));
        });
    }
    
    @Test
    public void testPipeable() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "-[1, 1, 2, 3, 5, 8, 13, 21, 34]-",
                    streamable.pipable().pipeTo(i -> "-" + i.toListString() + "-").toString());
            assertEquals(
                    "-[1, 1, 2, 3, 5, 8, 13, 21, 34]-",
                    streamable.pipable().pipeTo(i -> "-" + i.toListString() + "-").toString());
        });
    }
    
    @Test
    public void testSpawn() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "["
                    +   "Result:{ Value: 13 }, "
                    +   "Result:{ Value: 21 }, "
                    +   "Result:{ Value: 8 }"
                    + "]",
                    streamable
                        .spawn(i -> DeferAction.from(()->{ Thread.sleep(100*Math.abs(i - 15)); return i; }))
                        .limit(3)
                        .toListString());
        });
    }
    
    @Test
    public void testAccumulate() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "[1, 2, 4, 7, 12, 20, 33, 54, 88]",
                    streamable
                        .accumulate((a, i) -> a + i)
                        .toListString());
        });
    }
    
    @Test
    public void testRestate() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "[1, 0, 1, 1, 2, 3, 5, 8, 13]",
                    streamable
                        .restate((i, s) -> s.map(j -> j - i))
                        .toListString());
        });
    }
    
    @Test
    public void testMapFirst() {
        val streamable = wholeNumbers(14);
        run(()->{
            assertEquals(
                    "["
                    + "2, null, 2, null, 2, null, 2, null, 2, null, "
                    + "2, null, 2, null"
                    + "]",
                    streamable
                        .mapFirst(i -> (Integer)(((i % 2) == 0) ? 2 : null))
                        .toListString());
            assertEquals(
                    "["
                    + "2, null, 2, 3, 2, null, 2, null, 2, 3, "
                    + "2, null, 2, null"
                    + "]",
                    streamable
                        .mapFirst(
                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
                                i -> (Integer)(((i % 3) == 0) ? 3 : null))
                        .toListString());
            assertEquals(
                    "["
                    + "2, null, 2, 3, 2, 5, 2, null, 2, 3, "
                    + "2, null, 2, null"
                    + "]",
                    streamable
                        .mapFirst(
                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
                                i -> (Integer)(((i % 5) == 0) ? 5 : null))
                        .toListString());
            assertEquals(
                    "["
                    + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
                    + "2, null, 2, null"
                    + "]",
                    streamable
                        .mapFirst(
                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
                                i -> (Integer)(((i % 5) == 0) ? 5 : null),
                                i -> (Integer)(((i % 7) == 0) ? 7 : null))
                        .toListString());
            assertEquals(
                    "["
                    + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
                    + "2, 11, 2, null"
                    + "]",
                    streamable
                        .mapFirst(
                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
                                i -> (Integer)(((i % 5) == 0) ? 5 : null),
                                i -> (Integer)(((i % 7) == 0) ? 7 : null),
                                i -> (Integer)(((i % 11) == 0) ? 11 : null))
                        .toListString());
            assertEquals(
                    "["
                    + "2, null, 2, 3, 2, 5, 2, 7, 2, 3, "
                    + "2, 11, 2, 13"
                    + "]",
                    streamable
                        .mapFirst(
                                i -> (Integer)(((i % 2) == 0) ? 2 : null),
                                i -> (Integer)(((i % 3) == 0) ? 3 : null),
                                i -> (Integer)(((i % 5) == 0) ? 5 : null),
                                i -> (Integer)(((i % 7) == 0) ? 7 : null),
                                i -> (Integer)(((i % 11) == 0) ? 11 : null),
                                i -> (Integer)(((i % 13) == 0) ? 13 : null))
                        .toListString());
        });
    }
    
    @Test
    public void testMapThen() {
        val streamable = wholeNumbers(5);
        run(()->{
            assertEquals(
                    "["
                    + "(0)((0)), "
                    + "(1)((1)), "
                    + "(2)((2)), "
                    + "(3)((3)), "
                    + "(4)((4))]",
                    streamable
                        .mapThen(
                                i -> format("(%s)", i),
                                i -> format("((%s))", i),
                                (a,b)->a + b)
                        .toListString());
            assertEquals(
                    "["
                    + "(0)((0))(((0))), "
                    + "(1)((1))(((1))), "
                    + "(2)((2))(((2))), "
                    + "(3)((3))(((3))), "
                    + "(4)((4))(((4)))"
                    + "]",
                    streamable
                        .mapThen(
                                i -> format("(%s)", i),
                                i -> format("((%s))", i),
                                i -> format("(((%s)))", i),
                                (a,b,c)->a + b + c)
                        .toListString());
            assertEquals(
                    "["
                    + "(0)((0))(((0)))((((0)))), "
                    + "(1)((1))(((1)))((((1)))), "
                    + "(2)((2))(((2)))((((2)))), "
                    + "(3)((3))(((3)))((((3)))), "
                    + "(4)((4))(((4)))((((4))))"
                    + "]",
                    streamable
                        .mapThen(
                                i -> format("(%s)", i),
                                i -> format("((%s))", i),
                                i -> format("(((%s)))", i),
                                i -> format("((((%s))))", i),
                                (a,b,c,d)->a + b + c +d)
                        .toListString());
            assertEquals(
                    "["
                    + "(0)((0))(((0)))((((0))))(((((0))))), "
                    + "(1)((1))(((1)))((((1))))(((((1))))), "
                    + "(2)((2))(((2)))((((2))))(((((2))))), "
                    + "(3)((3))(((3)))((((3))))(((((3))))), "
                    + "(4)((4))(((4)))((((4))))(((((4)))))"
                    + "]",
                    streamable
                        .mapThen(
                                i -> format("(%s)", i),
                                i -> format("((%s))", i),
                                i -> format("(((%s)))", i),
                                i -> format("((((%s))))", i),
                                i -> format("(((((%s)))))", i),
                                (a,b,c,d,e)->a + b + c + d + e)
                        .toListString());
            assertEquals(
                    "["
                    + "(0)((0))(((0)))((((0))))(((((0)))))((((((0)))))), "
                    + "(1)((1))(((1)))((((1))))(((((1)))))((((((1)))))), "
                    + "(2)((2))(((2)))((((2))))(((((2)))))((((((2)))))), "
                    + "(3)((3))(((3)))((((3))))(((((3)))))((((((3)))))), "
                    + "(4)((4))(((4)))((((4))))(((((4)))))((((((4))))))"
                    + "]",
                    streamable
                        .mapThen(
                                i -> format("(%s)", i),
                                i -> format("((%s))", i),
                                i -> format("(((%s)))", i),
                                i -> format("((((%s))))", i),
                                i -> format("(((((%s)))))", i),
                                i -> format("((((((%s))))))", i),
                                (a,b,c,d,e,f)->a + b + c + d + e + f)
                        .toListString());
        });
    }
    
    @Test
    public void testMapTuple() {
        val streamable = wholeNumbers(5);
        run(()->{
            assertEquals(
                    "["
                    + "(0,#0), "
                    + "(1,#1), "
                    + "(2,#2), "
                    + "(3,#3), "
                    + "(4,#4)"
                    + "]",
                    streamable
                        .mapTuple(
                                i -> i,
                                i -> "#" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "(0,#0,##0), "
                    + "(1,#1,##1), "
                    + "(2,#2,##2), "
                    + "(3,#3,##3), "
                    + "(4,#4,##4)"
                    + "]",
                    streamable
                        .mapTuple(
                                i -> i,
                                i -> "#" + i,
                                i -> "##" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "(0,#0,##0,###0), "
                    + "(1,#1,##1,###1), "
                    + "(2,#2,##2,###2), "
                    + "(3,#3,##3,###3), "
                    + "(4,#4,##4,###4)"
                    + "]",
                    streamable
                        .mapTuple(
                                i -> i,
                                i -> "#" + i,
                                i -> "##" + i,
                                i -> "###" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "(0,#0,##0,###0,####0), "
                    + "(1,#1,##1,###1,####1), "
                    + "(2,#2,##2,###2,####2), "
                    + "(3,#3,##3,###3,####3), "
                    + "(4,#4,##4,###4,####4)"
                    + "]",
                    streamable
                        .mapTuple(
                                i -> i,
                                i -> "#" + i,
                                i -> "##" + i,
                                i -> "###" + i,
                                i -> "####" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "(0,#0,##0,###0,####0,#####0), "
                    + "(1,#1,##1,###1,####1,#####1), "
                    + "(2,#2,##2,###2,####2,#####2), "
                    + "(3,#3,##3,###3,####3,#####3), "
                    + "(4,#4,##4,###4,####4,#####4)"
                    + "]",
                    streamable
                        .mapTuple(
                                i -> i,
                                i -> "#" + i,
                                i -> "##" + i,
                                i -> "###" + i,
                                i -> "####" + i,
                                i -> "#####" + i)
                        .toListString());
        });
    }
    
    @Test
    public void testMapToMap() {
        val streamable = wholeNumbers(5);
        run(()->{
            assertEquals(
                    "["
                    + "{1:0}, "
                    + "{1:1}, "
                    + "{1:2}, "
                    + "{1:3}, "
                    + "{1:4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0}, "
                    + "{1:1, 2:#1}, "
                    + "{1:2, 2:#2}, "
                    + "{1:3, 2:#3}, "
                    + "{1:4, 2:#4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0}, "
                    + "{1:1, 2:#1, 3:##1}, "
                    + "{1:2, 2:#2, 3:##2}, "
                    + "{1:3, 2:#3, 3:##3}, "
                    + "{1:4, 2:#4, 3:##4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i,
                                "5", i -> "####" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i,
                                "5", i -> "####" + i,
                                "6", i -> "#####" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i,
                                "5", i -> "####" + i,
                                "6", i -> "#####" + i,
                                "7", i -> "######" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i,
                                "5", i -> "####" + i,
                                "6", i -> "#####" + i,
                                "7", i -> "######" + i,
                                "8", i -> "#######" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0, 9:########0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1, 9:########1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2, 9:########2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3, 9:########3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4, 9:########4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i,
                                "5", i -> "####" + i,
                                "6", i -> "#####" + i,
                                "7", i -> "######" + i,
                                "8", i -> "#######" + i,
                                "9", i -> "########" + i)
                        .toListString());
            assertEquals(
                    "["
                    + "{1:0, 2:#0, 3:##0, 4:###0, 5:####0, 6:#####0, 7:######0, 8:#######0, 9:########0, 10:#########0}, "
                    + "{1:1, 2:#1, 3:##1, 4:###1, 5:####1, 6:#####1, 7:######1, 8:#######1, 9:########1, 10:#########1}, "
                    + "{1:2, 2:#2, 3:##2, 4:###2, 5:####2, 6:#####2, 7:######2, 8:#######2, 9:########2, 10:#########2}, "
                    + "{1:3, 2:#3, 3:##3, 4:###3, 5:####3, 6:#####3, 7:######3, 8:#######3, 9:########3, 10:#########3}, "
                    + "{1:4, 2:#4, 3:##4, 4:###4, 5:####4, 6:#####4, 7:######4, 8:#######4, 9:########4, 10:#########4}"
                    + "]",
                    streamable
                        .mapToMap(
                                "1", i -> i,
                                "2", i -> "#" + i,
                                "3", i -> "##" + i,
                                "4", i -> "###" + i,
                                "5", i -> "####" + i,
                                "6", i -> "#####" + i,
                                "7", i -> "######" + i,
                                "8", i -> "#######" + i,
                                "9", i -> "########" + i,
                                "10", i -> "#########" + i)
                        .toListString());
        });
    }
    
    @Test
    public void testSegment_fixedSize() {
        Function<IntStreamPlus, String> streamToString = s -> s.toListString();
        
        val streamable = wholeNumbers(10);
        run(()->{
            assertEquals("["
                    + "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9]"
                    + "]",
                    streamable
                    .segment(3)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8]"
                    + "]",
                    streamable
                    .segment(3, false)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9]"
                    + "]",
                    streamable
                    .segment(3, true)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8]"
                    + "]",
                    streamable
                    .segment(3, IncompletedSegment.excluded)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2], "
                    + "[3, 4, 5], "
                    + "[6, 7, 8], "
                    + "[9]"
                    + "]",
                    streamable
                    .segment(3, IncompletedSegment.included)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9]"
                    + "]",
                    streamable
                    .segment(i -> i % 4 == 0)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9]"
                    + "]",
                    streamable
                    .segment(i -> i % 4 == 0, IncompletedSegment.included)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9]"
                    + "]",
                    streamable
                    .segment(i -> i % 4 == 0, true)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[0, 1, 2, 3], "
                    + "[4, 5, 6, 7]"
                    + "]",
                    streamable
                    .segment(i -> i % 4 == 0, IncompletedSegment.excluded)
                    .map(streamToString)
                    .toListString());
        });
    }
    
    @Test
    public void testSegment_conditions() {
        Function<IntStreamPlus, String> streamToString = s -> s.toListString();
        
        IntPredicate startCondition = i ->(i % 10) == 3;
        IntPredicate endCondition   = i ->(i % 10) == 6;
        
        val streamable = wholeNumbers(75);
        run(()->{
            assertEquals("["
                    + "[53, 54, 55, 56], " 
                    + "[63, 64, 65, 66], "
                    + "[73, 74]"
                    + "]",
                    streamable
                    .segment(startCondition, endCondition)
                    .skip(5)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[53, 54, 55, 56], " 
                    + "[63, 64, 65, 66], "
                    + "[73, 74]"
                    + "]",
                    streamable
                    .segment(startCondition, endCondition, true)
                    .skip(5)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[53, 54, 55, 56], " 
                    + "[63, 64, 65, 66]"
                    + "]",
                    streamable
                    .segment(startCondition, endCondition, false)
                    .skip(5)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[53, 54, 55, 56], " 
                    + "[63, 64, 65, 66], "
                    + "[73, 74]"
                    + "]",
                    streamable
                    .segment(startCondition, endCondition, IncompletedSegment.included)
                    .skip(5)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[53, 54, 55, 56], " 
                    + "[63, 64, 65, 66]"
                    + "]",
                    streamable
                    .segment(startCondition, endCondition, IncompletedSegment.excluded)
                    .skip(5)
                    .map(streamToString)
                    .toListString());
        });
    }
    
    @Test
    public void testSegmentSize() {
        Function<IntStreamPlus, String> streamToString = s -> s.toListString();
        
        val streamable = wholeNumbers(30);
        run(()->{
            assertEquals("["
                    + "[], "
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
                    + "]",
                    streamable
                    .segmentSize(i -> i)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[], "
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
                    + "]",
                    streamable
                    .segmentSize(i -> i, true)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[], "
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9, 10, 11, 12, 13, 14, 15]"
                    + "]",
                    streamable
                    .segmentSize(i -> i, false)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[], "
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9, 10, 11, 12, 13, 14, 15], "
                    + "[16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29]"
                    + "]",
                    streamable
                    .segmentSize(i -> i, IncompletedSegment.included)
                    .map(streamToString)
                    .toListString());
            
            assertEquals("["
                    + "[], "
                    + "[1], "
                    + "[2, 3], "
                    + "[4, 5, 6, 7], "
                    + "[8, 9, 10, 11, 12, 13, 14, 15]"
                    + "]",
                    streamable
                    .segmentSize(i -> i, IncompletedSegment.excluded)
                    .map(streamToString)
                    .toListString());
        });
    }
    
    @Test
    public void testCollapseWhen() {
        val streamable = wholeNumbers(10);
        run(()->{
            // [0, 1, 2 + 3, 4, 5 + 6, 7, 8 + 9]
            assertEquals("[0, 1, 9, 11, 24]",
                    streamable
                    .collapseWhen(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
                    .toListString());
        });
    }
    
    @Test
    public void testCollapseAfter() {
        val streamable = wholeNumbers(10);
        run(()->{
            // [0 + 1, 2, 3 + 4 + 5, 6 + 7, 8 + 9]
            assertEquals("[1, 2, 12, 13, 17]",
                    streamable
                    .collapseAfter(i -> i % 3 == 0 || i % 4 == 0, (a, b) -> a + b)
                    .toListString());
        });
    }
    
    @Test
    public void testCollapseSize() {
        val streamable = wholeNumbers(10);
        run(()->{
            // [0, 1, 2 + 3, 4 + 5 + 6 + 7]
            assertEquals("[1, 5, 22, 17]",
                    streamable
                    .collapseSize(i -> i, (a, b) -> a + b)
                    .toListString());
            
            assertEquals("[1, 5, 22, 17]",
                    streamable
                    .collapseSize(i -> i, (a, b) -> a + b, true)
                    .toListString());
            
            assertEquals("[1, 5, 22]",
                    streamable
                    .collapseSize(i -> i, (a, b) -> a + b, false)
                    .toListString());
            
            assertEquals("[1, 5, 22, 17]",
                    streamable
                    .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.included)
                    .toListString());
            
            assertEquals("[1, 5, 22]",
                    streamable
                    .collapseSize(i -> i, (a, b) -> a + b, IncompletedSegment.excluded)
                    .toListString());
        });
    }
    
    @Test
    public void testConcatWith() {
        val streamable = range(0, 5);
        val anotherStreamble = range(21, 27);
        run(()->{
            assertEquals("["
                            + "0, 1, 2, 3, 4, "
                            + "21, 22, 23, 24, 25, 26"
                        + "]", 
                        streamable
                        .concatWith(anotherStreamble)
                        .toListString());
        });
    }
    
    @Test
    public void testMergeWith() {
        val streamable = range(0, 5);
        val anotherStreamble = range(21, 27);
        run(()->{
            assertEquals("[0, 21, 1, 22, 2, 23, 3, 24, 4, 25, 26]", 
                    streamable
                            .mergeWith(anotherStreamble)
                            .toListString());
        });
    }
    
    @Test
    public void testZipWith_boxed() {
        val streamable = range(0, 5);
        val anotherStreamble1 = range(21, 27);
        val anotherStreamble2 = range(21, 24);
        run(()->{
            assertEquals("[(0,21), (1,22), (2,23), (3,24), (4,25)]", 
                         streamable.zipWith(anotherStreamble1.boxed()).toListString());
            
            assertEquals("[(0,21), (1,22), (2,23), (3,null), (4,null)]", 
                         streamable.zipWith(anotherStreamble2.boxed(), AllowUnpaired).toListString());
            
            assertEquals("[(0,21), (1,22), (2,23)]", 
                         streamable.zipWith(anotherStreamble2.boxed(), RequireBoth).toListString());
            
            assertEquals("[21, 23, 25]", 
                         streamable.zipWith(anotherStreamble2.boxed(), (a, b) -> a + b).toListString());
        });
    }
    
    @Test
    public void testChoose() {
        val streamable = range(0, 5);
        val anotherStreamble = range(22, 30);
        run(()->{
            // 0 % 3 = 0 vs 22 % 2 = 0 => 22
            // 1 % 3 = 1 vs 23 % 2 = 1 => 23
            // 2 % 3 = 2 vs 24 % 2 = 0 =>  2
            // 3 % 3 = 0 vs 25 % 2 = 1 => 25
            // 4 % 3 = 1 vs 26 % 2 = 0 =>  4
            //           vs 27 % 2 = 1 => none
            //           vs 28 % 2 = 0 => none
            //           vs 29 % 2 = 1 => none
            assertEquals("[22, 23, 2, 25, 4]", 
                         streamable
                         .choose(anotherStreamble, (a, b) -> a % 3 > b % 2)
                         .toListString());
        });
    }
    
    static class Sum implements IntCollectorPlus<AtomicInteger, Integer> {
        
        @Override
        public Collector<Integer, AtomicInteger, Integer> collector() {
            return this;
        }
        
        @Override
        public Integer process(IntStreamPlus stream) {
            return stream.sum();
        }
        
        @Override
        public Supplier<AtomicInteger> supplier() {
            return () -> new AtomicInteger();
        }
        
        @Override
        public IntAccumulator<AtomicInteger> intAccumulator() {
            return (atomicInt, i) -> {
                atomicInt.set(atomicInt.get() + i);
            };
        }
        
        @Override
        public BinaryOperator<AtomicInteger> combiner() {
            return (i1, i2) -> new AtomicInteger(i1.get() + i2.get());
        }
        
        @Override
        public Function<AtomicInteger, Integer> finisher() {
            return i -> i.get();
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
        
    }
    
    static class Max implements IntCollectorPlus<AtomicInteger, Integer> {
        
        @Override
        public Collector<Integer, AtomicInteger, Integer> collector() {
            return this;
        }
        
        @Override
        public Integer process(IntStreamPlus stream) {
            return stream.max().getAsInt();
        }
        
        @Override
        public Supplier<AtomicInteger> supplier() {
            return () -> new AtomicInteger();
        }
        
        @Override
        public IntAccumulator<AtomicInteger> intAccumulator() {
            return (atomicInt, i) -> {
                atomicInt.set(Math.max(atomicInt.get(), i));
            };
        }
        
        @Override
        public BinaryOperator<AtomicInteger> combiner() {
            return (i1, i2) -> new AtomicInteger(Math.max(i1.get(), i2.get()));
        }
        
        @Override
        public Function<AtomicInteger, Integer> finisher() {
            return i -> i.get();
        }
        
        @Override
        public Set<Characteristics> characteristics() {
            return EnumSet.noneOf(Characteristics.class);
        }
        
    }
    
    @Test
    public void testCalculate() {
        val sum = new Sum();
        val max = new Max();
        val streamable = range(0, 10);
        run(()->{
            assertEquals("45", 
                         "" + streamable.calculate(sum));
            
            assertEquals("(45,9)", 
                         "" + streamable.calculate(sum, max));
            
            assertEquals("(45,9,45)", 
                         "" + streamable.calculate(sum, max, sum));
            
            assertEquals("(45,9,45,9)", 
                         "" + streamable.calculate(sum, max, sum, max));
            
            assertEquals("(45,9,45,9,45)", 
                         "" + streamable.calculate(sum, max, sum, max, sum));
            
            assertEquals("(45,9,45,9,45,9)", 
                         "" + streamable.calculate(sum, max, sum, max, sum, max));
        });
    }
    
    @Test
    public void testMapOnly() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[0, 10, 2, 30, 4, 50, 6, 70, 8, 90]", 
                    streamable
                    .mapOnly(theInteger.thatIsOdd(), theInteger.time(10))
                    .toListString());
        });
    }
    
    @Test
    public void testMapIf() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]", 
                        streamable
                        .mapIf(
                            theInteger.thatIsOdd(), 
                            theInteger.time(10), 
                            i -> i/2)
                        .toListString());
        });
    }
    
    @Test
    public void testMapToObjIf() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[0, 10, 1, 30, 2, 50, 3, 70, 4, 90]", 
                        streamable
                        .mapToObjIf(
                            theInteger.thatIsOdd(), 
                            i -> i * 10, 
                            i -> i / 2)
                        .toListString());
        });
    }
    
    @Test
    public void testMapWithIndex() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[0, 1, 4, 9, 16, 25, 36, 49, 64, 81]", 
                        streamable
                        .mapWithIndex(
                            (index, value)->index*value)
                        .toListString());
            
            assertEquals("[(0,0), (1,1), (2,2), (3,3), (4,4), (5,5), (6,6), (7,7), (8,8), (9,9)]", 
                        streamable
                        .mapWithIndex()
                        .toListString());
        });
    }
    
    @Ignore
    @Test
    public void testMapToObjWithIndex() {
//        val streamable = range(0, 10);
//        run(()->{
//            assertEquals("[0-0, 1-1, 2-2, 3-3, 4-4, 5-5, 6-6, 7-7, 8-8, 9-9]", 
//                        streamable
//                        .mapToObjWithIndex(
//                            (index, value)->index + "-" + value)
//                        .toListString());
//            
//            assertEquals("[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]", 
//                        streamable
//                        .mapToObjWithIndex(
//                                i -> "" + i,
//                                (index, value) -> index + ": " + value
//                        )
//                        .toListString());
//        });
    }
    
    @Test
    public void testMapWithPrev() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("["
                    + "(OptionalInt.empty,0), "
                    + "(OptionalInt[0],1), "
                    + "(OptionalInt[1],2), "
                    + "(OptionalInt[2],3), "
                    + "(OptionalInt[3],4), "
                    + "(OptionalInt[4],5), "
                    + "(OptionalInt[5],6), "
                    + "(OptionalInt[6],7), "
                    + "(OptionalInt[7],8), "
                    + "(OptionalInt[8],9)"
                    + "]", 
                    streamable
                    .mapWithPrev()
                    .toListString());
            
            assertEquals("["
                    + "OptionalInt.empty-0, "
                    + "OptionalInt[0]-1, "
                    + "OptionalInt[1]-2, "
                    + "OptionalInt[2]-3, "
                    + "OptionalInt[3]-4, "
                    + "OptionalInt[4]-5, "
                    + "OptionalInt[5]-6, "
                    + "OptionalInt[6]-7, "
                    + "OptionalInt[7]-8, "
                    + "OptionalInt[8]-9"
                    + "]", 
                    streamable
                    .mapWithPrev(
                        (prev, i) -> prev + "-" + i
                    )
                    .toListString());
        });
    }
    
    @Test
    public void testFilterIn() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[1, 3, 5, 7, 9]", 
                        streamable
                        .filterIn(1, 3, 5, 7, 9, 11, 13)
                        .toListString());
            
            assertEquals("[1, 3, 5, 7, 9]", 
                        streamable
                        .filterIn(asList(1, 3, 5, 7, 9, 11, 13))
                        .toListString());
        });
    }
    
    @Test
    public void testExcludeIn() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[5, 6, 7, 8, 9]", 
                        streamable
                        .exclude(i -> i < 5)
                        .toListString());
            
            assertEquals("[0, 2, 4, 6, 8]", 
                        streamable
                        .excludeIn(1, 3, 5, 7, 9, 11, 13)
                        .toListString());
            
            assertEquals("[0, 2, 4, 6, 8]", 
                        streamable
                        .excludeIn(asList(1, 3, 5, 7, 9, 11, 13))
                        .toListString());
        });
    }
    
    @Test
    public void testFilterWithIndex() {
        val streamable = range(0, 10);
        run(()->{
            assertEquals("[0, 1, 2, 3, 8, 9]", 
                        streamable
                        .filterWithIndex((i, v) -> i < 4 || v > 7)
                        .toListString());
        });
    }
    
    @Test
    public void testPeekMore() {
        val streamable = range(0, 10);
        run(()->{
            {
                val lines = new ArrayList<String>();
                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
                            streamable
                            .peek(theInteger.thatIsEven(), i -> lines.add("" + i))
                            .toListString());
                
                assertEquals(
                        "[0, 2, 4, 6, 8]", 
                        "" + lines);
            }
            
            {
                val lines = new ArrayList<String>();
                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
                            streamable
                            .peek((int    i) -> "--> " + i + ";", 
                                  (String s) -> lines.add(s))
                            .toListString());
                
                assertEquals(
                        "[--> 0;, --> 1;, --> 2;, --> 3;, --> 4;, --> 5;, --> 6;, --> 7;, --> 8;, --> 9;]", 
                        "" + lines);
            }
            
            {
                val lines = new ArrayList<String>();
                assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", 
                            streamable
                            .peek((int    i) -> "--> " + i + ";", 
                                  (String s) -> s.contains("5"),
                                  (String s) -> lines.add(s))
                            .toListString());
                
                assertEquals(
                        "[--> 5;]", 
                        "" + lines);
            }
        });
    }
    
    @Test
    public void testFlatMapOnly() {
        val streamable = range(0, 7);
        run(()->{
            //            [0, 1 -> [0], 2, 3->[0, 1, 2], 4, 5 -> [0, 1, 2, 3, 4], 6]
            //            [0,      [0], 2,    [0, 1, 2], 4,      [0, 1, 2, 3, 4], 6]
            //            [0,       0,  2,     0, 1, 2,  4,       0, 1, 2, 3, 4,  6]
            assertEquals("[0, 0, 2, 0, 1, 2, 4, 0, 1, 2, 3, 4, 6]", 
                        streamable
                        .flatMapOnly(theInteger.thatIsOdd(), i -> range(0, i)).toListString());
        });
    }
    
    @Test
    public void testFlatMapIf() {
        val streamable = range(0, 7);
        run(()->{
            //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
            //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
            //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
            assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]", 
                        streamable
                        .flatMapIf(
                                theInteger.thatIsOdd(), 
                                i -> range(0, i),
                                i -> IntStreamable.of(-i)
                        )
                        .toListString());
        });
    }
    
    @Test
    public void testFlatMapToObjIf() {
        val streamable = range(0, 7);
        run(()->{
            //      [0 -> [0], 1 -> [0], 2 -> [-2], 3->[0, 1, 2], 4 -> [-4], 5 -> [0, 1, 2, 3, 4], 6 -> [-6]]
            //      [     [0],      [0],      [-2],    [0, 1, 2],      [-4],      [0, 1, 2, 3, 4],      [-6]]
            //      [      0,        0,        -2,      0, 1, 2,        -4,        0, 1, 2, 3, 4,        -6]
            assertEquals("[0, 0, -2, 0, 1, 2, -4, 0, 1, 2, 3, 4, -6]", 
                    streamable
                    .flatMapToObjIf(
                            theInteger.thatIsOdd(), 
                            i -> range(0, i)         .boxed(),
                            i -> IntStreamable.of(-i).boxed()
                    )
                    .toListString());
        });
    }
    
    @Test
    public void testForEachWithIndex() {
        val streamable = range(0, 10);
        run(()->{
            val lines = new ArrayList<String>();
            streamable
            .forEachWithIndex((i, v) -> lines.add(i + ": " + v));
            
            assertEquals(
                    "[0: 0, 1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]", 
                    "" + lines);
        });
    }
    
    @Test
    public void testMinBy() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[34]", 
                    streamable
                    .minBy(i -> Math.abs(i - 34))
                    .toString());
            
            assertEquals(
                    "OptionalInt[1]", 
                    streamable
                    .minBy(i -> Math.abs(i - 34), (a, b)-> b - a)
                    .toString());
        });
    }
    
    @Test
    public void testMaxBy() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[1]", 
                    streamable
                    .maxBy(i -> Math.abs(i - 34))
                    .toString());
            assertEquals(
                    "OptionalInt[34]", 
                    streamable
                    .maxBy(i -> Math.abs(i - 34), (a, b)-> b - a)
                    .toString());
        });
    }
    
    @Test
    public void testMinOf() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[34]", 
                    streamable
                    .minOf(i -> Math.abs(i - 34))
                    .toString());
        });
    }
    
    @Test
    public void testMaxOf() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[1]", 
                    streamable
                    .maxOf(i -> Math.abs(i - 34))
                    .toString());
        });
    }
    
    @Test
    public void testMinMax() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "Optional[(1,34)]", 
                    streamable
                    .minMax()
                    .toString());
        });
    }
    
    @Test
    public void testMinMaxOf() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "Optional[(34,1)]", 
                    streamable
                    .minMaxOf(i -> Math.abs(i - 34))
                    .toString());
        });
    }
    
    @Test
    public void testMinMaxBy() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "Optional[(34,1)]", 
                    streamable
                    .minMaxBy(i -> Math.abs(i - 34))
                    .toString());
            
            assertEquals(
                    "Optional[(1,34)]", 
                    streamable
                    .minMaxBy(i -> Math.abs(i - 34), (a, b)-> b - a)
                    .toString());
        });
    }
    
    @Test
    public void testFindFirst() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[5]", 
                    streamable
                    .findFirst(i -> i % 5 == 0)
                    .toString());
            assertEquals(
                    "OptionalInt[5]", 
                    streamable
                    .findFirst(i -> i*2, i -> i % 5 == 0)
                    .toString());
        });
    }
    
    @Test
    public void testFindAny() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[5]", 
                    streamable
                    .findAny(i -> i % 5 == 0)
                    .toString());
            assertEquals(
                    "OptionalInt[5]", 
                    streamable
                    .findAny(i -> i*2, i -> i % 5 == 0)
                    .toString());
        });
    }
    
    @Test
    public void testFindFirstBy() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[13]", 
                    streamable
                    .findFirstBy(
                            i -> "" + i, 
                            s -> s.length() > 1)
                    .toString());
        });
    }
    
    @Test
    public void testFindAnyBy() {
        val streamable = ints(1, 1, 2, 3, 5, 8, 13, 21, 34);
        run(()->{
            assertEquals(
                    "OptionalInt[13]", 
                    streamable
                    .findAnyBy(
                            i -> "" + i, 
                            s -> s.length() > 1)
                    .toString());
        });
    }
    
}
