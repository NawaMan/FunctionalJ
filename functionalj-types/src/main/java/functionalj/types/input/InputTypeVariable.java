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

import javax.lang.model.type.TypeVariable;

public interface InputTypeVariable extends InputReferenceType {
    
    public static InputTypeVariable of(Environment environment, TypeVariable typeVariable) {
        return new Impl(environment, typeVariable);
    }
    
    public static class Impl extends InputReferenceType.Impl implements InputTypeVariable {
        
        final TypeVariable typeVariable;
        
        Impl(Environment environment, TypeVariable typeVariable) {
            super(environment, typeVariable);
            this.typeVariable  = typeVariable;
        }
        
        @Override
        public InputTypeMirror getLowerBound() {
            return InputTypeMirror.of(environment, typeVariable.getLowerBound());
        }
        
        @Override
        public InputTypeMirror getUpperBound() {
            return InputTypeMirror.of(environment, typeVariable.getUpperBound());
        }
        
    }
    
    public InputTypeMirror getLowerBound();
    
    public InputTypeMirror getUpperBound();
    
}
