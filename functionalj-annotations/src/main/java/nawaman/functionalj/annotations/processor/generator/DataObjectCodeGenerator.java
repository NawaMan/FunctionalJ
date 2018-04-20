package nawaman.functionalj.annotations.processor.generator;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Stream.concat;
import static nawaman.functionalj.annotations.processor.generator.Accessibility.PRIVATE;
import static nawaman.functionalj.annotations.processor.generator.Accessibility.PUBLIC;
import static nawaman.functionalj.annotations.processor.generator.ILines.indent;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.Modifiability.FINAL;
import static nawaman.functionalj.annotations.processor.generator.Modifiability.MODIFIABLE;
import static nawaman.functionalj.annotations.processor.generator.Scope.INSTANCE;
import static nawaman.functionalj.functions.StringFunctions.wrapWith;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import lombok.val;
import nawaman.functionalj.lens.IPostConstruct;
import nawaman.functionalj.lens.LensSpec;
import nawaman.functionalj.lens.ObjectLensImpl;

@SuppressWarnings("javadoc")
public class DataObjectCodeGenerator {
    
    public static Stream<String> generateDataObjectClass(DataObjectSpec dataObjSpec) {
        val extendedList    = dataObjSpec.getExtendeds()   .stream().map(Type::getSimpleName).collect(joining(",")).trim();
        val implementedList = dataObjSpec.getImplementeds().stream().map(Type::getSimpleName).collect(joining(",")).trim();
        val dataObjFirstLine = asList(
                    "public",
                    "class",
                    dataObjSpec.getClassName(),
                    extendedList.isEmpty()    ? null : "extends "    + extendedList,
                    implementedList.isEmpty() ? null : "implements " + implementedList,
                    "{"
                ).stream()
                .filter(Objects::nonNull)
                .collect(joining(" "));
        val dataObjLastLine = "}";
        
        val fieldDefs       = dataObjSpec.getFields()      .stream().map(GenField      ::toDefinition);
        val constructorDefs = dataObjSpec.getConstructors().stream().map(GenConstructor::toDefinition);
        val methodDefs      = dataObjSpec.getMethods()     .stream().map(GenMethod     ::toDefinition);
        val moreDefs        = dataObjSpec.getMores()       .stream();
        
        val types = new HashSet<Type>();
        dataObjSpec.getFields()      .stream().flatMap(GenField      ::getRequiredTypes).forEach(types::add);
        dataObjSpec.getMethods()     .stream().flatMap(GenMethod     ::getRequiredTypes).forEach(types::add);
        dataObjSpec.getConstructors().stream().flatMap(GenConstructor::getRequiredTypes).forEach(types::add);
        dataObjSpec.getExtendeds()   .forEach(types::add);
        dataObjSpec.getImplementeds().forEach(types::add);
        
        types.remove(new Type(dataObjSpec.getClassName(), dataObjSpec.getPackageName()));
        
        val implicitImports = asList(
                "java.lang.String"
        );
        val alwaysImports = asList(
                IPostConstruct.class.getCanonicalName(),
                ObjectLensImpl.class.getCanonicalName(),
                LensSpec.class.getCanonicalName()
        );
        val lensImport = types.stream()
                .map(type -> type.getLensType(type))
                .filter(Objects::nonNull)
                .map(Type::fullName);
        
        val thisPackage = dataObjSpec.getPackageName();
        val lensClass   = dataObjSpec.getPackageName() + "." + dataObjSpec.getClassName() + "." + dataObjSpec.getClassName() + "Lens";
        val superClass  = dataObjSpec.getSourcePackageName() + "." + dataObjSpec.getSourceClassName();
        val samePackage = (Predicate<String>)((String name) -> {
            return name.startsWith(thisPackage)
                && name.substring(thisPackage.length() + 1).matches("^[^.]*$");
        });
        val isLensClass = (Predicate<String>)((String name) -> {
            return name.equals(lensClass);
        });
        
        val isSuperClass = (Predicate<String>)((String name) -> {
            return name.equals(superClass);
        });
        val imports = concat(concat(
                    alwaysImports.stream(),
                    lensImport),
                    types.stream()
                        .map(Type::fullName)
                        .filter(type -> !".int".equals(type))
                        .filter(type -> !".boolean".equals(type))
                        .filter(type -> !implicitImports.contains(type))
                 )
                .map(name -> name.replaceAll("<[ .,_$a-zA-Z0-9]*>$", ""))
                .filter(samePackage.negate())
                .filter(isLensClass.negate())
                .filter(isSuperClass.negate())
                .sorted()
                .distinct()
                .map(wrapWith("import ", ";"));
        
        val dataObjLines = asList(
                    line("package " + dataObjSpec.getPackageName() + ";"),
                    line(),
                    ILines.of(()->imports),
                    line(),
                    line(dataObjFirstLine),
                    indent(),
                    indent(fieldDefs),
                    indent(),
                    indent(constructorDefs),
                    indent(),
                    indent(methodDefs),
                    indent(),
                    indent(moreDefs),
                    indent(),
                    line(dataObjLastLine)
                )
                .stream()
                .flatMap(ILines::lines)
                .filter(Objects::nonNull);
        return dataObjLines;
    }
    
}
