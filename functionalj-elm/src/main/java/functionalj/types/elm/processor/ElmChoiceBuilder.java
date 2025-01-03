// ============================================================================
// Copyright (c) 2017-2025 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.Type;
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
    
    private final List<String> choiceTypes;
    
    public ElmChoiceBuilder(ElmChoiceSpec spec, List<String> choiceTypes) {
        this.spec = spec;
        this.choiceTypes = choiceTypes;
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
        ILines         definition = line("type " + spec.typeName());
        Stream<ILines> choices    = spec.sourceSpec().choices.stream().map(toCaseTypes).map(toLine);
        ILines         lines      = linesOf(choices).containWith("=", "|", null);
        return definition.append(indent(lines));
    }
    
    private static Function<CaseParam, String> toParamType = ElmChoiceBuilder::toParamType;
    
    private static String toParamType(CaseParam caseParam) {
        Type   paramType    = caseParam.type();
        String elmParamType = caseParam.isNullable() ? elmParamType(elmMayBeOfType(paramType)) : elmParamType(paramType);
        return elmParamType;
    }
    
    private static Function<Case, String> toCaseTypes = ElmChoiceBuilder::toCaseTypes;
    
    private static String toCaseTypes(Case choice) {
        String name   = choice.name;
        String params = choice.params.stream().map(toParamType).collect(joining(" "));
        return name + " " + params;
    }
    
    public ElmFunctionBuilder encoder() {
        String typeName    = spec.typeName();
        String name        = encoderName();
        String camelName   = camelName();
        String declaration = typeName + " -> Json.Encode.Value";
        ILines caseExpr    = line("case " + camelName + " of");
        ILines choices     = linesOf(spec.sourceSpec().choices.stream().map(toCaseField));
        ILines body        = caseExpr.append(choices.indent(1));
        return new ElmFunctionBuilder(name, declaration, camelName, body);
    }
    
    private static Function<Case, ILines> toCaseField = ElmChoiceBuilder::toCaseField;
    
    private static ILines toCaseField(Case choice) {
        String         paramNameList = choice.params.stream().map(param -> param.name()).collect(joining(" "));
        ILines         matchCase     = line(choice.name + " " + paramNameList + " ->");
        ILines         targetFunc    = line("Json.Encode.object");
        Stream<ILines> taggedEncoder = line("( \"__tagged\", Json.Encode.string \"" + choice.name + "\" )").toStream();
        Stream<ILines> encoders      = choice.params.stream().map(toCaseParam).map(toLine);
        ILines         params        = linesOf(concat(taggedEncoder, encoders)).containWith("[", ",", "]").indent(1);
        return matchCase.append(targetFunc.append(params).indent(1));
    }
    
    private static Function<CaseParam, String> toCaseParam = ElmChoiceBuilder::toCaseParam;
    
    private static String toCaseParam(CaseParam caseParam) {
        String encoder = encoderNameOf(caseParam.type(), caseParam.name(), caseParam.isNullable());
        String name = caseParam.name();
        return "( \"" + name + "\", " + encoder + " )";
    }
    
    public ElmFunctionBuilder decoder() {
        String typeName      = spec.typeName();
        String name          = decoderName();
        String declaration   = "Json.Decode.Decoder " + typeName;
        String params        = "";
        ILines firstLines    = line("Json.Decode.field \"__tagged\" Json.Decode.string", "    |> Json.Decode.andThen", "        (\\str ->", "            case str of");
        ILines fieldEncoders = linesOf(spec.sourceSpec().choices.stream().map(toFieldDecoder(typeName))).indent(4);
        ILines lastLines     = line("                somethingElse ->", "                    Json.Decode.fail <| \"Unknown tagged: \" ++ somethingElse", ")");
        ILines body = firstLines.append(fieldEncoders).append(lastLines);
        return new ElmFunctionBuilder(name, declaration, params, body);
    }
    
    private static Function<Case, ILines> toFieldDecoder(String typeName) {
        return choice -> toFieldDecoder(typeName, choice);
    }
    
    private static ILines toFieldDecoder(String typeName, Case choice) {
        String fieldName   = choice.name;
        ILines firstLine   = line("\"" + fieldName + "\" ->");
        ILines secondLine  = line("    Json.Decode.succeed " + fieldName);
        ILines restOfLines = linesOf(choice.params.stream().map(toChoiceParamDecoder)).indent(2);
        return firstLine.append(secondLine).append(restOfLines).append(line(""));
    }
    
    private static Function<CaseParam, ILines> toChoiceParamDecoder = ElmChoiceBuilder::toChoiceParamDecoder;
    
    private static ILines toChoiceParamDecoder(CaseParam caseParam) {
        Type   caseType     = caseParam.type();
        boolean isList      = caseType.isList() || caseType.isFuncList();
        Type    bareType    = (caseType.isNullable() || caseType.isOptional() || isList) ? caseType.generics().get(0).toType() : caseType;
        boolean isNullable  = caseParam.isNullable() || caseType.isNullable() || caseType.isOptional();
        String  reqOrOpt    = isNullable ? "Json.Decode.Pipeline.optional" : "Json.Decode.Pipeline.required";
        String  quotedName  = "\"" + caseParam.name() + "\"";
        String  decoderType = isList ? "(Json.Decode.list " + decoderNameOf(bareType) + ")" : (isNullable ? "(Json.Decode.maybe " + decoderNameOf(bareType) + ") Nothing" : decoderNameOf(caseType));
        return line("|> " + reqOrOpt + " " + quotedName + " " + decoderType);
    }
    
    private static final String topTemplate = "-- Generated by FunctionJ.io ( https://functionalj.io ) on %6$s \n" + "module %3$s exposing\n" + "    ( %1$s(..)\n" + "    , %2$sEncoder\n" + "    , %2$sDecoder\n" + "    , encode%1$s\n" + "    , decode%1$s\n" + "    , %2$sListEncoder\n" + "    , %2$sListDecoder\n" + "    , encode%1$sList\n" + "    , decode%1$sList\n" + "    )\n" + "\n" + "import Json.Decode\n" + "import Json.Decode.Pipeline\n" + "import Json.Encode\n" + "\n" + "%4$s\n" + "\n" + "\n" + "-- elm install elm/json\n" + "-- elm install NoRedInk/elm-json-decode-pipeline" + "\n" + "\n" + "%5$s" + "\n";
    
    private static final String separator = "\n\n\n";
    
    private String fileTemplate(ILines... ilines) {
        LocalDateTime genTime     = LocalDateTime.now();
        String        typeName    = typeName();
        String        camalName   = camelName();
        String        moduleName  = spec.moduleName();
        String        imports     = imports();
        String        content     = Stream.of(ilines).map(ILines::toText).collect(joining(separator));
        String        fileContent = format(topTemplate, typeName, camalName, moduleName, imports, content, genTime);
        return fileContent;
    }
    
    private String imports() {
        String currentPackageName = spec.sourceSpec().sourceType.packageName();
        String currentSimpleName = spec.sourceSpec().sourceType.simpleName();
        String getters = spec.sourceSpec().choices.stream().flatMap(choice -> choice.params.stream()).map(param -> param.type()).filter(type -> currentPackageName.equals(type.packageName())).filter(type -> !currentSimpleName.equals(type.simpleName())).map(type -> localImport(type)).collect(joining(",\n    "));
        ;
        return getters;
    }
    
    private String localImport(Type type) {
        String  typeSimpleName = type.simpleName();
        boolean isChoice       = choiceTypes.contains(typeSimpleName);
        String  specific       = isChoice ? ("," + typeSimpleName + "(..)") : "";
        return "import " + typeSimpleName + " exposing (.." + specific + ")";
    }
    
    public String toElmCode() {
        return fileTemplate(typeDefinition(), encoder(), decoder(), encode(), decode(), listEncoder(), listDecoder(), encodeList(), decodeList());
    }
}
