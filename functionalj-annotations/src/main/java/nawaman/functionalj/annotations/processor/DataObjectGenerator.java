package nawaman.functionalj.annotations.processor;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.concat;
import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.Accessibility.PRIVATE;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.Accessibility.PUBLIC;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.ILines.indent;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.ILines.line;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.Modifiability.FINAL;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.Modifiability.MODIFIABLE;
import static nawaman.functionalj.annotations.processor.DataObjectGenerator.Scope.INSTANCE;
import static nawaman.functionalj.functions.StringFunctions.format1With;
import static nawaman.functionalj.functions.StringFunctions.prependWith;
import static nawaman.functionalj.functions.StringFunctions.toStr;
import static nawaman.functionalj.functions.StringFunctions.wrapWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import static java.util.Collections.emptyList;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;
import nawaman.functionalj.annotations.processor.DataObjectGenerator.GenField;
import nawaman.functionalj.annotations.processor.DataObjectGenerator.GenMethod;
import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.lens.IPostConstruct;
import nawaman.functionalj.lens.StringLens;

@SuppressWarnings("javadoc")
public class DataObjectGenerator {

    
    @Value
    @Wither
    public static class Spec {
        
        private String specClassName;
        private String packageName;
        private String targetClassName;
        private String targetPackageName;
        private boolean isClass;
        private List<Getter> getters;
        
        public Type getTargetType() {
            return new Type(targetClassName, targetPackageName);
        }
        public Type toType() {
            return new Type(specClassName, packageName);
        }
    }
    
    @Value
    @Wither
    public static class Getter {
        
        private String name;
        private Type type;
        
    }
    
    @Value
    @Wither
    public static class Type {
        
        private String simpleName;
        private String packageName;
        
        public String fullName() {
            return packageName + "." + simpleName;
        }
        
    }
    
    
    
    
    // == output ==
    
    public static interface IRequireTypes {
        public Stream<Type> getRequiredTypes();
    }
    
    public static interface ILines {
        
        public Stream<String> lines();
        
        public static ILines line(String ... lines) {
            if (lines != null && lines.length == 0)
                return ()->Stream.of("");
            
            return ()->Stream.of(lines).filter(Objects::nonNull);
        }
        public static ILines of(ILines lines) {
            return () -> Stream.of(lines)
                    .flatMap(ILines::lines)
                    .filter(Objects::nonNull);
        }
        
        public static ILines of(Stream<ILines> streams) {
            return ()-> Stream.of(streams).flatMap(themAll()).flatMap(ILines::lines).filter(Objects::nonNull);
        }
        public static ILines indent(ILines lines) {
            return indent(Stream.of(lines));
        }
        public static ILines indent(Stream<ILines> streams) {
            return ()-> Stream.of(streams)
                    .flatMap(themAll())
                    .flatMap(ILines::lines)
                    .filter(Objects::nonNull)
                    .map(prependWith("    "));
        }
    }
    
    @Value
    @Builder
    @Wither
    public static class RecordSpec {
        
        private String className;
        private String packageName;
        private List<Type> extendeds;
        private List<Type> implementeds;
        private List<GenConstructor> constructors;
        private List<GenField>       fields;
        private List<GenMethod>      methods;
        private List<ILines>         mores;
        
        public String fullName() {
            return className + "." + packageName;
        }
    }
    
    public static enum Accessibility {
        PUBLIC, PRIVATE, PROTECTED, PACKAGE;
        
        public String toString() {
            return (this == PACKAGE)
                    ? null
                    : name().toLowerCase();
        }
    }
    
    public static enum Scope {
        STATIC, INSTANCE;
        
        public String toString() {
            return (this == INSTANCE)
                    ? null
                    : name().toLowerCase();
        }
        
    }
    
    public static enum Modifiability {
        FINAL, MODIFIABLE;
        
        public String toString() {
            return (this == MODIFIABLE)
                    ? null
                    : name().toLowerCase();
        }
        
    }
    
    public static interface GenElement extends IRequireTypes {
        public Accessibility getAccessibility();
        public Modifiability getModifiability();
        public Scope         getScope();
        public Type          getType();
        public String        getName();
        public <E extends GenElement> E withAccessibility(Accessibility accessibility);
        public <E extends GenElement> E withModifiability(Modifiability modifiability);
        public <E extends GenElement> E withScope(Scope scope);
        public <E extends GenElement> E withType(Type type);
        public <E extends GenElement> E withName(String name);
        
        @Override
        public default Stream<Type> getRequiredTypes() {
            return Stream.of(getType());
        }
        
    }
    
    @Value
    @Wither
    @EqualsAndHashCode(callSuper=false)
    public static class GenMethod implements GenElement {
        
        private Accessibility  accessibility;
        private Modifiability  modifiability;
        private Scope          scope;
        private Type           type;
        private String         name;
        private List<GenParam> params;
        private ILines         body;
        
        @Override
        public Stream<Type> getRequiredTypes() {
            Set<Type> types = new HashSet<>();
            types.add(type);
            GenElement.super
                .getRequiredTypes()
                .forEach(types::add);
            for (GenParam param : params) {
                Type paramType = param.getType();
                if (types.contains(paramType))
                    continue;
                types.add(paramType);
                param
                    .getRequiredTypes()
                    .forEach(types::add);
            }
            return types.stream();
        }
        
        public ILines toDefinition() {
            val paramDefs = params.stream().map(GenParam::toDefinition).collect(joining(", "));
            val definition = Stream.of(accessibility, modifiability, scope, type.getSimpleName(), name + "(" + paramDefs + ")", "{")
                            .map(toStr())
                            .filter(Objects::nonNull)
                            .collect(joining(" "));
            return ()->asList(
                            line(definition),
                            indent(body),
                            line("}")
                        )
                        .stream()
                        .flatMap(ILines::lines)
                        .filter(Objects::nonNull);
        }
    }
    
    @Value
    @Wither
    @EqualsAndHashCode(callSuper=false)
    public static class GenField implements GenElement {
        private Accessibility accessibility;
        private Modifiability modifiability;
        private Scope         scope;
        private String        name;
        private Type          type;
        private String        defaultValue;
//        private List<Type>    additionalTypes;    // TODO - Take care of this.
        
        public ILines toDefinition() {
            val def = asList(accessibility, modifiability, scope, type.getSimpleName(), name).stream()
                    .map(toStr())
                    .filter(Objects::nonNull)
                    .collect(joining(" "));
            val value = (defaultValue != null) ? " = " + defaultValue : "";
            return ()->Stream.of(def + value + ";");
        }
    }
    
    @Value
    @Wither
    @EqualsAndHashCode(callSuper=false)
    public static class GenConstructor implements IRequireTypes {
        private Accessibility  accessibility;
        private String         name;
        private List<GenParam> params;
        private ILines         body;
        
        public ILines toDefinition() {
            val paramDefs = params.stream().map(GenParam::toDefinition).collect(joining(", "));
            val definition = Stream.of(accessibility, name + "(" + paramDefs + ")", "{")
                            .map(toStr())
                            .filter(Objects::nonNull)
                            .collect(joining(" "));
            return ()->asList(
                            line(definition),
                            indent(body),
                            line("}")
                        )
                        .stream()
                        .flatMap(ILines::lines)
                        .filter(Objects::nonNull);
        }
        
        @Override
        public Stream<Type> getRequiredTypes() {
            Set<Type> types = new HashSet<>();
            for (val param : params) {
                val paramType = param.getType();
                if (types.contains(paramType))
                    continue;
                types.add(paramType);
                param
                    .getRequiredTypes()
                    .forEach(types::add);
            }
            return types.stream();
        }
    }
    
    @Value
    @Builder
    @Wither
    public static class GenParam implements IRequireTypes {
        private String name;
        private Type   type;
        
        @Override
        public Stream<Type> getRequiredTypes() {
            return Stream.of(type);
        }
        public String toDefinition() {
            return type.simpleName + " " + name;
        }
    }

    private static ILines generateLensClass(Spec sourceSpec) {
        val recordClassName = sourceSpec.getTargetClassName();
        val lensClassName   = recordClassName + "Lens";
        val lensFirstLine   = asList(
                "public",
                "static",
                "class",
                lensClassName + "<HOST>",
                "extends",
                "ObjectLensImpl<HOST, " + recordClassName + ">",
                "{"
            ).stream().collect(joining(" "));
        val recordLastLine = "}";
        
        Stream<GenField> lensFields  = sourceSpec.getGetters().stream().map(getter -> getterToLensField(getter, recordClassName, sourceSpec));
        
        
        val consParams = asList(new GenParam("spec", new Type("LensSpec<HOST, " + recordClassName + ">", "nawaman.functionalj.lens")));
        val consBody   = "super(spec);"; // This ignore the id for now.
        val cons       = new GenConstructor(PUBLIC, lensClassName, consParams, line(consBody));
        
        Stream<ILines> lensFieldDefs = lensFields           .map(field->field.toDefinition());
        Stream<ILines> lensConstDefs = asList(cons).stream().map(GenConstructor::toDefinition);
        
        val lensLines = (Stream<String>)asList(
                line(lensFirstLine),
                line(),
                indent(lensFieldDefs),
                line(),
                indent(lensConstDefs),
                line(),
//                indent(methodDefs),
//                line(),
                line(recordLastLine)
            )
            .stream()
            .flatMap(ILines::lines)
            .filter(Objects::nonNull);
        return ()->lensLines;
    }
    
    private static GenField getterToLensField(Getter getter, String recordClassName, Spec sourceSpec) {
        val recName = recordClassName;
        val name    = getter.getName();
        val type    = getter.getType();
        val t       = new Type(type.getSimpleName() + "Lens<HOST>", sourceSpec.getPackageName());
        val withName = withMethodName(getter);
        val spec    = "spec->()->spec"; // If Custom lens -> spec->new Brand.BrandLens<>(spec)
        val value   = format("createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", recName, name, withName, spec);
        val field = new GenField(PUBLIC, FINAL, INSTANCE, name, t, value);
        return field;
    }
    
    public static Stream<String> generateRecordClass(RecordSpec recordSpec) {
        val extendedList    = recordSpec.extendeds   .stream().map(Type::getSimpleName).collect(joining(",")).trim();
        val implementedList = recordSpec.implementeds.stream().map(Type::getSimpleName).collect(joining(",")).trim();
        val recordFirstLine = asList(
                    "public",
                    "class",
                    recordSpec.getClassName(),
                    extendedList.isEmpty()    ? null : "extends "    + extendedList,
                    implementedList.isEmpty() ? null : "implements " + implementedList,
                    "{"
                ).stream()
                .filter(Objects::nonNull)
                .collect(joining(" "));
        val recordLastLine = "}";
        
        val fieldDefs       = recordSpec.getFields()      .stream().map(GenField      ::toDefinition);
        val constructorDefs = recordSpec.getConstructors().stream().map(GenConstructor::toDefinition);
        val methodDefs      = recordSpec.getMethods()     .stream().map(GenMethod     ::toDefinition);
        val moreDefs        = recordSpec.getMores()       .stream();
        
        val types = new HashSet<Type>();
        recordSpec.getFields()      .stream().flatMap(GenField      ::getRequiredTypes).forEach(types::add);
        recordSpec.getMethods()     .stream().flatMap(GenMethod     ::getRequiredTypes).forEach(types::add);
        recordSpec.getConstructors().stream().flatMap(GenConstructor::getRequiredTypes).forEach(types::add);
        recordSpec.extendeds   .forEach(types::add);
        recordSpec.implementeds.forEach(types::add);
        
        types.remove(new Type(recordSpec.getClassName(), recordSpec.getPackageName()));
        
        val knownLensClasses = new HashMap<String, String>();
        knownLensClasses.put(String.class.getCanonicalName(), StringLens.class.getCanonicalName());
        
        val implicitImports = asList(
                "java.lang.String"
        );
        val alwaysImports = asList(
                IPostConstruct.class.getCanonicalName()
        );
        val lensImport = types.stream()
                .map(Type::fullName)
                .map(knownLensClasses::get)
                .filter(Objects::nonNull);
        
        val imports = concat(concat(
                    alwaysImports.stream(),
                    lensImport),
                    types.stream()
                        .map(Type::fullName)
                        .filter(type -> !implicitImports.contains(type))
                 )
                .map(wrapWith("import ", ";"));
        
        val recordLines = asList(
                    line("package " + recordSpec.getPackageName() + ";"),
                    line(),
                    ILines.of(()->imports),
                    line(),
                    line(recordFirstLine),
                    line(),
                    indent(fieldDefs),
                    line(),
                    indent(constructorDefs),
                    line(),
                    indent(methodDefs),
                    line(),
                    indent(moreDefs),
                    line(),
                    line(recordLastLine)
                )
                .stream()
                .flatMap(ILines::lines)
                .filter(Objects::nonNull);
        return recordLines;
    }
    
    public static RecordSpec generateRecordSpec(Spec sourceSpec) {
        val extendeds    = new ArrayList<Type>();
        val implementeds = new ArrayList<Type>();
        
        if (sourceSpec.isClass())
             extendeds   .add(sourceSpec.toType());
        else implementeds.add(sourceSpec.toType());
        
        val withMethodName = Func1.of(DataObjectGenerator::withMethodName);
        // TODO - Improve this to make it more humand-made ... we should remove the long class name and use simple.
        val ipostConstruct = IPostConstruct.class.getSimpleName();
        val postConstructMethod = new GenMethod(
                PRIVATE, MODIFIABLE, INSTANCE,
                sourceSpec.getTargetType(), "postProcess",
                asList(new GenParam("object", sourceSpec.getTargetType())),
                line("if (object instanceof " + ipostConstruct + ")",
                     "    ((" + ipostConstruct + ")object).postConstruct();",
                     "return object;"));
        
        val getterFields  = sourceSpec.getGetters().stream().map(getter -> getterToField(getter));
        val getterMethods = sourceSpec.getGetters().stream().map(getter -> getterToGetterMethod(getter));
        val witherMethods = sourceSpec.getGetters().stream().map(getter -> getterToWitherMethod(sourceSpec, withMethodName, getter));
        
        val noArgsConstructor = Func1.of((Spec spec) ->{
            val name = spec.getTargetClassName();
            val body = "this(" +
                        range(0, spec.getGetters().size())
                            .mapToObj(i -> "null")
                            .collect(joining(", ")) +
                       ");";
            return new GenConstructor(PUBLIC, name, emptyList(), line(body));
        });
        val allArgsConstructor = Func1.of((Spec spec) ->{
            val name = spec.getTargetClassName();
            List<GenParam> params = spec.getGetters().stream()
                    .map(getter -> {
                        val paramName = getter.getName();
                        val paramType = getter.getType();
                        return new GenParam(paramName, paramType);
                    })
                    .collect(toList());
            val body = spec.getGetters().stream()
                    .map(Getter::getName)
                    .map(format1With("this.%1$s = %1$s;"));
            return new GenConstructor(PUBLIC, name, params, ILines.of(()->body));
        });
        
        List<GenField> fields = getterFields.collect(toList());
        List<Stream<GenMethod>> flatMap = Arrays.<Stream<GenMethod>>asList(
                    getterMethods,
                    witherMethods,
                    Stream.of(postConstructMethod)
                 );
        List<GenMethod> methods = flatMap.stream().flatMap(ms -> ms).collect(toList());
        List<GenConstructor> constructors = Arrays.<GenConstructor>asList(
                    (GenConstructor)noArgsConstructor.apply(sourceSpec),
                    (GenConstructor)allArgsConstructor.apply(sourceSpec)
                );
        
        val lensLines = asList(generateLensClass(sourceSpec));
                
        RecordSpec recordSpec = new RecordSpec(
                sourceSpec.getTargetClassName(),
                sourceSpec.getTargetPackageName(),
                extendeds, implementeds,
                constructors, fields, methods, lensLines);
        return recordSpec;
    }

    private static GenMethod getterToWitherMethod(Spec sourceSpec,
            final nawaman.functionalj.functions.Func1<nawaman.functionalj.annotations.processor.DataObjectGenerator.Getter, java.lang.String> withMethodName,
            Getter getter) {
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType()));
        val paramCall = sourceSpec.getGetters().stream().map(Getter::getName).collect(joining(", "));
        val returnLine = "return postProcess(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
        return new GenMethod(PUBLIC, MODIFIABLE, INSTANCE, type, name, params, line(returnLine));
    }

    private static GenMethod getterToGetterMethod(Getter getter) {
        val name = getter.getName();
        val type = getter.getType();
        val params = new ArrayList<GenParam>();
        val body   = "return " + getter.getName() + ";";
        val method = new GenMethod(PUBLIC, MODIFIABLE, INSTANCE, type, name, params, line(body));
        return method;
    }

    private static GenField getterToField(Getter getter) {
        // It should be good to convert this to tuple2 and apply to the method.
        val name  = getter.getName();
        val type  = getter.getType();
        val field = new GenField(PRIVATE, FINAL, INSTANCE, name, type, null);
        return field;
    }
    
    private static String withMethodName(Getter getter) {
        val name = getter.getName();
        return "with" + name.substring(0,1).toUpperCase() + name.substring(1);
    }
    
}
