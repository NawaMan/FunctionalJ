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

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.val;

public interface StreamPlusWithAdditionalPeek<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /**
     * Peek only the value that is an instance of the give class.
     * 
     * @param <T>          the type of the data.
     * @param clzz         the class.
     * @param theConsumer  the consumer.
     * @return             the stream.
     */
    public default <T extends DATA> StreamPlus<DATA> peek(
            Class<T>            clzz,
            Consumer<? super T> theConsumer) {
        val streamPlus = streamPlus();
        return streamPlus
                .peek(value -> {
                    if (!clzz.isInstance(value))
                        return;
                    
                    val target = clzz.cast(value);
                    theConsumer.accept(target);
                });
    }
    
    /**
     * Peek only the value that is selected with selector.
     * 
     * @param <T>          the type of the data.
     * @param selector     the selector.
     * @param theConsumer  the consumer.
     * @return             the stream.
     */
    public default StreamPlus<DATA> peekBy(
            Predicate<? super DATA> selector,
            Consumer<? super DATA>  theConsumer) {
        val streamPlus = streamPlus();
        return streamPlus
                .peek(value -> {
                    if (!selector.test(value))
                        return;
                    
                    theConsumer.accept(value);
                });
    }
    
    // TODO - peekByInt, peekByLong, peekByDouble, peekByObj
    
    // TODO - peekAsInt, peekAsLong, peekAsDouble, peekAsObj
    
    /**
     * Peek the mapped value using the mapper.
     * 
     * @param <T>          the type of the data.
     * @param mapper       the mapper.
     * @param theConsumer  the consumer.
     * @return             the stream.
     */
    public default <T> StreamPlus<DATA> peekAs(
            Function<? super DATA, T> mapper,
            Consumer<? super T>       theConsumer) {
        val streamPlus = streamPlus();
        return streamPlus
                .peek(value -> {
                    val target = mapper.apply(value);
                    theConsumer.accept(target);
                });
    }

    /**
     * Peek only the mapped value using the mapper.
     * 
     * @param <T>          the type of the data.
     * @param selector     the selector.
     * @param theConsumer  the consumer.
     * @return             the stream.
     */
    public default <T> StreamPlus<DATA> peekBy(
            Function<? super DATA, T> mapper,
            Predicate<? super T>      selector,
            Consumer<? super DATA>    theConsumer) {
        val streamPlus = streamPlus();
        return streamPlus
                .peek(value -> {
                    val target = mapper.apply(value);
                    if (selector.test(target))
                        theConsumer.accept(value);
                });
    }
    
    /**
     * Peek only the mapped value using the mapper that is selected by the selector.
     * 
     * @param <T>          the type of the data.
     * @param mapper       the mapper.
     * @param selector     the selector.
     * @param theConsumer  the consumer.
     * @return             the stream.
     */
    public default <T> StreamPlus<DATA> peekAs(
            Function<? super DATA, T> mapper,
            Predicate<? super T>      selector,
            Consumer<? super T>       theConsumer) {
        val streamPlus = streamPlus();
        return streamPlus
                .peek(value -> {
                    val target = mapper.apply(value);
                    if (selector.test(target)) {
                        theConsumer.accept(target);
                    }
                });
    }
    
}
