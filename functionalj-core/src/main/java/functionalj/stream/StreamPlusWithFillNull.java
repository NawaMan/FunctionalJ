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
package functionalj.stream;

import static functionalj.stream.StreamPlusHelper.derive;

import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.lens.core.WriteLens;
import functionalj.lens.lenses.AnyLens;
import lombok.val;

public interface StreamPlusWithFillNull<DATA> {
    
    public StreamPlus<DATA> streamPlus();
    
    /** Replace any null value with the given replacement. */
    public default StreamPlus<DATA> fillNull(DATA replacement) {
        val streamPlus = streamPlus();
        return streamPlus
                .map(value -> {
                    val isNull = value == null;
                    val replaced
                            = isNull
                            ? replacement
                            : value;
                    return replaced;
                });
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the given replacement. */
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            VALUE                replacement) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacement);
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the given replacement. */
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       getter, 
            Func2<DATA, VALUE, DATA> setter, 
            VALUE                    replacement) {
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
            return (Stream<DATA>)stream
                    .map(orgElmt -> {
                        val value   = getter.apply(orgElmt);
                        if (value == null) {
                            val newElmt = setter.apply(orgElmt, replacement);
                            return (DATA)newElmt;
                        }
                        return orgElmt;
                    });
        });
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the replacement value from the supplier. */
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementSupplier);
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the replacement value from the supplier. */
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       getter, 
            Func2<DATA, VALUE, DATA> setter, 
            Supplier<VALUE>          replacementSupplier) {
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
            return (Stream<DATA>)stream
                    .map(orgValue -> {
                        val value = getter.apply(orgValue);
                        if (value == null) {
                            val replacement = replacementSupplier.get();
                            val newValue    = setter.apply(orgValue, replacement);
                            return (DATA)newValue;
                        }
                        return orgValue;
                    });
        });
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the replacement value from the function. */
    public default <VALUE> StreamPlus<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementFunction) {
        return fillNull(
                (Func1<DATA, VALUE>)lens, 
                ((WriteLens<DATA, VALUE>)lens)::apply, 
                replacementFunction);
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the replacement value from the function. */
    public default <VALUE> StreamPlus<DATA> fillNull(
            Func1<DATA, VALUE>       getter, 
            Func2<DATA, VALUE, DATA> setter, 
            Func1<DATA, VALUE>       replacementFunction) {
        val streamPlus = streamPlus();
        return derive(streamPlus, stream ->  {
            return (Stream<DATA>)stream
                    .map(orgValue -> {
                        val value = getter.apply(orgValue);
                        if (value == null) {
                            val replacement = replacementFunction.apply(orgValue);
                            val newValue    = setter.apply(orgValue, replacement);
                            return (DATA)newValue;
                        }
                        return orgValue;
                    });
        });
    }
}
