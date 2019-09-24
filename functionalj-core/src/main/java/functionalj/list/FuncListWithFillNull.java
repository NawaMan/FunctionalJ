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
package functionalj.list;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import functionalj.function.Func1;
import functionalj.function.Func2;
import functionalj.lens.lenses.AnyLens;
import functionalj.stream.Streamable;
import functionalj.stream.StreamableWithFillNull;

public interface FuncListWithFillNull<DATA>
        extends StreamableWithFillNull<DATA> {
    
    public <TARGET> FuncList<TARGET> deriveFrom(Function<Streamable<DATA>, Stream<TARGET>> action);
    
    //== fillNull ==
    
    public default <VALUE> FuncList<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            VALUE                replacement) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .fillNull(lens, replacement);
        });
    }
    
    public default <VALUE> FuncList<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            VALUE                    replacement) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .fillNull(get, set, replacement);
        });
    }
    
    public default <VALUE> FuncList<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Supplier<VALUE>      replacementSupplier) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .fillNull(lens, replacementSupplier);
        });
    }
    
    public default <VALUE> FuncList<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Supplier<VALUE>          replacementSupplier) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .fillNull(get, set, replacementSupplier);
        });
    }
    
    public default <VALUE> FuncList<DATA> fillNull(
            AnyLens<DATA, VALUE> lens, 
            Func1<DATA, VALUE>   replacementFunction) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .fillNull(lens, replacementFunction);
        });
    }
    
    public default <VALUE> FuncList<DATA> fillNull(
            Func1<DATA, VALUE>       get, 
            Func2<DATA, VALUE, DATA> set, 
            Func1<DATA, VALUE>       replacementFunction) {
        return deriveFrom(streamable -> {
            return streamable
                    .stream()
                    .fillNull(get, set, replacementFunction);
        });
    }
}
