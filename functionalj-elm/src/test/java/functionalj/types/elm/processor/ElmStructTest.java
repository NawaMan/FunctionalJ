// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net).
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
package functionalj.types.elm.processor;

import static functionalj.types.struct.SourceKind.METHOD;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

import functionalj.types.JavaVersionInfo;
import functionalj.types.Type;
import functionalj.types.struct.SourceKind;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.SourceSpec.Configurations;
import lombok.val;

public class ElmStructTest {
    
    private Configurations configuration = new Configurations();
    
    {
        configuration.coupleWithDefinition = true;
        configuration.generateNoArgConstructor = true;
        configuration.generateAllArgConstructor = true;
        configuration.generateLensClass = true;
    }
    
    private String definitionClassName = "Definitions.DataDef";
    
    private String targetClassName = "User";
    
    private String packageName = "example.functionalj.elm";
    
    private SourceKind sourceKind = METHOD;
    
    private List<Getter> getters = asList(new Getter("firstName", Type.STRING), new Getter("lastName", Type.STRING));
    
    @Test
    public void test() {
        val sourceSpec = new SourceSpec(
                new JavaVersionInfo(8, 8),
                definitionClassName, // specClassName
                packageName,         // packageName
                null,                // encloseName
                targetClassName,     // targetClassName
                packageName,         // targetPackageName
                sourceKind,
                null,
                null,
                configuration,
                getters,
                emptyList(),
                emptyList()
        );
        val spec = new ElmStructSpec(sourceSpec, "User", "Example/Functionalj/Elm", null);
        val struct = new ElmStructBuilder(spec, emptyList());
        val genCode = struct.toElmCode();
        val code = genCode.substring(genCode.indexOf('\n') + 1);
        assertEquals(expected, code);
    }
    
    private static final String expected = "module Example.Functionalj.Elm.User exposing\n" + "    ( User\n" + "    , userEncoder\n" + "    , userDecoder\n" + "    , encodeUser\n" + "    , decodeUser\n" + "    , userListEncoder\n" + "    , userListDecoder\n" + "    , encodeUserList\n" + "    , decodeUserList\n" + "    )\n" + "\n" + "import Json.Decode\n" + "import Json.Decode.Pipeline\n" + "import Json.Encode\n" + "\n" + "\n" + "\n" + "\n" + "-- elm install elm/json\n" + "-- elm install NoRedInk/elm-json-decode-pipeline\n" + "\n" + "type alias User = \n" + "    { firstName : String\n" + "    , lastName : String\n" + "    }\n" + "\n" + "\n" + "userEncoder : User -> Json.Encode.Value\n" + "userEncoder user = \n" + "    Json.Encode.object\n" + "        [ ( \"firstName\", Json.Encode.string user.firstName )\n" + "        , ( \"lastName\", Json.Encode.string user.lastName )\n" + "        ]\n" + "\n" + "\n" + "userDecoder : Json.Decode.Decoder User\n" + "userDecoder = \n" + "    Json.Decode.succeed User\n" + "        |> Json.Decode.Pipeline.required \"firstName\" Json.Decode.string\n" + "        |> Json.Decode.Pipeline.required \"lastName\" Json.Decode.string\n" + "\n" + "\n" + "encodeUser : User -> Int -> String\n" + "encodeUser user indent = \n" + "    userEncoder user |> Json.Encode.encode indent\n" + "\n" + "\n" + "decodeUser : String -> Result Json.Decode.Error User\n" + "decodeUser = \n" + "    Json.Decode.decodeString userDecoder\n" + "\n" + "\n" + "userListEncoder : List User -> Json.Encode.Value\n" + "userListEncoder userList = \n" + "    Json.Encode.list userEncoder userList\n" + "\n" + "\n" + "userListDecoder : Json.Decode.Decoder (List User)\n" + "userListDecoder = \n" + "    Json.Decode.list userDecoder\n" + "\n" + "\n" + "encodeUserList : List User -> Int -> String\n" + "encodeUserList userList indent = \n" + "    userListEncoder userList |> Json.Encode.encode indent\n" + "\n" + "\n" + "decodeUserList : String -> Result Json.Decode.Error (List User)\n" + "decodeUserList = \n" + "    Json.Decode.decodeString userListDecoder\n" + "";
}
