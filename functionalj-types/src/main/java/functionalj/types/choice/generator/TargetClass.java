// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.struct.generator.model.utils.samePackage;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import functionalj.types.Core;
import functionalj.types.Self;
import functionalj.types.Type;
import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.IChoice;
import functionalj.types.choice.generator.model.Method;
// import functionalj.types.choice.generator.model.Method.Kind;
import functionalj.types.choice.generator.model.SourceSpec;

public class TargetClass implements Lines {
    
    public final SourceSpec spec;
    
    public final Type type;
    
    public TargetClass(SourceSpec spec) {
        this.spec = spec;
        this.type = new Type(spec.sourceType.packageName(), null, spec.targetName, spec.generics);
    }
    
    public SourceSpec getSpec() {
        return spec;
    }
    
    public Type getType() {
        return type;
    }
    
    @Override
    public List<String> lines() {
        Set<String> imports = new TreeSet<String>();
        imports.add(ChoiceTypeSwitch.class.getCanonicalName());
        imports.add(IChoice.class.getCanonicalName());
        imports.add("java.util.function.Function");
        imports.add("java.util.function.Consumer");
        imports.add("java.util.function.Supplier");
        imports.add("functionalj.result.Result");
        imports.add("functionalj.pipeable.Pipeable");
        imports.add("functionalj.lens.core.LensSpec");
        imports.add("functionalj.lens.lenses.*");
        imports.add(Core.Generated.type().fullName());
        
        String       selfDef = "";
        List<String> specObj = null;
        if (spec.methods.stream().anyMatch(m -> Method.Kind.DEFAULT.equals(m.kind))) {
            imports.add("nullablej.utils.reflection.UProxy");
            specObj = asList(format("    private final %1$s __spec = UProxy.createDefaultProxy(%2$s.class);", spec.sourceType.fullName() + spec.sourceType.genericsString(), spec.sourceType.fullName()));
            if (spec.sourceType.generics().isEmpty())
                selfDef = ", Self";
            else
                selfDef = ", Self" + spec.sourceType.generics().size() + spec.sourceType.genericsString();
            if (spec.sourceType.generics().isEmpty())
                imports.add(Self.class.getCanonicalName());
            else
                imports.add(Self.class.getCanonicalName() + spec.sourceType.generics().size());
        }
        spec.choices.stream().map(c -> c.validationMethod).filter(m -> m != null).findAny().ifPresent(s -> {
            imports.add(spec.sourceType.packageName() + "." + spec.sourceType.encloseName() + "." + spec.sourceType.simpleName());
        });
        spec.choices.stream().flatMap(c -> c.params.stream()).map(p -> p.type()).filter(t -> t.packageName() != null).filter(t -> !"java.lang".equals(t.packageName())).forEach(t -> imports.add(t.fullName()));
        spec.generics.stream().flatMap(g -> g.boundTypes.stream()).filter(t -> t.packageName() != null).filter(t -> !"java.lang".equals(t.packageName())).forEach(t -> imports.add(t.fullName()));
        List<String> sourceMethods = new SourceMethod(this).lines().stream().filter(Objects::nonNull).map("    "::concat).collect(toList());
        
        List<String>  subClassConstructors = spec.choices.stream().flatMap(choice -> new SubClassConstructor(this, choice).lines().stream()).filter(Objects::nonNull).map("    "::concat).collect(toList());
        List<String>  subClassDefinitions  = spec.choices.stream().flatMap(choice -> new SubClassDefinition(this, choice).lines().stream()).filter(Objects::nonNull).map("    "::concat).collect(toList());
        List<String>  targetGeneral        = new TargetTypeGeneral(this, spec.choices).lines().stream().map("    "::concat).collect(toList());
        List<String>  targetCheckMethods   = new SubCheckMethod(this, spec.choices).lines().stream().map("    "::concat).collect(toList());
        List<String>  fromMapMethod        = new FromMapBuilder(this).lines().stream().map("    "::concat).collect(toList());
        List<String>  schemaMethod         = new SchemaBuilder(this).lines().stream().map("    "::concat).collect(toList());
        List<String>  switchClasses        = range(0, spec.choices.size()).mapToObj(index -> spec.choices.stream().skip(index).collect(toList())).flatMap(choices -> new SwitchClass(this, (choices.size() == spec.choices.size()), choices).lines().stream()).filter(Objects::nonNull).map("    "::concat).collect(toList());
        List<String>  choiceLens           = new ChoiceLensBuilder(spec).build();
        String        sourceSpec           = spec.sourceType.fullName();
        LocalDateTime genTime              = LocalDateTime.now();
        String        generated            = "@Generated(value = \"FunctionalJ\",date = \"" + genTime + "\", comments = \"" + sourceSpec + "\")";
        String        suppress             = "@SuppressWarnings(\"all\")";
        String        typeName             = type.typeWithGenerics();
        String        pckgName             = spec.sourceType.packageName();
        List<String>  importLines          = imports.stream().filter(importName -> !samePackage(pckgName, importName)).map(importName -> "import " + importName + ";").collect(toList());
        String        specConstant         = (spec.specObjName == null) ? "    " : "    public static final " + SourceSpec.class.getCanonicalName() + " " + spec.specObjName + " = " + spec.toCode() + ";";
        String        firstLine            = firstLine(selfDef);
        return asList(asList(format("package %s;", pckgName)), asList(format("")), importLines, asList(format("")), asList(generated), asList(suppress), asList(firstLine), asList(format("    ")), subClassConstructors, asList(format("    ")), specObj, asList(format("    ")), choiceLens, asList(format("    ")), asList(format("    private %s() {}", type.simpleName())), asList(format("    public %1$s __data() throws Exception { return this; }", typeName)), asList(format("    public Result<%1$s> toResult() { return Result.valueOf(this); }", typeName)), asList(format("    ")), fromMapMethod, asList(format("    ")), schemaMethod, asList(format("    ")), subClassDefinitions, asList(format("    ")), targetGeneral, asList(format("    ")), sourceMethods, asList(format("    ")), targetCheckMethods, asList(format("    ")), switchClasses, asList(format("    ")), asList(specConstant), asList(format("    ")), asList(format("}"))).stream().filter(Objects::nonNull).flatMap(List::stream).collect(toList());
    }
    
    private String firstLine(String selfDef) {
        if (spec.generateSealed) {
            String simpleName = type.simpleName();
            String permitList = spec.choices.stream().map(choice -> simpleName + "." + choice.name).collect(Collectors.joining(", "));
            return format("public sealed abstract class %1$s implements %6$s<%2$s.%2$sFirstSwitch%3$s>, Pipeable<%4$s>%5$s permits %7$s {", 
                    type.typeWithGenericDef(), 
                    type.simpleName(), 
                    type.genericsString(), 
                    type.typeWithGenerics(), 
                    selfDef, 
                    IChoice.class.getSimpleName(),
                    permitList);
        }
        
        return format("public abstract class %1$s implements %6$s<%2$s.%2$sFirstSwitch%3$s>, Pipeable<%4$s>%5$s {", 
                type.typeWithGenericDef(), 
                type.simpleName(), 
                type.genericsString(), 
                type.typeWithGenerics(), 
                selfDef, 
                IChoice.class.getSimpleName());
    }
    
}
