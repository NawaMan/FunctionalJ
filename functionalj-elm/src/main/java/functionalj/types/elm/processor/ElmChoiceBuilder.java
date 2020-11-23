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
import static functionalj.types.elm.processor.UElmType.elmMayBeOfType;
import static functionalj.types.elm.processor.UElmType.elmParamType;
import static functionalj.types.elm.processor.UElmType.encoderNameOf;
import static functionalj.types.struct.generator.ILines.indent;
import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.ILines.linesOf;
import static functionalj.types.struct.generator.ILines.toLine;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.concat;

import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.choice.generator.model.Case;
import functionalj.types.choice.generator.model.CaseParam;
import functionalj.types.struct.generator.ILines;


/**
 * This class generate Elm choice type.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmChoiceBuilder implements ElmTypeDef {
    
    private final ElmChoiceSpec spec;
    
    public ElmChoiceBuilder(ElmChoiceSpec spec) {
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
        var definition = line("type " + spec.typeName());
        var choices    = spec.sourceSpec()
                .choices.stream()
                .map(toCaseTypes)
                .map(toLine);
        var lines = linesOf(choices)
                    .containWith("=", "|", null);
        return definition
                .append(indent(lines));
    }
    
    private static Function<CaseParam, String> toParamType = ElmChoiceBuilder::toParamType;
    private static String toParamType(CaseParam caseParam) {
        var paramType    = caseParam.type;
        var elmParamType = caseParam.isNullable 
                ? elmParamType(elmMayBeOfType(paramType))
                : elmParamType(paramType);
        return elmParamType;
    }
    
    private static Function<Case, String> toCaseTypes = ElmChoiceBuilder::toCaseTypes;
    private static String toCaseTypes(Case choice) {
        var name   = choice.name;
        var params = choice
                .params.stream()
                .map    (toParamType)
                .collect(joining(" "));
        return name + " " + params;
    }
    
    public ElmFunctionBuilder encoder() {
        var typeName    = spec.typeName();
        var name        = encoderName();
        var params      = camelName();
        var declaration = typeName + " -> Json.Encode.Value";
        
        var caseExpr = line("case " + name + " of");
        var choices  = linesOf(
                spec.sourceSpec()
                .choices.stream()
                .map(toCaseField));
        var body = caseExpr.append(choices.indent(1));
        return new ElmFunctionBuilder(name, declaration, params, body);
    }
    
    private static Function<Case, ILines> toCaseField = ElmChoiceBuilder::toCaseField;
    private static ILines toCaseField(Case choice) {
        var paramNameList = choice.params.stream().map(param -> param.name).collect(joining(" "));
        var matchCase     = ILines.line(choice.name + " " + paramNameList + " ->");
        var targetFunc    = ILines.line("Json.Encode.object");
        
        var taggedEncoder = line("( \"__tagged\", Json.Encode.string \"" + choice.name + "\" )").toStream();
        var encoders 
                = choice
                .params.stream()
                .map(toCaseParam)
                .map(toLine);
        var params = linesOf(concat(taggedEncoder, encoders))
                    .containWith("[", ",", "]")
                    .indent(1);
        return matchCase.append(
                    targetFunc.append(
                        params)
                        .indent(1));
    }
    
    private static Function<CaseParam, String> toCaseParam = ElmChoiceBuilder::toCaseParam;
    
    private static String toCaseParam(CaseParam caseParam) {
        var encoder = encoderNameOf(caseParam.type, caseParam.name, caseParam.isNullable);
        var name    = caseParam.name;
        return "( \"" + name + "\", " + encoder + " )";
    }
    
    public ElmFunctionBuilder decoder() {
        var typeName    = spec.typeName();
        var name        = decoderName();
        var declaration = "Json.Decode.Decoder " + typeName;
        var params      = "";
        
        var firstLines = line(
                "Json.Decode.field \"__tagged\" Json.Decode.string",
                "    |> Json.Decode.andThen",
                "        (\\str ->",
                "            case str of"
            );
        var fieldEncoders 
                = linesOf(
                    spec
                    .sourceSpec()
                    .choices
                    .stream()
                    .map(toFieldDecoder(typeName)))
                    .indent(4);
        var lastLines = line(
                "                somethingElse ->",
                "                    Json.Decode.fail <| \"Unknown tagged: \" ++ somethingElse",
                ")");
        var body = firstLines.append(fieldEncoders).append(lastLines);
        var encoder = new ElmFunctionBuilder(name, declaration, params, body);
        return encoder;
    }
    
    private static Function<Case, ILines> toFieldDecoder(String typeName) {
        return choice -> toFieldDecoder(typeName, choice);
    }
    private static ILines toFieldDecoder(String typeName, Case choice) {
        var fieldName   = choice.name;
        var firstLine   = line("\"" + fieldName + "\" ->");
        var secondLine  = line("    Json.Decode.succeed " + fieldName);
        var restOfLines = linesOf(choice.params.stream().map(toChoiceParamDecoder)).indent(2);
        return firstLine
                .append(secondLine)
                .append(restOfLines)
                .append(line(""));
    }
    
    private static Function<CaseParam, ILines> toChoiceParamDecoder = ElmChoiceBuilder::toChoiceParamDecoder;
    private static ILines toChoiceParamDecoder(CaseParam caseParam) {
        var caseType = caseParam.type;
        var isList   = caseType.isList()
                    || caseType.isFuncList();
        var bareType = (caseType.isNullable() || caseType.isOptional() || isList)
                     ? caseType.generics().get(0).toType()
                     : caseType;
        var isNullable = caseParam.isNullable
                      || caseType.isNullable()
                      || caseType.isOptional();
        var reqOrOpt = isNullable
                     ? "Json.Decode.Pipeline.optional"
                     : "Json.Decode.Pipeline.required";
        var quotedName = "\"" + caseParam.name + "\"";
        var decoderType = isList
                        ? "(Json.Decode.list " + decoderNameOf(bareType) + ")"
                        : ( isNullable
                            ? "(Json.Decode.nullable " + decoderNameOf(bareType) + ") Nothing"
                            : decoderNameOf(caseType));
        
        return line("|> " + reqOrOpt + " " + quotedName + " " + decoderType);
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
        var typeName    = typeName();
        var camalName   = camelName();
        var moduleName  = spec.moduleName();
        var content     = Stream.of(ilines).map(ILines::toText).collect(joining(separator));
        var fileContent = format(topTemplate, typeName, camalName, moduleName, content);
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
