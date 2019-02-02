// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.choice.generator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.choice.generator.CaseLensBuilder;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.choice.generator.model.Type;
import lombok.val;

public class CaseLensGeneratorTest {

    @Test
    public void test() {
        Case white = null;
        Case black = null;
        Case rgb   = null;
        val spec = new SourceSpec(
                "BasicColor",
                new Type(FullGeneratorTest.class.getPackage().getName(), "ChoiceTypeExampleTest", "ChoiceType1TypeSpec"),
                asList(
                    white = new Case("White", emptyList()),
                    black = new Case("Black", emptyList()),
                    rgb   = new Case("RGB", asList(
                        new CaseParam("r", new Type("int"), false),
                        new CaseParam("g", new Type("int"), false),
                        new CaseParam("b", new Type("int"), false)
                    ))));
        assertEquals(
                "public static class WhiteLens<HOST> extends ObjectLensImpl<HOST, BasicColor.White> {\n" + 
                "    \n" + 
                "    public WhiteLens(LensSpec<HOST, BasicColor.White> spec) {\n" + 
                "        super(spec);\n" + 
                "    }\n" + 
                "    \n" + 
                "}", new CaseLensBuilder(spec, white).build().stream().collect(joining("\n")).toString());
        assertEquals(
                "public static class BlackLens<HOST> extends ObjectLensImpl<HOST, BasicColor.Black> {\n" + 
                "    \n" + 
                "    public BlackLens(LensSpec<HOST, BasicColor.Black> spec) {\n" + 
                "        super(spec);\n" + 
                "    }\n" + 
                "    \n" + 
                "}", new CaseLensBuilder(spec, black).build().stream().collect(joining("\n")).toString());
        assertEquals(
                "public static class RGBLens<HOST> extends ObjectLensImpl<HOST, BasicColor.RGB> {\n" + 
                "    \n" + 
                "    public final IntegerLens<HOST> r = (IntegerLens)createSubLens(BasicColor.RGB::r, BasicColor.RGB::withR, IntegerLens::of);\n" + 
                "    public final IntegerLens<HOST> g = (IntegerLens)createSubLens(BasicColor.RGB::g, BasicColor.RGB::withG, IntegerLens::of);\n" + 
                "    public final IntegerLens<HOST> b = (IntegerLens)createSubLens(BasicColor.RGB::b, BasicColor.RGB::withB, IntegerLens::of);\n" + 
                "    \n" + 
                "    public RGBLens(LensSpec<HOST, BasicColor.RGB> spec) {\n" + 
                "        super(spec);\n" + 
                "    }\n" + 
                "    \n" + 
                "}", new CaseLensBuilder(spec, rgb).build().stream().collect(joining("\n")).toString());
    }

}
