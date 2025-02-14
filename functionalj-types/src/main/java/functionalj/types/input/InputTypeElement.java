// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

import functionalj.types.JavaVersionInfo;

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
            return typeElement.getInterfaces().stream().map(element -> InputType.of(environment, element)).collect(toList());
        }
        
        @Override
        public List<? extends InputTypeParameterElement> typeParameters() {
            return typeElement.getTypeParameters().stream().map(element -> InputTypeParameterElement.of(environment, element)).collect(toList());
        }
    }
    
    public static class Mock extends InputElement.Mock implements InputTypeElement {
        
        private final String qualifiedName;
        
        private final NestingKind nestingKind;
        
        private final InputType superClass;
        
        private final List<InputType> interfaces;
        
        private final List<InputTypeParameterElement> typeParameters;
        
        @SuppressWarnings("rawtypes")
        public Mock(
                JavaVersionInfo                 versionInfo, 
                String                          simpleName, 
                String                          packageQualifiedName, 
                ElementKind                     kind, 
                Set<Modifier>                   modifiers, 
                InputElement                    enclosingElement, 
                Supplier<List<InputElement>>    enclosedElementsSupplier, 
                Function<Class, Annotation>     annotations, 
                InputType                       asType, 
                String                          printElement, 
                String                          toString, 
                String                          qualifiedName, 
                NestingKind                     nestingKind, 
                InputType                       superClass, 
                List<InputType>                 interfaces, 
                List<InputTypeParameterElement> typeParameters) {
            super(
                    versionInfo, 
                    simpleName, 
                    packageQualifiedName, 
                    kind, 
                    modifiers, 
                    enclosingElement, 
                    enclosedElementsSupplier, 
                    annotations, 
                    asType, 
                    printElement, 
                    toString);
            this.qualifiedName = qualifiedName;
            this.nestingKind = nestingKind;
            this.superClass = superClass;
            this.interfaces = interfaces;
            this.typeParameters = typeParameters;
        }
        
        @Override
        public String qualifiedName() {
            return qualifiedName;
        }
        
        @Override
        public NestingKind nestingKind() {
            return nestingKind;
        }
        
        @Override
        public InputType superclass() {
            return superClass;
        }
        
        @Override
        public List<? extends InputType> interfaces() {
            return interfaces;
        }
        
        @Override
        public List<? extends InputTypeParameterElement> typeParameters() {
            return typeParameters;
        }
        
        // == Builder ==
        @SuppressWarnings("rawtypes")
        public static class Builder extends InputElement.Mock.Builder {
            
            protected String qualifiedName;
            
            protected NestingKind nestingKind;
            
            protected InputType superClass;
            
            protected List<InputType> interfaces;
            
            protected List<InputTypeParameterElement> typeParameters;
            
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
            
            public Builder modifiers(Modifier... modifiers) {
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
            
            public Builder enclosedElements(InputElement... enclosedElements) {
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
            
            public Builder annotations(Function<Class, Annotation> annotations) {
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
            
            public Builder qualifiedName(String qualifiedName) {
                this.qualifiedName = qualifiedName;
                return this;
            }
            
            public Builder nestingKind(NestingKind nestingKind) {
                this.nestingKind = nestingKind;
                return this;
            }
            
            public Builder superClass(InputType superClass) {
                this.superClass = superClass;
                return this;
            }
            
            public Builder interfaces(InputType... interfaces) {
                return interfaces(Arrays.asList(interfaces));
            }
            
            public Builder interfaces(List<InputType> interfaces) {
                this.interfaces = interfaces;
                return this;
            }
            
            public Builder typeParameters(InputTypeParameterElement... typeParameters) {
                return typeParameters(asList(typeParameters));
            }
            
            public Builder typeParameters(List<InputTypeParameterElement> typeParameters) {
                this.typeParameters = typeParameters;
                return this;
            }
            
            public InputTypeElement build() {
                return new InputTypeElement.Mock(
                        versionInfo, 
                        simpleName, 
                        packageQualifiedName, 
                        kind, 
                        modifiers, 
                        enclosingElement, 
                        enclosedElementsSupplier, 
                        annotations, 
                        asType, 
                        printElement, 
                        toString, 
                        qualifiedName, 
                        nestingKind, 
                        superClass, 
                        interfaces, 
                        typeParameters);
            }
        }
    }
    
    @Override
    public default InputTypeElement asTypeElement() {
        return this;
    }
    
    public String qualifiedName();
    
    public NestingKind nestingKind();
    
    public InputType superclass();
    
    public List<? extends InputType> interfaces();
    
    public List<? extends InputTypeParameterElement> typeParameters();
}
