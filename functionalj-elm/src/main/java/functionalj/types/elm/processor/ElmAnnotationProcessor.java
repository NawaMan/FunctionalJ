// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
package functionalj.types.elm.processor;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import functionalj.types.Choice;
import functionalj.types.Struct;
import functionalj.types.choice.ChoiceSpec;
import functionalj.types.elm.Elm;
import functionalj.types.input.Environment;
import functionalj.types.input.InputElement;
import functionalj.types.struct.SourceSpecBuilder;

/**
 * Annotation processor for Elm.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmAnnotationProcessor extends AbstractProcessor {
    
    private Environment environment = null;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        Elements elementUtils = processingEnv.getElementUtils();
        Types    typeUtils    = processingEnv.getTypeUtils();
        Messager messager     = processingEnv.getMessager();
        Filer    filer        = processingEnv.getFiler();
        environment = new Environment(elementUtils, typeUtils, messager, filer);
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Elm.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean hasError = false;
        List<String> choiceTypes = collectAllChoiceTypes(roundEnv);
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(Elm.class)) {
            InputElement element = environment.element(javaElement);
            Struct       struct  = element.annotation(Struct.class);
            if (struct != null) {
                hasError = hasError | !handleStructType(element, choiceTypes);
                continue;
            }
            Choice choice = element.annotation(Choice.class);
            if (choice != null) {
                hasError = hasError | !handleChoiceType(element, choiceTypes);
                continue;
            }
            element.error("The element must either be a struct or a choice.");
        }
        return hasError;
    }
    
    private List<String> collectAllChoiceTypes(RoundEnvironment roundEnv) {
        List<String> allTypes = new ArrayList<String>();
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(Elm.class)) {
            InputElement element = environment.element(javaElement);
            Choice choice = element.annotation(Choice.class);
            if (choice != null) {
                String name = element.simpleName();
                allTypes.add(name);
            }
        }
        return allTypes;
    }
    
    private boolean handleStructType(InputElement element, List<String> choiceTypes) {
        SourceSpecBuilder structSpec     = new SourceSpecBuilder(element);
        String            packageName    = structSpec.packageName();
        String            specTargetName = structSpec.targetName();
        try {
            ElmStructSpec    elmStructSpec = new ElmStructSpec(structSpec.sourceSpec(), element);
            ElmStructBuilder elmStruct     = new ElmStructBuilder(elmStructSpec, choiceTypes);
            String           baseDir       = elmStructSpec.generatedDirectory();
            String           folderName    = elmStructSpec.folderName();
            String           fileName      = elmStructSpec.fileName();
            String           generatedPath = baseDir + folderName + "/";
            String           generatedCode = elmStruct.toElmCode();
            String           generatedName = generatedPath + fileName;
            generateElmCode(generatedPath, generatedCode, generatedName);
            return true;
        } catch (Throwable e) {
            element.error("Problem generating the class: " + packageName + "." + specTargetName + ": " + e.getMessage() + ":" + e.getClass() + stream(e.getStackTrace()).map(st -> "\n    @" + st).collect(joining()));
            return !element.hasError();
        }
    }
    
    private boolean handleChoiceType(InputElement element, List<String> choiceTypes) {
        ChoiceSpec choiceSpec     = new ChoiceSpec(element);
        String     packageName    = choiceSpec.packageName();
        String     specTargetName = choiceSpec.targetName();
        try {
            ElmChoiceSpec    elmChoiceSpec = new ElmChoiceSpec(choiceSpec.sourceSpec(), element);
            ElmChoiceBuilder elmChoice     = new ElmChoiceBuilder(elmChoiceSpec, choiceTypes);
            String           baseDir       = elmChoiceSpec.generatedDirectory();
            String           folderName    = elmChoiceSpec.folderName();
            String           fileName      = elmChoiceSpec.fileName();
            String           generatedPath = baseDir + folderName + "/";
            String           generatedCode = elmChoice.toElmCode();
            String           generatedName = generatedPath + fileName;
            generateElmCode(generatedPath, generatedCode, generatedName);
            return true;
        } catch (Exception exception) {
            String   template   = "Problem generating the class: %s.%s: %s:%s%s";
            String   excMsg     = exception.getMessage();
            Class<?> excClass   = exception.getClass();
            String   stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
            String   errMsg     = format(template, packageName, specTargetName, excMsg, excClass, stacktrace);
            exception.printStackTrace(System.err);
            element.error(errMsg);
            return !element.hasError();
        }
    }
    
    private void generateElmCode(String generatedPath, String generatedCode, String generatedName) throws IOException {
        new File(generatedPath).mkdirs();
        File         generatedFile = new File(generatedName);
        List<String> lines         = asList(generatedCode.split("\n"));
        Files.write(generatedFile.toPath(), lines);
    }
}
