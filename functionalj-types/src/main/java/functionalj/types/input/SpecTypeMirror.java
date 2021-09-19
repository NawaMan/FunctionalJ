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
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

public interface SpecTypeMirror {
    
    public static SpecTypeMirror of(Environment environment, TypeMirror typeMirror) {
        return new Impl(environment, typeMirror);
    }
    
    public static class Impl implements SpecTypeMirror {
        
        final Environment environment;
        final TypeMirror  typeMirror;
        
        Impl(Environment environment, TypeMirror typeMirror) {
            this.environment = environment;
            this.typeMirror  = typeMirror;
        }
        
        @Override
        public SpecPrimitiveType asPrimitiveType() {
            return (typeMirror instanceof PrimitiveType)
                    ? SpecPrimitiveType.of(environment, ((PrimitiveType)typeMirror))
                    : null;
        }
        
        @Override
        public SpecTypeElement asDeclaredType() {
            return (typeMirror instanceof DeclaredType)
                    ? environment.element(((TypeElement)((DeclaredType)typeMirror).asElement()))
                    : null;
        }
        
        public SpecTypeVariable asTypeVariable() {
            return (typeMirror instanceof TypeVariable)
                    ? SpecTypeVariable.of(environment, (TypeVariable)typeMirror)
                    : null;
        }
        
        @Override
        public boolean isNoType() {
            return typeMirror instanceof NoType;
        }
        
        @Override
        public List<? extends SpecTypeMirror> getTypeArguments() {
            return ((DeclaredType)typeMirror)
                    .getTypeArguments().stream()
                    .map    (element -> SpecTypeMirror.of(environment, element))
                    .collect(toList());
        }
        
        @Override
        public String getToString() {
            return typeMirror.toString();
        }
        
        @Override
        public String toString() {
            return typeMirror.toString();
        }
        
    }
    
    public default boolean isPrimitiveType() {
        return (asPrimitiveType() != null);
    }
    
    public default boolean isDeclaredType() {
        return (asDeclaredType() != null);
    }
    
    public default boolean isTypeVariable() {
        return (asTypeVariable() != null);
    }
    
    public SpecPrimitiveType asPrimitiveType();
    
    public SpecTypeElement asDeclaredType();
    
    public SpecTypeVariable asTypeVariable();
    
    public boolean isNoType();
    
    public List<? extends SpecTypeMirror> getTypeArguments();
    
    public String getToString();
    
}
