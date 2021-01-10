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
package functionalj.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks classes to create data object class.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Struct {
    
    /** @return the name of the target class. */
    public String name() default "";
    
    /** @return the name of the static final field for the source spec. */
    public String specField() default "";
    
    /**
     * @return the flag indicating that the generated class should extends or implements the definition
     *   class/interface - default to true.
     **/
    public boolean coupleWithDefinition() default true;
    
    /** @return the flag indicating that the no-arguments constructor should be created - default to true. */
    public boolean generateNoArgConstructor() default false;
    
    /** @return the flag indicating that the no-arguments constructor should be created - default to true. */
    public boolean generateAllArgConstructor() default true;
    
    /** @return the flag indicating that the lens class should be created - default to true. */
    public boolean generateLensClass() default true;
    
    /** @return the flag indicating that constructor with only required field should be created - default to true. */
    public boolean generateRequiredOnlyConstructor() default true;
    
    /** @return the flag indicating that the builder class should be created - default to true. */
    public boolean generateBuilderClass() default true;
    
    /** @return the flag indicating that the fields should be made public - default to true. */
    public boolean publicFields() default true;
    
    /** @return the flag indicating that the constructor should be made public - default to true -- or package level accessibility will be used. */
    public boolean publicConstructor() default true;
    
    /** @return the flag indicating that the fields should be made public - default to true. */
    public String toStringTemplate() default "";
    
    /** @return the flag indicating that the fields should be made public - default to true. */
    public boolean generateToString() default true;
    
}
