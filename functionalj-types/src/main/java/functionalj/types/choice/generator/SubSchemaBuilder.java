// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.util.function.Function.identity;
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
            return Stream.of(Stream.of("static private functionalj.map.FuncMap<String, " + valueType + "> __schema__ = functionalj.map.FuncMap.<String, " + valueType + ">empty();")).flatMap(identity());
        }
        Stream<String> params = choice.params.stream().map(param -> "    .with(\"" + param.name() + "\", " + param.toCode() + ")");
        Stream<String> def = Stream.of(Stream.of("static private functionalj.map.FuncMap<String, " + valueType + "> __schema__ = functionalj.map.FuncMap.<String, " + valueType + ">newMap()"), params, Stream.of("    .build();")).flatMap(identity());
        return def;
    }
    
    @Override
    public List<String> lines() {
        String       valueType = CaseParam.class.getCanonicalName();
        List<String> lines     = Stream.of(def(), Stream.of("public static java.util.Map<String, " + valueType + "> getCaseSchema() {"), Stream.of("    return __schema__;"), Stream.of("}")).flatMap(identity()).collect(toList());
        return lines;
    }
}
