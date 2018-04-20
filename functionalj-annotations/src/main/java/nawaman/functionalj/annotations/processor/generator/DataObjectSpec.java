package nawaman.functionalj.annotations.processor.generator;

import java.util.List;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Builder
@Wither
public class DataObjectSpec {
    
    private String className;
    private String packageName;
    private String sourceClassName;
    private String sourcePackageName;
    private List<Type> extendeds;
    private List<Type> implementeds;
    private List<GenConstructor> constructors;
    private List<GenField>       fields;
    private List<GenMethod>      methods;
    private List<ILines>         mores;
    
    public String fullName() {
        return className + "." + packageName;
    }
}