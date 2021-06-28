package functionalj.types.choice.generator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import lombok.val;

public class SubSchemaBuilder implements Lines {
    
    private final Case choice;
    
    public SubSchemaBuilder(Case choice) {
        this.choice = choice;
    }
    
    private Stream<String> def() {
        val valueType = CaseParam.class.getCanonicalName();
        if (choice.params.isEmpty()) {
            val def = Stream.of(
                    Stream.of("static private functionalj.map.FuncMap<String, " + valueType + "> __schema__ = functionalj.map.FuncMap.<String, " + valueType + ">empty();"))
                    .flatMap(identity());
            return def;
        }
        
        val params
                = choice
                .params.stream()
                .map(param -> "    .with(\"" + param.name + "\", " + param.toCode() + ")");
        val def = Stream.of(
                Stream.of("static private functionalj.map.FuncMap<String, " + valueType + "> __schema__ = functionalj.map.FuncMap.<String, " + valueType + ">newMap()"),
                params,
                Stream.of("    .build();"))
                .flatMap(identity());
        return def;
    }
    
    @Override
    public List<String> lines() {
        val valueType = CaseParam.class.getCanonicalName();
        val lines = Stream.of(
                def(),
                Stream.of("public static java.util.Map<String, " + valueType + "> getCaseSchema() {"),
                Stream.of("    return __schema__;"),
                Stream.of("}"))
            .flatMap(identity())
            .collect(toList());
        return lines;
    }
    
}
