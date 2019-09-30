package example.functionalj.structtype;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;
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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

// example.functionalj.structtype.StructLensExample.null

public class Personel implements IStruct,Pipeable<Personel> {
    
    public static final Personel.PersonelLens<Personel> thePersonel = new Personel.PersonelLens<>(LensSpec.of(Personel.class));
    public static final Personel.PersonelLens<Personel> eachPersonel = thePersonel;
    public final int id;
    public final String firstName;
    public final String lastName;
    
    public Personel(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = $utils.notNull(firstName);
        this.lastName = $utils.notNull(lastName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Personel __data() throws Exception  {
        return this;
    }
    public int id() {
        return id;
    }
    public String firstName() {
        return firstName;
    }
    public String lastName() {
        return lastName;
    }
    public Personel withId(int id) {
        return new Personel(id, firstName, lastName);
    }
    public Personel withId(Supplier<Integer> id) {
        return new Personel(id.get(), firstName, lastName);
    }
    public Personel withId(Function<Integer, Integer> id) {
        return new Personel(id.apply(this.id), firstName, lastName);
    }
    public Personel withId(BiFunction<Personel, Integer, Integer> id) {
        return new Personel(id.apply(this, this.id), firstName, lastName);
    }
    public Personel withFirstName(String firstName) {
        return new Personel(id, firstName, lastName);
    }
    public Personel withFirstName(Supplier<String> firstName) {
        return new Personel(id, firstName.get(), lastName);
    }
    public Personel withFirstName(Function<String, String> firstName) {
        return new Personel(id, firstName.apply(this.firstName), lastName);
    }
    public Personel withFirstName(BiFunction<Personel, String, String> firstName) {
        return new Personel(id, firstName.apply(this, this.firstName), lastName);
    }
    public Personel withLastName(String lastName) {
        return new Personel(id, firstName, lastName);
    }
    public Personel withLastName(Supplier<String> lastName) {
        return new Personel(id, firstName, lastName.get());
    }
    public Personel withLastName(Function<String, String> lastName) {
        return new Personel(id, firstName, lastName.apply(this.lastName));
    }
    public Personel withLastName(BiFunction<Personel, String, String> lastName) {
        return new Personel(id, firstName, lastName.apply(this, this.lastName));
    }
    public static Personel fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Personel obj = new Personel(
                    (int)$utils.fromMapValue(map.get("id"), $schema.get("id")),
                    (String)$utils.fromMapValue(map.get("firstName"), $schema.get("firstName")),
                    (String)$utils.fromMapValue(map.get("lastName"), $schema.get("lastName"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", functionalj.types.IStruct.$utils.toMapValueObject(id));
        map.put("firstName", functionalj.types.IStruct.$utils.toMapValueObject(firstName));
        map.put("lastName", functionalj.types.IStruct.$utils.toMapValueObject(lastName));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("id", new functionalj.types.struct.generator.Getter("id", new functionalj.types.Type(null, null, "int", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("firstName", new functionalj.types.struct.generator.Getter("firstName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("lastName", new functionalj.types.struct.generator.Getter("lastName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Personel[" + "id: " + id() + ", " + "firstName: " + firstName() + ", " + "lastName: " + lastName() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class PersonelLens<HOST> extends ObjectLensImpl<HOST, Personel> {
        
        public final IntegerLens<HOST> id = createSubLensPrimitive(Personel::id, Personel::withId);
        public final StringLens<HOST> firstName = createSubLens(Personel::firstName, Personel::withFirstName, StringLens::of);
        public final StringLens<HOST> lastName = createSubLens(Personel::lastName, Personel::withLastName, StringLens::of);
        
        public PersonelLens(LensSpec<HOST, Personel> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final PersonelBuilder_withoutFirstName id(int id) {
            return (String firstName)->{
            return (String lastName)->{
            return ()->{
                return new Personel(
                    id,
                    firstName,
                    lastName
                );
            };
            };
            };
        }
        
        public static interface PersonelBuilder_withoutFirstName {
            
            public PersonelBuilder_withoutLastName firstName(String firstName);
            
        }
        public static interface PersonelBuilder_withoutLastName {
            
            public PersonelBuilder_ready lastName(String lastName);
            
        }
        public static interface PersonelBuilder_ready {
            
            public Personel build();
            
            
            
        }
        
        
    }
    
}