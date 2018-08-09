package functionalj.annotations.uniontype.generator;

import lombok.Value;

@Value
public class Generic {
    public final String name;
    public final String withBound;
    public final Type boundType;
    public Generic(String name) {
        this(name, name, null);
    }
    public Generic(String name, String withBound, Type boundType) {
        this.name = name;
        this.withBound = (withBound == null) ? name : withBound;
        this.boundType = boundType;
    }
    
}
