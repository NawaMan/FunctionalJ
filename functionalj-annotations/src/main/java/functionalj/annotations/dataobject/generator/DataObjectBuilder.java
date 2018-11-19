//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.dataobject.generator;

import static functionalj.annotations.dataobject.generator.ILines.line;
import static functionalj.annotations.dataobject.generator.model.Accessibility.PRIVATE;
import static functionalj.annotations.dataobject.generator.model.Accessibility.PUBLIC;
import static functionalj.annotations.dataobject.generator.model.Modifiability.FINAL;
import static functionalj.annotations.dataobject.generator.model.Modifiability.MODIFIABLE;
import static functionalj.annotations.dataobject.generator.model.Scope.INSTANCE;
import static functionalj.annotations.dataobject.generator.model.Scope.STATIC;
import static functionalj.annotations.dataobject.generator.utils.listOf;
import static functionalj.annotations.dataobject.generator.utils.themAll;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.annotations.IPostReConstruct;
import functionalj.annotations.dataobject.generator.model.Accessibility;
import functionalj.annotations.dataobject.generator.model.GenClass;
import functionalj.annotations.dataobject.generator.model.GenConstructor;
import functionalj.annotations.dataobject.generator.model.GenField;
import functionalj.annotations.dataobject.generator.model.GenMethod;
import functionalj.annotations.dataobject.generator.model.GenParam;
import functionalj.annotations.dataobject.generator.model.Modifiability;
import functionalj.annotations.dataobject.generator.model.Scope;
import lombok.val;

/**
 * Builder for a data object.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class DataObjectBuilder {
    
    private static final String POST_CONSTRUCT = "postReConstruct";
    
    private SourceSpec sourceSpec;
    
    /**
     * Constructor for the data object builder.
     * 
     * @param sourceSpec  the source spec.
     */
    public DataObjectBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    /**
     * Build the data object.
     * 
     * @return  the data object.
     **/
    public DataObjectSpec build() {
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
        
        val withMethodName   = (Function<Getter, String>)(utils::withMethodName);
        val ipostReConstruct = Type.of(IPostReConstruct.class).simpleName();
        val postReConstructMethod = new GenMethod(
                PRIVATE, STATIC, MODIFIABLE,
                sourceSpec.getTargetType(), POST_CONSTRUCT,
                asList(new GenParam("object", sourceSpec.getTargetType())),
                line(
                    "if (object instanceof " + ipostReConstruct + ")",
                    "    ((" + ipostReConstruct + ")object).postReConstruct();",
                    "return object;"
                ));
        
        val getters = sourceSpec.getGetters();
        val getterFields  = getters.stream().map    (getter -> getterToField(sourceSpec, getter));
        val getterMethods = getters.stream().map    (getter -> getterToGetterMethod(getter));
        val witherMethods = getters.stream().flatMap(getter -> getterToWitherMethods(sourceSpec, withMethodName, getter));
        
        GenField theField = null;
        GenClass lensClass = null;
        if (sourceSpec.getConfigures().generateLensClass) {
            val lensClassBuilder = new LensClassBuilder(sourceSpec);
            lensClass            = lensClassBuilder.build();
            theField             = lensClassBuilder.generateTheLensField();
        }
        GenClass builderClass = null;
        if (sourceSpec.getConfigures().generateBuilderClass) {
            val builderClassBuilder = new BuilderClassBuilder(sourceSpec);
            builderClass            = builderClassBuilder.build();
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        val specField = ((sourceSpec.getSpecObjName() == null) || sourceSpec.getSpecObjName().isEmpty())
                ? (Stream<GenField>)(Stream)Stream.empty()
                : Stream.of(new GenField(
                        Accessibility.PUBLIC,
                        Modifiability.FINAL,
                        Scope.STATIC,
                        sourceSpec.getSpecObjName(),
                        Type.of(SourceSpec.class),
                        sourceSpec.toCode()));
        
        val toString = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.STRING,
                "toString",
                Collections.emptyList(),
                line("return \"" + sourceSpec.getTargetClassName() + "[\" + " +
                        getters.stream()
                        .map(g -> "\""+ g.getName() + ": \" + " + g.getName() + "()")
                        .collect(joining(" + \", \" + ")) +
                        " + \"]\";"));
        
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
                .map(g -> "            (" + g.getType().simpleNameWithGeneric() + ")map.get(\"" + g.getName() + "\")")
                .collect(Collectors.joining(",\n"))
                .split("\n"));
        val fromMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                sourceSpec.getTargetType(),
                "fromMap",
                asList(new GenParam("map", Type.MAP.withGenerics(asList(Type.STRING, Type.OBJECT)))),
                ILines.linesOf(
                    line("return new " + sourceSpec.getTargetType().simpleName() + "("),
                    fromMapBody,
                    line("        );")
                ));
        
        val toMapBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(g -> "map.put(\"" + g.getName() + "\",  (Object)" + g.getName() + ");")
                .collect(Collectors.toList()));
        val toMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(Type.STRING, Type.OBJECT)),
                "toMap",
                emptyList(),
                ILines.linesOf(
                    line("java.util.Map<String, Object> map = new HashMap<>();"),
                    toMapBody,
                    line("return map;")
                ),
                asList(Type.of(HashMap.class)),
                false);
        
        val flatMap = Arrays.<Stream<GenMethod>>asList(
                    getterMethods,
                    witherMethods,
                    Stream.of(postReConstructMethod),
                    Stream.of(fromMap, toMap),
                    Stream.of(toString, hashCode, equals)
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
        
        val dataObjSpec = new DataObjectSpec(
                sourceSpec.getTargetClassName(),
                sourceSpec.getTargetPackageName(),
                sourceSpec.getSpecClassName(),
                sourceSpec.getPackageName(),
                extendeds, implementeds,
                constructors, fields, methods, innerClasses, emptyList());
        return dataObjSpec;
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
        val assignments = sourceSpec.getGetters().stream()
                        .map    (getter -> "this." + getter.getName() + "=" + (getter.isRequired() ? getter.getName() : getter.getType().defaultValue()) + ";")
                        .collect(toList());
        return new GenConstructor(PUBLIC, name, params, ILines.line(assignments));
    }
    private GenConstructor allArgConstructor() {
        val allArgsConstructor = (BiFunction<SourceSpec, Accessibility, GenConstructor>)((spec, acc) ->{
            val name = spec.getTargetClassName();
            List<GenParam> params = spec.getGetters().stream()
                    .map    (this::getterToGenParam)
                    .collect(toList());
            val body = spec.getGetters().stream().map(this::initGetterField);
            return new GenConstructor(acc, name, params, ILines.of(()->body));
        });
        val allArgsConstAccessibility
                = sourceSpec.getConfigures().generateAllArgConstructor
                ? PUBLIC
                : PRIVATE;
        return allArgsConstructor.apply(sourceSpec, allArgsConstAccessibility);
    }

    private String initGetterField(Getter getter) {
        if (getter.getType().isList()) {
            val getterName = getter.getName();
            return String.format("this.%1$s = ImmutableList.from(%1$s);", getterName);
        } else if (getter.getType().isMap()) {
            val getterName = getter.getName();
            return String.format("this.%1$s = ImmutableMap.from(%1$s);", getterName);
        } else if (getter.getType().isFuncList()) {
            val getterName = getter.getName();
            return String.format("this.%1$s = ImmutableList.from(%1$s);", getterName);
        } else if (getter.getType().isFuncMap()) {
            val getterName = getter.getName();
            return String.format("this.%1$s = ImmutableMap.from(%1$s);", getterName);
        } else if (getter.getType().isNullable()) {
            val getterName = getter.getName();
            return String.format("this.%1$s = Nullable.of((%1$s == null) ? null : %1$s.get());", getterName);
        } else {
            val getterName = getter.getName();
            return String.format("this.%1$s = %1$s;", getterName);
        }
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
        val newArray = isFList ? "functionalj.list.ImmutableList.of" : "java.util.Arrays.asList";
        val paramCall 
                = sourceSpec
                .getGetters()
                .stream()
                .map(g -> listName.equals(g.getName()) 
                        ? newArray + "(" + g.getName() + ")"
                        : g.getName())
                .collect(joining(", "));
        val usedTypes = asList(isFList ? Type.FUNC_LIST : Type.of(Arrays.class));
        val returnLine = "return " + POST_CONSTRUCT + "(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine), usedTypes, true);
    }
    private GenMethod getterToWitherMethodValue(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType()));
        val paramCall = sourceSpec.getGetters().stream().map(Getter::getName).collect(joining(", "));
        val returnLine = "return " + POST_CONSTRUCT + "(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
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
        val returnLine = "return " + POST_CONSTRUCT + "(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
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
        val returnLine = "return " + POST_CONSTRUCT + "(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
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
        val returnLine = "return " + POST_CONSTRUCT + "(new " + sourceSpec.getTargetClassName() + "(" + paramCall + "));";
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
