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
package functionalj.types.elm.processor;

import static functionalj.types.DefaultValue.REQUIRED;
import static functionalj.types.StructToString.Legacy;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.JavaVersionInfo;
import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;

public class ElmTypeTest {
    
    public static final functionalj.types.struct.generator.SourceSpec userSpec 
            = new functionalj.types.struct.generator.SourceSpec(
                    new JavaVersionInfo(8, 8),
                    null, 
                    "example.functionalj.elm", 
                    "ElmExamples", 
                    "User", 
                    "example.functionalj.elm", 
                    null, 
                    null, 
                    "spec", 
                    null, 
                    new SourceSpec.Configurations(true, null, false, true, true, true, true, true, true, Serialize.To.NOTHING, Legacy, ""),
                    asList(
                        new Getter("firstName", new Type("java.lang", null, "String", emptyList()), false, REQUIRED), 
                        new Getter("lastName", new Type("java.lang", null, "String", emptyList()), false, REQUIRED)),
                    emptyList(),
                    asList("User"));
    
    public static final functionalj.types.choice.generator.model.SourceSpec loginStatusSpec = new functionalj.types.choice.generator.model.SourceSpec("LoginStatus", new Type("example.functionalj.choice", "ChoiceTypeExamples", "LoginStatusSpec", emptyList()), "spec", false, "__tagged", Serialize.To.NOTHING, emptyList(), asList(new Case("Login", null, asList(new CaseParam("userName", new Type("java.lang", null, "String", emptyList()), true, null))), new Case("Logout", null, emptyList())), emptyList(), emptyList());
    
    @Test
    public void testBasic() {
        assertEquals("Int", "" + new ElmType(Type.INT));
        assertEquals("Bool", "" + new ElmType(Type.BOOL));
        assertEquals("Float", "" + new ElmType(Type.DOUBLE));
        assertEquals("String", "" + new ElmType(Type.STRING));
        assertEquals("List String", "" + new ElmType(Type.LIST.withGenerics(new Generic(Type.STRING))));
        assertEquals("Dict Int String", "" + new ElmType(Type.MAP.withGenerics(new Generic(Type.INT), new Generic(Type.STRING))));
    }
    
    @Test
    public void testComplex() {
        assertEquals("List (List String)", "" + new ElmType(Type.LIST.withGenerics(new Generic(Type.LIST.withGenerics(new Generic(Type.STRING))))));
        assertEquals("Dict Int (List String)", "" + new ElmType(Type.MAP.withGenerics(new Generic(Type.INT), new Generic(Type.LIST.withGenerics(new Generic(Type.STRING))))));
    }
}
