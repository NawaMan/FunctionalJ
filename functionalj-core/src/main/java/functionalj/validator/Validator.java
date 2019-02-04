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
package functionalj.validator;

import java.util.function.Predicate;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.result.Result;
import functionalj.result.ValidationException;

@SuppressWarnings("javadoc")
public interface Validator<DATA> extends Predicate<DATA>, Func1<DATA, Boolean> {

    
    public static <D> Validator<D> of(Predicate<D> checker, Func2<? super D, ? super Predicate<? super D>, ? extends ValidationException> exceptionCreator) {
        return new SimpleValidator.Impl<D>(checker, exceptionCreator);
    }
    
    public static <D> Validator<D> of(Predicate<D> validChecker, String templateMsg) {
        return new SimpleValidator.Impl<D>(validChecker, SimpleValidator.exceptionFor(templateMsg));
    }
    
    @Override
    public default boolean test(DATA data) {
        return validate(data).isPresent();
    }
    @Override
    public default Boolean applyUnsafe(DATA data) throws Exception {
        return test(data);
    }
    
    public Result<DATA> validate(DATA data);
    
}
