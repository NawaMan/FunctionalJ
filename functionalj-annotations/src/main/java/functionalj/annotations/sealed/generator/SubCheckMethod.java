package functionalj.annotations.sealed.generator;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import functionalj.annotations.sealed.generator.model.Choice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubCheckMethod implements Lines {
    public final TargetClass  targetClass;
    public final List<Choice> choices;
    
    @Override
    public List<String> lines() {
        return choices.stream()
            .map(choice -> (List<String>)Arrays.asList(
                format("public boolean is%1$s() { return this instanceof %1$s; }",                                                    choice.name),
                format("public Result<%1$s> as%2$s() { return Result.of(this).filter(%2$s.class).map(%2$s.class::cast); }",           choice.name + targetClass.generics(), choice.name),
                format("public %1$s if%2$s(Consumer<%2$s%3$s> action) { if (is%2$s()) action.accept((%2$s%3$s)this); return this; }", targetClass.typeWithGenerics(), choice.name, targetClass.generics()),
                format("public %1$s if%2$s(Runnable action) { if (is%2$s()) action.run(); return this; }",                            targetClass.typeWithGenerics(), choice.name)
            ))
            .flatMap(List::stream)
            .collect(toList());
    }
    
}
