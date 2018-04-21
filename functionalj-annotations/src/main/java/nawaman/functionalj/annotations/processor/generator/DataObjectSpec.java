package nawaman.functionalj.annotations.processor.generator;

import static nawaman.functionalj.annotations.processor.generator.Accessibility.PUBLIC;
import static nawaman.functionalj.annotations.processor.generator.Modifiability.MODIFIABLE;
import static nawaman.functionalj.annotations.processor.generator.Scope.NONE;

import java.util.List;

import lombok.Value;
import lombok.experimental.Delegate;
import nawaman.functionalj.annotations.processor.generator.model.ClassSpec;

@Value
public class DataObjectSpec {
    
    @Delegate
    private ClassSpec classSpec;
    
    private String sourceClassName;
    private String sourcePackageName;
    
    public DataObjectSpec(
            String className,
            String packageName,
            String sourceClassName,
            String sourcePackageName,
            List<Type>           extendeds,
            List<Type>           implementeds,
            List<GenConstructor> constructors,
            List<GenField>       fields,
            List<GenMethod>      methods,
            List<ClassSpec>      innerClasses,
            List<ILines>         mores) {
        this.classSpec = new ClassSpec(PUBLIC, NONE, MODIFIABLE, new Type(className, packageName), null, extendeds, implementeds, constructors, fields, methods, innerClasses, mores);
        this.sourceClassName = sourceClassName;
        this.sourcePackageName = sourcePackageName;
    }
    
    public String simpleName() {
        return type().simpleName();
    }
    
    public String simpleNameWithGeneric() {
        return type().simpleNameWithGeneric();
    }
    
    public String packageName() {
        return type().packageName();
    }
    
    public Type getLensType() {
        return new Type.TypeBuilder()
                .encloseName(simpleName())
                .simpleName(simpleName() + "Lens")
                .packageName(packageName())
                .generic("HOST")
                .build();
    }
    
}