package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.model.Accessibility.PACKAGE;
import static functionalj.types.struct.generator.model.Accessibility.PRIVATE;
import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.FINAL;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.INSTANCE;
import static functionalj.types.struct.generator.model.Scope.STATIC;
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
import lombok.val;

public class StructGeneratorHelper {
    
    static GenMethod generateToString(SourceSpec sourceSpec, List<Getter> getters) {
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
        return toString;
    }
    
    static GenMethod generateHashCode(SourceSpec sourceSpec) {
        val hashCode = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.INT,
                "hashCode",
                Collections.emptyList(),
                line("return toString().hashCode();"));
        return hashCode;
    }
    
    static GenMethod generateEquals(SourceSpec sourceSpec) {
        val equals = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.BOOL,
                "equals",
                asList(new GenParam("another", Type.of(Object.class))),
                line("return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));"));
        return equals;
    }
    
    static GenMethod generateGetStructScheme(SourceSpec sourceSpec) {
       val getStructSchemaBody = ILines.line(
               sourceSpec.getGetters()
               .stream()
               .map(g -> "map.put(\"" + g.getName() + "\", " + g.toCode() + ");")
               .collect(Collectors.toList()));
       val getStructSchema = new GenMethod(
               Accessibility.PUBLIC,
               Scope.STATIC,
               Modifiability.MODIFIABLE,
               Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.of(Getter.class)))),
               "getStructSchema",
               emptyList(),
               ILines.linesOf(
                   line("java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();"),
                   getStructSchemaBody,
                   line("return map;")
               ),
               emptyList(),
               emptyList(),
               false);
        return getStructSchema;
    }
    
    static GenMethod generateGetSchema(SourceSpec sourceSpec) {
        val getSchema = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.of(Getter.class)))),
                "__getSchema",
                emptyList(),
                ILines.linesOf(line("return getStructSchema();")),
                asList(Type.of(Map.class), Type.of(HashMap.class), Type.of(Getter.class)),
                emptyList(),
                false);
        return getSchema;
    }
    
    static Stream<GenField> generateSpecField(SourceSpec sourceSpec) {
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
    
    static GenConstructor noArgConstructor(SourceSpec sourceSpec) {
        if (!sourceSpec.getConfigures().generateNoArgConstructor)
            return null;
        
        val name        = sourceSpec.getTargetClassName();
        val paramString = sourceSpec.getGetters().stream()
                .map    (getter -> getter.getType().defaultValue())
                .map    (String::valueOf)
                .collect(joining(", "));
        val body = "this(" + paramString + ");";
        
        val publicConstructor = sourceSpec.getConfigures().publicConstructor;
        val accessibility     = (publicConstructor ? PUBLIC : PACKAGE);
        return new GenConstructor(accessibility, name, emptyList(), line(body));
    }
    
    static GenConstructor requiredOnlyConstructor(SourceSpec sourceSpec) {
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
                        .map   (StructGeneratorHelper::getterToGenParam)
                        .collect(Collectors.toList());
        
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
        val publicConstructor = sourceSpec.getConfigures().publicConstructor;
        val accessibility     = (publicConstructor ? PUBLIC : PACKAGE);
        return new GenConstructor(accessibility, name, params, ILines.line(assignments));
    }
    static GenConstructor allArgConstructor(SourceSpec sourceSpec) {
        val allArgsConstructor = (BiFunction<SourceSpec, Accessibility, GenConstructor>)((spec, acc) ->{
            val name = spec.getTargetClassName();
            List<GenParam> params = spec.getGetters().stream()
                        .map(StructGeneratorHelper::getterToGenParam)
                        .collect(toList());
            
            val pkgName         = sourceSpec.getPackageName();
            val eclName         = sourceSpec.getEncloseName();
            val valName         = sourceSpec.getValidatorName();
            String getterParams = sourceSpec.getGetters().stream().map(getter -> getter.getName()).collect(Collectors.joining(","));
            
            val assignGetters  = spec.getGetters().stream().map(StructGeneratorHelper::initGetterField);
            val validate       = (Stream<String>)((valName == null) ? null : Stream.of("functionalj.result.ValidationException.ensure(" + pkgName + "." + eclName + "." + valName + "(" + getterParams + "), this);"));
            val ipostConstruct = Type.of(IPostConstruct.class).simpleName();
            val body = Stream.of(
                            assignGetters,
                            validate,
                            Stream.of("if (this instanceof " + ipostConstruct + ") ((" + ipostConstruct + ")this).postConstruct();"))
                    .flatMap(Function.identity());
            return new GenConstructor(acc, name, params, ILines.of(()->body));
        });
        val publicConstructor = sourceSpec.getConfigures().publicConstructor;
        val allArgsConstAccessibility
                = sourceSpec.getConfigures().generateAllArgConstructor
                ? (publicConstructor ? PUBLIC : PACKAGE)
                : PRIVATE;
        return allArgsConstructor.apply(sourceSpec, allArgsConstAccessibility);
    }
    
    static String initGetterField(Getter getter) {
        // TODO - some of these should be pushed to $utils
        val    getterName = getter.getName();
        val    getterType = getter.getType();
        String initValue  = null;
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
    
    static GenField getterToField(SourceSpec sourceSpec, Getter getter) {
        // It should be good to convert this to tuple2 and apply to the method.
        val name  = getter.getName();
        val type  = getter.getType();
        val accss = sourceSpec.getConfigures().publicFields ? PUBLIC : PRIVATE;
        val field = new GenField(accss, FINAL, INSTANCE, name, type, null);
        return field;
    }
    
    static GenParam getterToGenParam(Getter getter) {
        val paramName = getter.getName();
        val paramType = getter.getType();
        return new GenParam(paramName, paramType);
    }
    
    static Stream<GenMethod> getterToWitherMethods(
            SourceSpec               sourceSpec,
            Function<Getter, String> withMethodName,
            Getter                   getter) {
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
    
    static GenMethod getterToWitherMethodArray(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val listName = getter.getName();
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType().generics().get(0).toType()));
        val isFList = getter.getType().isFuncList();
        val newArray = isFList ? "functionalj.list.ImmutableFuncList.of" : Arrays.class.getCanonicalName() + ".asList";
        val paramCall
                = sourceSpec
                .getGetters()
                .stream()
                .map(g -> listName.equals(g.getName())
                        ? newArray + "(" + g.getName() + ")"
                        : g.getName())
                .collect(joining(", "));
//        val usedTypes = isFList ? asList(Type.FUNC_LIST) : Collections.<Type>emptyList();
        val usedTypes = Collections.<Type>emptyList();
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine), usedTypes, emptyList(),true);
    }
    static GenMethod getterToWitherMethodValue(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name = withMethodName.apply(getter);
        val type = sourceSpec.getTargetType();
        val params = asList(new GenParam(getter.getName(), getter.getType()));
        val paramCall = sourceSpec.getGetters().stream().map(Getter::getName).collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    static GenMethod getterToWitherMethodSupplier(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name       = withMethodName.apply(getter);
        val type       = sourceSpec.getTargetType();
        val getterName = getter.getName();
        val getterType = getter.getType().declaredType();
        val params     = asList(new GenParam(getter.getName(), Type.of(Supplier.class, new Generic(getterType))));
        val paramCall  = sourceSpec.getGetters().stream()
                            .map    (Getter::getName)
                            .map    (gName -> gName.equals(getterName) ? gName + ".get()" : gName)
                            .collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    static GenMethod getterToWitherMethodFunction(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name       = withMethodName.apply(getter);
        val type       = sourceSpec.getTargetType();
        val getterName = getter.getName();
        val getterType = getter.getType().declaredType();
        val params     = asList(new GenParam(getterName, Type.of(Function.class, new Generic(getterType), new Generic(getterType))));
        val paramCall  = sourceSpec.getGetters().stream()
                        .map    (Getter::getName)
                        .map    (gName -> gName.equals(getterName) ? gName + ".apply(this." + gName + ")" : gName)
                        .collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    static GenMethod getterToWitherMethodBiFunction(SourceSpec sourceSpec,
            Function<Getter, String> withMethodName, Getter getter) {
        val name       = withMethodName.apply(getter);
        val type       = sourceSpec.getTargetType();
        val getterName = getter.getName();
        val getterType = getter.getType().declaredType();
        val params     = asList(new GenParam(getterName, Type.of(BiFunction.class, new Generic(type), new Generic(getterType), new Generic(getterType))));
        val paramCall  = sourceSpec.getGetters().stream()
                .map    (Getter::getName)
                .map    (gName -> gName.equals(getterName) ? gName + ".apply(this, this." + gName + ")" : gName)
                .collect(joining(", "));
        val returnLine = "return new " + sourceSpec.getTargetClassName() + "(" + paramCall + ");";
        return new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(returnLine));
    }
    
    static GenMethod getterToGetterMethod(Getter getter) {
        val name = getter.getName();
        val type = getter.getType();
        val params = new ArrayList<GenParam>();
        val body   = "return " + getter.getName() + ";";
        val method = new GenMethod(PUBLIC, INSTANCE, MODIFIABLE, type, name, params, line(body));
        return method;
    }
}
