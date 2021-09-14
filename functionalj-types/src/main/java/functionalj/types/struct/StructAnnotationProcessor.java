// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.struct;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import functionalj.types.Struct;
import functionalj.types.input.EnvironmentBuilder;
import functionalj.types.struct.generator.StructBuilder;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;


/**
 * Annotation processor for Struct.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class StructAnnotationProcessor extends AbstractProcessor {
    
    private EnvironmentBuilder environmentBuilder = null;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        val elementUtils = processingEnv.getElementUtils();
        val types        = processingEnv.getTypeUtils();
        val messager     = processingEnv.getMessager();
        val filer        = processingEnv.getFiler();
        environmentBuilder = new EnvironmentBuilder(elementUtils, types, messager, filer);
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        val annotations = new LinkedHashSet<String>();
        annotations.add(Struct.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO - Should find a way to warn when a field is not immutable.
        boolean hasError = false;
        val elementsWithStruct
                = roundEnv.getElementsAnnotatedWith(Struct.class).stream()
                .map(environmentBuilder::newEnvironment)
                .collect(toList());
        for (val environment : elementsWithStruct) {
            val strucSpec      = new StructSpec(environment);
            val packageName    = strucSpec.packageName();
            val specTargetName = strucSpec.targetName();
            
            try {
                val sourceSpec = strucSpec.sourceSpec();
                if (sourceSpec == null)
                    continue;
                
                val dataObjSpec = new StructBuilder(sourceSpec).build();
                val className   = (String)dataObjSpec.type().fullName("");
                val content     = new GenStruct(sourceSpec, dataObjSpec).lines().collect(joining("\n"));
                environment.generateCode(className, content);
            } catch (Exception e) {
                environment.error("Problem generating the class: "
                                + packageName + "." + specTargetName
                                + ": "  + e.getMessage()
                                + ":"   + e.getClass()
                                + stream(e.getStackTrace())
                                    .map(st -> "\n    @" + st)
                                    .collect(joining()));
            } finally {
                hasError |= strucSpec.hasError();
            }
        }
        return hasError;
    }
    
}
