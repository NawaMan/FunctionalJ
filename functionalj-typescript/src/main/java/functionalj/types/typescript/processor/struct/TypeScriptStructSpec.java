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
package functionalj.types.typescript.processor.struct;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import functionalj.types.input.InputElement;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.typescript.TypeScript;
import functionalj.types.typescript.processor.Utils;

/**
 * This class represents The spec for a TypeScript structure type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class TypeScriptStructSpec {
    
    private final SourceSpec sourceSpec;
    
    private final String typeName;
    
    private final String folderName;
    
    private final String generatedDirectory;
    
    /** Constructs the spec. */
    public TypeScriptStructSpec(SourceSpec sourceSpec, InputElement element) {
        this.sourceSpec = sourceSpec;
        this.typeName   = sourceSpec.getTargetClassName();
        
        List<String> baseModule = asList(elmBaseModule(element, sourceSpec).split("\\."));
        this.folderName         = baseModule.stream().map(Utils::toTitleCase).collect(joining("/"));
        
        String generatedDirectory = element.annotation(TypeScript.class).generatedDirectory();
        this.generatedDirectory   = (generatedDirectory == null) ? TypeScript.DEFAULT_GENERATED_DIRECTORY : generatedDirectory;
    }
    
    TypeScriptStructSpec(SourceSpec sourceSpec, String typeName, String folderName, String generatedDirectory) {
        this.sourceSpec         = sourceSpec;
        this.typeName           = typeName;
        this.folderName         = folderName;
        this.generatedDirectory = (generatedDirectory == null) ? TypeScript.DEFAULT_GENERATED_DIRECTORY : generatedDirectory;
    }
    
    private String elmBaseModule(InputElement element, SourceSpec sourceSpec) {
        String baseModule = element.annotation(TypeScript.class).baseModule();
        String tsPackage  = sourceSpec.getPackageName();
        return (TypeScript.FROM_PACAKGE_NAME.equals(baseModule)) ? tsPackage : baseModule;
    }
    
    /** @return the source spec. **/
    public SourceSpec sourceSpec() {
        return sourceSpec;
    }
    
    /** @return the type name. **/
    public String typeName() {
        return typeName;
    }
    
    /** @return the file name. **/
    public String fileName() {
        return typeName + ".ts";
    }
    
    /** @return the module name. **/
    public String moduleName() {
        String moduleBase = ((folderName == null) || folderName.isEmpty()) ? "" : (folderName.replace('/', '.') + ".");
        return moduleBase + typeName;
    }
    
    /** @return the folder name. **/
    public String folderName() {
        return folderName;
    }
    
    /** @return the generated directory. **/
    public String generatedDirectory() {
        return generatedDirectory;
    }
    
    @Override
    public String toString() {
        String fileName   = fileName();
        String moduleName = moduleName();
        return "TypeScriptStructSpec ["
                + "typeName=" + typeName + ", " 
                + "fileName=" + fileName + ", " 
                + "moduleName=" + moduleName + ", " 
                + "folderName=" + folderName + ", " 
                + "generatedDirectory=" + generatedDirectory 
                + "]";
    }
}
