package functionalj.annotations.uniontype.generator;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;


// TODO 
// - Allow methods to be declared in the spec class.
// - Automatically remove "Spec" or whatever the suffix. - Also allows the override of this behavior.
// - Proper import - when params requires extra type.

@Value
@SuppressWarnings("javadoc")
public class Generator implements Lines {
    
    public final SourceSpec  sourceSpec;
    public final TargetClass targetClass;
    
    public Generator(String targetName, Type sourceType, List<Choice> choices, List<Generic> generics) {
        this.sourceSpec  = new SourceSpec(targetName, sourceType, choices, generics);
        this.targetClass = new TargetClass(sourceSpec);
    }
    public Generator(String targetName, Type sourceType, List<Choice> choices) {
        this(targetName, sourceType, choices, new ArrayList<Generic>());
    }
    
    @Override
    public List<String> lines() {
        return targetClass.lines();
    }
    
}
