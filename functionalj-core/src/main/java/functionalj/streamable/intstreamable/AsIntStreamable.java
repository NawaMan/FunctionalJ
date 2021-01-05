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
package functionalj.streamable.intstreamable;

import functionalj.stream.intstream.AsIntStreamPlus;
import functionalj.stream.intstream.IntStreamPlus;

/**
 * Classes implementing this interface can act like an int streamable.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public interface AsIntStreamable extends AsIntStreamPlus {
    
    /** Returns the stream for this streamable. */
    public static IntStreamable streamableOf(AsIntStreamable streamable) {
        return streamable.intStreamable();
    }
    
    /** @return  the stream plus instance of this object. */
    public IntStreamPlus intStream();
    
    /** @return  the stream plus instance of this object. */
    public default IntStreamPlus intStreamPlus() {
        return intStream();
    }
    
    public default IntStreamable intStreamable() {
        return ()->intStreamPlus();
    }
    
}
