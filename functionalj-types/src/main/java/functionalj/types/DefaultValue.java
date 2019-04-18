// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types;

import java.util.Optional;

import functionalj.types.struct.generator.Type;
import lombok.val;

public enum DefaultValue {
    
    REQUIRED,
    UNSPECIFIED,
    ZERO,
    ONE,
    MINUS_ONE,
    MAX_VALUE,
    MIN_VALUE,
    POSITIVE_INFINITY,
    NEGATIVE_INFINITY,
    NaN,
    FALSE,
    TRUE,
    NULL,
    EMPTY,
    SPACE,
    DEFAULT;
    
    public static boolean isSuitable(Type type, DefaultValue value) {
        if (value == REQUIRED)
            return true;
        if (value == DEFAULT)
            return true;
        
        if (value == UNSPECIFIED)
            return true;
        
        boolean posibleNumber = (value == ZERO)
                || (value == ONE)
                || (value == MINUS_ONE)
                || (value == MAX_VALUE)
                || (value == MIN_VALUE);
        if (Type.primitiveTypes.containsValue(type)) {
            if (Type.BOOL.equals(type))
                return ((value == TRUE) || (value == FALSE));
            
            if (Type.CHR.equals(type))
                return ((value == ZERO) || (value == SPACE));
            
            if ((Type.FLT.equals(type) || Type.DBL.equals(type))
             && ((value == POSITIVE_INFINITY) || (value == NEGATIVE_INFINITY) || (value == NaN)))
                return true;
            
            if ((Type.BIGINTEGER.equals(type) || Type.BIGDECIMAL.equals(type))
             && !((value == POSITIVE_INFINITY) || (value == NEGATIVE_INFINITY) || (value == NaN)
               || (value == MAX_VALUE)         || (value == MIN_VALUE)))
                       return true;
            
            return posibleNumber;
        }
        
        if (posibleNumber) {
            return Type.BYTE      .equals(type)
                || Type.SHORT     .equals(type)
                || Type.INTEGER   .equals(type)
                || Type.LONG      .equals(type)
                || Type.FLOAT     .equals(type)
                || Type.DOUBLE    .equals(type)
                || Type.BIGINTEGER.equals(type)
                || Type.BIGDECIMAL.equals(type);
        }
        
        if (value == NULL)
            return true;
        
        if (Type.STR.equals(type) 
         || Type.STRING.equals(type))
            return ((value != MAX_VALUE) || (value != MIN_VALUE));
        
        if (Type.LIST.equals(type)
         || Type.MAP.equals(type)
         || Type.FUNC_LIST.equals(type)
         || Type.FUNC_MAP.equals(type)
         || Type.NULLABLE.equals(type)
         || Type.OPTIONAL.equals(type))
            return (value == EMPTY);
        
        return false;
    }
    
    public static DefaultValue getUnspecfiedValue(Type type) {
        if (Type.primitiveTypes.containsValue(type)) {
            if (Type.BOOL.equals(type))
                return TRUE;
            
            if (Type.CHR.equals(type))
                return SPACE;
            
            return ZERO;
        }
        if (Type.NULLABLE .equals(type)
         || Type.OPTIONAL .equals(type)
         || Type.LIST     .equals(type)
         || Type.MAP      .equals(type)
         || Type.FUNC_LIST.equals(type)
         || Type.FUNC_MAP .equals(type))
            return EMPTY;
        
        return NULL;
    }
    
    public static String defaultValueCode(Type type, DefaultValue value) {
        if (value == UNSPECIFIED)
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        if (value == DEFAULT)
            return "IProvideDefault.defaultProvider().get().get(" + type.fullName() + ")";
        if (value == NULL)
            return "null";
        
        if (Type.BOOL.equals(type) || Type.BOOLEAN.equals(type)) {
            if (value == TRUE)  return "true";
            if (value == FALSE) return "false";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BYT.equals(type) || Type.BYTE.equals(type)) {
            if (value == ZERO)      return "(byte)0";
            if (value == ONE)       return "(byte)1";
            if (value == MINUS_ONE) return "(byte)-1";
            if (value == MAX_VALUE) return "Byte.MAX_VALUE";
            if (value == MIN_VALUE) return "Byte.MIN_VALUE";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.SHRT.equals(type) || Type.SHORT.equals(type)) {
            if (value == ZERO)      return "(short)0";
            if (value == ONE)       return "(short)1";
            if (value == MINUS_ONE) return "(short)-1";
            if (value == MAX_VALUE) return "Short.MAX_VALUE";
            if (value == MIN_VALUE) return "Short.MIN_VALUE";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.INT.equals(type) || Type.INTEGER.equals(type)) {
            if (value == ZERO)      return "0";
            if (value == ONE)       return "1";
            if (value == MINUS_ONE) return "-1";
            if (value == MAX_VALUE) return "Integer.MAX_VALUE";
            if (value == MIN_VALUE) return "Integer.MIN_VALUE";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.LNG.equals(type) || Type.LONG.equals(type)) {
            if (value == ZERO)      return "(long)0";
            if (value == ONE)       return "(long)1";
            if (value == MINUS_ONE) return "(long)-1";
            if (value == MAX_VALUE) return "Long.MAX_VALUE";
            if (value == MIN_VALUE) return "Long.MIN_VALUE";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FLT.equals(type) || Type.FLOAT.equals(type)) {
            if (value == ZERO)      return "(float)0.0";
            if (value == ONE)       return "(float)1.0";
            if (value == MINUS_ONE) return "(float)-1.0";
            if (value == MAX_VALUE) return "Float.MAX_VALUE";
            if (value == MIN_VALUE) return "-Float.MIN_VALUE";
            if (value == POSITIVE_INFINITY) return "Float.POSITIVE_INFINITY";
            if (value == NEGATIVE_INFINITY) return "Float.NEGATIVE_INFINITY";
            if (value == NaN)               return "Float.NaN";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.DBL.equals(type) || Type.DOUBLE.equals(type)) {
            if (value == ZERO)      return "0.0";
            if (value == ONE)       return "1.0";
            if (value == MINUS_ONE) return "-1.0";
            if (value == MAX_VALUE) return "Double.MAX_VALUE";
            if (value == MIN_VALUE) return "-Double.MIN_VALUE";
            if (value == POSITIVE_INFINITY) return "Double.POSITIVE_INFINITY";
            if (value == NEGATIVE_INFINITY) return "Double.NEGATIVE_INFINITY";
            if (value == NaN)               return "Double.NaN";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGINTEGER.equals(type)) {
            if (value == ZERO)      return "java.math.BigInteger.ZERO";
            if (value == ONE)       return "java.math.BigInteger.ONE";
            if (value == MINUS_ONE) return "java.math.BigInteger.NEGATIVE_ONE";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGDECIMAL.equals(type)) {
            if (value == ZERO)      return "java.math.BigDecimal.ZERO";
            if (value == ONE)       return "java.math.BigDecimal.ONE";
            if (value == MINUS_ONE) return "new java.math.BigDecimal(\"-1\")";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        
        if (Type.STRING.equals(type)
         || Type.STRING.equals(type)) {
            if (value == EMPTY) return "\"\"";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.LIST.equals(type)) {
            if (value == EMPTY) return "java.util.Collections.emptyList()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FUNC_LIST.equals(type)) {
            if (value == EMPTY) return "functionalj.list.FuncList.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.MAP.equals(type)) {
            if (value == EMPTY) return "java.util.Collections.emptyMap()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FUNC_MAP.equals(type)) {
            if (value == EMPTY) return "functionalj.list.FuncMap.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.NULLABLE.equals(type)) {
            if (value == EMPTY) return "nawaman.nullablej.nullable.Nullable.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.OPTIONAL.equals(type)) {
            if (value == EMPTY) return "java.util.Optional.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        
        throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
    }
    
    @SuppressWarnings({ "rawtypes" })
    public static Object defaultValue(Type type, DefaultValue value) {
        if (value == UNSPECIFIED)
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        if (value == DEFAULT) {
            try {
                val clzz      = new Type("IProvideDefault", "nawaman.defaultj.api").toClass();
                val dpMethod  = clzz.getMethod("defaultProvider");
                val dpOption  = (Optional)dpMethod.invoke(clzz);
                val dp        = dpOption.get();
                val getMethod = clzz.getMethod("get", Class.class);
                val retValue  = getMethod.invoke(dp, type.toClass());
                return retValue;
            } catch (Exception e) {
                throw new StructConversionException(e);
            }
        }
        if (value == NULL)
            return null;
        
        if (Type.BYT.equals(type)) {
            if (value == ZERO)      return (byte)0;
            if (value == ONE)       return (byte)1;
            if (value == MINUS_ONE) return (byte)-1;
            if (value == MAX_VALUE) return Byte.MAX_VALUE;
            if (value == MIN_VALUE) return Byte.MIN_VALUE;
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.SHRT.equals(type)) {
            if (value == ZERO)      return (short)0;
            if (value == ONE)       return (short)1;
            if (value == MINUS_ONE) return (short)-1;
            if (value == MAX_VALUE) return Short.MAX_VALUE;
            if (value == MIN_VALUE) return Short.MIN_VALUE;
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.INT.equals(type)) {
            if (value == ZERO)      return (int)0;
            if (value == ONE)       return (int)1;
            if (value == MINUS_ONE) return (int)-1;
            if (value == MAX_VALUE) return Integer.MAX_VALUE;
            if (value == MIN_VALUE) return Integer.MIN_VALUE;
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.LNG.equals(type)) {
            if (value == ZERO)      return (long)0;
            if (value == ONE)       return (long)1;
            if (value == MINUS_ONE) return (long)-1;
            if (value == MAX_VALUE) return Long.MAX_VALUE;
            if (value == MIN_VALUE) return Long.MIN_VALUE;
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FLT.equals(type)) {
            if (value == ZERO)      return (float)0.0;
            if (value == ONE)       return (float)1.0;
            if (value == MINUS_ONE) return (float)-1.0;
            if (value == MAX_VALUE) return Float.MAX_VALUE;
            if (value == MIN_VALUE) return -Float.MIN_VALUE;
            if (value == POSITIVE_INFINITY) return Float.POSITIVE_INFINITY;
            if (value == NEGATIVE_INFINITY) return Float.NEGATIVE_INFINITY;
            if (value == NaN)               return Float.NaN;
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.DBL.equals(type)) {
            if (value == ZERO)      return (double)0.0;
            if (value == ONE)       return (double)1.0;
            if (value == MINUS_ONE) return (double)-1.0;
            if (value == MAX_VALUE) return Double.MAX_VALUE;
            if (value == MIN_VALUE) return -Double.MIN_VALUE;
            if (value == POSITIVE_INFINITY) return Double.POSITIVE_INFINITY;
            if (value == NEGATIVE_INFINITY) return Double.NEGATIVE_INFINITY;
            if (value == NaN)               return Double.NaN;
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGINTEGER.equals(type)) {
            if (value == ZERO)      return java.math.BigInteger.ZERO;
            if (value == ONE)       return java.math.BigInteger.ONE;
            if (value == MINUS_ONE) return new java.math.BigInteger("-1");
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGDECIMAL.equals(type)) {
            if (value == ZERO)      return java.math.BigDecimal.ZERO;
            if (value == ONE)       return java.math.BigDecimal.ONE;
            if (value == MINUS_ONE) return new java.math.BigDecimal("-1");
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        
        if (Type.LIST.equals(type)) {
            if (value == EMPTY)
                return java.util.Collections.emptyList();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FUNC_LIST.equals(type)) {
            if (value == EMPTY) {
                return getEmpty(type);
            }
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.MAP.equals(type)) {
            if (value == EMPTY)
                return java.util.Collections.emptyMap();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FUNC_MAP.equals(type)) {
            if (value == EMPTY)
                return getEmpty(type);
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.NULLABLE.equals(type)) {
            if (value == EMPTY)
                return getEmpty(type);
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.OPTIONAL.equals(type)) {
            if (value == EMPTY)
                return getEmpty(type);
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        
        throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
    }
    
    private static Object getEmpty(Type type) {
        try {
            val funcList = type.toClass();
            val method   = funcList.getMethod("empty");
            val empty    = method.invoke(funcList);
            return empty;
        } catch (Exception e) {
            throw new StructConversionException(e);
        }
    }
    
}
