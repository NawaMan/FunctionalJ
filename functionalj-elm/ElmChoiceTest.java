package functionalj.types.elm.processor;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import functionalj.types.Generic;
import functionalj.types.Type;
import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.choice.generator.model.SourceSpec;
import lombok.val;


public class ElmChoiceTest {
    
    @Test
    public void test() {
        val genericYear = new Generic("year", null, asList(Type.INTEGER));
        val caseParams = asList(
                new CaseParam("name",   Type.STRING,  false),
                new CaseParam("age",    Type.INTEGER, false),
                new CaseParam("years",  new Type("java.util", null, "List", asList(genericYear)), false),
                new CaseParam("wealth", Type.DOUBLE,  true),
                new CaseParam("user",   new Type("example.functionalj.elm", null, "User"), false)
            );
        
        val sourceType = new Type("functionalj.types.elm", null, "LoggedIn");
        val cases      = asList(new Case("LoggedIn", caseParams), new Case("LoggedOut"));
        val choiceSpec = new SourceSpec("LoggedIn", sourceType, cases);
        val spec       = new ElmChoiceSpec(choiceSpec, "LoginStatus", "Example/Functionalj/Elm");
        val choice     = new ElmChoiceBuilder(spec);
        val genCode    = choice.toElmCode();
        val code       = genCode.substring(genCode.indexOf('\n')+1);
        assertEquals(expected, code);
    }
    
    private static final String expected =
            "module Example.Functionalj.Elm.LoginStatus exposing\n" + 
            "    ( LoginStatus\n" + 
            "    , loginStatusEncoder\n" + 
            "    , loginStatusDecoder\n" + 
            "    , encodeLoginStatus\n" + 
            "    , decodeLoginStatus\n" + 
            "    , loginStatusListEncoder\n" + 
            "    , loginStatusListDecoder\n" + 
            "    , encodeLoginStatusList\n" + 
            "    , decodeLoginStatusList\n" + 
            "    )\n" + 
            "\n" + 
            "import Json.Decode\n" + 
            "import Json.Decode.Pipeline\n" + 
            "import Json.Encode\n" + 
            "\n" + 
            "\n" + 
            "-- elm install elm/json\n" + 
            "-- elm install NoRedInk/elm-json-decode-pipeline\n" + 
            "\n" + 
            "type LoginStatus\n" + 
            "    = LoggedIn String Int (List Int) (Maybe Float) Example.Functionalj.Elm.User\n" + 
            "    | LoggedOut\n" + 
            "\n" + 
            "\n" + 
            "loginStatusEncoder : LoginStatus -> Json.Encode.Value\n" + 
            "loginStatusEncoder loginStatus = \n" + 
            "    case loginStatusEncoder of\n" + 
            "        LoggedIn name age years wealth user ->\n" + 
            "            Json.Encode.object\n" + 
            "                [ ( \"__tagged\", Json.Encode.string \"LoggedIn\" )\n" + 
            "                , ( \"name\", Json.Encode.string name )\n" + 
            "                , ( \"age\", Json.Encode.int age )\n" + 
            "                , ( \"years\", Json.Encode.list Json.Encode.int years )\n" + 
            "                , ( \"wealth\", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.float wealth) )\n" + 
            "                , ( \"user\", userEncoder user )\n" + 
            "                ]\n" + 
            "        LoggedOut  ->\n" + 
            "            Json.Encode.object\n" + 
            "                [ ( \"__tagged\", Json.Encode.string \"LoggedOut\" )\n" + 
            "                ]\n" + 
            "\n" + 
            "\n" + 
            "loginStatusDecoder : Json.Decode.Decoder LoginStatus\n" + 
            "loginStatusDecoder = \n" + 
            "    Json.Decode.field \"__tagged\" Json.Decode.string\n" + 
            "        |> Json.Decode.andThen\n" + 
            "            (\\str ->\n" + 
            "                case str of\n" + 
            "                    \"LoggedIn\" ->\n" + 
            "                        Json.Decode.succeed LoggedIn\n" + 
            "                            |> Json.Decode.Pipeline.required \"name\" Json.Decode.string\n" + 
            "                            |> Json.Decode.Pipeline.required \"age\" Json.Decode.int\n" + 
            "                            |> Json.Decode.Pipeline.required \"years\" (Json.Decode.list Json.Decode.int)\n" + 
            "                            |> Json.Decode.Pipeline.optional \"wealth\" (Json.Decode.nullable Json.Decode.float) Nothing\n" + 
            "                            |> Json.Decode.Pipeline.required \"user\" userDecoder\n" + 
            "                    \n" + 
            "                    \"LoggedOut\" ->\n" + 
            "                        Json.Decode.succeed LoggedOut\n" + 
            "                    \n" + 
            "                    somethingElse ->\n" + 
            "                        Json.Decode.fail <| \"Unknown tagged: \" ++ somethingElse\n" + 
            "    )\n" + 
            "\n" + 
            "\n" + 
            "encodeLoginStatus : LoginStatus -> Int -> String\n" + 
            "encodeLoginStatus loginStatus indent = \n" + 
            "    loginStatusEncoder loginStatus |> Json.Encode.encode indent\n" + 
            "\n" + 
            "\n" + 
            "decodeLoginStatus : String -> Result Json.Decode.Error LoginStatus\n" + 
            "decodeLoginStatus = \n" + 
            "    Json.Decode.decodeString loginStatusDecoder\n" + 
            "\n" + 
            "\n" + 
            "loginStatusListEncoder : List LoginStatus -> Json.Encode.Value\n" + 
            "loginStatusListEncoder loginStatusList = \n" + 
            "    Json.Encode.list loginStatusEncoder loginStatusList\n" + 
            "\n" + 
            "\n" + 
            "loginStatusListDecoder : Json.Decode.Decoder (List LoginStatus)\n" + 
            "loginStatusListDecoder = \n" + 
            "    Json.Decode.list loginStatusDecoder\n" + 
            "\n" + 
            "\n" + 
            "encodeLoginStatusList : List LoginStatus -> Int -> String\n" + 
            "encodeLoginStatusList loginStatusList indent = \n" + 
            "    loginStatusListEncoder loginStatusList |> Json.Encode.encode indent\n" + 
            "\n" + 
            "\n" + 
            "decodeLoginStatusList : String -> Result Json.Decode.Error (List LoginStatus)\n" + 
            "decodeLoginStatusList = \n" + 
            "    Json.Decode.decodeString loginStatusListDecoder\n" + 
            "";
    
}
