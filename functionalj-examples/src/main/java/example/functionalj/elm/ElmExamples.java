package example.functionalj.elm;

import java.util.Optional;

import functionalj.types.Choice;
import functionalj.types.Struct;
import functionalj.types.elm.Elm;

public class ElmExamples {
    
    @Elm
    @Struct
    void User(String firstName, String lastName) {}
    
    @Elm
    @Choice(specField ="spec")
    interface LoginStatus {
        void Loggined(
                String            name, 
                int               age, 
//                FuncList<Integer> years, 
                Optional<Double>  wealth,
                User              user);
        void LoggedOut();
    }
    
}
