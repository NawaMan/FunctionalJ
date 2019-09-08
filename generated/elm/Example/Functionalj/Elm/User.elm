module Example.Functionalj.Elm.User exposing
    ( User
    , userEncoder
    , userDecoder
    , encodeUser
    , decodeUser
    , userListEncoder
    , userListDecoder
    , encodeUserList
    , decodeUserList
    )

import Json.Decode
import Json.Decode.Pipeline
import Json.Encode


-- elm install elm/json
-- elm install NoRedInk/elm-json-decode-pipeline

type alias User = 
    { firstName : String
    , lastName : String
    }


userEncoder : User -> Json.Encode.Value
userEncoder user = 
    Json.Encode.object
        [ ( "firstName", Json.Encode.string user.firstName )
        , ( "lastName", Json.Encode.string user.lastName )
        ]


userDecoder : Json.Decode.Decoder User
userDecoder = 
    Json.Decode.succeed User
        |> Json.Decode.Pipeline.required "firstName" Json.Decode.string
        |> Json.Decode.Pipeline.required "lastName" Json.Decode.string


encodeUser : User -> Int -> String
encodeUser user indent = 
    userEncoder user |> Json.Encode.encode indent


decodeUser : String -> Result Json.Decode.Error User
decodeUser = 
    Json.Decode.decodeString userDecoder


userListEncoder : List User -> Json.Encode.Value
userListEncoder userList = 
    Json.Encode.list userEncoder userList


userListDecoder : Json.Decode.Decoder (List User)
userListDecoder = 
    Json.Decode.list userDecoder


encodeUserList : List User -> Int -> String
encodeUserList userList indent = 
    userListEncoder userList |> Json.Encode.encode indent


decodeUserList : String -> Result Json.Decode.Error (List User)
decodeUserList = 
    Json.Decode.decodeString userListDecoder
