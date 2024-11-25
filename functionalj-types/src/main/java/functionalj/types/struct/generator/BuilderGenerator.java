// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.FINAL;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static functionalj.types.struct.generator.model.Scope.STATIC;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenClass.TargetKind;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;

abstract class BuilderInterface {
    
    static class BuilderGetter extends BuilderInterface {
        
        private final Getter getter;
        
        public BuilderGetter(Getter getter) {
            this.getter = getter;
        }
        
        public Getter getter() {
            return getter;
        }
    }
    
    static class BuilderReady extends BuilderInterface {
    }
}

public class BuilderGenerator {
    
    private SourceSpec sourceSpec;
    
    public BuilderGenerator(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the class.
     *
     * @return  the generated class.
     */
    public GenClass build() {
        List<BuilderInterface> builderInterfaces = Stream.concat(sourceSpec.getGetters().stream().map(BuilderInterface.BuilderGetter::new), Stream.of(new BuilderInterface.BuilderReady())).collect(toList());
        String                 typeName          = sourceSpec.getTargetClassName();
        return generateBuilderCode(typeName, builderInterfaces);
    }
    
    private List<GenClass> generateSubInterfaces(String typeName, List<BuilderInterface> builderInterfaces) {
        int            interfaceCount = builderInterfaces.size();
        List<GenClass> interfaces     = new ArrayList<GenClass>();
        for (int i = 1; i < interfaceCount; i++) {
            GenClass code = generateSubInterface(typeName, builderInterfaces, i);
            interfaces.add(code);
        }
        return interfaces;
    }
    
    private GenClass generateSubInterface(String typeName, List<BuilderInterface> builderInterfaces, int i) {
        BuilderInterface builderInterface     = builderInterfaces.get(i);
        boolean          isGetter             = (builderInterface instanceof BuilderInterface.BuilderGetter);
        GenClass         builderInterfaceCode = isGetter ? generateGetterInterface(typeName, builderInterfaces, i) : generateReadyInterface(typeName);
        return builderInterfaceCode;
    }
    
    private GenClass generateGetterInterface(String typeName, List<BuilderInterface> builderInterfaces, int i) {
        BuilderInterface builderInterface = builderInterfaces.get(i);
        Getter           getter           = ((BuilderInterface.BuilderGetter) builderInterface).getter();
        String           getterName       = getter.name();
        String           getterType       = getter.type().simpleNameWithGeneric();
        String           interfaceName    = format("%1$sBuilder_without%2$s", typeName, capitalize(getterName));
        String           packageName      = sourceSpec.getPackageName();
        String           methodType       = getterAt(builderInterfaces, i + 1).map(nextGetter -> typeName + "Builder_without" + capitalize(nextGetter.name())).orElseGet(() -> typeName + "Builder_ready");
        GenParam         param            = new GenParam(getterName, new Type(packageName, getterType));
        String           mthd             = "public " + methodType + " " + getterName + "(" + param.toTerm(packageName) + ");";
        List<String>     extraMethodCode  = generateExtraMethods(typeName, builderInterfaces, i, true);
        GenClass         getterInterface  = new GenClass(null, PUBLIC, STATIC, MODIFIABLE, TargetKind.INTERFACE, new Type(packageName, interfaceName), (String) null, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), asList(ILines.linesOf(ILines.line(mthd), ILines.line(extraMethodCode))));
        return getterInterface;
    }
    
    private Optional<Getter> getterAt(List<BuilderInterface> builderInterfaces, int i) {
        int     interfaceCount = builderInterfaces.size();
        boolean isThisLastGetter = (i == (interfaceCount - 1));
        if (isThisLastGetter)
            return Optional.empty();
        
        BuilderInterface buildInterface = builderInterfaces.get(i);
        if (!(buildInterface instanceof BuilderInterface.BuilderGetter))
            return Optional.empty();
        
        Getter getter = ((BuilderInterface.BuilderGetter) buildInterface).getter();
        return Optional.of(getter);
    }
    
    private List<String> generateExtraMethods(String typeName, List<BuilderInterface> builderInterfaces, int interfaceIndx, boolean isDefault) {
        BuilderInterface builderInterface = builderInterfaces.get(interfaceIndx);
        Getter           getter           = ((BuilderInterface.BuilderGetter) builderInterface).getter();
        int              interfaceCount   = builderInterfaces.size();
        List<String>     lines            = new ArrayList<String>();
        lines.add("");
        if (getter.isNullable()) {
            for (int n = interfaceIndx + 1; n < interfaceCount; n++) {
                BuilderInterface bc = builderInterfaces.get(n);
                if (bc instanceof BuilderInterface.BuilderGetter) {
                    Getter bg         = ((BuilderInterface.BuilderGetter) bc).getter();
                    String bgName     = bg.name();
                    String bgType     = bg.type().simpleNameWithGeneric();
                    String methodType = getterAt(builderInterfaces, n + 1).map(nextGetter -> typeName + "Builder_without" + capitalize(nextGetter.name())).orElseGet(() -> typeName + "Builder_ready");
                    String mthDef     = format("%1$s(%2$s %1$s)", bgName, bgType);
                    lines.add("public " + (isDefault ? "default " : "") + methodType + " " + mthDef + "{");
                    StringBuffer returnLine = new StringBuffer();
                    returnLine.append("    return ");
                    
                    for (int c = interfaceIndx; c < n; c++) {
                        BuilderInterface bcc = builderInterfaces.get(c);
                        if (bcc instanceof BuilderInterface.BuilderGetter) {
                            Getter bgc = ((BuilderInterface.BuilderGetter) bcc).getter();
                            String call = bgc.name() + "(" + bgc.getDefaultValueCode("null") + ").";
                            returnLine.append(call);
                        }
                    }
                    
                    BuilderInterface bcc = builderInterfaces.get(n);
                    if (bcc instanceof BuilderInterface.BuilderGetter) {
                        Getter bgc  = ((BuilderInterface.BuilderGetter) bcc).getter();
                        String call = bgc.name() + "(" + bgc.name() + ")";
                        returnLine.append(call);
                    } else {
                        returnLine.append("");
                    }
                    returnLine.append(";");
                    lines.add(returnLine.toString());
                    lines.add("}");
                    if (!bg.isNullable()) {
                        break;
                    }
                } else {
                    lines.add("public " + (isDefault ? "default " : "") + typeName + " build() {");
                    StringBuffer returnLine = new StringBuffer();
                    returnLine.append("    return ");
                    for (int c = interfaceIndx; c < n; c++) {
                        BuilderInterface bcc = builderInterfaces.get(c);
                        if (bcc instanceof BuilderInterface.BuilderGetter) {
                            Getter bgc = ((BuilderInterface.BuilderGetter) bcc).getter();
                            returnLine.append(bgc.name() + "(");
                            returnLine.append(bgc.getDefaultValueCode("null"));
                            returnLine.append(").");
                        }
                    }
                    returnLine.append("build();");
                    lines.add(returnLine.toString());
                    lines.add("}");
                }
            }
        }
        return (lines.size() == 1) ? emptyList() : lines;
    }
    
    private GenClass generateReadyInterface(String typeName) {
        String packageName = sourceSpec.getPackageName();
        return new GenClass(
                null,
                PUBLIC, 
                STATIC, 
                MODIFIABLE, 
                TargetKind.INTERFACE, 
                new Type(packageName, typeName + "Builder_ready"), 
                null, // extends
                emptyList(), // implementeds
                emptyList(), // constructors
                emptyList(), // fields
                emptyList(), // methods
                asList(new GenMethod("build", sourceSpec.getTargetType(), PUBLIC, INSTANCE, MODIFIABLE, emptyList(), null)), // innerClasses
                emptyList(), // mores
                emptyList());
    }
    
    private GenClass generateBuilderCode(String typeName, List<BuilderInterface> builderInterfaces) {
        if (builderInterfaces.size() == 1) {
            return generateBuilderReadyClass(typeName);
        } else {
            return generateBuilderClass(typeName, builderInterfaces);
        }
    }
    
    private GenClass generateBuilderReadyClass(String typeName) {
        String targetClassName = sourceSpec.getTargetClassName();
        String packageName = sourceSpec.getPackageName();
        Type type = new Type(packageName, targetClassName, "Builder", new String[0]);
        // val builderReady    = new Type(packageName, targetClassName, typeName + "Builder_ready", new String[0]);
        // val implementeds    = asList(builderReady);
        List<Type> implementeds = Collections.emptyList();
        GenMethod buildMthd = new GenMethod(// name
            // type
            "build", sourceSpec.getTargetType(), PUBLIC, INSTANCE, FINAL, // params
            emptyList(), // Body
            ILines.line(String.format("return new %1$s();", typeName))
        );
        List<GenMethod> methods = asList(buildMthd);
        
        GenClass builderReadyClass = new GenClass(
                null,
                PUBLIC,
                STATIC,
                FINAL, // type
                type, // generic
                null, // extendeds
                emptyList(), // implementeds
                implementeds, // constructors
                emptyList(), // fields
                emptyList(), // methods
                methods, // innerClasses
                emptyList(), // mores
                emptyList());
        return builderReadyClass;
    }
    
    private GenClass generateBuilderClass(String typeName, List<BuilderInterface> builderInterfaces) {
        Getter           firstBuilder  = ((BuilderInterface.BuilderGetter) builderInterfaces.get(0)).getter();
        BuilderInterface secondBuilder = builderInterfaces.get(1);
        String           pckgName      = sourceSpec.getPackageName();
        Stream<String>   mthdBody      = builderBody(typeName, builderInterfaces);
        Type             mthdType      = builderMthdType(typeName, secondBuilder, pckgName);
        GenMethod builderMethod = new GenMethod(
                firstBuilder.name(),
                mthdType,
                PUBLIC,
                INSTANCE,
                FINAL,
                asList(new GenParam(firstBuilder.name(), firstBuilder.type())),
                ILines.line(mthdBody)
        );
        
        List<String>   extra = generateExtraMethods(typeName, builderInterfaces, 0, false);
        List<GenClass> interfaces = generateSubInterfaces(typeName, builderInterfaces);
        GenClass builderClass = new GenClass(
                null, PUBLIC, STATIC, FINAL, new Type(pckgName, typeName, "Builder", emptyList()), // generic
                null, // extendeds
                emptyList(), // implementeds
                emptyList(), // constructors
                emptyList(), // fields
                emptyList(), // methods
                asList(builderMethod), // innerClasses
                interfaces, // mores
                asList(ILines.line(extra))
        );
        return builderClass;
    }
    
    private Stream<String> builderBody(String typeName, List<BuilderInterface> builderInterfaces) {
        String         packageName = sourceSpec.getPackageName();
        Stream<String> returns     = builderInterfaces.stream()
                .skip(1)
                .map(builderClass -> {
                    if (builderClass instanceof BuilderInterface.BuilderGetter) {
                        BuilderInterface.BuilderGetter builder = (BuilderInterface.BuilderGetter) builderClass;
                        String line = String.format("return (%1$s %2$s)->{", builder.getter().type().simpleNameWithGeneric(packageName), builder.getter().name());
                        return line;
                    } else {
                        return "return ()->{";
                    }
                });
        String         newType   = format("    return new %1$s(", typeName);
        List<String>   paramList = builderInterfaces.stream().filter(b -> b instanceof BuilderInterface.BuilderGetter).map(b -> ((BuilderInterface.BuilderGetter) b).getter().name()).map(n -> "        " + n).collect(toList());
        Stream<String> params    = Stream.concat(paramList.stream().limit(paramList.size() - 1).map(p -> p + ","), paramList.stream().skip(paramList.size() - 1));
        Stream<String> closes    = builderInterfaces.stream().skip(1).map(builderClass -> "};");
        Stream<String> body      = Stream.of(returns, Stream.of(newType), params, Stream.of("    );"), closes).flatMap(s -> s);
        return body;
    }
    
    private Type builderMthdType(String typeName, BuilderInterface secondBuilder, final java.lang.String packageName) {
        Type builderType = null;
        if (secondBuilder instanceof BuilderInterface.BuilderGetter) {
            BuilderInterface.BuilderGetter secondB = (BuilderInterface.BuilderGetter) secondBuilder;
            builderType = new Type(packageName, typeName + "Builder_without" + capitalize(secondB.getter().name()));
        } else {
            builderType = new Type(packageName, typeName + "Builder_ready");
        }
        return builderType;
    }
    
    private static String capitalize(String name) {
        char first = Character.toUpperCase(name.charAt(0));
        if (name.length() == 1)
            return "" + first;
        
        return first + name.substring(1);
    }
}
