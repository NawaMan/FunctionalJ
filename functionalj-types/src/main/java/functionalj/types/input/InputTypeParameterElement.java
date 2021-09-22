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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.TypeVariable;


/**
 * Represents a formal type parameter of a generic class, interface, method, or constructor element.
 * A type parameter declares a {@link TypeVariable}.
 */
public interface InputTypeParameterElement extends InputElement {
    
    public static InputTypeParameterElement of(Environment environment, TypeParameterElement typeParameterElement) {
        return new Impl(environment, typeParameterElement);
    }
    
    public static class Impl extends InputElement.Impl implements InputTypeParameterElement {
        
        private final TypeParameterElement typeParameterElement;
        
        Impl(Environment environment, TypeParameterElement typeParameterElement) {
            super(environment, typeParameterElement);
            this.typeParameterElement = typeParameterElement;
        }
        
        @Override
        public List<? extends InputType> bounds() {
            return typeParameterElement
                    .getBounds().stream()
                    .map    (element -> InputType.of(environment, element))
                    .collect(toList());
        }
        
    }
    
    @SuppressWarnings("rawtypes") 
    public static class Mock extends InputElement.Mock implements InputTypeParameterElement {
        
        private final List<InputType> bounds;
        
        public Mock(
                String                 simpleName,
                String                 packageQualifiedName,
                ElementKind            kind,
                Set<Modifier>          modifiers,
                InputElement           enclosingElement,
                List<InputElement>     enclosedElements,
                Map<Class, Annotation> annotations,
                InputType              asType,
                String                 printElement,
                String                 toString,
                List<InputType>        bounds) {
            super(simpleName,
                  packageQualifiedName,
                  kind,
                  modifiers,
                  enclosingElement,
                  enclosedElements,
                  annotations,
                  asType,
                  printElement,
                  toString);
            this.bounds = bounds;
        }
        
        @Override
        public List<InputType> bounds() {
            return bounds;
        }
        
        //== Builder ==
        
        public static class Builder extends InputElement.Mock.Builder {
            
            protected Object constantValue;

            public Builder simpleName(String simpleName) {
                super.simpleName(simpleName);
                return this;
            }
            
            public Builder packageQualifiedName(String packageQualifiedName) {
                super.packageQualifiedName(packageQualifiedName);
                return this;
            }
            
            public Builder kind(ElementKind kind) {
                super.kind(kind);
                return this;
            }
            
            public Builder modifiers(Modifier ... modifiers) {
                super.modifiers(modifiers);
                return this;
            }
            
            public Builder modifiers(Set<Modifier> modifiers) {
                super.modifiers(modifiers);
                return this;
            }
            
            public Builder enclosingElement(InputElement enclosingElement) {
                super.enclosingElement(enclosingElement);
                return this;
            }
            
            public Builder enclosedElements(InputElement ... enclosedElements) {
                super.enclosedElements(enclosedElements);
                return this;
            }
            
            public Builder enclosedElements(List<InputElement> enclosedElements) {
                super.enclosedElements(enclosedElements);
                return this;
            }
            
            public Builder annotations(Class clzz, Annotation annotation) {
                super.annotations(clzz, annotation);
                return this;
            }
            
            public Builder annotations(Map<Class, Annotation> annotations) {
                super.annotations(annotations);
                return this;
            }
            
            public Builder asType(InputType asType) {
                super.asType(asType);
                return this;
            }
            
            public Builder printElement(String printElement) {
                super.printElement(printElement);
                return this;
            }
            
            public Builder toString(String toString) {
                super.toString(toString);
                return this;
            }
            
            public Builder constantValue(Object constantValue) {
                this.constantValue = constantValue;
                return this;
            }
            
            public InputVariableElement build() {
                return new InputVariableElement.Mock(
                                simpleName,
                                packageQualifiedName,
                                kind,
                                modifiers,
                                enclosingElement,
                                enclosedElements,
                                annotations,
                                asType,
                                printElement,
                                toString,
                                constantValue);
            }
        }
    }
    
    public default InputTypeElement asTypeElement() {
        return null;
    }
    
    public default InputMethodElement asMethodElement() {
        return null;
    }
    
    public default InputVariableElement asVariableElement() {
        return null;
    }
    
    public default InputTypeParameterElement asTypeParameterElement() {
        return this;
    }
    
    public List<? extends InputType> bounds();
    
}
