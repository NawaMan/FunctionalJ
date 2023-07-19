// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.stream.doublestream;

import java.util.OptionalDouble;
import java.util.function.DoubleBinaryOperator;
import functionalj.stream.markers.Eager;
import functionalj.stream.markers.Terminal;

public interface AsDoubleStreamPlusWithReduce {
    
    public DoubleStreamPlus doubleStreamPlus();
    
    /**
     * Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
     * and returns the reduced value.
     */
    @Eager
    @Terminal
    public default double reduce(double identity, DoubleBinaryOperator reducer) {
        return doubleStreamPlus().reduce(identity, reducer);
    }
    
    /**
     * Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function,
     * and returns the reduced value.
     */
    @Eager
    @Terminal
    public default OptionalDouble reduce(DoubleBinaryOperator reducer) {
        return doubleStreamPlus().reduce(reducer);
    }
}
