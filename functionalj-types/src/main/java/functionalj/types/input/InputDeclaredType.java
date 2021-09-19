// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.input;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

public interface InputDeclaredType extends InputReferenceType {
    
    public static InputDeclaredType of(Environment environment, DeclaredType declaredType) {
        return new Impl(environment, declaredType);
    }
    
    public static class Impl extends InputReferenceType.Impl implements InputDeclaredType {
        
        private final DeclaredType declaredType;
        
        Impl(Environment environment, DeclaredType declaredType) {
            super(environment, declaredType);
            this.declaredType = declaredType;
        }
        
        @Override
        public InputTypeElement asTypeElement() {
            return environment.element((TypeElement)declaredType.asElement());
        }
        
        @Override
        public List<? extends InputType> typeArguments() {
            return declaredType
                    .getTypeArguments().stream()
                    .map    (element -> InputType.of(environment, element))
                    .collect(toList());
        }
        
    }
    
    public default boolean isPrimitiveType() {
        return false;
    }
    
    public default boolean isDeclaredType() {
        return true;
    }
    
    public default boolean isTypeVariable() {
        return false;
    }
    
    public default InputPrimitiveType asPrimitiveType() {
        return null;
    }
    
    public default InputDeclaredType asDeclaredType() {
        return this;
    }
    
    public default InputTypeVariable asTypeVariable() {
        return null;
    }
    
    public InputTypeElement asTypeElement();
    
    public List<? extends InputType> typeArguments();
    
}
