package functionalj.annotations.sealed.generator;

import java.util.ArrayList;
import java.util.List;

import functionalj.annotations.sealed.generator.model.Choice;
import functionalj.annotations.sealed.generator.model.Generic;
import functionalj.annotations.sealed.generator.model.Method;
import functionalj.annotations.sealed.generator.model.SourceSpec;
import functionalj.annotations.sealed.generator.model.Type;
import lombok.Value;


// TODO 
// - Proper import - when params requires extra type.

@Value
@SuppressWarnings("javadoc")
public class Generator implements Lines {
    
    public final SourceSpec  sourceSpec;
    public final TargetClass targetClass;
    
    public Generator(String targetName, Type sourceType, String specObjName, boolean publicFields, List<Generic> generics, List<Choice> choices, List<Method> methods) {
        this.sourceSpec  = new SourceSpec(targetName, sourceType, specObjName, publicFields, generics, choices, methods);
        this.targetClass = new TargetClass(sourceSpec);
    }
    public Generator(String targetName, Type sourceType, List<Choice> choices) {
        this(targetName, sourceType, null, true, new ArrayList<Generic>(), choices, new ArrayList<Method>());
    }
    
    @Override
    public List<String> lines() {
        return targetClass.lines();
    }
    
}
