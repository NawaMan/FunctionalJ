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
package functionalj.types.choice.generator;

import static functionalj.types.choice.generator.Utils.toTitleCase;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import lombok.AllArgsConstructor;
import lombok.val;


@AllArgsConstructor
public class SubClassDefinition implements Lines {
    public final TargetClass targetClass;
    public final Case        choice;
    
    @Override
    public List<String> lines() {
        val name = choice.name;
        val lens = new CaseLensBuilder(targetClass.spec, choice);
        val lensTheInstance  = lens.generateTheLensField().toDefinition(targetClass.type.packageName()).lines().findFirst().get();
        val lensEachInstance = lens.generateEachLensField().toDefinition(targetClass.type.packageName()).lines().findFirst().get();
        val toMapMethod  = new ToMapBuilder(targetClass, this.choice);
        if (!choice.isParameterized()) {
            return asList(
                    asList(format("public static final class %1$s%2$s extends %3$s {",    name, targetClass.getType().genericDef(), targetClass.getType().typeWithGenerics())),
                    asList(format("    " + lensTheInstance)),
                    asList(format("    " + lensEachInstance)),
                    asList(format("    private static final %1$s instance = new %1$s();", name)),
                    asList(format("    private %1$s() {}",                                name)),
                    lens.build().stream().map(l -> "    " + l).collect(Collectors.toList()),
                    toMapMethod.lines().stream().map(line -> "    " + line).collect(toList()),
                    new SubSchemaBuilder(choice) .lines().stream().map(line -> "    " + line).collect(toList()),
                    new SubFromMapBuilder(targetClass, choice).lines().stream().map(line -> "    " + line).collect(toList()),
                    asList(format("}"))
            ).stream()
            .flatMap(List::stream)
            .collect(toList());
        }
        
        val paramDefs   = choice.mapJoinParams(p -> p.type().typeWithGenerics() + " " + p.name(), ", ");
        val paramCalls  = choice.mapJoinParams(p ->                                   p.name(), ", ");
        val fieldAccss  = targetClass.spec.publicFields ? "public" : "private";
        return asList(
                asList(               format("public static final class %1$s%2$s extends %3$s {", name, targetClass.getType().genericDef(), targetClass.getType().typeWithGenerics())),
                asList(               format("    " + lensTheInstance)),
                asList(               format("    " + lensEachInstance)),
                choice.mapParams(p -> format("    %1$s %2$s %3$s;",      fieldAccss, p.type().typeWithGenerics(), p.name())),
                asList(               format("    private %1$s(%2$s) {", name, paramDefs)),
                choice.mapParams(this::fieldAssignment),
                asList(               format("    }")),
                choice.mapParams(p -> format("    public %1$s %2$s() { return %2$s; }", p.type().typeWithGenerics(), p.name())),
                choice.mapParams(p -> format("    public %1$s with%2$s(%3$s %4$s) { return new %5$s(%6$s); }",
                                                      name + targetClass.getType().genericsString(), toTitleCase(p.name()), p.type().typeWithGenerics(), p.name(), name + targetClass.getType().genericsString(), paramCalls)),
                lens       .build().stream().map(line -> "    " + line).collect(toList()),
                toMapMethod.lines().stream().map(line -> "    " + line).collect(toList()),
                new SubSchemaBuilder(choice) .lines().stream().map(line -> "    " + line).collect(toList()),
                new SubFromMapBuilder(targetClass, choice).lines().stream().map(line -> "    " + line).collect(toList()),
                asList(               format("}"))
            ).stream()
            .flatMap(List::stream)
            .collect(toList());
    }
    
    private String fieldAssignment(CaseParam p) {
        if (!p.type().isPrimitive() && (p.defValue() != null))
             return format("        this.%1$s = (%1$s != null) ? %1$s : %2$s;", p.name(), p.defaultValueCode());
        else if (p.type().isPrimitive() || p.isNullable())
             return format("        this.%1$s = %1$s;",                 p.name());
        else return format("        this.%1$s = $utils.notNull(%1$s);", p.name());
    }
    
}
