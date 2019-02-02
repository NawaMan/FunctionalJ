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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import functionalj.types.struct.generator.Getter;
import lombok.val;

// TODO - Move commonly used things in here like
//   - new Getter
//   - primitive type
//   - java.util.Collections.emptyList()
//   - access to DefaultValue.

public interface IStruct {
    
    public Map<String, Object> toMap();
    public Map<String, Getter> getSchema();
    
    public static Object toMapValueObject(Object data) {
        return (data instanceof IStruct) ? ((IStruct)data).toMap() : (Object)data;
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T fromMapValue(Object obj, Getter getter) {
        val type         = getter.getType();
        val defaultValue = getter.getDefaultTo();
        if ((obj == null) && ((defaultValue == DefaultValue.UNSPECIFIED) || (defaultValue == DefaultValue.NULL)))
            return null;
        
        Class clzz = type.toClass();
        if ((obj instanceof Map) && IStruct.class.isAssignableFrom(clzz)) {
            return (T)fromMap((Map)obj, clzz);
        }
        
        if (obj != null)
            return (T)obj;
        
        val value = DefaultValue.defaultValue(type, defaultValue);
        return (T)value;
    }
    
    public static class $utils {
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        @SafeVarargs
        public static <D> List<D> asList(D ... ds) {
            return Arrays.asList(ds);
        }
    }
    
}
