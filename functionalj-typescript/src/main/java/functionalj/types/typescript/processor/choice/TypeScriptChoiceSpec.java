package functionalj.types.typescript.processor.choice;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.input.InputElement;
import functionalj.types.typescript.TypeScript;
import functionalj.types.typescript.processor.Utils;

/**
 * This class represents The spec for a TypeScript choice type.
 */
public class TypeScriptChoiceSpec {
    
    private final SourceSpec sourceSpec;
    
    private final String typeName;
    
    private final String folderName;
    
    private final String generatedDirectory;
    
    public TypeScriptChoiceSpec(SourceSpec sourceSpec, InputElement element) {
        this.sourceSpec = sourceSpec;
        this.typeName = sourceSpec.targetName;
        
        List<String> baseModule = asList(elmBaseModule(element, sourceSpec).split("\\."));
        this.folderName = baseModule.stream().map(Utils::toTitleCase).collect(joining("/"));
        
        String generatedDirectory = element.annotation(TypeScript.class).generatedDirectory();
        this.generatedDirectory = (generatedDirectory == null) ? TypeScript.DEFAULT_GENERATED_DIRECTORY : generatedDirectory;
    }
    
    TypeScriptChoiceSpec(SourceSpec sourceSpec, String typeName, String folderName) {
        this(sourceSpec, typeName, folderName, null);
    }
    
    TypeScriptChoiceSpec(SourceSpec sourceSpec, String typeName, String folderName, String generatedDirectory) {
        this.sourceSpec         = sourceSpec;
        this.typeName           = typeName;
        this.folderName         = folderName;
        this.generatedDirectory = (generatedDirectory == null) ? TypeScript.DEFAULT_GENERATED_DIRECTORY : generatedDirectory;
    }
    
    private String elmBaseModule(InputElement element, SourceSpec sourceSpec) {
        String baseModule  = element.annotation(TypeScript.class).baseModule();
        String elmtPackage = sourceSpec.sourceType.packageName();
        return (TypeScript.FROM_PACAKGE_NAME.equals(baseModule)) ? elmtPackage : baseModule;
    }
    
    public SourceSpec sourceSpec() {
        return sourceSpec;
    }
    
    public String typeName() {
        return typeName;
    }
    
    public String fileName() {
        return typeName + ".ts";
    }
    
    public String moduleName() {
        String moduleBase = ((folderName == null) || folderName.isEmpty()) ? "" : (folderName.replace('/', '.') + ".");
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
        return "TypeScriptStructSpec ["
                + "typeName=" + typeName + ", "
                + "fileName=" + fileName() + ", "
                + "moduleName=" + moduleName() + ", "
                + "folderName=" + folderName + ", "
                + "generatedDirectory=" + generatedDirectory + ", "
                + "sourceSpec=" + sourceSpec
                + "]";
    }
}
