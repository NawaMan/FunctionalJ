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
package functionalj.types.tuple;

import static functionalj.functions.StrFuncs.prependWith;
import static functionalj.lens.Access.$I;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.functions.Func;
import functionalj.functions.Func2;
import functionalj.types.list.FuncList;
import functionalj.types.result.Result;


public class TupleMapGenMain {
    
    private static final String newLine = "\n";
    private static final String template = FuncList.of(
            "    public default <%s> Tuple%d<%s> map(",
            "%s) {",
            "        return Tuple.of(",
            "%s);",
            "    }")
            .joining(newLine);
    private static final Tuple2<String, String> typeParamDefTemplates  = Tuple.of((String)null, "NT%1$s");
    private static final Tuple2<String, String> tupleParamDefTemplates = Tuple.of("T%1$s",      "NT%1$s");
    
    private static final String paramIndent    = "            ";
    private static final Tuple2<String, String> paramTemplates
            = Tuple.of("Absent                    absent%1$s",
                       "Function<? super T%1$s, NT%1$s> mapper%1$s");
    
    private static final String callIndent     = "                ";
    private static final Tuple2<String, String> callTemplates
            = Tuple.of("_%1$s()",
                       "mapper%1$s.apply(_%1$s())");
    
    @Test
    public void testGenerator() {
        assertEquals(
                "    public default <NT2> Tuple6<T1, NT2, T3, T4, T5, T6> map(\n" + 
                "            Absent                    absent1,\n" + 
                "            Function<? super T2, NT2> mapper2,\n" + 
                "            Absent                    absent3,\n" + 
                "            Absent                    absent4,\n" + 
                "            Absent                    absent5,\n" + 
                "            Absent                    absent6) {\n" + 
                "        return Tuple.of(\n" + 
                "                _1(),\n" + 
                "                mapper2.apply(_2()),\n" + 
                "                _3(),\n" + 
                "                _4(),\n" + 
                "                _5(),\n" + 
                "                _6());\n" + 
                "    }", 
                generate(6, FuncList.of(true, false, true, true, true, true)));
    }
    
    public static void main(String[] args) {
        System.out.println(generate(6));
    }
    
    private static String generate(int tupleSize, FuncList<Boolean> flags) {
        var typeParamDefs  = flags.mapWithIndex(generateEach(typeParamDefTemplates)).filter(Objects::nonNull).joining(", ");
        var tupleParamDefs = flags.mapWithIndex(generateEach(tupleParamDefTemplates)).filter(Objects::nonNull).joining(", ");
        
        var params = flags.mapWithIndex(generateEach(paramTemplates)).map(prependWith(paramIndent)).joining(",\n");
        var calls  = flags.mapWithIndex(generateEach(callTemplates)) .map(prependWith(callIndent)) .joining(",\n");
        
        return String.format(template, typeParamDefs, tupleSize, tupleParamDefs, params, calls);
    }
    private static String generate(int tupleSize) {
        var lines = new ArrayList<String>();
        
        range(0, (int)Math.pow(2, tupleSize)).mapToObj(i -> (Integer)i)
        .filter ($I.thatIsNot(0))
        .map    (index -> range(0, tupleSize).mapToObj(i -> !$I.bitAt(i).applyTo(index)))
        .map    (FuncList::from)
        .forEach(flags -> {
            lines.add(generate(6, flags));
            lines.add("    ");
        });
        
        return lines.stream().collect(Collectors.joining(newLine));
    }
    
    private static Func2<Integer, Boolean, String> generateEach(Tuple2<String, String> templates) {
        var trueTemplate  = templates._1();
        var falseTemplate = templates._2();
        return Func.of((Integer i, Boolean e)->{ 
            return Result.value(e ? trueTemplate : falseTemplate)
                    .map(template -> String.format(template, i + 1))
                    .get();
        });
    }
    
}
