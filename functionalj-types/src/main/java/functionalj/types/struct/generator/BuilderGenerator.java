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
        var builderInterfaces =
                Stream.concat(
                        sourceSpec.getGetters().stream().map(BuilderInterface.BuilderGetter::new),
                        Stream.of(new BuilderInterface.BuilderReady())
                )
                .collect(toList());
        var typeName = sourceSpec.getTargetClassName();
        return generateBuilderCode(typeName, builderInterfaces);
    }
    
    private List<GenClass> generateSubInterfaces(
            String                 typeName,
            List<BuilderInterface> builderInterfaces) {
        var interfaceCount = builderInterfaces.size();
        var interfaces     = new ArrayList<GenClass>();
        for (var i = 1; i < interfaceCount; i++) {
            GenClass code = generateSubInterface(typeName, builderInterfaces, i);
            interfaces.add(code);
        }
        return interfaces;
    }
    
    private GenClass generateSubInterface(String typeName, List<BuilderInterface> builderInterfaces, int i) {
        var builderInterface = builderInterfaces.get(i);
        var isGetter = (builderInterface instanceof BuilderInterface.BuilderGetter);
        var builderInterfaceCode
                = isGetter
                ? generateGetterInterface(typeName, builderInterfaces, i)
                : generateReadyInterface (typeName);
        return builderInterfaceCode;
    }
    
    private GenClass generateGetterInterface(String typeName, List<BuilderInterface> builderInterfaces, int i) {
        var builderInterface = builderInterfaces.get(i);
        var getter           = ((BuilderInterface.BuilderGetter) builderInterface).getter();
        var getterName       = getter.getName();
        var getterType       = getter.getType().simpleNameWithGeneric();
        
        var interfaceName = format("%1$sBuilder_without%2$s", typeName, capitalize(getterName));
        var packageName = sourceSpec.getPackageName();
        var methodType
                = getterAt(builderInterfaces, i + 1)
                .map      (nextGetter -> typeName + "Builder_without" + capitalize(nextGetter.getName()))
                .orElseGet(()         -> typeName + "Builder_ready");
        
        var param = new GenParam(getterName, new Type(packageName, getterType));
        var mthd  = "public " + methodType + " " + getterName + "(" + param.toTerm(packageName) + ");";
        var extraMethodCode = generateExtraMethods(typeName, builderInterfaces, i, true);
        
        var getterInterface = new GenClass(
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
        var interfaceCount   = builderInterfaces.size();
        var isThisLastGetter = (i == (interfaceCount - 1));
        
        if (isThisLastGetter)
            return Optional.empty();
        
        var buildInterface = builderInterfaces.get(i);
        if (!(buildInterface instanceof BuilderInterface.BuilderGetter))
            return Optional.empty();
        
        var getter = ((BuilderInterface.BuilderGetter) buildInterface).getter();
        return Optional.of(getter);
    }
    
    private List<String> generateExtraMethods(String typeName, List<BuilderInterface> builderInterfaces,
            int interfaceIndx, boolean isDefault) {
        
        var builderInterface = builderInterfaces.get(interfaceIndx);
        var getter           = ((BuilderInterface.BuilderGetter) builderInterface).getter();
        
        var interfaceCount = builderInterfaces.size();
        var lines          = new ArrayList<String>();
        lines.add("");
        
        if (getter.isNullable()) {
            for (int n = interfaceIndx + 1; n < interfaceCount; n++) {
                BuilderInterface bc = builderInterfaces.get(n);
                if (bc instanceof BuilderInterface.BuilderGetter) {
                    var bg = ((BuilderInterface.BuilderGetter) bc).getter();
                    var bgName = bg.getName();
                    var bgType = bg.getType().simpleNameWithGeneric();
                    
                    var methodType
                            = getterAt(builderInterfaces, n + 1)
                            .map      (nextGetter -> typeName + "Builder_without" + capitalize(nextGetter.getName()))
                            .orElseGet(()         -> typeName + "Builder_ready");
                    
                    var mthDef = format("%1$s(%2$s %1$s)", bgName, bgType);
                    lines.add("public " + (isDefault ? "default " : "") + methodType + " " + mthDef + "{");
                    
                    var returnLine = new StringBuffer();
                    returnLine.append("    return ");
                    for (int c = interfaceIndx; c < n; c++) {
                        BuilderInterface bcc = builderInterfaces.get(c);
                        if (bcc instanceof BuilderInterface.BuilderGetter) {
                            Getter bgc  = ((BuilderInterface.BuilderGetter)bcc).getter();
                            String call = bgc.getName() + "(" + bgc.getDefaultValueCode("null") + ").";
                            returnLine.append(call);
                        }
                    }
                    var bcc = builderInterfaces.get(n);
                    if (bcc instanceof BuilderInterface.BuilderGetter) {
                        var bgc  = ((BuilderInterface.BuilderGetter)bcc).getter();
                        var call = bgc.getName() + "(" + bgc.getName() + ")";
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
                    
                    var returnLine = new StringBuffer();
                    returnLine.append("    return ");
                    for (var c = interfaceIndx; c < n; c++) {
                        var bcc = builderInterfaces.get(c);
                        if (bcc instanceof BuilderInterface.BuilderGetter) {
                            Getter bgc = ((BuilderInterface.BuilderGetter)bcc).getter();
                            returnLine.append(bgc.getName() + "(");
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
        var packageName = sourceSpec.getPackageName();
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
        var targetClassName = sourceSpec.getTargetClassName();
        var packageName     = sourceSpec.getPackageName();
        var type            = new Type(packageName, targetClassName, "Builder", new String[0]);
        var builderReady    = new Type(packageName, targetClassName, typeName + "Builder_ready", new String[0]);
        var implementeds    = asList(builderReady);
        
        var buildMthd = new GenMethod(
                PUBLIC, INSTANCE, FINAL,
                // type
                sourceSpec.getTargetType(),
                // name
                "build",
                // params
                emptyList(),
                // Body
                ILines.line(String.format("return new %1$s();", typeName)));
        var methods = asList(buildMthd);
        
        var builderReadyClass = new GenClass(
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
        var firstBuilder  = ((BuilderInterface.BuilderGetter)builderInterfaces.get(0)).getter();
        var secondBuilder = builderInterfaces.get(1);
        
        var pckgName = sourceSpec.getPackageName();
        var mthdBody = builderBody(typeName, builderInterfaces);
        var mthdType = builderMthdType(typeName, secondBuilder, pckgName);
        
        var builderMethod = new GenMethod(
                PUBLIC, INSTANCE, FINAL,
                // type
                mthdType,
                // name
                firstBuilder.getName(),
                // params
                asList(new GenParam(firstBuilder.getName(), firstBuilder.getType())),
                // body
                ILines.line(mthdBody));
        
        var extra = generateExtraMethods(typeName, builderInterfaces, 0, false);
        
        var interfaces = generateSubInterfaces(typeName, builderInterfaces);
        
        var builderClass = new GenClass(
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
        var packageName = sourceSpec.getPackageName();
        var returns =
                builderInterfaces.stream()
                .skip(1)
                .map(builderClass -> {
                    if (builderClass instanceof BuilderInterface.BuilderGetter) {
                        BuilderInterface.BuilderGetter builder = (BuilderInterface.BuilderGetter)builderClass;
                        String line = String.format("return (%1$s %2$s)->{",
                                builder.getter().getType().simpleNameWithGeneric(packageName),
                                builder.getter().getName()
                        );
                        return line;
                    } else {
                        return "return ()->{";
                    }
                });
        
        var newType = String.format("    return new %1$s(", typeName);
        
        var paramList = builderInterfaces.stream()
                .filter (b -> b instanceof BuilderInterface.BuilderGetter)
                .map    (b -> ((BuilderInterface.BuilderGetter)b).getter().getName())
                .map    (n -> "        " + n)
                .collect(toList());
        var params = Stream.concat(
            paramList.stream().limit(paramList.size() - 1).map(p -> p + ","),
            paramList.stream().skip (paramList.size() - 1)
        );
        
        var closes = builderInterfaces.stream()
        .skip(1)
        .map(builderClass -> "};")
        ;
        
        var body = Stream.of(
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
            builderType = new Type(packageName, typeName + "Builder_without" + capitalize(secondB.getter().getName()));
        } else {
            builderType = new Type(packageName, typeName + "Builder_ready");
        }
        return builderType;
    }
    
    private static String capitalize(String name) {
        var first = Character.toUpperCase(name.charAt(0));
        if (name.length() == 1)
            return "" + first;
        
        return first + name.substring(1);
    }
    
}
