package functionalj.annotations.uniontype.generator;

import static functionalj.annotations.uniontype.generator.model.Method.Kind.DEFAULT;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;

import functionalj.annotations.uniontype.generator.model.Method;
import lombok.Value;

@Value
public class SourceMethod implements Lines {
    private final TargetClass targetClass;
    
    @Override
    public List<String> lines() {
        return targetClass
                .spec
                .methods.stream()
                .map    (this::methodToCode)
                .flatMap(List::stream)
                .collect(toList());
    }
    
    private List<String> methodToCode(Method m) {
        if (DEFAULT.equals(m.kind)) {
            if (isThisMethod(m)) {
                return asList(format(
                        "public %1$s {\n"
                      + "    return __spec.%2$s;\n"
                      + "}", m.definitionForThis(), m.callForThis())
                      .split("\n"));
            } else {
                return asList(format(
                        "public %1$s {\n"
                      + "    return __spec.%2$s;\n"
                      + "}", m.definition(), m.call())
                      .split("\n"));
            }
        } else {
            return asList(format(
                    "public static %1$s {\n"
                  + "    return %2$s.%3$s;\n"
                  + "}", m.definition(), targetClass.spec.sourceType.name, m.call())
                  .split("\n"));
        }
    }
    
    private boolean isThisMethod(Method m) {
        return !m.params.isEmpty() && m.params.get(0).type.equals(targetClass.type);
    }
    
}
