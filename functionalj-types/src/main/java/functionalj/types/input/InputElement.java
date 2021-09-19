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
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.types.Choice;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public interface InputElement {
    
    public static class Impl implements InputElement {
        
        final Environment environment;
        
        private final Element element;
        
        Impl(Environment environment, Element element) {
            this.environment = environment;
            this.element     = element;
        }
        
        @Override
        public String simpleName() {
            return element.getSimpleName().toString();
        }
        
        @Override
        public String packageQualifiedName() {
            return environment
                    .elementUtils
                    .getPackageOf(element)
                    .getQualifiedName()
                    .toString();
        }
        
        @Override
        public ElementKind kind() {
            return element.getKind();
        }
        
        @Override
        public Set<Modifier> modifiers() {
            return element.getModifiers();
        }
        
        @Override
        public InputElement enclosingElement() {
            return environment
                    .element(element.getEnclosingElement());
        }
        
        @Override
        public List<? extends InputElement> enclosedElements() {
            return element
                    .getEnclosedElements().stream()
                    .map    (environment::element)
                    .collect(toList());
        }
        
        @Override
        public <A extends Annotation> A annotation(Class<A> annotationType) {
            return element.getAnnotation(annotationType);
        }
        
        @Override
        public InputType asType() {
            return InputType.of(environment, element.asType());
        }
        
        @Override
        public String getToString() {
            return element.toString();
        }
        
        @Override
        public String toString() {
            return element.toString();
        }
        
        //== Actions ==
        
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
        public void error(String msg) {
            environment.messager.printMessage(Diagnostic.Kind.ERROR, msg, element);
            environment.markHasError();
        }
        
        @Override
        public void warn(String msg) {
            environment.messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
        }
        
        @Override
        public boolean hasError() {
            return environment.hasError();
        }
        
        @Override
        public void generateCode(String className, String content) throws IOException {
            val sourceFile = environment.filer.createSourceFile(className, element);
            try (Writer writer = sourceFile.openWriter()) {
                writer.write(content);
            }
        }
        
        //== Sub typing ==
        
        @Override
        public InputTypeElement asTypeElement() {
            return (element instanceof TypeElement)
                    ? environment.element(((TypeElement)element)) 
                    : null;
        }
        
        @Override
        public InputMethodElement asMethodElement() {
            return (element instanceof ExecutableElement)
                    ? environment.element(((ExecutableElement)element)) 
                    : null;
        }
        
    }
    
    //== Fundamental methods ==
    
    public String simpleName();
    
    public String packageQualifiedName();
    
    public ElementKind kind();
    
    public Set<Modifier> modifiers();
    
    public InputElement enclosingElement();
    
    public List<? extends InputElement> enclosedElements();
    
    public <A extends Annotation> A annotation(Class<A> annotationType);
    
    /**
     * Returns the type defined by this element.
     *
     * <p> A generic element defines a family of types, not just one.
     * If this is a generic element, a <i>prototypical</i> type is
     * returned.  This is the element's invocation on the
     * type variables corresponding to its own formal type parameters.
     * For example,
     * for the generic class element {@code C<N extends Number>},
     * the parameterized type {@code C<N>} is returned.
     * The {@link Types} utility interface has more general methods
     * for obtaining the full range of types defined by an element.
     *
     * @see Types
     *
     * @return the type defined by this element
     */
    public InputType asType();
    
    //== Action ==
    
    public String printElement();
    
    public String getToString();
    
    public void error(String msg);
    
    public void warn(String msg);
    
    public boolean hasError();
    
    public void generateCode(String className, String content) throws IOException;
    
    //== Sub typing == 
    
    public InputTypeElement asTypeElement();
    
    public InputMethodElement asMethodElement();
    
    //== Derived methods ==
    
    public default String packageName() {
        if (isTypeElement())
            return packageQualifiedName();
        
        if (isMethodElement())
            return asMethodElement().enclosingElement().packageQualifiedName();
        
        throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
    }
    
    public default boolean isStructOrChoice() {
        return (annotation(Struct.class) != null)
            || (annotation(Choice.class) != null);
    }
    
    public default boolean isInterface() {
        return ElementKind.INTERFACE.equals(kind());
    }
    
    public default boolean isClass() {
        return ElementKind.CLASS.equals(kind());
    }
    
    public default boolean isStatic() {
        return modifiers().contains(Modifier.STATIC);
    }
    
    public default boolean isPublic() {
        return modifiers().contains(Modifier.PUBLIC);
    }
    
    public default boolean isPrivate() {
        return modifiers().contains(Modifier.PRIVATE);
    }
    
    public default boolean isProtected() {
        return modifiers().contains(Modifier.PROTECTED);
    }
    
    public default Accessibility accessibility() {
        if (modifiers().contains(Modifier.PRIVATE))
            return Accessibility.PRIVATE;
        if (modifiers().contains(Modifier.DEFAULT))
            return Accessibility.PACKAGE;
        if (modifiers().contains(Modifier.PROTECTED))
            return Accessibility.PROTECTED;
        if (modifiers().contains(Modifier.PUBLIC))
            return Accessibility.PUBLIC;
        return Accessibility.PACKAGE;
    }
    
    public default Scope scope() {
        return modifiers().contains(Modifier.STATIC) ? Scope.STATIC : Scope.INSTANCE;
    }
    
    public default Modifiability modifiability() {
        return modifiers().contains(Modifier.FINAL) ? Modifiability.FINAL : Modifiability.MODIFIABLE;
    }
    
    public default Concrecity concrecity() {
        return modifiers().contains(Modifier.ABSTRACT) ? Concrecity.ABSTRACT : Concrecity.CONCRETE;
    }
    
    //== Sub typing ==
    
    public default boolean isTypeElement() {
        return asTypeElement() != null;
    }
    
    public default boolean isMethodElement() {
        return asMethodElement() != null;
    }
    
    //== From annotation ==
    
    public default String sourceName() {
        if (isTypeElement()) {
            val packageName = packageName();
            return packageQualifiedName().substring(packageName.length() + 1 );
        }
        
        if (isMethodElement())
            return null;
        
        throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
    }
    
    public default String targetName() {
        return targetName(this);
    }
    
    public default String targetName(InputElement element) {
        val specTargetName = specifiedTargetName();
        val simpleName     = element.simpleName().toString();
        if ((specTargetName != null) && !specTargetName.isEmpty())
            return specTargetName;
        
        if (simpleName.matches("^.*Spec$"))
            return simpleName.replaceAll("Spec$", "");
        
        if (simpleName.matches("^.*Model$"))
            return simpleName.replaceAll("Model$", "");
        
        return simpleName;
    }
    
    public default String specifiedTargetName() {
        if (annotation(Struct.class) != null) {
            return blankToNull(annotation(Struct.class).name());
            
        }
        if (annotation(Choice.class) != null) {
            return blankToNull(annotation(Choice.class).name());
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + this);
    }
    
    public default String specifiedSpecField() {
        if (annotation(Struct.class) != null) {
            val specField = annotation(Struct.class).specField();
            return blankToNull(specField);
        }
        if (annotation(Choice.class) != null) {
            val specField = annotation(Choice.class).specField();
            return blankToNull(specField);
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + this);
    }
    
    public default Serialize.To specifiedSerialize() {
        if (annotation(Struct.class) != null) {
            return annotation(Struct.class).serialize();
        }
        if (annotation(Choice.class) != null) {
            return annotation(Choice.class).serialize();
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + this);
    }
    
    public default String choiceTagMapKeyName() {
        if (annotation(Choice.class) != null) {
            val tagMapKeyName = annotation(Choice.class).tagMapKeyName();
            return blankToNull(tagMapKeyName);
        } else {
            return null;
        }
    }
    
    public default boolean specifiedPublicField() {
        if (annotation(Struct.class) != null) {
            return annotation(Struct.class).publicFields();
        }
        if (annotation(Choice.class) != null) {
            return annotation(Choice.class).publicFields();
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + this);
    }
    
    public default List<String> readLocalTypeWithLens() {
        return enclosingElement()
                .enclosedElements().stream()
                .filter (elmt -> elmt.isStructOrChoice())
                .map    (elmt -> targetName(elmt))
                .filter (name -> nonNull(name))
                .collect(toList());
    }
    
}
