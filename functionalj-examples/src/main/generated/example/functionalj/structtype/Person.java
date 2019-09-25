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

// example.functionalj.structtype.StructTypeExamples.null

public class Person implements IStruct,Pipeable<Person> {
    
    public static final Person.PersonLens<Person> thePerson = new Person.PersonLens<>(LensSpec.of(Person.class));
    public static final Person.PersonLens<Person> eachPerson = thePerson;
    public final String firstName;
    public final String middleName;
    public final String lastName;
    public final Integer age;
    
    public Person(String firstName, String lastName) {
        this.firstName = $utils.notNull(firstName);
        this.middleName = null;
        this.lastName = $utils.notNull(lastName);
        this.age = -1;
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    public Person(String firstName, String middleName, String lastName, Integer age) {
        this.firstName = $utils.notNull(firstName);
        this.middleName = java.util.Optional.ofNullable(middleName).orElseGet(()->null);
        this.lastName = $utils.notNull(lastName);
        this.age = java.util.Optional.ofNullable(age).orElseGet(()->-1);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public Person __data() throws Exception  {
        return this;
    }
    public String firstName() {
        return firstName;
    }
    public String middleName() {
        return middleName;
    }
    public String lastName() {
        return lastName;
    }
    public Integer age() {
        return age;
    }
    public Person withFirstName(String firstName) {
        return new Person(firstName, middleName, lastName, age);
    }
    public Person withFirstName(Supplier<String> firstName) {
        return new Person(firstName.get(), middleName, lastName, age);
    }
    public Person withFirstName(Function<String, String> firstName) {
        return new Person(firstName.apply(this.firstName), middleName, lastName, age);
    }
    public Person withFirstName(BiFunction<Person, String, String> firstName) {
        return new Person(firstName.apply(this, this.firstName), middleName, lastName, age);
    }
    public Person withMiddleName(String middleName) {
        return new Person(firstName, middleName, lastName, age);
    }
    public Person withMiddleName(Supplier<String> middleName) {
        return new Person(firstName, middleName.get(), lastName, age);
    }
    public Person withMiddleName(Function<String, String> middleName) {
        return new Person(firstName, middleName.apply(this.middleName), lastName, age);
    }
    public Person withMiddleName(BiFunction<Person, String, String> middleName) {
        return new Person(firstName, middleName.apply(this, this.middleName), lastName, age);
    }
    public Person withLastName(String lastName) {
        return new Person(firstName, middleName, lastName, age);
    }
    public Person withLastName(Supplier<String> lastName) {
        return new Person(firstName, middleName, lastName.get(), age);
    }
    public Person withLastName(Function<String, String> lastName) {
        return new Person(firstName, middleName, lastName.apply(this.lastName), age);
    }
    public Person withLastName(BiFunction<Person, String, String> lastName) {
        return new Person(firstName, middleName, lastName.apply(this, this.lastName), age);
    }
    public Person withAge(Integer age) {
        return new Person(firstName, middleName, lastName, age);
    }
    public Person withAge(Supplier<Integer> age) {
        return new Person(firstName, middleName, lastName, age.get());
    }
    public Person withAge(Function<Integer, Integer> age) {
        return new Person(firstName, middleName, lastName, age.apply(this.age));
    }
    public Person withAge(BiFunction<Person, Integer, Integer> age) {
        return new Person(firstName, middleName, lastName, age.apply(this, this.age));
    }
    public static Person fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        Person obj = new Person(
                    (String)$utils.fromMapValue(map.get("firstName"), $schema.get("firstName")),
                    (String)$utils.fromMapValue(map.get("middleName"), $schema.get("middleName")),
                    (String)$utils.fromMapValue(map.get("lastName"), $schema.get("lastName")),
                    (Integer)$utils.fromMapValue(map.get("age"), $schema.get("age"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", functionalj.types.IStruct.$utils.toMapValueObject(firstName));
        map.put("middleName", functionalj.types.IStruct.$utils.toMapValueObject(middleName));
        map.put("lastName", functionalj.types.IStruct.$utils.toMapValueObject(lastName));
        map.put("age", functionalj.types.IStruct.$utils.toMapValueObject(age));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("firstName", new functionalj.types.struct.generator.Getter("firstName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("middleName", new functionalj.types.struct.generator.Getter("middleName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.NULL));
        map.put("lastName", new functionalj.types.struct.generator.Getter("lastName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("age", new functionalj.types.struct.generator.Getter("age", new functionalj.types.Type("java.lang", null, "Integer", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.MINUS_ONE));
        return map;
    }
    public String toString() {
        return "Person[" + "firstName: " + firstName() + ", " + "middleName: " + middleName() + ", " + "lastName: " + lastName() + ", " + "age: " + age() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class PersonLens<HOST> extends ObjectLensImpl<HOST, Person> {
        
        public final StringLens<HOST> firstName = createSubLens(Person::firstName, Person::withFirstName, StringLens::of);
        public final StringLens<HOST> middleName = createSubLens(Person::middleName, Person::withMiddleName, StringLens::of);
        public final StringLens<HOST> lastName = createSubLens(Person::lastName, Person::withLastName, StringLens::of);
        public final IntegerLens<HOST> age = createSubLens(Person::age, Person::withAge, IntegerLens::of);
        
        public PersonLens(LensSpec<HOST, Person> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final PersonBuilder_withoutMiddleName firstName(String firstName) {
            return (String middleName)->{
            return (String lastName)->{
            return (Integer age)->{
            return ()->{
                return new Person(
                    firstName,
                    middleName,
                    lastName,
                    age
                );
            };
            };
            };
            };
        }
        
        public static interface PersonBuilder_withoutMiddleName {
            
            public PersonBuilder_withoutLastName middleName(String middleName);
            
            public default PersonBuilder_withoutAge lastName(String lastName){
                return middleName(null).lastName(lastName);
            }
            
        }
        public static interface PersonBuilder_withoutLastName {
            
            public PersonBuilder_withoutAge lastName(String lastName);
            
        }
        public static interface PersonBuilder_withoutAge {
            
            public PersonBuilder_ready age(Integer age);
            
            public default Person build() {
                return age(-1).build();
            }
            
        }
        public static interface PersonBuilder_ready {
            
            public Person build();
            
            
            
        }
        
        
    }
    
}