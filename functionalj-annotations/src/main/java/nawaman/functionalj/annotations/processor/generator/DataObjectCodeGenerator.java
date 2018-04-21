package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.annotations.processor.generator.ILines.indent;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.ILines.linesOf;
import static nawaman.functionalj.functions.StringFunctions.wrapWith;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import lombok.val;
import nawaman.functionalj.annotations.processor.generator.model.ClassSpec;
import nawaman.functionalj.lens.IPostConstruct;
import nawaman.functionalj.lens.LensSpec;
import nawaman.functionalj.lens.ObjectLensImpl;

@SuppressWarnings("javadoc")
public class DataObjectCodeGenerator {
    
    public static Stream<String> generateDataObjectClass(DataObjectSpec dataObjSpec) {
        val importList = generateImportList(dataObjSpec);
        val imports    = importList.map(wrapWith("import ", ";"));
        
        val dataObjLines = asList(
                    line("package " + dataObjSpec.packageName() + ";"),
                    line(),
                    linesOf(()->imports),
                    line(),
                    linesOf(dataObjSpec.getClassSpec().toDefinition())
                )
                .stream()
                .flatMap(ILines::lines)
                .filter(Objects::nonNull);
        return dataObjLines;
    }
    
    private static Stream<String> generateImportList(DataObjectSpec dataObjSpec) {
        // TODO - Think about moving it some to ClassSpec.
        val types = new HashSet<Type>();
        dataObjSpec.fields()      .stream().flatMap(GenField      ::getRequiredTypes).forEach(types::add);
        dataObjSpec.methods()     .stream().flatMap(GenMethod     ::getRequiredTypes).forEach(types::add);
        dataObjSpec.constructors().stream().flatMap(GenConstructor::getRequiredTypes).forEach(types::add);
        dataObjSpec.extendeds()   .forEach(types::add);
        dataObjSpec.implementeds().forEach(types::add);
        
        types.remove(dataObjSpec.type());
        
        val implicitImports = asList(
                "java.lang.String",
                "java.lang.Integer",
                "java.lang.Boolean",
                ".int",
                ".boolean"
        );
        val alwaysImports = asList(
                Type.of(IPostConstruct.class),
                Type.of(ObjectLensImpl.class),
                Type.of(LensSpec.class)
        );
        val lensImport = types.stream()
                .filter(Objects::nonNull)
                .collect(toList());
        
        val thisPackage = dataObjSpec.packageName();
        val lensClass   = dataObjSpec.type().lensType().fullName();
        val superClass  = dataObjSpec.getSourcePackageName() + "." + dataObjSpec.getSourceClassName();
        val isLensClass  = (Predicate<String>)((String name) -> name.equals(lensClass));
        val isSuperClass = (Predicate<String>)((String name) -> name.equals(superClass));
        
        val importList = asList(
                    alwaysImports.stream(),
                    lensImport.stream(),
                    types.stream(),
                    dataObjSpec.innerClasses().stream()
                        .map(ClassSpec::getRequiredTypes)
                        .flatMap(themAll())
                ).stream()
                .flatMap(themAll())
                .filter(type->!thisPackage.equals(type.packageName()))
                .map(Type::declaredType)
                .map(Type::fullName)
                .map(String.class::cast)
                .filter(type -> !implicitImports.contains(type))
                .filter(isLensClass.negate())
                .filter(isSuperClass.negate())
                .sorted()
                .distinct();
        return importList;
    }
    
}
