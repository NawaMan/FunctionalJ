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
import lombok.val;

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
        val definition = line("type " + spec.typeName());
        val choices    = spec.sourceSpec()
                .choices.stream()
                .map(toCaseTypes)
                .map(toLine);
        val lines = linesOf(choices)
                    .containWith("=", "|", null);
        return definition
                .append(indent(lines));
    }
    
    private static Function<CaseParam, String> toParamType = ElmChoiceBuilder::toParamType;
    private static String toParamType(CaseParam caseParam) {
        val paramType    = caseParam.type;
        val elmParamType = caseParam.isNullable 
                ? elmParamType(elmMayBeOfType(paramType))
                : elmParamType(paramType);
        return elmParamType;
    }
    
    private static Function<Case, String> toCaseTypes = ElmChoiceBuilder::toCaseTypes;
    private static String toCaseTypes(Case choice) {
        val name   = choice.name;
        val params = choice
                .params.stream()
                .map    (toParamType)
                .collect(joining(" "));
        return name + " " + params;
    }
    
    public ElmFunctionBuilder encoder() {
        val typeName    = spec.typeName();
        val name        = encoderName();
        val params      = camelName();
        val declaration = typeName + " -> Json.Encode.Value";
        
        val caseExpr = line("case " + name + " of");
        val choices  = linesOf(
                spec.sourceSpec()
                .choices.stream()
                .map(toCaseField));
        val body = caseExpr.append(choices.indent(1));
        return new ElmFunctionBuilder(name, declaration, params, body);
    }
    
    private static Function<Case, ILines> toCaseField = ElmChoiceBuilder::toCaseField;
    private static ILines toCaseField(Case choice) {
        val paramNameList = choice.params.stream().map(param -> param.name).collect(joining(" "));
        val matchCase     = ILines.line(choice.name + " " + paramNameList + " ->");
        val targetFunc    = ILines.line("Json.Encode.object");
        
        val taggedEncoder = line("( \"__tagged\", Json.Encode.string \"" + choice.name + "\" )").toStream();
        val encoders 
                = choice
                .params.stream()
                .map(toCaseParam)
                .map(toLine);
        val params = linesOf(concat(taggedEncoder, encoders))
                    .containWith("[", ",", "]")
                    .indent(1);
        return matchCase.append(
                    targetFunc.append(
                        params)
                        .indent(1));
    }
    
    private static Function<CaseParam, String> toCaseParam = ElmChoiceBuilder::toCaseParam;
    
    private static String toCaseParam(CaseParam caseParam) {
        val encoder = encoderNameOf(caseParam.type, caseParam.name, caseParam.isNullable);
        val name    = caseParam.name;
        return "( \"" + name + "\", " + encoder + " )";
    }
    
    public ElmFunctionBuilder decoder() {
        val typeName    = spec.typeName();
        val name        = decoderName();
        val declaration = "Json.Decode.Decoder " + typeName;
        val params      = "";
        
        val firstLines = line(
                "Json.Decode.field \"__tagged\" Json.Decode.string",
                "    |> Json.Decode.andThen",
                "        (\\str ->",
                "            case str of"
            );
        val fieldEncoders 
                = linesOf(
                    spec
                    .sourceSpec()
                    .choices
                    .stream()
                    .map(toFieldDecoder(typeName)))
                    .indent(4);
        val lastLines = line(
                "                somethingElse ->",
                "                    Json.Decode.fail <| \"Unknown tagged: \" ++ somethingElse",
                ")");
        val body = firstLines.append(fieldEncoders).append(lastLines);
        val encoder = new ElmFunctionBuilder(name, declaration, params, body);
        return encoder;
    }
    
    private static Function<Case, ILines> toFieldDecoder(String typeName) {
        return choice -> toFieldDecoder(typeName, choice);
    }
    private static ILines toFieldDecoder(String typeName, Case choice) {
        val fieldName   = choice.name;
        val firstLine   = line("\"" + fieldName + "\" ->");
        val secondLine  = line("    Json.Decode.succeed " + fieldName);
        val restOfLines = linesOf(choice.params.stream().map(toChoiceParamDecoder)).indent(2);
        return firstLine
                .append(secondLine)
                .append(restOfLines)
                .append(line(""));
    }
    
    private static Function<CaseParam, ILines> toChoiceParamDecoder = ElmChoiceBuilder::toChoiceParamDecoder;
    private static ILines toChoiceParamDecoder(CaseParam caseParam) {
        val caseType = caseParam.type;
        val isList   = caseType.isList()
                    || caseType.isFuncList();
        val bareType = (caseType.isNullable() || caseType.isOptional() || isList)
                     ? caseType.generics().get(0).toType()
                     : caseType;
        val isNullable = caseParam.isNullable
                      || caseType.isNullable()
                      || caseType.isOptional();
        val reqOrOpt = isNullable
                     ? "Json.Decode.Pipeline.optional"
                     : "Json.Decode.Pipeline.required";
        val quotedName = "\"" + caseParam.name + "\"";
        val decoderType = isList
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
