package functionalj.annotations.sealed;

public class SealedClasses {
    
    private SealedClasses() {}
    
    public static <S> S Switch(AbstractSealedClass<S> sealed) {
        return sealed.__switch();
    }
    
}
