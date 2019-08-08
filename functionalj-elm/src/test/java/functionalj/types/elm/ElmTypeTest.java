package functionalj.types.elm;

import static functionalj.types.DefaultValue.REQUIRED;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.elm.processor.ElmType;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;

public class ElmTypeTest {
    
    public static final functionalj.types.struct.generator.SourceSpec userSpec 
        = new functionalj.types.struct.generator.SourceSpec(
            null, 
            "example.functionalj.elm", 
            "ElmExamples", 
            "User", 
            "example.functionalj.elm", 
            null, 
            "spec", 
            null, 
            new SourceSpec.Configurations(
                true, 
                false, 
                true, 
                true, 
                true, 
                true, 
                true, 
                ""), 
            asList(
                new Getter(
                    "firstName", 
                    new Type("java.lang", null, "String", emptyList()), 
                    false, 
                    REQUIRED), 
                new Getter(
                    "lastName", 
                    new Type("java.lang", null, "String", emptyList()), 
                    false, 
                    REQUIRED)), 
            asList("User"));
    
    
    public static final functionalj.types.choice.generator.model.SourceSpec loginStatusSpec 
            = new functionalj.types.choice.generator.model.SourceSpec(
                    "LoginStatus", 
                    new Type(
                            "example.functionalj.choice", 
                            "ChoiceTypeExamples", 
                            "LoginStatusSpec", 
                            emptyList()), 
                    "spec", 
                    false, 
                    "__tagged", 
                    emptyList(), 
                    asList(
                            new Case("Login", null, 
                                    asList(
                                            new CaseParam(
                                                    "userName", 
                                                    new Type(
                                                            "java.lang", 
                                                            null, 
                                                            "String", 
                                                            emptyList()), 
                                                    true, 
                                                    null))), 
                            new Case("Logout", null, emptyList())), 
                    emptyList(), 
                    emptyList());
    
    @Test
    public void testBasic() {
        assertEquals("Int",    "" + new ElmType(Type.INT));
        assertEquals("Bool",   "" + new ElmType(Type.BOOL));
        assertEquals("Float",  "" + new ElmType(Type.DOUBLE));
        assertEquals("String", "" + new ElmType(Type.STRING));
        assertEquals("List String",     "" + new ElmType(Type.LIST.withGenerics(new Generic(Type.STRING))));
        assertEquals("Dict Int String", "" + new ElmType(Type.MAP.withGenerics(new Generic(Type.INT), new Generic(Type.STRING))));
    }
    
    @Test
    public void testComplex() {
        assertEquals("List (List String)",     "" + new ElmType(Type.LIST.withGenerics(new Generic(Type.LIST.withGenerics(new Generic(Type.STRING))))));
        assertEquals("Dict Int (List String)", "" + new ElmType(Type.MAP.withGenerics(new Generic(Type.INT), new Generic(Type.LIST.withGenerics(new Generic(Type.STRING))))));
    }
    
}
