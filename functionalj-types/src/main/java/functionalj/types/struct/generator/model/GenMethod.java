// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.IGenerateDefinition;
import functionalj.types.struct.generator.ILines;

/**
 * Representation of generated method.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class GenMethod implements IGenerateDefinition {
    
    private final String name;
    
    private final Type type;
    
    private final Accessibility accessibility;
    
    private final Scope scope;
    
    private final Modifiability modifiability;
    
    private final List<GenParam> params;
    
    private final List<Generic> generics;
    
    private final boolean isAbstract;
    
    private final boolean isVarAgrs;
    
    private final ILines body;
    
    private final List<Type> usedTypes;
    
    private final List<Type> exceptions;
    
    /**
     * Constructor a GenMethod.
     * @param name           the name of the method.
     * @param type           the method return type.
     * @param accessibility  the method  accessibility.
     * @param scope          the method score.
     * @param modifiability  the method score.
     * @param params         the parameters if the method.
     * @param body           the method body.
     */
    public GenMethod(String name, Type type, Accessibility accessibility, Scope scope, Modifiability modifiability, List<GenParam> params, ILines body) {
        this(name, type, accessibility, scope, modifiability, params, emptyList(), false, false, body, emptyList(), emptyList());
    }
    
    public GenMethod(String name, Type type, Accessibility accessibility, Scope scope, Modifiability modifiability,
            List<GenParam> params, List<Generic> generics, boolean isAbstract, boolean isVarAgrs, ILines body,
            List<Type> usedTypes, List<Type> exceptions) {
        super();
        this.name = name;
        this.type = type;
        this.accessibility = accessibility;
        this.scope = scope;
        this.modifiability = modifiability;
        this.params = params;
        this.generics = generics;
        this.isAbstract = isAbstract;
        this.isVarAgrs = isVarAgrs;
        this.body = body;
        this.usedTypes = usedTypes;
        this.exceptions = exceptions;
    }
    
    public String getName() {
        return name;
    }
    
    public Type getType() {
        return type;
    }
    
    public Accessibility getAccessibility() {
        return accessibility;
    }
    
    public Scope getScope() {
        return scope;
    }
    
    public Modifiability getModifiability() {
        return modifiability;
    }
    
    public List<GenParam> getParams() {
        return params;
    }
    
    public List<Generic> getGenerics() {
        return generics;
    }
    
    public boolean isAbstract() {
        return isAbstract;
    }
    
    public boolean isVarAgrs() {
        return isVarAgrs;
    }
    
    public ILines getBody() {
        return body;
    }
    
    public List<Type> getUsedTypes() {
        return usedTypes;
    }
    
    public List<Type> getExceptions() {
        return exceptions;
    }
    
    public GenMethod withName(String name) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withType(Type type) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withAccessibility(Accessibility accessibility) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withScope(Scope scope) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withModifiability(Modifiability modifiability) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withParams(List<GenParam> params) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withGenerics(List<Generic> generics) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withAbstract(boolean isAbstract) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withVarAgrs(boolean isVarAgrs) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withBody(ILines body) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withUsedTypes(List<Type> usedTypes) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    public GenMethod withExceptions(List<Type> exceptions) {
        return new GenMethod(name, type, accessibility, scope, modifiability, params, generics, isAbstract, isVarAgrs, body, usedTypes, exceptions);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        Set<Type> types = new HashSet<Type>();
        types.add(type);
        for (GenParam param : params) {
            Type paramType = param.getType();
            if (types.contains(paramType))
                continue;
            paramType.requiredTypes().forEach(types::add);
            param.requiredTypes().forEach(types::add);
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        Stream<Stream<Type>> streams = Stream.of(types.stream(), (usedTypes != null) ? usedTypes.stream() : (Stream<Type>) (Stream) Stream.empty(), (exceptions != null) ? exceptions.stream() : (Stream<Type>) (Stream) Stream.empty());
        return streams.flatMap(s -> s);
    }
    
    @Override
    public ILines toDefinition(String currentPackage) {
        int                       length     = params.size();
        AtomicInteger             paramIndex = new AtomicInteger(0);
        String                    paramDefs  = params.stream().map(param -> paramToTerm(currentPackage, param, isVarAgrs && (paramIndex.getAndIncrement() == (length - 1)))).collect(joining(", "));
        Function<Generic, String> toGeneric  = (Function<Generic, String>) (generic -> {
            String bounds = generic.boundTypes.stream().map(Type::fullName).collect(joining(" & "));
            return generic.name + ((bounds.isEmpty() || bounds.equals(Type.OBJECT.fullName())) ? "" : (" extends " + bounds));
        });
        String genericDef = generics.stream().map(toGeneric).collect(joining(","));
        String throwing   = (exceptions == null || exceptions.isEmpty()) ? "" : (" throws " + exceptions.stream().map(type -> type.simpleName()).collect(joining(", ")) + " ");
        String lineEnd    = throwing + ((body == null) ? ";" : " {");
        String definition = ILines.oneLineOf(accessibility, modifiability, scope, (genericDef.length() == 0) ? "" : ("<" + genericDef + ">"), type.simpleNameWithGeneric(""), name + "(" + paramDefs + ")" + lineEnd);
        return ILines.flatenLines(line(definition), (body == null) ? line("") : indent(body), (body == null) ? line("") : line("}"));
    }
    
    private String paramToTerm(String currentPackage, GenParam param, boolean isVarAgrs) {
        String term = param.toTerm(currentPackage);
        if (isVarAgrs) {
            term = term.replaceAll("(\\[\\])? ", " ... ");
        }
        return term;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GenMethod other = (GenMethod) obj;
        return Objects.equals(accessibility, other.accessibility)
            && Objects.equals(body,          other.body)
            && Objects.equals(exceptions,    other.exceptions)
            && Objects.equals(generics,      other.generics)
            && Objects.equals(isAbstract,    other.isAbstract)
            && Objects.equals(isVarAgrs,     other.isVarAgrs)
            && Objects.equals(modifiability, other.modifiability)
            && Objects.equals(name,          other.name)
            && Objects.equals(params,        other.params)
            && Objects.equals(scope,         other.scope)
            && Objects.equals(type,          other.type)
            && Objects.equals(usedTypes,     other.usedTypes);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(accessibility, body, exceptions, generics, isAbstract, isVarAgrs, modifiability, name,
                params, scope, type, usedTypes);
    }
    
}
