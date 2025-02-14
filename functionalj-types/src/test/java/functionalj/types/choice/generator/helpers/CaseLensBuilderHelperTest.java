// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
// ----------------------------------------------------------------------------
// MIT License
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
// ============================================================================
package functionalj.types.choice.generator.helpers;

import static functionalj.types.TestHelper.assertAsString;
import static functionalj.types.choice.generator.helpers.CaseLensBuilderHelper.createGenListLensField;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.stream.Collectors;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Serialize;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import functionalj.types.struct.generator.model.GenField;
import lombok.val;

public class CaseLensBuilderHelperTest {
    
    @Test
    public void testCreateGenListLensField() {
        String packageName = "functionalj.types.choice";
        SourceSpec sourceSpec = new SourceSpec("NestedCommand", new Type("functionalj.types.choice", "NestedListTest", "NestedCommandModel", emptyList()), "spec", false, "__tagged", false, Serialize.To.NOTHING, emptyList(), asList(new Case("Move", null, asList(new CaseParam("distance", new Type(null, null, "int", emptyList()), true, null))), new Case("Commands", null, asList(new CaseParam("commands", new Type("java.util", null, "List", asList(new Generic("String", "String", asList(new Type(null, null, "String", emptyList()))))), true, null)))), emptyList(), emptyList());
        String dataObjName = "NestedCommand.Commands";
        String name = "commands";
        Type type = sourceSpec.choices.get(1).params.get(0).type();
        String withName = "withCommands";
        GenField genField = createGenListLensField(sourceSpec, dataObjName, name, type, withName);
        String generated = genField.toDefinition(packageName).lines().collect(Collectors.joining("\n"));
        assertAsString("public final ListLens<HOST, String, StringLens<HOST>> " + "commands = createSubListLens(" + "NestedCommand.Commands::commands, " + "NestedCommand.Commands::withCommands, " + "StringLens::of);", generated);
    }
    
    @Test
    public void testCreateGenListLensField_custom() {
        SourceSpec sourceSpec = new SourceSpec("NestedCommand", new Type("functionalj.types.choice", "NestedListTest", "NestedCommandModel", emptyList()), "spec", false, "__tagged", false, Serialize.To.NOTHING, emptyList(), asList(new Case("Move", null, asList(new CaseParam("distance", new Type(null, null, "int", emptyList()), true, null))), new Case("Commands", null, asList(new CaseParam("commands", new Type("java.util", null, "List", asList(new Generic("Command", "Command", asList(new Type(null, null, "Command", emptyList()))))), true, null)))), emptyList(), asList("Command"));
        String packageName = "functionalj.types.choice";
        String dataObjName = "NestedCommand.Commands";
        String name = "commands";
        Type type = sourceSpec.choices.get(1).params.get(0).type();
        String withName = "withCommands";
        GenField genField = createGenListLensField(sourceSpec, dataObjName, name, type, withName);
        String generated = genField.toDefinition(packageName).lines().collect(Collectors.joining("\n"));
        assertAsString("public final ListLens<HOST, Command, Command.CommandLens<HOST>> " + "commands = createSubListLens(" + "NestedCommand.Commands::commands, " + "NestedCommand.Commands::withCommands, " + "Command.CommandLens::new);", generated);
    }
    
    @Test
    public void testListLens_notKnownType() {
        val sourceSpec = new SourceSpec("NestedCommand", new Type("functionalj.types.choice", "NestedListTest", "NestedCommandModel", emptyList()), null, false, "__tagged", false, Serialize.To.NOTHING, emptyList(), asList(new Case("Rotate", null, asList(new CaseParam("degree", new functionalj.types.Type(null, null, "int", emptyList()), false, null))), new Case("Move", null, asList(new CaseParam("distance", new functionalj.types.Type(null, null, "int", emptyList()), false, null))), new Case("Commands", null, asList(new CaseParam("commands", new Type("java.util", null, "List", asList(new Generic("NestedCommand", "NestedCommand", asList(new Type(null, null, "NestedCommand", emptyList()))))), false, null)))), emptyList(), // asList("NestedCommand")
        emptyList());
        String packageName = "functionalj.types.choice";
        String dataObjName = "NestedCommand.Commands";
        String name = "commands";
        Type type = sourceSpec.choices.get(2).params.get(0).type();
        String withName = "withCommands";
        GenField genField = createGenListLensField(sourceSpec, dataObjName, name, type, withName);
        String generated = genField.toDefinition(packageName).lines().collect(Collectors.joining("\n"));
        assertAsString("public final ListLens<HOST, Object, ObjectLens<HOST, Object>> " + "commands = createSubListLens(" + "NestedCommand.Commands::commands, " + "NestedCommand.Commands::withCommands);", generated);
    }
}
