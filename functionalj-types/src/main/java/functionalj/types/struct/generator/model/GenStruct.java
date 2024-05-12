// ============================================================================
// Copyright (c) 2017-2024 Nawapunth Manusitthipol (NawaMan - http://nawaman.net)
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
import static functionalj.types.struct.generator.model.utils.samePackage;
import static functionalj.types.struct.generator.model.utils.themAll;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import functionalj.types.Core;
import functionalj.types.IPostConstruct;
import functionalj.types.Type;
import functionalj.types.struct.generator.ILines;
import functionalj.types.struct.generator.SourceSpec;
import functionalj.types.struct.generator.StructSpec;

/**
 * Representation of Struct class.
 *
 * @author NawaMan -- nawa@nawaman.net
 */
public class GenStruct implements ILines {
    
    private static final List<String> implicitImports = asList("java.lang.String", "java.lang.Integer", "java.lang.Boolean", ".int", ".boolean");
    
    private static final List<Type> alwaysImports = asList(Type.of(IPostConstruct.class), Core.ObjectLensImpl.type(), Core.LensSpec.type());
    
    private SourceSpec sourceSpec;
    
    private StructSpec dataClass;
    
    /**
     * Construct a GenStruct with the data object spec.
     *
     * @param  sourceSpec   the source spec.
     * @param  dataObjSpec  the struct spec.
     */
    public GenStruct(SourceSpec sourceSpec, StructSpec dataObjSpec) {
        this.sourceSpec = sourceSpec;
        this.dataClass = dataObjSpec;
    }
    
    public Stream<String> lines() {
        String         packageName = dataClass.type().packageName();
        Stream<String> importList  = importListLines();
        List<String>   importLines = importList.filter(importName -> !samePackage(packageName, importName)).map(importName -> "import " + importName + ";").collect(toList());
        String         packageDef  = "package " + packageName + ";";
        ILines         dataObjDef  = dataClass.getClassSpec().toDefinition(packageName);
        String         specName    = (sourceSpec.getSpecName() == null) ? "" : "." + sourceSpec.getSpecName();
        String         source      = sourceSpec.getPackageName() + "." + sourceSpec.getEncloseName() + specName;
        LocalDateTime  genTime     = LocalDateTime.now();
        String         generated   = "@Generated(value = \"FunctionalJ\",date = \"" + genTime + "\", comments = \"" + source + "\")";
        String         suppress    = "@SuppressWarnings(\"all\")";
        ILines         lines       = linesOf(Stream.of(line(packageDef), line(importLines), line(generated), line(suppress), dataObjDef).filter(Objects::nonNull).flatMap(utils.delimitWith(emptyLine)));
        return lines.lines();
    }
    
    private Stream<String> importListLines() {
        Set<Type> types = new HashSet<Type>();
        types.add(Core.Generated.type());
        dataClass.fields().stream().flatMap(GenField::requiredTypes).forEach(types::add);
        dataClass.methods().stream().flatMap(GenMethod::requiredTypes).forEach(types::add);
        dataClass.constructors().stream().flatMap(GenConstructor::requiredTypes).forEach(types::add);
        dataClass.innerClasses().stream().flatMap(GenClass::requiredTypes).forEach(types::add);
        dataClass.extendeds().forEach(types::add);
        dataClass.implementeds().forEach(types::add);
        types.remove(dataClass.type());
        
        List<Type>        lensImport    = types.stream().filter(Objects::nonNull).collect(toList());
        String            thisPackage   = (String) dataClass.type().packageName();
        String            thisEnclose   = (String) dataClass.type().encloseName();
        String            thisClassName = (String) dataClass.type().simpleName();
        List<String>      withLens      = sourceSpec.getTypeWithLens();
        String            lensClass     = (String) dataClass.type().lensType(thisPackage, thisEnclose, withLens).fullName(thisPackage);
        String            superClass    = (String) dataClass.getSourcePackageName() + "." + dataClass.getSourceClassName();
        Predicate<String> isLensClass   = (Predicate<String>) ((String name) -> name.equals(lensClass));
        Predicate<String> isSuperClass  = (Predicate<String>) ((String name) -> name.equals(superClass));
        List<Type>        importTypes   = (List<Type>) asList(alwaysImports.stream(), lensImport.stream(), types.stream(), dataClass.innerClasses().stream().map(GenClass::requiredTypes).flatMap(themAll())).stream().flatMap(themAll()).collect(toList());
        Stream<String>    importList    = importTypes.stream().filter(type -> !type.isVirtual()).filter(type -> !thisPackage.equals(type.packageName()) || !Objects.equals(thisClassName, type.encloseName())).map(Type::declaredType).map(Type::fullName).filter(type -> !implicitImports.contains(type)).filter(isLensClass.negate()).filter(isSuperClass.negate()).sorted().distinct();
        return importList;
    }
}
