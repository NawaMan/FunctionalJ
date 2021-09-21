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

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;

public interface InputType {
    
    public static InputType of(Environment environment, TypeMirror typeMirror) {
        return new Impl(environment, typeMirror);
    }
    
    public static class Impl implements InputType {
        
        final Environment environment;
        
        private final TypeMirror typeMirror;
        
        Impl(Environment environment, TypeMirror typeMirror) {
            this.environment = environment;
            this.typeMirror  = typeMirror;
        }
        
        @Override
        public InputPrimitiveType asPrimitiveType() {
            return (typeMirror instanceof PrimitiveType)
                    ? InputPrimitiveType.of(environment, ((PrimitiveType)typeMirror))
                    : null;
        }
        
        @Override
        public InputDeclaredType asDeclaredType() {
            return (typeMirror instanceof DeclaredType)
                    ? InputDeclaredType.of(environment, (DeclaredType)typeMirror)
                    : null;
        }
        
        public InputTypeVariable asTypeVariable() {
            return (typeMirror instanceof TypeVariable)
                    ? InputTypeVariable.of(environment, (TypeVariable)typeMirror)
                    : null;
        }
        
        @Override
        public boolean isNoType() {
            return typeMirror instanceof NoType;
        }
        
        @Override
        public TypeKind typeKind() {
            return typeMirror.getKind();
        }
        
        @Override
        public String getToString() {
            return typeMirror.toString();
        }
        
        @Override
        public String toString() {
            return typeMirror.toString();
        }
        
        public String insight() {
            return "class=[" + typeMirror.getClass() + "]";
        }
        
    }
    
    public static abstract class Mock implements InputType {
        
        //-- Primitives --
        
        public static final InputPrimitiveType P_boolean = new InputPrimitiveType.Mock(TypeKind.BOOLEAN);
        public static final InputPrimitiveType P_byte    = new InputPrimitiveType.Mock(TypeKind.BYTE);
        public static final InputPrimitiveType P_short   = new InputPrimitiveType.Mock(TypeKind.SHORT);
        public static final InputPrimitiveType P_int     = new InputPrimitiveType.Mock(TypeKind.INT);
        public static final InputPrimitiveType P_long    = new InputPrimitiveType.Mock(TypeKind.LONG);
        public static final InputPrimitiveType P_char    = new InputPrimitiveType.Mock(TypeKind.CHAR);
        public static final InputPrimitiveType P_float   = new InputPrimitiveType.Mock(TypeKind.FLOAT);
        public static final InputPrimitiveType P_double  = new InputPrimitiveType.Mock(TypeKind.DOUBLE);
        public static final InputPrimitiveType P_void    = new InputPrimitiveType.Mock(TypeKind.VOID);
        
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
    
    public InputPrimitiveType asPrimitiveType();
    
    public InputDeclaredType asDeclaredType();
    
    public InputTypeVariable asTypeVariable();
    
    public boolean isNoType();
    
    public TypeKind typeKind();
    
    public String getToString();
    
}
