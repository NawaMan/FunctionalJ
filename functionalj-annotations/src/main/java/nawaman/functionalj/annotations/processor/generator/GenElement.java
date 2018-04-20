package nawaman.functionalj.annotations.processor.generator;

import java.util.stream.Stream;

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
    public default Stream<Type> getRequiredTypes() {
        return Stream.of(getType());
    }
    
}