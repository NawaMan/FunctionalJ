package example.functionalj.elm;

import functionalj.types.Struct;
import functionalj.types.elm.Elm;

public class ElmExamples {
    
    @Elm
    @Struct(specField ="spec")
    void User(String firstName, String lastName) {}
    
}
