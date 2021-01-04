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
package functionalj.stream.doublestream;

import java.util.OptionalDouble;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.stream.StreamSupport;

import functionalj.function.ObjDoubleBiFunction;
import functionalj.stream.StreamPlus;
import functionalj.tuple.ObjDoubleTuple;
import lombok.val;



public interface DoubleStreamPlusWithMapWithPrev {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /** @return  the stream of  each previous value and each current value. */
    public default StreamPlus<ObjDoubleTuple<OptionalDouble>> mapWithPrev() {
        val prev = new AtomicReference<OptionalDouble>(OptionalDouble.empty());
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .mapToObj(element -> {
                    val prevValue = prev.get();
                    prev.set(OptionalDouble.of(element));
                    val result = ObjDoubleTuple.of(prevValue, element);
                    return result;
                });
    }
    
    public default <TARGET> StreamPlus<TARGET> mapWithPrev(
            ObjDoubleBiFunction<OptionalDouble, ? extends TARGET> mapper) {
        val prev = new AtomicReference<OptionalDouble>(OptionalDouble.empty());
        val streamPlus = doubleStreamPlus();
        return streamPlus
                .mapToObj(element -> {
                    val prevValue = prev.get();
                    val newValue  = mapper.apply(prevValue, element);
                    prev.set(OptionalDouble.of(element));
                    return newValue;
                });
    }
  
  /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
  public default <TARGET> DoubleStreamPlus mapTwo(
          DoubleBinaryOperator combinator) {
      int count      = 2;
      val streamPlus = doubleStreamPlus();
      return streamPlus.derive(stream -> {
          val splitr = stream.spliterator();
          val resultStream = StreamSupport.doubleStream(new Spliterators.AbstractDoubleSpliterator(splitr.estimateSize(), 0) {
            double[] array = new double[count*10];
            int      start = 0;
            int      end   = 0;
            boolean  used  = false;
            @Override
            public boolean tryAdvance(DoubleConsumer consumer) {
                DoubleConsumer action = elem -> {
                    array[end] = elem;
                    end++;
                    int length = end - start;
                    if (length >= count) {
                        val prev  = array[start];
                        val curr  = array[start + 1];
                        val value = combinator.applyAsDouble(prev, curr);
                        consumer.accept(value);
                        start++;
                        used = true;
                    }
                    if (end >= array.length) {
                        System.arraycopy(array, start, array, 0, length - 1);
                        start = 0;
                        end   = length - 1;
                    }
                };
                boolean hasNext = splitr.tryAdvance(action);
//                if (!hasNext && !used) {
//                    val prev  = array[start];
//                    val curr  = array[start + 1];
//                    val value = combinator.applyAsDouble(prev, curr);
//                    consumer.accept(value);
//                }
                return hasNext;
            }
          }, false);
          return DoubleStreamPlus.from(resultStream);
      });
  }
  
//  /** @return  the stream of  each previous value and each current value. */
//  public default StreamPlus<StreamPlus<? super DATA>> mapWithPrev(int count) {
//      val streamPlus = streamPlus();
//      return sequential(streamPlus, stream -> {
//          val splitr = stream.spliterator();
//          val resultStream = StreamSupport.stream(new Spliterators.AbstractSpliterator<StreamPlus<? super DATA>>(splitr.estimateSize(), 0) {
//              Object[] array = new Object[count*10];
//              int      start = 0;
//              int      end   = 0;
//              boolean  used  = false;
//              @Override
//              public boolean tryAdvance(Consumer<? super StreamPlus<? super DATA>> consumer) {
//                  Consumer<? super DATA> action = elem -> {
//                      array[end] = elem;
//                      end++;
//                      int length = end - start;
//                      if (length >= count) {
//                          val iterator   = new ArrayBackedIteratorPlus<>(array, start, length);
//                          val streamPlus = new ArrayBackedStreamPlus<>(iterator);
//                          consumer.accept(streamPlus);
//                          used = true;
//                          start++;
//                      }
//                      if (end >= array.length) {
//                          System.arraycopy(array, start, array, 0, length - 1);
//                          start = 0;
//                          end   = length - 1;
//                      }
//                  };
//                  boolean hasNext = splitr.tryAdvance(action);
//                  if (!hasNext && !used) {
//                      val iterator   = new ArrayBackedIteratorPlus<>(array, start, end - start);
//                      val streamPlus = new ArrayBackedStreamPlus<>(iterator);
//                      consumer.accept(streamPlus);
//                  }
//                  return hasNext;
//              }
//          }, false);
//          return StreamPlus.from(resultStream);
//      });
//  }
//  
//  /** @return  the stream of  each previous value and each current value. */
//  public default <TARGET> StreamPlus<TARGET> mapWithPrev(int count, Func1<? super StreamPlus<? super DATA>, ? extends TARGET> combinator) {
//      return mapWithPrev(count)
//              .map(combinator);
//  }
//  
    
    
    
    
    
    
    
    
    
//  
//  /** Create a stream whose value is the combination between the previous value and the current value of this stream. */
//  public default <TARGET> StreamPlus<TARGET> mapWithPrev(
//          IntIntBiFunction<? extends TARGET> combinator) {
//      int count      = 2;
//      val streamPlus = intStreamPlus();
//      return (streamPlus, stream -> {
//          val splitr = stream.spliterator();
//          val resultStream = StreamSupport.stream(new Spliterators.AbstractSpliterator<TARGET>(splitr.estimateSize(), 0) {
//              Object[] array = new Object[count*10];
//              int      start = 0;
//              int      end   = 0;
//              @Override
//              public boolean tryAdvance(Consumer<? super TARGET> consumer) {
//                  Consumer<? super DATA> action = elem -> {
//                      array[end] = elem;
//                      end++;
//                      int length = end - start;
//                      if (length >= count) {
//                          val prev  = (DATA)array[start];
//                          val curr  = (DATA)array[start + 1];
//                          val value = combinator.apply(prev, curr);
//                          consumer.accept(value);
//                          start++;
//                      }
//                      if (end >= array.length) {
//                          System.arraycopy(array, start, array, 0, length - 1);
//                          start = 0;
//                          end   = length - 1;
//                      }
//                  };
//                  boolean hasNext = splitr.tryAdvance(action);
//                  return hasNext;
//              }
//          }, false);
//          return StreamPlus.from(resultStream);
//      });
//  }
//  
//  /** @return  the stream of  each previous value and each current value. */
//  public default StreamPlus<StreamPlus<? super DATA>> mapWithPrev(int count) {
//      val streamPlus = streamPlus();
//      return sequential(streamPlus, stream -> {
//          val splitr = stream.spliterator();
//          val resultStream = StreamSupport.stream(new Spliterators.AbstractSpliterator<StreamPlus<? super DATA>>(splitr.estimateSize(), 0) {
//              Object[] array = new Object[count*10];
//              int      start = 0;
//              int      end   = 0;
//              boolean  used  = false;
//              @Override
//              public boolean tryAdvance(Consumer<? super StreamPlus<? super DATA>> consumer) {
//                  Consumer<? super DATA> action = elem -> {
//                      array[end] = elem;
//                      end++;
//                      int length = end - start;
//                      if (length >= count) {
//                          val iterator   = new ArrayBackedIteratorPlus<>(array, start, length);
//                          val streamPlus = new ArrayBackedStreamPlus<>(iterator);
//                          consumer.accept(streamPlus);
//                          used = true;
//                          start++;
//                      }
//                      if (end >= array.length) {
//                          System.arraycopy(array, start, array, 0, length - 1);
//                          start = 0;
//                          end   = length - 1;
//                      }
//                  };
//                  boolean hasNext = splitr.tryAdvance(action);
//                  if (!hasNext && !used) {
//                      val iterator   = new ArrayBackedIteratorPlus<>(array, start, end - start);
//                      val streamPlus = new ArrayBackedStreamPlus<>(iterator);
//                      consumer.accept(streamPlus);
//                  }
//                  return hasNext;
//              }
//          }, false);
//          return StreamPlus.from(resultStream);
//      });
//  }
//  
//  /** @return  the stream of  each previous value and each current value. */
//  public default <TARGET> StreamPlus<TARGET> mapWithPrev(int count, Func1<? super StreamPlus<? super DATA>, ? extends TARGET> combinator) {
//      return mapWithPrev(count)
//              .map(combinator);
//  }
//  
    
}
