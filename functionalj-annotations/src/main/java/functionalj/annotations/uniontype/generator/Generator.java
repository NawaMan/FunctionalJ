package functionalj.annotations.uniontype.generator;

import java.util.List;


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
