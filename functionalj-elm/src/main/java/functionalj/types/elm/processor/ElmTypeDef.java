// ============================================================================
// Copyright(c) 2017-2020 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
        var camelName = Utils.toCamelCase(typeName());
        return camelName;
    }
    
    public String encoderName();
    
    public String decoderName();
    
    public ElmFunctionBuilder encoder();
    
    public ElmFunctionBuilder decoder();
    
    public default ElmFunctionBuilder listEncoder() {
        var listName    = camelName() + "List";
        var funcName    = listName + "Encoder";
        var declaration = "List " + typeName() + " -> Json.Encode.Value";
        var parameters  = listName;
        var body        = ILines.line("Json.Encode.list " + encoderName() + " " + listName);
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder listDecoder() {
        var listName    = camelName() + "List";
        var funcName    = listName + "Decoder";
        var declaration = "Json.Decode.Decoder (List " + typeName() + ")";
        var parameters  = "";
        var body        = ILines.line("Json.Decode.list " + decoderName());
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder decode() {
        var funcName    = "decode" + typeName();
        var declaration = "String -> Result Json.Decode.Error " + typeName();
        var parameters  = "";
        var body        = ILines.line("Json.Decode.decodeString " + decoderName());
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder decodeList() {
        var funcName    = "decode" + typeName() + "List";
        var declaration = "String -> Result Json.Decode.Error (List " + typeName() + ")";
        var parameters  = "";
        var body        = ILines.line("Json.Decode.decodeString " + camelName() + "ListDecoder");
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder encode() {
        var funcName    = "encode" + typeName();
        var declaration = typeName() + " -> Int -> String";
        var parameters  = camelName() + " indent";
        var body        = ILines.line(encoderName() + " " + camelName() + " |> Json.Encode.encode indent");
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
    public default ElmFunctionBuilder encodeList() {
        var funcName    = "encode" + typeName() + "List";
        var declaration = "List " + typeName() + " -> Int -> String";
        var parameters  = camelName() + "List indent";
        var body        = ILines.line(camelName() + "ListEncoder " + camelName() + "List |> Json.Encode.encode indent");
        return new ElmFunctionBuilder(funcName, declaration, parameters, body);
    }
    
}
