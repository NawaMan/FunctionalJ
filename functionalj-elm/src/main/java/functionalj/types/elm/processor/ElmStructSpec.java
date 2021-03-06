// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static java.util.stream.Collectors.joining;

import javax.lang.model.element.Element;

import functionalj.types.elm.Elm;
import functionalj.types.struct.generator.SourceSpec;
import lombok.val;

/**
 * This class represents The spec for an Elm structure type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmStructSpec {
    
    private final SourceSpec sourceSpec;
    private final String typeName;
    private final String folderName;
    
    public ElmStructSpec(SourceSpec sourceSpec, Element element) {
        this.sourceSpec = sourceSpec;
        this.typeName   = sourceSpec.getTargetClassName();
        
        val baseModule = asList(elmBaseModule(element, sourceSpec).split("\\."));
        this.folderName = baseModule.stream().map(Utils::toTitleCase).collect(joining("/"));
    }
    ElmStructSpec(SourceSpec sourceSpec, String typeName, String folderName) {
        this.sourceSpec = sourceSpec;
        this.typeName   = typeName;
        this.folderName = folderName;
    }
    
    private String elmBaseModule(Element element, SourceSpec sourceSpec) {
        val baseModule  = element.getAnnotation(Elm.class).baseModule();
        val elmtPackage = sourceSpec.getPackageName();
        return (Elm.FROM_PACAKGE_NAME.equals(baseModule)) 
                ? elmtPackage
                : baseModule;
    }
    
    public SourceSpec sourceSpec() {
        return sourceSpec;
    }
    
    public String typeName() {
        return typeName;
    }
    
    public String fileName() {
        return typeName + ".elm";
    }
    
    public String moduleName() {
        return folderName.replace('/', '.') + "." + typeName;
    }
    
    public String folderName() {
        return folderName;
    }
    
    @Override
    public String toString() {
        return "ElmStructSpec ["
                + "typeName="   + typeName     + ", "
                + "fileName="   + fileName()   + ", "
                + "moduleName=" + moduleName() + ", "
                + "folderName=" + folderName 
                + "]";
    }
    
}
