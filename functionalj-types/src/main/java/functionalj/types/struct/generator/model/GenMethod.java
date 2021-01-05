// ============================================================================
// Copyright(c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import lombok.val;


/**
 * Representation of generated method.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@With
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class GenMethod implements IGenerateDefinition {
    
    private Accessibility  accessibility;
    private Scope          scope;
    private Modifiability  modifiability;
    private Type           type;
    private String         name;
    private List<GenParam> params;
    private ILines         body;
    private List<Type>     usedTypes;
    private List<Type>     exceptions;
    private boolean        isVarAgrs;
    
    /**
     * Constructor a GenMethod.
     * @param accessibility  the method  accessibility.
     * @param scope          the method score.
     * @param modifiability  the method score.
     * @param type           the method return type.
     * @param name           the name of the method.
     * @param params         the parameters if the method.
     * @param body           the method body.
     */
    public GenMethod(Accessibility accessibility, Scope scope, Modifiability modifiability, Type type, String name,
            List<GenParam> params, ILines body) {
        this(accessibility, scope, modifiability, type, name, params, body, emptyList(), emptyList(), false);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        Set<Type> types = new HashSet<Type>();
        types.add(type);
        
        for (GenParam param : params) {
            val paramType = param.getType();
            if (types.contains(paramType))
                continue;
            
            paramType
                .requiredTypes()
                .forEach(types::add);
            param
                .requiredTypes()
                .forEach(types::add);
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        val streams = Stream.of(
                    types.stream(),
                    (usedTypes  != null) ? usedTypes .stream() : (Stream<Type>)(Stream)Stream.empty(),
                    (exceptions != null) ? exceptions.stream() : (Stream<Type>)(Stream)Stream.empty()
                );
        return streams.flatMap(s -> s);
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        val paramDefs
            = params.stream()
                .map(param -> param.toTerm(currentPackage))
                .collect(joining(", "));
        val paramDefsToText
            = isVarAgrs
            ? paramDefs.replaceAll("([^ ]+)$", "... $1")
            : paramDefs;
        val throwing = (exceptions == null || exceptions.isEmpty())
                     ? ""
                     : (" throws " + exceptions.stream().map(type -> type.simpleName()).collect(Collectors.joining(", ")) + " ");
        val lineEnd = throwing + ((body == null) ? ";" : " {");
        val definition
                = ILines.oneLineOf(
                    accessibility, modifiability, scope,
                    type.simpleNameWithGeneric(""), name + "(" + paramDefsToText + ")" + lineEnd);
        return ILines.flatenLines(
                line(definition),
                (body == null) ? line("") : indent(body),
                (body == null) ? line("") : line("}"));
    }
    
}
