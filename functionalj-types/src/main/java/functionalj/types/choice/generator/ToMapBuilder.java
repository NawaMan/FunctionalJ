// ============================================================================
// Copyright (c) 2017-2023 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
