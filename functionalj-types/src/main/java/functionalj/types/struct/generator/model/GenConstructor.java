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

import static functionalj.types.struct.generator.ILines.indent;
import static functionalj.types.struct.generator.ILines.line;
import static java.util.stream.Collectors.joining;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import lombok.val;


/**
 * Representation of a generated constructor.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@With
@EqualsAndHashCode(callSuper=false)
public class GenConstructor implements IGenerateDefinition {
    
    private Accessibility  accessibility;
    private String         name;
    private List<GenParam> params;
    private ILines         body;
    
    @Override
    public Stream<Type> requiredTypes() {
        Set<Type> types = new HashSet<Type>();
        for (val param : params) {
            val paramType = param.getType();
            if (types.contains(paramType))
                continue;
            
            paramType
                .requiredTypes()
                .forEach(types::add);
            param
                .requiredTypes()
                .forEach(types::add);
            if (paramType.isList())
                types.add(Core.ImmutableFuncList.type());
            if (paramType.isMap())
                types.add(Core.ImmutableFuncMap.type());
            if (paramType.isFuncList())
                types.add(Core.ImmutableFuncList.type());
            if (paramType.isFuncMap())
                types.add(Core.ImmutableFuncMap.type());
            if (paramType.isNullable())
                types.add(Core.Nullable.type());
            if (paramType.isOptional())
                types.add(Core.Optional.type());
        }
        return types.stream();
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        val paramDefs = params.stream().map(param -> param.toTerm(currentPackage)).collect(joining(", "));
        val definition = Stream.of(accessibility, name + "(" + paramDefs + ")", "{")
                .map    (utils.toStr())
                .filter (Objects::nonNull)
                .collect(joining(" "));
        return ILines.flatenLines(
                line  (definition),
                indent(body),
                line  ("}"));
    }
    
}
