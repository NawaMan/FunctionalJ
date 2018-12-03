package functionalj.annotations.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.annotations.rule.RuleSpec.RuleType;
import lombok.val;

public class RuleSpecTest {

    @Test
    public void testBoolean() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "java.lang.String", "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.String> {\n" + 
                "    public static ThreeDigitString from(java.lang.String value) { \n" + 
                "       return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    private ThreeDigitString(java.lang.String value) {\n" + 
                "        super(functionalj.result.Validation.ToBoolean(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\"), value);\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testBoolean_int() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "int", "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "       return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    private ThreeDigitString(int value) {\n" + 
                "        super(functionalj.result.Validation.ToBoolean(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\"), value);\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testErrorMessage() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "int", null, RuleType.ErrMsg);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "       return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    private ThreeDigitString(int value) {\n" + 
                "        super(functionalj.result.Validation.ToMessage(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString), value);\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testException() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.annotations.rule", "int", null, RuleType.Func);
        assertEquals(
                "package functionalj.annotations.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "       return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    private ThreeDigitString(int value) {\n" + 
                "        super(functionalj.result.Validation.ToException(functionalj.annotations.rule.RuleSpecTest::ThreeDigitString), value);\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }

}
