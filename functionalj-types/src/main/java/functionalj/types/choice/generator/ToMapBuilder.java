package functionalj.types.choice.generator;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.Case;

public class ToMapBuilder implements Lines {
    
    private final TargetClass targetClass;
    private final Case        choice;
    
    public ToMapBuilder(TargetClass targetClass, Case choice) {
        this.targetClass = targetClass;
        this.choice      = choice;
    }
    
    private Stream<String> body() {
        if (choice.params.isEmpty())
            return Stream.of("    return functionalj.map.FuncMap.empty();");
        
        Stream<String> params 
                = choice
                .params.stream()
                .map(param -> "    map.put(\"" + param.name + "\", this." + param.name + ");");
        return Stream.of(
                Stream.of("    java.util.Map<String, Object> map = new java.util.HashMap<>();"),
                Stream.of("    map.put(\"" + targetClass.spec.tagMapKeyName + "\", functionalj.types.ICanToMap.toMapValueObject(\"" + choice.name + "\"));"),
                params,
                Stream.of("    return map;")
            )
            .flatMap(allLines -> allLines);
    }
    
    @Override
    public List<String> lines() {
        return Stream.of(
                Stream.of("public java.util.Map<String, Object> __toMap() {"),
                body(),
                Stream.of("}")
            )
            .flatMap(allLines -> allLines)
            .collect(toList());
    }

}
