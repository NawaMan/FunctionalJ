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
package functionalj.types.struct.generator;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;

public class Callable {
    
    private final String name;
    
    private final Type type;
    
    private final boolean isVarAgrs;
    
    private final Accessibility accessibility;
    
    private final Scope scope;
    
    private final Modifiability modifiability;
    
    private final Concrecity concrecity;
    
    private final List<Parameter> parameters;
    
    private final List<Generic> generics;
    
    private final List<Type> exceptions;
    
    public Callable(String name, Type type, boolean isVarAgrs, Accessibility accessibility, Scope scope,
            Modifiability modifiability, Concrecity concrecity, List<Parameter> parameters, List<Generic> generics,
            List<Type> exceptions) {
        super();
        this.name = name;
        this.type = type;
        this.isVarAgrs = isVarAgrs;
        this.accessibility = accessibility;
        this.scope = scope;
        this.modifiability = modifiability;
        this.concrecity = concrecity;
        this.parameters = parameters;
        this.generics = generics;
        this.exceptions = exceptions;
    }
    
    public String name() {
        return name;
    }
    
    public Type type() {
        return type;
    }
    
    public boolean isVarAgrs() {
        return isVarAgrs;
    }
    
    public Accessibility accessibility() {
        return accessibility;
    }
    
    public Scope scope() {
        return scope;
    }
    
    public Modifiability modifiability() {
        return modifiability;
    }
    
    public Concrecity concrecity() {
        return concrecity;
    }
    
    public List<Parameter> parameters() {
        return parameters;
    }
    
    public List<Generic> generics() {
        return generics;
    }
    
    public List<Type> exceptions() {
        return exceptions;
    }
    
    public String toCode() {
        List<?> params = Arrays.asList(toStringLiteral(name), type.toCode(), isVarAgrs, (accessibility == null) ? "null" : (Accessibility.class.getCanonicalName() + "." + accessibility.name().toUpperCase()), (scope == null) ? "null" : (Scope.class.getCanonicalName() + "." + scope.name().toUpperCase()), (modifiability == null) ? "null" : (Modifiability.class.getCanonicalName() + "." + modifiability.name().toUpperCase()), (concrecity == null) ? "null" : (Concrecity.class.getCanonicalName() + "." + concrecity.name().toUpperCase()), toListCode(parameters, Parameter::toCode), toListCode(generics, Generic::toCode), toListCode(exceptions, Type::toCode));
        return "new functionalj.types.struct.generator.Callable(" + params.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ")";
    }
}
