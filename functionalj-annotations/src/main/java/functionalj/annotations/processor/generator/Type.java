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
package functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import functionalj.annotations.processor.Core;
import lombok.Builder;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;

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
    public static final Type INT = new Type("int", "");
    /** long type */
    public static final Type LNG = new Type("long", "");
    /** double type */
    public static final Type DBL = new Type("double", "");
    /** boolean type */
    public static final Type BOOL = new Type("boolean", "");
    /** string type */
    public static final Type STR = new Type("String", "");
    /** Integer type */
    public static final Type INTEGER = Type.of(Integer.class);
    /** Long type */
    public static final Type LONG = Type.of(Long.class);
    /** Double type */
    public static final Type DOUBLE = Type.of(Double.class);
    /** BigInteger type */
    public static final Type BIGINTEGER = Type.of(BigInteger.class);
    /** BigDecimal type */
    public static final Type BIGDECIMAL = Type.of(BigDecimal.class);
    /** Boolean type */
    public static final Type BOOLEAN = Type.of(Boolean.class);
    /** String type */
    public static final Type STRING = Type.of(String .class);
    /** List type */
    public static final Type LIST = Type.of(List.class);
    /** Map type */
    public static final Type MAP = Type.of(Map.class);
    /** Nullable type */
    public static final Type NULLABLE = Core.Nullable.type();
    /** Optional type */
    public static final Type OPTIONAL = Core.Optional.type();
    /** MayBe type */
    public static final Type MAYBE = Core.MayBe.type();
    /** FunctionalList type */
    public static final Type FUNCTIONAL_LIST = Core.FunctionalList.type();
    /** FunctionalMap type */
    public static final Type FUNCTIONAL_MAP = Core.FunctionalMap.type();
    
    // These are lens types that are in the main lens package.
    private static final Map<Type, Type> lensTypes = new HashMap<>();
    static {
        lensTypes.put(INT,        Core.IntegerLens   .type());
        lensTypes.put(LNG,        Core.LongLens      .type());
        lensTypes.put(DBL,        Core.DoubleLens    .type());
        lensTypes.put(BOOL,       Core.BooleanLens   .type());
        lensTypes.put(STR,        Core.StringLens    .type());
        lensTypes.put(INTEGER,    Core.IntegerLens   .type());
        lensTypes.put(LONG,       Core.LongLens      .type());
        lensTypes.put(DOUBLE,     Core.DoubleLens    .type());
        lensTypes.put(BIGINTEGER, Core.BigIntegerLens.type());
        lensTypes.put(BIGDECIMAL, Core.BigDecimalLens.type());
        lensTypes.put(BOOLEAN,    Core.BooleanLens   .type());
        lensTypes.put(STRING,     Core.StringLens    .type());
        lensTypes.put(LIST,       Core.ListLens      .type());
        lensTypes.put(MAP,        Core.MapLens       .type());
        lensTypes.put(NULLABLE,   Core.NullableLens  .type());
        lensTypes.put(OPTIONAL,   Core.OptionalLens  .type());
        lensTypes.put(MAYBE,      Core.MayBeLens     .type());
        
        lensTypes.put(FUNCTIONAL_LIST, Core.FunctionalListLens.type());
        lensTypes.put(FUNCTIONAL_MAP,  Core.FunctionalMapLens.type());
    }
    
    /**
     * Create a type of the given class.
     * 
     * @param clzz      the class.
     * @param generics  the generic for this type.
     * @return      the type.
     */
    public static Type of(Class<?> clzz, Type ... generics) {
        val pckg = clzz.getPackage().getName().toString();
        val name = clzz.getCanonicalName().toString().substring(pckg.length() + 1 );
        return new Type(name, pckg).withGenerics(asList(generics));
    }
    
    private String     encloseName;
    private String     simpleName;
    private String     packageName;
    private List<Type> generics;
    
    /**
     * Construct a type with the parameters.
     * 
     * @param encloseName  the enclose component name.
     * @param simpleName   the simple name.
     * @param packageName  the package name.
     * @param generics     the generic value.
     */
    public Type(String encloseName, String simpleName, String packageName, String ... generics) {
        this.encloseName = encloseName;
        this.simpleName  = simpleName;
        this.packageName = packageName;
        this.generics    = asList(generics).stream().map(generic->new Type(generic, null)).collect(toList());
    }
    
    /**
     * Construct a type with the parameters.
     * 
     * @param encloseName  the enclose component name.
     * @param simpleName   the simple name.
     * @param packageName  the package name.
     * @param generics     the generic value.
     */
    public Type(String encloseName, String simpleName, String packageName, Type ... generics) {
        this(encloseName, simpleName, packageName, asList(generics));
    }
    /**
     * Construct a type with the parameters.
     * 
     * @param encloseName  the enclose component name.
     * @param simpleName   the simple name.
     * @param packageName  the package name.
     * @param generics     the generic value.
     */
    public Type(String encloseName, String simpleName, String packageName, List<Type> generics) {
        this.encloseName = encloseName;
        this.simpleName  = simpleName;
        this.packageName = packageName;
        
        List<Type> genericList = (generics == null)
                            ? null
                            : generics.stream().filter(Objects::nonNull).collect(toList());
        this.generics = ((genericList == null) || genericList.isEmpty()) ? new ArrayList<Type>() : genericList;
    }
    
    /**
     * Construct a type with the parameters.
     * 
     * @param simpleName   the simple name.
     * @param packageName  the package name.
     */
    public Type(String simpleName, String packageName) {
        this(null, simpleName, packageName, (List<Type>)null);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.concat(
                Stream.of(this), 
                this.generics()
                    .stream()
                    .filter(type -> !((type.packageName == null) && (type.encloseName == null))));
    }
    
    /**
     * Returns the full type name without the generic without the package name opt-out when same package.
     * 
     * @return  the full name.
     */
    public String fullName() {
        return fullName("");
    }
    /**
     * Returns the full type name without the generic.
     * 
     * @param currentPackage  the current package so that the package can be opt-out or null if it should never.
     * @return  the full name.
     */
    public String fullName(String currentPackage) {
        return getPackageText(currentPackage) + getEncloseNameText() + simpleName;
    }

    private String getEncloseNameText() {
        return (encloseName != null) ? encloseName + "." : "";
    }

    private String getPackageText(String currentPackage) {
        if ((currentPackage == null) || currentPackage.equals(packageName))
            return "";
        
        return (packageName == null) ? "" : packageName + ".";
    }
    
    /**
     * Returns the full type name with the generic.
     * 
     * @param currentPackage  the current package.
     * @return  the full name.
     */
    public String fullNameWithGeneric(String currentPackage) {
        return fullName(currentPackage) + getGenericText(currentPackage);
    }
    
    /**
     * Returns the simple name without the generic.
     * 
     * @return  the simple name.
     */
    public String simpleNameWithGeneric() {
        return simpleNameWithGeneric("");
    }
    
    /**
     * Returns the simple name without the generic.
     * 
     * @param currentPackage  the current package.
     * @return  the simple name.
     */
    public String simpleNameWithGeneric(String currentPackage) {
        return simpleName() + getGenericText(currentPackage);
    }
    
    private String getGenericText(String currentPackage) {
        if (generics == null)
            return "";
        if (generics.isEmpty())
            return "";
        return "<" + generics.stream()
                .map(type->type.simpleNameWithGeneric())
                .collect(joining(", "))
             + ">";
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
        val lensType = lensTypes.get(this.declaredType());
        if (lensType != null)
            return lensType;
        
        if (simpleName().endsWith("Lens"))
            return this;
        
        return new TypeBuilder()
                .encloseName(simpleName())
                .simpleName(simpleName() + "Lens")
                .packageName(packageName())
                .generics(asList(new Type("HOST", "")))
                .build();
    }

    /**
     * Check if this lens is custom lens.
     * 
     * @return {@code true} if this lens is a custom lens.
     */
    public boolean isCustomLens() {
        val lensType = this.lensType();
        return !lensTypes.values().contains(lensType)
            && !lensTypes.values().stream()
             .anyMatch(type -> type.simpleName() .equals(lensType.simpleName())
                            && type.packageName().equals(lensType.packageName()));
    }
    
    /**
     * Check if this type is a list type.
     * 
     * @return {@code true} if this type is a list.
     */
    public boolean isList() {
        return this.fullName("").equals("java.util.List");
    }
    
    /**
     * Check if this type is a map type.
     * 
     * @return {@code true} if this type is a map.
     */
    public boolean isMap() {
        return this.fullName("").equals("java.util.Map");
    }
    
    /**
     * Check if this type is a functional list type.
     * 
     * @return {@code true} if this type is a functional list.
     */
    public boolean isFunctionalList() {
        return this.fullName("").equals("functionalj.types.list.FunctionalList");
    }
    
    /**
     * Check if this type is a functional map type.
     * 
     * @return {@code true} if this type is a functional map.
     */
    public boolean isFunctionalMap() {
        return this.fullName("").equals("functionalj.types.map.FunctionalMap");
    }
    
    /**
     * Check if this type is a nullable type.
     * 
     * @return {@code true} if this type is a nullable.
     */
    public boolean isNullable() {
        return this.fullName("").equals("nawaman.nullablej.nullable.Nullable");
    }
    
    /**
     * Check if this type is a Optional type.
     * 
     * @return {@code true} if this type is a Optional.
     */
    public boolean isOptional() {
        return this.fullName("").equals("java.util.Optional");
    }
    
    /**
     * Check if this type is a MayBe type.
     * 
     * @return {@code true} if this type is a MayBe.
     */
    public boolean isMayBe() {
        return this.fullName("").equals("functionalj.types.MayBe");
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