package functionalj.types.choice.generator;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;

public class SubSchemaBuilder implements Lines {
    
    private final Case choice;
    
    public SubSchemaBuilder(Case choice) {
        this.choice = choice;
    }
    
    private Stream<String> def() {
        String valueType = CaseParam.class.getCanonicalName();
        if (choice.params.isEmpty()) {
            return Stream.of(
                    Stream.of("static private functionalj.map.FuncMap<String, " + valueType + "> __schema__ = functionalj.map.FuncMap.<String, " + valueType + ">empty();"))
                    .flatMap(lines -> lines);
        }
        
        Stream<String> params 
                = choice
                .params.stream()
                .map(param -> "    .with(\"" + param.name + "\", " + param.toCode() + ")");
        return Stream.of(
                Stream.of("static private functionalj.map.FuncMap<String, " + valueType + "> __schema__ = functionalj.map.FuncMap.<String, " + valueType + ">newMap()"), 
                params,
                Stream.of("    .build();"))
                .flatMap(lines -> lines);
    }
    
    @Override
    public List<String> lines() {
        String valueType = CaseParam.class.getCanonicalName();
        return Stream.of(
                def(),
                Stream.of("public static java.util.Map<String, " + valueType + "> getCaseSchema() {"),
                Stream.of("    return __schema__;"),
                Stream.of("}"))
            .flatMap(lines -> lines)
            .collect(toList());
    }

}
