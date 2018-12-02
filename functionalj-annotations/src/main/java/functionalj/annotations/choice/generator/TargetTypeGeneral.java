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
package functionalj.annotations.choice.generator;

import static functionalj.annotations.choice.generator.Utils.templateRange;
import static functionalj.annotations.choice.generator.Utils.toCamelCase;
import static functionalj.annotations.choice.generator.model.Method.Kind.DEFAULT;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;

import functionalj.annotations.choice.generator.model.Case;
import functionalj.annotations.choice.generator.model.Method;
import lombok.val;


/**
 * Generator for general methods of TargetType like toString, hashCode and equals.
 * 
 * @author manusitn
 */
public class TargetTypeGeneral implements Lines {
    private final TargetClass  targetClass;
    private final List<Case> choices;
    
    private final String targetName;
    
    /**
     * Constructor.
     * 
     * @param targetClass  the target class.
     * @param choices      the choices.
     */
    public TargetTypeGeneral(TargetClass targetClass, List<Case> choices) {
        this.targetClass = targetClass;
        this.choices     = choices;
        this.targetName  = targetClass.type.name;
    }
    
    @Override
    public List<String> lines() {
        val emptyLine = asList("");
        val firstSwitch = prepareFirstSwitch(targetName);
        val toString    = prepareToStringMethod();
        val hashCode    = prepareHashCode();
        val equals      = prepareEquals(targetName);
        return asList(
            firstSwitch, emptyLine,
            toString,    emptyLine,
            hashCode,    emptyLine,
            equals
        ).stream()
         .filter (Objects::nonNull)
         .flatMap(List::stream)
         .collect(toList());
    }
    
    private List<String> prepareFirstSwitch(final java.lang.String targetName) {
        val firstSwitchTypeDef = format("%1$sFirstSwitch%2$s", targetName, targetClass.generics());
        val firstSwitchLines = 
                asList(format(
                          "public final %1$s mapSwitch = new %1$s(this);\n"
                        + "@Override public %1$s __switch() {\n"
                        + "     return mapSwitch;\n"
                        + "}",
                        firstSwitchTypeDef)
                        .split("\n"));
        return firstSwitchLines;
    }
    
    private List<String> prepareToStringMethod() {
        if (hasMethod(format("String toString(%s)", targetClass.type.toString()), DEFAULT))
            return null;
        if (hasMethod(format("java.lang.String toString(%s)", targetClass.type.toString()), DEFAULT))
            return null;
        
        val choiceStrings = choices.stream()
            .map(choice -> {
                val camelName  = toCamelCase(choice.name);
                val paramCount = choice.params.size();
                if (paramCount == 0) {
                    return format("            .%1$s(__ -> \"%2$s\")", camelName, choice.name);
                } else {
                    val template       = templateRange(1, paramCount + 1, ",");
                    val templateParams = choice.params.stream().map(p -> camelName + "." + p.name).collect(joining(","));
                    return format("            .%1$s(%1$s -> \"%2$s(\" + String.format(\"%3$s\", %4$s) + \")\")", 
                                      camelName, choice.name, template, templateParams);
                }
            })
            .map("    "::concat)
            .collect(toList());
        
        val toStringLines = asList(
            asList(( "private volatile String toString = null;\n"
                  + "@Override\n"
                  + "public String toString() {\n"
                  + "    if (toString != null)\n"
                  + "        return toString;\n"
                  + "    synchronized(this) {\n"
                  + "        if (toString != null)\n"
                  + "            return toString;\n"
                  + "        toString = $utils.Switch(this)"
                  ).split("\n")
            ),
            choiceStrings,
            asList(("        ;\n"
                  + "        return toString;\n"
                  + "    }\n"
                  + "}"
                  ).split("\n")
            )
        ).stream()
         .flatMap(List::stream)
         .collect(toList());
        return toStringLines;
    }
    
    private List<String> prepareHashCode() {
        val mthdSignature = format("int hashCode(%s)", targetClass.type.toString());
        if (hasMethod(mthdSignature, DEFAULT))
            return null;
        return asList(
               ("@Override\n"
              + "public int hashCode() {\n"
              + "    return toString().hashCode();\n"
              + "}"
              ).split("\n"));
    }
    
    private List<String> prepareEquals(String targetName) {
        if (hasMethod(format("boolean equals(%s, Object)", targetClass.type.toString()), DEFAULT))
            return null;
        if (hasMethod(format("boolean equals(%s, java.lang.Object)", targetClass.type.toString()), DEFAULT))
            return null;
        
        return asList(format(
                  "@Override\n"
                + "public boolean equals(Object obj) {\n"
                + "    if (!(obj instanceof %1$s))\n"
                + "        return false;\n"
                + "    \n"
                + "    if (this == obj)\n"
                + "        return true;\n"
                + "    \n"
                + "    String objToString  = obj.toString();\n"
                + "    String thisToString = this.toString();\n"
                + "    return thisToString.equals(objToString);\n"
                + "}", targetName)
                .split("\n"));
    }
    
    private boolean hasMethod(String mthdSignature, Method.Kind kind) {
        return targetClass.spec.methods
                .stream()
                .filter  (m -> (kind == null) ? true : kind.equals(m.kind))
                .anyMatch(m -> mthdSignature.equals(m.signature));
    }
}