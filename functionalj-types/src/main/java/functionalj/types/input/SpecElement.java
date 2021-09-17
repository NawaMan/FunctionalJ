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
package functionalj.types.input;

import static functionalj.types.Utils.blankToNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public interface SpecElement {
    
    public static SpecElement of(Environment environment, Element element) {
        return new Impl(environment, element);
    }
    
    public static class Impl implements SpecElement {
        
        final Environment environment;
        final Element     element;
        
        Impl(Environment environment, Element element) {
            this.environment = environment;
            this.element     = element;
        }
        
        @Override
        public String packageName() {
            if (isTypeElement())
                return packageQualifiedName();
            
            if (isMethodElement())
                return asMethodElement().enclosingElement().packageQualifiedName();
            
            throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
        }
        
        @Override
        public String simpleName() {
            return element.getSimpleName().toString();
        }
        
        @Override
        public String packageQualifiedName() {
            return environment.elementUtils.getPackageOf(element).getQualifiedName().toString();
        }
        
        @Override
        public ElementKind getKind() {
            return element.getKind();
        }
        
        @Override
        public boolean isStructOrChoise() {
            return (element.getAnnotation(Struct.class) != null)
                || (element.getAnnotation(Choice.class) != null);
        }
        
        @Override
        public boolean isInterface() {
            return ElementKind.INTERFACE.equals(element.getKind());
        }
        
        @Override
        public boolean isClass() {
            return ElementKind.CLASS.equals(element.getKind());
        }
        
        @Override
        public boolean isStatic() {
            return element.getModifiers().contains(Modifier.STATIC);
        }
        
        @Override
        public boolean isPublic() {
            return element.getModifiers().contains(Modifier.PUBLIC);
        }
        
        @Override
        public boolean isPrivate() {
            return element.getModifiers().contains(Modifier.PRIVATE);
        }
        
        @Override
        public boolean isProtected() {
            return element.getModifiers().contains(Modifier.PROTECTED);
        }
        
        @Override
        public Accessibility accessibility() {
            if (element.getModifiers().contains(Modifier.PRIVATE))
                return Accessibility.PRIVATE;
            if (element.getModifiers().contains(Modifier.DEFAULT))
                return Accessibility.PACKAGE;
            if (element.getModifiers().contains(Modifier.PROTECTED))
                return Accessibility.PROTECTED;
            if (element.getModifiers().contains(Modifier.PUBLIC))
                return Accessibility.PUBLIC;
            return Accessibility.PACKAGE;
        }
        
        @Override
        public Scope scope() {
            return element.getModifiers().contains(Modifier.STATIC) ? Scope.STATIC : Scope.INSTANCE;
        }
        
        @Override
        public Modifiability modifiability() {
            return element.getModifiers().contains(Modifier.FINAL) ? Modifiability.FINAL : Modifiability.MODIFIABLE;
        }
        
        @Override
        public Concrecity concrecity() {
            return element.getModifiers().contains(Modifier.ABSTRACT) ? Concrecity.ABSTRACT : Concrecity.CONCRETE;
        }
        
        @Override
        public SpecElement enclosingElement() {
            return SpecElement.of(environment, element.getEnclosingElement());
        }
        
        @Override
        public List<? extends SpecElement> enclosedElements() {
            return element
                    .getEnclosedElements().stream()
                    .map    (element -> SpecElement.of(environment, element))
                    .collect(toList());
        }
        
        @Override
        public <A extends Annotation> A annotation(Class<A> annotationType) {
            return element.getAnnotation(annotationType);
        }
        
        @Override
        public String printElement() {
            try (val writer = new StringWriter()) {
                environment.elementUtils.printElements(writer, element);
                return writer.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        
        @Override
        public String getToString() {
            return element.toString();
        }
        
        @Override
        public String toString() {
            return element.toString();
        }
        
        @Override
        public void error(String msg) {
            environment.messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
            environment.markHasError();
        }
        
        @Override
        public void warn(String msg) {
            environment.messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
        }
        
        @Override
        public void generateCode(String className, String content) throws IOException {
            try (Writer writer = environment.filer.createSourceFile(className, element).openWriter()) {
                writer.write(content);
            }
        }
        
        //== Sub typing ==
        
        @Override
        public boolean isTypeElement() {
            return (element instanceof TypeElement);
        }
        
        @Override
        public SpecTypeElement asTypeElement() {
            return isTypeElement() 
                    ? SpecTypeElement.of(environment, ((TypeElement)element)) 
                    : null;
        }
        
        @Override
        public boolean isMethodElement() {
            return (element instanceof ExecutableElement);
        }
        
        @Override
        public SpecMethodElement asMethodElement() {
            return isMethodElement() 
                    ? SpecMethodElement.of(environment, ((ExecutableElement)element)) 
                    : null;
        }
        
        //== From annotation ==
        
        @Override
        public String sourceName() {
            if (isTypeElement()) {
                val packageName = packageName();
                return packageQualifiedName().substring(packageName.length() + 1 );
            }
            
            if (isMethodElement())
                return null;
            
            throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
        }
        
        @Override
        public String targetName() {
            return targetName(this);
        }
        
        private String targetName(SpecElement element) {
            val specifiedTargetName = specifiedTargetName();
            val simpleName          = element.simpleName().toString();
            return extractTargetName(simpleName, specifiedTargetName);
        }
        
        public String extractTargetName(String simpleName, String specTargetName) {
            if ((specTargetName != null) && !specTargetName.isEmpty())
                return specTargetName;
            
            if (simpleName.matches("^.*Spec$"))
                return simpleName.replaceAll("Spec$", "");
            
            if (simpleName.matches("^.*Model$"))
                return simpleName.replaceAll("Model$", "");
            
            return simpleName;
        }
        
        @Override
        public String specifiedTargetName() {
            if (annotation(Struct.class) != null) {
                return blankToNull(element.getAnnotation(Struct.class).name());
                
            }
            if (annotation(Choice.class) != null) {
                return blankToNull(element.getAnnotation(Choice.class).name());
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public String specifiedSpecField() {
            if (annotation(Struct.class) != null) {
                val specField = annotation(Struct.class).specField();
                return blankToNull(specField);
            }
            if (annotation(Choice.class) != null) {
                val specField = element.getAnnotation(Choice.class).specField();
                return blankToNull(specField);
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public Serialize.To specifiedSerialize() {
            if (annotation(Struct.class) != null) {
                return annotation(Struct.class).serialize();
            }
            if (annotation(Choice.class) != null) {
                return annotation(Choice.class).serialize();
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public String choiceTagMapKeyName() {
            if (annotation(Choice.class) != null) {
                val tagMapKeyName = element.getAnnotation(Choice.class).tagMapKeyName();
                return blankToNull(tagMapKeyName);
            } else {
                return null;
            }
        }
        
        @Override
        public boolean specifiedPublicField() {
            if (annotation(Struct.class) != null) {
                return annotation(Struct.class).publicFields();
            }
            if (annotation(Choice.class) != null) {
                return annotation(Choice.class).publicFields();
            }
            throw new IllegalArgumentException("Unknown element annotation type: " + element);
        }
        
        @Override
        public List<String> readLocalTypeWithLens() {
            return enclosingElement()
                    .enclosedElements().stream()
                    .filter (elmt -> elmt.isStructOrChoise())
                    .map    (elmt -> targetName(elmt))
                    .filter (name -> nonNull(name))
                    .collect(toList());
        }
        
    }
    
    public String packageName();
    
    public String simpleName();
    
    public String packageQualifiedName();
    
    public ElementKind getKind();
    
    public boolean isStructOrChoise();
    
    public boolean isInterface();
    
    public boolean isClass();
    
    public boolean isStatic();
    
    public boolean isPublic();
    
    public boolean isPrivate();
    
    public boolean isProtected();
    
    public Accessibility accessibility();
    
    public Scope scope();
    
    public Modifiability modifiability();
    
    public Concrecity concrecity();
    
    public SpecElement enclosingElement();
    
    public List<? extends SpecElement> enclosedElements();
    
    public <A extends Annotation> A annotation(Class<A> annotationType);
    
    public String printElement();
    
    public String getToString();
    
    public void error(String msg);
    
    public void warn(String msg);
    
    public void generateCode(String className, String content) throws IOException;
    
    //== Sub typing ==
    
    public boolean isTypeElement();
    
    public SpecTypeElement asTypeElement();
    
    public boolean isMethodElement();
    
    public SpecMethodElement asMethodElement();
    
    //== From annotation ==
    
    public String sourceName();
    
    public String targetName();
    
    public String specifiedTargetName();
    
    public String specifiedSpecField();
    
    public Serialize.To specifiedSerialize();
    
    public String choiceTagMapKeyName();
    
    public boolean specifiedPublicField();
    
    public List<String> readLocalTypeWithLens();
    
}
