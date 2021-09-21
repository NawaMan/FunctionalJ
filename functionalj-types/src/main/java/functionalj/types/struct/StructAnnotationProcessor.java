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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import functionalj.types.Struct;
import functionalj.types.input.Environment;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputType;
import functionalj.types.struct.generator.StructSpecBuilder;
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
    
    private final List<String> logs = new ArrayList<>();
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // TODO - Should find a way to warn when a field is not immutable.
        boolean hasError = false;
        val elements
                = roundEnv
                .getElementsAnnotatedWith(Struct.class).stream()
                .map    (environment::element)
                .collect(toList());
        for (val element : elements) {
            val sourceSpecBuilder = new SourceSpecBuilder(element);
            val packageName       = sourceSpecBuilder.packageName();
            val specTargetName    = sourceSpecBuilder.targetName();
            
            prepareLogs(element);
            
            try {
                val sourceSpec = sourceSpecBuilder.sourceSpec();
                if (sourceSpec == null)
                    continue;
                
                val structSpec = new StructSpecBuilder(sourceSpec).build();
                val className  = structSpec.targetClassName();
                val generator  = new GenStruct(sourceSpec, structSpec);
                val content    = string(generator.lines());
                val logStrings = logs.stream().map("//  "::concat).collect(Collectors.joining("\n"));
                element.generateCode(className, content + "\n\n" + logStrings);
            } catch (Exception exception) {
                val template = "Problem generating the class: %s.%s: %s:%s%s";
                val excMsg     = exception.getMessage();
                val excClass   = exception.getClass();
                val stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
                val errMsg     = format(template, packageName, specTargetName, excMsg, excClass, stacktrace);
                
                exception.printStackTrace(System.err);
                element.error(errMsg);
            } finally {
                hasError |= element.hasError();
            }
        }
        return hasError;
    }
    
    private void prepareLogs(InputElement element) {
        if (element.isTypeElement()) {
            logs.add("Element is a type: " + element);
            return;
        }
        
        val method = element.asMethodElement();
        for (val parameter : method.parameters()) {
            logs.add("  - Parameter [" + parameter.simpleName() + "] is a type element: " + parameter.isTypeElement());
            logs.add("  - Parameter [" + parameter.simpleName() + "] toString         : " + parameter);
            logs.add("  - Parameter [" + parameter.simpleName() + "] simple name      : " + parameter.simpleName());
            logs.add("  - Parameter [" + parameter.simpleName() + "] asType.toString  : " + parameter.asType());
            logs.add("  - Parameter [" + parameter.simpleName() + "] asType.kind      : " + parameter.asType().typeKind());
            logs.add("  - Parameter [" + parameter.simpleName() + "] asType.class     : " + ((InputType.Impl)parameter.asType()).insight());
            
            if (parameter.asType().isDeclaredType()) {
                val type = parameter.asType().asDeclaredType();
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement              : " + type.asTypeElement());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.qualifiedName: " + type.asTypeElement().qualifiedName());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.typeArguments              : " + type.typeArguments());
            }
            
        }
    }
    
}
