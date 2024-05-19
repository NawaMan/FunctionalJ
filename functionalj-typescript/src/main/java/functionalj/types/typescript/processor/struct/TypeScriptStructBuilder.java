package functionalj.types.typescript.processor.struct;

import java.util.List;

public class TypeScriptStructBuilder {
    
    private TypeScriptStructSpec structSpec;
//    private List<String>         structTypes;
//    private List<String>         choiceTypes;
    
    /**
     * Create a builder for the given struct.
     * 
     * @param structSpec the struct specification.
     */
    public TypeScriptStructBuilder(TypeScriptStructSpec structSpec) {
        this(structSpec, null, null);
    }
    
    /**
     * Create a builder for the given struct.
     * 
     * @param structSpec   the struct specification.
     * @param structTypes  the list of struct types.
     * @param choiceTypes  the list of choice types.
     */
    public TypeScriptStructBuilder(
            TypeScriptStructSpec structSpec,
            List<String>         structTypes,
            List<String>         choiceTypes) {
        this.structSpec  = structSpec;
//        this.structTypes = structTypes;
//        this.choiceTypes = choiceTypes;
    }
    
    /** @return Generate the TypeScript code for the choice. */
    public String toTypeScriptCode() {
        return "Script code: " + structSpec;
    }
    
}
