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

import static functionalj.types.elm.processor.UElmType.decoderNameOf;
import static functionalj.types.elm.processor.UElmType.emlType;
import static functionalj.types.elm.processor.UElmType.encoderNameOf;
import static functionalj.types.elm.processor.Utils.toCamelCase;
import static functionalj.types.struct.generator.ILines.indent;
import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.ILines.linesOf;
import static functionalj.types.struct.generator.ILines.toLine;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.ILines;
import lombok.val;


/**
 * This class generate Elm structure type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmStructBuilder implements ElmTypeDef {
    
    private final ElmStructSpec spec;
    
    public ElmStructBuilder(ElmStructSpec spec) {
        this.spec = spec;
    }
    
    public String typeName() {
        return spec.typeName();
    }
    
    public String encoderName() {
        return camelName() + "Encoder";
    }
    
    public String decoderName() {
        return camelName() + "Decoder";
    }
    
    public ILines typeDefinition() {
        val definition = line("type alias " + spec.typeName() + " = ");
        val fields = spec
            .sourceSpec()
            .getGetters()
            .stream()
            .map(toField)
            .map(toLine);
        val lines = linesOf(fields)
                  .containWith("{", ",", "}");
        return definition
                .append(indent(lines));
    }
    
    public ElmFunctionBuilder encoder() {
        val typeName    = spec.typeName();
        val name        = encoderName();
        val declaration = typeName + " -> Json.Encode.Value";
        val params      = camelName();
        
        val firstLine = line("Json.Encode.object");
        val fieldEncoders 
                = linesOf(
                    spec
                    .sourceSpec()
                    .getGetters()
                    .stream()
                    .map(toFieldEncoder(typeName))
                    .map(toLine))
                    .containWith("[", ",", "]")
                    .indent(1);
        val body    = firstLine.append(fieldEncoders);
        val encoder = new ElmFunctionBuilder(name, declaration, params, body);
        return encoder;
    }
    
    private static Function<Getter, String> toField = ElmStructBuilder::toField;
    private static String toField(Getter getter) {
        val fieldName = getter.getName();
        val emlType   = emlType(getter.getType());
        return fieldName + " : " + emlType;
    }
    
    private static Function<Getter, String> toFieldEncoder(String typeName) {
        return getter -> toFieldEncoder(typeName, getter);
    }
    private static String toFieldEncoder(String typeName, Getter getter) {
        val fieldName   = getter.getName();
        val camelName   = toCamelCase   (typeName);
        val typeEncoder = encoderNameOf (getter.getType(), camelName + "." + fieldName, getter.isNullable());
        return "( \"" + fieldName + "\", " + typeEncoder + " )";
    }
    
    public ElmFunctionBuilder decoder() {
        val typeName    = spec.typeName();
        val name        = decoderName();
        val declaration = "Json.Decode.Decoder " + typeName;
        val params      = "";
        
        val firstLine = line("Json.Decode.succeed " + typeName);
        val fieldEncoders 
                = linesOf(
                    spec
                    .sourceSpec()
                    .getGetters()
                    .stream()
                    .map(toFieldDecoder(typeName))
                    .map(toLine))
                    .indent(1);
        val body    = firstLine.append(fieldEncoders);
        val encoder = new ElmFunctionBuilder(name, declaration, params, body);
        return encoder;
    }
    
    private static Function<Getter, String> toFieldDecoder(String typeName) {
        return getter -> toFieldDecoder(typeName, getter);
    }
    private static String toFieldDecoder(String typeName, Getter getter) {
        val fieldName   = getter.getName();
        val typeDecoder = decoderNameOf (getter.getType());
        val qualifier   = getter.isRequired() ? "required" : "optional";
        return "|> Json.Decode.Pipeline." + qualifier + " \"" + fieldName + "\" " + typeDecoder;
    }
    
    private static final String topTemplate = 
            "module %3$s exposing\n" + 
            "    ( %1$s\n" + 
            "    , %2$sEncoder\n" + 
            "    , %2$sDecoder\n" + 
            "    , encode%1$s\n" + 
            "    , decode%1$s\n" + 
            "    , %2$sListEncoder\n" + 
            "    , %2$sListDecoder\n" + 
            "    , encode%1$sList\n" + 
            "    , decode%1$sList\n" + 
            "    )\n" + 
            "\n" + 
            "import Json.Decode\n" + 
            "import Json.Decode.Pipeline\n" + 
            "import Json.Encode\n" + 
            "\n" + 
            "\n" + 
            "-- elm install elm/json\n" + 
            "-- elm install NoRedInk/elm-json-decode-pipeline" +
            "\n" + 
            "\n" + 
            "%4$s" + 
            "\n"
            ;
    
    private static final String separator = "\n\n\n";
    
    private String fileTemplate(ILines ... ilines) {
        val typeName    = typeName();
        val camalName   = camelName();
        val moduleName  = spec.moduleName();
        val content     = Stream.of(ilines).map(ILines::toText).collect(joining(separator));
        val fileContent = format(topTemplate, typeName, camalName, moduleName, content);
        return fileContent;
    }
    
    public String toElmCode() {
        return fileTemplate(
                typeDefinition(),
                encoder(),
                decoder(),
                encode(),
                decode(),
                listEncoder(),
                listDecoder(),
                encodeList(),
                decodeList());
    }
    
}
