package functionalj.annotations.choice.generator;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import functionalj.annotations.choice.generator.model.SourceSpec;
import lombok.val;

public class ChoiceLensBuilder {
    
    private final SourceSpec sourceSpec;
    
    public ChoiceLensBuilder(SourceSpec sourceSpec) {
        this.sourceSpec = sourceSpec;
    }
    
    public List<String> build() {
        val targetName    = sourceSpec.targetName;
        val lensClassName = targetName + "Lens";
        val lensClassDef = asList(
                "public static final " + lensClassName + "<" + targetName + "> the" + targetName + " = new " + lensClassName + "<>(LensSpec.of(" + targetName + ".class));",
                "public static class " + lensClassName + "<HOST> extends ObjectLensImpl<HOST, " + targetName + "> {\n"
                );
        
        val lensClassConstructor = asList(
                "    public " + lensClassName + "(LensSpec<HOST, " + targetName + "> spec) {",
                "        super(spec);",
                "    }"
                );
        
        val isMethods = sourceSpec
                .choices.stream()
                .map(choice -> "BooleanAccess<" + targetName + "> is" + choice.name + " = " + targetName + "::is" + choice.name + ";")
                .map(each   -> "    public final " + each)
                .collect(toList());
        val asMethods = sourceSpec
                .choices.stream()
                .map(choice -> "ResultAccess<HOST, " + choice.name + ", " + choice.name + "."+ choice.name + "Lens<HOST>> as" + choice.name + " = createSubResultLens(" + targetName +"::as" + choice.name + ", null, " + choice.name + "."+ choice.name + "Lens::new);")
                .map(each   -> "    public final " + each)
                .collect(toList());
        
        val lensClassDefClose = asList(
                "}"
                );
        
        return asList(
                lensClassDef,
                isMethods,
                asMethods,
                lensClassConstructor,
                lensClassDefClose
            ).stream()
            .map(List::stream)
            .flatMap(Function.identity())
            .map(line -> "    " + line)
            .collect(Collectors.toList());
    }
}
