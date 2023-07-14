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

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;

public interface InputPrimitiveType extends InputType {

    public static InputPrimitiveType of(Environment environment, PrimitiveType primitiveType) {
        return new Impl(environment, primitiveType);
    }

    public static class Impl extends InputType.Impl implements InputPrimitiveType {

        private final PrimitiveType primitiveType;

        public Impl(Environment environment, PrimitiveType primitiveType) {
            super(environment, primitiveType);
            this.primitiveType = primitiveType;
        }

        @Override
        public String primitiveName() {
            return primitiveType.toString();
        }

        @Override
        public String toString() {
            return "Q_PRIMITIVE.toString()";
        }
    }

    public static class Mock extends InputType.Mock implements InputPrimitiveType {

        private final TypeKind kind;

        Mock(TypeKind kind) {
            this.kind = kind;
        }

        @Override
        public boolean isNoType() {
            return kind == TypeKind.NONE;
        }

        @Override
        public TypeKind typeKind() {
            return kind;
        }

        @Override
        public String primitiveName() {
            return kind.name().toLowerCase();
        }

        @Override
        public String getToString() {
            return "Q_PRIMITIVE";
        }
    }

    public default boolean isPrimitiveType() {
        return true;
    }

    public default boolean isDeclaredType() {
        return false;
    }

    public default boolean isTypeVariable() {
        return false;
    }

    public default InputPrimitiveType asPrimitiveType() {
        return this;
    }

    public default InputDeclaredType asDeclaredType() {
        return null;
    }

    public default InputTypeVariable asTypeVariable() {
        return null;
    }

    public String primitiveName();
}
