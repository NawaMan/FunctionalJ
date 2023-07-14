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
package functionalj.validator;

import java.util.function.Predicate;
import functionalj.function.Func2;
import functionalj.result.Result;
import functionalj.result.ValidationException;
import lombok.val;

public interface SimpleValidator<DATA> extends Validator<DATA> {

    public static <D> Func2<D, Predicate<? super D>, ValidationException> exceptionFor(String template) {
        return (d, p) -> new ValidationException(String.format(template, d, p));
    }

    public static <D> Func2<D, Predicate<? super D>, ValidationException> exceptionFor(String template, Exception cause) {
        return (d, p) -> new ValidationException(String.format(template, d, p), cause);
    }

    public Predicate<? super DATA> checker();

    public ValidationException createException(DATA data);

    public default Result<DATA> validate(DATA data) {
        return Result.of(() -> {
            val checker = checker();
            if (checker.test(data))
                return data;
            val exception = createException(data);
            throw exception;
        });
    }

    public static class Impl<D> implements SimpleValidator<D> {

        private final Predicate<? super D> checker;

        private final Func2<? super D, ? super Predicate<? super D>, ? extends ValidationException> exceptionCreator;

        public Impl(Predicate<? super D> checker, Func2<? super D, ? super Predicate<? super D>, ? extends ValidationException> exceptionCreator) {
            this.checker = checker;
            this.exceptionCreator = exceptionCreator;
        }

        @Override
        public Predicate<? super D> checker() {
            return checker;
        }

        @Override
        public ValidationException createException(D data) {
            return exceptionCreator.apply(data, checker);
        }
    }
}
