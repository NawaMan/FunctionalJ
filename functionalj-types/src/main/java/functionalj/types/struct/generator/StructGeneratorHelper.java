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

import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.model.Accessibility.PACKAGE;
import static functionalj.types.struct.generator.model.Accessibility.PRIVATE;
import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.FINAL;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static functionalj.types.struct.generator.model.Scope.STATIC;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.function.Function.identity;
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

import functionalj.types.Core;
import functionalj.types.DefaultValue;
import functionalj.types.Generic;
import functionalj.types.IPostConstruct;
import functionalj.types.Type;
import functionalj.types.choice.generator.Utils;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;

public class StructGeneratorHelper {
    
    static GenMethod generateToString(SourceSpec sourceSpec, List<Getter> getters) {
        GenMethod toString = null;
        String    toStringTemplate = sourceSpec.getConfigures().toStringTemplate;
        if (toStringTemplate != null) {
            String toStringBody = null;
            if (!toStringTemplate.isEmpty()) {
                String strFuncs = Core.StrFunc.packageName() + "." + Core.StrFunc.simpleName();
                toStringBody = "return " + strFuncs + ".template(" + Utils.toStringLiteral(toStringTemplate) + "," + StructMapGeneratorHelper.METHOD_TO_MAP + "()::get);";
            } else {
                String template 
                        = sourceSpec.getConfigures().recordToString
                        ? "\"%1$s=\" + %1$s()"
                        : "\"%1$s: \" + %1$s()";
                
                String body 
                        = getters.stream()
                        .map    (g -> format(template, g.name()))
                        .collect(joining(" + \", \" + "));
                toStringBody = "return \"" + sourceSpec.getTargetClassName() + "[\" + " + (body.isEmpty() ? "\"\"" : body) + " + \"]\";";
            }
            toString = new GenMethod("toString", Type.STRING, Accessibility.PUBLIC, Scope.INSTANCE, Modifiability.MODIFIABLE, Collections.emptyList(), line(toStringBody));
        }
        return toString;
    }
    
    static GenMethod generateHashCode(SourceSpec sourceSpec) {
        GenMethod hashCode = new GenMethod("hashCode", Type.INT, Accessibility.PUBLIC, Scope.INSTANCE, Modifiability.MODIFIABLE, Collections.emptyList(), line("return toString().hashCode();"));
        return hashCode;
    }
    
    static GenMethod generateEquals(SourceSpec sourceSpec) {
        GenMethod equals = new GenMethod("equals", Type.BOOL, Accessibility.PUBLIC, Scope.INSTANCE, Modifiability.MODIFIABLE, asList(new GenParam("another", Type.of(Object.class))), line("return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));"));
        return equals;
    }
    
    static GenMethod generateGetStructScheme(SourceSpec sourceSpec) {
        ILines    getStructSchemaBody = ILines.line(sourceSpec.getGetters().stream().map(g -> "map.put(\"" + g.name() + "\", " + g.toCode() + ");").collect(toList()));
        GenMethod getStructSchema     = new GenMethod("getStructSchema", Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.of(Getter.class)))), Accessibility.PUBLIC, Scope.STATIC, Modifiability.MODIFIABLE, emptyList(), emptyList(), false, false, ILines.linesOf(line("java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();"), getStructSchemaBody, line("return map;")), emptyList(), emptyList());
        return getStructSchema;
    }
    
    static GenMethod generateGetSchema(SourceSpec sourceSpec) {
        GenMethod getSchema = new GenMethod("__getSchema", Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.of(Getter.class)))), Accessibility.PUBLIC, Scope.INSTANCE, Modifiability.MODIFIABLE, emptyList(), emptyList(), false, false, ILines.linesOf(line("return getStructSchema();")), asList(Type.of(Map.class), Type.of(HashMap.class), Type.of(Getter.class)), emptyList());
        return getSchema;
    }
    
    static Stream<GenField> generateSpecField(SourceSpec sourceSpec) {
        if (sourceSpec.hasSpecField()) {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            Stream<GenField> emptyStream = (Stream<GenField>) (Stream) Stream.empty();
            return emptyStream;
        }
        GenField genField = new GenField(PUBLIC, FINAL, STATIC, sourceSpec.getSpecObjName(), Type.of(SourceSpec.class), sourceSpec.toCode());
        return Stream.of(genField);
    }
    
    static GenConstructor noArgConstructor(SourceSpec sourceSpec) {
        if (!sourceSpec.getConfigures().generateNoArgConstructor)
            return null;
        
        String        name              = sourceSpec.getTargetClassName();
        String        paramString       = sourceSpec.getGetters().stream().map(getter -> getter.type().defaultValue()).map(String::valueOf).collect(joining(", "));
        String        body              = "this(" + paramString + ");";
        boolean       publicConstructor = sourceSpec.getConfigures().publicConstructor;
        Accessibility accessibility     = (publicConstructor ? PUBLIC : PACKAGE);
        return new GenConstructor(accessibility, name, emptyList(), line(body));
    }
    
    static GenConstructor requiredOnlyConstructor(SourceSpec sourceSpec) {
        if (!sourceSpec.getConfigures().generateRequiredOnlyConstructor)
            return null;
        if (sourceSpec.getGetters().stream().allMatch(Getter::isRequired))
            return null;
        if (sourceSpec.getConfigures().generateNoArgConstructor && sourceSpec.getGetters().stream().noneMatch(Getter::isRequired))
            return null;
        
        String         name              = sourceSpec.getTargetClassName();
        List<GenParam> params            = sourceSpec.getGetters().stream().filter(getter -> getter.isRequired()).map(StructGeneratorHelper::getterToGenParam).collect(toList());
        String         pkgName           = sourceSpec.getPackageName();
        String         eclName           = sourceSpec.getEncloseName();
        String         valName           = sourceSpec.getValidatorName();
        String         getterParams      = sourceSpec.getGetters().stream().map(getter -> getter.getDefaultValueCode(getter.name())).collect(joining(","));
        Stream<String> assignGetters     = sourceSpec.getGetters().stream().map(getter -> "this." + getter.name() + " = " + getter.getDefaultValueCode(getter.name()) + ";");
        Stream<String> validate          = (Stream<String>) ((valName == null) ? null : Stream.of("functionalj.result.ValidationException.ensure(" + pkgName + "." + eclName + "." + valName + "(" + getterParams + "), this);"));
        String         ipostConstruct    = Type.of(IPostConstruct.class).simpleName();
        Stream<String> postConstruct     = Stream.of("if (this instanceof " + ipostConstruct + ") ((" + ipostConstruct + ")this).postConstruct();");
        List<String>   assignments       = Stream.of(assignGetters, validate, postConstruct).filter(Objects::nonNull).flatMap(Function.identity()).collect(toList());
        boolean        publicConstructor = sourceSpec.getConfigures().publicConstructor;
        Accessibility  accessibility     = (publicConstructor ? PUBLIC : PACKAGE);
        return new GenConstructor(accessibility, name, params, ILines.line(assignments));
    }
    
    static GenConstructor allArgConstructor(SourceSpec sourceSpec) {
        BiFunction<SourceSpec, Accessibility, GenConstructor> allArgsConstructor = (BiFunction<SourceSpec, Accessibility, GenConstructor>) ((spec, acc) -> {
            String         name           = spec.getTargetClassName();
            List<GenParam> params         = spec.getGetters().stream().map(StructGeneratorHelper::getterToGenParam).collect(toList());
            String         pkgName        = sourceSpec.getPackageName();
            String         eclName        = sourceSpec.getEncloseName();
            String         valName        = sourceSpec.getValidatorName();
            String         getterParams   = sourceSpec.getGetters().stream().map(getter -> getter.name()).collect(Collectors.joining(","));
            Stream<String> assignGetters  = spec.getGetters().stream().map(StructGeneratorHelper::initGetterField);
            Stream<String> validate       = (Stream<String>) ((valName == null) ? null : Stream.of("functionalj.result.ValidationException.ensure(" + pkgName + "." + eclName + "." + valName + "(" + getterParams + "), this);"));
            String         ipostConstruct = Type.of(IPostConstruct.class).simpleName();
            Stream<String> body           = Stream.of(assignGetters, validate, Stream.of("if (this instanceof " + ipostConstruct + ") ((" + ipostConstruct + ")this).postConstruct();")).flatMap(identity());
            return new GenConstructor(acc, name, params, ILines.of(() -> body));
        });
        
        boolean       publicConstructor         = sourceSpec.getConfigures().publicConstructor;
        Accessibility allArgsConstAccessibility = sourceSpec.getConfigures().generateAllArgConstructor ? (publicConstructor ? PUBLIC : PACKAGE) : PRIVATE;
        return allArgsConstructor.apply(sourceSpec, allArgsConstAccessibility);
    }
    
    static String initGetterField(Getter getter) {
        // TODO - some of these should be pushed to $utils
        String getterName = getter.name();
        Type   getterType = getter.type();
        String initValue = null;
        if (getterType.isList()) {
            initValue = String.format("functionalj.list.ImmutableFuncList.from(%1$s)", getterName);
        } else if (getterType.isMap()) {
            initValue = String.format("functionalj.map.ImmutableFuncMap.from(%1$s)", getterName);
        } else if (getterType.isFuncList()) {
            initValue = String.format("functionalj.list.ImmutableFuncList.from(%1$s)", getterName);
        } else if (getterType.isFuncMap()) {
            initValue = String.format("functionalj.map.ImmutableFuncMap.from(%1$s)", getterName);
        } else if (getterType.isNullable()) {
            initValue = String.format("Nullable.of((%1$s == null) ? null : %1$s.get())", getterName);
        } else if (!getter.isNullable() && !getterType.isPrimitive()) {
            initValue = String.format("$utils.notNull(%1$s)", getterName);
        } else {
            initValue = getterName;
        }
        if (!getter.isRequired()) {
            String defaultValue = DefaultValue.defaultValueCode(getterType, getter.defValue());
            initValue = format("java.util.Optional.ofNullable(%1$s).orElseGet(()->%2$s)", getterName, defaultValue);
        }
        return format("this.%1$s = %2$s;", getterName, initValue);
    }
    
    static GenField getterToField(SourceSpec sourceSpec, Getter getter) {
        // It should be good to convert this to tuple2 and apply to the method.
        String        name  = getter.name();
        Type          type  = getter.type();
        Accessibility accss = sourceSpec.getConfigures().publicFields ? PUBLIC : PRIVATE;
        GenField      field = new GenField(accss, FINAL, INSTANCE, name, type, null);
        return field;
    }
    
    static GenParam getterToGenParam(Getter getter) {
        String paramName = getter.name();
        Type   paramType = getter.type();
        return new GenParam(paramName, paramType);
    }
    
    static GenMethod generatePipeMethod(Type targetType) {
        return new GenMethod(
                "__data", 
                targetType, 
                PUBLIC, 
                INSTANCE, 
                MODIFIABLE, 
                emptyList(), 
                emptyList(), 
                false, 
                false, 
                line("return this;"), 
                emptyList(), 
                asList(Type.of(Exception.class)));
    }
    
    static Stream<GenMethod> generatePipeMethods(Type targetType) {
        return Stream.of(generatePipeMethod(targetType));
    }
    
    static Stream<GenMethod> getterToWitherMethods(SourceSpec sourceSpec, Function<Getter, String> withMethodName, Getter getter) {
        Stream<GenMethod> stream = Stream.of(getterToWitherMethodValue(sourceSpec, withMethodName, getter), getterToWitherMethodSupplier(sourceSpec, withMethodName, getter), getterToWitherMethodFunction(sourceSpec, withMethodName, getter), getterToWitherMethodBiFunction(sourceSpec, withMethodName, getter));
        boolean           isList = getter.type().isList() || getter.type().isFuncList();
        if (!isList)
            return stream;
        
        GenMethod arrayMethod = getterToWitherMethodArray(sourceSpec, withMethodName, getter);
        return Stream.concat(Stream.of(arrayMethod), stream);
    }
    
    static GenMethod getterToWitherMethodArray(SourceSpec sourceSpec, Function<Getter, String> withMethodName, Getter getter) {
        String         listName    = getter.name();
        String         name        = withMethodName.apply(getter);
        Type           type        = sourceSpec.getTargetType();
        List<Generic>  generics    = getter.type().generics();
        Type           genericType = (generics.size() >= 1) ? generics.get(0).toType() : Type.OBJECT;
        List<GenParam> params      = asList(new GenParam(getter.name(), genericType));
        boolean        isFList     = getter.type().isFuncList();
        String         newArray    = isFList ? "functionalj.list.ImmutableFuncList.of" : Arrays.class.getCanonicalName() + ".asList";
        String         paramCall   = sourceSpec.getGetters().stream().map(g -> listName.equals(g.name()) ? newArray + "(" + g.name() + ")" : g.name()).collect(joining(", "));
        List<Type>     usedTypes   = Collections.<Type>emptyList();
        String         returnLine  = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(name, type, PUBLIC, INSTANCE, MODIFIABLE, params, emptyList(), false, true, line(returnLine), usedTypes, emptyList());
    }
    
    static GenMethod getterToWitherMethodValue(SourceSpec sourceSpec, Function<Getter, String> withMethodName, Getter getter) {
        String         name       = withMethodName.apply(getter);
        Type           type       = sourceSpec.getTargetType();
        List<GenParam> params     = asList(new GenParam(getter.name(), getter.type()));
        String         paramCall  = sourceSpec.getGetters().stream().map(Getter::name).collect(joining(", "));
        String         returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(name, type, PUBLIC, INSTANCE, MODIFIABLE, params, line(returnLine));
    }
    
    static GenMethod getterToWitherMethodSupplier(SourceSpec sourceSpec, Function<Getter, String> withMethodName, Getter getter) {
        String         name       = withMethodName.apply(getter);
        Type           type       = sourceSpec.getTargetType();
        String         getterName = getter.name();
        Type           getterType = getter.type().declaredType();
        List<GenParam> params     = asList(new GenParam(getter.name(), Type.of(Supplier.class, new Generic(getterType))));
        String         paramCall  = sourceSpec.getGetters().stream().map(Getter::name).map(gName -> gName.equals(getterName) ? gName + ".get()" : gName).collect(joining(", "));
        String         returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(name, type, PUBLIC, INSTANCE, MODIFIABLE, params, line(returnLine));
    }
    
    static GenMethod getterToWitherMethodFunction(SourceSpec sourceSpec, Function<Getter, String> withMethodName, Getter getter) {
        String         name       = withMethodName.apply(getter);
        Type           type       = sourceSpec.getTargetType();
        String         getterName = getter.name();
        Type           getterType = getter.type().declaredType();
        List<GenParam> params     = asList(new GenParam(getterName, Type.of(Function.class, new Generic(getterType), new Generic(getterType))));
        String         paramCall  = sourceSpec.getGetters().stream().map(Getter::name).map(gName -> gName.equals(getterName) ? gName + ".apply(this." + gName + ")" : gName).collect(joining(", "));
        String         returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(name, type, PUBLIC, INSTANCE, MODIFIABLE, params, line(returnLine));
    }
    
    static GenMethod getterToWitherMethodBiFunction(SourceSpec sourceSpec, Function<Getter, String> withMethodName, Getter getter) {
        String         name       = withMethodName.apply(getter);
        Type           type       = sourceSpec.getTargetType();
        String         getterName = getter.name();
        Type           getterType = getter.type().declaredType();
        List<GenParam> params     = asList(new GenParam(getterName, Type.of(BiFunction.class, new Generic(type), new Generic(getterType), new Generic(getterType))));
        String         paramCall  = sourceSpec.getGetters().stream().map(Getter::name).map(gName -> gName.equals(getterName) ? gName + ".apply(this, this." + gName + ")" : gName).collect(joining(", "));
        String         returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(name, type, PUBLIC, INSTANCE, MODIFIABLE, params, line(returnLine));
    }
    
    static GenMethod getterToGetterMethod(Getter getter) {
        String         name   = getter.name();
        Type           type   = getter.type();
        List<GenParam> params = new ArrayList<GenParam>();
        String         body   = "return " + getter.name() + ";";
        return new GenMethod(name, type, PUBLIC, INSTANCE, MODIFIABLE, params, line(body));
    }
    
    static GenMethod inheritMethod(SourceSpec sourceSpec, Callable callable) {
        String targetClassName = sourceSpec.getSpecName();
        
        // - Accessibility, Modifibility, exception, isVarAgrs
        Accessibility  accessibility = PUBLIC;
        Scope          scope         = callable.scope();
        Modifiability  modifiability = MODIFIABLE;
        Type           type          = callable.type();
        String         name          = callable.name();
        List<GenParam> params        = callable.parameters().stream().map(param -> generateParam(param)).collect(toList());
        String         paramNames    = callable.parameters().stream().map(param -> param.getName()).collect(joining(", "));
        List<Generic>  generics      = callable.generics();
        String         call          = format("%s.super.%s(%s);", targetClassName, name, paramNames);
        ILines         body          = (ILines) (type.toString().toLowerCase().equals("void") ? line(call) : line("return " + call));
        List<Type>     usedTypes     = Collections.<Type>emptyList();
        List<Type>     exceptions    = callable.exceptions();
        boolean        isVarAgrs     = callable.isVarAgrs();
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, false, isVarAgrs, body, usedTypes, exceptions);
    }
    
    static GenParam generateParam(Parameter param) {
        return new GenParam(param.getName(), param.getType());
    }
    
    static Stream<GenMethod> inheritMethods(SourceSpec sourceSpec, List<Callable> callables) {
        return callables.stream().map(callable -> inheritMethod(sourceSpec, callable)).filter(Objects::nonNull);
    }
}
