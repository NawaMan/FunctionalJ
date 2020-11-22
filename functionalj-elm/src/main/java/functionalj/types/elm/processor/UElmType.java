// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.elm.processor;

import static functionalj.types.elm.processor.Utils.toCamelCase;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.types.Type;
import lombok.val;

/**
 * This utility class help mapping Java type to Elm type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class UElmType {
    
    static private final Map<Type, String> primitiveTypes;
    static {
        primitiveTypes = new HashMap<Type, String>();
        primitiveTypes.put(Type.BOOL,       "Bool");
        primitiveTypes.put(Type.BOOLEAN,    "Bool");
        primitiveTypes.put(Type.BYT,        "Int");
        primitiveTypes.put(Type.BYTE,       "Int");
        primitiveTypes.put(Type.SHRT,       "Int");
        primitiveTypes.put(Type.SHORT,      "Int");
        primitiveTypes.put(Type.INT,        "Int");
        primitiveTypes.put(Type.INTEGER,    "Int");
        primitiveTypes.put(Type.LNG,        "Int");
        primitiveTypes.put(Type.LONG,       "Int");
        primitiveTypes.put(Type.BIGINTEGER, "Int");
        primitiveTypes.put(Type.FLT,        "Float");
        primitiveTypes.put(Type.FLOAT,      "Float");
        primitiveTypes.put(Type.DBL,        "Float");
        primitiveTypes.put(Type.DOUBLE,     "Float");
        primitiveTypes.put(Type.BIGDECIMAL, "Float");
        primitiveTypes.put(Type.CHR,        "Char");
        primitiveTypes.put(Type.CHARACTER,  "Char");
        primitiveTypes.put(Type.STR,        "String");
        primitiveTypes.put(Type.STRING,     "String");
    }
    
    public static String emlType(Type type) {
        var basicType = elmBasicType(type);
        
        // https://package.elm-lang.org/packages/elm-lang/core/3.0.0/Result
        // https://package.elm-lang.org/packages/elm-lang/core/3.0.0/Date
        // https://package.elm-lang.org/packages/elm-lang/core/3.0.0/Time
        
        if (basicType != null)
            return basicType;
        
        // Must check if the type is local. If not, we need to use full name.
        return elmFullName(type);
    }
    
    public static String elmParamType(Type type) {
        return param(type);
    }
    
    public static String elmParamType(String type) {
        return wrap(type);
    }
    
    private static String elmFullName(Type type) {
        var simpleName  = type.simpleName();
        var packageName = type.packageName();
        if (packageName == null)
            return simpleName;
        
        var moduleName = Stream.of(packageName.split("\\."))
                .map(Utils::toTitleCase)
                .collect(Collectors.joining("."));
        if (moduleName.isEmpty())
            return simpleName;
        
        return moduleName + "." + simpleName;
    }
    
    private static String elmBasicType(Type type) {
        String primitiveElmType = primitiveTypes.get(type);
        if (primitiveElmType != null)
            return primitiveElmType;
        
        if (type.isList() || type.isFuncList()) {
            var elmType = elmListType(type);
            return elmType;
        }
        
        if (type.isMap() || type.isFuncMap()) {
            var elmType = elmMapType(type);
            return elmType;
        }
        
        if (type.isOptional() || type.isNullable()) {
            var elmType = elmMayBeType(type);
            return elmType;
        }
        
        return null;
    }
    
    private static String elmListType(Type type) {
        var aType   = type.generics().get(0).toType();
        var aStrg   = param(aType);
        var elmType = "List " + aStrg;
        return elmType;
    }
    
    private static String elmMapType(Type type) {
        var kType = type.generics().get(0).toType();
        var kStrg = param(kType);
        var vType = type.generics().get(1).toType();
        var vStrg = param(vType);
        var elmType = "Dict " + kStrg + " " + vStrg;
        return elmType;
    }
    
    public static String elmMayBeType(Type type) {
        var aType = type.generics().get(0).toType();
        var aStrg = param(aType);
        var elmType = "Maybe " + aStrg;
        return elmType;
    }
    
    public static String elmMayBeOfType(Type type) {
        var aStrg = param(type);
        var elmType = "Maybe " + aStrg;
        return elmType;
    }
    
    private static String param(Type type) {
        var emlType   = emlType(type);
        var paramType = wrap(emlType);
        return paramType;
    }
    
    private static String wrap(String typeStr) {
        if (typeStr.startsWith("(") && typeStr.endsWith(""))
            return typeStr;
        if (!typeStr.contains(" "))
            return typeStr;
        return "(" + typeStr + ")";
    }
    
    public static String encoderNameOf(Type type, String name) {
        return encoderNameOf(type, name, false);
    }
    public static String encoderNameOf(Type type, String name, boolean isNullable) {
        name = (name != null) ? name : "";
        
        boolean isOptionalType = type.isOptional() || type.isNullable();
        if (isNullable || isOptionalType) {
            var paramType    = isOptionalType ? type.generics().get(0).toType() : type;
            var paramEncoder = encoderNameOf(paramType, name);
            return "Maybe.withDefault Json.Encode.null (Maybe.map " + paramEncoder + ")";
        }
        
        var primitiveElmType = primitiveTypes.get(type);
        if (primitiveElmType != null)
            return "Json.Encode." + primitiveElmType.toLowerCase() + " " + name;
        
        var typeName = toCamelCase(type.simpleName());
        if (type.isList() || type.isFuncList()) {
            var paramType            = type.generics().get(0).toType();
            var genericPrimitiveType = primitiveTypes.get(paramType);
            if (genericPrimitiveType != null)
                return "Json.Encode.list Json.Encode." + genericPrimitiveType.toLowerCase() + " " + name;
            
            return typeName + "ListEncoder " + name;
        }
        
        if (type.isMap() || type.isFuncMap()) {
            throw new UnsupportedOperationException("Encoder of map type is not yet support.");
        }
        
        return typeName + "Encoder " + name;
    }
    
    public static String decoderNameOf(Type type) {
        var primitiveElmType = primitiveTypes.get(type);
        if (primitiveElmType != null)
            return "Json.Decode." + primitiveElmType.toLowerCase();
        
        var typeName = Utils.toCamelCase(type.simpleName());
        if (type.isList() || type.isFuncList()) {
            return typeName + "ListDecode";
        }
        
        if (type.isMap() || type.isFuncMap()) {
            throw new UnsupportedOperationException("Decoder of map type is not yet support.");
        }
        
        if (type.isOptional() || type.isNullable()) {
            throw new UnsupportedOperationException("Decoder of optional type is not yet support.");
        }
        
        return typeName + "Decoder";
    }
    
}
