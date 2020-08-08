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
package functionalj.streamable;

import static functionalj.streamable.Streamable.deriveFrom;

import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.lens.lenses.AnyLens;

public interface StreamableWithFillNull<DATA>
        extends AsStreamable<DATA> {
    
    /** Replace any null value with the given replacement. */
    public default Streamable<DATA> fillNull(DATA replacement) {
        return deriveFrom(this, stream -> stream.fillNull(replacement));
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the given replacement. */
    public default <VALUE> Streamable<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            VALUE                replacement) {
        return deriveFrom(this, stream -> stream.fillNull(lens, replacement));
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the given replacement. */
    public default <VALUE> Streamable<DATA> fillNull(
            Func1<DATA, VALUE>       getter, 
            Func2<DATA, VALUE, DATA> setter, 
            VALUE                    replacement) {
        return deriveFrom(this, stream -> stream.fillNull(getter, setter, replacement));
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the replacement value from the supplier. */
    public default <VALUE> Streamable<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return deriveFrom(this, stream -> stream.fillNull(lens, replacementSupplier));
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the replacement value from the supplier. */
    public default <VALUE> Streamable<DATA> fillNull(
            Func1<DATA, VALUE>       getter, 
            Func2<DATA, VALUE, DATA> setter, 
            Supplier<VALUE>          replacementSupplier) {
        return deriveFrom(this, stream -> stream.fillNull(getter, setter, replacementSupplier));
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the replacement value from the function. */
    public default <VALUE> Streamable<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementFunction) {
        return deriveFrom(this, stream -> stream.fillNull(lens, replacementFunction));
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the replacement value from the function. */
    public default <VALUE> Streamable<DATA> fillNull(
            Func1<DATA, VALUE>       getter, 
            Func2<DATA, VALUE, DATA> setter, 
            Func1<DATA, VALUE>       replacementFunction) {
        return deriveFrom(this, stream -> stream.fillNull(getter, setter, replacementFunction));
    }
}
