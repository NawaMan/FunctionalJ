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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import lombok.val;


abstract class BuilderInterface {
    
    static class BuilderGetter extends BuilderInterface {
        private final Getter getter;
        public BuilderGetter(Getter getter) { this.getter = getter; }
        public Getter getter() { return getter; }
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
        val builderInterfaces =
                Stream.concat(
                        sourceSpec.getGetters().stream().map(BuilderInterface.BuilderGetter::new),
                        Stream.of(new BuilderInterface.BuilderReady())
                )
                .collect(toList());
        val typeName = sourceSpec.getTargetClassName();
        return generateBuilderCode(typeName, builderInterfaces);
    }
    
    private List<GenClass> generateSubInterfaces(
            String                 typeName,
            List<BuilderInterface> builderInterfaces) {
        val interfaceCount = builderInterfaces.size();
        val interfaces     = new ArrayList<GenClass>();
        for (int i = 1; i < interfaceCount; i++) {
            GenClass code = generateSubInterface(typeName, builderInterfaces, i);
            interfaces.add(code);
        }
        return interfaces;
    }
    
    private GenClass generateSubInterface(String typeName, List<BuilderInterface> builderInterfaces, int i) {
        val builderInterface = builderInterfaces.get(i);
        val isGetter = (builderInterface instanceof BuilderInterface.BuilderGetter);
        val builderInterfaceCode
                = isGetter
                ? generateGetterInterface(typeName, builderInterfaces, i)
                : generateReadyInterface (typeName);
        return builderInterfaceCode;
    }
    
    private GenClass generateGetterInterface(String typeName, List<BuilderInterface> builderInterfaces, int i) {
        val builderInterface = builderInterfaces.get(i);
        val getter           = ((BuilderInterface.BuilderGetter) builderInterface).getter();
        val getterName       = getter.name();
        val getterType       = getter.type().simpleNameWithGeneric();
        
        val interfaceName = format("%1$sBuilder_without%2$s", typeName, capitalize(getterName));
        val packageName = sourceSpec.getPackageName();
        val methodType
                = getterAt(builderInterfaces, i + 1)
                .map      (nextGetter -> typeName + "Builder_without" + capitalize(nextGetter.name()))
                .orElseGet(()         -> typeName + "Builder_ready");
        
        val param = new GenParam(getterName, new Type(packageName, getterType));
        val mthd  = "public " + methodType + " " + getterName + "(" + param.toTerm(packageName) + ");";
        val extraMethodCode = generateExtraMethods(typeName, builderInterfaces, i, true);
        
        val getterInterface = new GenClass(
                PUBLIC, STATIC, MODIFIABLE, false,
                new Type(packageName, interfaceName),
                (String)null,
                emptyList(), emptyList(),
                emptyList(), emptyList(),
                emptyList(),
                emptyList(),
                asList(ILines.linesOf(
                        ILines.line(mthd),
                        ILines.line(extraMethodCode))));
        
        return getterInterface;
    }
    
    private Optional<Getter> getterAt(List<BuilderInterface> builderInterfaces, int i) {
        val interfaceCount   = builderInterfaces.size();
        val isThisLastGetter = (i == (interfaceCount - 1));
        
        if (isThisLastGetter)
            return Optional.empty();
        
        val buildInterface = builderInterfaces.get(i);
        if (!(buildInterface instanceof BuilderInterface.BuilderGetter))
            return Optional.empty();
        
        val getter = ((BuilderInterface.BuilderGetter) buildInterface).getter();
        return Optional.of(getter);
    }
    
    private List<String> generateExtraMethods(String typeName, List<BuilderInterface> builderInterfaces,
            int interfaceIndx, boolean isDefault) {
        
        val builderInterface = builderInterfaces.get(interfaceIndx);
        val getter           = ((BuilderInterface.BuilderGetter) builderInterface).getter();
        
        val interfaceCount = builderInterfaces.size();
        val lines          = new ArrayList<String>();
        lines.add("");
        
        if (getter.isNullable()) {
            for (int n = interfaceIndx + 1; n < interfaceCount; n++) {
                BuilderInterface bc = builderInterfaces.get(n);
                if (bc instanceof BuilderInterface.BuilderGetter) {
                    val bg = ((BuilderInterface.BuilderGetter) bc).getter();
                    val bgName = bg.name();
                    val bgType = bg.type().simpleNameWithGeneric();
                    
                    val methodType
                            = getterAt(builderInterfaces, n + 1)
                            .map      (nextGetter -> typeName + "Builder_without" + capitalize(nextGetter.name()))
                            .orElseGet(()         -> typeName + "Builder_ready");
                    
                    val mthDef = format("%1$s(%2$s %1$s)", bgName, bgType);
                    lines.add("public " + (isDefault ? "default " : "") + methodType + " " + mthDef + "{");
                    
                    val returnLine = new StringBuffer();
                    returnLine.append("    return ");
                    for (int c = interfaceIndx; c < n; c++) {
                        BuilderInterface bcc = builderInterfaces.get(c);
                        if (bcc instanceof BuilderInterface.BuilderGetter) {
                            Getter bgc  = ((BuilderInterface.BuilderGetter)bcc).getter();
                            String call = bgc.name() + "(" + bgc.getDefaultValueCode("null") + ").";
                            returnLine.append(call);
                        }
                    }
                    val bcc = builderInterfaces.get(n);
                    if (bcc instanceof BuilderInterface.BuilderGetter) {
                        val bgc  = ((BuilderInterface.BuilderGetter)bcc).getter();
                        val call = bgc.name() + "(" + bgc.name() + ")";
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
                    
                    val returnLine = new StringBuffer();
                    returnLine.append("    return ");
                    for (int c = interfaceIndx; c < n; c++) {
                        val bcc = builderInterfaces.get(c);
                        if (bcc instanceof BuilderInterface.BuilderGetter) {
                            Getter bgc = ((BuilderInterface.BuilderGetter)bcc).getter();
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
        val packageName = sourceSpec.getPackageName();
        return new GenClass(PUBLIC, STATIC, MODIFIABLE,
                false,
                new Type(packageName, typeName + "Builder_ready"),
                null,
                // extends
                emptyList(),
                // implementeds
                emptyList(),
                // constructors
                emptyList(),
                // fields
                emptyList(),
                // methods
                asList(new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, sourceSpec.getTargetType(), "build", emptyList(), null)),
                // innerClasses
                emptyList(),
                // mores
                emptyList());
    }
    
    private GenClass generateBuilderCode(
            String                 typeName,
            List<BuilderInterface> builderInterfaces) {
        if (builderInterfaces.size() == 1) {
            return generateBuilderReadyClass(typeName);
        } else {
            return generateBuilderClass(typeName, builderInterfaces);
        }
    }
    
    private GenClass generateBuilderReadyClass(String typeName) {
        val targetClassName = sourceSpec.getTargetClassName();
        val packageName     = sourceSpec.getPackageName();
        val type            = new Type(packageName, targetClassName, "Builder", new String[0]);
        val builderReady    = new Type(packageName, targetClassName, typeName + "Builder_ready", new String[0]);
        val implementeds    = asList(builderReady);
        
        val buildMthd = new GenMethod(
                PUBLIC, INSTANCE, FINAL,
                // type
                sourceSpec.getTargetType(),
                // name
                "build",
                // params
                emptyList(),
                // Body
                ILines.line(String.format("return new %1$s();", typeName)));
        val methods = asList(buildMthd);
        
        val builderReadyClass = new GenClass(
                PUBLIC, STATIC, FINAL,
                // type
                type,
                // generic
                null,
                // extendeds
                emptyList(),
                // implementeds
                implementeds,
                // constructors
                emptyList(),
                // fields
                emptyList(),
                // methods
                methods,
                // innerClasses
                emptyList(),
                // mores
                emptyList());
        
        return builderReadyClass;
    }
    
    private GenClass generateBuilderClass(String typeName, List<BuilderInterface> builderInterfaces) {
        val firstBuilder  = ((BuilderInterface.BuilderGetter)builderInterfaces.get(0)).getter();
        val secondBuilder = builderInterfaces.get(1);
        
        val pckgName = sourceSpec.getPackageName();
        val mthdBody = builderBody(typeName, builderInterfaces);
        val mthdType = builderMthdType(typeName, secondBuilder, pckgName);
        
        val builderMethod = new GenMethod(
                PUBLIC, INSTANCE, FINAL,
                // type
                mthdType,
                // name
                firstBuilder.name(),
                // params
                asList(new GenParam(firstBuilder.name(), firstBuilder.type())),
                // body
                ILines.line(mthdBody));
        
        val extra = generateExtraMethods(typeName, builderInterfaces, 0, false);
        
        val interfaces = generateSubInterfaces(typeName, builderInterfaces);
        
        val builderClass = new GenClass(
                PUBLIC, STATIC, FINAL,
                new Type(pckgName, typeName, "Builder", emptyList()),
                // generic
                null,
                // extendeds
                emptyList(),
                // implementeds
                emptyList(),
                // constructors
                emptyList(),
                // fields
                emptyList(),
                // methods
                asList(builderMethod),
                // innerClasses
                interfaces,
                // mores
                asList(ILines.line(extra))
                );
        return builderClass;
    }
    
    private Stream<String> builderBody(String typeName, List<BuilderInterface> builderInterfaces) {
        val packageName = sourceSpec.getPackageName();
        val returns =
                builderInterfaces.stream()
                .skip(1)
                .map(builderClass -> {
                    if (builderClass instanceof BuilderInterface.BuilderGetter) {
                        BuilderInterface.BuilderGetter builder = (BuilderInterface.BuilderGetter)builderClass;
                        String line = String.format("return (%1$s %2$s)->{",
                                builder.getter().type().simpleNameWithGeneric(packageName),
                                builder.getter().name()
                        );
                        return line;
                    } else {
                        return "return ()->{";
                    }
                });
        
        val newType = String.format("    return new %1$s(", typeName);
        
        val paramList = builderInterfaces.stream()
                .filter (b -> b instanceof BuilderInterface.BuilderGetter)
                .map    (b -> ((BuilderInterface.BuilderGetter)b).getter().name())
                .map    (n -> "        " + n)
                .collect(toList());
        val params = Stream.concat(
            paramList.stream().limit(paramList.size() - 1).map(p -> p + ","),
            paramList.stream().skip (paramList.size() - 1)
        );
        
        val closes = builderInterfaces.stream()
        .skip(1)
        .map(builderClass -> "};")
        ;
        
        val body = Stream.of(
                returns,
                Stream.of(newType),
                params,
                Stream.of("    );"),
                closes
        )
        .flatMap(s -> s)
        ;
        return body;
    }
    
    private Type builderMthdType(String typeName, BuilderInterface secondBuilder,
            final java.lang.String packageName) {
        Type builderType = null;
        if (secondBuilder instanceof BuilderInterface.BuilderGetter) {
            BuilderInterface.BuilderGetter secondB = (BuilderInterface.BuilderGetter)secondBuilder;
            builderType = new Type(packageName, typeName + "Builder_without" + capitalize(secondB.getter().name()));
        } else {
            builderType = new Type(packageName, typeName + "Builder_ready");
        }
        return builderType;
    }
    
    private static String capitalize(String name) {
        val first = Character.toUpperCase(name.charAt(0));
        if (name.length() == 1)
            return "" + first;
        
        return first + name.substring(1);
    }
    
}
