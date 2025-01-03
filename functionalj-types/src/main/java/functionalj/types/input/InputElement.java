// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import functionalj.types.Choice;
import functionalj.types.JavaVersionInfo;
import functionalj.types.OptionalBoolean;
import functionalj.types.Serialize;
import functionalj.types.Struct;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;

public interface InputElement {
    
    public static class Impl implements InputElement {
        
        final Environment environment;
        
        private final Element element;
        
        Impl(Environment environment, Element element) {
            this.environment = environment;
            this.element = element;
        }
        
        @Override
        public JavaVersionInfo versionInfo() {
            return environment.versionInfo;
        }
        
        public void warn(CharSequence message) {
            environment.warn(element, null, message);
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
        public ElementKind kind() {
            return element.getKind();
        }
        
        @Override
        public Set<Modifier> modifiers() {
            return element.getModifiers();
        }
        
        @Override
        public InputElement enclosingElement() {
            return environment.element(element.getEnclosingElement());
        }
        
        @Override
        public List<? extends InputElement> enclosedElements() {
            return element.getEnclosedElements().stream().map(environment::element).collect(toList());
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
        
        public String insight() {
            return "class=[" + element.getClass() + "]";
        }
        
        // == Actions ==
        @Override
        public String printElement() {
            try (StringWriter writer = new StringWriter()) {
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
            JavaFileObject sourceFile = environment.filer.createSourceFile(className, element);
            try (Writer writer = sourceFile.openWriter()) {
                writer.write(content);
            }
        }
        
        // == Sub typing ==
        @Override
        public InputTypeElement asTypeElement() {
            return (element instanceof TypeElement) ? environment.element(((TypeElement) element)) : null;
        }
        
        @Override
        public InputMethodElement asMethodElement() {
            return (element instanceof ExecutableElement) ? environment.element(((ExecutableElement) element)) : null;
        }
        
        @Override
        public InputVariableElement asVariableElement() {
            return (element instanceof VariableElement)
                    ? environment.element(((VariableElement) element))
                    : null;
        }
        
        @Override
        public InputRecordComponentElement asRecordComponentElement() {
            return element.getKind().toString().equals("RECORD_COMPONENT")
                    ? environment.recordComponentElement(element)
                    : null;
        }
        
        @Override
        public InputTypeParameterElement asTypeParameterElement() {
            return (element instanceof TypeParameterElement) ? environment.element(((TypeParameterElement) element)) : null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static abstract class Mock implements InputElement {
        
        private final JavaVersionInfo versionInfo;
        
        private final String simpleName;
        
        private final String packageQualifiedName;
        
        private final ElementKind kind;
        
        private final Set<Modifier> modifiers;
        
        private final InputElement enclosingElement;
        
        private final Supplier<List<InputElement>> enclosedElementsSupplier;
        
        private final Function<Class, Annotation> annotations;
        
        private final InputType asType;
        
        private final String printElement;
        
        private final String toString;
        
        private final List<String> logs = new ArrayList<>();
        
        private final List<String> code = new ArrayList<>();
        
        private AtomicReference<List<InputElement>> enclosedElements = new AtomicReference<>(null);
        
        public Mock(
                JavaVersionInfo              versionInfo,
                String                       simpleName,
                String                       packageQualifiedName, 
                ElementKind                  kind, 
                Set<Modifier>                modifiers, 
                InputElement                 enclosingElement, 
                Supplier<List<InputElement>> enclosedElementsSupplier, 
                Function<Class, Annotation>  annotations, 
                InputType                    asType, 
                String                       printElement, 
                String                       toString) {
            this.versionInfo              = versionInfo;
            this.simpleName               = simpleName;
            this.packageQualifiedName     = packageQualifiedName;
            this.kind                     = kind;
            this.modifiers                = modifiers;
            this.enclosingElement         = enclosingElement;
            this.enclosedElementsSupplier = enclosedElementsSupplier;
            this.annotations              = annotations;
            this.asType                   = asType;
            this.printElement             = printElement;
            this.toString                 = toString;
        }
        
        @Override
        public JavaVersionInfo versionInfo() {
            return versionInfo;
        }
        
        @Override
        public String simpleName() {
            return simpleName;
        }
        
        @Override
        public String packageQualifiedName() {
            return packageQualifiedName;
        }
        
        @Override
        public ElementKind kind() {
            return kind;
        }
        
        @Override
        public Set<Modifier> modifiers() {
            return modifiers;
        }
        
        @Override
        public InputElement enclosingElement() {
            return enclosingElement;
        }
        
        @Override
        public List<? extends InputElement> enclosedElements() {
            return enclosedElements.updateAndGet(old -> {
                if (old != null)
                    return old;
                return enclosedElementsSupplier.get();
            });
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <A extends Annotation> A annotation(Class<A> annotationType) {
            return (A) annotations.apply(annotationType);
        }
        
        @Override
        public InputType asType() {
            return asType;
        }
        
        @Override
        public String printElement() {
            return printElement;
        }
        
        @Override
        public String getToString() {
            return toString;
        }
        
        @Override
        public void error(String msg) {
            logs.add("ERROR: " + msg);
        }
        
        @Override
        public void warn(String msg) {
            logs.add("WARN: " + msg);
        }
        
        @Override
        public boolean hasError() {
            return logs.stream().filter(s -> s.startsWith("ERROR: ")).findFirst().isPresent();
        }
        
        @Override
        public void generateCode(String className, String content) throws IOException {
            code.add("-|" + className + "|-----------------");
            for (String line : content.split("\n")) {
                code.add(line);
            }
            code.add("-------------------------------------");
        }
        
        @Override
        public String toString() {
            return toString;
        }
        
        // == Builder ==
        public static abstract class Builder {
            
            protected JavaVersionInfo versionInfo;
            
            protected String simpleName;
            
            protected String packageQualifiedName;
            
            protected ElementKind kind;
            
            protected Set<Modifier> modifiers;
            
            protected InputElement enclosingElement;
            
            protected Supplier<List<InputElement>> enclosedElementsSupplier;
            
            protected Function<Class, Annotation> annotations = __ -> null;
            
            protected InputType asType;
            
            protected String printElement;
            
            protected String toString;
            
            public Builder simpleName(String simpleName) {
                this.simpleName = simpleName;
                return this;
            }
            
            public Builder packageQualifiedName(String packageQualifiedName) {
                this.packageQualifiedName = packageQualifiedName;
                return this;
            }
            
            public Builder kind(ElementKind kind) {
                this.kind = kind;
                return this;
            }
            
            public Builder modifiers(Modifier... modifiers) {
                return modifiers(new HashSet<>(asList(modifiers)));
            }
            
            public Builder modifiers(Set<Modifier> modifiers) {
                this.modifiers = modifiers;
                return this;
            }
            
            public Builder enclosingElement(InputElement enclosingElement) {
                this.enclosingElement = enclosingElement;
                return this;
            }
            
            public Builder enclosedElements(InputElement... enclosedElements) {
                return enclosedElements(() -> asList(enclosedElements));
            }
            
            public Builder enclosedElements(List<InputElement> enclosedElements) {
                return enclosedElements(() -> enclosedElements);
            }
            
            public Builder enclosedElements(Supplier<List<InputElement>> enclosedElementsSupplier) {
                this.enclosedElementsSupplier = enclosedElementsSupplier;
                return this;
            }
            
            public Builder annotations(Class clzz, Annotation annotation) {
                Function<Class, Annotation> oldAnnotations = annotations;
                annotations = czz -> {
                    if (czz.equals(clzz)) {
                        return annotation;
                    }
                    return oldAnnotations.apply(clzz);
                };
                return this;
            }
            
            public Builder annotations(Function<Class, Annotation> annotations) {
                this.annotations = annotations;
                return this;
            }
            
            public Builder asType(InputType asType) {
                this.asType = asType;
                return this;
            }
            
            public Builder printElement(String printElement) {
                this.printElement = printElement;
                return this;
            }
            
            public Builder toString(String toString) {
                this.toString = toString;
                return this;
            }
        }
    }
    
    // == Fundamental methods ==
    
    public JavaVersionInfo versionInfo();
    
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
     * &lt;p&gt; A generic element defines a family of types, not just one.
     * If this is a generic element, a &lt;i&gt;prototypical&lt;/i&gt; type is
     * returned.  This is the element's invocation on the
     * type variables corresponding to its own formal type parameters.
     * For example,
     * for the generic class element {@code C&lt;N extends Number&gt;},
     * the parameterized type {@code C&lt;N&gt;} is returned.
     * The {@link Types} utility interface has more general methods
     * for obtaining the full range of types defined by an element.
     *
     * @see Types
     *
     * @return the type defined by this element
     */
    public InputType asType();
    
    // == Action ==
    
    public String printElement();
    
    public String getToString();
    
    public void error(String msg);
    
    public void warn(String msg);
    
    public boolean hasError();
    
    public void generateCode(String className, String content) throws IOException;
    
    // == Sub typing ==
    
    public default InputTypeElement asTypeElement() {
        return null;
    }
    
    public default InputMethodElement asMethodElement() {
        return null;
    }
    
    public default InputVariableElement asVariableElement() {
        return null;
    }
    
    public default InputRecordComponentElement asRecordComponentElement() {
        return null;
    }
    
    public default InputTypeParameterElement asTypeParameterElement() {
        return null;
    }
    
    // == Derived methods ==
    public default String packageName() {
        if (isTypeElement())
            return packageQualifiedName();
        if (isMethodElement())
            return asMethodElement().enclosingElement().packageQualifiedName();
        throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
    }
    
    public default boolean isStructOrChoice() {
        return (annotation(Struct.class) != null) || (annotation(Choice.class) != null);
    }
    
    public default boolean isInterface() {
        return ElementKind.INTERFACE.equals(kind());
    }
    
    public default boolean isClass() {
        return ElementKind.CLASS.equals(kind());
    }
    
    public default boolean isRecord() {
        return "RECORD".equals(kind().toString());
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
    
    // == Sub typing ==
    public default boolean isTypeElement() {
        return asTypeElement() != null;
    }
    
    public default boolean isMethodElement() {
        return asMethodElement() != null;
    }
    
    public default boolean isVariableElement() {
        return asVariableElement() != null;
    }
    
    public default boolean isRecordComponentElement() {
        return asRecordComponentElement() != null;
    }
    
    public default boolean isTypeParameterElement() {
        return asTypeParameterElement() != null;
    }
    
    // == From annotation ==
    public default String sourceName() {
        if (isTypeElement()) {
            String packageName = packageName();
            return packageQualifiedName().substring(packageName.length() + 1);
        }
        if (isMethodElement())
            return null;
        throw new IllegalArgumentException("Struct and Choice annotation is only support class or method.");
    }
    
    public default String targetName() {
        return targetName(this);
    }
    
    public default String targetName(InputElement element) {
        String specTargetName = specifiedTargetName();
        String simpleName = element.simpleName().toString();
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
            String specField = annotation(Struct.class).specField();
            return blankToNull(specField);
        }
        if (annotation(Choice.class) != null) {
            String specField = annotation(Choice.class).specField();
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
            String tagMapKeyName = annotation(Choice.class).tagMapKeyName();
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
    
    public default boolean generateSealedClass() {
        if (annotation(Choice.class) != null) {
            OptionalBoolean generateSealedClass = annotation(Choice.class).generateSealedClass();
            int             version             = versionInfo().minVersion();
            if ((generateSealedClass == OptionalBoolean.TRUE)
             || (generateSealedClass == OptionalBoolean.FALSE)) {
                
                if ((generateSealedClass == OptionalBoolean.TRUE)
                 && (version < 17)) {
                    error("Sealed class can only be generated for Java 17 or newer.");
                }
                
                return generateSealedClass.toBoolean();
            }
            
            return version >= 17;
        }
        throw new IllegalArgumentException("Unknown element annotation type: " + this);
    }
    
    public default List<String> readLocalTypeWithLens() {
        return enclosingElement().enclosedElements().stream().filter(elmt -> elmt.isStructOrChoice()).map(elmt -> targetName(elmt)).filter(name -> nonNull(name)).collect(toList());
    }
}
