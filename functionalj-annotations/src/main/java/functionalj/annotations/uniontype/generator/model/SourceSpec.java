package functionalj.annotations.uniontype.generator.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SourceSpec {
    public final String        targetName;
    public final Type          sourceType;
    public final List<Generic> generics;
    public final List<Choice>  choices;
    public final List<Method>  methods;
    public SourceSpec(String targetName, Type sourceType, List<Choice> choices) {
        this(targetName, sourceType, new ArrayList<Generic>(), choices, new ArrayList<>());
    }
    
}
