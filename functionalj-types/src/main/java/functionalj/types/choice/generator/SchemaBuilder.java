package functionalj.types.choice.generator;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.CaseParam;

public class SchemaBuilder implements Lines {
    
    private final TargetClass targetClass;
    
    public SchemaBuilder(TargetClass targetClass) {
        this.targetClass = targetClass;
    }
    private Stream<String> def() {
        String valueType = CaseParam.class.getCanonicalName();
        Stream<String> withLines = targetClass.spec
            .choices.stream()
            .map(choice -> "    .with(\"" + choice.name + "\", " + choice.name + ".getCaseSchema())");
        return Stream.of(
                Stream.of("static private functionalj.map.FuncMap<String, java.util.Map<String, " + valueType + ">> __schema__ = functionalj.map.FuncMap.<String, java.util.Map<String, " + valueType + ">>newMap()"),
                withLines,
                Stream.of("    .build();"))
            .flatMap(lines -> lines);
    }
    
    @Override
    public List<String> lines() {
        String valueType = CaseParam.class.getCanonicalName();
        return Stream.of(
                    def(),
                    Stream.of("public static java.util.Map<String, java.util.Map<String, " + valueType + ">> getChoiceSchema() {"),
                    Stream.of("    return __schema__;"),
                    Stream.of("}"))
                .flatMap(lines -> lines)
                .collect(toList());
    }
    
}
