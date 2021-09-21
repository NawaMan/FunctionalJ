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

import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

public interface InputTypeElement extends InputElement {
    
    public static class Impl extends InputElement.Impl implements InputTypeElement {
        
        private final TypeElement typeElement;
        
        Impl(Environment environment, TypeElement typeElement) {
            super(environment, typeElement);
            this.typeElement = typeElement;
        }
        
        @Override
        public String qualifiedName() {
            return typeElement.getQualifiedName().toString();
        }
        
        @Override
        public NestingKind nestingKind() {
            return typeElement.getNestingKind();
        }
        
        @Override
        public InputType superclass() {
            return InputType.of(environment, typeElement.getSuperclass());
        }
        
        @Override
        public List<? extends InputType> interfaces() {
            return typeElement
                    .getInterfaces().stream()
                    .map    (element -> InputType.of(environment, element))
                    .collect(toList());
        }
        
        @Override
        public List<? extends InputTypeParameterElement> typeParameters() {
            return typeElement
                    .getTypeParameters().stream()
                    .map    (element -> InputTypeParameterElement.of(environment, element))
                    .collect(toList());
        }
        
    }
    
    public default InputTypeElement asTypeElement() {
        return this;
    }
    
    public default InputMethodElement asMethodElement() {
        return null;
    }
    
    public String qualifiedName();
    
    public NestingKind nestingKind();
    
    public InputType superclass();
    
    public List<? extends InputType> interfaces();
    
    public List<? extends InputTypeParameterElement> typeParameters();
    
}
