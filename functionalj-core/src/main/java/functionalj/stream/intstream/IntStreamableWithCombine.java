// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.IntBiFunctionPrimitive;
import functionalj.stream.Streamable;
import lombok.val;

public interface IntStreamableWithCombine {
    
    public IntStreamable derive(Function<IntStreamable, IntStream> action);
    
    public <TARGET> Streamable<TARGET> deriveToStreamable(
            IntStreamable                           source, 
            Function<IntStreamable, Stream<TARGET>> action);
    
    
//    public default IntStreamable concatWith(
//            IntStreamable tail) {
//        return deriveWith(stream -> {
//            val tailStream = tail.stream();
//            return IntStreamPlus
//                .from(stream)
//                .concatWith(tailStream);
//        });
//    }
//    
//    public default IntStreamable concatWith(
//            int ... tail) {
//        return deriveWith(stream -> {
//            val tailStream = IntStream.of(tail);
//            return IntStreamPlus
//                .from(stream)
//                .concatWith(tailStream);
//        });
//    }
//    
//    public default IntStreamable merge(
//            IntStreamable anotherStreamable) {
//        return deriveWith(stream -> {
//            val anotherStream = anotherStreamable.stream();
//            return IntStreamPlus
//                .from(stream)
//                .merge(anotherStream);
//        });
//    }
//    
//    public default IntStreamable merge(
//            int ... anothers) {
//        return deriveWith(stream -> {
//            val anotherStream = IntStream.of(anothers);
//            return IntStreamPlus
//                .from(stream)
//                .merge(anotherStream);
//        });
//    }
//    
//    // TODO - Allow mapping before combiner, selecting
//    //-- Zip --
//    
//    public default <ANOTHER, TARGET> Streamable<TARGET> combineWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            IntObjBiFunction<ANOTHER, TARGET> combinator) {
//        return deriveFrom(stream -> {
//            val anotherStream = anotherStreamable.stream();
//            return IntStreamPlus
//                .from(stream)
//                .combineWith(anotherStream, combinator);
//        });
//    }
//    public default <ANOTHER, TARGET> Streamable<TARGET> combineWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            ZipWithOption                     option, 
//            IntObjBiFunction<ANOTHER, TARGET> combinator) {
//        return deriveFrom(stream -> {
//            val anotherStream = anotherStreamable.stream();
//            return IntStreamPlus
//                .from(stream)
//                .combineWith(anotherStream, option, combinator);
//        });
//    }
//    
//    public default <ANOTHER> Streamable<IntTuple2<ANOTHER>> zipWith(
//            Streamable<ANOTHER> anotherStreamable) {
//        return deriveToStreamable(streamable -> {
//            val anotherStream = anotherStreamable.stream();
//            return streamable
//                .stream()
//                .zipWith(anotherStream);
//        });
//    }
//    public default <ANOTHER> Streamable<IntTuple2<ANOTHER>> zipWith(
//            Streamable<ANOTHER> anotherStreamable, 
//            ZipWithOption   option) {
//        return deriveToStreamable(stream -> {
//            val anotherStream = anotherStreamable.stream();
//            return IntStreamPlus
//                .from(stream)
//                .zipWith(anotherStream, option);
//        });
//    }
//    
//    public default <ANOTHER, TARGET> Streamable<TARGET> zipWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            IntObjBiFunction<ANOTHER, TARGET> merger) {
//        return derive(stream -> {
//            val anotherStream = anotherStreamable.stream();
//            return IntStreamPlus
//                .from(stream)
//                .zipWith(anotherStream, merger);
//        });
//    }
//    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
//    public default <ANOTHER, TARGET> Streamable<TARGET> zipWith(
//            Streamable<ANOTHER>               anotherStreamable, 
//            ZipWithOption                     option,
//            IntObjBiFunction<ANOTHER, TARGET> merger) {
//        return derive(stream -> {
//            val anotherStream = anotherStreamable.stream();
//            return IntStreamPlus
//                .from(stream)
//                .zipWith(anotherStream, option, merger);
//        });
//   }
//    
//    public default Streamable<IntIntTuple> zipWith(
//            IntStreamable anotherStreamable) {
//        return derive(streamble -> {
//            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
//            return streamble
//                    .stream()
//                    .zipWith(anotherStream);
//        });
//    }
//    public default Streamable<IntIntTuple> zipWith(
//            IntStreamable anotherStreamable,
//            int           defaultValue) {
//        return derive(stream ->  {
//            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
//            return IntStreamPlus
//                    .from(stream)
//                    .zipWith(anotherStream, defaultValue);
//        });
//    }
    
    public default IntStreamable zipWith(
            IntStreamable          anotherStreamable, 
            IntBiFunctionPrimitive merger) {
        return derive(streamble ->  {
            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
            return streamble
                    .stream()
                    .zipWith(anotherStream, merger);
        });
    }
    public default IntStreamable zipWith(
            IntStreamable          anotherStreamable, 
            IntBiFunctionPrimitive merger,
            int                    defaultValue) {
        return derive(streamble ->  {
            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
            return streamble
                    .stream()
                    .zipWith(anotherStream, merger, defaultValue);
        });
    }
    
//    public default <T> Streamable<T> zipToObjWith(
//            IntStreamable       anotherStreamable, 
//            IntIntBiFunction<T> merger) {
//        return deriveFrom(stream ->  {
//            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
//            return IntStreamPlus
//                    .from(stream)
//                    .zipToObjWith(anotherStream, merger);
//        });
//    }
//    public default <T> Streamable<T> zipToObjWith(
//            IntStreamable       anotherStreamable, 
//            IntIntBiFunction<T> merger,
//            int                 defaultValue) {
//        return deriveFrom(stream ->  {
//            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
//            return IntStreamPlus
//                    .from(stream)
//                    .zipToObjWith(anotherStream, merger, defaultValue);
//        });
//    }
//    
//    public default IntStreamable choose(
//            IntStreamable           anotherStreamable, 
//            IntBiPredicatePrimitive selectThisNotAnother) {
//        return deriveWith(stream -> {
//            val anotherStream = IntStreamPlus.from(anotherStreamable.stream());
//            return IntStreamPlus
//                .from(stream)
//                .choose(anotherStream, selectThisNotAnother);
//        });
//    }
    
}
