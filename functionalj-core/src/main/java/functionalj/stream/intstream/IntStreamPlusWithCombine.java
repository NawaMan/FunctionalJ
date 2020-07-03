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

import static functionalj.function.FuncUnit0.funcUnit0;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.IntBiFunctionPrimitive;
import functionalj.function.IntBiPredicatePrimitive;
import functionalj.function.IntIntBiFunction;
import functionalj.function.IntObjBiFunction;
import functionalj.stream.IntIteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.IntIntTuple;
import functionalj.tuple.IntTuple2;
import lombok.val;

public interface IntStreamPlusWithCombine {
    
    public IntStream     intStream();
    public IntStreamPlus useIterator(Func1<IntIteratorPlus, IntStreamPlus> action);
    
    public <TARGET> StreamPlus<TARGET> useIteratorToObj(Func1<IntIteratorPlus, StreamPlus<TARGET>> action);
    
    
    public default IntStreamPlus concatWith(IntStream tail) {
        return IntStreamPlus.concat(
            IntStreamPlus.from(intStream()),
            IntStreamPlus.from(tail)
        );
    }
    
    public default IntStreamPlus mergeWith(IntStream anotherStream) {
        val thisStream = intStream();
        val iteratorA  = IntStreamPlusHelper.rawIterator(thisStream);
        val iteratorB  = IntStreamPlusHelper.rawIterator(anotherStream);
        
        val resultStream 
                = IntStreamPlusHelper
                .doMergeInt(iteratorA, iteratorB);
        
        resultStream
                .onClose(()->{
                    funcUnit0(()->thisStream   .close()).runCarelessly();
                    funcUnit0(()->anotherStream.close()).runCarelessly();
                });
        return resultStream;
    }
    
    public default <ANOTHER> StreamPlus<IntTuple2<ANOTHER>> zipWith(
            Stream<ANOTHER> anotherStream) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, IntTuple2::of);
    }
    public default <ANOTHER> StreamPlus<IntTuple2<ANOTHER>> zipWith(
            Stream<ANOTHER> anotherStream, 
            ZipWithOption   option) {
        return zipWith(anotherStream, option, IntTuple2::of);
    }
    
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            Stream<ANOTHER>                   anotherStream, 
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, merger);
    }
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            Stream<ANOTHER>                   anotherStream, 
            ZipWithOption                     option,
            IntObjBiFunction<ANOTHER, TARGET> merger) {
        return useIteratorToObj(iteratorA -> {
            return StreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntWith(option, merger, iteratorA, iteratorB);
                    });
        });
    }
    
    public default StreamPlus<IntIntTuple> zipWith(
            IntStream anotherStream) {
        return useIteratorToObj(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntObjWith(IntIntTuple::new, iteratorA, iteratorB);
                    });
        });
    }
    public default StreamPlus<IntIntTuple> zipWith(
            IntStream anotherStream,
            int       defaultValue) {
        return useIteratorToObj(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntObjWith(IntIntTuple::new, iteratorA, iteratorB, defaultValue);
                    });
        });
    }
    public default StreamPlus<IntIntTuple> zipWith(
            IntStream anotherStream,
            int       defaultValue1,
            int       defaultValue2) {
        return useIteratorToObj(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntObjWith(IntIntTuple::new, iteratorA, iteratorB, defaultValue1, defaultValue2);
                    });
        });
    }
    
    public default IntStreamPlus zipWith(
            IntStream              anotherStream, 
            IntBiFunctionPrimitive merger) {
        return useIterator(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntWith(merger, iteratorA, iteratorB);
                    });
        });
    }
    public default IntStreamPlus zipWith(
            IntStream              anotherStream, 
            int                    defaultValue,
            IntBiFunctionPrimitive merger) {
        return useIterator(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntWith(merger, iteratorA, iteratorB, defaultValue);
                    });
        });
    }
    public default IntStreamPlus zipWith(
            IntStream              anotherStream, 
            int                    defaultValue1,
            int                    defaultValue2,
            IntBiFunctionPrimitive merger) {
        return useIterator(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
                    });
        });
    }
    
    public default <T> StreamPlus<T> zipToObjWith(
            IntStream           anotherStream, 
            IntIntBiFunction<T> merger) {
        return useIteratorToObj(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntObjWith(merger, iteratorA, iteratorB);
                    });
        });
    }
    public default <T> StreamPlus<T> zipToObjWith(
            IntStream           anotherStream, 
            int                 defaultValue,
            IntIntBiFunction<T> merger) {
        return useIteratorToObj(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntObjWith(merger, iteratorA, iteratorB, defaultValue);
                    });
        });
    }
    public default <T> StreamPlus<T> zipToObjWith(
            IntStream           anotherStream, 
            int                 defaultValue1,
            int                 defaultValue2,
            IntIntBiFunction<T> merger) {
        return useIteratorToObj(iteratorA -> {
            return IntStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return IntStreamPlusHelper.doZipIntIntObjWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
                    });
        });
    }
    
    public default IntStreamPlus choose(IntStreamPlus anotherStream, IntBiPredicatePrimitive selectThisNotAnother) {
        return zipWith(anotherStream, (a, b) -> selectThisNotAnother.testIntInt(a, b) ? a : b);
    }
    
}
