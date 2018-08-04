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

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Utils.class)
@AllArgsConstructor
public class SwitchClass implements Lines {
    public final TargetClass  targetClass;
    public final boolean      expandPartial;
    public final boolean      isFirst;
    public final List<Choice> choices;
    
    public SwitchClass(TargetClass targetClass, boolean isFirst, List<Choice> choices) {
        this(targetClass, true, isFirst, choices);
    }
    
    @Override
    public List<String> lines() {
        val targetName      = targetClass.type.name;
        val switchClassName = switchClassName(targetName, choices);
        
        
        val isLast    = choices.size() <= 1;
        val nextName  = switchClassName(targetName, choices, 1);
        val retType   = isLast? "T" : nextName + "<T>";
        val retStmt   = isLast? "return newAction.apply(value);" : "return new " + nextName + " <T>(value, newAction);";
        val thisChoice = choices.get(0);
        val thisName  = thisChoice.name;
        val camelName = toCamelCase(thisChoice.name);
        
        val firstSwitchLines = !isFirst ? new ArrayList<String>() : asList(
            asList(format("public static class %1$sFirstSwitch {", targetName)),
            asList(format("    private %s value;",                                       targetName)),
            asList(format("    private %sFirstSwitch(%s value) { this.value = value; }", targetName, targetName)),
            asList(format("    ")),
            createCasesComplete(true, thisName, camelName, targetName, retType, retStmt),
            createCasesPartial (true, thisChoice, thisName, camelName, switchClassName, targetName),
            createCasesExpand  (true, thisChoice, thisName, camelName, switchClassName),
            asList(format("}"))
        ).stream()
        .flatMap(List::stream)
        .collect(toList());
        
        val switchLines = asList(
            asList(format("public static class %1$s<T> extends UnionTypeSwitch<%2$s, T> {",                   switchClassName, targetName)),
            asList(format("    private %1$s(%2$s value, Function<%2$s, T> action) { super(value, action); }", switchClassName, targetName)),
            asList(format("    ")),
            createCasesComplete(false, thisName, camelName, targetName, retType, retStmt),
            createCasesPartial (false, thisChoice, thisName, camelName, switchClassName, targetName),
            createCasesExpand  (false, thisChoice, thisName, camelName, switchClassName),
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
            String retType, String retStmt) {
        val methodGeneric = isFirst ? "<T> " : "";
        val lineBF = isFirst ? "    Function<" + targetName + ", T> action = null;" : "    @SuppressWarnings(\"unchecked\")";
        return asList(
            format("public %1$s%2$s %3$s(T action) {", methodGeneric, retType, camelName),
            format("    return %1$s(d->action);" , camelName),
            format("}"),
            format("public %1$s%2$s %3$s(Supplier<T> action) {", methodGeneric, retType, camelName),
            format("    return %1$s(d->action.get());"         , camelName),
            format("}"),
            format("public %1$s%2$s %3$s(Function<? super %4$s, T> theAction) {",      methodGeneric, retType, camelName, thisName),
            format(lineBF),
            format("    Function<%1$s, T> oldAction = (Function<%1$s, T>)action;", targetName),
            format("    Function<%1$s, T> newAction =",                            targetName),
            format("        (action != null)"),
            format("        ? oldAction : "),
            format("            (value instanceof %1$s)",                                         thisName),
            format("            ? (Function<%1$s, T>)(d -> theAction.apply((%2$s)d))", targetName,thisName),
            format("            : oldAction;"),
            format("    "),
            format("    " + retStmt),
            format("}")
        ).stream()
         .filter(Objects::nonNull)
         .map("    "::concat)
         .collect(toList());
    }
    
    private List<String> createCasesExpand(boolean isFirst, Choice thisChoice, String thisName, String camelName, String switchClassName) {
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
                    paramDefs. add(param.type.name + " " + param.name);
                    paramCheck.add(format("checkEquals(%s, %s.%s)", param.name, camelName, param.name));
                } else if (code == 2) {
                    val predicateType = param.type.getPredicateType();
                    paramDefs.add("Predicate<" + predicateType.name + "> " + param.name + "Check");
                    paramCheck.add(format("%sCheck.test(%s.%s)", param.name, camelName, param.name));
                }
            }
            
            String paramDefStr   = paramDefs.stream().collect(joining(", "));
            String paramCheckStr = paramCheck.stream().collect(joining(" && "));
            return asList(
                format(""),
                format("public %1$s<T> %2$s(%3$s, T value) {",  switchClassName, camelName, paramDefStr),
                format("    return %1$s(%1$s -> %2$s, value);", camelName, paramCheckStr),
                format("}"),
                format("public %1$s<T> %2$s(%3$s, Supplier<T> supplier) {", switchClassName, camelName, paramDefStr),
                format("    return %1$s(%1$s -> %2$s, supplier);",          camelName, paramCheckStr),
                format("}"),
                format("public %1$s<T> %2$s(%3$s, Function<%4$s, T> action) {", switchClassName, camelName, paramDefStr, thisName),
                format("    return %1$s(%1$s -> %2$s, action);",                camelName, paramCheckStr),
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
            String camelName, String switchClassName, String targetName) {
        val methodGeneric = isFirst ? "<T> " : "";
        return !thisChoice.isParameterized() ? new ArrayList<String>()
        : asList(
            format(""),
            format("public %1$s%2$s<T> %3$s(Predicate<%4$s> check, T action) {", methodGeneric, switchClassName, camelName, thisName),
            format("    return %1$s(check, d->action);",                         camelName),
            format("}"),
            format("public %1$s%2$s<T> %3$s(Predicate<%4$s> check, Supplier<T> action) {", methodGeneric, switchClassName, camelName, thisName),
            format("    return %1$s(check, d->action.get());",                             camelName),
            format("}"),
            format("public %1$s%2$s<T> %3$s(Predicate<%4$s> check, Function<? super %4$s, T> theAction) {", methodGeneric, switchClassName, camelName, thisName),
            format("    @SuppressWarnings(\"unchecked\")"),
            format("    Function<%1$s, T> oldAction = (Function<%1$s, T>)action;", targetName),
            format("    Function<%1$s, T> newAction =",                            targetName),
            format("        (action != null)"),
            format("        ? oldAction : "),
            format("            ((value instanceof %1$s) && check.test((%1$s)value))",  thisName),
            format("            ? (Function<%1$s, T>)(d -> theAction.apply((%2$s)d))", targetName,thisName),
            format("            : oldAction;"),
            format("    "),
            format("    return new %1$s<T>(value, newAction);", switchClassName),
            format("}")
        ).stream()
         .map("    "::concat)
         .collect(toList());
    }
}