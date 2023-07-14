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
package functionalj.types.choice;

import static functionalj.types.choice.generator.Lines.string;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import functionalj.types.Choice;
import functionalj.types.choice.generator.Generator;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.input.Environment;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputTypeParameterElement;

/**
 * Annotation processor for Choice.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ChoiceAnnotationProcessor extends AbstractProcessor {
    
    private Environment environment = null;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        Elements elementUtils = processingEnv.getElementUtils();
        Types    types        = processingEnv.getTypeUtils();
        Messager messager     = processingEnv.getMessager();
        Filer    filer        = processingEnv.getFiler();
        environment = new Environment(elementUtils, types, messager, filer);
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Choice.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean hasError = false;
        List<InputElement> elements = roundEnv.getElementsAnnotatedWith(Choice.class).stream().map(environment::element).collect(toList());
        for (InputElement element : elements) {
            ChoiceSpec choiceSpec = new ChoiceSpec(element);
            String packageName = choiceSpec.packageName();
            String targetName = choiceSpec.targetName();
            String className = packageName + "." + targetName;
            SourceSpec sourceSpec = choiceSpec.sourceSpec();
            if (sourceSpec.choices.isEmpty()) {
                String template = "Choice type must has at least one choice with name starts with a capital letter): %s";
                String errMsg = format(template, className);
                element.error(errMsg);
                continue;
            }
            Generator generator = new Generator(sourceSpec);
            try {
                String content = string(generator.lines());
                element.generateCode(className, content);
            } catch (Exception exception) {
                String   template = "Problem generating the class: %s: %s:%s:%s:%s @ %s";
                String   excMsg   = exception.getMessage();
                Class<?> excClass = exception.getClass();
                List<List<? extends InputTypeParameterElement>> typeParams = asList(element.asTypeElement().typeParameters());
                
                String stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
                String errMsg = format(template, className, excMsg, excClass, typeParams, generator, stacktrace);
                exception.printStackTrace(System.err);
                element.error(errMsg);
            } finally {
                hasError |= element.hasError();
            }
        }
        return hasError;
    }
}
