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
package functionalj.result;

import java.util.Optional;

import lombok.val;


public class Either<VALUE, ERROR> extends ImmutableResult<VALUE> {
    
    public static <V, E> Either<V, E> value(V value) {
        return new Either<V, E>(value, null);
    }
    public static <V, E> Either<V, E> error(E errorValue) {
        return new Either<V, E>(null, new ValueException(errorValue));
    }
    
    private Either(VALUE value, Exception exception) {
        super(value, exception);
    }
    
    public boolean isValue() {
        val exception = getException();
        return !(exception instanceof ValueException);
    }
    
    public boolean isError() {
        val exception = getException();
        return (exception instanceof ValueException);
    }
    
    @SuppressWarnings("unchecked")
    public ERROR errorValue() {
        val exception = getException();
        if (exception instanceof ValueException)
            return (ERROR)((ValueException)exception).getErrorValue();
        
        return null;
    }
    
    public Optional<ERROR> error() {
        return Optional.ofNullable(errorValue());
    }
    
}
