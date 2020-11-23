// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
            var struct = element.getAnnotation(Struct.class);
            if (struct != null) {
                handleStructType(element);
                continue;
            }
            
            var choice = element.getAnnotation(Choice.class);
            if (choice != null) {
                handleChoiceType(element);
                continue;
            }
            
            error(element, "The element must either be a struct or a choice.");
        }
        return hasError;
    }
    
    private void handleStructType(Element element) {
        var dummyMessager  = new DummyMessager();
        var input          = new StructSpecInputImpl(element, elementUtils, typeUtils, dummyMessager);
        var structSpec     = new StructSpec(input);
        var sourceSpec     = structSpec.sourceSpec();
        var packageName    = structSpec.packageName();
        var specTargetName = structSpec.targetTypeName();
        try {
            var elmStructSpec = new ElmStructSpec(sourceSpec, element);
            var elmStruct     = new ElmStructBuilder(elmStructSpec);
            var baseDir       = "./generated/elm/";
            var folderName    = elmStructSpec.folderName();
            var fileName      = elmStructSpec.fileName();
            
            var generatedPath = baseDir + folderName + "/";
            var generatedCode = elmStruct.toElmCode();
            var generatedName = generatedPath + fileName;
            
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
        var generatedFile = new File(generatedName);
        var lines         = asList(generatedCode.split("\n"));
        Files.write(generatedFile.toPath(), lines);
    }
    
    private void handleChoiceType(Element element) {
        var dummyMessager  = new DummyMessager();
        var input          = new ChoiceSpecInputImpl(element, elementUtils, dummyMessager);
        var choiceSpec     = new ChoiceSpec(input);
        var sourceSpec     = choiceSpec.sourceSpec();
        var packageName    = choiceSpec.packageName();
        var specTargetName = choiceSpec.targetName();
        try {
            var elmChoiceSpec = new ElmChoiceSpec(sourceSpec, element);
            var elmChoice     = new ElmChoiceBuilder(elmChoiceSpec);
            var baseDir       = "./generated/elm/";
            var folderName    = elmChoiceSpec.folderName();
            var fileName      = elmChoiceSpec.fileName();
            
            var generatedPath = baseDir + folderName + "/";
            var generatedCode = elmChoice.toElmCode();
            var generatedName = generatedPath + fileName;
            
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
