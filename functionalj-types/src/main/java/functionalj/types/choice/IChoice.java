// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.lang.reflect.Method;
import java.util.Map;

import functionalj.types.ChoiceConversionException;
import functionalj.types.IData;
import functionalj.types.choice.generator.model.CaseParam;

public interface IChoice<S> extends IData {
    
    public Map<String, Object> __toMap();
    
    public Map<String, Map<String, CaseParam>> __getSchema();
    
    public S match();
    
    public static <S extends IChoice<S>> S fromMap(Map<String, Object> map, Class<S> clazz) {
        try {
            Method method = clazz.getMethod("fromMap", Map.class);
            Object struct = method.invoke(clazz, map);
            return clazz.cast(struct);
        } catch (Exception cause) {
            throw new ChoiceConversionException(cause);
        }
    }
}
