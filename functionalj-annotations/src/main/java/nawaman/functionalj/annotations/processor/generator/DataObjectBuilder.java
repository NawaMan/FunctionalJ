package nawaman.functionalj.annotations.processor.generator;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.annotations.processor.generator.Accessibility.PRIVATE;
import static nawaman.functionalj.annotations.processor.generator.Accessibility.PUBLIC;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.ILines.linesOf;
import static nawaman.functionalj.annotations.processor.generator.Modifiability.FINAL;
import static nawaman.functionalj.annotations.processor.generator.Modifiability.MODIFIABLE;
import static nawaman.functionalj.annotations.processor.generator.Scope.INSTANCE;
import static nawaman.functionalj.annotations.processor.generator.Scope.STATIC;
import static nawaman.functionalj.functions.StringFunctions.format1With;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import static java.util.Collections.emptyList;

import lombok.val;
import nawaman.functionalj.annotations.processor.generator.model.ClassSpec;
import nawaman.functionalj.functions.Func1;
import nawaman.functionalj.lens.IPostConstruct;
import nawaman.functionalj.lens.LensSpec;
import nawaman.functionalj.lens.ObjectLensImpl;

public class DataObjectBuilder {
    
    public static DataObjectSpec generateDataObjSpec(SourceSpec sourceSpec) {
        val extendeds    = new ArrayList<Type>();
        val implementeds = new ArrayList<Type>();
        
        if (sourceSpec.isClass())
             extendeds   .add(sourceSpec.toType());
        else implementeds.add(sourceSpec.toType());
        
        val withMethodName = Func1.of(Utils::withMethodName);
        val ipostConstruct = IPostConstruct.class.getSimpleName();
        val postConstructMethod = new GenMethod(
                PRIVATE, MODIFIABLE, INSTANCE,
                sourceSpec.getTargetType(), "postProcess",
                asList(new GenParam("object", sourceSpec.getTargetType())),
                line(String.format(
                     "if (object instanceof %1$s)",
                     "    ((%1$s)object).postConstruct();",
                     "return object;", ipostConstruct)));
        
        val getters = sourceSpec.getGetters();
        val getterFields  = getters.stream().map(getter -> getterToField(getter));
        val getterMethods = getters.stream().map(getter -> getterToGetterMethod(getter));
        val witherMethods = getters.stream().map(getter -> getterToWitherMethod(sourceSpec, withMethodName, getter));
        
        val noArgsConstructor = Func1.of((SourceSpec spec) ->{
            val name        = spec.getTargetClassName();
            val paramString = spec.getGetters().stream()
                    .map(getter -> getter.getType().defaultValue())
                    .map(String::valueOf)
                    .collect(joining(", "));
            val body = "this(" + paramString + ");";
            return new GenConstructor(PUBLIC, name, emptyList(), line(body));
        });
        val allArgsConstructor = Func1.of((SourceSpec spec) ->{
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
            return new GenConstructor(PUBLIC, name, params, linesOf(()->body));
        });
        
        val dataObjClassName = sourceSpec.getTargetClassName();
        val lensType         = sourceSpec.getTargetType().lensType().withGeneric(dataObjClassName);
        val defaultValue     = String.format("new %1$s<>(%2$s.of(%3$s.class))", lensType.simpleName(), LensSpec.class.getSimpleName(), dataObjClassName);
        val theField         = new GenField(PUBLIC, FINAL, STATIC, "the"+dataObjClassName, lensType, defaultValue);
        
        List<GenField> fields = asList(
                    Stream.of(theField),
                    getterFields
                ).stream()
                .filter(Objects::nonNull)
                .flatMap(themAll())
                .collect(toList());
        List<Stream<GenMethod>> flatMap = Arrays.<Stream<GenMethod>>asList(
                    getterMethods,
                    witherMethods,
                    Stream.of(postConstructMethod)
                 );
        List<GenMethod> methods = flatMap.stream().flatMap(ms -> ms).collect(toList());
        List<GenConstructor> constructors = new ArrayList<>();
        if (sourceSpec.getConfigures().noArgConstructor)
            constructors.add((GenConstructor)noArgsConstructor.apply(sourceSpec));
        constructors.add((GenConstructor)allArgsConstructor.apply(sourceSpec));
        
        val innerClasses = asList(generateLensClass(sourceSpec));
        
        DataObjectSpec dataObjSpec = new DataObjectSpec(
                sourceSpec.getTargetClassName(),
                sourceSpec.getTargetPackageName(),
                sourceSpec.getSpecClassName(),
                sourceSpec.getPackageName(),
                extendeds, implementeds,
                constructors, fields, methods, innerClasses, emptyList());
        return dataObjSpec;
    }
    
    private static GenField getterToField(Getter getter) {
        // It should be good to convert this to tuple2 and apply to the method.
        val name  = getter.getName();
        val type  = getter.getType();
        val field = new GenField(PRIVATE, FINAL, INSTANCE, name, type, null);
        return field;
    }
    
    private static GenMethod getterToWitherMethod(SourceSpec sourceSpec,
            final nawaman.functionalj.functions.Func1<Getter, java.lang.String> withMethodName,
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
    
    // TODO - This should generate a class data not lines.
    public static ClassSpec generateLensClass(SourceSpec sourceSpec) {
        val dataObjClassName = sourceSpec.getTargetClassName();
        val lensClass = new Type.TypeBuilder()
                .encloseName(dataObjClassName)
                .simpleName(dataObjClassName + "Lens")
                .packageName(sourceSpec.getPackageName())
                .generic("HOST")
                .build();
        val superType = Type.of(ObjectLensImpl.class);
        
        Stream<GenField> lensFields  = sourceSpec.getGetters().stream().map(getter -> getterToLensField(getter, dataObjClassName, sourceSpec));
        
        val lensSpecType = new Type.TypeBuilder().packageName("nawaman.functionalj.lens").simpleName("LensSpec").generic("HOST, " + dataObjClassName).build();
        val consParams   = asList(new GenParam("spec", lensSpecType));
        val consBody     = "super(spec);"; // This ignore the id for now.
        val constructors = new GenConstructor(PUBLIC, lensClass.simpleName(), consParams, line(consBody));
        
        val lensClassSpec = new ClassSpec(
                PUBLIC, STATIC, MODIFIABLE, lensClass, "HOST",
                asList(superType.withGeneric("HOST, " + dataObjClassName)),
                emptyList(),
                asList(constructors),
                lensFields.collect(toList()),
                emptyList(),
                emptyList(),
                emptyList());
        return lensClassSpec;
    }
    
    private static GenField getterToLensField(Getter getter, String dataObjectClassName, SourceSpec sourceSpec) {
        val dataObjName = dataObjectClassName;
        val name        = getter.getName();
        val type        = getter.getType().declaredType();
        val lensType    = type.lensType().withGeneric("HOST");
        val withName    = Utils.withMethodName(getter);
        val spec        = "spec->()->spec"; // If Custom lens -> spec->new Brand.BrandLens<>(spec)
        val value       = format("createSubLens(%1$s::%2$s, %1$s::%3$s, %4$s)", dataObjName, name, withName, spec);
        val field       = new GenField(PUBLIC, FINAL, INSTANCE, name, lensType, value);
        return field;
    }
    
}
