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
package functionalj.types.input;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import functionalj.types.JavaVersionInfo;

public class Environment {
    
    final JavaVersionInfo versionInfo;
    
    final Elements elementUtils;
    
    final Types typeUtils;
    
    final Messager messager;
    
    final Filer filer;
    
    private final AtomicBoolean hasError = new AtomicBoolean(false);
    
    public Environment(JavaVersionInfo versionInfo, Elements elementUtils, Types typeUtils, Messager messager, Filer filer) {
        this.versionInfo  = versionInfo;
        this.elementUtils = elementUtils;
        this.typeUtils    = typeUtils;
        this.messager     = messager;
        this.filer        = filer;
    }
    
    public JavaVersionInfo versionInfo() {
        return versionInfo;
    }
    
    public void warn(Element e, AnnotationMirror a, CharSequence message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message, e, a);
    }
    
    public void markHasError() {
        hasError.set(true);
    }
    
    public boolean hasError() {
        return hasError.get();
    }
    
    public InputElement element(Element element) {
        return new InputElement.Impl(this, element);
    }
    
    public InputMethodElement element(ExecutableElement element) {
        return new InputMethodElement.Impl(this, element);
    }
    
    public InputTypeElement element(TypeElement element) {
        return new InputTypeElement.Impl(this, element);
    }
    
    public InputVariableElement element(VariableElement element) {
        return new InputVariableElement.Impl(this, element);
    }
    
    public InputTypeParameterElement element(TypeParameterElement element) {
        return new InputTypeParameterElement.Impl(this, element);
    }
    
    public InputRecordComponentElement recordComponentElement(Element element) {
        return new InputRecordComponentElement.Impl(this, element);
    }
}
