package functionalj.types;

public enum OptionalBoolean {
    
    TRUE,
    FALSE,
    WHATEVER;
    
    @Override
    public String toString() {
        return this.getClass().getCanonicalName() + "." + this.name();
    }
    
    public Boolean toBoolean() {
        return (this == WHATEVER) ? null : (this == TRUE);
    }
    
    public static Boolean toBoolean(OptionalBoolean optBoolean) {
        return (optBoolean == null) ? null : optBoolean.toBoolean();
    }
    
}
