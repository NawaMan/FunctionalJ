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
package functionalj.types.struct.generator.model;

import static functionalj.types.struct.generator.ILines.line;
import static functionalj.types.struct.generator.ILines.linesOf;
import static functionalj.types.struct.generator.model.utils.themAll;
import static functionalj.types.struct.generator.model.utils.wrapWith;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import functionalj.types.IPostConstruct;
import functionalj.types.Type;
import functionalj.types.struct.Core;
import functionalj.types.struct.generator.ILines;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.StructSpec;
import lombok.val;


/**
 * Representation of Struct class.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class GenStruct implements ILines {
    
    private static final List<String> implicitImports = asList(
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Boolean",
            ".int",
            ".boolean"
    );
    
    private static final List<Type> alwaysImports = asList(
            Type.of(IPostConstruct.class),
            Core.ObjectLensImpl.type(),
//            Core.ObjectLens.type(),
            Core.LensSpec.type()
    );
    
    private SourceSpec sourceSpec;
    private StructSpec dataClass;
    
    /**
     * Construct a GenStruct with the data object spec.
     *
     * @param dataObjSpec  the spec.
     */
    public GenStruct(SourceSpec sourceSpec, StructSpec dataObjSpec) {
        this.sourceSpec = sourceSpec;
        this.dataClass  = dataObjSpec;
    }
    
    public Stream<String> lines() {
        val importList  = importListLines();
        val imports     = importList.map(wrapWith("import ", ";")).collect(toList());
        val packageName = dataClass.type().packageName();
        val packageDef  = "package " + packageName + ";";
        val dataObjDef  = dataClass.getClassSpec().toDefinition(packageName);
        val lines
                = linesOf(Stream.of(
                    line(packageDef),
                    line(imports),
                    line("// " + sourceSpec.getPackageName() + "." + sourceSpec.getEncloseName() + "." + sourceSpec.getSpecName()),
                    dataObjDef
                )
                .filter (Objects::nonNull)
                .flatMap(utils.delimitWith(emptyLine)));
        return lines.lines();
    }
    
    private Stream<String> importListLines() {
        val types = new HashSet<Type>();
        dataClass.fields()      .stream().flatMap(GenField      ::requiredTypes).forEach(types::add);
        dataClass.methods()     .stream().flatMap(GenMethod     ::requiredTypes).forEach(types::add);
        dataClass.constructors().stream().flatMap(GenConstructor::requiredTypes).forEach(types::add);
        dataClass.innerClasses().stream().flatMap(GenClass      ::requiredTypes).forEach(types::add);
        dataClass.extendeds()   .forEach(types::add);
        dataClass.implementeds().forEach(types::add);
        
        types.remove(dataClass.type());
        
        val lensImport = types.stream()
                .filter (Objects::nonNull)
                .collect(toList());
        
        val thisPackage   = (String)dataClass.type().packageName();
        val thisEnclose   = (String)dataClass.type().encloseName();
        val thisClassName = (String)dataClass.type().simpleName();
        val withLens      = sourceSpec.getTypeWithLens();
        val lensClass     = (String)dataClass.type().lensType(thisPackage, thisEnclose, withLens).fullName(thisPackage);
        val superClass    = (String)dataClass.getSourcePackageName() + "." + dataClass.getSourceClassName();
        val isLensClass   = (Predicate<String>)((String name) -> name.equals(lensClass));
        val isSuperClass  = (Predicate<String>)((String name) -> name.equals(superClass));
        
        val importTypes = (List<Type>)asList(
                alwaysImports.stream(),
                lensImport.stream(),
                types.stream(),
                dataClass.innerClasses().stream()
                    .map    (GenClass::requiredTypes)
                    .flatMap(themAll())
            ).stream()
            .flatMap(themAll())
            .collect(toList());
        
        val importList = importTypes.stream()
                .filter(type->!type.isVirtual())
                .filter(type->!thisPackage.equals(type.packageName()) || !Objects.equals(thisClassName, type.encloseName()))
                .map   (Type::declaredType)
                .map   (Type::fullName)
                .filter(type -> !implicitImports.contains(type))
                .filter(isLensClass.negate())
                .filter(isSuperClass.negate())
                .sorted()
                .distinct();
        return importList;
    }
    
}
