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

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import functionalj.types.choice.IChoice;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.Type;
import lombok.val;

public interface IStruct extends ICanToMap {
    
    public Map<String, Object> __toMap();
    public Map<String, Getter> __getSchema();
    
    
    public static class $utils {
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        
        public static Object toMapValueObject(Object data) {
            return ICanToMap.toMapValueObject(data);
        }
        
        @SafeVarargs
        public static <D> List<D> asList(D ... ds) {
            return Arrays.asList(ds);
        }
        public static <S extends IStruct> S fromMap(Map<String, Object> map, Class<S> clazz) {
            try {
                val method = clazz.getMethod("fromMap", Map.class);
                val struct = method.invoke(clazz, map);
                return clazz.cast(struct);
            } catch (Exception cause) {
                throw new StructConversionException(cause);
            }
        }
        public static <T> T fromMapValue(Object obj, Getter getter) {
            val type         = getter.getType();
            val defaultValue = getter.getDefaultTo();
            
            return fromMapValue(obj, type, defaultValue);
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
            return ICanToMap.fromMapValue(obj, clzz, defaultValue, ()-> DefaultValue.defaultValue(type, defaultValue));
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
                    return FuncType.structFromMap((Map<String, Object>)obj, (Class)clazz);
            } else if (isChoice) {
                if (obj instanceof Map)
                    return FuncType.choiceFromMap((Map<String, Object>)obj, (Class)clazz);
            } else if (isList) {
                if (obj instanceof List)
                    return fromListValue((List)obj, type.generics().get(0));
                throw new IllegalArgumentException("Invalid list element (" + type + "): " + obj);
            } else if (isMap) {
                if (obj instanceof Map)
                    return fromMapValue((Map)obj, type);
                throw new IllegalArgumentException("Invalid list element (" + type + "): " + obj);
            }
            return clazz.cast(obj);
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Map fromMapValue(Map obj, Type type) {
            val keyType = ((type.generics().size() > 0) ? type.generics().get(0) : Type.OBJECT);
            val valType = ((type.generics().size() > 1) ? type.generics().get(1) : Type.OBJECT);
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
        private static List fromListValue(List obj, Type type) {
            val elementType = ((type.generics().size() > 0) ? type.generics().get(0) : Type.OBJECT);
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
    }
    
}
