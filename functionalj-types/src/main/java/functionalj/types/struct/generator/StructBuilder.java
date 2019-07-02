// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.model.Accessibility.PRIVATE;
import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.FINAL;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static functionalj.types.struct.generator.model.Scope.STATIC;
import static functionalj.types.struct.generator.utils.listOf;
import static functionalj.types.struct.generator.utils.themAll;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.types.DefaultValue;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.choice.generator.Utils;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

/**
 * Builder for a data object.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class StructBuilder {
    
    private SourceSpec sourceSpec;
    
    /**
     * Constructor for the data object builder.
     * 
     * @param sourceSpec  the source spec.
     */
    public StructBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the data object.
     * 
     * @return  the data object.
     **/
    public StructSpec build() {
        // TODO - Find sometime to clean this up - it is a mess.
        val extendeds    = new ArrayList<Type>();
        val implementeds = new ArrayList<Type>();
        
        if (sourceSpec.getConfigures().coupleWithDefinition) {
            if (sourceSpec.getIsClass() != null) {
                if (sourceSpec.isClass())
                     extendeds   .add(sourceSpec.toType());
                else implementeds.add(sourceSpec.toType());
            }
        }
        
        val istruct = Type.of(IStruct.class);
        implementeds.add(istruct);
        
        val targetType = new Type(sourceSpec.getTargetClassName(), sourceSpec.getTargetPackageName());
        val pipeable   = Core.Pipeable.type().withGenerics(asList(targetType));
        implementeds.add(pipeable);
        
        val pipeMethod = new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, targetType, "__data", emptyList(), ILines.line("return this;"), emptyList(), asList(Type.of(Exception.class)), false);
        
        val withMethodName = (Function<Getter, String>)(utils::withMethodName);
        val getters        = sourceSpec.getGetters();
        val getterFields   = getters.stream().map    (getter -> getterToField(sourceSpec, getter));
        val getterMethods  = getters.stream().map    (getter -> getterToGetterMethod(getter));
        val witherMethods  = getters.stream().flatMap(getter -> getterToWitherMethods(sourceSpec, withMethodName, getter));
        
        GenField theField = null;
        GenClass lensClass = null;
        if (sourceSpec.getConfigures().generateLensClass) {
            val lensClassBuilder = new LensClassBuilder(sourceSpec);
            lensClass            = lensClassBuilder.build();
            theField             = lensClassBuilder.generateTheLensField();
        }
        GenClass builderClass = null;
        if (sourceSpec.getConfigures().generateBuilderClass) {
            builderClass = new BuilderGenerator(sourceSpec).build();
        }
        
        val specField = generateSpecField();
        
        GenMethod toString = null;
        
        val toStringTemplate = sourceSpec.getConfigures().toStringTemplate;
        if (toStringTemplate != null) {
            String toStringBody = null;
            if (!toStringTemplate.isEmpty()) {
                toStringBody = "return functionalj.functions.StrFuncs.template(" + Utils.toStringLiteral(toStringTemplate) + ",toMap()::get);";
            } else {
                toStringBody =
                        "return \"" + sourceSpec.getTargetClassName() + "[\" + " +
                        getters.stream()
                        .map(g -> "\""+ g.getName() + ": \" + " + g.getName() + "()")
                        .collect(joining(" + \", \" + ")) +
                        " + \"]\";";
            }
            toString =  new GenMethod(
                    Accessibility.PUBLIC,
                    Scope.INSTANCE,
                    Modifiability.MODIFIABLE,
                    Type.STRING,
                    "toString",
                    Collections.emptyList(),
                    line(toStringBody));
        }
        
        val hashCode = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.INT,
                "hashCode",
                Collections.emptyList(),
                line("return toString().hashCode();"));
        val equals = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.BOOL,
                "equals",
                asList(new GenParam("another", Type.of(Object.class))),
                line("return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));"));
                
        val fields = listOf(
                    Stream.of(theField),
                    getterFields,
                    specField
                 );
        
        val fromMapBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(g -> "            (" + g.getType().simpleNameWithGeneric() + ")$utils.fromMapValue(map.get(\"" + g.getName() + "\"), $schema.get(\"" + g.getName() + "\"))")
                .collect(Collectors.joining(",\n"))
                .split("\n"));
        val getterHasGeneric
                = sourceSpec
                .getGetters().stream()
                .anyMatch(g -> 
                        !g
                        .getType()
                        .generics()
                        .isEmpty());
        val fromMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                sourceSpec.getTargetType(),
                "fromMap",
                asList(new GenParam("map", Type.MAP.withGenerics(asList(Type.STRING, Type.OBJECT)))),
                ILines.linesOf(
                    line("Map<String, Getter> $schema = getStructSchema();"),
                    getterHasGeneric ? line("@SuppressWarnings(\"unchecked\")") : line(""),
                    line(sourceSpec.getTargetType().simpleName() + " obj = new " + sourceSpec.getTargetType().simpleName() + "("),
                    fromMapBody,
                    line("        );"),
                    line("return obj;")
                ));
        
        val toMapBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(g -> "map.put(\"" + g.getName() + "\", " + IStruct.class.getCanonicalName() + ".$utils.toMapValueObject(" + g.getName() + "));")
                .collect(Collectors.toList()));
        val toMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(Type.STRING, Type.OBJECT)),
                "__toMap",
                emptyList(),
                ILines.linesOf(
                    line("Map<String, Object> map = new HashMap<>();"),
                    toMapBody,
                    line("return map;")
                ),
                asList(Type.of(Map.class), Type.of(HashMap.class)),
                emptyList(),
                false);
        
        val getStructSchemaBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(g -> "map.put(\"" + g.getName() + "\", " + g.toCode() + ");")
                .collect(Collectors.toList()));
        val getStructSchema = new GenMethod(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(Type.STRING, Type.of(Getter.class))),
                "getStructSchema",
                emptyList(),
                ILines.linesOf(
                    line("Map<String, Getter> map = new HashMap<>();"),
                    getStructSchemaBody,
                    line("return map;")
                ),
                asList(Type.of(Map.class), Type.of(HashMap.class), Type.of(Type.class), Type.of(Getter.class)),
                emptyList(),
                false);
        
        val getSchema = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(Type.STRING, Type.of(Getter.class))),
                "__getSchema",
                emptyList(),
                ILines.linesOf(line("return getStructSchema();")),
                asList(Type.of(Map.class), Type.of(HashMap.class), Type.of(Type.class), Type.of(Getter.class)),
                emptyList(),
                false);
        
        val flatMap = Arrays.<Stream<GenMethod>>asList(
                    Stream.of(pipeMethod),
                    getterMethods,
                    witherMethods,
                    Stream.of(fromMap, toMap, getSchema, getStructSchema),
                    Stream.of(toString, hashCode, equals).filter(Objects::nonNull)
                 );
        val methods = flatMap.stream().flatMap(themAll()).collect(toList());
        
        val constructors = listOf(
                    noArgConstructor(),
                    requiredOnlyConstructor(),
                    allArgConstructor()
                );
        
        val innerClasses = listOf(
                    lensClass,
                    builderClass
                );
        
        val dataObjSpec = new StructSpec(
                sourceSpec.getTargetClassName(),
                sourceSpec.getTargetPackageName(),
                sourceSpec.getSpecName(),
                sourceSpec.getPackageName(),
                extendeds, implementeds,
                constructors, fields, methods, innerClasses, emptyList());
        return dataObjSpec;
    }
    
    private Stream<GenField> generateSpecField() {
        if (sourceSpec.hasSpecField()) {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            Stream<GenField> emptyStream = (Stream<GenField>)(Stream)Stream.empty();
            return emptyStream;
        }
        
        val genField = new GenField(PUBLIC, FINAL, STATIC,
                        sourceSpec.getSpecObjName(),
                        Type.of(SourceSpec.class),
                        sourceSpec.toCode());
        return Stream.of(genField);
    }
    
    private GenConstructor noArgConstructor() {
        if (!sourceSpec.getConfigures().generateNoArgConstructor)
            return null;
        
        val name        = sourceSpec.getTargetClassName();
        val paramString = sourceSpec.getGetters().stream()
                .map    (getter -> getter.getType().defaultValue())
                .map    (String::valueOf)
                .collect(joining(", "));
        val body = "this(" + paramString + ");";
        return new GenConstructor(PUBLIC, name, emptyList(), line(body));
    }
    
    private GenConstructor requiredOnlyConstructor() {
        if (!sourceSpec.getConfigures().generateRequiredOnlyConstructor)
            return null;
        
        if (sourceSpec.getGetters().stream().allMatch(Getter::isRequired))
            return null;
        if (sourceSpec.getConfigures().generateNoArgConstructor
         && sourceSpec.getGetters().stream().noneMatch(Getter::isRequired))
            return null;
        
        val name   = sourceSpec.getTargetClassName();
        val params = sourceSpec.getGetters().stream()
                        .filter(getter -> getter.isRequired())
                        .map   (this::getterToGenParam)
                        .collect(toList());
        
        val pkgName      = sourceSpec.getPackageName();
        val eclName      = sourceSpec.getEncloseName();
        val valName      = sourceSpec.getValidatorName();
        val getterParams = sourceSpec.getGetters().stream().map(getter -> getter.getDefaultValueCode(getter.getName())).collect(joining(","));
        
        val assignGetters  = sourceSpec.getGetters().stream().map(getter -> "this." + getter.getName() + " = " + getter.getDefaultValueCode(getter.getName()) + ";");
        val validate       = (Stream<String>)((valName == null) ? null : Stream.of("functionalj.result.ValidationException.ensure(" + pkgName + "." + eclName + "." + valName + "(" + getterParams + "), this);"));
        val ipostConstruct = Type.of(IPostConstruct.class).simpleName();
        val postConstruct  = Stream.of("if (this instanceof " + ipostConstruct + ") ((" + ipostConstruct + ")this).postConstruct();");
        val assignments
                = Stream.of(
                        assignGetters,
                        validate,
                        postConstruct)
                .filter(Objects::nonNull)
                .flatMap(Function.identity())
                .collect(toList());
        return new GenConstructor(PUBLIC, name, params, ILines.line(assignments));
    }
    private GenConstructor allArgConstructor() {
        val allArgsConstructor = (BiFunction<SourceSpec, Accessibility, GenConstructor>)((spec, acc) ->{
            val name = spec.getTargetClassName();
            List<GenParam> params = spec.getGetters().stream()
                        .map(this::getterToGenParam)
                        .collect(toList());
            
            val pkgName         = sourceSpec.getPackageName();
            val eclName         = sourceSpec.getEncloseName();
            val valName         = sourceSpec.getValidatorName();
            String getterParams = sourceSpec.getGetters().stream().map(getter -> getter.getName()).collect(Collectors.joining(","));
            
            val assignGetters  = spec.getGetters().stream().map(this::initGetterField);
            val validate       = (Stream<String>)((valName == null) ? null : Stream.of("functionalj.result.ValidationException.ensure(" + pkgName + "." + eclName + "." + valName + "(" + getterParams + "), this);"));
            val ipostConstruct = Type.of(IPostConstruct.class).simpleName();
            val body = Stream.of(
                            assignGetters,
                            validate,
                            Stream.of("if (this instanceof " + ipostConstruct + ") ((" + ipostConstruct + ")this).postConstruct();"))
                    .flatMap(Function.identity());
            return new GenConstructor(acc, name, params, ILines.of(()->body));
        });
        val allArgsConstAccessibility
                = sourceSpec.getConfigures().generateAllArgConstructor
                ? PUBLIC
                : PRIVATE;
        return allArgsConstructor.apply(sourceSpec, allArgsConstAccessibility);
    }
    
    private String initGetterField(Getter getter) {
        // TODO - some of these should be pushed to $utils
        val    getterName = getter.getName();
        val    getterType = getter.getType();
        String initValue  = null;
        if (getterType.isList()) {
            initValue = String.format("ImmutableList.from(%1$s)", getterName);
        } else if (getterType.isMap()) {
            initValue = String.format("ImmutableMap.from(%1$s)", getterName);
        } else if (getterType.isFuncList()) {
            initValue = String.format("ImmutableList.from(%1$s)", getterName);
        } else if (getterType.isFuncMap()) {
            initValue = String.format("ImmutableMap.from(%1$s)", getterName);
        } else if (getterType.isNullable()) {
            initValue = String.format("Nullable.of((%1$s == null) ? null : %1$s.get())", getterName);
        } else if (!getter.isNullable() && !getterType.isPrimitive()){
            initValue = String.format("$utils.notNull(%1$s)", getterName);
        } else {
            initValue = getterName;
        }
        
        if (!getter.isRequired()) {
            val defaultValue = DefaultValue.defaultValueCode(getterType, getter.getDefaultTo());
            initValue = String.format("java.util.Optional.ofNullable(%1$s).orElseGet(()->%2$s)", getterName, defaultValue);
        }
        
        return String.format("this.%1$s = %2$s;", getterName, initValue);
    }
    
    private GenField getterToField(SourceSpec sourceSpec, Getter getter) {
        // It should be good to convert this to tuple2 and apply to the method.
        val name  = getter.getName();
        val type  = getter.getType();
        val accss = sourceSpec.getConfigures().publicFields ? PUBLIC : PRIVATE;
        val field = new GenField(accss, FINAL, INSTANCE, name, type, null);
        return field;
    }
    
    private GenParam getterToGenParam(Getter getter) {
        val paramName = getter.getName();
        val paramType = getter.getType();
        return new GenParam(paramName, paramType);
    }
    
    private Stream<GenMethod> getterToWitherMethods(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val stream = Stream.of(
                    getterToWitherMethodValue(     sourceSpec, withMethodName, getter),
                    getterToWitherMethodSupplier(  sourceSpec, withMethodName, getter),
                    getterToWitherMethodFunction(  sourceSpec, withMethodName, getter),
                    getterToWitherMethodBiFunction(sourceSpec, withMethodName, getter)
                );
        val isList 
                =  getter.getType().isList()
                || getter.getType().isFuncList();
        if (!isList)
            return stream;
        
        val arrayMethod = getterToWitherMethodArray(sourceSpec, withMethodName, getter);
        return Stream.concat(
                Stream.of(arrayMethod),
                stream);
    }
    
    // TODO - Should be refactored ... may be to classes.
    
    private GenMethod getterToWitherMethodArray(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val listName = getter.getName(); 
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType().generics().get(0)));
        val isFList = getter.getType().isFuncList();
        val newArray = isFList ? "functionalj.list.ImmutableList.of" : "$utils.asList";
        val paramCall 
                = sourceSpec
                .getGetters()
                .stream()
                .map(g -> listName.equals(g.getName()) 
                        ? newArray + "(" + g.getName() + ")"
                        : g.getName())
                .collect(joining(", "));
        val usedTypes = isFList ? asList(Type.FUNC_LIST) : Collections.<Type>emptyList();
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine), usedTypes, emptyList(),true);
    }
    private GenMethod getterToWitherMethodValue(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType()));
        val paramCall = sourceSpec.getGetters().stream().map(Getter::getName).collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    private GenMethod getterToWitherMethodSupplier(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name       = withMethodName.apply(getter);
        val type       = sourceSpec.getTargetType();
        val getterName = getter.getName();
        val getterType = getter.getType().declaredType();
        val params     = asList(new GenParam(getter.getName(), Type.of(Supplier.class, getterType)));
        val paramCall  = sourceSpec.getGetters().stream()
                            .map    (Getter::getName)
                            .map    (gName -> gName.equals(getterName) ? gName + ".get()" : gName)
                            .collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    private GenMethod getterToWitherMethodFunction(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name       = withMethodName.apply(getter);
        val type       = sourceSpec.getTargetType();
        val getterName = getter.getName();
        val getterType = getter.getType().declaredType();
        val params     = asList(new GenParam(getterName, Type.of(Function.class, getterType, getterType)));
        val paramCall  = sourceSpec.getGetters().stream()
                        .map    (Getter::getName)
                        .map    (gName -> gName.equals(getterName) ? gName + ".apply(this." + gName + ")" : gName)
                        .collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    private GenMethod getterToWitherMethodBiFunction(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name       = withMethodName.apply(getter);
        val type       = sourceSpec.getTargetType();
        val getterName = getter.getName();
        val getterType = getter.getType().declaredType();
        val params     = asList(new GenParam(getterName, Type.of(BiFunction.class, type, getterType, getterType)));
        val paramCall  = sourceSpec.getGetters().stream()
                .map    (Getter::getName)
                .map    (gName -> gName.equals(getterName) ? gName + ".apply(this, this." + gName + ")" : gName)
                .collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    
    private GenMethod getterToGetterMethod(Getter getter) {
        val name = getter.getName();
        val type = getter.getType();
        val params = new ArrayList<GenParam>();
        val body   = "return " + getter.getName() + ";";
        val method = new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(body));
        return method;
    }
    
}
