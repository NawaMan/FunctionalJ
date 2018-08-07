package functionalj.annotations.uniontype.generator;

import java.util.List;


// TODO 
// - "value", "supplier" and "function" in method -- as people might want to use the name
// - Generic support
// - First sub type with parameter have problem
// - Methods to check what sub type an instance is.

@SuppressWarnings("javadoc")
public class Generator implements Lines {
    
    public final SourceSpec  sourceSpec;
    public final TargetClass targetClass;
    
    public Generator(String targetName, Type sourceType, List<Choice> choices) {
        this.sourceSpec  =  new SourceSpec(targetName, sourceType, choices);
        this.targetClass = new TargetClass(sourceSpec);
    }
    
    @Override
    public List<String> lines() {
        return targetClass.lines();
    }
    
}
