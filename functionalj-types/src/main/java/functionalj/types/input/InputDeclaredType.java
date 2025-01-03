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

import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;

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
            return environment.element((TypeElement) declaredType.asElement());
        }
        
        @Override
        public List<InputTypeArgument> typeArguments() {
            return declaredType.getTypeArguments().stream().map(element -> InputTypeArgument.of(environment, element)).collect(toList());
        }
    }
    
    public static class Mock extends InputType.Mock implements InputDeclaredType {
        
        private final TypeKind kind;
        
        private final String toString;
        
        private final InputTypeElement asTypeElement;
        
        private final List<InputTypeArgument> typeArguments;
        
        Mock(TypeKind kind, String toString, InputTypeElement asTypeElement, List<InputTypeArgument> typeArguments) {
            this.kind = kind;
            this.toString = toString;
            this.asTypeElement = asTypeElement;
            this.typeArguments = typeArguments;
        }
        
        @Override
        public boolean isNoType() {
            return false;
        }
        
        @Override
        public TypeKind typeKind() {
            return kind;
        }
        
        @Override
        public String getToString() {
            return toString;
            // return "Q_DECLARED";
        }
        
        @Override
        public InputTypeElement asTypeElement() {
            return asTypeElement;
        }
        
        @Override
        public List<InputTypeArgument> typeArguments() {
            return typeArguments;
        }
        
        // == Builder ==
        public static class Builder {
        
            protected TypeKind kind;
        
            protected String toString;
        
            protected InputTypeElement asTypeElement;
        
            protected List<InputTypeArgument> typeArguments;
        
            public Builder kind(TypeKind kind) {
                this.kind = kind;
                return this;
            }
        
            public Builder toString(String toString) {
                this.toString = toString;
                return this;
            }
        
            public Builder asTypeElement(InputTypeElement asTypeElement) {
                this.asTypeElement = asTypeElement;
                return this;
            }
        
            public Builder typeArguments(InputTypeArgument... typeArguments) {
                return typeArguments(Arrays.asList(typeArguments));
            }
        
            public Builder typeArguments(List<InputTypeArgument> typeArguments) {
                this.typeArguments = typeArguments;
                return this;
            }
        
            public InputDeclaredType.Mock build() {
                return new InputDeclaredType.Mock(TypeKind.DECLARED, toString, asTypeElement, typeArguments);
            }
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
    
    public List<InputTypeArgument> typeArguments();
}
