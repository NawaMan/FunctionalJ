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
package functionalj.lens.lenses;

import java.util.function.IntSupplier;

import functionalj.functions.ThrowFuncs;
import lombok.val;


@FunctionalInterface
public interface IntegerAccessBoxed<HOST> extends IntegerAccess<HOST> {
    
    public default int applyAsInt(HOST host) {
        try {
            val integer = applyUnsafe(host);
            return integer.intValue();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw ThrowFuncs.exceptionTransformer.value().apply(e);
        }
    }
    
    public default IntegerAccessPrimitive<HOST> orElse(int fallback) {
        return value -> {
            val result = apply(value);
            return (result != null) ? result : fallback;
        };
    }
    
    public default IntegerAccessPrimitive<HOST> orGet(IntSupplier fallback) {
        return value -> {
            val result = apply(value);
            return (result != null) ? result : fallback.getAsInt();
        };
    }
    
}
