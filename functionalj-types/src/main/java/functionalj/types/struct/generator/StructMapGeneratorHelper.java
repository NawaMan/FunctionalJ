package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.ILines.line;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import functionalj.types.Generic;
import functionalj.types.IStruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public class StructMapGeneratorHelper {
    
    static GenMethod generateFromMap(SourceSpec sourceSpec) {
        val fromMapBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(g -> "            (" + g.getType().simpleNameWithGeneric() + ")$utils.fromMapValue(map.get(\"" + g.getName() + "\"), $schema.get(\"" + g.getName() + "\"))")
                .collect(Collectors.joining(",\n"))
                .split("\n"));
        val fromMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                sourceSpec.getTargetType(),
                "fromMap",
                asList(new GenParam("map", Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic("? extends Object", "? extends Object", null))))),
                ILines.linesOf(
                    line("Map<String, Getter> $schema = getStructSchema();"),
                    line(sourceSpec.getTargetType().simpleName() + " obj = new " + sourceSpec.getTargetType().simpleName() + "("),
                    fromMapBody,
                    line("        );"),
                    line("return obj;")
                ));
        return fromMap;
    }
    
    static GenMethod generateToMap(SourceSpec sourceSpec) {
        val toMapBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(g -> "map.put(\"" + g.getName() + "\", " + IStruct.class.getCanonicalName() + ".$utils.toMapValueObject(" + g.getName() + "));")
                .collect(Collectors.toList()));
        val toMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.OBJECT))),
                "__toMap",
                emptyList(),
                ILines.linesOf(
                    line("Map<String, Object> map = new HashMap<>();"),
                    toMapBody,
                    line("return map;")
                ),
                asList(Type.of(Map.class), Type.of(HashMap.class)),
                emptyList(),
                false);
        return toMap;
    }
    
}
