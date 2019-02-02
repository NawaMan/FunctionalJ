// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static functionalj.types.struct.generator.ILines.flatenLines;
import static functionalj.types.struct.generator.ILines.indent;
import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.ILines.linesOf;
import static functionalj.types.struct.generator.ILines.oneLineOf;
import static functionalj.types.struct.generator.ILines.withSeparateIndentedSpace;
import static functionalj.types.struct.generator.model.utils.allLists;
import static functionalj.types.struct.generator.model.utils.themAll;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;
import functionalj.types.struct.generator.IRequireTypes;
import functionalj.types.struct.generator.Type;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;

/**
 * Representation of a generated class.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
@Accessors(fluent=true)
public class GenClass implements IGenerateDefinition {
    
    private final Accessibility accessibility;
    private final Scope         scope;
    private final Modifiability modifiability;
    
    private final Type                 type;
    private final String               generic;
    private final List<Type>           extendeds;
    private final List<Type>           implementeds;
    private final List<GenConstructor> constructors;
    private final List<GenField>       fields;
    private final List<GenMethod>      methods;
    private final List<GenClass>       innerClasses;
    private final List<ILines>         mores;
    
    @Override
    public Stream<Type> requiredTypes() {
        return asList(
                        asList((IRequireTypes)(()->Stream.of(type))),
                        asList((IRequireTypes)(()->extendeds.stream())),
                        asList((IRequireTypes)(()->implementeds.stream())),
                        constructors,
                        fields,
                        mores
                ).stream()
                .flatMap(allLists())
                .map    (IRequireTypes.class::cast)
                .map    (IRequireTypes::requiredTypes)
                .flatMap(themAll());
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        val extendedList    = extendeds()   .stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        val implementedList = implementeds().stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        
        val fieldDefs       = fields()      .stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        val constructorDefs = constructors().stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        val methodDefs      = methods()     .stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        val innerClassDefs  = innerClasses().stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        val moreDefs        = mores()       .stream().collect(toList());
        
        val className = type().simpleNameWithGeneric();
        val firstLine
                = oneLineOf(
                    accessibility, scope, modifiability, "class", className,
                    utils.prefixWith(extendedList,    "extends "),
                    utils.prefixWith(implementedList, "implements "),
                    "{");
        
        val lastLine = "}";
        
        val componentLines
                = linesOf(
                    fieldDefs,
                    constructorDefs,
                    methodDefs,
                    innerClassDefs,
                    moreDefs);
        
        val lines = flatenLines(withSeparateIndentedSpace(
                line  (firstLine),
                indent(componentLines),
                line  (lastLine)));
        return lines;
    }
}