package functionalj.types;

public interface Property {
    
    String       name();
    Type         type();
    boolean      isNullable();
    DefaultValue defValue();
    
    public default boolean isRequired() {
        return !isNullable() && (defValue() == DefaultValue.REQUIRED);
    }
    
    public default String getDefaultValueCode(String orElse) {
        if (isRequired())
            return "$utils.notNull(" + orElse + ")";
        return DefaultValue.defaultValueCode(type(), defValue());
    }
    
    public default String defaultValueCode() {
        return (defValue() == null) ? "null" : DefaultValue.defaultValueCode(type(), defValue());
    }
    
    public default Object defaultValue() {
        return (defValue() == null) ? "null" : DefaultValue.defaultValue(type(), defValue());
    }
    
}
