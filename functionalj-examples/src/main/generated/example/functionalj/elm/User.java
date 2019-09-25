package example.functionalj.elm;

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

// example.functionalj.elm.ElmExamples.null

public class User implements IStruct,Pipeable<User> {
    
    public static final User.UserLens<User> theUser = new User.UserLens<>(LensSpec.of(User.class));
    public static final User.UserLens<User> eachUser = theUser;
    public final String firstName;
    public final String lastName;
    
    public User(String firstName, String lastName) {
        this.firstName = $utils.notNull(firstName);
        this.lastName = $utils.notNull(lastName);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public User __data() throws Exception  {
        return this;
    }
    public String firstName() {
        return firstName;
    }
    public String lastName() {
        return lastName;
    }
    public User withFirstName(String firstName) {
        return new User(firstName, lastName);
    }
    public User withFirstName(Supplier<String> firstName) {
        return new User(firstName.get(), lastName);
    }
    public User withFirstName(Function<String, String> firstName) {
        return new User(firstName.apply(this.firstName), lastName);
    }
    public User withFirstName(BiFunction<User, String, String> firstName) {
        return new User(firstName.apply(this, this.firstName), lastName);
    }
    public User withLastName(String lastName) {
        return new User(firstName, lastName);
    }
    public User withLastName(Supplier<String> lastName) {
        return new User(firstName, lastName.get());
    }
    public User withLastName(Function<String, String> lastName) {
        return new User(firstName, lastName.apply(this.lastName));
    }
    public User withLastName(BiFunction<User, String, String> lastName) {
        return new User(firstName, lastName.apply(this, this.lastName));
    }
    public static User fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        User obj = new User(
                    (String)$utils.fromMapValue(map.get("firstName"), $schema.get("firstName")),
                    (String)$utils.fromMapValue(map.get("lastName"), $schema.get("lastName"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", functionalj.types.IStruct.$utils.toMapValueObject(firstName));
        map.put("lastName", functionalj.types.IStruct.$utils.toMapValueObject(lastName));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("firstName", new functionalj.types.struct.generator.Getter("firstName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        map.put("lastName", new functionalj.types.struct.generator.Getter("lastName", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "User[" + "firstName: " + firstName() + ", " + "lastName: " + lastName() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class UserLens<HOST> extends ObjectLensImpl<HOST, User> {
        
        public final StringLens<HOST> firstName = createSubLens(User::firstName, User::withFirstName, StringLens::of);
        public final StringLens<HOST> lastName = createSubLens(User::lastName, User::withLastName, StringLens::of);
        
        public UserLens(LensSpec<HOST, User> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final UserBuilder_withoutLastName firstName(String firstName) {
            return (String lastName)->{
            return ()->{
                return new User(
                    firstName,
                    lastName
                );
            };
            };
        }
        
        public static interface UserBuilder_withoutLastName {
            
            public UserBuilder_ready lastName(String lastName);
            
        }
        public static interface UserBuilder_ready {
            
            public User build();
            
            
            
        }
        
        
    }
    
}