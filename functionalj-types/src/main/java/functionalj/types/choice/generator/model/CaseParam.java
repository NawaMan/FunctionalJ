// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.List;

import functionalj.types.DefaultValue;
import functionalj.types.Property;
import functionalj.types.Type;

public class CaseParam implements Property {
    
    private final String name;
    
    private final Type type;
    
    private final boolean isNullable;
    
    private final DefaultValue defValue;
    
    public CaseParam(String name, Type type, boolean isNullable) {
        this(name, type, isNullable, null);
    }
    
    public CaseParam(String name, Type type, boolean isNullable, DefaultValue defValue) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.defValue = defValue;
    }
    
    public String name() {
        return name;
    }
    
    public Type type() {
        return type;
    }
    
    public boolean isNullable() {
        return isNullable;
    }
    
    public DefaultValue defValue() {
        return defValue;
    }
    
    public CaseParam withName(String name) {
        return new CaseParam(name, type, isNullable);
    }
    
    public CaseParam withType(Type type) {
        return new CaseParam(name, type, isNullable);
    }
    
    public CaseParam withNullable(boolean isNullable) {
        return new CaseParam(name, type, isNullable);
    }
    
    public CaseParam withDefValue(DefaultValue defValue) {
        return new CaseParam(name, type, isNullable);
    }
    
    public String toCode() {
        List<String> params = asList(toStringLiteral(name), type.toCode(), "" + isNullable, (defValue == null) ? "null" : DefaultValue.defaultValueCode(type, defValue));
        return "new " + this.getClass().getCanonicalName() + "(" + params.stream().collect(joining(", ")) + ")";
    }
    
    @Override
    public String toString() {
        return "CaseParam [name=" + name + ", type=" + type + ", isNullable=" + isNullable + "]";
    }
}
