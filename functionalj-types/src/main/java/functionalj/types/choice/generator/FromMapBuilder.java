// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
import java.util.List;
import java.util.stream.Stream;

public class FromMapBuilder implements Lines {
    
    private final TargetClass targetClass;
    
    public FromMapBuilder(TargetClass targetClass) {
        this.targetClass = targetClass;
    }
    
    private Stream<String> body() {
        return Stream.of(Stream.of("    if (map == null)", "        return null;"), Stream.of("    String __tagged = (String)map.get(\"" + targetClass.spec.tagMapKeyName + "\");"), targetClass.spec.choices.stream().flatMap(choice -> Stream.of("    if (\"" + choice.name + "\".equals(__tagged))", "        return (T)" + choice.name + ".caseFromMap(map);")), Stream.of("    throw new IllegalArgumentException(\"Tagged value does not represent a valid type: \" + __tagged);")).flatMap(allLines -> allLines);
    }
    
    @Override
    public List<String> lines() {
        return Stream.of(Stream.of("public static <T extends " + targetClass.type.simpleName() + "> T fromMap(java.util.Map<String, ? extends Object> map) {"), body(), Stream.of("}")).flatMap(allLines -> allLines).collect(toList());
    }
}
