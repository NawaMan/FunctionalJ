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
package functionalj.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DefaultValueTest {
    
    @Test
    public void test() {
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.NULL));
        assertTrue(DefaultValue.isSuitable(new Type("java.lang", null, "String", new String[0]), DefaultValue.NULL));
        assertTrue(DefaultValue.isSuitable(Type.UUID, DefaultValue.RANDOM));
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.RANDOM));
        assertTrue(DefaultValue.isSuitable(Type.STR, DefaultValue.NOW));
        assertTrue(DefaultValue.isSuitable(Type.LONG, DefaultValue.RANDOM));
        assertTrue(DefaultValue.isSuitable(Type.LONG, DefaultValue.NOW));
        assertTrue(DefaultValue.isSuitable(Core.LocalDate.type(), DefaultValue.NOW));
        assertTrue(DefaultValue.isSuitable(Core.LocalDateTime.type(), DefaultValue.NOW));
        assertEquals("java.util.UUID.randomUUID().toString()", DefaultValue.defaultValueCode(Type.STR, DefaultValue.RANDOM));
        assertEquals("functionalj.types.DefaultValue.RAND.nextLong()", DefaultValue.defaultValueCode(Type.LONG, DefaultValue.RANDOM));
        assertEquals("java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME", DefaultValue.defaultValueCode(Type.STR, DefaultValue.NOW));
        assertEquals("System.currentTimeMillis()", DefaultValue.defaultValueCode(Type.LONG, DefaultValue.NOW));
        assertEquals("java.time.LocalDate.now()", DefaultValue.defaultValueCode(Core.LocalDate.type(), DefaultValue.NOW));
        assertEquals("java.time.LocalDateTime.now()", DefaultValue.defaultValueCode(Core.LocalDateTime.type(), DefaultValue.NOW));
    }
}
