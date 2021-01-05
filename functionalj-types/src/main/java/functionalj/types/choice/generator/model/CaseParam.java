// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.types.DefaultValue;
import functionalj.types.Type;
import lombok.val;


public class CaseParam {
    
    public final String  name;
    public final Type    type;
    public final boolean isNullable;
    public final DefaultValue defValue;
    
    public CaseParam(String name, Type type, boolean isNullable) {
        this(name, type, isNullable, null);
    }
    
    public CaseParam(String name, Type type, boolean isNullable, DefaultValue defValue) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.defValue = defValue;
    }
    
    public String defaultValueCode() {
        return (defValue == null) ? "null" : DefaultValue.defaultValueCode(type, defValue);
    }
    
    public Object defaultValue() {
        return (defValue == null) ? "null" : DefaultValue.defaultValue(type, defValue);
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode(),
                "" + isNullable,
                (defValue == null) ? "null" : DefaultValue.defaultValueCode(type, defValue)
        );
        return "new " + this.getClass().getCanonicalName() + "("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
    @Override
    public String toString() {
        return "CaseParam [name=" + name + ", type=" + type + ", isNullable=" + isNullable + "]";
    }
    
}
