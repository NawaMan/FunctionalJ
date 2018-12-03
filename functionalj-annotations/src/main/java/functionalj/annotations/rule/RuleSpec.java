package functionalj.annotations.rule;

import static functionalj.annotations.choice.generator.Utils.toStringLiteral;

import java.util.HashMap;
import java.util.Map;

import lombok.Value;
import lombok.val;

@Value
public class RuleSpec {
    
    enum RuleType {
        Bool("ToBoolean"), ErrMsg("ToMessage"), Func("ToException");
        
        private final String method;
        private RuleType(String method) {
            this.method = method;
        }
        public String getMethod() {
            return method;
        }
    }
    
    private final String targetName;
    private final String enclosingClass;
    private final String packageName;
    
    private final String dataType;
    private final boolean isSubRule;
    
    private final String   errorMsg;
    private final RuleType ruleType;
    
    private static final Map<String, String> genericTypes;
    static {
        val map = new HashMap<String, String>();
        map.put("byte",    Byte     .class.getCanonicalName());
        map.put("short",   Short    .class.getCanonicalName());
        map.put("int",     Integer  .class.getCanonicalName());
        map.put("long",    Long     .class.getCanonicalName());
        map.put("float",   Float    .class.getCanonicalName());
        map.put("double",  Double   .class.getCanonicalName());
        map.put("char",    Character.class.getCanonicalName());
        map.put("boolean", Boolean  .class.getCanonicalName());
        genericTypes = map;
    }
    
    public String toCode() {
        val dataTypeGeneric = getDataTypeGeneric();
        val validationCall  = validationCall();
        val superClass      = isSubRule ? dataType : "functionalj.result.Acceptable<" + dataTypeGeneric + "> implements functionalj.annotations.IRule";
        val strTemplate =
                "package " + packageName + ";\n" +
                "public class " + targetName + " extends " + superClass + " {\n" + 
                "    public static " + targetName + " from(" + dataType + " value) { \n" +
                "        return new " + targetName + "(value);\n" + 
                "    }\n" + 
                "    protected " + targetName + "(" + dataType + " value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected " + targetName + "(" + dataType + " value, functionalj.list.FuncList<functionalj.validator.Validator<? super " + dataTypeGeneric + ">> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).append(" + validationCall + ".toValidator()));\n" + 
                "    }\n" + 
                "}";
        return strTemplate;
    }
    
    private String validationCall() {
        val validationType = ruleType.getMethod();
        val msgParam       = (ruleType == RuleType.Bool) ? ", " + toStringLiteral(((errorMsg != null) && !errorMsg.isEmpty()) ? errorMsg : targetName) : "";
        return "functionalj.result.Validation." + validationType
                + "(" + packageName + "." + enclosingClass + "::" + targetName + msgParam + ")";
    }
    
    private String getDataTypeGeneric() {
        return genericTypes.getOrDefault(dataType, dataType);
    }
    
}
