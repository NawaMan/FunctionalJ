package functionalj.annotations.uniontype.generator;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Utils.class)
@AllArgsConstructor
public class SubCheckMethod implements Lines {
    public final TargetClass  targetClass;
    public final List<Choice> choices;
    
    @Override
    public List<String> lines() {
        val targetName = targetClass.spec.targetName;
        return choices.stream()
            .map(choice -> (List<String>)Arrays.asList(
                format("public boolean is%1$s() { return this instanceof %1$s; }",                                                        choice.name),
                format("public Result<%1$s> as%1$s() { return Result.of(this).filter(%1$s.class).map(%1$s.class::cast); }",               choice.name),
                format("public %1$s if%2$s(Consumer<%2$s> action) { if (is%2$s()) action.accept((%2$s)this); return this; }", targetName, choice.name),
                format("public %1$s if%2$s(Runnable action) { if (is%2$s()) action.run(); return this; }",                    targetName, choice.name)
            ))
            .flatMap(List::stream)
            .collect(toList());
    }
    
}
