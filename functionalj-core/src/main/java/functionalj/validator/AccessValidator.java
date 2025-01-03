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
package functionalj.validator;

import java.util.function.Function;
import java.util.function.Predicate;
import functionalj.function.Func4;
import functionalj.result.Result;
import functionalj.result.ValidationException;
import lombok.val;

public interface AccessValidator<DATA, TARGET> extends Validator<DATA> {
    
    public Function<DATA, TARGET> access();
    
    public Predicate<? super TARGET> checker();
    
    public ValidationException createException(DATA data, TARGET target);
    
    public default Result<DATA> validate(DATA data) {
        return Result.of(() -> {
            val mapper = access();
            val target = mapper.apply(data);
            val checker = checker();
            if (checker.test(target))
                return data;
            val exception = createException(data, target);
            throw exception;
        });
    }
    
    public static class Impl<D, T> implements AccessValidator<D, T> {
        
        private Function<D, T> access;
        
        private Predicate<? super T> checker;
        
        private Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> exceptionCreator;
        
        public Impl(Function<D, T> access, Predicate<? super T> checker, Func4<? super D, ? super T, ? super Function<? super D, T>, ? super Predicate<? super T>, ? extends ValidationException> exceptionCreator) {
            this.access = access;
            this.checker = checker;
            this.exceptionCreator = exceptionCreator;
        }
        
        @Override
        public Function<D, T> access() {
            return access;
        }
        
        @Override
        public Predicate<? super T> checker() {
            return checker;
        }
        
        @Override
        public ValidationException createException(D data, T target) {
            return exceptionCreator.apply(data, target, access, checker);
        }
    }
}
