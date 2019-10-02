package example.functionalj.structtype;

import functionalj.lens.core.LensSpec;
import functionalj.lens.lenses.BooleanLens;
import functionalj.lens.lenses.DoubleLens;
import functionalj.lens.lenses.IntegerLens;
import functionalj.lens.lenses.ObjectLensImpl;
import functionalj.lens.lenses.StringLens;
import functionalj.pipeable.Pipeable;
import functionalj.types.IPostConstruct;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import java.lang.Double;
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
    public final double salary;
    public final boolean isOnSite;
    
    public Personel(int id, String firstName, String lastName, double salary, boolean isOnSite) {
        this.id = id;
        this.firstName = $utils.notNull(firstName);
        this.lastName = $utils.notNull(lastName);
        this.salary = salary;
        this.isOnSite = isOnSite;
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
    public double salary() {
        return salary;
    }
    public boolean isOnSite() {
        return isOnSite;
    }
    public Personel withId(int id) {
        return new Personel(id, firstName, lastName, salary, isOnSite);
    }
    public Personel withId(Supplier<Integer> id) {
        return new Personel(id.get(), firstName, lastName, salary, isOnSite);
    }
    public Personel withId(Function<Integer, Integer> id) {
        return new Personel(id.apply(this.id), firstName, lastName, salary, isOnSite);
    }
    public Personel withId(BiFunction<Personel, Integer, Integer> id) {
        return new Personel(id.apply(this, this.id), firstName, lastName, salary, isOnSite);
    }
    public Personel withFirstName(String firstName) {
        return new Personel(id, firstName, lastName, salary, isOnSite);
    }
    public Personel withFirstName(Supplier<String> firstName) {
        return new Personel(id, firstName.get(), lastName, salary, isOnSite);
    }
    public Personel withFirstName(Function<String, String> firstName) {
        return new Personel(id, firstName.apply(this.firstName), lastName, salary, isOnSite);
    }
    public Personel withFirstName(BiFunction<Personel, String, String> firstName) {
        return new Personel(id, firstName.apply(this, this.firstName), lastName, salary, isOnSite);
    }
    public Personel withLastName(String lastName) {
        return new Personel(id, firstName, lastName, salary, isOnSite);
    }
    public Personel withLastName(Supplier<String> lastName) {
        return new Personel(id, firstName, lastName.get(), salary, isOnSite);
    }
    public Personel withLastName(Function<String, String> lastName) {
        return new Personel(id, firstName, lastName.apply(this.lastName), salary, isOnSite);
    }
    public Personel withLastName(BiFunction<Personel, String, String> lastName) {
        return new Personel(id, firstName, lastName.apply(this, this.lastName), salary, isOnSite);
    }
    public Personel withSalary(double salary) {
        return new Personel(id, firstName, lastName, salary, isOnSite);
    }
    public Personel withSalary(Supplier<Double> salary) {
        return new Personel(id, firstName, lastName, salary.get(), isOnSite);
    }
    public Personel withSalary(Function<Double, Double> salary) {
        return new Personel(id, firstName, lastName, salary.apply(this.salary), isOnSite);
    }
    public Personel withSalary(BiFunction<Personel, Double, Double> salary) {
        return new Personel(id, firstName, lastName, salary.apply(this, this.salary), isOnSite);
    }
    public Personel withIsOnSite(boolean isOnSite) {
        return new Personel(id, firstName, lastName, salary, isOnSite);
    }
    public Personel withIsOnSite(Supplier<Boolean> isOnSite) {
        return new Personel(id, firstName, lastName, salary, isOnSite.get());
    }
    public Personel withIsOnSite(Function<Boolean, Boolean> isOnSite) {
        return new Personel(id, firstName, lastName, salary, isOnSite.apply(this.isOnSite));
    }
    public Personel withIsOnSite(BiFunction<Personel, Boolean, Boolean> isOnSite) {
        return new Personel(id, firstName, lastName, salary, isOnSite.apply(this, this.isOnSite));
    }
    public static Personel fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Personel obj = new Personel(
                    (int)$utils.fromMapValue(map.get("id"), $schema.get("id")),
                    (String)$utils.fromMapValue(map.get("firstName"), $schema.get("firstName")),
                    (String)$utils.fromMapValue(map.get("lastName"), $schema.get("lastName")),
                    (double)$utils.fromMapValue(map.get("salary"), $schema.get("salary")),
                    (boolean)$utils.fromMapValue(map.get("isOnSite"), $schema.get("isOnSite"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", functionalj.types.IStruct.$utils.toMapValueObject(id));
        map.put("firstName", functionalj.types.IStruct.$utils.toMapValueObject(firstName));
        map.put("lastName", functionalj.types.IStruct.$utils.toMapValueObject(lastName));
        map.put("salary", functionalj.types.IStruct.$utils.toMapValueObject(salary));
        map.put("isOnSite", functionalj.types.IStruct.$utils.toMapValueObject(isOnSite));
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
        map.put("salary", new functionalj.types.struct.generator.Getter("salary", new functionalj.types.Type(null, null, "double", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("isOnSite", new functionalj.types.struct.generator.Getter("isOnSite", new functionalj.types.Type(null, null, "boolean", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "Personel[" + "id: " + id() + ", " + "firstName: " + firstName() + ", " + "lastName: " + lastName() + ", " + "salary: " + salary() + ", " + "isOnSite: " + isOnSite() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class PersonelLens<HOST> extends ObjectLensImpl<HOST, Personel> {
        
        public final IntegerLens<HOST> id = createSubLensInt(Personel::id, Personel::withId);
        public final StringLens<HOST> firstName = createSubLens(Personel::firstName, Personel::withFirstName, StringLens::of);
        public final StringLens<HOST> lastName = createSubLens(Personel::lastName, Personel::withLastName, StringLens::of);
        public final DoubleLens<HOST> salary = createSubLensDouble(Personel::salary, Personel::withSalary);
        public final BooleanLens<HOST> isOnSite = createSubLensBoolean(Personel::isOnSite, Personel::withIsOnSite);
        
        public PersonelLens(LensSpec<HOST, Personel> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final PersonelBuilder_withoutFirstName id(int id) {
            return (String firstName)->{
            return (String lastName)->{
            return (double salary)->{
            return (boolean isOnSite)->{
            return ()->{
                return new Personel(
                    id,
                    firstName,
                    lastName,
                    salary,
                    isOnSite
                );
            };
            };
            };
            };
            };
        }
        
        public static interface PersonelBuilder_withoutFirstName {
            
            public PersonelBuilder_withoutLastName firstName(String firstName);
            
        }
        public static interface PersonelBuilder_withoutLastName {
            
            public PersonelBuilder_withoutSalary lastName(String lastName);
            
        }
        public static interface PersonelBuilder_withoutSalary {
            
            public PersonelBuilder_withoutIsOnSite salary(double salary);
            
        }
        public static interface PersonelBuilder_withoutIsOnSite {
            
            public PersonelBuilder_ready isOnSite(boolean isOnSite);
            
        }
        public static interface PersonelBuilder_ready {
            
            public Personel build();
            
            
            
        }
        
        
    }
    
}