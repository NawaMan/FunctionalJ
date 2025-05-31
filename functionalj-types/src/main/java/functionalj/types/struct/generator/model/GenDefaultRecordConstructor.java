//============================================================================
//Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
//----------------------------------------------------------------------------
//MIT License
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.
//============================================================================
package functionalj.types.struct.generator.model;

import static functionalj.types.struct.generator.ILines.indent;
import static functionalj.types.struct.generator.ILines.line;
import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.ILines;
import lombok.EqualsAndHashCode;

/**
* Representation of a generated constructor.
*
* @author NawaMan -- nawa@nawaman.net
*/
@EqualsAndHashCode(callSuper = false)
public class GenDefaultRecordConstructor extends GenConstructor {
    
    public GenDefaultRecordConstructor(Accessibility accessibility, String name, ILines body) {
    	super(accessibility, name, null, body);
    }
    
    public GenDefaultRecordConstructor withAccessibility(Accessibility accessibility) {
        return new GenDefaultRecordConstructor(accessibility, getName(), getBody());
    }
    
    public GenDefaultRecordConstructor withName(String name) {
        return new GenDefaultRecordConstructor(getAccessibility(), name, getBody());
    }
    
    public GenDefaultRecordConstructor withBody(ILines body) {
        return new GenDefaultRecordConstructor(getAccessibility(), getName(), body);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.empty();
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        String definition = Stream.of(getAccessibility(), getName(), "{")
        					.map     (utils.toStr())
        					.filter  (Objects::nonNull)
        					.collect (joining(" "));
        return ILines.flatenLines(line(definition), indent(getBody()), line("}"));
    }
    
}
