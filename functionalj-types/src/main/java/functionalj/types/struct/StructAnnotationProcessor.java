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

import static functionalj.types.choice.generator.Lines.string;
import static java.lang.String.format;
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
import functionalj.types.input.Environment;
import functionalj.types.input.SpecElement;
import functionalj.types.struct.generator.StructBuilder;
import functionalj.types.struct.generator.model.GenStruct;
import lombok.val;


/**
 * Annotation processor for Struct.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class StructAnnotationProcessor extends AbstractProcessor {
    
    private Environment environment = null;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        val elementUtils = processingEnv.getElementUtils();
        val types        = processingEnv.getTypeUtils();
        val messager     = processingEnv.getMessager();
        val filer        = processingEnv.getFiler();
        environment = new Environment(elementUtils, types, messager, filer);
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
        val elements
                = roundEnv
                .getElementsAnnotatedWith(Struct.class).stream()
                .map    (element -> SpecElement.of(environment, element))
                .collect(toList());
        for (val element : elements) {
            val strucSpec      = new StructSpec(element);
            val packageName    = strucSpec.packageName();
            val specTargetName = strucSpec.targetName();
            
            try {
                val sourceSpec = strucSpec.sourceSpec();
                if (sourceSpec == null)
                    continue;
                
                val dataObjSpec = new StructBuilder(sourceSpec).build();
                val className   = (String)dataObjSpec.type().fullName("");
                val generator   = new GenStruct(sourceSpec, dataObjSpec);
                val content     = string(generator.lines());
                element.generateCode(className, content);
            } catch (Exception exception) {
                val template = "Problem generating the class: %s.%s: %s:%s%s";
                val excMsg     = exception.getMessage();
                val excClass   = exception.getClass();
                val stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
                val errMsg   = format(template, packageName, specTargetName, excMsg, excClass, stacktrace);
                exception.printStackTrace(System.err);
                element.error(errMsg);
            } finally {
                hasError |= element.hasError();
            }
        }
        return hasError;
    }
    
}
