package functionalj.types.typescript.processor.choice;

import org.junit.Test;

import functionalj.types.choice.generator.model.SourceSpec;

public class TypeScriptChoiceSpecTest {
    
    @Test
    public void test() {
        SourceSpec sourceSpec = new functionalj.types.choice.generator.model.SourceSpec(
                "Cape", 
                new functionalj.types.Type(
                    "javatsexample.services",
                    "PersonService",
                    "CapeModel", 
                    java.util.Collections.emptyList()
                ), 
                null, 
                false, 
                "__tagged", 
                functionalj.types.Serialize.To.NOTHING, 
                java.util.Collections.emptyList(),
                java.util.Arrays.asList(
                    new functionalj.types.choice.generator.model.Case(
                        "Color", 
                        null, 
                        java.util.Arrays.asList(
                            new functionalj.types.choice.generator.model.CaseParam(
                                "color", 
                                new functionalj.types.Type(
                                    "java.util", 
                                    null, 
                                    "List", 
                                    java.util.Arrays.asList(
                                        new functionalj.types.Generic(
                                            "java.lang.Integer", 
                                            "java.lang.Integer", 
                                            java.util.Arrays.asList(
                                                new functionalj.types.Type(
                                                    null, 
                                                    null, 
                                                    "java.lang.Integer", 
                                                    java.util.Collections.emptyList()
                                                )
                                            )
                                        )
                                    )
                                ), 
                                false, 
                                null
                            )
                        )
                    ), 
                    new functionalj.types.choice.generator.model.Case(
                        "None", 
                        null, 
                        java.util.Collections.emptyList()
                    )
                ), 
                java.util.Collections.emptyList(), 
                java.util.Arrays.asList("Person", "Cape")
            );
        
//        String specStr = "new functionalj.types.choice.generator.model.SourceSpec(\"Cape\", new functionalj.types.Type(\"javatsexample.services\", \"PersonService\", \"CapeModel\", java.util.Collections.emptyList()), null, false, \"__tagged\", functionalj.types.Serialize.To.NOTHING, java.util.Collections.emptyList(), java.util.Arrays.asList(new functionalj.types.choice.generator.model.Case(\"Color\", null, java.util.Arrays.asList(new functionalj.types.choice.generator.model.CaseParam(\"color\", new functionalj.types.Type(\"java.util\", null, \"List\", java.util.Arrays.asList(new functionalj.types.Generic(\"java.lang.Integer\", \"java.lang.Integer\", java.util.Arrays.asList(new functionalj.types.Type(null, null, \"java.lang.Integer\", java.util.Collections.emptyList()))))), false, null))), new functionalj.types.choice.generator.model.Case(\"None\", null, java.util.Collections.emptyList())), java.util.Collections.emptyList(), java.util.Arrays.asList(\"Person\", \"Cape\"))";
//        assertAsString(specStr, sourceSpec.toCode());
        
        String scriptCode = new TypeScriptChoiceBuilder(new TypeScriptChoiceSpec(sourceSpec, "Cape", "ts/web")).toTypeScriptCode();
        System.out.println(scriptCode);
    }
    
}
