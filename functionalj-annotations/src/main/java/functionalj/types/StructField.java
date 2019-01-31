package functionalj.types;

import functionalj.types.struct.generator.Type;

public class StructField {
    
    public final String       name;
    public final Type         type;
    public final DefaultValue defaultValue;
    
    public StructField(String name, Type type, DefaultValue defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    
    
    
}
