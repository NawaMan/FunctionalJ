// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import functionalj.types.IRule;
import functionalj.types.Rule;
import functionalj.types.input.Environment;
import functionalj.types.input.InputMethodElement;
import functionalj.types.input.InputType;
import functionalj.types.rule.RuleSpec.RuleType;
import lombok.val;

public class RuleAnnotationProcessor extends AbstractProcessor {
    
    private Environment environment = null;
    
    private boolean  hasError;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        val elementUtils = processingEnv.getElementUtils();
        val types        = processingEnv.getTypeUtils();
        val filer        = processingEnv.getFiler();
        val messager     = processingEnv.getMessager();
        environment = new Environment(elementUtils, types, messager, filer);
    }
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        val annotations = new LinkedHashSet<String>();
        annotations.add(Rule.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        hasError = false;
        val elements
                = roundEnv
                .getElementsAnnotatedWith(Rule.class).stream()
                .map    (environment::element)
                .collect(toList());
        for (val element : elements) {
            val method = element.asMethodElement();
            val rule   = method.annotation(Rule.class);
            val msg    = rule.value();
            val hasMsg = (msg != null) && !"".equals(msg);
            val isBool = method.returnType().isPrimitiveType()
                      && "boolean".equals(method.returnType().asPrimitiveType().primitiveName());
            
            if (!isBool && hasMsg) {
                method.warn("The error message is only used with a boolean checker.");
            }
            
            val ruleType = getRuleType(method.returnType());
            if (ruleType == null) {
                method.error("Invalid return type: only boolean, String and functionalj.result.ValidationException is allowed.");
                continue;
            }
            if (method.parameters().size() != 1) {
                method.error("Rule spec method MUST have one parameter.");
                continue;
            }
            
            val targetName     = method.simpleName().toString();
            val enclosingClass = method.enclosingElement().simpleName();
            val packageName    = method.packageQualifiedName();
            val superType      = getSuperType(method);
            val dataName       = getDataName(method);
            val dataType       = getDataType(method);
            val errorMsg       = isBool ? msg : null;
            val spec           = new RuleSpec(targetName, enclosingClass, packageName, superType, dataName, dataType, errorMsg, ruleType);
            val className      = packageName + "." + targetName;
            
            try {
                val content = spec.toCode();
                element.generateCode(className, content);
            } catch (Exception exception) {
                val template   = "Problem generating the class: %s: %s:%s @ %s";
                val excMsg     = exception.getMessage();
                val excClass   = exception.getClass();
                val stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
                val errMsg     = format(template, className, excMsg, excClass, stacktrace);
                
                exception.printStackTrace(System.err);
                element.error(errMsg);
            } finally {
                hasError |= element.hasError();
            }
        }
        return hasError;
    }
    
    private String getSuperType(InputMethodElement method) {
        val rule = method.annotation(Rule.class);
        if (rule == null)
            return null;
        
        val clzz = rule.extendRule();
        if (clzz == null)
            return null;
        
        return (clzz.trim().isEmpty() || clzz.equals(IRule.class.getCanonicalName())) ? null : clzz;
    }
    private String getDataName(InputMethodElement method) {
        val name = method.parameters().get(0).simpleName().toString();
        return name;
    }
    
    private String getDataType(InputMethodElement method) {
        val type = method.parameters().get(0).asType();
        if (type.isPrimitiveType())
            return type.asPrimitiveType().primitiveName();
        
        if (type.isDeclaredType()) {
            val typeElement = type.asDeclaredType().asTypeElement();
            return typeElement.qualifiedName();
        }
        method.error("The method parameter type is not supported.");
        return null;
    }
    
    private RuleType getRuleType(InputType returnType) {
        if (returnType.isPrimitiveType()) {
            if ("boolean".equals(returnType.asPrimitiveType().primitiveName()))
                return RuleType.Bool;
        }
        if (returnType.isDeclaredType()) {
            val typeElement = returnType.asDeclaredType().asTypeElement();
            val fullName    = typeElement.qualifiedName();
            if ("java.lang.String".equals(fullName))
                return RuleType.ErrMsg;
            if ("functionalj.result.ValidationException".equals(fullName))
                return RuleType.Func;
        }
        return null;
    }
    
}
