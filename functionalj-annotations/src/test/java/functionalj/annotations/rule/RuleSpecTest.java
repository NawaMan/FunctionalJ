package functionalj.annotations.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.rule.RuleSpec.RuleType;
import lombok.val;

public class RuleSpecTest {

    @Test
    public void testBoolean() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "java.lang.String", false, "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.String> implements functionalj.annotations.IRule {\n" + 
                "    public static ThreeDigitString from(java.lang.String value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(java.lang.String value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(java.lang.String value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.String>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).append(functionalj.result.Validation.ToBoolean(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\").toValidator()));\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testBoolean_int() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "int", false, "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> implements functionalj.annotations.IRule {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.Integer>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).append(functionalj.result.Validation.ToBoolean(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\").toValidator()));\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testErrorMessage() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "int", false, null, RuleType.ErrMsg);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> implements functionalj.annotations.IRule {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.Integer>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).append(functionalj.result.Validation.ToMessage(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString).toValidator()));\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testException() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "int", false, null, RuleType.Func);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> implements functionalj.annotations.IRule {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.Integer>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).append(functionalj.result.Validation.ToException(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString).toValidator()));\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }

}
