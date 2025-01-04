// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import functionalj.types.DefaultValue;
import functionalj.types.Property;
import functionalj.types.Type;

/**
 * Getter of the input spec.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class Getter implements Property {
    
    private final String name;
    
    private final Type type;
    
    private final boolean isNullable;
    
    private final DefaultValue defValue;
    
    /**
     * Create a getter for the name and type.
     *
     * @param name  the getter name.
     * @param type  the getter type.
     */
    public Getter(String name, Type type) {
        this(name, type, false, null);
    }
    
    /**
     * Create a getter for the name and type.
     * 
     * @param name          the getter name.
     * @param type          the getter type.
     * @param isNullable    nullable flag for this getter.
     * @param defaultValue  the default value to use.
     */
    public Getter(String name, Type type, boolean isNullable, DefaultValue defaultValue) {
        this.name = name;
        this.type = type;
        this.isNullable = isNullable;
        this.defValue = (defaultValue != null) ? defaultValue : DefaultValue.REQUIRED;
        if (!isNullable && (defValue == DefaultValue.NULL))
            throw new IllegalArgumentException("Non-nullable field can't have null as a default: " + name);
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
    
    public Getter withName(String name) {
        return new Getter(name, type, isNullable, defValue);
    }
    
    public Getter withType(Type type) {
        return new Getter(name, type, isNullable, defValue);
    }
    
    public Getter withNullable(boolean isNullable) {
        return new Getter(name, type, isNullable, defValue);
    }
    
    public Getter withDefValue(DefaultValue defValue) {
        return new Getter(name, type, isNullable, defValue);
    }
    
    public String toCode() {
        List<?> params = asList(toStringLiteral(name), type.toCode(), isNullable, DefaultValue.class.getCanonicalName() + "." + defValue);
        return "new functionalj.types.struct.generator.Getter(" + params.stream().map(String::valueOf).collect(joining(", ")) + ")";
    }
}
