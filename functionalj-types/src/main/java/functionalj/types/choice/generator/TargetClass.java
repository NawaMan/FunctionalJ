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

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

import functionalj.types.choice.IChoice;
import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.Self;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.choice.generator.model.Type;
import functionalj.types.choice.generator.model.Method.Kind;
import lombok.Value;
import lombok.val;


@Value
public class TargetClass implements Lines {
    public final SourceSpec spec;
    public final Type       type;
    
    public TargetClass(SourceSpec spec) {
        this.spec = spec;
        this.type = new Type(spec.sourceType.packageName, null, spec.targetName, spec.generics);
    }
    
    // TODO - type can do these.
    
    public String genericParams() {
        return (spec.generics.isEmpty() ? "" : (spec.generics.stream().map(g -> g.name).collect(joining(","))));
    }
    public String generics() {
        return (spec.generics.isEmpty() ? "" : ("<" + genericParams() + ">"));
    }
    public String typeWithGenerics() {
        return type.simpleName + generics();
    }
    public String genericDefParams() {
        return (spec.generics.isEmpty() ? "" : (spec.generics.stream().map(g -> g.withBound).collect(joining(","))));
    }
    public String genericDef() {
        return (spec.generics.isEmpty() ? "" : ("<" + genericDefParams() + ">"));
    }
    public String typeWithGenericDef() {
        return type.simpleName + genericDef();
    }
    
    @Override
    public List<String> lines() {
        val imports = new TreeSet<String>();
        imports.add(ChoiceTypeSwitch.class.getCanonicalName());
        imports.add(IChoice.class.getCanonicalName());
        imports.add("java.util.function.Function");
        imports.add("java.util.function.Consumer");
        imports.add("java.util.function.Predicate");
        imports.add("java.util.function.Supplier");
        imports.add("functionalj.result.Result");
        imports.add("functionalj.pipeable.Pipeable");
        imports.add("functionalj.lens.core.LensSpec");
        imports.add("functionalj.lens.lenses.*");
        
        val hasChoiceWuthMoreThanOneParam = spec.choices.stream().anyMatch(c -> c.params.size() >1);
        if (hasChoiceWuthMoreThanOneParam) {
            imports.add("functionalj.types.Absent");
        }
        
        String selfDef = "";
        List<String> specObj = null;
        if (spec.methods.stream().anyMatch(m -> Kind.DEFAULT.equals(m.kind))) {
            // TODO - move this to $utils ?
            imports.add("nullablej.utils.reflection.UProxy");
            specObj = asList(format("    private final %1$s __spec = UProxy.createDefaultProxy(%2$s.class);", 
                    spec.sourceType.fullName() + spec.sourceType.generics(),
                    spec.sourceType.fullName()));
            
            if (spec.sourceType.generics.isEmpty())
                 selfDef = ", Self";
            else selfDef = ", Self" + spec.sourceType.generics.size() + spec.sourceType.generics();
            
            if (spec.sourceType.generics.isEmpty())
                 imports.add(Self.class.getCanonicalName());
            else imports.add(Self.class.getCanonicalName() + spec.sourceType.generics.size());
        }
        
        spec.choices.stream()
            .map   (c -> c.validationMethod)
            .filter(m -> m != null)
            .findAny()
            .ifPresent(s -> {
                imports.add(spec.sourceType.packageName + "." + spec.sourceType.encloseName + "." + spec.sourceType.simpleName);
            });
        
        spec.choices.stream()
            .flatMap(c -> c.params.stream())
            .map    (p -> p.type)
            .filter (t -> t.packageName != null)
            .filter (t -> !"java.lang".equals(t.packageName))
            .forEach(t -> imports.add(t.fullName()));
        
        spec.generics.stream()
            .flatMap(g -> g.boundTypes.stream())
            .filter (t -> t.packageName != null)
            .filter (t -> !"java.lang".equals(t.packageName))
            .forEach(t -> imports.add(t.fullName()));
        
        val sourceMethods = new SourceMethod(this).lines().stream()
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList());;
        
        val subClassConstructors 
                = spec.choices.stream()
                .flatMap(choice -> new SubClassConstructor(this, choice).lines().stream())
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList());
        
        val subClassDefinitions
                = spec.choices.stream()
                .flatMap(choice -> new SubClassDefinition(this, choice).lines().stream())
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList());
        
        val targetGeneral
                = new TargetTypeGeneral(this, spec.choices)
                .lines().stream()
                .map("    "::concat)
                .collect(toList());
        
        val targetCheckMethods
                = new SubCheckMethod(this, spec.choices)
                .lines().stream()
                .map("    "::concat)
                .collect(toList());
        val fromMapMethod 
                = new FromMapBuilder(this)
                .lines().stream()
                .map("    "::concat)
                .collect(toList());
        val schemaMethod
                = new SchemaBuilder(this)
                .lines().stream()
                .map("    "::concat)
                .collect(toList());
        
        val switchClasses = range(0, spec.choices.size())
                .mapToObj(index   -> spec.choices.stream().skip(index).collect(toList()))
                .flatMap (choices -> new SwitchClass(this, (choices.size() == spec.choices.size()), choices).lines().stream())
                .filter(Objects::nonNull)
                .map("    "::concat)
                .collect(toList())
                ;
        
        val choiceLens = new ChoiceLensBuilder(spec).build();
        
        val typeName     = typeWithGenerics();
        val pckgName     = spec.sourceType.packageName;
        val importLines  = imports.stream().map(i -> "import " + i + ";").collect(toList());
        val specConstant = (spec.specObjName == null) ? "    " : "    public static final " + SourceSpec.class.getCanonicalName() + " " + spec.specObjName + " = " + spec.toCode() + ";";
        return asList(
                asList(format("package %s;", pckgName)),
                asList(format("")),
                importLines,
                asList(format("")),
                asList("// " + spec.sourceType.fullName()),
                asList(format("")),
                asList(format("@SuppressWarnings({\"javadoc\", \"rawtypes\", \"unchecked\"})")),
                asList(format("public abstract class %1$s implements %6$s<%2$s.%2$sFirstSwitch%3$s>, Pipeable<%4$s>%5$s {", typeWithGenericDef(), type.simpleName, generics(), typeWithGenerics(), selfDef, IChoice.class.getSimpleName())),
                asList(format("    ")),
                subClassConstructors,
                asList(format("    ")),
                specObj,
                asList(format("    ")),
                choiceLens,
                asList(format("    ")),
                asList(format("    private %s() {}", type.simpleName)),
                asList(format("    public %1$s __data() throws Exception { return this; }",     typeName)),
                asList(format("    public Result<%1$s> toResult() { return Result.valueOf(this); }", typeName)),
                asList(format("    ")),
                fromMapMethod,
                asList(format("    ")),
                schemaMethod,
                asList(format("    ")),
                subClassDefinitions,
                asList(format("    ")),
                targetGeneral,
                asList(format("    ")),
                sourceMethods,
                asList(format("    ")),
                targetCheckMethods,
                asList(format("    ")),
                switchClasses,
                asList(format("    ")),
                asList(specConstant),
                asList(format("    ")),
                asList(format("}"))
            ).stream()
            .filter (Objects::nonNull)
            .flatMap(List::stream)
            .collect(toList());
    }
}