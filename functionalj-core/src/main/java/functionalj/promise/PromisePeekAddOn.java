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
package functionalj.promise;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.result.Result;

@SuppressWarnings({ "unchecked", "rawtypes" })
public interface PromisePeekAddOn<DATA> {

    public <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper);

    public default <T extends DATA> Promise<DATA> peek(Class<T> clzz, Consumer<? super T> theConsumer) {
        return mapResult(result -> result.peek(clzz, (Consumer) theConsumer));
    }

    public default Promise<DATA> peek(Predicate<? super DATA> selector, Consumer<? super DATA> theConsumer) {
        return mapResult(result -> result.peek((Predicate) selector, (Consumer) theConsumer));
    }

    public default <T> Promise<DATA> peek(Function<? super DATA, T> mapper, Consumer<? super T> theConsumer) {
        return mapResult(result -> result.peek((Function) mapper, (Consumer) theConsumer));
    }

    public default <T> Promise<DATA> peek(Function<? super DATA, T> mapper, Predicate<? super T> selector, Consumer<? super T> theConsumer) {
        return mapResult(result -> result.peek((Function) mapper, (Predicate) selector, (Consumer) theConsumer));
    }
}
