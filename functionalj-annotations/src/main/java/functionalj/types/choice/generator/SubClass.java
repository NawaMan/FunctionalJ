package functionalj.types.choice.generator;

import functionalj.types.choice.generator.model.Case;

public class SubClass {
    public final SubClassConstructor constructor;
    public final SubClassDefinition  difinition;
    public SubClass(TargetClass targetClass, Case choice) {
        this.constructor = new SubClassConstructor(targetClass, choice);
        this.difinition  = new SubClassDefinition(targetClass, choice);
    }
}