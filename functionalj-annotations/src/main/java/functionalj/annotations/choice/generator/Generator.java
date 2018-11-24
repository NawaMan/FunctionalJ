package functionalj.annotations.choice.generator;

import java.util.ArrayList;
import java.util.List;

import functionalj.annotations.choice.generator.model.Case;
import functionalj.annotations.choice.generator.model.Generic;
import functionalj.annotations.choice.generator.model.Method;
import functionalj.annotations.choice.generator.model.SourceSpec;
import functionalj.annotations.choice.generator.model.Type;
import lombok.Value;


// TODO 
// - Proper import - when params requires extra type.

@Value
@SuppressWarnings("javadoc")
public class Generator implements Lines {
    
    public final SourceSpec  sourceSpec;
    public final TargetClass targetClass;
    
    public Generator(String targetName, Type sourceType, String specObjName, boolean publicFields, List<Generic> generics, List<Case> choices, List<Method> methods, List<String> localTypeWithNoLens) {
        this.sourceSpec  = new SourceSpec(targetName, sourceType, specObjName, publicFields, generics, choices, methods, localTypeWithNoLens);
        this.targetClass = new TargetClass(sourceSpec);
    }
    public Generator(String targetName, Type sourceType, List<Case> choices) {
        this(targetName, sourceType, null, true, new ArrayList<Generic>(), choices, new ArrayList<Method>(), new ArrayList<String>());
    }
    
    @Override
    public List<String> lines() {
        return targetClass.lines();
    }
    
}
