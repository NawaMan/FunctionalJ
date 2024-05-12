// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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
    RANDOM,
    NOW,
    DEFAULT;
    
    public static final Random RAND = new Random();
    
    public static final String RANDSTR = DefaultValue.class.getCanonicalName() + ".RAND";
    
    public static byte randomByte() {
        byte[] bytes = new byte[1];
        RAND.nextBytes(bytes);
        return bytes[0];
    }
    
    public static short randomShort() {
        return (short) (RAND.nextInt(65536) - 32768);
    }
    
    public static BigInteger randomBigInteger() {
        return BigInteger.valueOf(RAND.nextLong());
    }
    
    public static BigDecimal randomBigDecimal() {
        return BigDecimal.valueOf(RAND.nextDouble());
    }
    
    public static boolean isSuitable(Type type, DefaultValue value) {
        if (value == REQUIRED)
            return true;
        if (value == DEFAULT)
            return true;
        if (value == UNSPECIFIED)
            return true;
        if (value == RANDOM) {
            return Type.primitiveTypes.containsValue(type) || Type.declaredTypes.containsValue(type) || Type.STR.equals(type) || Type.STRING.equals(type) || Type.OBJECT.equals(type) || Type.BIGDECIMAL.equals(type) || Type.BIGINTEGER.equals(type) || Type.UUID.equals(type);
        }
        if (value == NOW) {
            return Type.temporalTypes.containsValue(type) || Type.LNG.equals(type) || Type.LONG.equals(type) || Type.STR.equals(type) || Type.STRING.equals(type);
        }
        boolean posibleNumber = (value == ZERO) || (value == ONE) || (value == MINUS_ONE) || (value == MAX_VALUE) || (value == MIN_VALUE);
        if (Type.primitiveTypes.containsValue(type)) {
            if (Type.BOOL.equals(type))
                return ((value == TRUE) || (value == FALSE));
            if (Type.CHR.equals(type))
                return ((value == ZERO) || (value == SPACE));
            if ((Type.FLT.equals(type) || Type.DBL.equals(type)) && ((value == POSITIVE_INFINITY) || (value == NEGATIVE_INFINITY) || (value == NaN)))
                return true;
            return posibleNumber;
        }
        if (posibleNumber) {
            return Type.BYTE.equals(type) || Type.SHORT.equals(type) || Type.INTEGER.equals(type) || Type.LONG.equals(type) || Type.FLOAT.equals(type) || Type.DOUBLE.equals(type) || Type.BIGINTEGER.equals(type) || Type.BIGDECIMAL.equals(type);
        }
        if ((Type.BIGINTEGER.equals(type) || Type.BIGDECIMAL.equals(type)) && !((value == POSITIVE_INFINITY) || (value == NEGATIVE_INFINITY) || (value == NaN) || (value == MAX_VALUE) || (value == MIN_VALUE)))
            return true;
        if (value == NULL)
            return true;
        if (Type.STR.equals(type) || Type.STRING.equals(type))
            return ((value != MAX_VALUE) || (value != MIN_VALUE));
        if (type.isList() || type.isMap() || type.isFuncList() || type.isFuncMap() || type.isNullable() || type.isOptional())
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
        if (Type.temporalTypes.containsValue(type))
            return NOW;
        if (type == Type.OBJECT)
            return NULL;
        if (type == Type.UUID)
            return RANDOM;
        if (Type.NULLABLE.equals(type) || Type.OPTIONAL.equals(type) || Type.LIST.equals(type) || Type.MAP.equals(type) || Type.FUNC_LIST.equals(type) || Type.FUNC_MAP.equals(type))
            return EMPTY;
        return NULL;
    }
    
    public static String defaultValueCode(Type type, DefaultValue value) {
        if (value == REQUIRED)
            return IStruct.class.getCanonicalName() + ".$utils.notNull((" + type.fullNameWithGeneric(type.packageName()) + ")null)";
        if (value == UNSPECIFIED)
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        if (value == DEFAULT)
            return "IProvideDefault.defaultProvider().get().get(" + type.fullName() + ")";
        if (value == NULL)
            return "null";
        if (Type.BOOL.equals(type) || Type.BOOLEAN.equals(type)) {
            if (value == TRUE)
                return "true";
            if (value == FALSE)
                return "false";
            if (value == RANDOM)
                return RANDSTR + ".nextBoolean()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BYT.equals(type) || Type.BYTE.equals(type)) {
            if (value == ZERO)
                return "(byte)0";
            if (value == ONE)
                return "(byte)1";
            if (value == MINUS_ONE)
                return "(byte)-1";
            if (value == MAX_VALUE)
                return "Byte.MAX_VALUE";
            if (value == MIN_VALUE)
                return "Byte.MIN_VALUE";
            if (value == RANDOM)
                return DefaultValue.class.getCanonicalName() + ".randomByte()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.SHRT.equals(type) || Type.SHORT.equals(type)) {
            if (value == ZERO)
                return "(short)0";
            if (value == ONE)
                return "(short)1";
            if (value == MINUS_ONE)
                return "(short)-1";
            if (value == MAX_VALUE)
                return "Short.MAX_VALUE";
            if (value == MIN_VALUE)
                return "Short.MIN_VALUE";
            if (value == RANDOM)
                return DefaultValue.class.getCanonicalName() + ".randomShort()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.INT.equals(type) || Type.INTEGER.equals(type)) {
            if (value == ZERO)
                return "0";
            if (value == ONE)
                return "1";
            if (value == MINUS_ONE)
                return "-1";
            if (value == MAX_VALUE)
                return "Integer.MAX_VALUE";
            if (value == MIN_VALUE)
                return "Integer.MIN_VALUE";
            if (value == RANDOM)
                return RANDSTR + ".nextInt()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.LNG.equals(type) || Type.LONG.equals(type)) {
            if (value == ZERO)
                return "(long)0";
            if (value == ONE)
                return "(long)1";
            if (value == MINUS_ONE)
                return "(long)-1";
            if (value == MAX_VALUE)
                return "Long.MAX_VALUE";
            if (value == MIN_VALUE)
                return "Long.MIN_VALUE";
            if (value == RANDOM)
                return RANDSTR + ".nextLong()";
            if (value == NOW)
                return "System.currentTimeMillis()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FLT.equals(type) || Type.FLOAT.equals(type)) {
            if (value == ZERO)
                return "(float)0.0";
            if (value == ONE)
                return "(float)1.0";
            if (value == MINUS_ONE)
                return "(float)-1.0";
            if (value == MAX_VALUE)
                return "Float.MAX_VALUE";
            if (value == MIN_VALUE)
                return "-Float.MIN_VALUE";
            if (value == POSITIVE_INFINITY)
                return "Float.POSITIVE_INFINITY";
            if (value == NEGATIVE_INFINITY)
                return "Float.NEGATIVE_INFINITY";
            if (value == NaN)
                return "Float.NaN";
            if (value == RANDOM)
                return RANDSTR + ".nextFloat()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.DBL.equals(type) || Type.DOUBLE.equals(type)) {
            if (value == ZERO)
                return "0.0";
            if (value == ONE)
                return "1.0";
            if (value == MINUS_ONE)
                return "-1.0";
            if (value == MAX_VALUE)
                return "Double.MAX_VALUE";
            if (value == MIN_VALUE)
                return "-Double.MIN_VALUE";
            if (value == POSITIVE_INFINITY)
                return "Double.POSITIVE_INFINITY";
            if (value == NEGATIVE_INFINITY)
                return "Double.NEGATIVE_INFINITY";
            if (value == NaN)
                return "Double.NaN";
            if (value == RANDOM)
                return RANDSTR + ".nextDouble()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGINTEGER.equals(type)) {
            if (value == ZERO)
                return "java.math.BigInteger.ZERO";
            if (value == ONE)
                return "java.math.BigInteger.ONE";
            if (value == MINUS_ONE)
                return "java.math.BigInteger.NEGATIVE_ONE";
            if (value == RANDOM)
                return DefaultValue.class.getCanonicalName() + ".randomBigInteger()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGDECIMAL.equals(type)) {
            if (value == ZERO)
                return "java.math.BigDecimal.ZERO";
            if (value == ONE)
                return "java.math.BigDecimal.ONE";
            if (value == MINUS_ONE)
                return "new java.math.BigDecimal(\"-1\")";
            if (value == RANDOM)
                return DefaultValue.class.getCanonicalName() + ".randomBigDecimal()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.OBJECT.equals(type)) {
            if (value == RANDOM)
                return "new Object()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.UUID.equals(type)) {
            if (value == RANDOM)
                return UUID.class.getCanonicalName() + ".randomUUID()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.temporalTypes.containsValue(type)) {
            if (value == NOW) {
                if (Core.DayOfWeek.type().equals(type))
                    return LocalDate.class.getCanonicalName() + ".now().getDayOfWeek()";
                if (Core.Month.type().equals(type))
                    return LocalDate.class.getCanonicalName() + ".now().getMonth()";
                if (Core.Instant.type().equals(type))
                    return Instant.class.getCanonicalName() + ".now()";
                if (Core.LocalDate.type().equals(type))
                    return LocalDate.class.getCanonicalName() + ".now()";
                if (Core.LocalDateTime.type().equals(type))
                    return LocalDateTime.class.getCanonicalName() + ".now()";
                if (Core.LocalTime.type().equals(type))
                    return LocalTime.class.getCanonicalName() + ".now()";
                if (Core.OffsetDateTime.type().equals(type))
                    return OffsetDateTime.class.getCanonicalName() + ".now()";
                if (Core.ZonedDateTime.type().equals(type))
                    return ZonedDateTime.class.getCanonicalName() + ".now()";
                throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
            }
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.STR.equals(type) || Type.STRING.equals(type)) {
            if (value == EMPTY)
                return "\"\"";
            if (value == RANDOM)
                return UUID.class.getCanonicalName() + ".randomUUID().toString()";
            if (value == NOW)
                return DateTimeFormatter.class.getCanonicalName() + ".ISO_LOCAL_DATE_TIME";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (type.isList()) {
            if (value == EMPTY)
                return "java.util.Collections.emptyList()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (type.isFuncList()) {
            if (value == EMPTY)
                return "functionalj.list.FuncList.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (type.isMap()) {
            if (value == EMPTY)
                return "java.util.Collections.emptyMap()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (type.isFuncMap()) {
            if (value == EMPTY)
                return "functionalj.list.FuncMap.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (type.isNullable()) {
            if (value == EMPTY)
                return "nullablej.nullable.Nullable.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (type.isOptional()) {
            if (value == EMPTY)
                return "java.util.Optional.empty()";
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Object defaultValue(Type type, DefaultValue value) {
        if (value == REQUIRED)
            throw new NullPointerException("Value is required. (type=" + type + ", defaultValue=" + value + ")");
        if (value == UNSPECIFIED)
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        if (value == DEFAULT) {
            try {
                Class    clzz      = new Type("nawaman.defaultj.api", "IProvideDefault").toClass();
                Method   dpMethod  = clzz.getMethod("defaultProvider");
                Optional dpOption  = (Optional) dpMethod.invoke(clzz);
                Object   dp        = dpOption.get();
                Method   getMethod = clzz.getMethod("get", Class.class);
                Object   retValue = getMethod.invoke(dp, type.toClass());
                return retValue;
            } catch (Exception e) {
                throw new StructConversionException(e);
            }
        }
        if (value == NULL)
            return null;
        if (Type.BYT.equals(type)) {
            if (value == ZERO)
                return (byte) 0;
            if (value == ONE)
                return (byte) 1;
            if (value == MINUS_ONE)
                return (byte) -1;
            if (value == MAX_VALUE)
                return Byte.MAX_VALUE;
            if (value == MIN_VALUE)
                return Byte.MIN_VALUE;
            if (value == RANDOM)
                return randomByte();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.SHRT.equals(type)) {
            if (value == ZERO)
                return (short) 0;
            if (value == ONE)
                return (short) 1;
            if (value == MINUS_ONE)
                return (short) -1;
            if (value == MAX_VALUE)
                return Short.MAX_VALUE;
            if (value == MIN_VALUE)
                return Short.MIN_VALUE;
            if (value == RANDOM)
                return randomShort();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.INT.equals(type)) {
            if (value == ZERO)
                return (int) 0;
            if (value == ONE)
                return (int) 1;
            if (value == MINUS_ONE)
                return (int) -1;
            if (value == MAX_VALUE)
                return Integer.MAX_VALUE;
            if (value == MIN_VALUE)
                return Integer.MIN_VALUE;
            if (value == RANDOM)
                return RAND.nextInt();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.LNG.equals(type)) {
            if (value == ZERO)
                return (long) 0;
            if (value == ONE)
                return (long) 1;
            if (value == MINUS_ONE)
                return (long) -1;
            if (value == MAX_VALUE)
                return Long.MAX_VALUE;
            if (value == MIN_VALUE)
                return Long.MIN_VALUE;
            if (value == RANDOM)
                return RAND.nextLong();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.FLT.equals(type)) {
            if (value == ZERO)
                return (float) 0.0;
            if (value == ONE)
                return (float) 1.0;
            if (value == MINUS_ONE)
                return (float) -1.0;
            if (value == MAX_VALUE)
                return Float.MAX_VALUE;
            if (value == MIN_VALUE)
                return -Float.MIN_VALUE;
            if (value == POSITIVE_INFINITY)
                return Float.POSITIVE_INFINITY;
            if (value == NEGATIVE_INFINITY)
                return Float.NEGATIVE_INFINITY;
            if (value == NaN)
                return Float.NaN;
            if (value == RANDOM)
                return RAND.nextFloat();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.DBL.equals(type)) {
            if (value == ZERO)
                return (double) 0.0;
            if (value == ONE)
                return (double) 1.0;
            if (value == MINUS_ONE)
                return (double) -1.0;
            if (value == MAX_VALUE)
                return Double.MAX_VALUE;
            if (value == MIN_VALUE)
                return -Double.MIN_VALUE;
            if (value == POSITIVE_INFINITY)
                return Double.POSITIVE_INFINITY;
            if (value == NEGATIVE_INFINITY)
                return Double.NEGATIVE_INFINITY;
            if (value == NaN)
                return Double.NaN;
            if (value == RANDOM)
                return RAND.nextDouble();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGINTEGER.equals(type)) {
            if (value == ZERO)
                return java.math.BigInteger.ZERO;
            if (value == ONE)
                return java.math.BigInteger.ONE;
            if (value == MINUS_ONE)
                return new java.math.BigInteger("-1");
            if (value == RANDOM)
                return randomBigInteger();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.BIGDECIMAL.equals(type)) {
            if (value == ZERO)
                return java.math.BigDecimal.ZERO;
            if (value == ONE)
                return java.math.BigDecimal.ONE;
            if (value == MINUS_ONE)
                return new java.math.BigDecimal("-1");
            if (value == RANDOM)
                return randomBigDecimal();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.OBJECT.equals(type)) {
            if (value == RANDOM)
                return new Object();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.UUID.equals(type)) {
            if (value == RANDOM)
                return UUID.randomUUID();
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.temporalTypes.containsValue(type)) {
            if (value == NOW) {
                if (Core.DayOfWeek.type().equals(type))
                    return LocalDate.now().getDayOfWeek();
                if (Core.Month.type().equals(type))
                    return LocalDate.now().getMonth();
                if (Core.Instant.type().equals(type))
                    return Instant.now();
                if (Core.LocalDate.type().equals(type))
                    return LocalDate.now();
                if (Core.LocalDateTime.type().equals(type))
                    return LocalDateTime.now();
                if (Core.LocalTime.type().equals(type))
                    return LocalTime.now();
                if (Core.OffsetDateTime.type().equals(type))
                    return OffsetDateTime.now();
                if (Core.ZonedDateTime.type().equals(type))
                    return ZonedDateTime.now();
                throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
            }
            throw new IllegalArgumentException("Type: " + type + ", Value: " + value);
        }
        if (Type.STR.equals(type) || Type.STRING.equals(type)) {
            if (value == EMPTY)
                return "";
            if (value == RANDOM)
                return UUID.randomUUID().toString();
            if (value == NOW)
                return DateTimeFormatter.class.getCanonicalName() + ".ISO_LOCAL_DATE_TIME";
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Object getEmpty(Type type) {
        try {
            Class  funcList = type.toClass();
            Method method   = funcList.getMethod("empty");
            Object empty    = method.invoke(funcList);
            return empty;
        } catch (Exception e) {
            throw new StructConversionException(e);
        }
    }
}
