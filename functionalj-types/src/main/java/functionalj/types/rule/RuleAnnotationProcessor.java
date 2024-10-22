// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import functionalj.types.IRule;
import functionalj.types.JavaVersionInfo;
import functionalj.types.Rule;
import functionalj.types.VersionUtils;
import functionalj.types.input.Environment;
import functionalj.types.input.InputDeclaredType;
import functionalj.types.input.InputElement;
import functionalj.types.input.InputMethodElement;
import functionalj.types.input.InputType;
import functionalj.types.input.InputTypeArgument;
import functionalj.types.input.InputTypeElement;
import functionalj.types.rule.RuleSpec.RuleType;
import javax.annotation.processing.*;

public class RuleAnnotationProcessor extends AbstractProcessor {
    
    private Environment environment = null;
    
    private boolean hasError;
    
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        Elements        elementUtils = processingEnv.getElementUtils();
        Types           types        = processingEnv.getTypeUtils();
        Filer           filer        = processingEnv.getFiler();
        Messager        messager     = processingEnv.getMessager();
        JavaVersionInfo versionInfo  = VersionUtils.getJavaVersionInfo(processingEnv);
        environment = new Environment(versionInfo, elementUtils, types, messager, filer);
    }
    
    private final List<String> logs = new ArrayList<>();
    
    
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Rule.class.getCanonicalName());
        return annotations;
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        hasError = false;
        List<InputElement> elements = roundEnv.getElementsAnnotatedWith(Rule.class).stream().map(environment::element).collect(toList());
        for (InputElement element : elements) {
            prepareLogs(element);
            
            InputMethodElement method = element.asMethodElement();
            Rule               rule   = method.annotation(Rule.class);
            String             msg    = rule.value();
            boolean            hasMsg = (msg != null) && !"".equals(msg);
            boolean            isBool = method.returnType().isPrimitiveType() && "boolean".equals(method.returnType().asPrimitiveType().primitiveName());
            if (!isBool && hasMsg) {
                method.warn("The error message is only used with a boolean checker.");
            }
            RuleType ruleType = getRuleType(method.returnType());
            if (ruleType == null) {
                method.error("Invalid return type: only boolean, String and functionalj.result.ValidationException is allowed.");
                continue;
            }
            if (method.parameters().size() != 1) {
                method.error("Rule spec method MUST have one parameter.");
                continue;
            }
            String   targetName     = method.simpleName().toString();
            String   enclosingClass = method.enclosingElement().simpleName();
            String   packageName    = method.packageQualifiedName();
            String   superType      = getSuperType(method);
            String   dataName       = getDataName(method);
            String   dataType       = getDataType(method);
            String   errorMsg       = isBool ? msg : null;
            RuleSpec spec           = new RuleSpec(targetName, enclosingClass, packageName, superType, dataName, dataType, errorMsg, ruleType);
            String   className      = packageName + "." + targetName;
            try {
                String content = spec.toCode();
                element.generateCode(className, content);
            } catch (Exception exception) {
                String template   = "Problem generating the class: %s: %s:%s @ %s";
                String excMsg     = exception.getMessage();
                Class  excClass   = exception.getClass();
                String stacktrace = stream(exception.getStackTrace()).map(st -> "\n    @" + st).collect(joining());
                String errMsg     = format(template, className, excMsg, excClass, stacktrace);
                exception.printStackTrace(System.err);
                element.error(errMsg);
            } finally {
                hasError |= element.hasError();
            }
        }
        return hasError;
    }
    
    private String getSuperType(InputMethodElement method) {
        Rule rule = method.annotation(Rule.class);
        if (rule == null)
            return null;
        String clzz = rule.extendRule();
        if (clzz == null)
            return null;
        return (clzz.trim().isEmpty() || clzz.equals(IRule.class.getCanonicalName())) ? null : clzz;
    }
    
    private String getDataName(InputMethodElement method) {
        String name = method.parameters().get(0).simpleName().toString();
        return name;
    }
    
    private String getDataType(InputMethodElement method) {
        InputType type = method.parameters().get(0).asType();
        if (type.isPrimitiveType())
            return type.asPrimitiveType().primitiveName();
        if (type.isDeclaredType()) {
            InputTypeElement typeElement = type.asDeclaredType().asTypeElement();
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
            InputTypeElement typeElement = returnType.asDeclaredType().asTypeElement();
            String           fullName    = typeElement.qualifiedName();
            if ("java.lang.String".equals(fullName))
                return RuleType.ErrMsg;
            if ("functionalj.result.ValidationException".equals(fullName))
                return RuleType.Func;
        }
        return null;
    }
    
    @SuppressWarnings("unused")
    private void prepareLogs(InputElement element) {
        if (element.isTypeElement()) {
            logs.add("Element is a type: " + element);
            return;
        }
        InputMethodElement method = element.asMethodElement();
        for (InputElement parameter : method.parameters()) {
            logs.add("  - Parameter [" + parameter.simpleName() + "] under version    : " + environment.versionInfo());
            logs.add("  - Parameter [" + parameter.simpleName() + "] is a type element: " + parameter.isTypeElement());
            logs.add("  - Parameter [" + parameter.simpleName() + "] toString         : " + parameter);
            logs.add("  - Parameter [" + parameter.simpleName() + "] simple name      : " + parameter.simpleName());
            logs.add("  - Parameter [" + parameter.simpleName() + "] asType.toString  : " + parameter.asType());
            logs.add("  - Parameter [" + parameter.simpleName() + "] asType.kind      : " + parameter.asType().typeKind());
            logs.add("  - Parameter [" + parameter.simpleName() + "] asType.class     : " + ((InputType.Impl) parameter.asType()).insight());
            if (parameter.asType().isDeclaredType()) {
                InputDeclaredType type = parameter.asType().asDeclaredType();
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement                     : " + type.asTypeElement());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.simpleName          : " + type.asTypeElement().simpleName());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.packageQualifiedName: " + type.asTypeElement().packageQualifiedName());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.kind                : " + type.asTypeElement().kind());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.modifiers           : " + type.asTypeElement().modifiers());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.enclosingElement    : " + type.asTypeElement().enclosingElement());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.enclosedElements    : " + type.asTypeElement().enclosedElements());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.qualifiedName       : " + type.asTypeElement().qualifiedName());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.packageName         : " + type.asTypeElement().packageName());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.asType              : " + type.asTypeElement().asType());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.asTypeElement.getToString         : " + type.asTypeElement().getToString());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.isDeclaredType                    : " + type.isDeclaredType());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.isNoType                          : " + type.isNoType());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.typeKind                          : " + type.typeKind());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.getToString                       : " + type.getToString());
                logs.add("  - Parameter [" + parameter.simpleName() + "] asType.typeArguments                     : " + type.typeArguments());
                for (int i = 0; i < type.typeArguments().size(); i++) {
                    InputTypeArgument inputType = type.typeArguments().get(i);
                    if (inputType instanceof InputType.Impl) {
                        logs.add("  - Parameter [" + parameter.simpleName() + "] asType.typeArguments[" + i + "]               : " + ((InputType.Impl) inputType).insight() + ": " + inputType.getClass());
                    } else {
                        logs.add("  - Parameter [" + parameter.simpleName() + "] asType.typeArguments[" + i + "]: inputType=   : " + inputType.getClass());
                    }
                }
                logs.add("------------------------------------------------");
            }
        }
    }
}
