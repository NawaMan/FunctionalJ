package functionalj.annotations.uniontype.generator.model;

import java.util.List;

import lombok.Value;

@Value
public class Generic {
    public final String name;
    public final String withBound;
    public final List<Type> boundTypes;
    public Generic(String name) {
        this(name, name, null);
    }
    public Generic(String name, String withBound, List<Type> boundTypes) {
        this.name = name;
        this.withBound = (withBound == null) ? name : withBound;
        this.boundTypes = boundTypes;
    }
    
}
