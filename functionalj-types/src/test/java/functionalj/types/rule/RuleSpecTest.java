package functionalj.types.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.rule.RuleSpec;
import functionalj.types.rule.RuleSpec.RuleType;
import lombok.val;

public class RuleSpecTest {

    @Test
    public void testBoolean() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.types.rule", null, "value", "java.lang.String", "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.types.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.String> implements functionalj.types.IRule<java.lang.String> {\n" + 
                "    public static ThreeDigitString from(java.lang.String value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(java.lang.String value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(java.lang.String value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.String>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).prepend(functionalj.result.Validation.ToBoolean(functionalj.types.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\").toValidator()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.lang.String value() { return value(); }\n" + 
                "    public String __dataName()  { return \"value\"; }\n" + 
                "    public java.lang.String __dataValue() { return value(); }\n" + 
                "    public Class<java.lang.String> __dataType() { return java.lang.String.class; }\n" + 
                "    @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n" + 
                "    public <R extends functionalj.types.IRule<java.lang.String>> Class<R> __superRule() { \n" + 
                "        return (Class)null;\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testBoolean_int() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.types.rule", null, "value", "int", "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.types.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> implements functionalj.types.IRule<java.lang.Integer> {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.Integer>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).prepend(functionalj.result.Validation.ToBoolean(functionalj.types.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\").toValidator()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.lang.Integer value() { return value(); }\n" + 
                "    public String __dataName()  { return \"value\"; }\n" + 
                "    public java.lang.Integer __dataValue() { return value(); }\n" + 
                "    public Class<java.lang.Integer> __dataType() { return int.class; }\n" + 
                "    @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n" + 
                "    public <R extends functionalj.types.IRule<java.lang.Integer>> Class<R> __superRule() { \n" + 
                "        return (Class)null;\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testErrorMessage() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.types.rule", null, "value", "int", null, RuleType.ErrMsg);
        assertEquals(
                "package functionalj.types.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> implements functionalj.types.IRule<java.lang.Integer> {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.Integer>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).prepend(functionalj.result.Validation.ToMessage(functionalj.types.rule.RuleSpecTest::ThreeDigitString).toValidator()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.lang.Integer value() { return value(); }\n" + 
                "    public String __dataName()  { return \"value\"; }\n" + 
                "    public java.lang.Integer __dataValue() { return value(); }\n" + 
                "    public Class<java.lang.Integer> __dataType() { return int.class; }\n" + 
                "    @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n" + 
                "    public <R extends functionalj.types.IRule<java.lang.Integer>> Class<R> __superRule() { \n" + 
                "        return (Class)null;\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testException() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.types.rule", null, "value", "int", null, RuleType.Func);
        assertEquals(
                "package functionalj.types.rule;\n" + 
                "public class ThreeDigitString extends functionalj.result.Acceptable<java.lang.Integer> implements functionalj.types.IRule<java.lang.Integer> {\n" + 
                "    public static ThreeDigitString from(int value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(int value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.Integer>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).prepend(functionalj.result.Validation.ToException(functionalj.types.rule.RuleSpecTest::ThreeDigitString).toValidator()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.lang.Integer value() { return value(); }\n" + 
                "    public String __dataName()  { return \"value\"; }\n" + 
                "    public java.lang.Integer __dataValue() { return value(); }\n" + 
                "    public Class<java.lang.Integer> __dataType() { return int.class; }\n" + 
                "    @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n" + 
                "    public <R extends functionalj.types.IRule<java.lang.Integer>> Class<R> __superRule() { \n" + 
                "        return (Class)null;\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }
    @Test
    public void testSubRule() {
        val ruleSpec = new RuleSpec("ThreeDigitString", "RuleSpecTest", "functionalj.types.rule", "NonNullString", "value", "java.lang.String", "Not valid.", RuleType.Bool);
        assertEquals(
                "package functionalj.types.rule;\n" + 
                "public class ThreeDigitString extends NonNullString {\n" + 
                "    public static ThreeDigitString from(java.lang.String value) { \n" + 
                "        return new ThreeDigitString(value);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(java.lang.String value) {\n" + 
                "        this(value, null);\n" + 
                "    }\n" + 
                "    protected ThreeDigitString(java.lang.String value, functionalj.list.FuncList<functionalj.validator.Validator<? super java.lang.String>> validators) {\n" + 
                "        super(value, functionalj.list.FuncList.from(validators).prepend(functionalj.result.Validation.ToBoolean(functionalj.types.rule.RuleSpecTest::ThreeDigitString, \"Not valid.\").toValidator()));\n" + 
                "    }\n" + 
                "    \n" + 
                "    public java.lang.String value() { return value(); }\n" + 
                "    public String __dataName()  { return \"value\"; }\n" + 
                "    public java.lang.String __dataValue() { return value(); }\n" + 
                "    public Class<java.lang.String> __dataType() { return java.lang.String.class; }\n" + 
                "    @SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n" + 
                "    public <R extends functionalj.types.IRule<java.lang.String>> Class<R> __superRule() { \n" + 
                "        return (Class)NonNullString.class;\n" + 
                "    }\n" + 
                "}",
                ruleSpec.toCode());
    }

}
