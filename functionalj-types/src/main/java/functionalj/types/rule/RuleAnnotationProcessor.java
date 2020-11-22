// ============================================================================
// Copyright (c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.rule;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.types.IRule;
import functionalj.types.Rule;
import functionalj.types.rule.RuleSpec.RuleType;
import lombok.val;

public class RuleAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
    @SuppressWarnings("unused")
    private Types    typeUtils;
    private Filer    filer;
    private Messager messager;
    private boolean  hasError;
    
    private List<String> logs = new ArrayList<String>();
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elementUtils = processingEnv.getElementUtils();
        typeUtils    = processingEnv.getTypeUtils();
        filer        = processingEnv.getFiler();
        messager     = processingEnv.getMessager();
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<String>();
        annotations.add(Rule.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    private void warn(Element e, String msg) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg, e);
    }
    
    private void error(Element e, String msg) {
        hasError = true;
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        hasError = false;
        for (Element element : roundEnv.getElementsAnnotatedWith(Rule.class)) {
            var method = (ExecutableElement)element;
            var rule   = method.getAnnotation(Rule.class);
            var msg    = rule.value();
            var hasMsg = (msg != null) && !"".equals(msg);
            var isBool = (method.getReturnType() instanceof PrimitiveType)
                      && "boolean".equals(((PrimitiveType)method.getReturnType()).toString());
            
            if (!isBool && hasMsg) {
                warn(method, "The error message is only used with a boolean checker.");
            }
            
            var ruleType = getRuleType(method.getReturnType());
            if (ruleType == null) {
                error(method, "Invalid return type: only boolean, String and functionalj.result.ValidationException is allowed.");
                continue;
            }
            if (method.getParameters().size() != 1) {
                error(method, "Rule spec method MUST have one parameter.");
                continue;
            }
            
            var targetName     = method.getSimpleName().toString();
            var enclosingClass = method.getEnclosingElement().getSimpleName().toString();
            var packageName    = elementUtils.getPackageOf(method).getQualifiedName().toString();
            var superType      = getSuperType(method);
            var dataName       = getDataName(method);
            var dataType       = getDataType(method);
            var errorMsg       = isBool ? msg : null;
            var spec           = new RuleSpec(targetName, enclosingClass, packageName, superType, dataName, dataType, errorMsg, ruleType);
            
            try {
                var className  = packageName + "." + targetName;
                var content    = "// " + spec.toString() + "\n" 
                               + "// " + logs.toString() + "\n" 
                               + 
                               spec.toCode();
                var logString  = "";//"\n" + logs.stream().map("// "::concat).collect(joining("\n"));
                generateCode(element, className, content + logString);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                error(element, "Problem generating the class: "
                                + packageName + "." + enclosingClass
                                + ": "  + e.getMessage()
                                + ":"   + e.getClass()
                                + " @ " + Stream.of(e.getStackTrace()).map(String::valueOf).collect(toList()));
            }
        }
        return hasError;
    }
    
    private void generateCode(Element element, String className, String content) throws IOException {
        try (Writer writer = filer.createSourceFile(className, element).openWriter()) {
            writer.write(content);
        }
    }
    
    private String getSuperType(ExecutableElement method) {
        var rule = method.getAnnotation(Rule.class);
        if (rule == null)
            return null;
        
        var clzz = rule.extendRule();
        if (clzz == null)
            return null;
        
        return (clzz.trim().isEmpty() || clzz.equals(IRule.class.getCanonicalName())) ? null : clzz;
    }
    private String getDataName(ExecutableElement method) {
        var name = method.getParameters().get(0).getSimpleName().toString();
        return name;
    }
    
    private String getDataType(ExecutableElement method) {
        var type = method.getParameters().get(0).asType();
        if (type instanceof PrimitiveType)
            return type.toString();
        
        if (type instanceof DeclaredType) {
            var typeElement = ((TypeElement)((DeclaredType)type).asElement());
            return typeElement.getQualifiedName().toString();
        }
        error(method, "The method parameter type is not supported.");
        return null;
    }
    
    private RuleType getRuleType(TypeMirror returnType) {
        if (returnType instanceof PrimitiveType) {
            if ("boolean".equals(((PrimitiveType)returnType).toString()))
                return RuleType.Bool;
        }
        if (returnType instanceof DeclaredType) {
            var typeElement = ((TypeElement)((DeclaredType)returnType).asElement());
            var fullName    = typeElement.getQualifiedName().toString();
            if ("java.lang.String".equals(fullName))
                return RuleType.ErrMsg;
            if ("functionalj.result.ValidationException".equals(fullName))
                return RuleType.Func;
        }
        return null;
    }
}
