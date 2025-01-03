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
package functionalj.types.elm.processor;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;

public class ElmChoiceBuilderWithNullableFieldTest {
    
    private static ElmChoiceBuilder setupBuilder() {
        val genericYear = new Generic("year", null, asList(Type.INTEGER));
        val caseParams = asList(new CaseParam("id", Type.STRING, true), new CaseParam("name", Type.STRING, false), new CaseParam("age", Type.INTEGER, false), new CaseParam("years", new Type("java.util", null, "List", asList(genericYear)), false), new CaseParam("wealth", Type.DOUBLE, true), new CaseParam("user", new Type("functionalj.types.elm", null, "User"), false));
        val sourceType = new Type("functionalj.types.elm", null, "LoggedIn");
        val cases = asList(new Case("LoggedIn", caseParams), new Case("LoggedOut"));
        val choiceSpec = new SourceSpec("LoggedIn", sourceType, false, cases);
        val spec = new ElmChoiceSpec(choiceSpec, "LoginStatus", "Example/Functionalj/Elm");
        val builder = new ElmChoiceBuilder(spec, emptyList());
        return builder;
    }
    
    @Test
    public void testChoiceTypeDefinition() {
        val builder = setupBuilder();
        assertEquals("type LoginStatus\n" + "    = LoggedIn (Maybe String) String Int (List Int) (Maybe Float) Functionalj.Types.Elm.User\n" + "    | LoggedOut", builder.typeDefinition().toText());
    }
    
    @Test
    public void testChoiceTypeEncoder() {
        val builder = setupBuilder();
        assertEquals("loginStatusEncoder : LoginStatus -> Json.Encode.Value\n" + "loginStatusEncoder loginStatus = \n" + "    case loginStatus of\n" + "        LoggedIn id name age years wealth user ->\n" + "            Json.Encode.object\n" + "                [ ( \"__tagged\", Json.Encode.string \"LoggedIn\" )\n" + "                , ( \"id\", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.string id) )\n" + "                , ( \"name\", Json.Encode.string name )\n" + "                , ( \"age\", Json.Encode.int age )\n" + "                , ( \"years\", Json.Encode.list Json.Encode.int years )\n" + "                , ( \"wealth\", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.float wealth) )\n" + "                , ( \"user\", userEncoder user )\n" + "                ]\n" + "        LoggedOut  ->\n" + "            Json.Encode.object\n" + "                [ ( \"__tagged\", Json.Encode.string \"LoggedOut\" )\n" + "                ]", builder.encoder().toText());
    }
    
    @Test
    public void testChoiceTypeDecoder() {
        val builder = setupBuilder();
        assertEquals("loginStatusDecoder : Json.Decode.Decoder LoginStatus\n" + "loginStatusDecoder = \n" + "    Json.Decode.field \"__tagged\" Json.Decode.string\n" + "        |> Json.Decode.andThen\n" + "            (\\str ->\n" + "                case str of\n" + "                    \"LoggedIn\" ->\n" + "                        Json.Decode.succeed LoggedIn\n" + "                            |> Json.Decode.Pipeline.optional \"id\" (Json.Decode.maybe Json.Decode.string) Nothing\n" + "                            |> Json.Decode.Pipeline.required \"name\" Json.Decode.string\n" + "                            |> Json.Decode.Pipeline.required \"age\" Json.Decode.int\n" + "                            |> Json.Decode.Pipeline.required \"years\" (Json.Decode.list Json.Decode.int)\n" + "                            |> Json.Decode.Pipeline.optional \"wealth\" (Json.Decode.maybe Json.Decode.float) Nothing\n" + "                            |> Json.Decode.Pipeline.required \"user\" userDecoder\n" + "                    \n" + "                    \"LoggedOut\" ->\n" + "                        Json.Decode.succeed LoggedOut\n" + "                    \n" + "                    somethingElse ->\n" + "                        Json.Decode.fail <| \"Unknown tagged: \" ++ somethingElse\n" + "    )", builder.decoder().toText());
    }
}
