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
package functionalj.types.struct.features;

import static java.lang.String.format;
import java.util.List;
import javax.lang.model.element.TypeElement;
import functionalj.types.Generic;
import functionalj.types.Serialize;
import functionalj.types.Serialize.To;
import functionalj.types.Type;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputMethodElement;
import functionalj.types.input.InputTypeElement;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec.Configurations;

public class FeatureSerialization {
    
    public static String validateSerialization(InputElement element, TypeElement type, List<Getter> getters, String packageName, String specTargetName, Configurations configures) {
        return validateSerialization(element, element.asTypeElement(), getters, packageName, specTargetName, configures);
    }
    
    public static String validateSerialization(InputElement element, InputTypeElement type, List<Getter> getters, String packageName, String specTargetName, Configurations configures) {
        To serializeTo = configures.serialize;
        if (serializeTo == Serialize.To.NOTHING)
            // Don't care about serialize method
            return null;
        
        // Find the default/non-abstract method with name `serialize` with no parameter.
        InputMethodElement method     = existingSerializeMethod(element, type);
        String             returnType = (method != null) ? method.returnType() + "" : null;
        String             expected   = null;
        if (serializeTo == Serialize.To.MAP) {
            if ("java.util.Map<java.lang.String,java.lang.Object>".equals(returnType) || "functionalj.map.FuncMap<java.lang.String,java.lang.Object>".equals(returnType))
                return null;
            
            if (returnType == null)
                // We will create the method
                return null;
            
            expected = "Map<String, Object>";
        } else if (serializeTo == Serialize.To.STRING) {
            if ("java.lang.String".equals(returnType))
                return null;
            
            if (returnType == null)
                // We will create the method
                return null;
            
            expected = "String";
        } else if (serializeTo == Serialize.To.BOOLEAN) {
            if ("boolean".equals(returnType) || "java.lang.Boolean".equals(returnType))
                return null;
            
            expected = "boolean";
        } else if (serializeTo == Serialize.To.INT) {
            if ("int".equals(returnType) || "java.lang.Integer".equals(returnType))
                return null;
            
            expected = "int";
        } else if (serializeTo == Serialize.To.LONG) {
            if ("long".equals(returnType) || "java.lang.Long".equals(returnType))
                return null;
            
            expected = "long";
        } else if (serializeTo == Serialize.To.DOUBLE) {
            if ("double".equals(returnType) || "java.lang.Double".equals(returnType))
                return null;
            
            expected = "double";
        }
        if (expected == null)
            return null;
        
        String template = "Serialize to %s must have a `serialize()` method that return `%s` but the follow is found: %s";
        return format(template, serializeTo, expected, returnType);
    }
    
    public static InputMethodElement existingSerializeMethod(InputElement element, InputTypeElement type) {
        return type.enclosedElements().stream().filter(elmt -> elmt.isMethodElement()).map(elmt -> elmt.asMethodElement()).filter(mthd -> mthd.isDefault() || !mthd.isAbstract()).filter(mthd -> mthd.parameters().isEmpty()).findFirst().orElse((InputMethodElement) null);
    }
    
    public static String existingSerializeMethodReturnType(InputElement element, InputTypeElement type) {
        InputMethodElement method     = existingSerializeMethod(element, type);
        String             returnType = (method != null) ? method.returnType() + "" : null;
        return returnType;
    }
    
    // The following methods assume that we already validate using `validateSerialization(...)`
    public static Type serializeToType(InputElement element, InputTypeElement type, Configurations configures) {
        To serializeTo = configures.serialize;
        if (serializeTo == Serialize.To.NOTHING)
            // Don't care about serialize method
            return null;
        
        String methodType = existingSerializeMethodReturnType(element, type);
        if (methodType != null) {
            Type primitiveType = Type.primitiveTypes.get(methodType);
            if (primitiveType != null)
                return primitiveType;
            
            Type boxedType = Type.primitiveTypes.get(methodType);
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
    
    public static Type serializeType(InputElement element, InputTypeElement type, Configurations configures) {
        Type toType = serializeToType(element, type, configures);
        return (toType != null) ? Type.SERIALIZE.withGenerics(new Generic(toType)) : null;
    }
}
