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
 * Self class is to be used to represent the generated class in the specification classes or method signature.
 * 
 * In an early version of FunctionalJ (2017-2018), standard JDK is unable to recognize that the generated class 
 *   before the annotation process happen (interestingly, Eclipse Java compiler can do that then).
 * Therefore, there has to be a way to represent the generated class in the class parameter (generic) or method signature.
 * The {@link Self} class as well as {@link Self1} and {@link Self2} were added for that.
 * 
 * NOTE that, I revisit this in late 2024 and seems like the need for the class is no longer there.
 * But since I do not have an actual insight into when and how this was addressed (also no free time),
 *    I do not want to remove them.
 *    
 * Usage: There are two way to use this.
 * 1. Implement this interface. The default `unwrap` implementation already assume that so no need to do anything else.
 * 2. If the spec class cannot be made (or you don't want) to implement {@link Self},
 *         {@link Self#wrap(Object)} can be used to create Self object for any object.
 */
public interface Self {
    
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
     * Unwrap the {@link Self} object and return the original object (null self).
     * 
     * @param <T>   the type of the original object.
     * @param self  the self object.
     * @return      the original object.
     */
    public static <T> T unwrap(Self self) {
        return self == null ? null : self.unwrap();
    }
    
    /**
     * Write an object with a {@link Self}.
     * 
     * @param <T>       the original object type.
     * @param original  the original object.
     * @return          the {@link Self} wrapping this object.
     */
    public static <T> Self wrap(T original) {
        return new Self() {
            @SuppressWarnings("unchecked")
            @Override
            public <TARGET> TARGET unwrap() {
                return (TARGET)original;
            }
            
        };
    }
}
