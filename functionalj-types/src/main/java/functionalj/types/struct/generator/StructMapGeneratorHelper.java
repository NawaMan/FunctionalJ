// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.struct.generator;

import static functionalj.types.struct.generator.ILines.line;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.Map;
import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.GenMethod;
import functionalj.types.struct.generator.model.GenParam;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;

public class StructMapGeneratorHelper {
    
    public static final String METHOD_TO_MAP = "__toMap";
    
    static GenMethod generateFromMap(SourceSpec sourceSpec) {
        Type   targetType  = sourceSpec.getTargetType();
        ILines fromMapBody = ILines
        .line(sourceSpec.getGetters().stream().map(field -> {
                String indent        = "            ";
                Type   fieldType     = field.type();
                String fieldTypeName = fieldType.simpleName();
                String fieldTypeFull = fieldType.simpleNameWithGeneric();
                String extraction    = format("%s(%s)$utils.extractPropertyFromMap(%s.class, %s.class, map, $schema, \"%s\")", indent, fieldTypeFull, targetType.simpleName(), fieldTypeName, field.name());
                return extraction;
            })
            .collect(joining(",\n"))
            .split("\n")
        );
        GenMethod fromMap = new GenMethod("fromMap", targetType, Accessibility.PUBLIC, Scope.STATIC, Modifiability.MODIFIABLE, asList(new GenParam("map", Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic("? extends Object", "? extends Object", null))))), ILines.linesOf(line("Map<String, Getter> $schema = getStructSchema();"), line(targetType.simpleName() + " obj = new " + targetType.simpleName() + "("), fromMapBody, line("        );"), line("return obj;")));
        return fromMap;
    }
    
    static GenMethod generateToMap(SourceSpec sourceSpec) {
        ILines    toMapBody = ILines.line(sourceSpec.getGetters().stream().map(g -> "map.put(\"" + g.name() + "\", $utils.toMapValueObject(" + g.name() + "));").collect(toList()));
        GenMethod toMap     = new GenMethod(METHOD_TO_MAP, Type.MAP.withGenerics(asList(new Generic(Type.STRING), new Generic(Type.OBJECT))), Accessibility.PUBLIC, Scope.INSTANCE, Modifiability.MODIFIABLE, emptyList(), emptyList(), false, false, ILines.linesOf(line("Map<String, Object> map = new HashMap<>();"), toMapBody, line("return map;")), asList(Type.of(Map.class), Type.of(HashMap.class)), emptyList());
        return toMap;
    }
}
