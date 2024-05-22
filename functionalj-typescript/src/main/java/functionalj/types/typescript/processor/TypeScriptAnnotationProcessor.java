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
package functionalj.types.typescript.processor;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
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
import functionalj.types.input.Environment;
import functionalj.types.input.InputElement;
import functionalj.types.struct.SourceSpecBuilder;
import functionalj.types.typescript.TypeScript;
import functionalj.types.typescript.processor.choice.TypeScriptChoiceBuilder;
import functionalj.types.typescript.processor.choice.TypeScriptChoiceSpec;
import functionalj.types.typescript.processor.struct.TypeScriptStructBuilder;
import functionalj.types.typescript.processor.struct.TypeScriptStructSpec;

/**
 * Annotation processor for TypeScript.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class TypeScriptAnnotationProcessor extends AbstractProcessor {
    
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
        annotations.add(TypeScript.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean hasError = false;
        List<String> structTypes = collectAllStructTypes(roundEnv, Struct.class);
        List<String> choiceTypes = collectAllChoiceTypes(roundEnv, Choice.class);
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(TypeScript.class)) {
            InputElement element = environment.element(javaElement);
            System.out.println("Processing: " + element);
            Struct       struct  = element.annotation(Struct.class);
            if (struct != null) {
                hasError = hasError | !handleStructType(element, structTypes, choiceTypes);
                continue;
            }
            Choice choice = element.annotation(Choice.class);
            if (choice != null) {
                hasError = hasError | !handleChoiceType(element, structTypes, choiceTypes);
                continue;
            }
            element.error("The element must either be a struct or a choice.");
        }
        return hasError;
    }
    
    private <A extends Annotation> List<String> collectAllStructTypes(RoundEnvironment roundEnv, Class<A> clazz) {
        List<String> allTypes = new ArrayList<String>();
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(TypeScript.class)) {
            InputElement element = environment.element(javaElement);
            A annotation = element.annotation(clazz);
            if (annotation != null) {
                SourceSpecBuilder structSpec = new SourceSpecBuilder(element);
                String name = structSpec.sourceSpec().getTargetClassName();
                allTypes.add(name);
            }
        }
        return allTypes;
    }
    
    private <A extends Annotation> List<String> collectAllChoiceTypes(RoundEnvironment roundEnv, Class<A> clazz) {
        List<String> allTypes = new ArrayList<String>();
        for (Element javaElement : roundEnv.getElementsAnnotatedWith(TypeScript.class)) {
            InputElement element = environment.element(javaElement);
            A annotation = element.annotation(clazz);
            if (annotation != null) {
                ChoiceSpec choiceSpec = new ChoiceSpec(element);
                String     name       = choiceSpec.targetName();
                allTypes.add(name);
            }
        }
        return allTypes;
    }
    
    private boolean handleStructType(InputElement element, List<String> structTypes, List<String> choiceTypes) {
        SourceSpecBuilder structSpec     = new SourceSpecBuilder(element);
        String            packageName    = structSpec.packageName();
        String            specTargetName = structSpec.targetName();
        try {
            TypeScriptStructSpec    tsStructSpec  = new TypeScriptStructSpec(structSpec.sourceSpec(), element);
            TypeScriptStructBuilder tsStruct      = new TypeScriptStructBuilder(tsStructSpec, structTypes, choiceTypes);
            String                  baseDir       = tsStructSpec.generatedDirectory();
            String                  folderName    = tsStructSpec.folderName();
            String                  fileName      = tsStructSpec.fileName();
            String                  generatedPath = baseDir + folderName + "/";
            String                  generatedCode = tsStruct.toTypeScriptCode();
            String                  generatedName = generatedPath + fileName;
            generateTypeScriptCode(generatedPath, generatedCode, generatedName);
            return true;
        } catch (Throwable e) {
            element.error("Problem generating the class: " + packageName + "." + specTargetName + ": " + e.getMessage() + ":" + e.getClass() + stream(e.getStackTrace()).map(st -> "\n    @" + st).collect(joining()));
            return !element.hasError();
        }
    }
    
    private boolean handleChoiceType(InputElement element, List<String> structTypes, List<String> choiceTypes) {
        ChoiceSpec choiceSpec     = new ChoiceSpec(element);
        String     packageName    = choiceSpec.packageName();
        String     specTargetName = choiceSpec.targetName();
        try {
            TypeScriptChoiceSpec    tsChoiceSpec  = new TypeScriptChoiceSpec(choiceSpec.sourceSpec(), element);
            TypeScriptChoiceBuilder tsChoice      = new TypeScriptChoiceBuilder(tsChoiceSpec, structTypes, choiceTypes);
            String                  baseDir       = tsChoiceSpec.generatedDirectory();
            String                  folderName    = tsChoiceSpec.folderName();
            String                  fileName      = tsChoiceSpec.fileName();
            String                  generatedPath = baseDir + folderName + "/";
            String                  generatedCode = tsChoice.toTypeScriptCode();
            String                  generatedName = generatedPath + fileName;
            generateTypeScriptCode(generatedPath, generatedCode, generatedName);
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
    
    private void generateTypeScriptCode(String generatedPath, String generatedCode, String generatedName) throws IOException {
        new File(generatedPath).mkdirs();
        File         generatedFile = new File(generatedName);
        List<String> lines         = asList(generatedCode.split("\n"));
        Files.write(generatedFile.toPath(), lines);
    }
    
}