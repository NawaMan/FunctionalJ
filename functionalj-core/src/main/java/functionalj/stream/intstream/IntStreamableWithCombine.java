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
import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.Streamable;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;

public interface IntStreamableWithCombine {
    
    public IntStreamable deriveWith(Function<IntStream, IntStream> action);
    
    public <TARGET> Streamable<TARGET> deriveFrom(Function<IntStream, Stream<TARGET>> action);
    
    
    public default IntStreamable concatWith(
            IntStream tail) {
        return deriveWith(stream -> 
                IntStreamPlus
                    .from(stream)
                    .concatWith(tail));
    }
    
    public default IntStreamable merge(
            IntStream anotherStream) {
        return deriveWith(stream -> 
                IntStreamPlus
                    .from(stream)
                    .merge(anotherStream));
    }
    
    // TODO - Allow mapping before combiner, selecting
    //-- Zip --
    
    public default <ANOTHER, TARGET> Streamable<TARGET> combineWith(
            Stream<ANOTHER>                   anotherStream, 
            IntObjBiFunction<ANOTHER, TARGET> combinator) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .combineWith(anotherStream, combinator));
    }
    public default <ANOTHER, TARGET> Streamable<TARGET> combineWith(
            Stream<ANOTHER>                   anotherStream, 
            ZipWithOption                     option, 
            IntObjBiFunction<ANOTHER, TARGET> combinator) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .combineWith(anotherStream, option, combinator));
    }
    
    public default <ANOTHER> Streamable<IntTuple2<ANOTHER>> zipWith(
            Stream<ANOTHER> anotherStream) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream));
    }
    public default <ANOTHER> Streamable<IntTuple2<ANOTHER>> zipWith(
            Stream<ANOTHER> anotherStream, 
            ZipWithOption   option) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream, option));
    }
    
    public default <ANOTHER, TARGET> Streamable<TARGET> zipWith(
            Stream<ANOTHER>                   anotherStream, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream, merger));
    }
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <ANOTHER, TARGET> Streamable<TARGET> zipWith(
            Stream<ANOTHER>                   anotherStream, 
            ZipWithOption                     option,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream, option, merger));
    }
    
    public default Streamable<IntIntTuple> zipWith(
            IntStream anotherStream) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream));
    }
    public default Streamable<IntIntTuple> zipWith(
            IntStream anotherStream,
            int       defaultValue) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream, defaultValue));
    }
    
    public default IntStreamable zipWith(
            IntStream              anotherStream, 
            IntBiFunctionPrimitive merger) {
        return deriveWith(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream, merger));
    }
    public default IntStreamable zipWith(
            IntStream              anotherStream, 
            IntBiFunctionPrimitive merger,
            int                    defaultValue) {
        return deriveWith(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipWith(anotherStream, merger, defaultValue));
    }
    
    public default <T> Streamable<T> zipToObjWith(
            IntStream           anotherStream, 
            IntIntBiFunction<T> merger) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipToObjWith(anotherStream, merger));
    }
    public default <T> Streamable<T> zipToObjWith(
            IntStream           anotherStream, 
            IntIntBiFunction<T> merger,
            int                 defaultValue) {
        return deriveFrom(stream -> 
                IntStreamPlus
                    .from(stream)
                    .zipToObjWith(anotherStream, merger, defaultValue));
    }
    
    public default IntStreamable choose(
            IntStreamPlus           anotherStream, 
            IntBiPredicatePrimitive selectThisNotAnother) {
        return deriveWith(stream -> 
                IntStreamPlus
                    .from(stream)
                    .choose(anotherStream, selectThisNotAnother));
    }
    
}
