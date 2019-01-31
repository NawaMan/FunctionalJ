package functionalj.types;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;

import lombok.val;

public class common {
    
    public static String extractTargetName(Element element) {
        if (element.getAnnotation(Struct.class) != null) {
            val specTargetName = element.getAnnotation(Struct.class).name();
            val simpleName     = element.getSimpleName().toString();
            return extractTargetName(simpleName, specTargetName);
        } else if (element.getAnnotation(Struct.class) != null) {
            val specTargetName = element.getAnnotation(Choice.class).name();
            val simpleName     = element.getSimpleName().toString();
            return extractTargetName(simpleName, specTargetName);
        }
        return null;
    }
    public static String extractTargetName(String simpleName, String specTargetName) {
        if ((specTargetName != null) && !specTargetName.isEmpty())
            return specTargetName;
        
        if (simpleName.matches("^.*Spec$"))
            return simpleName.replaceAll("Spec$", "");
        if (simpleName.matches("^.*Model$"))
            return simpleName.replaceAll("Model$", "");
        
        return simpleName;
    }
    
    public static List<String> readLocalTypeWithLens(Element element) {
        return element
                .getEnclosingElement()
                .getEnclosedElements().stream()
                .filter(elmt -> {
                    return (elmt.getAnnotation(Struct.class) != null)
                        || (elmt.getAnnotation(Choice.class) != null);
                })
                .map(elmt -> {
                    val targetName = extractTargetName(elmt);
                    return targetName;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
}
