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

import java.util.List;

import lombok.Value;
import lombok.experimental.Wither;

/**
 * Source specification of the data object.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Value
@Wither
public class SourceSpec {
    
    private String specClassName;
    private String packageName;
    private String targetClassName;
    private String targetPackageName;
    private boolean isClass;
    private Configurations configures;
    private List<Getter> getters;
    
    /** Configurations */
    public static class Configurations {
        
        /** Should extends/implements with the definition class/interface */
        public boolean coupleWithDefinition = true;
        /** Should the no-arguments constructor be created. */
        public boolean generateNoArgConstructor  = true;
        /** Should the lens class be generated. */
        public boolean generateLensClass = true;
    }
    
    /** @return the target type. */
    public Type getTargetType() {
        return new Type(targetClassName, targetPackageName);
    }
    /** @return the type of this source. */
    public Type toType() {
        return new Type(specClassName, packageName);
    }
}