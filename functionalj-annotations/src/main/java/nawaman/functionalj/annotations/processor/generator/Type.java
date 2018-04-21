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
package nawaman.functionalj.annotations.processor.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import lombok.Builder;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import nawaman.functionalj.lens.BooleanLens;
import nawaman.functionalj.lens.IntegerLens;
import nawaman.functionalj.lens.StringLens;

@Value
@Accessors(fluent=true)
@Wither
@Builder
public class Type implements IRequireTypes {
    
    public static final Type INT     = new Type("int",     "");
    public static final Type BOOL    = new Type("boolean", "");
    public static final Type STR     = new Type("String",  "");
    public static final Type INTEGER = Type.of(Integer.class);
    public static final Type BOOLEAN = Type.of(Boolean.class);
    public static final Type STRING  = Type.of(String .class);
    
    private static final Map<Type, Type> lensTypes = new HashMap<>();
    static {
        lensTypes.put(INT,     of(IntegerLens.class));
        lensTypes.put(BOOL,    of(BooleanLens.class));
        lensTypes.put(STR,     of(StringLens .class));
        lensTypes.put(INTEGER, of(IntegerLens.class));
        lensTypes.put(BOOLEAN, of(BooleanLens.class));
        lensTypes.put(STRING,  of(StringLens .class));
    }
    
    public static Type of(Class<?> clzz) {
        val pckg = clzz.getPackage().getName().toString();
        val name = clzz.getCanonicalName().toString().substring(pckg.length() + 1 );
        return new Type(name, pckg);
    }
    
    private String encloseName;
    private String simpleName;
    private String packageName;
    private String generic;
    
    public Type(String encloseName, String simpleName, String packageName, String generic) {
        this.encloseName = encloseName;
        this.simpleName  = simpleName;
        this.packageName = packageName;
        this.generic     = generic;
    }
    
    public Type(String simpleName, String packageName) {
        this(null, simpleName, packageName, null);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.of(this);
    }
    
    public String fullName() {
        return packageName + "." + ((encloseName != null) ? encloseName + "." : "") + simpleName;
    }
    
    public String fullNameWithGeneric() {
        return fullName() + ((generic != null) ? "<" + generic + ">" : "");
    }
    
    public String simpleNameWithGeneric() {
        return simpleName() + ((generic != null) ? "<" + generic + ">" : "");
    }
    
    public Type declaredType() {
        if (INT.equals(this))
            return INTEGER;
        if (BOOL.equals(this))
            return BOOLEAN;
        if (STR.equals(this))
            return STRING;
        return this;
    }
    public Object defaultValue() {
        if (INT.equals(this))
            return 0;
        if (BOOL.equals(this))
            return false;
        return null;
    }
    
    public Type lensType() {
        val lensType = lensTypes.get(this);
        if (lensType != null)
            return lensType;
        
        if (simpleName().endsWith("Lens"))
            return this;
        
        return new TypeBuilder()
                .encloseName(simpleName())
                .simpleName(simpleName() + "Lens")
                .packageName(packageName())
                .generic("HOST")
                .build();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Type other = (Type) obj;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        if (simpleName == null) {
            if (other.simpleName != null)
                return false;
        } else if (!simpleName.equals(other.simpleName))
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        result = prime * result + ((simpleName == null) ? 0 : simpleName.hashCode());
        return result;
    }
    
}