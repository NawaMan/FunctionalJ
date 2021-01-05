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
package functionalj.types.struct.generator;

import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import functionalj.types.DefaultValue;
import functionalj.types.Type;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * Getter of the input spec.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@With
public class Getter {
    
    private String name;
    private Type type;
    private boolean nullable;
    private DefaultValue defaultTo;
    
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
     * @param name      the getter name.
     * @param type      the getter type.
     * @param nullable  nullable flag for this getter.
     */
    public Getter(String name, Type type, boolean nullable, DefaultValue defaultValue) {
        this.name      = name;
        this.type      = type;
        this.nullable  = nullable;
        this.defaultTo = (defaultValue != null) ? defaultValue : DefaultValue.REQUIRED;
        if (!nullable && (defaultTo == DefaultValue.NULL))
            throw new IllegalArgumentException("Non-nullable field can't have null as a default: " + name);
    }
    
    public boolean isRequired() {
        return defaultTo == DefaultValue.REQUIRED;
    }
    
    public String getDefaultValueCode(String orElse) {
        if (isRequired())
            return "$utils.notNull(" + orElse + ")";
        return DefaultValue.defaultValueCode(type, defaultTo);
    }
    
    public String toCode() {
        val params = asList(
                toStringLiteral(name),
                type.toCode(),
                nullable,
                DefaultValue.class.getCanonicalName() + "." + defaultTo
        );
        return "new functionalj.types.struct.generator.Getter("
                + params.stream().map(String::valueOf).collect(joining(", "))
                + ")";
    }
    
}
