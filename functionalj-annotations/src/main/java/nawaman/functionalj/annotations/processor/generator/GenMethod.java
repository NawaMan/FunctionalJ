package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.annotations.processor.generator.ILines.indent;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.functions.StringFunctions.toStr;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;

@Value
@Wither
@EqualsAndHashCode(callSuper=false)
public class GenMethod implements GenElement {
    
    private Accessibility  accessibility;
    private Modifiability  modifiability;
    private Scope          scope;
    private Type           type;
    private String         name;
    private List<GenParam> params;
    private ILines         body;
    
    @Override
    public Stream<Type> getRequiredTypes() {
        Set<Type> types = new HashSet<>();
        types.add(type);
        GenElement.super
            .getRequiredTypes()
            .forEach(types::add);
        for (GenParam param : params) {
            Type paramType = param.getType();
            if (types.contains(paramType))
                continue;
            types.add(paramType);
            param
                .getRequiredTypes()
                .forEach(types::add);
        }
        return types.stream();
    }
    
    public ILines toDefinition() {
        val paramDefs = params.stream().map(GenParam::toDefinition).collect(joining(", "));
        val definition = Stream.of(accessibility, modifiability, scope, type.simpleName(), name + "(" + paramDefs + ")", "{")
                        .map(toStr())
                        .filter(Objects::nonNull)
                        .collect(joining(" "));
        return ()->asList(
                        line(definition),
                        indent(body),
                        line("}")
                    )
                    .stream()
                    .flatMap(ILines::lines)
                    .filter(Objects::nonNull);
    }
}