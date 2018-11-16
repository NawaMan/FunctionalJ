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

import static functionalj.annotations.uniontype.generator.Utils.toListCode;
import static functionalj.annotations.uniontype.generator.Utils.toStringLiteral;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.List;

import lombok.Value;
import lombok.val;
import lombok.experimental.Wither;

/**
 * Source specification of the data object.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
public class SourceSpec {
    
    private String         specClassName;
    private String         packageName;
    private String         targetClassName;
    private String         targetPackageName;
    private Boolean        isClass;
    private String         specObjName;
    private Configurations configures;
    private List<Getter>   getters;
    
    /** Configurations */
    public static class Configurations {
        
        /** Should extends/implements with the definition class/interface */
        public boolean coupleWithDefinition = true;
        /** Should the no-arguments constructor be created. */
        public boolean generateNoArgConstructor  = false;
        /** Should the required-field-only constructor be created. */
        public boolean generateRequiredOnlyConstructor = true;
        /** Should the all-arguments constructor be created. */
        public boolean generateAllArgConstructor  = true;
        /** Should the lens class be generated. */
        public boolean generateLensClass = true;
        /** Should the builder class be generated. */
        public boolean generateBuilderClass = true;
        
        public Configurations() {}
        public Configurations(
                boolean coupleWithDefinition,
                boolean generateNoArgConstructor,
                boolean generateRequiredOnlyConstructor,
                boolean generateAllArgConstructor,
                boolean generateLensClass,
                boolean generateBuilderClass) {
            this.coupleWithDefinition            = coupleWithDefinition;
            this.generateNoArgConstructor        = generateNoArgConstructor;
            this.generateRequiredOnlyConstructor = generateRequiredOnlyConstructor;
            this.generateAllArgConstructor       = generateAllArgConstructor;
            this.generateLensClass               = generateLensClass;
            this.generateBuilderClass            = generateBuilderClass;
        }
        
        @Override
        public String toString() {
            return "Configurations ["
                    + "coupleWithDefinition="            + coupleWithDefinition + ", "
                    + "generateNoArgConstructor="        + generateNoArgConstructor + ", "
                    + "generateRequiredOnlyConstructor=" + generateRequiredOnlyConstructor + ", "
                    + "generateAllArgConstructor="       + generateAllArgConstructor + ", "
                    + "generateLensClass="               + generateLensClass + ", "
                    + "generateBuilderClass="            + generateBuilderClass
                    + "]";
        }
        public String toCode() {
            val params = asList(
                    coupleWithDefinition,
                    generateNoArgConstructor,
                    generateRequiredOnlyConstructor,
                    generateAllArgConstructor,
                    generateLensClass,
                    generateBuilderClass
            );
            return "new functionalj.annotations.dataobject.generator.SourceSpec.Configurations("
                    + params.stream().map(String::valueOf).collect(joining(", "))
                    + ")";
        }
    }
    
    /** @return the target type. */
    public Type getTargetType() {
        return new Type(targetClassName, targetPackageName);
    }
    /** @return the type of this source. */
    public Type toType() {
        return new Type(specClassName, packageName);
    }
    public Boolean isClass() {
        return isClass;
    }
    
    // TODO - Optimize spec by importing them.
    
    public String toCode() {
        val params = asList(
                toStringLiteral(specClassName),
                toStringLiteral(packageName),
                toStringLiteral(targetClassName),
                toStringLiteral(targetPackageName),
                isClass,
                toStringLiteral(specObjName),
                configures.toCode(),
                toListCode(getters, Getter::toCode)
        );
        return "new functionalj.annotations.dataobject.generator.SourceSpec("
                + params.stream().map(String::valueOf).collect(joining(", "))
                + ")";
    }
}