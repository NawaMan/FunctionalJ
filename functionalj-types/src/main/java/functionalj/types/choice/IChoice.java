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
package functionalj.types.choice;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import functionalj.types.ICanToMap;
import functionalj.types.IStruct;
import functionalj.types.StructConversionException;
import functionalj.types.choice.generator.model.CaseParam;
import lombok.val;

public interface IChoice<S> extends ICanToMap {
    
    public Map<String, Object> toMap();
    
    public S match();
    
    public static class $utils {
        public static <D> D notNull(D value) {
            return Objects.requireNonNull(value);
        }
        
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
        
        public static <S extends IChoice<S>> S fromMap(Map<String, Object> map, Class<S> clazz) {
            try {
                val method = clazz.getMethod("fromMap", Map.class);
                val struct = method.invoke(clazz, map);
                return clazz.cast(struct);
            } catch (Exception cause) {
                throw new StructConversionException(cause);
            }
        }
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public static <T> T fromMapValue(Object obj, CaseParam caseParam) {
            val   type         = caseParam.type;
            Class clzz         = type.toClass();
            val   defaultValue = caseParam.defValue;
            
            if ((obj instanceof List) && type.toStructType().isList()) {
                return IStruct.$utils.fromMapValue(obj, type.toStructType(), defaultValue);
            }
            
            return (T)ICanToMap.fromMapValue(obj, clzz, defaultValue, ()->caseParam.defaultValue());
        }
        
        public static <T> T propertyFromMap(Map<String, Object> map, Map<String, CaseParam> schema, String name) {
            val caseParam = schema.get(name);
            if (caseParam == null)
                throw new IllegalArgumentException("Unknown property: " + name);
            
            val rawValue = map.get(name);
            return fromMapValue(rawValue, caseParam);
        }
    }
    
}
