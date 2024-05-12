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
package functionalj.function;

import java.util.function.Predicate;

// Ummm ... these are not a good name. :-(
public interface Check {
    
    public static <T> Func1<T, T> check(Predicate<T> checker, Func1<T, T> mapper) {
        return input -> checker.test(input) ? mapper.apply(input) : input;
    }
    
    public static <T> Func1<T, T> check(Predicate<T> checker, Func0<T> supplier) {
        return input -> checker.test(input) ? supplier.get() : input;
    }
    
    public static <T> Func1<T, T> check(Predicate<T> checker, T value) {
        return input -> checker.test(input) ? value : input;
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func1<T, D> mapper, Func1<T, D> elseMapper) {
        return input -> checker.test(input) ? mapper.apply(input) : elseMapper.apply(input);
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func1<T, D> mapper, Func0<D> elseSupplier) {
        return input -> checker.test(input) ? mapper.apply(input) : elseSupplier.get();
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func1<T, D> mapper, D elseValue) {
        return input -> checker.test(input) ? mapper.apply(input) : elseValue;
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func0<D> supplier, Func1<T, D> elseMapper) {
        return input -> checker.test(input) ? supplier.get() : elseMapper.apply(input);
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func0<D> supplier, Func0<D> elseSupplier) {
        return input -> checker.test(input) ? supplier.get() : elseSupplier.get();
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, Func0<D> supplier, D elseValue) {
        return input -> checker.test(input) ? supplier.get() : elseValue;
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, D value, Func1<T, D> elseMapper) {
        return input -> checker.test(input) ? value : elseMapper.apply(input);
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, D value, Func0<D> elseSupplier) {
        return input -> checker.test(input) ? value : elseSupplier.get();
    }
    
    public static <T, D> Func1<T, D> check(Predicate<T> checker, D value, D elseValue) {
        return input -> checker.test(input) ? value : elseValue;
    }
    
    public static <T, D> Func1<T, D> only(Predicate<T> checker, Func1<T, D> mapper) {
        return input -> checker.test(input) ? mapper.apply(input) : null;
    }
    
    public static <T, D> Func1<T, D> only(Predicate<T> checker, Func0<D> supplier) {
        return input -> checker.test(input) ? supplier.get() : null;
    }
    
    public static <T, D> Func1<T, D> only(Predicate<T> checker, D value) {
        return input -> checker.test(input) ? value : null;
    }
}
