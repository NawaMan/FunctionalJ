package nawaman.functionalj.annotations.processor.generator.model;

import java.util.stream.Stream;

import nawaman.functionalj.annotations.processor.generator.IRequireTypes;
import nawaman.functionalj.annotations.processor.generator.Type;

public interface GenElement extends IRequireTypes {
    public Accessibility getAccessibility();
    public Modifiability getModifiability();
    public Scope         getScope();
    public Type          getType();
    public String        getName();
    public <E extends GenElement> E withAccessibility(Accessibility accessibility);
    public <E extends GenElement> E withModifiability(Modifiability modifiability);
    public <E extends GenElement> E withScope(Scope scope);
    public <E extends GenElement> E withType(Type type);
    public <E extends GenElement> E withName(String name);
    
    @Override
    public default Stream<Type> requiredTypes() {
        return Stream.of(getType());
    }
    
}