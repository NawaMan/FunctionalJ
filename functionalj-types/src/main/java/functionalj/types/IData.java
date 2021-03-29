// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import functionalj.types.choice.ChoiceTypes;
import functionalj.types.choice.IChoice;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.Getter;
import lombok.val;


public interface IData {
    
    public Map<String, Object> __toMap();
    
    
    public static <DATA extends IData> Optional<DATA> fromMap(Map<String, Object> map, Class<DATA> clazz) {
        return Optional.ofNullable(IData.$utils.fromMap(map, clazz));
    }
    
    
    public static class $utils {
        
        public static Supplier<Object> defaultValueOf(Type type, DefaultValue defaultValue) {
            return ()->DefaultValue.defaultValue(type, defaultValue);
        }
        
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        
        // == To and from Map ==
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static <D extends IData> D fromMap(Map<String, Object> map, Class<D> clazz) {
            if (IStruct.class.isAssignableFrom(clazz))
                return (D)IStruct.fromMap(map, (Class<IStruct>)clazz);
            if (IChoice.class.isAssignableFrom(clazz))
                return (D)IChoice.fromMap(map, (Class<IChoice>)clazz);
            
            throw new DataConversionException(clazz);
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static Object toMapValueObject(Object data) {
            if (data instanceof List) {
                return ((List)data).stream()
                        .map(IData.$utils::toMapValueObject)
                        .collect(toList());
            }
            
            return (data instanceof IData)
                    ? ((IData)data).__toMap()
                    : data;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static <T> T fromMapValue(
                Object           obj,
                Class<T>         clzz,
                DefaultValue     defaultValue,
                Supplier<Object> defaultValueSupplier) {
            if ((obj == null) && ((defaultValue == DefaultValue.UNSPECIFIED) || (defaultValue == DefaultValue.NULL)))
                return null;
            
            if ((obj instanceof Map) && IStruct.class.isAssignableFrom(clzz))
                return (T)IStruct.fromMap((Map)obj, (Class)clzz);
            if ((obj instanceof Map) && IChoice.class.isAssignableFrom(clzz))
                return (T)IChoice.fromMap((Map)obj, (Class)clzz);
            
            if (obj != null) {
                if (obj instanceof String) {
                    return extractFromStringValue(obj, clzz);
                }
                // The value is sorted of a number so we try it as a timestamp.
                if ((obj instanceof Byte) 
                 || (obj instanceof Short) 
                 || (obj instanceof Integer) 
                 || (obj instanceof Long) 
                 || (obj instanceof Float) 
                 || (obj instanceof Double)) {
                    val seconds = new BigDecimal(obj.toString()).longValue();
                    val instant = Instant.ofEpochSecond(seconds);
                    try {
                        if (Instant.class.isAssignableFrom(clzz)) {
                            return (T)instant;
                        }
                        if (LocalDate.class.isAssignableFrom(clzz)) {
                            return (T)LocalDate.from(instant.atZone(ZoneId.systemDefault()));
                        }
                        if (LocalDateTime.class.isAssignableFrom(clzz)) {
                            return (T)LocalDateTime.from(instant.atZone(ZoneId.systemDefault()));
                        }
                        if (LocalTime.class.isAssignableFrom(clzz)) {
                            return (T)LocalTime.from(instant.atZone(ZoneId.systemDefault()));
                        }
                        if (ZonedDateTime.class.isAssignableFrom(clzz)) {
                            return (T)instant.atZone(ZoneId.systemDefault());
                        }
                    } catch (DateTimeException exception) {
                        // So the number is not a valid timestamp.
                    }
                }
                return (T)obj;
            }
            
            val value = defaultValueSupplier.get();
            return (T)value;
        }
        
        // TODO - Find the better/more maintainable way to do this.
        // TODO - It will also nice if we can specify pattern to parse date-time
        @SuppressWarnings("unchecked")
        private static <T> T extractFromStringValue(Object obj, Class<T> clzz) {
            if (CharSequence.class.isAssignableFrom(clzz)) {
                return (T)obj;
            }
            if (byte[].class.isAssignableFrom(clzz)) {
                return (T)((String)obj).getBytes();
            }
            
            // Byte, Short, Integer, Long, Float, Double.
            if (byte.class.isAssignableFrom(clzz) || Byte.class.isAssignableFrom(clzz)) {
                return (T)Byte.valueOf((String)obj);
            }
            if (short.class.isAssignableFrom(clzz) || Short.class.isAssignableFrom(clzz)) {
                return (T)Short.valueOf((String)obj);
            }
            if (int.class.isAssignableFrom(clzz) || Integer.class.isAssignableFrom(clzz)) {
                return (T)Integer.valueOf((String)obj);
            }
            if (long.class.isAssignableFrom(clzz) || Long.class.isAssignableFrom(clzz)) {
                return (T)Long.valueOf((String)obj);
            }
            if (float.class.isAssignableFrom(clzz) || Float.class.isAssignableFrom(clzz)) {
                return (T)Float.valueOf((String)obj);
            }
            if (double.class.isAssignableFrom(clzz) || Double.class.isAssignableFrom(clzz)) {
                return (T)Double.valueOf((String)obj);
            }
            
            // BigDecimal, BigInteger
            if (BigDecimal.class.isAssignableFrom(clzz)) {
                return (T)new BigDecimal((String)obj);
            }
            if (BigInteger.class.isAssignableFrom(clzz)) {
                return (T)new BigInteger((String)obj);
            }
            
            // UUID
            if (UUID.class.isAssignableFrom(clzz)) {
                return (T)UUID.fromString((String)obj);
            }
            
            // Enum
            if (clzz.isEnum()) {
                for (val enumValue : clzz.getEnumConstants()) {
                    if (enumValue.toString().equalsIgnoreCase((String)obj)) {
                        return (T)enumValue;
                    }
                }
            }
            
            String str = (String)obj;
            if (str.matches("^[0-9]+$")) {
                val seconds = Long.parseLong(str);
                val instant = Instant.ofEpochSecond(seconds);
                
                if (Instant.class.isAssignableFrom(clzz)) {
                    return (T)instant;
                }
                if (LocalDate.class.isAssignableFrom(clzz)) {
                    return (T)LocalDate.from(instant.atZone(ZoneId.systemDefault()));
                }
                if (LocalDateTime.class.isAssignableFrom(clzz)) {
                    return (T)LocalDateTime.from(instant.atZone(ZoneId.systemDefault()));
                }
                if (LocalTime.class.isAssignableFrom(clzz)) {
                    return (T)LocalTime.from(instant.atZone(ZoneId.systemDefault()));
                }
                if (ZonedDateTime.class.isAssignableFrom(clzz)) {
                    return (T)instant.atZone(ZoneId.systemDefault());
                }
            }
            
            // Date time
            if (Duration.class.isAssignableFrom(clzz)) {
                return (T)Duration.parse((String)obj);
            }
            if (Instant.class.isAssignableFrom(clzz)) {
                return (T)Instant.parse((String)obj);
            }
            if (LocalDate.class.isAssignableFrom(clzz)) {
                return (T)LocalDate.parse((String)obj);
            }
            if (LocalDateTime.class.isAssignableFrom(clzz)) {
                return (T)LocalDateTime.parse((String)obj);
            }
            if (LocalTime.class.isAssignableFrom(clzz)) {
                return (T)LocalTime.parse((String)obj);
            }
            if (OffsetDateTime.class.isAssignableFrom(clzz)) {
                return (T)OffsetDateTime.parse((String)obj);
            }
            if (OffsetTime.class.isAssignableFrom(clzz)) {
                return (T)OffsetTime.parse((String)obj);
            }
            if (Period.class.isAssignableFrom(clzz)) {
                return (T)Period.parse((String)obj);
            }
            if (ZonedDateTime.class.isAssignableFrom(clzz)) {
                return (T)ZonedDateTime.parse((String)obj);
            }
            return (T)obj;
        }
        
        public static <T> T fromMapValue(Object obj, Getter getter) {
            val type         = getter.getType();
            val defaultValue = getter.getDefaultTo();
            
            return fromMapValue(obj, type, defaultValue);
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public static <T> T fromMapValue(Object obj, CaseParam caseParam) {
            val   type         = caseParam.type;
            Class clzz         = type.toClass();
            val   defaultValue = caseParam.defValue;
            
            if ((obj instanceof List) && type.isList()) {
                return IStruct.$utils.fromMapValue(obj, type, defaultValue);
            }
            
            return (T)IData.$utils.fromMapValue(obj, clzz, defaultValue, ()->caseParam.defaultValue());
        }
        
        public static <T> T propertyFromMap(Map<String, ? extends Object> map, Map<String, CaseParam> schema, String name) {
            val caseParam = schema.get(name);
            if (caseParam == null)
                throw new IllegalArgumentException("Unknown property: " + name);
            
            val rawValue = map.get(name);
            return fromMapValue(rawValue, caseParam);
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static <T> T fromMapValue(Object obj, Type type, DefaultValue defaultValue) {
            if ((obj instanceof List) && (type.isList() || type.isFuncList())) {
                return (T)fromListValue((List)obj, type);
            }
            if ((obj instanceof Map) && (type.isMap() || type.isFuncMap())) {
                return (T)fromMapValue((Map)obj, type);
            }
            
            Class<T> clzz = type.toClass();
            return IData.$utils.fromMapValue(obj, clzz, defaultValue, ()-> DefaultValue.defaultValue(type, defaultValue));
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Map fromMapValue(Map obj, Type type) {
            val keyType = ((type.generics().size() > 0) ? type.generics().get(0).toType() : Type.OBJECT);
            val valType = ((type.generics().size() > 1) ? type.generics().get(1).toType() : Type.OBJECT);
            Map map = new HashMap();
            obj
            .entrySet()
            .forEach(entry -> {
                val key   = fromValue(((Map.Entry)entry).getKey(),   keyType);
                val value = fromValue(((Map.Entry)entry).getValue(), valType);
                map.put(key, value);
            });
            if (type.isFuncMap()) {
                try {
                    val funcListName  = Core.FuncMap.type().fullName();
                    val funcListClass = Class.forName(funcListName);
                    val funcListFrom  = funcListClass.getMethod("from", Map.class);
                    return (Map)funcListFrom.invoke(funcListClass, map);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Object does not fit the data specfication for type (" + type + "): " + obj);
                }
            }
            return map;
        }
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Object fromValue(Object obj, Type type) {
            if (obj == null)
                return null;
            
            val clazz    = type.toClass();
            val isStruct = IStruct.class.isAssignableFrom(clazz);
            val isChoice = IChoice.class.isAssignableFrom(clazz);
            val isList   = List.class.isAssignableFrom(clazz);
            val isMap    = Map.class.isAssignableFrom(clazz);
            
            if (isStruct) {
                if (obj instanceof Map)
                    return IStruct.fromMap((Map<String, Object>)obj, (Class)clazz);
            } else if (isChoice) {
                if (obj instanceof Map)
                    return IChoice.fromMap((Map<String, Object>)obj, (Class)clazz);
            } else if (isList) {
                if (obj instanceof List)
                    return fromListValue((List)obj, type.generics().get(0).toType());
                throw new IllegalArgumentException("Invalid list element (" + type + "): " + obj);
            } else if (isMap) {
                if (obj instanceof Map)
                    return fromMapValue((Map)obj, type);
                throw new IllegalArgumentException("Invalid map element (" + type + "): " + obj);
            }
            return clazz.cast(obj);
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static List fromListValue(List obj, Type type) {
            val elementType = ((type.generics().size() > 0) ? type.generics().get(0).toType() : Type.OBJECT);
            List list = (List)(obj).stream()
                    .map(each -> fromValue(each, elementType))
                    .collect(toList());
            if (type.isFuncList()) {
                try {
                    val funcListName  = Core.FuncList.type().fullName();
                    val funcListClass = Class.forName(funcListName);
                    val funcListFrom  = funcListClass.getMethod("from", List.class);
                    return (List)funcListFrom.invoke(funcListClass, list);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Object does not fit the data specfication for type (" + type + "): " + obj);
                }
            }
            return list;
        }
        
        //== Choice ==
        
        public static <S> S Match(IChoice<S> choiceType) {
            return ChoiceTypes.Match(choiceType);
        }
        
        public static boolean checkEquals(byte a, byte b) {
            return a == b;
        }
        public static boolean checkEquals(short a, short b) {
            return a == b;
        }
        public static boolean checkEquals(int a, int b) {
            return a == b;
        }
        public static boolean checkEquals(long a, long b) {
            return a == b;
        }
        public static boolean checkEquals(float a, float b) {
            return a == b;
        }
        public static boolean checkEquals(double a, double b) {
            return a == b;
        }
        public static boolean checkEquals(boolean a, boolean b) {
            return a == b;
        }
        public static boolean checkEquals(Object a, Object b) {
            return ((a == null) && (b == null)) || Objects.equals(a, b);
        }
    }
    
}
