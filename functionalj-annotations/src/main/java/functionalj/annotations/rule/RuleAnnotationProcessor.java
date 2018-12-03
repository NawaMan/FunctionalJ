package functionalj.annotations.rule;

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
import javax.tools.Diagnostic;

import functionalj.annotations.Rule;
import functionalj.annotations.rule.RuleSpec.RuleType;
import lombok.val;

public class RuleAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Filer    filer;
    private Messager messager;
    private boolean  hasError;
    
    @SuppressWarnings("unused")
    private List<String> logs = new ArrayList<String>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        elementUtils = processingEnv.getElementUtils();
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
            val method = (ExecutableElement)element;
            val rule   = method.getAnnotation(Rule.class);
            val msg    = rule.value();
            val hasMsg = (msg != null) && !"".equals(msg);
            val isBool = (method.getReturnType() instanceof PrimitiveType)
                      && "boolean".equals(((PrimitiveType)method.getReturnType()).toString());
            
            if (!isBool && hasMsg) {
                warn(method, "The error message is only used with a boolean checker.");
            }
            
            val ruleType = getRuleType(method.getReturnType());
            if (ruleType == null) {
                error(method, "Invalid return type: only boolean, String and functionalj.result.ValidationException is allowed.");
                continue;
            }
            if (method.getParameters().size() != 1) {
                error(method, "Rule spec method MUST have one parameter.");
                continue;
            }
            
            val targetName     = method.getSimpleName().toString();
            val enclosingClass = method.getEnclosingElement().getSimpleName().toString();
            val packageName    = elementUtils.getPackageOf(method).getQualifiedName().toString();
            val dataType       = getDataType(method);
            val errorMsg       = isBool ? msg : null;
            val spec           = new RuleSpec(targetName, enclosingClass, packageName, dataType, errorMsg, ruleType);
            
            try {
                val className  = packageName + "." + targetName;
                val content    = /*"// " + spec.toString() + "\n" 
                               + "// " + logs.toString() + "\n" 
                               + */
                               spec.toCode();
                val logString  = "";//"\n" + logs.stream().map("// "::concat).collect(joining("\n"));
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
    
    private String getDataType(ExecutableElement method) {
        val type = method.getParameters().get(0).asType();
        if (type instanceof PrimitiveType)
            return type.toString();
        
        if (type instanceof DeclaredType) {
            val typeElement = ((TypeElement)((DeclaredType)type).asElement());
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
            val typeElement = ((TypeElement)((DeclaredType)returnType).asElement());
            val fullName    = typeElement.getQualifiedName().toString();
            if ("java.lang.String".equals(fullName))
                return RuleType.ErrMsg;
            if ("functionalj.result.ValidationException".equals(fullName))
                return RuleType.Func;
        }
        return null;
    }
}
