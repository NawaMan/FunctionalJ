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
package functionalj.streamable.intstreamable;

import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import lombok.val;

public interface IntStreamableWithMatch extends AsIntStreamable {

    /** Return the first element that matches the predicate. */
    public default OptionalInt findFirst(
            IntPredicate predicate) {
        return streamPlus()
                .findFirst(predicate);
    }

    /** Return the any element that matches the predicate. */
    public default OptionalInt findAny(
            IntPredicate predicate) {
        return streamPlus()
                .findAny(predicate);
    }

    /** Use the mapper, return the first element that its mapped value matches the predicate. */
    public default <T> OptionalInt findFirst(
            IntFunction<T>       mapper,
            Predicate<? super T> theCondition) {
        return streamPlus()
                .findFirst(i -> {
                    val mapped = mapper.apply(i);
                    return theCondition.test(mapped);
                });
    }

    /** Use the mapper, return the any element that its mapped value matches the predicate. */
    public default <T> OptionalInt findAny(
            IntFunction<T> mapper,
            Predicate<? super T> theCondition) {
        return streamPlus()
                .findAny(i -> {
                    val mapped = mapper.apply(i);
                    return theCondition.test(mapped);
                });
    }

}
