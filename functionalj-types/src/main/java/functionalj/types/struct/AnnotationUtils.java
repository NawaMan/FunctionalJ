package functionalj.types.struct;

import java.io.IOException;
import java.io.StringWriter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import functionalj.types.struct.StructSpec.Input;
import functionalj.types.struct.generator.model.Accessibility;
import functionalj.types.struct.generator.model.Concrecity;
import functionalj.types.struct.generator.model.Modifiability;
import functionalj.types.struct.generator.model.Scope;
import lombok.val;

public class AnnotationUtils {
    
    public static boolean isAbstract(Input input, ExecutableElement method) {
        // Seriously ... no other way?
        try (val writer = new StringWriter()) {
            input.elementUtils().printElements(writer, method);
            return writer.toString().contains(" abstract ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isAbstract(ExecutableElement method) {
        return method.getModifiers().contains(Modifier.ABSTRACT);
    }
    
    public static boolean isStatic(ExecutableElement method) {
        return method.getModifiers().contains(Modifier.STATIC);
    }
    
    public static boolean isPrivate(ExecutableElement method) {
        return method.getModifiers().contains(Modifier.PRIVATE);
    }
    
    public static Accessibility accessibilityOf(ExecutableElement method) {
        if (method.getModifiers().contains(Modifier.PRIVATE))
            return Accessibility.PRIVATE;
        if (method.getModifiers().contains(Modifier.DEFAULT))
            return Accessibility.PACKAGE;
        if (method.getModifiers().contains(Modifier.PROTECTED))
            return Accessibility.PROTECTED;
        if (method.getModifiers().contains(Modifier.PUBLIC))
            return Accessibility.PUBLIC;
        return Accessibility.PACKAGE;
    }
    
    public static Scope scopeOf(ExecutableElement method) {
        return method.getModifiers().contains(Modifier.STATIC) ? Scope.STATIC : Scope.INSTANCE;
    }
    
    public static Modifiability modifiabilityOf(ExecutableElement method) {
        return method.getModifiers().contains(Modifier.FINAL) ? Modifiability.FINAL : Modifiability.MODIFIABLE;
    }
    
    public static Concrecity concrecityOf(ExecutableElement method) {
        if (method.isDefault())
            return Concrecity.DEFAULT;
        return method.getModifiers().contains(Modifier.ABSTRACT) ? Concrecity.ABSTRACT : Concrecity.CONCRETE;
    }
    
}
