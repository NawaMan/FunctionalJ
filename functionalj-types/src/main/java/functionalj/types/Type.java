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
package functionalj.types;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class Type implements IRequireTypes {
    
    /** Self */
    public static final Type SELF = Type.of(Self.class);
    
    /** Self1 */
    public static final Type SELF1 = Type.of(Self1.class);
    
    /** Self2 */
    public static final Type SELF2 = Type.of(Self2.class);
    
    /**
     * char AbstractType
     */
    public static final Type CHR = new Type("char");
    
    /**
     * byte AbstractType
     */
    public static final Type BYT = new Type("byte");
    
    /**
     * short AbstractType
     */
    public static final Type SHRT = new Type("short");
    
    /**
     * int AbstractType
     */
    public static final Type INT = new Type("int");
    
    /**
     * long AbstractType
     */
    public static final Type LNG = new Type("long");
    
    /**
     * float AbstractType
     */
    public static final Type FLT = new Type("float");
    
    /**
     * double AbstractType
     */
    public static final Type DBL = new Type("double");
    
    /**
     * boolean AbstractType
     */
    public static final Type BOOL = new Type("boolean");
    
    /**
     * Character AbstractType
     */
    public static final Type CHARACTER = Type.of(Character.class);
    
    /**
     * Byte AbstractType
     */
    public static final Type BYTE = Type.of(Byte.class);
    
    /**
     * Short AbstractType
     */
    public static final Type SHORT = Type.of(Short.class);
    
    /**
     * Integer AbstractType
     */
    public static final Type INTEGER = Type.of(Integer.class);
    
    /**
     * Long AbstractType
     */
    public static final Type LONG = Type.of(Long.class);
    
    /**
     * Float AbstractType
     */
    public static final Type FLOAT = Type.of(Float.class);
    
    /**
     * Double AbstractType
     */
    public static final Type DOUBLE = Type.of(Double.class);
    
    /**
     * Boolean AbstractType
     */
    public static final Type BOOLEAN = Type.of(Boolean.class);
    
    /**
     * BigInteger AbstractType
     */
    public static final Type BIGINTEGER = Type.of(BigInteger.class);
    
    /**
     * BigDecimal AbstractType
     */
    public static final Type BIGDECIMAL = Type.of(BigDecimal.class);
    
    /**
     * string AbstractType
     */
    public static final Type STR = new Type("String");
    
    /**
     * String AbstractType
     */
    public static final Type STRING = Type.of(String.class);
    
    /**
     * Object AbstractType
     */
    public static final Type OBJECT = Type.of(Object.class);
    
    /**
     * UUID AbstractType
     */
    public static final Type UUID = Type.of(java.util.UUID.class);
    
    /**
     * List AbstractType
     */
    public static final Type LIST = Type.of(List.class);
    
    /**
     * Map AbstractType
     */
    public static final Type MAP = Type.of(Map.class);
    
    /**
     * Nullable AbstractType
     */
    public static final Type NULLABLE = Core.Nullable.type();
    
    /**
     * Optional AbstractType
     */
    public static final Type OPTIONAL = Core.Optional.type();
    
    /**
     * FuncList AbstractType
     */
    public static final Type FUNC_LIST = Core.FuncList.type();
    
    /**
     * FuncMap AbstractType
     */
    public static final Type FUNC_MAP = Core.FuncMap.type();
    
    /**
     * Serialize AbstractType
     */
    public static final Type SERIALIZE = Type.of(Serialize.class);
    
    static public class TypeBuilder {
        
        private String packageName;
        
        private String encloseName;
        
        private String simpleName;
        
        private boolean isVirtual;
        
        private List<Generic> generics;
        
        public TypeBuilder encloseName(String encloseName) {
            this.encloseName = encloseName;
            return this;
        }
        
        public TypeBuilder simpleName(String simpleName) {
            this.simpleName = simpleName;
            return this;
        }
        
        public TypeBuilder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }
        
        public TypeBuilder isVirtual(boolean isVirtual) {
            this.isVirtual = isVirtual;
            return this;
        }
        
        public TypeBuilder generics(List<Generic> generics) {
            this.generics = generics;
            return this;
        }
        
        public Type build() {
            return new Type(encloseName, simpleName, packageName, isVirtual, generics);
        }
    }
    
    private final String simpleName;
    
    private final String encloseName;
    
    private final String packageName;
    
    private final boolean isVirtual;
    
    private final List<Generic> generics;
    
    public Type(String simpleName) {
        this(null, simpleName);
    }
    
    /**
     * Construct a AbstractType with the parameters.
     * @param packageName  the package name.
     * @param simpleName   the simple name.
     */
    public Type(String packageName, String simpleName) {
        this(packageName, null, simpleName, (List<Generic>) null);
    }
    
    /**
     * Construct a AbstractType with the parameters.
     * @param packageName  the package name.
     * @param encloseName  the enclose component name.
     * @param simpleName   the simple name.
     * @param generics     the generic value.
     */
    public Type(String packageName, String encloseName, String simpleName, String... generics) {
        this.encloseName = encloseName;
        this.simpleName = simpleName;
        this.packageName = packageName;
        this.isVirtual = false;
        this.generics = asList(generics).stream().map(generic -> new Generic(generic)).collect(toList());
    }
    
    /**
     * Construct a AbstractType with the parameters.
     * @param packageName  the package name.
     * @param encloseName  the enclose component name.
     * @param simpleName   the simple name.
     * @param generics     the generic value.
     */
    public Type(String packageName, String encloseName, String simpleName, List<Generic> generics) {
        this.encloseName = encloseName;
        this.simpleName = simpleName;
        this.packageName = packageName;
        this.isVirtual = false;
        List<Generic> genericList = (generics == null) ? null : generics.stream().filter(Objects::nonNull).collect(toList());
        this.generics = Collections.unmodifiableList(((genericList == null) || genericList.isEmpty()) ? new ArrayList<Generic>() : new ArrayList<Generic>(generics));
    }
    
    public Type(String encloseName, String simpleName, String packageName, boolean isVirtual, List<Generic> generics) {
        this.encloseName = encloseName;
        this.simpleName = simpleName;
        this.packageName = packageName;
        this.isVirtual = isVirtual;
        this.generics = generics;
    }
    
    private Type(String simpleName, boolean isVirtual) {
        if (!isVirtual)
            throw new IllegalArgumentException();
        this.encloseName = null;
        this.simpleName = simpleName;
        this.packageName = null;
        this.isVirtual = isVirtual;
        this.generics = emptyList();
    }
    
    public static Type newVirtualType(String name) {
        return new Type(name, true);
    }
    
    /**
     * Create a type of the given class.
     *
     * @param clzz      the class.
     * @param generics  the generic for this type.
     * @return      the type.
     */
    public static Type of(Class<?> clzz, Generic... generics) {
        String pckg = clzz.getPackage().getName().toString();
        String name = clzz.getCanonicalName().toString().substring(pckg.length() + 1);
        return new Type(pckg, name).withGenerics(asList(generics));
    }
    
    public String packageName() {
        return packageName;
    }
    
    public String encloseName() {
        return encloseName;
    }
    
    public String simpleName() {
        return simpleName;
    }
    
    public boolean isVirtual() {
        return isVirtual;
    }
    
    public List<Generic> generics() {
        return (generics == null) ? emptyList() : generics;
    }
    
    @Override
    public String toString() {
        String generics = ofNullable(this.generics).filter(l -> !l.isEmpty()).map(l -> this.generics.stream()).map(s -> s.map(this::genericToString)).map(c -> c.collect(joining(","))).map(s -> "<" + s + ">").orElse("");
        return asList(packageName, encloseName, simpleName + generics).stream().filter(Objects::nonNull).collect(joining("."));
    }
    
    private String genericToString(Generic generic) {
        return ((generic.name != null) && !generic.name.contains(".")) ? generic.name : generic.toType().toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Type)
            return toString().equals(String.valueOf(obj));
        return false;
    }
    
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    
    public String toCode() {
        List<String> params = asList(toStringLiteral(packageName), toStringLiteral(encloseName), toStringLiteral(simpleName), toListCode(generics, Generic::toCode));
        return "new " + Type.class.getCanonicalName() + "(" + params.stream().collect(joining(", ")) + ")";
    }
    
    public boolean isPrimitive() {
        return primitiveTypes.containsValue(this);
    }
    
    @Override
    public Stream<Type> requiredTypes() {
        return Stream.concat(Stream.of(this), this.generics().stream().flatMap(generic -> generic.boundTypes.stream()).filter(type -> !((type.packageName() == null) && (type.encloseName() == null))));
    }
    
    private static ConcurrentHashMap<Type, Object> classCache = new ConcurrentHashMap<Type, Object>();
    
    @SuppressWarnings("unchecked")
    public <T> Class<T> toClass() {
        Object result = classCache.computeIfAbsent(this, t -> {
            if (Type.primitiveTypes.containsValue(t)) {
                if (Type.BYT.equals(t))
                    return byte.class;
                if (Type.SHRT.equals(t))
                    return short.class;
                if (Type.INT.equals(t))
                    return int.class;
                if (Type.LNG.equals(t))
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
                String fullName = t.fullName();
                return Class.forName(fullName);
            } catch (Exception e) {
                return e;
            }
        });
        if (result instanceof Exception)
            throw new StructConversionException((Exception) result);
        return (Class<T>) result;
    }
    
    public static final Map<String, Type> primitiveTypes;
    
    static {
        Map<String, Type> map = new HashMap<String, Type>();
        map.put("char", CHR);
        map.put("byte", BYT);
        map.put("short", SHRT);
        map.put("int", INT);
        map.put("long", LNG);
        map.put("float", FLT);
        map.put("double", DBL);
        map.put("boolean", BOOL);
        primitiveTypes = map;
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
        return asList(packageName, encloseName, simpleName.replaceAll("\\[\\]$", "")).stream().filter(Objects::nonNull).collect(joining("."));
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
            return (byte) 0;
        if (SHRT.equals(this))
            return (short) 0;
        if (INT.equals(this))
            return 0;
        if (LNG.equals(this))
            return 0L;
        if (FLT.equals(this))
            return 0.0f;
        if (DBL.equals(this))
            return 0.0;
        if (CHR.equals(this))
            return (char) 0;
        if (BOOL.equals(this))
            return false;
        if (this.packageName().equals(Core.Nullable.packageName()) && this.simpleName().equals(Core.Nullable.simpleName()))
            return this.fullName() + ".empty()";
        if (this.packageName().equals(Core.Optional.packageName()) && this.simpleName().equals(Core.Optional.simpleName()))
            return this.fullName() + ".empty()";
        if (this.packageName().equals(Core.FuncList.packageName()) && this.simpleName().equals(Core.FuncList.simpleName()))
            return this.fullName() + ".empty()";
        if (this.packageName().equals(Core.FuncMap.packageName()) && this.simpleName().equals(Core.FuncMap.simpleName()))
            return this.fullName() + ".empty()";
        return null;
    }
    
    /**
     * Returns the lens type of this type.
     *
     * @param  packageName        the package name.
     * @param  encloseName        the name of the type that enclose this type.
     * @param  localTypeWithLens  the list of local types that has lens.
     * @return  the lens type.
     */
    public Type lensType(String packageName, String encloseName, List<String> localTypeWithLens) {
        Type lensType = knownLensType();
        if (lensType != null)
            return lensType;
        if (this.isOptional())
            return Core.OptionalLens.type().withGenerics(this.generics());
        if (this.isNullable())
            return Core.NullableLens.type().withGenerics(this.generics());
        if (this.isList())
            return Core.ListLens.type().withGenerics(this.generics());
        if (this.isFuncList())
            return Core.FuncListLens.type().withGenerics(this.generics());
        if (this.isMap())
            return Core.MapLens.type().withGenerics(this.generics());
        if (this.isFuncMap())
            return Core.FuncMapLens.type().withGenerics(this.generics());
        if ((localTypeWithLens != null) && !localTypeWithLens.contains(simpleName))
            return Core.ObjectLens.type();
        return new Type(null, simpleName() + "." + simpleName() + "Lens", packageName(), false, asList(new Generic("HOST")));
    }
    
    public Type knownLensType() {
        Type declaredType = this.declaredType();
        Type lensType = lensTypes.get(declaredType);
        if (lensType != null)
            return lensType;
        if (simpleName().endsWith("Lens"))
            return this;
        return null;
    }
    
    /**
     * Check if this lens is custom lens.
     *
     * @return {@code true} if this lens is a custom lens.
     */
    public boolean isCustomLens() {
        Type lensType = this.knownLensType();
        return !lensTypes.values().contains(lensType) && !lensTypes.values().stream().anyMatch(type -> type.simpleName().equals(lensType.simpleName()) && type.packageName().equals(lensType.packageName()));
    }
    
    /**
     * Check if this type is an ObjectLens type.
     *
     * @return {@code true} if this lens is an object lens.
     */
    public boolean isObjectLens() {
        return this.equals(Core.ObjectLens.type());
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
        return this.fullName("").equals("nullablej.nullable.Nullable");
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
     * Check if this type is a Optional type.
     *
     * @return {@code true} if this type is a Optional.
     */
    public boolean isObject() {
        return this.fullName("").equals("java.lang.Object");
    }
    
    // These are lens types that are in the main lens package.
    private static final Map<Type, Type> lensTypes = new HashMap<Type, Type>();
    
    static {
        lensTypes.put(OBJECT, Core.ObjectLens.type());
        lensTypes.put(INT, Core.IntegerLens.type());
        lensTypes.put(LNG, Core.LongLens.type());
        lensTypes.put(DBL, Core.DoubleLens.type());
        lensTypes.put(BOOL, Core.BooleanLens.type());
        lensTypes.put(STR, Core.StringLens.type());
        lensTypes.put(INTEGER, Core.IntegerLens.type());
        lensTypes.put(LONG, Core.LongLens.type());
        lensTypes.put(DOUBLE, Core.DoubleLens.type());
        lensTypes.put(BIGINTEGER, Core.BigIntegerLens.type());
        lensTypes.put(BIGDECIMAL, Core.BigDecimalLens.type());
        lensTypes.put(BOOLEAN, Core.BooleanLens.type());
        lensTypes.put(STRING, Core.StringLens.type());
        lensTypes.put(LIST, Core.ListLens.type());
        lensTypes.put(MAP, Core.MapLens.type());
        lensTypes.put(NULLABLE, Core.NullableLens.type());
        lensTypes.put(OPTIONAL, Core.OptionalLens.type());
        lensTypes.put(FUNC_LIST, Core.FuncListLens.type());
        lensTypes.put(FUNC_MAP, Core.FuncMapLens.type());
        lensTypes.put(Core.DayOfWeek.type(), Core.DayOfWeekLens.type());
        lensTypes.put(Core.Duration.type(), Core.DurationLens.type());
        lensTypes.put(Core.Instant.type(), Core.InstantLens.type());
        lensTypes.put(Core.LocalDate.type(), Core.LocalDateLens.type());
        lensTypes.put(Core.LocalDateTime.type(), Core.LocalDateTimeLens.type());
        lensTypes.put(Core.LocalTime.type(), Core.LocalTimeLens.type());
        lensTypes.put(Core.Month.type(), Core.MonthLens.type());
        lensTypes.put(Core.OffsetDateTime.type(), Core.OffsetDateTimeLens.type());
        lensTypes.put(Core.Period.type(), Core.PeriodLens.type());
        lensTypes.put(Core.ZonedDateTime.type(), Core.ZonedDateTimeLens.type());
        lensTypes.put(Core.ZoneId.type(), Core.ZonedIdLens.type());
        lensTypes.put(Core.ZoneOffset.type(), Core.ZonedOffsetLens.type());
        lensTypes.put(Core.ZoneOffsetTransition.type(), Core.ZonedOffsetTransitionLens.type());
        lensTypes.put(Core.ZoneOffsetTransitionRule.type(), Core.ZonedOffsetTransitionRuleLens.type());
    }
    
    public static final Map<String, Type> boxedType;
    
    static {
        Map<String, Type> map = new HashMap<String, Type>();
        map.put(Character.class.getCanonicalName(), CHARACTER);
        map.put(Byte.class.getCanonicalName(), BYTE);
        map.put(Short.class.getCanonicalName(), SHORT);
        map.put(Integer.class.getCanonicalName(), INTEGER);
        map.put(Long.class.getCanonicalName(), LONG);
        map.put(Float.class.getCanonicalName(), FLOAT);
        map.put(Double.class.getCanonicalName(), DOUBLE);
        map.put(Boolean.class.getCanonicalName(), BOOLEAN);
        map.put(Object.class.getCanonicalName(), OBJECT);
        boxedType = map;
    }
    
    public static final Map<Type, Type> declaredTypes;
    
    static {
        Map<Type, Type> map = new HashMap<Type, Type>();
        map.put(CHR, CHARACTER);
        map.put(BYT, BYTE);
        map.put(SHRT, SHORT);
        map.put(INT, INTEGER);
        map.put(LNG, LONG);
        map.put(FLT, FLOAT);
        map.put(DBL, DOUBLE);
        map.put(BOOL, BOOLEAN);
        declaredTypes = map;
    }
    
    public static final Map<String, Type> temporalTypes;
    
    static {
        Map<String, Type> map = new HashMap<String, Type>();
        map.put(DayOfWeek.class.getCanonicalName(), Core.DayOfWeek.type());
        map.put(Instant.class.getCanonicalName(), Core.Instant.type());
        map.put(LocalDate.class.getCanonicalName(), Core.LocalDate.type());
        map.put(LocalDateTime.class.getCanonicalName(), Core.LocalDateTime.type());
        map.put(LocalTime.class.getCanonicalName(), Core.LocalTime.type());
        map.put(Month.class.getCanonicalName(), Core.Month.type());
        map.put(OffsetDateTime.class.getCanonicalName(), Core.OffsetDateTime.type());
        map.put(ZonedDateTime.class.getCanonicalName(), Core.ZonedDateTime.type());
        temporalTypes = map;
    }
    
    public String genericParams() {
        return (generics.isEmpty() ? "" : (generics.stream().map(g -> g.name).collect(joining(","))));
    }
    
    public String genericsString() {
        return (generics.isEmpty() ? "" : ("<" + genericParams() + ">"));
    }
    
    public String typeWithGenerics() {
        return simpleName + genericsString();
    }
    
    public String genericDefParams() {
        return (generics.isEmpty() ? "" : (generics.stream().map(g -> g.withBound).collect(joining(","))));
    }
    
    public String genericDef() {
        return (generics.isEmpty() ? "" : ("<" + genericDefParams() + ">"));
    }
    
    public String typeWithGenericDef() {
        return simpleName + genericDef();
    }
    
    public Type getPredicateType() {
        String toString = this.toString();
        if ("int".equals(toString))
            return INTEGER;
        if ("long".equals(toString))
            return LONG;
        if ("boolean".equals(toString))
            return BOOLEAN;
        if ("double".equals(toString))
            return DOUBLE;
        if ("char".equals(toString))
            return CHR;
        if ("byte".equals(toString))
            return BYTE;
        if ("short".equals(toString))
            return SHORT;
        if ("float".equals(toString))
            return FLOAT;
        return this;
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
        Function<? super Generic, ? extends String> genericToString = generic -> {
            if (generic.withBound != null) {
                return generic.withBound;
            }
            return generic.toType().simpleNameWithGeneric();
        };
        return "<" + generics.stream().map(genericToString).collect(joining(", ")) + ">";
    }
    
    public Type withGenerics(Generic... generics) {
        return withGenerics(Arrays.asList(generics));
    }
    
    public Type withGenerics(List<Generic> generics) {
        return new Type(encloseName, simpleName, packageName, isVirtual, generics);
    }
}
