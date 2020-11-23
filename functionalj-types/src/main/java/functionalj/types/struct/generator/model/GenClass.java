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

import functionalj.types.IRequireTypes;
import functionalj.types.Type;
import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;
import lombok.Value;
import lombok.With;

import lombok.experimental.Accessors;

/**
 * Representation of a generated class.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@With
@Accessors(fluent=true)
public class GenClass implements IGenerateDefinition {
    
    private final Accessibility accessibility;
    private final Scope         scope;
    private final Modifiability modifiability;
    
    private final boolean isClass;
    
    private final Type                 type;
    private final String               generic;
    private final List<Type>           extendeds;
    private final List<Type>           implementeds;
    private final List<GenConstructor> constructors;
    private final List<GenField>       fields;
    private final List<GenMethod>      methods;
    private final List<GenClass>       innerClasses;
    private final List<ILines>         mores;
    
    public GenClass(
            Accessibility        accessibility,
            Scope                scope,
            Modifiability        modifiability,
            Type                 type,
            String               generic,
            List<Type>           extendeds,
            List<Type>           implementeds,
            List<GenConstructor> constructors,
            List<GenField>       fields,
            List<GenMethod>      methods,
            List<GenClass>       innerClasses,
            List<ILines>         mores) {
        this(accessibility, scope, modifiability,
                true,
                type, generic, extendeds, implementeds,
                constructors, fields, methods, innerClasses, mores);
    }
    
    public GenClass(
            Accessibility        accessibility,
            Scope                scope,
            Modifiability        modifiability,
            boolean              isClass,
            Type                 type,
            String               generic,
            List<Type>           extendeds,
            List<Type>           implementeds,
            List<GenConstructor> constructors,
            List<GenField>       fields,
            List<GenMethod>      methods,
            List<GenClass>       innerClasses,
            List<ILines>         mores) {
        this.accessibility = accessibility;
        this.scope         = scope;
        this.modifiability = modifiability;
        
        this.isClass = isClass;
        
        this.type         = type;
        this.generic      = generic;
        this.extendeds    = extendeds;
        this.implementeds = implementeds;
        this.constructors = constructors;
        this.fields       = fields;
        this.methods      = methods;
        this.innerClasses = innerClasses;
        this.mores        = mores;
    }
    
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
        var extendedList    = extendeds()   .stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        var implementedList = implementeds().stream().map(Type::simpleNameWithGeneric).collect(joining(",")).trim();
        
        var fieldDefs       = fields()      .stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        var constructorDefs = constructors().stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        var methodDefs      = methods()     .stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        var innerClassDefs  = innerClasses().stream().map(gen -> gen.toDefinition(currentPackage)).collect(toList());
        var moreDefs        = mores()       .stream().collect(toList());
        
        var className = type().simpleNameWithGeneric();
        var firstLine
                = oneLineOf(
                    accessibility, scope, modifiability, isClass ? "class" : "interface", className,
                    utils.prefixWith(extendedList,    "extends "),
                    utils.prefixWith(implementedList, "implements "),
                    "{");
        
        var lastLine = "}";
        
        var componentLines
                = linesOf(
                    fieldDefs,
                    constructorDefs,
                    methodDefs,
                    innerClassDefs,
                    moreDefs);
        
        var lines = flatenLines(withSeparateIndentedSpace(
                line  (firstLine),
                indent(componentLines),
                line  (lastLine)));
        return lines;
    }
    
}
