package example.functionalj.accesslens;

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

// example.functionalj.accesslens.AccessLensExamples.null

public class User implements IStruct,Pipeable<User> {
    
    public static final User.UserLens<User> theUser = new User.UserLens<>(LensSpec.of(User.class));
    public final String name;
    
    public User(String name) {
        this.name = $utils.notNull(name);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public User __data() throws Exception  {
        return this;
    }
    public String name() {
        return name;
    }
    public User withName(String name) {
        return new User(name);
    }
    public User withName(Supplier<String> name) {
        return new User(name.get());
    }
    public User withName(Function<String, String> name) {
        return new User(name.apply(this.name));
    }
    public User withName(BiFunction<User, String, String> name) {
        return new User(name.apply(this, this.name));
    }
    public static User fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        User obj = new User(
                    (String)$utils.fromMapValue(map.get("name"), $schema.get("name"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", functionalj.types.IStruct.$utils.toMapValueObject(name));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("name", new functionalj.types.struct.generator.Getter("name", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "User[" + "name: " + name() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class UserLens<HOST> extends ObjectLensImpl<HOST, User> {
        
        public final StringLens<HOST> name = createSubLens(User::name, User::withName, StringLens::of);
        
        public UserLens(LensSpec<HOST, User> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final UserBuilder_ready name(String name) {
            return ()->{
                return new User(
                    name
                );
            };
        }
        
        public static interface UserBuilder_ready {
            
            public User build();
            
            
            
        }
        
        
    }
    
}