// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.StreamSupport;
import functionalj.function.LongObjBiConsumer;
import functionalj.result.NoMoreResultException;
import functionalj.stream.StreamPlus;
import functionalj.stream.doublestream.DoubleStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;
import lombok.val;

public interface LongStreamPlusWithMapMulti extends AsLongStreamPlus {
    
    public default LongStreamPlus mapMulti(LongObjBiConsumer<LongConsumer> mapper) {
        val orgSpliterator = this.spliterator();
        val newSpliterator = new Spliterators.AbstractLongSpliterator(orgSpliterator.estimateSize(), 0) {
        
            private volatile boolean shouldContinue = true;
        
            @Override
            public boolean tryAdvance(LongConsumer consumer) {
                return shouldContinue && orgSpliterator.tryAdvance((LongConsumer) (elem -> {
                    try {
                        mapper.accept(elem, consumer);
                    } catch (NoMoreResultException e) {
                        shouldContinue = false;
                    }
                }));
            }
        };
        val newStream = StreamSupport.longStream(newSpliterator, false);
        return LongStreamPlus.from(newStream);
    }
    
    public default IntStreamPlus mapMultiToInt(LongObjBiConsumer<IntConsumer> mapper) {
        val orgSpliterator = this.spliterator();
        val newSpliterator = new Spliterators.AbstractIntSpliterator(orgSpliterator.estimateSize(), 0) {
        
            private volatile boolean shouldContinue = true;
        
            @Override
            public boolean tryAdvance(IntConsumer consumer) {
                return shouldContinue && orgSpliterator.tryAdvance((LongConsumer) (elem -> {
                    try {
                        mapper.accept(elem, consumer);
                    } catch (NoMoreResultException e) {
                        shouldContinue = false;
                    }
                }));
            }
        };
        val newStream = StreamSupport.intStream(newSpliterator, false);
        return IntStreamPlus.from(newStream);
    }
    
    public default LongStreamPlus mapMultiToLong(LongObjBiConsumer<LongConsumer> mapper) {
        return mapMulti(mapper);
    }
    
    public default DoubleStreamPlus mapMultiToDouble(LongObjBiConsumer<DoubleConsumer> mapper) {
        val orgSpliterator = this.spliterator();
        val newSpliterator = new Spliterators.AbstractDoubleSpliterator(orgSpliterator.estimateSize(), 0) {
        
            private volatile boolean shouldContinue = true;
        
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                return shouldContinue && orgSpliterator.tryAdvance((LongConsumer) (elem -> {
                    try {
                        mapper.accept(elem, consumer);
                    } catch (NoMoreResultException e) {
                        shouldContinue = false;
                    }
                }));
            }
        };
        val newStream = StreamSupport.doubleStream(newSpliterator, false);
        return DoubleStreamPlus.from(newStream);
    }
    
    public default <T> StreamPlus<T> mapMultiToObj(LongObjBiConsumer<Consumer<? super T>> mapper) {
        val orgSpliterator = this.spliterator();
        val newSpliterator = new Spliterators.AbstractSpliterator<T>(orgSpliterator.estimateSize(), 0) {
        
            private volatile boolean shouldContinue = true;
        
            @Override
            public boolean tryAdvance(Consumer<? super T> consumer) {
                return shouldContinue && orgSpliterator.tryAdvance((LongConsumer) (elem -> {
                    try {
                        mapper.accept(elem, consumer);
                    } catch (NoMoreResultException e) {
                        shouldContinue = false;
                    }
                }));
            }
        };
        val newStream = StreamSupport.stream(newSpliterator, false);
        return StreamPlus.from(newStream);
    }
}
