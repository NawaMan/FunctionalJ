package functionalj.types.choice.generator.helpers;

import static functionalj.types.TestHelper.assertAsString;
import static functionalj.types.choice.generator.helpers.CaseLensBuilderHelper.createGenListLensField;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.struct.generator.model.GenField;
import lombok.val;

public class CaseLensBuilderHelperTest {
    
    @Test
    public void testCreateGenListLensField() {
        String     packageName = "functionalj.types.choice";
        SourceSpec sourceSpec 
                            = new SourceSpec("NestedCommand", 
                                new Type("functionalj.types.choice", "NestedListTest", "NestedCommandModel", emptyList()), "spec", false, "__tagged", emptyList(), 
                                asList(
                                   new Case("Move",     null, asList(new CaseParam("distance", new Type(null, null, "int", emptyList()), true, null))), 
                                   new Case("Commands", null, asList(new CaseParam("commands", 
                                       new Type("java.util", null, "List", asList(
                                           new Generic("String", "String", 
                                               asList(new Type(null, null, "String", emptyList()))))), 
                                       true, null)))), emptyList(), emptyList());
        String   dataObjName = "NestedCommand.Commands";
        String   name        = "commands";
        Type     type        = sourceSpec.choices.get(1).params.get(0).type();
        String   withName    = "withCommands";
        GenField genField    = createGenListLensField(sourceSpec, dataObjName, name, type, withName);
        String   generated   = genField.toDefinition(packageName).lines().collect(Collectors.joining("\n"));
        assertAsString(
                "public final ListLens<HOST, String, StringLens<HOST>> "
                                + "commands = createSubListLens("
                                    + "NestedCommand.Commands::commands, "
                                    + "NestedCommand.Commands::withCommands, "
                                    + "StringLens::of);", 
                generated);
    }
    
    @Test
    public void testCreateGenListLensField_custom() {
        SourceSpec sourceSpec 
                = new SourceSpec("NestedCommand", 
                    new Type("functionalj.types.choice", "NestedListTest", "NestedCommandModel", emptyList()), "spec", false, "__tagged", emptyList(), 
                    asList(
                       new Case("Move",     null, asList(new CaseParam("distance", new Type(null, null, "int", emptyList()), true, null))), 
                       new Case("Commands", null, asList(new CaseParam("commands", 
                           new Type("java.util", null, "List", asList(
                               new Generic("Command", "Command", 
                                   asList(new Type(null, null, "Command", emptyList()))))), 
                           true, null)))), emptyList(),
                    asList("Command"));
        String   packageName = "functionalj.types.choice";
        String   dataObjName = "NestedCommand.Commands";
        String   name        = "commands";
        Type     type        = sourceSpec.choices.get(1).params.get(0).type();
        String   withName    = "withCommands";
        GenField genField    = createGenListLensField(sourceSpec, dataObjName, name, type, withName);
        String   generated   = genField.toDefinition(packageName).lines().collect(Collectors.joining("\n"));
        assertAsString(
                "public final ListLens<HOST, Command, Command.CommandLens<HOST>> "
                                + "commands = createSubListLens("
                                    + "NestedCommand.Commands::commands, "
                                    + "NestedCommand.Commands::withCommands, "
                                    + "Command.CommandLens::new);", 
                generated);
    }
    
    @Test
    public void testListLens_notKnownType() {
        val sourceSpec
                = new SourceSpec("NestedCommand", 
                        new Type("functionalj.types.choice", "NestedListTest", "NestedCommandModel", emptyList()), null, false, "__tagged", emptyList(), 
                        asList(new Case("Rotate", null, asList(new CaseParam("degree", new functionalj.types.Type(null, null, "int", emptyList()), false, null))), 
                               new Case("Move", null, asList(new CaseParam("distance", new functionalj.types.Type(null, null, "int", emptyList()), false, null))), 
                               new Case("Commands", null, asList(new CaseParam("commands", 
                                       new Type("java.util", null, "List", asList(
                                               new Generic("NestedCommand", "NestedCommand", 
                                                       asList(new Type(null, null, "NestedCommand", emptyList()))))), 
                                       false, null)))), emptyList(), 
                        //asList("NestedCommand")
                        emptyList()
                        );
        String   packageName = "functionalj.types.choice";
        String   dataObjName = "NestedCommand.Commands";
        String   name        = "commands";
        Type     type        = sourceSpec.choices.get(2).params.get(0).type();
        String   withName    = "withCommands";
        GenField genField    = createGenListLensField(sourceSpec, dataObjName, name, type, withName);
        String   generated   = genField.toDefinition(packageName).lines().collect(Collectors.joining("\n"));
        assertAsString(
                "public final ListLens<HOST, Object, ObjectLens<HOST, Object>> "
                                + "commands = createSubListLens("
                                    + "NestedCommand.Commands::commands, "
                                    + "NestedCommand.Commands::withCommands);", 
                generated);
    }
    
}
