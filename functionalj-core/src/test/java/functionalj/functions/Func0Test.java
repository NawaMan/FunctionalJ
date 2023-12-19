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
package functionalj.functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import org.junit.Test;

import functionalj.function.Func0;
import lombok.val;

public class Func0Test {
    
    @Test
    public void testElseUse() throws Exception {
        val str = nullString();
        val func = (Func0<Integer>) (() -> {
            return str.length();
        });
        assertTrue(func.getSafely().isException());
        assertEquals(0, func.whenAbsentUse(0).get().intValue());
    }
    
    private String nullString() {
        return (String) null;
    }
    
    @Test
    public void testSupplier() throws Exception {
        val supplier = (Supplier<Integer>)(() -> 5);
        val func0    = (Func0<Integer>)(() -> 5);
        
        assertEquals(5, valueFrom(supplier).intValue());
        assertEquals(5, valueFrom(func0).intValue());
        
        assertEquals(5, valueFrom2(supplier::get).intValue());
        assertEquals(5, valueFrom2(func0).intValue());
        
        val value = new AtomicBoolean();
        assertEquals(5, valueFrom(() -> 5).intValue());
        
        // The following code does not compile.
//        assertEquals(5, valueFrom(() -> {
//            if (value.get()) {
//                return 5;
//            }
//            throw new IOException();
//        }).intValue());
        
        assertEquals(5, valueFrom2(() -> 5).intValue());
        val exception = assertThrows(IOException.class, () -> valueFrom2(() -> {
            if (value.get()) {
                return 5;
            }
            throw new IOException();
        }));
        assertEquals("java.io.IOException", String.valueOf(exception));
    }
    
    private Integer valueFrom(Supplier<Integer> supplier) throws Exception {
        return Func0.from(supplier).getUnsafe();
    }
    
    private Integer valueFrom2(Func0<Integer> supplier) throws Exception {
        return Func0.from(supplier).getUnsafe();
    }
}
