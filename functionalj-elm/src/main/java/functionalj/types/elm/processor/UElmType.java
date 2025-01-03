// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import functionalj.types.Type;

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
        String basicType = elmBasicType(type);
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
        String simpleName  = type.simpleName();
        String packageName = type.packageName();
        if (packageName == null)
            return simpleName;
        
        String moduleName = Stream.of(packageName.split("\\.")).map(Utils::toTitleCase).collect(joining("."));
        if (moduleName.isEmpty())
            return simpleName;
        
        return moduleName + "." + simpleName;
    }
    
    private static String elmBasicType(Type type) {
        String primitiveElmType = primitiveTypes.get(type);
        if (primitiveElmType != null)
            return primitiveElmType;
        if (type.isList() || type.isFuncList()) {
            String elmType = elmListType(type);
            return elmType;
        }
        if (type.isMap() || type.isFuncMap()) {
            String elmType = elmMapType(type);
            return elmType;
        }
        if (type.isOptional() || type.isNullable()) {
            String elmType = elmMayBeType(type);
            return elmType;
        }
        return null;
    }
    
    private static String elmListType(Type type) {
        Type   aType   = type.generics().get(0).toType();
        String aStrg   = param(aType);
        String elmType = "List " + aStrg;
        return elmType;
    }
    
    private static String elmMapType(Type type) {
        Type   kType   = type.generics().get(0).toType();
        String kStrg   = param(kType);
        Type   vType   = type.generics().get(1).toType();
        String vStrg   = param(vType);
        String elmType = "Dict " + kStrg + " " + vStrg;
        return elmType;
    }
    
    public static String elmMayBeType(Type type) {
        Type   aType   = type.generics().get(0).toType();
        String aStrg   = param(aType);
        String elmType = "Maybe " + aStrg;
        return elmType;
    }
    
    public static String elmMayBeOfType(Type type) {
        String aStrg   = param(type);
        String elmType = "Maybe " + aStrg;
        return elmType;
    }
    
    private static String param(Type type) {
        String emlType   = emlType(type);
        String paramType = wrap(emlType);
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
            Type   paramType    = isOptionalType ? type.generics().get(0).toType() : type;
            String paramEncoder = encoderNameOf(paramType, name);
            return "Maybe.withDefault Json.Encode.null (Maybe.map " + paramEncoder + ")";
        }
        String primitiveElmType = primitiveTypes.get(type);
        if (primitiveElmType != null)
            return "Json.Encode." + primitiveElmType.toLowerCase() + " " + name;
        String typeName = toCamelCase(type.simpleName());
        if (type.isList() || type.isFuncList()) {
            Type   paramType            = type.generics().get(0).toType();
            String genericPrimitiveType = primitiveTypes.get(paramType);
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
        String primitiveElmType = primitiveTypes.get(type);
        if (primitiveElmType != null)
            return "Json.Decode." + primitiveElmType.toLowerCase();
        
        String typeName = Utils.toCamelCase(type.simpleName());
        if (type.isList() || type.isFuncList())
            return typeName + "ListDecode";
        
        if (type.isMap() || type.isFuncMap()) {
            throw new UnsupportedOperationException("Decoder of map type is not yet support.");
        }
        if (type.isOptional() || type.isNullable()) {
            throw new UnsupportedOperationException("Decoder of optional type is not yet support.");
        }
        return typeName + "Decoder";
    }
}
