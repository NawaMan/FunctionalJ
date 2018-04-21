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
package nawaman.functionalj.annotations.processor.generator;

import static java.util.Arrays.asList;
import static nawaman.functionalj.FunctionalJ.themAll;
import static nawaman.functionalj.annotations.processor.generator.ILines.line;
import static nawaman.functionalj.annotations.processor.generator.ILines.linesOf;
import static nawaman.functionalj.functions.StringFunctions.wrapWith;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

import lombok.val;
import nawaman.functionalj.annotations.processor.generator.model.GenClass;
import nawaman.functionalj.annotations.processor.generator.model.GenConstructor;
import nawaman.functionalj.annotations.processor.generator.model.GenField;
import nawaman.functionalj.annotations.processor.generator.model.GenMethod;
import nawaman.functionalj.lens.IPostConstruct;
import nawaman.functionalj.lens.LensSpec;
import nawaman.functionalj.lens.ObjectLensImpl;

public class DataObjectCode implements ILines {
    
    private static final List<String> implicitImports = asList(
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Boolean",
            ".int",
            ".boolean"
    );
    
    private static final List<Type> alwaysImports = asList(
            Type.of(IPostConstruct.class),
            Type.of(ObjectLensImpl.class),
            Type.of(LensSpec.class)
    );
    
    private DataObjectSpec dataClass;
    
    public DataObjectCode(DataObjectSpec dataObjSpec) {
        this.dataClass = dataObjSpec;
    }
    
    public Stream<String> lines() {
        val importList = importListLines();
        val imports    = importList.map(wrapWith("import ", ";")).collect(toList());
        val packageDef = "package " + dataClass.packageName() + ";";
        val dataObjDef = dataClass.getClassSpec().toDefinition();
        val lines
                = linesOf(
                    line(packageDef),
                    emptyLine,
                    line(imports),
                    emptyLine,
                    dataObjDef
                );
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
                .filter(Objects::nonNull)
                .collect(toList());
        
        val thisPackage  = (String)dataClass.packageName();
        val lensClass    = (String)dataClass.type().lensType().fullName();
        val superClass   = (String)dataClass.getSourcePackageName() + "." + dataClass.getSourceClassName();
        val isLensClass  = (Predicate<String>)((String name) -> name.equals(lensClass));
        val isSuperClass = (Predicate<String>)((String name) -> name.equals(superClass));
        
        val importTypes = (List<Type>)asList(
                alwaysImports.stream(),
                lensImport.stream(),
                types.stream(),
                dataClass.innerClasses().stream()
                    .map(GenClass::requiredTypes)
                    .flatMap(themAll())
            ).stream()
            .flatMap(themAll())
            .collect(toList());
        val importList = importTypes.stream()
                .filter(type->!thisPackage.equals(type.packageName()))
                .map(Type::declaredType)
                .map(Type::fullName)
                .filter(type -> !implicitImports.contains(type))
                .filter(isLensClass.negate())
                .filter(isSuperClass.negate())
                .sorted()
                .distinct();
        return importList;
    }
    
}
