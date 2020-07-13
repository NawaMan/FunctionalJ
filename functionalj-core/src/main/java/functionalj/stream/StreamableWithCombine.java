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

import functionalj.function.Func2;
import functionalj.tuple.Tuple2;

public interface StreamableWithCombine<DATA> extends AsStreamable<DATA> {
    
    public default Streamable<DATA> concatWith(Streamable<DATA> tail) {
        return Streamable.deriveFrom(this, stream -> stream.concatWith(tail.stream()));
    }
    
    public default Streamable<DATA> merge(Streamable<DATA> anotherStreamable) {
        return Streamable.deriveFrom(this, stream -> stream.merge(anotherStreamable.stream()));
    }
    
    //-- Zip --
    
    public default <B> Streamable<Tuple2<DATA,B>> zipWith(Streamable<B> anotherStreamable) {
        return Streamable.deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream()));
    }
    public default <B> Streamable<Tuple2<DATA,B>> zipWith(
            Streamable<B> anotherStreamable, 
            ZipWithOption option) {
        return Streamable.deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream(), option));
    }
    
    public default <B, C> Streamable<C> zipWith(
            Streamable<B>     anotherStreamable, 
            Func2<DATA, B, C> merger) {
        return Streamable.deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream(), merger));
    }
    public default <B, C> Streamable<C> zipWith(
            Streamable<B>     anotherStreamable, 
            ZipWithOption     option, 
            Func2<DATA, B, C> merger) {
        return Streamable.deriveFrom(this, stream -> stream.zipWith(anotherStreamable.stream(), option, merger));
    }
    
    public default Streamable<DATA> choose(
            Streamable<DATA>           anotherStreamable, 
            Func2<DATA, DATA, Boolean> selectThisNotAnother) {
        return Streamable.deriveFrom(this, stream -> stream.choose(anotherStreamable.stream(), selectThisNotAnother));
    }
    
}
