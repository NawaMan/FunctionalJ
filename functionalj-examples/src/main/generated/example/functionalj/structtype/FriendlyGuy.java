package example.functionalj.structtype;

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

// example.functionalj.structtype.StructExtendFormTest.StructExtendFormTest.FriendlyGuySpec

public class FriendlyGuy extends StructExtendFormTest.FriendlyGuySpec implements IStruct,Pipeable<FriendlyGuy> {
    
    public static final FriendlyGuy.FriendlyGuyLens<FriendlyGuy> theFriendlyGuy = new FriendlyGuy.FriendlyGuyLens<>(LensSpec.of(FriendlyGuy.class));
    public final String greetWord;
    
    public FriendlyGuy(String greetWord) {
        this.greetWord = $utils.notNull(greetWord);
        if (this instanceof IPostConstruct) ((IPostConstruct)this).postConstruct();
    }
    
    public FriendlyGuy __data() throws Exception  {
        return this;
    }
    public String greetWord() {
        return greetWord;
    }
    public FriendlyGuy withGreetWord(String greetWord) {
        return new FriendlyGuy(greetWord);
    }
    public FriendlyGuy withGreetWord(Supplier<String> greetWord) {
        return new FriendlyGuy(greetWord.get());
    }
    public FriendlyGuy withGreetWord(Function<String, String> greetWord) {
        return new FriendlyGuy(greetWord.apply(this.greetWord));
    }
    public FriendlyGuy withGreetWord(BiFunction<FriendlyGuy, String, String> greetWord) {
        return new FriendlyGuy(greetWord.apply(this, this.greetWord));
    }
    public static FriendlyGuy fromMap(Map<String, Object> map) {
        Map<String, Getter> $schema = getStructSchema();
        
        FriendlyGuy obj = new FriendlyGuy(
                    (String)$utils.fromMapValue(map.get("greetWord"), $schema.get("greetWord"))
                );
        return obj;
    }
    public Map<String, Object> __toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("greetWord", functionalj.types.IStruct.$utils.toMapValueObject(greetWord));
        return map;
    }
    public Map<String, Getter> __getSchema() {
        return getStructSchema();
    }
    public static Map<String, Getter> getStructSchema() {
        Map<String, Getter> map = new HashMap<>();
        map.put("greetWord", new functionalj.types.struct.generator.Getter("greetWord", new functionalj.types.Type("java.lang", null, "String", java.util.Collections.emptyList()), false, functionalj.types.DefaultValue.REQUIRED));
        return map;
    }
    public String toString() {
        return "FriendlyGuy[" + "greetWord: " + greetWord() + "]";
    }
    public int hashCode() {
        return toString().hashCode();
    }
    public boolean equals(Object another) {
        return (another == this) || ((another != null) && (getClass().equals(another.getClass())) && java.util.Objects.equals(toString(), another.toString()));
    }
    
    public static class FriendlyGuyLens<HOST> extends ObjectLensImpl<HOST, FriendlyGuy> {
        
        public final StringLens<HOST> greetWord = createSubLens(FriendlyGuy::greetWord, FriendlyGuy::withGreetWord, StringLens::of);
        
        public FriendlyGuyLens(LensSpec<HOST, FriendlyGuy> spec) {
            super(spec);
        }
        
    }
    public static final class Builder {
        
        public final FriendlyGuyBuilder_ready greetWord(String greetWord) {
            return ()->{
                return new FriendlyGuy(
                    greetWord
                );
            };
        }
        
        public static interface FriendlyGuyBuilder_ready {
            
            public FriendlyGuy build();
            
            
            
        }
        
        
    }
    
}