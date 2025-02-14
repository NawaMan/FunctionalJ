// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types;

/**
 * Self2 class is to be used to represent the generated class in the specification classes or method signature.
 * 
 * See {@link Self} documentation for more detail.
 */
public interface Self2<TARGET1, TARGET2> {
    
    /**
     * By default, the object can be unwrapped as itself.
     * 
     * @param <T>  the type of the original object.
     * @return     the original object.
     */
    @SuppressWarnings("unchecked")
    public default <T> T unwrap() {
        return (T)this;
    }
    
    /**
     * Unwrap the {@link Self2} object and return the original object (null self).
     * 
     * @param <T>   the type of the original object.
     * @param <T1>  the type of the first parameter object.
     * @param <T2>  the type of the second parameter object.
     * @param self  the self object.
     * @return      the original object.
     */
    public static <T, T1, T2> T unwrap(Self2<T1, T2> self) {
        return self == null ? null : self.unwrap();
    }
    
    /**
     * Write an object with a {@link Self2}.
     * 
     * @param <T>       the type of the original object.
     * @param <T1>      the type of the first parameter object.
     * @param <T2>      the type of the second parameter object.
     * @param original  the original object.
     * @return          the {@link Self2} wrapping this object.
     */
    public static <T, T1, T2> Self2<T1, T2> wrap(T original) {
        return new Self2<T1, T2>() {
            @SuppressWarnings("unchecked")
            @Override
            public <TARGET> TARGET unwrap() {
                return (TARGET)original;
            }
            
        };
    }

}