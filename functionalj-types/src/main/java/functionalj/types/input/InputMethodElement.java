// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import functionalj.types.struct.generator.model.Concrecity;

public interface InputMethodElement extends InputElement {
    
    public static class Impl extends InputElement.Impl implements InputMethodElement {
        
        private final ExecutableElement executableElement;
        
        Impl(Environment environment, ExecutableElement executableElement) {
            super(environment, executableElement);
            this.executableElement = executableElement;
        }
        
        @Override
        public Concrecity concrecity() {
            if (isDefault())
                return Concrecity.DEFAULT;
            return super.concrecity();
        }
        
        @Override
        public boolean isDefault() {
            return executableElement.isDefault();
        }
        
        @Override
        public boolean isAbstract() {
            // Seriously ... no other way?
            try (StringWriter writer = new StringWriter()) {
                environment.elementUtils.printElements(writer, executableElement);
                return writer.toString().contains(" abstract ");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            return false;
        }
        
        @Override
        public boolean isVarArgs() {
            return executableElement.isVarArgs();
        }
        
        @Override
        public InputType receiverType() {
            TypeMirror receiverType = executableElement.getReceiverType();
            return InputType.of(environment, receiverType);
        }
        
        @Override
        public InputType returnType() {
            TypeMirror returnType = executableElement.getReturnType();
            return InputType.of(environment, returnType);
        }
        
        @Override
        public List<? extends InputVariableElement> parameters() {
            return executableElement.getParameters().stream().map(element -> (VariableElement) element).map(element -> environment.element(element)).collect(toList());
        }
        
        @Override
        public List<? extends InputTypeParameterElement> typeParameters() {
            return executableElement.getTypeParameters().stream().map(element -> InputTypeParameterElement.of(environment, element)).collect(toList());
        }
        
        public List<? extends InputType> thrownTypes() {
            return executableElement.getThrownTypes().stream().map(element -> InputType.of(environment, element)).collect(Collectors.toList());
        }
        
        // == Builder ==
        @SuppressWarnings("rawtypes")
        public static class Builder extends InputElement.Mock.Builder {
            
            protected boolean isDefault;
            
            protected boolean isAbstract;
            
            protected boolean isVarArgs;
            
            protected InputType receiverType;
            
            protected InputType returnType;
            
            protected List<? extends InputElement> parameters;
            
            protected List<? extends InputTypeParameterElement> typeParameters;
            
            protected List<? extends InputType> thrownTypes;
            
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
            
            public Builder enclosedElements(Supplier<List<InputElement>> enclosedElementsSupplier) {
                super.enclosedElements(enclosedElementsSupplier);
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
            
            public Builder isDefault(boolean isDefault) {
                this.isDefault = isDefault;
                return this;
            }
            
            public Builder isAbstract(boolean isAbstract) {
                this.isAbstract = isAbstract;
                return this;
            }
            
            public Builder isVarArgs(boolean isVarArgs) {
                this.isVarArgs = isVarArgs;
                return this;
            }
            
            public Builder receiverType(InputType receiverType) {
                return receiverType(receiverType);
            }
            
            public Builder returnType(InputType returnType) {
                this.returnType = returnType;
                return this;
            }
            
            public Builder parameters(InputElement... parameters) {
                return parameters(asList(parameters));
            }
            
            public Builder parameters(List<? extends InputElement> parameters) {
                this.parameters = parameters;
                return this;
            }
            
            public Builder typeParameters(InputTypeParameterElement... typeParameters) {
                return typeParameters(asList(typeParameters));
            }
            
            public Builder typeParameters(List<? extends InputTypeParameterElement> typeParameters) {
                this.typeParameters = typeParameters;
                return this;
            }
            
            public Builder thrownTypes(InputType... thrownTypes) {
                return thrownTypes(asList(thrownTypes));
            }
            
            public Builder thrownTypes(List<? extends InputType> thrownTypes) {
                this.thrownTypes = thrownTypes;
                return this;
            }
        }
    }
    
    @Override
    public default InputMethodElement asMethodElement() {
        return this;
    }
    
    public boolean isDefault();
    
    public boolean isAbstract();
    
    public boolean isVarArgs();
    
    public InputType receiverType();
    
    public InputType returnType();
    
    public List<? extends InputElement> parameters();
    
    public List<? extends InputTypeParameterElement> typeParameters();
    
    public List<? extends InputType> thrownTypes();
}
