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
package functionalj.stream.longstream;

import static functionalj.function.FuncUnit0.funcUnit0;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import functionalj.function.LongBiFunctionPrimitive;
import functionalj.function.LongBiPredicatePrimitive;
import functionalj.function.LongLongBiFunction;
import functionalj.function.LongObjBiFunction;
import functionalj.stream.LongIteratorPlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.StreamPlusHelper;
import functionalj.stream.ZipWithOption;
import functionalj.tuple.LongLongTuple;
import functionalj.tuple.LongTuple2;
import lombok.val;

public interface LongStreamPlusWithCombine {
    
    public LongStream     longStream();
    public LongStreamPlus useIterator(Function<LongIteratorPlus, LongStreamPlus> action);
    
    public <TARGET> StreamPlus<TARGET> useIteratorToObj(Function<LongIteratorPlus, StreamPlus<TARGET>> action);
    
    
    public default LongStreamPlus concatWith(LongStream tail) {
        return LongStreamPlus.concat(
                LongStreamPlus.from(longStream()),
                LongStreamPlus.from(tail)
        );
    }
    
    public default LongStreamPlus mergeWith(LongStream anotherStream) {
        val thisStream = longStream();
        val iteratorA  = LongStreamPlusHelper.rawIterator(thisStream);
        val iteratorB  = LongStreamPlusHelper.rawIterator(anotherStream);
        
        val resultStream 
                = LongStreamPlusHelper
                .doMergeLong(iteratorA, iteratorB);
        
        resultStream
                .onClose(()->{
                    funcUnit0(()->thisStream   .close()).runCarelessly();
                    funcUnit0(()->anotherStream.close()).runCarelessly();
                });
        return resultStream;
    }
    
    public default <ANOTHER> StreamPlus<LongTuple2<ANOTHER>> zipWith(
            Stream<ANOTHER> anotherStream) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, LongTuple2::of);
    }
    public default <ANOTHER> StreamPlus<LongTuple2<ANOTHER>> zipWith(
            Stream<ANOTHER> anotherStream, 
            ZipWithOption   option) {
        return zipWith(anotherStream, option, LongTuple2::of);
    }
    
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            Stream<ANOTHER>                    anotherStream, 
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return zipWith(anotherStream, ZipWithOption.RequireBoth, merger);
    }
    // https://stackoverflow.com/questions/24059837/iterate-two-java-8-streams-together?noredirect=1&lq=1
    public default <ANOTHER, TARGET> StreamPlus<TARGET> zipWith(
            Stream<ANOTHER>                    anotherStream, 
            ZipWithOption                      option,
            LongObjBiFunction<ANOTHER, TARGET> merger) {
        return useIteratorToObj(iteratorA -> {
            return StreamPlusHelper.useIterator(StreamPlus.from(anotherStream), iteratorB -> {
                return LongStreamPlusHelper.doZipLongWith(option, merger, iteratorA, iteratorB);
            });
        });
    }
    
    public default StreamPlus<LongLongTuple> zipWith(
            LongStream anotherStream) {
        return useIteratorToObj(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongObjWith(LongLongTuple::new, iteratorA, iteratorB);
                    });
        });
    }
    public default StreamPlus<LongLongTuple> zipWith(
            LongStream anotherStream,
            long      defaultValue) {
        return useIteratorToObj(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongObjWith(LongLongTuple::new, iteratorA, iteratorB, defaultValue);
                    });
        });
    }
    public default StreamPlus<LongLongTuple> zipWith(
            LongStream anotherStream,
            long       defaultValue1,
            long       defaultValue2) {
        return useIteratorToObj(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongObjWith(LongLongTuple::new, iteratorA, iteratorB, defaultValue1, defaultValue2);
                    });
        });
    }
    
    public default LongStreamPlus zipWith(
            LongStream              anotherStream, 
            LongBiFunctionPrimitive merger) {
        return useIterator(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongWith(merger, iteratorA, iteratorB);
                    });
        });
    }
    public default LongStreamPlus zipWith(
            LongStream              anotherStream, 
            long                    defaultValue,
            LongBiFunctionPrimitive merger) {
        return useIterator(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongWith(merger, iteratorA, iteratorB, defaultValue);
                    });
        });
    }
    public default LongStreamPlus zipWith(
            LongStream              anotherStream, 
            long                    defaultValue1,
            long                    defaultValue2,
            LongBiFunctionPrimitive merger) {
        return useIterator(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIterator(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
                    });
        });
    }
    
    public default <T> StreamPlus<T> zipToObjWith(
            LongStream            anotherStream, 
            LongLongBiFunction<T> merger) {
        return useIteratorToObj(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongObjWith(merger, iteratorA, iteratorB);
                    });
        });
    }
    public default <T> StreamPlus<T> zipToObjWith(
            LongStream            anotherStream, 
            long                  defaultValue,
            LongLongBiFunction<T> merger) {
        return useIteratorToObj(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongObjWith(merger, iteratorA, iteratorB, defaultValue);
                    });
        });
    }
    public default <T> StreamPlus<T> zipToObjWith(
            LongStream           anotherStream, 
            long                 defaultValue1,
            long                 defaultValue2,
            LongLongBiFunction<T> merger) {
        return useIteratorToObj(iteratorA -> {
            return LongStreamPlus
                    .from(anotherStream)
                    .useIteratorToObj(iteratorB -> {
                        return LongStreamPlusHelper.doZipLongLongObjWith(merger, iteratorA, iteratorB, defaultValue1, defaultValue2);
                    });
        });
    }
    
    public default LongStreamPlus choose(LongStreamPlus anotherStream, LongBiPredicatePrimitive selectThisNotAnother) {
        return zipWith(anotherStream, (a, b) -> selectThisNotAnother.testLongLong(a, b) ? a : b);
    }
    
}
