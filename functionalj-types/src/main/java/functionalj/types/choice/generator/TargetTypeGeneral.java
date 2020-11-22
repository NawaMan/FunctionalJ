// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static functionalj.types.choice.generator.Utils.templateRange;
import static functionalj.types.choice.generator.Utils.toCamelCase;
import static functionalj.types.choice.generator.model.Method.Kind.DEFAULT;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.Method;
import lombok.val;


/**
 * Generator for general methods of TargetType like toString, hashCode and equals.
 * 
 * @author manusitn
 */
public class TargetTypeGeneral implements Lines {
    private final TargetClass  targetClass;
    private final List<Case> choices;
    
    private final String targetName;
    
    /**
     * Constructor.
     * 
     * @param targetClass  the target class.
     * @param choices      the choices.
     */
    public TargetTypeGeneral(TargetClass targetClass, List<Case> choices) {
        this.targetClass = targetClass;
        this.choices     = choices;
        this.targetName  = targetClass.type.simpleName();
    }
    
    @Override
    public List<String> lines() {
        var emptyLine   = asList("");
        var getSchema   = new GetSchemaBuilder().lines();
        var firstSwitch = prepareFirstSwitch(targetName);
        var toString    = prepareToStringMethod();
        var hashCode    = prepareHashCode();
        var equals      = prepareEquals(targetName);
        return asList(
    		getSchema,   emptyLine,
            firstSwitch, emptyLine,
            toString,    emptyLine,
            hashCode,    emptyLine,
            equals
        ).stream()
         .filter (Objects::nonNull)
         .flatMap(List::stream)
         .collect(toList());
    }
    
    private List<String> prepareFirstSwitch(final java.lang.String targetName) {
        var firstSwitchTypeDef = format("%1$sFirstSwitch%2$s", targetName, targetClass.getType().genericsString());
        var firstSwitchLines = 
                asList(format(
                          "private final %1$s __switch = new %1$s(this);\n"
                        + "@Override public %1$s match() {\n"
                        + "     return __switch;\n"
                        + "}",
                        firstSwitchTypeDef)
                        .split("\n"));
        return firstSwitchLines;
    }
    
    private List<String> prepareToStringMethod() {
        if (hasMethod(format("String toString(%s)", targetClass.type.toString()), DEFAULT))
            return null;
        if (hasMethod(format("java.lang.String toString(%s)", targetClass.type.toString()), DEFAULT))
            return null;
        
        var choiceStrings = choices.stream()
            .map(choice -> {
                var camelName  = toCamelCase(choice.name);
                var paramCount = choice.params.size();
                if (paramCount == 0) {
                    return format("            .%1$s(__ -> \"%2$s\")", camelName, choice.name);
                } else {
                    var template       = templateRange(1, paramCount + 1, ",");
                    var templateParams = choice.params.stream().map(p -> camelName + "." + p.name).collect(joining(","));
                    return format("            .%1$s(%1$s -> \"%2$s(\" + String.format(\"%3$s\", %4$s) + \")\")", 
                                      camelName, choice.name, template, templateParams);
                }
            })
            .map("    "::concat)
            .collect(toList());
        
        var toStringLines = asList(
            asList(( "private volatile String toString = null;\n"
                  + "@Override\n"
                  + "public String toString() {\n"
                  + "    if (toString != null)\n"
                  + "        return toString;\n"
                  + "    synchronized(this) {\n"
                  + "        if (toString != null)\n"
                  + "            return toString;\n"
                  + "        toString = $utils.Match(this)"
                  ).split("\n")
            ),
            choiceStrings,
            asList(("        ;\n"
                  + "        return toString;\n"
                  + "    }\n"
                  + "}"
                  ).split("\n")
            )
        ).stream()
         .flatMap(List::stream)
         .collect(toList());
        return toStringLines;
    }
    
    private List<String> prepareHashCode() {
        var mthdSignature = format("int hashCode(%s)", targetClass.type.toString());
        if (hasMethod(mthdSignature, DEFAULT))
            return null;
        return asList(
               ("@Override\n"
              + "public int hashCode() {\n"
              + "    return toString().hashCode();\n"
              + "}"
              ).split("\n"));
    }
    
    private List<String> prepareEquals(String targetName) {
        if (hasMethod(format("boolean equals(%s, Object)", targetClass.type.toString()), DEFAULT))
            return null;
        if (hasMethod(format("boolean equals(%s, java.lang.Object)", targetClass.type.toString()), DEFAULT))
            return null;
        
        return asList(format(
                  "@Override\n"
                + "public boolean equals(Object obj) {\n"
                + "    if (!(obj instanceof %1$s))\n"
                + "        return false;\n"
                + "    \n"
                + "    if (this == obj)\n"
                + "        return true;\n"
                + "    \n"
                + "    String objToString  = obj.toString();\n"
                + "    String thisToString = this.toString();\n"
                + "    return thisToString.equals(objToString);\n"
                + "}", targetName)
                .split("\n"));
    }
    
    private boolean hasMethod(String mthdSignature, Method.Kind kind) {
        return targetClass.spec.methods
                .stream()
                .filter  (m -> (kind == null) ? true : kind.equals(m.kind))
                .anyMatch(m -> mthdSignature.equals(m.signature));
    }
}