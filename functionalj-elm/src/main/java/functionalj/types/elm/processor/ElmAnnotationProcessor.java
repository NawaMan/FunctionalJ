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
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import functionalj.types.Choice;
import functionalj.types.Struct;
import functionalj.types.choice.ChoiceSpec;
import functionalj.types.elm.Elm;
import functionalj.types.input.Environment;
import functionalj.types.input.InputElement;
import functionalj.types.struct.SourceSpecBuilder;
import lombok.val;


/**
 * Annotation processor for Elm.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmAnnotationProcessor extends AbstractProcessor {
    
    private Environment environment = null;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        val elementUtils = processingEnv.getElementUtils();
        val typeUtils    = processingEnv.getTypeUtils();
        val messager     = processingEnv.getMessager();
        val filer        = processingEnv.getFiler();
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
        
        val structTypes = collectAllStructTypes(roundEnv);
        val choiceTypes = collectAllChoiceTypes(roundEnv);
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(Elm.class)) {
            val element = environment.element(javaElement);
            
            val struct = element.annotation(Struct.class);
            if (struct != null) {
                hasError = hasError | !handleStructType(element, structTypes, choiceTypes);
                continue;
            }
            
            val choice = element.annotation(Choice.class);
            if (choice != null) {
                hasError = hasError | !handleChoiceType(element, structTypes, choiceTypes);
                continue;
            }
            
            element.error("The element must either be a struct or a choice.");
        }
        return hasError;
    }
    
    private List<String> collectAllStructTypes(RoundEnvironment roundEnv) {
        val allTypes = new ArrayList<String>();
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(Elm.class)) {
            val element = environment.element(javaElement);
            val struct  = element.annotation(Struct.class);
            if (struct != null) {
                val name = element.simpleName();
                allTypes.add(name);
            }
        }
        return allTypes;
    }
    
    private List<String> collectAllChoiceTypes(RoundEnvironment roundEnv) {
        val allTypes = new ArrayList<String>();
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(Elm.class)) {
            val element = environment.element(javaElement);
            val choice = element.annotation(Choice.class);
            if (choice != null) {
                val name = element.simpleName();
                allTypes.add(name);
            }
        }
        return allTypes;
    }
    
    private boolean handleStructType(InputElement element, List<String> structTypes, List<String> choiceTypes) {
        val structSpec     = new SourceSpecBuilder(element);
        val sourceSpec     = structSpec.sourceSpec();
        val packageName    = structSpec.packageName();
        val specTargetName = structSpec.targetName();
        try {
            val elmStructSpec = new ElmStructSpec(sourceSpec, element);
            val elmStruct     = new ElmStructBuilder(elmStructSpec, structTypes, choiceTypes);
            val baseDir       = elmStructSpec.generatedDirectory();
            val folderName    = elmStructSpec.folderName();
            val fileName      = elmStructSpec.fileName();
            
            val generatedPath = baseDir + folderName + "/";
            val generatedCode = elmStruct.toElmCode();
            val generatedName = generatedPath + fileName;
            
            generateElmCode(generatedPath, generatedCode, generatedName);
            return true;
        } catch (Throwable e) {
            element.error("Problem generating the class: "
                    + packageName + "." + specTargetName
                    + ": "  + e.getMessage()
                    + ":"   + e.getClass()
                    + stream(e.getStackTrace())
                        .map(st -> "\n    @" + st)
                        .collect(joining()));
            return !element.hasError();
        }
    }
    
    private void generateElmCode(String generatedPath, String generatedCode, String generatedName) throws IOException {
        new File(generatedPath).mkdirs();
        val generatedFile = new File(generatedName);
        val lines         = asList(generatedCode.split("\n"));
        Files.write(generatedFile.toPath(), lines);
    }
    
    private boolean handleChoiceType(InputElement element, List<String> structTypes, List<String> choiceTypes) {
        val choiceSpec     = new ChoiceSpec(element);
        val sourceSpec     = choiceSpec.sourceSpec();
        val packageName    = choiceSpec.packageName();
        val specTargetName = choiceSpec.targetName();
        try {
            val elmChoiceSpec = new ElmChoiceSpec(sourceSpec, element);
            val elmChoice     = new ElmChoiceBuilder(elmChoiceSpec, structTypes, choiceTypes);
            val baseDir       = elmChoiceSpec.generatedDirectory();
            val folderName    = elmChoiceSpec.folderName();
            val fileName      = elmChoiceSpec.fileName();
            
            val generatedPath = baseDir + folderName + "/";
            val generatedCode = elmChoice.toElmCode();
            val generatedName = generatedPath + fileName;
            
            generateElmCode(generatedPath, generatedCode, generatedName);
            return true;
        } catch (Exception exception) {
            val template = "Problem generating the class: %s.%s: %s:%s%s";
            val excMsg     = exception.getMessage();
            val excClass   = exception.getClass();
            val stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
            val errMsg   = format(template, packageName, specTargetName, excMsg, excClass, stacktrace);
            exception.printStackTrace(System.err);
            element.error(errMsg);
            
            return !element.hasError();
        }
    }
    
}
