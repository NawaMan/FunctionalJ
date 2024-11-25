package functionalj.types.struct;

public enum SourceKind {
    METHOD,
    INTERFACE,
    CLASS,
    RECORD;
    
    @Override
    public String toString() {
        return SourceKind.class.getCanonicalName() + "." + this.name();
    }
    
    public boolean isClass() {
        return this == CLASS;
    }
    
    public boolean isInterface() {
        return this == INTERFACE;
    }
    
    public boolean isRecord() {
        return this == RECORD;
    }
    
    public boolean isMethod() {
        return this == METHOD;
    }
    
}
