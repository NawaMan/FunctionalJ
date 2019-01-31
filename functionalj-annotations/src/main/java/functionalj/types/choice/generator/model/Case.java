package functionalj.annotations.choice.generator.model;

import static functionalj.annotations.choice.generator.Utils.toListCode;
import static functionalj.annotations.choice.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import lombok.ToString;
import lombok.val;

@ToString
public class Case {
    public final String name;
    public final String validationMethod;
    public final List<CaseParam> params;
    
    public Case(String name) {
        this(name, null, emptyList());
    }
    public Case(String name, List<CaseParam> params) {
        this(name, null, params);
    }
    public Case(String name, String validationMethod, List<CaseParam> params) {
        this.name             = name;
        this.validationMethod = validationMethod;
        this.params           = params;
    }
    public boolean isParameterized() {
        return (params != null) && !params.isEmpty();
    }
    
    public String mapJoinParams(Function<CaseParam, String> mapper, String delimiter) {
        if (params == null)
            return null;
        return params.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(joining(delimiter));
    }
    public List<String> mapParams(Function<CaseParam, String> mapper) {
        if (params == null)
            return null;
        return params.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(toList());
    }
    
    public String toCode() {
        val parameters = asList(
                toStringLiteral(name),
                toStringLiteral(validationMethod),
                toListCode     (params, CaseParam::toCode)
        );
        return "new " + this.getClass().getCanonicalName() + "("
                + parameters.stream().collect(joining(", "))
                + ")";
    }
}