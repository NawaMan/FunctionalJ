package functionalj.types.choice.generator;

import static java.util.stream.Collectors.toList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import functionalj.types.choice.generator.model.Case;

public class ToMapBuilder implements Lines {
    
    private final TargetClass targetClass;
    
    private final Case choice;
    
    public ToMapBuilder(TargetClass targetClass, Case choice) {
        this.targetClass = targetClass;
        this.choice = choice;
    }
    
    private Stream<String> body() {
        if (choice.params.isEmpty())
            return Stream.of("    return " + Collections.class.getCanonicalName() + ".singletonMap(\"__tagged\", $utils.toMapValueObject(\"" + choice.name + "\"));");
        
        Stream<String> params = choice.params.stream().map(param -> "    map.put(\"" + param.name() + "\", this." + param.name() + ");");
        Stream<String> body   = Stream.of(Stream.of("    " + Map.class.getCanonicalName() + "<String, Object> map = new " + HashMap.class.getCanonicalName() + "<>();"), Stream.of("    map.put(\"" + targetClass.spec.tagMapKeyName + "\", $utils.toMapValueObject(\"" + choice.name + "\"));"), params, Stream.of("    return map;")).flatMap(allLines -> allLines);
        return body;
    }
    
    @Override
    public List<String> lines() {
        List<String> lines = Stream.of(Stream.of("public java.util.Map<String, Object> __toMap() {"), body(), Stream.of("}")).flatMap(allLines -> allLines).collect(toList());
        return lines;
    }
}
