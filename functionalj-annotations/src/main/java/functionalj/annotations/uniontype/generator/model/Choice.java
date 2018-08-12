package functionalj.annotations.uniontype.generator.model;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import lombok.ToString;

@ToString
public class Choice {
    public final String name;
    public final String validationMethod;
    public final List<ChoiceParam> params;
    
    public Choice(String name) {
        this(name, null, emptyList());
    }
    public Choice(String name, List<ChoiceParam> params) {
        this(name, null, params);
    }
    public Choice(String name, String validationMethod, List<ChoiceParam> params) {
        this.name             = name;
        this.validationMethod = validationMethod;
        this.params           = params;
    }
    public boolean isParameterized() {
        return (params != null) && !params.isEmpty();
    }
    
    public String mapJoinParams(Function<ChoiceParam, String> mapper, String delimiter) {
        if (params == null)
            return null;
        return params.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(joining(delimiter));
    }
    public List<String> mapParams(Function<ChoiceParam, String> mapper) {
        if (params == null)
            return null;
        return params.stream()
                .map(mapper)
                .filter(Objects::nonNull)
                .collect(toList());
    }
}