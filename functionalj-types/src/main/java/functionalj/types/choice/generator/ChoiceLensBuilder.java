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

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;

public class ChoiceLensBuilder {
    
    private final SourceSpec sourceSpec;
    
    public ChoiceLensBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    public List<String> build() {
        val targetName    = sourceSpec.targetName;
        val lensClassName = targetName + "Lens";
        val lensClassDef = asList(
                "public static final " + lensClassName + "<" + targetName + "> the" + targetName + " = new " + lensClassName + "<>(LensSpec.of(" + targetName + ".class));",
                "public static final " + lensClassName + "<" + targetName + "> each" + targetName + " = the" + targetName + ";",
                "public static class " + lensClassName + "<HOST> extends ObjectLensImpl<HOST, " + targetName + "> {\n"
                );
        
        val lensClassConstructor = asList(
                "    public " + lensClassName + "(LensSpec<HOST, " + targetName + "> spec) {",
                "        super(spec);",
                "    }"
                );
        
        val isMethods = sourceSpec
                .choices.stream()
                .map(choice -> "BooleanAccessPrimitive<" + targetName + "> is" + choice.name + " = " + targetName + "::is" + choice.name + ";")
                .map(each   -> "    public final " + each)
                .collect(toList());
        val asMethods = sourceSpec
                .choices.stream()
                .map(choice -> "ResultAccess<HOST, " + choice.name + ", " + choice.name + "."+ choice.name + "Lens<HOST>> as" + choice.name + " = createSubResultLens(" + targetName +"::as" + choice.name + ", (functionalj.lens.core.WriteLens<" + targetName + ",Result<" + choice.name + ">>)null, " + choice.name + "."+ choice.name + "Lens::new);")
                .map(each   -> "    public final " + each)
                .collect(toList());
        
        val lensClassDefClose = asList(
                "}"
                );
        
        return asList(
                lensClassDef,
                isMethods,
                asMethods,
                lensClassConstructor,
                lensClassDefClose
            ).stream()
            .map(List::stream)
            .flatMap(Function.identity())
            .map(line -> "    " + line)
            .collect(Collectors.toList());
    }
}
