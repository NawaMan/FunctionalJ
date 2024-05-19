package functionalj.types.typescript.processor;

import static functionalj.types.Type.BOOL;
import static functionalj.types.Type.BOOLEAN;
import static functionalj.types.Type.BYT;
import static functionalj.types.Type.BYTE;
import static functionalj.types.Type.CHARACTER;
import static functionalj.types.Type.CHR;
import static functionalj.types.Type.DBL;
import static functionalj.types.Type.DOUBLE;
import static functionalj.types.Type.FLOAT;
import static functionalj.types.Type.FLT;
import static functionalj.types.Type.INT;
import static functionalj.types.Type.INTEGER;
import static functionalj.types.Type.LNG;
import static functionalj.types.Type.LONG;
import static functionalj.types.Type.SHORT;
import static functionalj.types.Type.SHRT;
import static functionalj.types.Type.STR;
import static functionalj.types.Type.STRING;

import java.util.HashMap;
import java.util.Map;

import functionalj.types.Type;

/**
 * This class provides a way to convert Java types to TypeScript types.
 **/
public class TypeScriptTypes {
    
    private TypeScriptTypes() {
    }
    
    private static final Map<String, String> tsBasicTypes = new HashMap<>();
    static {
        tsBasicTypes.put(BYT.toString(),     "number");
        tsBasicTypes.put(SHRT.toString(),    "number");
        tsBasicTypes.put(INT.toString(),     "number");
        tsBasicTypes.put(LNG.toString(),     "number");
        tsBasicTypes.put(BYTE.toString(),    "number");
        tsBasicTypes.put(SHORT.toString(),   "number");
        tsBasicTypes.put(INTEGER.toString(), "number");
        tsBasicTypes.put(LONG.toString(),    "number");
        tsBasicTypes.put(FLT.toString(),     "number");
        tsBasicTypes.put(DBL.toString(),     "number");
        tsBasicTypes.put(FLOAT.toString(),   "number");
        tsBasicTypes.put(DOUBLE.toString(),  "number");
        
        tsBasicTypes.put(BOOL.toString(),    "boolean");
        tsBasicTypes.put(BOOLEAN.toString(), "boolean");
        
        tsBasicTypes.put(CHR.toString(),       "string");
        tsBasicTypes.put(CHARACTER.toString(), "string");
        tsBasicTypes.put(STR.toString(),       "string");
        tsBasicTypes.put(STRING.toString(),    "string");
//        
//        javaToTypeScriptMap.put("Map", "Map");
//        javaToTypeScriptMap.put("Object", "any");
//        
//        public static final Type BIGINTEGER = Type.of(BigInteger.class);
//        public static final Type BIGDECIMAL = Type.of(BigDecimal.class);
//        public static final Type UUID = Type.of(java.util.UUID.class);
//        public static final Type FUNC_LIST = Core.FuncList.type();
//        public static final Type FUNC_MAP = Core.FuncMap.type();
//        
//        public static final Type OBJECT = Type.of(Object.class);
    }
    
    /** @return the TypeScript type for the given Java type. */
    public static String toTypeScriptType(Type type) {
        if (type.isList() || type.isFuncList()) {
            return tsArrayType(type);
        }
        if (type.isMap() || type.isFuncMap()) {
            return tsMapType(type);
        }
        if (type.isNullable() || type.isOptional()) {
            Type   paramType   = type.generics().get(0).toType();
            String paramTsType =  toTypeScriptType(paramType);
            return paramTsType + "| null";
        }
        
        String tsType = tsBasicTypes.get(type.toString());
        if (tsType != null) {
            return tsType;
        }
        
        return "any";
    }
    
    private static String tsMapType(Type type) {
        Type   keyType     = type.generics().get(0).toType();
        Type   valueType   = type.generics().get(1).toType();
        String keyTsType   =  toTypeScriptType(keyType);
        String valueTsType =  toTypeScriptType(valueType);
        return "Map<" + keyTsType + ", " + valueTsType + ">";
    }
    
    private static String tsArrayType(Type type) {
        Type   paramType   = type.generics().get(0).toType();
        String paramTsType =  toTypeScriptType(paramType);
        String tsType = paramTsType + "[]";
        return tsType;
    }
    
}
