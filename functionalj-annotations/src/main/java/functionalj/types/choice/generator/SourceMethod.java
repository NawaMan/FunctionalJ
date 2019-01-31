package functionalj.types.choice.generator;

import static functionalj.types.choice.generator.model.Method.Kind.DEFAULT;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import functionalj.types.choice.Self;
import functionalj.types.choice.generator.model.Method;
import lombok.val;

public class SourceMethod implements Lines {
    
    private final TargetClass targetClass;
    
    public SourceMethod(TargetClass targetClass) {
        super();
        this.targetClass = targetClass;
    }
    
    public TargetClass getTargetClass() {
        return targetClass;
    }
    
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
        val genericsDef = m.generics.isEmpty() ? "" : 
                        "<" + m.generics.stream()
                               .map(g -> g.withBound.replaceAll(" extends Object$", ""))
                               .collect(joining(", ")) + "> ";
        val returnSelf = Objects.equals(m.returnType.pckg,            targetClass.type.pckg)
                      && Objects.equals(m.returnType.encloseClass,    targetClass.type.encloseClass)
                      && Objects.equals(m.returnType.name,            targetClass.type.name)
                      && Objects.equals(m.returnType.generics.size(), targetClass.type.generics.size());
        val genericCount = targetClass.type.generics.size();
        val returnPrefix = returnSelf ? Self.class.getCanonicalName() + (genericCount == 0 ? "" : genericCount) + ".getAsMe(" : "";
        val returnSuffix = returnSelf ? ")"                            : "";
        if (DEFAULT.equals(m.kind)) {
            if (isThisMethod(m)) {
                return asList(format(
                        "public %1$s%2$s {\n"
                      + "    return %3$s__spec.%4$s%5$s;\n"
                      + "}", genericsDef, m.definitionForThis(), returnPrefix, m.callForThis(targetClass.type), returnSuffix)
                      .split("\n"));
            } else {
                return asList(format(
                        "public %1$s%2$s {\n"
                      + "    return %3$s__spec.%4$s%5$s;\n"
                      + "}", genericsDef, m.definition(), returnPrefix, m.call(), returnSuffix)
                      .split("\n"));
            }
        } else {
            return asList(format(
                    "public static %1$s%2$s {\n"
                  + "    return %3$s%4$s.%5$s%6$s;\n"
                  + "}", 
                  genericsDef,
                  m.definition(),
                  returnPrefix,
                  targetClass.spec.sourceType.name,
                  m.call(),
                  returnSuffix)
                  .split("\n"));
        }
    }
    
    private boolean isThisMethod(Method m) {
        return !m.params.isEmpty() && m.params.get(0).type.toString().equals(targetClass.type.toString());
    }
    
}
