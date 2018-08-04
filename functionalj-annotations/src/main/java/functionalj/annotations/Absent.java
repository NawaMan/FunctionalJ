//  ========================================================================
//  Copyright (c) 2017-2018 Nawapunth Manusitthipol (NawaMan).
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

/**
 * Intance of this class represent an absent of a parameter in functions.
 * 
 * @author NawaMan -- nawa@nawaman.net
 */
public final class Absent {
    
    /** The absent instance indicating that a parameter is absent. */
    public static final Absent absent = new Absent();
    
    /** The absent instance indicating that a parameter is absent. */
    public static final Absent __ = absent;
    
    /** The absent instance indicating that a parameter is absent. */
    public static final Absent drop = absent;
    
}
