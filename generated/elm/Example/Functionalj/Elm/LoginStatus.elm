module Example.Functionalj.Elm.LoginStatus exposing
    ( LoginStatus
    , loginStatusEncoder
    , loginStatusDecoder
    , encodeLoginStatus
    , decodeLoginStatus
    , loginStatusListEncoder
    , loginStatusListDecoder
    , encodeLoginStatusList
    , decodeLoginStatusList
    )

import Json.Decode
import Json.Decode.Pipeline
import Json.Encode


-- elm install elm/json
-- elm install NoRedInk/elm-json-decode-pipeline

type LoginStatus
    = Loggined (Maybe String) (Maybe Int) (Maybe (List Int)) (Maybe (Maybe Float)) (Maybe Example.Functionalj.Elm.User)
    | LoggedOut


loginStatusEncoder : LoginStatus -> Json.Encode.Value
loginStatusEncoder loginStatus = 
    case loginStatusEncoder of
        Loggined name age years wealth user ->
            Json.Encode.object
                [ ( "__tagged", Json.Encode.string "Loggined" )
                , ( "name", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.string name) )
                , ( "age", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.int age) )
                , ( "years", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.list Json.Encode.int years) )
                , ( "wealth", Maybe.withDefault Json.Encode.null (Maybe.map Json.Encode.float wealth) )
                , ( "user", Maybe.withDefault Json.Encode.null (Maybe.map userEncoder user) )
                ]
        LoggedOut  ->
            Json.Encode.object
                [ ( "__tagged", Json.Encode.string "LoggedOut" )
                ]


loginStatusDecoder : Json.Decode.Decoder LoginStatus
loginStatusDecoder = 
    Json.Decode.field "__tagged" Json.Decode.string
        |> Json.Decode.andThen
            (\str ->
                case str of
                    "Loggined" ->
                        Json.Decode.succeed Loggined
                            |> Json.Decode.Pipeline.optional "name" (Json.Decode.nullable Json.Decode.string) Nothing
                            |> Json.Decode.Pipeline.optional "age" (Json.Decode.nullable Json.Decode.int) Nothing
                            |> Json.Decode.Pipeline.optional "years" (Json.Decode.list Json.Decode.int)
                            |> Json.Decode.Pipeline.optional "wealth" (Json.Decode.nullable Json.Decode.float) Nothing
                            |> Json.Decode.Pipeline.optional "user" (Json.Decode.nullable userDecoder) Nothing
                    
                    "LoggedOut" ->
                        Json.Decode.succeed LoggedOut
                    
                    somethingElse ->
                        Json.Decode.fail <| "Unknown tagged: " ++ somethingElse
    )


encodeLoginStatus : LoginStatus -> Int -> String
encodeLoginStatus loginStatus indent = 
    loginStatusEncoder loginStatus |> Json.Encode.encode indent


decodeLoginStatus : String -> Result Json.Decode.Error LoginStatus
decodeLoginStatus = 
    Json.Decode.decodeString loginStatusDecoder


loginStatusListEncoder : List LoginStatus -> Json.Encode.Value
loginStatusListEncoder loginStatusList = 
    Json.Encode.list loginStatusEncoder loginStatusList


loginStatusListDecoder : Json.Decode.Decoder (List LoginStatus)
loginStatusListDecoder = 
    Json.Decode.list loginStatusDecoder


encodeLoginStatusList : List LoginStatus -> Int -> String
encodeLoginStatusList loginStatusList indent = 
    loginStatusListEncoder loginStatusList |> Json.Encode.encode indent


decodeLoginStatusList : String -> Result Json.Decode.Error (List LoginStatus)
decodeLoginStatusList = 
    Json.Decode.decodeString loginStatusListDecoder
