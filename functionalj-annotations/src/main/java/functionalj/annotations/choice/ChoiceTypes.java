package functionalj.annotations.choice;

public class ChoiceTypes {
    
    private ChoiceTypes() {}
    
    public static <S> S Switch(AbstractChoiceClass<S> choiceType) {
        return choiceType.__switch();
    }
    
}
