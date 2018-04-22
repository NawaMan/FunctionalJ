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
import nawaman.functionalj.annotations.processor.Core;

/**
 * Data type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Accessors(fluent=true)
@Wither
@Builder
public class Type implements IRequireTypes {
    
    /** int type */
    public static final Type INT     = new Type("int",     "");
    /** boolean type */
    public static final Type BOOL    = new Type("boolean", "");
    /** Integer type */
    public static final Type INTEGER = Type.of(Integer.class);
    /** Boolean type */
    public static final Type BOOLEAN = Type.of(Boolean.class);
    /** String type */
    public static final Type STRING  = Type.of(String .class);
    
    private static final Map<Type, Type> lensTypes = new HashMap<>();
    static {
        lensTypes.put(INT,     Core.IntegerLens.type());
        lensTypes.put(BOOL,    Core.BooleanLens.type());
        lensTypes.put(INTEGER, Core.IntegerLens.type());
        lensTypes.put(BOOLEAN, Core.BooleanLens.type());
        lensTypes.put(STRING,  Core.StringLens .type());
    }
    
    /**
     * Create a type of the given class.
     * 
     * @param clzz  the class.
     * @return      the type.
     */
    public static Type of(Class<?> clzz) {
        val pckg = clzz.getPackage().getName().toString();
        val name = clzz.getCanonicalName().toString().substring(pckg.length() + 1 );
        return new Type(name, pckg);
    }
    
    private String encloseName;
    private String simpleName;
    private String packageName;
    private String generic;
    
    /**
     * Construct a type with the parameters.
     * 
     * @param encloseName  the enclose component name.
     * @param simpleName   the simple name.
     * @param packageName  the package name.
     * @param generic      the generic value.
     */
    public Type(String encloseName, String simpleName, String packageName, String generic) {
        this.encloseName = encloseName;
        this.simpleName  = simpleName;
        this.packageName = packageName;
        this.generic     = generic;
    }
    
    /**
     * Construct a type with the parameters.
     * 
     * @param simpleName   the simple name.
     * @param packageName  the package name.
     */
    public Type(String simpleName, String packageName) {
        this(null, simpleName, packageName, null);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.of(this);
    }
    
    /**
     * Returns the full type name without the generic.
     * 
     * @return  the full name.
     */
    public String fullName() {
        return packageName + "." + ((encloseName != null) ? encloseName + "." : "") + simpleName;
    }
    
    /**
     * Returns the full type name with the generic.
     * 
     * @return  the full name.
     */
    public String fullNameWithGeneric() {
        return fullName() + ((generic != null) ? "<" + generic + ">" : "");
    }
    
    /**
     * Returns the simple name without the generic.
     * 
     * @return  the simple name.
     */
    public String simpleNameWithGeneric() {
        return simpleName() + ((generic != null) ? "<" + generic + ">" : "");
    }
    
    /**
     * Returns the declared type of this class (in case of non-primitive, declared type is the type).
     * 
     * @return  the declared type.
     */
    public Type declaredType() {
        if (INT.equals(this))
            return INTEGER;
        if (BOOL.equals(this))
            return BOOLEAN;
        return this;
    }
    
    /**
     * Returns the default value - to be used in the declaration of fields.
     * 
     * @return  the default value.
     */
    public Object defaultValue() {
        if (INT.equals(this))
            return 0;
        if (BOOL.equals(this))
            return false;
        return null;
    }
    
    /**
     * Returns the lens type of this type.
     * 
     * @return  the lens type.
     */
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