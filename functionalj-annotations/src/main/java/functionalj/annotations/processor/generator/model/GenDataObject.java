//  ========================================================================
//  Copyright (c) 2017 Nawapunth Manusitthipol (NawaMan).
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
package functionalj.annotations.processor.generator.model;

import static functionalj.annotations.processor.generator.ILines.line;
import static functionalj.annotations.processor.generator.ILines.linesOf;
import static functionalj.annotations.processor.generator.model.utils.themAll;
import static functionalj.annotations.processor.generator.model.utils.wrapWith;
import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import functionalj.annotations.IPostReConstruct;
import functionalj.annotations.processor.Core;
import functionalj.annotations.processor.generator.DataObjectSpec;
import functionalj.annotations.processor.generator.ILines;
import functionalj.annotations.processor.generator.Type;
import lombok.val;

/**
 * Representation of DataObject class.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public class GenDataObject implements ILines {
    
    private static final List<String> implicitImports = asList(
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Boolean",
            ".int",
            ".boolean"
    );
    
    private static final List<Type> alwaysImports = asList(
            Type.of(IPostReConstruct.class),
            Core.ObjectLensImpl.type(),
            Core.LensSpec.type()
    );
    
    private DataObjectSpec dataClass;
    
    /**
     * Construct a GenDataObject with the data object spec.
     * 
     * @param dataObjSpec  the spec.
     */
    public GenDataObject(DataObjectSpec dataObjSpec) {
        this.dataClass = dataObjSpec;
    }
    
    public Stream<String> lines() {
        val importList = importListLines();
        val imports    = importList.map(wrapWith("import ", ";")).collect(toList());
        String packageDef = "package " + dataClass.type().packageName() + ";";
        ILines dataObjDef = dataClass.getClassSpec().toDefinition();
        ILines lines
                = linesOf(Stream.of(
                    line(packageDef),
                    line(imports),
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
        dataClass.extendeds()   .forEach(types::add);
        dataClass.implementeds().forEach(types::add);
        
        types.remove(dataClass.type());
        
        val lensImport = types.stream()
                .filter (Objects::nonNull)
                .collect(toList());
        
        val thisPackage  = (String)dataClass.type().packageName();
        val lensClass    = (String)dataClass.type().lensType().fullName();
        val superClass   = (String)dataClass.getSourcePackageName() + "." + dataClass.getSourceClassName();
        val isLensClass  = (Predicate<String>)((String name) -> name.equals(lensClass));
        val isSuperClass = (Predicate<String>)((String name) -> name.equals(superClass));
        
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
                .filter(type->!thisPackage.equals(type.packageName()))
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
