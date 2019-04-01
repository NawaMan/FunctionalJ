// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import functionalj.types.DefaultValue;
import lombok.val;

public class CaseParam {
    public final String  name;
    public final Type    type;
    public final boolean isNotNull;
    public final DefaultValue defValue;
    
    public CaseParam(String name, Type type, boolean isNotNull) {
        this(name, type, isNotNull, DefaultValue.REQUIRED);
    }
    
    public CaseParam(String name, Type type, boolean isNotNull, DefaultValue defValue) {
        this.name = name;
        this.type = type;
        this.isNotNull = isNotNull;
        this.defValue = defValue;
    }
    
    static private functionalj.types.struct.generator.Type toStructType(Type choiceType) {
        val encloseName = choiceType.encloseClass;
        val simpleName  = choiceType.name;
        val packageName = choiceType.pckg;
        val generics    = choiceType.generics.stream()
                        .map(g -> g.getBoundTypes().stream().findFirst().get())
                        .map(t -> toStructType(t))
                        .collect(toList());
        val structType = new functionalj.types.struct.generator.Type(encloseName, simpleName, packageName, generics);
        return structType;
    }
    
    public String defaultValueCode() {
        return (defValue == null) ? "null" : DefaultValue.defaultValueCode(toStructType(type), defValue);
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode(),
                "" + isNotNull,
                (defValue == null) ? "null" : DefaultValue.defaultValueCode(toStructType(type), defValue)
        );
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
    @Override
    public String toString() {
        return "CaseParam [name=" + name + ", type=" + type + ", isNotNull=" + isNotNull + "]";
    }
    
}