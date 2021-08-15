package functionalj.types.struct.features;

import static functionalj.types.struct.AnnotationUtils.isAbstract;
import static java.lang.String.format;

import java.util.List;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import functionalj.types.Generic;
import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.struct.StructSpec.Input;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import lombok.val;

public class FeatureSerialization {
    
    public static String validateSerialization(
                    Input          input,
                    TypeElement    type, 
                    List<Getter>   getters, 
                    String         packageName,
                    String         specTargetName, 
                    Configurations configures) {
        val serializeTo = configures.serialize;
        if (serializeTo == Serialize.To.NOTHING)
            return null;    // Don't care about serialize method
        
        // Find the default/non-abstract method with name `serialize` with no parameter.
        val method      = existingSerializeMethod(input, type);
        val returnType  = (method != null) ? method.getReturnType() + "" : null;
        String expected = null;
        if (serializeTo == Serialize.To.MAP) {
            if ("java.util.Map<java.lang.String,java.lang.Object>"          .equals(returnType)
             || "functionalj.map.FuncMap<java.lang.String,java.lang.Object>".equals(returnType))
                return null;
            
            if (returnType == null)
                return null;    // We will create the method
            
            expected = "Map<String, Object>";
        } else if (serializeTo == Serialize.To.STRING) {
            if ("java.lang.String".equals(returnType))
                return null;
            
            if (returnType == null)
                return null;    // We will create the method
            
            expected = "String";
        } else if (serializeTo == Serialize.To.BOOLEAN) {
            if ("boolean".equals(returnType) 
             || "java.lang.Boolean".equals(returnType))
                return null;
            
            expected = "boolean";
        } else if (serializeTo == Serialize.To.INT) {
            if ("int".equals(returnType) 
             || "java.lang.Integer".equals(returnType))
                return null;
            
            expected = "int";
        } else if (serializeTo == Serialize.To.LONG) {
            if ("long".equals(returnType) 
             || "java.lang.Long".equals(returnType))
                return null;
            
            expected = "long";
        } else if (serializeTo == Serialize.To.DOUBLE) {
            if ("double".equals(returnType) 
             || "java.lang.Double".equals(returnType))
                return null;
            
            expected = "double";
        }
        
        if (expected == null)
            return null;
        
        val template = "Serialize to %s must have a `serialize()` method that return `%s` but the follow is found: %s";
        return format(template, serializeTo, expected, returnType);
    }
    
    public static ExecutableElement existingSerializeMethod(Input input, TypeElement type) {
        return type.getEnclosedElements().stream()
                .filter(elmt -> elmt.getKind().equals(ElementKind.METHOD))
                .map   (elmt -> ((ExecutableElement)elmt))
                .filter(mthd -> mthd.isDefault() || !isAbstract(input, mthd))
                .filter(mthd -> mthd.getParameters().isEmpty())
                .findFirst()
                .orElse((ExecutableElement)null);
    }
    
    public static String existingSerializeMethodReturnType(Input input, TypeElement type) {
        val method     = existingSerializeMethod(input, type);
        val returnType = (method != null) ? method.getReturnType() + "" : null;
        return returnType;
    }
    
    // The following methods assume that we already validate using `validateSerialization(...)`
    
    public static Type serializeToType(Input input, TypeElement type, Configurations configures) {
        val serializeTo = configures.serialize;
        if (serializeTo == Serialize.To.NOTHING)
            return null;    // Don't care about serialize method
        
        val methodType = existingSerializeMethodReturnType(input, type);
        if (methodType != null) {
            val primitiveType = Type.primitiveTypes.get(methodType);
            if (primitiveType != null)
                return primitiveType;
            val boxedType = Type.primitiveTypes.get(methodType);
            if (boxedType != null)
                return boxedType;
            if ("java.lang.String".equals(methodType))
                return Type.STRING;
            if ("java.util.Map<java.lang.String,java.lang.Object>".equals(methodType))
                return Type.MAP.withGenerics(new Generic(Type.STRING), new Generic(Type.OBJECT));
            if ("functionalj.map.FuncMap<java.lang.String,java.lang.Object>".equals(methodType))
                return Type.FUNC_MAP.withGenerics(new Generic(Type.STRING), new Generic(Type.OBJECT));
        }
        
        if (serializeTo == Serialize.To.MAP)
            return Type.FUNC_MAP.withGenerics(new Generic(Type.STRING), new Generic(Type.OBJECT));
        if (serializeTo == Serialize.To.STRING)
            return Type.STRING;
        
        return null;
    }
    
    public static Type serializeType(Input input, TypeElement type, Configurations configures) {
        val toType = serializeToType(input, type, configures);
        return (toType != null) ? Type.SERIALIZE.withGenerics(new Generic(toType)) : null;
    }
    
}
