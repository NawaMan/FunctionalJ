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
package functionalj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks classes to create data object class.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DataObject {
    
    /** @return the name of the target class. */
    public String name() default "";
    
    /**
     * @return the flag indicating that the generated class should extends or implements the definition
     *   class/interface - default to true.
     **/
    public boolean coupleWithDefinition() default true;
    
    /** @return the flag indicating that the no-arguments constructor should be created - default to true. */
    public boolean generateNoArgConstructor() default true;
    
    /** @return the flag indicating that the no-arguments constructor should be created - default to true. */
    public boolean generateAllArgConstructor() default true;
    
    /** @return the flag indicating that the lens class should be created - default to true. */
    public boolean generateLensClass() default true;
    
    
}
