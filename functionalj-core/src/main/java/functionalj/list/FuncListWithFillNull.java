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
package functionalj.list;

import static functionalj.list.FuncList.deriveFrom;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.function.Func1;
import functionalj.lens.lenses.AnyLens;
import functionalj.streamable.AsStreamable;

public interface FuncListWithFillNull<DATA> extends AsStreamable<DATA> {
    
    /** Replace any null value with the given replacement. */
    public default FuncList<DATA> fillNull(DATA replacement) {
        return deriveFrom(this, stream -> stream.fillNull(replacement));
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the given replacement. */
    public default <VALUE> FuncList<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            VALUE                replacement) {
        return deriveFrom(this, stream -> stream.fillNull(lens, replacement));
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the given replacement. */
    public default <VALUE> FuncList<DATA> fillNull(
            Function<DATA, VALUE>         getter, 
            BiFunction<DATA, VALUE, DATA> setter, 
            VALUE                         replacement) {
        return deriveFrom(this, stream -> stream.fillNull(getter, setter, replacement));
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the replacement value from the supplier. */
    public default <VALUE> FuncList<DATA> fillNullWith(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return deriveFrom(this, stream -> stream.fillNullWith(lens, replacementSupplier));
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the replacement value from the supplier. */
    public default <VALUE> FuncList<DATA> fillNullWith(
            Function<DATA, VALUE>         getter, 
            BiFunction<DATA, VALUE, DATA> setter, 
            Supplier<VALUE>               replacementSupplier) {
        return deriveFrom(this, stream -> stream.fillNullWith(getter, setter, replacementSupplier));
    }
    
    /** Replace sub element that is null (accessed with the given lens) with the replacement value from the function. */
    public default <VALUE> FuncList<DATA> fillNullBy(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementFunction) {
        return deriveFrom(this, stream -> stream.fillNullBy(lens, replacementFunction));
    }
    
    /** Replace sub element that is null (accessed with the given getter and setter) with the replacement value from the function. */
    public default <VALUE> FuncList<DATA> fillNullBy(
            Function<DATA, VALUE>         getter, 
            BiFunction<DATA, VALUE, DATA> setter, 
            Function<DATA, VALUE>         replacementFunction) {
        return deriveFrom(this, stream -> stream.fillNullBy(getter, setter, replacementFunction));
    }
}
