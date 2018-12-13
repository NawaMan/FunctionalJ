package functionalj.annotations.choice;

public class ChoiceTypes {
    
    private ChoiceTypes() {}
    
    public static <S> S Match(AbstractChoiceClass<S> choiceType) {
        return choiceType.match();
    }
    
}
