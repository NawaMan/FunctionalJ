package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.NullableLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.OptionalLens;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import java.lang.Exception;
import java.lang.Object;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import nullablej.nullable.Nullable;

// functionalj.types.struct.WithNullableOptionalTest.WithNullableOptionalTest.IParent3

public class ParentWithNullableOptional implements WithNullableOptionalTest.IParent3,IStruct,Pipeable<ParentWithNullableOptional> {
    
    public static final ParentWithNullableOptional.ParentWithNullableOptionalLens<ParentWithNullableOptional> theParentWithNullableOptional = new ParentWithNullableOptional.ParentWithNullableOptionalLens<>(LensSpec.of(ParentWithNullableOptional.class));
    public final Nullable<String> nullableName;
    public final Optional<String> optionalName;
    
    public ParentWithNullableOptional(Nullable<String> nullableName, Optional<String> optionalName) {
        this.nullableName = Nullable.of((nullableName == null) ? null : nullableName.get());
        this.optionalName = $utils.notNull(optionalName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public ParentWithNullableOptional __data() throws Exception  {
        return this;
    }
    public Nullable<String> nullableName() {
        return nullableName;
    }
    public Optional<String> optionalName() {
        return optionalName;
    }
    public ParentWithNullableOptional withNullableName(Nullable<String> nullableName) {
        return new ParentWithNullableOptional(nullableName, optionalName);
    }
    public ParentWithNullableOptional withNullableName(Supplier<Nullable<String>> nullableName) {
        return new ParentWithNullableOptional(nullableName.get(), optionalName);
    }
    public ParentWithNullableOptional withNullableName(Function<Nullable<String>, Nullable<String>> nullableName) {
        return new ParentWithNullableOptional(nullableName.apply(this.nullableName), optionalName);
    }
    public ParentWithNullableOptional withNullableName(BiFunction<ParentWithNullableOptional, Nullable<String>, Nullable<String>> nullableName) {
        return new ParentWithNullableOptional(nullableName.apply(this, this.nullableName), optionalName);
    }
    public ParentWithNullableOptional withOptionalName(Optional<String> optionalName) {
        return new ParentWithNullableOptional(nullableName, optionalName);
    }
    public ParentWithNullableOptional withOptionalName(Supplier<Optional<String>> optionalName) {
        return new ParentWithNullableOptional(nullableName, optionalName.get());
    }
    public ParentWithNullableOptional withOptionalName(Function<Optional<String>, Optional<String>> optionalName) {
        return new ParentWithNullableOptional(nullableName, optionalName.apply(this.optionalName));
    }
    public ParentWithNullableOptional withOptionalName(BiFunction<ParentWithNullableOptional, Optional<String>, Optional<String>> optionalName) {
        return new ParentWithNullableOptional(nullableName, optionalName.apply(this, this.optionalName));
    }
    public static ParentWithNullableOptional fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        @SuppressWarnings("unchecked")
        ParentWithNullableOptional obj = new ParentWithNullableOptional(
                    (Nullable<String>)$utils.fromMapValue(map.get("nullableName"), $schema.get("nullableName")),
                    (Optional<String>)$utils.fromMapValue(map.get("optionalName"), $schema.get("optionalName"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nullableName", functionalj.types.IStruct.$utils.toMapValueObject(nullableName));
        map.put("optionalName", functionalj.types.IStruct.$utils.toMapValueObject(optionalName));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("nullableName", new functionalj.types.struct.generator.Getter("nullableName", new functionalj.types.Type("nullablej.nullable", null, "Nullable", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", "java.lang.String", java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("optionalName", new functionalj.types.struct.generator.Getter("optionalName", new functionalj.types.Type("java.util", null, "Optional", java.util.Arrays.asList(new functionalj.types.Generic("java.lang.String", "java.lang.String", java.util.Arrays.asList(new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()))))), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "ParentWithNullableOptional[" + "nullableName: " + nullableName() + ", " + "optionalName: " + optionalName() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class ParentWithNullableOptionalLens<HOST> extends ObjectLensImpl<HOST, ParentWithNullableOptional> {
        
        public final NullableLens<HOST, String, StringLens<HOST>> nullableName = createSubNullableLens(ParentWithNullableOptional::nullableName, ParentWithNullableOptional::withNullableName, StringLens::of);
        public final OptionalLens<HOST, String, StringLens<HOST>> optionalName = createSubOptionalLens(ParentWithNullableOptional::optionalName, ParentWithNullableOptional::withOptionalName, StringLens::of);
        
        public ParentWithNullableOptionalLens(LensSpec<HOST, ParentWithNullableOptional> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final ParentWithNullableOptionalBuilder_withoutOptionalName nullableName(Nullable<String> nullableName) {
            return (Optional<String> optionalName)->{
            return ()->{
                return new ParentWithNullableOptional(
                    nullableName,
                    optionalName
                );
            };
            };
        }
        
        public static interface ParentWithNullableOptionalBuilder_withoutOptionalName {
            
            public ParentWithNullableOptionalBuilder_ready optionalName(Optional<String> optionalName);
            
        }
        public static interface ParentWithNullableOptionalBuilder_ready {
            
            public ParentWithNullableOptional build();
            
            
            
        }
        
        
    }
    
}