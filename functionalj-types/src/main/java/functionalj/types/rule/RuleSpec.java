// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.rule;

import static functionalj.types.choice.generator.Utils.toStringLiteral;

import java.util.HashMap;
import java.util.Map;

import functionalj.types.IRule;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.val;


@Value
@AllArgsConstructor
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
    
    private final String superRule;
    private final String dataName;
    private final String dataType;
    
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
        val isSubRule       = (superRule != null) && !superRule.equals(IRule.class.getCanonicalName());
        val superClass      = isSubRule ? superRule : "functionalj.result.Acceptable<" + dataTypeGeneric + "> implements functionalj.types.IRule<" + dataTypeGeneric + ">";
        val strTemplate =
                "package " + packageName + ";\n" +
                "public class " + targetName + " extends " + superClass + " {\n" +
                "    public static " + targetName + " from(" + dataType + " " + dataName + ") { \n" +
                "        return new " + targetName + "(" + dataName + ");\n" +
                "    }\n" +
                "    protected " + targetName + "(" + dataType + " " + dataName + ") {\n" +
                "        this(" + dataName + ", null);\n" +
                "    }\n" +
                "    protected " + targetName + "(" + dataType + " " + dataName + ", functionalj.list.FuncList<functionalj.validator.Validator<? super " + dataTypeGeneric + ">> validators) {\n" +
                "        super(" + dataName + ", functionalj.list.FuncList.from(validators).prepend(" + validationCall + ".toValidator()));\n" +
                "    }\n" +
                "    \n" +
                "    public " + dataTypeGeneric + " " + dataName + "() { return value(); }\n" +
                "    public String __dataName()  { return " + toStringLiteral(dataName) + "; }\n" +
                "    public " + dataTypeGeneric + " __dataValue() { return value(); }\n" +
                "    public Class<" + dataTypeGeneric + "> __dataType() { return " + dataType + ".class; }\n" +
                "    public static Class<" + dataTypeGeneric + "> ___dataType() { return " + dataType + ".class; }\n" +
                "    @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n" +
                "    public <R extends functionalj.types.IRule<" + dataTypeGeneric + ">> Class<R> __superRule() { \n" +
                "        return (Class)" + ((superRule == null) ? null : superClass + ".class") + ";\n" +
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
