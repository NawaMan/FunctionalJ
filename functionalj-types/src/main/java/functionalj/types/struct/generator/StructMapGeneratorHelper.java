package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.ILines.line;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public class StructMapGeneratorHelper {
    
    public static final String METHOD_TO_MAP = "__toMap";
    
    static GenMethod generateFromMap(SourceSpec sourceSpec) {
        val targetType  = sourceSpec.getTargetType();
        val fromMapBody = ILines.line(
                sourceSpec.getGetters()
                .stream()
                .map(field -> {
                    val indent        = "            ";
                    val fieldType     = field.type();
                    val fieldTypeName = fieldType.simpleName();
                    val fieldTypeFull = fieldType.simpleNameWithGeneric();
                    String extraction 
                            = format(
                                "%s(%s)$utils.extractPropertyFromMap(%s.class, %s.class, map, $schema, \"%s\")",
                                indent,
                                fieldTypeFull,
                                targetType.simpleName(),
                                fieldTypeName,
                                field.name()
                            );
                    return extraction;
                })
                .collect(Collectors.joining(",\n"))
                .split("\n"));
        val fromMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.STATIC,
                Modifiability.MODIFIABLE,
                targetType,
                "fromMap",
                asList(new GenParam("map", Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic("? extends Object", "? extends Object", null))))),
                ILines.linesOf(
                    line("Map<String, Getter> $schema = getStructSchema();"),
                    line(targetType.simpleName() + " obj = new " + targetType.simpleName() + "("),
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
                .map(g -> "map.put(\"" + g.name() + "\", $utils.toMapValueObject(" + g.name() + "));")
                .collect(Collectors.toList()));
        val toMap = new GenMethod(
                Accessibility.PUBLIC,
                Scope.INSTANCE,
                Modifiability.MODIFIABLE,
                Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.OBJECT))),
                METHOD_TO_MAP,
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
