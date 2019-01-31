//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.struct.generator;

import static functionalj.annotations.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import functionalj.annotations.DefaultValue;
import lombok.Value;
import lombok.experimental.Wither;

/**
 * Getter of the input spec.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
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
            throw new IllegalArgumentException("Nullable field can't have null as a default: " + name);
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
        List<Object> params = asList(
                toStringLiteral(name),
                type.toCode(),
                nullable,
                DefaultValue.class.getCanonicalName() + "." + defaultTo
        );
        return "new functionalj.annotations.struct.generator.Getter("
                + params.stream().map(String::valueOf).collect(joining(", "))
                + ")";
    }
    
}