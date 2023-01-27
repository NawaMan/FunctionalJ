package functionalj.types.struct.generator;

import static functionalj.types.choice.generator.Utils.toListCode;
import static functionalj.types.choice.generator.Utils.toStringLiteral;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;
import lombok.experimental.Accessors;

@Value
@RequiredArgsConstructor
@Accessors(fluent = true)
public class Callable {
    
    private final String          name;
    private final Type            type;
    private final boolean         isVarAgrs;
    private final Accessibility   accessibility;
    private final Scope           scope;
    private final Modifiability   modifiability;
    private final Concrecity      concrecity;
    private final List<Parameter> parameters;
    private final List<Generic>   generics;
    private final List<Type>      exceptions;
    
    public String toCode() {
        val params = Arrays.asList(
                toStringLiteral(name),
                type.toCode(),
                isVarAgrs,
                (accessibility == null) ? "null" : (Accessibility.class.getCanonicalName() + "." + accessibility.name().toUpperCase()),
                (scope         == null) ? "null" : (Scope        .class.getCanonicalName() + "." + scope        .name().toUpperCase()),
                (modifiability == null) ? "null" : (Modifiability.class.getCanonicalName() + "." + modifiability.name().toUpperCase()),
                (concrecity    == null) ? "null" : (Concrecity   .class.getCanonicalName() + "." + concrecity   .name().toUpperCase()),
                toListCode(parameters, Parameter::toCode),
                toListCode(generics,   Generic::toCode),
                toListCode(exceptions, Type::toCode)
        );
        return "new functionalj.types.struct.generator.Callable("
                + params.stream().map(String::valueOf).collect(Collectors.joining(", "))
                + ")";
    }
}
