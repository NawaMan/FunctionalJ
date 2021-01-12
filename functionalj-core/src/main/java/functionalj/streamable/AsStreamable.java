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
package functionalj.streamable;

import java.util.ArrayList;

import functionalj.list.FuncList;
import functionalj.stream.AsStreamPlus;
import functionalj.stream.IterablePlus;
import functionalj.stream.StreamPlus;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;
import lombok.val;


/**
 * Classes implementing this interface can act like a streamable.
 *
 * @param <DATA>
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface AsStreamable<DATA> 
                    extends 
                        AsStreamPlus<DATA>, 
                        AsStreamableWithCalculate<DATA> {
    
    
    /** Returns the streamable for this streamable. */
    public static <D> Streamable<D> streamableFrom(AsStreamable<D> streamable) {
        return streamable.streamable();
    }
    
    /** @return  the stream plus instance of this object. */
    public StreamPlus<DATA> stream();
    
    @Override
    public default StreamPlus<DATA> streamPlus() {
        return stream();
    }
    
    public default Streamable<DATA> streamable() {
        return ()->streamPlus();
    }
    
    //== Terminal operations ==
    
    /** @return the iterable of this streamable. */
    public default IterablePlus<DATA> iterable() {
        return () -> iterator();
    }
    
    /** @return the array list containing the elements. */
    @Eager
    @Terminal
    public default ArrayList<DATA> toArrayList() {
        val list = new ArrayList<DATA>(streamable().size());
        stream().forEach(list::add);
        return list;
    }
    
    /**
     * Return function that represent this streamable.
     * 
     * Note: Different from {@link StreamPlus#toFuncList()},
     *         this method can successfully be done even if this stream is infinit.
     *       Of course, certain operations cannot be run on it (like those that need size()).
     **/
    @Terminal
    public default FuncList<DATA> toFuncList() {
        return FuncList.from(this);
    }
    
}
