// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.model.Accessibility.PUBLIC;
import static functionalj.types.struct.generator.model.Modifiability.MODIFIABLE;
import static functionalj.types.struct.generator.model.Scope.NONE;
import static java.util.Arrays.asList;

import java.util.List;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.GenClass;
import functionalj.types.struct.generator.model.GenConstructor;
import functionalj.types.struct.generator.model.GenField;
import functionalj.types.struct.generator.model.GenMethod;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * Specification for Record.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
public class StructSpec {
    
    @Delegate
    private GenClass classSpec;
    
    private String sourceClassName;
    private String sourcePackageName;
    
    /**
     * Constructs a RecordSpec.
     * 
     * @param className           the name of the generated class.
     * @param packageName         the package name.
     * @param sourceClassName     the name of the source class.
     * @param sourcePackageName   the name of the source paclage.
     * @param extendeds           the list of extensions.
     * @param implementeds        the list of implementations.
     * @param constructors        the list of constructors.
     * @param fields              the list of fields.
     * @param methods             the list of methods.
     * @param innerClasses        the list of inner classes.
     * @param mores               other ILines.
     */
    public StructSpec(
            String               className,
            String               packageName,
            String               sourceName,
            String               sourcePackageName,
            List<Type>           extendeds,
            List<Type>           implementeds,
            List<GenConstructor> constructors,
            List<GenField>       fields,
            List<GenMethod>      methods,
            List<GenClass>       innerClasses,
            List<ILines>         mores) {
        this.classSpec         = new GenClass(PUBLIC, NONE, MODIFIABLE, new Type(packageName, className), null, extendeds, implementeds, constructors, fields, methods, innerClasses, mores);
        this.sourceClassName   = sourceName;
        this.sourcePackageName = sourcePackageName;
    }
    
    /**
     * Returns the lens type of this class.
     * 
     * @return the lens type of this class.
     */
    public Type getLensType() {
        return new Type.TypeBuilder()
                .encloseName(type().simpleName())
                .simpleName(type().simpleName() + "Lens")
                .packageName(type().packageName())
                .generics(asList(new Generic("HOST")))
                .build();
    }
    
}