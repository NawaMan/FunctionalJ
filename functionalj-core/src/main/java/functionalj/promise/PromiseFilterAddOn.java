// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.result.Result;

@SuppressWarnings({ "unchecked", "rawtypes" })
public interface PromiseFilterAddOn<DATA> {
    
    public <TARGET> Promise<TARGET> mapResult(Function<Result<? super DATA>, Result<? extends TARGET>> mapper);
    
    public default <T extends DATA> Promise<DATA> filter(Class<T> clzz) {
        return (Promise) mapResult(result -> result.filter(clzz));
    }
    
    public default <T extends DATA> Promise<DATA> filter(Class<T> clzz, Predicate<? super T> theCondition) {
        return (Promise) mapResult(result -> result.filter(clzz, theCondition));
    }
    
    public default <T> Promise<DATA> filter(Function<? super DATA, T> mapper, Predicate<? super T> theCondition) {
        return (Promise) mapResult(result -> result.filter((Function) mapper, (Predicate) theCondition));
    }
}
