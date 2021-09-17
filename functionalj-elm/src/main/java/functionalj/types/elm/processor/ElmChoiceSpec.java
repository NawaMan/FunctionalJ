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
package functionalj.types.elm.processor;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.elm.Elm;
import functionalj.types.input.SpecElement;
import lombok.val;


public class ElmChoiceSpec {
    
    private final SourceSpec sourceSpec;
    private final String typeName;
    private final String folderName;
    private final String generatedDirectory;
    
    public ElmChoiceSpec(SourceSpec sourceSpec, SpecElement element) {
        this.sourceSpec = sourceSpec;
        this.typeName   = sourceSpec.targetName;
        
        val baseModule = asList(elmBaseModule(element, sourceSpec).split("\\."));
        this.folderName = baseModule.stream().map(Utils::toTitleCase).collect(joining("/"));
        
        val generatedDirectory  = element.annotation(Elm.class).generatedDirectory();
        this.generatedDirectory = (generatedDirectory == null) ? Elm.DEFAULT_GENERATED_DIRECTORY : generatedDirectory;
    }
    ElmChoiceSpec(SourceSpec sourceSpec, String typeName, String folderName) {
        this(sourceSpec, typeName, folderName, null);
    }
    ElmChoiceSpec(SourceSpec sourceSpec, String typeName, String folderName, String generatedDirectory) {
        this.sourceSpec         = sourceSpec;
        this.typeName           = typeName;
        this.folderName         = folderName;
        this.generatedDirectory = (generatedDirectory == null) ? Elm.DEFAULT_GENERATED_DIRECTORY : generatedDirectory;
    }
    
    private String elmBaseModule(SpecElement element, SourceSpec sourceSpec) {
        val baseModule  = element.annotation(Elm.class).baseModule();
        val elmtPackage = sourceSpec.sourceType.packageName();
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
        val moduleBase = ((folderName == null) || folderName.isEmpty()) ? "" : (folderName.replace('/', '.') + ".");
        return moduleBase + typeName;
    }
    
    public String folderName() {
        return folderName;
    }
    
    public String generatedDirectory() {
        return generatedDirectory;
    }
    
    @Override
    public String toString() {
        return "ElmStructSpec ["
                + "typeName="   + typeName     + ", "
                + "fileName="   + fileName()   + ", "
                + "moduleName=" + moduleName() + ", "
                + "folderName=" + folderName + ", "
                + "generatedDirectory=" + generatedDirectory
                + "]";
    }

}
