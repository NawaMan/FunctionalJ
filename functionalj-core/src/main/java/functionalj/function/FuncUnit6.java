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
package functionalj.function;

/**
 * Defines a functional interface for a method that takes six input parameters and performs an operation,
 * potentially throwing an Exception.
 * 
 * This interface represents a function that accepts six arguments and returns no result.
 *
 * @param <INPUT1>  the first input data type.
 * @param <INPUT2>  the second input data type.
 * @param <INPUT3>  the third input data type.
 * @param <INPUT4>  the forth input data type.
 * @param <INPUT5>  the fifth input data type.
 * @param <INPUT6>  the sixth input data type.
 * @param <OUTPUT>  the output data type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@FunctionalInterface
public interface FuncUnit6<INPUT1, INPUT2, INPUT3, INPUT4, INPUT5, INPUT6> {
    
    /**
     * Performs an operation on the given inputs, potentially throwing an exception.
     *
     * @param input1  the first input parameter
     * @param input2  the second input parameter
     * @param input3  the third input parameter
     * @param input4  the fourth input parameter
     * @param input5  the fifth input parameter
     * @param input6  the sixth input parameter
     * @throws Exception  when unable to perform the operation
     */
    public void acceptUnsafe(
                    INPUT1 input1,
                    INPUT2 input2,
                    INPUT3 input3,
                    INPUT4 input4,
                    INPUT5 input5,
                    INPUT6 input6)
                        throws Exception;
    
}
