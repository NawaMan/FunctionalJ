package functionalj.all.matchcase;

import java.util.Optional;

import functionalj.types.choice.AbstractChoiceClass;

public class Case {
    
    public static <S> S of(AbstractChoiceClass<S> choiceType) {
        return choiceType.match();
    }
    
    public static <S> OptionalSwitchCase.OptionalSwitch<S> of(Optional<S> optional) {
        return new OptionalSwitchCase.OptionalSwitch<>(optional);
    }
    
}
