package nawaman.functionalj.annotations.processor.generator;

import java.util.List;

import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class SourceSpec {
    
    private String specClassName;
    private String packageName;
    private String targetClassName;
    private String targetPackageName;
    private boolean isClass;
    private Configurations configures;
    private List<Getter> getters;
    
    public static class Configurations {
        public boolean noArgConstructor  = false;
        public boolean generateLensClass = false;
    }
    
    public Type getTargetType() {
        return new Type(targetClassName, targetPackageName);
    }
    public Type toType() {
        return new Type(specClassName, packageName);
    }
}