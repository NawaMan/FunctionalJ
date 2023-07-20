// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.choice.generator.model;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import functionalj.types.Generic;
import functionalj.types.Type;

public class Method {
    
    public static enum Kind {
        DEFAULT, STATIC;
        
        public String toCode() {
            return this.getClass().getCanonicalName() + "." + this.toString();
        }
    }
    
    public final String signature;
    
    public final Kind kind;
    
    public final String name;
    
    public final Type returnType;
    
    public final List<MethodParam> params;
    
    public final List<Generic> generics;
    
    public final List<Type> exceptions;
    
    public Method(Kind kind, String name, Type returnType, List<MethodParam> params) {
        this(kind, name, returnType, params, new ArrayList<Generic>(), new ArrayList<Type>());
    }
    
    public Method(Kind kind, String name, Type returnType, List<MethodParam> params, List<Generic> generics, List<Type> exceptions) {
        this.kind = kind;
        this.name = name;
        this.returnType = returnType;
        this.params = params;
        this.generics = generics;
        this.exceptions = exceptions;
        this.signature = (Kind.STATIC.equals(kind) ? "static " : "") + returnType.toString() + " " + toString(param -> param.type.toString());
    }
    
    public String signature() {
        return signature;
    }
    
    public Kind kind() {
        return kind;
    }
    
    public String name() {
        return name;
    }
    
    public Type returnType() {
        return returnType;
    }
    
    public List<MethodParam> params() {
        return params;
    }
    
    public List<Generic> generics() {
        return generics;
    }
    
    public List<Type> exceptions() {
        return exceptions;
    }
    
    public String definition() {
        return returnType.toString() + " " + toString(param -> param.type.toString() + " " + param.name) + (exceptions.isEmpty() ? "" : " throws " + exceptions.stream().map(e -> e.toString()).collect(joining(",")));
    }
    
    public String definitionForThis() {
        AtomicBoolean isFirst = new AtomicBoolean(true);
        return returnType.toString() + " " + toString(param -> {
            boolean isFirstCall = isFirst.get();
            isFirst.set(false);
            return isFirstCall ? null : param.type.toString() + " " + param.name;
        }) + (exceptions.isEmpty() ? "" : " throws " + exceptions.stream().map(e -> e.toString()).collect(joining(",")));
    }
    
    public String call() {
        return toString(param -> param.name);
    }
    
    public String callForThis(Type type) {
        AtomicBoolean isFirst      = new AtomicBoolean(true);
        int           genericCount = type.generics().size();
        String        firstStr     = "this";
        return toString(param -> {
            boolean isFirstCall = isFirst.get();
            isFirst.set(false);
            String prefix = param.type.toString().equals(type.toString()) ? format("Self%1$s.wrap(", (genericCount != 0) ? "" + genericCount : "") : "";
            String suffix = param.type.toString().equals(type.toString()) ? ")" : "";
            return prefix + (isFirstCall ? firstStr : param.name) + suffix;
        });
    }
    
    public String toString(Function<? super MethodParam, ? extends String> paramMapper) {
        String paramsStr = params.stream().map(paramMapper).filter(Objects::nonNull).collect(joining(", "));
        return name + "(" + paramsStr + ")";
    }
    
    public String toCode() {
        List<String> parameters = asList(kind.toCode(), toStringLiteral(name), returnType.toCode(), toListCode(params, MethodParam::toCode), toListCode(generics, Generic::toCode), toListCode(exceptions, Type::toCode));
        return "new " + this.getClass().getCanonicalName() + "(" + parameters.stream().collect(joining(", ")) + ")";
    }
}
