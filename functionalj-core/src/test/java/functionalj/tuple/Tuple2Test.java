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

import static functionalj.functions.StrFuncs.appendWith;
import static functionalj.lens.Access.theTuple2;
import static functionalj.lens.Access.theTupleOf;
import static functionalj.lens.LensTypes.STRING;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.lens.lenses.StringLens;
import functionalj.lens.lenses.Tuple2Lens;
import functionalj.list.ImmutableList;
import lombok.val;


public class Tuple2Test {
    
    // Do not like this one bit. - Find the way to improve this!
    private static final Tuple2Lens<Tuple2<String, String>, String, String, StringLens<Tuple2<String, String>>, StringLens<Tuple2<String, String>>> 
            theTuple = theTuple2.of(STRING(), STRING());
    
    private static final ImmutableList<ImmutableTuple2<String, String>> tuples = ImmutableList.of(
            new ImmutableTuple2<>("I", "Integer"),
            new ImmutableTuple2<>("S", "String")
        );
    
    @Test
    public void testLensRead() {
        val theTuple = theTuple2.of(STRING(), STRING());
        assertEquals("[I, S]", "" + tuples.map(theTuple._1()));
    }
    
    @Test
    public void testLensRead2() {
        assertEquals("[I, S]", "" + tuples.map(theTupleOf(STRING(), STRING())._1()));
    }

    @Test
    public void testLensChange() {
        assertEquals("[(I: ,Integer), (S: ,String)]", 
                "" + tuples.map(theTuple._1().changeTo(appendWith(": "))));
    }
    
}
