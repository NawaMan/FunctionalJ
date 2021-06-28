package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.lens.lenses.java.time.LocalDateLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import java.lang.Exception;
import java.lang.Object;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Generated;

@Generated(value = "FunctionalJ",date = "2021-06-27T22:28:42.490205", comments = "functionalj.types.struct.FromMapTest")

public class Birthday implements IStruct,Pipeable<Birthday> {
    
    public static final Birthday.BirthdayLens<Birthday> theBirthday = new Birthday.BirthdayLens<>(LensSpec.of(Birthday.class));
    public static final Birthday.BirthdayLens<Birthday> eachBirthday = theBirthday;
    public final String name;
    public final LocalDate date;
    public static final SourceSpec spec = new functionalj.types.struct.generator.SourceSpec(null, "functionalj.types.struct", "FromMapTest", "Birthday", "functionalj.types.struct", null, "spec", null, new functionalj.types.struct.generator.SourceSpec.Configurations(true, false, true, true, true, true, true, true, ""), java.util.Arrays.asList(new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED), new functionalj.types.struct.generator.Getter("date", new functionalj.types.Type("java.time", null, "LocalDate", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED)), java.util.Arrays.asList("Birthday"));
    
    public Birthday(String name, LocalDate date) {
        this.name = $utils.notNull(name);
        this.date = $utils.notNull(date);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Birthday __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public LocalDate date() {
        return date;
    }
    public Birthday withName(String name) {
        return new Birthday(name, date);
    }
    public Birthday withName(Supplier<String> name) {
        return new Birthday(name.get(), date);
    }
    public Birthday withName(Function<String, String> name) {
        return new Birthday(name.apply(this.name), date);
    }
    public Birthday withName(BiFunction<Birthday, String, String> name) {
        return new Birthday(name.apply(this, this.name), date);
    }
    public Birthday withDate(LocalDate date) {
        return new Birthday(name, date);
    }
    public Birthday withDate(Supplier<LocalDate> date) {
        return new Birthday(name, date.get());
    }
    public Birthday withDate(Function<LocalDate, LocalDate> date) {
        return new Birthday(name, date.apply(this.date));
    }
    public Birthday withDate(BiFunction<Birthday, LocalDate, LocalDate> date) {
        return new Birthday(name, date.apply(this, this.date));
    }
    public static Birthday fromMap(Map<String, ? extends Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Birthday obj = new Birthday(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name")),
                    (LocalDate)$utils.fromMapValue(map.get("date"), $schema.get("date"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        map.put("date", functionalj.types.IStruct.$utils.toMapValueObject(date));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        java.util.Map<String, functionalj.types.struct.generator.Getter> map = new java.util.HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("date", new functionalj.types.struct.generator.Getter("date", new functionalj.types.Type("java.time", null, "LocalDate", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Birthday[" + "name: " + name() + ", " + "date: " + date() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class BirthdayLens<HOST> extends ObjectLensImpl<HOST, Birthday> {
        
        public final StringLens<HOST> name = createSubLens(Birthday::name, Birthday::withName, StringLens::of);
        public final LocalDateLens<HOST> date = createSubLens(Birthday::date, Birthday::withDate, LocalDateLens::of);
        
        public BirthdayLens(LensSpec<HOST, Birthday> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final BirthdayBuilder_withoutDate name(String name) {
            return (LocalDate date)->{
            return ()->{
                return new Birthday(
                    name,
                    date
                );
            };
            };
        }
        
        public static interface BirthdayBuilder_withoutDate {
            
            public BirthdayBuilder_ready date(LocalDate date);
            
        }
        public static interface BirthdayBuilder_ready {
            
            public Birthday build();
            
            
            
        }
        
        
    }
    
}