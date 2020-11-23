// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static functionalj.types.choice.generator.model.Method.Kind.DEFAULT;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import functionalj.types.choice.Self;
import functionalj.types.choice.generator.model.Method;


public class SourceMethod implements Lines {
    
    private final TargetClass targetClass;
    
    public SourceMethod(TargetClass targetClass) {
        super();
        this.targetClass = targetClass;
    }
    
    public TargetClass getTargetClass() {
        return targetClass;
    }
    
    @Override
    public List<String> lines() {
        return targetClass
                .spec
                .methods.stream()
                .map    (this::methodToCode)
                .flatMap(List::stream)
                .collect(toList());
    }
    
    private List<String> methodToCode(Method m) {
        var genericsDef = m.generics.isEmpty() ? "" :
                        "<" + m.generics.stream()
                               .map(g -> g.withBound.replaceAll(" extends Object$", ""))
                               .collect(joining(", ")) + "> ";
        var returnSelf = Objects.equals(m.returnType.packageName(),     targetClass.type.packageName())
                      && Objects.equals(m.returnType.encloseName(),     targetClass.type.encloseName())
                      && Objects.equals(m.returnType.simpleName(),      targetClass.type.simpleName())
                      && Objects.equals(m.returnType.generics().size(), targetClass.type.generics().size());
        var genericCount = targetClass.type.generics().size();
        var returnPrefix = returnSelf ? Self.class.getCanonicalName() + (genericCount == 0 ? "" : genericCount) + ".unwrap(" : "";
        var returnSuffix = returnSelf ? ")"                            : "";
        if (DEFAULT.equals(m.kind)) {
            if (isThisMethod(m)) {
                return asList(format(
                        "public %1$s%2$s {\n"
                      + "    return %3$s__spec.%4$s%5$s;\n"
                      + "}", genericsDef, m.definitionForThis(), returnPrefix, m.callForThis(targetClass.type), returnSuffix)
                      .split("\n"));
            } else {
                return asList(format(
                        "public %1$s%2$s {\n"
                      + "    return %3$s__spec.%4$s%5$s;\n"
                      + "}", genericsDef, m.definition(), returnPrefix, m.call(), returnSuffix)
                      .split("\n"));
            }
        } else {
            return asList(format(
                    "public static %1$s%2$s {\n"
                  + "    return %3$s%4$s.%5$s%6$s;\n"
                  + "}",
                  genericsDef,
                  m.definition(),
                  returnPrefix,
                  targetClass.spec.sourceType.fullName(),
                  m.call(),
                  returnSuffix)
                  .split("\n"));
        }
    }
    
    private boolean isThisMethod(Method m) {
        return !m.params.isEmpty() && m.params.get(0).type.toString().equals(targetClass.type.toString());
    }
    
}
