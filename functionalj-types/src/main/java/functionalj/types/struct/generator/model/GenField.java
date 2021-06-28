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
package functionalj.types.struct.generator.model;

import static functionalj.types.struct.generator.ILines.oneLineOf;

import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import lombok.val;


/**
 * Representation of a generated field.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@With
@EqualsAndHashCode(callSuper=false)
public class GenField implements IGenerateDefinition {
    
    private Accessibility accessibility;
    private Modifiability modifiability;
    private Scope         scope;
    private String        name;
    private Type  type;
    private String        defaultValue;
    
    @Override
    public Stream<Type> requiredTypes() {
        return getType().requiredTypes();
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        val def = oneLineOf(
                    accessibility,
                    scope,
                    modifiability,
                    type.simpleNameWithGeneric(currentPackage),
                    name
                );
        
        val value = (defaultValue != null) ? " = " + defaultValue : "";
        return ()->Stream.of(def + value + ";");
    }
    
}
