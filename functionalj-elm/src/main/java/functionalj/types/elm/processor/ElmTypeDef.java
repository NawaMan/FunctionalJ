// ============================================================================
// Copyright(c) 2017-2019 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import functionalj.types.struct.generator.ILines;
import lombok.val;

/**
 * Classes implementing this interface can create type definition in Elm.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public interface ElmTypeDef {
    
    public String typeName();
    
    public default String camelName() {
        val camelName = Utils.toCamelCase(typeName());
        return camelName;
    }
    
    public String encoderName();
    
    public String decoderName();
    
    public ElmFunctionBuilder encoder();
    
    public ElmFunctionBuilder decoder();
    
    public default ElmFunctionBuilder listEncoder() {
        val listName    = camelName() + "List";
        val funcName    = listName + "Encoder";
        val declaration = "List " + typeName() + " -> Json.Encode.Value";
        val parameters  = listName;
        val body        = ILines.line("Json.Encode.list " + encoderName() + " " + listName);
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder listDecoder() {
        val listName    = camelName() + "List";
        val funcName    = listName + "Decoder";
        val declaration = "Json.Decode.Decoder (List " + typeName() + ")";
        val parameters  = "";
        val body        = ILines.line("Json.Decode.list " + decoderName());
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder decode() {
        val funcName    = "decode" + typeName();
        val declaration = "String -> Result Json.Decode.Error " + typeName();
        val parameters  = "";
        val body        = ILines.line("Json.Decode.decodeString " + decoderName());
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder decodeList() {
        val funcName    = "decode" + typeName() + "List";
        val declaration = "String -> Result Json.Decode.Error (List " + typeName() + ")";
        val parameters  = "";
        val body        = ILines.line("Json.Decode.decodeString " + camelName() + "ListDecoder");
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder encode() {
        val funcName    = "encode" + typeName();
        val declaration = typeName() + " -> Int -> String";
        val parameters  = camelName() + " indent";
        val body        = ILines.line(encoderName() + " " + camelName() + " |> Json.Encode.encode indent");
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder encodeList() {
        val funcName    = "encode" + typeName() + "List";
        val declaration = "List " + typeName() + " -> Int -> String";
        val parameters  = camelName() + "List indent";
        val body        = ILines.line(camelName() + "ListEncoder " + camelName() + "List |> Json.Encode.encode indent");
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
}
