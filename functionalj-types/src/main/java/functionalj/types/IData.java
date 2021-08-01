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

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
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
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import functionalj.promise.Promise;
import functionalj.ref.Ref;
import functionalj.result.Acceptable;
import functionalj.result.DerivedResult;
import functionalj.result.Result;
import functionalj.result.Value;
import functionalj.types.choice.ChoiceTypes;
import functionalj.types.choice.IChoice;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.struct.Core;
import lombok.val;
import nullablej.nullable.IAsNullable;
import nullablej.nullable.LiveNullable;
import nullablej.nullable.Nullable;
import nullablej.nullable.NullableImpl;


public interface IData {
    
    public Map<String, Object> __toMap();
    
    
    public static <DATA extends IData> Optional<DATA> fromMap(Map<String, Object> map, Class<DATA> clazz) {
        return Optional.ofNullable(IData.$utils.fromMap(map, clazz));
    }
    
    public static Ref<Locale>            localeRef            = Ref.ofValue(Locale.getDefault());
    public static Ref<DateTimeFormatter> dateTimeFormatterRef = Ref.ofValue(DateTimeFormatter.ISO_DATE_TIME);
    public static Ref<DateTimeFormatter> dateFormatterRef     = Ref.ofValue(DateTimeFormatter.ISO_DATE);
    public static Ref<DateTimeFormatter> timeFormatterRef     = Ref.ofValue(DateTimeFormatter.ISO_TIME);
    
    
    // TODO - Extract this out so it can be used in other scenarios.
    @SuppressWarnings("rawtypes")
    public static class $utils {
        
        private static final Map<Class<?>, Class<?>> boxedClasses;
        static {
            val map = new HashMap<Class<?>, Class<?>>();
            map.put(boolean.class, Boolean.class);
            map.put(byte.class,    Byte.class);
            map.put(char.class,    Character.class);
            map.put(double.class,  Double.class);
            map.put(float.class,   Float.class);
            map.put(int.class,     Integer.class);
            map.put(long.class,    Long.class);
            map.put(short.class,   Short.class);
            boxedClasses = unmodifiableMap(map);
        }
        
        @SuppressWarnings("unused")
        private static final Set<Class<?>> premitiveClasses;
        static {
            val set = new HashSet<Class<?>>();
            set.add(boolean.class);
            set.add(char.class);
            set.add(byte.class);
            set.add(short.class);
            set.add(int.class);
            set.add(long.class);
            set.add(float.class);
            set.add(double.class);
            premitiveClasses = unmodifiableSet(set);
        }
        
        private static final Set<Class<?>> premitiveLikeClasses;
        static {
            val set = new HashSet<Class<?>>();
            set.add(boolean.class);
            set.add(char.class);
            set.add(byte.class);
            set.add(short.class);
            set.add(int.class);
            set.add(long.class);
            set.add(float.class);
            set.add(double.class);
            set.add(Boolean.class);
            set.add(Character.class);
            set.add(Byte.class);
            set.add(Short.class);
            set.add(Integer.class);
            set.add(Long.class);
            set.add(Float.class);
            set.add(Double.class);
            premitiveLikeClasses = unmodifiableSet(set);
        }
        
        private static final Map<Class, Function> wrapperCreators;
        static {
            val map = new HashMap<Class, Function>();
            map.put(Optional.class,      Optional::ofNullable);
            map.put(Nullable.class,      Nullable::of);
            map.put(NullableImpl.class,  Nullable::of);
            map.put(LiveNullable.class,  Nullable::of);
            map.put(IAsNullable.class,   Nullable::of);
            map.put(Result.class,        Result::ofValue);
            map.put(DerivedResult.class, Result::ofValue);
            map.put(Value.class,         Value::ofValue);
            map.put(Promise.class,       Promise::ofValue);
            map.put(OptionalInt.class, i -> {
                // TODO - We might want to use $utils function to do this conversion.
                if (i instanceof OptionalInt) return (OptionalInt)i;
                if (i instanceof Optional)    return (int)((Optional)i).get();
                if (i instanceof String)      return OptionalInt.of(Integer.parseInt(((String)i).trim()));
                if (i instanceof Number)      return OptionalInt.of(((Number)i).intValue());
                return OptionalInt.of((int)i); 
            });
            map.put(OptionalLong.class, i -> {
                // TODO - We might want to use $utils function to do this conversion.
                if (i instanceof OptionalLong) return (OptionalLong)i;
                if (i instanceof Optional)     return (long)((Optional)i).get();
                if (i instanceof String)       return OptionalLong.of(Long.parseLong(((String)i).trim()));
                if (i instanceof Number)       return OptionalLong.of(((Number)i).longValue());
                return OptionalInt.of((int)i); 
            });
            map.put(OptionalDouble.class, i -> {
                // TODO - We might want to use $utils function to do this conversion.
                if (i instanceof OptionalDouble) return (OptionalDouble)i;
                if (i instanceof Optional)       return (double)((Optional)i).get();
                if (i instanceof String)         return OptionalDouble.of(Double.parseDouble(((String)i).trim()));
                if (i instanceof Number)         return OptionalDouble.of(((Number)i).longValue());
                return OptionalInt.of((int)i); 
            });
            wrapperCreators = unmodifiableMap(map);
        }
        
        private static final Map<Class, Type> wrappedTypes;
        static {
            val map = new HashMap<Class, Type>();
            map.put(OptionalInt.class, Type.INT);
            map.put(OptionalLong.class, Type.LNG);
            map.put(OptionalDouble.class, Type.DBL);
            wrappedTypes = unmodifiableMap(map);
        }
        
        
        public static Supplier<Object> defaultValueOf(Type type, DefaultValue defaultValue) {
            return ()->DefaultValue.defaultValue(type, defaultValue);
        }
        
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        
        // == To and from Map ==
        
        @SuppressWarnings("unchecked")
        public static <O, D> D extractPropertyFromMap(
                        Class<O>                        objClzz,
                        Class<D>                        valueClzz, 
                        Map<String, ? extends Object>   map,
                        Map<String, ? extends Property> schema,
                        String                          fieldName) {
            val valueFromMap   = map.get(fieldName);
            val getterSpec     = schema.get(fieldName);
            Object extractedValue = null;
            try {
                extractedValue = $utils.fromMapValue(valueFromMap, getterSpec);
                if (valueClzz.isInstance(extractedValue)) {
                    return (D)extractedValue;
                }
                
                val boxClass   = boxedClasses.get(valueClzz);
                val valueClass = extractedValue.getClass();
                if (boxClass == valueClass) {
                    return (D)extractedValue;
                }
                val wrapperCreator = wrapperCreators.get(valueClzz);
                if (wrapperCreator != null) {
                    val wrappedType = Optional.ofNullable(wrappedTypes.get(valueClzz)).orElseGet(()-> getterSpec.type().generics().get(0).getBoundTypes().get(0));
                    val unwrappedRxtractedValue = fromMapValue(extractedValue, wrappedType, DefaultValue.REQUIRED);
                    return (D)wrapperCreator.apply(unwrappedRxtractedValue);
                }
                
                if (Acceptable.class.isAssignableFrom(valueClzz) && IRule.class.isAssignableFrom(valueClzz)) {
                    // TODO - This should be cached
                    val baseClass  = (Class)valueClzz.getMethod("___dataType").invoke(null);
                    val acceptable = valueClzz.getMethod("from", baseClass).invoke(null, extractedValue);
                    return (D)acceptable;
                }
                
                return valueClzz.cast(extractedValue);
            } catch (Exception exception) {
                val errMsg = prepareExtractValueErrMsg(objClzz, fieldName, valueFromMap, getterSpec, extractedValue);
                throw new IllegalArgumentException(errMsg, exception);
            }
        }
        
        private static <O> String prepareExtractValueErrMsg(
                        Class<O> objClzz, 
                        String   fieldName, 
                        Object   valueFromMap, 
                        Property propertySpec, 
                        Object   extractedValue) {
            val valueFromMapClass   = (valueFromMap   != null) ? valueFromMap  .getClass().getSimpleName() : "void";
            val extractedValueClass = (extractedValue != null) ? extractedValue.getClass().getSimpleName() : "void";
            val errorMessage = format(
                    "Fail to extract field value from map: "
                    + "class=`%s`, "
                    + "field=`%s`,"
                    + "original value=`%s` (%s),"
                    + "converted value=`%s` (%s),"
                    + "field spec=`%s`.",
                    objClzz.getSimpleName(), fieldName, 
                    valueFromMap, valueFromMapClass,
                    extractedValue, extractedValueClass,
                    propertySpec);
            return errorMessage;
        }
        
        @SuppressWarnings("unchecked")
        public static <D extends IData> D fromMap(Map<String, Object> map, Class<D> clazz) {
            if (IStruct.class.isAssignableFrom(clazz))
                return (D)IStruct.fromMap(map, (Class<IStruct>)clazz);
            if (IChoice.class.isAssignableFrom(clazz))
                return (D)IChoice.fromMap(map, (Class<IChoice>)clazz);
            
            throw new DataConversionException(clazz);
        }
        
        @SuppressWarnings("unchecked")
        public static Object toMapValueObject(Object data) {
            if (data instanceof List) {
                return ((List)data).stream()
                        .map(IData.$utils::toMapValueObject)
                        .collect(toList());
            }
            // TODO - Put some of these in a map as oppose to linear search
            
            if (data instanceof Optional) {
                val optional = ((Optional)data).orElse(null);
                return IData.$utils.toMapValueObject(optional);
            }
            
            if (data instanceof OptionalInt) {
                val optional = ((OptionalInt)data);
                return optional.isPresent() ? optional.getAsInt() : null;
            }
            
            if (data instanceof OptionalLong) {
                val optional = ((OptionalLong)data);
                return optional.isPresent() ? optional.getAsLong() : null;
            }
            
            if (data instanceof OptionalDouble) {
                val optional = ((OptionalDouble)data);
                return optional.isPresent() ? optional.getAsDouble() : null;
            }
            
            if (data instanceof Value) {
                // Invalid value cannot be serialized.
                return ((Value)data).get();
            }
            if (data instanceof Nullable) {
                // Invalid value cannot be serialized.
                return ((Nullable)data).get();
            }
            if (data instanceof TemporalAccessor) {
                val temporal = (TemporalAccessor)data;
                // TODO - Include Locale
                // TODO - Add more type
                if ((data instanceof LocalDate) || (data instanceof ChronoLocalDate)) {
                    return dateFormatterRef.get().format(temporal);
                }
                if ((data instanceof LocalTime) || (data instanceof OffsetTime)) {
                    return timeFormatterRef.get().format(temporal);
                }
                if ((data instanceof LocalDateTime) || (data instanceof OffsetDateTime) || (data instanceof ZonedDateTime) || (data instanceof ChronoLocalDateTime)) {
                    return dateTimeFormatterRef.get().format(temporal);
                }
                return dateTimeFormatterRef.get().format((TemporalAccessor)data);
            }
            
            return (data instanceof IData)
                    ? ((IData)data).__toMap()
                    : data;
        }
        
        @SuppressWarnings("unchecked")
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
                if (premitiveLikeClasses.contains(clzz)) {
                    if ((clzz == char.class) || (clzz == Character.class)) {
                        if (obj instanceof Integer) {
                            obj = (char)((Integer)obj).intValue();
                            return (T)obj;
                        } else  if ((obj instanceof String) && (((String)obj).length() == 1)) {
                            obj = ((String)obj).charAt(0);
                            return (T)obj;
                        }
                    }
                    if (!(obj instanceof String)) {
                        return (T)obj;
                    }
                }
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
            
            // TODO ... this can be done with Hashtable.
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
            if (boolean.class.isAssignableFrom(clzz) || Boolean.class.isAssignableFrom(clzz)) {
                return (T)Boolean.valueOf(((String)obj).toLowerCase());
            }
            
            // BigDecimal, BigInteger
            if (BigDecimal.class.isAssignableFrom(clzz)) {
                return (T)new BigDecimal((String)obj);
            }
            if (BigInteger.class.isAssignableFrom(clzz)) {
                return (T)new BigInteger((String)obj);
            }
            
            // UUID
            // TODO - Final
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
            
            // String of int
            // TODO - Improve this.
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
            
            // TODO - Use the right pattern
            // Date time
            if (TemporalAmount.class.isAssignableFrom(clzz)) {
                // TODO - Final class can be found with HASHMAP
                //  - Duration
                //  - Period
                if (Duration.class.isAssignableFrom(clzz)) {
                    return (T)Duration.parse((String)obj);
                }
                if (Period.class.isAssignableFrom(clzz)) {
                    return (T)Period.parse((String)obj);
                }
            }
            if (Temporal.class.isAssignableFrom(clzz)) {
                // TODO - Final class can be found with HASHMAP
                //  - Instant
                //  - LocalDate
                //  - LocalDateTime
                //  - LocalTime
                //  - OffsetDateTime
                //  - OffsetTime
                //  - ZonedDateTime
                if (Instant.class.isAssignableFrom(clzz)) {
                    return (T)Instant.parse((String)obj);
                }
                if (LocalDate.class.isAssignableFrom(clzz)) {
                    return (T)LocalDate.parse((String)obj, dateFormatterRef.get());
                }
                if (LocalDateTime.class.isAssignableFrom(clzz)) {
                    return (T)LocalDateTime.parse((String)obj, dateTimeFormatterRef.get());
                }
                if (LocalTime.class.isAssignableFrom(clzz)) {
                    return (T)LocalTime.parse((String)obj, timeFormatterRef.get());
                }
                if (OffsetDateTime.class.isAssignableFrom(clzz)) {
                    return (T)OffsetDateTime.parse((String)obj, dateTimeFormatterRef.get());
                }
                if (OffsetTime.class.isAssignableFrom(clzz)) {
                    return (T)OffsetTime.parse((String)obj, timeFormatterRef.get());
                }
                if (ZonedDateTime.class.isAssignableFrom(clzz)) {
                    return (T)ZonedDateTime.parse((String)obj, dateTimeFormatterRef.get());
                }
            }
            return (T)obj;
        }
        
        public static <T> T fromMapValue(Object obj, Property property) {
            val type         = property.type();
            val defaultValue = property.defValue();
            
            return fromMapValue(obj, type, defaultValue);
        }
        
        @SuppressWarnings("unchecked")
        public static <T> T fromMapValue(Object obj, CaseParam caseParam) {
            val   type         = caseParam.type();
            Class clzz         = type.toClass();
            val   defaultValue = caseParam.defValue();
            
            if ((obj instanceof List) && type.isList()) {
                return IStruct.$utils.fromMapValue(obj, type, defaultValue);
            }
            
            return (T)IData.$utils.fromMapValue(obj, clzz, defaultValue, ()->caseParam.defaultValue());
        }
        
        @SuppressWarnings("unchecked")
        public static <T> T fromMapValue(Object obj, Type type, DefaultValue defaultValue) {
            if ((obj instanceof List) && (type.isList() || type.isFuncList())) {
                return (T)fromListValue((List)obj, type);
            }
            if ((obj instanceof Map) && (type.isMap() || type.isFuncMap())) {
                return (T)fromMapValue((Map)obj, type);
            }
            
            Class<T>         clzz                 = type.toClass();
            Supplier<Object> defaultValueSupplier = ()-> DefaultValue.defaultValue(type, defaultValue);
            return IData.$utils.fromMapValue(obj, clzz, defaultValue, defaultValueSupplier);
        }
        
        @SuppressWarnings("unchecked")
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
        
        @SuppressWarnings("unchecked")
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
                    return fromListValue((List)obj, type);
                throw new IllegalArgumentException("Invalid list element (" + type + "): " + obj);
            } else if (isMap) {
                if (obj instanceof Map)
                    return fromMapValue((Map)obj, type);
                throw new IllegalArgumentException("Invalid map element (" + type + "): " + obj);
            }
            return clazz.cast(obj);
        }
        @SuppressWarnings("unchecked")
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
