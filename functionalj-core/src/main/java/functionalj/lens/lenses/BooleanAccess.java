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
package functionalj.lens.lenses;

import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("javadoc")
@FunctionalInterface
public interface BooleanAccess<HOST> 
        extends 
            AnyAccess<HOST, Boolean>, 
            Predicate<HOST>, 
            ConcreteAccess<HOST, Boolean, BooleanAccess<HOST>> {
    
    @Override
    public default BooleanAccess<HOST> newAccess(Function<HOST, Boolean> access) {
        return access::apply;
    }
    
    public default boolean test(HOST host) {
        return Boolean.TRUE.equals(this.apply(host));
    }
    
    public default BooleanAccess<HOST> nagate() {
        return booleanAccess(false, bool -> !bool);
    }
    public default BooleanAccess<HOST> or(boolean anotherBoolean) {
        return booleanAccess(false, bool -> bool || anotherBoolean);
    }
    public default BooleanAccess<HOST> and(boolean anotherBoolean) {
        return booleanAccess(false, bool -> bool && anotherBoolean);
    }
    public default BooleanAccess<HOST> or(Predicate<? super HOST> anotherPredicate) {
        return host -> {
            boolean bool1 = test(host);
            return bool1 || anotherPredicate.test(host);
        };
    }
    public default BooleanAccess<HOST> and(Predicate<? super HOST> anotherPredicate) {
        return host -> {
            boolean bool1 = test(host);
            return bool1 && anotherPredicate.test(host);
        };
    }
    
}

