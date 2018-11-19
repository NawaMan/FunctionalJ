package functionalj.annotations.sealed.generator;

import functionalj.annotations.sealed.generator.model.Choice;

public class SubClass {
    public final SubClassConstructor constructor;
    public final SubClassDefinition  difinition;
    public SubClass(TargetClass targetClass, Choice choice) {
        this.constructor = new SubClassConstructor(targetClass, choice);
        this.difinition  = new SubClassDefinition(targetClass, choice);
    }
}