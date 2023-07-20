// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;

public interface InputTypeArgument {
    
    public static InputTypeArgument of(Environment environment, TypeMirror typeMirror) {
        return new Impl(environment, typeMirror);
    }
    
    public static class Impl implements InputTypeArgument {
        
        private final InputType inputType;
        
        private final boolean isExtends;
        
        private final boolean isSuper;
        
        public Impl(Environment environment, TypeMirror typeMirror) {
            this(InputType.of(environment, typeMirror), (typeMirror instanceof WildcardType) ? ((WildcardType) typeMirror).getExtendsBound() != null : false, (typeMirror instanceof WildcardType) ? ((WildcardType) typeMirror).getSuperBound() != null : false);
        }
        
        public Impl(InputType inputType) {
            this(inputType, false, false);
        }
        
        public Impl(InputType inputType, boolean isExtends) {
            this(inputType, isExtends, false);
        }
        
        public Impl(InputType inputType, boolean isExtends, boolean isSuper) {
            this.inputType = inputType;
            this.isSuper = isSuper;
            this.isExtends = isExtends;
        }
        
        @Override
        public InputType inputType() {
            return inputType;
        }
        
        @Override
        public boolean isSuper() {
            return isSuper;
        }
        
        @Override
        public boolean isExtends() {
            return isExtends;
        }
    }
    
    public InputType inputType();
    
    public boolean isSuper();
    
    public boolean isExtends();
}
