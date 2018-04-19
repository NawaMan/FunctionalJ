package nawaman.functionalj.annotations.processor.generator;

import java.util.HashMap;
import java.util.Map;

import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;
import nawaman.functionalj.lens.BooleanLens;
import nawaman.functionalj.lens.IntegerLens;
import nawaman.functionalj.lens.StringLens;

@Value
@Wither
public class Type {
    
    public static final Type INT     = new Type("int",     "");
    public static final Type BOOL    = new Type("boolean", "");
    public static final Type STR     = new Type("String",  "");
    public static final Type INTEGER = new Type(Integer.class.getSimpleName(), Integer.class.getPackage().getName());
    public static final Type BOOLEAN = new Type(Boolean.class.getSimpleName(), Boolean.class.getPackage().getName());
    public static final Type STRING  = new Type(String .class.getSimpleName(), String .class.getPackage().getName());
    
    private static final Map<Type, Type> lensTypes = new HashMap<>();
    static {
        lensTypes.put(INT,     of(IntegerLens.class));
        lensTypes.put(BOOL,    of(BooleanLens.class));
        lensTypes.put(STR,     of(StringLens .class));
        lensTypes.put(INTEGER, of(IntegerLens.class));
        lensTypes.put(BOOLEAN, of(BooleanLens.class));
        lensTypes.put(STRING,  of(StringLens .class));
    }
    
    public static Type of(Class<?> clzz) {
        val pckg = clzz.getPackage().getName().toString();
        val name = clzz.getCanonicalName().toString().substring(pckg.length() + 1 );
        return new Type(name, pckg);
    }
    
    private String simpleName;
    private String packageName;
    
    public String fullName() {
        return packageName + "." + simpleName;
    }
    
    public Type withGeneric(String generic) {
        return new Type(simpleName + "<" + generic + ">", packageName);
    }
    
    public Type declaredType() {
        if (INT.equals(this))
            return INTEGER;
        if (BOOL.equals(this))
            return BOOLEAN;
        if (STR.equals(this))
            return STRING;
        return this;
    }
    public Object defaultValue() {
        if (INT.equals(this))
            return 0;
        if (BOOL.equals(this))
            return false;
        return null;
    }
    
    public Type getLensType(Type fallback) {
        val type = lensTypes.get(this);
        return (type != null) ? type : (fallback != null) ? fallback : new Type(this.declaredType().getSimpleName() + "Lens", this.getPackageName());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Type other = (Type) obj;
        if (packageName == null) {
            if (other.packageName != null)
                return false;
        } else if (!packageName.equals(other.packageName))
            return false;
        if (simpleName == null) {
            if (other.simpleName != null)
                return false;
        } else if (!simpleName.equals(other.simpleName))
            return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
        result = prime * result + ((simpleName == null) ? 0 : simpleName.hashCode());
        return result;
    }

    public Type(String simpleName, String packageName) {
        super();
        this.simpleName = simpleName;
        this.packageName = packageName;
    }
    
}