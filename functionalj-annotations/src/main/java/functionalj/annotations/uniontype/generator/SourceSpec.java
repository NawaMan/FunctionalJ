package functionalj.annotations.uniontype.generator;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SourceSpec {
    public final String       targetName;
    public final Type         sourceType;
    public final List<Choice> choices;
}
