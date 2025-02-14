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
package functionalj.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import functionalj.types.choice.generator.model.SourceSpec;

/**
 * This annotation marks an interface to create a choice type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Choice {
    
    /**
     * @return the name of the target class.
     */
    public String name() default "";
    
    /**
     * @return the name of the static final field for the source spec.
     */
    public String specField() default "";
    
    /**
     * @return the name of key in the map when do __toMap().
     */
    public String tagMapKeyName() default SourceSpec.TAG_MAP_KEY_NAME;
    
    /**
     * @return the flag indicating that the fields should be made public - default to true.
     */
    public boolean publicFields() default false;
    
    /**
     * @return the optional flat indicating that the generated code would be a sealed class.
     */
    public OptionalBoolean generateSealedClass() default OptionalBoolean.WHATEVER;
    
    /**
     * @return the target serialization type.
     */
    public Serialize.To serialize() default Serialize.To.NOTHING;
    
}
