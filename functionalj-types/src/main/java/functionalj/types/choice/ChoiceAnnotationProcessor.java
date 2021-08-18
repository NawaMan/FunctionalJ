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

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import functionalj.types.Choice;
import functionalj.types.choice.generator.Generator;
import functionalj.types.input.EnvironmentBuilder;
import lombok.val;


/**
 * Annotation processor for Choice.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ChoiceAnnotationProcessor extends AbstractProcessor {
    
    private Filer filer;
    
    private List<String> logs = new ArrayList<String>();
    
    private EnvironmentBuilder environmentBuilder = null;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        filer = processingEnv.getFiler();
        
        val elementUtils = processingEnv.getElementUtils();
        val types        = processingEnv.getTypeUtils();
        val messager     = processingEnv.getMessager();
        environmentBuilder = new EnvironmentBuilder(elementUtils, types, messager);
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
        for (Element element : roundEnv.getElementsAnnotatedWith(Choice.class)) {
            val input       = environmentBuilder.newEnvironment(element);
            val choiceSpec  = new ChoiceSpec(input);
            val sourceSpec  = choiceSpec.sourceSpec();
            val packageName = choiceSpec.packageName();
            val targetName  = choiceSpec.targetName();
            
            if (sourceSpec.choices.isEmpty()) {
                val errMsg 
                        = "Choice type must has at least one choice "
                        + "(Reminder: a choice name must start with a capital letter): " 
                        + packageName + "." + targetName;
                environmentBuilder.error(element, errMsg);
                continue;
            }
            
            val generator  = new Generator(sourceSpec);
            
            val typeElement    = (TypeElement)element;
            try {
                val className      = packageName + "." + targetName;
                val content        = generator.lines().stream().collect(joining("\n"));
                val logString      = "\n" + logs.stream().map("// "::concat).collect(joining("\n"));
                generateCode(element, className, content + logString);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                environmentBuilder.error(element, "Problem generating the class: "
                                + packageName + "." + targetName
                                + ": "  + e.getMessage()
                                + ":"   + e.getClass()
                                + ":"   + Arrays.asList(typeElement.getTypeParameters())
                                + ":"   + generator
                                + " @ " + Stream.of(e.getStackTrace()).map(String::valueOf).collect(toList()));
            } finally {
                hasError = hasError || choiceSpec.hasError();
            }
        }
        return hasError;
    }
    
    private void generateCode(Element element, String className, String content) throws IOException {
        try (Writer writer = filer.createSourceFile(className, element).openWriter()) {
            writer.write(content);
        }
    }
    
}
