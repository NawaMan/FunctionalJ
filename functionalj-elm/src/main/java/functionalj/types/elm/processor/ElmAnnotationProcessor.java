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

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.Struct;
import functionalj.types.choice.ChoiceSpec;
import functionalj.types.choice.ChoiceSpecInputImpl;
import functionalj.types.elm.Elm;
import functionalj.types.struct.StructSpec;
import functionalj.types.struct.StructSpecInputImpl;
import lombok.val;


/**
 * Annotation processor for Elm.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types    typeUtils;
    private Messager messager;
    private boolean  hasError;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elementUtils = processingEnv.getElementUtils();
        messager     = processingEnv.getMessager();
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
    
    private void error(Element e, String msg) {
        hasError = true;
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        hasError = false;
        for (Element element : roundEnv.getElementsAnnotatedWith(Elm.class)) {
            val struct = element.getAnnotation(Struct.class);
            if (struct != null) {
                handleStructType(element);
                continue;
            }
            
            val choice = element.getAnnotation(Choice.class);
            if (choice != null) {
                handleChoiceType(element);
                continue;
            }
            
            error(element, "The element must either be a struct or a choice.");
        }
        return hasError;
    }
    
    private void handleStructType(Element element) {
        val dummyMessager  = new DummyMessager();
        val input          = new StructSpecInputImpl(element, elementUtils, typeUtils, dummyMessager);
        val structSpec     = new StructSpec(input);
        val sourceSpec     = structSpec.sourceSpec();
        val packageName    = structSpec.packageName();
        val specTargetName = structSpec.targetName();
        try {
            val elmStructSpec = new ElmStructSpec(sourceSpec, element);
            val elmStruct     = new ElmStructBuilder(elmStructSpec);
            val baseDir       = elmStructSpec.generatedDirectory();
            val folderName    = elmStructSpec.folderName();
            val fileName      = elmStructSpec.fileName();
            
            val generatedPath = baseDir + folderName + "/";
            val generatedCode = elmStruct.toElmCode();
            val generatedName = generatedPath + fileName;
            
            generateElmCode(generatedPath, generatedCode, generatedName);
        } catch (Exception e) {
            error(element, "Problem generating the class: "
                    + packageName + "." + specTargetName
                    + ": "  + e.getMessage()
                    + ":"   + e.getClass()
                    + stream(e.getStackTrace())
                        .map(st -> "\n    @" + st)
                        .collect(joining()));
        } finally {
            hasError |= structSpec.hasError();
        }
    }
    
    private void generateElmCode(String generatedPath, String generatedCode, String generatedName) throws IOException {
        new File(generatedPath).mkdirs();
        val generatedFile = new File(generatedName);
        val lines         = asList(generatedCode.split("\n"));
        Files.write(generatedFile.toPath(), lines);
    }
    
    private void handleChoiceType(Element element) {
        val dummyMessager  = new DummyMessager();
        val input          = new ChoiceSpecInputImpl(element, elementUtils, typeUtils, dummyMessager);
        val choiceSpec     = new ChoiceSpec(input);
        val sourceSpec     = choiceSpec.sourceSpec();
        val packageName    = choiceSpec.packageName();
        val specTargetName = choiceSpec.targetName();
        try {
            val elmChoiceSpec = new ElmChoiceSpec(sourceSpec, element);
            val elmChoice     = new ElmChoiceBuilder(elmChoiceSpec);
            val baseDir       = elmChoiceSpec.generatedDirectory();
            val folderName    = elmChoiceSpec.folderName();
            val fileName      = elmChoiceSpec.fileName();
            
            val generatedPath = baseDir + folderName + "/";
            val generatedCode = elmChoice.toElmCode();
            val generatedName = generatedPath + fileName;
            
            generateElmCode(generatedPath, generatedCode, generatedName);
        } catch (Exception e) {
            error(element, "Problem generating the class: "
                    + packageName + "." + specTargetName
                    + ": "  + e.getMessage()
                    + ":"   + e.getClass()
                    + stream(e.getStackTrace())
                        .map(st -> "\n    @" + st)
                        .collect(joining()));
        } finally {
            hasError |= choiceSpec.hasError();
        }
    }
    
}
