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
package functionalj.annotations.dataobject.generator;

import static functionalj.annotations.dataobject.generator.model.Accessibility.PUBLIC;
import static functionalj.annotations.dataobject.generator.model.Modifiability.MODIFIABLE;
import static functionalj.annotations.dataobject.generator.model.Scope.NONE;
import static java.util.Arrays.asList;

import java.util.List;

import functionalj.annotations.dataobject.generator.model.GenClass;
import functionalj.annotations.dataobject.generator.model.GenConstructor;
import functionalj.annotations.dataobject.generator.model.GenField;
import functionalj.annotations.dataobject.generator.model.GenMethod;
import lombok.Value;
import lombok.experimental.Delegate;

/**
 * Specification for DataObject.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
public class DataObjectSpec {
    
    @Delegate
    private GenClass classSpec;
    
    private String sourceClassName;
    private String sourcePackageName;
    
    /**
     * Constructs a DataObjetSpec.
     * 
     * @param className           the name of the generated class.
     * @param packageName         the package name.
     * @param sourceClassName     the name of the source class.
     * @param sourcePackageName   the name of the source paclage.
     * @param extendeds           the list of extensions.
     * @param implementeds        the list of implementations.
     * @param constructors        the list of constructors.
     * @param fields              the list of fields.
     * @param methods             the list of methods.
     * @param innerClasses        the list of inner classes.
     * @param mores               other ILines.
     */
    public DataObjectSpec(
            String className,
            String packageName,
            String sourceClassName,
            String sourcePackageName,
            List<Type>           extendeds,
            List<Type>           implementeds,
            List<GenConstructor> constructors,
            List<GenField>       fields,
            List<GenMethod>      methods,
            List<GenClass>      innerClasses,
            List<ILines>         mores) {
        this.classSpec = new GenClass(PUBLIC, NONE, MODIFIABLE, new Type(className, packageName), null, extendeds, implementeds, constructors, fields, methods, innerClasses, mores);
        this.sourceClassName = sourceClassName;
        this.sourcePackageName = sourcePackageName;
    }
    
    /**
     * Returns the lens type of this class.
     * 
     * @return the lens type of this class.
     */
    public Type getLensType() {
        return new Type.TypeBuilder()
                .encloseName(type().simpleName())
                .simpleName(type().simpleName() + "Lens")
                .packageName(type().packageName())
                .generics(asList(new Type("HOST", null)))
                .build();
    }
    
}