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
package functionalj.tuple;

import static functionalj.tuple.Tuple.createTheTuple;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.lens.lenses.StringLens;
import lombok.val;


public class IntTuple2Test {

    
    // Do not like this one bit. - Find the way to improve this!
    private static final IntTuple2Lens<IntTuple2<String>, String, StringLens<IntTuple2<String>>> 
            theTuple = createTheTuple(StringLens::of);
    
    @Test
    public void testLensRead() {
        val tuples = Arrays.asList(
                new IntTuple2<>(1, "One"),
                new IntTuple2<>(2, "Two"),
                new IntTuple2<>(3, "Three")
            );
        assertEquals("[1, 2, 3]", tuples.stream()
                            .map(theTuple._1())
                            .collect(Collectors.toList()).toString());
    }
    
}
