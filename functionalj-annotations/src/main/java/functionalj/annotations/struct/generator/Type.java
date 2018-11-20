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

import static functionalj.annotations.sealed.generator.Utils.toListCode;
import static functionalj.annotations.sealed.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import functionalj.annotations.StructConversionException;
import functionalj.annotations.struct.Core;
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
    
    /** char type */
    public static final Type CHR = new Type("char", "");
    /** byte type */
    public static final Type BYT = new Type("byte", "");
    /** short type */
    public static final Type SHRT = new Type("short", "");
    /** int type */
    public static final Type INT = new Type("int", "");
    /** long type */
    public static final Type LNG = new Type("long", "");
    /** float type */
    public static final Type FLT = new Type("float", "");
    /** double type */
    public static final Type DBL = new Type("double", "");
    /** boolean type */
    public static final Type BOOL = new Type("boolean", "");
    
    /** Character type */
    public static final Type CHARACTER = Type.of(Character.class);
    /** Byte type */
    public static final Type BYTE = Type.of(Byte.class);
    /** Short type */
    public static final Type SHORT = Type.of(Short.class);
    /** Integer type */
    public static final Type INTEGER = Type.of(Integer.class);
    /** Long type */
    public static final Type LONG = Type.of(Long.class);
    /** Float type */
    public static final Type FLOAT = Type.of(Float.class);
    /** Double type */
    public static final Type DOUBLE = Type.of(Double.class);
    /** Boolean type */
    public static final Type BOOLEAN = Type.of(Boolean.class);
    
    /** BigInteger type */
    public static final Type BIGINTEGER = Type.of(BigInteger.class);
    /** BigDecimal type */
    public static final Type BIGDECIMAL = Type.of(BigDecimal.class);
    /** string type */
    public static final Type STR = new Type("String", "");
    /** String type */
    public static final Type STRING = Type.of(String .class);
    /** Object type */
    public static final Type OBJECT = Type.of(Object .class);
    /** List type */
    public static final Type LIST = Type.of(List.class);
    /** Map type */
    public static final Type MAP = Type.of(Map.class);
    /** Nullable type */
    public static final Type NULLABLE = Core.Nullable.type();
    /** Optional type */
    public static final Type OPTIONAL = Core.Optional.type();
    /** FuncList type */
    public static final Type FUNC_LIST = Core.FuncList.type();
    /** FuncMap type */
    public static final Type FUNC_MAP = Core.FuncMap.type();
    
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
        
        lensTypes.put(FUNC_LIST, Core.FuncListLens.type());
        lensTypes.put(FUNC_MAP,  Core.FuncMapLens.type());
    }
    public static final Map<String, Type> primitiveTypes;
    static {
        val map = new HashMap<String, Type>();
        map.put("char",    CHR);
        map.put("byte",    BYT);
        map.put("short",   SHRT);
        map.put("int",     INT);
        map.put("long",    LNG);
        map.put("float",   FLT);
        map.put("double",  DBL);
        map.put("boolean", BOOL);
        primitiveTypes = map;
    }
    public static final Map<Type, Type> declaredTypes;
    static {
        val map = new HashMap<Type, Type>();
        map.put(CHR,  CHARACTER);
        map.put(BYT,  BYTE);
        map.put(SHRT, SHORT);
        map.put(INT,  INTEGER);
        map.put(LNG,  LONG);
        map.put(FLT,  FLOAT);
        map.put(DBL,  DOUBLE);
        map.put(BOOL, BOOLEAN);
        declaredTypes = map;
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
        return declaredTypes.getOrDefault(this, this);
    }
    
    /**
     * Returns the default value - to be used in the declaration of fields.
     * 
     * @return  the default value.
     */
    public Object defaultValue() {
        if (BYT.equals(this))
            return (byte)0;
        if (SHRT.equals(this))
            return (short)0;
        if (INT.equals(this))
            return 0;
        if (LNG.equals(this))
            return 0L;
        if (FLT.equals(this))
            return 0.0f;
        if (DBL.equals(this))
            return 0.0;
        if (CHR.equals(this))
            return (char)0;
        if (BOOL.equals(this))
            return false;
        
        if (this.packageName().equals(Core.Nullable.packageName())
         && this.simpleName() .equals(Core.Nullable.simpleName()))
            return this.fullName() + ".empty()";
        
        if (this.packageName().equals(Core.Optional.packageName())
         && this.simpleName() .equals(Core.Optional.simpleName()))
            return this.fullName() + ".empty()";
        if (this.packageName().equals(Core.FuncList.packageName())
         && this.simpleName() .equals(Core.FuncList.simpleName()))
            return this.fullName() + ".empty()";
        if (this.packageName().equals(Core.FuncMap.packageName())
         && this.simpleName() .equals(Core.FuncMap.simpleName()))
            return this.fullName() + ".empty()";
        
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
    public boolean isFuncList() {
        return this.fullName("").equals("functionalj.list.FuncList");
    }
    
    /**
     * Check if this type is a functional map type.
     * 
     * @return {@code true} if this type is a functional map.
     */
    public boolean isFuncMap() {
        return this.fullName("").equals("functionalj.map.FuncMap");
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
    
    public String toCode() {
        val params = asList(
                toStringLiteral(encloseName),
                toStringLiteral(simpleName),
                toStringLiteral(packageName),
                toListCode     (generics, Type::toCode)
        );
        return "new Type("
                + params.stream().collect(joining(", "))
                + ")";
    }
    
    private static ConcurrentHashMap<Type, Object> classCache = new ConcurrentHashMap<>();
    
    @SuppressWarnings("unchecked")
    public <T> Class<T> toClass()  {
        val result = classCache.computeIfAbsent(this, t -> {
            if (Type.primitiveTypes.containsValue(t)) {
                if (Type.BYT.equals(t))
                    return byte.class;
                if (Type.SHRT.equals(t))
                    return short.class;
                if (Type.INT.equals(t))
                    return int.class;
                if (Type.LONG.equals(t))
                    return long.class;
                if (Type.FLT.equals(t))
                    return float.class;
                if (Type.DBL.equals(t))
                    return double.class;
                if (Type.CHR.equals(t))
                    return char.class;
                if (Type.BOOL.equals(t))
                    return boolean.class;
            }
            
            try {
                val fullName = t.fullName();
                return Class.forName(fullName);
            } catch (Exception e) {
                return e;
            }
        });
        if (result instanceof Exception)
            throw new StructConversionException((Exception)result);
        
        return (Class<T>)result;
    }
    
}