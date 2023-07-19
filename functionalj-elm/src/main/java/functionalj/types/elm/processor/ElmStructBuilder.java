// ============================================================================
// Copyright (c) 2017-2021 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static functionalj.types.elm.processor.UElmType.encoderNameOf;
import static functionalj.types.elm.processor.Utils.toCamelCase;
import static functionalj.types.struct.generator.ILines.indent;
import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.ILines.linesOf;
import static functionalj.types.struct.generator.ILines.toLine;
import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import functionalj.types.Type;
import functionalj.types.struct.generator.Getter;
import functionalj.types.struct.generator.ILines;

/**
 * This class generate Elm structure type.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class ElmStructBuilder implements ElmTypeDef {
    
    private final ElmStructSpec spec;
    
    // private final List<String>  structTypes;
    private final List<String> choiceTypes;
    
    public ElmStructBuilder(ElmStructSpec spec, List<String> structTypes, List<String> choiceTypes) {
        this.spec = spec;
        // this.structTypes = structTypes;
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
        ILines         definition         = line("type alias " + spec.typeName() + " = ");
        String         currentPackageName = spec.sourceSpec().getPackageName();
        String         currentSimpleName  = spec.sourceSpec().toType().simpleName();
        Stream<ILines> fields             = spec.sourceSpec().getGetters().stream().map(getter -> toField(currentPackageName, currentSimpleName, getter)).map(toLine);
        ILines         lines              = linesOf(fields).containWith("{", ",", "}");
        return definition.append(indent(lines));
    }
    
    public ElmFunctionBuilder encoder() {
        String typeName      = spec.typeName();
        String name          = encoderName();
        String declaration   = typeName + " -> Json.Encode.Value";
        String params        = camelName();
        ILines firstLine     = line("Json.Encode.object");
        ILines fieldEncoders = linesOf(spec.sourceSpec().getGetters().stream().map(toFieldEncoder(typeName)).map(toLine)).containWith("[", ",", "]").indent(1);
        ILines body          = firstLine.append(fieldEncoders);
        return new ElmFunctionBuilder(name, declaration, params, body);
    }
    
    private static String toField(String currentPackageName, String currentSimpleName, Getter getter) {
        String fieldName = getter.name();
        String emlType   = emlType(currentPackageName, currentSimpleName, getter.type());
        String maybe     = getter.isNullable() ? "Maybe " : "";
        return fieldName + " : " + maybe + emlType;
    }
    
    private static String emlType(String currentPackageName, String currentSimpleName, Type type) {
        String typePackageName = type.packageName();
        String typeSimpleName  = type.simpleName();
        if (currentPackageName.equals(typePackageName) && !currentSimpleName.equals(typeSimpleName)) {
            return typeSimpleName;
        }
        return UElmType.emlType(type);
    }
    
    private static Function<Getter, String> toFieldEncoder(String typeName) {
        return getter -> toFieldEncoder(typeName, getter);
    }
    
    private static String toFieldEncoder(String typeName, Getter getter) {
        String fieldName   = getter.name();
        String camelName   = toCamelCase(typeName);
        String typeEncoder = encoderNameOf(getter.type(), camelName + "." + fieldName, getter.isNullable());
        return "( \"" + fieldName + "\", " + typeEncoder + " )";
    }
    
    public ElmFunctionBuilder decoder() {
        String typeName      = spec.typeName();
        String name          = decoderName();
        String declaration   = "Json.Decode.Decoder " + typeName;
        String params        = "";
        ILines firstLine     = line("Json.Decode.succeed " + typeName);
        ILines fieldEncoders = linesOf(spec.sourceSpec().getGetters().stream().map(toFieldDecoder(typeName)).map(toLine)).indent(1);
        ILines body          = firstLine.append(fieldEncoders);
        return new ElmFunctionBuilder(name, declaration, params, body);
    }
    
    private static Function<Getter, String> toFieldDecoder(String typeName) {
        return getter -> toFieldDecoder(typeName, getter);
    }
    
    private static String toFieldDecoder(String typeName, Getter getter) {
        String fieldName      = getter.name();
        String rawTypeDecoder = decoderNameOf(getter.type());
        String qualifier      = getter.isRequired() ? "required" : "optional";
        String typeDecoder    = getter.isRequired() ? rawTypeDecoder : ("(Json.Decode.maybe " + rawTypeDecoder + ") Nothing");
        return "|> Json.Decode.Pipeline." + qualifier + " \"" + fieldName + "\" " + typeDecoder;
    }
    
    private static final String topTemplate = "-- Generated by FunctionJ.io ( https://functionalj.io ) on %6$s \n" + "module %3$s exposing\n" + "    ( %1$s\n" + "    , %2$sEncoder\n" + "    , %2$sDecoder\n" + "    , encode%1$s\n" + "    , decode%1$s\n" + "    , %2$sListEncoder\n" + "    , %2$sListDecoder\n" + "    , encode%1$sList\n" + "    , decode%1$sList\n" + "    )\n" + "\n" + "import Json.Decode\n" + "import Json.Decode.Pipeline\n" + "import Json.Encode\n" + "\n" + "%4$s\n" + "\n" + "\n" + "-- elm install elm/json\n" + "-- elm install NoRedInk/elm-json-decode-pipeline" + "\n" + "\n" + "%5$s" + "\n";
    
    private static final String separator = "\n\n\n";
    
    private String fileTemplate(ILines... ilines) {
        LocalDateTime genTime = LocalDateTime.now();
        String typeName    = typeName();
        String camalName   = camelName();
        String moduleName  = spec.moduleName();
        String imports     = imports();
        String content     = Stream.of(ilines).map(ILines::toText).collect(joining(separator));
        String fileContent = format(topTemplate, typeName, camalName, moduleName, imports, content, genTime);
        return fileContent;
    }
    
    private String imports() {
        String currentPackageName = spec.sourceSpec().getPackageName();
        String currentSimpleName  = spec.sourceSpec().toType().simpleName();
        String getters            = spec.sourceSpec().getGetters().stream().map(Getter::type).filter(type -> currentPackageName.equals(type.packageName())).filter(type -> !currentSimpleName.equals(type.simpleName())).map(type -> localImport(type)).collect(joining(",\n    "));
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
