package functionalj.types.struct;

import functionalj.lens.core.LensSpec;
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

// functionalj.types.struct.DefaultFieldValueTest.null

public class PersonWithDefault implements IStruct,Pipeable<PersonWithDefault> {
    
    public static final PersonWithDefault.PersonWithDefaultLens<PersonWithDefault> thePersonWithDefault = new PersonWithDefault.PersonWithDefaultLens<>(LensSpec.of(PersonWithDefault.class));
    public final String firstName;
    public final String middleName;
    public final String lastName;
    
    public PersonWithDefault(String firstName, String lastName) {
        this.firstName = $utils.notNull(firstName);
        this.middleName = "";
        this.lastName = $utils.notNull(lastName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    public PersonWithDefault(String firstName, String middleName, String lastName) {
        this.firstName = $utils.notNull(firstName);
        this.middleName = java.util.Optional.ofNullable(middleName).orElseGet(()->"");
        this.lastName = $utils.notNull(lastName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public PersonWithDefault __data() throws Exception  {
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
    public PersonWithDefault withFirstName(String firstName) {
        return new PersonWithDefault(firstName, middleName, lastName);
    }
    public PersonWithDefault withFirstName(Supplier<String> firstName) {
        return new PersonWithDefault(firstName.get(), middleName, lastName);
    }
    public PersonWithDefault withFirstName(Function<String, String> firstName) {
        return new PersonWithDefault(firstName.apply(this.firstName), middleName, lastName);
    }
    public PersonWithDefault withFirstName(BiFunction<PersonWithDefault, String, String> firstName) {
        return new PersonWithDefault(firstName.apply(this, this.firstName), middleName, lastName);
    }
    public PersonWithDefault withMiddleName(String middleName) {
        return new PersonWithDefault(firstName, middleName, lastName);
    }
    public PersonWithDefault withMiddleName(Supplier<String> middleName) {
        return new PersonWithDefault(firstName, middleName.get(), lastName);
    }
    public PersonWithDefault withMiddleName(Function<String, String> middleName) {
        return new PersonWithDefault(firstName, middleName.apply(this.middleName), lastName);
    }
    public PersonWithDefault withMiddleName(BiFunction<PersonWithDefault, String, String> middleName) {
        return new PersonWithDefault(firstName, middleName.apply(this, this.middleName), lastName);
    }
    public PersonWithDefault withLastName(String lastName) {
        return new PersonWithDefault(firstName, middleName, lastName);
    }
    public PersonWithDefault withLastName(Supplier<String> lastName) {
        return new PersonWithDefault(firstName, middleName, lastName.get());
    }
    public PersonWithDefault withLastName(Function<String, String> lastName) {
        return new PersonWithDefault(firstName, middleName, lastName.apply(this.lastName));
    }
    public PersonWithDefault withLastName(BiFunction<PersonWithDefault, String, String> lastName) {
        return new PersonWithDefault(firstName, middleName, lastName.apply(this, this.lastName));
    }
    public static PersonWithDefault fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        PersonWithDefault obj = new PersonWithDefault(
                    (String)$utils.fromMapValue(map.get("firstName"), $schema.get("firstName")),
                    (String)$utils.fromMapValue(map.get("middleName"), $schema.get("middleName")),
                    (String)$utils.fromMapValue(map.get("lastName"), $schema.get("lastName"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", functionalj.types.IStruct.$utils.toMapValueObject(firstName));
        map.put("middleName", functionalj.types.IStruct.$utils.toMapValueObject(middleName));
        map.put("lastName", functionalj.types.IStruct.$utils.toMapValueObject(lastName));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("firstName", new functionalj.types.struct.generator.Getter("firstName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("middleName", new functionalj.types.struct.generator.Getter("middleName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), true, functionalj.types.DefaultValue.EMPTY));
        map.put("lastName", new functionalj.types.struct.generator.Getter("lastName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "PersonWithDefault[" + "firstName: " + firstName() + ", " + "middleName: " + middleName() + ", " + "lastName: " + lastName() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class PersonWithDefaultLens<HOST> extends ObjectLensImpl<HOST, PersonWithDefault> {
        
        public final StringLens<HOST> firstName = createSubLens(PersonWithDefault::firstName, PersonWithDefault::withFirstName, StringLens::of);
        public final StringLens<HOST> middleName = createSubLens(PersonWithDefault::middleName, PersonWithDefault::withMiddleName, StringLens::of);
        public final StringLens<HOST> lastName = createSubLens(PersonWithDefault::lastName, PersonWithDefault::withLastName, StringLens::of);
        
        public PersonWithDefaultLens(LensSpec<HOST, PersonWithDefault> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final PersonWithDefaultBuilder_withoutMiddleName firstName(String firstName) {
            return (String middleName)->{
            return (String lastName)->{
            return ()->{
                return new PersonWithDefault(
                    firstName,
                    middleName,
                    lastName
                );
            };
            };
            };
        }
        
        public static interface PersonWithDefaultBuilder_withoutMiddleName {
            
            public PersonWithDefaultBuilder_withoutLastName middleName(String middleName);
            
            public default PersonWithDefaultBuilder_ready lastName(String lastName){
                return middleName("").lastName(lastName);
            }
            
        }
        public static interface PersonWithDefaultBuilder_withoutLastName {
            
            public PersonWithDefaultBuilder_ready lastName(String lastName);
            
        }
        public static interface PersonWithDefaultBuilder_ready {
            
            public PersonWithDefault build();
            
            
            
        }
        
        
    }
    
}