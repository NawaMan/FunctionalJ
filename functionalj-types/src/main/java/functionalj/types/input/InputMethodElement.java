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

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.lang.model.element.ExecutableElement;

import functionalj.types.struct.generator.model.Concrecity;
import lombok.val;

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
            try (val writer = new StringWriter()) {
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
            val receiverType = executableElement.getReceiverType();
            return InputType.of(environment, receiverType);
        }
        
        @Override
        public InputType returnType() {
            val returnType = executableElement.getReturnType();
            return InputType.of(environment, returnType);
        }
        
        @Override
        public List<? extends InputElement> parameters() {
            return executableElement
                    .getParameters().stream()
                    .map    (element -> environment.element(element))
                    .collect(toList());
        }
        
        @Override
        public List<? extends InputTypeParameterElement> typeParameters() {
            return executableElement
                    .getTypeParameters().stream()
                    .map    (element -> InputTypeParameterElement.of(environment, element))
                    .collect(toList());
        }
        
        public List<? extends InputType> thrownTypes() {
            return executableElement
                    .getThrownTypes().stream()
                    .map    (element -> InputType.of(environment, element))
                    .collect(Collectors.toList());
        }
        
    }
    
    public default InputTypeElement asTypeElement() {
        return null;
    }
    
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
