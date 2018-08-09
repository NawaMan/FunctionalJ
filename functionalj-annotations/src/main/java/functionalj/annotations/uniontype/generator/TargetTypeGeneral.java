package functionalj.annotations.uniontype.generator;

import static functionalj.annotations.uniontype.generator.Utils.toCamelCase;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.experimental.ExtensionMethod;


@ExtensionMethod(Utils.class)
@AllArgsConstructor
public class TargetTypeGeneral implements Lines {
    public final TargetClass  targetClass;
    public final List<Choice> choices;
    
    @Override
    public List<String> lines() {
        val targetName    = targetClass.type.name;
        val choiceStrings = choices.stream()
            .map(choice -> {
                val camelName  = toCamelCase(choice.name);
                val paramCount = choice.params.size();
                if (paramCount == 0) {
                    return format("            .%1$s(\"%2$s\")", camelName, choice.name);
                } else {
                    val template = range(0, paramCount).mapToObj(i -> "%" + (i + 1) + "$s").collect(joining(","));
                    val templateParams = choice.params.stream().map(p -> camelName + "." + p.name).collect(joining(","));
                    return format("            .%1$s(%1$s -> \"%2$s(\" + String.format(\"%3$s\", %4$s) + \")\")", 
                                      camelName, choice.name, template, templateParams);
                }
            })
            .map("    "::concat)
            .collect(toList());
        
        return asList(
            asList(format("public final %1$sFirstSwitch%2$s mapSwitch = new %1$sFirstSwitch%2$s(this);", targetName, targetClass.generics())),
            asList(format("@Override public %1$sFirstSwitch%2$s __switch() { return mapSwitch; }",         targetName, targetClass.generics())),
            asList(format("")),
            asList(format("private volatile String toString = null;")),
            asList(format("@Override")),
            asList(format("public String toString() {")),
            asList(format("    if (toString != null)")),
            asList(format("        return toString;")),
            asList(format("    synchronized(this) {")),
            asList(format("        if (toString != null)")),
            asList(format("            return toString;")),
            asList(format("        toString = Switch(this)")),
            choiceStrings,
            asList(format("        ;")),
            asList(format("        return toString;")),
            asList(format("    }")),
            asList(format("}")),
            asList(format("@Override")),
            asList(format("public int hashCode() {")),
            asList(format("    return toString().hashCode();")),
            asList(format("}")),
            asList(format("@Override")),
            asList(format("public boolean equals(Object obj) {")),
            asList(format("    if (!(obj instanceof %1$s))", targetName)),
            asList(format("        return false;")),
            asList(format("    ")),
            asList(format("    if (this == obj)")),
            asList(format("        return true;")),
            asList(format("    ")),
            asList(format("    String objToString  = obj.toString();")),
            asList(format("    String thisToString = this.toString();")),
            asList(format("    if (thisToString.equals(objToString))")),
            asList(format("        return true;")),
            asList(format("    ")),
            asList(format("    String objAlternative  = ((" + targetClass.typeWithGenerics() + ")obj).alternativeString();")),
            asList(format("    String thisAlternative = this.alternativeString();")),
            asList(format("    return thisAlternative.equals(objAlternative);")),
            asList(format("}")),
            asList(format(""))
        ).stream()
         .flatMap(List::stream)
         .collect(toList());
    }
}