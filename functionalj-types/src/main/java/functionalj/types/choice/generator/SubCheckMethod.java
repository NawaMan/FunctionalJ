// ============================================================================
// Copyright (c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

import functionalj.types.choice.generator.model.Case;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SubCheckMethod implements Lines {
    public final TargetClass  targetClass;
    public final List<Case> choices;
    
    @Override
    public List<String> lines() {
        return choices.stream()
            .map(choice -> (List<String>)Arrays.asList(
                format("public boolean is%1$s() { return this instanceof %1$s; }",                                                    choice.name),
                format("public Result<%1$s> as%2$s() { return Result.valueOf(this).filter(%2$s.class).map(%2$s.class::cast); }",      choice.name + targetClass.getType().genericsString(), choice.name),
                format("public %1$s if%2$s(Consumer<%2$s%3$s> action) { if (is%2$s()) action.accept((%2$s%3$s)this); return this; }", targetClass.getType().typeWithGenerics(), choice.name, targetClass.getType().genericsString()),
                format("public %1$s if%2$s(Runnable action) { if (is%2$s()) action.run(); return this; }",                            targetClass.getType().typeWithGenerics(), choice.name)
            ))
            .flatMap(List::stream)
            .collect(toList());
    }
    
}
