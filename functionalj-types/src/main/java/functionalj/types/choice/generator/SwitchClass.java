package functionalj.types.choice.generator;

import static functionalj.types.choice.generator.Utils.switchClassName;
import static functionalj.types.choice.generator.Utils.toCamelCase;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import functionalj.types.choice.ChoiceTypeSwitch;
import functionalj.types.choice.generator.model.Case;
import lombok.val;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Utils.class)
public class SwitchClass implements Lines {
	
    public final TargetClass  targetClass;
    public final boolean      expandPartial;
    public final boolean      isFirst;
    public final List<Case> choices;
    
    public SwitchClass(TargetClass targetClass, boolean isFirst, List<Case> choices) {
        this(targetClass, true, isFirst, choices);
    }
    
    public SwitchClass(TargetClass targetClass, boolean expandPartial, boolean isFirst, List<Case> choices) {
        this.targetClass = targetClass;
        this.expandPartial = expandPartial;
        this.isFirst = isFirst;
        this.choices = choices;
    }
    
    @Override
    public List<String> lines() {
        val targetName      = targetClass.type.name;
        val switchClassName = switchClassName(targetName, choices);
        val mapTargetType   = "TARGET";
        
        val isLast    = choices.size() <= 1;
        val nextName  = switchClassName(targetName, choices, 1);
        val retType   = isLast? mapTargetType : nextName + "<" + mapTargetType + (targetClass.genericParams().isEmpty() ? "" : ", " + targetClass.genericParams()) + ">";
        val retStmt   = isLast? "return newAction.apply($value);" : "return new " + nextName + "<" + mapTargetType + (targetClass.genericParams().isEmpty() ? "" : ", " + targetClass.genericParams()) + ">($value, newAction);";
        val thisChoice = choices.get(0);
        val thisName  = thisChoice.name;
        val camelName = toCamelCase(thisChoice.name);
        
        val firstSwitchLines = !isFirst ? new ArrayList<String>() : asList(
            asList(format("public static class %1$sFirstSwitch%2$s {",                                targetName, targetClass.genericDef())),
            asList(format("    private %s $value;",                                                               targetClass.typeWithGenerics())),
            asList(format("    private %sFirstSwitch(%s theValue) { this.$value = theValue; }",       targetName, targetClass.typeWithGenerics())),
            asList(format("    public <%2$s> %1$sFirstSwitchTyped<%2$s%3$s> toA(Class<%2$s> clzz) {", targetName, mapTargetType, (targetClass.genericParams().isEmpty() ? "" : ", " + targetClass.genericParams()))),
            asList(format("        return new %1$sFirstSwitchTyped<%2$s%3$s>($value);",               targetName, mapTargetType, (targetClass.genericParams().isEmpty() ? "" : ", " + targetClass.genericParams()))),
            asList(format("    }")),
            asList(format("    ")),
            createCasesComplete(true, false,             thisName, camelName,                  targetName, retType, retStmt, mapTargetType),
            createCasesPartial (true, false, thisChoice, thisName, camelName, switchClassName, targetName,                   mapTargetType),
            asList(format("}"))
        ).stream()
        .flatMap(List::stream)
        .collect(toList());
        
        val firstSwitchTypeLines = !isFirst ? new ArrayList<String>() : asList(
            asList(format("public static class %1$sFirstSwitchTyped<%2$s> {",                        targetName, mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericDefParams()))),
            asList(format("    private %s $value;",                                                  targetClass.typeWithGenerics())),
            asList(format("    private %sFirstSwitchTyped(%s theValue) { this.$value = theValue; }", targetName, targetClass.typeWithGenerics())),
            asList(format("    ")),
            createCasesComplete(true, true,             thisName, camelName,                  targetName, retType, retStmt, mapTargetType),
            createCasesPartial (true, true, thisChoice, thisName, camelName, switchClassName, targetName,                   mapTargetType),
            asList(format("}"))
        ).stream()
        .flatMap(List::stream)
        .collect(toList());
        
        val switchLines = asList(
            asList(format("public static class %1$s<%3$s> extends %5$s<%2$s, %4$s> {",                            switchClassName, targetName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericDefParams()), mapTargetType, ChoiceTypeSwitch.class.getSimpleName())),
            asList(format("    private %1$s(%2$s theValue, Function<%2$s, ? extends %3$s> theAction) { super(theValue, theAction); }", switchClassName, targetName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType)),
            asList(format("    ")),
            createCasesComplete(false, true,             thisName, camelName,                  targetName, retType, retStmt, mapTargetType),
            createCasesPartial (false, true, thisChoice, thisName, camelName, switchClassName, targetName,                   mapTargetType),
            asList(format("}"))
        ).stream()
        .flatMap(List::stream)
        .collect(toList());
        
        val lines = new ArrayList<String>();
        lines.addAll(firstSwitchLines);
        lines.addAll(firstSwitchTypeLines);
        lines.addAll(switchLines);
        return lines;
    }
    
    private List<String> createCasesComplete(boolean isFirst, boolean typed, String thisName, String camelName, String targetName, 
            String retType, String retStmt, String mapTargetType) {
        val methodGeneric = typed ? "" : (isFirst ? "<" + mapTargetType + "> " : "");
        val lineBF = isFirst ? "    Function<" + targetName + targetClass.generics() + ", " + mapTargetType + "> $action = null;" : null;
        return asList(
            format("public %1$s%2$s %3$s(Function<? super %4$s, ? extends %5$s> theAction) {", methodGeneric, retType, camelName, thisName + targetClass.generics(), mapTargetType),
            lineBF,
            format("    Function<%1$s, %2$s> oldAction = (Function<%1$s, %2$s>)$action;", targetName + targetClass.generics(), mapTargetType),
            format("    Function<%1$s, %2$s> newAction =",                                targetName + targetClass.generics(), mapTargetType),
            format("        ($action != null)"),
            format("        ? oldAction : "),
            format("            ($value instanceof %1$s)",                                                                    thisName),
            format("            ? (Function<%1$s, %3$s>)(d -> theAction.apply((%2$s)d))", targetName + targetClass.generics(),thisName + targetClass.generics(), mapTargetType),
            format("            : oldAction;"),
            format("    "),
            format("    " + retStmt),
            format("}"),
            format("public %1$s%2$s %3$s(Supplier<? extends %4$s> theSupplier) {", methodGeneric, retType, camelName, mapTargetType),
            format("    return %1$s(d->theSupplier.get());"         , camelName),
            format("}"),
            format("public %1$s%2$s %3$s(%4$s theValue) {", methodGeneric, retType, camelName, mapTargetType),
            format("    return %1$s(d->theValue);"         , camelName),
            format("}")
        ).stream()
         .filter(Objects::nonNull)
         .map("    "::concat)
         .collect(toList());
    }
    
    private List<String> createCasesPartial(boolean isFirst, boolean typed, Case thisChoice, String thisName,
            String camelName, String switchClassName, String targetName, String mapTargetType) {
        val methodGeneric = typed ? "" : (isFirst ? "<" + mapTargetType + "> " : "");
        val lineBF = isFirst ? "    Function<" + targetName  + targetClass.generics() + ", " + mapTargetType + "> $action = null;" : null;
        return !thisChoice.isParameterized() ? new ArrayList<String>()
        : asList(
            format(""),
            format("public %1$s%2$s<%5$s> %3$s(Predicate<%4$s> check, Function<? super %4$s, ? extends %6$s> theAction) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
            lineBF,
            format("    Function<%1$s, %2$s> oldAction = (Function<%1$s, %2$s>)$action;", targetName + targetClass.generics(), mapTargetType),
            format("    Function<%1$s, %2$s> newAction =",                               targetName + targetClass.generics(), mapTargetType),
            format("        ($action != null)"),
            format("        ? oldAction : "),
            format("            (($value instanceof %1$s) && check.test((%2$s)$value))",    thisName, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics())),
            format("            ? (Function<%1$s, %3$s>)(d -> theAction.apply((%2$s)d))", targetName + targetClass.generics(), thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType),
            format("            : oldAction;"),
            format("    "),
            format("    return new %1$s<%2$s>($value, newAction);", switchClassName, mapTargetType + (targetClass.genericParams().isEmpty() ? "" : ", " + targetClass.genericParams())),
            format("}"),
            format("public %1$s%2$s<%5$s> %3$s(Predicate<%4$s> check, Supplier<? extends %6$s> theSupplier) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
            format("    return %1$s(check, d->theSupplier.get());",                                   camelName),
            format("}"),
            format("public %1$s%2$s<%5$s> %3$s(Predicate<%4$s> check, %6$s theValue) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
            format("    return %1$s(check, d->theValue);",                                   camelName),
            format("}")
        ).stream()
         .filter(Objects::nonNull)
         .map("    "::concat)
         .collect(toList());
    }
}