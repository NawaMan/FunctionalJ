package functionalj.annotations.uniontype.generator;

import static functionalj.annotations.uniontype.generator.Utils.switchClassName;
import static functionalj.annotations.uniontype.generator.Utils.toCamelCase;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import functionalj.annotations.uniontype.generator.model.Choice;
import lombok.val;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Utils.class)
public class SwitchClass implements Lines {
	
    public final TargetClass  targetClass;
    public final boolean      expandPartial;
    public final boolean      isFirst;
    public final List<Choice> choices;
    
    public SwitchClass(TargetClass targetClass, boolean isFirst, List<Choice> choices) {
        this(targetClass, true, isFirst, choices);
    }
    
    public SwitchClass(TargetClass targetClass, boolean expandPartial, boolean isFirst, List<Choice> choices) {
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
            asList(format("public static class %1$sFirstSwitch%2$s {",                          targetName, targetClass.genericDef())),
            asList(format("    private %s $value;",                                                         targetClass.typeWithGenerics())),
            asList(format("    private %sFirstSwitch(%s theValue) { this.$value = theValue; }", targetName, targetClass.typeWithGenerics())),
            asList(format("    ")),
            createCasesComplete(true,             thisName, camelName,                  targetName, retType, retStmt, mapTargetType),
            createCasesPartial (true, thisChoice, thisName, camelName, switchClassName, targetName,                   mapTargetType),
            createCasesExpand  (true, thisChoice, thisName, camelName, switchClassName,                               mapTargetType),
            asList(format("}"))
        ).stream()
        .flatMap(List::stream)
        .collect(toList());
        
        val switchLines = asList(
            asList(format("public static class %1$s<%3$s> extends UnionTypeSwitch<%2$s, %4$s> {",                            switchClassName, targetName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericDefParams()), mapTargetType)),
            asList(format("    private %1$s(%2$s theValue, Function<%2$s, %3$s> theAction) { super(theValue, theAction); }", switchClassName, targetName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType)),
            asList(format("    ")),
            createCasesComplete(false,             thisName, camelName,                  targetName, retType, retStmt, mapTargetType),
            createCasesPartial (false, thisChoice, thisName, camelName, switchClassName, targetName,                   mapTargetType),
            createCasesExpand  (false, thisChoice, thisName, camelName, switchClassName,                               mapTargetType),
            asList(format("}"))
        ).stream()
        .flatMap(List::stream)
        .collect(toList());
        
        val lines = new ArrayList<String>();
        lines.addAll(firstSwitchLines);
        lines.addAll(switchLines);
        return lines;
    }
    
    private List<String> createCasesComplete(boolean isFirst, String thisName, String camelName, String targetName, 
            String retType, String retStmt, String mapTargetType) {
        val methodGeneric = isFirst ? "<" + mapTargetType + "> " : "";
        val lineBF = isFirst ? "    Function<" + targetName + targetClass.generics() + ", " + mapTargetType + "> $action = null;" : null;
        return asList(
            format("public %1$s%2$s %3$s(Function<? super %4$s, %5$s> theAction) {", methodGeneric, retType, camelName, thisName + targetClass.generics(), mapTargetType),
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
            format("public %1$s%2$s %3$s(Supplier<%4$s> theSupplier) {", methodGeneric, retType, camelName, mapTargetType),
            format("    return %1$s(d->theSupplier.get());"         , camelName),
            format("}")
        ).stream()
         .filter(Objects::nonNull)
         .map("    "::concat)
         .collect(toList());
    }
    
    private List<String> createCasesExpand(boolean isFirst, Choice thisChoice, String thisName, String camelName, String switchClassName, String mapTargetType) {
        val eachExpand = (Function<Integer, List<String>>)(theCode -> {
            val count = thisChoice.params.size();
            val codes = new ArrayList<Integer>();
            int loopCode  = theCode;
            for (int i = 0; i < count; i++) {
                codes.add(loopCode % 3);
                loopCode = loopCode / 3;
            }
            
            val paramDefs  = new ArrayList<String>();
            val paramCheck = new ArrayList<String>();
            for (int i = 0; i < count; i++) {
                val code  = codes.get(i);
                val param = thisChoice.params.get(i);
                if (code == 0) {
                    paramDefs.add("Absent " + param.name);
                } else if (code == 1) {
                	val paramName = "a" + param.name.substring(0, 1).toUpperCase() + param.name.substring(1);
                    paramDefs. add(param.type.name + " " + paramName);
                    paramCheck.add(format("checkEquals(%s, %s.%s)", paramName, camelName, param.name));
                } else if (code == 2) {
                    val predicateType = param.type.getPredicateType();
                    paramDefs.add("Predicate<" + predicateType.name + "> " + param.name + "Check");
                    paramCheck.add(format("%sCheck.test(%s.%s)", param.name, camelName, param.name));
                }
            }
            
            val methodGeneric = isFirst ? "<" + mapTargetType + "> " : "";
            String paramDefStr   = paramDefs.stream().collect(joining(", "));
            String paramCheckStr = paramCheck.stream().collect(joining(" && "));
            return asList(
                format(""),
                format("public %1$s%2$s<%6$s> %3$sOf(%4$s, Function<%5$s, %7$s> theAction) {", methodGeneric, switchClassName, camelName, paramDefStr, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
                format("    return %1$s(%1$s -> %2$s, theAction);",                            camelName, paramCheckStr),
                format("}"),
                format("public %1$s%2$s<%5$s> %3$sOf(%4$s, Supplier<%6$s> theSupplier) {", methodGeneric, switchClassName, camelName, paramDefStr, mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
                format("    return %1$s(%1$s -> %2$s, theSupplier);",                      camelName, paramCheckStr),
                format("}")
            );
        });
        
        return (!thisChoice.isParameterized() || !expandPartial) ? new ArrayList<String>()
        : range(1, (int)round(pow(3, thisChoice.params.size())))
          .mapToObj(Integer::valueOf)
          .map     (eachExpand)
          .flatMap (List::stream)
          .map     ("    "::concat)
          .collect(toList());
    }
    
    private List<String> createCasesPartial(boolean isFirst, Choice thisChoice, String thisName,
            String camelName, String switchClassName, String targetName, String mapTargetType) {
        val methodGeneric = isFirst ? "<" + mapTargetType + "> " : "";
        val lineBF = isFirst ? "    Function<" + targetName  + targetClass.generics() + ", " + mapTargetType + "> $action = null;" : null;
        return !thisChoice.isParameterized() ? new ArrayList<String>()
        : asList(
            format(""),
            format("public %1$s%2$s<%5$s> %3$s(Predicate<%4$s> check, Function<? super %4$s, %6$s> theAction) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
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
            format("public %1$s%2$s<%5$s> %3$s(Predicate<%4$s> check, Supplier<%6$s> theSupplier) {", methodGeneric, switchClassName, camelName, thisName + (targetClass.generics().isEmpty() ? "" : targetClass.generics()), mapTargetType + (targetClass.genericDefParams().isEmpty() ? "" : ", " + targetClass.genericParams()), mapTargetType),
            format("    return %1$s(check, d->theSupplier.get());",                                   camelName),
            format("}")
        ).stream()
         .filter(Objects::nonNull)
         .map("    "::concat)
         .collect(toList());
    }
}